<%@ page session="false" contentType="text/html;charset=utf-8"%>
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
      <h:form>
        <s:imageLoop id="imageloop" forceId="true" delay="1000" minDelay="200" maxDelay="4000" transitionTime="500" width="200" height="200">
          <s:imageLoopItem url="images/imageloop1.png"/>
          <s:imageLoopItems value="#{imageLoopBean.imageCollection}"/>
          <s:imageLoopItems value="#{imageLoopBean.imageArray}"/>
        </s:imageLoop>
        <t:commandButton onclick="getImageLoop('imageloop').start(); return false" value="Start"/>
        <t:commandButton onclick="getImageLoop('imageloop').stop(); return false" value="Stop"/>
        <t:commandButton onclick="getImageLoop('imageloop').back(); return false" value="Back"/>
        <t:commandButton onclick="getImageLoop('imageloop').forward(); return false" value="Next"/>
        <t:commandButton onclick="getImageLoop('imageloop').accelerate(); return false" value="+"/>
        <t:commandButton onclick="getImageLoop('imageloop').decelerate(); return false" value="-"/>
        <t:commandButton onclick="getImageLoop('imageloop').reset(); return false" value="Reset"/>

        <t:commandButton onclick="getImageLoop('imageloop').setImageIndex(0); return false" value="1"/>
        <t:commandButton onclick="getImageLoop('imageloop').setImageIndex(1); return false" value="2"/>
        <t:commandButton onclick="getImageLoop('imageloop').setImageIndex(2); return false" value="3"/>
      </h:form>
    </f:view>

    <%@include file="inc/page_footer.jsp" %>

  </body>
</html>
