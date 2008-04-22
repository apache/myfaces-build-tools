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

org_apache_myfaces_registerTagsWithFocus(hiddenClientId)
{
    dojo.event.connect(window, "onload", function(evt) {
      var elementsArr = new dojo.collections.ArrayList();
      elementsArr.addRange(document.getElementsByTagName("INPUT"));
      elementsArr.addRange(document.getElementsByTagName("SELECT"));
      elementsArr.addRange(document.getElementsByTagName("TEXTAREA"));
      
      for(var i=0;i<elementsArr.count;i++)
      {
        var elem = elementsArr.item(i);
        dojo.event.connect(elem,"onfocus",function(evt)
          {
            document.getElementById(hiddenClientId).value=evt.target.getAttribute('id');
          }
        );
      }
    });
}