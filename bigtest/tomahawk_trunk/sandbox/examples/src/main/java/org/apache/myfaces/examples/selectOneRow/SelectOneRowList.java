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
package org.apache.myfaces.examples.selectOneRow;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.myfaces.custom.datascroller.ScrollerActionEvent;

/**
 * DOCUMENT ME!
 * @author Ernst Fastl
 * @version
 */
public class SelectOneRowList
{
    private List _list = new ArrayList();

    private Long _selectedRowIndex;

    public Long getSelectedRowIndex()
    {
        return _selectedRowIndex;
    }

    public void setSelectedRowIndex(Long selectedRowIndex)
    {
        _selectedRowIndex = selectedRowIndex;
    }

    public String getSelectionMessage()
    {
        if(getSelectedRowIndex()==null)
        {
            return "Currently there is no Row selected!";
        }
        else
        {
            return "Row number: " + _selectedRowIndex.toString() + " selected!";
        }
    }

    public SelectOneRowList()
    {
        for (int i = 1; i < 10; i++)
        {
            _list.add(new SimpleCar(i, "Car Type " + i, "blue"));
            _list.add(new SimpleCar(i, "Car Type " + i, "red"));
            _list.add(new SimpleCar(i, "Car Type " + i, "green"));
            _list.add(new SimpleCar(i, "Car Type " + i, "black"));
            _list.add(new SimpleCar(i, "Car Type " + i, "white"));
        }
    }

    public List getList()
    {
        return _list;
    }

    public void scrollerAction(ActionEvent event)
    {
        ScrollerActionEvent scrollerEvent = (ScrollerActionEvent) event;
        FacesContext.getCurrentInstance().getExternalContext().log(
                        "scrollerAction: facet: "
                                        + scrollerEvent.getScrollerfacet()
                                        + ", pageindex: "
                                        + scrollerEvent.getPageIndex());
    }

    public void processRowSelection(ValueChangeEvent event)
    {
        Long newVal = (Long) event.getNewValue();
    }

}
