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
package org.apache.myfaces.examples.valueChangeNotifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NotifierBean
{

	private static Log log = LogFactory.getLog(NotifierBean.class);

	private String selectedCategory;
	private List categories;
	private List listData;
	private UIData listDataBinding;

	public static class EntryList implements Serializable
	{
		private List listData;

		public List getListData()
		{
			return listData;
		}

		public void setListData(List listData)
		{
			this.listData = listData;
		}
	}

	public static class Entry implements Serializable
	{
		private String data;

		public Entry()
		{
		}

		public String getData()
		{
			return data;
		}

		public void setData(String data)
		{
			this.data = data;
		}

		public String toString()
		{
			return "Entry=" + data;
		}
	}

	public NotifierBean()
	{
		categories = new ArrayList();
		categories.add(new SelectItem("a", "Category A"));
		categories.add(new SelectItem("b", "Category b"));

		Entry a1 = new Entry();
		a1.setData("A1");
		Entry b1 = new Entry();
		b1.setData("B1");
		Entry c1 = new Entry();
		c1.setData("C1");
		Entry d1 = new Entry();
		d1.setData("D1");
		List listData1 = Arrays.asList(new Entry[]
		{
				a1,b1,c1,d1
		});
		Entry a2 = new Entry();
		a2.setData("A2");
		Entry b2 = new Entry();
		b2.setData("B2");
		Entry c2 = new Entry();
		c2.setData("C2");
		Entry d2 = new Entry();
		d2.setData("D2");
		List listData2 = Arrays.asList(new Entry[]
		{
				a2,b2,c2,d2
		});

		EntryList al1 = new EntryList();
		al1.setListData(listData1);
		EntryList al2 = new EntryList();
		al2.setListData(listData2);

		listData = Arrays.asList(new EntryList[]
		                                       {
				al1, al2
		                                       });
	}

	public void valueChange(ValueChangeEvent vce)
			throws AbortProcessingException
	{
		if (log.isInfoEnabled())
		{
			log.info("invoked valueChange method with " + vce.getNewValue()
					+ " as its new value");

			if (listDataBinding != null)
			{
				log.info("current uiData row=" + listDataBinding.getRowIndex());
				if (listDataBinding.getRowIndex() > -1)
				{
					log.info("current uiData rowData=" + listDataBinding.getRowData());
				}
			}
		}

		selectedCategory = vce.getNewValue()!=null?vce.getNewValue().toString():"#null?";
	}

	public List getListData()
	{
		return listData;
	}

	public String getSelectedCategory()
	{
		return selectedCategory;
	}

	public void setSelectedCategory(String selectedCategory)
	{
		this.selectedCategory = selectedCategory;
	}

	public List getCategories()
	{
		return categories;
	}

	public void setCategories(List categories)
	{
		this.categories = categories;
	}

	public UIData getListDataBinding()
	{
		return listDataBinding;
	}

	public void setListDataBinding(UIData listDataBinding)
	{
		this.listDataBinding = listDataBinding;
	}
}
