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
package org.apache.myfaces.buildtools.maven2.plugin.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.digester.Digester;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.xml.sax.SAXException;

/**
 * Utilities to write a Model as xml, and read a Model in from xml.
 */
public class IOUtils
{
    
    private final static String MYFACES_METADATA = "META-INF/myfaces-metadata.xml";
    
    /**
     * Write the contents of the model to an xml file.
     */
    public static void saveModel(Model model, File outfile)
            throws MojoExecutionException
    {
        FileWriter writer = null;

        try
        {
            outfile.getParentFile().mkdirs();

            writer = new FileWriter(outfile);
            writeModel(model, writer);
        }
        catch (IOException e)
        {
            throw new MojoExecutionException("Unable to save data", e);
        }
        finally
        {
            try
            {
                if (writer != null)
                {
                    writer.close();
                }
            }
            catch (IOException e)
            {
                // ignore
            }
        }
    }

    /**
     * Write the contents of the model to a provided Writer object.
     */
    public static void writeModel(Model model, Writer writer)
            throws MojoExecutionException
    {
        try
        {
            writer.write("<?xml version='1.0' ?>\n");
            PrintWriter pw = new PrintWriter(writer);
            XmlWriter xmlWriter = new XmlWriter(pw);
            Model.writeXml(xmlWriter, model);
        }
        catch (IOException e)
        {
            throw new MojoExecutionException("Unable to save data", e);
        }
    }

    /**
     * Read the contents of the model from an xml file.
     */
    public static Model loadModel(File infile) throws MojoExecutionException
    {
        FileReader reader = null;
        try
        {
            reader = new FileReader(infile);
            return readModel(reader);
        }
        catch (FileNotFoundException e)
        {
            throw new MojoExecutionException("No metadata file:" + infile);
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    // ignore
                }
            }
        }
    }
    
    public static boolean existsSourceFile(String filename, List sourceDirs)
    {
        boolean existsFile = false;            
        for (int i = 0; i < sourceDirs.size(); i++)
        {
            String srcDir = (String) sourceDirs.get(i);
            
            //This directory that contains target/myfaces-builder-plugin/main
            //is banned, all here is generated
            //so we don't take a look at this directory
            //if (srcDir.matches(".*\\W+target\\W+myfaces-builder-plugin\\W+main\\W+.*"))
            //if (srcDir.matches(".*\\W+target\\W+generated-sources\\W+myfaces-builder-plugin"))
            //{
            //    continue;
            //}                        
            
            File f = new File(srcDir,filename);
            if (f.exists())
            {
                existsFile=true;
                break;
            }
        }
        return existsFile;
    }
    
    public static Model getModelFromArtifact(Artifact artifact) 
        throws MojoExecutionException
    {
        Model model = null;
        File jarFile = artifact.getFile();

        URLClassLoader archetypeJarLoader;

        InputStream is = null;
        try
        {
            URL[] urls = new URL[1];
            urls[0] = jarFile.toURI().toURL();
            archetypeJarLoader = new URLClassLoader(urls);

            is = getStream(MYFACES_METADATA, archetypeJarLoader);

            if (is != null)
            {
                Reader r = null;
                try
                {
                    r = new InputStreamReader(is);
                    model = readModel(r);
                    r.close();
                }
                catch (IOException e)
                {
                    throw new MojoExecutionException(
                            "Error reading myfaces-metadata.xml form "
                                    + artifact.getFile().getName(), e);
                }
                finally
                {
                    if (r != null)
                    {
                        try
                        {
                            r.close();
                        }
                        catch (IOException e)
                        {
                            //ignore
                        }
                    }
                }

                System.out.println("Artifact: "
                        + artifact.getFile().getName()
                        + " have META-INF/myfaces-metadata.xml");
            }
        }
        catch (IOException e)
        {
            throw new MojoExecutionException(
                    "Error reading myfaces-metadata.xml form "
                            + artifact.getFile().getName(), e);
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException ex)
                {
                    //ignore
                }
            }
        }

        return model;
    }

    /**
     * Scan every jarfile that this maven project has a dependency on, looking for metadata files.
     * <p>
     * Each file found is loaded into memory as a Model object and added to the list.
     */
    public static List getModelsFromArtifacts(MavenProject project)
            throws MojoExecutionException
    {
        List models = new ArrayList();
        
        for (Iterator it = project.getArtifacts().iterator(); it.hasNext();)
        {

            Artifact artifact = (Artifact) it.next();
            
            if ("compile".equals(artifact.getScope())
                    || "provided".equals(artifact.getScope())
                    || "system".equals(artifact.getScope()))
            {
                //This is safe since we have all depencencies on the
                //pom, so they are downloaded first by maven.
                File jarFile = artifact.getFile();

                URLClassLoader archetypeJarLoader;

                InputStream is = null;
                try
                {
                    URL[] urls = new URL[1];
                    urls[0] = jarFile.toURL();
                    archetypeJarLoader = new URLClassLoader(urls);

                    is = getStream(MYFACES_METADATA, archetypeJarLoader);

                    if (is != null)
                    {
                        Reader r = null;
                        try
                        {
                            r = new InputStreamReader(is);
                            Model m = readModel(r);
                            models.add(m);
                            r.close();
                        }
                        catch (IOException e)
                        {
                            throw new MojoExecutionException(
                                    "Error reading myfaces-metadata.xml form "
                                            + artifact.getFile().getName(), e);
                        }
                        finally
                        {
                            if (r != null)
                            {
                                try
                                {
                                    r.close();
                                }
                                catch (IOException e)
                                {
                                    //ignore
                                }
                            }
                        }

                        System.out.println("Artifact: "
                                + artifact.getFile().getName()
                                + " have META-INF/myfaces-metadata.xml");
                    }
                }
                catch (IOException e)
                {
                    throw new MojoExecutionException(
                            "Error reading myfaces-metadata.xml form "
                                    + artifact.getFile().getName(), e);
                }
                finally
                {
                    if (is != null)
                    {
                        try
                        {
                            is.close();
                        }
                        catch (IOException ex)
                        {
                            //ignore
                        }
                    }
                }
            }
        }
        return models;
    }
        
    private static InputStream getStream( String name,
            ClassLoader loader )
    {
        if ( loader == null )
        {
            return Thread.currentThread().getContextClassLoader().getResourceAsStream( name );
        }
        return loader.getResourceAsStream( name );
    }
    
    /**
     * Read the contents of the model from a provided Reader object.
     */
    public static Model readModel(Reader reader) throws MojoExecutionException
    {
        try
        {
            //Digester d = new Digester();
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            // requires JAXP 1.3, in JavaSE 5.0
            // spf.setXIncludeAware(true);
            Digester d = new Digester(spf.newSAXParser());
            d.setNamespaceAware(true);            

            Model.addXmlRules(d);

            d.parse(reader);

            Model model = (Model) d.getRoot();
            return model;
        }
        catch (IOException e)
        {
            throw new MojoExecutionException("Unable to load metadata", e);
        }
        catch (SAXException e)
        {
            throw new MojoExecutionException("Unable to load metadata", e);
        }
        catch (ParserConfigurationException e)
        {
            // TODO Auto-generated catch block
            throw new MojoExecutionException("Unable to load parser", e);
        }
    }
}
