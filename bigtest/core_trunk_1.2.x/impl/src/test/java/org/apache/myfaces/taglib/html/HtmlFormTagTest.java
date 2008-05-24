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
package org.apache.myfaces.taglib.html;

import javax.faces.component.html.HtmlForm;

import org.apache.myfaces.el.LiteralValueExpression;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Mathias Broekelmann (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlFormTagTest extends AbstractJsfTestCase
{
    /**
     * @param name
     */
    public HtmlFormTagTest(String name)
    {
        super(name);
    }

    @Override
    @BeforeMethod
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    @Test
    public void testPrependId() throws Exception
    {
        HtmlFormTag htmlFormTag = new HtmlFormTag();
        htmlFormTag.setPrependId(new LiteralValueExpression(false));
        HtmlForm htmlForm = new HtmlForm();
        htmlFormTag.setProperties(htmlForm);
        Assert.assertEquals(false, htmlForm.isPrependId());
    }
}
