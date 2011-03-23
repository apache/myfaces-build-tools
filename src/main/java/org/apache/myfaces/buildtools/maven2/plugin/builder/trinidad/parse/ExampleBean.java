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
package org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse;

/**
 * ExampleBean is a Java representation of the faces-config component or
 * renderer Example XML element.
 */
public class ExampleBean extends ObjectBean
{
  /**
   * Get source Example Description Text
   * 
   * @return source Example Description Text
   */
  public String getSourceDescription()
  {
    return _sourceDescription;
  }

  /**
   * Set source example Description Text.
   *
   * @param sourceDescription  source example Description Text.
   */
  public void setSourceDescription( String sourceDescription )
  {
    _sourceDescription = sourceDescription;
  }

  /**
   * Returns source Example.
   *
   * @return source Example
   */
  public String getSourceCode()
  {
    return _source;
  }

  /**
   * Set source example.
   *
   * @param source  source example to be added to the list.
   */
  public void setSourceCode( String source )
  {
    _source = source;
  }

  /**
   * Returns Example hashmap key.
   *
   * @return Example hashmap key
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Set source example.
   *
   * @param key Set key for this example put in 
   *        ComponentBean _examples hashmap.
   */
  protected void setKey( String key )
  {
    _key = key;
  }

  private String _sourceDescription = null;
  private String _source            = null;
  private String _key               = null;
}
