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
package org.apache.myfaces.examples.listexample;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.myfaces.custom.datascroller.ScrollerActionEvent;

/**
 * DOCUMENT ME!
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DataScrollerList
{
	private Long rowCount = new Long(10);

	public Long getRowCount() {
		return rowCount;
	}

	public void setRowCount(Long rowCount) {
		this.rowCount = rowCount;
	}
	
    private List _list = new ArrayList();

    public DataScrollerList()
    {
        for (int i = 1; i < 995; i++)
        {
            _list.add(new SimpleCar(i, "Car Type " + i, "blue"));
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
}
