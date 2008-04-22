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

package org.apache.myfaces.examples.jslistener;

import javax.faces.model.SelectItem;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Martin Marinschek
 */
public class JsListenerModel
{
    private List options;
    private List optionItems;

    public List getOptions()
    {
        if(options == null)
        {
            initOptions();
        }
        return options;
    }

    public void setOptions(List options)
    {
        this.options = options;
    }

    public List getOptionItems()
    {
    	if( optionItems == null ) {
    		initOptions();
    	}
        return optionItems;
    }
    
    private void initOptions() {
    	options = new ArrayList();
        options.add("o1");
        options.add("o2");
        options.add("o3");
        options.add("o4");

        optionItems = new ArrayList();
        optionItems.add(new SelectItem("o1","Option 1"));
        optionItems.add(new SelectItem("o2","Option 2"));
        optionItems.add(new SelectItem("o3","Option 3"));
        optionItems.add(new SelectItem("o4","Option 4"));            
    }
}
