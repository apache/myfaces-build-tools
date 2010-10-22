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

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.myfaces.buildtools.maven2.plugin.builder.model.Model;

/**
 * An interface that is implemented by classes that are capable of collecting
 * metadata about JSF components, converters, validators, etc.
 * <p>
 * A ModelBuilder implementation might read xml files, or scan source code for
 * annotations, or read data from a database, or any number of possible
 * approaches. All that matters is that it makes a series of calls to a Model
 * object to add information.
 * <p>
 * ModelBuilder implementations may be run as a chain, ie one instance used to
 * populate the Model object with some objects, then a second instance used to
 * add more data or modify the data already in the model.
 */
public interface ModelBuilder
{
    /**
     * Given a model (which might already be partly populated with data, add
     * information about JSF artifacts.
     */
    public void buildModel(Model model, ModelParams parameters)
            throws MojoExecutionException;
}