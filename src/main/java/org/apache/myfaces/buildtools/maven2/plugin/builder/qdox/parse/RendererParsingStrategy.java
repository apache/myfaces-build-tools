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

import java.util.List;
import java.util.Map;

import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.RenderKitMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.RendererMeta;
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
public class RendererParsingStrategy implements JavaClassParsingStrategy
{
    private static final String DOC_RENDERER = "JSFRenderer";
    private static final String DOC_RENDERERS = "JSFRenderers";

    public void parseClass(JavaClass clazz, Model model)
    {
        DocletTag tag = null;
        Annotation anno = null;
        // renderer
        DocletTag [] tags = clazz.getTagsByName(DOC_RENDERER, false);
        for (int i = 0; i < tags.length; i++)
        {
            tag = tags[i];
            if (tag != null)
            {
                Map props = tag.getNamedParameterMap();
                processRenderer(props, (AbstractJavaEntity)tag.getContext(), clazz, model);
            }
        }
        anno = QdoxHelper.getAnnotation(clazz, DOC_RENDERER);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processRenderer(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
        }
        anno = QdoxHelper.getAnnotation(clazz, DOC_RENDERERS);
        if (anno != null)
        {
            Object jspProps = anno.getNamedParameter("renderers");
            
            if (jspProps instanceof Annotation)
            {
                Annotation jspPropertiesAnno = (Annotation) jspProps;
                Map props = jspPropertiesAnno.getNamedParameterMap();
                processRenderer(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
            }
            else
            {
                List jspPropsList = (List) jspProps;
                for (int i = 0; i < jspPropsList.size();i++)
                {
                    Annotation ranno = (Annotation) jspPropsList.get(i);
                    Map props = ranno.getNamedParameterMap();
                    processRenderer(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
                }
            }
        }
    }
    
    private void processRenderer(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, Model model)
    {
        String longDescription = clazz.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = QdoxHelper.getString(clazz, "desc", props, descDflt);
   
        String renderKitId = QdoxHelper.getString(clazz, "renderKitId", props, null);
        String rendererClass = QdoxHelper.getString(clazz, "class", props, clazz
                .getFullyQualifiedName());
        rendererClass = QdoxHelper.getString(clazz,"clazz",props,rendererClass);
        
        String componentFamily = QdoxHelper.getString(clazz,"family", props,null);
        String rendererType = QdoxHelper.getString(clazz,"type", props,null);
        
        RenderKitMeta renderKit = model.findRenderKitById(renderKitId);
        
        if (renderKit == null)
        {
            renderKit = new RenderKitMeta();
            renderKit.setRenderKitId(renderKitId);
            model.addRenderKit(renderKit);            
        }

        RendererMeta renderer = new RendererMeta();
        renderer.setClassName(rendererClass);
        renderer.setDescription(shortDescription);
        renderer.setComponentFamily(componentFamily);
        renderer.setRendererType(rendererType);
        renderKit.addRenderer(renderer);
    }
}
