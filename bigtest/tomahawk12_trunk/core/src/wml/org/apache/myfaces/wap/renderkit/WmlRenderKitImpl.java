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
package org.apache.myfaces.wap.renderkit;

import javax.faces.render.Renderer;
import javax.faces.render.RenderKit;

import javax.faces.render.ResponseStateManager;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class WmlRenderKitImpl extends RenderKit {
    private static Log log = LogFactory.getLog(WmlRenderKitImpl.class);

    private Map renderers;
    private ResponseStateManager rsm;

    /** Creates a new instance of RenderKitImpl */
    public WmlRenderKitImpl() {
        log.debug("created object " + this.getClass().getName());
        renderers = new HashMap();
        rsm = new WmlResponseStateManagerImpl();
        //rsm = new com.sun.faces.renderkit.ResponseStateManagerImpl();
    }

    public void addRenderer(String family, String rendererType, javax.faces.render.Renderer renderer) {
        renderers.put(family + "." + rendererType, renderer);
    }

    public Renderer getRenderer(String family, String rendererType) {
        log.debug("getRenderer() family:" + family + " renderType:" + rendererType);
        Renderer renderer = (Renderer)renderers.get(family + "." + rendererType);
        if (renderer == null){
            log.warn("Unsupported component-family/renderer-type: " + family + "/" + rendererType);
        }
        return(renderer);
    }

    public ResponseStateManager getResponseStateManager() {
        return(rsm);
    }

    public javax.faces.context.ResponseStream createResponseStream(java.io.OutputStream outputStream) {
        throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
    }

    public javax.faces.context.ResponseWriter createResponseWriter(java.io.Writer writer, String contentTypeList, String characterEncoding) {
        log.debug("createResponseWriter()");
        if (contentTypeList == null) {
            log.info("No content type list given, creating WmlResponseWriterImpl with default content type.");
            return new WmlResponseWriterImpl(writer, null, characterEncoding);
        }

        StringTokenizer st = new StringTokenizer(contentTypeList, ",");
        while (st.hasMoreTokens()) {
            String contentType = st.nextToken().trim();
            return new WmlResponseWriterImpl(writer, contentType, characterEncoding);
        }

        throw new IllegalArgumentException("ContentTypeList does not contain a supported content type: " + contentTypeList);
    }

}
