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

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;


/**
 * Default component for the dojo intializer
 *
 * @JSFComponent
 *   name = "t:dojoInitializer"
 *   tagClass = "org.apache.myfaces.custom.dojo.DojoInitializerTag"
 *
 * @author Werner Punz (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DojoInitializer extends UIOutput {

    public static final String COMPONENT_TYPE        = "org.apache.myfaces.DojoInitializer";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.DojoInitializerRenderer";
    public static final String COMPONENT_FAMILY      = "javax.faces.Output";
    Boolean                    _debugConsole         = null;
    DojoConfig                 _dojoConfig           = new DojoConfig();
    Boolean                    _expanded             = null;
    String                     _provide              = null;
    String                     _require              = null;
    Boolean                    _development          = null;
    
    //we handle that specifically to speed things up (we do not want an NxN runtime complexity via enforced
    //reflection in the utils
    boolean dojoConfigParamSet = false;

    public DojoInitializer() {
        super();
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    /**
     * @JSFProperty
     */
    public Boolean getAllowQueryConfig() {
        return _dojoConfig.getAllowQueryConfig();
    }

    /**
     * @JSFProperty
     */
    public String getBaseScriptUri() {
        return _dojoConfig.getBaseScriptUri();
    }

    /**
     * @JSFProperty
     */
    public String getBindEncoding() {
        return _dojoConfig.getBindEncoding();
    }

    public String getComponentType() {
        return COMPONENT_TYPE;
    }

    /**
     * @JSFProperty
     */
    public Boolean getDebug() {
        return _dojoConfig.getDebug();
    }

    /**
     * @JSFProperty
     */
    public Boolean getDebugAtAllCosts() {
        return _dojoConfig.getDebugAtAllCosts();
    }

    /**
     * @JSFProperty
     */
    public Boolean getDebugConsole() {
        return _debugConsole;
    }

    /**
     * @JSFProperty
     */
    public String getDebugContainerId() {
        return _dojoConfig.getDebugContainerId();
    }

    public DojoConfig getDojoConfig() {
        return _dojoConfig;
    }

    /**
     * @JSFProperty
     */
    public Boolean getExpanded() {
        return _expanded;
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    /**
     * @JSFProperty
     */
    public Boolean getIgnoreClassNames() {
        return _dojoConfig.getIgnoreClassNames();
    }

    /**
     * @JSFProperty
     */
    public String getIoSendTransport() {
        return _dojoConfig.getIoSendTransport();
    }

    /**
     * @JSFProperty
     */
    public Boolean getParseWidgets() {
        return _dojoConfig.getParseWidgets();
    }

    /**
     * @JSFProperty
     */
    public Boolean getPreventBackButtonFix() {
        return _dojoConfig.getPreventBackButtonFix();
    }

    /**
     * @JSFProperty
     */
    public String getProvide() {
        return _provide;
    }

    public String getRendererType() {
        return DojoInitializerRenderer.RENDERER_TYPE;
    }

    /**
     * @JSFProperty
     */
    public String getRequire() {
        return _require;
    }

    /**
     * @JSFProperty
     */
    public String getSearchIds() {
        return _dojoConfig.getSearchIds();
    }
    
    /**
     * @JSFProperty
     */
    public Boolean getDevelopment() {
        return _dojoConfig.getDevelopment();
    }

    public Object getValue() {
        return "DojoInitializers";
    }

    public boolean isDojoConfigParamSet() {
        return dojoConfigParamSet;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        setAllowQueryConfig((Boolean) values[1]);
        setBaseScriptUri((String) values[2]);
        setBindEncoding((String) values[3]);
        setDebug((Boolean) values[4]);
        setDebugContainerId((String) values[5]);
        setIgnoreClassNames((Boolean) values[6]);
        setIoSendTransport((String) values[7]);
        setParseWidgets((Boolean) values[8]);
        setPreventBackButtonFix((Boolean) values[9]);
        setSearchIds((String) values[10]);
        _require      = (String) values[11];
        _provide      = (String) values[12];
        _debugConsole = (Boolean) values[13];
        setDebugAtAllCosts((Boolean) values[14]);
        _expanded = (Boolean) values[15];
        _development = (Boolean) values[16];
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[17];
        values[0]  = super.saveState(context);
        values[1]  = _dojoConfig.getAllowQueryConfig();
        values[2]  = _dojoConfig.getBaseScriptUri();
        values[3]  = _dojoConfig.getBindEncoding();
        values[4]  = _dojoConfig.getDebug();
        values[5]  = _dojoConfig.getDebugContainerId();
        values[6]  = _dojoConfig.getIgnoreClassNames();
        values[7]  = _dojoConfig.getIoSendTransport();
        values[8]  = _dojoConfig.getParseWidgets();
        values[9]  = _dojoConfig.getPreventBackButtonFix();
        values[10] = _dojoConfig.getSearchIds();
        values[11] = _require;
        values[12] = _provide;
        values[13] = _debugConsole;
        values[14] = _dojoConfig.getDebugAtAllCosts();
        values[15] = _expanded;
        values[16] = _development;
        
        return values;
    }

    public void setAllowQueryConfig(Boolean allowQueryConfig) {

        if (allowQueryConfig != null) {
            dojoConfigParamSet = true;
            DojoUtils.getDjConfigInstance(FacesContext.getCurrentInstance()).setAllowQueryConfig(allowQueryConfig);
        }

        _dojoConfig.setAllowQueryConfig(allowQueryConfig);

    }

    public void setBaseScriptUri(String baseScriptUri) {

        if (baseScriptUri != null) {
            dojoConfigParamSet = true;
            DojoUtils.getDjConfigInstance(FacesContext.getCurrentInstance()).setBaseScriptUri(baseScriptUri);
        }

        _dojoConfig.setBaseScriptUri(baseScriptUri);
    }

    public void setBindEncoding(String bindEncoding) {

        if (bindEncoding != null) {
            dojoConfigParamSet = true;
            DojoUtils.getDjConfigInstance(FacesContext.getCurrentInstance()).setBindEncoding(bindEncoding);
        }

        _dojoConfig.setBindEncoding(bindEncoding);
    }

    public void setDebug(Boolean debug) {

        if (debug != null) {
            dojoConfigParamSet = true;
            DojoUtils.getDjConfigInstance(FacesContext.getCurrentInstance()).setDebug(debug);
        }

        _dojoConfig.setDebug(debug);

    }

    public void setDebugAtAllCosts(Boolean debugAtAllCosts) {

        if (debugAtAllCosts != null) {
            dojoConfigParamSet = true;
            DojoUtils.getDjConfigInstance(FacesContext.getCurrentInstance()).setDebugAtAllCosts(debugAtAllCosts);
        }

        _dojoConfig.setDebugAtAllCosts(debugAtAllCosts);
    }

    public void setDebugConsole(Boolean debugConsole) {
        this._debugConsole = debugConsole;
    }

    public void setDebugContainerId(String debugContainerId) {

        if (debugContainerId != null) {
            dojoConfigParamSet = true;
            DojoUtils.getDjConfigInstance(FacesContext.getCurrentInstance()).setDebugContainerId(debugContainerId);
        }

        _dojoConfig.setDebugContainerId(debugContainerId);
    }

    public void setDojoConfigParamSet(boolean dojoConfigParamSet) {
        this.dojoConfigParamSet = dojoConfigParamSet;
    }

    public void setExpanded(Boolean expanded) {

        //we have a logical or over all expanded tags
        if (expanded != null) {
            dojoConfigParamSet = true;
            DojoUtils.setExpanded(FacesContext.getCurrentInstance(), expanded);
        }

        _expanded = expanded;
    }

    public void setIgnoreClassNames(Boolean ignoreClassNames) {

        if (ignoreClassNames != null) {
            dojoConfigParamSet = true;
            DojoUtils.getDjConfigInstance(FacesContext.getCurrentInstance()).setIgnoreClassNames(ignoreClassNames);
        }

        _dojoConfig.setIgnoreClassNames(ignoreClassNames);
    }

    public void setIoSendTransport(String ioSendTransport) {

        if (ioSendTransport != null) {
            dojoConfigParamSet = true;
            DojoUtils.getDjConfigInstance(FacesContext.getCurrentInstance()).setIoSendTransport(ioSendTransport);
        }

        _dojoConfig.setIoSendTransport(ioSendTransport);

    }

    public void setParseWidgets(Boolean parseWidgets) {

        if (parseWidgets != null) {
            dojoConfigParamSet = true;
            DojoUtils.getDjConfigInstance(FacesContext.getCurrentInstance()).setParseWidgets(parseWidgets);
        }

        _dojoConfig.setParseWidgets(parseWidgets);
    }

    public void setPreventBackButtonFix(Boolean preventBackButtonFix) {

        if (preventBackButtonFix != null) {
            dojoConfigParamSet = true;
            DojoUtils.getDjConfigInstance(FacesContext.getCurrentInstance()).setPreventBackButtonFix(preventBackButtonFix);
        }

        _dojoConfig.setPreventBackButtonFix(preventBackButtonFix);
    }

    public void setProvide(String provide) {
        this._provide = provide;
    }

    public void setRequire(String required) {
        this._require = required;
    }

    public void setSearchIds(String searchIds) {

        if (searchIds != null) {
            dojoConfigParamSet = true;
            DojoUtils.getDjConfigInstance(FacesContext.getCurrentInstance()).setSearchIds(searchIds);
        }

        _dojoConfig.setSearchIds(searchIds);
    }

 
     public void setDevelopment(Boolean development)
    {
         if (development != null) {
             dojoConfigParamSet = true;
             DojoUtils.getDjConfigInstance(FacesContext.getCurrentInstance()).setDevelopment(development);
         }
         _development = development;
    }

 
}
