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
package org.apache.myfaces.buildtools.maven2.plugin.builder.qdox;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.buildtools.maven2.plugin.builder.ModelParams;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.ComponentMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.FacetMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.MethodSignatureMeta;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.PropertyMeta;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public class CompositeComponentModelBuilder
{
    private final Log log = LogFactory.getLog(CompositeComponentModelBuilder.class);
    public static final String COMPOSITE_LIBRARY_NAME = "http://java.sun.com/jsf/composite";
    
    public static final String INTERFACE_NAME = "interface";
    public static final String ATTRIBUTE_NAME = "attribute";
    public static final String FACET_NAME = "facet";

    private static class CompositeComponentMetadataHandler extends
            DefaultHandler implements LexicalHandler
    {
        private final Model model;
        
        private final String alias;
        
        private final String componentName;
        
        private final String libraryName;
        
        private final String resourceName;

        private boolean inDocument = false;

        private Locator locator;

        private boolean inCompositeInterface = false;
        
        private ComponentMeta component = null;

        public CompositeComponentMetadataHandler(Model model, String alias, String componentName,
                String libraryName, String resourceName)
        {
            this.model = model;
            this.alias = alias;
            this.componentName = componentName;
            this.libraryName = libraryName;
            this.resourceName = resourceName;
        }

        public void characters(char[] ch, int start, int length) throws SAXException
        {
            if (this.inDocument && inCompositeInterface)
            {
            }
        }

        public void comment(char[] ch, int start, int length) throws SAXException
        {
            if (this.inDocument && inCompositeInterface)
            {
            }
        }

        protected _TagAttributes createAttributes(Attributes attrs)
        {
            int len = attrs.getLength();
            _TagAttribute[] ta = new _TagAttribute[len];
            for (int i = 0; i < len; i++)
            {
                ta[i] = new _TagAttribute(this.createLocation(), attrs.getURI(i), attrs.getLocalName(i), attrs
                        .getQName(i), attrs.getValue(i));
            }
            return new _TagAttributes(ta);
        }

        protected _Location createLocation()
        {
            return new _Location(this.alias, this.locator.getLineNumber(), this.locator.getColumnNumber());
        }

        public void endCDATA() throws SAXException
        {
            if (this.inDocument && inCompositeInterface)
            {
            }
        }

        public void endDocument() throws SAXException
        {
            super.endDocument();
        }

        public void endDTD() throws SAXException
        {
            this.inDocument = true;
        }

        public void endElement(String uri, String localName, String qName) throws SAXException
        {
            if (COMPOSITE_LIBRARY_NAME.equals(uri))
            {
                if (INTERFACE_NAME.equals(localName))
                {
                    this.inCompositeInterface=false;
                    this.component = null;
                }
            }
        }

        public void endEntity(String name) throws SAXException
        {
        }

        public void endPrefixMapping(String prefix) throws SAXException
        {
        }

        public void fatalError(SAXParseException e) throws SAXException
        {
            if (this.locator != null)
            {
                throw new SAXException("Error Traced[line: " + this.locator.getLineNumber() + "] " + e.getMessage());
            }
            else
            {
                throw e;
            }
        }

        public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException
        {
            if (this.inDocument && inCompositeInterface)
            {
            }
        }

        public InputSource resolveEntity(String publicId, String systemId) throws SAXException
        {
            String dtd = "org/apache/myfaces/buildtools/maven2/plugin/builder/qdox/default.dtd";
            /*
             * if ("-//W3C//DTD XHTML 1.0 Transitional//EN".equals(publicId)) { dtd = "xhtml1-transitional.dtd"; } else
             * if (systemId != null && systemId.startsWith("file:/")) { return new InputSource(systemId); }
             */
            URL url = getResource(dtd);
            return new InputSource(url.toString());
        }
        
        public static URL getResource(String resource)
        {
            URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
            if (url == null)
            {
                url = ClassUtils.class.getClassLoader().getResource(resource);
            }
            return url;
        }

        public void setDocumentLocator(Locator locator)
        {
            this.locator = locator;
        }

        public void startCDATA() throws SAXException
        {
            if (this.inDocument && inCompositeInterface)
            {
            }
        }

        public void startDocument() throws SAXException
        {
            this.inDocument = true;
        }

        public void startDTD(String name, String publicId, String systemId) throws SAXException
        {
            // metadata does not require output doctype
            this.inDocument = false;
        }

        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
        {
            if (COMPOSITE_LIBRARY_NAME.equals(uri))
            {
                if (INTERFACE_NAME.equals(localName))
                {
                    this.inCompositeInterface=true;
                    _TagAttributes attrs = this.createAttributes(attributes);
                    
                    this.component = null;
                    //1. Use componentType attribute to find the class in the model.
                    _TagAttribute type = attrs.get("componentType");
                    boolean setComponentType = false;
                    if (type != null)
                    {
                        this.component = model.findComponentByType(type.getValue());                        
                        if (this.component == null)
                        {
                            //Component not found, set component type when created
                            setComponentType = true;
                        }
                    }
                    if (this.component == null)
                    {
                        //2. Use libraryName + '.' + componentName
                        this.component = model.findComponentByClassName(alias);
                    }
                    if (this.component == null)
                    {
                        //No class bound, create
                        this.component = new ComponentMeta();
                        // Set as class name the assigned by Application.createResource, 
                        // even if not exists.
                        this.component.setClassName(alias);
                        if (!setComponentType)
                        {
                            this.component.setParentClassName("javax.faces.component.UINamingContainer");
                            PropertyMeta idProp = new PropertyMeta();
                            idProp.setName("id");
                            idProp.setRequired(false);
                            this.component.addProperty(idProp);
                        }
                        this.component.setModelId(model.getModelId());
                    }
                    //componentType
                    if (setComponentType)
                    {
                        component.setType(type.getValue());
                    }
                    
                    //Mark it as composite
                    component.setComposite(Boolean.TRUE);
                    
                    component.setCcLibraryName(libraryName);
                    
                    component.setCcResourceName(resourceName);
                    
                    //name: Force name to be the one derived by the composite component
                    component.setName(componentName);

                    //shortDescription
                    _TagAttribute shortDescription = attrs.get("shortDescription");
                    if (shortDescription != null)
                    {
                        component.setDescription(shortDescription.getValue());
                    }
                    
                    // Ignored properties:
                    // displayName
                    // preferred
                    // expert
                    // hidden
                    model.addComponent(component);
                }
                if (ATTRIBUTE_NAME.equals(localName))
                {
                    _TagAttributes attrs = this.createAttributes(attributes);
                    //name
                    String name = attrs.get("name").getValue();
                    
                    PropertyMeta property = component.getProperty(name);
                    
                    if (property == null)
                    {
                        property = new PropertyMeta();
                        property.setName(name);
                        component.addProperty(property);
                    }
                    
                    //default
                    _TagAttribute defaultValue = attrs.get("default");
                    if (defaultValue != null)
                    {
                        property.setDefaultValue(defaultValue.getValue());
                    }
                    //required
                    _TagAttribute required = attrs.get("required");
                    if (required != null)
                    {
                        property.setRequired(Boolean.valueOf(required.getValue()));
                    }
                    //shortDescription
                    _TagAttribute shortDescription = attrs.get("shortDescription");
                    if (shortDescription != null)
                    {
                        property.setDescription(shortDescription.getValue());
                    }
                    //type
                    _TagAttribute type = attrs.get("type");
                    if (type != null)
                    {
                        property.setDeferredValueType(type.getValue());
                    }
                    //method-signature
                    _TagAttribute methodSignature = attrs.get("method-signature");
                    if (methodSignature != null)
                    {
                        String signature = methodSignature.getValue();
                        try
                        {
                            MethodSignatureMeta msm = new MethodSignatureMeta();
                            List paramsList = _getParameters(signature);
                            for (int i = 0; i < paramsList.size(); i++)
                            {
                                msm.addParameterType((String)paramsList.get(i));
                            }
                            msm.setReturnType(_getReturnType(signature));
                            property.setMethodBindingSignature(msm);
                        }
                        catch (Exception e)
                        {
                            throw new SAXException(e);
                        }
                    }

                    // Ignored properties:
                    // preferred
                    // expert
                    // hidden
                    // targets
                    // displayName
                }
                if (FACET_NAME.equals(localName))
                {
                    _TagAttributes attrs = this.createAttributes(attributes);
                    
                    String name = attrs.get("name").getValue();
                    
                    FacetMeta facet = component.getFacet(name);
                    
                    if (facet == null)
                    {
                        facet = new FacetMeta();
                        facet.setName(name);
                        component.addFacet(facet);
                    }
                    
                    //required
                    _TagAttribute required = attrs.get("required");
                    if (required != null)
                    {
                        facet.setRequired(Boolean.valueOf(required.getValue()));
                    }
                    
                    //shortDescription
                    _TagAttribute shortDescription = attrs.get("shortDescription");
                    if (shortDescription != null)
                    {
                        facet.setDescription(shortDescription.getValue());
                    }
                    
                    // Ignored properties:
                    // preferred
                    // expert
                    // hidden
                    // displayName
                }
            }
        }

        private String _getReturnType(String signature) throws Exception
        {
            int endName = signature.indexOf('(');
            if (endName < 0)
            {
                throw new Exception("Invalid method signature:" + signature);
            }
            int end = signature.lastIndexOf(' ', endName);
            if (end < 0)
            {
                throw new Exception("Invalid method signature:" + signature);
            }
            return signature.substring(0,end);
        }

        /**
         * Get the parameters types from the function signature.
         * 
         * @return An array of parameter class names
         */
        private List _getParameters(String signature) throws Exception
        {
            ArrayList params = new ArrayList();
            // Signature is of the form
            // <return-type> S <method-name S? '('
            // < <arg-type> ( ',' <arg-type> )* )? ')'
            int start = signature.indexOf('(') + 1;
            boolean lastArg = false;
            while (true)
            {
                int p = signature.indexOf(',', start);
                if (p < 0)
                {
                    p = signature.indexOf(')', start);
                    if (p < 0)
                    {
                        throw new Exception("Invalid method signature:"+signature);
                    }
                    lastArg = true;
                }
                String arg = signature.substring(start, p).trim();
                if (!"".equals(arg))
                {
                    params.add(arg);
                }
                if (lastArg)
                {
                    break;
                }
                start = p + 1;
            }
            return params;
        }
        
        public void startEntity(String name) throws SAXException
        {
        }

        public void startPrefixMapping(String prefix, String uri) throws SAXException
        {
        }

        public void processingInstruction(String target, String data) throws SAXException
        {
            if (this.inDocument && inCompositeInterface)
            {
            }
        }  
    }
    
    public void buildModel(Model model, ModelParams parameters)
    {
        if (parameters.getCompositeComponentDirectories() != null &&
            parameters.getCompositeComponentLibraries() != null &&
            !parameters.getCompositeComponentDirectories().isEmpty() &&
            !parameters.getCompositeComponentLibraries().isEmpty())
        {
            for (Iterator it = parameters.getCompositeComponentDirectories().iterator(); it.hasNext();)
            {
                Object odir = it.next();
                File dir = null;
                if (odir instanceof File)
                {
                    dir = (File) odir;
                }
                else
                {
                    dir = new File(odir.toString());
                }
                if (dir != null && dir.exists())
                {
                    for (Iterator it2 = parameters.getCompositeComponentLibraries().entrySet().iterator(); 
                         it2.hasNext();)
                    {
                        Map.Entry entry = (Map.Entry) it2.next();
                        String shortTagLibraryName = (String) entry.getKey();
                        String libraryName = (String) entry.getValue();
                        File dirToScan = new File(dir,libraryName);
                        if (dirToScan != null && dirToScan.exists() && dirToScan.isDirectory() )
                        {
                             
                             final String[] fileExtensions = parameters.getCompositeComponentFileExtensions() != null ? 
                                     parameters.getCompositeComponentFileExtensions().split(" ") : new String[]{".xhtml"};
                             FileFilter fileFilter = new FileFilter()
                             {
                                 public boolean accept(File file)
                                 {
                                     for (String extension : fileExtensions)
                                     {
                                         if (file.getName().endsWith(extension))
                                         {
                                             return true;
                                         }
                                     }
                                     return false;
                                 }
                             };
                             File[] files = dirToScan.listFiles(fileFilter);
                             if (files != null)
                             {
                                 for (int i = 0; i < files.length; i++)
                                 {
                                     String fileExtension = null;
                                     for (String extension : fileExtensions)
                                     {
                                         if (files[i].getName().endsWith(extension))
                                         {
                                             fileExtension = extension;
                                             break;
                                         }
                                     }
                                     String componentName = files[i].getName().substring(
                                                 0, files[i].getName().length()-fileExtension.length());
                                     try
                                    {
                                        doParseMetadata(model, files[i].toURL(), 
                                                shortTagLibraryName, libraryName, files[i].getName(),
                                                componentName, libraryName+'.'+ componentName);
                                    }
                                    catch (MalformedURLException e)
                                    {
                                        log.error("Error while processing composite component: "+
                                                libraryName+'/'+componentName, e);
                                    }
                                    catch (IOException e)
                                    {
                                        log.error("Error while processing composite component: "+
                                                libraryName+'/'+componentName, e);
                                    }
                                    catch (SAXException e)
                                    {
                                        log.error("Error while processing composite component: "+
                                                libraryName+'/'+componentName, e);
                                    }
                                    catch (ParserConfigurationException e)
                                    {
                                        log.error("Error while processing composite component: "+
                                                libraryName+'/'+componentName, e);
                                    }
                                 }
                             }
                        }
                    }
                }
            }
        }
    }

    protected void doParseMetadata(Model model, 
            URL src, String shortLibraryName, 
            String libraryName, String resourceName, String componentName, String alias) 
    throws IOException, SAXException,
            ParserConfigurationException
    {
        InputStream is = null;
        try
        {
            is = new BufferedInputStream(src.openStream(), 1024);
            CompositeComponentMetadataHandler handler = 
                new CompositeComponentMetadataHandler(model, alias, shortLibraryName + ':' + 
                        componentName, libraryName, resourceName);
            SAXParser parser = this.createSAXParser(handler);
            parser.parse(is, handler);
        }
        finally
        {
            if (is != null)
            {
                is.close();
            }
        }
    }

    private final SAXParser createSAXParser(DefaultHandler handler)
            throws SAXException, ParserConfigurationException
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature("http://xml.org/sax/features/namespace-prefixes",
                true);
        factory.setFeature("http://xml.org/sax/features/validation", false);
        factory.setValidating(false);
        SAXParser parser = factory.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        reader.setProperty("http://xml.org/sax/properties/lexical-handler",
                handler);
        reader.setErrorHandler(handler);
        reader.setEntityResolver(handler);
        return parser;
    }
}
