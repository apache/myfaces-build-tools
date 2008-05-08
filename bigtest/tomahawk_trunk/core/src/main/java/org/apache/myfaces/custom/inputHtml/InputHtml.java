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
package org.apache.myfaces.custom.inputHtml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.ext.HtmlInputText;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * HTML Editor using the kupu library.
 * http://kupu.oscom.org/
 *
 * @JSFComponent
 *   name = "t:inputHtml"
 *   tagClass = "org.apache.myfaces.custom.inputHtml.InputHtmlTag"
 *
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class InputHtml extends HtmlInputText {
    public static final String COMPONENT_TYPE = "org.apache.myfaces.InputHtml";

    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.InputHtml";

    private static final Log log = LogFactory.getLog(HtmlInputText.class);

	private String _fallback;
    private String _type;

    private Boolean _allowEditSource;
    private Boolean _allowExternalLinks;
    private Boolean _addKupuLogo;

	private Boolean _showAllToolBoxes;
    private Boolean _showPropertiesToolBox;
    private Boolean _showLinksToolBox;
    private Boolean _showImagesToolBox;
    private Boolean _showTablesToolBox;
	private Boolean _showCleanupExpressionsToolBox;
    private Boolean _showDebugToolBox;

    public InputHtml() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[4];
        values[0] = super.saveState(context);

		String[] types = new String[2];
		types[0] = _fallback;
		types[1] = _type;

        values[1] = types;

        Boolean toolBarButtons[] = new Boolean[3];
        toolBarButtons[0] = _allowEditSource;
        toolBarButtons[1] = _allowExternalLinks;
        toolBarButtons[2] = _addKupuLogo;

        values[2] = toolBarButtons;

        Boolean toolBoxes[] = new Boolean[7];
		toolBoxes[0] = _showAllToolBoxes;
        toolBoxes[1] = _showPropertiesToolBox;
        toolBoxes[2] = _showLinksToolBox;
        toolBoxes[3] = _showImagesToolBox;
        toolBoxes[4] = _showTablesToolBox;
		toolBoxes[5] = _showCleanupExpressionsToolBox;
        toolBoxes[6] = _showDebugToolBox;

        values[3] = toolBoxes;

        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);

		String[] types = (String[]) values[1];
		_fallback = types[0];
        _type = types[1];

        Boolean[] toolBarButtons = (Boolean[]) values[2];
        _allowEditSource = toolBarButtons[0];
        _allowExternalLinks = toolBarButtons[1];
        _addKupuLogo = toolBarButtons[2];

        Boolean[] toolBoxes = (Boolean[]) values[3];
		_showAllToolBoxes = toolBoxes[0];
        _showPropertiesToolBox = toolBoxes[1];
        _showLinksToolBox = toolBoxes[2];
        _showImagesToolBox = toolBoxes[3];
        _showTablesToolBox = toolBoxes[4];
		_showCleanupExpressionsToolBox = toolBoxes[5];
        _showDebugToolBox = toolBoxes[6];
    }

    /**
     * @JSFProperty
     */
    public String getFallback(){
        if (_fallback != null)
            return _fallback;
        ValueBinding vb = getValueBinding("fallback");
        return vb != null ? vb.getValue(getFacesContext()).toString() : "false";
    }
    public void setFallback(String _fallback){
        this._fallback = _fallback;
    }

    /**
     * @JSFProperty
     */
    public String getType(){
        if (_type != null)
            return _type;
        ValueBinding vb = getValueBinding("type");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : "fragment";
    }
    public void setType(String _type){
        this._type = _type;
    }
    public boolean isTypeDocument(){
        return getType().equals("document");
    }

    /**
     * @JSFProperty
     */
    public boolean isAllowEditSource(){
   		if (_allowEditSource != null)
   		    return _allowEditSource.booleanValue();
   		ValueBinding vb = getValueBinding("allowEditSource");
   		return vb != null ? ((Boolean)vb.getValue(getFacesContext())).booleanValue() : true;
    }
    public void setAllowEditSource(boolean allowEditSource){
        this._allowEditSource = Boolean.valueOf(allowEditSource);
    }

    /**
     * @JSFProperty
     */
    public boolean isAllowExternalLinks(){
        if (_allowExternalLinks != null)
            return _allowExternalLinks.booleanValue();
        ValueBinding vb = getValueBinding("allowExternalLinks");
        return vb != null ? ((Boolean)vb.getValue(getFacesContext())).booleanValue() : true;
    }
    public void setAllowExternalLinks(boolean allowExternalLinks){
        this._allowExternalLinks = Boolean.valueOf(allowExternalLinks);
    }

    /**
     * @JSFProperty
     */
    public boolean isAddKupuLogo(){
   		if (_addKupuLogo != null)
   		    return _addKupuLogo.booleanValue();
   		ValueBinding vb = getValueBinding("addKupuLogo");
   		return vb != null ? ((Boolean)vb.getValue(getFacesContext())).booleanValue() : true;
    }
    public void setAddKupuLogo(boolean addKupuLogo){
        this._addKupuLogo = Boolean.valueOf(addKupuLogo);
    }

    /**
     * @JSFProperty
     */
	public boolean isShowAllToolBoxes(){
   		if (_showAllToolBoxes != null)
   		    return _showAllToolBoxes.booleanValue();
    	ValueBinding vb = getValueBinding("showAllToolBoxes");
    	return vb != null ? ((Boolean)vb.getValue(getFacesContext())).booleanValue() : false;
    }
    public void setShowAllToolBoxes(boolean showAllToolBoxes){
        this._showAllToolBoxes = Boolean.valueOf(showAllToolBoxes);
    }

    /**
     * @JSFProperty
     */
    public boolean isShowPropertiesToolBox(){
		if( isShowAllToolBoxes() )
			return true;

   		if (_showPropertiesToolBox != null)
   		    return _showPropertiesToolBox.booleanValue();
    	ValueBinding vb = getValueBinding("showPropertiesToolBox");
    	return vb != null ? ((Boolean)vb.getValue(getFacesContext())).booleanValue() : false;
    }

    public void setShowPropertiesToolBox(boolean showPropertiesToolBox){
        this._showPropertiesToolBox = Boolean.valueOf(showPropertiesToolBox);
    }

    /**
     * @JSFProperty
     */
    public boolean isShowLinksToolBox(){
		if( isShowAllToolBoxes() )
			return true;

   		if (_showLinksToolBox != null)
   		    return _showLinksToolBox.booleanValue();
    	ValueBinding vb = getValueBinding("showLinksToolBox");
    	return vb != null ? ((Boolean)vb.getValue(getFacesContext())).booleanValue() : false;
    }
    
    public void setShowLinksToolBox(boolean showLinksToolBox){
        this._showLinksToolBox = Boolean.valueOf(showLinksToolBox);
    }

    /**
     * @JSFProperty
     */
    public boolean isShowImagesToolBox(){
		if( isShowAllToolBoxes() )
			return true;

   		if (_showImagesToolBox != null)
   		    return _showImagesToolBox.booleanValue();
    	ValueBinding vb = getValueBinding("showImagesToolBox");
    	return vb != null ? ((Boolean)vb.getValue(getFacesContext())).booleanValue() : false;
    }
    public void setShowImagesToolBox(boolean showImagesToolBox){
        this._showImagesToolBox = Boolean.valueOf(showImagesToolBox);
    }

    /**
     * @JSFProperty
     */
    public boolean isShowTablesToolBox(){
		if( isShowAllToolBoxes() )
			return true;

   		if (_showTablesToolBox != null)
   		    return _showTablesToolBox.booleanValue();
    	ValueBinding vb = getValueBinding("showTablesToolBox");
    	return vb != null ? ((Boolean)vb.getValue(getFacesContext())).booleanValue() : false;
    }
    public void setShowTablesToolBox(boolean showTablesToolBox){
        this._showTablesToolBox = Boolean.valueOf(showTablesToolBox);
    }

    /**
     * @JSFProperty
     */
	public boolean isShowCleanupExpressionsToolBox(){
		if( isShowAllToolBoxes() )
			return true;

   		if (_showCleanupExpressionsToolBox != null)
   		    return _showCleanupExpressionsToolBox.booleanValue();
    	ValueBinding vb = getValueBinding("showCleanupExpressionsToolBox");
    	return vb != null ? ((Boolean)vb.getValue(getFacesContext())).booleanValue() : false;
    }
    public void setShowCleanupExpressionsToolBox(boolean showCleanupExpressionsToolBox){
        this._showCleanupExpressionsToolBox = Boolean.valueOf(showCleanupExpressionsToolBox);
    }

    /**
     * @JSFProperty
     */
    public boolean isShowDebugToolBox(){
		if( isShowAllToolBoxes() )
			return true;

   		if (_showDebugToolBox != null)
   		    return _showDebugToolBox.booleanValue();
    	ValueBinding vb = getValueBinding("showDebugToolBox");
    	return vb != null ? ((Boolean)vb.getValue(getFacesContext())).booleanValue() : false;
    }
    public void setShowDebugToolBox(boolean showTablesToolBox){
        this._showDebugToolBox = Boolean.valueOf(showTablesToolBox);
    }

    public boolean isShowAnyToolBox(){
   		return isShowAllToolBoxes()
   			|| isShowPropertiesToolBox()
   			|| isShowLinksToolBox()
   			|| isShowImagesToolBox()
   			|| isShowTablesToolBox()
   			|| isShowCleanupExpressionsToolBox()
   			|| isShowDebugToolBox();
    }

    public String getValueAsHtmlDocument(FacesContext context){
        String val = RendererUtils.getStringValue(context, this);
        if( isHtmlDocument( val ) )
            return val;

        return "<html><body>"+(val==null ? "" : val)+"</body></html>";
    }

    private static boolean isHtmlDocument(String text){
        if( text == null )
            return false;

        if( text.indexOf("<body>")!=-1 || text.indexOf("<body ")!=-1
            || text.indexOf("<BODY>")!=-1 || text.indexOf("<BODY ")!=-1 )
            return true;

        return false;
    }

    public String getValueFromDocument(String text){
        if( text == null )
            return "";

        if( isTypeDocument() )
            return text.trim();

        if( !isHtmlDocument(text) )
            return text.trim();

        // Extract the fragment from the document.
        String fragment = getHtmlBody( text );
        if( fragment.endsWith("<br />") )
        	return fragment.substring(0, fragment.length()-6);
        return fragment;
    }
    
    String getHtmlBody(String html){
    	html = html.trim();
    	if( html.length() == 0 )
    		return "";

		String lcText = html.toLowerCase();
        int textLength = lcText.length();
        int bodyStartIndex = -1;
        while(bodyStartIndex < textLength){
        	bodyStartIndex++;
            bodyStartIndex = lcText.indexOf("<body", bodyStartIndex);
            if( bodyStartIndex == -1 )
                break; // not found.

            bodyStartIndex += 5;
            char c = lcText.charAt(bodyStartIndex);
            if( c=='>' )
                break;

            if( c!=' ' && c!='\t' )
                continue;

            bodyStartIndex = lcText.indexOf('>', bodyStartIndex);
            break;
        }
        bodyStartIndex++;

        int bodyEndIndex = lcText.lastIndexOf("</body>")-1;
        
        if( bodyStartIndex<0 || bodyEndIndex<0
           || bodyStartIndex > bodyEndIndex
           || bodyStartIndex>=textLength || bodyEndIndex>=textLength ){

        	if( lcText.indexOf("<body/>")!=-1 || lcText.indexOf("<body />")!=-1 )
        		return "";
        	
        	int htmlStartIndex = lcText.indexOf("<html>");
        	int htmlEndIndex = lcText.indexOf("</html>");
        	if( htmlStartIndex != -1 && htmlEndIndex > htmlStartIndex )
        		return html.substring(htmlStartIndex+6, htmlEndIndex);
        	
        	if( isTypeDocument() )
        		log.warn("Couldn't extract HTML body from :\n"+html);
            return html.trim();
        }

        return html.substring(bodyStartIndex, bodyEndIndex+1).trim();
	}
}
