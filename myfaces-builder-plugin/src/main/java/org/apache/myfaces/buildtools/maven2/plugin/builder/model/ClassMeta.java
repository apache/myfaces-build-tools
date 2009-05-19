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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;

/**
 * Base class for various metadata (model) classes.
 * <p>
 * This holds metadata common to all model classes that represent a JSF entity,
 * eg a component, converter, validator, renderer. In all these cases, the class has a
 * type, a parent and optionally a list of implemented interfaces.
 * <p>
 * Instances of this type are intended primarily to map to entries in generated
 * JSP TLD files, faces-config.xml files and similar. The "class" attribute on
 * this class contains the name of the java class that implements that entity.
 * <p>
 * However there are also instances of this class that represent "abstract"
 * entities that are not directly referenced by TLD or faces-config files; they
 * exist to represent ancestor classes of the concrete entities. 
 * <p>
 * The instances are created via annotations on Java classes. Therefore each
 * ClassMeta has a "sourceClass" property that records the name of the annotated
 * class. In simple cases, "class" and "sourceClass" are the same, ie the JSF entity
 * <i>is</i> the class that holds the relevant annotations. But this is not true in
 * the case of "code generation"; here the class property references the actual JSF
 * entity (the generated class).
 * <p>
 * As described in the documentation for the parent attribute, the ClassMeta objects
 * describe a java inheritance tree, but it is not quite the same as the real java
 * inheritance hierarchy for the annotated java classes. The ClassMeta data omits
 * any classes in the hierarchy that are not annotated, as they are not relevant
 * for the purposes of metadata inheritance.
 */
public class ClassMeta
{
    private String _xmlElementName;

    private String _className;
    private String _parentClassName;
    
    private List _interfaceClassNames = new ArrayList();
    private String _modelId;
    private String _sourceClassName;
    private String _sourceClassParentClassName;

    /**
     * Add digester rules to repopulate an instance of this type from an xml
     * file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        digester.addBeanPropertySetter(prefix + "/modelId");
        digester.addBeanPropertySetter(prefix + "/className");
        digester.addBeanPropertySetter(prefix + "/parentClassName");
        digester.addBeanPropertySetter(prefix + "/sourceClassName");
        digester.addBeanPropertySetter(prefix + "/sourceClassParentClassName");
        digester.addCallMethod(prefix + "/interfaces/interface",
                "addInterfaceClassName", 1);
        digester.addCallParam(prefix + "/interfaces/interface", 0, "name");
    }

    /**
     * Constructor.
     * 
     * Param xmlElementName is the name of the xml element that is created
     * when method writeXml is invoked.
     */
    protected ClassMeta(String xmlElementName)
    {
        _xmlElementName = xmlElementName;
    }

    /**
     * Write the properties of this instance out as xml.
     * <p>
     * The name of the xml element that is created to hold the properties
     * was specified when the constructor was called.
     * <p>
     * Subclasses that want to output their own properties should not
     * override this method. Instead, they should override writeXmlSimple
     * (and in rare cases writeXmlComplex).
     * <p>
     * Having two write methods (writeXmlSimple/writeXmlComplex) gives some basic
     * control over the order in which data is written to xml, in order to make
     * the generated xml look nice. Any properties written in writeXmlSimple will
     * appear in the output file before properties written by writeXmlComplex. 
     * Therefore, properties which are "easily read" should be written out in
     * a writeXmlSimple method. Data which has large CDATA blocks, or complicated
     * nested structure should be written out in a writeXmlComplex method so that
     * the "simple" stuff can be easily read and is not buried in the middle of
     * the harder-to-read output.
     */
    protected void writeXml(XmlWriter out)
    {
        out.beginElement(_xmlElementName);
        writeXmlSimple(out);
        writeXmlComplex(out);
        out.endElement(_xmlElementName);
    }

    /**
     * Write this model out as xml.
     * <p>
     * Subclasses that wish to write out properties as xml should override
     * this method, call the super implementation, then call methods on the
     * XmlWriter object to output their data.
     */
    protected void writeXmlSimple(XmlWriter out)
    {
        out.writeElement("modelId", _modelId);
        out.writeElement("className", _className);
        out.writeElement("parentClassName", _parentClassName);
        out.writeElement("sourceClassName", _sourceClassName);
        out.writeElement("sourceClassParentClassName", _sourceClassParentClassName);

        // Interface data is a little complex, and could possibly be placed in the
        // writeXmlComplex method. But except for very complicated inheritance
        // hierarchies is is still only going to be a few lines and it logically
        // belongs with the className/parentClassName/etc data. So it is written here
        // along with the other "simple" data.
        if (!_interfaceClassNames.isEmpty())
        {
            out.beginElement("interfaces");
            for (Iterator i = _interfaceClassNames.iterator(); i.hasNext();)
            {
                String name = (String) i.next();
                out.beginElement("interface");
                out.writeAttr("name", name);
                out.endElement("interface");
            }
            out.endElement("interfaces");
        }
    }

    /**
     * See documentation for writeXml and writeXmlSimple methods.
     */
    protected void writeXmlComplex(XmlWriter out)
    {
        // no complex data to write for this class.
    }

    /**
     * Merge any inheritable data from the specified "other" instance into
     * the metadata held by this instance.
     */
    protected void merge(ClassMeta other)
    {
        // There is nothing to merge between two ClassMeta objects;
        // none of the properties on this class are inheritable.
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
     * The fully-qualified name of the JSF entity class, ie the class that actually
     * implements a Component, Converter, Validator, Renderer, etc.
     * <p>
     * The specified class may be a hand-written one, or one created via code generation. 
     */
    public String getClassName()
    {
        return _className;
    }

    public void setClassName(String className)
    {
        _className = className;
    }

    /**
     * Utility method to return just the packagename part of the className
     * attribute.
     */
    public String getPackageName()
    {
        return StringUtils.substring(getClassName(), 0, StringUtils.lastIndexOf(getClassName(), '.'));
    }

    /**
     * The nearest relevant (annotated) ancestor class of the class that this metadata was
     * extracted from.
     * <p>
     * For example, when a class is marked as a Component class, then this will
     * refer to the nearest ancestor class that is also marked as a Component
     * class. Note that this is "consistent with" the actual Java class hierarchy for 
     * annotated classes that were used to create this data, but skips java classes
     * that were not annotated (and are therefore irrelevant for metadata purposes).
     * <p>
     * The value of this attribute will match the className attribute of another ClassMeta
     * object. Following the chain of ParentClassName links (plus Interface links) gives all
     * the ClassMeta objects needed to determine the set of metadata properties for a JSF
     * entity.    
     */
    public String getParentClassName()
    {
        return _parentClassName;
    }

    public void setParentClassName(String className)
    {
        _parentClassName = className;
    }

    /**
     * The list of relevant interface classes.
     * <p>
     * For example, when a class is marked as a Component class, then this will
     * refer to the list of interfaces which that class implements that are also
     * marked as a component.
     */
    public List getInterfaceClassNames()
    {
        return _interfaceClassNames;
    }

    public void setInterfaceClassNames(List classNames)
    {
        _interfaceClassNames = classNames;
    }

    public void addInterfaceClassName(String name)
    {
        _interfaceClassNames.add(name);
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

    /**
     * Return the real java parent class of the class from which this metadata
     * was gathered (see classSource property).
     * <p>
     * This value is usually the same as property parentClassName. However if
     * the parent of the annotated class is not itself annotated, then this
     * property still points to the real java parent (which will not have a
     * ClassMeta object representing it) while property parentClass will point
     * to the nearest superclass that does have an annotation (and does have a
     * corresponding ClassMeta object).
     * <p>
     * This information is needed for code generation.
     */
    public void setSourceClassParentClassName(String sourceClassParentClassName)
    {
        this._sourceClassParentClassName = sourceClassParentClassName;
    }

    public String getSourceClassParentClassName()
    {
        if (_sourceClassParentClassName == null)
        {
            //return the parent class name instead.
            return getParentClassName();
        }
        else
        {
            return _sourceClassParentClassName;            
        }        
    }
}
