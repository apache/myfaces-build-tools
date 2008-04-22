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
package org.apache.myfaces.examples.collapsiblepanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

/**
 * @author Martin Marinschek
 * @version $Revision: $ $Date: $
 *          <p/>
 *          $Log: $
 */
public class Person implements Serializable
{

    /**
     * serial id for serialisation
     */
    private static final long serialVersionUID = 1L;
    private String _surName;
    private String _firstName;
    private boolean _collapsed;
    private static Log log = LogFactory.getLog(Person.class);

    public String getSurName()
    {
        return _surName;
    }

    public void setSurName(String surName)
    {
        _surName = surName;
    }

    public String getFirstName()
    {
        return _firstName;
    }

    public void setFirstName(String firstName)
    {
        _firstName = firstName;
    }

    public boolean isCollapsed()
    {
        return _collapsed;
    }

    public void setCollapsed(boolean collapsed)
    {
        _collapsed = collapsed;
    }


    public String test()
    {
     	log.info("test called for " + Person.class + " with name: "+getFirstName());
     	return null;
    }

}
