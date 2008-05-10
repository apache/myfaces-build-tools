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
package org.apache.myfaces.custom.tree2;

import javax.faces.component.UICommand;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.component.LocationAware;

import java.util.Map;

/**
 * Represents "tree data" in an HTML format.  Also provides a mechanism for maintaining expand/collapse
 * state of the nodes in the tree.
 *
 * @JSFComponent
 *   name = "t:tree2"
 *   class = "org.apache.myfaces.custom.tree2.HtmlTree"
 *   superClass = "org.apache.myfaces.custom.tree2.AbstractHtmlTree"
 *   tagClass = "org.apache.myfaces.custom.tree2.TreeTag"
 *
 * @author Sean Schofield
 */
public abstract class AbstractHtmlTree extends UITreeData
    implements LocationAware
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlTree2";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.HtmlTree2";

    private UICommand _expandControl = null;
    
    //public abstract boolean isClientSideToggle();
    
    // Property: clientSideToggle
    private boolean _clientSideToggle = true;
    private boolean _clientSideToggleSet;

    /**
     * Gets 
     * @JSFProperty
     *   defaultValue = "true"
     * @return  the new clientSideToggle value
     */
    public boolean isClientSideToggle()
    {
      if (_clientSideToggleSet)
      {
        return _clientSideToggle;
      }
      ValueBinding expression = getValueBinding("clientSideToggle");
      if (expression != null)
      {
        return ((Boolean)expression.getValue(getFacesContext())).booleanValue();
      }
      return true;
    }

    /**
     * Sets 
     * 
     * @param clientSideToggle  the new clientSideToggle value
     */
    public void setClientSideToggle(boolean clientSideToggle)
    {
      this._clientSideToggle = clientSideToggle;
      this._clientSideToggleSet = true;
    }
    
    
    /**
     * @see org.apache.myfaces.custom.tree2.UITreeData#processNodes(javax.faces.context.FacesContext, int, org.apache.myfaces.custom.tree2.TreeWalker)
     */
    protected void processNodes(FacesContext context, int processAction, TreeWalker walker)
    {
        if (isClientSideToggle()) {
            walker.setCheckState(false);
        }
        super.processNodes(context, processAction, walker);
    }
    
    //public abstract String getLocalVarNodeToggler();

    // see superclass for documentation
    public void setNodeId(String nodeId)
    {
        super.setNodeId(nodeId);

        if (_varNodeToggler != null)
        {
            Map requestMap = getFacesContext().getExternalContext().getRequestMap();
            requestMap.put(_varNodeToggler, this);
        }
    }

    /**
     * Gets the expand/collapse control that can be used to handle expand/collapse nodes.  This is only used in server-side
     * mode.  It allows the nagivation controls (if any) to be clickable as well as any commandLinks the user has set up in
     * their JSP.
     * 
     * @return UICommand
     */
    public UICommand getExpandControl()
    {
        if (_expandControl == null){
            _expandControl = new HtmlCommandLink();
            _expandControl.setParent(this);
        }
        return _expandControl;
    }
    
    // Property: varNodeToggler
    private String _varNodeToggler;

    /**
     * Gets 
     *
     * @JSFProperty
     * @return  the new varNodeToggler value
     */
    public String getVarNodeToggler()
    {
      return _varNodeToggler;
    }
    
    /**///setVarNodeToggler
    public void setVarNodeToggler(String varNodeToggler)
    {
        _varNodeToggler = varNodeToggler;

        // create a method binding for the expand control
        String bindingString = "#{" + varNodeToggler + ".toggleExpanded}";
        MethodBinding actionBinding = FacesContext.getCurrentInstance().getApplication().createMethodBinding(bindingString, null);
        getExpandControl().setAction(actionBinding);
    }
    
    public Object saveState(FacesContext facesContext)
    {
      Object[] values = new Object[4];
      values[0] = super.saveState(facesContext);
      values[1] = _varNodeToggler;
      values[2] = Boolean.valueOf(_clientSideToggle);
      values[3] = Boolean.valueOf(_clientSideToggleSet);

      return values;
    }

    public void restoreState(FacesContext facesContext, Object state)
    {
      Object[] values = (Object[])state;
      super.restoreState(facesContext,values[0]);
      _varNodeToggler = (String)values[1];
      _clientSideToggle = ((Boolean)values[2]).booleanValue();
      _clientSideToggleSet = ((Boolean)values[3]).booleanValue();
      
    }
        
    /**
     * @JSFProperty
     *   defaultValue = "true"
     */
    public abstract boolean isShowNav();

    /**
     * @JSFProperty
     *   defaultValue = "true"
     */
    public abstract boolean isShowLines();

    /**
     * @JSFProperty
     *   defaultValue = "true"
     */
    public abstract boolean isShowRootNode();

    /**
     * @JSFProperty
     *   defaultValue = "true"
     */
    public abstract boolean isPreserveToggle();

}
