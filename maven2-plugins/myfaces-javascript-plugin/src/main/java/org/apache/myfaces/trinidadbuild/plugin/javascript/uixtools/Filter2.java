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

import java.io.IOException;

import java.util.Vector;
import java.util.HashMap;

/**
 * Renames local variable names to short ones
 * @version $Name:  $ ($Revision$) $Date$
 */
public class Filter2 implements TokenReader
{

  public Filter2(TokenReader in)
  {
    _in = in;
    Runnable runner = new Runnable()
      {
        public void run()
        {
          try
          {
            //ystem.out.println("Compressor: start:"+Thread.currentThread());
            _run();
            //ystem.out.println("Comressor: end:"+Thread.currentThread());
          }
          catch (InterruptedException e)
          {
            e.printStackTrace();
          }
        }
      };
    new Thread(runner).start();
  }

  /**
   * @see TokenReader#read()
   */
  public Token read() throws IOException, InterruptedException
  {
    return _buffer.read();
  }

  private void _run() throws InterruptedException
  {
    try
    {
      Token cur = _in.read();
      for(;cur!=null;)
      {
        _process(cur);
        cur = _in.read();
      }
    }
    catch (IOException e)
    {
      _buffer.write(e);
    }
    catch (TokenException e)
    {
      e.printStackTrace();
      _buffer.write(new IOException("Error parsing line:"+
                                    e.getToken().lineNumber));
    }
    catch (RuntimeException e)
    {
      e.printStackTrace();
      _buffer.write(new IOException());
    }
    _buffer.close();
  }

  /**
   * renames local variable names to short names.
   * First pass. sets a flag if this function uses the JS eval method.
   */
  private void _process(Token cur) throws InterruptedException
  {
    if ((cur.code == Token.LEFT_BRACE) && (cur.ch == '{'))
      _openCurly++;
    else if ((cur.code == Token.RIGHT_BRACE) && (cur.ch == '}'))
      _openCurly--;


    switch(_state)
    {
    case ROOT_MODE :
      if ((cur.code==Token.RESERVED) && (cur.string.equals("function")))
      {
        _state = FUNCTION_PARAM_MODE;
        _function.clear();
        _isFunctionUsingEval = false;
        _function.add(cur);
      }
      else _buffer.write(cur);
      break;
    case FUNCTION_PARAM_MODE :
      _function.add(cur);
      if ((cur.code==Token.LEFT_BRACE) && (cur.ch=='{'))
      {
        _state = FUNCTION_BODY_MODE;
        _beginFunction = _openCurly;
      }
      break;
    case FUNCTION_BODY_MODE :
      _function.add(cur);
      if (_openCurly<_beginFunction)
      {
        _state = ROOT_MODE;
        if (_isFunctionUsingEval) _writeTokens(_function);
        else
        {
          for(int i=0,sz=_function.size(); i<sz; i++)
          {
            _process2((Token) _function.get(i));
          }
        }
      }
      else if ((cur.code==Token.NAME) && cur.string.equals("eval"))
      {
        _isFunctionUsingEval = true;
      }
      break;
    }
  }

  /**
   * 2nd pass
   * Does the actual renaming
   */
  private void _process2(Token cur) throws InterruptedException
  {
    if ((cur.code == Token.LEFT_BRACE) && (cur.ch == '{'))
      _openCurly++;
    else if ((cur.code == Token.RIGHT_BRACE) && (cur.ch == '}'))
      _openCurly--;

    switch(_state)
    {
    case ROOT_MODE :
      if ((cur.code==Token.RESERVED) && (cur.string.equals("function")))
        _state = FUNCTION_MODE;
      break;
    case FUNCTION_MODE :
      if ((cur.code==Token.LEFT_BRACE) && (cur.ch=='('))
      {
        _state = FUNCTION_PARAM_MODE;
        _localVarMap.clear();
      }
      else if (cur.code==Token.NAME)
      {
        //ystem.out.println("function name;"+cur.string);
        _nameGen.reset();
      }
      break;
    case FUNCTION_PARAM_MODE :
      if ((cur.code==Token.LEFT_BRACE) && (cur.ch=='{'))
      {
        _state = FUNCTION_BODY_MODE;
        _beginFunction = _openCurly;
      }
      else if (cur.code == Token.NAME)
      {
        //ystem.out.println(" param:"+cur.string);
        Token tok = _getNewToken(cur);
        cur = tok;
      }
      break;
    case FUNCTION_BODY_MODE :
      if (_openCurly<_beginFunction)
      {
        _state = ROOT_MODE;
      }
      else if (cur.code==Token.PERIOD)
        _state = PERIOD_MODE;
      else if ((cur.code==Token.RESERVED) && (cur.string.equals("var")))
        _state = VAR_DEF_MODE;
      else if (cur.code==Token.NAME)
      {
        cur = _substForToken(cur);
      }
      break;
    case VAR_DEF_MODE :
      if (cur.code==Token.NAME)
      {
        _state = FUNCTION_BODY_MODE;
        //ystem.out.println(" local var:"+cur.string);
        Token tok = _getNewToken(cur);
        cur = tok;
      }
      break;
    case PERIOD_MODE :
      // this is just to skip the next token after a period.
      _state = FUNCTION_BODY_MODE;
      break;
    }
    _buffer.write(cur);
  }

  private void _writeTokens(Vector tokens) throws InterruptedException
  {
    for(int i=0,sz=tokens.size(); i<sz; i++)
    {
      _buffer.write((Token) tokens.get(i));
    }
  }

  /**
   * @return a new token to replace the old one with. the new one will have
   *  a short name
   */
  private Token _getNewToken(Token oldToken)
  {
    String oldName = oldToken.string;
    String newName = (String) _localVarMap.get(oldName);
    if (newName==null)
    {
      newName = _nameGen.getNext();
      _localVarMap.put(oldName, newName);
    }
    return new Token(oldToken.code, oldToken.lineNumber, newName);
  }

  /**
   * @return the substitute token. if no token has been substituted for the
   *  <code>oldToken</code> then the <code>oldToken</code> is returned.
   * @param oldToken the token to substitute for.
   */
  private Token _substForToken(Token oldToken)
  {
    String oldName = oldToken.string;
    String newName = (String) _localVarMap.get(oldName);
    if (newName==null)
    {
      if (_nameGen.isInUse(oldName))
        throw new TokenException(oldToken,
                                 "Conflict with global var:"+oldName);
      return oldToken;
    }
    Token tok = new Token(oldToken.code, oldToken.lineNumber, newName);
    return tok;
  }

  private class NameGen
  {
    private int _i = 0;
    private char _ch = 'a';
    private final StringBuffer _sb = new StringBuffer();

    /**
     * @return a new unique short name
     */
    public String getNext()
    {
      _sb.setLength(0);
      return _sb.append(_ch).append(_i++).toString();
    }

    public void reset()
    {
      _i = 0;
    }

    /**
     * @return true if <code>varName</code> has already been returned by
     *   the method <code>getNext()</code>
     * @see #getNext()
     */
    public boolean isInUse(String varName)
    {
      int sz=varName.length();
      if ((varName.charAt(0)!=_ch) || (sz<=1)) return false;

      for(int i=1; i<sz; i++)
      {
        if (!Character.isDigit(varName.charAt(i))) return false;
      }
      String ipart = varName.substring(1);
      int j = Integer.parseInt(ipart);
      if (j >= _i) return false;
      else return true;
    }
  }

  private int _state = ROOT_MODE;
  private int _openCurly = 0;
  private int _beginFunction = 0;
  private boolean _isFunctionUsingEval = false;

  private final TokenReader _in;
  private final NameGen _nameGen = new NameGen();
  private final HashMap _localVarMap = new HashMap();
  private final TokenBuffer _buffer = new TokenBuffer();
  private final Vector _function = new Vector();

  private static final int ROOT_MODE =           0;
  private static final int FUNCTION_MODE =       1;
  private static final int FUNCTION_PARAM_MODE = 2;
  private static final int FUNCTION_BODY_MODE =  3;
  private static final int VAR_DEF_MODE =        4;
  private static final int PERIOD_MODE =         5;

}