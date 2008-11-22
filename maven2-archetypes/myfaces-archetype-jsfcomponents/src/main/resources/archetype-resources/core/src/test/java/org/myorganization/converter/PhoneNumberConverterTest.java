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

    public void setUp() throws Exception
    {
        super.setUp();

        phoneNumConverter = new PhoneNumberConverter();
        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);
    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        phoneNumConverter = null;
        writer = null;
    }

    public void testNumOnly() throws Exception
    {
        Object obj = phoneNumConverter.getAsObject(facesContext, new HtmlInputText(), "6373824");
        assertNotNull(obj);
        PhoneNumber phoneNum = (PhoneNumber) obj;
        assertEquals(phoneNum.getNumber(), "6373824");
    }
    
    public void testAreaCodeNum() throws Exception
    {
        Object obj = phoneNumConverter.getAsObject(facesContext, new HtmlInputText(), "09-6373824");
        assertNotNull(obj);
        PhoneNumber phoneNum = (PhoneNumber) obj;
        assertEquals(phoneNum.getAreaCode(), "09");
        assertEquals(phoneNum.getNumber(), "6373824");
    }
}
