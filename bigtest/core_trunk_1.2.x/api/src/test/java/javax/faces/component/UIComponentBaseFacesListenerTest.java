package javax.faces.component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.faces.event.ActionListener;
import javax.faces.event.FacesListener;
import javax.faces.event.ValueChangeListener;

import static org.testng.Assert.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: mathias
 * Date: 18.03.2007
 * Time: 01:16:50
 * To change this template use File | Settings | File Templates.
 */
public class UIComponentBaseFacesListenerTest extends AbstractUIComponentBaseTest
{
    private ActionListener _actionListener;
    private ValueChangeListener _valueChangeListener;

    @Override
    @BeforeMethod
    protected void setUp() throws Exception
    {
        super.setUp();
        _actionListener = _mocksControl.createMock(ActionListener.class);
        _valueChangeListener = _mocksControl.createMock(ValueChangeListener.class);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void testArgNPE() throws Exception
    {
        _testImpl.getFacesListeners(null);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testInvalidListenerType() throws Exception
    {
        _testImpl.getFacesListeners(Date.class);
    }

    @Test
    public void testEmptyListener() throws Exception
    {
        FacesListener[] listener = _testImpl.getFacesListeners(ActionListener.class);
        assertNotNull(listener);
        assertEquals(0, listener.length);
    }

    @Test
    public void testGetFacesListeners()
    {
        _testImpl.addFacesListener(_actionListener);

        FacesListener[] listener = _testImpl.getFacesListeners(ValueChangeListener.class);
        assertNotNull(listener);
        assertEquals(0, listener.length);
        assertTrue(ValueChangeListener.class.equals(listener.getClass().getComponentType()));

        _testImpl.addFacesListener(_valueChangeListener);

        listener = _testImpl.getFacesListeners(FacesListener.class);
        assertNotNull(listener);
        assertEquals(2, listener.length);
        Collection<FacesListener> col = Arrays.asList(listener);
        assertTrue(col.contains(_actionListener));
        assertTrue(col.contains(_valueChangeListener));
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void testRemoveFacesListenerArgNPE() throws Exception
    {
        _testImpl.removeFacesListener(null);
    }

    @Test
    public void testRemoveFacesListener() throws Exception
    {
        _testImpl.addFacesListener(_actionListener);
        assertEquals(_actionListener, _testImpl.getFacesListeners(FacesListener.class)[0]);
        _testImpl.removeFacesListener(_actionListener);
        assertEquals(0, _testImpl.getFacesListeners(FacesListener.class).length);
    }
}
