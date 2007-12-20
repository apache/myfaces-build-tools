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
package org.apache.myfaces.trinidadbuild.plugin.faces.generator.component;

import org.apache.myfaces.trinidadbuild.plugin.faces.generator.ClassGenerator;
import org.apache.myfaces.trinidadbuild.plugin.faces.io.PrettyWriter;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.ComponentBean;

import java.io.IOException;
import java.util.Collection;

/**
 * Generates component classes
 *
 * @author Bruno Aranda (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ComponentGenerator extends ClassGenerator
{

  void writeGenericConstants(
      PrettyWriter out,
      String componentFamily,
      String componentType) throws IOException;

  void writePropertyConstants(
      PrettyWriter out,
      String superclassName,
      ComponentBean component) throws IOException;

  void writePropertyValueConstants(
      PrettyWriter out,
      ComponentBean component) throws IOException;

  void writeFacetConstants(
      PrettyWriter out,
      ComponentBean component) throws IOException;

  void writeGetFamily(
      PrettyWriter out) throws IOException;

  void writePropertyMethods(
      PrettyWriter out,
      ComponentBean component) throws IOException;

  void writeFacetMethods(
      PrettyWriter out,
      ComponentBean component) throws IOException;

  void writeListenerMethods(
      PrettyWriter out,
      ComponentBean component) throws IOException;

  void writeStateManagementMethods(
      PrettyWriter out,
      ComponentBean component) throws IOException;

  void writeOther(
      PrettyWriter out, ComponentBean component) throws IOException;


  void writePropertyMethods(PrettyWriter out,
                            ComponentBean component,
                            Collection ignoreList) throws IOException;
}
