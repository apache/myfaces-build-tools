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
package org.apache.myfaces.trinidadbuild.plugin.xrts;

import java.util.Map;

/**
 * The <code>RTSWriter</code> interface defines methods for writing the
 * header, body, and footer of various output implementations. <p>
 *
 * The methods <code>startBundle</code> and <code>endBundle</code> write the
 * header and footer portions to the output.  The methods for writing the
 * body are <code>writeString</code>, <code>writeStringArray</code>, and
 * <code>writeMap</code>. These methods are supplied with data parsed
 * by the XML-based RTS file <code>XRTSParser</code> or the
 * <code>RTSProperties</code> parser.<p>
 *
 * @see ListRTSWriter
 *
 * @version $Name:  $ ($Revision: 1.7 $) $Date: 2001/04/09 23:22:22 $
 */
public interface RTSWriter
{

  /**
   * Writes the <file or other output header(s).
   *
   * @param parms a <code>Map</code> of command line parameters.
   * @param meta a <code>Map</code> of parsed non-resource data
   * (e.g., package).
   */
  public void startBundle(Map parms, Map meta)
    throws Throwable;

  /**
   * Writes a <code>String</code> to the implementation output. <p>
   *
   * This is the typical case use.  A single <code>String</code> key associated
   * with a single <code>String</code> value forming a key/value pair (kvp).
   * Note that the other two body methods, <code>writeStringArray</code> and
   * <code>writeMap</code> both support a single <code>String</code>
   * key associated with multiple values. <p>
   *
   * A key/value pair may have associated attributes in the RTS file.
   * In this method, these are received from the XML parser as a
   * <code>Map</code>. <p>
   *
   * @param parms a <code>Map</code> of command line parameters
   * @param meta a <code>Map</code> of parsed non-resource data
   * (e.g., package).
   * @param key a <code>String</code> key.
   * @param value a <code>String</code> value.
   */
  public void writeString(Map parms, Map meta, String key,
    String value) throws Throwable;

  /**
   * Writes the file or other output footer(s).
   *
   * @param parms a <code>Map</code> of command line parameters.
   * @param meta a <code>Map</code> of parsed non-resource data
   * (e.g., package).
   */
  public void endBundle(Map parms, Map meta) throws Throwable;

}
