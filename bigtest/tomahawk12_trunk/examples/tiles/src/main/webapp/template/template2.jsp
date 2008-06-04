<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@
        taglib prefix="f" uri="http://java.sun.com/jsf/core" %><%@
        taglib prefix="h" uri="http://java.sun.com/jsf/html" %><%@
        taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<table border="2">
<tr>
<td>
<f:subview id="nested1">
  <tiles:insertAttribute name="nested1" flush="false" />
</f:subview>
</td>
<td>
<f:subview id="nested2"> 
  <tiles:insertAttribute name="nested2" flush="false"/>
</f:subview>
</td>
</tr>
</table>