<%@ page import="java.math.BigDecimal,
                 java.util.Date"%>
<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
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

    <f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" var="example_messages"/>

    <h:form>

        <h:panelGrid columns="2">
            <h:outputLabel for="text1" value="Text:"/>
            <h:inputText id="text1">
                <t:jsValueChangeListener for="text2" property="value" expressionValue="($srcElem.value=='yes')?'true':'false'" />
                <t:jsValueChangeListener for="text3" property="value" expressionValue="$srcElem.value" />
                <t:jsValueChangeListener for="text4" expressionValue="$destElem.innerHTML = $srcElem.value" />
                <t:jsValueChangeListener for="text5" expressionValue="($srcElem.value=='hide')?$destElem.style.display='none':$destElem.style.display='inline'" />
                <t:jsValueSet name="countryMap" value="#{countryList.countryMap}"/>
                <t:jsValueChangeListener for="text6" expressionValue="$destElem.innerHTML = countryMap[$srcElem.value]" />
            </h:inputText>
            <h:inputText id="text2"/>
            <h:inputText id="text3"/>
            <h:panelGroup id="text4" />
            <h:panelGroup id="text5" >
                <h:outputText value="Hide me - enter hide in first input-field."/>
            </h:panelGroup>
            <h:panelGroup id="text6" >
                <h:outputText value="Countryname - enter ISO Code in first input-field (e.g. AT)"/>
            </h:panelGroup>
            <h:panelGroup/>
            <h:selectOneMenu id="selone_menu_options" value="o2" styleClass="selectOneMenu">
                <f:selectItem itemValue="" itemLabel="#{example_messages['empty_selitem']}" />
                <f:selectItems value="#{jsListenerModel.optionItems}" />
                <t:jsValueChangeListener for="selone_menu_options_text" expressionValue="($srcElem.options[$srcElem.selectedIndex].value=='o1')?$destElem.style.display='inline':$destElem.style.display='none';"/>
            </h:selectOneMenu>
            <h:inputText id="selone_menu_options_text"/>
        </h:panelGrid>

    </h:form>

    <jsp:include page="inc/mbean_source.jsp"/>

</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
