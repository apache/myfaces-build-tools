<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" 
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
  <meta http-equiv="Content-Type" content="text/html;CHARSET=iso-8859-1" />
  <title>Myfaces - Tiles</title>
  <link rel="stylesheet" type="text/css" href="css/tiles.css" />
</head>
<f:view>
<body>
  <div id="lftBar">
    <f:subview id="menu">
      <tiles:insertAttribute name="menu" flush="false" />
    </f:subview>
  </div>
  <div id="level0">
    <div id="level1">
       <div id="topBar">
          <f:subview id="header">
            <tiles:insertAttribute name="header" flush="false"/>
          </f:subview>
       </div>
       <div id="level2">
         <f:subview id="content">
            <tiles:insertAttribute name="body" flush="false"/>
         </f:subview>
      </div>
    </div>
  </div>
</body>
</f:view>
</html>