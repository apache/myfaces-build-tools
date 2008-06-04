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


/*=====================================================
* A timed notifier encapsulation  class
* @author werpu
* this class will enable a timed  notfier
* system within myfaces
======================================================*/
myfaces_TimedNotifier = function (dialogId, confirmButtonId, timeShow, timeHide) {
//    dojo.widget.HtmlWidget.call(this);
    this.dialogId = dialogId;
    /*
	 * notification message timeouts
	 */
    this.timeoutShow = timeShow;
    this.timeoutHide = timeHide;
    /*if not set, set default values*/
    if (this.timeoutShow == null) {
        this.timeoutShow = 0;
    }
    if (this.timeoutHide == null) {
        this.timeoutHide = -1;
    }
    this.dialog = dojo.widget.createWidget("Dialog", {id:(this.dialogId + "_internaldialogid"), bgColor:"white", bgOpacity:0.5, toggle:"fade", toggleDuration:250}, dojo.byId(this.dialogId));
    
    if(confirmButtonId != null)
    	dojo.event.connect(confirmButtonId, "onclick", this, "hideDialog");
    
    /*
	 * timing functions for show and hide
	/*shows the dialog under the given circumstances*/
    this.showDialog = function () {
        this.dialog.show();
        if (this.timeoutHide > 0) {
            dojo.lang.setTimeout(dojo.lang.hitch(this, 'hideDialog'), this.timeoutHide);
        }
    };
    /*
	 * simple hide function
	 */
    this.hideDialog = function () {
        this.dialog.hide();
    };
};

