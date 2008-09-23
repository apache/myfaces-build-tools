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
            <h:outputLabel for="areaCode" value="Your area code is:"/> 
            <h:outputText id="areaCode" value="#{sayHelloBean.phoneNumber.areaCode}"/>
            <h:outputLabel for="num" value="Your number is:"/> 
            <h:outputText id="num" value="#{sayHelloBean.phoneNumber.number}"/>
            <h:commandLink value="[HOME]" action="go_home"/>
        </h:panelGrid>
    </h:form>
</f:view>


</body>

</html>
