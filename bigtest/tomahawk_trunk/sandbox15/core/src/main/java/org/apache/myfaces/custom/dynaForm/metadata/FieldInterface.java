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
package org.apache.myfaces.custom.dynaForm.metadata;

import javax.faces.component.UIComponent;
import javax.persistence.TemporalType;

import org.apache.myfaces.custom.dynaForm.guiBuilder.ComponentEnum;
import org.apache.myfaces.custom.dynaForm.lib.SelectionSourceEnum;

public interface FieldInterface
{
	public String getName();
	public String getBaseName();
	public String getExternalName();
	public Class getType();
	public boolean isEntityType();
	public Boolean getDisabled();
	public Boolean getCanRead();
	public Boolean getCanWrite();
	public Boolean getDisplayOnly();
	public boolean getRequired();
	public Selection[] getAllowedSelections();
	public RelationType getRelationType();

	/**
	 * in case of hierarchical structures this defines if a child class should be treatened
	 * as "embedded".<br />
	 * <ul>
	 * <li>Embedded: like a composite key in hibernate</li>
	 * <li>Not Embedded: like a relation to another entity (ManyToOne)</li>
	 * </ul>
	 * This is not a metadata for the field itself, but for the context in which this field
	 * (or its entity) will be used
	 */
	public boolean isEmbedded();

	public Integer getMaxSize();
	public Double getMaxValue();
	public Integer getMinSize();
	public Integer getDisplaySize();
	public Double getMinValue();
	public UIComponent getWantedComponent();
	public ComponentEnum getWantedComponentType();
	public TemporalType getTemporalType();
	public boolean getAllowMultipleSelections();
	public SelectionSourceEnum getSelectionSource();

	public String getDataSource();
	public String getDataSourceDescription();
	public String getConverterId();
	public Class getConverterClass();
}