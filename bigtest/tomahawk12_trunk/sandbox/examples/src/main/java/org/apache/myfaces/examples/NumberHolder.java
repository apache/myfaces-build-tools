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

package org.apache.myfaces.examples;

import java.math.BigDecimal;

public class NumberHolder
{
	private short shortNumber;
	private int intNumber;
	private long longNumber;
	private double doubleNumber;
	private BigDecimal bigDecimalNumber;
	private double doubleNumberManual;
	
	public BigDecimal getBigDecimalNumber()
	{
		return bigDecimalNumber;
	}
	public void setBigDecimalNumber(BigDecimal bigDecimalNumber)
	{
		this.bigDecimalNumber = bigDecimalNumber;
	}
	public double getDoubleNumber()
	{
		return doubleNumber;
	}
	public void setDoubleNumber(double doubleNumber)
	{
		this.doubleNumber = doubleNumber;
	}
	public int getIntNumber()
	{
		return intNumber;
	}
	public void setIntNumber(int intNumber)
	{
		this.intNumber = intNumber;
	}
	public long getLongNumber()
	{
		return longNumber;
	}
	public void setLongNumber(long longNumber)
	{
		this.longNumber = longNumber;
	}
	public short getShortNumber()
	{
		return shortNumber;
	}
	public void setShortNumber(short shortNumber)
	{
		this.shortNumber = shortNumber;
	}
	public double getDoubleNumberManual()
	{
		return doubleNumberManual;
	}
	public void setDoubleNumberManual(double doubleNumberManual)
	{
		this.doubleNumberManual = doubleNumberManual;
	}
}
