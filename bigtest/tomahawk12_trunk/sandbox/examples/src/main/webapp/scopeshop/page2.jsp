<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>

<html>
<head>
  <meta HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=UTF-8" />
  <title>MyFaces - the free JSF Implementation</title>
  <link rel="stylesheet" type="text/css" href="css/basic.css" />
</head>
<body>
<f:view>
	<h:panelGroup>

		<t:div id="Content">
			<h:form id="scopeForm">
				<t:saveState id="thescope" value="#{ScopeBean}" />
				<h:panelGrid columns="1">
					<h:outputFormat value="Frobozz Industrial Magic Order Form" />
					<h:outputFormat value="Customer data" />
				</h:panelGrid>

				<h:panelGrid columns="2">
					<h:outputFormat value="First Name" />
					<h:inputText id="firstname" value="#{ScopeBean.firstname}" />
					<h:outputFormat value="Last Name" />
					<h:inputText id="lastname" value="#{ScopeBean.lastname}" />
					<h:outputFormat value="Street" />
					<h:inputText id="street" value="#{ScopeBean.street}" />
					<h:outputFormat value="Number" />
					<h:inputText id="strno" value="#{ScopeBean.number}" />
					<h:outputFormat value="Zip/Area Code" />
					<h:inputText id="zip" value="#{ScopeBean.zipcode}" />
					<h:outputFormat value="City" />
					<h:inputText id="city" value="#{ScopeBean.city}" />
					<h:outputFormat value="Country" />
					<h:inputText id="country" value="#{ScopeBean.country}" />

				</h:panelGrid>

				<h:panelGrid columns="4">
					<h:commandLink value="[Reset Order]" action="go_reset" />
					<h:commandLink value="[<<< Previous]" action="go_first" />
					<h:commandLink value="[Next >>>]" action="go_next" />
				</h:panelGrid>
			</h:form>
		</t:div>
	</h:panelGroup>
</f:view>
</body>
<%@include file="../inc/page_footer.jsp" %>
</html>
