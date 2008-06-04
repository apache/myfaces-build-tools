<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<html>

    <%@include file="inc/head.inc" %>
    <body>

        <f:view>
        <h:form>

            <f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" var="example_messages"/>
            <f:loadBundle basename="org.apache.myfaces.examples.resource.build" var="buildInfo"/>

            <h:panelGrid id="header_group1" columns="2" styleClass="pageHeader"  >
                <t:graphicImage id="header_logo" url="images/logo_mini.jpg" alt="#{example_messages['alt_logo']}" />
                <f:verbatim>
                    <h:outputText style="font-size:20px;color:#FFFFFF" escape="false" value="MyFaces - The free JavaServer&#8482; Faces Implementation"/>
                    <h:outputText style="font-size:10px;color:#FFFFFF" value=" (Tomahawk Version #{buildInfo['tomahawk_version']}, using #{buildInfo ['jsf_implementation']})"/>
                </f:verbatim>
            </h:panelGrid>

            <h:panelGrid>
                <h:outputLink value="sample1.jsf" ><f:verbatim>Sample 1</f:verbatim></h:outputLink>
                <h:outputLink value="sample2.jsf" ><f:verbatim>Sample 2</f:verbatim></h:outputLink>
                <h:outputLink value="displayValueOnly.jsf" ><f:verbatim>displayValueOnly attribute</f:verbatim></h:outputLink>
                <h:outputLink value="forceId.jsf" ><f:verbatim>forceId</f:verbatim></h:outputLink>
                <h:outputLink value="validate.jsf" ><f:verbatim>Validations</f:verbatim></h:outputLink>
                <h:outputLink value="aliasBean.jsf" ><f:verbatim>Alias Bean</f:verbatim></h:outputLink>
                <h:outputLink value="buffer.jsf" ><f:verbatim>Buffer</f:verbatim></h:outputLink>
                <h:outputLink value="div.jsf" ><f:verbatim>Div</f:verbatim></h:outputLink>
				<h:outputLink value="filteredStylesheet.jsf" ><f:verbatim>Filtered Stylesheet</f:verbatim></h:outputLink>
                <h:outputText value="Data Table"/>
                <h:panelGrid style="padding-left:25px">
                    <h:outputLink value="masterDetail.jsf" ><f:verbatim>Master-Detail</f:verbatim></h:outputLink>
                    <h:outputLink value="dataScroller.jsf" ><f:verbatim>Data Scroller</f:verbatim></h:outputLink>
                    <h:outputLink value="sortTable.jsf" ><f:verbatim>Sortable</f:verbatim></h:outputLink>
                    <h:outputLink value="sortAutoTable.jsf" ><f:verbatim>Automatically sortable by all columns</f:verbatim></h:outputLink>
                    <h:outputLink value="sortAutoTable2.jsf" ><f:verbatim>Automatically sortable by choosen columns</f:verbatim></h:outputLink>
                    <h:outputLink value="pagedSortTable.jsf" ><f:verbatim>Paged and Sortable</f:verbatim></h:outputLink>
                    <h:outputLink value="openDataTable.jsf" ><f:verbatim>Paged and Sortable (dynamic number of columns; mouseover)</f:verbatim></h:outputLink>
                    <h:outputLink value="crossDataTable.jsf" ><f:verbatim>Dynamic number of columns, add a column</f:verbatim></h:outputLink>
                    <h:outputLink value="optDataTable.jsf" ><f:verbatim>Optional Header/Footer</f:verbatim></h:outputLink>
                    <h:outputLink value="simpleGroupBy.jsf" ><f:verbatim>Group by columns</f:verbatim></h:outputLink>
                	<h:outputLink value="newspaperTable.jsf" ><f:verbatim>Newspaper Table</f:verbatim></h:outputLink>
                	<h:outputLink value="colspan.jsf" ><f:verbatim>Colspan</f:verbatim></h:outputLink>
                </h:panelGrid>
                <h:outputLink value="selectbox.jsf" ><f:verbatim>Select boxes</f:verbatim></h:outputLink>
                <h:outputLink value="fileupload.jsf" ><f:verbatim>File upload</f:verbatim></h:outputLink>
                <h:outputLink value="tabbedPane.jsf" ><f:verbatim>Tabbed Pane</f:verbatim></h:outputLink>
                <h:outputLink value="calendar.jsf" ><f:verbatim>Calendar</f:verbatim></h:outputLink>
                <h:outputLink value="popup.jsf" ><f:verbatim>Popup</f:verbatim></h:outputLink>
                <h:outputText value="Menus"/>
                <h:panelGrid style="padding-left:25px">
                    <h:outputLink value="jscookmenu.jsf" ><f:verbatim>JSCookMenu</f:verbatim></h:outputLink>
                    <h:outputLink value="panelnavigation_1.jsf" ><f:verbatim>PanelNavigation Classic (Static JSP)</f:verbatim></h:outputLink>
                    <h:outputLink value="panelnavigation_2.jsf" ><f:verbatim>PanelNavigation with NavigationMenuItems (Dynamic)</f:verbatim></h:outputLink>
                    <h:outputLink value="panelnavigation_4.jsf" ><f:verbatim>Horizontal PanelNavigation</f:verbatim></h:outputLink>
                    <h:outputLink value="panelnavigation_5.jsf" ><f:verbatim>PanelNavigation Item toggles activ/open depending on ViewId</f:verbatim></h:outputLink>
                </h:panelGrid>
                <h:outputLink value="jslistener.jsf" ><f:verbatim>Javascript Listener</f:verbatim></h:outputLink>
                <h:outputLink value="date.jsf" ><f:verbatim>Date</f:verbatim></h:outputLink>
                <h:outputLink value="inputTextHelp.jsf" ><f:verbatim>InputTextHelp</f:verbatim></h:outputLink>
                <h:outputLink value="inputHtml.jsf" ><f:verbatim>Html Editor</f:verbatim></h:outputLink>
                <h:outputLink value="htmlTag.jsf" ><f:verbatim>Html Tag</f:verbatim></h:outputLink>
                <h:outputLink value="dataList.jsf" ><f:verbatim>Dynamic Lists</f:verbatim></h:outputLink>
                <h:outputLink value="selectItems.jsf" ><f:verbatim>Auto-generated SelectItems</f:verbatim></h:outputLink>
                <h:outputLink value="selectOneCountry.jsf" ><f:verbatim>Select one Country</f:verbatim></h:outputLink>
                <h:outputLink value="selectOneLanguage.jsf" ><f:verbatim>Select one Language</f:verbatim></h:outputLink>
                <h:outputLink value="tree.jsf" ><f:verbatim>Tree</f:verbatim></h:outputLink>
                <h:outputLink value="treeTable.jsf" ><f:verbatim>Tree Table</f:verbatim></h:outputLink>
                <h:outputText value="Tree2"/>
                <h:panelGrid style="padding-left:25px">
                    <h:outputLink value="tree2.jsf" ><f:verbatim>Tree2 (client-side toggle, server-side toggle)</f:verbatim></h:outputLink>
                    <h:outputLink value="tree2HideRoot.jsf" ><f:verbatim>Tree2 (hide root node)</f:verbatim></h:outputLink>
                    <h:outputLink value="tree2NiceWrap.jsf" ><f:verbatim>Tree2 (nice wrap)</f:verbatim></h:outputLink>
                    <h:outputLink value="tree2ExpandAll.jsf" ><f:verbatim>Tree2 (expand all)</f:verbatim></h:outputLink>
                    <h:outputLink value="tree2NoNav.jsf" ><f:verbatim>Tree2 (no nav icons)</f:verbatim></h:outputLink>
                </h:panelGrid>
                <h:outputLink value="panelstack.jsf" ><f:verbatim>Panel Stack</f:verbatim></h:outputLink>
                <h:outputLink value="css.jsf" ><f:verbatim>Style Sheet</f:verbatim></h:outputLink>
                <h:outputLink value="swapimage.jsf" ><f:verbatim>Swap Image</f:verbatim></h:outputLink>
                <h:outputLink value="collapsiblePanel.jsf" ><f:verbatim>Collapsible Panel</f:verbatim></h:outputLink>
                <h:outputLink value="testExceptions.jsf" ><f:verbatim>Test the custom error page - not really jsf stuff, but a demonstration how to print the real exception</f:verbatim></h:outputLink>
				<h:outputLink value="panelGroup.jsf" ><f:verbatim>Panel Group</f:verbatim></h:outputLink>
                <h:outputText value="Dojo Integration" />
                <h:panelGrid style="padding-left:25px">
                	<h:outputLink value="dojo/dojoimporttest.jsf">
                		<f:verbatim>Dojo Library Import via t:dojoInitializer</f:verbatim>
                	</h:outputLink>
                	<h:outputLink value="dojo/textareatestjsfonly.jsf">
                		<f:verbatim>Simple Integration of the Dojo Toolkit example</f:verbatim>
                	</h:outputLink>
                	<h:outputLink value="dojo/debugconsolejsfonly.jsf">
                		<f:verbatim>The dojo Debug Console</f:verbatim>
                	</h:outputLink>
                </h:panelGrid>
                <h:outputText value="Schedule" />
                <h:panelGrid style="padding-left:25px">
                    <h:outputLink value="schedule1.jsf">
                        <f:verbatim>Schedule with sample entries</f:verbatim>
                    </h:outputLink>
                    <h:outputLink value="schedule2.jsf">
                        <f:verbatim>Schedule with possibility for adding/removing entries</f:verbatim>
                    </h:outputLink>
                    <h:outputLink value="schedule3.jsf">
                        <f:verbatim>Customizable schedule</f:verbatim>
                    </h:outputLink>
                    <h:outputLink value="schedule4.jsf">
                        <f:verbatim>Schedule with custom styleClasses and custom EntryRenderer</f:verbatim>
                    </h:outputLink>
                    <h:outputLink value="schedule5.jsf">
                        <f:verbatim>Example demonstrating the submitOnClick and mouseListener properties</f:verbatim>
                    </h:outputLink>
                </h:panelGrid>

                <h:outputText value="Behaviours controlled by init params" />
                <h:panelGrid style="padding-left:25px">
                    <h:outputLink value="autoscroll.jsf">
                        <f:verbatim>AutoScroll (org.apache.myfaces.AUTO_SCROLL)</f:verbatim>
                    </h:outputLink>
                </h:panelGrid>


        </h:panelGrid>

		</h:form>
        </f:view>
    </body>
</html>
