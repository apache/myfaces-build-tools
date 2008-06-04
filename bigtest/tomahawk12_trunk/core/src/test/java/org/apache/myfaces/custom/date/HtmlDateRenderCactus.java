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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import org.apache.myfaces.custom.date.AbstractHtmlInputDate.UserData;

public class HtmlDateRenderCactus extends ServletTestCase {
  private FacesContext facesContext;
  private UIViewRoot viewRoot;
  
  public void setUp() throws Exception {
    FacesContextFactory facesContextFactory = (FacesContextFactory) FactoryFinder
        .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
    LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder
        .getFactory(FactoryFinder.LIFECYCLE_FACTORY);
    Lifecycle lifecycle = lifecycleFactory
        .getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
    facesContext = facesContextFactory.getFacesContext(this.config
        .getServletContext(), request, response, lifecycle);
    assertNotNull(facesContext);
    Application application = facesContext.getApplication();
    ViewHandler viewHandler = application.getViewHandler();
    String viewId = "/index.jsp";
    viewRoot = viewHandler.createView(facesContext, viewId);
    viewRoot.setViewId(viewId);
    facesContext.setViewRoot(viewRoot);
  }

  public void beginDecodeDate(WebRequest request) {
    request.addParameter("test.day", "14");
    request.addParameter("test.month", "1");
    request.addParameter("test.year", "2005");
  }
  
  public void testDecodeDate() {
    HtmlInputDate inputDate = new HtmlInputDate();
    inputDate.setId("test");
    inputDate.setType("date");
    HtmlDateRenderer subject = new HtmlDateRenderer();
    // decode
    subject.decode(facesContext, inputDate);
    UserData data = inputDate.getUserData(Locale.ENGLISH);
    assertEquals("14", data.getDay());
    assertEquals("1", data.getMonth());
    assertEquals("2005", data.getYear());
  }
  
  public void beginDecodeWithSubmittedValue(WebRequest request) {
    request.addParameter("test.day", "14");
    request.addParameter("test.month", "1");
    request.addParameter("test.year", "2005");
  }
  
  public void testDecodeWithSubmittedValue() throws Exception {
    HtmlInputDate inputDate = new HtmlInputDate();
    inputDate.setId("test");
    inputDate.setType("date");
    Date today = new Date();
    inputDate.setSubmittedValue(new UserData(today, Locale.ENGLISH, null,false, "date"));
    HtmlDateRenderer subject = new HtmlDateRenderer();
    // decode
    subject.decode(facesContext, inputDate);
    UserData data = inputDate.getUserData(Locale.ENGLISH);
    assertEquals("14", data.getDay());
    assertEquals("1", data.getMonth());
    assertEquals("2005", data.getYear());
  }

  public void beginDecodeTime(WebRequest request) {
    request.addParameter("test.hours", "12");
    request.addParameter("test.minutes", "15");
    request.addParameter("test.seconds", "35");
  }
  public void testDecodeTime() throws Exception {
    HtmlInputDate inputDate = new HtmlInputDate();
    inputDate.setId("test");
    inputDate.setType("time");
    HtmlDateRenderer subject = new HtmlDateRenderer();
    // decode
    subject.decode(facesContext, inputDate);
    UserData data = inputDate.getUserData(Locale.ENGLISH);
    assertEquals("12", data.getHours());
    assertEquals("15", data.getMinutes());
    assertEquals("35", data.getSeconds());
  }

  public void beginDecodeFull(WebRequest request) {
    request.addParameter("test.day", "14");
    request.addParameter("test.month", "1");
    request.addParameter("test.year", "2005");
    request.addParameter("test.hours", "12");
    request.addParameter("test.minutes", "15");
    request.addParameter("test.seconds", "35");
  }

  public void testDecodeFull() throws Exception {
    HtmlInputDate inputDate = new HtmlInputDate();
    inputDate.setId("test");
    inputDate.setType("full");
    HtmlDateRenderer subject = new HtmlDateRenderer();
    // decode
    subject.decode(facesContext, inputDate);
    UserData data = inputDate.getUserData(Locale.ENGLISH);
    assertEquals("14", data.getDay());
    assertEquals("1", data.getMonth());
    assertEquals("2005", data.getYear());
    assertEquals("12", data.getHours());
    assertEquals("15", data.getMinutes());
    assertEquals("35", data.getSeconds());
  }

  public void beginDecodeFlorp(WebRequest request) {
    request.addParameter("test.day", "14");
    request.addParameter("test.month", "1");
    request.addParameter("test.year", "2005");
    request.addParameter("test.hours", "12");
    request.addParameter("test.minutes", "15");
  }

  public void testDecodeFlorp() throws Exception {
    HtmlInputDate inputDate = new HtmlInputDate();
    inputDate.setId("test");
    // is this correct? Should it parse correctly if the type is not valid?
    inputDate.setType("florp");
    HtmlDateRenderer subject = new HtmlDateRenderer();
    // decode
    subject.decode(facesContext, inputDate);
    UserData data = inputDate.getUserData(Locale.ENGLISH);
    assertEquals("14", data.getDay());
    assertEquals("1", data.getMonth());
    assertEquals("2005", data.getYear());
    assertEquals("12", data.getHours());
    assertEquals("15", data.getMinutes());
  }

  public void beginDecodeDisabled(WebRequest request) {
    request.addParameter("test.day", "14");
    request.addParameter("test.month", "1");
    request.addParameter("test.year", "2005");
  }

  public void testDecodeDisabled() throws Exception {
    HtmlInputDate inputDate = new HtmlInputDate();
    inputDate.setId("test");
    inputDate.setType("date");
    inputDate.setDisabled(true);
    HtmlDateRenderer subject = new HtmlDateRenderer();
    // decode - when disabled currently defaults to today
    // JIRA Issue #233 requests that the default be null
    // and the control handle data that is not required
    subject.decode(facesContext, inputDate);
    UserData data = inputDate.getUserData(Locale.ENGLISH);
    Calendar cal = GregorianCalendar.getInstance();
    assertEquals(cal.get(Calendar.DATE) + "", data.getDay());
    // different bases - cal starts at 0 in January, UserData starts at 1 in
    // January
    assertEquals((cal.get(Calendar.MONTH) + 1) + "", data.getMonth());
    assertEquals(cal.get(Calendar.YEAR) + "", data.getYear());
  }

  public void tearDown() {
    facesContext = null;
    viewRoot = null;
  }
}
