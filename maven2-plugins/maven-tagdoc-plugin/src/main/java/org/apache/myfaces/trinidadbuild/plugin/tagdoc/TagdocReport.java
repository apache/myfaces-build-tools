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
package org.apache.myfaces.trinidadbuild.plugin.tagdoc;

//import org.apache.maven.doxia.sink.Sink;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.apache.commons.digester.Digester;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenMultiPageReport;
import org.apache.maven.reporting.MavenReportException;
import org.apache.maven.reporting.sink.SinkFactory;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.ComponentBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.ConverterBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.EventBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.EventRefBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.FacesConfigBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.FacesConfigParser;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.FacetBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.PropertyBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.ValidatorBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.ComponentFilter;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.ConverterFilter;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.FilteredIterator;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.PropertyFilter;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.ValidatorFilter;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.XIncludeFilter;
import org.codehaus.doxia.sink.Sink;
import org.codehaus.doxia.site.renderer.SiteRenderer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Report for generating JSF tagdoc based on faces-config.xml parsing.
 * Note that this is not really an AbstractMavenMultiPageReport - the
 * secondary pages are manually generated XDoc.  I tried using the true
 * multipage report approach, and ran into Maven bugs (as of Maven 2.0.2,
 * site 2.0b4).
 *
 * @goal tagdoc
 */
public class TagdocReport extends AbstractMavenMultiPageReport
{
  protected void executeReport( Locale locale )
      throws MavenReportException
  {
    // Why the heck doesn't Maven do this for me?
    SinkFactory factory = new SinkFactory();
    factory.setSiteRenderer(getSiteRenderer());
    factory.setSiteDirectory(getOutputDirectory());
    setSinkFactory(factory);
    
    processIndex(project, resourcePath);
    try
    {
      _generateTagDocs();
    }
    catch (Exception e)
    {
      throw new MavenReportException("Couldn't generate tagdoc", e);
    }
  }

  private void _generateTagDocs() throws Exception
  {
    FacesConfigBean facesConfig = getFacesConfig();
    if (!facesConfig.hasComponents())
    {
      getLog().info("Nothing to generate - no components found");
      return;
    }


    Iterator components = facesConfig.components();
    components = new FilteredIterator(components, new SkipFilter());
    components = new FilteredIterator(components, new ComponentTagFilter());
    components = new FilteredIterator(components, new ComponentNamespaceFilter());
    
    Iterator validators = facesConfig.validators();
    validators = new FilteredIterator(validators, new ValidatorTagFilter());
    validators = new FilteredIterator(validators, new ValidatorNamespaceFilter());
    
    Iterator converters = facesConfig.converters();
    converters = new FilteredIterator(converters, new ConverterTagFilter());
    converters = new FilteredIterator(converters, new ConverterNamespaceFilter());
    
    // =-=AEW Note that only updating out-of-date components, etc. is
    // permanently tricky, even if we had proper detection in place,
    // because the index always has to have all docs
    /*
    if (!components.hasNext() && !converters.hasNext() && !validators.hasNext())
    {
      getLog().info("Nothing to generate - all docs are up to date");
      return;
    }
    */

    Set componentPages = new TreeSet();
    Set converterPages = new TreeSet();
    Set validatorPages = new TreeSet();
    
    int count = 0;
    while (components.hasNext())
    {
      String pageName = _generateComponentDoc((ComponentBean)components.next());
      if (pageName != null)
      {
        componentPages.add(pageName);
        count++;
      }
    }
    while (converters.hasNext())
    {
      String pageName = _generateConverterDoc((ConverterBean)converters.next());
      if (pageName != null)
      {
        converterPages.add(pageName);
        count++;
      }
    }
    while (validators.hasNext())
    {
      String pageName = _generateValidatorDoc((ValidatorBean)validators.next());
      if (pageName != null)
      {
        validatorPages.add(pageName);
        count++;
      }
    }


    Set otherPages = _gatherOtherTags();

    getLog().info("Generated " + count + " page(s)");

    Sink sink = getSink();
    sink.head();
    sink.title();
    sink.text("Tag library documentation");
    sink.title_();    
    sink.head_();
    sink.body();

    sink.sectionTitle1();
    sink.text("Tag library information");
    sink.sectionTitle1_();
    sink.section1();

    for (Iterator i = taglibs.entrySet().iterator(); i.hasNext(); )
    {
      Map.Entry entry = (Map.Entry)i.next();
      sink.paragraph();

      sink.bold();
      sink.text("Short name:");
      sink.bold_();
      sink.nonBreakingSpace();
      sink.text(entry.getKey().toString());
      sink.lineBreak();

      sink.bold();
      sink.text("Namespace:");
      sink.bold_();
      sink.nonBreakingSpace();
      sink.text(entry.getValue().toString());
      sink.lineBreak();

      sink.paragraph_();
    }

    sink.section1_();

    _writeIndexSection(sink, componentPages, "Components");
    _writeIndexSection(sink, converterPages, "Converters");
    _writeIndexSection(sink, validatorPages, "Validators");
    _writeIndexSection(sink, otherPages, "Miscellaneous");

    sink.body_();
  }

  private Set _gatherOtherTags()
  {
    TreeSet set = new TreeSet();
    String subDir = 
      _platformAgnosticPath(_platformAgnosticPath("xdoc/" + 
                                                  _DOC_SUBDIRECTORY));
    File siteSubDir = new File(siteDirectory, subDir);
    if (siteSubDir.exists())
    {
      String[] files = siteSubDir.list();
      for (int i = 0; i < files.length; i++)
      {
        String file = files[i];
        if (file.endsWith(".xml"))
        {
          set.add(file.substring(0, file.length() - 4));
        }
      }
    }

    return set;
  }

  private String _formatPropList(
    String[] pList, 
    String   header)
  {
    String[] nullList = {};
    return _formatPropList(pList, header, nullList);
  }

  private String _formatPropList(
    String[] pList, 
    String   header,
    String[] ignores)
  {
    String formatted = null;
    if ((pList != null) && (pList.length > 0))
    {
      // Don't know how long this will be, but 100 should be plenty.
      StringBuffer sb = new StringBuffer(100);
      sb.append("\n");
      sb.append("<b>");
      sb.append(header);
      sb.append(":</b> ");

      boolean gotOne = false;

      for (int arrInd = 0; arrInd < pList.length; arrInd++)
      {
        String curStr = pList[arrInd];
        outer:
        if (curStr != null)
        {
          for (int i = 0; i < ignores.length; i++)
          {
            String s = ignores[i];
            if ((s != null) && (s.equalsIgnoreCase(curStr)))
              break outer;
          }

          if (gotOne)
          {
            sb.append(", ");
          }
          gotOne = true;
          sb.append(curStr);
        }
      }
      if (gotOne)
      {
        sb.append("<br/>\n");
        formatted = sb.toString();
      }
    }
    return formatted;
  }

  private void _writeIndexSection(Sink sink, Set pages, String title)
  {
    if (pages.isEmpty())
      return;

    sink.sectionTitle1();
    sink.text(title);
    sink.sectionTitle1_();
    sink.section1();
    sink.table();
    sink.tableRow();
    sink.tableHeaderCell();
    sink.text("Tag Name");
    sink.tableHeaderCell_();
    sink.tableRow_();
    
    Iterator iter = pages.iterator();
    while (iter.hasNext())
    {
      sink.tableRow();
      sink.tableCell();
      
      String name = (String) iter.next();
      String tagName = "<" + name.replace('_', ':') + ">";
      
      sink.link(_DOC_SUBDIRECTORY + "/" + name + ".html");
      sink.text(tagName);
      sink.link_();
      
      sink.tableCell_();
      sink.tableRow_();
    }
    
    sink.table_();
    sink.section1_();
  }

  public boolean usePageLinkBar()
  {
    return false;
  }

  private String _toPageName(QName qName)
  {
    return _getPrefix(qName) + "_" + qName.getLocalPart();
  }
  
  private String _getQualifiedName(QName qName)
  {
    return _getPrefix(qName) + ":" + qName.getLocalPart();
  }

  private String _getPrefix(QName qName)
  {
    if ((qName.getPrefix() != null) && !"".equals(qName.getPrefix()))
      return qName.getPrefix();
    
    String namespace = qName.getNamespaceURI();
    if (namespace == null)
      return null;

    for (Iterator i = taglibs.entrySet().iterator(); i.hasNext(); )
    {
      Map.Entry entry = (Map.Entry)i.next();
      if (namespace.equals(entry.getValue()))
        return (String) entry.getKey();
    }

    return "unknown";
  }

  private String _generateComponentDoc(ComponentBean component)
    throws Exception
  {
    if (component.getTagName() == null)
    {
      return null;
    }
    String pageName = _toPageName(component.getTagName());

    File targetDir = new File(outputDirectory.getParentFile(), 
                              _platformAgnosticPath("generated-site/xdoc/" + 
                                                      _DOC_SUBDIRECTORY));
    targetDir.mkdirs();
    File targetFile = new File(targetDir, pageName + ".xml");

    Writer out = new OutputStreamWriter(new FileOutputStream(targetFile),
                                        "UTF-8");
    try
    {
      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
      out.write("<document>\n");
      out.write(" <properties>\n");
      out.write("  <title>&lt;" + _getQualifiedName(component.getTagName()) + "&gt;</title>\n");
      out.write(" </properties>\n");
      out.write(" <body>\n");

      out.write(" <section name=\"Summary\">\n");
      out.write(" <p>\n");
      _writeComponentSummary(out, component);
      out.write(" </p>\n");
      out.write(" </section>\n");
      
      if (component.hasEvents(true))
      {
        out.write(" <section name=\"Events\">\n");
        out.write(" <p>\n");
        _writeComponentEvents(out, component);
        out.write(" </p>\n");
        out.write(" </section>\n");
      }
      
      if (component.hasFacets(true))
      {
        out.write(" <section name=\"Supported Facets\">\n");
        out.write(" <p>\n");
        _writeComponentFacets(out, component);
        out.write(" </p>\n");
        out.write(" </section>\n");
      }
      
      out.write(" <section name=\"Attributes\">\n");
      _writeComponentAttributes(out, component);
      out.write(" </section>\n");

      out.write(" </body>\n");
      out.write("</document>\n");
    }
    finally
    {
      out.close();
    }

    return pageName;
  }
  
  private String _generateConverterDoc(ConverterBean converter) throws IOException
  {
    if (converter.getTagName() == null)
    {
      return null;
    }

    String pageName = _toPageName(converter.getTagName());

    File targetDir = new File(outputDirectory.getParentFile(), 
                              _platformAgnosticPath("generated-site/xdoc/" + 
                                                     _DOC_SUBDIRECTORY));
    targetDir.mkdirs();
    File targetFile = new File(targetDir, pageName + ".xml");

    Writer out = new OutputStreamWriter(new FileOutputStream(targetFile),
                                        "UTF-8");
    try
    {
      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
      out.write("<document>\n");
      out.write(" <properties>\n");
      out.write("  <title>&lt;" + _getQualifiedName(converter.getTagName()) + "&gt;</title>\n");
      out.write(" </properties>\n");
      out.write(" <body>\n");

      out.write(" <section name=\"Summary\">\n");
      out.write(" <p>\n");
      _writeConverterSummary(out, converter);
      out.write(" </p>\n");
      out.write(" </section>\n");
            
      out.write(" <section name=\"Attributes\">\n");
      _writeConverterAttributes(out, converter);
      out.write(" </section>\n");

      out.write(" </body>\n");
      out.write("</document>\n");
    }
    finally
    {
      out.close();
    }

    return pageName;
  }

  private String _generateValidatorDoc(ValidatorBean validator) throws IOException
  {
    if (validator.getTagName() == null)
    {
      return null;
    }

    String pageName = _toPageName(validator.getTagName());

    File targetDir = new File(outputDirectory.getParentFile(), 
                              _platformAgnosticPath("generated-site/xdoc/" + 
                                                      _DOC_SUBDIRECTORY));
    targetDir.mkdirs();
    File targetFile = new File(targetDir, pageName + ".xml");

    Writer out = new OutputStreamWriter(new FileOutputStream(targetFile),
                                        "UTF-8");
    try
    {
      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
      out.write("<document>\n");
      out.write(" <properties>\n");
      out.write("  <title>&lt;" + _getQualifiedName(validator.getTagName()) + "&gt;</title>\n");
      out.write(" </properties>\n");
      out.write(" <body>\n");

      out.write(" <section name=\"Summary\">\n");
      out.write(" <p>\n");
      _writeValidatorSummary(out, validator);
      out.write(" </p>\n");
      out.write(" </section>\n");
            
      out.write(" <section name=\"Attributes\">\n");
      _writeValidatorAttributes(out, validator);
      out.write(" </section>\n");

      out.write(" </body>\n");
      out.write("</document>\n");
    }
    finally
    {
      out.close();
    }

    return pageName;
  }


  private void _writeComponentSummary(Writer out, ComponentBean bean) throws IOException
  {
    out.write("   <b>Tag name:</b> &lt;" +
              _getQualifiedName(bean.getTagName()) + "&gt;\n");
    out.write("   <br/>\n");
    

    out.write("   <b>UIComponent class:</b> ");
    String javadocURL = _platformAgnosticPath("../apidocs/" +
      bean.getComponentClass().replace('.', '/') + ".html");
    out.write("<a href=\"" + javadocURL + "\">");

    out.write(bean.getComponentClass());
    out.write("</a>");
    out.write("\n");
    out.write("   <br/>\n");
    out.write("   <b>Component type:</b> " + bean.getComponentType() +  "\n");
    out.write("   <br/>\n");

    if (_isNamingContainer(bean))
    {
      out.write("   <p><b>Naming container:</b>  Yes.  When referring to children of this " +
                "component (\"partialTriggers\", <code>findComponent()</code>, etc.), " +
                "you must prefix the child's ID with this component's ID and a colon (':').</p>");
    }
      
    String fmtd = _formatPropList(bean.getUnsupportedAgents(),
                                  "Unsupported agents",
                                  _NON_DOCUMENTED_AGENTS);
    if (fmtd != null)
    {
      out.write("   " + fmtd);
    }

    String doc = bean.getLongDescription();
    if (doc == null)
      doc = bean.getDescription();

    out.write(_preToSource(doc));
    out.write("\n");
  }


  private boolean _isNamingContainer(ComponentBean bean)
  {
    if (bean.isNamingContainer())
      return true;

    ComponentBean parent = bean.resolveSupertype();
    if (parent == null)
      return false;
    return _isNamingContainer(parent);
  }

  private void _writeValidatorSummary(Writer out, ValidatorBean bean) throws IOException
  {
    out.write("   <b>Tag name:</b> &lt;" +
              _getQualifiedName(bean.getTagName()) + "&gt;\n");
    out.write("   <br/>\n");
    
    out.write("   <br/>\n");
    out.write("   <b> type:</b> " + bean.getValidatorId() +  "\n");
    out.write("   <br/>\n");

    String doc = bean.getLongDescription();
    if (doc == null)
      doc = bean.getDescription();

    out.write(_preToSource(doc));
    out.write("\n");
  }


  private void _writeConverterSummary(Writer out, ConverterBean bean) throws IOException
  {
    out.write("   <b>Tag name:</b> &lt;" +
              _getQualifiedName(bean.getTagName()) + "&gt;\n");
    out.write("   <br/>\n");
    
    out.write("   <br/>\n");
    out.write("   <b> type:</b> " + bean.getConverterId() +  "\n");
    out.write("   <br/>\n");

    String doc = bean.getLongDescription();
    if (doc == null)
      doc = bean.getDescription();

    out.write(_preToSource(doc));
    out.write("\n");
  }

  static private final String _preToSource(String in)
  {
    in = in.replaceAll("<pre>", "<source><![CDATA[");
    in = in.replaceAll("</pre>", "]]></source>");
    in = in.replaceAll("<html:", "<");
    in = in.replaceAll("</html:", "</");

    return in;
  }
  
  static private final String _platformAgnosticPath(String path) {
      return path.replace('/', File.separatorChar);
  }


  private class GroupComparator implements Comparator
  {
    public int compare(Object o1, Object o2)
    {
      return _getGroupIndex(o1) - _getGroupIndex(o2);
    }

    public boolean equals(Object o)
    {
      return (o instanceof GroupComparator);
    }

    private int _getGroupIndex(Object o)
    {
      String s = (o == null) ? null : o.toString();




      if ("message".equalsIgnoreCase(s))
      {
        return 0;
      }

      if ("core".equalsIgnoreCase(s))
      {
        return 1;
      }
      if ("events".equalsIgnoreCase(s))
      {
        return 2;
      }

      if (s != null)
        getLog().warn("UNKNOWN ATTRIBUTE GROUP: " + s);

      return 3;
    }
  }


  private void _writeComponentAttributes(Writer out, ComponentBean bean) throws IOException
  {
    // Sort the names
    TreeSet attributes = new TreeSet();
    Iterator attrs = bean.properties(true);
    while (attrs.hasNext())
    {
      PropertyBean property = (PropertyBean) attrs.next();
      if (!property.isTagAttributeExcluded())
        attributes.add(property.getPropertyName());
    }

    // Now get a list of PropertyBeans
    List list = new ArrayList();
    Iterator iter = attributes.iterator();
    while (iter.hasNext())
    {
      String attrName = (String) iter.next();
      list.add(bean.findProperty(attrName, true));
    }
    

    TreeSet groups = new TreeSet(new GroupComparator());
    /* No current support for grouping
    // Make sure "null" is the representative for unknown groups
    Iterator iter = attributes.iterator();
    while (iter.hasNext())
    {
      String group = ((AttributeDoc) iter.next()).group;
      if (group != null)
        groups.add(group);
    }
    */

    _writeComponentAttributes(out,
                              list.iterator(),
                              bean.getComponentClass(),
                              groups.isEmpty() ? null : "Ungrouped");

    Iterator groupIter = groups.iterator();
    while (groupIter.hasNext())
    {
      _writeComponentAttributes(out,
                                list.iterator(),
                                bean.getComponentClass(),
                                (String) groupIter.next());
    }

  }


  private void _writeConverterAttributes(Writer out, ConverterBean bean) throws IOException
  {
    // Sort the names
    TreeSet attributes = new TreeSet();
    Iterator attrs = bean.properties();
    while (attrs.hasNext())
    {
      PropertyBean property = (PropertyBean) attrs.next();
      if (!property.isTagAttributeExcluded())
        attributes.add(property.getPropertyName());
    }

    // Now get a list of PropertyBeans
    List list = new ArrayList();
    Iterator iter = attributes.iterator();
    while (iter.hasNext())
    {
      String attrName = (String) iter.next();
      list.add(bean.findProperty(attrName));
    }
    
    _writeComponentAttributes(out,
                              list.iterator(),
                              bean.getConverterClass(),
                              null);
  }



  private void _writeValidatorAttributes(Writer out, ValidatorBean bean) throws IOException
  {
    // Sort the names
    TreeSet attributes = new TreeSet();
    Iterator attrs = bean.properties();
    while (attrs.hasNext())
    {
      PropertyBean property = (PropertyBean) attrs.next();
      if (!property.isTagAttributeExcluded())
        attributes.add(property.getPropertyName());
    }

    // Now get a list of PropertyBeans
    List list = new ArrayList();
    Iterator iter = attributes.iterator();
    while (iter.hasNext())
    {
      String attrName = (String) iter.next();
      list.add(bean.findProperty(attrName));
    }
    
    _writeComponentAttributes(out,
                              list.iterator(),
                              bean.getValidatorClass(),
                              null);
  }



  private void _writeComponentAttributes(Writer out,
                                         Iterator attributes,
                                         String className,
                                         String group) throws IOException
  {
    boolean writtenAnyAttributes = false;

    while (attributes.hasNext())
    {
      PropertyBean attr = (PropertyBean) attributes.next();

      /*
      if ((group == null) || "Ungrouped".equals(group))
      {
        if (doc.group != null)
          continue;
      }
      else
      {
        if (!group.equalsIgnoreCase(doc.group))
          continue;
      }
      */

      if (!writtenAnyAttributes)
      {
        writtenAnyAttributes = true;
        if (group != null)
        {
          String sectionName;
          if ("events".equalsIgnoreCase(group))
            sectionName = "Javascript attributes";
          else
            sectionName = group + " attributes";

          out.write("<subsection name=\"" + sectionName + "\">\n");
        }

        out.write("<table>\n");
        out.write("<tr>\n");
        out.write("<th>Name</th>\n");
        out.write("<th>Type</th>\n");
        out.write("<th>Supports EL?</th>\n");
        if (!_attrDocSpansColumns)
          out.write("<th>Description</th>\n");
        out.write("</tr>\n");
      }

      String propertyName = attr.getPropertyName();
      // Quick fix of problems with actionExpression vs. action
      // actionExpression is the MethodExpression on the component,
      // but on the tag it's 'action'
      if ("actionExpression".equals(propertyName))
        propertyName = "action";

      out.write("<tr>\n");
      out.write("<td>" + propertyName + "</td>");
      String type = _getDisplayType(className,
                                    propertyName,
                                    attr.getPropertyClass());

      out.write("<td>" + type + "</td>");

      String elSupported;
      // MethodBindings, "binding", and some other attributes
      // require EL support
      if (attr.isMethodBinding() || 
          attr.isMethodExpression() ||
          "binding".equals(propertyName))
      {
        // "action" doesn't require EL; all else do.
        elSupported = "action".equals(propertyName) ? "Yes" : "Only EL";
      }
      else
      {
        elSupported = attr.isLiteralOnly() ? "No" : "Yes";
      }

      out.write("<td>" + elSupported + "</td>");

      if (attr.getDescription()  != null)
      {
        String valStr = _formatPropList(attr.getPropertyValues(),
                                        "Valid Values");
        String unsupAgentsStr =
          _formatPropList(attr.getUnsupportedAgents(),
                          "Not supported on the following agents",
                          _NON_DOCUMENTED_AGENTS);
        String unsupRkStr =
          _formatPropList(attr.getUnsupportedRenderKits(),
                          "Not supported on the following renderkits");

        if (_attrDocSpansColumns)
        {
          out.write("</tr>\n");
          out.write("<tr>\n");
          out.write("<td colspan=\"3\">\n");
        }
        else
        {
          out.write("<td>\n");
        }
        
        //        out.write(EscapeUtils.escapeElementValue(doc.doc));
        if (valStr != null)
        {
          out.write(valStr);
          out.write("<br/>");
        }
        out.write(attr.getDescription());
        if (unsupAgentsStr != null)
        {
          out.write("<br/>");
          out.write(unsupAgentsStr);
        }
        if (unsupRkStr != null)
        {
          out.write("<br/>");
          out.write(unsupRkStr);
        }
        //out.write(EscapeUtils.escapeAmpersands(doc.doc));
        out.write("</td>\n");
      }

      out.write("</tr>\n");
    }

    if (writtenAnyAttributes)
    {
      out.write("</table>\n");
      if (group != null)
        out.write("</subsection>\n");
    }
  }

  static private String _getDisplayType(String className, String name, String type)
  {
    if (type.startsWith("java.lang."))
    {
      return type.substring("java.lang.".length());
    }
    else if ("binding".equals(name))
    {
      StringTokenizer tokens = new StringTokenizer(className, ".", true);
      String out = "";
      while (tokens.hasMoreTokens())
      {
        String token = tokens.nextToken();
        out = out + token;
        // Give ourselves an opportunity for a line break after "component.";
        if (out.endsWith("component."))
          out = out + "<wbr/>";
      }

      return out;
    }

    return type;
  }

  private void _writeComponentEvents(Writer out, ComponentBean bean) throws IOException
  {
    out.write("<table>\n");
    out.write("<tr>\n");
    out.write("<th>Type</th>\n");
    out.write("<th>Phases</th>\n");
    out.write("<th>Description</th>\n");
    out.write("</tr>\n");

    Iterator iter = bean.events(true);
    while (iter.hasNext())
    {
      EventRefBean eventRef = (EventRefBean) iter.next();
      EventBean    event = eventRef.resolveEventType();

      out.write("<tr>\n");
      out.write("<td>" + event.getEventClass() + "</td>");
      out.write("<td nowrap=\"nowrap\">");
      String[] phases = eventRef.getEventDeliveryPhases();
      for (int i = 0; i < phases.length; i++)
      {
        if (i > 0)
          out.write(",<br/>");
        out.write((String) phases[i]);
      }

      out.write("</td>");
      out.write("<td>" + event.getDescription() + "</td>");
      out.write("</tr>\n");
    }

    out.write("</table>\n");
  }





  private void _writeComponentFacets(Writer out, ComponentBean bean) throws IOException
  {
    // Sort the facets
    TreeSet facetNames = new TreeSet();
    Iterator iter = bean.facets(true);
    while (iter.hasNext())
    {
      facetNames.add(((FacetBean) iter.next()).getFacetName());
    }

    out.write("<table>\n");
    out.write("<tr>\n");
    out.write("<th>Name</th>\n");
    out.write("<th>Description</th>\n");
    out.write("</tr>\n");

    Iterator nameIter = facetNames.iterator();
    while (nameIter.hasNext())
    {
      String name = (String) nameIter.next();
      FacetBean facet = bean.findFacet(name, true);
      out.write("<tr>\n");
      out.write("<td>" + facet.getFacetName() + "</td>");
      out.write("<td>");
      out.write(facet.getDescription());
      out.write("</td>\n");
      out.write("</tr>\n");
    }

    out.write("</table>\n");
  }




  protected MavenProject getProject()
  {
    return project;
  }
  
  protected String getOutputDirectory()
  {
    return outputDirectory.getAbsolutePath();
  }

  protected SiteRenderer getSiteRenderer()
  {
    return siteRenderer;
  }

  public String getName( Locale locale )
  {
    return "JSF Tag Documentation";
  }

  public String getDescription( Locale locale )
  {
    return "Documentation for JSF Tags";
  }

  public String getOutputName()
  {
    return "tagdoc";
  }

  protected void processIndex(
    MavenProject project,
    String       resourcePath) throws MavenReportException
  {
    _facesConfig = new FacesConfigBean();

    URL[] index = readIndex(project);
    for (int i=0; i < index.length; i++)
    {
      processIndexEntry(index[i]);
    }
  }

  protected void processIndexEntry(
    URL entry) throws MavenReportException
  {
    try
    {
      new FacesConfigParser().merge(_facesConfig, entry);
    }
    catch (MojoExecutionException e)
    {
      throw new MavenReportException("Couldn't parse faces config",e);
    }
  }

  protected FacesConfigBean getFacesConfig()
  {
    return _facesConfig;
  }


  protected List getMasterConfigs(
    MavenProject project) throws MavenReportException
  {
    String resourcePath = "META-INF/maven-faces-plugin/faces-config.xml";
    return getCompileDependencyResources(project, resourcePath);
  }

  protected List getCompileDependencyResources(
    MavenProject project,
    String       resourcePath) throws MavenReportException
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
      throw new MavenReportException("Unable to get resources for path " +
                                       "\"" + resourcePath + "\"", e);
    }

  }

  protected URL[] readIndex(
    MavenProject project) throws MavenReportException
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
      throw new MavenReportException("Failed to parse master config", e);
    }
    catch (SAXException e)
    {
      throw new MavenReportException("Failed to parse master config", e);
    }
    catch (IOException e)
    {
      throw new MavenReportException("Failed to parse master config", e);
    }
  }

  private ClassLoader createCompileClassLoader(
    MavenProject project) throws MavenReportException
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
      throw new MavenReportException("Error calculating scope classpath", e);
    }
    catch (MalformedURLException e)
    {
      throw new MavenReportException("Error calculating scope classpath", e);
    }

    return cl;
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

  static protected class SkipFilter extends ComponentFilter
  {
    protected boolean accept(
      ComponentBean component)
    {
      String componentType = component.getComponentType();

      // always skip API and base class generation
      return (!componentType.startsWith("javax") &&
              !componentType.endsWith("Base"));
    }
  }



  private class ComponentNamespaceFilter extends ComponentFilter
  {
    public ComponentNamespaceFilter()
    {
    }

    protected boolean accept(
      ComponentBean component)
    {
      if (component.getTagName() == null)
        return false;

      return taglibs.containsValue(component.getTagName().getNamespaceURI());
    }
  }

  private class ValidatorNamespaceFilter extends ValidatorFilter
  {
    public ValidatorNamespaceFilter()
    {
    }

    protected boolean accept(
      ValidatorBean component)
    {
      if (component.getTagName() == null)
        return false;

      return taglibs.containsValue(component.getTagName().getNamespaceURI());
    }
  }

  private class ConverterNamespaceFilter extends ConverterFilter
  {
    public ConverterNamespaceFilter()
    {
    }

    protected boolean accept(
      ConverterBean component)
    {
      if (component.getTagName() == null)
        return false;

      return taglibs.containsValue(component.getTagName().getNamespaceURI());
    }
  }



  static final protected class TagAttributeFilter extends PropertyFilter
  {
    protected boolean accept(
      PropertyBean property)
    {
      return (!property.isTagAttributeExcluded());
    }
  }

  static final protected class ComponentTagFilter extends ComponentFilter
  {
    protected boolean accept(
      ComponentBean component)
    {
      return (component.getTagName() != null);
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


  private FacesConfigBean _facesConfig;



  // todo: make this configurable?
  private boolean _attrDocSpansColumns = false;

  /**
   * Specifies the directory where the report will be generated
   *
   * @parameter default-value="${project.reporting.outputDirectory}"
   * @required
   */
  private File outputDirectory;
  

  /**
   * Directory where the original site is present.
   * (TRIED using ${baseDir}/src/site;  that inserted a 'null' into
   * the string for some reason.  TRIED using ${siteDirectory},
   * which was undefined.  TRIED ${project.directory}src/site; which also
   * inserted a null.  ${project.build.directory}/../src/site seems to work,
   * though it assumes that ${project.build.directory} is 
   * ${project.directory}/target.
   * 
   * @parameter default-value="${project.build.directory}/../src/site/"
   * @required
   */
  private File siteDirectory;

  /**
   * @parameter expression="${project}"
   * @required
   * @readonly
   */
  private MavenProject project;

  /**
   * @parameter
   * @required
   */
  private Map taglibs;

  /**
   * @parameter expression="META-INF/maven-faces-plugin/faces-config.xml"
   * @required
   * @readonly
   */
  private String resourcePath;


  /**
   * @component
   * @required
   * @readonly
   */
  private SiteRenderer siteRenderer;

  static private final String _DOC_SUBDIRECTORY = "tagdoc";
  static private final String[] _NON_DOCUMENTED_AGENTS = {"phone", "voice"};

}