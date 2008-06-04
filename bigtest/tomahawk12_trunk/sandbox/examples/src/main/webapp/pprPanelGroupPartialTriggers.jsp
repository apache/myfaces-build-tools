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
<h1>PPR Examples - using partialTriggers</h1>
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

        <t:messages id="messages" showDetail="true" showSummary="true" layout="list"/>

        <h:panelGrid columns="2">
            <f:facet name="header">
                <h:outputText value="Examples showing the pprPanelGroup in action"/>
            </f:facet>
            <h:outputText value="Enter the value for update:" />
			<h:inputText id="textField" 
                    value="#{pprExampleBean.textField}" required="true" />
            
			<h:outputText value="Entered text will appear here:" />
			<s:pprPanelGroup id="ppr1" appendMessages="messages"
				partialTriggers="pprSubmitButton">
				<h:outputText value="#{pprExampleBean.textField}" />
			</s:pprPanelGroup>

            <h:outputText value="partial update button:" />
			<h:commandButton id="pprSubmitButton" value="PPR Submit" />

        </h:panelGrid>

        <h:panelGrid columns="2">
            <f:facet name="header">
                <h:outputText value="Crosstest - normal submits should still work"/>
            </f:facet>
            <h:outputText value="normal submit button:" />
            <h:commandButton id="normalSubmitButton" value="Normal Submit"/>

            <h:outputText value="normal submit button going home:" />
            <h:commandButton id="normalSubmitButtonGoingHome" value="Normal Submit going home" action="home"/>

            <h:outputText value="normal update link:" />
            <h:commandLink id="normalLink" value="Normal Submit with Link"/>

            <h:outputText value="normal update link going home:" />
            <h:commandLink id="normalLinkGoingHome" value="Normal Submit with Link going home" action="home"/>
                        
        </h:panelGrid>
        <s:fieldset legend="about this example">
            <f:verbatim>
                <br />
                <br />
                The partial Triggers Attribute of the PPRPanelGroup contains  <br />
                a comma separated list of component ids. These Component <br />
                ids should reference input components like the commandButton   <br />
                in this example. When any of the referenced input components <br />
                is activated ( clicked or submission using an onChange-Event Handler <br />
                which would normally cause a submit ) an AJAX request is performed  <br />
                which updates all components within the pprPanelGroup  <br />
            </f:verbatim>
        </s:fieldset>
    </h:form>

</f:view>

<%@include file="inc/page_footer.jsp"%>

</body>

</html>



