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
package org.apache.myfaces.buildtools.maven2.plugin.javascript.jmt;

import java.io.File;

/**
 * Goal to be used from a jar project, to compress the scripts present in 
 * some folder, and put the result in other folder.
 * 
 * @goal directory-compress
 * @phase test
 * @author <a href="mailto:nicolas@apache.org">nicolas De Loof</a>
 */
public class DirectoryCompressMojo
    extends AbstractCompressMojo
{

    /**
     * The directory where the files are taken.
     * 
     * @parameter
     * @required
     */
    private File sourceDirectory;
    
    /**
     * The directory where the compressed files are copied.
     * 
     * @parameter
     * @required
     */
    private File outputDirectory;

    /**
     * classifier for the compressed artifact. If not set, compressed script
     * will replace uncompressed ones, and will apply without any change in
     * HTML/JSP.
     * 
     * @parameter
     */
    private String classifier;

    /**
     * {@inheritDoc}
     * 
     * @see org.codehaus.mojo.javascript.AbstractCompressMojo#getExtension()
     */
    public String getExtension()
    {
        return classifier;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.codehaus.mojo.javascript.AbstractCompressMojo#getOutputDirectory()
     */
    protected File getOutputDirectory()
    {
        return outputDirectory;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.codehaus.mojo.javascript.AbstractCompressMojo#getSourceDirectory()
     */
    protected File getSourceDirectory()
    {
        return sourceDirectory;
    }

}
