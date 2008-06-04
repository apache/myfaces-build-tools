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
package org.apache.myfaces.custom.dynaForm.metadata.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class TypeInfos
{
	private final static Map<Class, Info> INFOS = new HashMap<Class, Info>(10);

	public static class Info
	{
		/**
		 * min possible value
		 */
		private final Double minValue;

		/**
		 * max possible value
		 */
		private final Double maxValue;

		/**
		 * display length, -1 means unknown
		 */
		private final int length;

		/**
		 * do this type has a fractional part
		 */
		private final boolean hasFractional;

		/**
		 * if this is a number
		 */
		private final boolean number;

		private Info(boolean number, Double minValue, Double maxValue, boolean hasFractional)
		{
			if (minValue != null)
			{
				int length = String.valueOf(minValue).length();
				if (!hasFractional)
				{
					length=length-2; // strip off the .0 part after string conversion
				}
				this.length = length;
			}
			else
			{
				length = -1;
			}
			this.minValue = minValue;
			this.maxValue = maxValue;
			this.hasFractional = hasFractional;
			this.number = number;
		}

		public int getLength()
		{
			return length;
		}

		public Double getMaxValue()
		{
			return maxValue;
		}

		public Double getMinValue()
		{
			return minValue;
		}

		public boolean isHasFractional()
		{
			return hasFractional;
		}

		public boolean isNumber()
		{
			return number;
		}
	}

	static
	{
		addInfo(new Info(true, (double) Byte.MIN_VALUE, (double) Byte.MAX_VALUE, false), Byte.class, Byte.TYPE);
		addInfo(new Info(true, (double) Short.MIN_VALUE, (double) Short.MAX_VALUE, false), Short.class, Short.TYPE);
		addInfo(new Info(true, (double) Integer.MIN_VALUE, (double) Integer.MAX_VALUE, false), Integer.class, Integer.TYPE);
		addInfo(new Info(true, (double) Long.MIN_VALUE, (double) Long.MAX_VALUE, false), Long.class, Long.TYPE);
		addInfo(new Info(true, null, null, false), BigInteger.class);
		addInfo(new Info(true, null, (double) Float.MAX_VALUE, true), Float.class, Float.TYPE);
		addInfo(new Info(true, null, Double.MAX_VALUE, true), Double.class, Double.TYPE);
		addInfo(new Info(true, null, null, true), BigDecimal.class);
		addInfo(new Info(false, null, null, false), String.class);
	}

	private TypeInfos()
	{
	}

	private static void addInfo(Info info, Class ... types)
	{
		for (Class type : types)
		{
			INFOS.put(type, info);
		}
	}

	public static Info getInfo(Class type)
	{
		return INFOS.get(type);
	}
}
