<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<f:view>
<h:outputText value="plain JSF page..."/>
<f:verbatim><br/></f:verbatim>
<h:form>
<h:commandLink action="back">
<h:outputText value="back" />
</h:commandLink>
</h:form>
</f:view>