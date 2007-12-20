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

/**
 * ObjectBean is the base class for all parsed beans.
 */
public class ObjectBean
{
  /**
   * Creates a new ObjectBean.
   */
  public ObjectBean()
  {
  }

  /**
   * Returns true if this faces config document has been modified since
   * the specified timestamp.
   *
   * @param timestamp  the timestamp in UTC millis
   *
   * @return true  if this faces config document has been modified,
   *         otherwise false
   */
  public boolean isModifiedSince(
    long timestamp)
  {
    return (_lastModified > timestamp);
  }

  protected FacesConfigBean getOwner()
  {
    return _owner;
  }

  /**
   * Attaches the object.
   *
   * @param owner  the faces config owner
   */
  protected void attach(
    FacesConfigBean owner)
  {
    _owner = owner;
    ObjectBean object = owner;
    touch(object._lastModified);
  }

  /**
   * Sets the last modified timestamp for the parsed faces config document
   * if it is newer than the existing last modified timestamp.
   *
   * @param lastModified  the last modified time in UTC millis
   */
  void touch(
    long lastModified)
  {
    _lastModified = Math.max(_lastModified, lastModified);
  }

  private long _lastModified;
  private FacesConfigBean _owner;
}
