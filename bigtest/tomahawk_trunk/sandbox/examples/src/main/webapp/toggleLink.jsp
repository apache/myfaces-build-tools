<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>

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
			<h:outputText value="Allows toggling between View/Edit modes. Click on the link to enter a value. An entry less than 5 characters triggers validation error. When the page is re-displayed after a validation error, the component 'remembers' that it is in Edit mode. "/>

			<f:verbatim> <br/><br/> </f:verbatim>

			<s:togglePanel>
				<s:toggleLink for="code">
					<h:outputText value="#{toggleBean.testValue}"/>
				</s:toggleLink>

				<t:inputText
					id="code"
					value="#{toggleBean.testValue}"
					required="true"
					style="font-color:red"
					size="6">
		        	<f:validateLength minimum="5"/>
				</t:inputText>
			</s:togglePanel>
			<t:messages showDetail="true"/>
			<h:commandButton value="Update"/>
			
			<f:verbatim> <br/><hr/><br/> </f:verbatim>

			<s:togglePanel>
				<s:toggleGroup>
					<h:outputLabel for="fullName" value="Full Name (click to edit) "/>
					<s:toggleLink for="editNames">
						<h:outputText id="fullName" value="#{toggleBean.firstName} #{toggleBean.lastName}"/>
					</s:toggleLink>
				</s:toggleGroup>

				<t:div id="editNames">
					<h:outputLabel for="firstName" value="First Name"/>
					<t:inputText id="firstName"	value="#{toggleBean.firstName}"/>

					<h:outputLabel for="lastName" value="Last Name"/>
					<t:inputText id="lastName"	value="#{toggleBean.lastName}"/>

					<h:commandButton value="Update"/>	
				</t:div>
			</s:togglePanel>

		</h:form>
	</f:view>
</body>

<%@include file="inc/page_footer.jsp" %>

</html>