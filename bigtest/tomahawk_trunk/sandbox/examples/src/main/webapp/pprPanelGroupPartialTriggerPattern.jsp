<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

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

<html>

<%@ include file="inc/head.inc"%>


<body>
<h1>PPR Example - update components which ids match a regular expression</h1>
<span id="cnt">0</span>
seconds since last page refresh.
<script>
    var sec=0;
    function counter(){
        setTimeout("counter();",1000);
        document.getElementById("cnt").innerHTML = sec++;
    }
    counter();
</script>

<f:view>

	<h:form id="mainform">

		<h:panelGrid columns="2">
			<h:outputText value="Enter the value for update:" />
			<h:inputText value="#{pprExampleBean.textField}" />
			
			<h:outputText value="partial update button:" />
			<h:commandButton value="button 1" />

            <h:outputText value="partial update button:" />
			<h:commandButton value="button 2" />


			<h:outputText value="PPRPanelGroup:" />
			<s:pprPanelGroup id="ppr"
				partialTriggerPattern="mainform:.*">
				<h:outputText value="#{pprExampleBean.textField}" />
			</s:pprPanelGroup>
		</h:panelGrid>
	</h:form>
    <s:fieldset legend="about this example">
         <f:verbatim>
             <br />
             <br />
            The partialTriggerPattern attribute takes a regular expression  <br />
            (JavaScript regular expression). All input components which     <br />
             clientIds match this regular expression trigger AJAX updates   <br />
             of this pprPanelGroup. <br />
             <br />
             In this example all buttons sit within the form with the id  <br />
             -mainform-. Therefore all clientIds of this buttons          <br />
             will start with -mainform:-. (A client id is the HTML-ID     <br />
             attribute the HTML-element rendered by a component has set)  <br />
             Therefore the clientIds of all buttons match the pattern specified <br />
             in the pprPanelGroup and so all of them trigger AJAX updates  <br />
             of the group.
        </f:verbatim>
    </s:fieldset>
</f:view>

<%@include file="inc/page_footer.jsp"%>

</body>

</html>

