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

package javax.faces.component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.render.Renderer;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.same;
import static org.easymock.classextension.EasyMock.createControl;
import org.easymock.classextension.IMocksControl;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

@SuppressWarnings("deprecation")
public class UIComponentBaseTest
{
    private UIComponentBase _testImpl;

    private IMocksControl _mocksControl;
    private FacesContext _facesContext;
    private Renderer _renderer;

    @BeforeMethod(alwaysRun = true)
    protected void setUp() throws Exception
    {
        _mocksControl = createControl();
        _facesContext = _mocksControl.createMock(FacesContext.class);
        _testImpl = _mocksControl.createMock(UIComponentBase.class, getMockedMethodsArray());
        _renderer = _mocksControl.createMock(Renderer.class);
    }

    protected final Method[] getMockedMethodsArray()
    {
        Collection<Method> mockedMethods = getMockedMethods();
        return mockedMethods.toArray(new Method[mockedMethods.size()]);
    }

    protected Collection<Method> getMockedMethods()
    {
        try
        {
            Collection<Method> methods = new ArrayList<Method>();
            methods.add(UIComponentBase.class.getDeclaredMethod("getRenderer", new Class[] { FacesContext.class }));
            methods.add(UIComponentBase.class.getDeclaredMethod("getFacesContext", null));
            methods.add(UIComponentBase.class.getDeclaredMethod("getParent", null));
            methods.add(UIComponentBase.class
                    .getDeclaredMethod("getPathToComponent", new Class[] { UIComponent.class }));

            return methods;
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /*
     * Test method for 'javax.faces.component.UIComponentBase.getAttributes()'
     */
    @Test
    public void testGetAttributes()
    {
        // TODO implement tests for _ComponentAttributesMap
        assertTrue(_testImpl.getAttributes() instanceof _ComponentAttributesMap);
    }

    @Test
    public void testGetRendersChildren()
    {
        assertGetRendersChildren(false, null);
        assertGetRendersChildren(true, _renderer);
        assertGetRendersChildren(false, _renderer);
    }

    private void assertGetRendersChildren(boolean expectedValue, Renderer renderer)
    {
        expect(_testImpl.getFacesContext()).andReturn(_facesContext);
        expect(_testImpl.getRenderer(same(_facesContext))).andReturn(renderer);
        if (renderer != null)
            expect(renderer.getRendersChildren()).andReturn(expectedValue);
        _mocksControl.replay();
        assertEquals(expectedValue, _testImpl.getRendersChildren());
        _mocksControl.verify();
        _mocksControl.reset();
    }

    @Test
    public void testGetChildCount() throws Exception
    {
        assertEquals(0, _testImpl.getChildCount());
        UIComponent child = _mocksControl.createMock(UIComponent.class);
        List<UIComponent> children = _testImpl.getChildren();
        expect(child.getParent()).andReturn(null);
        child.setParent(same(_testImpl));
        _mocksControl.replay();
        children.add(child);
        assertEquals(1, _testImpl.getChildCount());
        _mocksControl.reset();
        child.setParent((UIComponent) isNull());
        _mocksControl.replay();
        children.remove(child);
        assertEquals(0, _testImpl.getChildCount());
    }

    @Test(expectedExceptions = { NullPointerException.class })
    public void testBroadcastArgNPE() throws Exception
    {
        _testImpl.broadcast(null);
    }

    @Test
    public void testBroadcast() throws Exception
    {
        FacesEvent event = _mocksControl.createMock(FacesEvent.class);
        _testImpl.broadcast(event);

        FacesListener listener1 = _mocksControl.createMock(FacesListener.class);
        FacesListener listener2 = _mocksControl.createMock(FacesListener.class);
        _testImpl.addFacesListener(listener1);
        _testImpl.addFacesListener(listener2);

        expect(event.isAppropriateListener(same(listener1))).andReturn(false);
        expect(event.isAppropriateListener(same(listener2))).andReturn(true);
        event.processListener(same(listener2));

        _mocksControl.replay();
        _testImpl.broadcast(event);
        _mocksControl.verify();
    }

    @Test(expectedExceptions = { NullPointerException.class })
    public void testDecodeArgNPE() throws Exception
    {
        _testImpl.decode(null);
    }

    @Test
    public void testDecode() throws Exception
    {
        expect(_testImpl.getRenderer(same(_facesContext))).andReturn(_renderer);
        _renderer.decode(same(_facesContext), same(_testImpl));
        _mocksControl.replay();
        _testImpl.decode(_facesContext);
        _mocksControl.verify();
    }

    @Test(expectedExceptions = { NullPointerException.class })
    public void testEncodeBeginArgNPE() throws Exception
    {
        _testImpl.encodeBegin(null);
    }

    @Test
    public void testEncodeBegin() throws Exception
    {
        _testImpl.setRendered(false);
        _mocksControl.replay();
        _testImpl.encodeBegin(_facesContext);

        _mocksControl.reset();
        _testImpl.setRendered(true);
        expect(_testImpl.getRenderer(same(_facesContext))).andReturn(_renderer);
        _renderer.encodeBegin(same(_facesContext), same(_testImpl));
        _mocksControl.replay();
        _testImpl.encodeBegin(_facesContext);
        _mocksControl.verify();
    }

    @Test(expectedExceptions = { NullPointerException.class })
    public void testEncodeChildrenArgNPE() throws Exception
    {
        _testImpl.encodeChildren(null);
    }

    @Test
    public void testEncodeChildren() throws Exception
    {
        _testImpl.setRendered(false);
        _mocksControl.replay();
        _testImpl.encodeChildren(_facesContext);

        _mocksControl.reset();
        _testImpl.setRendered(true);
        expect(_testImpl.getRenderer(same(_facesContext))).andReturn(_renderer);
        _renderer.encodeChildren(same(_facesContext), same(_testImpl));
        _mocksControl.replay();
        _testImpl.encodeChildren(_facesContext);
        _mocksControl.verify();
    }

    @Test(expectedExceptions = { NullPointerException.class })
    public void testEncodeEndArgNPE() throws Exception
    {
        _testImpl.encodeEnd(null);
    }

    @Test
    public void testEncodeEnd() throws Exception
    {
        _testImpl.setRendered(false);
        _mocksControl.replay();
        _testImpl.encodeEnd(_facesContext);

        _mocksControl.reset();
        _testImpl.setRendered(true);
        expect(_testImpl.getRenderer(same(_facesContext))).andReturn(_renderer);
        _renderer.encodeEnd(same(_facesContext), same(_testImpl));
        _mocksControl.replay();
        _testImpl.encodeEnd(_facesContext);
        _mocksControl.verify();
    }

    @Test(expectedExceptions = { NullPointerException.class })
    public void testQueueEventArgNPE() throws Exception
    {
        _testImpl.queueEvent(null);
    }

    @Test(expectedExceptions = { IllegalStateException.class })
    public void testQueueEventWithoutParent() throws Exception
    {
        FacesEvent event = _mocksControl.createMock(FacesEvent.class);
        expect(_testImpl.getParent()).andReturn(null);
        _mocksControl.replay();
        _testImpl.queueEvent(event);
    }

    @Test
    public void testQueueEvent() throws Exception
    {
        FacesEvent event = _mocksControl.createMock(FacesEvent.class);
        UIComponent parent = _mocksControl.createMock(UIComponent.class);
        expect(_testImpl.getParent()).andReturn(parent);
        parent.queueEvent(same(event));
        _mocksControl.replay();
        _testImpl.queueEvent(event);
        _mocksControl.verify();
    }

    @Test(expectedExceptions = { NullPointerException.class })
    public void testProcessDecodesArgNPE() throws Exception
    {
        _testImpl.processDecodes(null);
    }

    @Test(expectedExceptions = { RuntimeException.class })
    public void testProcessDecodesCallsRenderResoponseIfDecodeThrowsException()
    {
        expect(_testImpl.getFacetsAndChildren()).andReturn(Collections.EMPTY_LIST.iterator());
        _testImpl.decode(same(_facesContext));
        expectLastCall().andThrow(new RuntimeException());
        _facesContext.renderResponse();
        _mocksControl.replay();
        try
        {
            _testImpl.processDecodes(_facesContext);
        }
        finally
        {
            _mocksControl.verify();
        }
    }

    @Test
    public void testProcessDecodesWithRenderedFalse() throws Exception
    {
        _testImpl.setRendered(false);
        _mocksControl.replay();
        _testImpl.processDecodes(_facesContext);
    }

    @Test
    public void testProcessDecodesWithRenderedTrue() throws Exception
    {
        Collection<Method> methods = getMockedMethods();
        methods.add(UIComponentBase.class.getDeclaredMethod("getFacetsAndChildren", null));
        methods.add(UIComponentBase.class.getDeclaredMethod("decode", new Class[] { FacesContext.class }));
        _testImpl = _mocksControl.createMock(UIComponentBase.class, methods.toArray(new Method[methods.size()]));
        UIComponent child = _mocksControl.createMock(UIComponent.class);
        expect(_testImpl.getFacetsAndChildren()).andReturn(Arrays.asList(new UIComponent[] { child }).iterator());
        child.processDecodes(same(_facesContext));
        _testImpl.decode(same(_facesContext));
        _mocksControl.replay();
        _testImpl.processDecodes(_facesContext);
        _mocksControl.verify();
    }

    @Test(expectedExceptions = { NullPointerException.class })
    public void testProcessValidatorsArgNPE() throws Exception
    {
        _testImpl.processValidators(null);
    }

    @Test
    public void testProcessValidatorsWithRenderedFalse() throws Exception
    {
        _testImpl.setRendered(false);
        _mocksControl.replay();
        _testImpl.processValidators(_facesContext);
    }

    @Test
    public void testProcessValidatorsWithRenderedTrue() throws Exception
    {
        UIComponent child = setupProcessXYZTest();
        child.processValidators(same(_facesContext));
        _mocksControl.replay();
        _testImpl.processValidators(_facesContext);
        _mocksControl.verify();
    }

    private UIComponent setupProcessXYZTest() throws Exception
    {
        Collection<Method> methods = getMockedMethods();
        methods.add(UIComponentBase.class.getDeclaredMethod("getFacetsAndChildren", null));
        _testImpl = _mocksControl.createMock(UIComponentBase.class, methods.toArray(new Method[methods.size()]));
        UIComponent child = _mocksControl.createMock(UIComponent.class);
        expect(_testImpl.getFacetsAndChildren()).andReturn(Arrays.asList(new UIComponent[] { child }).iterator());
        return child;
    }

    @Test(expectedExceptions = { NullPointerException.class })
    public void testProcessUpdatesArgNPE() throws Exception
    {
        _testImpl.processUpdates(null);
    }

    @Test
    public void testProcessUpdatesWithRenderedFalse() throws Exception
    {
        _testImpl.setRendered(false);
        _mocksControl.replay();
        _testImpl.processUpdates(_facesContext);
    }

    @Test
    public void testProcessUpdatesWithRenderedTrue() throws Exception
    {
        UIComponent child = setupProcessXYZTest();
        child.processUpdates(same(_facesContext));
        _mocksControl.replay();
        _testImpl.processUpdates(_facesContext);
        _mocksControl.verify();
    }

    @Factory
    public Object[] createPropertyTests() throws Exception
    {
        return new Object[] {
                new AbstractUIComponentPropertyTest<Boolean>("rendered", true, new Boolean[] { false, true })
                {
                    @Override
                    protected UIComponent createComponent()
                    {
                        return getMocksControl().createMock(UIComponentBase.class, new Method[0]);
                    }
                }, new AbstractUIComponentPropertyTest<String>("rendererType", null, new String[] { "xyz", "abc" })
                {
                    @Override
                    protected UIComponent createComponent()
                    {
                        return getMocksControl().createMock(UIComponentBase.class, new Method[0]);
                    }
                } };
    }
}
