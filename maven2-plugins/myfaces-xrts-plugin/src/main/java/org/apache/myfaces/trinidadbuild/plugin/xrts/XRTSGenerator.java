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

import javax.xml.parsers.SAXParser;

import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * The <code>XRTSGenerator</code> class is a online facility (as opposed to the
 * command line utility <code>XRTSMakeBundle</code>) for parsing and building
 * XML-based RTS files to the given implementation output. <p>
 *
 * @version $Name:  $ ($Revision: 1.1 $) $Date: 2001/07/09 18:15:53 $
 * @since RTS 2.0
 */
public final class XRTSGenerator
{

  /**
   * Parse the XML-based RTS source and create output according to an
   * <code>RTSWriter</code> implementation.
   *
   * @param parser see class description.
   * @param is the XML-based RTS source file.
   * @param rtsw an <code>RTSWriter</code> implementation.
   * @param parms a <code>Map</code> of command line parameters.
   *
   */
  public static void generate(SAXParser parser, InputSource is, RTSWriter rtsw,
    Map parms) throws Throwable
  {
    XRTSParser rtsp = new XRTSParser(rtsw, parms);
    parser.parse(is, rtsp);
  }

  //
  // No need to instantiate this class
  //
  private XRTSGenerator()
  {
  }
}
