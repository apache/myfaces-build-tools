<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>

<html>

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
					<h:outputFormat value="Thank you for shopping at Frobozz Industrial Magic" />
				</h:panelGrid>

				<h:panelGrid columns="2">

					<h:outputFormat value="First Name" />
					<h:outputFormat id="firstname" value="#{ScopeBean.firstname}" />
					<h:outputFormat value="Last Name" />
					<h:outputFormat id="lastname" value="#{ScopeBean.lastname}" />
					<h:outputFormat value="Street" />
					<h:outputFormat id="street" value="#{ScopeBean.street}" />
					<h:outputFormat value="Number" />
					<h:outputFormat id="strno" value="#{ScopeBean.number}" />
					<h:outputFormat value="Zip/Area Code" />
					<h:outputFormat id="zip" value="#{ScopeBean.zipcode}" />
					<h:outputFormat value="City" />
					<h:outputFormat id="city" value="#{ScopeBean.city}" />
					<h:outputFormat value="Country" />
					<h:outputFormat id="country" value="#{ScopeBean.country}" />
					<h:outputFormat value="Itemslist" />
					<h:outputFormat id="itemsno" value="#{ScopeBean.itemsList}" />

				</h:panelGrid>

				<h:panelGrid columns="3">
					<h:commandLink value="[<<< Previous]" action="go_previous" />
				</h:panelGrid>
			</h:form>
		</t:div>
	</h:panelGroup>
</f:view>
</body>
<%@include file="../inc/page_footer.jsp" %>
</html>
