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
package org.apache.myfaces.buildtools.maven2.plugin.javascript.javascriptcompiler;

import java.io.PrintStream;

import java.util.HashMap;

import java.util.StringTokenizer;

import org.apache.myfaces.buildtools.maven2.plugin.javascript.javascript20parser.*;

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

