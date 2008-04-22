<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
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

<%-- Unnecessary, as we define head below --%>
<%-- <%@include file="inc/head.inc" %> --%>

<f:view>
    <head>
        <meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1">
        <title>MyFaces - the free JSF Implementation</title>
        <t:stylesheet path="/css/basic.css"/>
    </head>

    <body>

        <f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" var="example_messages"/>
        <h:outputText value="#{example_messages['css_msg']}"/><code>stylesheet</code>-Component<br />
        <a href='http://myfaces.apache.org'>The MyFaces-Team</a>.

         <jsp:include page="inc/mbean_source.jsp"/>

        <%@include file="inc/page_footer.jsp" %>

    </body>
</f:view>
</html>
