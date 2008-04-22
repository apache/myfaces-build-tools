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

import javax.faces.render.ResponseStateManager;

import javax.faces.FacesException;
import javax.faces.application.StateManager;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */ 
public class WmlResponseStateManagerImpl extends ResponseStateManager {
    private static Log log = LogFactory.getLog(WmlResponseStateManagerImpl.class);

    private static final String TREE_PARAM = "jsf_tree";
    private static final String STATE_PARAM = "jsf_state";
    private static final String VIEWID_PARAM = "jsf_viewid";
    private static final String BASE64_TREE_PARAM = "jsf_tree_64";
    private static final String BASE64_STATE_PARAM = "jsf_state_64";
    private static final String ZIP_CHARSET = "UTF-8";

    private static final String WML_POSTFIELD = "postfield";
    private static final String WML_POSTFIELD_NAME = "name";
    private static final String WML_POSTFIELD_VALUE = "value";

    public void writeState(FacesContext facescontext, StateManager.SerializedView serializedview) throws IOException {
        ResponseWriter responseWriter = facescontext.getResponseWriter();
        Object treeStruct = serializedview.getStructure();
        Object compStates = serializedview.getState();

        if (treeStruct != null) {
            if (treeStruct instanceof String) {
                responseWriter.startElement(WML_POSTFIELD, null);
                responseWriter.writeAttribute(WML_POSTFIELD_NAME, TREE_PARAM, null);
                responseWriter.writeAttribute(WML_POSTFIELD_VALUE, treeStruct, null);
                responseWriter.endElement(WML_POSTFIELD);
            }
            else {
                responseWriter.startElement(WML_POSTFIELD, null);
                responseWriter.writeAttribute(WML_POSTFIELD_NAME, BASE64_TREE_PARAM, null);
                responseWriter.writeAttribute(WML_POSTFIELD_VALUE, encode64(treeStruct), null);
                responseWriter.endElement(WML_POSTFIELD);
            }
        }
        else {
            log.error("No tree structure to be saved in client response!");
        }

        if (compStates != null) {
            if (compStates instanceof String) {
                responseWriter.startElement(WML_POSTFIELD, null);
                responseWriter.writeAttribute(WML_POSTFIELD_NAME, STATE_PARAM, null);
                responseWriter.writeAttribute(WML_POSTFIELD_VALUE, compStates, null);
                responseWriter.endElement(WML_POSTFIELD);
            }
            else {
                responseWriter.startElement(WML_POSTFIELD, null);
                responseWriter.writeAttribute(WML_POSTFIELD_NAME, BASE64_STATE_PARAM, null);
                responseWriter.writeAttribute(WML_POSTFIELD_VALUE, encode64(compStates), null);
                responseWriter.endElement(WML_POSTFIELD);
            }
        }
        else {
            log.error("No component states to be saved in client response!");
        }

        responseWriter.startElement(WML_POSTFIELD, null);
        responseWriter.writeAttribute(WML_POSTFIELD_NAME, VIEWID_PARAM, null);
        responseWriter.writeAttribute(WML_POSTFIELD_VALUE, facescontext.getViewRoot().getViewId(), null);
        responseWriter.endElement(WML_POSTFIELD);
    }

    private String encode64(Object obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStream zos = new GZIPOutputStream(baos);
            ObjectOutputStream oos = new ObjectOutputStream(zos);
            oos.writeObject(obj);
            oos.close();
            zos.close();
            baos.close();
            Base64 base64Codec = new Base64();
            return new String(base64Codec.encode( baos.toByteArray() ), ZIP_CHARSET);
        }
        catch (IOException e) {
            log.fatal("Cannot encode Object with Base64", e);
            throw new FacesException(e);
        }
    }


    public Object getTreeStructureToRestore(FacesContext facescontext, String viewId) {
        Map reqParamMap = facescontext.getExternalContext().getRequestParameterMap();
        Object param = reqParamMap.get(VIEWID_PARAM);
        if (param == null || !param.equals(viewId)) {
            //no saved state or state of different viewId
            return null;
        }

        param = reqParamMap.get(TREE_PARAM);
        if (param != null) {
            return param;
        }

        param = reqParamMap.get(BASE64_TREE_PARAM);
        if (param != null) {
            return decode64((String)param);
        }

        return null;
    }

    public Object getComponentStateToRestore(FacesContext facescontext) {
        Map reqParamMap = facescontext.getExternalContext().getRequestParameterMap();
        Object param = reqParamMap.get(STATE_PARAM);
        if (param != null) {
            return param;
        }

        param = reqParamMap.get(BASE64_STATE_PARAM);
        if (param != null) {
            return decode64((String)param);
        }

        return null;
    }

    private Object decode64(String s) {
        try {
            Base64 base64Codec = new Base64();
            ByteArrayInputStream decodedStream = new ByteArrayInputStream( base64Codec.decode( s.getBytes(ZIP_CHARSET) ) );
            InputStream unzippedStream = new GZIPInputStream(decodedStream);
            ObjectInputStream ois = new ObjectInputStream(unzippedStream);
            Object obj = ois.readObject();
            ois.close();
            unzippedStream.close();
            decodedStream.close();
            return obj;
        }
        catch (IOException e) {
            log.fatal("Cannot decode Object from Base64 String", e);
            throw new FacesException(e);
        }
        catch (ClassNotFoundException e) {
            log.fatal("Cannot decode Object from Base64 String", e);
            throw new FacesException(e);
        }
    }



    private void writeStateParam(ResponseWriter writer, String name, String value) throws IOException {
        writer.write(name);
        writer.write('=');
        writer.write(URLEncoder.encode(value, writer.getCharacterEncoding()));
    }
}
