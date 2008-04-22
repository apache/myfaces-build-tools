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
package org.apache.myfaces.custom.dynaForm.guiBuilder;

import java.util.Iterator;
import java.util.Map;

import org.apache.myfaces.custom.dynaForm.metadata.FieldInterface;
import org.apache.myfaces.custom.dynaForm.metadata.MetaDataInterface;

/**
 * The slipstream will bring the configuration-metaData/gui together 
 */
public class Slipstream
{
	private boolean displayOnly;
	private MetaDataInterface modelMetaData;
	private GuiBuilder guiBuilder;
	private Map labelBundle;
	
	public Slipstream()
	{
	}

	public boolean isDisplayOnly()
	{
		return displayOnly;
	}

	public void setDisplayOnly(boolean displayOnly)
	{
		this.displayOnly = displayOnly;
	}
	
	public MetaDataInterface getModelMetaData()
	{
		return modelMetaData;
	}

	public void setModelMetaData(MetaDataInterface modelMetaData)
	{
		this.modelMetaData = modelMetaData;
	}

	public GuiBuilder getGuiBuilder()
	{
		return guiBuilder;
	}

	public void setGuiBuilder(GuiBuilder guiBuilder)
	{
		this.guiBuilder = guiBuilder;
	}
	
	public Map getLabelBundle()
	{
		return labelBundle;
	}

	public void setLabelBundle(Map labelBundle)
	{
		this.labelBundle = labelBundle;
	}

	public void process()
	{
		configureGuiBuilder();
		
		MetaDataInterface metaData = modelMetaData;
		Iterator<String> iterFieldNames = metaData.iterFieldNames();
		while (iterFieldNames.hasNext())
		{
			String fieldName = iterFieldNames.next();
			FieldInterface field = modelMetaData.getField(fieldName);
			
			guiBuilder.buildField(field);
		}
	}

	protected void configureGuiBuilder()
	{
		guiBuilder.setDisplayOnly(isDisplayOnly());
		guiBuilder.setLabelBundle(getLabelBundle());
	}

}
