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
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.WebConfigMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.WebConfigParamMeta;
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
public class WebConfigParamParsingStrategy implements JavaClassParsingStrategy
{
    private static final String DOC_WEB_CONFIG_PARAM = "JSFWebConfigParam";

    public void parseClass(JavaClass clazz, Model model)
    {
        DocletTag tag;
        Annotation anno;
        WebConfigMeta webConfig = model.findWebConfigsByModelId(model.getModelId());
        boolean createWebConfig = false;
        if (webConfig == null)
        {
            createWebConfig = true;
            webConfig = new WebConfigMeta();
            webConfig.setModelId(model.getModelId());
        }
        //Web Config Params
        JavaField[] fields = clazz.getFields();
        for (int i = 0; i < fields.length; ++i)
        {
            JavaField field = fields[i];
            tag = field.getTagByName(DOC_WEB_CONFIG_PARAM);
            if (tag != null)
            {
                Map props = tag.getNamedParameterMap();
                processWebConfigParam(props, (AbstractJavaEntity)tag.getContext(), 
                        clazz, field, webConfig);
            }
            anno = QdoxHelper.getAnnotation(field, DOC_WEB_CONFIG_PARAM);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processWebConfigParam(props, (AbstractJavaEntity)anno.getContext(),
                        clazz, field, webConfig);
            }
        }
        if (webConfig.webConfigParametersSize() > 0 && createWebConfig)
        {
            model.addWebConfig(webConfig);
        }
    }
    
    private void processWebConfigParam(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaField field, WebConfigMeta webConfig)
    {
        String longDescription = field.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = QdoxHelper.getString(clazz, "desc", props, descDflt);
        
        String name = QdoxHelper.getString(clazz, "name", props, 
                QdoxHelper.evaluateParameterInitializationExpression(
                        field.getInitializationExpression()));
        String defaultValue = QdoxHelper.getString(clazz,"defaultValue",props,null);
        String expectedValues = QdoxHelper.getString(clazz,"expectedValues",props,null);
        String since = QdoxHelper.getString(clazz,"since",props,null);
        
        WebConfigParamMeta wcp = new WebConfigParamMeta();
        wcp.setName(name);
        wcp.setFieldName(field.getName());
        wcp.setSourceClassName(clazz.getFullyQualifiedName());
        wcp.setDefaultValue(defaultValue);
        wcp.setExpectedValues(expectedValues);
        wcp.setLongDescription(longDescription);
        wcp.setDescription(shortDescription);
        wcp.setSince(since);
        webConfig.addWebConfigParam(wcp);
    }
}
