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
package org.apache.myfaces.custom.loadbundle;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A load-bundle alternative which allows to use load-bundle
 * even on AJAX-enabled pages.
 * <p/>
 * A tag that allows to load bundles not only on rendering, but whenever the
 * page author needs it. By default, it loads it on every lifecycle phase
 * except restore-state and save-state.
 *
 * The core-load-bundle only loads its message-bundle
 * on rendering - this load-bundle does it on every life-cycle,
 * and optionally whenever the method loadBundle is called.

 * @author Martin Marinschek
 */
public abstract class AbstractLoadBundleTag extends UIComponentTag {
    
    private Log log = LogFactory.getLog(AbstractLoadBundleTag.class);

    public int doStartTag() throws JspException
    {
        int retVal= super.doStartTag();

        UIComponent comp = getComponentInstance();

        if(comp instanceof LoadBundle)
        {
            ((LoadBundle) comp).loadBundle();
        }
        else
        {
            log.warn("associated component is no loadbundle.");
        }

        return retVal;
    }
}
