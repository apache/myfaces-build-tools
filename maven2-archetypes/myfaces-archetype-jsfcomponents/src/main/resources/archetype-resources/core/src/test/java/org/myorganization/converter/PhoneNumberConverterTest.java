package org.myorganization.converter;

import java.io.StringWriter;

import javax.faces.component.html.HtmlInputText;

import junit.framework.Test;

import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockResponseWriter;

public class PhoneNumberConverterTest extends AbstractJsfTestCase
{
    public static Test suite()
    {
        return null;
    }

    private MockResponseWriter writer ;
    private PhoneNumberConverter phoneNumConverter;

    public PhoneNumberConverterTest(String name)
    {
        super(name);
    }

    public void setUp()
    {
        super.setUp();

        phoneNumConverter = new PhoneNumberConverter();
        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);
    }

    public void tearDown()
    {
        super.tearDown();
        phoneNumConverter = null;
        writer = null;
    }

    public void testNumOnly()
    {
        Object obj = phoneNumConverter.getAsObject(facesContext, new HtmlInputText(), "6373824");
        assertNotNull(obj);
        PhoneNumber phoneNum = (PhoneNumber) obj;
        assertEquals(phoneNum.getNumber(), "6373824");
    }
    
    public void testAreaCodeNum()
    {
        Object obj = phoneNumConverter.getAsObject(facesContext, new HtmlInputText(), "09-6373824");
        assertNotNull(obj);
        PhoneNumber phoneNum = (PhoneNumber) obj;
        assertEquals(phoneNum.getAreaCode(), "09");
        assertEquals(phoneNum.getNumber(), "6373824");
    }
}
