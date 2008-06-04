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
package org.apache.myfaces.taglib.html.ext;

import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.html.ext.HtmlDataTable;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlDataTableTagBase;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;

import javax.faces.component.UIComponent;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlDataTableTag
        extends HtmlDataTableTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlDataTableTag.class);

    public String getComponentType()
    {
        return HtmlDataTable.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlDataTable.DEFAULT_RENDERER_TYPE;
    }

    private String _preserveDataModel;
    private String _preserveRowStates;
    private String _forceIdIndexFormula;
    private String _sortColumn;
    private String _sortAscending;
    private String _sortable;
    private String _preserveSort;
    private String _enabledOnUserRole;
    private String _visibleOnUserRole;
    private String _renderedIfEmpty;
    private String _rowIndexVar;
    private String _rowCountVar;
    private String _previousRowDataVar;
    private String _sortedColumnVar;
    private String _rowOnClick;
    private String _rowOnDblClick;
    private String _rowOnMouseDown;
    private String _rowOnMouseUp;
    private String _rowOnMouseOver;
    private String _rowOnMouseMove;
    private String _rowOnMouseOut;
    private String _rowOnKeyPress;
    private String _rowOnKeyDown;
    private String _rowOnKeyUp;
    private String _rowId;
    private String _varDetailToggler;

    private String _rowStyleClass;
    private String _rowStyle;
    private String _rowGroupStyle;
    private String _rowGroupStyleClass;

    private String _bodyStyle;
    private String _bodyStyleClass;

    /** the number of newspaper columns */
    private String _newspaperColumns = null;
    /** the orientation of the newspaper table - horizontal/vertical */
    private String _newspaperOrientation = null;

	private String _embedded = null;
	private String _detailStampExpandedDefault = null;
	private String _detailStampLocation = null;

	public void release()
    {
        super.release();

        _preserveDataModel=null;
        _preserveRowStates = null;
        _forceIdIndexFormula=null;
        _sortColumn=null;
        _sortAscending=null;
        _sortable=null;
        _preserveSort=null;
        _enabledOnUserRole=null;
        _visibleOnUserRole=null;
        _renderedIfEmpty=null;
        _rowIndexVar=null;
        _rowCountVar=null;
        _previousRowDataVar=null;
        _sortedColumnVar=null;
        _rowOnClick=null;
        _rowOnDblClick=null;
        _rowOnMouseDown=null;
        _rowOnMouseUp=null;
        _rowOnMouseOver=null;
        _rowOnMouseMove=null;
        _rowOnMouseOut=null;
        _rowOnKeyPress=null;
        _rowOnKeyDown=null;
        _rowOnKeyUp=null;
        _rowId=null;
        _varDetailToggler=null;

        _rowStyleClass = null;
        _rowStyle = null;
        _rowGroupStyle = null;
        _rowGroupStyleClass = null;

        _newspaperColumns = null;
        _newspaperOrientation = null;

        _bodyStyle = null;
        _bodyStyleClass = null;

		_embedded = null;
		_detailStampExpandedDefault = null;
		_detailStampLocation = null;
	}

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setBooleanProperty(component, "preserveDataModel", _preserveDataModel);
        setBooleanProperty(component, "preserveRowStates", _preserveRowStates);
        setValueBinding(component, "forceIdIndexFormula", _forceIdIndexFormula);
        setValueBinding(component, "sortColumn", _sortColumn);
        setValueBinding(component, "sortAscending", _sortAscending);
        setBooleanProperty(component, "sortable", _sortable);
        setBooleanProperty(component, "preserveSort", _preserveSort);
        setStringProperty(component, UserRoleAware.ENABLED_ON_USER_ROLE_ATTR, _enabledOnUserRole);
        setStringProperty(component, UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR, _visibleOnUserRole);
        setBooleanProperty(component, "renderedIfEmpty", _renderedIfEmpty);
        setStringProperty(component, "rowIndexVar", _rowIndexVar);
        setStringProperty(component, "rowCountVar", _rowCountVar);
        setStringProperty(component, "previousRowDataVar", _previousRowDataVar);
        setStringProperty(component, "sortedColumnVar", _sortedColumnVar);
        setStringProperty(component, "rowOnClick", _rowOnClick);
        setStringProperty(component, "rowOnDblClick", _rowOnDblClick);
        setStringProperty(component, "rowOnMouseDown", _rowOnMouseDown);
        setStringProperty(component, "rowOnMouseUp", _rowOnMouseUp);
        setStringProperty(component, "rowOnMouseOver", _rowOnMouseOver);
        setStringProperty(component, "rowOnMouseMove", _rowOnMouseMove);
        setStringProperty(component, "rowOnMouseOut", _rowOnMouseOut);
        setStringProperty(component, "rowOnKeyPress", _rowOnKeyPress);
        setStringProperty(component, "rowOnKeyDown", _rowOnKeyDown);
        setStringProperty(component, "rowOnKeyUp", _rowOnKeyUp);
        setStringProperty(component, JSFAttr.ROW_ID, _rowId);
        setStringProperty(component,"varDetailToggler",_varDetailToggler);

        setStringProperty(component, JSFAttr.ROW_STYLECLASS_ATTR, _rowStyleClass);
        setStringProperty(component, JSFAttr.ROW_STYLE_ATTR, _rowStyle);
        setStringProperty(component, "rowGroupStyle", _rowGroupStyle);
        setStringProperty(component, "rowGroupStyleClass", _rowGroupStyleClass);

        setStringProperty(component, "bodyStyle", _bodyStyle);
        setStringProperty(component, "bodyStyleClass", _bodyStyleClass);

        setIntegerProperty(component, HtmlDataTable.NEWSPAPER_COLUMNS_PROPERTY, _newspaperColumns);
        setStringProperty(component, HtmlDataTable.NEWSPAPER_ORIENTATION_PROPERTY, _newspaperOrientation);

		setBooleanProperty(component, "embedded", _embedded);
		setBooleanProperty(component, "detailStampExpandedDefault", _detailStampExpandedDefault);
		setStringProperty(component, "detailStampLocation", _detailStampLocation);
    }

    public void setPreserveDataModel(String preserveDataModel)
    {
        _preserveDataModel = preserveDataModel;
    }

    public void setPreserveRowStates(String preserveRowStates)
    {
        _preserveRowStates = preserveRowStates;
    }

    public void setForceIdIndexFormula(String forceIdIndexFormula)
    {
        _forceIdIndexFormula = forceIdIndexFormula;
    }

    public void setSortColumn(String sortColumn)
    {
        _sortColumn = sortColumn;
    }

    public void setSortAscending(String sortAscending)
    {
        _sortAscending = sortAscending;
    }

    public void setSortable(String sortable)
    {
        _sortable = sortable;
    }

    public void setPreserveSort(String preserveSort)
    {
        _preserveSort = preserveSort;
    }

    public void setEnabledOnUserRole(String enabledOnUserRole)
    {
        _enabledOnUserRole = enabledOnUserRole;
    }

    public void setVisibleOnUserRole(String visibleOnUserRole)
    {
        _visibleOnUserRole = visibleOnUserRole;
    }

    public void setRenderedIfEmpty(String renderedIfEmpty)
    {
        _renderedIfEmpty = renderedIfEmpty;
    }

    public void setRowIndexVar(String rowIndexVar)
    {
        _rowIndexVar = rowIndexVar;
    }

    public void setRowCountVar(String rowCountVar)
    {
        _rowCountVar = rowCountVar;
    }

    public void setPreviousRowDataVar(String previousRowDataVar)
    {
        _previousRowDataVar = previousRowDataVar;
    }

    public void setSortedColumnVar(String sortedColumnVar)
    {
        _sortedColumnVar = sortedColumnVar;
    }

    public void setRowOnMouseOver(String rowOnMouseOver)
    {
        _rowOnMouseOver = rowOnMouseOver;
    }

    public void setRowOnMouseOut(String rowOnMouseOut)
    {
        _rowOnMouseOut = rowOnMouseOut;
    }

    public void setRowOnClick(String rowOnClick)
    {
      _rowOnClick = rowOnClick;
    }

    public void setRowOnDblClick(String rowOnDblClick)
    {
      _rowOnDblClick = rowOnDblClick;
    }

    public void setRowOnKeyDown(String rowOnKeyDown)
    {
      _rowOnKeyDown = rowOnKeyDown;
    }

    public void setRowOnKeyPress(String rowOnKeyPress)
    {
      _rowOnKeyPress = rowOnKeyPress;
    }

    public void setRowOnKeyUp(String rowOnKeyUp)
    {
      _rowOnKeyUp = rowOnKeyUp;
    }

    public void setRowOnMouseDown(String rowOnMouseDown)
    {
      _rowOnMouseDown = rowOnMouseDown;
    }

    public void setRowOnMouseMove(String rowOnMouseMove)
    {
      _rowOnMouseMove = rowOnMouseMove;
    }

    public void setRowOnMouseUp(String rowOnMouseUp)
    {
      _rowOnMouseUp = rowOnMouseUp;
    }

    public void setRowId(String rowId)
    {
      _rowId = rowId;
    }

    public void setRowStyleClass(String rowStyleClass)
    {
      _rowStyleClass = rowStyleClass;
    }

    public void setRowStyle(String rowStyle)
    {
      _rowStyle = rowStyle;
    }

    public void setRowGroupStyle(String rowGroupStyle)
    {
        _rowGroupStyle = rowGroupStyle;
    }

    public void setRowGroupStyleClass(String rowGroupStyleClass)
    {
        _rowGroupStyleClass = rowGroupStyleClass;
    }

    public String getVarDetailToggler() {
		return _varDetailToggler;
	}

	public void setVarDetailToggler(String varDetailToggler) {
		_varDetailToggler = varDetailToggler;
	}

    public void setNewspaperColumns(String newspaperColumns) {
        this._newspaperColumns = newspaperColumns;
    }

    public void setNewspaperOrientation(String newspaperOrientation) {
        this._newspaperOrientation = newspaperOrientation;
    }

    public String getBodyStyle() {
		return _bodyStyle;
	}

    public void setBodyStyle(String bodyStyle) {
        _bodyStyle = bodyStyle;
    }

    public String getBodyStyleClass() {
        return _bodyStyleClass;
    }

    public void setBodyStyleClass(String bodyStyleClass) {
        _bodyStyleClass = bodyStyleClass;
	}

	public String getEmbedded()
	{
		return _embedded;
	}

	public void setEmbedded(String embedded)
	{
		this._embedded = embedded;
	}

	public String getDetailStampExpandedDefault()
	{
		return _detailStampExpandedDefault;
	}

	public void setDetailStampExpandedDefault(String detailStampExpandedDefault)
	{
		this._detailStampExpandedDefault = detailStampExpandedDefault;
	}

	public String getDetailStampLocation()
	{
		return _detailStampLocation;
	}

	public void setDetailStampLocation(String detailStampLocation)
	{
		this._detailStampLocation = detailStampLocation;
	}
}
