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
package org.apache.myfaces.examples.clientvalidation;

import java.io.Serializable;

/**
 * @author cagatay (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class Customer implements Serializable{

	private Integer _id;
	private String _name;
	private String _surname;
	private Integer _age;
	private String _address;
	private Double _salary;
	private Float _salaryBonus;
	
	public Integer getId() {
		return _id;
	}
	public void setId(Integer _id) {
		this._id = _id;
	}

	public String getName() {
		return _name;
	}
	public void setName(String _name) {
		this._name = _name;
	}
	
	public String getSurname() {
		return _surname;
	}
	public void setSurname(String _surname) {
		this._surname = _surname;
	}
	
	public Integer getAge() {
		return _age;
	}
	public void setAge(Integer _age) {
		this._age = _age;
	}
	
	public String getAddress() {
		return _address;
	}
	public void setAddress(String _address) {
		this._address = _address;
	}
	public Double getSalary() {
		return _salary;
	}
	public void setSalary(Double _salary) {
		this._salary = _salary;
	}
	public Float getSalaryBonus() {
		return _salaryBonus;
	}
	public void setSalaryBonus(Float bonus) {
		_salaryBonus = bonus;
	}
}