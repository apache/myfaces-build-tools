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
package org.apache.myfaces.examples.dynaForm.lib;

import org.apache.myfaces.custom.dynaForm.annot.ui.NotNull;
import org.apache.myfaces.custom.dynaForm.annot.ui.DataProvider;
import org.apache.myfaces.custom.dynaForm.annot.ui.UIComponent;
import org.apache.myfaces.custom.dynaForm.guiBuilder.ComponentEnum;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.io.Serializable;

public class PersonRelation implements Serializable
{
	private Person person1;
	private Person person2;
	private Date liveTogetherSince;

	@NotNull
	@ManyToOne
	@DataProvider(
		value="#{personProvider.getSearchPersons}",
		description="#{personProvider.getPersonDescription}"
	)
	public Person getPerson1()
	{
		return person1;
	}

	public void setPerson1(Person person1)
	{
		this.person1 = person1;
	}

	@NotNull
	@ManyToOne
	@UIComponent(type= ComponentEnum.SelectOneMenu)
	@DataProvider(
		value="#{personProvider.getSearchPersons}",
		description="#{personProvider.getPersonDescription}"
	)
	public Person getPerson2()
	{
		return person2;
	}

	public void setPerson2(Person person2)
	{
		this.person2 = person2;
	}

	@Temporal(value=TemporalType.DATE)
	public Date getLiveTogetherSince()
	{
		return liveTogetherSince;
	}

	public void setLiveTogetherSince(Date liveTogetherSince)
	{
		this.liveTogetherSince = liveTogetherSince;
	}
}
