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

   <h:form>

     <f:verbatim><br/><br/></f:verbatim>

     <t:dojoInitializer bindEncoding="utf-8"/>

     <h:panelGrid columns="9">
         <h:outputText value="default suggest"/>
         <s:inputSuggestAjax suggestedItemsMethod="#{inputSuggestAjax.getItems}"
                             value="#{inputSuggestAjax.suggestValue}"  charset="utf-8"/>

         <h:outputText value="suggest without autoComplete"/>
         <s:inputSuggestAjax suggestedItemsMethod="#{inputSuggestAjax.getItems}"
                             value="#{inputSuggestAjax.suggestValue}" autoComplete="false"/>

         <h:outputText value="suggest with limited suggested items"/>
         <s:inputSuggestAjax suggestedItemsMethod="#{inputSuggestAjax.getItems}"
                             maxSuggestedItems="2" value="#{inputSuggestAjax.suggestValueMaxItems}"/>

         <h:outputText value="suggest with label/value functionality"/>
         <s:inputSuggestAjax suggestedItemsMethod="#{inputSuggestAjax.getAddresses}" 
                             itemLabelMethod="#{inputSuggestAjax.getAddressLabel}"
                             value="#{inputSuggestAjax.choosenAddress}">
             <f:converter converterId="inputSuggestAjaxConverter" />
         </s:inputSuggestAjax>

         <h:commandButton/>
     </h:panelGrid>

    </h:form>
    
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>

