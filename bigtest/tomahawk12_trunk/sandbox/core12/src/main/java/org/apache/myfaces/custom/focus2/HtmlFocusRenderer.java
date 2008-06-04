package org.apache.myfaces.custom.focus2;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.faces.component.ContextCallback;
/**
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.Focus2"
 *
 */
public class HtmlFocusRenderer extends Renderer
{

    protected void setSubmittedValue(UIComponent component, Object value)
    {
        ((EditableValueHolder) component).setSubmittedValue(value);
    }

    public void decode(FacesContext context, UIComponent component)
    {
        super.decode(context, component);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException
    {
        HtmlFocus focus = (HtmlFocus) component;

        String focusForId = getFocusForId(context, focus);

        ResponseWriter writer = context.getResponseWriter();

        writeForElementStore(context, component, focusForId);

        startScript(component, writer);
        if (focusForId != null && focusForId.length() > 0)
        {
            writeSetFocusScript(component, focusForId, writer);
        }
        String focusAndSubmitOnEnter = focus.getFocusAndSubmitOnEnter();
        if (focusAndSubmitOnEnter != null && focusAndSubmitOnEnter.length() > 0)
        {
            UIComponent targetComponent = focus
                    .findComponent(focusAndSubmitOnEnter);
            if (targetComponent == null)
                throw new IllegalStateException("Button component for id :"
                        + focusAndSubmitOnEnter + " not found.");
            writeSetDefaultButtonScript(component, targetComponent
                    .getClientId(context), context);
        }
        writeUpdateFocusScript(context, component);
        endScript(writer);

    }

    private void writeUpdateFocusScript(FacesContext context,
            UIComponent component) throws IOException
    {

        UIComponent formParent = getFormParent(component);

        if (formParent == null)
            throw new IllegalStateException("parent form for component: "
                    + component.getClientId(context) + " not found. ");

        String compClientId = component.getClientId(context);
        String formParentClientId = formParent.getClientId(context);

        StringBuilder b = new StringBuilder();

        b.append("\nfunction csDomTrackActiveElement(evt) {\n");
        b.append("  if (evt && evt.target) { \n");
        b.append("    if (evt.target == document) {\n");
        b.append("      document.activeElement = null;\n");
        b.append("    } else {\n");
        b.append("      if (evt.target.type != 'submit') {\n");
        b.append("        document.activeElement = evt.target; ;\n");
        b.append("      }\n");
        b.append("    }\n");
        b.append("  }\n");
        b.append("}\n");
        b.append("if (document.addEventListener) { \n");
        b
                .append("document.addEventListener('focus',csDomTrackActiveElement,true);\n");
        b.append("}\n");
        b.append("  var csFocusParentForm =document.getElementById(\"").append(
                formParentClientId).append("\");\n");
        b
                .append("  csFocusParentForm.csFocusSubmitSaver =csFocusParentForm.submit;\n");
        b
                .append("  csFocusParentForm.csFocusOnsubmitSaver =csFocusParentForm.onsubmit;\n");

        b.append("  csFocusParentForm.submit=function() {\n");
        transferId(compClientId, b);
        b.append("    if(csFocusParentForm.csFocusSubmitSaver) ");
        b.append("      return csFocusParentForm.csFocusSubmitSaver();\n");
        b.append("  }\n");
        b.append("  csFocusParentForm.onsubmit=function() {\n");
        transferId(compClientId, b);
        b.append("    if(csFocusParentForm.csFocusOnsubmitSaver) ");
        b.append("      return csFocusParentForm.csFocusOnsubmitSaver();\n");
        b.append("  }\n");

        context.getResponseWriter().write(b.toString());
    }

    private void transferId(String compClientId, StringBuilder b)
    {
        b.append("  if(document.activeElement) {\n");
        b
                .append("document.getElementById(\"")
                .append(compClientId)
                .append(
                        "\").value=document.activeElement.id?document.activeElement.id:document.activeElement.name;\n");
        b.append("  }\n");
    }

    private UIComponent getFormParent(UIComponent component)
    {
        UIComponent parent = component;

        while (parent != null && !(parent instanceof UIForm))
        {
            parent = parent.getParent();
        }

        return parent;
    }

    private void writeForElementStore(FacesContext context,
            UIComponent component, String currentFocusClientId)
            throws IOException
    {
        ResponseWriter writer = context.getResponseWriter();
        String clientId = component.getClientId(context);
        writer.startElement("input", component);
        writer.writeAttribute("type", "hidden", "type");
        writer.writeAttribute("name", clientId, "clientId");
        writer.writeAttribute("id", clientId, "clientId");
        writer.writeAttribute("value", currentFocusClientId, "defaultFocusId");
        writer.endElement("input");
    }

    private void writeSetFocusScript(UIComponent component, String clientId,
            ResponseWriter writer) throws IOException
    {
        writer.writeText("setTimeout(\"document.getElementById('" + clientId
                + "').focus()\", 100);", null);
    }

    private void writeSetDefaultButtonScript(UIComponent component,
            String clientId, FacesContext context) throws IOException
    {

        String formClientId = getFormParent(component).getClientId(context);

        StringBuilder b = new StringBuilder(3000);

        b.append("  var csFocusKeyParentForm =document.getElementById(\"")
                .append(formClientId).append("\");\n");
        b
                .append("\n\ncsFocusKeyParentForm.csFocusOnkeypressSaver =csFocusKeyParentForm.onkeypress;\n");

        b.append("csFocusKeyParentForm.onkeypress=function(evt) {\n");
        b.append("  var retValue = csSubmitOnEnter(evt,'" + formClientId
                + "','" + clientId + "'); \n");
        b.append("    if(csFocusKeyParentForm.csFocusOnkeypressSaver) {\n");
        b
                .append("      retValue =csFocusKeyParentForm.csFocusOnkeypressSaver(evt) && retValue;\n");
        b.append("    }\n");
        b.append("  return retValue;\n");
        b.append("}\n");
        b.append("\n");

        b.append("function csGetKC(evt)\n");
        b.append("{\n");
        b.append("if(window.event)\n");
        b.append("    return window.event.keyCode;\n");
        b.append("  else if(evt)\n");
        b.append("    return evt.which;\n");
        b.append("  return -1;\n");
        b.append("}\n\n");

        b.append("function csSubmitOnEnter(evt,formId,buttonId)\n");
        b.append("{\n");
        b.append("  if(window.event!=(void 0))\n");
        b.append("    evt=window.event;\n");
        b.append("  var fromElement;\n");
        b.append("  if(evt.srcElement==undefined)\n");
        b.append("    fromElement=evt.target;\n");
        b.append("  else\n");
        b.append("    fromElement=evt.srcElement;\n");
        b.append("  if(!fromElement) return true;\n");
        b.append("  if(fromElement.tagName.toUpperCase()=='A') return true;\n");
        b.append("  if((fromElement.tagName.toUpperCase()=='INPUT')&&\n");
        b.append("     (fromElement.type!='submit')&&\n");
        b.append("     (fromElement.type!='reset'))\n");
        b.append("  {\n");
        b.append("    if(csGetKC(evt)==13)\n");
        b.append("    {\n");
        b.append("      if(buttonId!=(void 0))\n");
        b.append("     {\n");
        b.append("        document.getElementById(buttonId).click();\n");
        b.append("      }\n");
        b.append("     return false;\n");
        b.append("    }\n");
        b.append("  }\n");
        b.append("  return true;\n");
        b.append("}\n");

        context.getResponseWriter().writeText(b.toString(), null);
    }

    private void endScript(ResponseWriter writer) throws IOException
    {
        writer.writeText("/*]]>*/", null);
        writer.endElement("script");
    }

    private void startScript(UIComponent component, ResponseWriter writer)
            throws IOException
    {
        writer.startElement("script", component);
        writer.writeAttribute("type", "text/javascript", null);
        writer.writeText("/*<![CDATA[*/", null);
    }

    /**
     *
     * @param context
     * @param focus
     * @return
     */
    private String getFocusForId(FacesContext context, HtmlFocus focus)
    {

        //#1: override focus-id has been set
        String forId = focus.getOverrideFocusId();
        if (forId != null && forId.length() > 0)
        {
            UIComponent targetComponent = focus.findComponent(forId);

            if (targetComponent == null)
            {
                throw new IllegalStateException("target-component for id :"
                        + forId + " not found.");
            }
            return targetComponent.getClientId(context);
        }

        //#2: error-messages have been queued
        if (focus.isFocusOnError())
        {
            Iterator it = context.getMessages();
            Iterator msgs = context.getClientIdsWithMessages();
            if (msgs.hasNext())
            {
                String clientId = (String) msgs.next();
                return clientId;
            }
        }

        //#3: a value has already been submitted, take this one
        String clientId = (String) focus.getValue();
        if (clientId != null && clientId.length() > 0)
        {
            final StringHolder nextClientId = new StringHolder();
            context.getViewRoot().invokeOnComponent(context, clientId,
                    new ContextCallback()
                    {

                        public void invokeContextCallback(FacesContext context,
                                UIComponent target)
                        {
                            String nextClientIdString = getNextValueHolder(
                                    context, target);
                            if (nextClientIdString != null)
                                nextClientId.string = nextClientIdString;
                        }
                    });

            if (nextClientId.string != null && nextClientId.string.length() > 0)
                return nextClientId.string;

            return clientId;
        }

        //#4: focus on the first (rendered) editable value holder
        if (focus.isFocusOnFirst())
        {
            UIComponent comp = getFormParent(focus);
            String firstClientId = getNextValueHolder(context, comp);
            if (firstClientId != null)
                return firstClientId;
        }

        return clientId;
    }

    private static class StringHolder
    {
        public String string;
    }

    private static String getNextValueHolder(FacesContext context,
            UIComponent comp)
    {

        final StringHolder holder = new StringHolder();

        TreeVisitor.traverseTree(context,
                new TreeVisitor.TreeTraversalListener()
                {

                    private int count = 0;

                    public boolean traverse(FacesContext context, int level,
                            UIComponent component)
                    {
                        if (count > 0)
                        {
                            if (!(component instanceof HtmlFocus)
                                    && (component instanceof EditableValueHolder && component.isRendered())
                            //&& (!(component instanceof Field) || (component instanceof Field && isVisible(component)))
                            )
                            {
                                holder.string = component.getClientId(context);
                                return false;
                            }
                        }

                        count++;
                        return true;
                    }

                    private boolean isVisible(UIComponent component)
                    {
                        //Field field = (Field) component;
                        //return (("mutable".equals(field.getDisplay()) || "required"
                        //       .equals(field.getDisplay()))
                        //        && !field.isReadonly() && !field.isDisabled())
                        //        && !"label".equals(field.getOnly());
                        return true;
                    }
                }, comp);

        return holder.string;
    }

}
