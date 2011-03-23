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

import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ClientBehaviorMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.QdoxHelper;

import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;

/**
 * 
 * @author Leonardo Uribe
 * @since 1.0.9
 *
 */
public class ClientBehaviorParsingStrategy extends ClassMetaPropertyParsingStrategy
{
    private static final String DOC_CLIENT_BEHAVIOR = "JSFClientBehavior";

    public void parseClass(JavaClass clazz, Model model)
    {
        DocletTag tag;
        Annotation anno;
        // client behaviors
        tag = clazz.getTagByName(DOC_CLIENT_BEHAVIOR, false);
        if (tag != null)
        {
            Map props = tag.getNamedParameterMap();
            processClientBehavior(props, (AbstractJavaEntity)tag.getContext(), clazz, model);
        }
        anno = QdoxHelper.getAnnotation(clazz, DOC_CLIENT_BEHAVIOR);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processClientBehavior(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
        }
    }

    private void processClientBehavior(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, Model model)
    {
        String longDescription = clazz.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = QdoxHelper.getString(clazz, "desc", props, descDflt);

        String behaviorIdDflt = null;
        JavaField fieldBehaviorId = clazz
                .getFieldByName("BEHAVIOR_ID");
        if (fieldBehaviorId != null)
        {
            String value = fieldBehaviorId.getInitializationExpression();
            behaviorIdDflt = QdoxHelper.clean(value.substring(value.indexOf('"')));
        }
        
        String rendererTypeDflt = null;
        JavaField fieldRendererType = clazz
                .getFieldByName("RENDERER_TYPE");
        if (fieldRendererType != null)
        {
            rendererTypeDflt = QdoxHelper.clean(fieldRendererType
                    .getInitializationExpression());
        }
        
        String behaviorId = QdoxHelper.getString(clazz, "id", props, behaviorIdDflt);

        // Check for both "class" and "clazz" in order to support
        // doclet and real annotations.
        String classNameOverride = QdoxHelper.getString(clazz, "class", props, null);
        classNameOverride = QdoxHelper.getString(clazz,"clazz",props,classNameOverride);
        
        String componentName = QdoxHelper.getString(clazz, "name", props, null);
        String bodyContent = QdoxHelper.getString(clazz, "bodyContent", props, null);
        String tagHandler = QdoxHelper.getString(clazz, "tagHandler", props, null);
        Boolean configExcluded = QdoxHelper.getBoolean(clazz,"configExcluded",props,null);
        Boolean evaluateELOnExecution = QdoxHelper.getBoolean(clazz,"evaluateELOnExecution",props,null);
        String rendererType = QdoxHelper.getString(clazz, "rendererType", props, rendererTypeDflt);

        ClientBehaviorMeta behavior = new ClientBehaviorMeta();
        initClassMeta(model, clazz, behavior, classNameOverride);
        behavior.setName(componentName);
        behavior.setBodyContent(bodyContent);
        behavior.setBehaviorId(behaviorId);
        behavior.setDescription(shortDescription);
        behavior.setLongDescription(longDescription);
        behavior.setTagHandler(tagHandler);
        behavior.setConfigExcluded(configExcluded);
        behavior.setEvaluateELOnExecution(evaluateELOnExecution);
        behavior.setRendererType(rendererType);
        
        // Now here walk the component looking for property annotations.
        processComponentProperties(clazz, behavior);
        
        model.addBehavior(behavior);
    }    
}
