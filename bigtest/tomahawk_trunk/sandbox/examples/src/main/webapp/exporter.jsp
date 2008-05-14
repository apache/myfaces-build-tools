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
    	<p>This component allows to export the datatable contents to an excel or pdf file.</p>
		
		<br>
		
		<t:dataTable id="tbl_cars"
                styleClass="scrollerTable"
                headerClass="standardTable_Header"
                footerClass="standardTable_Header"
                rowClasses="standardTable_Row1,standardTable_Row2"
                columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
                var="car"
                value="#{exporterBean.carsList}"
                preserveDataModel="false" 
           >
           <h:column>
               <f:facet name="header">
                   <h:outputText value="Id" />
               </f:facet>
               <h:outputText value="#{car.id}" />
           </h:column>

           <h:column>
               <f:facet name="header">
                  <h:outputText value="Type" />
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

		<br>
		
		<h:commandButton action="" value="Export as excel">
			<s:exporterActionListener for="tbl_cars" fileType="XLS"/>
		</h:commandButton>
		
		<br>
		
		<h:commandButton action="" value="Export as pdf">
			<s:exporterActionListener for="tbl_cars" fileType="PDF"/>
		</h:commandButton>
		
    </h:form>
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
