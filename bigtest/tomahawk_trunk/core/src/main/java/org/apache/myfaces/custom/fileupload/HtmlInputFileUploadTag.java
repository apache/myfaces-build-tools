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
package org.apache.myfaces.custom.fileupload;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlInputTagBase;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlInputFileUploadTag
        extends HtmlInputTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlInputFileUploadTag.class);

    public String getComponentType()
    {
        return HtmlInputFileUpload.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlInputFileUpload.DEFAULT_RENDERER_TYPE;
    }

    // UIComponent attributes --> already implemented in UIComponentTagBase

    // user role attributes --> already implemented in UIComponentTagBase

    // HTML universal attributes --> already implemented in HtmlComponentTagBase

    // HTML event handler attributes --> already implemented in HtmlComponentTagBase

    // HTML input attributes
    private String _accesskey;
    private String _align;
    private String _alt; //FIXME: HTML 4.0 transitional attribute and not in strict... what to do?
    private String _datafld;
    private String _datasrc;
    private String _dataformatas;
    private String _disabled;
    private String _maxlength;
    private String _onblur;
    private String _onchange;
    private String _onfocus;
    private String _onselect;
    private String _size;
    private String _tabindex;

    // UIOutput attributes
    // value and converterId --> already implemented in UIComponentTagBase

    // UIInput attributes
    // --> already implemented in HtmlInputTagBase

    // HtmlInputFileUpload attributes
    private String _accept;
    private String _storage;

    // User Role support
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;

    
    public void release() {
        super.release();
        _accesskey=null;
        _align=null;
        _alt=null; 
        _datafld=null;
        _datasrc=null;
        _dataformatas=null;
        _disabled=null;
        _maxlength=null;
        _onblur=null;
        _onchange=null;
        _onfocus=null;
        _onselect=null;
        _size=null;
        _tabindex=null;
        _accept=null;
        _storage=null;
        _enabledOnUserRole=null;
        _visibleOnUserRole=null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        
        setStringProperty(component, HTML.ACCESSKEY_ATTR, _accesskey);
        setStringProperty(component, HTML.ALIGN_ATTR, _align);
        setStringProperty(component, HTML.ALT_ATTR, _alt);
        setStringProperty(component, HTML.DATAFLD_ATTR, _datafld);
        setStringProperty(component, HTML.DATASRC_ATTR, _datasrc);
        setStringProperty(component, HTML.DATAFORMATAS_ATTR, _dataformatas);
        setBooleanProperty(component, HTML.DISABLED_ATTR, _disabled);
        setIntegerProperty(component, HTML.MAXLENGTH_ATTR, _maxlength);
        setStringProperty(component, HTML.ONBLUR_ATTR, _onblur);
        setStringProperty(component, HTML.ONCHANGE_ATTR, _onchange);
        setStringProperty(component, HTML.ONFOCUS_ATTR, _onfocus);
        setStringProperty(component, HTML.ONSELECT_ATTR, _onselect);
        setIntegerProperty(component, HTML.SIZE_ATTR, _size);
        setStringProperty(component, HTML.TABINDEX_ATTR, _tabindex);

        setStringProperty(component, HTML.ACCEPT_ATTR, _accept);
        setStringProperty(component, "storage", _storage);

        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
    }
    
    public void setStorage(String storage)
    {
        _storage = storage;
    }

    public void setAccesskey(String accesskey)
    {
        _accesskey = accesskey;
    }

    public void setAlign(String align)
    {
        _align = align;
    }

    public void setAlt(String alt)
    {
        _alt = alt;
    }

    public void setDatafld(String datafld)
    {
        _datafld = datafld;
    }

    public void setDatasrc(String datasrc)
    {
        _datasrc = datasrc;
    }

    public void setDataformatas(String dataformatas)
    {
        _dataformatas = dataformatas;
    }

    public void setDisabled(String disabled)
    {
        _disabled = disabled;
    }

    public void setMaxlength(String maxlength)
    {
        _maxlength = maxlength;
    }

    public void setOnblur(String onblur)
    {
        _onblur = onblur;
    }

    public void setOnchange(String onchange)
    {
        _onchange = onchange;
    }

    public void setOnfocus(String onfocus)
    {
        _onfocus = onfocus;
    }

    public void setOnselect(String onselect)
    {
        _onselect = onselect;
    }

    public void setSize(String size)
    {
        _size = size;
    }

    public void setTabindex(String tabindex)
    {
        _tabindex = tabindex;
    }

    public void setAccept(String accept)
    {
        _accept = accept;
    }

    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }
}
