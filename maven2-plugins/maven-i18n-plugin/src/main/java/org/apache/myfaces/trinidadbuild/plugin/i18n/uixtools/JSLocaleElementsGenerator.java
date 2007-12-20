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
package org.apache.myfaces.trinidadbuild.plugin.i18n.uixtools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Generates JavaScript libraries containing the Locale information from
 * Java.
 * @version $Name:  $ ($Revision: 1.3 $) $Date: 2004/03/25 22:36:42 $
 */
public class JSLocaleElementsGenerator
{
  public static void main(
    String[] args
    )
  {

    if ((args.length == 1) && ("?".equals(args[0])))
    {
      System.out.println("Generates JavaScript Locale information files");
      System.out.println("for the Locales available in the JVM.");
      System.out.println("These files are used by the Cabo HTML client");
      System.out.println("logic to handle date formatting and validation.");
      System.out.println();
      System.out.println("Parameters:");
      System.out.println("\tprettyPrint=false\tTurns off pretty printed output");
      System.out.println("\toutDir=[path]\t\tDirectory path to write JavaScript files to");
      System.out.println("\tsourceDir=[path]\t\tRoot directory path to write Java source to");
      System.out.println("\tbundleOutDir=[path]\t\tRoot directory path to write Java ResourceBundles to (defaults to sourceDir).");
      System.out.println("\tvariant=[variant name]\t\tIf supplied utility will generate ResourceBundles for this variant, and nothing else.");
      System.out.println("\tverbose=true\t\tTurns on verbose output");
      System.out.println("\tgenerateBundleBaseOnly=true\t\tGenerates the base LocaleElement.java file only");
      System.exit(0);
    }

    // whether the output should be pretty printed for legibility
    boolean prettyPrint = getArgBooleanValue(args, "prettyPrint", true);

    // whether verbose ouput should be generated.
    boolean verbose = getArgBooleanValue(args, "verbose", false);

    boolean writeSource = getArgBooleanValue(args, "writeSource", true);
    boolean writeJavascript = getArgBooleanValue(args, "writeJavascript", true);

    // whether only the Base LocaleElements.java file should be created.
    // this is based on the Locale.US
    boolean generateBundleBaseOnly = getArgBooleanValue(args,"generateBundleBaseOnly",false);

    // the output directory
    String outDir = getArgStringValue(args, "outDir", null);

    if (outDir == null)
    {
      outDir = System.getProperty("user.dir") +
               File.separator +
               _DEFAULT_LOCATION_PATH;
    }

    if (!outDir.endsWith(File.separator))
    {
      outDir = outDir + File.separator;
    }


    // the requested variant
    String variant = getArgStringValue(args, "variant", null);
    boolean writeAll = true;
    if (variant != null)
    {
      writeAll = false;
      variant = variant.toUpperCase();
    }

    // the source directory
    String sourceDir = getArgStringValue(args, "sourceDir", null);

    if (sourceDir == null)
    {
      sourceDir = System.getProperty("user.dir");
    }

    String bundleOutDir = getArgStringValue(args, "bundleOutDir", null);

    if (bundleOutDir == null)
      bundleOutDir = sourceDir;

    // The bundle output dir is the base of the heirarchy, we'll actually put
    // the files into the appropriate sub directory for the package.
    String fullBundleOutDir = (bundleOutDir
                               + (bundleOutDir.endsWith(File.separator)
                                  ? ""
                                  : File.separator)
                              + _DEFAULT_BUNDLE_LOCATION_PATH);

    String version = getArgStringValue(args, "version", null);
    version = VersionUtils.normalizeVersionSuffix(version);

    if (verbose)
    {
      System.out.println("Writing files to: " + outDir);
      if (writeAll)
      {
        System.out.println("Writing source to: " + sourceDir);
      }
      System.out.println("Writing bundles to: " + fullBundleOutDir);
    }

    try
    {
      File localeListFile = null;

      if (writeSource)
        (new File(fullBundleOutDir)).mkdirs();

      if (writeJavascript)
        (new File(outDir)).mkdirs();

      if (writeAll && writeSource && !generateBundleBaseOnly)
      {
        localeListFile = new File(sourceDir, _LOCALE_LIST_PATH);
        localeListFile.getParentFile().mkdirs();
        localeListFile.createNewFile();
      }

      Locale[] locales = null;

      if (generateBundleBaseOnly)
      {
         locales = new Locale[1];
         locales[0] = Locale.US;
      }
      else
      {
        locales = Locale.getAvailableLocales();
      }

      //
      // loop through the available Locales, writing their contents out
      // as JavaScript libraries and/or ResourceBundles
      //
      for (int i = 0; i < locales.length; i++)
      {
        // write the JavaSCript library for this locale
        _generateJSLocaleElements(outDir, fullBundleOutDir, locales[i],
                                  variant, prettyPrint, verbose,
                                  writeJavascript, writeSource, generateBundleBaseOnly, version);
      }

      // And write out a listing of all the locales, but  not when we want to
      // just generate the base Resource file.
      if (writeAll && writeSource && !generateBundleBaseOnly)
      {
        Writer sourceWriter = new FileWriter(localeListFile);
        sourceWriter.write(_LOCALE_LIST_CODE_START);

        for (int i = 0; i < locales.length; i++)
        {
          sourceWriter.write("    new Locale(\"");
          sourceWriter.write(locales[i].getLanguage());
          sourceWriter.write("\",\"");
          sourceWriter.write(locales[i].getCountry());
          sourceWriter.write("\",\"");
          sourceWriter.write(locales[i].getVariant());
          sourceWriter.write("\"),\n");
        }
        sourceWriter.write(_LOCALE_LIST_CODE_END);
        sourceWriter.close();
      }
    }
    catch (IOException e)
    {
      System.err.println(e);
      e.printStackTrace();
    }

    if (verbose)
    {
      System.out.println("Done writing files");
    }
  }

  private static String getArgStringValue(
    String[] args,
    String   argName,
    String   defaultValue
    )
  {
    int argLength = argName.length();

    for (int i = 0; i < args.length; i++)
    {
      String currArg = args[i];

      if (currArg.startsWith(argName))
      {
        if (!currArg.equals(argName) &&
            ('=' == currArg.charAt(argLength)))
        {
          return currArg.substring(argLength + 1);
        }
      }
    }

    return defaultValue;
  }

  private static boolean getArgBooleanValue(
    String[] args,
    String   argName,
    boolean  defaultValue
    )
  {
    //
    // check for = true or = false
    //
    String argValue = getArgStringValue(args, argName, null);

    if (argValue != null)
    {
      // check for = self
      if (argName.equals(argValue))
      {
        return true;
      }
      else
      {
        return Boolean.valueOf(argValue).booleanValue();
      }
    }

    //
    // check for the name by itself
    //
    for (int i = 0; i < args.length; i++)
    {
      if (args[i].equals(argName))
      {
        return true;
      }
    }

    //
    // check for the name turned off
    //
    String notName = "-" + argName;

    for (int i = 0; i < args.length; i++)
    {
      if (args[i].equals(notName))
      {
        return false;
      }
    }

    return defaultValue;
  }

  private static void _generateJSLocaleElements(
    String  outDir,
    String  bundleOutDir,
    Locale  targetLocale,
    String  variant,
    boolean prettyPrint,
    boolean verbose,
    boolean writeJavascript,
    boolean writeSource,
    boolean generateBundleBaseOnly,
    String version
    ) throws IOException
  {
    String fileName;
    PrintWriter localeWriter;

    if (variant == null && writeJavascript)
    {
      fileName = _getFileName(targetLocale, version, generateBundleBaseOnly) + _JAVASCRIPT_EXTENSION;

      if (verbose)
      {
        System.out.println("Writing " + fileName);
      }

      // create the file to write to
      localeWriter = new PrintWriter
        (new BufferedWriter(new FileWriter(outDir + fileName),
                            _DEFAULT_BUFFER_SIZE));
      _writeLocale(localeWriter, targetLocale, prettyPrint);
      localeWriter.close();
    }
  }

  private static void _writeJavaBundle(
    Writer      output,
    Locale      targetLocale,
    String      fileName
    ) throws IOException
  {
    output.write(_LOCALE_JAVA_CODE_START);
    output.write("public class "
                 + fileName
                 + " extends ListResourceBundle\n{\n"
                 + "  public Object[][] getContents()\n  {\n"
                 + "    return contents;\n  }\n\n");
    try
    {
      ResourceBundle elementsData =
        ResourceBundle.getBundle(_LOCALE_ELEMENTS_PATH, targetLocale);

      boolean doneOne = false;

      for (int i = 0; i < LOCALE_ELEMENTS_GET_KEYS.length; i ++)
      {
        String currKey = LOCALE_ELEMENTS_GET_KEYS[i];

        Object data = _getElementData(currKey, elementsData, targetLocale);
        _writeJavaBundleElement(output, currKey, data, doneOne);
        doneOne = true;
      }
      doneOne = false;
      output.write("  static final Object[][] contents = \n  {\n");
      for (int i = 0; i < LOCALE_ELEMENTS_GET_KEYS.length; i ++)
      {
        String currKey = LOCALE_ELEMENTS_GET_KEYS[i];
        output.write((doneOne ? ",\n" : "")
                     + "    { \""
                     + currKey
                     + "\", "
                     + _getKeyArrayName(currKey)
                     + "}");
        doneOne = true;
      }
      output.write("\n  };");
    }
    catch (MissingResourceException e)
    {
      // make sure that the class will, at least, compile even if incomplete.
      output.write("  // Bundle generation error:\n  // " + e);
      output.write("\n  static final Object[][] contents = null;");
      System.err.println(e);
    }
    output.write("\n}\n");
  }

  private static void _writeLocale(
    Writer      output,
    Locale      targetLocale,
    boolean     prettyPrint
    ) throws IOException
  {

    output.write("var LocaleSymbols_");
    output.write(targetLocale.toString());
    output.write(" = new LocaleSymbols({");

    if (prettyPrint)
      output.write('\n');

    Enumeration zoneEnumeration =
                               new ArrayEnumeration(DATE_FORMAT_ZONE_GET_KEYS);

    // write the locale elements into the file
    _writeResourceContents(output,
                           _LOCALE_ELEMENTS_PATH,
                           new ArrayEnumeration(LOCALE_ELEMENTS_GET_KEYS),
                           targetLocale,
                           zoneEnumeration.hasMoreElements(),
                           prettyPrint);

    // write the date format elements into the file
    _writeResourceContents(output,
                           _DATE_FORMAT_ZONE_PATH,
                           zoneEnumeration,
                           targetLocale,
                           false,  // this is the last resource to write
                           prettyPrint);

    output.write("});");

    if (prettyPrint)
      output.write('\n');
  }


  private static void _writeResourceContents(
    Writer      output,
    String      baseName,
    Enumeration keys,
    Locale      targetLocale,
    boolean     hasMoreResources,
    boolean     prettyPrint
    ) throws IOException
  {
    try
    {
      ResourceBundle elementsData = ResourceBundle.getBundle(baseName,
                                                             targetLocale);

      while(keys.hasMoreElements())
      {
        String currKey = (String)keys.nextElement();

        Object data = null;

        if("CurrencyElements".equals(currKey))
          data = _getCurrencyData(targetLocale);
        else
          data = _getElementData(currKey, elementsData, targetLocale);

        boolean wroteElement = _writeResourceElement(
                                    output,
                                    currKey,
                                    data,
                                    hasMoreResources || keys.hasMoreElements(),
                                    prettyPrint);

        if (wroteElement && prettyPrint)
        {
          output.write('\n');
        }
      }
    }
    catch (MissingResourceException e)
    {
      System.err.println(e);
    }
  }

  private static Object _getCurrencyData(
    Locale targetLocale)
  {
    DecimalFormat df = (DecimalFormat) NumberFormat.getCurrencyInstance(targetLocale);
    DecimalFormatSymbols decimalFormatSymbols = df.getDecimalFormatSymbols();
    Object[] currencyData = new Object[6];
    currencyData[0] = decimalFormatSymbols.getCurrencySymbol(); 
    currencyData[1] = decimalFormatSymbols.getCurrency().getCurrencyCode();
    currencyData[2] = df.getPositivePrefix();
    currencyData[3] = df.getPositiveSuffix();
    currencyData[4] = df.getNegativePrefix();
    currencyData[5] = df.getNegativeSuffix();

    return currencyData;
  }

  private static Object _getElementData(
    String currKey,
    ResourceBundle elementsData,
    Locale targetLocale
   )
  {
    return elementsData.getObject(currKey);
  }

  private static boolean _writeResourceElement(
    Writer  output,
    String  key,
    Object  value,
    boolean notLast,
    boolean prettyPrint
    ) throws IOException
  {
    if (key != null)
    {
      // start writing element, using key as the property name
      output.write(key);
      output.write(':');
    }

    Object[] values = null;
    int valueCount = 0;

    if (value instanceof Object[])
    {
      values = (Object[])value;

      valueCount = values.length;

      if (valueCount == 0)
      {
        value = "";
      }
    }

    if (valueCount != 0)
    {
      // output.write("new Array(");
      output.write('[');

      for (int i = 0; i < valueCount; i++)
      {
        _writeResourceElement(output,
                              null,
                              values[i],
                              i < valueCount - 1,
                              prettyPrint);
      }

      //output.write(')');
      output.write(']');
    }
    else
    {
      output.write('\"');
      _writeEscapedString(output, value.toString(), false);
      output.write('\"');
    }

    if (notLast)
    {
      output.write(',');

      if (prettyPrint)
      {
        output.write(' ');
      }
    }

    // we wrote some ouput
    return true;
  }

  private static void _writeJavaBundleElement(
    Writer  output,
    String  key,
    Object  value,
    boolean wasPrevious
    ) throws IOException
  {
    output.write("  private static final String "
                 + _getKeyArrayName(key)
                 + "[]");
    Object[] values = null;
    int valueCount = 0;
    boolean doneOne = false;

    if (value instanceof Object[])
    {
      values = (Object[]) value;

      valueCount = values.length;

      if (valueCount <= 0)
      {
        values = _EMPTY_VALUES;
        valueCount = 1;
      }
    }

    if (valueCount > 0)
    {
      output.write(" = \n  {\n");
      for (int i = 0; i < valueCount; i++)
      {
        output.write((doneOne ? ", \n" : "") + "    \"");
        _writeEscapedString(output, values[i].toString(), true);
        output.write("\"");
        doneOne = true;
      }
      output.write("\n  };\n\n");
    }
    else
      output.write(" = null");
  }

  private static void _writeEscapedString(
    Writer output,
    String value,
    boolean isJava
    ) throws IOException
  {
    int length = value.length();

    for (int i = 0; i < length; i++)
    {
      char currChar = value.charAt(i);

      if (currChar > 255)
      {
        output.write("\\u");
        output.write(_getHexString(currChar, 4));
      }
      else
      {
        if (isJava)
        {
          if ((currChar > 31)
              && (currChar < 128))
          {
            if (currChar == '\"')
              output.write("\\\"");
            else
              output.write(currChar);
          }
          else
          {
            output.write("\\u");
            output.write(_getHexString(currChar, 4));
          }
        }
        else
        {
          // write ascii printable characters, except for the double quote,
          // which needs to be escaped because we are already in a String.
          if ((currChar > 31)    &&
              (currChar < 128)   &&
              (currChar != '\"') &&
              (currChar != '\''))
          {
            output.write(currChar);
          }
          else
          {
            output.write("\\x");
            output.write(_getHexString(currChar, 2));
          }
        }
      }
    }
  }


  private static String _getHexString(
    int number,
    int minDigits
    )
  {
    String hexString = Integer.toHexString(number);

    int hexLength = hexString.length();

    int zeroPadding = minDigits - hexLength;

    if (zeroPadding > 0)
    {
      String paddedString = "0";

      while (zeroPadding > 1)
      {
        paddedString += "0";
        zeroPadding--;
      }

      hexString = paddedString + hexString;
    }
    else
    {
      if (zeroPadding < 0)
      {
        throw new IllegalArgumentException();
      }
    }

    return hexString;
  }

  private static String _getKeyArrayName(String key)
  {
    return "_array" + key;
  }

  private static String _getFileName(
    Locale locale,
    String version,
    boolean generateBundleBaseOnly
    )
  {

    if (generateBundleBaseOnly)
    {
      return "LocaleElements";
    }

    if (version == null)
      return "LocaleElements_" + locale;
    return "LocaleElements_" + locale + version;
  }

  private static String _getLocaleSuffix(
    Locale locale
    )
  {
    String localeString =  locale.toString();

    return localeString.substring(localeString.indexOf('_'));
  }

  //
  // Array of DateFormatZoneData key names to retrieve.
  // Only keys from this list are used to generate locale information
  //
  private static final String[] LOCALE_ELEMENTS_GET_KEYS = 
  {
    "MonthNames",
    "MonthAbbreviations",
    "DayNames",
    "DayAbbreviations",
    "AmPmMarkers",
    "Eras",
    "DateTimePatterns",
    "DateTimeElements",
    "NumberElements",
    "CurrencyElements",
  };

  //
  // Array of DateFormatZoneData key names to retrieve.
  // Only keys from this list are used to generate locale information
  //
  private static final String[] LOCALE_ELEMENTS_MAPPINGS = 
  {
    "MonthNames", "getMonths",
    "MonthAbbreviations", "getShortMonths",
    "DayNames", "getWeekdays",
    "DayAbbreviations", "getShortWeekdays",
    "AmPmMarkers", "getAmPmStrings",
    "Eras", "getEras",
    "DateTimePatterns", null,
    "DateTimeElements", null,
    "NumberElements", null
  };

  //
  // Array of LocaleElements key names to retrieve.
  // Only keys from this list are used to generate locale information
  //
  private static final String[] DATE_FORMAT_ZONE_GET_KEYS = {};

  // J2SE 1.3:
  // private static String _RESOURCES_PACKAGE = "java.text.resources";

  // J2SE 1.4:
  private static String _RESOURCES_PACKAGE = "sun.text.resources";

  private static final String _DATE_FORMAT_ZONE_PATH =
                          _RESOURCES_PACKAGE + ".DateFormatZoneData";

  private static final String _LOCALE_ELEMENTS_PATH =
                          _RESOURCES_PACKAGE + ".LocaleElements";

  private static final String _DEFAULT_LOCATION_PATH =
          "org\\apache\\myfaces\\trinidadinternal\\ui\\jsLibs\\resources\\".replace('\\',
                                                          File.separatorChar);

  // Mimicking to be in api path. In the trinidad-faces impl side we have
  // the following directory structure. There is identical directory sturcture
  // in trinidad-faces api side.
  // Only _TRINIDAD_LOCALE_ELEMENT base file will be added to the api side,
  // while all the variants will be in impl side in the directory structure
  // defined below.
  private static final String _TRINIDAD_LOCALE_ELEMENTS_PACKAGE =
    "org.apache.myfaces.trinidad.resource";

  public static final String _DEFAULT_BUNDLE_LOCATION_PATH =
    (_TRINIDAD_LOCALE_ELEMENTS_PACKAGE.replace('.', File.separatorChar)
     + File.separatorChar);

  private static final String _TRINIDAD_LOCALE_ELEMENTS_PATH =
    _TRINIDAD_LOCALE_ELEMENTS_PACKAGE + ".LocaleElements";

  // buffer size of the BufferedWriter to which output is written
  private static final int _DEFAULT_BUFFER_SIZE = 1 << 14;

  private static final String _LOCALE_LIST_PATH =
          "org\\apache\\myfaces\\trinidadinternal\\ui\\laf\\base\\xhtml\\LocaleList.java".replace('\\',
                                                          File.separatorChar);

  private static final String _EMPTY_VALUES[] = { "" };

  private static final String _LOCALE_LIST_CODE_START =
"/*\n" +
"**\n" +
"**34567890123456789012345678901234567890123456789012345678901234567890123456789\n" +
"*/\n" +
"package org.apache.myfaces.trinidadinternal.ui.laf.base.xhtml;\n" +
"\n" +
"import java.util.Locale;\n" +
"\n" +
"import java.util.HashMap;\n" +
"\n" +
"/*\n" +
" * List of supported locales.  Automatically generated - do not modify!\n" +
" */\n" +
"public class LocaleList\n" +
"{\n" +
"  /*\n" +
"   * Returns the list of supported locales.\n" +
"   */\n" +
"  static public HashMap<Locale, Locale> getSupportedLocales()\n" +
"  {\n" +
"    return _sLocaleMapper;\n" +
"  }\n" +
"  \n" +
"  private LocaleList()\n" +
"  {\n" +
"  }\n" +
"\n" +
"  static private final Locale[] _sLocales = new Locale[]\n" +
"  {\n";


  private static final String _LOCALE_LIST_CODE_END =
"  };\n" +
"\n" +
"  static private HashMap<Locale, Locale> _sLocaleMapper;\n" +
"\n" +
"  static\n" +
"  {\n" +
"    _sLocaleMapper = new HashMap<Locale, Locale>();\n" +
"    for(Locale locale : _sLocales)\n" +
"    {\n" +
"      _sLocaleMapper.put(locale, locale);\n" +
"    }\n" +
"  }\n" +
"}\n";

  private static final String _LOCALE_JAVA_CODE_START =
    "// Do not edit this file!\n"
    + "// This file has been automatically generated.\n"
    + "// Edit JSLocaleElementsGenerator instead.\n//\n"
    + "package "
    + _TRINIDAD_LOCALE_ELEMENTS_PACKAGE
    + ";\n\n"
    + "import java.util.ListResourceBundle;\n";

  private static final String _JAVASCRIPT_EXTENSION = ".js";
  private static final String _JAVA_EXTENSION = ".java";
 
  //Introduced to remove the dependency on bali-share in the api side.
  private static class ArrayEnumeration implements Enumeration
  {
    public ArrayEnumeration(Object[] array)
    {
      _objects = (array == null)? _EMPTY_ARRAY : array;
    }

    public boolean hasMoreElements()
    {
      return _currentIndex < _objects.length;
    }

    public Object nextElement()
    {
      return _objects[_currentIndex++];
    }

    private Object[] _objects;

    private int _currentIndex;

    private static final Object[] _EMPTY_ARRAY = new Object[0];
  }
}
