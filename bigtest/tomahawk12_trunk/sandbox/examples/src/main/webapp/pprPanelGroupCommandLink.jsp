<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>
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

<%@ include file="inc/head.inc"%>


<body>
<h1>PPR Example - using a command Link for triggering an action via AJAX</h1>
<span id="cnt">0</span>
seconds since last page refresh.
<script>
    var sec=0;
    function counter(){
        setTimeout("counter();",1000);
        document.getElementById("cnt").innerHTML = sec++;
    }
    counter();
</script>

<f:view>

    <h:form id="mainform">

        <t:commandLink id="searchLink" action="#{pprExampleBean.searchNames}" value="Filter the list"/>
        <f:verbatim><br /></f:verbatim>
        <f:verbatim><br /></f:verbatim>

        <h:outputText value="Filter the Names:" />
        <h:inputText value="#{pprExampleBean.textField}" />

        <f:verbatim><br /></f:verbatim>
        <f:verbatim><br /></f:verbatim>

        <s:pprPanelGroup id="nameList" partialTriggers="searchLink">
            <t:dataList var="name" value="#{pprExampleBean.names}" layout="list">
                <h:outputText value="#{name}<br />" escape="false"/>
            </t:dataList>
        </s:pprPanelGroup>

        <s:fieldset legend="about this example">
            <f:verbatim>
                 <br />
                 <br />
                This example demonstrates that also actions can be invoked via AJAX <br />
                when using PPR.
            </f:verbatim>
        </s:fieldset>
    </h:form>


</f:view>

<%@include file="inc/page_footer.jsp"%>

</body>

</html>


