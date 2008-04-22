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
package org.apache.myfaces.custom.calendar;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.inputTextHelp.HtmlInputTextHelp;
import org.apache.myfaces.custom.prototype.PrototypeResourceLoader;
import org.apache.myfaces.dateformat.SimpleDateFormatter;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.JavascriptUtils;
import org.apache.myfaces.shared_tomahawk.util.MessageUtils;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Render a "calendar" which the user can use to choose a specific day.
 * <p>
 * This renderer behaves quite differently for "inline" and "popup" calendars.
 * <p>
 * When inline, this component automatically creates child components (text and
 * link components) to represent all the dates, scrollers, etc. within itself and
 * renders those children. Clicking on any link in the component causes the
 * surrounding form to immediately submit.
 * <p>
 * When popup, this component just renders an empty span with the component id,
 * and a dozen or so lines of javascript which create an instance of a
 * "tomahawk calendar object", set its properties from the properties on the
 * associated HtmlInputCalendar component, and then invoke it. That javascript
 * object then dynamically builds DOM objects and attaches them to the empty span.
 * This component also renders a button or image that toggles the visibility of
 * that empty span, thereby making the popup appear and disappear.
 * <p>
 * Note that the two ways of generating the calendar use totally different code;
 * one implementation is here and the other is in a javascript resource file. For
 * obvious reasons the appearance of the two calendars should be similar, so if a
 * feature is added in one place it is recommended that the other be updated also. 
 * <p>
 * The behaviour of both of the Calendar objects varies depending upon the 
 * "current locale". This is derived from getViewRoot().getLocale(), which is
 * normally set according to the browser preferences; users whose browsers
 * have "english" as the preferred language will get the "en" locale, while
 * users with "deutsch" as the preferred language will get the "de" locale.
 * One specific example is the "first day of week" (ie the day displayed at
 * the left of each week row); this is "sunday" for the english locale, and
 * "monday" for the german locale. There is currently no way for the
 * calendar component to be configured to force a specific firstDayOfWeek
 * to be used for all users.
 * <p>
 * @author Martin Marinschek (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlCalendarRenderer
        extends HtmlRenderer
{
    private final Log log = LogFactory.getLog(HtmlCalendarRenderer.class);

    private static final String JAVASCRIPT_ENCODED = "org.apache.myfaces.calendar.JAVASCRIPT_ENCODED";

    // TODO: move this to HtmlRendererUtils in shared
    private static final String RESOURCE_NONE = "none";

    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, component, HtmlInputCalendar.class);

        HtmlInputCalendar inputCalendar = (HtmlInputCalendar) component;

        Locale currentLocale = facesContext.getViewRoot().getLocale();
        log.debug("current locale:" + currentLocale.toString());

        Date value;

        try
        {
            // value = RendererUtils.getDateValue(inputCalendar);
            Converter converter = getConverter(inputCalendar);
            if (converter instanceof DateConverter)
            {
                value = ((DateConverter) converter).getAsDate(facesContext, component);
            }
            else
            {
                value = RendererUtils.getDateValue(inputCalendar);
            }
        }
        catch (IllegalArgumentException illegalArgumentException)
        {
            value = null;
        }


        Calendar timeKeeper = Calendar.getInstance(currentLocale);
        timeKeeper.setTime(value!=null?value:new Date());

        DateFormatSymbols symbols = new DateFormatSymbols(currentLocale);

        if(inputCalendar.isRenderAsPopup())
        {
            renderPopup(facesContext, inputCalendar, value, timeKeeper, symbols);
        }
        else
        {
            renderInline(facesContext, inputCalendar, value, timeKeeper, symbols);
        }

        component.getChildren().removeAll(component.getChildren());
    }

    private void renderPopup(
            FacesContext facesContext, 
            HtmlInputCalendar inputCalendar,
            Date value,
            Calendar timeKeeper,
            DateFormatSymbols symbols) throws IOException
    {
        if(inputCalendar.isAddResources())
            addScriptAndCSSResources(facesContext, inputCalendar);

         // Check for an enclosed converter:
         UIInput uiInput = (UIInput) inputCalendar;
         Converter converter = uiInput.getConverter();
         String dateFormat = null;
         if (converter != null && converter instanceof DateTimeConverter) {
             dateFormat = ((DateTimeConverter) converter).getPattern();
         }
         if (dateFormat == null) {
             dateFormat = CalendarDateTimeConverter.createJSPopupFormat(facesContext,
                                                                        inputCalendar.getPopupDateFormat());
         }

        Application application = facesContext.getApplication();

        HtmlInputTextHelp inputText = getOrCreateInputTextChild(inputCalendar, application);

        RendererUtils.copyHtmlInputTextAttributes(inputCalendar, inputText);

        inputText.setConverter(null); // value for this transient component will already be converted
        inputText.setTransient(true);
        inputText.setHelpText(inputCalendar.getHelpText());
        inputText.setSelectText(true);

        if (value == null && inputCalendar.getSubmittedValue() != null)
        {
            inputText.setValue(inputCalendar.getSubmittedValue());
        }
        else
        {
            inputText.setValue(getConverter(inputCalendar).getAsString(
                    facesContext,inputCalendar,value));
        }
        inputText.setDisabled(inputCalendar.isDisabled());
        inputText.setReadonly(inputCalendar.isReadonly());
        inputText.setEnabledOnUserRole(inputCalendar.getEnabledOnUserRole());
        inputText.setVisibleOnUserRole(inputCalendar.getVisibleOnUserRole());

        //This is where two components with the same id are in the tree,
        //so make sure that during the rendering the id is unique.

        inputCalendar.setId(inputCalendar.getId()+"tempId");

        inputCalendar.getChildren().add(inputText);

        RendererUtils.renderChild(facesContext, inputText);

        inputCalendar.getChildren().remove(inputText);

        //Set back the correct id to the input calendar
        inputCalendar.setId(inputText.getId());

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.startElement(HTML.SPAN_ELEM,inputCalendar);
        writer.writeAttribute(HTML.ID_ATTR,inputCalendar.getClientId(facesContext)+"Span",
                              JSFAttr.ID_ATTR);
        writer.endElement(HTML.SPAN_ELEM);

        if (!inputCalendar.isDisabled())
        {
            writer.startElement(HTML.SCRIPT_ELEM, inputCalendar);
            writer.writeAttribute(HTML.SCRIPT_TYPE_ATTR,HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT,null);

            String calendarVar = JavascriptUtils.getValidJavascriptName(
                    inputCalendar.getClientId(facesContext)+"CalendarVar",false);

            writer.writeText(calendarVar+"=new org_apache_myfaces_PopupCalendar();\n",null);
            writer.writeText(getLocalizedLanguageScript(facesContext,symbols,
                                                        timeKeeper.getFirstDayOfWeek(),inputCalendar,calendarVar)+"\n",null);
            // pass the selectMode attribute
            StringBuffer script = new StringBuffer();
            setStringVariable(script, calendarVar +".initData.selectMode",inputCalendar.getPopupSelectMode());
            writer.writeText(script.toString(), null);

            writer.writeText(calendarVar+".init(document.getElementById('"+
                             inputCalendar.getClientId(facesContext)+"Span"+"'));\n",null);
            writer.endElement(HTML.SCRIPT_ELEM);
            if(!inputCalendar.isDisplayValueOnly())
            {
                getScriptBtn(writer, facesContext, inputCalendar,
                                              dateFormat,inputCalendar.getPopupButtonString(), new FunctionCallProvider(){
                    public String getFunctionCall(FacesContext facesContext, UIComponent uiComponent, String dateFormat)
                    {
                        String clientId = uiComponent.getClientId(facesContext);

                        String clientVar = JavascriptUtils.getValidJavascriptName(clientId+"CalendarVar",true);

                        return clientVar+"._popUpCalendar(this,document.getElementById('"+clientId+"'),'"+dateFormat+"')";
                    }
                });
            }
        }
    }

    private void renderInline(
            FacesContext facesContext, 
            HtmlInputCalendar inputCalendar,
            Date value,
            Calendar timeKeeper,
            DateFormatSymbols symbols) throws IOException
    {
        String[] weekdays = mapShortWeekdays(symbols);
        String[] months = mapMonths(symbols);

        int lastDayInMonth = timeKeeper.getActualMaximum(Calendar.DAY_OF_MONTH);

        int currentDay = timeKeeper.get(Calendar.DAY_OF_MONTH);

        if (currentDay > lastDayInMonth)
            currentDay = lastDayInMonth;

        timeKeeper.set(Calendar.DAY_OF_MONTH, 1);

        int weekDayOfFirstDayOfMonth = mapCalendarDayToCommonDay(timeKeeper.get(Calendar.DAY_OF_WEEK));

        int weekStartsAtDayIndex = mapCalendarDayToCommonDay(timeKeeper.getFirstDayOfWeek());

        ResponseWriter writer = facesContext.getResponseWriter();

        HtmlRendererUtils.writePrettyLineSeparator(facesContext);
        HtmlRendererUtils.writePrettyLineSeparator(facesContext);

        writer.startElement(HTML.TABLE_ELEM, inputCalendar);
        HtmlRendererUtils.renderHTMLAttributes(writer, inputCalendar, HTML.UNIVERSAL_ATTRIBUTES);
        HtmlRendererUtils.renderHTMLAttributes(writer, inputCalendar, HTML.EVENT_HANDLER_ATTRIBUTES);
        writer.flush();

        HtmlRendererUtils.writePrettyLineSeparator(facesContext);

        writer.startElement(HTML.TR_ELEM, inputCalendar);

        if(inputCalendar.getMonthYearRowClass() != null)
            writer.writeAttribute(HTML.CLASS_ATTR, inputCalendar.getMonthYearRowClass(), null);

        writeMonthYearHeader(facesContext, writer, inputCalendar, timeKeeper,
                             currentDay, weekdays, months);

        writer.endElement(HTML.TR_ELEM);

        HtmlRendererUtils.writePrettyLineSeparator(facesContext);

        writer.startElement(HTML.TR_ELEM, inputCalendar);

        if(inputCalendar.getWeekRowClass() != null)
            writer.writeAttribute(HTML.CLASS_ATTR, inputCalendar.getWeekRowClass(), null);

        writeWeekDayNameHeader(weekStartsAtDayIndex, weekdays,
                               facesContext, writer, inputCalendar);

        writer.endElement(HTML.TR_ELEM);

        HtmlRendererUtils.writePrettyLineSeparator(facesContext);

        writeDays(facesContext, writer, inputCalendar, timeKeeper,
                  currentDay, weekStartsAtDayIndex, weekDayOfFirstDayOfMonth,
                  lastDayInMonth, weekdays);

        writer.endElement(HTML.TABLE_ELEM);
    }

    private HtmlInputTextHelp getOrCreateInputTextChild(HtmlInputCalendar inputCalendar, Application application)
    {
        HtmlInputTextHelp inputText = null;

        List li = inputCalendar.getChildren();

        for (int i = 0; i < li.size(); i++)
        {
            UIComponent uiComponent = (UIComponent) li.get(i);

            if(uiComponent instanceof HtmlInputTextHelp)
            {
                inputText = (HtmlInputTextHelp) uiComponent;
                break;
            }
        }

        if(inputText == null)
        {
            inputText = (HtmlInputTextHelp) application.createComponent(HtmlInputTextHelp.COMPONENT_TYPE);
        }
        return inputText;
    }

    /**
     * Used by the x:inputDate renderer : HTMLDateRenderer
     */
    static public void addScriptAndCSSResources(FacesContext facesContext, UIComponent component){
        // Check to see if javascript has already been written (which could happen if more than one calendar
        // on the same page). Note that this means that if two calendar controls in the same page have
        // different styleLocation or scriptLocation settings then all but the first one get ignored.
        // Having different settings for calendars on the same page would be unusual, so ignore this
        // for now..
        if (facesContext.getExternalContext().getRequestMap().containsKey(JAVASCRIPT_ENCODED))
        {
            return;
        }

        AddResource addresource = AddResourceFactory.getInstance(facesContext);
        // Add the javascript and CSS pages

        String styleLocation = HtmlRendererUtils.getStyleLocation(component);

        if(styleLocation==null)
        {
            addresource.addStyleSheet(facesContext, AddResource.HEADER_BEGIN, HtmlCalendarRenderer.class, "WH/theme.css");
            addresource.addStyleSheet(facesContext, AddResource.HEADER_BEGIN, HtmlCalendarRenderer.class, "DB/theme.css");
        }
        else if (!RESOURCE_NONE.equals(styleLocation))
        {
            addresource.addStyleSheet(facesContext, AddResource.HEADER_BEGIN, styleLocation+"/theme.css");
        }
        else
        {
            // output nothing; presumably the page directly references the necessary stylesheet
        }

        String javascriptLocation = HtmlRendererUtils.getJavascriptLocation(component);

        if(javascriptLocation==null)
        {
            addresource.addJavaScriptAtPosition(facesContext, AddResource.HEADER_BEGIN, PrototypeResourceLoader.class, "prototype.js");
            addresource.addJavaScriptAtPosition(facesContext, AddResource.HEADER_BEGIN, HtmlCalendarRenderer.class, "date.js");
            addresource.addJavaScriptAtPosition(facesContext, AddResource.HEADER_BEGIN, HtmlCalendarRenderer.class, "popcalendar.js");
        }
        else if (!RESOURCE_NONE.equals(javascriptLocation))
        {
            addresource.addJavaScriptAtPosition(facesContext, AddResource.HEADER_BEGIN, javascriptLocation+ "/prototype.js");
            addresource.addJavaScriptAtPosition(facesContext, AddResource.HEADER_BEGIN, javascriptLocation+ "/date.js");
            addresource.addJavaScriptAtPosition(facesContext, AddResource.HEADER_BEGIN, javascriptLocation+ "/popcalendar.js");
        }
        else
        {
            // output nothing; presumably the page directly references the necessary javascript
        }

        facesContext.getExternalContext().getRequestMap().put(JAVASCRIPT_ENCODED, Boolean.TRUE);
    }
    
    
    /**
     * Creates and returns a String which contains the initialisation data for
     * the popup calendar control as a sequence of javascript commands that
     * assign values to properties of a javascript object whose name is in
     * parameter popupCalendarVariable.
     * <p>
     * 
     * @param firstDayOfWeek
     *            is in java.util.Calendar form, ie Sun=1, Mon=2, Sat=7
     */
    public static String getLocalizedLanguageScript(FacesContext facesContext,
            DateFormatSymbols symbols, int firstDayOfWeek, UIComponent uiComponent,
            String popupCalendarVariable)
    {

        // Convert day value to java.util.Date convention (Sun=0, Mon=1, Sat=6).
        // This is the convention that javascript Date objects use.
        int realFirstDayOfWeek = firstDayOfWeek - 1;

        String[] weekDays;

        if (realFirstDayOfWeek == 0)
        {
            // Sunday
            weekDays = mapShortWeekdaysStartingWithSunday(symbols);
        }
        else if (realFirstDayOfWeek == 1)
        {
            // Monday
            weekDays = mapShortWeekdays(symbols);
        }
        else if (realFirstDayOfWeek == 6)
        {
            // Saturday. Often used in Arabic countries
            weekDays = mapShortWeekdaysStartingWithSaturday(symbols);
        }
        else
        {
            throw new IllegalStateException("Week may only start with saturday, sunday or monday.");
        }

        StringBuffer script = new StringBuffer();
        AddResource ar = AddResourceFactory.getInstance(facesContext);

        if (uiComponent instanceof HtmlInputCalendar)
        {
            HtmlInputCalendar calendar = (HtmlInputCalendar) uiComponent;
            // Set the themePrefix variable
            String popupTheme = calendar.getPopupTheme();
            if (popupTheme == null)
            {
                popupTheme = "DB";
            }
            setStringVariable(script, popupCalendarVariable + ".initData.themePrefix",
                    "jscalendar-" + popupTheme);

            // specify the URL for the directory in which all the .gif images
            // can be found
            String imageLocation = HtmlRendererUtils.getImageLocation(uiComponent);
            if (imageLocation == null)
            {
                String uri = ar.getResourceUri(facesContext, HtmlCalendarRenderer.class, popupTheme
                        + "/");
                setStringVariable(script, popupCalendarVariable + ".initData.imgDir",
                        JavascriptUtils.encodeString(uri));
            }
            else
            {
                setStringVariable(script, popupCalendarVariable + ".initData.imgDir",
                        (JavascriptUtils.encodeString(AddResourceFactory.getInstance(facesContext)
                                .getResourceUri(facesContext, imageLocation + "/"))));
            }
        }
        else
        {
            String imageLocation = HtmlRendererUtils.getImageLocation(uiComponent);
            if (imageLocation != null)
            {
                setStringVariable(script, popupCalendarVariable + ".initData.imgDir",
                        (JavascriptUtils.encodeString(AddResourceFactory.getInstance(facesContext)
                                .getResourceUri(facesContext, imageLocation + "/"))));
            }
        }
        defineStringArray(script, popupCalendarVariable + ".initData.monthName", mapMonths(symbols));
        defineStringArray(script, popupCalendarVariable + ".initData.dayName", weekDays);
        setIntegerVariable(script, popupCalendarVariable + ".initData.startAt", realFirstDayOfWeek);

        defineStringArray(script, popupCalendarVariable + ".dateFormatSymbols.weekdays",
                mapWeekdaysStartingWithSunday(symbols));
        defineStringArray(script, popupCalendarVariable + ".dateFormatSymbols.shortWeekdays",
                mapShortWeekdaysStartingWithSunday(symbols));
        defineStringArray(script, popupCalendarVariable + ".dateFormatSymbols.shortMonths",
                mapShortMonths(symbols));
        defineStringArray(script, popupCalendarVariable + ".dateFormatSymbols.months",
                mapMonths(symbols));
        defineStringArray(script, popupCalendarVariable + ".dateFormatSymbols.eras", symbols
                .getEras());
        defineStringArray(script, popupCalendarVariable + ".dateFormatSymbols.ampms", symbols
                .getAmPmStrings());

        if (uiComponent instanceof HtmlInputCalendar)
        {

            HtmlInputCalendar inputCalendar = (HtmlInputCalendar) uiComponent;

            if (inputCalendar.getPopupGotoString() != null)
                setStringVariable(script, popupCalendarVariable + ".initData.gotoString",
                        inputCalendar.getPopupGotoString());
            if (inputCalendar.getPopupTodayString() != null)
                setStringVariable(script, popupCalendarVariable + ".initData.todayString",
                        inputCalendar.getPopupTodayString());
            if (inputCalendar.getPopupTodayDateFormat() != null)
                setStringVariable(script, popupCalendarVariable + ".initData.todayDateFormat",
                        inputCalendar.getPopupTodayDateFormat());
            else if (inputCalendar.getPopupDateFormat() != null)
                setStringVariable(script, popupCalendarVariable + ".initData.todayDateFormat",
                        inputCalendar.getPopupDateFormat());
            if (inputCalendar.getPopupWeekString() != null)
                setStringVariable(script, popupCalendarVariable + ".initData.weekString",
                        inputCalendar.getPopupWeekString());
            if (inputCalendar.getPopupScrollLeftMessage() != null)
                setStringVariable(script, popupCalendarVariable + ".initData.scrollLeftMessage",
                        inputCalendar.getPopupScrollLeftMessage());
            if (inputCalendar.getPopupScrollRightMessage() != null)
                setStringVariable(script, popupCalendarVariable + ".initData.scrollRightMessage",
                        inputCalendar.getPopupScrollRightMessage());
            if (inputCalendar.getPopupSelectMonthMessage() != null)
                setStringVariable(script, popupCalendarVariable + ".initData.selectMonthMessage",
                        inputCalendar.getPopupSelectMonthMessage());
            if (inputCalendar.getPopupSelectYearMessage() != null)
                setStringVariable(script, popupCalendarVariable + ".initData.selectYearMessage",
                        inputCalendar.getPopupSelectYearMessage());
            if (inputCalendar.getPopupSelectDateMessage() != null)
                setStringVariable(script, popupCalendarVariable + ".initData.selectDateMessage",
                        inputCalendar.getPopupSelectDateMessage());
            setBooleanVariable(script, popupCalendarVariable + ".initData.popupLeft", inputCalendar
                    .isPopupLeft());

        }

        return script.toString();
    }

    private static void setBooleanVariable(StringBuffer script, String name, boolean value)
    {
        script.append(name);
        script.append(" = ");
        script.append(value);
        script.append(";\n");
    }

    private static void setIntegerVariable(StringBuffer script, String name, int value)
    {
        script.append(name);
        script.append(" = ");
        script.append(value);
        script.append(";\n");
    }

    private static void setStringVariable(StringBuffer script, String name, String value)
    {
        script.append(name);
        script.append(" = \"");
        script.append(StringEscapeUtils.escapeJavaScript(value));
        script.append("\";\n");
    }

    private static void defineStringArray(StringBuffer script, String arrayName, String[] array)
    {
        script.append(arrayName);
        script.append(" = new Array(");

        for(int i=0;i<array.length;i++)
        {
            if(i!=0)
                script.append(",");

            script.append("\"");
            script.append(StringEscapeUtils.escapeJavaScript(array[i]));
            script.append("\"");
        }

        script.append(");\n");
    }

    public static void getScriptBtn(ResponseWriter writer, FacesContext facesContext, UIComponent uiComponent,
                                      String dateFormat, String popupButtonString, FunctionCallProvider prov)
        throws IOException
    {
        boolean renderButtonAsImage = false;
        String popupButtonStyle = null;
        String popupButtonStyleClass = null;

        if(uiComponent instanceof HtmlInputCalendar)
        {
            HtmlInputCalendar calendar = (HtmlInputCalendar)uiComponent;
            renderButtonAsImage = calendar.isRenderPopupButtonAsImage();
            popupButtonStyle = calendar.getPopupButtonStyle();
            popupButtonStyleClass = calendar.getPopupButtonStyleClass();
        }

        if (!renderButtonAsImage) {
            // render the button
            writer.startElement(HTML.INPUT_ELEM, uiComponent);
            writer.writeAttribute(HTML.TYPE_ATTR, HTML.INPUT_TYPE_BUTTON, null);

            writer.writeAttribute(HTML.ONCLICK_ATTR,
                                  prov.getFunctionCall(facesContext,uiComponent,dateFormat),
                                  null);

            if(popupButtonString==null)
                popupButtonString="...";
            writer.writeAttribute(HTML.VALUE_ATTR, StringEscapeUtils.escapeJavaScript(popupButtonString), null);

            if(popupButtonStyle != null)
            {
                writer.writeAttribute(HTML.STYLE_ATTR, popupButtonStyle, null);
            }

            if(popupButtonStyleClass != null)
            {
                writer.writeAttribute(HTML.CLASS_ATTR, popupButtonStyleClass, null);
            }

            writer.endElement(HTML.INPUT_ELEM);
        } else {
            // render the image
            writer.startElement(HTML.IMG_ELEM, uiComponent);
            AddResource addResource = AddResourceFactory.getInstance(facesContext);

            String imgUrl = (String) uiComponent.getAttributes().get("popupButtonImageUrl");

            if(imgUrl!=null)
            {
                writer.writeAttribute(HTML.SRC_ATTR, addResource.getResourceUri(facesContext, imgUrl), null);
            }
            else
            {
                writer.writeAttribute(HTML.SRC_ATTR, addResource.getResourceUri(facesContext, HtmlCalendarRenderer.class, "images/calendar.gif"), null);
            }

            if(popupButtonStyle != null)
            {
                writer.writeAttribute(HTML.STYLE_ATTR, popupButtonStyle, null);
            }
            else
            {
                writer.writeAttribute(HTML.STYLE_ATTR, "vertical-align:bottom;", null);
            }

            if(popupButtonStyleClass != null)
            {
                writer.writeAttribute(HTML.CLASS_ATTR, popupButtonStyleClass, null);
            }

            writer.writeAttribute(HTML.ONCLICK_ATTR, prov.getFunctionCall(facesContext, uiComponent, dateFormat),
                                  null);

            writer.endElement(HTML.IMG_ELEM);
        }
    }


    private void writeMonthYearHeader(FacesContext facesContext, ResponseWriter writer, UIInput inputComponent, Calendar timeKeeper,
                                      int currentDay, String[] weekdays,
                                      String[] months)
            throws IOException
    {
        Calendar cal = shiftMonth(facesContext, timeKeeper, currentDay, -1);

        writeCell(facesContext, writer, inputComponent, "<", cal.getTime(), null);

        writer.startElement(HTML.TD_ELEM, inputComponent);
        writer.writeAttribute(HTML.COLSPAN_ATTR, new Integer(weekdays.length - 2), null);
        writer.writeText(months[timeKeeper.get(Calendar.MONTH)] + " " + timeKeeper.get(Calendar.YEAR), null);
        writer.endElement(HTML.TD_ELEM);

        cal = shiftMonth(facesContext, timeKeeper, currentDay, 1);

        writeCell(facesContext, writer, inputComponent, ">", cal.getTime(), null);
    }

    private Calendar shiftMonth(FacesContext facesContext,
                                Calendar timeKeeper, int currentDay, int shift)
    {
        Calendar cal = copyCalendar(facesContext, timeKeeper);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + shift);

        if(currentDay > cal.getActualMaximum(Calendar.DAY_OF_MONTH))
            currentDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        cal.set(Calendar.DAY_OF_MONTH, currentDay);
        return cal;
    }

    private Calendar copyCalendar(FacesContext facesContext, Calendar timeKeeper)
    {
        Calendar cal = Calendar.getInstance(facesContext.getViewRoot().getLocale());
        cal.setTime(timeKeeper.getTime());
        return cal;
    }

    private void writeWeekDayNameHeader(int weekStartsAtDayIndex, String[] weekdays, FacesContext facesContext, ResponseWriter writer, UIInput inputComponent)
            throws IOException
    {
        for (int i = weekStartsAtDayIndex; i < weekdays.length; i++)
            writeCell(facesContext,
                      writer, inputComponent, weekdays[i], null, null);

        for (int i = 0; i < weekStartsAtDayIndex; i++)
            writeCell(facesContext, writer,
                      inputComponent, weekdays[i], null, null);
    }

    private void writeDays(FacesContext facesContext, ResponseWriter writer,
                           HtmlInputCalendar inputComponent, Calendar timeKeeper, int currentDay, int weekStartsAtDayIndex,
                           int weekDayOfFirstDayOfMonth, int lastDayInMonth, String[] weekdays)
            throws IOException
    {
        Calendar cal;

        int space = (weekStartsAtDayIndex < weekDayOfFirstDayOfMonth) ? (weekDayOfFirstDayOfMonth - weekStartsAtDayIndex)
                    : (weekdays.length - weekStartsAtDayIndex + weekDayOfFirstDayOfMonth);

        if (space == weekdays.length)
            space = 0;

        int columnIndexCounter = 0;

        for (int i = 0; i < space; i++)
        {
            if (columnIndexCounter == 0)
            {
                writer.startElement(HTML.TR_ELEM, inputComponent);
            }

            writeCell(facesContext, writer, inputComponent, "",
                      null, inputComponent.getDayCellClass());
            columnIndexCounter++;
        }

        for (int i = 0; i < lastDayInMonth; i++)
        {
            if (columnIndexCounter == 0)
            {
                writer.startElement(HTML.TR_ELEM, inputComponent);
            }

            cal = copyCalendar(facesContext, timeKeeper);
            cal.set(Calendar.DAY_OF_MONTH, i + 1);

            String cellStyle = inputComponent.getDayCellClass();

            if((currentDay - 1) == i)
                cellStyle = inputComponent.getCurrentDayCellClass();

            writeCell(facesContext, writer,
                      inputComponent, String.valueOf(i + 1), cal.getTime(),
                      cellStyle);

            columnIndexCounter++;

            if (columnIndexCounter == weekdays.length)
            {
                writer.endElement(HTML.TR_ELEM);
                HtmlRendererUtils.writePrettyLineSeparator(facesContext);
                columnIndexCounter = 0;
            }
        }

        if (columnIndexCounter != 0)
        {
            for (int i = columnIndexCounter; i < weekdays.length; i++)
            {
                writeCell(facesContext, writer,
                          inputComponent, "", null, inputComponent.getDayCellClass());
            }

            writer.endElement(HTML.TR_ELEM);
            HtmlRendererUtils.writePrettyLineSeparator(facesContext);
        }
    }

    /**
     * Generate components and output for a single "day" cell within the calendar display.
     */
    private void writeCell(FacesContext facesContext,
                           ResponseWriter writer, UIInput component, String content,
                           Date valueForLink, String styleClass)
            throws IOException
    {
        writer.startElement(HTML.TD_ELEM, component);

        if (styleClass != null)
            writer.writeAttribute(HTML.CLASS_ATTR, styleClass, null);

        if (valueForLink == null)
            writer.writeText(content, JSFAttr.VALUE_ATTR);
        else
        {
            writeLink(content, component, facesContext, valueForLink);
        }

        writer.endElement(HTML.TD_ELEM);
    }

    /**
     * Create child components to represent a link to a specific date value, and render them.
     * <p>
     * For a disabled calendar, this just creates a Text component, attaches it as a child
     * of the calendar and renders it. The value of the component is the string returned by
     * valueForLink.getTime().
     * <p>
     * For a non-disabled calendar, create an HtmlCommandLink child that wraps the text
     * returned by valueForLink.getTime(), and add it to the component.
     */
    private void writeLink(String content,
                           UIInput component,
                           FacesContext facesContext,
                           Date valueForLink)
            throws IOException
    {
        Converter converter = getConverter(component);
        Application application = facesContext.getApplication();

        HtmlOutputText text
                = (HtmlOutputText)application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue(content);
        text.setId(component.getId() + "_" + valueForLink.getTime() + "_text");
        text.setTransient(true);

        HtmlInputCalendar calendar = (HtmlInputCalendar)component;
        if (calendar.isDisabled() || calendar.isReadonly())
        {
            component.getChildren().add(text);

            RendererUtils.renderChild(facesContext, text);
            return;
        }

        HtmlCommandLink link
                = (HtmlCommandLink)application.createComponent(HtmlCommandLink.COMPONENT_TYPE);
        link.setId(component.getId() + "_" + valueForLink.getTime() + "_link");
        link.setTransient(true);
        link.setImmediate(component.isImmediate());

        UIParameter parameter
                = (UIParameter)application.createComponent(UIParameter.COMPONENT_TYPE);
        parameter.setId(component.getId() + "_" + valueForLink.getTime() + "_param");
        parameter.setTransient(true);
        parameter.setName(component.getClientId(facesContext));
        parameter.setValue(converter.getAsString(facesContext, component, valueForLink));

        RendererUtils.addOrReplaceChild(component,link);
        link.getChildren().add(parameter);
        link.getChildren().add(text);

        RendererUtils.renderChild(facesContext, link);
    }

    private Converter getConverter(UIInput component)
    {
        Converter converter = component.getConverter();

        if (converter == null)
        {
            converter = new CalendarDateTimeConverter();
        }
        return converter;
    }

    private int mapCalendarDayToCommonDay(int day)
    {
        switch (day)
        {
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.SUNDAY:
                return 6;
            default:
                return 0;
        }
    }

    private static String[] mapShortWeekdays(DateFormatSymbols symbols)
    {
        String[] weekdays = new String[7];

        String[] localeWeekdays = symbols.getShortWeekdays();

        weekdays[0] = localeWeekdays[Calendar.MONDAY];
        weekdays[1] = localeWeekdays[Calendar.TUESDAY];
        weekdays[2] = localeWeekdays[Calendar.WEDNESDAY];
        weekdays[3] = localeWeekdays[Calendar.THURSDAY];
        weekdays[4] = localeWeekdays[Calendar.FRIDAY];
        weekdays[5] = localeWeekdays[Calendar.SATURDAY];
        weekdays[6] = localeWeekdays[Calendar.SUNDAY];

        return weekdays;
    }

    private static String[] mapShortWeekdaysStartingWithSunday(DateFormatSymbols symbols)
    {
        String[] weekdays = new String[7];

        String[] localeWeekdays = symbols.getShortWeekdays();

        weekdays[0] = localeWeekdays[Calendar.SUNDAY];
        weekdays[1] = localeWeekdays[Calendar.MONDAY];
        weekdays[2] = localeWeekdays[Calendar.TUESDAY];
        weekdays[3] = localeWeekdays[Calendar.WEDNESDAY];
        weekdays[4] = localeWeekdays[Calendar.THURSDAY];
        weekdays[5] = localeWeekdays[Calendar.FRIDAY];
        weekdays[6] = localeWeekdays[Calendar.SATURDAY];

        return weekdays;
    }

    
    private static String[] mapShortWeekdaysStartingWithSaturday(DateFormatSymbols symbols) 
    {
        String[] weekdays = new String[7];

        String[] localeWeekdays = symbols.getShortWeekdays();

        weekdays[0] = localeWeekdays[Calendar.SATURDAY];
        weekdays[1] = localeWeekdays[Calendar.SUNDAY];
        weekdays[2] = localeWeekdays[Calendar.MONDAY];
        weekdays[3] = localeWeekdays[Calendar.TUESDAY];
        weekdays[4] = localeWeekdays[Calendar.WEDNESDAY];
        weekdays[5] = localeWeekdays[Calendar.THURSDAY];
        weekdays[6] = localeWeekdays[Calendar.FRIDAY];

        return weekdays;
    }    
    
    private static String[] mapWeekdaysStartingWithSunday(DateFormatSymbols symbols)
    {
        String[] weekdays = new String[7];

        String[] localeWeekdays = symbols.getWeekdays();

        weekdays[0] = localeWeekdays[Calendar.SUNDAY];
        weekdays[1] = localeWeekdays[Calendar.MONDAY];
        weekdays[2] = localeWeekdays[Calendar.TUESDAY];
        weekdays[3] = localeWeekdays[Calendar.WEDNESDAY];
        weekdays[4] = localeWeekdays[Calendar.THURSDAY];
        weekdays[5] = localeWeekdays[Calendar.FRIDAY];
        weekdays[6] = localeWeekdays[Calendar.SATURDAY];

        return weekdays;
    }

    public static String[] mapMonths(DateFormatSymbols symbols)
    {
        String[] months = new String[12];

        String[] localeMonths = symbols.getMonths();

        months[0] = localeMonths[Calendar.JANUARY];
        months[1] = localeMonths[Calendar.FEBRUARY];
        months[2] = localeMonths[Calendar.MARCH];
        months[3] = localeMonths[Calendar.APRIL];
        months[4] = localeMonths[Calendar.MAY];
        months[5] = localeMonths[Calendar.JUNE];
        months[6] = localeMonths[Calendar.JULY];
        months[7] = localeMonths[Calendar.AUGUST];
        months[8] = localeMonths[Calendar.SEPTEMBER];
        months[9] = localeMonths[Calendar.OCTOBER];
        months[10] = localeMonths[Calendar.NOVEMBER];
        months[11] = localeMonths[Calendar.DECEMBER];

        return months;
    }

    public static String[] mapShortMonths(DateFormatSymbols symbols)
    {
        String[] months = new String[12];

        String[] localeMonths = symbols.getShortMonths();

        months[0] = localeMonths[Calendar.JANUARY];
        months[1] = localeMonths[Calendar.FEBRUARY];
        months[2] = localeMonths[Calendar.MARCH];
        months[3] = localeMonths[Calendar.APRIL];
        months[4] = localeMonths[Calendar.MAY];
        months[5] = localeMonths[Calendar.JUNE];
        months[6] = localeMonths[Calendar.JULY];
        months[7] = localeMonths[Calendar.AUGUST];
        months[8] = localeMonths[Calendar.SEPTEMBER];
        months[9] = localeMonths[Calendar.OCTOBER];
        months[10] = localeMonths[Calendar.NOVEMBER];
        months[11] = localeMonths[Calendar.DECEMBER];

        return months;
    }


    public void decode(FacesContext facesContext, UIComponent component)
    {
        if(HtmlRendererUtils.isDisabledOrReadOnly(component))
        {
            // nothing to do here
            return;
        }

        RendererUtils.checkParamValidity(facesContext, component, HtmlInputCalendar.class);

        String helperString = getHelperString(component);

        if (!(component instanceof EditableValueHolder)) {
            throw new IllegalArgumentException("Component "
                                               + component.getClientId(facesContext)
                                               + " is not an EditableValueHolder");
        }
        Map paramMap = facesContext.getExternalContext()
                .getRequestParameterMap();
        String clientId = component.getClientId(facesContext);

        if(paramMap.containsKey(clientId))
        {
            String value = (String) paramMap.get(clientId);

            if(!value.equalsIgnoreCase(helperString))
            {
                ((EditableValueHolder) component).setSubmittedValue(value);
            }
            else
            {
                // The field was initially filled with the "helper string", and has
                // not been altered by the user so treat this as if null had been
                // passed by the user.
                //
                // TODO: does this mean the target date is set to todays date?
                // And how does this affect the "required" property?
                ((EditableValueHolder) component).setSubmittedValue("");
            }
        }
        else
        {
            log.warn(HtmlRendererUtils.NON_SUBMITTED_VALUE_WARNING +
                " Component : "+
                RendererUtils.getPathToComponent(component));
        }

    }

    public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue) throws ConverterException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, HtmlInputCalendar.class);

        UIInput uiInput = (UIInput) uiComponent;

        Converter converter = uiInput.getConverter();

        if(converter==null)
        {
            converter = new CalendarDateTimeConverter();
        }

        if (submittedValue != null && !(submittedValue instanceof String))
        {
            throw new IllegalArgumentException("Submitted value of type String expected");
        }

        return converter.getAsObject(facesContext, uiComponent, (String) submittedValue);
    }

    public interface DateConverter extends Converter
    {
        public Date getAsDate(FacesContext facesContext, UIComponent uiComponent);
    }

    private static String getHelperString(UIComponent uiComponent)
    {
        return uiComponent instanceof HtmlInputCalendar?((HtmlInputCalendar) uiComponent).getHelpText():null;
    }

    public static class CalendarDateTimeConverter implements DateConverter
    {
        private static final String CONVERSION_MESSAGE_ID = "org.apache.myfaces.calendar.CONVERSION";

        public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s)
        {
            if(s==null || s.trim().length()==0 || s.equals(getHelperString(uiComponent)))
                return null;

            if(uiComponent instanceof HtmlInputCalendar && ((HtmlInputCalendar) uiComponent).isRenderAsPopup())
            {
                HtmlInputCalendar calendar = (HtmlInputCalendar) uiComponent;
                String popupDateFormat = calendar.getPopupDateFormat();
                String formatStr = createJSPopupFormat(facesContext, popupDateFormat);
                Locale locale = facesContext.getViewRoot().getLocale();
                Calendar timeKeeper = Calendar.getInstance(locale);
                int firstDayOfWeek = timeKeeper.getFirstDayOfWeek() - 1;
                org.apache.myfaces.dateformat.DateFormatSymbols symbols = new org.apache.myfaces.dateformat.DateFormatSymbols(locale);
                SimpleDateFormatter dateFormat = new SimpleDateFormatter(formatStr, symbols, firstDayOfWeek);
                
                Date date = dateFormat.parse(s); 
                if (date != null) {
                    return date;
                }
                FacesMessage msg = MessageUtils.getMessage(CONVERSION_MESSAGE_ID,new Object[]{
                        uiComponent.getId(),s});
                throw new ConverterException(msg);
            }
            else
            {
                DateFormat dateFormat = createStandardDateFormat(facesContext);
                dateFormat.setLenient(false);
                try
                {
                    Date date = dateFormat.parse(s); 
                    return date;
                }
                catch (ParseException e)
                {
                    FacesMessage msg = MessageUtils.getMessage(CONVERSION_MESSAGE_ID,new Object[]{
                            uiComponent.getId(),s});
                    throw new ConverterException(msg,e);
                }
            }
        }

        public Date getAsDate(FacesContext facesContext, UIComponent uiComponent)
        {
            return RendererUtils.getDateValue(uiComponent);
        }

        public static String createJSPopupFormat(FacesContext facesContext, String popupDateFormat)
        {

            if(popupDateFormat == null)
            {
                SimpleDateFormat defaultDateFormat = createStandardDateFormat(facesContext);
                popupDateFormat = defaultDateFormat.toPattern();
            }

            return popupDateFormat;
        }

        public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o)
        {
            Date date = (Date) o;

            if(date==null)
                return getHelperString(uiComponent);

            if(uiComponent instanceof HtmlInputCalendar && ((HtmlInputCalendar) uiComponent).isRenderAsPopup())
            {
                HtmlInputCalendar calendar = (HtmlInputCalendar) uiComponent;
                String popupDateFormat = calendar.getPopupDateFormat();
                String formatStr = createJSPopupFormat(facesContext, popupDateFormat);
                Locale locale = facesContext.getViewRoot().getLocale();
                Calendar timeKeeper = Calendar.getInstance(locale);
                int firstDayOfWeek = timeKeeper.getFirstDayOfWeek() - 1;
                org.apache.myfaces.dateformat.DateFormatSymbols symbols = new org.apache.myfaces.dateformat.DateFormatSymbols(locale);

                SimpleDateFormatter dateFormat = new SimpleDateFormatter(formatStr, symbols, firstDayOfWeek);
                return dateFormat.format(date);
            }
            else
            {
                DateFormat dateFormat = createStandardDateFormat(facesContext);
                dateFormat.setLenient(false);
                return dateFormat.format(date);
            }
        }

        private static SimpleDateFormat createStandardDateFormat(FacesContext facesContext)
        {
            DateFormat dateFormat;
            dateFormat = DateFormat.getDateInstance(DateFormat.SHORT,
                                                    facesContext.getViewRoot().getLocale());

            if(dateFormat instanceof SimpleDateFormat)
                return (SimpleDateFormat) dateFormat;
            else
                return new SimpleDateFormat("dd.MM.yyyy", facesContext.getViewRoot().getLocale());
        }
    }
}
