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
package org.apache.myfaces.examples.conversation;

public class WizardData
{
	// page 1
	private String salutation;
	private String title;
	private String name;
	private String surename;
	
	// page 2
	private String street;
	private String city;
	private String state;
	private String province;
	private String postal;
	private String country;
	
	// page 3
	private String info;
	
	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public String getCountry()
	{
		return country;
	}
	public void setCountry(String country)
	{
		this.country = country;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getPostal()
	{
		return postal;
	}
	public void setPostal(String postal)
	{
		this.postal = postal;
	}
	public String getProvince()
	{
		return province;
	}
	public void setProvince(String province)
	{
		this.province = province;
	}
	public String getSalutation()
	{
		return salutation;
	}
	public void setSalutation(String salutation)
	{
		this.salutation = salutation;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	public String getStreet()
	{
		return street;
	}
	public void setStreet(String street)
	{
		this.street = street;
	}
	public String getSurename()
	{
		return surename;
	}
	public void setSurename(String surename)
	{
		this.surename = surename;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getInfo()
	{
		return info;
	}
	public void setInfo(String info)
	{
		this.info = info;
	}
}
