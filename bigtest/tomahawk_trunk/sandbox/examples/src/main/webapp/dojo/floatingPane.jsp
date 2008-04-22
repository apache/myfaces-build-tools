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
			<h:form>
				<h:outputLink value="#" onclick="toggleWindow();">
						<h:outputText value="[press me for toggle]" />
				</h:outputLink>
			</h:form>
			<h:panelGroup>
				<t:dojoInitializer require="dojo.widget.SplitContainer" />
				<t:dojoInitializer require="dojo.widget.ContentPane" />
				<s:floatingPane id="myfloater" hasShadow="true"
				constrainToContainer="false" resizable="true"
				titleBarDisplay="true"
				title="floatingpane"
				displayCloseAction="true"
				displayMinimizeAction="true"
				widgetVar="floatingFrame"
				>
					<f:verbatim>
					float me 
					over the entire content
					</f:verbatim>
				</s:floatingPane>
				
			</h:panelGroup>

			<f:verbatim>
				<script type="text/javascript">
				<!--
				function toggleWindow(); {
					if(floatingFrame.windowState == "minimized")
						floatingFrame.restoreWindow();
					else
						floatingFrame.minimizeWindow();
				}
				-->
				</script>
			</f:verbatim>
			</h:panelGroup>
		</f:view>
		<%@ include file="../inc/page_footer.jsp"%>
	</body>
</html>
