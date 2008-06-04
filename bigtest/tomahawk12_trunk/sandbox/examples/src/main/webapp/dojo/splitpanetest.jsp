<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>

<html>
	<head>
		<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=UTF-8" />
		<title>MyFaces - the free JSF Implementation</title>

		<link rel="stylesheet" type="text/css" href="css/basic.css" />
		<style type="text/css">
    html, body{	
		width: 100%;	/* make the body expand to fill the visible window */
		height: 100%;
		overflow: hidden;	/* erase window level scrollbars */
		padding: 0 0 0 0;
		margin: 0 0 0 0;
    }
	.dojoSplitPane{
		margin: 5px;
	}
   </style>
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
				<t:dojoInitializer require="dojo.widget.SplitContainer" />
				<t:dojoInitializer require="dojo.widget.ContentPane" />

				
					<t:div id="origSplitContainer" forceId="true"
						style="width: 100%; height: 100%; background: #eeeeee; padding: 10px;">
						<t:div id="leftcontainer"  style="overflow: auto;">
						   <f:verbatim>
								I'm a content pane, and I'm the first child of the
								SplitContainer, so I appear on the left. My initial size is 20%,
								but if you adjust it and come back to the demo, my parent (the
								SplitContainer) will remember the size you set. On my right is a
								split container. (The parent split container contains a child
								split container.)
								
								This test is now done in a mixed jsf, html environment.
							</f:verbatim>
						</t:div>

						<t:div id="rightPane" forceId="true">
							<t:div id="topcontainer" forceId="true">
								<h:outputFormat value="This is the top part of the inner split container."></h:outputFormat>
							</t:div>
							<t:div id="bottomcontainer" forceId="true">
								<h:outputFormat value="...and this is the bottom."></h:outputFormat>
							</t:div>
						</t:div>
					</t:div>
				<f:verbatim>	
					<script type="text/javascript">
					<!--
						var container = dojo.widget.createWidget("SplitContainer", {id:"mycontainer", orientation:"horizontal", sizerWidth:5,
						activeSizing:0}, dojo.byId("origSplitContainer"));
						
						var leftcontainer = dojo.widget.createWidget("ContentPane", {id:"myleftcontainer", sizeShare:20}, dojo.byId("leftcontainer"));
						
						var rightPane = dojo.widget.createWidget("SplitContainer", {id:"myrightpane", orientation:"vertical", sizerWidth:5,
						activeSizing:0}, dojo.byId("rightPane"));
					
						var topcontainer = dojo.widget.createWidget("ContentPane", {id:"mytopcontainer", sizeShare:50}, dojo.byId("topcontainer"));
						var bottomcontainer = dojo.widget.createWidget("ContentPane", {id:"mybottomcontainer", sizeShare:50}, dojo.byId("bottomcontainer"));
					
					
						container.addChild(rightPane);
						container.addChild(leftcontainer);
					
						rightPane.addChild(bottomcontainer);
						rightPane.addChild(topcontainer);
					//-->
					</script>
				</f:verbatim>
			</h:panelGroup>

		</f:view>
		<%@ include file="../inc/page_footer.jsp"%>
	</body>
</html>
