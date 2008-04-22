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

import org.apache.myfaces.custom.suggestajax.SuggestAjax;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import java.io.IOException;

/**
 * @author Gerald Muellan
 *         Date: 25.03.2006
 *         Time: 17:04:58
 */
public class TableSuggestAjax extends SuggestAjax {
    public static final String COMPONENT_TYPE = "org.apache.myfaces.TableSuggestAjax";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.TableSuggestAjax";

    private String _popupId;
    private Integer _betweenKeyUp;
    private Integer _startRequest;

    private String _popupStyleClass;
    private String _tableStyleClass;
    private String _comboBoxStyleClass;
    private String _rowStyleClass;
    private String _evenRowStyleClass;
    private String _oddRowStyleClass;
    private String _hoverRowStyleClass;

    private String _var;


    public TableSuggestAjax() {
        super();

        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[13];
        values[0] = super.saveState(context);
        values[1] = _var;
        values[2] = _betweenKeyUp;
        values[3] = _startRequest;
        values[4] = _tableStyleClass;
        values[5] = _popupId;
        values[6] = _popupStyleClass;
        values[7] = _comboBoxStyleClass;
        values[8] = _rowStyleClass;
        values[9] = _evenRowStyleClass;
        values[10] = _oddRowStyleClass;
        values[11] = _hoverRowStyleClass;

        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _var = (String) values[1];
        _betweenKeyUp = (Integer) values[2];
        _startRequest = (Integer) values[3];
        _tableStyleClass = (String) values[4];
        _popupId = (String) values[5];
        _popupStyleClass = (String) values[6];
        _comboBoxStyleClass = (String) values[7];
        _rowStyleClass = (String) values[8];
        _evenRowStyleClass = (String) values[9];
        _oddRowStyleClass = (String) values[10];
        _hoverRowStyleClass = (String) values[11];
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

    public Integer getBetweenKeyUp() {
        if (_betweenKeyUp != null) {
            return _betweenKeyUp;
        }
        ValueBinding vb = getValueBinding("delay");
        return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
    }

    public void setBetweenKeyUp(Integer betweenKeyUp) {
        _betweenKeyUp = betweenKeyUp;
    }

    public Integer getStartRequest() {
        if (_startRequest != null) {
            return _startRequest;
        }
        ValueBinding vb = getValueBinding("startRequest");
        return vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
    }

    public void setStartRequest(Integer startRequest) {
        _startRequest = startRequest;
    }

    public void setVar(String var) {
        _var = var;
    }

    public String getVar() {
        if (_var != null) {
            return _var;
        }
        ValueBinding vb = getValueBinding("var");
        return vb != null ? vb.getValue(getFacesContext()).toString() : null;
    }

    public String getTableStyleClass() {
        if (_tableStyleClass != null) {
            return _tableStyleClass;
        }
        ValueBinding vb = getValueBinding("tableStyleClass");
        return vb != null ? vb.getValue(getFacesContext()).toString() : null;
    }

    public void setTableStyleClass(String tableStyleClass) {
        _tableStyleClass = tableStyleClass;
    }

    public String getPopupId()
    {
        if (_popupId != null) {
            return _popupId;
        }
        ValueBinding vb = getValueBinding("popupId");
        return vb != null ? vb.getValue(getFacesContext()).toString() : null;
    }

    public void setPopupId(String popupId) {
        _popupId = popupId;
    }

    public String getPopupStyleClass() {
        if (_popupStyleClass != null)
            return _popupStyleClass;
        ValueBinding vb = getValueBinding("popupStyleClass");
        return vb != null ? vb.getValue(getFacesContext()).toString() : null;
    }

    public void setPopupStyleClass(String popupStyleClass) {
        _popupStyleClass = popupStyleClass;
    }

    public String getComboBoxStyleClass() {
        if (_comboBoxStyleClass != null) {
            return _comboBoxStyleClass;
        }
        ValueBinding vb = getValueBinding("comboBoxStyleClass");
        return vb != null ? vb.getValue(getFacesContext()).toString() : null;
    }

    public void setComboBoxStyleClass(String comboBoxStyleClass) {
        _comboBoxStyleClass = comboBoxStyleClass;
    }

    public String getRowStyleClass() {
        if (_rowStyleClass != null) {
            return _rowStyleClass;
        }
        ValueBinding vb = getValueBinding("rowStyleClass");
        return vb != null ? vb.getValue(getFacesContext()).toString() : null;
    }

    public void setRowStyleClass(String rowStyleClass) {
        _rowStyleClass = rowStyleClass;
    }

    public String getEvenRowStyleClass() {
        if (_evenRowStyleClass != null) {
            return _evenRowStyleClass;
        }
        ValueBinding vb = getValueBinding("evenRowStyleClass");
        return vb != null ? vb.getValue(getFacesContext()).toString() : null;
    }

    public void setEvenRowStyleClass(String evenRowStyleClass) {
        _evenRowStyleClass = evenRowStyleClass;
    }

    public String getOddRowStyleClass() {
        if (_oddRowStyleClass != null) {
            return _oddRowStyleClass;
        }
        ValueBinding vb = getValueBinding("oddRowStyleClass");
        return vb != null ? vb.getValue(getFacesContext()).toString() : null;
    }

    public void setOddRowStyleClass(String oddRowStyleClass) {
        _oddRowStyleClass = oddRowStyleClass;
    }

    public String getHoverRowStyleClass() {
        if (_hoverRowStyleClass != null) {
            return _hoverRowStyleClass;
        }
        ValueBinding vb = getValueBinding("hoverRowStyleClass");
        return vb != null ? vb.getValue(getFacesContext()).toString() : null;
    }

    public void setHoverRowStyleClass(String hoverRowStyleClass) {
        _hoverRowStyleClass = hoverRowStyleClass;
    }
}
