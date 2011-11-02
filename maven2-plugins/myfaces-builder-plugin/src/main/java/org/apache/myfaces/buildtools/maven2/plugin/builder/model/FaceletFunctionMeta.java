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
package org.apache.myfaces.buildtools.maven2.plugin.builder.model;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;

/**
 * 
 * @since 1.0.10
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 796607 $ $Date: 2009-07-21 22:00:30 -0500 (mar, 21 jul 2009) $
 */
public class FaceletFunctionMeta
{
    private String _modelId;
    private String _longDescription;
    private String _description;
    private String _name;

    private String _signature;
    private String _declaredSignature;
    private String _sourceClassName;

    public FaceletFunctionMeta()
    {
    }
    
    public FaceletFunctionMeta(FaceletFunctionMeta pm)
    {
        _modelId = pm._modelId;
        _name = pm._name;
        _description = pm._description;
        _longDescription = pm._longDescription;
        _signature = pm._signature;
        _sourceClassName = pm._sourceClassName;
        _declaredSignature = pm._declaredSignature;
    }
    
    protected void writeXml(XmlWriter out)
    {
        writeXml(out, this);
    }
    
    /**
     * Write this model out as xml.
     */
    public static void writeXml(XmlWriter out, FaceletFunctionMeta pm)
    {
        out.beginElement("faceletFunction");
        out.writeElement("modelId", pm._modelId);
        out.writeElement("name", pm._name);
        out.writeElement("desc", pm._description);
        out.writeElement("longDesc", pm._longDescription);
        out.writeElement("sourceClassName", pm._sourceClassName);
        out.writeElement("signature", pm._signature);
        out.writeElement("declaredSignature", pm._declaredSignature);
        out.endElement("faceletFunction");
    }

    /**
     * Add digester rules to repopulate a Model instance from an xml file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/faceletFunction";

        digester.addObjectCreate(newPrefix, FaceletFunctionMeta.class);
        digester.addSetNext(newPrefix, "addFaceletFunction");
        digester.addBeanPropertySetter(newPrefix + "/modelId");
        digester.addBeanPropertySetter(newPrefix + "/name");
        digester.addBeanPropertySetter(newPrefix + "/sourceClassName");
        digester.addBeanPropertySetter(newPrefix + "/desc", "description");
        digester.addBeanPropertySetter(newPrefix + "/longDesc", "longDescription");
        digester.addBeanPropertySetter(newPrefix + "/signature");
        digester.addBeanPropertySetter(newPrefix + "/declaredSignature");
    }

    /**
     * Merge the data in the specified other property into this one, throwing an
     * exception if there is an incompatibility.
     */
    public void merge(FaceletFunctionMeta other)
    {
        // Merge className does not harm, since you cannot
        //use polymorphism on a jsf component.
        _name = ModelUtils.merge(this._name, other._name);
        _modelId = ModelUtils.merge(this._modelId, other._modelId);
        _description = ModelUtils.merge(this._description, other._description);
        _longDescription = ModelUtils.merge(this._longDescription, other._longDescription);
        _sourceClassName = ModelUtils.merge(this._sourceClassName, other._sourceClassName);
        _signature = ModelUtils.merge(this._signature, other._signature);
        _declaredSignature = ModelUtils.merge(this._declaredSignature, other._declaredSignature);
    }

    /**
     * Indicates which "group" of metadata this class belongs to.
     * <p>
     * Projects can inherit metadata from other projects, in which case
     * all the ClassMeta objects end up in one big collection. But for
     * some purposes it is necessary to iterate over the objects belonging
     * to only one project (eg when generating components). This return
     * value can be tested to check which "group" (project) a particular
     * instance belongs to.
     */
    public String getModelId()
    {
        return _modelId;
    }

    public void setModelId(String modelId)
    {
        this._modelId = modelId;
    }

    /**
     * Set the name that users refer to this property by.
     * <p>
     * This sets the name of xml tag attributes, and the base names of generated
     * getter/setter methods.
     */
    public void setName(String name)
    {
        _name = name;
    }

    public String getName()
    {
        return _name;
    }

    public void setDescription(String desc)
    {
        _description = desc;
    }

    public String getDescription()
    {
        return _description;
    }

    public void setLongDescription(String desc)
    {
        _longDescription = desc;
    }

    public String getLongDescription()
    {
        return _longDescription;
    }
    
    /**
     * Utility method to return just the packagename part of the className
     * attribute.
     */
    public String getSourcePackageName()
    {
        return StringUtils.substring(getSourceClassName(), 0, StringUtils.lastIndexOf(getSourceClassName(), '.'));
    }

    /**
     * Return the className of the real java class from which this metadata was gathered.
     * <p>
     * This is mostly used for documentation. However when generating code in "template mode",
     * this is used to locate the original class in order to find the source code to copy.
     * It is also used for some reason in MakeComponentsMojo when determining whether to
     * generate a class or not - this is probably wrong.
     */
    public String getSourceClassName()
    {
        return _sourceClassName;
    }

    public void setSourceClassName(String sourceClassName)
    {
        this._sourceClassName = sourceClassName;
    }
    
    public void setSignature(String signature)
    {
      _signature = signature;
    }

    public String getSignature()
    {
      return _signature;
    }
    
    public void setDeclaredSignature(String signature)
    {
      _declaredSignature = signature;
    }

    public String getDeclaredSignature()
    {
      return _declaredSignature;
    }
}
