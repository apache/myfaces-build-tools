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
package org.apache.myfaces.lifecycle;

import static org.apache.myfaces.Assert.assertException;
import static org.easymock.EasyMock.*;

import java.util.Locale;

import javax.faces.application.ViewExpiredException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.event.PhaseId;

import org.apache.myfaces.FacesTestCase;
import org.apache.myfaces.TestRunner;

/**
 * @author Mathias Broekelmann (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class RestoreViewExecutorTest extends FacesTestCase
{
    private RestoreViewExecutor _testimpl;
    private ViewHandler _viewHandler;
    private RestoreViewSupport _restoreViewSupport;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        _viewHandler = _mocksControl.createMock(ViewHandler.class);
        _restoreViewSupport = _mocksControl.createMock(RestoreViewSupport.class);
        _testimpl = new RestoreViewExecutor();
        _testimpl.setRestoreViewSupport(_restoreViewSupport);
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.lifecycle.RestoreViewExecutor#execute(javax.faces.context.FacesContext)}.
     */
    public void testExecuteWithExistingViewRoot()
    {
        expect(_facesContext.getApplication()).andReturn(_application).anyTimes();
        expect(_application.getViewHandler()).andReturn(_viewHandler).anyTimes();
        _viewHandler.initView(eq(_facesContext));
        UIViewRoot viewRoot = _mocksControl.createMock(UIViewRoot.class);
        expect(_facesContext.getViewRoot()).andReturn(viewRoot);
        Locale expectedLocale = new Locale("xxx");
        expect(_facesContext.getExternalContext()).andReturn(_externalContext).anyTimes();
        expect(_externalContext.getRequestLocale()).andReturn(expectedLocale);
        viewRoot.setLocale(eq(expectedLocale));
        _restoreViewSupport.processComponentBinding(same(_facesContext), same(viewRoot));

        _mocksControl.replay();
        _testimpl.execute(_facesContext);
        _mocksControl.verify();
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.lifecycle.RestoreViewExecutor#execute(javax.faces.context.FacesContext)}.
     */
    public void testExecuteWOExistingViewRootNoPostBack()
    {
        setupWOExistingViewRoot();
        expect(_restoreViewSupport.isPostback(same(_facesContext))).andReturn(false);
        _facesContext.renderResponse();
        UIViewRoot viewRoot = _mocksControl.createMock(UIViewRoot.class);
        expect(_viewHandler.createView(same(_facesContext), eq("calculatedViewId"))).andReturn(viewRoot);
        _facesContext.setViewRoot(same(viewRoot));

        _mocksControl.replay();
        _testimpl.execute(_facesContext);
        _mocksControl.verify();
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.lifecycle.RestoreViewExecutor#execute(javax.faces.context.FacesContext)}.
     */
    public void testExecuteWOExistingViewRootPostBack()
    {
        setupWOExistingViewRoot();
        expect(_restoreViewSupport.isPostback(same(_facesContext))).andReturn(true);
        UIViewRoot viewRoot = _mocksControl.createMock(UIViewRoot.class);
        expect(_viewHandler.restoreView(same(_facesContext), eq("calculatedViewId"))).andReturn(viewRoot);
        _restoreViewSupport.processComponentBinding(same(_facesContext), same(viewRoot));
        _facesContext.setViewRoot(same(viewRoot));

        _mocksControl.replay();
        _testimpl.execute(_facesContext);
        _mocksControl.verify();
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.lifecycle.RestoreViewExecutor#execute(javax.faces.context.FacesContext)}.
     */
    public void testExecuteWOExistingViewRootPostBackAndViewExpired()
    {
        setupWOExistingViewRoot();
        expect(_restoreViewSupport.isPostback(same(_facesContext))).andReturn(true);
        expect(_viewHandler.restoreView(same(_facesContext), eq("calculatedViewId"))).andReturn(null);

        _mocksControl.replay();
        assertException(ViewExpiredException.class, new TestRunner()
        {
            public void run() throws Throwable
            {
                _testimpl.execute(_facesContext);
            };
        });
        _mocksControl.verify();
    }

    private void setupWOExistingViewRoot()
    {
        expect(_facesContext.getApplication()).andReturn(_application).anyTimes();
        expect(_application.getViewHandler()).andReturn(_viewHandler).anyTimes();
        _viewHandler.initView(eq(_facesContext));
        expect(_facesContext.getViewRoot()).andReturn(null);
        expect(_restoreViewSupport.calculateViewId(eq(_facesContext))).andReturn("calculatedViewId");
    }

    /**
     * Test method for {@link org.apache.myfaces.lifecycle.RestoreViewExecutor#getRestoreViewSupport()}.
     */
    public void testGetRestoreViewSupport() throws Exception
    {
        assertTrue(DefaultRestoreViewSupport.class.equals(new RestoreViewExecutor().getRestoreViewSupport().getClass()));
    }

    /**
     * Test method for {@link org.apache.myfaces.lifecycle.RestoreViewExecutor#getPhase()}.
     */
    public void testGetPhase()
    {
        assertEquals(PhaseId.RESTORE_VIEW, _testimpl.getPhase());
    }

}
