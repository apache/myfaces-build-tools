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
<script type="">
    // can use this for debugging, add onSuccess="showAlert" to table
    function showAlert(originalRequest){
        alert("orig: " + originalRequest.responseText);
    }

</script>
<f:view>

    <f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" var="example_messages"/>

    <h:panelGroup id="body">

     <h:form>

        <h:outputText value="Note: This component is deprecated; Use pprPeriodicalUpdate instead!" style="font-weight:bold"/>

        <s:autoUpdateDataTable id="data1"
                rows="5"
                styleClass="standardTable"
                headerClass="standardTable_Header"
                footerClass="standardTable_Header"
                rowClasses="standardTable_Row1,standardTable_Row2"
                columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
                var="number"
                value="#{autoUpdateDataTableBean.testList}"
                preserveDataModel="true"
                frequency="5"
                >
           <h:column>
               <f:facet name="header">
                  <h:outputText value="Numbers" />
               </f:facet>
                <h:outputText value="#{number}" />

           </h:column>

        </s:autoUpdateDataTable>

     </h:form>

        <f:verbatim><br></f:verbatim>

    </h:panelGroup>

</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
