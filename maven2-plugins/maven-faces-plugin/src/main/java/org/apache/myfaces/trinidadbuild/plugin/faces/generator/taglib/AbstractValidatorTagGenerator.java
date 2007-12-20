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
package org.apache.myfaces.trinidadbuild.plugin.faces.generator.taglib;

import org.apache.myfaces.trinidadbuild.plugin.faces.parse.ValidatorBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.AbstractTagBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.parse.PropertyBean;
import org.apache.myfaces.trinidadbuild.plugin.faces.io.PrettyWriter;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.Util;
import org.apache.myfaces.trinidadbuild.plugin.faces.util.FilteredIterator;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.StringWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.Iterator;

public abstract class AbstractValidatorTagGenerator extends AbstractTagGenerator {

  public AbstractValidatorTagGenerator(boolean is12, String licenseHeader, Log log) {
    super(is12, licenseHeader, log);
  }

   public void generateTagHandler(
    ValidatorBean validator, File generatedSourceDirectory)
  {

    try
    {
      getLog().debug("Generating " + validator.getTagClass());

      StringWriter sw = new StringWriter();
      PrettyWriter out = new PrettyWriter(sw);
      Set imports = createImports(validator);
      writeHeader(out, validator, imports);

      writeClass(out, validator);

      writeConstructor(out, validator);

      writePropertyMethods(out, validator);
      writeDoStartTag(out, validator);
      writeCreateValidator(out, validator);
      writeSetProperties(out, validator);
      writeRelease(out, validator);

      writeEnd(out);

      // delay write in case of error
      // timestamp should not be updated when an error occurs
      // delete target file first, because it is readonly
      File targetFile = createFile(generatedSourceDirectory, validator.getTagClass());
      targetFile.delete();
      FileWriter fw = new FileWriter(targetFile);
      StringBuffer buf = sw.getBuffer();
      fw.write(buf.toString());
      fw.close();
      targetFile.setReadOnly();
    }
    catch (Throwable e)
    {
      getLog().error("Error generating " + validator.getTagClass(), e);
    }
  }

  protected abstract Set createImports(ValidatorBean validator);

  protected void writeClass(PrettyWriter out, AbstractTagBean abstractTag) {
    String className = Util.getClassFromFullClass(abstractTag.getTagClass());
    if (is12())
    {
      out.println("public class " + className +
                  " extends ValidatorELTag");
    }
    else
    {
      out.println("public class " + className +
                  " extends ValidatorTag");
    }

    out.println("{");
    out.indent();
  }

  private void writeDoStartTag(
     PrettyWriter  out,
     ValidatorBean validator) throws IOException
   {
     out.println();
     if (!is12())
     {
       String validatorFullClass = validator.getValidatorClass();
       String validatorClass = Util.getClassFromFullClass(validatorFullClass);

       // TODO: restore coding standards, and make final
       //out.println("@Override");
       out.println("public int doStartTag() throws JspException");
       out.println("{");
       out.indent();
       out.println("super.setValidatorId(" + validatorClass + ".VALIDATOR_ID);");
       out.println("return super.doStartTag();");
       out.unindent();
       out.println("}");
     }
   }


  private void writeCreateValidator(
    PrettyWriter  out,
    ValidatorBean validator) throws IOException
  {
    Iterator properties = validator.properties();
    properties = new FilteredIterator(properties, new TagAttributeFilter());
    if (properties.hasNext())
    {
      String validatorFullClass = validator.getValidatorClass();
      String validatorClass = Util.getClassFromFullClass(validatorFullClass);

      out.println();
      // TODO: restore coding standards, and make final
      if (is12()) {
        out.println("@Override");
      }
      out.println("protected Validator createValidator() throws JspException");
      out.println("{");
      out.indent();
      if (is12())
      {
        out.println("String validatorId = " + validatorClass + ".VALIDATOR_ID;");
        out.println("Application appl = FacesContext.getCurrentInstance().getApplication();");
        out.println(validatorClass + " validator = " +
                    "(" + validatorClass + ")appl.createValidator(validatorId);");
      }
      else
      {
        out.println(validatorClass + " validator = " +
                    "(" + validatorClass + ")super.createValidator();");
      }
      out.println("_setProperties(validator);");
      out.println("return validator;");
      out.unindent();
      out.println("}");
    }
  }

  private void writeSetProperties(
      PrettyWriter  out,
      ValidatorBean validator) throws IOException
    {
      Iterator properties = validator.properties();
      properties = new FilteredIterator(properties, new TagAttributeFilter());
      if (properties.hasNext())
      {
        String validatorFullClass = validator.getValidatorClass();
        String validatorClass = Util.getClassFromFullClass(validatorFullClass);
        out.println();
        out.println("private void _setProperties(");
        out.indent();
        out.println(validatorClass + " validator) throws JspException");
        out.unindent();
        out.println("{");
        out.indent();
        while (properties.hasNext())
        {
          PropertyBean property = (PropertyBean)properties.next();
          writeSetProperty(out, property);
        }
        out.unindent();
        out.println("}");
      }
    }

  protected abstract void writeSetProperty(
     PrettyWriter out,
     PropertyBean property);

}
