package javax.faces.component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.easymock.EasyMock;
import static org.easymock.EasyMock.*;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: mathias
 * Date: 18.03.2007
 * Time: 01:14:31
 * To change this template use File | Settings | File Templates.
 */
public class UIComponentBaseProcessSaveRestoreStateTest extends AbstractUIComponentBaseTest
{
    private static final String CHILD_STATE = "childState";
    private static final String TESTIMPL_STATE = "testimplState";
    private static final String FACET_STATE = "facetState";
    private UIComponent _facet;
    private UIComponent _child;

    @Override
    @BeforeMethod(alwaysRun = true)
    protected void setUp() throws Exception
    {
        super.setUp();
        _facet = _mocksControl.createMock(UIComponent.class);
        _child = _mocksControl.createMock(UIComponent.class);
    }

    @Override
    protected Collection<Method> getMockedMethods() throws Exception
    {
        Collection<Method> methods = super.getMockedMethods();
        methods.add(UIComponentBase.class.getDeclaredMethod("getFacets", null));
        methods.add(UIComponentBase.class.getDeclaredMethod("getChildren", null));
        methods.add(UIComponentBase.class.getDeclaredMethod("getFacetCount", null));
        methods.add(UIComponentBase.class.getDeclaredMethod("getChildCount", null));
        methods.add(UIComponentBase.class.getDeclaredMethod("saveState", new Class[]{FacesContext.class}));
        methods.add(UIComponentBase.class.getDeclaredMethod("restoreState", new Class[]{FacesContext.class,
                Object.class}));
        return methods;
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void testSaveStateExpections() throws Exception
    {
        _testImpl.processSaveState(null);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void testRestoreStateExpections() throws Exception
    {
        _testImpl.processRestoreState(null, null);
    }

    @Test
    public void testSaveRestoreStateWithTransientChilds() throws Exception
    {
        _testImpl.setTransient(true);
        assertNull(_testImpl.processSaveState(_facesContext));

        _testImpl.setTransient(false);
        setUpChilds(true, true, true);
        _mocksControl.replay();
        Object state = _testImpl.processSaveState(_facesContext);
        assertNotNull(state);
        _mocksControl.verify();

        _mocksControl.reset();
        _testImpl.restoreState(EasyMock.same(_facesContext), EasyMock.eq(TESTIMPL_STATE));
        _mocksControl.replay();
        _testImpl.processRestoreState(_facesContext, state);
        _mocksControl.verify();
    }

    @Test
    public void testSaveRestoreState() throws Exception
    {
        _testImpl.setTransient(true);
        assertNull(_testImpl.processSaveState(_facesContext));

        _testImpl.setTransient(false);
        setUpChilds(true, false, false);
        _mocksControl.replay();
        Object state = _testImpl.processSaveState(_facesContext);
        assertNotNull(state);
        _mocksControl.verify();

        _mocksControl.reset();
        setUpChilds(false, false, false);
        _mocksControl.replay();
        _testImpl.processRestoreState(_facesContext, state);
        _mocksControl.verify();
    }

    private void setUpChilds(boolean saveState, boolean facetTransient, boolean childTransient)
    {
        if (saveState || !facetTransient)
        {
            Map<String, UIComponent> facetMap = new HashMap<String, UIComponent>();
            facetMap.put("testFacet", _facet);
            expect(_testImpl.getFacetCount()).andReturn(1).anyTimes();
            expect(_testImpl.getFacets()).andReturn(facetMap).anyTimes();
            expect(_facet.isTransient()).andReturn(facetTransient).anyTimes();
        }
        if (!facetTransient)
        {
            if (saveState)
                expect(_facet.processSaveState(EasyMock.same(_facesContext))).andReturn(FACET_STATE);
            else
                _facet.processRestoreState(EasyMock.same(_facesContext), EasyMock.eq(FACET_STATE));
        }
        if (saveState || !childTransient)
        {
            List<UIComponent> childs = new ArrayList<UIComponent>();
            childs.add(_child);
            expect(_testImpl.getChildCount()).andReturn(1).anyTimes();
            expect(_testImpl.getChildren()).andReturn(childs).anyTimes();
            expect(_child.isTransient()).andReturn(childTransient).anyTimes();
        }
        if (!childTransient)
        {
            if (saveState)
                expect(_child.processSaveState(EasyMock.same(_facesContext))).andReturn(CHILD_STATE);
            else
                _child.processRestoreState(EasyMock.same(_facesContext), EasyMock.eq(CHILD_STATE));
        }
        if (saveState)
            expect(_testImpl.saveState(EasyMock.same(_facesContext))).andReturn(TESTIMPL_STATE);
        else
            _testImpl.restoreState(EasyMock.same(_facesContext), EasyMock.eq(TESTIMPL_STATE));
    }
}
