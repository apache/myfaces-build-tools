/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
*/
package javax.faces.webapp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ExternalContext;
import javax.faces.el.ValueBinding;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.BodyContent;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Base class for all JSP tags that represent a JSF UIComponent.
 * <p>
 * <i>Disclaimer</i>: The official definition for the behaviour of
 * this class is the JSF specification but for legal reasons the
 * specification cannot be replicated here. Any javadoc present on this
 * class therefore describes the current implementation rather than the
 * officially required behaviour, though it is believed that this class
 * does comply with the specification.
 * 
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/api/index.html">JSF Specification</a> for more.
 * 
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class UIComponentTag
        implements Tag
{
    private static final String FORMER_CHILD_IDS_SET_ATTR = UIComponentTag.class.getName() + ".FORMER_CHILD_IDS";
    private static final String FORMER_FACET_NAMES_SET_ATTR = UIComponentTag.class.getName() + ".FORMER_FACET_NAMES";
    private static final String COMPONENT_STACK_ATTR =  UIComponentTag.class.getName() + ".COMPONENT_STACK";

    private static final String UNIQUE_ID_COUNTER_ATTR = UIComponentTag.class.getName() + ".UNIQUE_ID_COUNTER";

    private static final String PARTIAL_STATE_SAVING_METHOD_PARAM_NAME = "javax.faces.PARTIAL_STATE_SAVING_METHOD";
    private static final String PARTIAL_STATE_SAVING_METHOD_ON = "true";
    private static final String PARTIAL_STATE_SAVING_METHOD_OFF = "false";

    private static final String BEFORE_VIEW_CONTEXT = "org.apache.myfaces.BEFORE_VIEW_CONTEXT";

    protected PageContext pageContext = null;
    private Tag _parent = null;

    //tag attributes
    private String _binding = null;
    private String _id = null;
    private String _rendered = null;

    private FacesContext _facesContext = null;
    private UIComponent _componentInstance = null;
    private boolean _created = false;
    private Boolean _suppressed = null;
    private ResponseWriter _writer = null;
    private Set _childrenAdded = null;
    private Set _facetsAdded = null;
    private Boolean _partialStateSaving = null;

    private static Log log = LogFactory.getLog(UIComponentTag.class);
    private static final int READ_LENGTH = 8000;

    private boolean isPartialStateSavingOn(javax.faces.context.FacesContext context)
    {
        if(context == null) throw new NullPointerException("context");
        if (_partialStateSaving != null) return _partialStateSaving.booleanValue();
        String stateSavingMethod = context.getExternalContext().getInitParameter(PARTIAL_STATE_SAVING_METHOD_PARAM_NAME);
        if (stateSavingMethod == null)
        {
            _partialStateSaving = Boolean.FALSE; //Specs 10.1.3: default server saving
            context.getExternalContext().log("No context init parameter '"+PARTIAL_STATE_SAVING_METHOD_PARAM_NAME+"' found; no partial state saving method defined, assuming default partial state saving method off.");
        }
        else if (stateSavingMethod.equals(PARTIAL_STATE_SAVING_METHOD_ON))
        {
            _partialStateSaving = Boolean.TRUE;
        }
        else if (stateSavingMethod.equals(PARTIAL_STATE_SAVING_METHOD_OFF))
        {
            _partialStateSaving = Boolean.FALSE;
        }
        else
        {
            _partialStateSaving = Boolean.FALSE; //Specs 10.1.3: default server saving
            context.getExternalContext().log("Illegal context init parameter '"+PARTIAL_STATE_SAVING_METHOD_PARAM_NAME+"' found; illegal partial state saving method '" + stateSavingMethod + "', default partial state saving will be used (partial state saving off).");
        }
        return _partialStateSaving.booleanValue();
    }


    public UIComponentTag()
    {

    }

    public void release()
    {
        internalRelease();

        //members, that must/need only be reset when there is no more risk, that the container
        //wants to reuse this tag
        pageContext = null;
        _parent = null;

        // Reset tag attribute members. These are reset here rather than in
        // internalRelease because of some Resin-related issue. See commit
        // r166747.
        _binding = null;
        _id = null;
        _rendered = null;
    }


    /**
     * Reset any members that apply to the according component instance and
     * must not be reused if the container wants to reuse this tag instance.
     * This method is called when rendering for this tag is finished 
     * ( doEndTag() ) or when released by the container.
     */
    private void internalRelease()
    {
        _facesContext = null;
        _componentInstance = null;
        _created = false;
        _suppressed = null;
        _writer = null;
        _childrenAdded = null;
        _facetsAdded = null;
    }

    /** Setter for common JSF xml attribute "binding". */
    public void setBinding(String binding)
            throws JspException
    {
        if (!isValueReference(binding))
        {
            throw new IllegalArgumentException("not a valid binding: " + binding);
        }
        _binding = binding;
    }

    /** Setter for common JSF xml attribute "id". */
    public void setId(String id)
    {
        _id = id;
    }

    /**
     * Return the id (if any) specified as an xml attribute on this tag.
     */
    protected String getId()
    {
        return _id;
    }

    /** Setter for common JSF xml attribute "rendered". */
    public void setRendered(String rendered)
    {
        _rendered = rendered;
    }

    /**
     * Specify the "component type name" used together with the component's
     * family and the Application object to create a UIComponent instance for
     * this tag. This method is called by other methods in this class, and is
     * intended to be overridden in subclasses to specify the actual component
     * type to be created.
     * 
     * @return a registered component type name, never null.
     */
    public abstract String getComponentType();

    /**
     * Return the UIComponent instance associated with this tag.
     * @return a UIComponent, never null.
     */
    public UIComponent getComponentInstance()
    {
        return _componentInstance;
    }

    /**
     * Return true if this tag created the associated UIComponent (rather
     * than locating an existing instance of the UIComponent in the view).
     */
    public boolean getCreated()
    {
        return _created;
    }

    /**
     * Return the nearest JSF tag that encloses this tag.
     */
    public static UIComponentTag getParentUIComponentTag(PageContext pageContext)
    {
        // Question: why not just walk up the _parent chain testing for
        // instanceof UIComponentTag rather than maintaining a separate
        // stack with the pushTag and popTag methods?
        List list = (List)pageContext.getAttribute(COMPONENT_STACK_ATTR,
                                                   PageContext.REQUEST_SCOPE);
        if (list != null)
        {
            return (UIComponentTag)list.get(list.size() - 1);
        }
        return null;
    }

    /** See documentation for pushTag. */
    private void popTag()
    {
        List list = (List)pageContext.getAttribute(COMPONENT_STACK_ATTR,
                                                    PageContext.REQUEST_SCOPE);
        if (list != null)
        {
            int size = list.size();
            list.remove(size -1);
            if (size <= 1)
            {
                pageContext.removeAttribute(COMPONENT_STACK_ATTR,
                                             PageContext.REQUEST_SCOPE);
            }
        }
    }

    /**
     * Push this tag onto the stack of JSP tags seen.
     * <p>
     * The pageContext's request scope map is used to hold a stack of
     * JSP tag objects seen so far, so that a new tag can find the
     * parent tag that encloses it. Access to the parent tag is used
     * to find the parent UIComponent for the component associated
     * with this tag plus some other uses. 
     */
    private void pushTag()
    {
        List list = (List)pageContext.getAttribute(COMPONENT_STACK_ATTR,
                                                    PageContext.REQUEST_SCOPE);
        if (list == null)
        {
            list = new ArrayList();
            pageContext.setAttribute(COMPONENT_STACK_ATTR,
                                      list,
                                      PageContext.REQUEST_SCOPE);
        }
        list.add(this);
    }

    /**
     * Specify the "renderer type name" used together with the current
     * renderKit to get a Renderer instance for the corresponding UIComponent.
     * <p>
     * A JSP tag can return null here to use the default renderer type string.
     * If non-null is returned, then the UIComponent's setRendererType method
     * will be called passing this value, and this will later affect the
     * type of renderer object returned by UIComponent.getRenderer().
     */
    public abstract String getRendererType();

    /**
     * Return true if the specified string contains an EL expression.
     * <p>
     * UIComponent properties are often required to be value-binding
     * expressions; this method allows code to check whether that is
     * the case or not.
     */
    public static boolean isValueReference(String value)
    {
        if (value == null) throw new NullPointerException("value");

        int start = value.indexOf("#{");
        if (start < 0) return false;

        int end = value.lastIndexOf('}');
        return (end >=0 && start < end);
    }

    /**
     * Standard method invoked by the JSP framework to inform this tag
     * of the PageContext associated with the jsp page currently being
     * processed. 
     */
    public void setPageContext(PageContext pageContext)
    {
        this.pageContext = pageContext;
    }

    /**
     * Returns the enclosing JSP tag object. Note that this is not
     * necessarily a JSF tag.
     */
    public Tag getParent()
    {
        return _parent;
    }

    /**
     * Standard method invoked by the JSP framework to inform this tag
     * of the enclosing JSP tag object.
     */
    public void setParent(Tag parent)
    {
        _parent = parent;
    }

    /**
     * Flushes the Writer and adds the content of the Writer ( which is only the direct output
     * of the JSP if the component uses a dummyWriter wich does not render the Component itself) as
     * UIOutput component after the current component to the myfaces component tree.
     * @param writerToFlush
     * @throws JspException
     * @throws IOException
     */

    private void flushWriter( JspWriter writerToFlush ) throws JspException,IOException {

      if (getFacesContext() == null)
            return;
      BodyContent tempwriter;
      _componentInstance = findComponent(_facesContext);
      if (writerToFlush instanceof BodyContent)
        {
            int count = 0;
            char [] readChars = new char[READ_LENGTH];
            tempwriter = (BodyContent) writerToFlush;
            Reader read =tempwriter.getReader();
            count = read.read(readChars,0,READ_LENGTH);
            String readString = new String(readChars,0,count);
            if(!readString.trim().equals(""))
            {
                //_componentInstance = findComponent(_facesContext);
                UIComponentTag parentTag = getParentUIComponentTag(pageContext);
                addOutputComponentAfterComponent(parentTag,createUIOutputComponentFromString(readString),_componentInstance);
                tempwriter.clearBody();
            }

        }
        else
      {
          if (_componentInstance.getParent() == null) {
            writerToFlush.flush();
            String beforeViewContent = _facesContext.getExternalContext().getResponse().toString();
            if((beforeViewContent != null)&&(!beforeViewContent.trim().equals("")))
            {   // BEFORE BODY CONTENT
                _facesContext.getExternalContext().getRequestMap().put(BEFORE_VIEW_CONTEXT,beforeViewContent);
            }
          }
      }

    }

    /**
     * Adds an Output Component afert a given component.
     * @param parentTag the parent tag of the component.
     * @param outputComponent the component which should be added after this component.
     * @param component the component after witch the outputComponent should be added.
     */
    private void addOutputComponentAfterComponent(UIComponentTag parentTag,
                                                  UIComponent outputComponent,
                                                  UIComponent component) {
	int indexOfComponentInParent = 0;
	UIComponent parent = component.getParent();

	if (null == parent) {
	    return;
	}
	List children = parent.getChildren();
	indexOfComponentInParent = children.indexOf(component);
	if (children.size() - 1 == indexOfComponentInParent) {
	    children.add(outputComponent);
	}
	else {
	    children.add(indexOfComponentInParent + 1, outputComponent);
	}
    parentTag.addChildIdToParentTag(parentTag,outputComponent.getId());

    }


    /**
     * Creates a UIOutput component and fill in the content. The attribute
     * escape will be set to false. The content will be renderd as it is.
     * @param content the content of the UIOutput component.
     * @return A UIOutput component with the content an the attribute escape
     *         set to false.
     */
     private UIComponent createUIOutputComponentFromString( String content) {
         UIOutput outputComponent = null;

         outputComponent = createUIOutputComponent(getFacesContext());
         outputComponent.setValue(content);

         return outputComponent;
     }


    /**
     * Creates a UIOutput component with the attribute escape = false. The Value
     * of the Component will be rendert as it is, which is useful if there are html tags
     * which should be injectet into the component tree.
     * @param context the Myfaces Context.
     * @return A UIOutput Component with escape = false. The String of this Component
     *         will be rendert as it is.
     */
     private UIOutput createUIOutputComponent(FacesContext context) {
         if (context == null) return null;
         UIOutput outputComponent = null;
         Application application = context.getApplication();
         outputComponent = (UIOutput) application.createComponent("javax.faces.HtmlOutputText");
         outputComponent.setTransient(true);
         outputComponent.getAttributes().put("escape", Boolean.FALSE);
         outputComponent.setId(context.getViewRoot().createUniqueId());
         return outputComponent;
     }

    /**
     * Invoked by the standard jsp processing mechanism when the opening
     * tag of a JSF component element is found.
     * <p>
     * The UIComponent associated with this tag is created (if the view
     * doesn't exist) or located if the view is being re-rendered. If
     * the component is not "suppressed" then its encodeBegin method is
     * called (see method isSuppressed). Note also that method
     * encodeBegin is <i>not</i> called for components for which
     * getRendersChildren returns true; that occurs only in doEndTag.
     */
    public int doStartTag()
            throws JspException
    {
        setupResponseWriter();
        FacesContext facesContext = getFacesContext();
        if ( isPartialStateSavingOn(facesContext) )
        {
            JspWriter writer = pageContext.getOut();
            try {
                flushWriter(writer);
            } catch (IOException e) {
                log.error(e.toString());
            }
        }
        UIComponent component = findComponent(facesContext);
        if (!component.getRendersChildren() && !isSuppressed())
        {
            try
            {
                encodeBegin();
                _writer.flush();
            }
            catch (IOException e)
            {
                throw new JspException(e.getMessage(), e);
            }
        }
        pushTag();
        return getDoStartValue();
    }

    /**
     * Invoked by the standard jsp processing mechanism when the closing
     * tag of a JSF component element is found.
     * <p>
     * When the view is being re-rendered, any former children of this tag's
     * corresponding component which do not have corresponding tags
     * as children of this tag are removed from the view. This isn't likely
     * to be a common occurrence: wrapping JSF tags in JSTL tag "c:if" is
     * one possible cause. Programmatically created components are not affected
     * by this.
     * <p>
     * If the corresponding component returns true from getRendersChildren
     * then its encodeBegin and encodeChildren methods are called here.
     * <p>
     * The component's encodeEnd method is called provided the component
     * is not "suppressed".
     */
    public int doEndTag()
            throws JspException
    {
        if (isPartialStateSavingOn(getFacesContext()))
        {
            JspWriter writerToFlush =   pageContext.getOut();
            if (_componentInstance == null) {
                findComponent(getFacesContext());
            }
            if ((!(writerToFlush instanceof BodyContent)) && _componentInstance.getParent() == null) {
                //add the before view content to the UIViewRoot
                _componentInstance.getChildren().add(0,
                        createUIOutputComponentFromString((String)
                                _facesContext.getExternalContext().getRequestMap().get(BEFORE_VIEW_CONTEXT)));
                _facesContext.getExternalContext().getRequestMap().remove(BEFORE_VIEW_CONTEXT);
            }
        }

        popTag();
        UIComponent component = getComponentInstance();
        removeFormerChildren(component);
        removeFormerFacets(component);

        try
        {
            if (!isSuppressed())
            {
            	// Note that encodeBegin is inside this if-clause because for components that do
            	// NOT render their children, begin was already called during the doStartTag method.
            	// However for components that do render their children, we want those children to
            	// exist before the begin method gets called, so delay until here. Of course that
            	// causes nasty problems when this tag has non-JSF stuff inside it, eg plain text.
            	// That plain text will be output before encodeBegin is invoked. The spec authors
            	// presumably decided this was an acceptable tradeoff to allow encodeBegin to have
            	// access to the children.
                if (component.getRendersChildren())
                {
                    encodeBegin();
                    encodeChildren();
                }
                encodeEnd();
            }
        }
        catch (IOException e)
        {
            throw new JspException(e.getMessage(), e);
        }

        int retValue = getDoEndValue();
        internalRelease();
        return retValue;
    }

    /**
     * Remove any child components of the associated components which do not
     * have corresponding tags as children of this tag. This only happens
     * when a view is being re-rendered and there are components in the view
     * tree which don't have corresponding JSP tags. Wrapping JSF tags in
     * JSTL "c:if" statements is one way this can happen.
     * <br />
     * Attention: programmatically added components are are not affected by this:
     * they will not be on the old list of created components nor on the new list
     * of created components, so nothing will happen to them.
     */
    private void removeFormerChildren(UIComponent component)
    {
        Set formerChildIdsSet = (Set)component.getAttributes().get(FORMER_CHILD_IDS_SET_ATTR);
        if (formerChildIdsSet != null)
        {
            for (Iterator iterator = formerChildIdsSet.iterator(); iterator.hasNext();)
            {
                String childId = (String)iterator.next();
                if (_childrenAdded == null || !_childrenAdded.contains(childId))
                {
                    UIComponent childToRemove = component.findComponent(childId);
                    if (childToRemove != null)
                    {
                        component.getChildren().remove(childToRemove);
                    }
                }
            }
            if (_childrenAdded == null)
            {
                component.getAttributes().remove(FORMER_CHILD_IDS_SET_ATTR);
            }
            else
            {
                component.getAttributes().put(FORMER_CHILD_IDS_SET_ATTR, _childrenAdded);
            }
        }
        else
        {
            if (_childrenAdded != null)
            {
                component.getAttributes().put(FORMER_CHILD_IDS_SET_ATTR, _childrenAdded);
            }
        }
    }

    /** See removeFormerChildren. */
    private void removeFormerFacets(UIComponent component)
    {
        Set formerFacetNamesSet = (Set)component.getAttributes().get(FORMER_FACET_NAMES_SET_ATTR);
        if (formerFacetNamesSet != null)
        {
            for (Iterator iterator = formerFacetNamesSet.iterator(); iterator.hasNext();)
            {
                String facetName = (String)iterator.next();
                if (_facetsAdded == null || !_facetsAdded.contains(facetName))
                {
                    component.getFacets().remove(facetName);
                }
            }
            if (_facetsAdded == null)
            {
                component.getAttributes().remove(FORMER_FACET_NAMES_SET_ATTR);
            }
            else
            {
                component.getAttributes().put(FORMER_FACET_NAMES_SET_ATTR, _facetsAdded);
            }
        }
        else
        {
            if (_facetsAdded != null)
            {
                component.getAttributes().put(FORMER_FACET_NAMES_SET_ATTR, _facetsAdded);
            }
        }
    }

    /**
     * Invoke encodeBegin on the associated UIComponent. Subclasses can
     * override this method to perform custom processing before or after
     * the UIComponent method invocation.
     */
    protected void encodeBegin()
            throws IOException
    {
        if(log.isDebugEnabled())
            log.debug("Entered encodeBegin for client-Id: "+_componentInstance.getClientId(getFacesContext()));
        _componentInstance.encodeBegin(getFacesContext());
        if(log.isDebugEnabled())
            log.debug("Exited encodeBegin");
    }

    /**
     * Invoke encodeChildren on the associated UIComponent. Subclasses can
     * override this method to perform custom processing before or after
     * the UIComponent method invocation. This is only invoked for components
     * whose getRendersChildren method returns true.
     */
    protected void encodeChildren()
            throws IOException
    {
        if(log.isDebugEnabled())
            log.debug("Entered encodeChildren for client-Id: "+_componentInstance.getClientId(getFacesContext()));
        _componentInstance.encodeChildren(getFacesContext());
        if(log.isDebugEnabled())
            log.debug("Exited encodeChildren for client-Id: "+_componentInstance.getClientId(getFacesContext()));
    }

    /**
     * Invoke encodeEnd on the associated UIComponent. Subclasses can override this
     * method to perform custom processing before or after the UIComponent method
     * invocation.
     */
    protected void encodeEnd()
            throws IOException
    {
        if(log.isDebugEnabled())
            log.debug("Entered encodeEnd for client-Id: "+_componentInstance.getClientId(getFacesContext()));
        _componentInstance.encodeEnd(getFacesContext());
        if(log.isDebugEnabled())
            log.debug("Exited encodeEnd for client-Id: "+_componentInstance.getClientId(getFacesContext()));

    }

    /**
     * Return the corresponding UIComponent for this tag, creating it
     * if necessary.
     * <p>
     * If this is not the first time this method has been called, then
     * return the cached component instance found last time.
     * <p>
     * If this is not the first time this view has been seen, then
     * locate the existing component using the id attribute assigned
     * to this tag and return it. Note that this is simple for
     * components with user-assigned ids. For components with
     * generated ids, the "reattachment" relies on the fact that
     * UIViewRoot will generate the same id values for tags in
     * this page as it did when first generating the view. For this
     * reason all JSF tags within a JSTL "c:if" are required to have
     * explicitly-assigned ids. 
     * <p>
     * Otherwise create the component, populate its properties from
     * the xml attributes on this JSP tag and attach it to its parent.
     * <p>
     * When a component is found or created the parent JSP tag is also
     * told that the component has been "seen". When the parent tag
     * ends it will delete any components which were in the view
     * previously but have not been seen this time; see doEndTag for
     * more details.
     */
    protected UIComponent findComponent(FacesContext context)
            throws JspException
    {
        if (_componentInstance != null) return _componentInstance;
        UIComponentTag parentTag = getParentUIComponentTag(pageContext);
        if (parentTag == null)
        {
            //This is the root
            _componentInstance = context.getViewRoot();
            setProperties(_componentInstance);
            return _componentInstance;
        }

        UIComponent parent = parentTag.getComponentInstance();
        //TODO: what if parent == null?
        if (parent == null) throw new IllegalStateException("parent is null?");

        String facetName = getFacetName();
        if (facetName != null)
        {
            //Facet
            String id = getOrCreateUniqueId(context);
            _componentInstance = parent.getFacet(facetName);
            if (_componentInstance == null)
            {
            	_componentInstance = createComponentInstance(context, id);
                setProperties(_componentInstance);
                parent.getFacets().put(facetName, _componentInstance);
            }
            else
            {
            	if (checkFacetNameOnParentExists(parentTag, facetName))
            	{
            		throw new IllegalStateException("facet '" + facetName + "' already has a child associated. current associated component id: "
            			+ _componentInstance.getClientId(context) + " class: " + _componentInstance.getClass().getName());
            	}
            }
            
            addFacetNameToParentTag(parentTag, facetName);
            return _componentInstance;
        }
        else
        {
            //Child
            //
            // Note that setProperties is called only when we create the
            // component; on later passes, the attributes defined on the
            // JSP tag are set on this Tag object, but then completely
            // ignored.

            String id = getOrCreateUniqueId(context);
            
            // Warn users that this tag is about to find/steal the UIComponent
            // that has already been created for a sibling tag with the same id value .
            // _childrenAdded is a Set, and we will stomp over a past id when calling 
            // addChildIdToParentTag.
            //
            // we throw an exception here - RI issues a warning.
            if(parentTag._childrenAdded != null && parentTag._childrenAdded.contains(id))
            {
                throw new FacesException("There is more than one JSF tag with id : " + id+" for parent component with id : '"+parent.getId()+"'");
            }
            
            _componentInstance = findComponent(parent,id);
            if (_componentInstance == null)
            {
                _componentInstance = createComponentInstance(context, id);
                setProperties(_componentInstance);
                int index = getAddedChildrenCount(parentTag);
                List children = parent.getChildren();
                if (index <= children.size())
                {
                    children.add(index, _componentInstance);
                }
                else
                {
                    throw new FacesException("cannot add component with id '" +
                            _componentInstance.getId() + " to its parent component with id : '"+parent.getId()+"' and path '"+
                            getPathToComponent(parent)+"'at position :"+index+" in list of children. "+
                            "This might be a problem due to a duplicate id in a previously added component,"+
                            "if this is the case, the problematic id might be one of : "+printSet(parentTag._childrenAdded));
                }
            }
            addChildIdToParentTag(parentTag, id);
            return _componentInstance;
        }
    }

    private UIComponent findComponent(UIComponent parent, String id)
    {
        List li = parent.getChildren();

        for (int i = 0; i < li.size(); i++)
        {
            UIComponent uiComponent = (UIComponent) li.get(i);
            if(uiComponent.getId()!=null && uiComponent.getId().equals(id))
            {
                return uiComponent;
            }
        }

        return null;
    }

    /**
     * Utility method for creating diagnostic output.
     */
    private String printSet(Set childrenAdded)
    {
        StringBuffer buf = new StringBuffer();

        if(childrenAdded!=null)
        {
            Iterator it = childrenAdded.iterator();

            while (it.hasNext())
            {
                Object obj =  it.next();
                buf.append(obj);

                if(it.hasNext())
                    buf.append(",");
            }
        }
        return buf.toString();
    }


    private String getOrCreateUniqueId(FacesContext context)
    {
        String id = getId();
        if (id != null)
        {
            return id;
        }
        else
        {
            //we've been calling
            //return context.getViewRoot().createUniqueId(); - don't want that anymore
            Long currentCounter = (Long) context.getExternalContext().getRequestMap().get(UNIQUE_ID_COUNTER_ATTR);
            long lCurrentCounter = 0;

            if(currentCounter!=null)
            {
                lCurrentCounter = currentCounter.longValue();
            }

            StringBuffer retValue = new StringBuffer(UIViewRoot.UNIQUE_ID_PREFIX);
            retValue.append("Jsp");
            retValue.append(lCurrentCounter);

            lCurrentCounter++;

            context.getExternalContext().getRequestMap().put(UNIQUE_ID_COUNTER_ATTR,new Long(lCurrentCounter));

            ExternalContext extCtx = FacesContext.getCurrentInstance().getExternalContext();
            return extCtx.encodeNamespace(retValue.toString());
        }
    }

    /**
     * Create a UIComponent. Abstract method getComponentType is invoked to
     * determine the actual type name for the component to be created.
     * 
     * If this tag has a "binding" attribute, then that is immediately
     * evaluated to store the created component in the specified property.
     */
    private UIComponent createComponentInstance(FacesContext context, String id)
    {
        String componentType = getComponentType();
        if (componentType == null)
        {
            throw new NullPointerException("componentType");
        }

        if (_binding != null)
        {
            Application application = context.getApplication();
            ValueBinding componentBinding = application.createValueBinding(_binding);
            UIComponent component = application.createComponent(componentBinding,
                                                                context,
                                                                componentType);
            component.setId(id);
            component.setValueBinding("binding", componentBinding);
            recurseFacetsAndChildrenForId(component.getFacetsAndChildren(), id + "_", 0);
            _created = true;
            return component;
        }
        else
        {
            UIComponent component = context.getApplication().createComponent(componentType);
            component.setId(id);
            _created = true;
            return component;
        }
    }

    /**
     * Recurse all facets and children and assign them an unique ID if
     * necessary. We must *not* use UIViewRoot#createUniqueId here,
     * because this would affect the order of the created ids upon
     * rerendering the page!
     */
    private int recurseFacetsAndChildrenForId(
            Iterator facetsAndChildren,
            String idPrefix,
            int cnt)
    {
        while (facetsAndChildren.hasNext())
        {
            UIComponent comp = (UIComponent)facetsAndChildren.next();
            if (comp.getId() == null)
            {
                ++cnt;
                comp.setId(idPrefix + cnt);
            }
            cnt = recurseFacetsAndChildrenForId(comp.getFacetsAndChildren(), idPrefix, cnt);
        }
        return cnt;
    }

    /**
     * Notify the enclosing JSP tag of the id of this component's id. The
     * parent tag will later delete any existing view components that were
     * not seen during this rendering phase; see doEndTag for details.
     */
    private void addChildIdToParentTag(UIComponentTag parentTag, String id)
    {
        if (parentTag._childrenAdded == null)
        {
            parentTag._childrenAdded = new HashSet();
        }
        parentTag._childrenAdded.add(id);
    }

    /**
     * check if the facet is already added to the parent
     */
    private boolean checkFacetNameOnParentExists(UIComponentTag parentTag, String facetName)
    {
        return parentTag._facetsAdded != null && parentTag._facetsAdded.contains(facetName); 
    }
    
    /**
     * Notify the enclosing JSP tag of the id of this facet's id. The parent
     * tag will later delete any existing view facets that were not seen
     * during this rendering phase; see doEndTag for details.
     */
    private void addFacetNameToParentTag(UIComponentTag parentTag, String facetName)
    {
        if (parentTag._facetsAdded == null)
        {
            parentTag._facetsAdded = new HashSet();
        }
        parentTag._facetsAdded.add(facetName);
    }

    private int getAddedChildrenCount(UIComponentTag parentTag)
    {
        return parentTag._childrenAdded != null ?
               parentTag._childrenAdded.size() :
               0;
    }

    /**
     * Get the value to be returned by the doStartTag method to the
     * JSP framework. Subclasses which wish to use the inherited
     * doStartTag but control whether the tag is permitted to contain
     * nested tags or not can just override this method to return
     * Tag.SOME_CONSTANT.
     * 
     * @return Tag.EVAL_BODY_INCLUDE
     */
    protected int getDoStartValue()
            throws JspException
    {
        return Tag.EVAL_BODY_INCLUDE;
    }

    /**
     * Get the value to be returned by the doEndTag method to the
     * JSP framework. Subclasses which wish to use the inherited
     * doEndTag but control whether the tag is permitted to contain
     * nested tags or not can just override this method to return
     * Tag.SOME_CONSTANT.
     * 
     * @return Tag.EVAL_PAGE
     */
    protected int getDoEndValue()
            throws JspException
    {
        return Tag.EVAL_PAGE;
    }

    protected FacesContext getFacesContext()
    {
        if (_facesContext == null)
        {
            _facesContext = FacesContext.getCurrentInstance();
        }
        return _facesContext;
    }


    private boolean isFacet()
    {
        return _parent != null && _parent instanceof FacetTag;
    }

    protected String getFacetName()
    {
        return isFacet() ? ((FacetTag)_parent).getName() : null;
    }

    /**
     * Determine whether this component renders itself. A component
     * is "suppressed" when it is either not rendered, or when it is
     * rendered by its parent component at a time of the parent's choosing.
     */
    protected boolean isSuppressed()
    {
        if (_suppressed == null)
        {
            // we haven't called this method before, so determine the suppressed
            // value and cache it for later calls to this method.

            if (isFacet())
            {
                // facets are always rendered by their parents --> suppressed
                _suppressed = Boolean.TRUE;
                return true;
            }

            UIComponent component = getComponentInstance();

            // Does any parent render its children?
            // (We must determine this first, before calling any isRendered method
            //  because rendered properties might reference a data var of a nesting UIData,
            //  which is not set at this time, and would cause a VariableResolver error!)
            UIComponent parent = component.getParent();
            while (parent != null)
            {
                if (parent.getRendersChildren())
                {
                    //Yes, parent found, that renders children --> suppressed
                    _suppressed = Boolean.TRUE;
                    return true;
                }
                parent = parent.getParent();
            }

            // does component or any parent has a false rendered attribute?
            while (component != null)
            {
                if (!component.isRendered())
                {
                    //Yes, component or any parent must not be rendered --> suppressed
                    _suppressed = Boolean.TRUE;
                    return true;
                }
                component = component.getParent();
            }

            // else --> not suppressed
            _suppressed = Boolean.FALSE;
        }
        return _suppressed.booleanValue();
    }

    protected void setProperties(UIComponent component)
    {
        if (getRendererType() != null)
        {
            _componentInstance.setRendererType(getRendererType());
        }

        if (_rendered != null)
        {
            if (isValueReference(_rendered))
            {
                ValueBinding vb = getFacesContext().getApplication().createValueBinding(_rendered);
                component.setValueBinding("rendered", vb);
            } else
            {
                boolean b = Boolean.valueOf(_rendered).booleanValue();
                component.setRendered(b);
            }
        }
    }

    protected void setupResponseWriter()
    {
        FacesContext facesContext = getFacesContext();

        if(facesContext == null)
        {
            throw new FacesException("Faces context not found. getResponseWriter will fail. "+
                    "Check if the FacesServlet has been initialized at all in your web.xml configuration file"+
                    "and if you are accessing your jsf-pages through the correct mapping. E.g.: if your FacesServlet is mapped to "+
                    " *.jsf (with the <servlet-mapping>-element), you need to access your pages as 'sample.jsf'. If you tried to access 'sample.jsp', you'd get this error-message."                    
                    );
        }

        _writer = facesContext.getResponseWriter();
        if (_writer == null)
        {
            RenderKitFactory renderFactory = (RenderKitFactory)FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
            RenderKit renderKit = renderFactory.getRenderKit(facesContext,
                                                             facesContext.getViewRoot().getRenderKitId());

            if (isPartialStateSavingOn(facesContext)) {
                    _writer = renderKit.createResponseWriter(new _DummyPageContextOutWriter(pageContext),
                                                             null /*Default: get the allowed content-types from the accept-header*/,
                                                             pageContext.getRequest().getCharacterEncoding());
            } else {
                    _writer = renderKit.createResponseWriter(new _PageContextOutWriter(pageContext),
                                                             null /*Default: get the allowed content-types from the accept-header*/,
                                                             pageContext.getRequest().getCharacterEncoding());

            }
            facesContext.setResponseWriter(_writer);
        }
    }

    /** Generate diagnostic output. */
    private String getPathToComponent(UIComponent component)
    {
        StringBuffer buf = new StringBuffer();

        if(component == null)
        {
            buf.append("{Component-Path : ");
            buf.append("[null]}");
            return buf.toString();
        }

        getPathToComponent(component,buf);

        buf.insert(0,"{Component-Path : ");
        buf.append("}");

        return buf.toString();
    }

    /** Generate diagnostic output. */
    private static void getPathToComponent(UIComponent component, StringBuffer buf)
    {
        if(component == null)
            return;

        StringBuffer intBuf = new StringBuffer();

        intBuf.append("[Class: ");
        intBuf.append(component.getClass().getName());
        if(component instanceof UIViewRoot)
        {
            intBuf.append(",ViewId: ");
            intBuf.append(((UIViewRoot) component).getViewId());
        }
        else
        {
            intBuf.append(",Id: ");
            intBuf.append(component.getId());
        }
        intBuf.append("]");

        buf.insert(0,(Object)intBuf);

        getPathToComponent(component.getParent(),buf);
    }

}
