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

import org.apache.myfaces.buildtools.maven2.plugin.builder.model.AttributeMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FaceletTagMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.QdoxHelper;

import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.Type;

/**
 * 
 * @author Leonardo Uribe
 * @since 1.0.9
 *
 */
public class FaceletTagParsingStrategy extends ClassMetaParsingStrategy
{
    private static final String DOC_FACELET_TAG = "JSFFaceletTag";
    private static final String DOC_FACELET_TAGS = "JSFFaceletTags";
    private static final String DOC_FACELET_TAG_ATTRIBUTE = "JSFFaceletAttribute";
    private static final String DOC_FACELET_TAG_ATTRIBUTES = "JSFFaceletAttributes";

    public void parseClass(JavaClass clazz, Model model)
    {
        DocletTag tag = null;
        Annotation anno = null;
        //facelet tagHandler
        tag = clazz.getTagByName(DOC_FACELET_TAG, false);
        if (tag != null)
        {
            Map props = tag.getNamedParameterMap();
            processFaceletTag(props, (AbstractJavaEntity)tag.getContext(), clazz, model);
        }
        anno = QdoxHelper.getAnnotation(clazz, DOC_FACELET_TAG);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processFaceletTag(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
        }        
        anno = QdoxHelper.getAnnotation(clazz, DOC_FACELET_TAGS);
        if (anno != null)
        {
            Object jspProps = anno.getNamedParameter("tags");
            if (jspProps instanceof Annotation)
            {
                Annotation jspPropertiesAnno = (Annotation) jspProps;
                Map props = jspPropertiesAnno.getNamedParameterMap();
                processFaceletTag(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
            }
            else
            {
                List jspPropsList = (List) jspProps;
                for (int i = 0; i < jspPropsList.size();i++)
                {
                    Annotation ranno = (Annotation) jspPropsList.get(i);
                    Map props = ranno.getNamedParameterMap();
                    processFaceletTag(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
                }
            }
        }        
    }
    
    private void processFaceletTag(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, Model model)
    {
        String longDescription = clazz.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = QdoxHelper.getString(clazz, "desc", props, descDflt);
        
        longDescription = QdoxHelper.getString(clazz,"longDescription",props, longDescription);

        String tagName = QdoxHelper.getString(clazz, "name", props, null);
        String classNameOverride = QdoxHelper.getString(clazz, "class", props, null);
        classNameOverride = QdoxHelper.getString(clazz,"clazz",props,classNameOverride);
        
        String bodyContent = QdoxHelper.getString(clazz, "bodyContent", props, "JSP");
        String componentClass = QdoxHelper.getString(clazz, "componentClass", props, null);
        String tagClass = QdoxHelper.getString(clazz, "tagClass", props, null);
        String converterClass = QdoxHelper.getString(clazz, "converterClass", props, null);
        String validatorClass = QdoxHelper.getString(clazz, "validatorClass", props, null);
        String behaviorClass = QdoxHelper.getString(clazz, "behaviorClass", props, null);

        FaceletTagMeta tag = new FaceletTagMeta();
        initClassMeta(model, clazz, tag, classNameOverride);
        tag.setName(tagName);
        tag.setBodyContent(bodyContent);
        tag.setDescription(shortDescription);
        tag.setLongDescription(longDescription);
        tag.setComponentClass(componentClass);
        tag.setTagClass(tagClass);
        tag.setConverterClass(converterClass);
        tag.setValidatorClass(validatorClass);
        tag.setBehaviorClass(behaviorClass);
        
        processFaceletTagAttributes(clazz, tag);
        model.addFaceletTag(tag);
    }
    

    
    private void processFaceletTagAttributes(JavaClass clazz,
            FaceletTagMeta ctag)
    {
        JavaMethod[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; ++i)
        {
            JavaMethod method = methods[i];

            DocletTag tag = method.getTagByName(DOC_FACELET_TAG_ATTRIBUTE);
            if (tag != null)
            {
                Map props = tag.getNamedParameterMap();
                processFaceletTagAttribute(props, (AbstractJavaEntity)tag.getContext(), clazz,
                        method, ctag);
            }

            Annotation anno = QdoxHelper.getAnnotation(method, DOC_FACELET_TAG_ATTRIBUTE);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processFaceletTagAttribute(props, (AbstractJavaEntity)anno.getContext(), clazz,
                        method, ctag);
            }
        }
        
        JavaField[] fields = clazz.getFields();
        for (int i = 0; i < fields.length; ++i)
        {
            JavaField field = fields[i];
            DocletTag tag = field.getTagByName(DOC_FACELET_TAG_ATTRIBUTE);
            if (tag != null)
            {
                Map props = tag.getNamedParameterMap();
                processFaceletTagAttribute(props, (AbstractJavaEntity)tag.getContext(), clazz, field, ctag);
            }

            Annotation anno = QdoxHelper.getAnnotation(field, DOC_FACELET_TAG_ATTRIBUTE);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processFaceletTagAttribute(props, (AbstractJavaEntity)anno.getContext(), clazz, field, ctag);
            }
        }
        
        DocletTag[] jspProperties = clazz.getTagsByName(DOC_FACELET_TAG_ATTRIBUTE);
        for (int i = 0; i < jspProperties.length; ++i)
        {
            //We have here only doclets, because this part is only for
            //solve problems with binding property on 1.1
            DocletTag tag = jspProperties[i];
            
            Map props = tag.getNamedParameterMap();
            processFaceletTagAttribute(props, (AbstractJavaEntity)tag.getContext(), clazz,
                    ctag);
            
        }
        
        Annotation jspPropertyAnno = QdoxHelper.getAnnotation(clazz, DOC_FACELET_TAG_ATTRIBUTE);
        if (jspPropertyAnno != null)
        {
            Map props = jspPropertyAnno.getNamedParameterMap();
            processFaceletTagAttribute(props, (AbstractJavaEntity)jspPropertyAnno.getContext(),
                    clazz, ctag);
        }
        
        Annotation jspAnno = QdoxHelper.getAnnotation(clazz, DOC_FACELET_TAG_ATTRIBUTES);        
        if (jspAnno != null)
        {
            Object jspProps = jspAnno.getNamedParameter("attributes");
            
            if (jspProps instanceof Annotation)
            {
                Annotation jspPropertiesAnno = (Annotation) jspProps;
                Map props = jspPropertiesAnno.getNamedParameterMap();
                processFaceletTagAttribute(props, (AbstractJavaEntity)jspAnno.getContext(), clazz,
                        ctag);
            }
            else
            {
                List jspPropsList = (List) jspProps;
                for (int i = 0; i < jspPropsList.size();i++)
                {
                    Annotation anno = (Annotation) jspPropsList.get(i);

                    Map props = anno.getNamedParameterMap();
                    processFaceletTagAttribute(props, (AbstractJavaEntity)jspAnno.getContext(), clazz,
                            ctag);                    
                }
            }
        }
    }
    

    private void processFaceletTagAttribute(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, FaceletTagMeta tag)
    {
        Boolean required = QdoxHelper.getBoolean(clazz, "required", props, null);
        Boolean rtexprvalue = QdoxHelper.getBoolean(clazz, "rtexprvalue", props, null);

        String longDescription = ctx.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = QdoxHelper.getString(clazz, "desc", props, descDflt);
                
        Type returnType = null;
        
        if (method.getName().startsWith("set"))
        {
            returnType = method.getParameters()[0].getType();
        }
        else
        {
            returnType = method.getReturns();
        }

        String fullyQualifiedReturnType = returnType.getJavaClass().getFullyQualifiedName();
        
        fullyQualifiedReturnType = QdoxHelper.getFullyQualifiedClassName(clazz,fullyQualifiedReturnType);
        
        if (returnType.isArray() && (fullyQualifiedReturnType.indexOf('[') == -1))
        {
            for (int i = 0; i < returnType.getDimensions();i++)
            {
                fullyQualifiedReturnType = fullyQualifiedReturnType + "[]";
            }
        }
                
        String className = QdoxHelper.getString(clazz,"className",props, fullyQualifiedReturnType);
        String deferredValueType = QdoxHelper.getString(clazz, "deferredValueType", props, null);
        String deferredMethodSignature = QdoxHelper.getString(clazz, "deferredMethodSignature", props, null);
        Boolean exclude = QdoxHelper.getBoolean(clazz, "exclude", props, null);
        
        AttributeMeta a = new AttributeMeta();
        a.setName(QdoxHelper.methodToPropName(method.getName()));
        a.setClassName(className);
        a.setRequired(required);
        a.setRtexprvalue(rtexprvalue);
        a.setDescription(shortDescription);
        a.setLongDescription(longDescription);
        a.setDeferredValueType(deferredValueType);
        a.setDeferredMethodSignature(deferredMethodSignature);
        a.setExclude(exclude);
        
        tag.addAttribute(a);
    }

    private void processFaceletTagAttribute(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, FaceletTagMeta tag)
    {
        Boolean required = QdoxHelper.getBoolean(clazz, "required", props, null);
        Boolean rtexprvalue = QdoxHelper.getBoolean(clazz, "rtexprvalue", props, null);

        String longDescription = QdoxHelper.getString(clazz, "longDescription", props, null);
        String descDflt = longDescription;
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = QdoxHelper.getString(clazz, "desc", props, descDflt);
                
        String name = QdoxHelper.getString(clazz, "name", props, null);
        String className = QdoxHelper.getString(clazz, "className", props, null);
        String deferredValueType = QdoxHelper.getString(clazz, "deferredValueType", props, null);
        String deferredMethodSignature = QdoxHelper.getString(clazz, "deferredMethodSignature", props, null);
        Boolean exclude = QdoxHelper.getBoolean(clazz, "exclude", props, null);
                
        AttributeMeta a = new AttributeMeta();
        a.setName(name);
        a.setClassName(className);
        a.setRequired(required);
        a.setRtexprvalue(rtexprvalue);
        a.setDescription(shortDescription);
        a.setLongDescription(longDescription);
        a.setDeferredValueType(deferredValueType);
        a.setDeferredMethodSignature(deferredMethodSignature);
        a.setExclude(exclude);
        
        tag.addAttribute(a);
    }

    private void processFaceletTagAttribute(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaField field, FaceletTagMeta tag)
    {
        Boolean required = QdoxHelper.getBoolean(clazz, "required", props, null);
        Boolean rtexprvalue = QdoxHelper.getBoolean(clazz, "rtexprvalue", props, null);

        String longDescription = ctx.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = QdoxHelper.getString(clazz, "desc", props, descDflt);
                
        String name = QdoxHelper.getString(clazz, "name", props, field.getName());
        String className = QdoxHelper.getString(clazz, "className", props, null);
        String deferredValueType = QdoxHelper.getString(clazz, "deferredValueType", props, null);
        String deferredMethodSignature = QdoxHelper.getString(clazz, "deferredMethodSignature", props, null);
        Boolean exclude = QdoxHelper.getBoolean(clazz, "exclude", props, null);
                
        AttributeMeta a = new AttributeMeta();
        a.setName(name);
        a.setClassName(className);
        a.setRequired(required);
        a.setRtexprvalue(rtexprvalue);
        a.setDescription(shortDescription);
        a.setLongDescription(longDescription);
        a.setDeferredValueType(deferredValueType);
        a.setDeferredMethodSignature(deferredMethodSignature);
        a.setExclude(exclude);
        
        tag.addAttribute(a);
    }
    
}
