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
<h1>PPR Example - PeriodicalUpdate</h1>
Automatically page refresh through periodic updates
<f:view>

	<h:form id="mainform">


		<t:messages id="messageList" styleClass="error" showDetail="true"
			        summaryFormat="{0} " />

        <h:panelGrid columns="5">

            <h:panelGrid>
                <h:outputText value="List of addresses (not refreshed):"/>

                <t:dataTable var="address" value="#{pprExampleBean.periodicalUpdatedValues}">
                    <t:column>
                        <f:facet name="header">
                            <h:outputText value="streetnumber"/>
                        </f:facet>
                        <h:outputText value="#{address.streetNumber}"/>
                    </t:column>
                    <t:column>
                        <f:facet name="header">
                            <h:outputText value="streetname"/>
                        </f:facet>
                        <h:outputText value="#{address.streetName}"/>
                    </t:column>
                </t:dataTable>
            </h:panelGrid>

            <h:panelGroup/>

            <h:panelGrid>
                <h:outputText value="List of updated addresses after 2000ms:"/>

                <s:pprPanelGroup id="periodicalUpdatedArea" periodicalUpdate="2000" showDebugMessages="false"
                                 stateUpdate="false">
                    <t:dataTable var="address" value="#{pprExampleBean.periodicalUpdatedValues}">
                        <t:column>
                            <f:facet name="header">
                                <h:outputText value="streetnumber"/>
                            </f:facet>
                            <h:outputText value="#{address.streetNumber}"/>
                        </t:column>
                        <t:column>
                            <f:facet name="header">
                                <h:outputText value="streetname"/>
                            </f:facet>
                            <h:outputText value="#{address.streetName}"/>
                        </t:column>
                        <t:column>
                            <f:facet name="header">
                                <h:outputText value="zip"/>
                            </f:facet>
                            <h:outputText value="#{address.zip}"/>
                        </t:column>
                        <t:column>
                            <f:facet name="header">
                                <h:outputText value="state"/>
                            </f:facet>
                            <h:outputText value="#{address.state}"/>
                        </t:column>
                    </t:dataTable>
                </s:pprPanelGroup>
            </h:panelGrid>

            <h:panelGroup/>

            <h:panelGrid>
                <h:outputText value="List of updated addresses after 5000ms: (another ppr-group)"/>
                <s:pprPanelGroup id="periodicalUpdatedArea2" periodicalUpdate="5000" showDebugMessages="false"
                                 stateUpdate="false" excludeFromStoppingPeriodicalUpdate=".dontBlock"
								 waitBeforePeriodicalUpdate="5000">
                <t:dataTable var="address" value="#{pprExampleBean.periodicalUpdatedValues}">
                    <t:column>
                        <f:facet name="header">
                            <h:outputText value="zip"/>
                        </f:facet>
                        <h:outputText value="#{address.zip}"/>
                    </t:column>
                    <t:column>
                        <f:facet name="header">
                            <h:outputText value="state"/>
                        </f:facet>
                        <h:outputText value="#{address.state}"/>
                    </t:column>
                </t:dataTable>
                </s:pprPanelGroup>
            </h:panelGrid>

            <h:commandButton action="#{pprExampleBean.doTimeConsumingStuff}" value="blocking of auto refresh"/>

			<h:commandLink action="#{pprExampleBean.doTimeConsumingStuff}" value="new window, blocking only occurs for 5 seconds" id="dontBlock"
						   target="_blank"/>

			<h:commandLink action="#{pprExampleBean.doTimeConsumingStuff}" value="blocking should not occur" id="second_dontBlock"/>

			<h:commandLink action="#{pprExampleBean.doTimeConsumingStuff}" value="also blocking" id="block"/>

		</h:panelGrid>
    </h:form>


</f:view>

<%@include file="inc/page_footer.jsp"%>

</body>

</html>

