<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<html>

<%@ include file="inc/head.inc" %>

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

    <f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages"
                  var="example_messages"/>

    <t:div id="hNav_outer">
        <t:panelNavigation2 id="nav1" layout="list" itemClass="off" activeItemClass="on" openItemClass="on"
                            renderAll="true">
            <t:commandNavigation2 value="#{example_messages['panelnav_products']}" style="padding-left: 0px;">
                <t:commandNavigation2>
                    <f:verbatim>&#8250; </f:verbatim>
                    <t:outputText value="#{example_messages['panelnav_serach1']}"/>
                </t:commandNavigation2>
                <t:commandNavigation2 externalLink="http://www.irian.at" target="_blank">
                    <f:verbatim>&#8250; </f:verbatim>
                    <t:outputText value="#{example_messages['panelnav_serach_acc1']}"/>
                </t:commandNavigation2>
                <t:commandNavigation2>
                    <f:verbatim>&#8250; </f:verbatim>
                    <t:outputText value="#{example_messages['panelnav_search_adv1']}"/>
                </t:commandNavigation2>
            </t:commandNavigation2>
            <t:commandNavigation2 value="#{example_messages['panelnav_shop']}" externalLink="http://www.yahoo.com"
                                  target="_blank"/>
            <t:commandNavigation2 value="#{example_messages['panelnav_corporate']}" style="padding-left: 150px;">
                <t:commandNavigation2>
                    <f:verbatim>&#8250; </f:verbatim>
                    <t:outputText value="#{example_messages['panelnav_news1']}"/>
                </t:commandNavigation2>
                <t:commandNavigation2>
                    <f:verbatim>&#8250; </f:verbatim>
                    <t:outputText value="#{example_messages['panelnav_investor1']}"/>
                </t:commandNavigation2>
            </t:commandNavigation2>
            <t:commandNavigation2 value="#{example_messages['panelnav_contact']}" externalLink="http://mail.yahoo.com"
                                  target="_blank"/>
        </t:panelNavigation2>
    </t:div>

    <jsp:include page="inc/mbean_source.jsp"/>

</f:view>
<%@ include file="inc/page_footer.jsp" %>

</body>

</html>
