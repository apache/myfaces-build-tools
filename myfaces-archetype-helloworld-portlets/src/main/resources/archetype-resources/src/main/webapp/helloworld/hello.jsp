<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<f:view>
  <html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
      <title>hello</title>
    </head>
    <body>
      <h:form>
        <h:panelGrid columns="1">
          <h:outputText value="Hello #{namebean.name}"/>
          <h:commandButton value="return" action="helloWorld"/>
        </h:panelGrid>
      </h:form>
    </body>
  </html>
</f:view>