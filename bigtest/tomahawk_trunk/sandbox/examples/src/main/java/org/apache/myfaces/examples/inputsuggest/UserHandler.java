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
package org.apache.myfaces.examples.inputsuggest;

import java.util.Map;

/**
 * Backing bean designed to handle changes to an inputSuggest field and map it to the
 * {@link StateInfo} associated with a {@link User.}
 *
 * @author Sean Schofield
 * @version $Revision: $ $Date: $
 */
public class UserHandler
{
   private User user;
   private Map choices;

   public void setUser(User user) {
       this.user = user;
   }

   public void setChoices(Map choices) {
       this.choices = choices;
   }

   /**
    * Updates the "datastore" with the user's choice.  This method simluates the process of adding a new
    * choice to the database and setting the user's state info key to the id that the database would
    * produce.
    *
    * @return String
    */
   public String update() {

       String newKey = null;
       String currentKey = (String) user.getState().getKey();
       String currentText = (String) user.getState().getText();
       if (!choices.containsKey(currentKey)) {

           // database code goes here

           // fake key (actual key would probably come from a database function that adds the new value)
           newKey = String.valueOf(choices.size() + 1);

           // Replace the old choice value
           user.setState(new StateInfo(newKey, currentText));
       }

       return "success";
   }
}
