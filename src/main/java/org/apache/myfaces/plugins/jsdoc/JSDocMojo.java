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
package org.apache.myfaces.plugins.jsdoc;

import java.util.Locale;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.reporting.MavenReportException;

/**
 * @author Werner Punz (latest modification by $Author$)
 * @version $Revision$ $Date$
 *          <p/>
 *          A simple jsdoc plugin which should cover our documentation needs
 *          Note this plugin is a simplified tailored derivate from
 *          <p/>
 *          http://www.abiss.gr some code stems from there.
 *          <p/>
 *          since we use jsdoc for now and are not in the reporting part
 *          a simple plugin suffices.
 * @goal jsdoc
 */
public class JSDocMojo extends AbstractJSDocMojo
{
    
    public void execute() throws MojoExecutionException
    {
        try
        {
            executeReport(Locale.US);
        }
        catch ( MavenReportException e )
        {
            getLog().error("MavenReportException: Error while creating archive", e );
        }
        catch ( RuntimeException e )
        {
            getLog().error("RuntimeException: Error while creating archive", e );
        }
    }

}
