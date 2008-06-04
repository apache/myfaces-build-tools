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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.model.ListDataModel;

import junit.framework.Test;

import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.utils.TestUtils;

/**
 * @author Mathias Br�kelmann (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlDataTableTest extends AbstractTomahawkViewControllerTestCase
{

    private HtmlDataTable _dataTable;

    public HtmlDataTableTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        return null; // keep this method or maven won't run it
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        _dataTable = new HtmlDataTable();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
        _dataTable = null;
    }

    /*
     * Test method for
     * 'org.apache.myfaces.component.html.ext.HtmlDataTable.getClientId(FacesContext)'
     */
    public void testGetClientIdFacesContext()
    {
        assertEquals(-1, _dataTable.getRowIndex());
        String baseClientId = _dataTable.getClientId(facesContext);
        assertNotNull(baseClientId);
        Collection rowClientIds = new HashSet();
        for (int i = 0; i < 10; i++)
        {
            _dataTable.setRowIndex(i);
            assertTrue("Duplicate client id while iterating rows", rowClientIds
                    .add(_dataTable.getClientId(facesContext)));
        }
        _dataTable.setRowIndex(-1);
        assertEquals(baseClientId, _dataTable.getClientId(facesContext));
    }

    /*
     * Test method for
     * 'org.apache.myfaces.component.html.ext.HtmlDataTable.findComponent(String clientId)'
     */
    public void testFindComponent()
    {
        UIInput input = createInputInTree(facesContext);

        UIData data = (UIData) input.getParent().getParent();

        UIComponent comp = data.findComponent(":data:1:input");

        assertTrue(comp instanceof UIInput);

        UIInput uiInput = (UIInput) comp;

        assertEquals(uiInput.getValue(), "test2");

    }

    /**
     * Verify component renders with the default renderer.
     */
    public void testDefaultRenderer()
    {
        // Define the component
        UIData component = new HtmlDataTable();
        component.setId("TestComponent");

        // Add rows to the table
        List rows = new ArrayList();
        rows.add(new TestData("test1", "test1"));
        rows.add(new TestData("test2", "test2"));
        rows.add(new TestData("test3", "test3"));
        component.setValue(new ListDataModel(rows));

        // Render the component
        try
        {
            TestUtils.renderComponent(facesContext, component);
        }
        catch (IOException e)
        {
            fail(e.getMessage());
        }

        // Verify component was rendered
        assertIdExists(component.getId());
    }

    private UIInput createInputInTree(FacesContext context)
    {

        UIData uiData = new HtmlDataTable();
        uiData.setId("data");

        List li = new ArrayList();
        li.add(new TestData("test1", "test1"));
        li.add(new TestData("test2", "test2"));
        li.add(new TestData("test3", "test3"));

        uiData.setValue(new ListDataModel(li));
        uiData.setVar("detail");

        UIColumn column = new UIColumn();

        uiData.getChildren().add(column);

        UIInput input = new UIInput();
        input.setId("input");
        input.setValueBinding("value",
                createValueBinding("#{detail.description}"));

        column.getChildren().add(input);

        facesContext.getViewRoot().getChildren().add(uiData);

        return input;
    }

    private ValueBinding createValueBinding(String expr)
    {
        return facesContext.getApplication().createValueBinding(expr);
    }

    public static class TestData implements Serializable
    {
        private String _description;
        private String _value;

        public TestData()
        {

        }

        TestData(String description, String value)
        {
            _description = description;
            _value = value;
        }

        public String getDescription()
        {
            return _description;
        }

        public void setDescription(String description)
        {
            _description = description;
        }

        public String getValue()
        {
            return _value;
        }

        public void setValue(String value)
        {
            _value = value;
        }
    }

}
