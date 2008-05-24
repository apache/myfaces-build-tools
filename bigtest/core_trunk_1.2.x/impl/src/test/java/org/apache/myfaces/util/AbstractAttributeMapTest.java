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
package org.apache.myfaces.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * @author Mathias Broekelmann (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AbstractAttributeMapTest extends TestCase
{
    private TestAttributeMap _testimpl;

    @Override
    protected void setUp() throws Exception
    {
        Map map = new HashMap();
        map.put("key", "value");
        _testimpl = new TestAttributeMap(map);
    }
    
    /**
     * Test method for {@link java.util.AbstractMap#hashCode()}.
     */
    public void testHashCodeEquals()
    {
        assertEquals(_testimpl.hashCode(), _testimpl.hashCode());
    }

    public void testValues() throws Exception
    {
        _testimpl.put("myKey", "myValue");
        assertTrue(_testimpl.values().contains("myValue"));
    }

    private static final class TestAttributeMap extends AbstractAttributeMap
    {
        private final Map _values;

        public TestAttributeMap(Map values)
        {
            _values = values;
        }

        @Override
        protected Object getAttribute(String key)
        {
            return _values.get(key);
        }

        @Override
        protected Enumeration getAttributeNames()
        {
            return new IteratorEnumeration(_values.keySet().iterator());
        }

        @Override
        protected void removeAttribute(String key)
        {
            _values.remove(key);
        }

        @Override
        protected void setAttribute(String key, Object value)
        {
            _values.put(key, value);
        }
    }

}
