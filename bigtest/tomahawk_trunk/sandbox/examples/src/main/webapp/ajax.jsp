<%! private void showChildren(UIComponent viewRoot, int level)
{
    System.out.println(level + " - uic: " + viewRoot.getId());
List children = viewRoot.getChildren();
    System.out.println("Children: " + children);
    if(children != null){
        for (int i = 0; i < children.size(); i++)
        {
            UIComponent uiComponent = (javax.faces.component.UIComponent) children.get(i);
            showChildren(uiComponent, level + 1);
        }
    }
}%>
<%@ page
        import="java.io.PrintWriter, javax.faces.component.UIComponent, javax.faces.context.FacesContext, javax.faces.component.UIViewRoot, java.util.List, javax.faces.application.ViewHandler, javax.faces.application.StateManager"%>
<%
    //AutoUpdateDataTableBean bean = (AutoUpdateDataTableBean) FacesContext.getCurrentInstance().getApplication().getVariableResolver().resolveVariable(FacesContext.getCurrentInstance(), "autoUpdateDataTableBean");
    String vi = request.getParameter("vi");
    String vi2 = request.getParameter("vi2");
    String vi3 = request.getParameter("vi3");

            String elname = request.getParameter("elname");
    if (elname != null) {
        System.out.println("elname: " + elname);
        System.out.println("viewId: " + vi);
        System.out.println("vi2: " + vi2);
        System.out.println("vi3: " + vi3);

        // so now lookup view with viewId, then look up component with elname (which is component id)
        FacesContext context = FacesContext.getCurrentInstance();
        ViewHandler viewHandler = context.getApplication().getViewHandler();
        UIViewRoot viewRoot = viewHandler.restoreView(context, vi3);
        if(viewRoot == null){
            viewRoot = viewHandler.createView(context, vi3);
        }
        System.out.println("viewRoot: " + viewRoot);
        UIComponent component = viewRoot.findComponent(elname);
        System.out.println("Component: " + component);
        showChildren(viewRoot, 0);

        String elvalue = request.getParameter("elvalue");
        //bean.addItem();
        response.setContentType("application/xml");
        PrintWriter o = response.getWriter();
        System.out.println("test2");
        o.println("<?xml version=\"1.0\"?>");
        o.println("<response type=\"object\" id=\"myOb\">");
        o.println("<myObject elname=\"" + elname + "\" elvalue=\"" + elvalue + "\"/>");
        o.println("</response>");

    }

%>