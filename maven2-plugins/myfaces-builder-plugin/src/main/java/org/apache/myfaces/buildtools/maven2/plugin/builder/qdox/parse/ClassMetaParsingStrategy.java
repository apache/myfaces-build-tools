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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ClassMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.qdox.QdoxHelper;

import com.thoughtworks.qdox.model.JavaClass;

/**
 * 
 * @author Leonardo Uribe
 * @since 1.0.9
 *
 */
public abstract class ClassMetaParsingStrategy implements JavaClassParsingStrategy
{
    
    /**
     * Set the basic data on a ClassMeta.
     * <p>
     * There is one property not set here: the parentClassName. See method
     * initComponentAncestry for further details.
     */
    public void initClassMeta(Model model, JavaClass clazz,
            ClassMeta modelItem, String classNameOverride)
    {
        modelItem.setModelId(model.getModelId());
        modelItem.setSourceClassName(clazz.getFullyQualifiedName());
        JavaClass realParentClass = clazz.getSuperJavaClass();
        if (realParentClass != null)
        {
            String fqn = realParentClass.getFullyQualifiedName();
            
            if ((fqn != null) && !fqn.startsWith("java.lang"))
            {
                fqn = QdoxHelper.getFullyQualifiedClassName(clazz,fqn);
                modelItem.setSourceClassParentClassName(fqn);
            }
        }

        // JSF Entity class.
        if (StringUtils.isEmpty(classNameOverride))
        {
            modelItem.setClassName(clazz.getFullyQualifiedName());
        }
        else
        {
            modelItem.setClassName(classNameOverride);
        }

        // interfaces metadata is inherited from
        JavaClass[] classes = clazz.getImplementedInterfaces();
        List ifaceNames = new ArrayList();
        for (int i = 0; i < classes.length; ++i)
        {
            JavaClass iclazz = classes[i];

            ComponentMeta ifaceComponent = model
                    .findComponentByClassName(iclazz.getFullyQualifiedName());
            if (ifaceComponent != null)
            {
                ifaceNames.add(ifaceComponent.getClassName());
            }
        }
        modelItem.setInterfaceClassNames(ifaceNames);
    }

}
