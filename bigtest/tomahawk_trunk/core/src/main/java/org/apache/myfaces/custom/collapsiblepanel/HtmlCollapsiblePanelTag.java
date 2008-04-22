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
package org.apache.myfaces.custom.collapsiblepanel;

import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlInputTagBase;
import org.apache.myfaces.component.UserRoleAware;

import javax.faces.component.UIComponent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Kalle Korhonen (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlCollapsiblePanelTag
        extends HtmlInputTagBase implements BodyTag
{
    //private static final Log log = LogFactory.getLog(HtmlCollapsiblePanelTag.class);

    public String getComponentType()
    {
        return HtmlCollapsiblePanel.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return "org.apache.myfaces.CollapsiblePanel";
    }

    private String _title;
    private String _var;
    private String _titleVar;
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;

    public void release() {
        super.release();
        _title=null;
        _var=null;
        _titleVar=null;
        _enabledOnUserRole=null;
        _visibleOnUserRole=null;
        bodyContent = null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, "title", _title);
        setStringProperty(component,"var",_var);
        setStringProperty(component,"titleVar",_titleVar);
        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);

    }


    public void setTitle(String title)
    {
        _title = title;
    }

    public void setVar(String var)
    {
        _var = var;
    }

    public void setTitleVar(String titleVar)
    {
        _titleVar = titleVar;
    }

    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }


    // API field
    protected BodyContent bodyContent;


    public int doAfterBody()
            throws JspException
    {
        return getDoAfterBodyValue();
    }

    public void doInitBody()
            throws JspException
    {
    }

    public BodyContent getBodyContent()
    {
        return bodyContent;
    }

    public void setBodyContent(BodyContent bodyContent)
    {
        this.bodyContent = bodyContent;
    }

    public JspWriter getPreviousOut()
    {
        return bodyContent.getEnclosingWriter();
    }

    protected int getDoStartValue()
            throws JspException
    {
        return BodyTag.EVAL_BODY_BUFFERED;
    }

    protected int getDoAfterBodyValue()
            throws JspException
    {
        return BodyTag.SKIP_BODY;
    }

}
