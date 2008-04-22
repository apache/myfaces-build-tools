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
    <h:form>
        <h:outputText value="The init parameter <i>org.apache.myfaces.AUTO_SCROLL</i> is set to <b>#{initParam['org.apache.myfaces.AUTO_SCROLL']}</b>. " escape="false"/>
        <h:outputText value="In this case, the page "/>
        <h:outputText value="should go to the top when a link is clicked." rendered="#{initParam['org.apache.myfaces.AUTO_SCROLL'] == false}"/>
        <h:outputText value="shouldn't move when a link is clicked." rendered="#{initParam['org.apache.myfaces.AUTO_SCROLL'] == true}"/>

        <h:dataTable var="num" value="#{autoScrollBean.numbers}">
            <h:column>
                <h:commandLink value="#{num}. Click me!" action="nothing"/>
            </h:column>
        </h:dataTable>
    </h:form>
    <jsp:include page="inc/mbean_source.jsp"/>
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>