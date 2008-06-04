<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

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
	  		<h:panelGrid columns="1">
				<h:outputText value="Test if the custom error page correctly works" />
				
				<h:outputText value="Exceptions without text:" />
		  		<h:commandButton action="#{testException.npe}" value="simple npe"/>
		  		<h:commandButton action="#{testException.wrappedRuntimeNpe}" value="wrapped npe in runtime exception"/>
		  		<h:commandButton action="#{testException.wrappedServletNpe}" value="wrapped npe in ServletException"/>
		  		
				<h:outputText value="Exceptions with text:" />
		  		
		  		<h:commandButton action="#{testException.npeTxt}" value="simple npe"/>
		  		<h:commandButton action="#{testException.wrappedRuntimeNpeTxt}" value="wrapped npe in runtime exception"/>
		  		<h:commandButton action="#{testException.wrappedServletNpeTxt}" value="wrapped npe in ServletException"/>
		  	</h:panelGrid>
		  </h:form>

          <jsp:include page="inc/mbean_source.jsp"/>
          
      </f:view>

<%@include file="inc/page_footer.jsp" %>

</body>
</html>