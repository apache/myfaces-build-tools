<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>

<html>

<%@include file="inc/head.inc" %>

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
    <h:form>
        <h:panelGrid>     
             <t:messages showDetail="true" showSummary="false"/>
            <h:outputText value="Example for disabled input value submit"/>                        
            
            <f:verbatim>
                <script type="text/javascript"><!--
                    function changeDisabledInputValue() {
                        var fName = document.getElementById("_idJsp0:firstName");
                        var lName = document.getElementById("_idJsp0:lastName");
                        var fullName = document.getElementById("_idJsp0:fullName");
                        fullName.value = fName.value+" "+lName.value;
                        fullName.onchange();                        
                    }
                //--></script>    
            </f:verbatim>    
                        
            <h:outputText value="Person" style="font-weight:bold;"/>
            <h:panelGrid columns="2">
                <h:outputLabel for="firstName" value="First Name:"/>
                <h:inputText id="firstName" value="#{inputTextDisabled.firstName}"/>
                <h:outputLabel for="lastName" value="Last Name:"/>                
                <h:inputText id="lastName" value="#{inputTextDisabled.lastName}"/>                
                <h:outputLabel for="fullName" value="Full Name:"/>
                <t:inputText id="fullName" value="#{inputTextDisabled.fullName}" disabledOnClientSide="true">
                    <f:validateLength maximum="5" minimum="2"/>
                </t:inputText>                   
            </h:panelGrid>
            <h:outputLink value="#" onclick="changeDisabledInputValue();"><f:verbatim>Change disabled input field's value</f:verbatim></h:outputLink>                
            <h:commandButton value="Show current values of Person"/>                        
        </h:panelGrid>
                
        
        <h:panelGrid columns="2">
            <h:outputText value="First name of Person:"/>
            <h:outputText value="#{inputTextDisabled.firstName}"/>
            <h:outputText value="Last name of Person:"/>
            <h:outputText value="#{inputTextDisabled.lastName}"/>
            <h:outputText value="Full name of Person:"/>
            <h:outputText value="#{inputTextDisabled.fullName}"/>            
        </h:panelGrid>

    </h:form>

    <jsp:include page="inc/mbean_source.jsp"/>

</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
