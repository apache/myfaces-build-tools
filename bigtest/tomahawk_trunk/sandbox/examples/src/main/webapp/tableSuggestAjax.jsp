<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>

<html>

<%@include file="inc/head.inc" %>

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
    
   <h:form id="ajaxform">

     <f:verbatim><br/><br/><br/><br/></f:verbatim>

     <h:panelGrid >
        <h:panelGrid columns="4">
         <f:verbatim> City Names starting with 'Sa' <br/> Suggest starts with 2. char </f:verbatim>
         <s:tableSuggestAjax var="address" id="suggest" startRequest="2"
                             value="#{inputSuggestAjax.suggestValue}" betweenKeyUp="300"
                             suggestedItemsMethod="#{inputSuggestAjax.getCityList}" charset="utf-8">
            <t:column>
                 <f:facet name="header">
                     <s:outputText value="City"/>
                 </f:facet>
                 <s:outputText for="suggest" label="#{address.city}"/>
             </t:column>
             <t:column>
                 <f:facet name="header">
                     <s:outputText value="Zip"/>
                 </f:facet>
                 <s:outputText for="zipField" label="#{address.zip}"/>
             </t:column>
             <t:column>
                 <f:facet name="header">
                     <s:outputText value="State"/>
                 </f:facet>
                 <s:outputText forValue="stateField" label="#{address.stateName}" value="#{address.stateCode}"/>
             </t:column>
         </s:tableSuggestAjax>
             
     </h:panelGrid>
         <f:verbatim><br/><br/><br/><br/><br/></f:verbatim>
         <h:panelGrid>
             <h:commandButton/>
             <h:outputText value="Zip Code"/>
             <t:inputText id="zipField" />
             <h:outputText value="State"/>
             <t:selectOneMenu id="stateField">
                  <f:selectItem value="" itemLabel="New York" itemValue="NY"/>
                  <f:selectItem value="" itemLabel="California" itemValue="CA"/>
                  <f:selectItem value="" itemLabel="Texas" itemValue="TX"/>
             </t:selectOneMenu>
         </h:panelGrid>
 </h:panelGrid>
    </h:form>
    
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>


