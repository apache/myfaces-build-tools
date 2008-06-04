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
package org.apache.myfaces.examples.picklist;

import java.util.ArrayList;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

public class PicklistBean
{
    private ArrayList testList = new ArrayList();

    private String selectedInfo;

    public PicklistBean()
    {
        testList.add(new SelectItem("option4", "another option 4"));
        testList.add(new SelectItem("option5", "another option 5"));
        testList.add(new SelectItem("option6", "another option 6"));
    }

    public void selectionChanged(ValueChangeEvent evt)
    {
        String[] selectedValues = (String[]) evt.getNewValue();

        if (selectedValues.length == 0)
        {
            selectedInfo = "No selected values";
        }
        else
        {

            StringBuffer sb = new StringBuffer("Selected values: ");

            for (int i = 0; i < selectedValues.length; i++)
            {
                if (i > 0)
                    sb.append(", ");
                sb.append(selectedValues[i]);
            }

            selectedInfo = sb.toString();
        }
    }

    public ArrayList getTestList()
    {
        return testList;
    }

    public String getSelectedInfo()
    {
        return selectedInfo;
    }

}
