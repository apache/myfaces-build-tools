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
package org.apache.myfaces.custom.suggestajax.tablesuggestajax;

import java.io.IOException;

import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.suggestajax.SuggestAjax;

/**
 * @JSFComponent
 *   name = "s:tableSuggestAjax"
 *   class = "org.apache.myfaces.custom.suggestajax.tablesuggestajax.TableSuggestAjax"
 *   superClass = "org.apache.myfaces.custom.suggestajax.tablesuggestajax.AbstractTableSuggestAjax"
 *   tagClass = "org.apache.myfaces.custom.suggestajax.tablesuggestajax.TableSuggestAjaxTag"
 *   
 * @author Gerald Muellan
 *         Date: 25.03.2006
 *         Time: 17:04:58
 */
public abstract class AbstractTableSuggestAjax extends SuggestAjax {

    public static final String COMPONENT_TYPE = "org.apache.myfaces.TableSuggestAjax";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.TableSuggestAjax";

    public AbstractTableSuggestAjax() {
        super();

        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public boolean getRendersChildren() {
        if (getVar() != null)
        {
            return true;
        }
        else
        {
            return super.getRendersChildren();
        }
    }

    public void encodeChildren(FacesContext context) throws IOException {
        super.encodeChildren(context);
    }

    /**
     * @JSFProperty
     */
    public abstract Integer getBetweenKeyUp();

    /**
     * @JSFProperty
     */
    public abstract Integer getStartRequest();

    /**
     * @JSFProperty
     */
    public abstract String getVar();

    /**
     * @JSFProperty
     */
    public abstract String getTableStyleClass();

    /**
     * @JSFProperty
     */
    public abstract String getPopupId();

    /**
     * @JSFProperty
     */
    public abstract String getPopupStyleClass();

    /**
     * @JSFProperty
     */
    public abstract String getComboBoxStyleClass();

    /**
     * @JSFProperty
     */
    public abstract String getRowStyleClass();

    /**
     * @JSFProperty
     */
    public abstract String getEvenRowStyleClass();

    /**
     * @JSFProperty
     */
    public abstract String getOddRowStyleClass();

    /**
     * @JSFProperty
     */
    public abstract String getHoverRowStyleClass();
    
}
