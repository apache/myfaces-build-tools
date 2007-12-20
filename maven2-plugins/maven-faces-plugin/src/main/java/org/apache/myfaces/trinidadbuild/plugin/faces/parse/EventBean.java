/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.myfaces.trinidadbuild.plugin.faces.parse;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.Util;

/**
 * EventBean is a Java representation of the faces-config-extension
 * event XML element.
 */
public class EventBean extends ObjectBean
{
  public String getEventName()
  {
    return Util.getEventNameFromEventType(_eventType);
  }

  /**
   * Sets the description of this property.
   *
   * @param description  the property description
   */
  public void setDescription(
    String description)
  {
    _description = description;
  }

  /**
   * Returns the description of this property.
   *
   * @return  the property description
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Sets the event type for this event.
   *
   * @param eventType  the event type
   */
  public void setEventType(
    String eventType)
  {
    _eventType = eventType;
  }

  /**
   * Returns the event type for this event.
   *
   * @return  the event type
   */
  public String getEventType()
  {
    return _eventType;
  }

  /**
   * Sets the event class for this event.
   *
   * @param eventName  the event class
   */
  public void setEventClass(
    String eventClass)
  {
    _eventClass = eventClass;
  }

  /**
   * Returns the event class for this event.
   *
   * @return  the event class
   */
  public String getEventClass()
  {
    return _eventClass;
  }

  /**
   * Sets the event listener class for this event.
   *
   * @param eventListenerClass  the event listener class
   */
  public void setEventListenerClass(
    String eventListenerClass)
  {
    _eventListenerClass = eventListenerClass;
  }

  /**
   * Returns the event listener class for this event.
   *
   * @return  the event listener class
   */
  public String getEventListenerClass()
  {
    return _eventListenerClass;
  }

  /**
   * Sets the event source interface for this event.
   *
   * @param eventSourceInterface  the event source interface
   */
  public void setEventSourceInterface(
    String eventSourceInterface)
  {
    _eventSourceInterface = eventSourceInterface;
  }

  /**
   * Returns the event source interface for this event.
   *
   * @return  the event source interface
   */
  public String getEventSourceInterface()
  {
    return _eventSourceInterface;
  }

  private String _description;
  private String _eventType;
  private String _eventClass;
  private String _eventListenerClass;
  private String _eventSourceInterface;
}
