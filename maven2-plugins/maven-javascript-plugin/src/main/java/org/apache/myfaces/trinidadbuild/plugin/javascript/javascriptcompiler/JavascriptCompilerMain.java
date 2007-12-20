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
package org.apache.myfaces.trinidadbuild.plugin.javascript.javascriptcompiler;

import org.apache.myfaces.trinidadbuild.plugin.javascript.javascriptcompiler.JavascriptCompiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import java.util.HashMap;


public class JavascriptCompilerMain
{
  private JavascriptCompiler _javascriptCompiler;

  public JavascriptCompilerMain()
  {
  }

  public static void main(String[] args)
  {
    JavascriptCompilerMain _main = new JavascriptCompilerMain();
    _main._javascriptCompiler = new JavascriptCompiler();
    try
    {
      FileInputStream fis = new FileInputStream("D:\\Foo\\data1.txt");
      ObjectInputStream ois = new ObjectInputStream(fis);
      _main._javascriptCompiler.fileTable =  (HashMap<String, String>)ois.readObject(); // Read the object back in here
      //
      // 
      _main._javascriptCompiler.fileTable.put("RichObject", "AdfObject");
      _main._javascriptCompiler.fileTable.put("RichPage", "AdfPage");
      _main._javascriptCompiler.fileTable.put("RichConverter", "AdfConverter");
      _main._javascriptCompiler.fileTable.put("RichValidator", "AdfValidator");
      _main._javascriptCompiler.fileTable.put("Bootstrap", "AdfBootstrap");
      _main._javascriptCompiler.fileTable.put("BootstrapFaces", "AdfBootstrapFaces");
      _main._javascriptCompiler.fileTable.put("XMLRequest", "AdfXMLRequest");
      _main._javascriptCompiler.fileTable.put("MarshalingService", "AdfMarshalingService");
      _main._javascriptCompiler.fileTable.put("MessagingService", "AdfMessagingService");
      ois.close();
      fis.close();
    }
    catch (FileNotFoundException e)
    {
      // TODO
    }
    catch (IOException e)
    {
      // TODO
    }
    catch (ClassNotFoundException e) 
    {
      // TODO
    }
    File in = 
      new File("D:\\jdevrt\\adf-faces\\trunk\\adf-richclient-demo\\src\\main\\webapp\\jsLibs");
    File out = new File("D:\\Foo\\Demo\\jsLibs");
    _main._javascriptCompiler.process(in, out);
    in = 
      new File("D:\\jdevrt\\adf-faces\\trunk\\adf-richclient-demo-crm\\src\\main\\webapp\\jsLibs");
    out = new File("D:\\Foo\\crm\\jsLibs");
    _main._javascriptCompiler.process(in, out);

    //
    //  Transform the Api JavaScript files
    //
//    File in = 
//      new File("D:\\jdevrt\\adf-faces\\trunk\\adf-richclient-api\\src\\main\\javascript\\oracle\\adf\\view");
//    File out = new File("D:\\Foo\\api");
//    _main._javascriptCompiler.process(in, out);
    //
    //  Transform the Impl JavaScript files
    //
//    in = 
//       new File("D:\\jdevrt\\adf-faces\\trunk\\adf-richclient-impl\\src\\main\\javascript\\oracle\\adfinternal\\view\\");
//    out = new File("D:\\Foo\\impl");
//    _main._javascriptCompiler.process(in, out);
    //
    //  Uodate the serialized version of the HashMap
    //
    FileOutputStream fos;
    ObjectOutputStream oos;
    try
    {

      fos = new FileOutputStream("D:\\Foo\\data1.txt");
      oos = new ObjectOutputStream(fos);
      oos.writeObject(_main._javascriptCompiler.fileTable);
      oos.flush();
      oos.close();
    }
    catch (FileNotFoundException e)
    {
      // TODO
    }
    catch (IOException e)
    {
      // TODO
    }
  }
}