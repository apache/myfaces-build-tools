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

package org.apache.myfaces.custom.validators;

import javax.faces.component.UIInput;
import javax.faces.validator.ValidatorException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.custom.regexprvalidator.RegExprValidator;

public class RegExprValidatorTestCase extends AbstractValidatorTestCase
{

  public RegExprValidatorTestCase(String arg0) {
    super(arg0);
  }
  
  RegExprValidator validator;
  
  protected void setUp() throws Exception
  {
    super.setUp();
    validator = new RegExprValidator();
    
  }

  protected void tearDown() throws Exception
  {
    super.tearDown();
  }

  public static Test suite()
  {
    return new TestSuite(RegExprValidatorTestCase.class);
  }
  
  /**
   * Test when context is set to null
   */
  public void testNullContext()
  {

    doTestNullContext(component, validator);
  }
  
  public void testRightValue()
  {
    validator.setPattern("\\d{5}");
    
    UIInput comp1 = new UIInput();
    comp1.setValue("12345");
    comp1.setId("comp1");
    facesContext.getViewRoot().getChildren().add(comp1);
    
    validator.validate(facesContext, comp1, comp1.getValue());

  }

  public void testWrongValue()
  {
    try
    {
      validator.setPattern("\\d{12}");
      
      UIInput comp1 = new UIInput();
      comp1.setValue("12345");
      comp1.setId("comp1");
      facesContext.getViewRoot().getChildren().add(comp1);
      
      validator.validate(facesContext, comp1, comp1.getValue());
      
      fail("Expected ValidatorException");
    }
    catch (ValidatorException ve)
    {
      // if exception then fine.
    }

  }

  
}
