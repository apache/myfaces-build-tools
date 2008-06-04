<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>
<html>

<!--
/*
 * Copyright 2004-2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//-->

<%@include file="inc/head.inc" %>

<body>

<f:view>

    <h:form>
	    <f:verbatim>
	    This component demonstrates use of the convertStringUtils converter. The converter can operate on strings and perform
	    some basic manipulation including change to upper and lower case, capitalize the string, trim the string and truncate
	    a string at a fixed length.
	    </f:verbatim>
	    <t:htmlTag value="br"/>
	    <t:htmlTag value="br"/>
        <h:outputText value="Input a long line of text: "/>
        <h:inputText value="#{convertStringUtilsBean.text}" size="48" />        
        <h:commandButton value="Submit" action="none"/>        
	    <t:htmlTag value="br"/>
	    <t:htmlTag value="br"/>
	    <t:htmlTag value="br"/>
	    <t:htmlTag value="br"/>
	    <h:outputText value="Raw text: " />
	    <h:outputText value="#{convertStringUtilsBean.text}" />
	    <t:htmlTag value="br"/>
	    <h:outputText value="Changed to upper case: " />
	    <h:outputText value="#{convertStringUtilsBean.text}" >
	    	<s:convertStringUtils format="uppercase" trim="true" />
	    </h:outputText>
	    <t:htmlTag value="br"/>
	    <h:outputText value="Changed to lower case: " />
	    <h:outputText value="#{convertStringUtilsBean.text}" >
	    	<s:convertStringUtils format="lowercase" trim="true" />
	    </h:outputText>
	    <t:htmlTag value="br"/>
	    <h:outputText value="Truncated to 40 chars: " />
	    <h:outputText value="#{convertStringUtilsBean.text}" >
	    	<s:convertStringUtils trim="true" maxLength="40" appendEllipsesDuringOutput="true"/>
	    </h:outputText>	    
    </h:form>
</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
