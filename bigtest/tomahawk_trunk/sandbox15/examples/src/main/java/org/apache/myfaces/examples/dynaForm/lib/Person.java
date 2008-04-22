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

import org.apache.myfaces.custom.dynaForm.annot.ui.DisplayOnly;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable
{
	private Long id;

	private String userName;
    private long age;
    private Date creationDate;
    private Date birthday;
    private MartialStatus martialStatus;
    private String description;
    private boolean checkedData;

	@DisplayOnly
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public long getAge()
    {
        return age;
    }

    public void setAge(long age)
    {
        this.age = age;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    @Temporal(value=TemporalType.DATE)
    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public MartialStatus getMartialStatus()
    {
        return martialStatus;
    }

    public void setMartialStatus(MartialStatus martialStatus)
    {
        this.martialStatus = martialStatus;
    }

    public boolean isCheckedData()
    {
        return checkedData;
    }

    public void setCheckedData(boolean checkedData)
    {
        this.checkedData = checkedData;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

	@Override
	public boolean equals(Object o)
	{
		Person p = (Person) o;

		return id.equals(p.id);
	}
}
