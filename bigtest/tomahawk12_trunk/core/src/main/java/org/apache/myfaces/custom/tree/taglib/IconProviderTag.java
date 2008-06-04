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

import org.apache.myfaces.shared_tomahawk.util.ClassUtils;
import org.apache.myfaces.custom.tree.HtmlTree;
import org.apache.myfaces.custom.tree.IconProvider;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * @JSFJspTag
 *   name="t:iconProvider"
 *   bodyContent="empty"
 *   
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision$ $Date$
 */
public class IconProviderTag
    extends TagSupport
{
    private static final long serialVersionUID = -8851450834386187922L;
    private String type = null;


    public IconProviderTag()
    {
    }

    /**
     * @JSFJspAttribute
     *   required = "true"
     */
    public void setType(String type)
    {
        this.type = type;
    }


    public int doStartTag() throws JspException
    {
        if (type == null)
        {
            throw new JspException("type attribute not set");
        }

        //Find parent UIComponentTag
        UIComponentTag componentTag = UIComponentTag.getParentUIComponentTag(pageContext);
        if (componentTag == null)
        {
            throw new JspException("IconProviderTag has no UIComponentTag ancestor");
        }

        UIComponent component = componentTag.getComponentInstance();
        if (component instanceof HtmlTree)
        {
            String className;
            if (UIComponentTag.isValueReference(type))
            {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ValueBinding vb = facesContext.getApplication().createValueBinding(type);
                className = (String) vb.getValue(facesContext);
            } else
            {
                className = type;
            }
            IconProvider provider = (IconProvider) ClassUtils.newInstance(className);
            ((HtmlTree) component).setIconProvider(provider);
        } else
        {
            throw new JspException("Component " + component.getId() + " is no HtmlTree");
        }

        return Tag.SKIP_BODY;
    }
}
