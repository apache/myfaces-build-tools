<%@ page session="false" contentType="text/html;charset=iso-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type"
      content="text/html; charset=iso-8859-1" />
<link type="text/css" rel="stylesheet" href="/css/basic.css" />
<style type="text/css">
    html>body tbody.scrollContent {
        height: 262px;
        overflow-x:hidden;
        overflow-y: auto;
    }

    tbody.scrollContent td, tbody.scrollContent tr td {
        background: #FFF;
        padding: 2px;
    }

    tbody.scrollContent tr.alternateRow td {
        background: #e3edfa;
        padding: 2px;
    }

    tbody.scrollContent tr.selected td {
        background: yellow;
        padding: 2px;
    }
    tbody.scrollContent tr:hover td {
        background: #a6c2e7;
        padding: 2px;
    }
    tbody.scrollContent tr.selected:hover td {
        background: #ff3;
        padding: 2px;
    }

    table {
        font-family:Lucida Grande, Verdana;
        font-size:0.8em;
        width:400px;
        border:1px solid #ccc;
        border-collapse:collapse;
        cursor:default;
    }
    table td,
    table th{
        padding:2px;
        font-weight:normal;
    }
    table thead td, table thead th {
        background-image:url(images/ft-head.gif);
        background-repeat:no-repeat;
        background-position:top right;
    }
    table thead td.selectedUp, table thead th.selectedUp {
        background-image:url(images/ft-headup.gif);
    }
    table thead td.selectedDown, table thead th.selectedDown {
        background-image:url(images/ft-headdown.gif);
    }

    table tbody tr td{
        border-bottom:1px solid #ddd;
    }
    table tbody tr.alt td{
        background: #e3edfa;
    }
    table tbody tr.selected td{
        background: yellow;
    }
    table tbody tr:hover td{
        background: #a6c2e7;
    }
    table tbody tr.selected:hover td{
        background:#ff9;
    }
</style>
<script type="text/javascript">
    function manufacturerFilter(name){
        return (name.charAt(0) >= 'M' && name.charAt(0) <= 'Z');
    }
</script>
</head>
<body>
<f:view>
    <input type="button" value="Show only manufacturers between M and Z" onclick="dojo.widget.byId('filterTbl').setFilter('manufacturer', manufacturerFilter);" />
    <input type="button" value="Clear Filters" onclick="dojo.widget.byId('filterTbl').clearFilters()" />
    <s:filterTable id="filterTbl" var="car" value="#{sortableTableBean.cars}" >
        <s:sortableColumn field="id" dataType="Number" text="Id">
            <h:outputText value="#{car.id}" />
        </s:sortableColumn>
        <s:sortableColumn field="manufacturer" text="Manufacturer">
            <h:outputText value="#{car.manufacturer}" />
        </s:sortableColumn>
        <s:sortableColumn field="model" text="Model">
            <h:outputText value="#{car.model}" />
        </s:sortableColumn>
    </s:filterTable>
</f:view>

<%@ include file="inc/page_footer.jsp" %>

</body>
</html>
