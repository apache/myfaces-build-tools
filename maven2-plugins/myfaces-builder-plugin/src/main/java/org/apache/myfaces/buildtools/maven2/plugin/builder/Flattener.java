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
package org.apache.myfaces.buildtools.maven2.plugin.builder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.AttributeMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ConverterMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.TagMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ValidatorMeta;

import org.apache.myfaces.buildtools.maven2.plugin.builder.model.MethodSignatureMeta;

/**
 */
public class Flattener
{
    private final Log log = LogFactory.getLog(Flattener.class);

    private Model model;
    private Set flattened = new HashSet();

    public Flattener(Model model)
    {
        this.model = model;
    }

    /**
     * Flatten the specified model.
     * <p>
     * In the flattened representation, each model object directly contains the
     * data that it inherits from its parents, so that the getter methods return
     * all available metadata, not just the data that was defined directly on
     * that item.
     */
    public void flatten()
    {
        flattenComponentProperties();
        flattenValidatorProperties();
        flattenConverterProperties();
        flattenTagAttributes();
    }

    private void flattenComponentProperties()
    {
        List components = model.getComponents();
        for (Iterator i = components.iterator(); i.hasNext();)
        {
            ComponentMeta comp = (ComponentMeta) i.next();
            flattenComponent(comp);
        }
    }

    private void flattenComponent(ComponentMeta component)
    {
        if (flattened.contains(component))
        {
            // already done
            return;
        }
        String parentClassName = component.getParentClassName();
        if (parentClassName != null)
        {
            ComponentMeta parent = model
                    .findComponentByClassName(parentClassName);
            if (parent != null)
            {
                flattenComponent(parent);
                component.merge(parent);
            }
            else
            {
                //How to manage a component that its
                //parent class is not a real component?
                //Use UIComponent instead and log a warn
                //so if needed we can fix this.
                log.warn("Component:"+component.getClassName()+
                        " without a parent defined as component, using " +
                        "UIComponent");
                parent = model
                    .findComponentByClassName("javax.faces.component.UIComponent");
                flattenComponent(parent);
                component.merge(parent);                
            }
        }

        List interfaceClassNames = component.getInterfaceClassNames();
        for (Iterator i = interfaceClassNames.iterator(); i.hasNext();)
        {
            String ifaceClassName = (String) i.next();
            ComponentMeta iface = model
                    .findComponentByClassName(ifaceClassName);
            flattenComponent(iface);
            component.merge(iface);
        }

        flattened.add(component);
    }
    
    private void flattenValidatorProperties()
    {
        List validator = model.getValidators();
        for (Iterator i = validator.iterator(); i.hasNext();)
        {
            ValidatorMeta val = (ValidatorMeta) i.next();
            flattenValidator(val);
        }
    }
    
    private void flattenValidator(ValidatorMeta validator)
    {
        if (flattened.contains(validator))
        {
            // already done
            return;
        }
        String parentClassName = validator.getParentClassName();
        if (parentClassName != null)
        {
            ValidatorMeta parent = model
                    .findValidatorByClassName(parentClassName);
            if (parent != null)
            {
                flattenValidator(parent);
                validator.merge(parent);
            }
            else
            {
                //How to manage a validator that its
                //parent class is not a real validator?
                //Ans: no problem, do nothing.
            }
        }

        flattened.add(validator);
    }

    private void flattenConverterProperties()
    {
        List converter = model.getConverters();
        for (Iterator i = converter.iterator(); i.hasNext();)
        {
            ConverterMeta val = (ConverterMeta) i.next();
            flattenConverter(val);
        }
    }
    
    private void flattenConverter(ConverterMeta converter)
    {
        if (flattened.contains(converter))
        {
            // already done
            return;
        }
        String parentClassName = converter.getParentClassName();
        if (parentClassName != null)
        {
            ConverterMeta parent = model
                    .findConverterByClassName(parentClassName);
            if (parent != null)
            {
                flattenConverter(parent);
                converter.merge(parent);
            }
            else
            {
                //How to manage a validator that its
                //parent class is not a real validator?
                //Ans: no problem, do nothing.
            }
        }

        flattened.add(converter);
    }
    
    private void flattenTagAttributes()
    {
        List tags = model.getTags();
        for (Iterator i = tags.iterator(); i.hasNext();)
        {
            TagMeta val = (TagMeta) i.next();
            flattenTag(val);
        }
    }
    
    /**
     * This method allows component tag classes to be
     * used with tags, so it is possible to inherit attribute
     * for component tags. But tags cannot inherit
     * attributes from other tags. 
     *  
     */
    private void flattenTag(TagMeta tag)
    {
        if (flattened.contains(tag))
        {
            // already done
            return;
        }
        
        if (tag.getSourceClassParentClassName() == null)
        {
            //No need to scan
            return;
        }
        ComponentMeta component = model.findComponentByTagClassName(
                tag.getSourceClassParentClassName());
        
        if (null != component)
        {
            Collection propertyList = component.getPropertyList();
            for (Iterator it = propertyList.iterator(); it.hasNext();)
            {
                PropertyMeta property = (PropertyMeta) it.next();
                
                //Just add all non tag excluded properties. 
                if (!property.isTagExcluded().booleanValue())
                {
                    AttributeMeta attribute = new AttributeMeta();
                    if (property.isMethodExpression())
                    {
                        attribute.setClassName("javax.el.MethodExpression");
                        MethodSignatureMeta sig = property.getMethodBindingSignature();
                        attribute.setDeferredMethodSignature(
                                sig.getReturnType()+" myMethod("+sig.getParameterTypesAsString()+")");
                    }
                    else if (null == property.isRtexprvalue() || !property.isRtexprvalue().booleanValue())
                    {
                        attribute.setDeferredValueType(property.getClassName());
                        attribute.setClassName("javax.el.ValueExpression");
                    }
                    else
                    {
                        //rtexprvalue = true, set className as expected
                        attribute.setClassName(property.getClassName());
                    }
                    attribute.setRtexprvalue(property.isRtexprvalue());
                    
                    attribute.setDescription(property.getDescription());
                    attribute.setLongDescription(property.getLongDescription());
                    attribute.setName(property.getJspName());
                    attribute.setRequired(property.isRequired());

                    //just add attribute to tag
                    AttributeMeta attributeInTag = tag.getAttribute(attribute.getName());
                    
                    if (attributeInTag != null)
                    {
                        //attributeInTag takes precedence, so 
                        //we have to merge and copy
                        attribute.merge(attributeInTag);
                        attributeInTag.copy(attribute);
                    }
                    else
                    {
                        //Add it to tag
                        tag.addAttribute(attribute);
                    }
                }
            }
        }
        
        flattened.add(tag);
    }
}