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
 * ScreenshotBean is a Java representation of the faces-config mfp:screenshot
 * XML element.
 * An example of how the ScreenshotBean would be represented as an XML element is:
 * <mfp:screenshot>
 *   <mfp:image>
 *     <![CDATA[
 *     <img src="../images/inputDate.png" alt="inputDate screenshot"></img>
 *     ]]> 
 *   </mfp:image>
 *   <mfp:description>
 *     inputDate component as shown when rendered in a simple form
 *   </mfp:description>
 * </mfp:screenshot>
 */
public class ScreenshotBean extends ObjectBean
{
  /**
   * Get Description Text. The description text is used as a caption for the screen
   * shot image in the generated tag doc.
   * 
   * @return Screenshot Description Text
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set screenshot Description Text.  The description text is used as a caption for the screen
   * shot image in the generated tag doc.
   *
   * @param description   screenshot Description Text.
   */
  public void setDescription( String description )
  {
    _description = description;
  }

  /**
   * Returns image Screenshot.
   *
   * @return image Screenshot
   */
  public String getImage()
  {
    return _image;
  }

  /**
   * Set image for screenshot. 
   *
   * @param image  image screenshot to be added to the list.
   */
  public void setImage( String image )
  {
    _image = image;
  }

  /**
   * Returns Screenshot hashmap key.
   *
   * @return Screenshot hashmap key
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Set hashmap key for this screenshot.
   *
   * @param key Set key for this screenshot put in 
   *        ComponentBean _screenshots hashmap.
   */
  protected void setKey( String key )
  {
    _key = key;
  }

  private String _description  = null;
  private String _image        = null;
  private String _key          = null;
}
