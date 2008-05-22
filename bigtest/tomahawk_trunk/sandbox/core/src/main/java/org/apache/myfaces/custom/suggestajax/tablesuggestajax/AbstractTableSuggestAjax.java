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
 * Provides an input textbox with "suggest" functionality, using an ajax request 
 * to the server. The popUp contains a table where each column value can be set 
 * to a specific dom node (through dom node id).
 * 
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
     * If the time between two keyup events is lower than this given value, 
     * the ajax request will not be fired. In milliseconds. Prevents stressing 
     * the server with too much user inputs. In driven tests the component 
     * seems to be more stable if this value is set. A recommended value 
     * in which case the component works very well is about 300ms.
     * 
     * @JSFProperty
     */
    public abstract Integer getBetweenKeyUp();

    /**
     * The AJAX Request is only triggered if the number of chars typed in is 
     * equal or greater than this given value.
     * 
     * @JSFProperty
     */
    public abstract Integer getStartRequest();

    /**
     * Same principle as it can be found in dataTable. SuggestedItemsMethod returns 
     * a list of objects, where the class variables can be accessed with the 
     * alias after the dot of the var.
     * 
     * @JSFProperty
     */
    public abstract String getVar();

    /**
     * StyleClass for the suggested table.
     * 
     * @JSFProperty
     */
    public abstract String getTableStyleClass();

    /**
     * Id for the pop up window
     * 
     * @JSFProperty
     */
    public abstract String getPopupId();

    /**
     * StyleClass for the window with a suggested list of items;
     * pop up for each incoming Ajax response
     * 
     * @JSFProperty
     */
    public abstract String getPopupStyleClass();

    /**
     * StyleClass for dropdown box and arrow.
     * 
     * @JSFProperty
     */
    public abstract String getComboBoxStyleClass();

    /**
     * StyleClass which applies to every row in the suggested table.
     * 
     * @JSFProperty
     */
    public abstract String getRowStyleClass();

    /**
     * StyleClass which only applies to even rows in the suggested table.
     * 
     * @JSFProperty
     */
    public abstract String getEvenRowStyleClass();

    /**
     * StyleClass which only applies to odd rows in the suggested table.
     * 
     * @JSFProperty
     */
    public abstract String getOddRowStyleClass();

    /**
     * StyleClass for the rows for onmouseover events.
     * 
     * @JSFProperty
     */
    public abstract String getHoverRowStyleClass();
    
}
