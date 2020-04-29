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
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FacetHolder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FacetMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ListenerHolder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ListenerMeta;
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
public class ComponentParsingStrategy extends ClassMetaPropertyParsingStrategy
{
    private static final String DOC_FACET = "JSFFacet";
    private static final String DOC_LISTENER = "JSFListener";

    public void parseClass(JavaClass clazz, Model model)
    {
        DocletTag tag;
        Annotation anno;
        // components
        tag = clazz.getTagByName(DOC_COMPONENT, false);
        if (tag != null)
        {
            Map props = tag.getNamedParameterMap();
            processComponent(props, (AbstractJavaEntity)tag.getContext(), clazz, model);
        }
        anno = QdoxHelper.getAnnotation(clazz, DOC_COMPONENT);
        if (anno != null)
        {
            Map props = anno.getNamedParameterMap();
            processComponent(props, (AbstractJavaEntity)anno.getContext(), clazz, model);
        }
    }

    private void processComponent(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, Model model)
    {
        String componentTypeDflt = null;
        JavaField fieldComponentType = clazz.getFieldByName("COMPONENT_TYPE");
        if (fieldComponentType != null)
        {
            componentTypeDflt = QdoxHelper.clean(fieldComponentType
                    .getInitializationExpression());
        }

        String componentTypeFamily = null;
        JavaField fieldComponentFamily = clazz
                .getFieldByName("COMPONENT_FAMILY");
        if (fieldComponentFamily != null)
        {
            componentTypeFamily = QdoxHelper.clean(fieldComponentFamily
                    .getInitializationExpression());
        }

        String rendererTypeDflt = null;
        JavaField fieldRendererType = clazz
                .getFieldByName("DEFAULT_RENDERER_TYPE");
        if (fieldRendererType != null)
        {
            rendererTypeDflt = QdoxHelper.clean(fieldRendererType
                    .getInitializationExpression());
        }

        String componentName = QdoxHelper.getString(clazz, "name", props, null);

        // Check for both "class" and "clazz" in order to support
        // doclet and real annotations.
        String classNameOverride = QdoxHelper.getString(clazz, "class", props, null);
        classNameOverride = QdoxHelper.getString(clazz,"clazz",props,classNameOverride);
                
        Boolean template = QdoxHelper.getBoolean(clazz,"template",props, null);
                
        String longDescription = clazz.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = QdoxHelper.getString(clazz, "desc", props, descDflt);

        String bodyContent = QdoxHelper.getString(clazz, "bodyContent", props, null);
        
        String componentFamily = QdoxHelper.getString(clazz, "family", props,
                componentTypeFamily);
        String componentType = QdoxHelper.getString(clazz, "type", props,
                componentTypeDflt);
        String rendererType = QdoxHelper.getString(clazz, "defaultRendererType", props,
                rendererTypeDflt);
        Boolean canHaveChildren = QdoxHelper.getBoolean(clazz, "canHaveChildren", props, null);
        Boolean configExcluded = QdoxHelper.getBoolean(clazz,"configExcluded",props,null);        

        String tagClass = QdoxHelper.getString(clazz, "tagClass", props, null);
        String tagSuperclass = QdoxHelper.getString(clazz, "tagSuperclass", props, null);
        String tagHandler = QdoxHelper.getString(clazz, "tagHandler", props, null);
        String defaultEventName = QdoxHelper.getString(clazz, "defaultEventName", props, null);
        String serialuid = QdoxHelper.getString(clazz, "serialuid", props, null);
        String implementsValue = QdoxHelper.getString(clazz, "implements", props, null);
        implementsValue = QdoxHelper.getString(clazz, "implementz", props, implementsValue);
        
        Boolean composite = QdoxHelper.getBoolean(clazz, "composite", props, null);

        ComponentMeta component = new ComponentMeta();
        initClassMeta(model, clazz, component, classNameOverride);
        component.setName(componentName);
        component.setBodyContent(bodyContent);
        component.setDescription(shortDescription);
        component.setLongDescription(longDescription);
        component.setConfigExcluded(configExcluded);
        component.setType(componentType);
        component.setFamily(componentFamily);
        component.setRendererType(rendererType);
        component.setChildren(canHaveChildren);
        component.setSerialuid(serialuid);
        component.setImplements(implementsValue);
        component.setTemplate(template);
        component.setDefaultEventName(defaultEventName);
        if (defaultEventName != null)
        {
            component.setOverrideDefaultEventName(Boolean.TRUE);
        }
        JavaClass[] interfaces = clazz.getImplementedInterfaces();
        for (int i = 0; i < interfaces.length; ++i)
        {
            JavaClass iface = interfaces[i];
            if (iface.getFullyQualifiedName().equals(
                    "jakarta.faces.component.NamingContainer"))
            {
                component.setNamingContainer(Boolean.TRUE);
                break;
            }
            if (iface.getFullyQualifiedName().equals(
                    "jakarta.faces.component.behavior.ClientBehaviorHolder"))
            {
                component.setClientBehaviorHolder(Boolean.TRUE);
                break;
            }
            if (!(template != null && template.booleanValue()))
            {
                component.addImplementedInterfaceClassName(
                        QdoxHelper.getFullyQualifiedClassName(iface, iface.getFullyQualifiedName()));
            }
        }
        if (implementsValue != null)
        {
            if (StringUtils.contains(implementsValue, "jakarta.faces.component.behavior.ClientBehaviorHolder"))
            {
                component.setClientBehaviorHolder(Boolean.TRUE);
            }
            StringTokenizer st = new StringTokenizer(implementsValue,",");
            while (st.hasMoreTokens())
            {
                component.addImplementedInterfaceClassName(st.nextToken());
            }
        }
        component.setTagClass(tagClass);
        component.setTagSuperclass(tagSuperclass);
        component.setTagHandler(tagHandler);
        component.setComposite(composite);

        // Now here walk the component looking for property annotations.
        processComponentProperties(clazz, component);
        processComponentFacets(clazz, component);
        processComponentListeners(clazz, component);

        model.addComponent(component);
    }
    
    private void processComponentFacets(JavaClass clazz,
            FacetHolder component)
    {
        JavaMethod[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; ++i)
        {
            JavaMethod method = methods[i];

            DocletTag tag = method.getTagByName(DOC_FACET);
            if (tag != null)
            {
                Map props = tag.getNamedParameterMap();
                processComponentFacet(props, (AbstractJavaEntity)tag.getContext(), clazz,
                        method, component);
            }

            Annotation anno = QdoxHelper.getAnnotation(method, DOC_FACET);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processComponentFacet(props, (AbstractJavaEntity)anno.getContext(), clazz,
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

                    DocletTag tag = intfmethod.getTagByName(DOC_FACET);
                    if (tag != null)
                    {
                        Map props = tag.getNamedParameterMap();
                        processInterfaceComponentFacet(props, (AbstractJavaEntity)tag.getContext(), 
                                clazz, intfmethod, component);
                    }

                    Annotation anno = QdoxHelper.getAnnotation(intfmethod, DOC_FACET);
                    if (anno != null)
                    {
                        Map props = anno.getNamedParameterMap();
                        processInterfaceComponentFacet(props, (AbstractJavaEntity)anno.getContext(),
                                clazz, intfmethod, component);
                    }
                }
            }
        }
    }
    
    private void processInterfaceComponentFacet(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, FacetHolder component)
    {
        this.processComponentFacet(props, ctx, clazz, method, component);
                
        FacetMeta facet = component.getFacet(QdoxHelper.methodToPropName(method.getName()));
                
        //Try to get the method from the component clazz to see if this
        //has an implementation
        JavaMethod clazzMethod = clazz.getMethodBySignature(method.getName(), null , false);
                
        if (clazzMethod == null)
        {
            //The method should be generated!
            facet.setGenerated(Boolean.TRUE);
        }            
    }
    
    private void processInterfaceComponentListener(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, ListenerHolder component)
    {
        this.processComponentListener(props, ctx, clazz, method, component);
                
        ListenerMeta listener = component.getListener(QdoxHelper.methodToPropName(method.getName()));
                
        //Try to get the method from the component clazz to see if this
        //has an implementation
        JavaMethod clazzMethod = clazz.getMethodBySignature(method.getName(), null , false);
                
        if (clazzMethod == null)
        {
            //The method should be generated!
            listener.setGenerated(Boolean.TRUE);
        }            
    }

    private void processComponentListeners(JavaClass clazz,
            ListenerHolder component)
    {
        JavaMethod[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; ++i)
        {
            JavaMethod method = methods[i];

            DocletTag tag = method.getTagByName(DOC_LISTENER);
            if (tag != null)
            {
                Map props = tag.getNamedParameterMap();
                processComponentListener(props, (AbstractJavaEntity)tag.getContext(), clazz,
                        method, component);
            }

            Annotation anno = QdoxHelper.getAnnotation(method, DOC_LISTENER);
            if (anno != null)
            {
                Map props = anno.getNamedParameterMap();
                processComponentListener(props, (AbstractJavaEntity)anno.getContext(), clazz,
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

                    DocletTag tag = intfmethod.getTagByName(DOC_LISTENER);
                    if (tag != null)
                    {
                        Map props = tag.getNamedParameterMap();
                        processInterfaceComponentListener(props, (AbstractJavaEntity)tag.getContext(), 
                                clazz, intfmethod, component);
                    }

                    Annotation anno = QdoxHelper.getAnnotation(intfmethod, DOC_LISTENER);
                    if (anno != null)
                    {
                        Map props = anno.getNamedParameterMap();
                        processInterfaceComponentListener(props, (AbstractJavaEntity)anno.getContext(),
                                clazz, intfmethod, component);
                    }
                }
            }
        }
    }
    
    private void processComponentFacet(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, FacetHolder component)
    {
        Boolean required = QdoxHelper.getBoolean(clazz, "required", props, null);

        String longDescription = ctx.getComment();
        String descDflt = QdoxHelper.getFirstSentence(longDescription);
        if ((descDflt == null) || (descDflt.length() < 2))
        {
            descDflt = "no description";
        }
        String shortDescription = QdoxHelper.getString(clazz, "desc", props, descDflt);
        
        FacetMeta p = new FacetMeta();
        p.setName(QdoxHelper.methodToPropName(method.getName()));
        p.setRequired(required);
        p.setDescription(shortDescription);
        p.setLongDescription(longDescription);
        
        //If the method is abstract this should be generated
        if (method.isAbstract())
        {
            p.setGenerated(Boolean.TRUE);
        }

        component.addFacet(p);
    }
    
    private void processComponentListener(Map props, AbstractJavaEntity ctx,
            JavaClass clazz, JavaMethod method, ListenerHolder component)
    {
        Boolean required = QdoxHelper.getBoolean(clazz, "required", props, null);

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
        fullyQualifiedReturnType = QdoxHelper.getFullyQualifiedClassName(clazz, fullyQualifiedReturnType);
        fullyQualifiedReturnType = QdoxHelper.getString(clazz, "clazz", props, fullyQualifiedReturnType);
        
        String phases = QdoxHelper.getString(clazz, "phases", props, null);
        String eventClassName = QdoxHelper.getString(clazz, "event", props, null);
        String name = QdoxHelper.getString(clazz, "name", props, QdoxHelper.methodToPropName(method.getName()));
        
        ListenerMeta p = new ListenerMeta();
        p.setName(name);
        p.setClassName(fullyQualifiedReturnType);
        p.setEventClassName(eventClassName);
        p.setRequired(required);
        p.setDescription(shortDescription);
        p.setLongDescription(longDescription);
        p.setPhases(phases);
        
        //If the method is abstract this should be generated
        if (method.isAbstract())
        {
            p.setGenerated(Boolean.TRUE);
        }

        component.addListener(p);
    }
}
