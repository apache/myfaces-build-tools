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
package org.apache.myfaces.buildtools.maven2.plugin.builder.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.apache.velocity.util.ClassUtils;
import org.apache.velocity.util.ExceptionUtils;

/**
 * This class extends ClasspathResourceLoader adding the feature
 * of configure a path inside the classpath for load resources.
 * 
 * @author Leonardo
 *
 */
public class RelativeClasspathResourceLoader extends ResourceLoader
{

    private List paths = new ArrayList();

    public void init(ExtendedProperties configuration)
    {
        if (log.isTraceEnabled())
        {
            log
                    .trace("RelativeClasspathResourceLoader : initialization complete.");
        }

        paths.addAll(configuration.getVector("path"));

        // trim spaces from all paths
        org.apache.velocity.util.StringUtils.trimStrings(paths);
        if (log.isInfoEnabled())
        {
            // this section lets tell people what paths we will be using
            int sz = paths.size();
            for (int i = 0; i < sz; i++)
            {
                log.info("RelativeClasspathResourceLoader : adding path '"
                        + (String) paths.get(i) + "'");
            }
            log
                    .trace("RelativeClasspathResourceLoader : initialization complete.");
        }
    }

    public InputStream getResourceStream(String name)
            throws ResourceNotFoundException
    {
        if (StringUtils.isEmpty(name))
        {
            throw new ResourceNotFoundException("No template name provided");
        }

        /**
         * look for resource in thread classloader first (e.g. WEB-INF\lib in
         * a servlet container) then fall back to the system classloader.
         */

        int size = paths.size();
        for (int i = 0; i < size; i++)
        {
            String path = (String) paths.get(i);
            InputStream inputStream = null;

            try
            {
                inputStream = findTemplate(path, name);
            }
            catch (IOException ioe)
            {
                log.error("While loading Template " + name + ": ", ioe);
            }

            if (inputStream != null)
            {
                return inputStream;
            }
        }
        String msg = "ClasspathResourceLoader Error: cannot find resource "
                + name;

        throw new ResourceNotFoundException(msg);
    }

    private InputStream findTemplate(final String path, final String name)
            throws IOException
    {
        InputStream result = null;

        try
        {
            result = ClassUtils.getResourceAsStream(getClass(), path + "/"
                    + name);
        }
        catch (Exception fnfe)
        {
            throw (ResourceNotFoundException) ExceptionUtils.createWithCause(
                    ResourceNotFoundException.class, "problem with template: "
                            + name, fnfe);
        }

        if (result == null)
        {
            String msg = "ClasspathResourceLoader Error: cannot find resource "
                    + name;

            throw new ResourceNotFoundException(msg);
        }

        return result;
    }

    public boolean isSourceModified(Resource resource)
    {
        return false;
    }

    public long getLastModified(Resource resource)
    {
        return 0;
    }

}
