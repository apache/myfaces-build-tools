/*
 * Copyright 2004-2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.faces.convert;

import javax.el.ValueExpression;
import javax.faces.component.html.HtmlInputText;

import junit.framework.Test;

import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.el.MockValueExpression;

public class MessageUtilsTest extends AbstractJsfTestCase {
	
	public MessageUtilsTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
    protected void tearDown() throws Exception {
        super.tearDown();
    }

	public void testGetLabelFromAttributesMap() {
		HtmlInputText inputText = new HtmlInputText();
		inputText.getAttributes().put("label", "testLabel");
		String label = _MessageUtils.getLabel(facesContext, inputText);
		assertEquals("testLabel", label);
	}
	
	public void testGetLabelFromValueExpression() {
		facesContext.getExternalContext().getRequestMap().put("lbl", "testLabel");
		HtmlInputText inputText = new HtmlInputText();
		ValueExpression expression = new MockValueExpression("#{requestScope.lbl}",String.class);
		inputText.setValueExpression("label", expression);
		
		String label = _MessageUtils.getLabel(facesContext, inputText);
		assertEquals("testLabel", label);
	}
	
	public void testGetLabelReturnsClientIdWhenLabelIsNotSpecified() {
		HtmlInputText inputText = new HtmlInputText();
		inputText.setId("testId");
		String label = _MessageUtils.getLabel(facesContext, inputText);
		assertEquals("testId", label);
	}
}