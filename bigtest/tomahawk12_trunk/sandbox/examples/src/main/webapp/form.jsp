<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>

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
		<h:form id="formTest">
            <h:panelGrid columns="2">
                <h:outputLabel for="input1" value="Input1: "/>
                <h:inputText id="input1"/>
                <h:outputLabel for="input2" value="Input2: "/>
                <h:inputText id="input2"/>
                <h:outputLabel for="input3" value="Input3: "/>
                <h:inputText id="input3"/>
                <h:commandLink value="test1" action="#{formBean.testAction}"/>
                <t:commandLink value="test2" action="#{formBean.testAction}"/>
                <h:commandButton value="test1" action="#{formBean.testAction}"/>
                <t:commandButton value="test2" action="#{formBean.testAction}"/>
            </h:panelGrid>
        </h:form>
        <s:form id="formtest2">
            <h:panelGrid columns="2">
                <h:outputLabel for="input1" value="Input1: "/>
                <h:inputText id="input1"/>
                <h:outputLabel for="input2" value="Input2: "/>
                <h:inputText id="input2"/>
                <h:outputLabel for="input3" value="Input3: "/>
                <h:inputText id="input3"/>
                <h:commandLink value="test1" action="#{formBean.testAction}"/>
                <t:commandLink value="test2" action="#{formBean.testAction}"/>
                <h:commandButton value="test1" action="#{formBean.testAction}"/>
                <t:commandButton value="test2" action="#{formBean.testAction}"/>
            </h:panelGrid>
        </s:form>
      </f:view>
</body>

<%@include file="inc/page_footer.jsp" %>

</html>