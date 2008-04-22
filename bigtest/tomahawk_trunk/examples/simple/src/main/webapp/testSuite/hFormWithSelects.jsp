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

<f:view>
  <t:document>
    <t:documentHead>
      <t:htmlTag value="title">
        <h:outputText value="Test #{view.viewId}" />
      </t:htmlTag>
    </t:documentHead>
    <t:documentBody>
      <h:outputText value="Running test #{view.viewId}" />
      <t:htmlTag value="br" />
      <t:htmlTag value="br" />

      <!-- Begin test -->

      <!-- Testing h:selectBooleanCheckbox -->
      <h:form id="selectBooleanCheckboxForm" >
        <h:selectBooleanCheckbox id="hSelectBooleanCheckboxFalse" value="no" />
        <h:selectBooleanCheckbox id="hSelectBooleanCheckboxTrue" value="true" />
      </h:form>

      <!-- Testing h:selectManyCheckbox -->
      <h:form id="selectManyCheckboxForm" >
        <h:selectManyCheckbox id="hSelectManyCheckbox" >
          <f:selectItem id="cb_1" itemLabel="Check Box 1" itemValue="CB1"/>
          <f:selectItem id="cb_2" itemLabel="Check Box 2" itemValue="CB2" itemDisabled="true"/>
          <f:selectItem id="cb_3" itemLabel="Check Box 3" itemValue="CB3"/>
          <f:selectItem id="cb_4" itemLabel="Check Box 4" itemValue="CB4"/>
          <f:selectItem id="cb_5" itemLabel="Check Box 5" itemValue="CB5"/>
        </h:selectManyCheckbox>
      </h:form>

      <h:form id="selectManyCheckboxPageDirectionForm">
        <h:selectManyCheckbox id="hSelectManyCheckbox" layout="pageDirection">
          <f:selectItem id="cb_1" itemLabel="Check Box 1" itemValue="CB1"/>
          <f:selectItem id="cb_2" itemLabel="Check Box 2" itemValue="CB2" itemDisabled="true"/>
          <f:selectItem id="cb_3" itemLabel="Check Box 3" itemValue="CB3"/>
          <f:selectItem id="cb_4" itemLabel="Check Box 4" itemValue="CB4"/>
          <f:selectItem id="cb_5" itemLabel="Check Box 5" itemValue="CB5"/>
        </h:selectManyCheckbox>
      </h:form>

       <h:form id="selectManyCheckboxLineDirectionForm" >
        <h:selectManyCheckbox id="hSelectManyCheckbox" layout="lineDirection" border="3">
          <f:selectItem id="cb_1" itemLabel="Check Box 1" itemValue="CB1"/>
          <f:selectItem id="cb_2" itemLabel="Check Box 2" itemValue="CB2" itemDisabled="true"/>
          <f:selectItem id="cb_3" itemLabel="Check Box 3" itemValue="CB3"/>
          <f:selectItem id="cb_4" itemLabel="Check Box 4" itemValue="CB4"/>
          <f:selectItem id="cb_5" itemLabel="Check Box 5" itemValue="CB5"/>
        </h:selectManyCheckbox>
      </h:form>

      <h:form id="selectManyListBoxForm">
         <h:selectManyListbox id="hSelectManyListbox" size="2" enabledClass="enabled_class" disabledClass="disable_class" onselect="onSelect" accesskey="A">
          <f:selectItem id="lb_1" itemLabel="Listbox Item 1" itemValue="LB1"/>
          <f:selectItem id="lb_2" itemLabel="Listbox Item 2" itemValue="LB2" itemDisabled="true"/>
          <f:selectItem id="lb_3" itemLabel="Listbox Item 3" itemValue="LB3"/>
          <f:selectItem id="lb_4" itemLabel="Listbox Item 4" itemValue="LB4"/>
          <f:selectItem id="lb_5" itemLabel="Listbox Item 5" itemValue="LB5"/>
         </h:selectManyListbox>
      </h:form>
      
      <h:form id="selectManyMenuForm" >
         <h:selectManyMenu id="hSelectManyMenu" onselect="onSelect" accesskey="A">
          <f:selectItem id="mi_1" itemLabel="Menu Item 1" itemValue="MI1"/>
          <f:selectItem id="mi_2" itemLabel="Menu Item 2" itemValue="MI2" itemDisabled="true"/>
          <f:selectItem id="mi_3" itemLabel="Menu Item 3" itemValue="MI3"/>
          <f:selectItem id="mi_4" itemLabel="Menu Item 4" itemValue="MI4"/>
          <f:selectItem id="mi_5" itemLabel="Menu Item 5" itemValue="MI5"/>
         </h:selectManyMenu>
      </h:form>
      
      <h:form id="selectOneListBoxForm" >
         <h:selectOneListbox id="hSelectOneListbox" size="10" onselect="onSelect" accesskey="A">
          <f:selectItem id="lb_1" itemLabel="Listbox Item 1" itemValue="LB1"/>
          <f:selectItem id="lb_2" itemLabel="Listbox Item 2" itemValue="LB2" itemDisabled="true"/>
          <f:selectItem id="lb_3" itemLabel="Listbox Item 3" itemValue="LB3"/>
          <f:selectItem id="lb_4" itemLabel="Listbox Item 4" itemValue="LB4"/>
          <f:selectItem id="lb_5" itemLabel="Listbox Item 5" itemValue="LB5"/>
         </h:selectOneListbox>
      </h:form>

      <h:form id="selectOneMenuForm" >
         <h:selectOneMenu id="hSelectOneMenu" onselect="onSelect" accesskey="A">
          <f:selectItem id="mi_1" itemLabel="Menu Item 1" itemValue="MI1"/>
          <f:selectItem id="mi_2" itemLabel="Menu Item 2" itemValue="MI2" itemDisabled="true"/>
          <f:selectItem id="mi_3" itemLabel="Menu Item 3" itemValue="MI3"/>
          <f:selectItem id="mi_4" itemLabel="Menu Item 4" itemValue="MI4"/>
          <f:selectItem id="mi_5" itemLabel="Menu Item 5" itemValue="MI5"/>
         </h:selectOneMenu>
      </h:form>

      <h:form id="selectOneRadioForm" >
         <h:selectOneRadio id="hSelectOneRadio">
          <f:selectItem id="rb_1" itemLabel="Radio Item 1" itemValue="RB1"/>
          <f:selectItem id="rb_2" itemLabel="Radio Item 2" itemValue="RB2" itemDisabled="true"/>
          <f:selectItem id="rb_3" itemLabel="Radio Item 3" itemValue="RB3"/>
          <f:selectItem id="rb_4" itemLabel="Radio Item 4" itemValue="RB4"/>
          <f:selectItem id="rb_5" itemLabel="Radio Item 5" itemValue="RB5"/>
         </h:selectOneRadio>
      </h:form>
       
      
      <!-- End test -->

      <t:htmlTag value="br" />
      <t:htmlTag value="br" />
      <h:form id="footerLinks">
        <h:commandLink id="homeLink" action="home" value="Home" />
        <h:outputText value=" " />
        <h:outputLink id="sourceLink" target="sourceWindow"
          value="#{facesContext.externalContext.requestContextPath}#{view.viewId}.source">
          <h:outputText value="View source in popup window" />
        </h:outputLink>
      </h:form>
    </t:documentBody>
  </t:document>
</f:view>
