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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleGroupByList
{
	private List _demoList = new ArrayList();

	private Map _demoListMap = new HashMap();

	public SimpleGroupByList()
	{
		for (int i = 0; i < 12; i++)
		{
			Object demo = new SimpleDemo(i, (i < 3 )? "Group 1":(i < 6 ) ? "Group 2": (i < 9)?"Group 3":"Group 4", "Item "+i);
			_demoList.add(demo);
			_demoListMap.put(new Integer(i), demo);
		}
	}

	public List getDemoList()
	{
		return _demoList;
	}

	public void setDemoList(List demo)
    {
        _demoList = demo;
    }

}
