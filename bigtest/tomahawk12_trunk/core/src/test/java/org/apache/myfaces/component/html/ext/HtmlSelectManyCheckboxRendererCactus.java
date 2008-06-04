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
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UISelectItems;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.model.SelectItem;

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import org.apache.cactus.WebResponse;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlResponseWriterImpl;
import org.apache.myfaces.renderkit.html.ext.HtmlCheckboxRenderer;

/**
 * Cactus test for the rendering of the
 * {@link org.apache.myfaces.component.html.ext.HtmlSelectBooleanCheckbox}
 * component.  Various combinations of layouts (lineDirection,pageDirection)
 * and layoutWidths are tested.
 * TODO: Test the markup generated when SelectItemGroup's are used in
 *       combination with different layouts and layoutWidths.
 * @author Ken Weiner
 */
public class HtmlSelectManyCheckboxRendererCactus extends ServletTestCase
{
    private FacesContext facesContext;
    private UIViewRoot viewRoot;

    public void setUp() throws Exception
    {
        FacesContextFactory facesContextFactory = (FacesContextFactory) FactoryFinder
                .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
        LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder
                .getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        Lifecycle lifecycle = lifecycleFactory
                .getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
        facesContext = facesContextFactory.getFacesContext(this.config
                .getServletContext(), request, response, lifecycle);
        assertNotNull(facesContext);

        Writer htmlResponseWriter = response.getWriter();
        facesContext.setResponseWriter(new HtmlResponseWriterImpl(
                htmlResponseWriter, "text/html", "ISO-8859-1"));

        Application application = facesContext.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        String viewId = "/index.jsp";
        viewRoot = viewHandler.createView(facesContext, viewId);
        viewRoot.setViewId(viewId);
        facesContext.setViewRoot(viewRoot);
    }

    public void beginEncodeWithLineDirection(WebRequest request)
    {
        // Nothing to do
    }

    public void testEncodeWithLineDirection() throws IOException
    {
        // Make component and set its properties
        HtmlSelectManyCheckbox selectMany = new HtmlSelectManyCheckbox();
        selectMany.setId("test");
        selectMany.setLayout("lineDirection");
        selectMany.setValue(getSelections(2));
        UISelectItems uiSelectItems = new UISelectItems();
        uiSelectItems.setParent(selectMany);
        uiSelectItems.setValue(getSelectManyCheckboxSelections(3));
        selectMany.getChildren().add(uiSelectItems);

        // Make a renderer, associate it with component, and call encode
        HtmlCheckboxRenderer renderer = new HtmlCheckboxRenderer();
        renderer.encodeBegin(facesContext, selectMany);
        renderer.encodeEnd(facesContext, selectMany);
    }

    public void endEncodeWithLineDirection(WebResponse response)
    {
        // Check the markup produced by the component
        String htmlMarkup = response.getText();
        String expected =
            "<table id=\"test\">" +
            "<tr>" +
            "<td>" +
            "<label>" +
            "<input type=\"checkbox\" name=\"test\" checked=\"checked\" value=\"Value-0\" />" +
            "&#160;Label-0" +
            "</label>" +
            "</td>" +
            "<td>" +
            "<label>" +
            "<input type=\"checkbox\" name=\"test\" checked=\"checked\" value=\"Value-1\" />" +
            "&#160;Label-1" +
            "</label>" +
            "</td>" +
            "<td>" +
            "<label>" +
            "<input type=\"checkbox\" name=\"test\" value=\"Value-2\" />" +
            "&#160;Label-2" +
            "</label>" +
            "</td>" +
            "</tr>" +
            "</table>";
        assertEquals(expected, htmlMarkup);
    }

    public void beginEncodeWithPageDirection(WebRequest request)
    {
        // Nothing to do
    }

    public void testEncodeWithPageDirection() throws IOException
    {
        // Make component and set its properties
        HtmlSelectManyCheckbox selectMany = new HtmlSelectManyCheckbox();
        selectMany.setId("test");
        selectMany.setLayout("pageDirection");
        selectMany.setValue(getSelections(2));
        UISelectItems uiSelectItems = new UISelectItems();
        uiSelectItems.setParent(selectMany);
        uiSelectItems.setValue(getSelectManyCheckboxSelections(3));
        selectMany.getChildren().add(uiSelectItems);

        // Make a renderer, associate it with component, and call encode
        HtmlCheckboxRenderer renderer = new HtmlCheckboxRenderer();
        renderer.encodeBegin(facesContext, selectMany);
        renderer.encodeEnd(facesContext, selectMany);
    }

    public void endEncodeWithPageDirection(WebResponse response)
    {
        // Check the markup produced by the component
        String htmlMarkup = response.getText();
        String expected =
            "<table id=\"test\">" +
            "<tr>" +
            "<td>" +
            "<label>" +
            "<input type=\"checkbox\" name=\"test\" checked=\"checked\" value=\"Value-0\" />" +
            "&#160;Label-0" +
            "</label>" +
            "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>" +
            "<label>" +
            "<input type=\"checkbox\" name=\"test\" checked=\"checked\" value=\"Value-1\" />" +
            "&#160;Label-1" +
            "</label>" +
            "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>" +
            "<label>" +
            "<input type=\"checkbox\" name=\"test\" value=\"Value-2\" />" +
            "&#160;Label-2" +
            "</label>" +
            "</td>" +
            "</tr>" +
            "</table>";
        assertEquals(expected, htmlMarkup);
    }

    public void beginEncodeWithLineDirectionAndLayoutWidth(WebRequest request)
    {
        // Nothing to do
    }

    public void testEncodeWithLineDirectionAndLayoutWidth() throws IOException
    {
        // Make component and set its properties
        HtmlSelectManyCheckbox selectMany = new HtmlSelectManyCheckbox();
        selectMany.setId("test");
        selectMany.setLayout("lineDirection");
        selectMany.setLayoutWidth("2");
        selectMany.setValue(getSelections(2));
        UISelectItems uiSelectItems = new UISelectItems();
        uiSelectItems.setParent(selectMany);
        uiSelectItems.setValue(getSelectManyCheckboxSelections(5));
        selectMany.getChildren().add(uiSelectItems);

        // Make a renderer, associate it with component, and call encode
        HtmlCheckboxRenderer renderer = new HtmlCheckboxRenderer();
        renderer.encodeBegin(facesContext, selectMany);
        renderer.encodeEnd(facesContext, selectMany);
    }

    public void endEncodeWithLineDirectionAndLayoutWidth(WebResponse response)
    {
        // Check the markup produced by the component
        String htmlMarkup = response.getText();
        String expected =
            "<table id=\"test\">" +
            "<tr>" +
            "<td>" +
            "<label>" +
            "<input type=\"checkbox\" name=\"test\" checked=\"checked\" value=\"Value-0\" />" +
            "&#160;Label-0" +
            "</label>" +
            "</td>" +
            "<td>" +
            "<label>" +
            "<input type=\"checkbox\" name=\"test\" value=\"Value-2\" />" +
            "&#160;Label-2" +
            "</label>" +
            "</td>" +
            "<td>" +
            "<label>" +
            "<input type=\"checkbox\" name=\"test\" value=\"Value-4\" />" +
            "&#160;Label-4" +
            "</label>" +
            "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>" +
            "<label>" +
            "<input type=\"checkbox\" name=\"test\" checked=\"checked\" value=\"Value-1\" />" +
            "&#160;Label-1" +
            "</label>" +
            "</td>" +
            "<td>" +
            "<label>" +
            "<input type=\"checkbox\" name=\"test\" value=\"Value-3\" />" +
            "&#160;Label-3" +
            "</label>" +
            "</td>" +
            "<td>" +
            // empty cell
            "</td>" +
            "</tr>" +
            "</table>";
        assertEquals(expected, htmlMarkup);
    }

    public void beginEncodeWithPageDirectionAndLayoutWidth(WebRequest request)
    {
        // Nothing to do
    }

    public void testEncodeWithPageDirectionAndLayoutWidth() throws IOException
    {
        // Make component and set its properties
        HtmlSelectManyCheckbox selectMany = new HtmlSelectManyCheckbox();
        selectMany.setId("test");
        selectMany.setLayout("pageDirection");
        selectMany.setLayoutWidth("2");
        selectMany.setValue(getSelections(2));
        UISelectItems uiSelectItems = new UISelectItems();
        uiSelectItems.setParent(selectMany);
        uiSelectItems.setValue(getSelectManyCheckboxSelections(5));
        selectMany.getChildren().add(uiSelectItems);

        // Make a renderer, associate it with component, and call encode
        HtmlCheckboxRenderer renderer = new HtmlCheckboxRenderer();
        renderer.encodeBegin(facesContext, selectMany);
        renderer.encodeEnd(facesContext, selectMany);
    }

    public void endEncodeWithPageDirectionAndLayoutWidth(WebResponse response)
    {
        // Check the markup produced by the component
        String htmlMarkup = response.getText();
        String expected =
            "<table id=\"test\">" +
            "<tr>" +
            "<td>" +
            "<label>" +
            "<input type=\"checkbox\" name=\"test\" checked=\"checked\" value=\"Value-0\" />" +
            "&#160;Label-0" +
            "</label>" +
            "</td>" +
            "<td>" +
            "<label>" +
            "<input type=\"checkbox\" name=\"test\" checked=\"checked\" value=\"Value-1\" />" +
            "&#160;Label-1" +
            "</label>" +
            "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>" +
            "<label>" +
            "<input type=\"checkbox\" name=\"test\" value=\"Value-2\" />" +
            "&#160;Label-2" +
            "</label>" +
            "</td>" +
            "<td>" +
            "<label>" +
            "<input type=\"checkbox\" name=\"test\" value=\"Value-3\" />" +
            "&#160;Label-3" +
            "</label>" +
            "</td>" +
            "</tr>" +
            "<tr>" +
            "<td>" +
            "<label>" +
            "<input type=\"checkbox\" name=\"test\" value=\"Value-4\" />" +
            "&#160;Label-4" +
            "</label>" +
            "</td>" +
            "<td>" +
            // empty cell
            "</td>" +
            "</tr>" +
            "</table>";
        assertEquals(expected, htmlMarkup);
    }

    /**
     * Helper method to generate list of selected checkbox
     * values.  The values will be strings in the form
     * "Value-X" where X is the 0-based index of the value.
     * @param numSelected the number of values requested
     * @return the list of selected checkbox values
     */
    protected List getSelections(int numSelected)
    {
        List selectedValues = new ArrayList(numSelected);
        for (int i = 0; i < numSelected; i++)
        {
            selectedValues.add("Value-" + i);
        }
        return selectedValues;
    }

    /**
     * Helper method to generate list of select items that
     * represent the selection of checkboxes.
     * The select items will have labels and values in the form
     * "Label-X", "Value-X" where X is the 0-based index of
     * the label or value.
     * @param numItems the number of checkboxes requested
     * @return the list of checkbox select items
     */
    protected List getSelectManyCheckboxSelections(int numItems)
    {
        List selectItems = new ArrayList(numItems);
        for (int i = 0; i < numItems; i++)
        {
            selectItems.add(new SelectItem("Value-" + i, "Label-" + i));
        }
        return selectItems;
    }

    public void tearDown()
    {
        facesContext = null;
        viewRoot = null;
    }
}
