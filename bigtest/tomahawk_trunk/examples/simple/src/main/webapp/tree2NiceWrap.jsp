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

<%@include file="inc/head.inc" %>

<body>

<f:view>
<h:form>

    <span style="font-family:verdana">
        <b>Tree2 w/nice wrap</b><br/>
    </span>
    <br/>

    <h:panelGrid width="200">
        <t:tree2 id="wrapTree" value="#{treeBacker.treeData}" var="node" varNodeToggler="t" clientSideToggle="false">
            <f:facet name="person">
                <h:panelGrid id="a" columns="2" cellpadding="0" cellspacing="0">
                    <t:graphicImage value="/images/yellow-folder-open.png" rendered="#{t.nodeExpanded}" border="0"/>
                    <t:graphicImage value="/images/yellow-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                    <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                </h:panelGrid>
            </f:facet>
            <f:facet name="foo-folder">
                <h:panelGrid id="b" columns="2" cellpadding="2" cellspacing="0">
                    <t:graphicImage value="/images/yellow-folder-open.png" rendered="#{t.nodeExpanded}" border="0"/>
                    <t:graphicImage value="/images/yellow-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                    <h:panelGroup>
                        <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                        <h:outputText value=" (#{node.childCount})" styleClass="childCount" rendered="#{!empty node.children}"/>
                    </h:panelGroup>
                </h:panelGrid>
            </f:facet>
            <f:facet name="bar-folder">
                <h:panelGrid id="c" columns="2" cellpadding="2" cellspacing="0">
                    <t:graphicImage value="/images/blue-folder-open.gif" rendered="#{t.nodeExpanded}" border="0"/>
                    <t:graphicImage value="/images/blue-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                    <h:panelGroup>
                        <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                        <h:outputText value=" (#{node.childCount})" styleClass="childCount" rendered="#{!empty node.children}"/>
                    </h:panelGroup>
                </h:panelGrid>
            </f:facet>
            <f:facet name="document">
                <h:panelGroup>
                    <h:commandLink immediate="true" styleClass="#{t.nodeSelected ? 'documentSelected':'document'}" actionListener="#{t.setNodeSelected}">
                        <t:graphicImage value="/images/document.png" border="0"/>
                        <h:outputText value="#{node.description}"/>
                        <f:param name="docNum" value="#{node.identifier}"/>
                    </h:commandLink>
                </h:panelGroup>
            </f:facet>
        </t:tree2>
    </h:panelGrid>
    
</h:form>

<jsp:include page="inc/mbean_source.jsp"/>    

</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>

