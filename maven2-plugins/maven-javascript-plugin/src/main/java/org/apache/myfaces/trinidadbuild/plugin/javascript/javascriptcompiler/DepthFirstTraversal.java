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

import java.io.PrintStream;

import java.util.HashMap;

import java.util.StringTokenizer;

import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTAddOp;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTAdditiveExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTAnnotatableDirective;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTArguments;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTArrayLiteral;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTAssignementOperator;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTAssignmentExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTAssignmentExpressionNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTAttribute;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTAttributeExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTAttributes;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTBitwiseANDExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTBitwiseANDExpressionNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTBitwiseANDOp;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTBitwiseORExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTBitwiseORExpressionNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTBitwiseOROp;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTBitwiseXORExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTBitwiseXORExpressionNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTBitwiseXOROp;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTBlock;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTBreakStatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTCaseElement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTCaseElements;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTCaseLabel;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTClassDefinition;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTConditionalExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTConditionalExpressionNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTContinueStatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTDirective;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTDirectives;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTDoStatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTElementList;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTEmptyStatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTEolCommentSkipWs;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTEqualOp;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTEqualityExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTEqualityExpressionNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTExportBinding;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTExportBindingList;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTExportDefinition;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTExpressionQualifiedIdentifier;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTExpressionStatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTFieldList;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTFieldName;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTForInBinding;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTForInitializer;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTForStatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTFullNewExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTFullNewSubexpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTFullPostfixExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTFunctionCommon;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTFunctionConstructor;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTFunctionDefinition;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTFunctionExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTFunctionName;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTIdentifier;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTIfStatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTImportDirective;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTIncludeDirective;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTInheritance;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTInterfaceDefinition;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTLabeledStatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTListExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTListExpressionNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTLiteralElement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTLiteralField;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTLogicalANDExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTLogicalANDExpressionNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTLogicalORExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTLogicalORExpressionNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTMulOp;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTMultiplicativeExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTNamespaceDefinition;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTNonAssignmentExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTNonAssignmentExpressionNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTObjectLiteral;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTPackageDefinition;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTPackageIdentifiers;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTPackageName;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTParameter;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTParameterInit;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTParameters;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTPostfixExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTPostfixOp;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTPragma;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTPragmaArgument;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTPragmaExpr;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTPragmaItem;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTPragmaItems;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTPrimaryExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTProgram;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTPropertyOperator;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTPropertyOrArguments;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTQualifiedIdentifier;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTRelOp;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTRelationalExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTRelationalExpressionNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTReservedNamespace;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTRestParameters;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTResult;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTReturnStatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTSc;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTShiftExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTShiftOp;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTSimpleQualifiedIdentifier;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTSimpleVariableDefinition;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTStatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTSubstatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTSubstatements;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTSuperExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTSuperStatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTSwitchStatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTThrowStatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTTryStatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTTypeExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTTypeExpressionList;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTTypeExpressionNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTTypedIdentifier;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTTypedIdentifierNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTUnaryExpression;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTUntypedVariableBinding;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTUntypedVariableBindingList;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTUseDirective;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTVariableBinding;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTVariableBindingList;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTVariableBindingListNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTVariableBindingNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTVariableDefinition;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTVariableDefinitionKind;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTVariableDefinitionNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTVariableInitialisation;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTVariableInitialisationNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTVariableInitializer;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTVariableInitializerNoIN;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTWhileStatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.ASTWithStatement;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.JSParser20Visitor;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.SimpleNode;
import org.apache.myfaces.trinidadbuild.plugin.javascript.javascript20parser.Token;

public class DepthFirstTraversal implements JSParser20Visitor
{
  protected PrintStream out;
  private boolean _debug;
  private int depth;
  private HashMap<String, String> nameTable;

  public DepthFirstTraversal()
  {
    out = System.out;
    _debug = true;
    depth = 0;
  }


  public DepthFirstTraversal(PrintStream o)
  {
    out = o;
    _debug = true;
    depth = 0;
  }

  public DepthFirstTraversal(PrintStream o, HashMap<String, String> tMap)
  {
    out = o;
    _debug = true;
    depth = 0;
    nameTable = tMap;
  }


  public Object depthFirstTraversal(SimpleNode node, Object data)
  {
    depth++;

    Token t1 = node.getFirstToken();
    Token t = new Token();
    t.next = t1;

    SimpleNode n;

    for (int ord = 0; ord < node.jjtGetNumChildren(); ord++)
    {
      //
      //  Get the next chid node in sequence.
      //
      n = (SimpleNode) node.jjtGetChild(ord);

      //
      // Loop through all the Tokens that are attached to this node.
      //
      if (_debug == true)
      {
        while (true)
        {
          t = t.next;

          if (t == n.getFirstToken()) break;

          print(t);
        }
      }

      //
      // Depth Fist search so visit this nodes children before siblings
      //
      n.jjtAccept(this, data);

      t = n.getLastToken();
    }

    if (_debug == true)
    {
      while (t != node.getLastToken())
      {
        if (t.next == null) {
            break;
        }
        t = t.next;
        print(t);
      }
    }

    depth--;

    return data;
  }

  protected void printNode(SimpleNode node)
  {
    Token t = node.getFirstToken();

    while (t != node.getLastToken())
    {
      print(t);
      t = t.next;
    }

    print(node.getLastToken());
  }

  protected void print(Token t)
  {
    //
    //  Hack Alert: Due to an error with the leaf node ASTEolWithComments
    //  Wich only matches special and no regular tokens thus we get the next
    //  valid leafs token stuck in here. Thus we get duplicate info in each file.
    //  Fixed this with simple flag to recognizes if the token has already been 
    //  written out.Need to refactor that terminal.
    //
    if (t.hasPrinted == true) return;
    
    Token tt = t.specialToken;

    if (tt != null)
    {
      while (tt.specialToken != null)
        tt = tt.specialToken;

      while (tt != null)
      {
        out.print(addUnicodeEscapes(tt.image));
        tt = tt.next;
      }
    }
    //
    //  Check to see if the Token is an identifier kind.
    //
    if (t.kind == 78) 
    {
      //
      //  Potential item to swap out.
      //
      String temp = nameTable.get(t.image);
      if (temp != null) 
      {
        t.image = temp;
      }
    } else if (t.kind == 70 || t.kind == 72 || t.kind == 77) 
    {
      //
      //   Got string that could have a potential class in it (like Eval or 
      //   RegExp so need to scan the string.
      //
      StringTokenizer st;
      if (t.image.charAt(0) == '"' && t.image.length() > 2) 
      {
        st = new StringTokenizer(t.image.substring(1, (t.image.length()-1)));
                                                  
      } else 
      {
        st = new StringTokenizer(t.image);
      }
      StringBuilder replacementString = new StringBuilder(t.image);
      boolean update = false;
      while (st.hasMoreTokens()) {
          String temp1 = st.nextToken();
          String temp = nameTable.get(temp1);
          if (temp != null) 
          {
            int start = replacementString.indexOf(temp1);
            int end = start + temp1.length();
            update = true;
            replacementString.delete(start, end);
            replacementString.insert(start, temp);
          } 
      }
      if (update == true) 
      {
          System.out.println("Old String is:" + t.image);
          t.image = replacementString.toString();
          System.out.println("New String is:" + t.image);
      }
    }
    out.print(addUnicodeEscapes(t.image));
    t.hasPrinted = true;
  }

  private String addUnicodeEscapes(String str)
  {
    String retval = "";
    char ch;

    for (int i = 0; i < str.length(); i++)
    {
      ch = str.charAt(i);

      if (((ch < 0x20) || (ch > 0x7e)) && (ch != '\t') && (ch != '\n') &&
          (ch != '\r') && (ch != '\f'))
      {
        String s = "0000" + Integer.toString(ch, 16);
        retval += ("\\u" + s.substring(s.length() - 4, s.length()));
      }
      else
      {
        retval += ch;
      }
    }

    return retval;
  }

  private String buildPaddedString(String s)
  {
    StringBuffer sb = new StringBuffer();

    for (int count = 0; count <= depth; count++)
    {
      sb = sb.append(" ");
    }

    sb = sb.append(s);

    return sb.toString();
  }

  public Object visit(SimpleNode node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTProgram node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTIdentifier node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTSimpleQualifiedIdentifier node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTExpressionQualifiedIdentifier node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTQualifiedIdentifier node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTPrimaryExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTReservedNamespace node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTFunctionExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTObjectLiteral node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTFieldList node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTLiteralField node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
 }

  public Object visit(ASTFieldName node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTArrayLiteral node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTElementList node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTLiteralElement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTSuperExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTPostfixExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTAttributeExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTFullPostfixExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTFullNewExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTFunctionConstructor node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTFullNewSubexpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTPostfixOp node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTPropertyOrArguments node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTPropertyOperator node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTArguments node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTUnaryExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTMulOp node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTMultiplicativeExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTAddOp node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTAdditiveExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTShiftOp node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTShiftExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTRelOp node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTRelationalExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTRelationalExpressionNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTEqualOp node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTEqualityExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTEqualityExpressionNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTBitwiseANDOp node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTBitwiseANDExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTBitwiseANDExpressionNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTBitwiseXOROp node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTBitwiseXORExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTBitwiseXORExpressionNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTBitwiseOROp node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTBitwiseORExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTBitwiseORExpressionNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTLogicalANDExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTLogicalANDExpressionNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTLogicalORExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTLogicalORExpressionNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTConditionalExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTConditionalExpressionNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTNonAssignmentExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTNonAssignmentExpressionNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTAssignementOperator node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTAssignmentExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTAssignmentExpressionNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTListExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTListExpressionNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTTypeExpression node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTTypeExpressionNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;

  }

  public Object visit(ASTTypeExpressionList node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;

  }

  public Object visit(ASTStatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;

  }

  public Object visit(ASTSubstatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTSubstatements node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTSc node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTEolCommentSkipWs node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTEmptyStatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTExpressionStatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTSuperStatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTBlock node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTLabeledStatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTIfStatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTSwitchStatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTCaseElements node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTCaseElement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTCaseLabel node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTDoStatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTWhileStatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTForStatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTForInitializer node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTForInBinding node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTWithStatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTContinueStatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTBreakStatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTReturnStatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTThrowStatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTTryStatement node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTDirectives node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTDirective node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTAnnotatableDirective node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTAttributes node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTAttribute node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTUseDirective node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTImportDirective node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTIncludeDirective node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTPragma node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTPragmaItems node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTPragmaItem node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTPragmaExpr node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTPragmaArgument node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTExportDefinition node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTExportBindingList node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTExportBinding node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTVariableDefinition node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTVariableDefinitionNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTVariableDefinitionKind node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTVariableBindingList node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTVariableBindingListNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTVariableBinding node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTVariableBindingNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTVariableInitialisation node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTVariableInitialisationNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTVariableInitializer node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTVariableInitializerNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTTypedIdentifier node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTTypedIdentifierNoIN node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTSimpleVariableDefinition node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTUntypedVariableBindingList node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTUntypedVariableBinding node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTFunctionDefinition node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTFunctionName node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTFunctionCommon node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTParameters node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTParameter node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTParameterInit node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTRestParameters node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTResult node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTClassDefinition node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTInterfaceDefinition node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTInheritance node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTNamespaceDefinition node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTPackageDefinition node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTPackageName node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }

  public Object visit(ASTPackageIdentifiers node, Object data)
  {
    depthFirstTraversal(node, data);

    return data;
  }
}

