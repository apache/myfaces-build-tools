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
package org.apache.myfaces.custom.navmenu.htmlnavmenu;

import org.apache.myfaces.taglib.html.ext.HtmlCommandLinkTag;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler
 * @author Thomas Spiegl
 */
public class HtmlCommandNavigationItemTag extends HtmlCommandLinkTag {
    private static final String OPEN_ATTR = "open".intern();
    private static final String ACTIVE_ATTR = "active".intern();
    private static final String ACTIVE_ON_VIEW_IDS_ATTR = "activeOnViewIds".intern();
    private static final String EXTERNAL_LINK = "externalLink".intern();

    private String _open;
    private String _active;
    private String _activeOnViewIds;
    private String _externalLink;

    public String getComponentType() {
        return HtmlCommandNavigationItem.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return HtmlNavigationMenuRenderer.RENDERER_TYPE;
    }

    
	public void release() {
		super.release();
		_open = null;
		_active = null;
		_activeOnViewIds = null;
		_externalLink = null;
	}

	protected void setProperties(UIComponent component) {
        super.setProperties(component);

        setBooleanProperty(component, OPEN_ATTR, _open);
        setBooleanProperty(component, ACTIVE_ATTR, _active);
        setStringProperty(component, ACTIVE_ON_VIEW_IDS_ATTR, _activeOnViewIds);
        setStringProperty(component, EXTERNAL_LINK, _externalLink);
    }

    public void setOpen(String open) {
        _open = open;
    }

    public void setActive(String active) {
        _active = active;
    }

    public void setActiveOnViewIds(String activeOnViewIds) {
        _activeOnViewIds = activeOnViewIds;
    }

    public void setExternalLink(String externalLink) {
        _externalLink = externalLink;
    }
}
