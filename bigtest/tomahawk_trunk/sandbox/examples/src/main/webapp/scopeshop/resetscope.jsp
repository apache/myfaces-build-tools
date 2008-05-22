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
   <h:form>
	<h:panelGroup>

		<t:div id="Content">
			<h:panelGrid columns="1">
				<h:outputFormat value="Order reset, press ok to begin anew" />
				<h:commandLink value="[OK]" action="go_first"></h:commandLink>
			</h:panelGrid>
		</t:div>
	</h:panelGroup>
    </h:form>
</f:view>
</body>
<%@include file="../inc/page_footer.jsp" %>
</html>
