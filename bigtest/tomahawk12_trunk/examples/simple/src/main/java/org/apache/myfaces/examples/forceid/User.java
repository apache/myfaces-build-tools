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
package org.apache.myfaces.examples.forceid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author <a href="mailto:matzew@apache.org">Matthias We&szlig;endorf</a> 
 */
public class User {
    
    public String username,password;
    private static final Log log = LogFactory.getLog(User.class);
    
    public User(){}
    
    public User(String username,String password){
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String update(){
        if(log.isDebugEnabled()){
            log.debug("USERNAME: "+this.username);
            log.debug("PASSWORD: "+this.password);
        }
        return ("go_forceId");
    }
}
