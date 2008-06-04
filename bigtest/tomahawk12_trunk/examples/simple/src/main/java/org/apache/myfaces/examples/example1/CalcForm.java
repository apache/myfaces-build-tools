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
import java.math.BigDecimal;

/**
 * DOCUMENT ME!
 * @author Manfred Geiler
 * @version $Revision$ $Date$
 */
public class CalcForm
    implements Serializable
{
    /**
     * serial id for serialisation versioning
     */
    private static final long serialVersionUID = 1L;
    private BigDecimal number1 = new BigDecimal(0d);
    private BigDecimal number2 = new BigDecimal(0d);
    private BigDecimal result = new BigDecimal(0d);

    public void add()
    {
        result = number1.add(number2);
    }

    public void subtract()
    {
        result = number1.subtract(number2);
    }

    public BigDecimal getNumber1()
    {
        return number1;
    }

    public void setNumber1(BigDecimal number1)
    {
        this.number1 = number1;
    }

    public BigDecimal getNumber2()
    {
        return number2;
    }

    public void setNumber2(BigDecimal number2)
    {
        this.number2 = number2;
    }

    public BigDecimal getResult()
    {
        return result;
    }

    public void setResult(BigDecimal result)
    {
        this.result = result;
    }

}
