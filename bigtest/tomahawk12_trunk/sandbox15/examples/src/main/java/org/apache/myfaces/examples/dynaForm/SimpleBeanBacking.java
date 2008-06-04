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
package org.apache.myfaces.examples.dynaForm;

import org.apache.myfaces.examples.dynaForm.lib.Person;
import org.apache.myfaces.examples.dynaForm.lib.PersonRelation;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class SimpleBeanBacking
{
	private PersonRelation personRelation;
    private Person person;
    private List<Person> persons;

    public SimpleBeanBacking()
    {
        initBean();
    }

    protected void initBean()
    {
        person = new Person();
		person.setId(666L);

		Person s1 = new Person();
		s1.setId(4711L);
		s1.setCheckedData(true);
        s1.setCreationDate(new Date());
        s1.setAge(1L);
        s1.setUserName("bean 1");
        Person s2 = new Person();
		s2.setId(815L);
		s2.setCheckedData(true);
        s2.setCreationDate(new Date());
        s2.setAge(2L);
        s2.setUserName("bean 2");

        persons = new ArrayList<Person>();
        persons.add(s1);
        persons.add(s2);

		personRelation = new PersonRelation();
	}

    public Person getPerson()
    {
        return person;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }

    public List<Person> getPersons()
    {
        return persons;
    }

    public void setPersons(List<Person> persons)
    {
        this.persons = persons;
	}

	public PersonRelation getPersonRelation()
	{
		return personRelation;
	}

	public void setPersonRelation(PersonRelation personRelation)
	{
		this.personRelation = personRelation;
	}
}