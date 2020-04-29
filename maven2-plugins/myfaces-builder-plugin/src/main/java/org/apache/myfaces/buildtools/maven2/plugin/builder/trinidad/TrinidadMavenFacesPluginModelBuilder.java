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
package org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.myfaces.buildtools.maven2.plugin.builder.ModelParams;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ConverterMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FacetMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ListenerMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.MethodSignatureMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyHolder;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.RenderKitMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.RendererMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ValidatorMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse.AbstractTagBean;
import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse.ComponentBean;
import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse.ConverterBean;
import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse.EventBean;
import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse.EventRefBean;
import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse.FacesConfigBean;
import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse.FacetBean;
import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse.MethodSignatureBean;
import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse.PropertyBean;
import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse.RenderKitBean;
import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse.RendererBean;
import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse.ValidatorBean;

public class TrinidadMavenFacesPluginModelBuilder
{
    
    public void buildModel(Model model, ModelParams parameters)
            throws MojoExecutionException
    {
        FacesConfigBean facesConfigBean = parameters.getFacesConfigBean();
        
        if (facesConfigBean == null)
        {
            return;
        }
        
        for (Iterator<ComponentBean> it = facesConfigBean.components(); it.hasNext();)
        {
            ComponentBean sourceComponent = it.next();
            
            String componentType = sourceComponent.getComponentType();
            if (componentType.startsWith("jakarta.faces.") || sourceComponent.getComponentClass().startsWith("jakarta.faces."))
            {
                // Components in jakarta.faces package were already loaded, so
                // there is no need to load them again
                continue;
            }
            
            boolean created = false;
            ComponentMeta targetComponent = model.findComponentByClassName(sourceComponent.getComponentClass());
            if (targetComponent == null)
            {
                targetComponent = new ComponentMeta();
                created = true;
            }

            targetComponent.setModelId(model.getModelId());
            // className
            targetComponent.setClassName(sourceComponent.getComponentClass());
            // sourceClassName
            targetComponent.setSourceClassName(sourceComponent.getComponentClass());
            // parentClassName
            // sourceClassParentClassName
            // Trinidad Maven Faces plugin uses hieararchy by type, but MyFaces
            // Builder Plugin do it per class name.  
            String componentSuperclass = sourceComponent.findComponentSuperclass(); 
            if (componentSuperclass != null)
            {
                if (componentSuperclass.equals("UIXCollection"))
                {
                    componentSuperclass = "org.apache.myfaces.trinidad.component.UIXCollection";
                }
                targetComponent.setParentClassName(componentSuperclass);
                targetComponent.setSourceClassParentClassName(componentSuperclass);
            }

            // type
            targetComponent.setType(sourceComponent.getComponentType());
            // name
            if (sourceComponent.getTagName() != null)
            {
                targetComponent.setName(getTagPrefix(sourceComponent.getTagName())+':'+sourceComponent.getTagName().getLocalPart());
            }
            // description
            targetComponent.setDescription(sourceComponent.getDescription());
            // longDescription
            targetComponent.setLongDescription(sourceComponent.getLongDescription());
            // family
            targetComponent.setFamily(sourceComponent.getComponentFamily());
            // rendererType
            targetComponent.setRendererType(sourceComponent.getRendererType());
            // tagClass
            targetComponent.setTagClass(sourceComponent.getTagClass());
            // tagHandler
            targetComponent.setTagHandler(sourceComponent.getTagHandler());
            // tagSuperclass;
            targetComponent.setTagSuperclass(sourceComponent.getTagSuperclass());
            // namingContainer
            if (sourceComponent.isNamingContainer())
            {
                targetComponent.setNamingContainer(sourceComponent.isNamingContainer());
            }
            // clientBehaviorHolder
            if (sourceComponent.isClientBehaviorHolder())
            {
                targetComponent.setClientBehaviorHolder(sourceComponent.isClientBehaviorHolder());
            }
            // children
            targetComponent.setChildren(sourceComponent.hasChildren());
            // defaultEventName
            targetComponent.setDefaultEventName(sourceComponent.getDefaultEventName());
            
            // Attributes without match
            // bodyContent
            // configExcluded
            // serialuid
            // implements
            // composite
            // overrideDefaultEventName
            // ccLibraryName;
            // ccResourceName;
            // generatedComponentClass
            // generatedTagClass
            // template;
            
            visitProperties(sourceComponent, targetComponent);
            
            for (Iterator<FacetBean> facetIterator = sourceComponent.facets(); facetIterator.hasNext();)
            {
                FacetBean facet = facetIterator.next();
                FacetMeta facetMeta = targetComponent.getFacet(facet.getFacetName());
                boolean facetCreated = false;
                if (facetMeta == null)
                {
                    facetMeta = new FacetMeta();
                    facetCreated = true;
                }
                
                facetMeta.setName(facet.getFacetName());
                facetMeta.setDescription(facet.getDescription());
                facetMeta.setLongDescription(facet.getDescription());
                if (facet.isRequired())
                {
                    facetMeta.setRequired(facet.isRequired());
                }
                
                if (facetCreated)
                {
                    targetComponent.addFacet(facetMeta);
                }
            }
            
            for (Iterator<EventRefBean> eventIterator = sourceComponent.events(); eventIterator.hasNext();)
            {
                EventRefBean event = eventIterator.next();
                
                String listenerName = event.getEventName()+"Listener";
                
                ListenerMeta listenerMeta = targetComponent.getListener(listenerName);
                boolean listenerCreated = false;
                if (listenerMeta == null)
                {
                    listenerMeta = new ListenerMeta();
                    listenerCreated = true;
                }

                listenerMeta.setEventClassName(event.getEventType());
                listenerMeta.setName(listenerName);
                
                if (event.getEventDeliveryPhases() != null)
                {
                    StringBuilder phases = new StringBuilder();
                    for (int i = 0; i < event.getEventDeliveryPhases().length; i++)
                    {
                        phases.append(event.getEventDeliveryPhases()[i]);
                        if (i+1 < event.getEventDeliveryPhases().length)
                        {
                            phases.append(" ,");
                        }
                    }
                    listenerMeta.setPhases(phases.toString());
                }
                
                EventBean eventBean = event.resolveEventType();
                
                if (eventBean != null)
                {
                    listenerMeta.setClassName(eventBean.getEventListenerClass());
                    listenerMeta.setDescription(eventBean.getDescription());
                    listenerMeta.setLongDescription(eventBean.getDescription());
                }
                
                if (listenerCreated)
                {
                    targetComponent.addListener(listenerMeta);
                }
            }
            
            if (created)
            {
                model.addComponent(targetComponent);
            }
        }
        
        for (Iterator<ConverterBean> it = facesConfigBean.converters(); it.hasNext();)
        {
            ConverterBean sourceComponent = it.next();
            
            String converterId = sourceComponent.getConverterId();
            if (converterId.startsWith("jakarta.faces.") || sourceComponent.getConverterClass().startsWith("jakarta.faces."))
            {
                // Components in jakarta.faces package were already loaded, so
                // there is no need to load them again
                continue;
            }
            
            boolean created = false;
            ConverterMeta targetComponent = model.findConverterByClassName(sourceComponent.getConverterClass());
            if (targetComponent == null)
            {
                targetComponent = new ConverterMeta();
                created = true;
            }
            
            targetComponent.setModelId(model.getModelId());
            
            targetComponent.setClassName(sourceComponent.getConverterClass());
            targetComponent.setSourceClassName(sourceComponent.getConverterClass());
            targetComponent.setParentClassName(sourceComponent.getConverterSuperClass());
            targetComponent.setSourceClassParentClassName(sourceComponent.getConverterSuperClass());

            // name
            if (sourceComponent.getTagName() != null)
            {
                targetComponent.setName(getTagPrefix(sourceComponent.getTagName())+':'+sourceComponent.getTagName().getLocalPart());
            }
            
            targetComponent.setDescription(sourceComponent.getDescription());
            targetComponent.setLongDescription(sourceComponent.getLongDescription());
            
            targetComponent.setConverterId(sourceComponent.getConverterId());
            targetComponent.setTagClass(sourceComponent.getTagClass());
            
            visitProperties(sourceComponent, targetComponent);
            
            if (created)
            {
                model.addConverter(targetComponent);
            }
        }
        
        for (Iterator<ValidatorBean> it = facesConfigBean.validators(); it.hasNext();)
        {
            ValidatorBean sourceComponent = it.next();

            String validatorId = sourceComponent.getValidatorId();
            if (validatorId.startsWith("jakarta.faces.") || sourceComponent.getValidatorClass().startsWith("jakarta.faces."))
            {
                // Components in jakarta.faces package were already loaded, so
                // there is no need to load them again
                continue;
            }
            
            boolean created = false;
            ValidatorMeta targetComponent = model.findValidatorByClassName(sourceComponent.getValidatorClass());
            if (targetComponent == null)
            {
                targetComponent = new ValidatorMeta();
                created = true;
            }

            targetComponent.setModelId(model.getModelId());
            
            targetComponent.setClassName(sourceComponent.getValidatorClass());
            targetComponent.setSourceClassName(sourceComponent.getValidatorClass());
            targetComponent.setParentClassName(sourceComponent.getValidatorSuperClass());
            targetComponent.setSourceClassParentClassName(sourceComponent.getValidatorSuperClass());

            // name
            if (sourceComponent.getTagName() != null)
            {
                targetComponent.setName(getTagPrefix(sourceComponent.getTagName())+':'+sourceComponent.getTagName().getLocalPart());
            }

            
            targetComponent.setDescription(sourceComponent.getDescription());
            targetComponent.setLongDescription(sourceComponent.getLongDescription());
            
            targetComponent.setValidatorId(sourceComponent.getValidatorId());
            targetComponent.setTagClass(sourceComponent.getTagClass());
            
            visitProperties(sourceComponent, targetComponent);
            
            if (created)
            {
                model.addValidator(targetComponent);
            }
        }
        
        for (Iterator<RenderKitBean> it = facesConfigBean.renderKits(); it.hasNext();)
        {
            RenderKitBean sourceComponent = it.next();
            
            boolean created = false;
            RenderKitMeta targetComponent = model.findRenderKitById(sourceComponent.getRenderKitId());
            if (targetComponent == null)
            {
                targetComponent = new RenderKitMeta();
                created = true;
            }
            
            targetComponent.setRenderKitId(sourceComponent.getRenderKitId());
            
            for (Iterator<RendererBean> rendererIterator = sourceComponent.renderers(); rendererIterator.hasNext(); )
            {
                RendererBean renderer = rendererIterator.next();
                
                boolean rendererCreated = false;
                RendererMeta rendererMeta = targetComponent.findRenderer(renderer.getComponentFamily(), renderer.getRendererType());
                if (rendererMeta == null)
                {
                    rendererMeta = new RendererMeta();
                    rendererCreated = true;
                }
                
                rendererMeta.setModelId(model.getModelId());
                rendererMeta.setClassName(renderer.getRendererClass());
                rendererMeta.setParentClassName(renderer.getRendererSuperclass());
                rendererMeta.setComponentFamily(renderer.getComponentFamily());
                rendererMeta.setRendererType(renderer.getRendererType());
                rendererMeta.setDescription(renderer.getDescription());

                if (rendererCreated)
                {
                    targetComponent.addRenderer(rendererMeta);
                }
            }
            
            if (created)
            {
                model.addRenderKit(targetComponent);
            }
        }
    }
    
    private String getTagPrefix(QName tagName)
    {
        String prefix = tagName.getPrefix();
        
        if (prefix != null && prefix.length() > 0)
        {
            return prefix;
        }
        else
        {
            if ("http://myfaces.apache.org/trinidad".equals(tagName.getNamespaceURI()))
            {
                return "tr";
            }
            else if ("http://myfaces.apache.org/trinidad/html".equals(tagName.getNamespaceURI()))
            {
                return "trh";
            }
            else
            {
                return tagName.getNamespaceURI();
            }
        }
    }
    
    private void visitProperties(AbstractTagBean sourceComponent, PropertyHolder targetComponent)
    {
        for (Iterator<PropertyBean> propertyIterator = sourceComponent.properties(); propertyIterator.hasNext();)
        {
            PropertyBean property = propertyIterator.next();
            
            PropertyMeta propertyMeta = targetComponent.getProperty(property.getPropertyName());
            boolean propertyCreated = false;
            if (propertyMeta == null)
            {
                propertyMeta = new PropertyMeta();
                propertyCreated = true;
            }
            // name
            propertyMeta.setName(property.getPropertyName());
            // className
            propertyMeta.setClassName(property.getPropertyClass());
            // jspName
            propertyMeta.setJspName(property.getJspPropertyName());
            // fieldName
            propertyMeta.setFieldName(property.getFieldPropertyName());
            // required
            if (property.isRequired())
            {
                propertyMeta.setRequired(property.isRequired());
            }
            // literalOnly
            if (property.isLiteralOnly())
            {
                propertyMeta.setLiteralOnly(property.isLiteralOnly());
            }
            // transient
            if (property.isTransient())
            {
                propertyMeta.setTransient(property.isTransient());
            }
            // stateHolder
            if(property.isStateHolder())
            {
                propertyMeta.setStateHolder(property.isStateHolder());
            }
            // description
            propertyMeta.setDescription(property.getDescription());
            // longDescription
            propertyMeta.setLongDescription(property.getDescription());
            // defaultValue;
            propertyMeta.setDefaultValue(property.getDefaultValue());
            // rtexprvalue;
            if (property.isRtexprvalue())
            {
                propertyMeta.setRtexprvalue(property.isRtexprvalue());
            }
            
            MethodSignatureBean msb = property.getMethodBindingSignature();
            if (msb != null)
            {
                MethodSignatureMeta msm = new MethodSignatureMeta();
                msm.setReturnType(msb.getReturnType());
                for (String paramType : msb.getParameterTypes())
                {
                    msm.addParameterType(paramType);
                }
                propertyMeta.setMethodBindingSignature(msm);
            }
            
            if (property.isTagAttributeExcluded())
            {
                propertyMeta.setTagExcluded(Boolean.TRUE);
            }
            /*
            partialStateHolder
            inherited
            inheritedTag
            tagExcluded
            generated
            localMethodScope
            localMethod
            setMethodScope
            setMethod
            clientEvent
            deferredValueType
            faceletsOnly
            */
            if (propertyCreated)
            {
                targetComponent.addProperty(propertyMeta);
            }
        }
    }
}
