<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<html>

    <%@include file="/inc/head.inc" %>
<body>
    <f:view>

		<h:panelGrid columns="1">

			<h:outputText value="Your input:" />

			<h:outputText value="#{requestTrackerRedirectBean.input}" />

			<h:outputText value="Damn cool, isnt it?" />

		</h:panelGrid>

		<f:verbatim><hr /></f:verbatim>

		<h:messages globalOnly="false" showDetail="true" showSummary="true"/>

	</f:view>
</body>
</html>
