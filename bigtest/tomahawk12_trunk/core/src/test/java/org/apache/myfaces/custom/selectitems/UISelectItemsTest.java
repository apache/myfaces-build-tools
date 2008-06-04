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
package org.apache.myfaces.custom.selectitems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.faces.el.ValueBinding;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.shale.test.base.AbstractJsfTestCase;

/**
 * @author cagatay (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UISelectItemsTest extends AbstractJsfTestCase{
	
	private UISelectItems selectItems;
	
	private Collection movieCollection;
	
	private Collection movieSelectItemsGroupCollection;
	
	private Map movieMap;
	
	private SelectItem[] movieSelectItems;

	public UISelectItemsTest(String testName) {
		super(testName);
	}
	
	public static Test suite() {
		return new TestSuite(UISelectItemsTest.class);
	}

	/**
	 * Sets up the test environment for <s:selectItems value="#{PossibleValueHere}" var="Movie" itemLabel="#{Movie.name} itemValue="#{Movie.director}" />
	 * Accepted possible values for the component can be a SelectItems array, a collection, a map and SelectItems group collection.
	 */
	public void setUp() throws Exception{
		super.setUp();
		//component
		selectItems = new UISelectItems();
		ValueBinding itemLabelVb =  facesContext.getApplication().createValueBinding("#{Movie.name}");
		ValueBinding itemValueVb =  facesContext.getApplication().createValueBinding("#{Movie.director}");
		
		selectItems.setValueBinding("itemLabel", itemLabelVb);
		selectItems.setValueBinding("itemValue", itemValueVb);
		selectItems.getAttributes().put("var", "Movie");
		
		//entities
		Movie movie1 = new Movie("Godfather", "Francis Ford Coppola");
		Movie movie2 = new Movie("Goodfellas", "Martin Scorsese");
		Movie movie3 = new Movie("Casino", "Martin Scorsese");
		Movie movie4 = new Movie("Scarface", "Brian De Palma");
		
		//different value types
		movieSelectItems = new SelectItem[3];
		movieSelectItems[0] = new SelectItem(movie2.getDirector(), movie2.getName());
		movieSelectItems[1] = new SelectItem(movie3.getDirector(), movie3.getName());
		movieSelectItems[2] = new SelectItem(movie4.getDirector(), movie4.getName());
		
		movieCollection = new ArrayList();
		movieCollection.add(movie1);
		movieCollection.add(movie2);
		movieCollection.add(movie3);
		movieCollection.add(movie4);
		
		movieMap = new HashMap();
		movieMap.put(movie3.getName(), movie3);
		movieMap.put(movie4.getName(), movie4);

		movieSelectItemsGroupCollection = new ArrayList();
		movieSelectItemsGroupCollection.add(createSelectItemGroup("group1", movieSelectItems));
		movieSelectItemsGroupCollection.add(createSelectItemGroup("group2", movieSelectItems));
	}
	
	private SelectItemGroup createSelectItemGroup(String groupName, SelectItem[] items) {
		SelectItemGroup group = new SelectItemGroup();
		group.setLabel(groupName);
		group.setSelectItems(items);
 		return group;
	}
	
	public void tearDown() throws Exception{
		selectItems = null;
		super.tearDown();
	}
	
	public void testCreateSelectItemsBySelectItems() {
		selectItems.setValue(movieSelectItems);
		SelectItem[] items = (SelectItem[]) selectItems.getValue();
		assertEquals(items[1].getValue(), new String("Martin Scorsese"));
		assertEquals(items[1].getLabel(), new String("Casino"));
		assertEquals(items.length, 3);
	}
	
	public void testCreateSelectItemsFromCollection() {
		selectItems.setValue(movieCollection);
		SelectItem[] items = (SelectItem[]) selectItems.getValue();
		assertEquals(items[0].getValue(), "Francis Ford Coppola");
		assertEquals(items[0].getLabel(), "Godfather");
		assertEquals(items.length, 4);
	}
	
	public void testCreateSelectItemsFromMap() {
		selectItems.setValue(movieMap);
		SelectItem[] items = (SelectItem[]) selectItems.getValue();
		assertEquals(items.length, 2);
	}
	
	public void testCreateSelectItemsFromSelectItemsGroupCollection() {
		selectItems.setValue(movieSelectItemsGroupCollection);
		SelectItem[] items = (SelectItem[]) selectItems.getValue();
		assertEquals(items[5].getValue(), "Brian De Palma");
		assertEquals(items[5].getLabel(),"Scarface");
		assertEquals(items.length, 6);
	}
}
