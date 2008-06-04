<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>
<html>

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

<%@include file="inc/head.inc" %>

<body>

<f:view>

    <f:verbatim>
        <script type="text/javascript" language="JavaScript">
function notifyOriginalEvent()
{
    var notifyer = document.getElementById("notifyer");
    notifyer.value=notifyer.value + ">";
    if (notifyer.value.length > 80)
    {
        notifyer.value=">";
    }
}

function mySpecialUserCallback(event, srcComponentId, clickComponentId)
{
    var keycode;
    if (window.event)
    {
        keycode = window.event.keyCode;
    }
    else if (event)
    {
        keycode = event.which;
    }

    return (keycode == 97 || keycode == 65);
}
            
        </script>
    </f:verbatim>

    <h:form>

        <h:panelGroup>
            <t:outputText value="additional '>' should appear to notify the existence of any old event handler" />
            <t:inputText forceId="true" id="notifyer" readonly="true" value=">" />
        </h:panelGroup>

        <h:panelGrid columns="3">


            <f:facet name="footer">
                <h:panelGrid columns="4">
                    <h:commandButton id="trapButton" value="trap button" action="#{submitOnEvent.trapAction}"/>
                    <h:commandButton id="submitButton" value="submit button" action="#{submitOnEvent.buttonAction}"/>
                    <h:commandLink id="submitLink" value="submit link" action="#{submitOnEvent.linkAction}"/>

                    <h:outputText value="last action was: #{submitOnEvent.lastAction}" />

                </h:panelGrid>
            </f:facet>

            <h:outputText value="description" />
            <h:outputText value="control" />
            <h:outputText value="output" />


            <h:outputLabel for="text1" value="submit via button on enter"/>
            <h:inputText id="text1" value="#{submitOnEvent.strings.text1}" onkeypress="javascript:notifyOriginalEvent()">
                <s:submitOnEvent for="submitButton" />
            </h:inputText>
            <h:outputText value="#{submitOnEvent.strings.text1}"/>


            <h:outputLabel for="text2" value="submit via link on enter"/>
            <h:inputText id="text2" value="#{submitOnEvent.strings.text2}" onkeypress="javascript:notifyOriginalEvent()">
                <s:submitOnEvent for="submitLink" />
            </h:inputText>
            <h:outputText value="#{submitOnEvent.strings.text2}"/>


            <h:outputLabel for="text2" value="submit via button on change" />
            <h:selectOneMenu id="text3" value="#{submitOnEvent.strings.text3}" onchange="javascript:notifyOriginalEvent()">
                <f:selectItem itemLabel="selection1" itemValue="selection1" />
                <f:selectItem itemLabel="selection2" itemValue="selection2" />
                <f:selectItem itemLabel="selection3" itemValue="selection3" />
                <f:selectItem itemLabel="selection4" itemValue="selection4" />
                
                <s:submitOnEvent for="submitButton" event="change" />
            </h:selectOneMenu>
            <h:outputText value="#{submitOnEvent.strings.text3}"/>


            <h:outputLabel for="text4" value="submit via link on focus lost (blur)"/>
            <h:inputText id="text4" value="#{submitOnEvent.strings.text4}" onblur="javascript:notifyOriginalEvent()">
                <s:submitOnEvent for="submitLink" event="blur"/>
            </h:inputText>
            <h:outputText value="#{submitOnEvent.strings.text4}"/>


            <h:outputLabel for="text5" value="submit via button on when pressing 'a'"/>
            <h:inputText id="text5" value="#{submitOnEvent.strings.text5}" onblur="javascript:notifyOriginalEvent()">
                <s:submitOnEvent for="submitLink" callback="mySpecialUserCallback"/>
            </h:inputText>
            <h:outputText value="#{submitOnEvent.strings.text5}"/>

        </h:panelGrid>
    </h:form>
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
