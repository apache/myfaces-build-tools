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
package org.apache.myfaces.trinidadbuild.plugin.faces;

import java.lang.reflect.Field;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import junit.framework.TestCase;

import org.apache.maven.model.Model;
import org.apache.maven.model.Build;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * Tests the component generation mojo.
 */
abstract public class AbstractMojoTestCase extends TestCase
{
  /**
   * Creates a new AbstractMojoTestCase.
   *
   * @param testName  the test to execute
   */
  public AbstractMojoTestCase(
    String testName)
  {
    super(testName);
  }

  protected void setMojoProject(
    Mojo   mojo,
    String name) throws MojoExecutionException
  {
    Model model = new Model();
    Build build = new Build();
    build.setOutputDirectory("target/test-build-output");
    model.setBuild(build);
    setMojoField(mojo, name, new MavenProject(model));
  }

  protected void setMojoField(
    Mojo   mojo,
    String name,
    Object value) throws MojoExecutionException
  {
    try
    {
      Class mojoClass = mojo.getClass();
      Field field = mojoClass.getDeclaredField(name);
      field.setAccessible(true);
      field.set(mojo, value);
    }
    catch (NoSuchFieldException e)
    {
      throw new MojoExecutionException("Unknown Mojo property " +
                                       "\"" + name + "\"", e);
    }
    catch (IllegalAccessException e)
    {
      throw new MojoExecutionException("Error setting Mojo property " +
                                       "\"" + name + "\"", e);
    }
  }

  protected void setMojoProperty(
    Mojo   mojo,
    String name,
    Object value) throws MojoExecutionException
  {
    setMojoProperty(mojo, name, value.getClass(), value);
  }

  protected void setMojoProperty(
    Mojo    mojo,
    String  name,
    boolean value) throws MojoExecutionException
  {
    setMojoProperty(mojo, name, Boolean.TYPE, Boolean.valueOf(value));
  }

  protected void setMojoProperty(
    Mojo   mojo,
    String name,
    Class  type,
    Object value) throws MojoExecutionException
  {
    try
    {
      Class mojoClass = mojo.getClass();
      Method method = mojoClass.getDeclaredMethod(
                                    "set" +
                                    Character.toUpperCase(name.charAt(0)) +
                                    name.substring(1),
                                    new Class[]{type});
      method.setAccessible(true);
      method.invoke(mojo, new Object[]{value});
    }
    catch (NoSuchMethodException e)
    {
      throw new MojoExecutionException("Unknown Mojo property " +
                                       "\"" + name + "\"", e);
    }
    catch (InvocationTargetException e)
    {
      throw new MojoExecutionException("Error setting Mojo property " +
                                       "\"" + name + "\"",
                                       e.getTargetException());
    }
    catch (IllegalAccessException e)
    {
      throw new MojoExecutionException("Error setting Mojo property " +
                                       "\"" + name + "\"", e);
    }
  }
}
