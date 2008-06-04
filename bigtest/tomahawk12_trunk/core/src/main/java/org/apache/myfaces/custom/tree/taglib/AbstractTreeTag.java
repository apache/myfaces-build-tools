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
package org.apache.myfaces.custom.tree.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.JspException;

import org.apache.myfaces.custom.tree.HtmlTree;
import org.apache.myfaces.custom.tree.model.DefaultTreeModel;
import org.apache.myfaces.custom.tree.model.TreeModel;
import org.apache.myfaces.custom.tree.model.TreePath;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlPanelGroupTag;

/**
 * <p>
 * HtmlTree tag.
 * </p>
 *
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller </a>
 * @version $Revision$ $Date: 2004/10/13 11:50:58
 */
public class AbstractTreeTag extends HtmlPanelGroupTag {

    private String value;

    private boolean expandRoot;

    public String getComponentType() {
        return "org.apache.myfaces.HtmlTree";
    }

    public String getRendererType() {
        return "org.apache.myfaces.HtmlTree";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String newValue) {
        value = newValue;
    }

    public boolean isExpandRoot() {
        return expandRoot;
    }

    public void setExpandRoot(boolean expandRoot) {
        this.expandRoot = expandRoot;
    }

    /**
     * Obtain tree model or create a default model.
     */
    public int doStartTag() throws JspException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (value != null) {
            ValueBinding valueBinding = context.getApplication()
                    .createValueBinding(value);
            TreeModel treeModel = (TreeModel) (valueBinding.getValue(context));

            if (treeModel == null) {
                // create default model
                treeModel = new DefaultTreeModel();
                valueBinding.setValue(context, treeModel);
            }
        }
        int answer = super.doStartTag();
        HtmlTree tree = (HtmlTree) getComponentInstance();

        if (getCreated() && expandRoot) {
            // component was created, so expand the root node
            TreeModel model = tree.getModel(context);

            if (model != null) {
                tree.expandPath(new TreePath(new Object[] { model.getRoot() }),
                        context);
            }
        }

        tree.addToModelListeners();
        return answer;
    }

    public void release() {
        super.release();
        value = null;
        expandRoot = false;
    }

    /**
     * Applies attributes to the tree component
     */
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();

        if (value != null) {
            if (isValueReference(value)) {
                ValueBinding binding = context.getApplication()
                        .createValueBinding(value);
                component.setValueBinding("model", binding);
            }
        } else {
            ValueBinding binding = component.getValueBinding("model");
            if (binding == null) {
                binding = context.getApplication().createValueBinding(
                        "#{sessionScope.tree}");
            }
            component.setValueBinding("model", binding);
        }
    }
}
