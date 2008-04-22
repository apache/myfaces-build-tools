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

    <h:form>


    <h:panelGroup id="body">

        <t:dataTable id="data"
                styleClass="scrollerTable"
                headerClass="standardTable_Header"
                footerClass="standardTable_Header"
                rowClasses="standardTable_Row1,standardTable_Row2"
                columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
                var="car"
                value="#{selectOneRowList.list}"
                preserveDataModel="false"
                rows="10"
           >
           <h:column>
               <f:facet name="header">
                   <h:outputText value="Select"/>
               </f:facet>
               <s:selectOneRow  groupName="selection" id="hugo" value="#{selectOneRowList.selectedRowIndex}"
                       onchange="submit();" immediate="true"
                       valueChangeListener="#{selectOneRowList.processRowSelection}"/>
           </h:column>
           <h:column>
               <f:facet name="header">
               </f:facet>
               <h:outputText value="#{car.id}" />
           </h:column>

           <h:column>
               <f:facet name="header">
                  <h:outputText value="Cars" />
               </f:facet>
               <h:outputText value="#{car.type}" />
           </h:column>

           <h:column>
               <f:facet name="header">
                  <h:outputText value="Color" />
               </f:facet>
               <h:outputText value="#{car.color}" />
           </h:column>

        </t:dataTable>

        <h:panelGrid columns="1" styleClass="scrollerTable2" columnClasses="standardTable_ColumnCentered" >
            <t:dataScroller id="scroll_1"
                    for="data"
                    fastStep="10"
                    pageCountVar="pageCount"
                    pageIndexVar="pageIndex"
                    styleClass="scroller"
                    paginator="true"
                    paginatorMaxPages="9"
                    paginatorTableClass="paginator"
                    paginatorActiveColumnStyle="font-weight:bold;"
                    actionListener="#{selectOneRowList.scrollerAction}"
                    >
                <f:facet name="first" >
                    <h:outputText value="First" />
                </f:facet>
                <f:facet name="last">
                    <h:outputText value="Last" />
                </f:facet>
                <f:facet name="previous">
                    <h:outputText value="Prev" />
                </f:facet>
                <f:facet name="next">
                    <h:outputText value="Next" />
                </f:facet>
                <f:facet name="fastforward">
                    <h:outputText value="FFW" />
                </f:facet>
                <f:facet name="fastrewind">
                    <h:outputText value="FRW" />
                </f:facet>
            </t:dataScroller>
            <t:dataScroller id="scroll_2"
                    for="data"
                    rowsCountVar="rowsCount"
                    displayedRowsCountVar="displayedRowsCountVar"
                    firstRowIndexVar="firstRowIndex"
                    lastRowIndexVar="lastRowIndex"
                    pageCountVar="pageCount"
                    immediate="true"
                    pageIndexVar="pageIndex"
                    >
                <h:outputFormat value="#{example_messages['dataScroller_pages']}" styleClass="standard" >
                    <f:param value="#{rowsCount}" />
                    <f:param value="#{displayedRowsCountVar}" />
                    <f:param value="#{firstRowIndex}" />
                    <f:param value="#{lastRowIndex}" />
                    <f:param value="#{pageIndex}" />
                    <f:param value="#{pageCount}" />
                </h:outputFormat>
            </t:dataScroller>
            <h:outputText value="#{selectOneRowList.selectionMessage}" />
            <h:commandButton value="Select" />
        </h:panelGrid>

    </h:panelGroup>
    </h:form>
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
