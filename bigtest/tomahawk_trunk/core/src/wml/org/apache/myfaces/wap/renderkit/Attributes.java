/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.wap.renderkit;

/**
 * Interface define attribute names for all JSF tags.
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface Attributes {
    /** common attributes */
    final public static String ID = "id";
    final public static String BINDING = "binding";
    final public static String CONVERTER = "converter";
    final public static String ESCAPE = "escape";
    final public static String HEADER = "header";
    final public static String FOOTER = "footer";
    final public static String RENDERED = "rendered";
    final public static String VALUE = "value";

    final public static String ALIGN = "align";
    final public static String ALT = "alt";
    final public static String CLASS = "class";
    final public static String COLUMNS = "columns";
    final public static String EMPTY_OK = "emptyok";
    final public static String FORMAT = "format";
    final public static String HEIGHT = "height";
    final public static String HSPACE = "hspace";
    final public static String HREF = "href";
    final public static String MAX_LENGTH = "maxlength";
    final public static String METHOD = "method";
    final public static String MULTIPLE = "multiple";
    final public static String NAME = "name";
    final public static String LABEL = "label";
    final public static String LOCAL_SRC = "localsrc";
    final public static String OPTIONAL = "optional";
    final public static String SIZE = "size";
    final public static String SRC = "src";
    final public static String STYLE = "style";
    final public static String STYLE_CLASS = "class";
    final public static String TABINDEX = "tabindex";
    final public static String TITLE = "title";
    final public static String TYPE = "type";
    final public static String VSPACE = "vspace";
    final public static String WIDTH = "width";
    final public static String XML_LANG = "xml:lang";

    /** tag names */
    final public static String A = "a";
    final public static String ANCHOR = "anchor";
    final public static String BR = "br";
    final public static String DO = "do";
    final public static String GO = "go";
    final public static String IMG = "img";
    final public static String INPUT = "input";
    final public static String OPTION = "option";
    final public static String OPTGROUP = "optgroup";
    final public static String POSTFIELD = "postfield";
    final public static String REFRESH = "refresh";
    final public static String SELECT = "select";
    final public static String SETVAR = "setvar";
    final public static String TABLE = "table";
    final public static String TD = "td";
    final public static String TR = "tr";

    /** hidden fields */
    final public static String POSTFIX_SUBMITED = "_submited";
    final public static String POSTFIX_ACTIVATED = "_activated";

    /** constants */
    final public static String SELECT_MANY_SEPARATOR = ";";
    final public static String POST = "post";
    final public static String LIST = "list";
    final public static String ACCEPT = "accept";

}
