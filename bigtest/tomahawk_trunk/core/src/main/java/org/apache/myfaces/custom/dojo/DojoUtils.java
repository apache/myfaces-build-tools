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

package org.apache.myfaces.custom.dojo;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

/**
 * Utils class for the dojo infrastructure to ease the component building
 * mechanisms note this class uses its own double entries filter due to the fact
 * that we can mix and match header and body scripts as needed (we do not want
 * to lose portal functionality do we?)
 *
 * @author Werner Punz (latest modification by $Author$)
 * @version $Revision$ $Date: 2006-09-08 20:56:28 +0000 (Fri, 08 Sep
 *          2006) $
 */
public final class DojoUtils {
    private static final String MYFACES_DOJO_DEBUGCONSOLE_ID     = "myfaces_Dojo_Debugger";

    private static final String DEBUG_CONSOLE_TYPE               = "DebugConsole";

    private static final String LAYOUT_ALIGN_ATTR                = "layoutAlign";

    private static final String DISPLAY_CLOSE_ACTION_ATTR        = "displayCloseAction";

    private static final String RESIZABLE_ATTR                   = "resizable";

    private static final String HAS_SHADOW_ATTR                  = "hasShadow";

    private static final String CONSTRAIN_TO_CONTAINER_ATTR      = "constrainToContainer";

    private static final String ICON_SRC_ATTR                    = "iconSrc";

    private static final String TITLE_ATTR                       = "title";

    private static final String INCL_TYPE_REQ_KEY                = "DOJO_DEVELOPMENT_INCLUDE";

    private static final Log    log                              = LogFactory.getLog(DojoUtils.class);

    private static final String DOJO_PROVIDE                     = "dojo.provide:";

    private static final String DOJO_REQUIRE                     = "dojo.require:";

    private static final String DOJO_NAMESPACE                   = "dojo.namespace:";

    private static final String DJCONFIG_INITKEY                 = "/*djconfig init*/";

    private static final String BODY_SCRIPT_INFOS_ATTRIBUTE_NAME = "bodyScriptInfos";

    private static final String DOJO_FILE_UNCOMPRESSED           = "dojo.js.uncompressed.js";

    private static final String DOJO_FILE                        = "dojo.js";

    private static final String DJCONFIG_REQ_KEY                 = "MYFACES_DJCONFIG";

    private static final String DOJOEXTENSIONS_NAMESPACE         = "dojo.setModulePrefix('extensions', '../dojoextensions.ResourceLoader');";

    private DojoUtils() {
        // nope
    }

    /**
     * creates a dojoed attribute map upon the given array of attribute names
     *
     * @param facesContext
     *            standard faces context used internally
     * @param attributeNames
     *            string array of traversable attribute names
     * @param component
     *            the source component with the values set
     * @return a map which resembles the attribute map for further processing
     */
    public static Map getAttributeMap(FacesContext facesContext, String[] attributeNames, UIComponent component) {
        Log log = null;

        Class componentClass = component.getClass();
        Map returnMap = new HashMap();
        for (int cnt = 0; cnt < attributeNames.length; cnt++) {
            try {
                String attributeName = attributeNames[cnt];
                //the dojo attributes deal with different ids than the rest
                if (attributeName.equals("id") || attributeName.equals("widgetId")) {
                    String calculatedId = DojoUtils.calculateWidgetId(facesContext, component);
                    returnMap.put("id", calculatedId);
                } else {
                    String attributeCasedName = attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
                    String getForm = "get" + attributeCasedName; // to prevent multiple creating in finding
                    String isForm = "is" + attributeCasedName; // to prevent multiple creating in finding
                    Method m = null;
                    // finding getter in hiearchy: try current class for "get" and "is" form, if NOT found, try superclass
                    // doesn't use getMethod() to avoid walking whole hiearchy for wrong form in case of "is"
                    // and getMethod() uses getSuperClass() anyway, so no performance hit this way + ability to invoke protected methods
                    while ((componentClass != null) && (m == null)) {
                        m = componentClass.getDeclaredMethod(getForm, null);
                        if (m == null) {
                            // try alternative method name for dealing with Booleans
                         m = componentClass.getDeclaredMethod(isForm, null);
                        }
                        if (m == null) {
                            // load superclass
                            componentClass = componentClass.getSuperclass(); // null in case of componentClass == Object
                        }
                    }
                    if (m != null) {
                        Object execRetval = m.invoke(component, null);
                        if (execRetval != null)
                            returnMap.put(attributeName, execRetval);
                    }
                }
            } catch (Exception e) {
                if (log == null)
                    log = LogFactory.getLog(DojoUtils.class);
                // this should not happen but can
                log.error("getAttributeMap", e);
            }
        }
        return returnMap;
    }

    /**
     * adds a debug console to the output this is for helping to debug the dojo
     * system a debug:true is required for this to work properly it will not be
     * set by this method (due to the avoidance of unwanted automatisms causing
     * sideefects)
     *
     * @param facesContext
     * @param component
     * @return
     */
    public static void addDebugConsole(FacesContext facesContext, UIComponent component) throws IOException {

        /* check whether we have a debugging flag already set */
        if (isInlineScriptSet(facesContext, "/*DOJO DEBUGCONSOLE ON*/"))
            return;

        AddResource addResource = AddResourceFactory.getInstance(facesContext);
        addResource.addInlineScriptAtPosition(facesContext, AddResource.HEADER_BEGIN, "/*DOJO DEBUGCONSOLE ON*/");

        ResponseWriter writer = facesContext.getResponseWriter();
        // we for now have to break html until the dynamic creation
        // isses are resolved, so hold on for this messy code now
        // Since this is for debugging purposes only, we can live with it

        writer.startElement(HTML.DIV_ELEM, component);
        writer.writeAttribute(HTML.ID_ATTR, MYFACES_DOJO_DEBUGCONSOLE_ID, null);
        writer.writeAttribute(HTML.STYLE_ATTR, "width: 400px; height: 500px; left: 200px;", null);
        writer.endElement(HTML.DIV_ELEM);

        Map attributeMap = new HashMap();
        attributeMap.put(TITLE_ATTR, "MyFaces Dojo Debug console");
        attributeMap.put(ICON_SRC_ATTR, "images/flatScreen.gif");
        attributeMap.put(CONSTRAIN_TO_CONTAINER_ATTR, new Integer(1));
        attributeMap.put(HAS_SHADOW_ATTR, new Boolean(true));
        attributeMap.put(RESIZABLE_ATTR, new Boolean(true));
        attributeMap.put(DISPLAY_CLOSE_ACTION_ATTR, new Boolean(true));
        attributeMap.put(LAYOUT_ALIGN_ATTR, "client");
        renderWidgetInitializationCode(writer, component, DEBUG_CONSOLE_TYPE, attributeMap, MYFACES_DOJO_DEBUGCONSOLE_ID, true);
    }

	/**
	 * check if dojo is going to be used 
	 */
	public static boolean isDojoInitialized(FacesContext facesContext)
	{
		return isInlineScriptCheck(facesContext, DJCONFIG_INITKEY);
	}

	public static void addMainInclude(FacesContext facesContext, UIComponent component, String javascriptLocation, DojoConfig config) throws IOException {

        AddResource addResource = AddResourceFactory.getInstance(facesContext);

        /*
         * var djConfig = { isDebug: false }; TODO add a saner handling of
         * collecting all djconfig data and then merging it
         */
        if (!isInlineScriptSet(facesContext, DJCONFIG_INITKEY)) {
            addResource.addInlineScriptAtPosition(facesContext, AddResource.HEADER_BEGIN, DJCONFIG_INITKEY);
            addResource.addInlineScriptAtPosition(facesContext, AddResource.HEADER_BEGIN, config.toString());

            String dojofile = ((getExpanded(facesContext) != null) && getExpanded(facesContext).booleanValue()) ? DOJO_FILE_UNCOMPRESSED : DOJO_FILE;

            if (javascriptLocation != null) {
                addResource.addJavaScriptAtPosition(facesContext, AddResource.HEADER_BEGIN, javascriptLocation + dojofile);
            } else {
                /*
                 * ResponseWriter writer = facesContext.getResponseWriter();
                 * writer.startElement(HTML.SCRIPT_ELEM,component);
                 *
                 * MyFacesResourceHandler handler = new
                 * MyFacesResourceHandler(DojoResourceLoader.class, dojofile);
                 * String uri = handler.getResourceUri(facesContext); uri =
                 * uri.replaceAll("dojo\\.js\\;jsessionid(.)*\\\"","dojo.js");
                 * writer.writeAttribute(HTML.SRC_ATTR, uri, null);
                 *
                 * writer.endElement(HTML.SCRIPT_ELEM);
                 * addResource.addJavaScriptAtPosition(facesContext,
                 * AddResource.HEADER_BEGIN, DojoResourceLoader.class,
                 * dojofile);
                 */

                addResource.addJavaScriptAtPositionPlain(facesContext, AddResource.HEADER_BEGIN, DojoResourceLoader.class, dojofile);
            }
            addResource.addInlineScriptAtPosition(facesContext, AddResource.HEADER_BEGIN, DOJOEXTENSIONS_NAMESPACE);
        }
    }

    /**
     * adds a new namespace location to the mix
     *
     * @param facesContext
     *            the faces context which is used internally
     * @param component
     *            the affected component
     * @param namespace
     *            the namespace which has to be applied
     * @param location
     *            the script location
     * @throws IOException
     * @link http://blog.dojotoolkit.org/2006/01/11/putting-your-code-outside-of-the-dojo-source-tree
     */
    public static void addNamespace(FacesContext facesContext, UIComponent component, String namespace, String location) throws IOException {

        if (isInlineScriptSet(facesContext, DOJO_NAMESPACE + namespace))
            return;

        String namespaceStr = createNamespaceScript(namespace, location);
        writeInlineScript(facesContext, component, namespaceStr);
    }

    /**
     * adds a dojo provide to the current list of definitions within the header
     *
     * @param context
     *            the faces context for accessing the resources internally
     * @param provided
     *            the package with the class provided by this implementation
     * @see <a
     *      href="http://dojotoolkit.org/docs/fast_widget_authoring.html">Dojo-Widget-Authoring</a>
     *      for an example on this
     */
    public static void addProvide(FacesContext context, String provided) {

        if (isInlineScriptSet(context, DOJO_PROVIDE + provided))
            return;

        AddResource addResource = AddResourceFactory.getInstance(context);
        String providedBuilder = createDojoProvideScript(provided);

        addResource.addInlineScriptAtPosition(context, AddResource.HEADER_BEGIN, providedBuilder);
    }

    /**
     * adds a dojo provide
     *
     * @param facesContext
     * @param component
     * @param provided
     * @throws IOException
     */
    public static void addProvide(FacesContext facesContext, UIComponent component, String provided) throws IOException {

        if (isInlineScriptSet(facesContext, DOJO_PROVIDE + provided))
            return;

        String providedBuilder = createDojoProvideScript(provided);

        writeInlineScript(facesContext, component, providedBuilder);

    }

    /**
     * convenience method for easier requires handling
     * @param facesContext standard faces context
     * @param component the component
     * @param requires an array of requires which is rendered into single dojo.require statements
     * @throws IOException
     */
    public static void addRequire(FacesContext facesContext, UIComponent component, String[] requires) throws IOException {
        for (int cnt = 0; cnt < requires.length; cnt++)
            addRequire(facesContext, component, requires[cnt]);
    }

    /**
     * adds a dojo require include to our mix of stuff used
     *
     * @param facesContext
     * @param required
     */
    public static void addRequire(FacesContext facesContext, UIComponent component, String required) throws IOException {

        if (isInlineScriptSet(facesContext, DOJO_REQUIRE + required))
            return;

        String requireAsScript = createDojoRequireString(required);
        writeInlineScript(facesContext, component, requireAsScript);
    }

    /**
     * creates a debug statement for the debug console
     *
     * @param stmnt
     *            the debug message displayed by the debug console
     * @return javaScriptcode String
     */
    public static String createDebugStatement(String stmnt) {
        return "dojo.debug(\"" + stmnt + "\");\n";
    }

    /**
     * creates a debug statement and a corresponding value for the debug console
     *
     * @param stmnt
     *            the debug message displayed and given value by the debug
     *            console
     * @return javaScriptcode String
     */
    public static String createDebugStatement(String stmnt, String value) {
        return "dojo.debug(\"" + stmnt + ":\");dojo.debug(" + value + ");\n";
    }

    /**
     * helper method which does the proper dojo provide script creation
     *
     * @param provided
     *            the provided class name
     * @return dojoProvide String
     */
    public static String createDojoProvideScript(String provided) {

        StringBuffer providedBuilder = new StringBuffer(32);
        providedBuilder.append("dojo.provide('");
        providedBuilder.append(provided.trim());
        providedBuilder.append("');");

        return providedBuilder.toString();
    }

    /**
     * helper method for the proper dojo require script creation
     *
     * @param required
     *            the creation package for the require functionality
     * @return dojoRequire String
     */
    public static String createDojoRequireString(String required) {
        StringBuffer requiredBuilder = new StringBuffer(32);
        requiredBuilder.append("dojo.require('");
        requiredBuilder.append(required.trim());
        requiredBuilder.append("');");

        return requiredBuilder.toString();
    }

    /**
     * Request singleton getter method for the djConfig object
     *
     * @param context
     * @return
     */
    public static DojoConfig getDjConfigInstance(FacesContext context) {

        // we wont have a need for a synchronized here, since
        // we are in a single request cycle anyway
        // but take care if you use the djconfig in multiple threads!
        DojoConfig djConfig = (DojoConfig) ((HttpServletRequest) context.getExternalContext().getRequest()).getAttribute(DJCONFIG_REQ_KEY);

        if (djConfig == null) {
            djConfig = new DojoConfig();
            ((HttpServletRequest) context.getExternalContext().getRequest()).setAttribute(DJCONFIG_REQ_KEY, djConfig);
        }

        return djConfig;
    }

    /**
     * getter for the expanded flat
     *
     * @param facesContext
     * @return
     */
    public static Boolean getExpanded(FacesContext facesContext) {
        // either the development attribute set or a special request key
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Boolean devStatus = (Boolean) request.getAttribute(INCL_TYPE_REQ_KEY);
        DojoConfig config = getDjConfigInstance(facesContext);
        if (devStatus == null)
            devStatus = new Boolean(false);
        devStatus = new Boolean(devStatus.booleanValue() || (config.getDevelopment() != null && config.getDevelopment().booleanValue()));

        return devStatus;
    }

    /**
     * Inline script set
     *
     * @param context
     *            standard faces context
     * @param inlineScript
     *            key to the inline script
     * @return true if the inline script already is set
     */
    public static boolean isInlineScriptSet(FacesContext context, String inlineScript) {

        // TODO move this non neutral code into the resource handler
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Set set = getBodyScriptInfos(request);

        if (!set.contains(inlineScript)) {
            set.add(inlineScript);

            return false;
        }

        return true;
    }

	/**
	 * check if the script with the given inlineScript-name has been added
	 */
	public static boolean isInlineScriptCheck(FacesContext context, String inlineScript)
	{
		// TODO move this non neutral code into the resource handler
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		Set set = getBodyScriptInfos(request);

		return set.contains(inlineScript);
	}

	/**
     * please, instead of using standard dojo taglib mechanisms use this code
     * for initialisation it will render a clean and proper javascript
     * initialisation instead. There are issues with ADF and the dojo taglib
     * mechanisms and also (Alex Russel wont like to hear this) the dojo taglib
     * initialisation fails on W3C validations. returns the name of the
     * javascript var for further processing
     *
     * @param facesContext
     *            standard faces context
     * @param component
     *            standard component
     * @param dojoType
     *            the dojo type of this component
     * @param paramMap
     */
    public static String renderWidgetInitializationCode(FacesContext facesContext, UIComponent component, String dojoType, Map paramMap) throws IOException {
        ResponseWriter writer = facesContext.getResponseWriter();
        String clientId = component.getClientId(facesContext);
        return renderWidgetInitializationCode(writer, component, dojoType, paramMap, clientId, true);
    }

    /**
     * convenience method to render the widget init code automatically
     * for a given component and a set of attribute names
     *
     * @param facesContext
     * @param component
     * @param dojoType
     * @param attributeNames
     * @return
     * @throws IOException
     */
    public static String renderWidgetInitializationCode(FacesContext facesContext, UIComponent component, String dojoType, String[] attributeNames)
            throws IOException {
        Map paramMap = DojoUtils.getAttributeMap(facesContext, attributeNames, component);
        return renderWidgetInitializationCode(facesContext, component, dojoType, paramMap);
    }

    /**
     * same for a given neutral id...
     *
     * @param dojoType
     * @param paramMap
     * @param clientId
     *            the referencing id which the widget has to render to (note the
     *            id is enforced the uicomponent does nothing in this case!!!!)
     * @param refId
     *            if true the refid is set in the dojo javascript init code if
     *            false no ref is set the false often is needed for containers
     *            which dynamically generated widgets with no referencing div
     * @return a string with the name of the javascript variable
     */
    public static String renderWidgetInitializationCode(ResponseWriter writer, UIComponent component, String dojoType, Map paramMap, String clientId,
            boolean refId) throws IOException {

        writer.startElement(HTML.SCRIPT_ELEM, component);
        writer.writeAttribute(HTML.TYPE_ATTR, HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);

        String javascriptVar = (String) paramMap.get("widgetVar");
        if (StringUtils.isBlank(javascriptVar))
            javascriptVar = calculateWidgetVarName(clientId);

        Iterator it = paramMap.entrySet().iterator();

        writer.write("var ");
        writer.write(javascriptVar);
        writer.write(" = ");

        writer.write("dojo.widget.createWidget(\"");
        writer.write(dojoType);
        writer.write("\",");

        writer.write("{");
        boolean first = true;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            Object value = entry.getValue();
            if (value != null) {
                if (!first)
                    writer.write(",");
                writer.write(entry.getKey().toString());
                writer.write(":"); 	// only real string values should be within
                					// ambersants, dojo req
                boolean isString = value instanceof String;
                if (isString)
                {
                    if( value.equals("true")
                        || value.equals("false") )
                        isString = false;
                }
                if (isString)
                    writer.write("'");
                writer.write(value.toString());
                if (isString)
                    writer.write("'");
                first = false;
            }

        }
        writer.write("}");
        if (refId) {
            writer.write(",dojo.byId('");
            writer.write(clientId);
            writer.write("')");
        }
        writer.write(");");

        writer.endElement(HTML.SCRIPT_ELEM);
        return javascriptVar;
    }

    /**
     * helper method to centralize the widget variable name calculation for our
     * dojo javascript widget init code
     *
     * @param clientId
     *            the client id upon which the var name has to be generated
     * @return the javascript widget var name for the given client id
     */
    public static String calculateWidgetVarName(String clientId) {
        return clientId.replaceAll("\\:", "_") + "_dojoControl";
    }

    /**
     *
     * @return
     */
    public static String calculateWidgetId(FacesContext context, UIComponent widget) {
        String widgetVarName = "";
        if (widget instanceof DojoWidget) {
            widgetVarName = ((DojoWidget) widget).getWidgetId();
        }
        if (StringUtils.isBlank(widgetVarName)) {
            widgetVarName = calculateWidgetVarName(widget.getClientId(context));
        }
        return widgetVarName;
    }


    /**
     *
     * @return
     */
    public static String calculateWidgetVarName(FacesContext context, UIComponent widget) {
        String widgetVarName = "";
        if (widget instanceof DojoWidget) {
            widgetVarName = ((DojoWidget) widget).getWidgetVar();
        }
        if (StringUtils.isBlank(widgetVarName)) {
            widgetVarName = calculateWidgetVarName(widget.getClientId(context));
        }
        return widgetVarName;
    }

    /**
     * helper to merge in an external dojo config instance the merge algorithm
     * is that an existing entry is overwritten if a new config entry is set
     * make sure that this is not called too often due to the fact that we do
     * heavy reflection in here
     *
     * @param context
     * @param config
     */
    public static void mergeExternalDjConfig(FacesContext context, DojoConfig config) {

        // we now do the same as beanutils, but for dependency reasons we code
        // it
        DojoConfig configSingleton = getDjConfigInstance(context);
        Class dcConfigClass = DojoConfig.class;
        Method[] djConfigFieldArr = dcConfigClass.getMethods();

        for (int cnt = 0; cnt < djConfigFieldArr.length; cnt++) {

            try {
                Method configPropertyField = djConfigFieldArr[cnt];
                String methodCore = null;

                if ((!configPropertyField.getName().startsWith("getClass") && configPropertyField.getName().startsWith("get"))
                        || configPropertyField.getName().startsWith("is"))
                    methodCore = (configPropertyField.getName().startsWith("get")) ? configPropertyField.getName().substring(3) : configPropertyField.getName()
                            .substring(2);

                if (methodCore != null) {
                    Object val = configPropertyField.invoke(config, null);

                    if (val != null) {
                        Class[] setterParams = new Class[1];
                        setterParams[0] = val.getClass();

                        Method setMethod = dcConfigClass.getMethod("set" + methodCore, setterParams);

                        if (setMethod != null) {
                            Object[] setterArgs = new Object[1];
                            setterArgs[0] = val;
                            setMethod.invoke(configSingleton, setterArgs);
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                log.error(e);
            } catch (SecurityException e) {
                log.error(e);
            } catch (IllegalAccessException e) {
                log.error(e);
            } catch (InvocationTargetException e) {
                log.error(e);
            } catch (NoSuchMethodException e) {
                log.error(e);
            }
        }

    }

    /**
     * if this flag is set to true somewhere before the rendering, the expanded
     * version is loaded otherwise the nonexpanded version is loaded
     *
     * @param facesContext
     *            context because we again have a full request singleton here
     * @param expanded
     *            if set to true the expanded version of the dojo scripts are
     *            loaded otherwise the non expanded ones are loaded
     */
    public static void setExpanded(FacesContext facesContext, Boolean expanded) {
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        request.setAttribute(INCL_TYPE_REQ_KEY, expanded);
    }

    /**
     * helper to write out debug statements this is only a convenience method to
     * reduce the code bloat
     *
     * @param writer
     * @param stmnt
     * @return
     * @throws IOException
     */
    public static void writeDebugStatement(ResponseWriter writer, String stmnt) throws IOException {
        stmnt = createDebugStatement(stmnt);
        writer.write(stmnt);
    }

    /**
     * dojo namespace definition method allows the definition of a new namespace
     * within the parameters of the dojo namespacing system
     *
     * @param namespace
     *            the dojo namespace
     * @param location
     *            the exaclt script location (can be a relative location)
     *
     * @return the namespace script which has to be printed / executed
     */
    private static String createNamespaceScript(String namespace, String location) {
        StringBuffer namespaceBuilder = new StringBuffer(32);
        namespaceBuilder.append("dojo.hostenv.setModulePrefix('");
        namespaceBuilder.append(namespace);
        namespaceBuilder.append("','");
        namespaceBuilder.append(location);
        namespaceBuilder.append("');");

        String namespaceStr = namespaceBuilder.toString();

        return namespaceStr;
    }

    private static Set getBodyScriptInfos(HttpServletRequest request) {
        Set set = (Set) request.getAttribute(BODY_SCRIPT_INFOS_ATTRIBUTE_NAME);

        if (set == null) {
            set = new TreeSet();
            request.setAttribute(BODY_SCRIPT_INFOS_ATTRIBUTE_NAME, set);
        }

        return set;
    }

    /**
     * helper to write an inline javascript at the exact resource location of
     * the call
     *
     * @param facesContext
     * @param component
     * @param script
     * @throws IOException
     */
    private static void writeInlineScript(FacesContext facesContext, UIComponent component, String script) throws IOException {
        ResponseWriter writer = facesContext.getResponseWriter();
        writer.startElement(HTML.SCRIPT_ELEM, component);
        writer.writeAttribute(HTML.TYPE_ATTR, HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);
        writer.write(script);
        writer.endElement(HTML.SCRIPT_ELEM);
    }

}
