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
package org.apache.myfaces.trinidad.blank;

import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

/**
 * A typical simple backing bean, that is backed to <code>helloworld.jsp</code>
 * 
 */
public class HelloWorldBacking
{

  //properties
  private String _name;

  /**
   * default empty constructor
   */
  public HelloWorldBacking()
  {   
  }

  //-------------------getter & setter
  public String getName()
  {
    return _name;
  }

  public void setName(String name)
  {
    this._name = name;
  }

  /**
   * Method that is backed to a submit button of a form.
   */
  public String send()
  {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExtendedRenderKitService service = (ExtendedRenderKitService)
      Service.getRenderKitService(facesContext, ExtendedRenderKitService.class);
    service.addScript(facesContext, "alert('Script added by ExtendedRenderKitService')");

    //do real logic
    return ("success");
  }
}