<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>


<html>

    <%@include file="inc/head.inc" %>
<body>
    <f:view>

        <f:loadBundle basename="org.apache.myfaces.examples.resource.build" var="buildInfo"/>
    <h:form>

        <h:panelGrid>
            <h:panelGrid id="header_group1" columns="2" styleClass="pageHeader"  >
                <h:graphicImage id="header_logo" url="images/logo_mini.jpg" alt="" />
                <f:verbatim>
                    <h:outputText style="font-size:20px;color:#FFFFFF;" escape="false" value="MyFaces - The free JavaServer&#8482; Faces Implementation"/>
                    <h:outputText style="font-size:10px;color:#FFFFFF" value=" (Sandbox Version #{buildInfo['tomahawk_version']}, using #{buildInfo ['jsf_implementation']})"/>
                </f:verbatim>
            </h:panelGrid>

            <h:outputText value="New in 1.5" style="font-weight:bold;color:red"/>
            <h:panelGrid style="padding-left:25px">
                <f:verbatim><a href="#imageLoop">Image loop - slide show component</a></f:verbatim>
                <f:verbatim><a href="#partialPageRendering">Partial Page Rendering</a></f:verbatim>
                <f:verbatim><a href="#filterTable">Filter Table</a></f:verbatim>
                <f:verbatim><a href="#ClientSideValidation">Client-Side Validation</a></f:verbatim>
            </h:panelGrid>

            <f:verbatim><a name="partialPageRendering" /></f:verbatim>
            <h:outputText value="Partial Page Rendering"/>
            <h:panelGrid style="padding-left:25px">
	            <h:outputLink value="pprPanelGroupPartialTriggers.jsf" ><f:verbatim>PPRPanelGroup - parts of a page are getting manually refreshed by AJAX-Calls (basic partial Trigger example)</f:verbatim></h:outputLink>
	            <h:outputLink value="pprPanelGroupMultiple.jsf" ><f:verbatim>PPRPanelGroup - using multiple pprPanelGroups and triggering Components</f:verbatim></h:outputLink>
	            <h:outputLink value="pprPanelGroupPartialTriggerPattern.jsf" ><f:verbatim>PPRPanelGroup - update components which ids match a regular expression</f:verbatim></h:outputLink>
	            <h:outputLink value="pprPanelGroupCommandLink.jsf" ><f:verbatim>PPRPanelGroup - using a command Link for triggering an action via AJAX</f:verbatim></h:outputLink>
	            <h:outputLink value="pprPanelGroupLoadingMessage.jsf" ><f:verbatim>PPRPanelGroup - displaying an inline Loading Message during AJAX requests</f:verbatim></h:outputLink>
	            <h:outputLink value="pprPanelGroupOnChange.jsf" ><f:verbatim>PPRPanelGroup - using PPR with input-fields and event handlers (e.g. onclick, onchange, onkeyup)</f:verbatim></h:outputLink>
	            <h:outputLink value="pprPanelGroupDataScroller.jsf" ><f:verbatim>PPRPanelGroup - ajaxifying a datatable using PPR with partialTriggerPattern</f:verbatim></h:outputLink>
                <h:outputLink value="pprPanelGroupPeriodicalUpdate.jsf" ><f:verbatim>PPRPanelGroup - parts of a page are getting automatically refreshed by AJAX-Calls (through periodic intervals)</f:verbatim></h:outputLink>
                <h:outputLink value="pprPanelGroupDataTable.jsf" ><f:verbatim>PPRPanelGroup - dataTable example, server-side requested refresh example </f:verbatim></h:outputLink>
                <h:outputLink value="pprPanelGroupProcessComponents.jsf" ><f:verbatim>PPRPanelGroup - form with validate/upate-model only configured components</f:verbatim></h:outputLink>
            </h:panelGrid>
            <h:outputText value="Resource Serving"/>
            <h:panelGrid style="padding-left:25px">
	            <h:outputLink value="graphicImageDynamic.jsf" ><f:verbatim>GraphicImageDynamic - graphic image without a dedicated servlet</f:verbatim></h:outputLink>
	            <h:outputLink value="graphicImageDynamicText.jsf" ><f:verbatim>GraphicImageDynamic - text rendered as graphic image</f:verbatim></h:outputLink>
	            <h:outputLink value="outputLinkDynamic.jsf" ><f:verbatim>OutputLinkDynamic - resource serving from a link without a dedicated servlet</f:verbatim></h:outputLink>
            </h:panelGrid>

            <h:outputText value="Input Handling"/>
            <h:panelGrid style="padding-left:25px">
            	<h:outputLink value="focus.jsf"><f:verbatim>Focus - a component to set a target component as the focus on page load.</f:verbatim></h:outputLink>
                <h:outputLink value="toggleLink.jsf"><f:verbatim>Toggle - a component that allows the user to switch between Display/Edit modes</f:verbatim></h:outputLink>
            	<h:outputLink value="stateChangedNotifier.jsf"><f:verbatim>stateChangedNotifier - Shows a confirmation message if some of the fields in the form have changed</f:verbatim></h:outputLink>
            	<h:outputLink value="timedNotifier.jsf"><f:verbatim>timedNotifier - Shows a time triggered notification dialog</f:verbatim></h:outputLink>

	            <h:outputLink value="valueChangeNotifier.jsf"><f:verbatim>valueChangeNotifier - Calls a custom valueChangeEvent method during MODEL_UPDATE Phase of the Faces Lifecycle</f:verbatim></h:outputLink>
	            <h:outputLink value="form.jsf"><f:verbatim>Form component. Standard form vs. Tomahawk/Sandbox form</f:verbatim></h:outputLink>

	            <h:outputText value="Conversion"/>
	            <h:panelGrid style="padding-left:25px">
	                <h:outputLink value="dateTimeConverter.jsf"><f:verbatim>convertDateTime - a datetime converter that uses system timezone as default</f:verbatim></h:outputLink>
                    <h:outputLink value="typedNumberConvert.jsf"><f:verbatim>convertNumber - automatically convert the number to the correct type</f:verbatim></h:outputLink>
	                <h:outputLink value="convertStringUtils.jsf"><f:verbatim>convertStringUtils - a datetime converter that uses system timezone as default</f:verbatim></h:outputLink>
	            </h:panelGrid>
                <h:outputText value="Validation"/>
	            <h:panelGrid style="padding-left:25px">
	            	<h:outputLink value="validateUrl.jsf" ><f:verbatim>Validation example 2 - including URL validator</f:verbatim></h:outputLink>
	            	<h:outputLink value="validateCompareTo.jsf" ><f:verbatim>validateCompareTo - Compare values on two different components</f:verbatim></h:outputLink>
	            	<h:outputLink value="subForm.jsf"><f:verbatim>SubForm - Partial validation and model update with SubForms</f:verbatim></h:outputLink>
	            	<h:outputLink value="validateCSV.jsf"><f:verbatim>CSVValidator - validate comma separated values with a given (sub)validator</f:verbatim></h:outputLink>
	                
            		<f:verbatim><a name="ClientSideValidation" /></f:verbatim>
	                <h:outputText value="Client Side Validation"/>
		            <h:panelGrid style="padding-left:25px">
	   		            <h:outputLink value="clientValidationWithStandardForm.jsf"><f:verbatim>Conversion&Validation with Standart Form</f:verbatim></h:outputLink>
	 		            <h:outputLink value="clientValidationWithExtForm.jsf"><f:verbatim>Conversion&Validation with Extended Form</f:verbatim></h:outputLink>
		            	<h:outputLink value="clientValidation.jsf"><f:verbatim>Conversion&Validation with ValidationScript Component</f:verbatim></h:outputLink>
		            </h:panelGrid>
	            </h:panelGrid>

            </h:panelGrid>

            <h:outputText value="Layout"/>
            <h:panelGrid style="padding-left:25px">
                <h:outputLink value="dojo/splitpanejsfonly.jsf" ><f:verbatim>SplitPane - Dojos ContentPane widget to separate a page into panes of belonging content</f:verbatim></h:outputLink>
                <h:outputLink value="dojo/titlepanejsfonly.jsf" ><f:verbatim>TitlePane - Dojos TitlePane; Displays some data with a title on top. Data can be collapsed leaving only the title shown. </f:verbatim></h:outputLink>
                <h:outputLink value="accordionPanel.jsf" ><f:verbatim>AccordionPanel</f:verbatim></h:outputLink>
                <h:outputLink value="hmenu.jsf" ><f:verbatim>Horizontal Menu</f:verbatim></h:outputLink>
				<h:outputLink value="fieldset.jsf"><f:verbatim>FieldSet</f:verbatim></h:outputLink>
            </h:panelGrid>

            <h:outputText value="Input Suggest"/>
            <h:panelGrid style="padding-left:25px">
	            <h:outputLink value="inputSuggestAjax.jsf" ><f:verbatim>InputSuggestAjax - Suggested items list through Ajax</f:verbatim></h:outputLink>
                <h:outputLink value="tableSuggestAjax.jsf" ><f:verbatim>TableSuggestAjax - Suggested table through Ajax (choosing a row puts column values to specific dom nodes) </f:verbatim></h:outputLink>
                <h:outputLink value="inputAjax.jsf" ><f:verbatim>AJAX Form Components - server side validation through ajax </f:verbatim></h:outputLink>
	            <h:outputLink value="inputSuggest.jsf" ><f:verbatim>Input Suggest - Suggest without Ajax</f:verbatim></h:outputLink>
            </h:panelGrid>

            <h:outputText value="Data Tables"/>
            <h:panelGrid style="padding-left:25px">
                <f:verbatim><a name="filterTable" /></f:verbatim>
                <h:outputLink value="filterTable.jsf" ><f:verbatim>Filter Table</f:verbatim></h:outputLink>
            	<h:outputLink value="autoUpdateDataTable.jsf" ><f:verbatim>Automatically updated dataTable per AJAX</f:verbatim></h:outputLink>
            	<h:outputLink value="selectOneRow.jsf"><f:verbatim>selectOneRow - a DataTable Enhancement</f:verbatim></h:outputLink>
			<h:outputLink value="exporter.jsf"><f:verbatim>Exporter - Export datatable contents as an excel file or as a pdf file</f:verbatim></h:outputLink>
            </h:panelGrid>

            <h:outputText value="Selection Lists"/>
            <h:panelGrid style="padding-left:25px">
               <h:outputLink value="picklist.jsf"><f:verbatim>selectManyPicklist - A picklist, where you select components from a list and the selected items are displayed in another list</f:verbatim></h:outputLink>
            </h:panelGrid>

            <h:outputText value="Messages"/>
            <h:panelGrid style="padding-left:25px">
               <h:outputLink value="ifMessage.jsf"><f:verbatim>ifMessage - renders its children only if there is a message in the FacesContext for the specified component(s)</f:verbatim></h:outputLink>
            </h:panelGrid>

            <h:outputText value="FishEye Navigation"/>
            <h:panelGrid style="padding-left:25px">
               <h:outputLink value="fisheye.jsf"><f:verbatim>fishEyeNavigationMenu - the Dojo Toolkit FishEye widget</f:verbatim></h:outputLink>
            </h:panelGrid>

            <h:outputText value="Miscellaneous"/>
            <h:panelGrid style="padding-left:25px">
	 			<h:commandLink action="go_scope_shop"><f:verbatim>Scopeshop1, an extended saveState Example showing a wizard</f:verbatim></h:commandLink>
           		<h:outputLink value="effect.jsf" ><f:verbatim>Effect - DOJO and script.aculo.us effects</f:verbatim></h:outputLink>
                <f:verbatim><a name="imageLoop" /></f:verbatim><h:outputLink value="imageloop.jsf" ><f:verbatim>Image loop/slide show with fading effects based on Dojo</f:verbatim></h:outputLink>
	            <h:outputLink value="killSession.jsf"><f:verbatim>Kill Session - refreshes state</f:verbatim></h:outputLink>

            </h:panelGrid>
            <h:panelGrid style="padding-left:25px">
                <h:outputLink value="ajaxChildComboBox.jsf" >
                    <f:verbatim>Ajax-enabled combo box - reloads its contents when the value of another combo box is changed</f:verbatim>
                </h:outputLink>
            </h:panelGrid>

            <h:outputText value="Conversation"/>
            <h:panelGrid style="padding-left:25px">
           		<h:outputLink value="conversation/index.jsf" ><f:verbatim>Conversation Tag examples</f:verbatim></h:outputLink>
				<h:outputLink value="springConversation/index.jsf" ><f:verbatim>A new Spring "conversation" scope</f:verbatim></h:outputLink>
            </h:panelGrid>

			<h:outputText value="Redirect Tracker"/>
			<h:panelGrid style="padding-left:25px">
				   <h:outputLink value="redirectTracker/index.jsf" ><f:verbatim>Redirect Tracker - tries to capture the current request state and reset it after a redirect</f:verbatim></h:outputLink>
			</h:panelGrid>

			<h:outputText value="XML Template"/>
            <h:panelGrid style="padding-left:25px">
           		<h:outputLink value="template/index.jsf" ><f:verbatim>XML Template examples</f:verbatim></h:outputLink>
            </h:panelGrid>

            <h:outputText value="Modal Dialog"/>
            <h:panelGrid style="padding-left:25px">
                <h:outputLink value="dojo/dojoDialog.jsf" ><f:verbatim>A modal dialog similar to an alert or confirm or popup window</f:verbatim></h:outputLink>
            </h:panelGrid>

            <h:outputText value="Submit on event"/>
            <h:panelGrid style="padding-left:25px">
                <h:outputLink value="submitOnEventInput.jsf" ><f:verbatim>submit on event attached to input controls</f:verbatim></h:outputLink>
                <h:outputLink value="submitOnEventGlobal.jsf" ><f:verbatim>submit on a global event</f:verbatim></h:outputLink>
                <h:outputLink value="submitOnEventLink.jsf" ><f:verbatim>submit on a global event as child of a commandLink</f:verbatim></h:outputLink>
            </h:panelGrid>
            
        </h:panelGrid>

        <h:outputText value="Limit Rendered"/>
        <h:panelGrid style="padding-left:25px">
            <h:outputLink value="limitRendered.jsf" >
                <f:verbatim>Limit Rendered - limit the number of child components to render</f:verbatim>
            </h:outputLink>
        </h:panelGrid>
        
        <h:outputText value="Rounded DIV"/>
        <h:panelGrid style="padding-left:25px">
            <h:outputLink value="roundedDiv.jsf" >
                <f:verbatim>Rounded DIV - DIV with rounded corners</f:verbatim>
            </h:outputLink>
        </h:panelGrid>
        
         <h:outputText value="Password Strength"/>
         <h:panelGrid style="padding-left:25px">
	            <h:outputLink value="passwordStrength.jsf" >
	                <f:verbatim>Password Strength - component to check whether the password is strong or not</f:verbatim>
	            </h:outputLink>
         </h:panelGrid>
        
         <h:outputText value="CAPTCHA"/>
         <h:panelGrid style="padding-left:25px">
	            <h:outputLink value="captcha.jsf" >
	                <f:verbatim>CAPTCHA - component to generate random text images for security purposes</f:verbatim>
	            </h:outputLink>
         </h:panelGrid>
         
         <h:outputText value="Media"/>
         <h:panelGrid style="padding-left:25px">
	            <h:outputLink value="media.jsf" >
	                <f:verbatim>Media - component to display media content, such as audio, video, or image in a player embedded in the user agent.</f:verbatim>
	            </h:outputLink>
         </h:panelGrid>         
        
        <f:verbatim><br/><br/><br/><br/><br/><br/><br/></f:verbatim>
    </h:form>
    </f:view>

</body>
</html>
