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
package org.apache.myfaces.custom.selectOneLanguage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;

import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.renderkit.html.ext.HtmlMenuRenderer;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.SelectOne"
 *   type = "org.apache.myfaces.SelectOneLanguageRenderer"
 * 
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date: 2005-05-11 12:14:23 -0400 (Wed, 11 May 2005) $
 */
public class SelectOneLanguageRenderer extends HtmlMenuRenderer {
	
	public void encodeEnd(FacesContext facesContext, UIComponent component)
    throws IOException
	{
		RendererUtils.checkParamValidity(facesContext, component, null);

		SelectOneLanguage selectOneLanguage = (SelectOneLanguage) component;
		ResponseWriter writer = facesContext.getResponseWriter();
		
		if(HtmlRendererUtils.isDisplayValueOnly(component))
		{
		    //HtmlRendererUtils.renderDisplayValueOnlyForSelects(facesContext, component);
			writer.startElement(HTML.SPAN_ELEM, selectOneLanguage);
	        HtmlRendererUtils.writeIdIfNecessary(writer, selectOneLanguage, facesContext);
	    
	        String[] supportedAttributes = {HTML.STYLE_CLASS_ATTR, HTML.STYLE_ATTR};
            HtmlRendererUtils.renderHTMLAttributes(writer, selectOneLanguage, supportedAttributes);
	        
	        String languageCode = selectOneLanguage.getValue().toString();
	        String languageName = new Locale(languageCode).getDisplayLanguage( facesContext.getViewRoot().getLocale() );
	        
	        writer.write( languageName );
	        
	        writer.endElement(HTML.SPAN_ELEM);
		    return;
		}
		
        writer.startElement(HTML.SELECT_ELEM, component);
        HtmlRendererUtils.writeIdIfNecessary(writer, selectOneLanguage, facesContext);
        writer.writeAttribute(HTML.NAME_ATTR, selectOneLanguage.getClientId(facesContext), null);

        List selectItemList = selectOneLanguage.getLanguagesChoicesAsSelectItemList();
        Converter converter = HtmlRendererUtils.findUIOutputConverterFailSafe(facesContext, selectOneLanguage);

        writer.writeAttribute(HTML.SIZE_ATTR, "1", null);

        HtmlRendererUtils.renderHTMLAttributes(writer, selectOneLanguage, HTML.SELECT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        if ( isDisabled(facesContext, selectOneLanguage) ) {
            writer.writeAttribute(HTML.DISABLED_ATTR, Boolean.TRUE, null);
        }

        Set lookupSet = HtmlRendererUtils.getSubmittedOrSelectedValuesAsSet(false, selectOneLanguage, facesContext, converter);

        HtmlRendererUtils.renderSelectOptions(facesContext, selectOneLanguage, converter, lookupSet, selectItemList);
        // bug #970747: force separate end tag
        writer.writeText("", null);
        writer.endElement(HTML.SELECT_ELEM);
	}
}
