<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<html>
    <head>
        <title>Hello World</title>
    </head>
    <body>
        <f:view>
            <h:form id="form">
              <h:panelGrid id="grid" columns="2">
                <h:outputText id="output1" value="Please enter your name"/>
                <h:inputText id="input1" value="#{helloWorldBacking.name}" required="true"/>
                <h:commandButton id="button1" value="press me" action="#{helloWorldBacking.send}"/>
                <h:message id="message1" for="input1"/>
              </h:panelGrid>
            </h:form>
        </f:view>
    </body>
</html>
