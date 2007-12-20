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
package org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser;

import java.util.Vector;

public class AnnotatedToken extends Token {
    protected static final Vector SPECIAL_TYPES = new Vector();

    static {
        SPECIAL_TYPES.add("Profiler");
        SPECIAL_TYPES.add("Checkpoint");
        SPECIAL_TYPES.add("Assert");
        SPECIAL_TYPES.add("Logger");
    }
    
    public static final int PREFIX_WS = 1;
    public static final int INFIX_WS = 2;

    protected int _annotationKind = AnnotationConstants.UNDEFINED;
    protected Object _annotationObject;
    protected int _wsSensitive = 0;
    protected boolean _remove;
    protected boolean _funcUsesEval;
    protected String _type = "undefined";
    protected Vector _productions = new Vector();

    public AnnotatedToken() {
    }

    public AnnotatedToken(int annotationKind, Object annotationObject) {
        super();
        _annotationKind = annotationKind;
        _annotationObject = annotationObject;
    }

    public void setAnnotationKind(int annotationKind) {
        _annotationKind = annotationKind;
    }

    public int getAnnotationKind() {
        return _annotationKind;
    }

    public int getKind() {
        if (_annotationKind != AnnotationConstants.UNDEFINED) {
            return _annotationKind;
        }

        return kind;
    }

    public void setAnnotationObject(Object annotationObject) {
        _annotationObject = annotationObject;
    }

    public Object getAnnotationObject() {
        return _annotationObject;
    }

    public AnnotatedToken getNext() {
        return (AnnotatedToken)next;
    }

    public AnnotatedToken getSpecialToken() {
        return (AnnotatedToken)specialToken;
    }

    public boolean isWhiteSpace() {
        return (kind == JSParser15Constants.WS);
    }

    public boolean isNewLine() {
        return (kind == JSParser15Constants.EOL);
    }

    public boolean isComment() {
        return ((kind == JSParser15Constants.SINGLE_LINE_COMMENT) ||
                (kind == JSParser15Constants.MULTI_LINE_COMMENT));
    }

    public boolean isWSSensitive() {
        return _wsSensitive != 0;
    }

    public void setWSSensitive(int i) {
        _wsSensitive = i;
    }

    public boolean isPrefixWSSensitive() {
        return _wsSensitive == PREFIX_WS;
    }

    public boolean isInfixWSSensitive() {
        return _wsSensitive == INFIX_WS;
    }

    public boolean isRemovableKeyword() {
        boolean isRemovable = false;

        switch (kind) {
        default :
            break;

            //      case JSParser14Constants.ASSERT_STATEMENT:
            //      case JSParser14Constants.LOGGER_STATEMENT:
            //        isRemovable = true;
            //        break;
        }

        return isRemovable;
    }

    public boolean isSpecialClassType() {
        return SPECIAL_TYPES.contains(image);
    }

    public boolean isSpecialObjectType() {
        return SPECIAL_TYPES.contains(_type);
    }

    /**
   * @return Returns the _remove.
   */
    public boolean canRemove() {
        return _remove;
    }

    /**
   * @param _remove The _remove to set.
   */
    public void setRemovable(boolean remove) {
        this._remove = remove;
    }

    /**
   * @return Returns the _type.
   */
    public String getType() {
        return _type;
    }

    /**
   * @param _type The _type to set.
   */
    public void setType(String type) {
        this._type = type;
    }

    public void setFunctionUsesEval(boolean usesEval) {
        _funcUsesEval = usesEval;
    }

    public boolean functionUsesEval() {
        return _funcUsesEval;
    }

    //    public void addProduction(SimpleNode node) {
    //        _productions.add(node);
    //    }

    public Vector getProductions() {
        return _productions;
    }
}
