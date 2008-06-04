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

package org.apache.myfaces.examples.dateTimeConverter;

import java.util.Date;

/**
 * User: treeder
 * Date: Oct 31, 2005
 * Time: 9:05:17 AM
 */
public class DateTimeConverterBean
{
    private Date date1;
    private Date date2;

    public DateTimeConverterBean()
    {
        date1 = new Date();
        date2 = new Date();
    }

    public Date getDate1()
    {
        return date1;
    }

    public void setDate1(Date date1)
    {
        this.date1 = date1;
    }

    public Date getDate2()
    {
        return date2;
    }

    public void setDate2(Date date2)
    {
        this.date2 = date2;
    }
    public String submit(){
        // do nothing
        return null;
    }
}
