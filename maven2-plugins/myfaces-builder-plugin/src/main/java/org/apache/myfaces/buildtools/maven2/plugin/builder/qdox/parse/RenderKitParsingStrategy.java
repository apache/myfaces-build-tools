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
package org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.parse;

import java.util.Map;

import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.RenderKitMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.QdoxHelper;

import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;

/**
 * 
 * @author Leonardo Uribe
 * @since 1.0.9
 *
 */
public class RenderKitParsingStrategy implements JavaClassParsingStrategy
{
    private static final String DOC_RENDERKIT = "JSFRenderKit";

    public void parseClass(JavaClass clazz, Model model)
    {
        DocletTag tag = null;
        Annotation anno = null;
        // renderKit
        tag = clazz.getTagByName(DOC_RENDERKIT, false);
        if (tag != null)
        {
            Map props = tag.getNamedParameterMap();
            processRenderKit(props, (AbstractJavaEntity)tag.getContext(), clazz, model);
        }
        anno = QdoxHelper.getAnnotation(clazz, DOC_RENDERKIT);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processRenderKit(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
        }
    }
    
    private void processRenderKit(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, Model model)
    {

        String renderKitId = QdoxHelper.getString(clazz, "renderKitId", props, null);
        String renderKitClass = QdoxHelper.getString(clazz, "class", props, clazz
                .getFullyQualifiedName());
        renderKitClass = QdoxHelper.getString(clazz,"clazz",props,renderKitClass);

        RenderKitMeta renderKit = model.findRenderKitById(renderKitId);
        
        if (renderKit == null)
        {
            renderKit = new RenderKitMeta();
            renderKit.setClassName(renderKitClass);
            renderKit.setRenderKitId(renderKitId);
            model.addRenderKit(renderKit);        
        }
        else
        {
            renderKit.setClassName(renderKitClass);
            renderKit.setRenderKitId(renderKitId);            
        }
    }
}
