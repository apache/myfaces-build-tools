package org.myorganization.validator;

import java.io.StringWriter;

import javax.faces.component.html.HtmlInputText;
import javax.faces.validator.ValidatorException;

import junit.framework.Test;

import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockResponseWriter;

public class OddNumberValidatorTest extends AbstractJsfTestCase
{

    public static Test suite()
    {
        return null;
    }

    private MockResponseWriter writer ;
    private OddNumberValidator oddNumValidator;

    public OddNumberValidatorTest(String name)
    {
        super(name);
    }

    public void setUp()
    {
        super.setUp();

        oddNumValidator = new OddNumberValidator();
        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);
    }

    public void tearDown()
    {
        super.tearDown();
        oddNumValidator = null;
        writer = null;
    }

    public void testOddNumInput()
    {
        boolean noExceptionsThrown = true;
        try 
        {
            oddNumValidator.validate(facesContext, new HtmlInputText(), new Integer(3));
        } 
        catch(ValidatorException ve) 
        {
            noExceptionsThrown = false;
        }
        assertTrue(noExceptionsThrown);
    }
    
    public void testEvenNumInput()
    {
        boolean noExceptionsThrown = true;
        try 
        {
            oddNumValidator.validate(facesContext, new HtmlInputText(), new Integer(8));
        } 
        catch(ValidatorException ve) 
        {
            noExceptionsThrown = false;
        }
        assertFalse(noExceptionsThrown);
    }
    
    
}
