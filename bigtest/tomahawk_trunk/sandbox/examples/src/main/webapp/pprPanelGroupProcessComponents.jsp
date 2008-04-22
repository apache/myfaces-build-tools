<%@ page session="false" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>

<!--
/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements. See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership. The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/
//-->

<html>

<%@ include file="inc/head.inc" %>


<body>
<h1>PPR Examples - submit a form but process only specific components</h1>
<span id="cnt">0</span>
seconds since last page refresh.
<script>
    var sec = 0;
    function counter()
    {
        setTimeout("counter();", 1000);
        document.getElementById("cnt").innerHTML = sec++;
    }
    counter();
</script>

<f:view>

    <h:form id="mainform">


        <h:panelGrid columns="2">
            <h:outputText value="Enter the value for update:"/>
            <h:inputText id="textfield1" value="#{pprExampleBean.textField}" required="true"/>

            <h:outputText value="Leave empty for force validation error"/>
            <h:inputText id="textfield2" value="#{pprExampleBean.inputTextValue}" required="true"/>

            <h:outputText value="partial update button which failes:"/>
            <h:commandButton id="pprSubmitButtonFail"
                             value="PPR Submit"
                             action="#{pprExampleBean.doNothingAction}"/>

            <h:outputText value="partial update button which works:"/>
            <s:pprSubmit processComponentIds="textfield1">
                <h:commandButton id="pprSubmitButtonWork"
                                 value="PPR Submit - Textfield1 only"
                                 action="#{pprExampleBean.doNothingAction}" />
            </s:pprSubmit>

            <h:outputText value="PPRPanelGroup:"/>
            <s:pprPanelGroup id="ppr1"
                             partialTriggers="pprSubmitButtonFail,pprSubmitButtonWork"
                             inlineLoadingMessage="Loading..."
                             replaceMessages="messages"
                             componentUpdateFunction="localDomUpdateFunction">
                <h:inputText id="pprOut" value="#{pprExampleBean.textField}" readonly="true"/>
            </s:pprPanelGroup>
        </h:panelGrid>

        <f:verbatim>
<script type="text/javascript">
function localDomUpdateFunction(formNode, targetNode, responseNode)
{
    document.getElementById("mainform:pprOut").value="LDUF:" + responseNode.firstChild.value;
}
</script>
        </f:verbatim>

        <s:fieldset legend="about this example">
            <f:verbatim>
                <br/>
                Both input fields in this form are required. Normally a PPR request will fail as long as
                the validation chain reports a single error.
                <br />
                The second button configures the ppr request in a way that allows MyFaces to validate/model-update
                only those fields in question.
                <br />
                <br />
                If you press the first button nothing happens as long as the second textfield has no data.
                <br />
                If you press the second button it is sufficient to enter something into the first textfield.
                <br />
                Also notice that a user defined java script function is used to read the ppr response and
                update the dom as required.
            </f:verbatim>
        </s:fieldset>
    </h:form>

    <h:messages id="messages" />
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>

