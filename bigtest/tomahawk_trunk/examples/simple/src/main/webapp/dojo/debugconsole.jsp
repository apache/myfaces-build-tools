<%@ page session="false" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>

<html>
<head>
    <meta HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=UTF-8"/>
    <title>MyFaces - the free JSF Implementation</title>

    <link rel="stylesheet" type="text/css" href="css/basic.css"/>

</head>

<!--
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
//-->


<body>
<f:view>
    <h:panelGroup>
        <t:dojoInitializer require="dojo.widget.Editor" debug="true"/>
        <t:dojoInitializer require="dojo.widget.DebugConsole"/>
        <t:dojoInitializer require="dojo.widget.ResizeHandle"/>
        <t:dojoInitializer require="dojo.widget.Button"/>
        <f:verbatim>
            <script type="text/javascript">
                function generateSomeDebuggingInfo() {
                    for (var x = 0; x < 10; x++) {
                        dojo.debug(x + ": Here is some debugging info, should be 10 new lines");
                    }
                }
            </script>

            <p>Debugging Console. This widget, once loaded, will have djConfig output all debugging information to its
                floating pane. Some debugging information won't go to the debugConsole before it loads, but there isn't
                much that can be done about that. If you set djConfig = {isDebug:true} it will log to the bottom of the
                screen (or some div if you set that) until the debugConsole finishes loading.</p>
            <button id="go" class="dojo-button" onClick="generateSomeDebuggingInfo();">Generate Debugging info
            </button>

            <div dojoType="DebugConsole"
                 title="Debug Console"
                 iconSrc="images/flatScreen.gif"
                 constrainToContainer="1"
                 style="width: 700px; height: 500px; left: 200px;"
                 hasShadow="true"
                 resizable="true"
                 displayCloseAction="true"
                 layoutAlign="client"
                >
            </div>


        </f:verbatim>
    </h:panelGroup>

</f:view>
<%@ include file="../inc/page_footer.jsp" %>
</body>
</html>
