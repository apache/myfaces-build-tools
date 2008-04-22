<%@ page
	session="false"
	contentType="text/html;charset=utf-8"%>
<%@ taglib
	uri="http://java.sun.com/jsf/html"
	prefix="h"%>
<%@ taglib
	uri="http://java.sun.com/jsf/core"
	prefix="f"%>
<%@ taglib
	uri="http://myfaces.apache.org/tomahawk"
	prefix="t"%>
<%@ taglib
	uri="http://myfaces.apache.org/sandbox"
	prefix="s"%>
<html>

<%@include file="inc/head.inc"%>

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
		<h:selectOneMenu
			value="#{notifierBean.selectedCategory}"
			onchange="submit()">

			<s:valueChangeNotifier method="#{notifierBean.valueChange}" />
			<f:selectItems value="#{notifierBean.categories}" />

		</h:selectOneMenu>

		<h:dataTable
			value="#{notifierBean.listData}"
			var="item"
			border="1">
			<h:column>
				<h:dataTable
					value="#{item.listData}"
					var="itemSub"
					binding="#{notifierBean.listDataBinding}"
					border="1">
					<h:column>
						<h:inputText value="#{itemSub.data}">
							<s:valueChangeNotifier method="#{notifierBean.valueChange}" />
						</h:inputText>
					</h:column>
				</h:dataTable>
			</h:column>
		</h:dataTable>

		<h:commandButton />

		<f:verbatim>You selected:</f:verbatim><h:outputText value="#{notifierBean.selectedCategory}" />

	</h:form>
</f:view>

<%@include file="inc/page_footer.jsp"%>

</body>

</html>
