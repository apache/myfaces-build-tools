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

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.MethodSignatureMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyHolder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.QdoxHelper;

import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.Type;

public abstract class ClassMetaPropertyParsingStrategy extends ClassMetaParsingStrategy
{
    public static final String DOC_COMPONENT = "JSFComponent";
    public static final String DOC_PROPERTY = "JSFProperty";
    //This property is used in special cases where properties 
    //does not have methods defined on component class, like binding
    //in JSF 1.1 (in 1.2 has component counterpart). In fact, all
    //properties must be defined with JSFProperty
    public static final String DOC_JSP_PROPERTY = "JSFJspProperty";
    public static final String DOC_JSP_PROPERTIES = "JSFJspProperties";

    /**
     * Look for any methods on the specified class that are annotated as being
     * component properties, and add metadata about them to the model.
     */
    public void processComponentProperties(JavaClass clazz,
            PropertyHolder component)
    {
        JavaMethod[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; ++i)
        {
            JavaMethod method = methods[i];

            DocletTag tag = method.getTagByName(DOC_PROPERTY);
            if (tag != null)
            {
                Map props = tag.getNamedParameterMap();
                processComponentProperty(props, (AbstractJavaEntity)tag.getContext(), clazz,
                        method, component);
            }

            Annotation anno = QdoxHelper.getAnnotation(method, DOC_PROPERTY);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processComponentProperty(props, (AbstractJavaEntity)anno.getContext(), clazz,
                        method, component);
            }
        }
        
        Type [] interfaces = clazz.getImplements();
        
        //Scan interfaces for properties to be added to this component
        //This feature allow us to have groups of functions.
        for (int i = 0; i < interfaces.length;++i)
        {
            JavaClass intf = interfaces[i].getJavaClass();

            //If the interfaces has a JSFComponent Doclet,
            //this is managed in other way
            if (intf.getTagByName(DOC_COMPONENT, false) == null)
            {
                JavaMethod[] intfmethods = intf.getMethods();
                for (int j = 0; j < intfmethods.length; ++j)
                {
                    JavaMethod intfmethod = intfmethods[j];

                    DocletTag tag = intfmethod.getTagByName(DOC_PROPERTY);
                    if (tag != null)
                    {
                        Map props = tag.getNamedParameterMap();
                        processInterfaceComponentProperty(props, (AbstractJavaEntity)tag.getContext(), 
                                clazz, intfmethod, component);
                    }

                    Annotation anno = QdoxHelper.getAnnotation(intfmethod, DOC_PROPERTY);
                    if (anno != null)
                    {
                        Map props = anno.getNamedParameterMap();
                        processInterfaceComponentProperty(props, (AbstractJavaEntity)anno.getContext(),
                                clazz, intfmethod, component);
                    }
                }
            }
        }

        //Scan for properties defined only on JSP (special case on myfaces 1.1,
        //this feature should not be used on typical situations)
        DocletTag[] jspProperties = clazz.getTagsByName(DOC_JSP_PROPERTY);
        for (int i = 0; i < jspProperties.length; ++i)
        {
            //We have here only doclets, because this part is only for
            //solve problems with binding property on 1.1
            DocletTag tag = jspProperties[i];
            
            Map props = tag.getNamedParameterMap();
            processComponentJspProperty(props, (AbstractJavaEntity)tag.getContext(), clazz,
                    component);
        }
        
        Annotation jspPropertyAnno = QdoxHelper.getAnnotation(clazz, DOC_JSP_PROPERTY);
        if (jspPropertyAnno != null)
        {
            Map props = jspPropertyAnno.getNamedParameterMap();
            processComponentJspProperty(props, (AbstractJavaEntity)jspPropertyAnno.getContext(),
                    clazz, component);
        }
        
        
        Annotation jspAnno = QdoxHelper.getAnnotation(clazz, DOC_JSP_PROPERTIES);        
        if (jspAnno != null)
        {
            Object jspProps = jspAnno.getNamedParameter("properties");
            
            if (jspProps instanceof Annotation)
            {
                Annotation jspPropertiesAnno = (Annotation) jspProps;
                Map props = jspPropertiesAnno.getNamedParameterMap();
                processComponentJspProperty(props, (AbstractJavaEntity)jspAnno.getContext(), clazz,
                        component);
            }
            else
            {
                List jspPropsList = (List) jspProps;
                for (int i = 0; i < jspPropsList.size();i++)
                {
                    Annotation anno = (Annotation) jspPropsList.get(i);

                    Map props = anno.getNamedParameterMap();
                    processComponentJspProperty(props, (AbstractJavaEntity)jspAnno.getContext(), clazz,
                            component);
                }
            }
            
        }
    }
    
    private void processComponentProperty(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, PropertyHolder component)
    {
        Boolean required = QdoxHelper.getBoolean(clazz, "required", props, null);
        Boolean transientProp = QdoxHelper.getBoolean(clazz, "transient", props, null);
        transientProp = QdoxHelper.getBoolean(clazz, "istransient", props, transientProp);
        Boolean stateHolder = QdoxHelper.getBoolean(clazz, "stateHolder", props, null);
        Boolean partialStateHolder = QdoxHelper.getBoolean(clazz, "partialStateHolder", props, null);
        Boolean literalOnly = QdoxHelper.getBoolean(clazz, "literalOnly", props, null);
        Boolean tagExcluded = QdoxHelper.getBoolean(clazz, "tagExcluded", props, null);
        Boolean localMethod = QdoxHelper.getBoolean(clazz, "localMethod",props,null);
        Boolean setMethod = QdoxHelper.getBoolean(clazz, "setMethod",props,null);
        String localMethodScope = QdoxHelper.getString(clazz, "localMethodScope",props,null);
        String setMethodScope = QdoxHelper.getString(clazz, "setMethodScope",props,null);
        Boolean inheritedTag = QdoxHelper.getBoolean(clazz, "inheritedTag",props,null);

        String longDescription = ctx.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = QdoxHelper.getString(clazz, "desc", props, descDflt);
        String returnSignature = QdoxHelper.getString(clazz, "returnSignature", props, null);
        String methodSignature = QdoxHelper.getString(clazz, "methodSignature", props, null);
        String defaultValue = QdoxHelper.getString(clazz,"defaultValue",props,null);
        String jspName = QdoxHelper.getString(clazz,"jspName",props,null);
        Boolean rtexprvalue = QdoxHelper.getBoolean(clazz, "rtexprvalue",props,null);
        String clientEvent = QdoxHelper.getString(clazz, "clientEvent",props,null);
        String deferredValueType = QdoxHelper.getString(clazz, "deferredValueType", props, null);
        Boolean faceletsOnly = QdoxHelper.getBoolean(clazz, "faceletsOnly", props, null);

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
        
        fullyQualifiedReturnType = QdoxHelper.getFullyQualifiedClassName(clazz, fullyQualifiedReturnType);
        
        if (returnType.isArray() && (fullyQualifiedReturnType.indexOf('[') == -1))
        {
            for (int i = 0; i < returnType.getDimensions();i++)
            {
                fullyQualifiedReturnType = fullyQualifiedReturnType + "[]";
            }
        }
        
        PropertyMeta p = new PropertyMeta();
        p.setName(QdoxHelper.methodToPropName(method.getName()));
        p.setClassName(fullyQualifiedReturnType);
        p.setRequired(required);
        p.setTransient(transientProp);
        p.setStateHolder(stateHolder);
        p.setPartialStateHolder(partialStateHolder);
        p.setLiteralOnly(literalOnly);
        p.setTagExcluded(tagExcluded);
        p.setDescription(shortDescription);
        p.setLongDescription(longDescription);
        p.setDefaultValue(defaultValue);
        p.setLocalMethod(localMethod);
        p.setLocalMethodScope(localMethodScope);
        p.setSetMethod(setMethod);
        p.setSetMethodScope(setMethodScope);
        p.setJspName(jspName);
        p.setRtexprvalue(rtexprvalue);
        p.setDeferredValueType(deferredValueType);
        p.setClientEvent(clientEvent);
        p.setInheritedTag(inheritedTag);
        p.setFaceletsOnly(faceletsOnly);
        
        if (returnSignature != null)
        {
            MethodSignatureMeta signature = new MethodSignatureMeta();
            signature.setReturnType(returnSignature);
            
            if (methodSignature != null)
            {
                String[] params = StringUtils.split(methodSignature,',');
                
                if (params != null)
                {
                    for (int i = 0; i < params.length; i++)
                    {
                        signature.addParameterType(params[i].trim());
                    }
                }
            }
            p.setMethodBindingSignature(signature);
        }
        
        //If the method is abstract this should be generated
        if (method.isAbstract())
        {
            p.setGenerated(Boolean.TRUE);
        }

        component.addProperty(p);
    }
    
    private void processInterfaceComponentProperty(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, PropertyHolder component)
    {
        this.processComponentProperty(props, ctx, clazz, method, component);
        
        PropertyMeta property = component.getProperty(QdoxHelper.methodToPropName(method.getName()));
        
        //Try to get the method from the component clazz to see if this
        //has an implementation
        JavaMethod clazzMethod = clazz.getMethodBySignature(method.getName(), null , false);
        
        if (clazzMethod == null)
        {
            //The method should be generated!
            property.setGenerated(Boolean.TRUE);
        }            
    }

    private void processComponentJspProperty(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, PropertyHolder component)
    {
        Boolean required = QdoxHelper.getBoolean(clazz, "required", props, null);
        Boolean transientProp = QdoxHelper.getBoolean(clazz, "transient", props, null);
        Boolean stateHolder = QdoxHelper.getBoolean(clazz, "stateHolder", props, null);
        Boolean literalOnly = QdoxHelper.getBoolean(clazz, "literalOnly", props, null);
        Boolean tagExcluded = QdoxHelper.getBoolean(clazz, "tagExcluded", props, null);
        Boolean inheritedTag = QdoxHelper.getBoolean(clazz, "inheritedTag", props, null);

        String longDescription = QdoxHelper.getString(clazz, "longDesc", props, null);
        
        String descDflt = longDescription;
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = QdoxHelper.getString(clazz, "desc", props, descDflt);
        String returnType = QdoxHelper.getString(clazz, "returnType", props, null);
        String name = QdoxHelper.getString(clazz, "name", props, null);
        
        PropertyMeta p = new PropertyMeta();
        p.setName(name);
        p.setClassName(returnType);
        p.setRequired(required);
        p.setTransient(transientProp);
        p.setStateHolder(stateHolder);
        p.setLiteralOnly(literalOnly);
        p.setTagExcluded(tagExcluded);
        p.setInheritedTag(inheritedTag);
        p.setDescription(shortDescription);
        p.setLongDescription(longDescription);
        p.setGenerated(Boolean.FALSE);
        component.addProperty(p);
    }

}
