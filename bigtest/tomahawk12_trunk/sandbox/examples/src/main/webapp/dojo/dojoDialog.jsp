<%@ page session="false" contentType="text/html;charset=iso-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s" %>
<html>
<head>
   <meta http-equiv="Content-Type"
         content="text/html; charset=iso-8859-1"/>
   <link type="text/css" rel="stylesheet" href="/css/basic.css"/>
   <style type="text/css">
       .dojoDialog {
           background: #eee;
           border: 1px solid #999;
           -moz-border-radius: 5px;
           padding: 4px; /*positioning outside of the visible scope
to prevent some ui glitches visibility hidden does not work*/
           position: absolute;
           left: -800px;
           top: -800px;
       }

       .viewDialog {
           background: #eee;
           border: 1px solid #999;
           -moz-border-radius: 5px;
           padding: 4px; /*positioning outside of the visible scope
to prevent some ui glitches visibility hidden does not work*/
           position: absolute;
           left: -800px;
           top: -800px;
       }

       .viewDialogDecoration {
           width: 600px;
       }

       .viewDialogContent {
           width: 600px;
           height: 500px;
       }

   </style>
</head>
<body>
<f:view>

<h:form id="form">

<h:panelGrid columns="2" style="width:700px;">
   <h:outputLabel value="Email" for="email1" styleClass="label"/>
   <h:panelGrid columns="4" width="180">
       <t:inputText id="email1" value="Test" forceId="true" size="30"/>
       <f:verbatim>&#160;</f:verbatim>
       <t:message for="email1"/>
       <h:outputLink value="#" onclick="dojoDialog.show();document.getElementById('filter1').focus();">
           <h:outputText value="search"/>
       </h:outputLink>
   </h:panelGrid>
</h:panelGrid>
<h:panelGrid columns="2" style="width:700px;">
   <h:outputLabel value="Email" for="email2" styleClass="label"/>
   <h:panelGrid columns="4" width="180">
       <t:inputText id="email2" value="Test" forceId="true" size="30"/>
       <f:verbatim>&#160;</f:verbatim>
       <t:message for="email2"/>
       <h:outputLink value="#" onclick="dojoDialog.show();document.getElementById('filter2').focus();">
           <h:outputText value="search"/>
       </h:outputLink>
   </h:panelGrid>
</h:panelGrid>
<h:panelGrid columns="2" style="width:700px;">
   <h:outputLabel value="Email" for="email3" styleClass="label"/>
   <h:panelGrid columns="4" width="180">
       <t:inputText id="email3" value="Test" forceId="true" size="30"/>
       <f:verbatim>&#160;</f:verbatim>
       <t:message for="email3"/>
       <h:outputLink value="#" onclick="dojoDialog.show();document.getElementById('filter3').focus();">
           <h:outputText value="search"/>
       </h:outputLink>
       <h:outputLink value="#" onclick="dojoDialogOuterform.show();">
           <h:outputText value="searchOuterForm"/>
       </h:outputLink>

   </h:panelGrid>
</h:panelGrid>

<h:panelGrid columns="3">

   <t:outputText value="Show dialog using another view in its content"/>

   <h:outputLink value="#"
                 onclick="viewDialog.show();">
       <h:outputText value="open dialog"/>
   </h:outputLink>

   <h:commandButton id="dismissAction" value="action after dismiss"/>

</h:panelGrid>

<s:modalDialog dialogId="DialogContent" dialogVar="dojoDialog"
              dialogAttr="bgColor='white' bgOpacity='0.5' toggle='fade' toggleDuration='250'"
              hiderIds="cancel1,cancel2,cancel3" styleClass="dojoDialog">
   <h:panelGrid columns="1">
       <h:panelGrid columns="2">
           <t:inputText id="filter1" forceId="true" value="Test" size="25"/>
           <t:commandButton id="cancel1" forceId="true" value="Cancel" onclick="dojo.widget.byId('DialogContent').hide();"/>
       </h:panelGrid>
       <h:panelGrid columns="2">
           <t:inputText id="filter2" forceId="true" value="Test" size="25"/>
           <t:commandButton id="cancel2" forceId="true" value="Cancel" onclick="dojo.widget.byId('DialogContent').hide();"/>
       </h:panelGrid>
       <h:panelGrid columns="2">
           <t:inputText id="filter3" forceId="true" value="Test" size="25"/>
           <t:commandButton id="cancel3" forceId="true" value="Cancel"
 onclick="dojo.widget.byId('DialogContent').hide();"/>
       </h:panelGrid>
   </h:panelGrid>
</s:modalDialog>

<s:modalDialog dialogId="FormDialog" dialogVar="dojoDialogOuterform"
              dialogAttr="bgColor='white' bgOpacity='0.5' toggle='fade' toggleDuration='250'"
              styleClass="dojoDialog">

   <h:panelGrid columns="1">
       <h:panelGrid columns="2">
           <t:inputText id="filter1Outerform" forceId="true" value="Test"
                        size="25"/>
           <t:commandButton id="cancel1Outerform" forceId="true"
                            value="Cancel" onclick="dojo.widget.byId('FormDialog').hide();"/>
       </h:panelGrid>
       <h:panelGrid columns="2">
           <t:inputText id="filter2Outerform" forceId="true" value="Test"
                        size="25"/>
           <t:commandButton id="cancel2Outerform" forceId="true"
                            value="Cancel" onclick="dojo.widget.byId('FormDialog').hide();"/>
       </h:panelGrid>
       <h:panelGrid columns="2">
           <t:inputText id="filter3Outerform" forceId="true" value="Test"
                        size="25"/>
           <t:commandButton id="cancel3Outerform" forceId="true"
                            value="Cancel" onclick="dojo.widget.byId('FormDialog').hide();"/>
       </h:panelGrid>
       <h:commandLink id="outerformsubmit">
           <h:outputFormat value="Submit"/>
       </h:commandLink>
   </h:panelGrid>
</s:modalDialog>


<s:modalDialog
       dialogId="viewDialog"
       dialogVar="viewDialog"
       styleClass="viewDialog"
       dialogTitle="ViewId Dialog"
       closeButton="true"
       viewId="dojo/dojoDialogView.jsf">

   <s:submitOnEvent event="dialogok" for="dismissAction"/>

</s:modalDialog>


<h:outputLink value="#" onclick="updatedDojoDialog.show();">
   <h:outputText value="Updated Dialog"/>
</h:outputLink>

<s:modalDialog dialogId="updatedDialogContent"
dialogVar="updatedDojoDialog"
              dialogAttr="bgColor='white' bgOpacity='0.5' "
              hiderIds="cancel11" styleClass="dojoDialog">

   <h:panelGrid columns="1">
       <t:outputText id="msg" forceId="true"
                     value="Berechnung kann einige Minuten dauern, wollen Sie trotzdem fortsetzen?"/>
       <t:commandButton id="cancel11" value="Cancel"
                        styleClass="Btn2" onclick="dojo.widget.byId('updatedDialogContent').hide();"/>
       <t:commandButton id="ok1" value="OK" styleClass="Btn2" immediate="true"
                        action="#{dojoDialogBean.startAction}"/>


       <s:pprPanelGroup id="periodicalUpdatedArea"
                        periodicalUpdate="1000"
                        periodicalTriggers="ok1">
           <h:panelGrid columns="1"
                        style="width:300px;height:100px;text-align:center;">
               <t:outputText forceId="true" value="Berechnung lauft. Bitte warten... #{dojoDialogBean.progress}"
                             rendered="#{dojoDialogBean.actionRunning}"/>
               <t:outputText forceId="true" value="Berechnung beendet..."
                             rendered="#{!dojoDialogBean.actionRunning && dojoDialogBean.progress>0}"/>
           </h:panelGrid>
       </s:pprPanelGroup>

   </h:panelGrid>
</s:modalDialog>


<%@ include file="../inc/page_footer.jsp" %>

</h:form>

</f:view>
</body>
</html>