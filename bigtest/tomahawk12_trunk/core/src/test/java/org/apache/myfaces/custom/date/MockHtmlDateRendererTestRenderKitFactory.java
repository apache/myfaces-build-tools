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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

public class MockHtmlDateRendererTestRenderKitFactory extends RenderKitFactory {
  private Map renderKits = new HashMap();

  public MockHtmlDateRendererTestRenderKitFactory() {
    addRenderKit(RenderKitFactory.HTML_BASIC_RENDER_KIT, new MockHtmlDateRendererTestRenderKit());
  }
  
  public void addRenderKit(String renderKitId, RenderKit renderKit) {
    renderKits.put(renderKitId, renderKit);
  }

  public RenderKit getRenderKit(FacesContext context, String renderKitId) {
    return (RenderKit)renderKits.get(renderKitId);
  }

  public Iterator getRenderKitIds() {
    List ids = new ArrayList();
    ids.add(RenderKitFactory.HTML_BASIC_RENDER_KIT);
    return ids.iterator();
  }

}
