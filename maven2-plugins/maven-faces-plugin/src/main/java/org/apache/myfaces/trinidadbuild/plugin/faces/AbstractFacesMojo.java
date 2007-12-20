/*
* Copyright 2006 The Apache Software Foundation.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.apache.myfaces.trinidadbuild.plugin.faces;

import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.apache.commons.digester.Digester;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.trinidadbuild.plugin.faces.generator.GeneratorHelper;
import org.apache.myfaces.trinidadbuild.plugin.faces.io.PrettyWriter;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.*;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.*;
import org.codehaus.plexus.util.FileUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.*;

abstract public class AbstractFacesMojo extends AbstractMojo
{
  protected List getCompileDependencyResources(
    MavenProject project,
    String       resourcePath) throws MojoExecutionException
  {
    try
    {
      ClassLoader cl = createCompileClassLoader(project);
      Enumeration e = cl.getResources(resourcePath);
      List urls = new ArrayList();
      while (e.hasMoreElements())
      {
		URL url = (URL)e.nextElement();
        urls.add(url);
      }
      return Collections.unmodifiableList(urls);
    }
    catch (IOException e)
    {
      throw new MojoExecutionException("Unable to get resources for path " +
                                       "\"" + resourcePath + "\"", e);
    }

  }

  protected void addResourceRoot(
    MavenProject project,
    String       resourceRoot)
  {
    List resources = project.getBuild().getResources();
    Resource resource = new Resource();
    resource.setDirectory(resourceRoot);
    resources.add(resource);
  }

  /**
   * @deprecated
   */
  protected URL[] readIndex(
    MavenProject project,
    String       resourcePath) throws MojoExecutionException
  {
    return readIndex(project);
  }

  protected List getMasterConfigs(
    MavenProject project) throws MojoExecutionException
  {
    if (localResource != null)
    {
      List urls = new ArrayList();
      try {
        urls.add(localResource.toURL());
      } catch (MalformedURLException e) {
        getLog().error("", e);
      }
      return urls;  
    }
    else
    {
      String resourcePath = "META-INF/maven-faces-plugin/faces-config.xml";
      return getCompileDependencyResources(project, resourcePath);
    }
  }

  protected URL[] readIndex(
    MavenProject project) throws MojoExecutionException
  {
    try
    {
      // 1. read master faces-config.xml resources
      List masters = getMasterConfigs(project);
      if (masters.isEmpty())
      {
        getLog().warn("Master faces-config.xml not found");
        return new URL[0];
      }
      else
      {
        List entries = new LinkedList();

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        // requires JAXP 1.3, in JavaSE 5.0
        // spf.setXIncludeAware(false);

        for (Iterator i=masters.iterator(); i.hasNext();)
        {
          URL url = (URL)i.next();
          Digester digester = new Digester(spf.newSAXParser());
          digester.setNamespaceAware(true);

          // XInclude
          digester.setRuleNamespaceURI(XIncludeFilter.XINCLUDE_NAMESPACE);
          digester.addCallMethod("faces-config/include", "add", 1);
          digester.addFactoryCreate("faces-config/include",
                                    URLCreationFactory.class);
          digester.addCallParam("faces-config/include", 0, 0);

          digester.push(url);
          digester.push(entries);
          digester.parse(url.openStream());
        }

        return (URL[])entries.toArray(new URL[0]);
      }
    }
    catch (ParserConfigurationException e)
    {
      throw new MojoExecutionException("Failed to parse master config", e);
    }
    catch (SAXException e)
    {
      throw new MojoExecutionException("Failed to parse master config", e);
    }
    catch (IOException e)
    {
      throw new MojoExecutionException("Failed to parse master config", e);
    }
  }

  protected boolean isModifiedSince(
    URL[] urls,
    long  lastModified) throws IOException
  {
    for (int i=0; i < urls.length; i++)
    {
      URLConnection conn = urls[i].openConnection();
      if (conn.getLastModified() > lastModified)
        return true;
    }

    return false;
  }

  /**
  * @deprecated call Util.convertStringToLiteral instead
  */
  protected String convertStringToLiteral(String value)
  {
    return Util.convertStringToLiteral("String", value);
  }

 /**
  * @deprecated call Util.convertStringToLiteral instead
  */
  protected String convertStringToLiteral(String className, String value)
  {
    return Util.convertStringToLiteral(className, value);
  }

  protected String readLicenseHeader() throws MojoExecutionException
  {
    if (licenseHeaderFile == null)
    {
      return _DEFAULT_LICENSE_HEADER;
    }
    
    if (!licenseHeaderFile.exists())
    {
      throw new MojoExecutionException("License header file not found: "
                                       +licenseHeaderFile.getName());
    }

    if (licenseHeaderFile.isDirectory())
    {
       throw new MojoExecutionException("Expecting a file and found a directory: "
               +licenseHeaderFile.getName());
    }

    StringBuffer sb = new StringBuffer();
    
    try
    {
      BufferedReader reader = new BufferedReader(new FileReader(licenseHeaderFile));
      String line;
      
      while ((line = reader.readLine()) != null)
      {
        sb.append(line+"\n");
      }
    }
    catch (IOException e)
    {
      throw new MojoExecutionException("Exception reading license header file", e);
    }
    
    return sb.toString();
  }
  
  protected String getLicenseHeader() throws MojoExecutionException
  {
    if (_licenseHeader == null)
    {
      _licenseHeader = readLicenseHeader();
    }
    
    return _licenseHeader;
  }

  static public class URLCreationFactory extends AbstractObjectCreationFactory
  {
    public Object createObject(
      Attributes attributes) throws MalformedURLException
    {
      String href = attributes.getValue("href");
      if (href == null)
        throw new IllegalStateException("Missing href attribute");

      URL master = (URL)digester.getRoot();
      return new URL(master, href);
    }
  }

  protected void processIndex(
    MavenProject project,
    String       resourcePath) throws MojoExecutionException
  {
    _facesConfig = new FacesConfigBean();

    URL[] index = readIndex(project, resourcePath);
    for (int i=0; i < index.length; i++)
    {
      processIndexEntry(index[i]);
    }
  }

  protected void processIndexEntry(
    URL entry) throws MojoExecutionException
  {
    URL old = _facesConfig.setCurrentResource(entry);
    try
    {
      new FacesConfigParser().merge(_facesConfig, entry);
    }
    finally
    {
      _facesConfig.setCurrentResource(old);
    }
  }

  protected FacesConfigBean getFacesConfig()
  {
    return _facesConfig;
  }

  protected void writePreamble(
    PrettyWriter out) throws MojoExecutionException
  {
    out.write(_AUTO_GENERATE_WARNING);
    out.write(getLicenseHeader());
  }

  protected void copyFile(
    File   sourceDirectory,
    String sourcePath,
    File   targetDirectory) throws MojoExecutionException
  {
    try
    {
      File sourceFile = new File(sourceDirectory, sourcePath);
      File targetFile = new File(targetDirectory, sourcePath);

      if (sourceFile.exists() &&
          sourceFile.lastModified() > targetFile.lastModified())
      {
        if (getLog().isDebugEnabled())
          getLog().debug("Copying file \"" + sourcePath + "\"");

        FileUtils.copyFile(sourceFile, targetFile);
      }
    }
    catch (IOException e)
    {
      throw new MojoExecutionException("Error copying file \"" +
                                       sourcePath + "\"", e);
    }
  }

 /**
  * @deprecated use Util.writeImports instead
  */
  protected void writeImports(
    PrettyWriter out,
    String       packageName,
    Set          imports)
  {
    GeneratorHelper.writeImports(out,packageName,imports);
  }

  private ClassLoader createCompileClassLoader(
    MavenProject project) throws MojoExecutionException
  {
    Thread current = Thread.currentThread();
    ClassLoader cl = current.getContextClassLoader();

    try
    {
      List classpathElements = project.getCompileClasspathElements();
      if (!classpathElements.isEmpty())
      {
        String[] entries = (String[])classpathElements.toArray(new String[0]);
        URL[] urls = new URL[entries.length];
        for (int i=0; i < urls.length; i++)
        {
          urls[i] = new File(entries[i]).toURL();
        }
        cl = new URLClassLoader(urls, cl);
      }
    }
    catch (DependencyResolutionRequiredException e)
    {
      throw new MojoExecutionException("Error calculating scope classpath", e);
    }
    catch (MalformedURLException e)
    {
      throw new MojoExecutionException("Error calculating scope classpath", e);
    }

    return cl;
  }

  protected class SkipFilter extends ComponentFilter
  {
    protected boolean accept(
      ComponentBean component)
    {
      String componentType = component.getComponentType();

      // skip API and base class generation?
      return !skipApiOrBaseClasses || (!componentType.startsWith("javax") &&
                !componentType.endsWith("Base"));

    }
  }

  static final protected class ComponentTypeFilter extends ComponentFilter
  {
    public ComponentTypeFilter(
      String typePrefix)
    {
      _typePrefix = typePrefix;
    }

    protected boolean accept(
      ComponentBean component)
    {
      String componentType = component.getComponentType();        
      return (componentType.startsWith(_typePrefix));
    }
     private final String _typePrefix;
  }

  static final protected class ComponentClassFilter extends ComponentFilter
  {
    public ComponentClassFilter(
      String packageContains)
    {
      _packageContains = packageContains;
    }

    protected boolean accept(
      ComponentBean component)
    {
      String componentClass = component.getComponentClass();
      String packageName = Util.getPackageFromFullClass(componentClass);

      // accept only classes that match the package
      return (packageName.contains(_packageContains));
    }

    private final String _packageContains;
  }

  static final protected class ComponentTagClassFilter extends ComponentFilter
  {
    public ComponentTagClassFilter(
      String packageContains)
    {
      _packageContains = packageContains;
    }

    protected boolean accept(
      ComponentBean component)
    {
      String tagClass = component.getTagClass();
      String packageName = Util.getPackageFromFullClass(tagClass);

      // accept only classes in this project's (sub)package
      return (packageName.contains(_packageContains));
    }

    private final String _packageContains;
  }

  static final protected class ConverterTagClassFilter extends ConverterFilter
  {
    public ConverterTagClassFilter(
      String packageContains)
    {
      _packageContains = packageContains;
    }

    protected boolean accept(
      ConverterBean converter)
    {
      String tagClass = converter.getTagClass();
      String packageName = Util.getPackageFromFullClass(tagClass);

      // accept only classes that contain the desired string
      return (packageName.contains(_packageContains));
    }

    private final String _packageContains;
  }

  static final protected class ValidatorTagClassFilter extends ValidatorFilter
  {
    public ValidatorTagClassFilter(
      String packageContains)
    {
      _packageContains = packageContains;
    }

    protected boolean accept(
      ValidatorBean validator)
    {
      String tagClass = validator.getTagClass();
      String packageName = Util.getPackageFromFullClass(tagClass);

      // accept only classes that contain the desired string
      return (packageName.contains(_packageContains));
    }

    private final String _packageContains;
  }

  

  static final protected class ComponentTagFilter extends ComponentFilter
  {
    protected boolean accept(
      ComponentBean component)
    {
      return (component.getTagClass() != null);
    }
  }

  static final protected class ConverterTagFilter extends ConverterFilter
  {
    protected boolean accept(
      ConverterBean converter)
    {
      return (converter.getTagClass() != null);
    }
  }

  static final protected class ValidatorTagFilter extends ValidatorFilter
  {
    protected boolean accept(
      ValidatorBean validator)
    {
      return (validator.getTagClass() != null);
    }
  }

  static final protected class ComponentTagLibraryFilter extends ComponentFilter
  {
    public ComponentTagLibraryFilter(
      String namespaceURI)
    {
      this(namespaceURI, true);
    }

    public ComponentTagLibraryFilter(
      String namespaceURI,
      boolean requireTagClass)
    {
      _namespaceURI = namespaceURI;
      _requireTagClass = requireTagClass;
    }

    protected boolean accept(
      ComponentBean component)
    {
      QName tagName = component.getTagName();
      String tagClass = component.getTagClass();

      // accept if tagClass is present
      // and if tagName is in the desired namespaceURI
      return ((!_requireTagClass || (tagClass != null)) &&
              tagName != null &&
              _namespaceURI.equals(tagName.getNamespaceURI()));
    }

    private final String _namespaceURI;
    private final boolean _requireTagClass;
  }

  static final protected class ValidatorTagLibraryFilter extends ValidatorFilter
  {
    public ValidatorTagLibraryFilter(
      String namespaceURI)
    {
      this(namespaceURI, true);
    }

    public ValidatorTagLibraryFilter(
      String namespaceURI,
      boolean requireTagClass)
    {
      _namespaceURI = namespaceURI;
      _requireTagClass = requireTagClass;
    }

    protected boolean accept(
      ValidatorBean validator)
    {
      QName tagName = validator.getTagName();
      String tagClass = validator.getTagClass();

      // accept if tagClass is present
      // and if tagName is in the desired namespaceURI
      return ((!_requireTagClass || (tagClass != null)) &&
              tagName != null &&
              _namespaceURI.equals(tagName.getNamespaceURI()));
    }

    private final String _namespaceURI;
    private final boolean _requireTagClass;
  }

  static final protected class ConverterTagLibraryFilter extends ConverterFilter
  {
    public ConverterTagLibraryFilter(
      String namespaceURI)
    {
      this(namespaceURI, true);
    }

    public ConverterTagLibraryFilter(
      String namespaceURI,
      boolean requireTagClass)
    {
      _namespaceURI = namespaceURI;
      _requireTagClass = requireTagClass;
    }

    protected boolean accept(
      ConverterBean converter)
    {
      QName tagName = converter.getTagName();
      String tagClass = converter.getTagClass();

      // accept if tagClass is present
      // and if tagName is in the desired namespaceURI
      return ((!_requireTagClass || (tagClass != null)) &&
              tagName != null &&
              _namespaceURI.equals(tagName.getNamespaceURI()));
    }

    private final String _namespaceURI;
    private final boolean _requireTagClass;
  }

  static protected class VirtualAttributeFilter extends AttributeFilter
  {
    protected boolean accept(
      AttributeBean attribute)
    {
      return !attribute.isVirtual();
    }
  }

 /**
  * @parameter
  */
  private File licenseHeaderFile;

  /**
  * @parameter default-value = "true"
  */
  private boolean skipApiOrBaseClasses;

  /**
   * @parameter
   */
  private File localResource;



  private FacesConfigBean _facesConfig;
  private String _licenseHeader;

  static final private String _AUTO_GENERATE_WARNING =
"// WARNING: This file was automatically generated. Do not edit it directly,\n"+
"//          or you will lose your changes.\n\n";


  static final private String _DEFAULT_LICENSE_HEADER =
    "/*\n" +
    " * Licensed to the Apache Software Foundation (ASF) under one\n" +
    " * or more contributor license agreements.  See the NOTICE file\n" +
    " * distributed with this work for additional information\n" +
    " * regarding copyright ownership.  The ASF licenses this file\n" +
    " * to you under the Apache License, Version 2.0 (the\n" +
    " * \"License\"); you may not use this file except in compliance\n" +
    " * with the License.  You may obtain a copy of the License at\n" +
    " *\n" +
    " *   http://www.apache.org/licenses/LICENSE-2.0\n" +
    " *\n" +
    " * Unless required by applicable law or agreed to in writing,\n" +
    " * software distributed under the License is distributed on an\n" +
    " * \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY\n" +
    " * KIND, either express or implied.  See the License for the\n" +
    " * specific language governing permissions and limitations\n" +
    " * under the License.\n" +
    "*/\n";
}
  
