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

function reloadChildComboBox(componentId, parentValue) {
    	
    var childCombo = document.getElementsByName(componentId)[0];
    var formElement = dojo.dom.getAncestorsByTag(childCombo, "form", true);
    
    dojo.io.bind({
	    formNode: formElement,
        content: {
			affectedAjaxComponent: componentId,
			parentValue: parentValue
	    },
        load: function(type, data, evt){ 
            
            childCombo.options.length = 0;
	    var data = data.getElementsByTagName('option');
            for (i = 0; i < data.length; i++) {
            	var label = data[i].childNodes[0].firstChild.nodeValue;
                var value = data[i].childNodes[1].firstChild.nodeValue;
                var option = new Option(label, value);    	       
                try {
                	childCombo.add(option,null);
		} catch(e) {        
			childCombo.add(option, -1);
		}
            }
        },
        mimetype: "text/xml"
    });

}
