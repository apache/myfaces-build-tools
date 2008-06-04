package org.apache.myfaces.custom.validators;

import javax.faces.component.UIComponent;
import javax.faces.validator.Validator;

import org.apache.shale.test.jmock.AbstractJmockJsfTestCase;
import org.jmock.Mock;

public abstract class AbstractValidatorTestCase extends AbstractJmockJsfTestCase
{

  public AbstractValidatorTestCase(String arg0) {
    super(arg0);
  }
  
  Mock mockComponent;
  UIComponent component;
  
  protected void setUp() throws Exception
  {
    super.setUp();
    mockComponent = mock(UIComponent.class);
    component = (UIComponent) mockComponent.proxy();
    
  }

  protected void tearDown() throws Exception
  {
    super.tearDown();
  }


  /**
   * if contex or component = null then should throw NullPointerException
   */
  protected void doTestNullContext(
    UIComponent component,
    Validator validator) throws NullPointerException
  {
    try
    {
      validator.validate(null, component, "dummy");
      fail("Expected NullpointerException - if context or component is null");
    }
    catch (NullPointerException npe)
    {
      // this is expected
    }
  }
}