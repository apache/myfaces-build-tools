package org.apache.myfaces.custom.validators;

import javax.faces.component.UIInput;
import javax.faces.validator.ValidatorException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.custom.equalvalidator.EqualValidator;

public class EqualValidatorTestCase extends AbstractValidatorTestCase
{

  public EqualValidatorTestCase(String arg0) {
    super(arg0);
  }
  
  EqualValidator equalValidator;
  
  protected void setUp() throws Exception
  {
    super.setUp();
    equalValidator = new EqualValidator();
    
  }

  protected void tearDown() throws Exception
  {
    super.tearDown();
  }

  public static Test suite()
  {
    return new TestSuite(EqualValidatorTestCase.class);
  }
  
  /**
   * Test when context is set to null
   */
  public void testNullContext()
  {

    doTestNullContext(component, equalValidator);
  }
  
  public void testRightValue()
  {
    equalValidator.setFor("comp1");
    
    UIInput comp1 = new UIInput();
    comp1.setValue("HANS");
    comp1.setId("comp1");
    facesContext.getViewRoot().getChildren().add(comp1);
    
    
    UIInput comp2 = new UIInput ();
    comp2.setId("comp2");
    facesContext.getViewRoot().getChildren().add(comp2);

    equalValidator.validate(facesContext, comp2, "HANS");

  }

  public void testWrongValue()
  {
    try
    {
      equalValidator.setFor("comp1");
      
      UIInput comp1 = new UIInput();
      comp1.setValue("HANS");
      comp1.setId("comp1");
      facesContext.getViewRoot().getChildren().add(comp1);
      
      
      UIInput comp2 = new UIInput ();
      comp2.setId("comp2");
      facesContext.getViewRoot().getChildren().add(comp2);

      equalValidator.validate(facesContext, comp2, "BUUBA");
      
      fail("Expected ValidatorException");
    }
    catch (ValidatorException ve)
    {
      // if exception then fine.
    }

  }

}
