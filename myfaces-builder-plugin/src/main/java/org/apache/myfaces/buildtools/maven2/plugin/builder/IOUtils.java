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
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import org.apache.commons.digester.Digester;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;
import org.xml.sax.SAXException;

/**
 */
public class IOUtils
{
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

    /**
     * Read the contents of the model from a provided Reader object.
     */
    public static Model readModel(Reader reader) throws MojoExecutionException
    {
        try
        {
            Digester d = new Digester();

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
    }
}
