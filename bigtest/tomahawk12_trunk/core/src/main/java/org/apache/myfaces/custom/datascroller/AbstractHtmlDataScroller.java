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
package org.apache.myfaces.custom.datascroller;

import javax.faces.FacesException;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.ext.HtmlPanelGroup;

/**
 * Scroller for UIData components eg. dataTable 
 * 
 * Must be nested inside footer facet of dataTable OR for 
 * attribute must be given so that corresponding uiData can be found. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * A component which works together with a UIData component to allow a
 * user to view a large list of data one "page" at a time, and navigate
 * between pages.
 * 
 * @JSFComponent
 *   name = "t:dataScroller"
 *   class = "org.apache.myfaces.custom.datascroller.HtmlDataScroller"
 *   superClass = "org.apache.myfaces.custom.datascroller.AbstractHtmlDataScroller"
 *   tagClass = "org.apache.myfaces.custom.datascroller.HtmlDataScrollerTag"
 *  
 * @JSFJspProperty name = "onkeydown" tagExcluded = "true"
 * @JSFJspProperty name = "onkeypress" tagExcluded = "true"
 * @JSFJspProperty name = "onkeyup" tagExcluded = "true"
 * @JSFJspProperty name = "onmousedown" tagExcluded = "true"
 * @JSFJspProperty name = "onmousemove" tagExcluded = "true"
 * @JSFJspProperty name = "onmouseout" tagExcluded = "true"
 * @JSFJspProperty name = "onmouseover" tagExcluded = "true"
 * @JSFJspProperty name = "onmouseup" tagExcluded = "true"
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlDataScroller extends HtmlPanelGroup 
    implements ActionSource
{
    
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlDataScroller";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.DataScroller";
    private static final boolean DEFAULT_IMMEDIATE = false;
    
    private static final Log log = LogFactory.getLog(AbstractHtmlDataScroller.class);

    private static final String FIRST_FACET_NAME = "first";
    private static final String LAST_FACET_NAME = "last";
    private static final String NEXT_FACET_NAME = "next";
    private static final String PREVIOUS_FACET_NAME = "previous";
    private static final String FAST_FORWARD_FACET_NAME = "fastforward";
    private static final String FAST_REWIND_FACET_NAME = "fastrewind";

    public static final String FACET_FIRST = "first".intern();
    public static final String FACET_PREVIOUS = "previous".intern();
    public static final String FACET_NEXT = "next".intern();
    public static final String FACET_LAST = "last".intern();
    public static final String FACET_FAST_FORWARD = "fastf".intern();
    public static final String FACET_FAST_REWIND = "fastr".intern();
        
    private static final String TABLE_LAYOUT = "table";
    private static final String LIST_LAYOUT = "list";
    private static final String SINGLE_LIST_LAYOUT = "singleList";
    private static final String SINGLE_TABLE_LAYOUT = "singleTable";

    // just for caching the associated uidata
    private transient UIData _UIData;

    private transient Boolean _listLayout;

    private transient Boolean _singleElementLayout;

    private MethodBinding _actionListener;

    public boolean isListLayout()
    {
        if(_listLayout == null)
        {
            String layout=getLayout();
            if(layout == null || layout.equals(TABLE_LAYOUT) || layout.equals(SINGLE_TABLE_LAYOUT))
                _listLayout = Boolean.FALSE;
            else if(layout.equals(LIST_LAYOUT) || layout.equals(SINGLE_LIST_LAYOUT))
            {
                _listLayout = Boolean.TRUE;
            }
            else
            {
                log.error("Invalid layout-parameter : "+layout +" provided. Defaulting to table-layout.");
                _listLayout = Boolean.FALSE;
            }
        }

        return _listLayout.booleanValue();
    }

    public boolean isSingleElementLayout()
    {
        if(_singleElementLayout == null)
        {
            String layout=getLayout();
            if(layout == null || layout.equals(SINGLE_LIST_LAYOUT) || layout.equals(SINGLE_TABLE_LAYOUT))
                _singleElementLayout = Boolean.TRUE;
            else
                _singleElementLayout = Boolean.FALSE;
        }

        return _singleElementLayout.booleanValue();
    }

    /**
     * Catch any attempts to queue events for this component, and ensure
     * the event's phase is set appropriately. Events are expected to be
     * queued by this component's renderer.
     * <p>
     * When this component is marked "immediate", any ActionEvent will
     * be marked to fire in the "apply request values" phase. When this
     * component is not immediate the event will fire during the
     * "invoke application" phase instead.
     */
    public void queueEvent(FacesEvent event)
    {
        if (event != null && event instanceof ActionEvent)
        {
            if (isImmediate())
            {
                event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
            }
            else
            {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            }
        }
        super.queueEvent(event);
    }

    /**
     * Invoke any action listeners attached to this class.
     * <p>
     * After listener invocation, the associated UIData's properties get
     * updated:
     * <ul>
     * <li>if the user selected an absolute page# then setFirst is called with
     * uiData.getRows() * pageNumber.
     * <li>if the user selected the "first page" option then setFirst(0) is called.
     * <li>if the user selected the "previous page" option then setFirst is decremented
     * by uiData.getRows().
     * <li>if the user selected the "fast rewind" option then setFirst is decremented
     * by uiData.getRows() * fastStep.
     * <li>next, fast-forward and last options have the obvious effect.
     * </ul>
     */
    public void broadcast(FacesEvent event) throws AbortProcessingException
    {
        super.broadcast(event);

        if (event instanceof ScrollerActionEvent)
        {
            ScrollerActionEvent scrollerEvent = (ScrollerActionEvent) event;

            broadcastToActionListener(scrollerEvent);
            
            // huh? getUIData never returns null.
            UIData uiData = getUIData();
            if (uiData == null)
            {
                return;
            }

            int pageindex = scrollerEvent.getPageIndex();
            if (pageindex == -1)
            {
                String facet = scrollerEvent.getScrollerfacet();
                if (FACET_FIRST.equals(facet))
                {
                    setFirst(uiData, 0);
                }
                else if (FACET_PREVIOUS.equals(facet))
                {
                    int previous = uiData.getFirst() - uiData.getRows();
                    if (previous >= 0)
                        setFirst(uiData, previous);
                }
                else if (FACET_NEXT.equals(facet))
                {
                    int next = uiData.getFirst() + uiData.getRows();
                    if (next < uiData.getRowCount())
                        setFirst(uiData, next);
                }
                else if (FACET_FAST_FORWARD.equals(facet))
                {
                    int fastStep = getFastStep();
                    if (fastStep <= 0)
                        fastStep = 1;
                    int next = uiData.getFirst() + uiData.getRows() * fastStep;
                    int rowcount = uiData.getRowCount();
                    if (next >= rowcount)
                        next = (rowcount - 1) - ((rowcount - 1) % uiData.getRows());
                    setFirst(uiData, next);
                }
                else if (FACET_FAST_REWIND.equals(facet))
                {
                    int fastStep = getFastStep();
                    if (fastStep <= 0)
                        fastStep = 1;
                    int previous = uiData.getFirst() - uiData.getRows() * fastStep;
                    if (previous < 0)
                        previous = 0;
                    setFirst(uiData, previous);
                }
                else if (FACET_LAST.equals(facet))
                {
                    int rowcount = uiData.getRowCount();
                    int rows = uiData.getRows();
                    int delta = rowcount % rows;
                    int first = delta > 0 && delta < rows ? rowcount - delta : rowcount - rows;
                    if (first >= 0)
                    {
                        setFirst(uiData, first);
                    }
                    else
                    {
                        setFirst(uiData, 0);
                    }
                }
            }
            else
            {
                int pageCount = getPageCount();
                if (pageindex > pageCount)
                {
                    pageindex = pageCount;
                }
                if (pageindex <= 0)
                {
                    pageindex = 1;
                }
                setFirst(uiData, uiData.getRows() * (pageindex - 1));
            }
        }
    }

    protected void setFirst(UIData uiData, int value) {
        //there might be special cases where the first-property of the data-table
        //is bound to a backing bean. If this happens, the user probably wants
        //the data-scroller to update this backing-bean value - if not, you can always
        //override this method in a subclass.
        if(uiData.getValueBinding("first")!=null)
        {
            ValueBinding vb = uiData.getValueBinding("first");
            vb.setValue(getFacesContext(),new Integer(value));
        }
        else
        {
            uiData.setFirst(value);
        }
    }

    /**
     * @param event
     */
    protected void broadcastToActionListener(ScrollerActionEvent event)
    {
        FacesContext context = getFacesContext();

        MethodBinding actionListenerBinding = getActionListener();
        if (actionListenerBinding != null)
        {
            try
            {
                actionListenerBinding.invoke(context, new Object[] {event});
            }
            catch (EvaluationException e)
            {
                Throwable cause = e.getCause();
                if (cause != null && cause instanceof AbortProcessingException)
                {
                    throw (AbortProcessingException)cause;
                }
                throw e;
            }
        }
        
        ActionListener defaultActionListener
                = context.getApplication().getActionListener();
        if (defaultActionListener != null)
        {
            defaultActionListener.processAction((ActionEvent)event);
        }
    }

    /**
     * @return int
     */
    public UIData getUIData()
    {
        if (_UIData == null)
        {
            _UIData = findUIData();
        }
        return _UIData;
    }

    /**
     * @return the page index of the uidata
     */
    public int getPageIndex()
    {
        UIData uiData = getUIData();
        int rows = uiData.getRows();
        if (0 == rows)
        {
            throw new FacesException("You need to set a value to the 'rows' attribute of component '" + uiData.getClientId(getFacesContext()) + "'" );
        }

        int pageIndex;
        if (rows > 0)
        {
            pageIndex = uiData.getFirst() / rows + 1;
        }
        else
        {
            log.warn("DataTable " + uiData.getClientId(FacesContext.getCurrentInstance())
                            + " has invalid rows attribute.");
            pageIndex = 0;
        }
        if (uiData.getFirst() % rows > 0)
        {
            pageIndex++;
        }
        return pageIndex;
    }

    /**
     * @return the page count of the uidata
     */
    public int getPageCount()
    {
        UIData uiData = getUIData();
        int rows = uiData.getRows();
        int pageCount;
        if (rows > 0)
        {
            pageCount = rows <= 0 ? 1 : uiData.getRowCount() / rows;
            if (uiData.getRowCount() % rows > 0)
            {
                pageCount++;
            }
        }
        else
        {
            rows = 1;
            pageCount = 1;
        }
        return pageCount;
    }

    /**
     * @return int
     */
    public int getRowCount()
    {
        return getUIData().getRowCount();
    }

    /**
     * @return int
     */
    public int getRows()
    {
        return getUIData().getRows();
    }

    /**
     * @return int
     */
    public int getFirstRow()
    {
        return getUIData().getFirst();
    }

    /**
     * Find the UIData component associated with this scroller.
     * <p>
     * If the "for" attribute is not null then that value is used to find the
     * specified component by id. Both "relative" and "absolute" ids are allowed;
     * see method UIComponent.findComponent for details.
     * <p>
     * If the "for" attribute is not defined, then this component is expected to
     * be a child of a UIData component.
     * 
     * @throws IllegalArgumentException if an associated UIData component
     * cannot be found.
     */
    protected UIData findUIData()
    {
        String forStr = getFor();
        UIComponent forComp;
        if (forStr == null)
        {
            // DataScroller may be a child of uiData
            forComp = getParent();
        }
        else
        {
            forComp = findComponent(forStr);
        }
        if (forComp == null)
        {
            throw new IllegalArgumentException(
                    "could not find UIData referenced by attribute dataScroller@for = '"
                    + forStr + "'");
        }
        else if (!(forComp instanceof UIData))
        {
            throw new IllegalArgumentException(
                "uiComponent referenced by attribute dataScroller@for = '"
                + forStr + "' must be of type " + UIData.class.getName()
                + ", not type " + forComp.getClass().getName());
        }
        return (UIData) forComp;
    }

    public void setFirst(UIComponent first)
    {
        getFacets().put(FIRST_FACET_NAME, first);
    }

    /**
     * @JSFFacet
     */
    public UIComponent getFirst()
    {
        return (UIComponent) getFacets().get(FIRST_FACET_NAME);
    }

    public void setLast(UIComponent last)
    {
        getFacets().put(LAST_FACET_NAME, last);
    }

    /**
     * @JSFFacet
     */
    public UIComponent getLast()
    {
        return (UIComponent) getFacets().get(LAST_FACET_NAME);
    }

    public void setNext(UIComponent next)
    {
        getFacets().put(NEXT_FACET_NAME, next);
    }

    /**
     * @JSFFacet
     */
    public UIComponent getNext()
    {
        return (UIComponent) getFacets().get(NEXT_FACET_NAME);
    }

    public void setFastForward(UIComponent previous)
    {
        getFacets().put(FAST_FORWARD_FACET_NAME, previous);
    }

    /**
     * @JSFFacet
     */
    public UIComponent getFastForward()
    {
        return (UIComponent) getFacets().get(FAST_FORWARD_FACET_NAME);
    }

    public void setFastRewind(UIComponent previous)
    {
        getFacets().put(FAST_REWIND_FACET_NAME, previous);
    }

    /**
     * @JSFFacet
     */
    public UIComponent getFastRewind()
    {
        return (UIComponent) getFacets().get(FAST_REWIND_FACET_NAME);
    }

    public void setPrevious(UIComponent previous)
    {
        getFacets().put(PREVIOUS_FACET_NAME, previous);
    }

    /**
     * @JSFFacet
     */
    public UIComponent getPrevious()
    {
        return (UIComponent) getFacets().get(PREVIOUS_FACET_NAME);
    }

    public boolean getRendersChildren()
    {
        return true;
    }

    /**
     * @see javax.faces.component.ActionSource#getAction()
     */
    public MethodBinding getAction()
    {
        // not used
        return null;
    }

    /**
     * @see javax.faces.component.ActionSource#setAction(javax.faces.el.MethodBinding)
     */
    public void setAction(MethodBinding action)
    {
        throw new UnsupportedOperationException(
                        "defining an action is not supported. use an actionlistener");
    }

    /**
     * @see javax.faces.component.ActionSource#setActionListener(javax.faces.el.MethodBinding)
     */
    public void setActionListener(MethodBinding actionListener)
    {
        _actionListener = actionListener;
    }

    /**
     * MethodBinding pointing at method acception an ActionEvent with return type void.
     * 
     * @JSFProperty
     *   returnSignature="void"
     *   methodSignature="javax.faces.event.ActionEvent"   
     * @see javax.faces.component.ActionSource#getActionListener()
     */
    public MethodBinding getActionListener()
    {
        return _actionListener;
    }

    /**
     * @see javax.faces.component.ActionSource#addActionListener(javax.faces.event.ActionListener)
     */
    public void addActionListener(ActionListener listener)
    {
        addFacesListener(listener);
    }

    /**
     * @see javax.faces.component.ActionSource#getActionListeners()
     */
    public ActionListener[] getActionListeners()
    {
        return (ActionListener[]) getFacesListeners(ActionListener.class);
    }

    /**
     * @see javax.faces.component.ActionSource#removeActionListener(javax.faces.event.ActionListener)
     */
    public void removeActionListener(ActionListener listener)
    {
        removeFacesListener(listener);
    }

    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = saveAttachedState(context, _actionListener);
        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        _actionListener = (MethodBinding)restoreAttachedState(context, values[1]);
    }
    
    /**
     * The JSF id of a UIData component that this scroller will affect.
     *  
     * If this attribute is not present then the datascroller must be 
     * a child of a UIData component.
     * 
     * @JSFProperty
     */
    public abstract String getFor();

    /**
     * step (pages) used for fastforward and fastrewind
     * 
     * @JSFProperty
     *   defaultValue="Integer.MIN_VALUE"
     */
    public abstract int getFastStep();

    /**
     * A parameter name, under which the actual page index is set 
     * in request scope similar to the var parameter.
     * 
     * @JSFProperty
     */
    public abstract String getPageIndexVar();

    /**
     * A parameter name, under which the actual page count is set 
     * in request scope similar to the var parameter.
     * 
     * @JSFProperty
     */
    public abstract String getPageCountVar();

    /**
     * A parameter name, under which the actual rows count is set 
     * in request scope similar to the var parameter.
     * 
     * @JSFProperty
     */
    public abstract String getRowsCountVar();

    /**
     * A parameter name, under which the actual displayed rows count 
     * is set in request scope similar to the var parameter.
     * 
     * @JSFProperty
     */
    public abstract String getDisplayedRowsCountVar();

    /**
     * A parameter name, under which the actual first displayed row 
     * index is set in request scope similar to the var parameter.
     * 
     * @JSFProperty
     */
    public abstract String getFirstRowIndexVar();

    /**
     * A parameter name, under which the actual last displayed row 
     * index is set in request scope similar to the var parameter.
     * 
     * @JSFProperty
     */
    public abstract String getLastRowIndexVar();

    /**
     * If set true, then the paginator gets rendered
     * 
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract boolean isPaginator();

    /**
     * The maximum amount of pages to be displayed in the paginator.
     * 
     * @JSFProperty
     *   defaultValue = "Integer.MIN_VALUE"
     */
    public abstract int getPaginatorMaxPages();

    /**
     * styleclass for pagingator
     * 
     * @JSFProperty
     */
    public abstract String getPaginatorTableClass();

    /**
     * style for pagingator
     * 
     * @JSFProperty
     */
    public abstract String getPaginatorTableStyle();

    /**
     * styleClass for paginator's column
     * 
     * @JSFProperty
     */
    public abstract String getPaginatorColumnClass();

    /**
     * style for paginator's column
     * 
     * @JSFProperty
     */
    public abstract String getPaginatorColumnStyle();

    /**
     * styleClass for paginator's column with pageIndex = currentPageIndex
     * 
     * @JSFProperty
     */
    public abstract String getPaginatorActiveColumnClass();

    /**
     * 'true' - render a link for the paginator's column with 
     * pageIndex = currentPageIndex. Default-value is 'true'.
     * 
     * @JSFProperty
     *   defaultValue = "true"
     */
    public abstract boolean isPaginatorRenderLinkForActive();

    /**
     * style-class for data-scroller first-element
     * 
     * @JSFProperty
     */
    public abstract String getFirstStyleClass();

    /**
     * style-class for data-scroller last-element
     * 
     * @JSFProperty
     */
    public abstract String getLastStyleClass();

    /**
     * style-class for data-scroller previous-element
     * 
     * @JSFProperty
     */
    public abstract String getPreviousStyleClass();

    /**
     * style-class for dataScroller next-element
     * 
     * @JSFProperty
     */
    public abstract String getNextStyleClass();

    /**
     * style-class for data-scroller fast-forward-element
     * 
     * @JSFProperty
     */
    public abstract String getFastfStyleClass();

    /**
     * style-class for data-scroller fast-rewind-element
     * 
     * @JSFProperty
     */
    public abstract String getFastrStyleClass();

    /**
     * style for paginator's column with pageIndex = currentPageIndex
     * 
     * @JSFProperty
     */
    public abstract String getPaginatorActiveColumnStyle();

    /**
     * If set to false, the facets aren't renderd if all the 
     * lines are contained on a single page. Default is true.
     * 
     * @JSFProperty
     *   defaultValue="true"
     */
    public abstract boolean isRenderFacetsIfSinglePage();

    /**
     * True means that the default ActionListener should be 
     * executed immediately (i.e. during Apply Request 
     * Values phase of the request processing lifecycle), 
     * rather than waiting until the Invoke Application phase.
     * 
     * @JSFProperty
     *   defaultValue="false"
     */    
    public abstract boolean isImmediate();
    
}
