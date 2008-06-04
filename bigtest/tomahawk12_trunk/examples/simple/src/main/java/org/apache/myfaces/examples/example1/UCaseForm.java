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
package org.apache.myfaces.examples.example1;

import java.io.Serializable;

/**
 * DOCUMENT ME!
 * @author Manfred Geiler
 * @version $Revision$ $Date$
 */
public class UCaseForm
        implements Serializable
{  /**
     * serial id for serialisation versioning
     */
    private static final long serialVersionUID = 1L;
    private String text = "";

    public void uppercase()
    {
        text = text.toUpperCase();
    }

    public void lowercase()
    {
        text = text.toLowerCase();
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * Test method for method binding.
     */
    public String jumpHome()
    {
        System.out.println("JumpHome Action was called.");
        return "jump_home";
    }

}
