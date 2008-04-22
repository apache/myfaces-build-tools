<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<html>
	<head>
		<meta HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=UTF-8" />
		<title>MyFaces - the free JSF Implementation</title>

		<link rel="stylesheet" type="text/css" href="css/basic.css" />
		<style>
		body {
			font-family: Arial, Helvetica, sans-serif;
			padding: 0;
			margin: 0;
		}

		.page {
			padding: 20px 20px 20px 20px;
		}

		#outerbar {
			padding: 10px;
			color: white;
			background-color: #667;
			text-align: center;
			position: relative;
			left: 0px;
			top: 0px;
			width: 100%;
			/* height: 30px; */
		}
  </STYLE>
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
				<f:verbatim>
					<script language="JavaScript" type="text/javascript">
				
						var built = false;
						function buildEditor(){
							if(built){ 
								return;
								// built.onClose(false, true);
							}
							var groups = [];
							var ob = document.getElementById("outerbar");
							var eb = document.getElementById("scriptEditorBlock");
							var inputs = ob.getElementsByTagName("input");
							for(var x=0; x<inputs.length; x++){
								if((inputs[x].type == "checkbox")&&(inputs[x].checked)){
									groups.push(inputs[x].name);
									groups.push("|");
								}
							}
							if(groups.length){
								groups.pop();
							}
							
							built = dojo.widget.createWidget("Editor", { items: groups }, eb);
							dojo.fx.wipeOut(ob, 750);
						}
					</script>
				</f:verbatim>




				<t:dojoInitializer require="dojo.fx.*"/>	
				<t:dojoInitializer require="dojo.widget.Editor"/>	

								
				<h:outputText value="Dojo import tests, you now can setup dojo by using the s:dojoInitializer component." />
				<h:outputText value="this component provides you with the DjConfig flags and also allows you to import via require" />
				

				<f:verbatim>
					<div id="outerbar">
						<input type="checkbox" name="commandGroup">
						Save/Cancel
						<input type="checkbox" checked name="blockGroup">
						Block Formatting + Fonts
						<input type="checkbox" checked name="textGroup">
						Text Formatting
						<input type="checkbox" name="listGroup">
						Lists
						<input type="checkbox" checked name="justifyGroup">
						Justification
						<input type="checkbox" name="indentGroup">
						Indention
						<input type="checkbox" name="linkGroup">
						Links and Images
						<input type="checkbox" name="colorGroup">
						Colors
						<input type="button" onclick="buildEditor();" value="Build It!">
					</div>

					<div class="page" id="scriptEditorBlock">
						<h3>
							Select Your Toolbar Items And Then Edit This!
						</h3>
						<p>
							Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nam facilisis enim. Pellentesque in elit et lacus euismod dignissim. Aliquam dolor pede, convallis eget, dictum a, blandit ac, urna. Pellentesque sed nunc ut justo volutpat egestas. Class
							aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. In erat. Suspendisse potenti. Fusce faucibus nibh sed nisi. Phasellus faucibus, dui a cursus dapibus, mauris nulla euismod velit, a lobortis turpis arcu vel dui.
							Pellentesque fermentum ultrices pede. Donec auctor lectus eu arcu. Curabitur non orci eget est porta gravida. Aliquam pretium orci id nisi. Duis faucibus, mi non adipiscing venenatis, erat urna aliquet elit, eu fringilla lacus tellus quis erat. Nam
							tempus ornare lorem. Nullam feugiat.
						</p>
					</div>

					<div class="page dojo-Editor">
						<h3>
							Editable Content With Default Toolbar
						</h3>
						<ul>
							<li>
								Sed congue.
							</li>
							<li>
								Aenean blandit sollicitudin mi.
							</li>
							<li>
								Maecenas pellentesque.
							</li>
							<li>
								Vivamus ac urna.
							</li>
						</ul>
						<p>
							Nunc consequat nisi vitae quam. Suspendisse sed nunc. Proin suscipit porta magna. Duis accumsan nunc in velit. Nam et nibh. Nulla facilisi. Cras venenatis urna et magna. Aenean magna mauris, bibendum sit amet, semper quis, aliquet nec, sapien.
							Aliquam aliquam odio quis erat. Etiam est nisi, condimentum non, lacinia ac, vehicula laoreet, elit. Sed interdum augue sit amet quam dapibus semper. Nulla facilisi. Pellentesque lobortis erat nec quam.
						</p>
						<p>
							Sed arcu magna, molestie at, fringilla in, sodales eu, elit. Curabitur mattis lorem et est. Quisque et tortor. Integer bibendum vulputate odio. Nam nec ipsum. Vestibulum mollis eros feugiat augue. Integer fermentum odio lobortis odio. Nullam mollis
							nisl non metus. Maecenas nec nunc eget pede ultrices blandit. Ut non purus ut elit convallis eleifend. Fusce tincidunt, justo quis tempus euismod, magna nulla viverra libero, sit amet lacinia odio diam id risus. Ut varius viverra turpis. Morbi urna
							elit, imperdiet eu, porta ac, pharetra sed, nisi. Etiam ante libero, ultrices ac, faucibus ac, cursus sodales, nisl. Praesent nisl sem, fermentum eu, consequat quis, varius interdum, nulla. Donec neque tortor, sollicitudin sed, consequat nec,
							facilisis sit amet, orci. Aenean ut eros sit amet ante pharetra interdum.
						</p>
						<p>
							Fusce rutrum pede eget quam. Praesent purus. Aenean at elit in sem volutpat facilisis. Nunc est augue, commodo at, pretium a, fermentum at, quam. Nam sit amet enim. Suspendisse potenti. Cras hendrerit rhoncus justo. Integer libero. Cum sociis
							natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam erat volutpat. Sed adipiscing mi vel ipsum.
						</p>
						<p>
							Sed aliquam, quam consectetuer condimentum bibendum, neque libero commodo metus, non consectetuer magna risus vitae eros. Pellentesque mollis augue id libero. Morbi nonummy hendrerit dui. Morbi nisi felis, fringilla ac, euismod vitae, dictum mollis,
							pede. Integer suscipit, est sed posuere ullamcorper, ipsum lectus interdum nunc, quis blandit erat eros hendrerit pede. Vestibulum varius, elit id mattis mattis, nulla est feugiat ante, eget vestibulum augue eros ut odio. Maecenas euismod purus quis
							felis. Ut hendrerit tincidunt est. Fusce euismod, nunc eu tempus tempor, purus ligula volutpat tellus, nec lacinia sapien enim id risus. Aliquam orci turpis, condimentum sed, sollicitudin vel, placerat in, purus. Proin tortor nisl, blandit quis,
							imperdiet quis, scelerisque at, nisl. Maecenas suscipit fringilla erat. Curabitur consequat, dui blandit suscipit dictum, felis lectus imperdiet tellus, sit amet ornare risus mauris non ipsum. Fusce a purus. Vestibulum sodales. Sed porta ultrices
							nibh. Vestibulum metus.
						</p>
					</div>

				</f:verbatim>
			</h:panelGroup>
		</f:view>

		<%@include file="../inc/page_footer.jsp"%>

	</body>

</html>

