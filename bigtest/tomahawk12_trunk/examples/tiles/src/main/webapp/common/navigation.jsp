<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<h:form>
<h:panelGrid columns="1" >
    <h:commandLink action="nav_page1">
        <h:outputText value="Page1" />
    </h:commandLink>
    <h:commandLink action="nav_page2">
        <h:outputText value="Page2" />
    </h:commandLink>
    <h:commandLink action="nav_page3">
        <h:outputText value="non-tiles page" />
    </h:commandLink>
    <h:commandButton value="nested Tiles" action="#{tilesBacking.pressMe}">
        <f:actionListener type="org.apache.myfaces.tiles.example.DemoActionListener"/>
    </h:commandButton>
</h:panelGrid>
</h:form>