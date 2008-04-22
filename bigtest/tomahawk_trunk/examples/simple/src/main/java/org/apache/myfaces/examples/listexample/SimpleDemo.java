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
package org.apache.myfaces.examples.listexample;

import java.io.Serializable;

/**
 * DOCUMENT ME!
 * @author Thomas Spiegl (latest modification by $Author: werpu $)
 * @version $Revision: 371731 $ $Date: 2006-01-24 01:18:44 +0100 (Di, 24 Jan 2006) $
 */
public class SimpleDemo
        implements Serializable
{
    /**
     * serial id for serialisation versioning
     */
    private static final long serialVersionUID = 1L;
    private int _id;
    private String _value1;
    private String _value2;

    public SimpleDemo(int id, String type, String color)
    {
        _id = id;
        _value1 = type;
        _value2 = color;
    }

    public int getId()
    {
        return _id;
    }

    public void setId(int id)
    {
        _id = id;
    }

    public String getValue1()
    {
        return _value1;
    }

    public void setValue1(String value1)
    {
        _value1 = value1;
    }

    public String getValue2()
    {
        return _value2;
    }

    public void setValue2(String value2)
    {
        _value2 = value2;
    }
}
