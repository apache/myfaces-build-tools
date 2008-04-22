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
package org.apache.myfaces.custom.dynaForm.metadata.impl.ejb;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.myfaces.custom.dynaForm.lib.DynaFormException;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.BasicType;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;

/**
 * Extracts fields/method of the given class using BCEL. <br />
 * This allows us to keep the ordering.
 */
public class BcelHelper implements ClassHelper
{
	public Field[] getFields(Class clazz)
	{
		JavaClass javaClass = Repository.lookupClass(clazz);
		org.apache.bcel.classfile.Field[] fields = javaClass.getFields();
		
		List<Field> ret = new ArrayList<Field>(fields.length);
		for (org.apache.bcel.classfile.Field field : fields)
		{
			try
			{
				ret.add(clazz.getDeclaredField(field.getName()));
			}
			catch (SecurityException e)
			{
				throw new DynaFormException(e);
			}
			catch (NoSuchFieldException e)
			{
				throw new DynaFormException(e);
			}
		}
		return ret.toArray(new Field[ret.size()]);
	}

	public Method[] getMethods(Class clazz)
	{
		JavaClass javaClass = Repository.lookupClass(clazz);
		org.apache.bcel.classfile.Method[] methods = javaClass.getMethods();
		
		List<Method> ret = new ArrayList<Method>(methods.length);
		for (org.apache.bcel.classfile.Method method : methods)
		{
			if ("<init>".equals(method.getName()))
			{
				continue;
			}
			if (!method.getName().startsWith("set") && !method.getName().startsWith("get") && !method.getName().startsWith("is"))
			{
				continue;
			}
			
			Type[] types = method.getArgumentTypes();
			Class[] args = new Class[types.length];
			for (int i = 0; i<types.length; i++)
			{
				args[i] = type2Class(types[i]);
			}
			
			try
			{
				ret.add(clazz.getDeclaredMethod(method.getName(), args));
			}
			catch (SecurityException e)
			{
				throw new DynaFormException(e);
			}
			catch (NoSuchMethodException e)
			{
				throw new DynaFormException(e);
			}
		}
		return ret.toArray(new Method[ret.size()]);
	}

	protected Class type2Class(Type type)
	{
		if (type instanceof BasicType)
		{
			BasicType basicType = (BasicType) type;
			if (basicType.getType() == BasicType.BOOLEAN.getType())
			{
				return boolean.class;
			}
			else if (basicType.getType() == BasicType.BYTE.getType())
			{
				return byte.class;
			}
			else if (basicType.getType() == BasicType.CHAR.getType())
			{
				return char.class;
			}
			else if (basicType.getType() == BasicType.DOUBLE.getType())
			{
				return double.class;
			}
			else if (basicType.getType() == BasicType.FLOAT.getType())
			{
				return float.class;
			}
			else if (basicType.getType() == BasicType.INT.getType())
			{
				return int.class;
			}
			else if (basicType.getType() == BasicType.LONG.getType())
			{
				return long.class;
			}
			else if (basicType.getType() == BasicType.SHORT.getType())
			{
				return short.class;
			}
			else if (basicType.getType() == BasicType.STRING.getType())
			{
				return String.class;
			}
			else if (basicType.getType() == BasicType.VOID.getType())
			{
				return void.class;
			}
			throw new IllegalArgumentException("dont know how to map " + basicType);
		}
		else if (type instanceof ObjectType)
		{
			ObjectType objectType = (ObjectType) type;
			try
			{
				return Class.forName(objectType.getClassName());
			}
			catch (ClassNotFoundException e)
			{
				throw new DynaFormException(e);
			}
		}
		else if (type instanceof ArrayType)
		{
			Class elementType = type2Class(((ArrayType) type).getElementType());
			return Array.newInstance(elementType, 0).getClass();
		}
		
		throw new IllegalArgumentException("unkown type " + type);
	}

}
