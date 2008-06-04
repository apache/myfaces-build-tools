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
<h1>PPR Example - ajaxifying a datatable using PPR with partialTriggerPattern</h1>

<span id="cnt">0</span>
seconds since last page refresh.
<script>
    var sec=0;
    function counter(){
        setTimeout("counter();",1000);
        document.getElementById("cnt").innerHTML = sec++;
    }
    counter();
</script>

<f:view>

	<h:form id="mainform">


            <h:panelGrid>

                <s:pprPanelGroup id="dataTableArea" partialTriggerPattern="mainform:.*">
                    <t:dataTable var="address" value="#{pprExampleBean.periodicalUpdatedValues}"
                            rows="5"
                            styleClass="standardTable"
                            headerClass="standardTable_SortHeader"
                            footerClass="standardTable_Footer"
                            rowClasses="standardTable_Row1,standardTable_Row2"
                            id="data"
                            sortable="true"
                            preserveDataModel="true"
                            preserveSort="true">
                        <t:column>
                            <f:facet name="header">
                                <h:outputText value="streetnumber"/>
                            </f:facet>
                            <h:outputText value="#{address.streetNumber}"/>
                        </t:column>
                        <t:column defaultSorted="true">
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
                </s:pprPanelGroup>
            </h:panelGrid>

            <s:fieldset legend="about this example">
         <f:verbatim>
             <br />
             <br />
            The partialTriggerPattern attribute takes a regular expression  <br />
            (JavaScript regular expression). All input components which     <br />
             clientIds match this regular expression trigger AJAX updates   <br />
             of this pprPanelGroup. <br />
             <br />
             In this example inputs for sorting and scrolling the datatable  <br />
             sit in -mainform-. Therefore all clientIds of this inputs          <br />
             will start with -mainform:-. (A client id is the HTML-ID     <br />
             attribute the HTML-element rendered by a component has set)  <br />
             Therefore the clientIds of all buttons match the pattern specified <br />
             in the pprPanelGroup and so all of them trigger AJAX updates  <br />
             of the group.    <br />
              <br />
             In order not to interfere with other possible components on the <br />
             page the table could also be placed inside a naming container <br />
             like f:subview.
        </f:verbatim>
    </s:fieldset>
    </h:form>


</f:view>

<%@include file="inc/page_footer.jsp"%>

</body>

</html>


