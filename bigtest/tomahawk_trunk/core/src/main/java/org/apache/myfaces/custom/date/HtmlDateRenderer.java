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
package org.apache.myfaces.custom.date;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.custom.calendar.HtmlCalendarRenderer;
import org.apache.myfaces.custom.calendar.FunctionCallProvider;
import org.apache.myfaces.custom.calendar.HtmlCalendarRenderer.CalendarDateTimeConverter;
import org.apache.myfaces.custom.date.HtmlInputDate.UserData;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.JavascriptUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_tomahawk.util.MessageUtils;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Input"
 *   type = "org.apache.myfaces.Date"
 * 
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlDateRenderer extends HtmlRenderer {
	/**
	 * <p>The message identifier of the {@link FacesMessage} to be created if
	 * the input is not a valid date.</p>
	 */
	public static final String DATE_MESSAGE_ID = "org.apache.myfaces.Date.INVALID";

	private static final String ID_DAY_POSTFIX = ".day";
    private static final String ID_MONTH_POSTFIX = ".month";
    private static final String ID_YEAR_POSTFIX = ".year";
    private static final String ID_HOURS_POSTFIX = ".hours";
    private static final String ID_MINUTES_POSTFIX = ".minutes";
    private static final String ID_SECONDS_POSTFIX = ".seconds";
    private static final String ID_AMPM_POSTFIX = ".ampm";

    static public String getClientIdForDaySubcomponent(String clientId)
    {
    	return clientId + ID_DAY_POSTFIX;
    }
    
    protected boolean isDisabled(FacesContext facesContext, HtmlInputDate inputDate) {
        if( !UserRoleUtils.isEnabledOnUserRole(inputDate) )
            return false;

        return inputDate.isDisabled();
    }

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        RendererUtils.checkParamValidity(facesContext, uiComponent, HtmlInputDate.class);

        HtmlInputDate inputDate = (HtmlInputDate) uiComponent;
        Locale currentLocale = facesContext.getViewRoot().getLocale();
        UserData userData = (UserData) inputDate.getSubmittedValue();
        if( userData == null )
            userData = inputDate.getUserData(currentLocale);
        String type = inputDate.getType();
        boolean ampm = inputDate.isAmpm();
        String clientId = uiComponent.getClientId(facesContext);

        boolean disabled = isDisabled(facesContext, inputDate);
		boolean readonly = inputDate.isReadonly();

        ResponseWriter writer = facesContext.getResponseWriter();

        HtmlRendererUtils.writePrettyLineSeparator(facesContext);

        writer.startElement(HTML.SPAN_ELEM, uiComponent);
        writer.writeAttribute(HTML.ID_ATTR, clientId, null);

        if( ! (type.equals("time") || type.equals("short_time"))){
	        encodeInputDay(inputDate, writer, clientId, userData, disabled, readonly);
	        encodeInputMonth(inputDate, writer, clientId, userData, currentLocale, disabled, readonly);
	        encodeInputYear(inputDate, writer, clientId, userData, disabled, readonly);

	        if( inputDate.isPopupCalendar() && ! disabled && ! readonly )
	            encodePopupCalendarButton(facesContext, uiComponent, writer, clientId, currentLocale);
        }
        if( type.equals("both") || type.equals("full")){
            writer.write(" ");
        }
        if( ! type.equals("date")){
	        encodeInputHours(uiComponent, writer, clientId, userData, disabled, readonly);
	        writer.write(":");
	        encodeInputMinutes(uiComponent, writer, clientId, userData, disabled, readonly);
	        if (type.equals("full")|| type.equals("time")) {
						writer.write(":");
	        	encodeInputSeconds(uiComponent, writer, clientId, userData, disabled, readonly);
					}
            if (ampm) {
                encodeInputAmpm(uiComponent, writer, clientId, userData, disabled, readonly, currentLocale);
            }
        }

        writer.endElement(HTML.SPAN_ELEM);
    }

    protected void encodeInputField(UIComponent uiComponent, ResponseWriter writer, String id,
			String value, int size, boolean disabled, boolean readonly)  throws IOException {
        writer.startElement(HTML.INPUT_ELEM, uiComponent);
        HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.UNIVERSAL_ATTRIBUTES);
        HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.EVENT_HANDLER_ATTRIBUTES);
        HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.INPUT_ATTRIBUTES);
        HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.COMMON_FIELD_EVENT_ATTRIBUTES);

		if (disabled) {
		    writer.writeAttribute(HTML.DISABLED_ATTR, Boolean.TRUE, null);
		}
		if( readonly ) {
			writer.writeAttribute(HTML.READONLY_ATTR, Boolean.TRUE, null);
		}

		writer.writeAttribute(HTML.ID_ATTR, id, null);
		writer.writeAttribute(HTML.NAME_ATTR, id, null);
		writer.writeAttribute(HTML.SIZE_ATTR, Integer.toString(size), null);
		writer.writeAttribute(HTML.MAXLENGTH_ATTR, Integer.toString(size), null);
		if (value != null) {
		    writer.writeAttribute(HTML.VALUE_ATTR, value, null);
		}
		writer.endElement(HTML.INPUT_ELEM);
    }

    protected void encodeInputDay(UIComponent uiComponent, ResponseWriter writer, String clientId,
			UserData userData, boolean disabled, boolean readonly) throws IOException {
        encodeInputField(uiComponent, writer, getClientIdForDaySubcomponent(clientId), userData.getDay(), 2, disabled, readonly);
    }

    protected void encodeInputMonth(UIComponent uiComponent, ResponseWriter writer, String clientId, UserData userData, Locale currentLocale,
            boolean disabled, boolean readonly) throws IOException {
        writer.startElement(HTML.SELECT_ELEM, uiComponent);
        writer.writeAttribute(HTML.ID_ATTR, clientId + ID_MONTH_POSTFIX, null);
        writer.writeAttribute(HTML.NAME_ATTR, clientId + ID_MONTH_POSTFIX, null);
        writer.writeAttribute(HTML.SIZE_ATTR, "1", null);
        HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.UNIVERSAL_ATTRIBUTES);
        HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.EVENT_HANDLER_ATTRIBUTES);
        HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.COMMON_FIELD_EVENT_ATTRIBUTES);

        if (disabled) {
            writer.writeAttribute(HTML.DISABLED_ATTR, Boolean.TRUE, null);
        }
        if (readonly) {
            writer.writeAttribute(HTML.READONLY_ATTR, Boolean.TRUE, null);
        }

        int selectedMonth = userData.getMonth() == null ? -1 : Integer.parseInt(userData.getMonth())-1;

        String[] months = HtmlCalendarRenderer.mapMonths(new DateFormatSymbols(currentLocale));
        encodeEmptyInputMonthSelection(uiComponent, writer, selectedMonth);
        for (int i = 0; i < months.length; i++) {
            String monthName = months[i];
            String monthNumber = Integer.toString(i+1);

            writer.write("\t\t");
            writer.startElement(HTML.OPTION_ELEM, uiComponent);
            writer.writeAttribute(HTML.VALUE_ATTR, monthNumber, null);

            if (i == selectedMonth)
                writer.writeAttribute(HTML.SELECTED_ATTR, HTML.SELECTED_ATTR, null);

            writer.writeText(monthName, null);

            writer.endElement(HTML.OPTION_ELEM);
        }

        // bug #970747: force separate end tag
        writer.writeText("", null);
        writer.endElement(HTML.SELECT_ELEM);
    }

    protected void encodeEmptyInputMonthSelection(UIComponent component, ResponseWriter writer, int selectedMonth) throws IOException{
    	 writer.startElement(HTML.OPTION_ELEM, component);
         writer.writeAttribute(HTML.VALUE_ATTR, "-1", null);

         if(selectedMonth == -1)
        	 writer.writeAttribute(HTML.SELECTED_ATTR, HTML.SELECTED_ATTR, null);

         writer.writeText(((HtmlInputDate)component).getEmptyMonthSelection(), null);
         writer.endElement(HTML.OPTION_ELEM);
    }

    protected void encodeInputYear(UIComponent uiComponent, ResponseWriter writer, String clientId,
			UserData userData, boolean disabled, boolean readonly) throws IOException {
        encodeInputField(uiComponent, writer, clientId + ID_YEAR_POSTFIX, userData.getYear(), 4, disabled, readonly);
    }

    protected void encodeInputHours(UIComponent uiComponent, ResponseWriter writer, String clientId,
			UserData userData, boolean disabled, boolean readonly) throws IOException {
        encodeInputField(uiComponent, writer, clientId + ID_HOURS_POSTFIX, userData.getHours(), 2, disabled, readonly);
    }

    protected void encodeInputMinutes(UIComponent uiComponent, ResponseWriter writer, String clientId,
			UserData userData, boolean disabled, boolean readonly) throws IOException {
        encodeInputField(uiComponent, writer, clientId + ID_MINUTES_POSTFIX, userData.getMinutes(), 2, disabled, readonly);
    }

    protected void encodeInputSeconds(UIComponent uiComponent, ResponseWriter writer, String clientId,
			UserData userData, boolean disabled, boolean readonly) throws IOException {
        encodeInputField(uiComponent, writer, clientId + ID_SECONDS_POSTFIX, userData.getSeconds(), 2, disabled, readonly);
    }

    protected void encodeAmpmChoice(DateFormatSymbols symbols, UIComponent uiComponent, ResponseWriter writer, int calendar_ampm, int selected) throws IOException {
    	String[] ampm_choices = symbols.getAmPmStrings();
        writer.write("\t\t");
        writer.startElement(HTML.OPTION_ELEM, uiComponent);
        writer.writeAttribute(HTML.VALUE_ATTR, new Integer(calendar_ampm), null);
        if (calendar_ampm == selected)
            writer.writeAttribute(HTML.SELECTED_ATTR, HTML.SELECTED_ATTR, null);
        writer.writeText(ampm_choices[calendar_ampm], null);
        writer.endElement(HTML.OPTION_ELEM);
    }

    protected void encodeInputAmpm(UIComponent uiComponent, ResponseWriter writer, String clientId,
			UserData userData, boolean disabled, boolean readonly, Locale currentLocale) throws IOException {
        writer.startElement(HTML.SELECT_ELEM, uiComponent);
        writer.writeAttribute(HTML.ID_ATTR, clientId + ID_AMPM_POSTFIX, null);
        writer.writeAttribute(HTML.NAME_ATTR, clientId + ID_AMPM_POSTFIX, null);
        writer.writeAttribute(HTML.SIZE_ATTR, "1", null);
        HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.UNIVERSAL_ATTRIBUTES);
        HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.EVENT_HANDLER_ATTRIBUTES);
        HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.COMMON_FIELD_EVENT_ATTRIBUTES);

        if (disabled) {
            writer.writeAttribute(HTML.DISABLED_ATTR, Boolean.TRUE, null);
        }
        if (readonly) {
            writer.writeAttribute(HTML.READONLY_ATTR, Boolean.TRUE, null);
        }

        DateFormatSymbols symbols = new DateFormatSymbols(currentLocale);

        int selectedAmpm = userData.getAmpm() == null ? -1 : Integer.parseInt(userData.getAmpm());
        encodeEmtypAmpmChoice(uiComponent, writer, selectedAmpm);
        encodeAmpmChoice(symbols, uiComponent, writer, Calendar.AM, selectedAmpm);
        encodeAmpmChoice(symbols, uiComponent, writer, Calendar.PM, selectedAmpm);


        // bug #970747: force separate end tag
        writer.writeText("", null);
        writer.endElement(HTML.SELECT_ELEM);
    }

    protected void encodeEmtypAmpmChoice(UIComponent component, ResponseWriter writer, int selectedAmpm) throws IOException{
    	 writer.startElement(HTML.OPTION_ELEM, component);
         writer.writeAttribute(HTML.VALUE_ATTR, "-1", null);

         if(selectedAmpm == -1)
        	 writer.writeAttribute(HTML.SELECTED_ATTR, HTML.SELECTED_ATTR, null);

         writer.writeText(((HtmlInputDate)component).getEmptyAmpmSelection(), null);
         writer.endElement(HTML.OPTION_ELEM);
    }

    protected void encodePopupCalendarButton(FacesContext facesContext, UIComponent uiComponent, ResponseWriter writer, String clientId, Locale currentLocale) throws IOException{

        DateFormatSymbols symbols = new DateFormatSymbols(currentLocale);

        HtmlCalendarRenderer.addScriptAndCSSResources(facesContext,uiComponent);

        String calendarVar = JavascriptUtils.getValidJavascriptName(
                uiComponent.getClientId(facesContext)+"CalendarVar",false);
        String dateFormat = CalendarDateTimeConverter.createJSPopupFormat(facesContext, null);

        String localizedLanguageScript = HtmlCalendarRenderer.getLocalizedLanguageScript(facesContext,
                							symbols,Calendar.getInstance(currentLocale).getFirstDayOfWeek(),
                							uiComponent,calendarVar);

        writer.startElement(HTML.SPAN_ELEM,uiComponent);
        writer.writeAttribute(HTML.ID_ATTR,uiComponent.getClientId(facesContext)+"Span",
                JSFAttr.ID_ATTR);
        writer.endElement(HTML.SPAN_ELEM);

        writer.startElement(HTML.SCRIPT_ELEM,uiComponent);
        writer.writeAttribute(HTML.SCRIPT_TYPE_ATTR,HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT,null);

        writer.writeText("var "+calendarVar+"=new org_apache_myfaces_PopupCalendar();\n",null);
        writer.write(localizedLanguageScript);
        writer.writeText(calendarVar+".init(document.getElementById('"+
                uiComponent.getClientId(facesContext)+"Span"+"'));\n",null);


        writer.endElement(HTML.SCRIPT_ELEM);
	 HtmlCalendarRenderer.getScriptBtn(writer, facesContext, uiComponent,
                dateFormat,"...",new FunctionCallProvider(){
            public String getFunctionCall(FacesContext facesContext, UIComponent uiComponent, String dateFormat)
            {
                String clientId = uiComponent.getClientId(facesContext);

                String clientVar = JavascriptUtils.getValidJavascriptName(clientId+"CalendarVar",true);

                return clientVar+"._popUpCalendarForInputDate('"+clientId+"','"+dateFormat+"');";

            }
        });
    }

    public void decode(FacesContext facesContext, UIComponent uiComponent) {
        RendererUtils.checkParamValidity(facesContext, uiComponent, HtmlInputDate.class);

        HtmlInputDate inputDate = (HtmlInputDate) uiComponent;

        if( isDisabled(facesContext, inputDate) ) // For safety, do not set the submited value if the component is disabled.
            return;

        Locale currentLocale = facesContext.getViewRoot().getLocale();
        UserData userData = (UserData) inputDate.getSubmittedValue();
        if( userData == null )
            userData = inputDate.getUserData(currentLocale);

        String clientId = inputDate.getClientId(facesContext);
        String type = inputDate.getType();
        Map requestMap = facesContext.getExternalContext().getRequestParameterMap();

        if( ! (type.equals( "time" ) || type.equals( "short_time" )) ){
            userData.setDay( (String) requestMap.get(getClientIdForDaySubcomponent(clientId)) );
            userData.setMonth( (String) requestMap.get(clientId + ID_MONTH_POSTFIX) );
            userData.setYear( (String) requestMap.get(clientId + ID_YEAR_POSTFIX) );
        }

        if( ! type.equals( "date" ) ){
            userData.setHours( (String) requestMap.get(clientId + ID_HOURS_POSTFIX) );
            userData.setMinutes( (String) requestMap.get(clientId + ID_MINUTES_POSTFIX) );
            if (type.equals("full") || type.equals("time"))
				userData.setSeconds( (String) requestMap.get(clientId + ID_SECONDS_POSTFIX) );

            if (inputDate.isAmpm()) {
            	userData.setAmpm( (String) requestMap.get(clientId + ID_AMPM_POSTFIX) );
            }
        }
        inputDate.setSubmittedValue( userData );
    }

    public Object getConvertedValue(FacesContext context, UIComponent uiComponent, Object submittedValue) throws ConverterException {
        UserData userData = (UserData) submittedValue;
        try {
            return userData.parse();
        } catch (ParseException e) {
            Object[] args = {uiComponent.getId()};
            throw new ConverterException(MessageUtils.getMessage(FacesMessage.SEVERITY_ERROR, DATE_MESSAGE_ID, args));
        }
    }
}
