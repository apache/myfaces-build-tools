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
<h1>PPR Examples - using multiple pprPanelGroups and triggering Components</h1>
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
			
			<h:outputText value="Update panelGroup 1:" />
			<h:commandButton id="pprSubmitButton1" value="update" />

            <h:outputText value="Update panelGroup 2:" />
			<h:commandButton id="pprSubmitButton2" value="update" />

            <h:outputText value="Update both panelGroups:" />
			<h:commandButton id="pprSubmitButtonBoth" value="update" />

            <h:outputText value="PPRPanelGroup 1:" />
			<s:pprPanelGroup id="ppr1"
				partialTriggers="pprSubmitButton1,pprSubmitButtonBoth">
				<h:outputText value="#{pprExampleBean.textField}" />
			</s:pprPanelGroup>
            <h:outputText value="PPRPanelGroup 2:" />
			<s:pprPanelGroup id="ppr2"
				partialTriggers="pprSubmitButton2,pprSubmitButtonBoth">
				<h:outputText value="#{pprExampleBean.textField}" />
			</s:pprPanelGroup>
        </h:panelGrid>
        <s:fieldset legend="about this example">
            <f:verbatim>
                <br />
                <br />
                The partial Triggers Attribute of the PPRPanelGroup contains <br />
                a comma separated list of component ids. This example shows <br />
                how multiple partialTriggers can be used in more than one <br />
                pprPanelGroup.
            </f:verbatim>
        </s:fieldset>
    </h:form>

</f:view>

<%@include file="inc/page_footer.jsp"%>

</body>

</html>

