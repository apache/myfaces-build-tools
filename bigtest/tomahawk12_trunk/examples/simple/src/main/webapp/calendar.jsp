<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!--
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
//-->
<html>

<%@include file="inc/head.inc" %>

<body>

<f:view>
    <f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" var="example_messages"/>

    <t:saveState value="#{calendarBean}"/>

        <h:panelGroup id="body">

        <t:messages id="messageList" showSummary="false" showDetail="true" />

        <h:outputText id="cdt" value="#{example_messages['js_form']}"/>

        <h:form id="calendarForm">
            <t:inputCalendar monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader"
                currentDayCellClass="currentDayCell" value="#{calendarBean.firstDate}"/>
        </h:form>

        <f:verbatim><br/></f:verbatim>

        <h:outputText value="#{calendarBean.firstDate}" />

        <f:verbatim><br/><br/><br/></f:verbatim>

        <h:outputText value="#{example_messages['js_popup']}"/>

        <h:form id="calendarForm2">

            <t:outputLabel for="secondOne" value="Second calendar input"/>
            <t:inputCalendar id="secondOne" monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
                currentDayCellClass="currentDayCell" value="#{calendarBean.secondDate}" renderAsPopup="true"
                popupTodayString="#{example_messages['popup_today_string']}"
                popupDateFormat="MM/dd/yyyy" popupWeekString="#{example_messages['popup_week_string']}"
                helpText="MM/DD/YYYY"
                forceId="true"/>

            <t:inputCalendar id="thirdOne" monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
                currentDayCellClass="currentDayCell" value="#{calendarBean.secondDate}" renderAsPopup="true"
                popupTodayString="#{example_messages['popup_today_string']}"
                popupDateFormat="MM/dd/yyyy" popupWeekString="#{example_messages['popup_week_string']}"
                helpText="MM/DD/YYYY"/>

            <h:inputText value="#{calendarBean.text}"/>
            <h:commandButton value="#{example_messages['js_submit']}" action="#{calendarBean.submitMethod}" />
        </h:form>


    </h:panelGroup>
    <%--h:panelGroup id="body">


        <f:verbatim><br/></f:verbatim>

        <h:outputText value="#{calendarBean.firstDate}" />

        <f:verbatim><br/><br/><br/></f:verbatim>

        <h:outputText value="#{example_messages['js_popup']}"/>

        <h:form id="calendarForm2">

            <t:outputLabel for="secondOne" value="Second calendar input"/>
            <t:inputCalendar id="secondOne" monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
                currentDayCellClass="currentDayCell" value="#{calendarBean.secondDate}" renderAsPopup="true"
                popupTodayString="#{example_messages['popup_today_string']}" popupWeekString="#{example_messages['popup_week_string']}" helpText="MM/DD/YYYY"/>
            <h:inputText value="#{calendarBean.text}"/>
            <h:commandButton value="#{example_messages['js_submit']}" action="#{calendarBean.submitMethod}" />
        </h:form>
        
        <h:outputText value="#{calendarBean.secondDate}" />
        
        <f:verbatim><br/><br/></f:verbatim>
        
        <h:form id="calendarForm3">
            <t:inputCalendar monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader"
                currentDayCellClass="currentDayCell" value="#{calendarBean.secondDate}" renderAsPopup="true"
                popupTodayString="#{example_messages['popup_today_string']}" popupWeekString="#{example_messages['popup_week_string']}"
                renderPopupButtonAsImage="true"
                popupButtonImageUrl="/images/help.gif"/>
        </h:form>

        <h:form id="calendarForm4">

            <t:dataTable id="data"
                    styleClass="standardTable"
                    headerClass="standardTable_Header"
                    rowClasses="standardTable_Row1,standardTable_Row2"
                    columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
                    var="dateHolder"
                    value="#{calendarBean.dates}"
                    preserveDataModel="true">
                <h:column>
                    <t:inputCalendar monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader"
                        currentDayCellClass="currentDayCell" value="#{dateHolder.date}" renderAsPopup="true"
                        popupTodayString="#{example_messages['popup_today_string']}" popupWeekString="#{example_messages['popup_week_string']}" />
                </h:column>
            </t:dataTable>

            <h:commandButton value="#{example_messages['js_submit']}"/>

        </h:form>
    </h:panelGroup--%>
    <jsp:include page="inc/mbean_source.jsp"/>
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>

