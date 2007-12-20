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
 * EventBean is a Java representation of the faces-config component-extension
 * event XML element.
 */
public class EventRefBean extends ObjectBean
{
  public String getEventName()
  {
    return Util.getEventNameFromEventType(_eventType);
  }

  /**
   * Sets the event type for this event reference.
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
   * Sets the delivery phases for this event.
   *
   * @param deliveryPhases  the event delivery phases
   */
  public void setEventDeliveryPhases(
    String[] deliveryPhases)
  {
    if ((_deliveryPhases != null) && (_deliveryPhases.length > 0))
    {
      int oldLen = _deliveryPhases.length;
      int newLen = deliveryPhases.length;
      String[] newDeliveryPhases = new String[oldLen + newLen];
      System.arraycopy(_deliveryPhases, 0, newDeliveryPhases, 0, oldLen);
      System.arraycopy(deliveryPhases, 0, newDeliveryPhases, oldLen, newLen);
      _deliveryPhases = newDeliveryPhases;
    }
    else
    {
      _deliveryPhases = deliveryPhases;
    }
    
  }

  /**
   * Returns the delivery phases for this event.
   *
   * @return  the event delivery phases
   */
  public String[] getEventDeliveryPhases()
  {
    return _deliveryPhases;
  }

  /**
   * Sets a flag indicating whether or not to automatically implement
   * the event source interface on the generated component source.
   *
   * @param ignoreSourceInterface  true,  to ignore the event source interface;
   *                               false, otherwise
   */
  public void setIgnoreSourceInterface(
    boolean ignoreSourceInterface)
  {
    _ignoreSourceInterface = ignoreSourceInterface;
  }

  /**
   * Returns a flag indicating whether or not to automatically implement
   * the event source interface on the generated component source.
   *
   * @return  true,  to ignore the event source interface;
   *          false, otherwise
   */
  public boolean isIgnoreSourceInterface()
  {
    return _ignoreSourceInterface;
  }

  /**
   * Resolves the event type.
   *
   * @return  the event with the referencing event type,
   *          or null if not found
   */
  public EventBean resolveEventType()
  {
    return getOwner().findEvent(_eventType);
  }

  private String   _eventType;
  private String[] _deliveryPhases;
  private boolean  _ignoreSourceInterface;
}
