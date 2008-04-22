<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>

<html>
<%@include file="../inc/head.inc" %>
<body>
<f:view>
	<h:panelGroup>
		<t:div id="Header">
			<f:subview id="head">
				<jsp:include page="../inc/head.jsp" />
			</f:subview>
		</t:div>
		<t:div id="Menu">
			<f:subview id="nav">
				<jsp:include page="../inc/navigation.jsp" />
			</f:subview>
		</t:div>

		<t:div id="Content">
			<h:panelGrid columns="1">
				<h:outputFormat value="Order reset, press ok to begin anew" />
				<h:commandLink value="[OK]" action="go_first"></h:commandLink>
			</h:panelGrid>
		</t:div>
	</h:panelGroup>
</f:view>
</body>
<%@include file="../inc/page_footer.jsp" %>
</html>
