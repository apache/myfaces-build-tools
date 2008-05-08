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
package org.apache.myfaces.component.html.ext;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.ResultDataModel;
import javax.faces.model.ResultSetDataModel;
import javax.faces.model.ScalarDataModel;
import javax.servlet.jsp.jstl.sql.Result;

import org.apache.myfaces.component.ForceIdAware;
import org.apache.myfaces.custom.ExtendedComponentBase;

/**
 * Reimplement all UIData functionality to be able to have (protected) access
 * the internal DataModel.
 *
 * @JSFComponent
 *  configExcluded = "true"
 *
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class HtmlDataTableHack extends
                javax.faces.component.html.HtmlDataTable 
                
{
    private Map _dataModelMap = new HashMap();

    // will be set to false if the data should not be refreshed at the beginning of the encode phase
    private boolean _isValidChilds = true;

    // holds for each row the states of the child components of this UIData
    private Map _rowStates = new HashMap();

    // contains the initial row state which is used to initialize each row
    private Object _initialDescendantComponentState = null;

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Every field and method from here is identical to UIData !!!!!!!!!
    // EXCEPTION: we can create a DataModel from a Collection

    private static final Class OBJECT_ARRAY_CLASS = (new Object[0]).getClass();

    private static final boolean DEFAULT_PRESERVEROWSTATES = false;

    private int _rowIndex = -1;

    private Boolean _preserveRowStates;

    public boolean isRowAvailable()
    {
        return getDataModel().isRowAvailable();
    }

    public int getRowCount()
    {
        return getDataModel().getRowCount();
    }

    public Object getRowData()
    {
        return getDataModel().getRowData();
    }

    public int getRowIndex()
    {
        return _rowIndex;
    }
    
    /**
     * Hack since RI does not call getRowIndex()
     */
    public String getClientId(FacesContext context)
    {
        String clientId = super.getClientId(context);
        int rowIndex = getRowIndex();
        if (rowIndex == -1)
        {
            return clientId;
        }
        // the following code tries to avoid rowindex to be twice in the client id
        int index = clientId.lastIndexOf(NamingContainer.SEPARATOR_CHAR);
        if(index != -1)
        {
            String rowIndexString = clientId.substring(index + 1);
            try
            {
                if(Integer.parseInt(rowIndexString) == rowIndex)
                {
                    return clientId;
                }
            }
            catch(NumberFormatException e)
            {
                return clientId + NamingContainer.SEPARATOR_CHAR + rowIndex;
            }
        }
        return clientId + NamingContainer.SEPARATOR_CHAR + rowIndex;
    }

    /**
     * @see javax.faces.component.UIData#processUpdates(javax.faces.context.FacesContext)
     */
    public void processUpdates(FacesContext context)
    {
        super.processUpdates(context);
        // check if a update model error forces the render response for our data
        if (context.getRenderResponse())
        {
            _isValidChilds = false;
        }
    }

    /**
     * @see javax.faces.component.UIData#processValidators(javax.faces.context.FacesContext)
     */
    public void processValidators(FacesContext context)
    {
        super.processValidators(context);
        // check if a validation error forces the render response for our data
        if (context.getRenderResponse())
        {
            _isValidChilds = false;
        }
    }

    /**
     * @see javax.faces.component.UIData#encodeBegin(javax.faces.context.FacesContext)
     */
    public void encodeBegin(FacesContext context) throws IOException
    {
        _initialDescendantComponentState = null;
        if (_isValidChilds && !hasErrorMessages(context))
        {
            //Refresh DataModel for rendering:
            _dataModelMap.clear();
            if (!isPreserveRowStates())
            {
            	_rowStates.clear();
            }
        }
        super.encodeBegin(context);
    }

    public void setPreserveRowStates(boolean preserveRowStates)
    {
        _preserveRowStates = Boolean.valueOf(preserveRowStates);
    }

    /**
     * @JSFProperty
     *   defaultValue="false"
     */
    public boolean isPreserveRowStates()
    {
        if (_preserveRowStates != null)
            return _preserveRowStates.booleanValue();
        ValueBinding vb = getValueBinding("preserveRowStates");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : DEFAULT_PRESERVEROWSTATES;
    }

    protected boolean hasErrorMessages(FacesContext context)
    {
        for(Iterator iter = context.getMessages(); iter.hasNext();)
        {
            FacesMessage message = (FacesMessage) iter.next();
            if(FacesMessage.SEVERITY_ERROR.compareTo(message.getSeverity()) <= 0)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @see javax.faces.component.UIComponentBase#encodeEnd(javax.faces.context.FacesContext)
     */
    public void encodeEnd(FacesContext context) throws IOException
    {
        setRowIndex(-1);
        super.encodeEnd(context);
    }

    public void setRowIndex(int rowIndex)
    {
        if (rowIndex < -1)
        {
            throw new IllegalArgumentException("rowIndex is less than -1");
        }

        if (_rowIndex == rowIndex)
        {
            return;
        }

        FacesContext facesContext = getFacesContext();

        if (_rowIndex == -1)
        {
            if (_initialDescendantComponentState == null)
            {
                _initialDescendantComponentState = saveDescendantComponentStates(getChildren()
                                .iterator(), false);
            }
        }
        else
        {
            _rowStates.put(getClientId(facesContext),
                            saveDescendantComponentStates(getChildren()
                                            .iterator(), false));
        }

        _rowIndex = rowIndex;

        DataModel dataModel = getDataModel();
        dataModel.setRowIndex(rowIndex);

        String var = getVar();
        if (rowIndex == -1)
        {
            if (var != null)
            {
                facesContext.getExternalContext().getRequestMap().remove(var);
            }
        }
        else
        {
            if (var != null)
            {
                if (isRowAvailable())
                {
                    Object rowData = dataModel.getRowData();
                    facesContext.getExternalContext().getRequestMap().put(var,
                                    rowData);
                }
                else
                {
                    facesContext.getExternalContext().getRequestMap().remove(
                                    var);
                }
            }
        }

        if (_rowIndex == -1)
        {
            restoreDescendantComponentStates(getChildren().iterator(),
                            _initialDescendantComponentState, false);
        }
        else
        {
            Object rowState = _rowStates.get(getClientId(facesContext));
            if (rowState == null)
            {
                restoreDescendantComponentStates(getChildren().iterator(),
                                _initialDescendantComponentState, false);
            }
            else
            {
                restoreDescendantComponentStates(getChildren().iterator(),
                                rowState, false);
            }
        }
    }

    protected void restoreDescendantComponentStates(Iterator childIterator,
            Object state, boolean restoreChildFacets)
    {
        Iterator descendantStateIterator = null;
        while (childIterator.hasNext())
        {
            if (descendantStateIterator == null && state != null)
            {
                descendantStateIterator = ((Collection) state).iterator();
            }
            UIComponent component = (UIComponent) childIterator.next();
            // reset the client id (see spec 3.1.6)
            component.setId(component.getId());
            if(!component.isTransient())
            {
                Object childState = null;
                Object descendantState = null;
                if (descendantStateIterator != null
                        && descendantStateIterator.hasNext())
                {
                    Object[] object = (Object[]) descendantStateIterator.next();
                    childState = object[0];
                    descendantState = object[1];
                }
                if (childState != null && component instanceof EditableValueHolder)
                {
                    ((EditableValueHolderState) childState)
                            .restoreState((EditableValueHolder) component);
                }
                Iterator childsIterator;
                if (restoreChildFacets)
                {
                    childsIterator = component.getFacetsAndChildren();
                }
                else
                {
                    childsIterator = component.getChildren().iterator();
                }
                restoreDescendantComponentStates(childsIterator, descendantState,
                        true);
            }
        }
    }

    protected Object saveDescendantComponentStates(Iterator childIterator,
            boolean saveChildFacets)
    {
        Collection childStates = null;
        while (childIterator.hasNext())
        {
            if (childStates == null)
            {
                childStates = new ArrayList();
            }
            UIComponent child = (UIComponent) childIterator.next();
            if(!child.isTransient())
            {
                Iterator childsIterator;
                if (saveChildFacets)
                {
                    childsIterator = child.getFacetsAndChildren();
                }
                else
                {
                    childsIterator = child.getChildren().iterator();
                }
                Object descendantState = saveDescendantComponentStates(
                        childsIterator, true);
                Object state = null;
                if (child instanceof EditableValueHolder)
                {
                    state = new EditableValueHolderState(
                            (EditableValueHolder) child);
                }
                childStates.add(new Object[] { state, descendantState });
            }
        }
        return childStates;
    }

    public void setValueBinding(String name, ValueBinding binding)
    {
        if (name == null)
        {
            throw new NullPointerException("name");
        }
        else if (name.equals("value"))
        {
            _dataModelMap.clear();
        }
        else if (name.equals("var") || name.equals("rowIndex"))
        {
            throw new IllegalArgumentException(
                    "You can never set the 'rowIndex' or the 'var' attribute as a value-binding. Set the property directly instead. Name " + name);
        }
        super.setValueBinding(name, binding);
    }

    /**
     * @see javax.faces.component.UIData#setValue(java.lang.Object)
     */
    public void setValue(Object value)
    {
        super.setValue(value);
        _dataModelMap.clear();
        _rowStates.clear();
        _isValidChilds = true;
    }

    protected DataModel getDataModel()
    {
        DataModel dataModel = null;
        String clientID = "";
        
        UIComponent parent = getParent();
        if (parent != null) 
        {
            clientID = parent.getClientId(getFacesContext());
        }
        dataModel = (DataModel) _dataModelMap.get(clientID);
        if (dataModel == null)
        {
            dataModel = createDataModel();
            _dataModelMap.put(clientID, dataModel);
        }               
        
        return dataModel;
    }

    protected void setDataModel(DataModel datamodel)
    {
        UIComponent parent = getParent();
        String clientID = "";
        if(parent != null)
        {
            clientID = parent.getClientId(getFacesContext());
        }
        _dataModelMap.put(clientID, datamodel);
    }

    /**
     * Creates a new DataModel around the current value.
     */
    protected DataModel createDataModel()
    {
        Object value = getValue();
        if (value == null)
        {
            return EMPTY_DATA_MODEL;
        }
        else if (value instanceof DataModel)
        {
            return (DataModel) value;
        }
        else if (value instanceof List)
        {
            return new ListDataModel((List) value);
        }
        // accept a Collection is not supported in the Spec
        else if (value instanceof Collection)
        {
            return new ListDataModel(new ArrayList((Collection) value));
        }
        else if (OBJECT_ARRAY_CLASS.isAssignableFrom(value.getClass()))
        {
            return new ArrayDataModel((Object[]) value);
        }
        else if (value instanceof ResultSet)
        {
            return new ResultSetDataModel((ResultSet) value);
        }
        else if (value instanceof Result)
        {
            return new ResultDataModel((Result) value);
        }
        else
        {
            return new ScalarDataModel(value);
        }
    }
    
    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[4];
        values[0] = super.saveState(context);
        values[1] = _preserveRowStates;
        values[2] = _forceId;
        values[3] = _forceIdIndex;
        return values;
    }
    
    public void restoreState(FacesContext context, Object state)
    {
        Object[] values = (Object[])state;
        super.restoreState(context, values[0]);
        _preserveRowStates = (Boolean) values[1];
        _forceId = (Boolean) values[2];
        _forceIdIndex = (Boolean) values[3];
    }

    private static final DataModel EMPTY_DATA_MODEL = new _SerializableDataModel()
    {
        public boolean isRowAvailable()
        {
            return false;
        }

        public int getRowCount()
        {
            return 0;
        }

        public Object getRowData()
        {
            throw new IllegalArgumentException();
        }

        public int getRowIndex()
        {
            return -1;
        }

        public void setRowIndex(int i)
        {
            if (i < -1)
                throw new IndexOutOfBoundsException("Index < 0 : " + i);
        }

        public Object getWrappedData()
        {
            return null;
        }

        public void setWrappedData(Object obj)
        {
            if (obj == null)
                return; //Clearing is allowed
            throw new UnsupportedOperationException(this.getClass().getName()
                            + " UnsupportedOperationException");
        }
    };

    private class EditableValueHolderState
    {
        private final Object _value;
        private final boolean _localValueSet;
        private final boolean _valid;
        private final Object _submittedValue;

        public EditableValueHolderState(EditableValueHolder evh)
        {
            _value = evh.getLocalValue();
            _localValueSet = evh.isLocalValueSet();
            _valid = evh.isValid();
            _submittedValue = evh.getSubmittedValue();
        }

        public void restoreState(EditableValueHolder evh)
        {
            evh.setValue(_value);
            evh.setLocalValueSet(_localValueSet);
            evh.setValid(_valid);
            evh.setSubmittedValue(_submittedValue);
        }
    }
    
    // Property: forceId
    private Boolean _forceId  = Boolean.valueOf(false);
    
    /**
     * @JSFProperty
     *   literalOnly = "true"
     *   defaultValue = "false"
     *   
     * @return
     */
    public Boolean getForceId()
    {
        return _forceId;
    }

    public void setForceId(Boolean forceId)
    {
        this._forceId = forceId;
    }
    // Property: forceIdIndex
    private Boolean _forceIdIndex  = Boolean.valueOf(true);
    
    /**
     * @JSFProperty
     *   literalOnly = "true"
     *   defaultValue = "true"
     *   
     * @return
     */
    public Boolean getForceIdIndex()
    {
        return _forceIdIndex;
    }

    public void setForceIdIndex(Boolean forceIdIndex)
    {
        this._forceIdIndex = forceIdIndex;
    }
    
    private static boolean booleanFromObject(Object obj, boolean defaultValue)
    {
        if(obj instanceof Boolean)
        {
            return ((Boolean) obj).booleanValue();
        }
        else if(obj instanceof String)
        {
            return Boolean.valueOf(((String) obj)).booleanValue();
        }

        return defaultValue;
    }

    /**
     * Remove all preserved row state for the dataTable
     */
    public void clearRowStates()
    {
    	_rowStates.clear();
    }
    
    /**
     * Remove preserved row state for deleted row and adjust row state to reflect deleted row.
     * @param deletedIndex index of row to delete
     */
    public void deleteRowStateForRow(int deletedIndex)
    {
    	// save row index
    	int savedRowIndex = getRowIndex();
    	
    	FacesContext facesContext = FacesContext.getCurrentInstance();
     	setRowIndex(deletedIndex);
    	String currentRowStateKey = getClientId(facesContext);

    	// copy next rowstate to current row for each row from deleted row onward.
    	int rowCount = getRowCount();
    	for (int index = deletedIndex + 1; index < rowCount; ++index)
    	{
        	setRowIndex(index);
        	String nextRowStateKey = getClientId(facesContext);

            Object nextRowState = _rowStates.get(nextRowStateKey);
            if (nextRowState == null)
            {
            	_rowStates.remove(currentRowStateKey);
            }
            else
            {
            	_rowStates.put(currentRowStateKey, nextRowState);
            }
        	currentRowStateKey = nextRowStateKey;
    	}

    	// Remove last row
    	_rowStates.remove(currentRowStateKey);

    	// restore saved row index
    	setRowIndex(savedRowIndex);
    }
    
}
