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

<!--
managed beans used:
    countryList
-->

<f:view>

    <f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" var="example_messages"/>
    <h:form>
     <t:dataTable id="data"
                  styleClass="scrollerTable"
                  headerClass="standardTable_Header"
                  footerClass="standardTable_Header"
                  rowClasses="standardTable_Row1,standardTable_Row2"
                  columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
                  var="car"
                  value="#{pagedSort.cars}"
                  preserveDataModel="true"
                  rows="10"
                  rowId="#{car.type}"
                  rowOnClick="alert('rowId: ' + this.id)"
                  sortColumn="#{pagedSort.sort}"
                  sortAscending="#{pagedSort.ascending}"
                  preserveSort="true">
        <h:column>
            <f:facet name="header"></f:facet>
            <h:outputText value="#{car.id}" />
        </h:column>
        <h:column>
            <f:facet name="header">
                <t:commandSortHeader columnName="type" arrow="true" immediate="false">
                    <h:outputText value="#{example_messages['sort_cartype']}" />
                </t:commandSortHeader>
            </f:facet>
            <h:outputText value="#{car.type}" />
        </h:column>
        <h:column>
            <f:facet name="header">
                <t:commandSortHeader columnName="color" arrow="true" immediate="false">
                    <h:outputText value="#{example_messages['sort_carcolor']}" />
                </t:commandSortHeader>
            </f:facet>
            <h:inputText value="#{car.color}" >
                <f:validateLength maximum="10"/>
            </h:inputText>
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
                        paginatorActiveColumnStyle="font-weight:bold;">
            <f:actionListener type="org.apache.myfaces.examples.listexample.DataScrollerActionListener"/>
            <f:facet name="first" >
                <t:graphicImage url="images/arrow-first.gif" border="1" />
            </f:facet>
            <f:facet name="last">
                <t:graphicImage url="images/arrow-last.gif" border="1" />
            </f:facet>
            <f:facet name="previous">
                <t:graphicImage url="images/arrow-previous.gif" border="1" />
            </f:facet>
            <f:facet name="next">
                <t:graphicImage url="images/arrow-next.gif" border="1" />
            </f:facet>
            <f:facet name="fastforward">
                <t:graphicImage url="images/arrow-ff.gif" border="1" />
            </f:facet>
            <f:facet name="fastrewind">
                <t:graphicImage url="images/arrow-fr.gif" border="1" />
            </f:facet>
        </t:dataScroller>

    </h:panelGrid>
    </h:form>

    <jsp:include page="inc/mbean_source.jsp"/>

</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
