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
package org.apache.myfaces.trinidadbuild.plugin.javascript.uixtools;

import java.io.File;
import java.io.FilenameFilter;

/**
 * This utility class can be used to perform some processing on input files,
 * and write the results to corresponding output files. It supports recursing
 * into subdirectories.
 * @version $Name:  $ ($Revision$) $Date$
 */
public abstract class FileProcessor
{
  /**
   * @param filenameExt the filename extension to accept. If null, all files
   *  are accepted.
   * @return a filter that only accepts files with names that end with the
   *  specified extension. However, the filter accepts all subdirectories.
   */
  public static FilenameFilter getExtensionFilter(final String filenameExt)
  {
    return new FilenameFilter() {
        public boolean accept(File dir, String name)
        {
          if ((filenameExt==null) || (name.endsWith(filenameExt))) return true;
          File f = new File(dir, name);
          return f.isDirectory();
        }
      };
  }

  /**
   * @param filter used to select files and/or subdirectories.
   * @param failFast if true, terminates at the first error (otherwise,
   *  continues with the next file).
   * @param overwrite if false, will not overwrite existing output files (That
   *  particular input and output file will be skipped).
   * @param verbose if true, generates verbose output.  */
  public FileProcessor(FilenameFilter filter,
                       boolean failFast, boolean overwrite, boolean verbose)
  {
    _filter = filter;
    _failFast = failFast;
    _dontOverwrite = !overwrite;
    isVerbose = verbose;
  }

  /**
   * Creates a Fileprocessor that overwrites all output files, and does not
   * fail fast.
   * @param filter used to select files and/or subdirectories.  */
  public FileProcessor(FilenameFilter filter, boolean verbosity)
  {
    this(filter, false, true, verbosity);
  }

  /**
   * Creates a Fileprocessor that overwrites all output files, and does not
   * fail fast.
   * @param extension the file extension to filter for. if this is null, all
   * files and subdirectories in a directory will be processed recursively.
   * @see #getExtensionFilter(String extension)
   */
  public FileProcessor(String extension, boolean verbosity)
  {
    this(getExtensionFilter(extension), verbosity);
  }

  /**
   * @param in the input file or directory
   * @param out if the input is a directory, then this must be the output
   *  directory. Otherwise, this is the output file.
   * @return true if there were no errors. false otherwise.  */
  public boolean process(File in, File out)
  {
    if (isVerbose) System.out.println("Processing file:"+in+" to file:"+out);

    if (in.isDirectory())
    {
      boolean success = true;
      String[] names = in.list(_filter);
      for(int i=0, sz=names.length; i<sz; i++)
      {
        File subIn = new File(in, names[i]);
        File subOut = new File(out, names[i]);
        success &= process(subIn, subOut);
        if (_failFast && (!success)) return false;
      }
      return success;
    }
    else
    {
      try
      {
        if (_dontOverwrite && out.exists())
        {
          if (isVerbose) System.out.println("Skipping file:"+in+
                                          " as destination file:"+out+
                                          " already exists.");
        }
        else
        {
          out.getParentFile().mkdirs();
          processFile(in, out);
        }
        return true;
      }
      catch (Exception e)
      {
        System.out.println("Error processing file:"+in+" to file:"+out);
        e.printStackTrace();
        return false;
      }
    }
  }

  /**
   * Called to process a single file.
   * @param in the input file. This is never a directory.
   * @param out the output file. If this file exists and overwriting is not
   * permitted, then this method is never called.
   * @exception Exception if this class is set to <i>fail fast</i> then any
   * exception thrown will cause all execution to halt. If this class does not
   * fail fast, then the exception will be reported and execution will
   * continue with the next file.  */
  protected abstract void processFile(File in, File out) throws Exception;

  /**
   * If this is true, verbose output will be generated.
   */
  protected final boolean isVerbose;

  private   final boolean _failFast, _dontOverwrite;
  private   final FilenameFilter _filter;
}