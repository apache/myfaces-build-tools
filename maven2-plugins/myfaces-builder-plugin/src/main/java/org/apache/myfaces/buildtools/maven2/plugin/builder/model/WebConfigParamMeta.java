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
 * @since 1.0.4
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 796607 $ $Date: 2009-07-21 22:00:30 -0500 (mar, 21 jul 2009) $
 */
public class WebConfigParamMeta
{
    private String _name;
    private String _fieldName;
    private String   _defaultValue;
    private String _description;
    private String _longDescription;
    private String _expectedValues;
    private String _sourceClassName;
    private String _since;
    private String _group;
    private String _tags;
    private Boolean _deprecated;
    private String _alias;
    private Boolean _ignoreUpperLowerCase;
    private String _classType;

    public WebConfigParamMeta()
    {
        
    }
    
    public WebConfigParamMeta(WebConfigParamMeta pm)
    {
        _name = pm._name;
        _fieldName = pm._fieldName;    
        _description = pm._description;
        _longDescription = pm._longDescription;
        _defaultValue = pm._defaultValue;
        _expectedValues = pm._expectedValues;
        _since = pm._since;
        _group = pm._group;
        _tags = pm._tags;
        _deprecated = pm._deprecated;
        _alias = pm._alias;
        _ignoreUpperLowerCase = pm._ignoreUpperLowerCase;
        _classType = pm._classType;
    }
    
    /**
     * Write this model out as xml.
     */
    public static void writeXml(XmlWriter out, WebConfigParamMeta pm)
    {
        out.beginElement("webConfigParam");
        out.writeElement("name", pm._name);
        out.writeElement("fieldName", pm._fieldName);
        out.writeElement("desc", pm._description);
        out.writeElement("longDesc", pm._longDescription);
        out.writeElement("defaultValue", pm._defaultValue);
        out.writeElement("expectedValues", pm._expectedValues);
        out.writeElement("sourceClassName", pm._sourceClassName);
        out.writeElement("since", pm._since);
        out.writeElement("group", pm._group);
        out.writeElement("tags", pm._tags);
        out.writeElement("deprecated", pm._deprecated);
        out.writeElement("alias", pm._alias);
        out.writeElement("ignoreUpperLowerCase", pm._ignoreUpperLowerCase);
        out.writeElement("classType", pm._classType);
        out.endElement("webConfigParam");
    }

    /**
     * Add digester rules to repopulate a Model instance from an xml file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/webConfigParam";

        digester.addObjectCreate(newPrefix, WebConfigParamMeta.class);
        digester.addSetNext(newPrefix, "addWebConfigParam");

        digester.addBeanPropertySetter(newPrefix + "/name");
        digester.addBeanPropertySetter(newPrefix + "/fieldName");
        digester.addBeanPropertySetter(newPrefix + "/sourceClassName");
        digester.addBeanPropertySetter(newPrefix + "/since");
        digester.addBeanPropertySetter(newPrefix + "/desc", "description");
        digester.addBeanPropertySetter(newPrefix + "/longDesc", "longDescription");
        digester.addBeanPropertySetter(newPrefix + "/defaultValue", "defaultValue");
        digester.addBeanPropertySetter(newPrefix + "/expectedValues", "expectedValues");
        digester.addBeanPropertySetter(newPrefix + "/group", "group");
        digester.addBeanPropertySetter(newPrefix + "/tags", "tags");
        digester.addBeanPropertySetter(newPrefix + "/deprecated", "deprecated");
        digester.addBeanPropertySetter(newPrefix + "/alias", "alias");
        digester.addBeanPropertySetter(newPrefix + "/ignoreUpperLowerCase", "ignoreUpperLowerCase");
        digester.addBeanPropertySetter(newPrefix + "/classType", "classType");
        MethodSignatureMeta.addXmlRules(digester, newPrefix);
        
    }

    /**
     * Merge the data in the specified other property into this one, throwing an
     * exception if there is an incompatibility.
     */
    public void merge(WebConfigParamMeta other)
    {
        // Merge className does not harm, since you cannot
        //use polymorphism on a JSF component.
        _name = ModelUtils.merge(this._name, other._name);
        _fieldName = ModelUtils.merge(this._fieldName, other._fieldName);
        _description = ModelUtils.merge(this._description, other._description);
        _longDescription = ModelUtils.merge(this._longDescription, other._longDescription);
        _defaultValue = ModelUtils.merge(this._defaultValue, other._defaultValue);
        _sourceClassName = ModelUtils.merge(this._sourceClassName, other._sourceClassName);
        _since = ModelUtils.merge(this._since, other._since);
        _group = ModelUtils.merge(this._group, other._group);
        _tags = ModelUtils.merge(this._tags, other._tags);
        _deprecated = ModelUtils.merge(this._deprecated, other._deprecated);
        _alias = ModelUtils.merge(this._alias, other._alias);
        _ignoreUpperLowerCase = ModelUtils.merge(this._ignoreUpperLowerCase, other._ignoreUpperLowerCase);
        _classType = ModelUtils.merge(this._classType, other._classType);
        setExpectedValues(ModelUtils.merge(this._expectedValues, other._expectedValues));
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
     * Sets the default value of this attribute.
     *
     * @param defaultValue  the attribute default value
     */
    public void setDefaultValue(String defaultValue)
    {
      _defaultValue = defaultValue;
    }

    /**
     * Returns the default value of this attribute.
     *
     * @return  the attribute default value
     */
    public String getDefaultValue()
    {
      return _defaultValue;
    }
    
    public void setFieldName(String fieldName)
    {
      _fieldName = fieldName;
    }

    public String getFieldName()
    {
      return _fieldName;
    }

    public void setExpectedValues(String expectedValues)
    {
        _expectedValues = expectedValues;
    }

    public String getExpectedValues()
    {
        return _expectedValues;
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
    
    public String getSince()
    {
        return _since;
    }

    public void setSince(String since)
    {
        _since = since;
    }

    public String getGroup()
    {
        return _group;
    }

    public void setGroup(String group)
    {
        this._group = group;
    }

    public String getTags()
    {
        return _tags;
    }

    public void setTags(String tags)
    {
        this._tags = tags;
    }

    public Boolean isDeprecated()
    {
        return ModelUtils.defaultOf(_deprecated, false);
    }

    public void setDeprecated(Boolean deprecated)
    {
        this._deprecated = deprecated;
    }

    public String getAlias()
    {
        return _alias;
    }

    public void setAlias(String alias)
    {
        this._alias = alias;
    }

    public Boolean isIgnoreUpperLowerCase()
    {
        return ModelUtils.defaultOf(_ignoreUpperLowerCase, false);
    }

    public void setIgnoreUpperLowerCase(Boolean ignoreUpperLowerCase)
    {
        this._ignoreUpperLowerCase = ignoreUpperLowerCase;
    }
    
    public String getClassType()
    {
        return _classType;
    }

    public void setClassType(String classType)
    {
        this._classType = classType;
    }

    public boolean containsTag(String tag)
    {
        if (this.getGroup() != null && this.getGroup().equals(tag))
        {
            return true;
        }
        if (this.getTags() != null)
        {
            String [] splitTags =  StringUtils.split(getTags(), ',');
            if (splitTags != null && splitTags.length > 0)
            {
                for (int i = 0; i < splitTags.length; i++)
                {
                    String s = splitTags[i];
                    if (s != null && s.length() > 0)
                    {
                        if (s.trim().equals(tag))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
