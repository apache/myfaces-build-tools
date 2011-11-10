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

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.apache.commons.digester.Digester;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.parse.rules.BeanPropertySetterRule;
import org.apache.myfaces.buildtools.maven2.plugin.builder.trinidad.util.XIncludeFilter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


public class FacesConfigParser
{
  public void merge(
    FacesConfigBean owner,
    URL             url) throws MojoExecutionException
  {
    try
    {
      URLConnection conn = url.openConnection();
      long lastModified = conn.getLastModified();
      InputStream is = conn.getInputStream();

      if (is != null)
      {
        // Establish the current last-modified value
        // As new components are added, they will remember
        // this current value as their own last-modified
        owner.touch(lastModified);

        Digester digester = createDigester();
        digester.push(url);
        digester.push(owner);
        digester.parse(is);
        is.close();
      }
    }
    catch (IOException e)
    {
      throw new MojoExecutionException("Failed to parse " + url, e);
    }
    catch (SAXException e)
    {
      throw new MojoExecutionException("Failed to parse " + url, e);
    }
    catch (ParserConfigurationException e)
    {
      throw new MojoExecutionException("Failed to parse " + url, e);
    }
  }


  static protected Digester createEmptyDigester()
    throws ParserConfigurationException, SAXException
  {
    SAXParserFactory spf = SAXParserFactory.newInstance();
    spf.setNamespaceAware(true);
    // requires JAXP 1.3, in JavaSE 5.0
    //spf.setXIncludeAware(true);
    Digester digester = new Digester(spf.newSAXParser());
    digester.setNamespaceAware(true);

    return digester;
  }

  static protected void addComponentDigesterRules(Digester digester, boolean withCreate)
  {
    digester.setRuleNamespaceURI("http://java.sun.com/xml/ns/javaee");

    // faces-config/component
    // Only use if we're creating the component;  turn this off
    // when we're simply including content
    if (withCreate)
    {
      // faces-config/component
      digester.addObjectCreate("faces-config/component", ComponentBean.class);
      digester.addBeanPropertySetter("faces-config/component/component-type",
                                     "componentType");
      digester.addBeanPropertySetter("faces-config/component/component-class",
                                     "componentClass");
      digester.addBeanPropertySetter("faces-config/component/js-component-class",
                                     "jsComponentClass");
      digester.addBeanPropertySetter("faces-config/component/description");
      digester.addSetNext("faces-config/component", "addComponent",
                          ComponentBean.class.getName());
    }

    // faces-config/component/property
    digester.addObjectCreate("faces-config/component/property", PropertyBean.class);
    addComponentPropertyDigesterRules(digester);
    digester.addSetNext("faces-config/component/property", "addProperty",
                        PropertyBean.class.getName());


    // faces-config/component/facet
    digester.addObjectCreate("faces-config/component/facet", FacetBean.class);
    digester.addBeanPropertySetter("faces-config/component/facet/facet-name",
                                   "facetName");
    digester.addBeanPropertySetter("faces-config/component/facet/description");
    digester.addSetNext("faces-config/component/facet", "addFacet",
                        FacetBean.class.getName());

    // Maven Faces Plugin
    digester.setRuleNamespaceURI("http://myfaces.apache.org/maven-faces-plugin");

    // faces-config/component/facet/facet-extension
    digester.addBeanPropertySetter("faces-config/component/facet/facet-extension/hidden");

    // faces-config/component/facet/facet-extension/facet-metadata/accessibility-guideline
    digester.addCallMethod("faces-config/component/facet/facet-extension/facet-metadata/accessibility-guideline",
                           "addAccessibilityGuideline", 1);
    digester.addCallParam("faces-config/component/facet/facet-extension/facet-metadata/accessibility-guideline", 0);

    // faces-config/component/facet/example
    digester.addObjectCreate("faces-config/component/facet/example",
                             ExampleBean.class);
    digester.addBeanPropertySetter("faces-config/component/facet/example/source-description",
                                   "sourceDescription");
    digester.addBeanPropertySetter("faces-config/component/facet/example/source-code",
                                   "sourceCode");
    digester.addSetNext("faces-config/component/facet/example",
                        "addExample", ExampleBean.class.getName());

    // faces-config/component/component-extension
    digester.addBeanPropertySetter("faces-config/component/component-extension/long-description",
                                   "longDescription");
    digester.addBeanPropertySetter("faces-config/component/component-extension/component-family",
                                   "componentFamily");
    digester.addBeanPropertySetter("faces-config/component/component-extension/component-supertype",
                                   "componentSupertype");
    digester.addBeanPropertySetter("faces-config/component/component-extension/component-superclass",
                                   "componentSuperclass");
    digester.addBeanPropertySetter("faces-config/component/component-extension/renderer-type",
                                   "rendererType");
    digester.addBeanPropertySetter("faces-config/component/component-extension/naming-container",
                                   "namingContainer");
    digester.addBeanPropertySetter("faces-config/component/component-extension/accepts-child-components",
                                   "children");
    digester.addBeanPropertySetter("faces-config/component/component-extension/tag-class",
                                   "tagClass");
    digester.addBeanPropertySetter("faces-config/component/component-extension/tag-superclass",
                                   "tagSuperclass");
    digester.addBeanPropertySetter("faces-config/component/component-extension/tag-handler",
                                   "tagHandler");
    digester.addBeanPropertySetter("faces-config/component/component-extension/implementation-type",
                                   "implementationType");
    digester.addCallMethod("faces-config/component/component-extension/tag-class-modifier",
                           "parseTagClassModifier", 1);
    digester.addCallParam("faces-config/component/component-extension/tag-class-modifier", 0);
    digester.addCallMethod("faces-config/component/component-extension/unsupported-agents",
                           "parseUnsupportedAgents", 1);
    digester.addCallParam("faces-config/component/component-extension/unsupported-agents", 0);

    digester.addCallMethod("faces-config/component/component-extension/component-class-modifier",
                           "parseComponentClassModifier", 1);
    digester.addCallParam("faces-config/component/component-extension/component-class-modifier", 0);
    digester.addRule("faces-config/component/component-extension/tag-name",
                     new BeanPropertySetterRule("tagName"));
    digester.addBeanPropertySetter("faces-config/component/component-extension/uix2-local-name",
                                   "localName");
    digester.addBeanPropertySetter("faces-config/component/component-extension/uix2-node-class",
                                   "nodeClass");

    // faces-config/component/component-extension/accessibility-guideline
    digester.addCallMethod("faces-config/component/component-extension/accessibility-guideline",
                           "addAccessibilityGuideline", 1);
    digester.addCallParam("faces-config/component/component-extension/accessibility-guideline", 0);

    // faces-config/component/component-extension/example
    digester.addObjectCreate("faces-config/component/component-extension/example",
                             ExampleBean.class);
    digester.addBeanPropertySetter("faces-config/component/component-extension/example/source-description",
                                   "sourceDescription");
    digester.addBeanPropertySetter("faces-config/component/component-extension/example/source-code",
                                   "sourceCode");
    digester.addSetNext("faces-config/component/component-extension/example",
                        "addExample", ExampleBean.class.getName());

    // faces-config/component/component-extension/screenshot
    digester.addObjectCreate("faces-config/component/component-extension/screenshot",
                             ScreenshotBean.class);
    digester.addBeanPropertySetter("faces-config/component/component-extension/screenshot/description",
                                   "description");
    digester.addBeanPropertySetter("faces-config/component/component-extension/screenshot/image",
                                   "image");
    digester.addSetNext("faces-config/component/component-extension/screenshot",
                        "addScreenshot", ScreenshotBean.class.getName());

    // faces-config/component/component-extension/event
    digester.addObjectCreate("faces-config/component/component-extension/event", EventRefBean.class);
    digester.addBeanPropertySetter("faces-config/component/component-extension/event/event-type",
                                   "eventType");
    digester.addBeanPropertySetter("faces-config/component/component-extension/event/event-delivery-phase",
                                   "eventDeliveryPhases");
    digester.addBeanPropertySetter("faces-config/component/component-extension/event/ignore-source-interface",
                                   "ignoreSourceInterface");
    digester.addSetNext("faces-config/component/component-extension/event", "addEvent",
                        EventRefBean.class.getName());

    // jsr-276 metadata rules
    digester.setRuleNamespaceURI("http://java.sun.com/xml/ns/javaee/faces/design-time-metadata");
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/property-metadata/required");
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/property-metadata/value-expression", "valueExpression");
    digester.addBeanPropertySetter("faces-config/component/component-extension/component-metadata/default-event-name",
                                   "defaultEventName");
    digester.addBeanPropertySetter("faces-config/component/facet/facet-extension/facet-metadata/hidden");
    digester.addCallMethod("faces-config/component/component-extension/component-metadata/event-names",
                           "parseEventNames", 1);
    digester.addCallParam("faces-config/component/component-extension/component-metadata/event-names", 0);

    // faces-config/component/facet/facet-extension/facet-metadata/allowed-child-components
    digester.addCallMethod("faces-config/component/facet/facet-extension/facet-metadata/allowed-child-components",
                           "parseAllowedChildComponents", 1);
    digester.addCallParam("faces-config/component/facet/facet-extension/facet-metadata/allowed-child-components", 0);

    // faces-config/component/component-extension/component-metadata/required-ancestor-contracts
    digester.addCallMethod("faces-config/component/component-extension/component-metadata/required-ancestor-contracts",
                           "parseRequiredAncestorContracts", 1);
    digester.addCallParam("faces-config/component/component-extension/component-metadata/required-ancestor-contracts", 0);

    // faces-config/component/component-extension/component-metadata/satisfied-contracts
    digester.addCallMethod("faces-config/component/component-extension/component-metadata/satisfied-contracts",
                           "parseSatisfiedContracts", 1);
    digester.addCallParam("faces-config/component/component-extension/component-metadata/satisfied-contracts", 0);

    // XInclude rules
    digester.setRuleNamespaceURI(XIncludeFilter.XINCLUDE_NAMESPACE);
    digester.addFactoryCreate("faces-config/component/include",
                              ComponentIncludeFactory.class);
    digester.addFactoryCreate("faces-config/component/property/include",
                              ComponentPropertyIncludeFactory.class);
    digester.addFactoryCreate("faces-config/component/component-extension/include",
                              ComponentIncludeFactory.class);
    digester.addFactoryCreate("faces-config/component/component-extension/component-metadata/include",
                              ComponentIncludeFactory.class);
  }

  // Add component property-related digster rules
  protected static void addComponentPropertyDigesterRules(Digester digester)
  {
    String oldNamespace = digester.getRuleNamespaceURI();

    digester.setRuleNamespaceURI("http://java.sun.com/xml/ns/javaee");

    digester.addBeanPropertySetter("faces-config/component/property/property-name",
                                   "propertyName");
    digester.addBeanPropertySetter("faces-config/component/property/property-class",
                                   "propertyClass");
    digester.addBeanPropertySetter("faces-config/component/property/description");
    digester.addBeanPropertySetter("faces-config/component/property/default-value",
                                   "defaultValue");

    // Maven Faces Plugin
    digester.setRuleNamespaceURI("http://myfaces.apache.org/maven-faces-plugin");

    // faces-config/component/property/property-extension
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/state-holder",
                                   "stateHolder");
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/jsp-property-name",
                                   "jspPropertyName");
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/list",
                                   "list");
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/required");
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/alias-of",
                                   "aliasOf");
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/virtual");
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/transient");
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/literal-only",
                                   "literalOnly");
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/enum",
                                   "enum");
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/alternate-class",
                                   "alternateClass");
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/tag-attribute-excluded",
                                   "tagAttributeExcluded");
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/hidden");
    digester.addCallMethod("faces-config/component/property/property-extension/property-values",
                           "parsePropertyValues", 1);
    digester.addCallParam("faces-config/component/property/property-extension/property-values", 0);
    digester.addCallMethod("faces-config/component/property/property-extension/unsupported-agents",
                           "parseUnsupportedAgents", 1);
    digester.addCallParam("faces-config/component/property/property-extension/unsupported-agents", 0);
    digester.addCallMethod("faces-config/component/property/property-extension/unsupported-render-kits",
                           "parseUnsupportedRenderKits", 1);
    digester.addCallParam("faces-config/component/property/property-extension/unsupported-render-kits", 0);

    digester.addObjectCreate("faces-config/component/property/property-extension/method-binding-signature",
                             MethodSignatureBean.class);
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/method-binding-signature/return-type",
                                   "returnType");
    digester.addCallMethod("faces-config/component/property/property-extension/method-binding-signature/parameter-type",
                           "addParameterType", 1);
    digester.addCallParam("faces-config/component/property/property-extension/method-binding-signature/parameter-type", 0);
    digester.addSetNext("faces-config/component/property/property-extension/method-binding-signature",
                        "setMethodBindingSignature",
                        MethodSignatureBean.class.getName());

    // faces-config/component/property/property-extension/property-metadata
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/property-metadata/use-max-time",
                                   "useMaxTime");
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/property-metadata/deprecated");
    digester.addCallMethod("faces-config/component/property/property-extension/property-metadata/no-op", "makeNoOp");

    // faces-config/component/property/property-extension/property-metadata/accessibility-guideline
    digester.addCallMethod("faces-config/component/property/property-extension/property-metadata/accessibility-guideline",
                           "addAccessibilityGuideline", 1);
    digester.addCallParam("faces-config/component/property/property-extension/property-metadata/accessibility-guideline", 0);

    // jsr-276 metadata rules
    digester.setRuleNamespaceURI("http://java.sun.com/xml/ns/javaee/faces/design-time-metadata");
    digester.addCallMethod("faces-config/component/property/property-extension/property-metadata/property-values",
                           "parsePropertyValues", 1);
    digester.addCallParam("faces-config/component/property/property-extension/property-metadata/property-values", 0);
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/property-metadata/deprecated");
    digester.addBeanPropertySetter("faces-config/component/property/property-extension/property-metadata/hidden");

    digester.setRuleNamespaceURI(oldNamespace);
  }

  protected static void addConverterDigesterRules(Digester digester)
  {
    digester.setRuleNamespaceURI("http://java.sun.com/xml/ns/javaee");

    // faces-config/converter
    digester.addObjectCreate("faces-config/converter", ConverterBean.class);
    digester.addBeanPropertySetter("faces-config/converter/converter-id",
                                   "converterId");
    digester.addBeanPropertySetter("faces-config/converter/converter-class",
                                   "converterClass");
    digester.addBeanPropertySetter("faces-config/converter/description");
    digester.addSetNext("faces-config/converter", "addConverter",
                        ConverterBean.class.getName());

    // faces-config/converter/property
    digester.addObjectCreate("faces-config/converter/property", PropertyBean.class);
    digester.addBeanPropertySetter("faces-config/converter/property/property-name",
                                   "propertyName");
    digester.addBeanPropertySetter("faces-config/converter/property/property-class",
                                   "propertyClass");
    digester.addBeanPropertySetter("faces-config/converter/property/description");
    digester.addBeanPropertySetter("faces-config/converter/property/default-value",
                                   "defaultValue");
    digester.addSetNext("faces-config/converter/property", "addProperty",
                        PropertyBean.class.getName());

    // Maven Faces Plugin
    digester.setRuleNamespaceURI("http://myfaces.apache.org/maven-faces-plugin");

    // faces-config/converter/converter-extension
    digester.addBeanPropertySetter("faces-config/converter/converter-extension/root-converter-id",
                                   "rootConverterId");
    digester.addBeanPropertySetter("faces-config/converter/converter-extension/long-description",
                                   "longDescription");
    digester.addBeanPropertySetter("faces-config/converter/converter-extension/tag-class",
                                   "tagClass");
    digester.addRule("faces-config/converter/converter-extension/tag-name",
                     new BeanPropertySetterRule("tagName"));
    digester.addCallMethod("faces-config/converter/converter-extension/tag-class-modifier",
                           "parseTagClassModifier", 1);
    digester.addCallParam("faces-config/converter/converter-extension/tag-class-modifier", 0);

    // faces-config/converter/converter-extension/example
    digester.addObjectCreate("faces-config/converter/converter-extension/example",
                             ExampleBean.class);
    digester.addBeanPropertySetter("faces-config/converter/converter-extension/example/source-description",
                                   "sourceDescription");
    digester.addBeanPropertySetter("faces-config/converter/converter-extension/example/source-code",
                                   "sourceCode");
    digester.addSetNext("faces-config/converter/converter-extension/example",
                        "addExample", ExampleBean.class.getName());

    // faces-config/converter/property/property-extension
    digester.addBeanPropertySetter("faces-config/converter/property/property-extension/tag-attribute-excluded",
                                   "tagAttributeExcluded");
  }

  protected static void addValidatorDigesterRules(Digester digester)
  {
    // Java Enterprise 5.0
    digester.setRuleNamespaceURI("http://java.sun.com/xml/ns/javaee");

    // faces-config/validator
    digester.addObjectCreate("faces-config/validator", ValidatorBean.class);
    digester.addBeanPropertySetter("faces-config/validator/validator-id",
                                   "validatorId");
    digester.addBeanPropertySetter("faces-config/validator/validator-class",
                                   "validatorClass");
    digester.addBeanPropertySetter("faces-config/validator/description");
    digester.addSetNext("faces-config/validator", "addValidator",
                        ValidatorBean.class.getName());

    // faces-config/validator/property
    digester.addObjectCreate("faces-config/validator/property", PropertyBean.class);
    digester.addBeanPropertySetter("faces-config/validator/property/property-name",
                                   "propertyName");
    digester.addBeanPropertySetter("faces-config/validator/property/property-class",
                                   "propertyClass");
    digester.addBeanPropertySetter("faces-config/validator/property/description");
    digester.addBeanPropertySetter("faces-config/validator/property/default-value",
                                   "defaultValue");
    digester.addSetNext("faces-config/validator/property", "addProperty",
                        PropertyBean.class.getName());

    // Maven Faces Plugin
    digester.setRuleNamespaceURI("http://myfaces.apache.org/maven-faces-plugin");

    // faces-config/validator/validator-extension
    digester.addBeanPropertySetter("faces-config/validator/validator-extension/root-validator-id",
                                   "rootValidatorId");
    digester.addBeanPropertySetter("faces-config/validator/validator-extension/long-description",
                                   "longDescription");
    digester.addBeanPropertySetter("faces-config/validator/validator-extension/tag-class",
                                   "tagClass");
    digester.addRule("faces-config/validator/validator-extension/tag-name",
                     new BeanPropertySetterRule("tagName"));
    digester.addCallMethod("faces-config/validator/validator-extension/tag-class-modifier",
                           "parseTagClassModifier", 1);
    digester.addCallParam("faces-config/validator/validator-extension/tag-class-modifier", 0);

    // faces-config/validator/validator-extension/example
    digester.addObjectCreate("faces-config/validator/validator-extension/example",
                             ExampleBean.class);
    digester.addBeanPropertySetter("faces-config/validator/validator-extension/example/source-description",
                                   "sourceDescription");
    digester.addBeanPropertySetter("faces-config/validator/validator-extension/example/source-code",
                                   "sourceCode");
    digester.addSetNext("faces-config/validator/validator-extension/example",
                        "addExample", ExampleBean.class.getName());

    // faces-config/validator/property/property-extension
    digester.addBeanPropertySetter("faces-config/validator/property/property-extension/tag-attribute-excluded",
                                   "tagAttributeExcluded");
    // faces-config/validator/property/property-extension/property-metada
    digester.addBeanPropertySetter("faces-config/validator/property/property-extension/property-metadata/use-max-time",
                                 "useMaxTime");
  }

  protected static void addRenderKitDigesterRules(Digester digester)
  {
    // Java Enterprise 5.0
    digester.setRuleNamespaceURI("http://java.sun.com/xml/ns/javaee");

    // faces-config/render-kit
    digester.addObjectCreate("faces-config/render-kit", RenderKitBean.class);
    digester.addBeanPropertySetter("faces-config/render-kit/render-kit-id",
                                   "renderKitId");
    digester.addSetNext("faces-config/render-kit", "addRenderKit",
                        RenderKitBean.class.getName());

    // faces-config/render-kit/renderer
    digester.addObjectCreate("faces-config/render-kit/renderer", RendererBean.class);
    digester.addBeanPropertySetter("faces-config/render-kit/renderer/description");
    digester.addBeanPropertySetter("faces-config/render-kit/renderer/component-family",
                                   "componentFamily");
    digester.addBeanPropertySetter("faces-config/render-kit/renderer/renderer-type",
                                   "rendererType");
    digester.addBeanPropertySetter("faces-config/render-kit/renderer/renderer-class",
                                   "rendererClass");
    digester.addSetNext("faces-config/render-kit/renderer", "addRenderer",
                        RendererBean.class.getName());

    // Maven Faces Plugin
    digester.setRuleNamespaceURI("http://myfaces.apache.org/maven-faces-plugin");

    // faces-config/render-kit/renderer/renderer-extension
    digester.addBeanPropertySetter("faces-config/render-kit/renderer/renderer-extension/component-type",
                                   "componentType");
    digester.addBeanPropertySetter("faces-config/render-kit/renderer/renderer-extension/renderer-superclass",
                                   "rendererSuperclass");
  }

  protected static void addEventDigesterRules(Digester digester)
  {
    // Java Enterprise 5.0
    // digester.setRuleNamespaceURI("http://java.sun.com/xml/ns/javaee");

    // Maven Faces Plugin
    digester.setRuleNamespaceURI("http://myfaces.apache.org/maven-faces-plugin");

    // faces-config/faces-config-extension/event
    digester.addObjectCreate("faces-config/faces-config-extension/event", EventBean.class);
    digester.addBeanPropertySetter("faces-config/faces-config-extension/event/description");
    digester.addBeanPropertySetter("faces-config/faces-config-extension/event/event-type",
                                   "eventType");
    digester.addBeanPropertySetter("faces-config/faces-config-extension/event/event-class",
                                   "eventClass");
    digester.addBeanPropertySetter("faces-config/faces-config-extension/event/event-listener-class",
                                   "eventListenerClass");
    digester.addBeanPropertySetter("faces-config/faces-config-extension/event/event-source-interface",
                                   "eventSourceInterface");
    digester.addSetNext("faces-config/faces-config-extension/event", "addEvent",
                        EventBean.class.getName());
  }

  protected Digester createDigester() throws ParserConfigurationException, SAXException
  {
    Digester digester = createEmptyDigester();

    addComponentDigesterRules(digester, true);

   //digester.addObjectCreate("faces-config", FacesConfigBean.class);

    addConverterDigesterRules(digester);
    addValidatorDigesterRules(digester);
    addRenderKitDigesterRules(digester);
    addEventDigesterRules(digester);

    // TBD: JSR-276 metadata (ask Jeff Stephenson)

    return digester;
  }

  // Base class for include factories
  abstract static public class AbstractIncludeFactory extends AbstractObjectCreationFactory
  {
    public Object createObject(
      Attributes attributes) throws Exception
    {
      String href = attributes.getValue("href");
      if (href == null)
        throw new IllegalStateException("Missing href attribute");

      URL master = (URL)digester.getRoot();
      URL included = new URL(master, href);

      Digester includedDigester = createEmptyDigester();
      addDigesterRules(includedDigester);
      includedDigester.push(included);
      includedDigester.push(digester.peek());

      URLConnection conn = included.openConnection();
      InputStream is = conn.getInputStream();
      includedDigester.parse(is);
      is.close();

      // We don't really want the included object - but return it anyway
      return included;
    }

    abstract public void addDigesterRules(Digester includedDigester);
  }

  // Factory for component includes
  static public class ComponentIncludeFactory extends AbstractIncludeFactory
  {
    public void addDigesterRules(Digester digester) {
      addComponentDigesterRules(digester, false);
    }
  }

  // Factory for component property includes
  static public class ComponentPropertyIncludeFactory extends AbstractIncludeFactory
  {
    public void addDigesterRules(Digester digester) {
      addComponentPropertyDigesterRules(digester);
    }
  }

}
