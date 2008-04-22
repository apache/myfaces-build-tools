<%@ page session="false" contentType="text/html;charset=iso-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=iso-8859-1" />
		<link type="text/css" rel="stylesheet" href="/css/basic.css" />
	</head>
	<body>
		<f:view>

			<h:outputText value="This is the text from the dojo/dojoDialogView.jsp file" />

			<h:panelGrid columns="1">
				<h:outputLink
					value="#"
					onclick="window.parent._myfaces_currentModal._myfaces_ok=true;window.parent._myfaces_currentModal.hide();">
					<h:outputText value="close window" />
				</h:outputLink>

			</h:panelGrid>

		</f:view>

	</body>
</html>
