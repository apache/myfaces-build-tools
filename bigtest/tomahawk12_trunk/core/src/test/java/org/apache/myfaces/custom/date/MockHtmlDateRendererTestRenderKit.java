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

import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;
import javax.faces.render.ResponseStateManager;

public class MockHtmlDateRendererTestRenderKit extends RenderKit {
  private Map renderers = new HashMap();
  
  public MockHtmlDateRendererTestRenderKit() {
    addRenderer("javax.faces.Input", "org.apache.myfaces.Date", new HtmlDateRenderer());
  }
  
  public void addRenderer(String family, String rendererType, Renderer renderer) {
    Map sub = (Map)renderers.get(family);
    if(null == sub) {
      sub = new HashMap();
      renderers.put(family, sub);
    }
    sub.put(rendererType, renderer);
  }

  public Renderer getRenderer(String family, String rendererType) {
    Renderer renderer = null;
    Map sub = (Map)renderers.get(family);
    if(null != sub) {
      renderer = (Renderer)sub.get(rendererType);
    }
    return renderer;
  }

  public ResponseStateManager getResponseStateManager() {
    return null;
  }

  public ResponseWriter createResponseWriter(Writer writer,
      String contentTypeList, String characterEncoding) {
    return null;
  }

  public ResponseStream createResponseStream(OutputStream out) {
    return null;
  }

}
