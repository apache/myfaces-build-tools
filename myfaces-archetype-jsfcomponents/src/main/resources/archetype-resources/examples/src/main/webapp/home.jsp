<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://www.myorganitzation.org/mycomponents" prefix="mycomp"%>

<html>

<head>
    <title>My JSF Components Examples</title>
</head>

<body>

<f:view>
    <h:form>

        <f:loadBundle basename="org.myorganization.component.example.build" var="buildInfo"/>

        <h:panelGrid columns="2">
            <h:outputText style="font-weight: bold" value="My JSF Components Library"/>
            <h:outputText value=" (Version #{buildInfo['mycomponents_version']}, using #{buildInfo ['jsf_implementation']})"/>
        </h:panelGrid>

        <h:panelGrid>

            <h:outputText value="My component examples" />
            <h:panelGrid style="padding-left:25px">
                <h:outputLink value="sayhello.jsf">
                    <f:verbatim>sayHello - demo component</f:verbatim>
                </h:outputLink>
            </h:panelGrid>

        </h:panelGrid>

    </h:form>
</f:view>
</body>
</html>
