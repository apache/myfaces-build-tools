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
package org.apache.myfaces.examples.listexample;

import java.io.Serializable;

/**
 * @author MBroekelmann
 *
 */
public class SimpleCity implements Serializable
{  
    /**
     * serial id for serialisation versioning
     */
    private static final long serialVersionUID = 1L;
    private String mName;

    private boolean selected;
    
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void unselect(){
		setSelected(false);
	}
	

	/**
	 * 
	 */
	public SimpleCity(String name)
	{
		mName = name;
	}

	/**
	 * 
	 */
	public SimpleCity()
	{
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return mName;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		mName = name;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return getName();
	}
}
