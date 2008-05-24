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
package org.apache.myfaces.el;

import static org.easymock.EasyMock.*;
import static org.testng.AssertJUnit.*;

import java.beans.FeatureDescriptor;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.el.ELContext;
import javax.el.ELResolver;

import org.easymock.IAnswer;
import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Mathias Broekelmann (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CompositeELResolverTest
{
    private IMocksControl _mocksControl;
    private ELContext _elContext;
    private CompositeELResolver _testImpl;

    @BeforeMethod
    public void setup()
    {
        _mocksControl = EasyMock.createControl();
        _elContext = _mocksControl.createMock(ELContext.class);
        _testImpl = new CompositeELResolver();
    }

    @Test
    public void testFeatureDescriptorsIterator()
    {
        ELResolver notnullFDResolver = _mocksControl.createMock(ELResolver.class);
        ELResolver emptyFDResolver = _mocksControl.createMock(ELResolver.class);
        Object base = new Object();

        _testImpl.add(notnullFDResolver);
        _testImpl.add(emptyFDResolver);
        _testImpl.add(notnullFDResolver);

        final List<FeatureDescriptor> fds = new ArrayList<FeatureDescriptor>();
        fds.add(new FeatureDescriptor());
        fds.add(null);
        fds.add(new FeatureDescriptor());
        fds.add(null);
        expect(notnullFDResolver.getFeatureDescriptors(eq(_elContext), eq(base))).andAnswer(
                new IAnswer<Iterator<FeatureDescriptor>>()
                {
                    public Iterator<FeatureDescriptor> answer() throws Throwable
                    {
                        return fds.iterator();
                    }
                }).anyTimes();

        expect(emptyFDResolver.getFeatureDescriptors(eq(_elContext), eq(base))).andReturn(
                Collections.EMPTY_LIST.iterator());

        _mocksControl.replay();

        Iterator<FeatureDescriptor> descriptors = _testImpl.getFeatureDescriptors(_elContext, base);

        assertNotNull(descriptors);
        assertEquals(true, descriptors.hasNext());
        assertEquals(fds.get(0), descriptors.next());
        assertEquals(true, descriptors.hasNext());
        assertEquals(fds.get(2), descriptors.next());
        assertEquals(true, descriptors.hasNext());
        assertEquals(fds.get(0), descriptors.next());
        assertEquals(true, descriptors.hasNext());
        assertEquals(fds.get(2), descriptors.next());
        assertEquals(false, descriptors.hasNext());

    }
}
