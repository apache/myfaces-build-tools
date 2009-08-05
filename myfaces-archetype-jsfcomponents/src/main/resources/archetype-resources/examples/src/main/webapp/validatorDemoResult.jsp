<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://www.myorganitzation.org/mycomponents" prefix="mycomp"%>

<html>
<head>
    <title>My JSF Components</title>
</head>

<body>

<f:view>
    <h:form>
        <h:panelGrid columns="2">
            Value entered is: <h:outputText value="#{sayHelloBean.oddNumber}"/>
            <h:commandLink value="[HOME]" action="go_home"/>
        </h:panelGrid>
    </h:form>
</f:view>


</body>

</html>
