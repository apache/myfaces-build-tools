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
package org.apache.myfaces.custom.selectOneCountry;

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
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SelectOneCountryRenderer extends HtmlMenuRenderer {
	
    public void encodeEnd(FacesContext facesContext, UIComponent component)
    throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, component, null);

        SelectOneCountry selectOneCountry = (SelectOneCountry) component;
        ResponseWriter writer = facesContext.getResponseWriter();

        if(HtmlRendererUtils.isDisplayValueOnly(component))
        {
            //HtmlRendererUtils.renderDisplayValueOnlyForSelects(facesContext, component);
            writer.startElement(HTML.SPAN_ELEM, selectOneCountry);
            HtmlRendererUtils.writeIdIfNecessary(writer, selectOneCountry, facesContext);

            String[] supportedAttributes = {HTML.STYLE_CLASS_ATTR, HTML.STYLE_ATTR};
            HtmlRendererUtils.renderHTMLAttributes(writer, selectOneCountry, supportedAttributes);

            String countryCode = selectOneCountry.getValue().toString();
            String countryName = new Locale(countryCode, countryCode).getDisplayCountry( facesContext.getViewRoot().getLocale() );

            writer.write( countryName );

            writer.endElement(HTML.SPAN_ELEM);
            return;
        }

        writer.startElement(HTML.SELECT_ELEM, selectOneCountry);
        HtmlRendererUtils.writeIdIfNecessary(writer, selectOneCountry, facesContext);
        writer.writeAttribute(HTML.NAME_ATTR, component.getClientId(facesContext), null);

        List selectItemList = selectOneCountry.getCountriesChoicesAsSelectItemList();
        Converter converter = HtmlRendererUtils.findUIOutputConverterFailSafe(facesContext, selectOneCountry);

        writer.writeAttribute(HTML.SIZE_ATTR, "1", null);

        HtmlRendererUtils.renderHTMLAttributes(writer, selectOneCountry, HTML.SELECT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        if ( isDisabled(facesContext, selectOneCountry) ) {
            writer.writeAttribute(HTML.DISABLED_ATTR, Boolean.TRUE, null);
        }

        Set lookupSet = HtmlRendererUtils.getSubmittedOrSelectedValuesAsSet(false, selectOneCountry, facesContext, converter);

        HtmlRendererUtils.renderSelectOptions(facesContext, selectOneCountry, converter, lookupSet, selectItemList);
        // bug #970747: force separate end tag
        writer.writeText("", null);
        writer.endElement(HTML.SELECT_ELEM);
    }
}
