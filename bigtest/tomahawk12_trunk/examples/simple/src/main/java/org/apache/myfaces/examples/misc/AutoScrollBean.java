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

package org.apache.myfaces.examples.misc;

import java.util.List;
import java.util.ArrayList;

/**
 * Bean to demonstrate the org.apache.myfaces.AUTO_SCROLL init parameter behaviour
 *
 * @author Bruno Aranda (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AutoScrollBean
{

    private List numbers;

    public AutoScrollBean()
    {
        this.numbers = new ArrayList(100);

        for (int i=0; i<100; i++)
        {
            numbers.add(String.valueOf(i));
        }
    }

    public List getNumbers()
    {
        return numbers;
    }

}
