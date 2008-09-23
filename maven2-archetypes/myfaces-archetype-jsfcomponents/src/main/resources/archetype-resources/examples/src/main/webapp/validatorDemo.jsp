<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://www.myorganitzation.org/mycomponents" prefix="mycomp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>My JSF Components Examples</title>
</head>
<body>

<f:view>
    <h:outputText value="Custom Validator Demo"/>
    <f:verbatim><hr></f:verbatim>

    <h:form>
        <h:panelGrid columns="2">
            <h:outputLabel for="num" value="Enter an odd number" />
            <h:inputText id="num" value="#{sayHelloBean.oddNumber}">
                <mycomp:validateOddNum/>
            </h:inputText>
            <h:commandButton value="Submit" action="#{sayHelloBean.submitOddNumber}"/>
        </h:panelGrid>
        <h:commandLink value="[HOME]" action="go_home"/>
        <h:messages />
    </h:form>
</f:view>

</body>
</html>
