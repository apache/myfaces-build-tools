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
package org.apache.myfaces.config.impl.digester.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 */
public class MapEntries extends ElementBaseImpl implements org.apache.myfaces.config.element.MapEntries
{

    private String keyClass;
    private String valueClass;
    private List entries = new ArrayList();


    public String getKeyClass()
    {
        return keyClass;
    }


    public void setKeyClass(String keyClass)
    {
        this.keyClass = keyClass;
    }


    public String getValueClass()
    {
        return valueClass;
    }


    public void setValueClass(String valueClass)
    {
        this.valueClass = valueClass;
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
    }


    public Iterator getMapEntries()
    {
        return entries.iterator();
    }


    public static class Entry extends ElementBaseImpl implements org.apache.myfaces.config.element.MapEntry {
       String key;
        boolean nullValue = false;
        String value;


        public String getKey()
        {
            return key;
        }


        public void setKey(String key)
        {
            this.key = key;
        }


        public boolean isNullValue()
        {
            return nullValue;
        }


        public void setNullValue()
        {
            this.nullValue = true;
        }


        public String getValue()
        {
            return value;
        }


        public void setValue(String value)
        {
            this.value = value;
        }
    }
}
