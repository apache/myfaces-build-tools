<?xml version="1.0" ?>
<!--
    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.
	   
-->
<xsl:stylesheet xmlns="http://java.sun.com/JSF/Configuration"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:javaee="http://java.sun.com/xml/ns/javaee"
                xmlns:mfp="http://myfaces.apache.org/maven-faces-plugin" 
                exclude-result-prefixes="xsl xs javaee mfp"
                version="1.0" >

  <xsl:output method="xml" indent="yes"/>
  <xsl:param name="packageContains" />
  <xsl:param name="typePrefix" />
  <xsl:param name="removeRenderers" />

  <xsl:variable name="doctype" ><![CDATA[
  <!DOCTYPE faces-config PUBLIC
  "-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.1//EN"
  "http://java.sun.com/dtd/web-facesconfig_1_1.dtd"[
  <!ELEMENT component-metadata ANY>
  <!ELEMENT attribute-metadata ANY>
  <!ELEMENT property-metadata ANY>
  <!ELEMENT facet-metadata ANY>
  <!ELEMENT renderer-metadata ANY>
  <!ELEMENT accepts-child-components (#PCDATA)>
  <!ELEMENT attribute-values (#PCDATA)>
  <!ELEMENT base-component-type (#PCDATA)>
  <!ELEMENT deprecated (#PCDATA)>
  <!ELEMENT display-order (#PCDATA)>
  <!ELEMENT expert (#PCDATA)>
  <!ELEMENT favorite-property (#PCDATA)>
  <!ELEMENT group (#PCDATA)>
  <!ELEMENT help-topic (#PCDATA)>
  <!ELEMENT hidden (#PCDATA)>
  <!ELEMENT initial-value (#PCDATA)>
  <!ELEMENT preferred (#PCDATA)>
  <!ELEMENT preferred-children (#PCDATA)>
  <!ELEMENT allowable-children (#PCDATA)>
  <!ELEMENT preferred-component-types (#PCDATA)>
  <!ELEMENT property-editor (#PCDATA)>
  <!ELEMENT short-description (#PCDATA)>
  <!ELEMENT translatable (#PCDATA)>
  <!ELEMENT unsupported-agents (#PCDATA)>
  <!ATTLIST component-metadata component-type CDATA #IMPLIED>
]>
]]></xsl:variable>

  <xsl:key name="component-type" 
           match="javaee:component" 
           use="javaee:component-type/text()" />

  <xsl:key name="render-kit-id" 
           match="javaee:render-kit" 
           use="javaee:render-kit-id/text()" />

  <!-- switch off default text processing -->  
  <xsl:template match="//text()" />

  <xsl:template match="/javaee:faces-config" >
    <xsl:value-of disable-output-escaping="yes" select="$doctype" />
    <xsl:element name="faces-config"
                 namespace="http://java.sun.com/JSF/Configuration" >
      <xsl:apply-templates select="javaee:application" />
      <xsl:apply-templates select="javaee:factory" />
      <xsl:apply-templates select="javaee:component[not(contains(javaee:component-extension/mfp:component-class-modifier/text(), 'abstract')) and
                                                    starts-with(javaee:component-type, $typePrefix)]" />
      <xsl:apply-templates select="javaee:converter[contains(javaee:converter-class, $packageContains)]" />
      <xsl:apply-templates select="javaee:managed-bean[contains(javaee:managed-bean-class, $packageContains)]" />
      <xsl:apply-templates select="javaee:navigation-rule" />
      <xsl:apply-templates select="javaee:referenced-bean" />
      <!-- merge the render-kits together -->
      <xsl:for-each select="javaee:render-kit[contains(javaee:render-kit-class, $packageContains)]" >
        <xsl:element name="render-kit" >
          <xsl:apply-templates select="javaee:description" />
          <xsl:apply-templates select="javaee:display-name" />
          <xsl:apply-templates select="javaee:icon" />
          <xsl:apply-templates select="javaee:render-kit-id" />
          <xsl:apply-templates select="javaee:render-kit-class" />
          <!-- Drop renderers if desired -->
          <xsl:if test="$removeRenderers != 'true'">
            <xsl:for-each select="key('render-kit-id', javaee:render-kit-id/text())" >
              <xsl:apply-templates select="javaee:renderer[contains(javaee:renderer-class, $packageContains)]" />
            </xsl:for-each>
          </xsl:if>
        </xsl:element>
      </xsl:for-each>
      <xsl:apply-templates select="javaee:lifecycle[contains(javaee:phase-listener, $packageContains)]" />
      <xsl:apply-templates select="javaee:validator[contains(javaee:validator-class, $packageContains)]" />
    </xsl:element>
  </xsl:template>

  <!-- this templates applies javaee:property templates
       for a component and all supertypes -->
  <xsl:template name="apply-property-templates" >
    <xsl:param name="component" />
    <xsl:variable name="componentSupertype"
                  select="$component/javaee:component-extension/mfp:component-supertype/text()" />
    <xsl:if test="$componentSupertype" >
      <xsl:call-template name="apply-property-templates" >
        <xsl:with-param name="component" 
                        select="key('component-type', $componentSupertype)" />
      </xsl:call-template>
    </xsl:if>
    <xsl:apply-templates select="$component/javaee:property" />
  </xsl:template>
  
  <!-- this templates applies javaee:attribute templates
       for a component and all supertypes -->
  <xsl:template name="apply-attribute-templates" >
    <xsl:param name="component" />
    <xsl:variable name="componentSupertype"
                  select="$component/javaee:component-extension/mfp:component-supertype/text()" />
    <xsl:if test="$componentSupertype" >
      <xsl:call-template name="apply-attribute-templates" >
        <xsl:with-param name="component"
                        select="key('component-type', $componentSupertype)" />
      </xsl:call-template>
    </xsl:if>
    <xsl:apply-templates select="$component/javaee:attribute" />
  </xsl:template>
  
  <!-- this templates applies javaee:facet templates
       for a component and all supertypes -->
  <xsl:template name="apply-facet-templates" >
    <xsl:param name="component" />
    <xsl:variable name="componentSupertype"
                  select="$component/javaee:component-extension/mfp:component-supertype/text()" />
    <xsl:if test="$componentSupertype" >
      <xsl:call-template name="apply-facet-templates" >
        <xsl:with-param name="component"
                        select="key('component-type', $componentSupertype)" />
      </xsl:call-template>
    </xsl:if>
    <xsl:apply-templates select="$component/javaee:facet" />
  </xsl:template>
  
  <xsl:template match="//javaee:component[javaee:component-extension/mfp:component-supertype]" 
                priority="1" >
    <xsl:element name="component" >
      <xsl:apply-templates select="javaee:description" />
      <xsl:apply-templates select="javaee:display-name" />
      <xsl:apply-templates select="javaee:icon" />
      <xsl:apply-templates select="javaee:component-type" />
      <xsl:apply-templates select="javaee:component-class" />
      <xsl:call-template name="apply-facet-templates" >
        <xsl:with-param name="component" select="." />
      </xsl:call-template>
      <xsl:call-template name="apply-attribute-templates" >
        <xsl:with-param name="component" select="." />
      </xsl:call-template>
      <xsl:call-template name="apply-property-templates" >
        <xsl:with-param name="component" select="." />
      </xsl:call-template>
      <xsl:apply-templates select="javaee:component-extension" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:application" >
    <xsl:element name="application" >
      <xsl:apply-templates select="javaee:action-listener[contains(text(), $packageContains)]" />
      <xsl:apply-templates select="javaee:default-render-kit-id" />
      <xsl:apply-templates select="javaee:message-bundle[contains(text(), $packageContains)]" />
      <xsl:apply-templates select="javaee:view-handler[contains(text(), $packageContains)]" />
      <xsl:apply-templates select="javaee:state-manager[contains(text(), $packageContains)]" />
      <xsl:apply-templates select="javaee:navigation-handler[contains(text(), $packageContains)]" />
      <xsl:apply-templates select="javaee:property-resolver[contains(text(), $packageContains)]" />
      <xsl:apply-templates select="javaee:variable-resolver[contains(text(), $packageContains)]" />
      <xsl:apply-templates select="javaee:locale-config" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:action-listener" >
    <xsl:element name="action-listener" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:default-render-kit-id" >
    <xsl:element name="default-render-kit-id" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:message-bundle" >
    <xsl:element name="message-bundle" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:navigation-handler" >
    <xsl:element name="navigation-handler" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:view-handler" >
    <xsl:element name="view-handler" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:state-manager" >
    <xsl:element name="state-manager" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:property-resolver" >
    <xsl:element name="property-resolver" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:variable-resolver" >
    <xsl:element name="variable-resolver" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:locale-config" >
    <xsl:element name="locale-config" >
      <xsl:apply-templates />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:factory" >
    <xsl:element name="factory" >
      <xsl:apply-templates select="javaee:application-factory[contains(text(), $packageContains)]" />
      <xsl:apply-templates select="javaee:faces-context-factory[contains(text(), $packageContains)]" />
      <xsl:apply-templates select="javaee:lifecycle-factory[contains(text(), $packageContains)]" />
      <xsl:apply-templates select="javaee:render-kit-factory[contains(text(), $packageContains)]" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:application-factory" >
    <xsl:element name="application-factory" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:faces-context-factory" >
    <xsl:element name="faces-context-factory" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:lifecycle-factory" >
    <xsl:element name="lifecycle-factory" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:render-kit-factory" >
    <xsl:element name="render-kit-factory" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:component" >
    <xsl:element name="component" >
      <xsl:apply-templates />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:component-type" >
    <xsl:element name="component-type" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:component-class" >
    <xsl:element name="component-class" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:component/javaee:facet[1]" priority="1" >
    <xsl:comment><xsl:value-of select="parent::node()/javaee:component-type/text()" /> facets</xsl:comment>
    <xsl:element name="facet" >
      <xsl:apply-templates/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:facet" >
    <xsl:element name="facet" >
      <xsl:apply-templates/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:facet-name" >
    <xsl:element name="facet-name" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:facet-extension[mfp:facet-metadata|mfp:allowable-children|mfp:preferred-children|mfp:preferred|mfp:unsupported-agents|mfp:deprecated]" >
    <xsl:element name="facet-extension" >
      <xsl:element name="facet-metadata" >
        <xsl:apply-templates/>
      </xsl:element>
    </xsl:element>
  </xsl:template>

  
  <xsl:template match="//javaee:component/javaee:attribute[1]" priority="1" >
    <xsl:comment><xsl:value-of select="parent::node()/javaee:component-type/text()" /> attributes</xsl:comment>
    <xsl:element name="attribute" >
      <xsl:apply-templates/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:component/javaee:attribute[1]" priority="1" >
    <xsl:comment><xsl:value-of select="parent::node()/javaee:component-type/text()" /> attributes</xsl:comment>
    <xsl:element name="attribute" >
      <xsl:apply-templates/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:attribute" >
    <xsl:element name="attribute" >
      <xsl:apply-templates/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:attribute-name" >
    <xsl:element name="attribute-name" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:attribute-class" >
    <xsl:element name="attribute-class" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:attribute-extension" >
    <xsl:element name="attribute-extension" >
      <xsl:apply-templates />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:component/javaee:property[1]" priority="1" >
    <xsl:comment><xsl:value-of select="parent::node()/javaee:component-type/text()" /> properties</xsl:comment>
    <xsl:element name="property" >
      <xsl:apply-templates/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:property[javaee:property-name/text() = 'binding']" priority='2' >
    <!-- skip over properties named 'binding' -->
  </xsl:template>

  <xsl:template match="//javaee:property" >
    <xsl:element name="property" >
      <xsl:apply-templates/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:property-name" >
    <xsl:element name="property-name" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:property-class" >
    <xsl:element name="property-class" >
      <!-- eliminate generics for 1.4-based classes in JSF 1.1 -->
      <xsl:choose>
        <xsl:when test="contains(text(), '&lt;')" >
          <xsl:value-of select="substring-before(text(), '&lt;')" />
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="text()" />
        </xsl:otherwise>
      </xsl:choose>
    </xsl:element>
  </xsl:template>
  
  <xsl:template match="//javaee:property-extension[mfp:property-values|mfp:group|mfp:translatable|mfp:property-editor|mfp:expert|mfp:unsupported-agents|*[namespace-uri() != 'http://java.sun.com/xml/ns/javaee' and namespace-uri() !='http://myfaces.apache.org/maven-faces-plugin']]" >
    <xsl:element name="property-extension" >
      <xsl:element name="property-metadata" >
        <xsl:apply-templates/>
      </xsl:element>
    </xsl:element>
  </xsl:template>
  
  <xsl:template match="//javaee:property-extension[mfp:property-metadata]" priority="1" >
    <xsl:element name="property-extension" >
      <!-- do not copy the property values, but instead let them be picked up in the property-metadata element -->
      <xsl:apply-templates select="*[name() != 'mfp:property-values']" />
    </xsl:element>
  </xsl:template>
  
  <xsl:template match="//mfp:property-metadata" >
    <xsl:element name="property-metadata" >
      <xsl:apply-templates/>
      <!-- pick up any property-values elements from the parent node -->
      <xsl:apply-templates select="../mfp:property-values" />
    </xsl:element>
  </xsl:template>

  <!-- this templates grabs the component-family from an ancestor -->
  <xsl:template match="//javaee:component-extension[mfp:component-supertype]" priority="1" >
    <xsl:variable name="componentSupertype"
                  select="mfp:component-supertype/text()" />
    <xsl:element name="component-extension" >
      <xsl:element name="component-family">
        <xsl:value-of select="key('component-type', $componentSupertype)/javaee:component-extension/mfp:component-family/text()" />
      </xsl:element>
      <xsl:apply-templates/>
    </xsl:element>
  </xsl:template>
  
  <xsl:template match="//javaee:component-extension[mfp:component-family]" priority="2" >
    <xsl:element name="component-extension" >
      <xsl:apply-templates/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:component-extension" >
    <xsl:comment>Warning: this component has no component-family!</xsl:comment>
    <xsl:element name="component-extension" >
      <xsl:apply-templates/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="//mfp:component-metadata" >
    <xsl:element name="component-metadata" >
      <xsl:apply-templates/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:converter" >
    <xsl:element name="converter" >
      <!-- xsl:apply-templates/ TODO use this instead -->
      <xsl:apply-templates select="javaee:description"/>
      <xsl:apply-templates select="javaee:display-name"/>
      <xsl:apply-templates select="javaee:converter-id"/>
      <xsl:apply-templates select="javaee:converter-for-class"/>
      <xsl:apply-templates select="javaee:converter-class"/>
      <xsl:apply-templates select="javaee:property"/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:converter-id" >
    <xsl:element name="converter-id" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:converter-for-class" >
    <xsl:element name="converter-for-class" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:converter-class" >
    <xsl:element name="converter-class" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:managed-bean" >
    <xsl:element name="managed-bean" >
      <xsl:apply-templates />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:managed-bean-name" >
    <xsl:element name="managed-bean-name" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:managed-bean-class" >
    <xsl:element name="managed-bean-class" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:managed-bean-scope" >
    <xsl:element name="managed-bean-scope" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:managed-property" >
    <xsl:element name="managed-property" >
      <xsl:apply-templates />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:null-value" >
    <xsl:element name="null-value" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:value" >
    <xsl:element name="value" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:map-entries" >
    <xsl:element name="map-entries" >
      <xsl:apply-templates />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:map-entry" >
    <xsl:element name="map-entry" >
      <xsl:apply-templates />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:key" >
    <xsl:element name="key" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:key-class" >
    <xsl:element name="key-class" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:value-class" >
    <xsl:element name="value-class" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:message-bundle" >
    <xsl:element name="message-bundle" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:list-entries" >
    <xsl:element name="list-entries" >
      <xsl:apply-templates />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:navigation-rule" >
    <xsl:element name="navigation-rule" >
      <xsl:apply-templates />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:from-view-id" >
    <xsl:element name="from-view-id" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:navigation-case" >
    <xsl:element name="navigation-case" >
      <xsl:apply-templates />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:from-action" >
    <xsl:element name="from-action" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:from-outcome" >
    <xsl:element name="from-outcome" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:to-view-id" >
    <xsl:element name="to-view-id" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:redirect" >
    <xsl:element name="redirect" />
  </xsl:template>

  <xsl:template match="//javaee:referenced-bean" >
    <xsl:element name="referenced-bean" >
      <xsl:apply-templates />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:referenced-bean-name" >
    <xsl:element name="referenced-bean-name" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:referenced-bean-class" >
    <xsl:element name="referenced-bean-class" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:render-kit" >
    <xsl:element name="render-kit" >
      <xsl:apply-templates />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:render-kit-id" >
    <xsl:element name="render-kit-id" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:render-kit-class" >
    <xsl:element name="render-kit-class" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

<!-- TODO: flatten component properties into renderer as attributes -->
<!--
  <xsl:template match="//javaee:renderer[javaee:renderer-extension/mfp:component-type]"
                priority="1" >
    <xsl:element name="renderer" >
      <xsl:apply-templates select="javaee:component-family" />
      <xsl:apply-templates select="javaee:renderer-type" />
      <xsl:apply-templates select="javaee:renderer-class" />
      <xsl:variable name="componentType" select="javaee:renderer-extension/mfp:component-type/text()" />
      <xsl:for-each select="key('component-type', $componentType)/javaee:property" >
        <xsl:element name="attribute" >
          <xsl:apply-templates select="javaee:description" />
          <xsl:apply-templates select="javaee:display-name" />
          <xsl:apply-templates select="javaee:icon" />
          <xsl:element name="attribute-name" >
            <xsl:value-of select="javaee:property-name" />
          </xsl:element>
          <xsl:element name="attribute-class" >
            <xsl:value-of select="javaee:property-class" />
          </xsl:element>
          <xsl:apply-templates select="javaee:default-value" />
          <xsl:apply-templates select="javaee:suggested-value" />
          <xsl:element name="attribute-extension" >
            <xsl:apply-templates select="mfp:property-extension/*" />
          </xsl:element>
        </xsl:element>
      </xsl:for-each>
      <xsl:apply-templates select="javaee:attribute" />
      <xsl:apply-templates select="javaee:renderer-extension" />
    </xsl:element>
  </xsl:template>
-->

  <xsl:template match="//javaee:renderer" >
    <xsl:element name="renderer" >
      <xsl:apply-templates />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:component-family" >
    <xsl:element name="component-family" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:renderer-type" >
    <xsl:element name="renderer-type" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:renderer-class" >
    <xsl:element name="renderer-class" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:renderer-extension[mfp:unsupported-agents]" >
    <xsl:element name="renderer-extension" >
      <xsl:element name="renderer-metadata" >
        <xsl:apply-templates/>
      </xsl:element>
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:lifecycle" >
    <xsl:element name="lifecycle" >
      <xsl:apply-templates select="javaee:phase-listener[contains(text(), $packageContains)]" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:phase-listener" >
    <xsl:element name="phase-listener" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:default-locale" >
    <xsl:element name="default-locale" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:supported-locale" >
    <xsl:element name="supported-locale" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:validator" >
    <xsl:element name="validator" >
      <!-- xsl:apply-templates/ TODO use this instead -->
      <xsl:apply-templates select="javaee:display-name"/>
      <xsl:apply-templates select="javaee:validator-id"/>
      <xsl:apply-templates select="javaee:validator-class"/>
      <xsl:apply-templates select="javaee:property"/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:validator-id" >
    <xsl:element name="validator-id" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:validator-class" >
    <xsl:element name="validator-class" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:description" >
    <xsl:element name="description" >
      <xsl:apply-templates select="@*" />
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:property[javaee:property-extension/mfp:long-description]/javaee:description" priority="1">
    <xsl:element name="description" >
      <xsl:apply-templates select="../javaee:property-extension/mfp:long-description/@*" />
      <xsl:value-of select="../javaee:property-extension/mfp:long-description/text()" />
    </xsl:element>
  </xsl:template>


  <xsl:template match="//javaee:description/@xml:lang" >
    <xsl:attribute name="xml:lang" ><xsl:value-of select="@xml:lang" /></xsl:attribute>
  </xsl:template>

  <xsl:template match="//javaee:display-name" >
    <xsl:element name="display-name" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:icon" >
    <xsl:element name="icon" >
      <xsl:apply-templates/>
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:small-icon" >
    <xsl:element name="small-icon" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:large-icon" >
    <xsl:element name="large-icon" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:default-value" >
    <xsl:element name="default-value" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//javaee:suggested-value" >
    <xsl:element name="suggested-value" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//mfp:component-family" >
    <xsl:element name="component-family" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//mfp:favorite-property" >
    <xsl:element name="favorite-property" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//mfp:renderer-type" >
    <xsl:element name="renderer-type" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//mfp:property-values" >
    <xsl:element name="attribute-values" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//mfp:unsupported-agents" >
    <xsl:element name="unsupported-agents" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <!-- Handle metadata we do not know about by letting it through.  Currently,
   just for property-extension and component-metadata, but should be global.
   See JIRA issues ADFFACES-358, ADFFACES-361 and ADFFACES-472 -->
  <xsl:template match="javaee:property-extension/*[namespace-uri() != 'http://java.sun.com/xml/ns/javaee' and namespace-uri() !='http://myfaces.apache.org/maven-faces-plugin']">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
      <xsl:value-of select="text()"/>
    </xsl:copy> 
  </xsl:template>

  <xsl:template match="mfp:component-metadata/*[namespace-uri() != 'http://java.sun.com/xml/ns/javaee' and namespace-uri() !='http://myfaces.apache.org/maven-faces-plugin']">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
      <xsl:value-of select="text()"/>
    </xsl:copy> 
  </xsl:template>


  <xsl:template match="//mfp:component-metadata/mfp:group" >
<!-- Disable component groups for now
    <xsl:element name="group" >
      <xsl:value-of select="text()" />
    </xsl:element>
-->
  </xsl:template>

  <xsl:template match="//mfp:property-metadata/mfp:group" >
    <xsl:element name="group" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//mfp:property-metadata/mfp:translatable" >
    <xsl:element name="translatable" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>
  
  <xsl:template match="//mfp:accepts-child-components" >
    <xsl:element name="accepts-child-components" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>


  <xsl:template match="//mfp:property-editor" >
    <xsl:element name="property-editor" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//mfp:expert" >
    <xsl:element name="expert" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//mfp:preferred-children" >
    <xsl:element name="preferred-children" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//mfp:allowable-children" >
    <xsl:element name="allowable-children" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//mfp:preferred" >
    <xsl:element name="preferred" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//mfp:deprecated" >
    <xsl:element name="deprecated" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

  <xsl:template match="//mfp:initial-value" >
    <xsl:element name="initial-value" >
      <xsl:value-of select="text()" />
    </xsl:element>
  </xsl:template>

</xsl:stylesheet>
