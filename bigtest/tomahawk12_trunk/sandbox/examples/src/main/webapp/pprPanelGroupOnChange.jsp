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
<h1>PPR Example - using PPR with drop-down and checkbox onChange handlers</h1>
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
        <t:commandLink />
		<h:panelGrid columns="2">

			<h:outputText value="update group by checking:" />
			<h:selectBooleanCheckbox id="checkbox" immediate="true"
                    value="#{pprExampleBean.checkBoxValue}" valueChangeListener="#{pprExampleBean.checkBoxChanged}" required="true"/>
			
			<h:outputText value="update group by changing the value:" />
			<h:selectOneMenu id="dropDown" immediate="true"
				value="#{pprExampleBean.dropDownValue}"	valueChangeListener="#{pprExampleBean.dropDownChanged}" required="true">
				<f:selectItem itemLabel="test1" itemValue="value1"/>
				<f:selectItem itemLabel="test2" itemValue="value2"/>
				<f:selectItem itemLabel="test3" itemValue="value3"/>
			</h:selectOneMenu>

			<h:outputText value="update group by changing the value:" />
            <h:inputText id="inputText" immediate="true"
                         value="#{pprExampleBean.inputTextValue}" valueChangeListener="#{pprExampleBean.inputTextChanged}" required="true"/>

            <h:outputText value="Checkbox is:" />
			<s:pprPanelGroup id="pprCheckBoxValue"
				partialTriggers="checkbox(onclick,onchange)">
				<h:outputText value="#{pprExampleBean.checkBoxValue ? 'checked' : 'not checked'}" />
			</s:pprPanelGroup>

            <h:outputText value="Drop Down value is:" />
			<s:pprPanelGroup id="dropDownPPR"
				partialTriggers="dropDown(onchange)">
				<h:outputText value="#{pprExampleBean.dropDownValue}" />
			</s:pprPanelGroup>

            <h:outputText value="Input text value is:" />
			<s:pprPanelGroup id="inputTextPPR"
				partialTriggers="inputText(onkeyup)">
				<h:outputText value="#{pprExampleBean.inputTextValue}" />
			</s:pprPanelGroup>            
        </h:panelGrid>

        <s:fieldset legend="about this example">
         <f:verbatim>
             <br />
             <br />
            This example shows how event-handlers of input-fields (such as text-inputs, checkboxes or dropdowns) <br />
            can be used to update page regions via AJAX.<br />
            For this, use the special feature of pprPanelGroup to supply desired-event-handlers in the partialTriggers
            section of a component. e.g.: componentId1(onkeyup) or componentId2(onclick,onchange).
            This example also shows that in the general case, you need to set immediate=true and a valueChangeListener
            on components to make sure that this very component is processed ahead of the pack for conversion
            and validation, so that a failed conversion or validation won't hinder the AJAXified part of the
            page to redisplay.
        </f:verbatim>
    </s:fieldset>

    </h:form>

</f:view>

<%@include file="inc/page_footer.jsp"%>

</body>

</html>

