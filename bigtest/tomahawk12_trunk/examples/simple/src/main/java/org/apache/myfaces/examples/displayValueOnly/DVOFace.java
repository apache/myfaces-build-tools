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
package org.apache.myfaces.examples.displayValueOnly;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date: 2005-03-24 12:47:11 -0400 (Thu, 24 Mar 2005) $
 */
public class DVOFace implements Serializable
{

    /**
     * serial id for serialisation versioning
     */
    private static final long serialVersionUID = 1L;

    private boolean attribute = true;
    
    private Map map = new HashMap(){

        /**
         * serial id for serialisation versioning
         */
        private static final long serialVersionUID = 1L;

        public Object get(Object key){
    		Object held = super.get( key );
    		if( held != null )
    			return held;
    		if( key.toString().toLowerCase().indexOf("list")>0 )
    			return new ArrayList();
    		
    		return null;	
    	}
    };

	public boolean isAttribute() {
		return attribute;
	}
	public void setAttribute(boolean attribute) {
		this.attribute = attribute;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
}