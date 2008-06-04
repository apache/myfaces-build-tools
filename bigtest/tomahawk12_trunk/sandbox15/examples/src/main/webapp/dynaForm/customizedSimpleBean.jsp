<%--
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
  --%>

<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox15" prefix="sn"%>

<f:view>
<t:document>
<t:documentHead>
    <%@include file="/inc/head.inc" %>
</t:documentHead>
<t:documentBody>

    <h:form>
        <%@include file="/inc/page_header.jsp" %>

        <f:loadBundle basename="org.apache.myfaces.examples.resource.dynaForm_messages" var="dynaFormBundle"/>

        <h:panelGrid>

            <t:htmlTag value="h2">
                <h:outputText value="A simple bean input form with customized 'description'" />
            </t:htmlTag>

            <sn:dynaForm
                    var="person"
                    uri="org.apache.myfaces.examples.dynaForm.lib.Person"
                    valueBindingPrefix="simpleBeanBacking.person"
                    bundle="dynaFormBundle">
                <sn:dynaFormConfigs>
                    <sn:dynaFormConfig for="description">
                        <t:inputHtml/>
                    </sn:dynaFormConfig>
                </sn:dynaFormConfigs>

                <h:panelGrid
                        id="person-layout"
                        columns="2" />
            </sn:dynaForm>

            <h:commandButton/>
        </h:panelGrid>

        <%@include file="/inc/page_footer.jsp" %>
    </h:form>

</t:documentBody>
</t:document>
</f:view>

