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
package org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

public class AbstractTagBean extends ObjectBean {
  private List<String> _accessibilityGuidelines = new ArrayList<String>();
  private String       _description;
  private String       _longDescription;
  private QName        _tagName;
  private String       _tagClass;
  protected Map<String, PropertyBean> _properties;
  private int          _tagClassModifiers;
  private Map<String, ExampleBean>    _examples;
  private int          _exampleIdx = 0;
  private Map<String, ScreenshotBean> _screenshots;
  private int          _screenshotIdx = 0;
  
  public AbstractTagBean() 
  {
    this(false);
  }

  public AbstractTagBean(boolean isComponentBean) 
  {
    // Component Bean does its own thing
    // with properties.  The other bean
    // types, i.e. Converters and Validators
    // use the same properties.
    if (!isComponentBean)
    {
      _properties = new LinkedHashMap<String, PropertyBean>();
    }
    _examples   = new LinkedHashMap<String, ExampleBean>();      
    _screenshots = new LinkedHashMap<String, ScreenshotBean>();   
  }

  /**
   * Sets the description of this property.
   *
   * @param description  the property description
   */
  public void setDescription(
    String description)
  {
    _description = description;
  }

  /**
   * Returns the description of this property.
   *
   * @return  the property description
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Sets the long description of this property.
   *
   * @param longDescription  the long property description
   */
  public void setLongDescription(
    String longDescription)
  {
    _longDescription = longDescription;
  }

  /**
   * Returns the long description of this property.
   *
   * @return  the long property description
   */
  public String getLongDescription()
  {
    return _longDescription;
  }

  /**
   * Sets the JSP tag handler class for this component.
   *
   * @param tagClass  the JSP tag handler class
   */
  public void setTagClass(
    String tagClass)
  {
    _tagClass = tagClass;
  }

  /**
   * Returns the JSP tag handler class for this component.
   *
   * @return  the JSP tag handler class
   */
  public String getTagClass()
  {
    return _tagClass;
  }

  /**
   * Sets the JSP tag name for this component.
   *
   * @param tagName  the JSP tag name
   */
  public void setTagName(
      QName tagName)
  {
    _tagName = tagName;
  }


  /**
   * Returns the JSP tag name for this component.
   *
   * @return  the JSP tag name
   */
  public QName getTagName()
  {
    return _tagName;
  }

  /**
   * Adds a property to this component.
   *
   * @param property  the property to add
   */
  public void addProperty(
    PropertyBean property)
  {
    _properties.put(property.getPropertyName(), property);
  }

  /**
   * Returns the property for this property name.
   *
   * @param propertyName  the property name to find
   */
  public PropertyBean findProperty(
    String propertyName)
  {
    return _properties.get(propertyName);
  }

  /**
   * Returns true if this component has any properties.
   *
   * @return  true   if this component has any properties,
   *          false  otherwise
   */
  public boolean hasProperties()
  {
    return !_properties.isEmpty();
  }

  /**
   * Returns an iterator for all properties on this component only.
   *
   * @return  the property iterator
   */
  public Iterator<PropertyBean> properties()
  {
    return _properties.values().iterator();
  }

  /**
   * Adds a Example to this component.
   *
   * @param example  the example to add
   */
  public void addExample(
    ExampleBean example)
  {
    String key = _generateExampleKey();
    example.setKey(key);
    _examples.put(key, example);
  }

  /**
   * Returns true if this component has any examples.
   *
   * @return  true   if this component has any examples,
   *          false  otherwise
   */
  public boolean hasExamples()
  {
    return !_examples.isEmpty();
  }

  /**
   * Returns the example for this example key.
   *
   * @param key  the hashmap example key
   */
  public ExampleBean findExample(
    String key)
  {
    return _examples.get(key);
  }

  /**
   * Returns an iterator for all examples on this component only.
   *
   * @return  the example iterator
   */
  public Iterator<ExampleBean> examples()
  {
    return _examples.values().iterator();
  }

  /**
   * Adds a Screenshot to this component.
   *
   * @param screenshot  the screenshot to add
   */
  public void addScreenshot(
    ScreenshotBean screenshot)
  {
    String key = _generateScreenshotKey();
    screenshot.setKey(key);
    _screenshots.put(key, screenshot);
  }

  /**
   * Returns true if this component has any screenshots.
   *
   * @return  true   if this component has any screenshots,
   *          false  otherwise
   */
  public boolean hasScreenshots()
  {
    return !_screenshots.isEmpty();
  }

  /**
   * Returns the screenshot for this screenshot key.
   *
   * @param key  the hashmap screenshot key
   */
  public ScreenshotBean findScreenshot(
    String key)
  {
    return _screenshots.get(key);
  }

  /**
  * Returns an iterator for all screenshots on this component only.
  *
  * @return  the screenshot iterator
  */
  public Iterator<ScreenshotBean> screenshots()
  {
    return _screenshots.values().iterator();
  }

  /**
   * Adds an Accessibility (e.g. section 508 compliance) Guideline to this component. The
   * accessibility guidelines are used during tag doc generation to give the application
   * developer hints on how to configure the component to be accessibility-compliant.
   *
   * @param accessibilityGuideline  the accessibility guideline to add
   */
  public void addAccessibilityGuideline(
    String accessibilityGuideline)
  {
    _accessibilityGuidelines.add(accessibilityGuideline);    
  }

  /**
   * Returns true if this component has any accessibility guidelines.
   *
   * @return  true   if this component has any accessibility guidelines,
   *          false  otherwise
   */
  public boolean hasAccessibilityGuidelines()
  {
    return !_accessibilityGuidelines.isEmpty();
  }

  /**
   * Returns an iterator for all accessibility guidelines on this component only.
   *
   * @return  the accessibility guidelines iterator
   */
  public Iterator<String> accessibilityGuidelines()
  {
    return _accessibilityGuidelines.iterator();
  }

  public void parseTagClassModifier(
    String modifier)
  {
    addTagClassModifier(_parseModifier(modifier));
  }

  protected int _parseModifier(
    String text)
  {
    if ("public".equals(text))
      return Modifier.PUBLIC;
    else if ("protected".equals(text))
      return Modifier.PROTECTED;
    else if ("private".equals(text))
      return Modifier.PRIVATE;
    else if ("abstract".equals(text))
      return Modifier.ABSTRACT;
    else if ("final".equals(text))
      return Modifier.FINAL;

    throw new IllegalArgumentException("Unrecognized modifier: " + text);
  }

  /**
   * Adds a Java Language class modifier to the tag class.
   *
   * @param modifier  the modifier to be added
   */
  public void addTagClassModifier(
    int modifier)
  {
    _tagClassModifiers |= modifier;
  }

  /**
   * Returns the Java Language class modifiers for the tag class.
   * By default, these modifiers include Modifier.PUBLIC.
   *
   * @return  the Java Language class modifiers for the tag class
   */
  public int getTagClassModifiers()
  {
    int modifiers = _tagClassModifiers;

    if (!Modifier.isPrivate(modifiers) &&
        !Modifier.isProtected(modifiers) &&
        !Modifier.isPublic(modifiers))
    {
      modifiers |= Modifier.PUBLIC;
    }

    return modifiers;
  }

 /**
  * Number of properties for this component
  * @return num of properties
  */
  public int propertiesSize()
  {
    return _properties.size();
  }
 
  /* Get a generated key to use in storing
   * this example bean in its hashmap.
   */
  private String _generateExampleKey()
  {
    String key = "Example" + Integer.toString(_exampleIdx);
    _exampleIdx++;
    return key;
  }
  
  /* Get a generated key to use in storing
   * this screen shot bean in its hashmap.
   */
  private String _generateScreenshotKey()
  {
    String key = "Screenshot" + Integer.toString(_screenshotIdx);
    _screenshotIdx++;
    return key;
  }  
}
