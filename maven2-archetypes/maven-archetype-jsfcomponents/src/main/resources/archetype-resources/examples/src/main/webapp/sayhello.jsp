<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://www.myorganitzation.org/mycomponents" prefix="mycomp"%>

<html>
<head>
    <title>My JSF Components</title>
</head>

<body>

<f:view>

    <mycomp:sayHello firstName="John" lastName="Smith"/>

    <h:outputText value=" (probably not you, I know)"/>

    <f:verbatim><hr></f:verbatim>

    <h:form>
        <h:panelGrid columns="2">
            <h:outputLabel for="inputFirstName" value="First name" />
            <h:inputText id="inputFirstName" value="#{sayHelloBean.firstName}"/>

            <h:outputLabel for="inputLastName" value="Last name" />
            <h:inputText id="inputLastName" value="#{sayHelloBean.lastName}"/>
        </h:panelGrid>

        <h:panelGrid columns="1">

            <h:commandButton value="Say Hello!" actionListener="#{sayHelloBean.sayIt}"/>

            <mycomp:sayHello firstName="#{sayHelloBean.firstName}"
                             lastName="#{sayHelloBean.lastName}"
                             rendered="#{sayHelloBean.renderGreeting}"/>

            <h:commandLink value="[HOME]" action="go_home"/>

        </h:panelGrid>

    </h:form>

</f:view>


</body>

</html>
