﻿﻿<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<html>

    <%@include file="/inc/head.inc" %>
<body>
    <f:view>

        <h:panelGrid>
        <f:verbatim>
        Open the links in an new window/tab and see how it will start a new conversation context
        </f:verbatim>

            <h:outputText value="Conversation"/>
            <h:panelGrid style="padding-left:25px">
           		<h:outputLink value="pageConversation.jsf" ><f:verbatim>Single page conversation</f:verbatim></h:outputLink>
				<h:outputLink value="startConversation.jsf" ><f:verbatim>Start a conversation "on command"</f:verbatim></h:outputLink>
           		<h:outputLink value="wizardPage1.jsf" ><f:verbatim>Wizard</f:verbatim></h:outputLink>
            </h:panelGrid>
            
        </h:panelGrid>
    </f:view>
   
</body>
</html>
