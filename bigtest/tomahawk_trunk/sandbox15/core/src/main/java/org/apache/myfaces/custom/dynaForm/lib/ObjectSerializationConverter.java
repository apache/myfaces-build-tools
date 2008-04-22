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
package org.apache.myfaces.custom.dynaForm.lib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;


import org.apache.commons.codec.binary.Base64;
import org.apache.myfaces.custom.dynaForm.lib.NullObject;

/**
 * This converter will be used to e.g. render a selection menu. <br />
 * It is responsible to convert a object to a string-identifier representation with which it is possible to
 * recreate the object again - aka serialization 
 */
public class ObjectSerializationConverter implements Converter
{
	public static final NullObject SELECT_NULL_OBJECT = new NullObject();
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
	{
		if (value == null || value.length() < 1 || SELECT_NULL_OBJECT.equals(value))
		{
			return null;
		}

		ObjectInputStream ois = null;
		Serializable objectIdent;
		try
		{
			byte[] base64 = Base64.decodeBase64(value.getBytes("UTF-8"));
			ois = new ObjectInputStream(new ByteArrayInputStream(base64));
			objectIdent = (Serializable) ois.readObject();
			ois.close();
		}
		catch (IOException e)
		{
			throw new ConverterException(e);
		}
		catch (ClassNotFoundException e)
		{
			throw new ConverterException(e);
		}
		finally
		{
			if (ois != null)
			{
				try
				{
					ois.close();
				}
				catch (IOException e)
				{
					// consume exception, should never happen
				}
			}
		}
		
		return objectIdent;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
	{
		if (value == null || SELECT_NULL_OBJECT.equals(value))
		{
			return "";
		}
		
		ObjectOutputStream oos = null;
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1024); 
			oos = new ObjectOutputStream(baos);
			oos.writeObject(value);
			byte[] base64 = Base64.encodeBase64(baos.toByteArray());
			String objectString = new String(base64, "UTF-8");
			return objectString;
		}
		catch (IOException e)
		{
			throw new ConverterException(e);
		}
		finally
		{
			if (oos != null)
			{
				try
				{
					oos.close();
				}
				catch (IOException e)
				{
					// consume exception, should never happen
				}
			}
		}
	}
}
