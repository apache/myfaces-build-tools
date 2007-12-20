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

import java.io.BufferedReader;
import java.io.IOException;

/**
 * A Tokenizer for JavaScript source files.
 * @version $Name:  $ ($Revision$) $Date$
 */
public class Tokenizer implements TokenReader
{

  /**
   * @param in used to read data from the JS file
   */
  public Tokenizer(BufferedReader in)
  {
    _in = in;
    Runnable runner = new Runnable()
      {
        public void run()
        {
          //ystem.out.println("Tokenizer: start:"+Thread.currentThread());
          _run();
          //ystem.out.println("Tokenizer: end:"+Thread.currentThread());
        }
      };
    new Thread(runner).start();
  }

  /**
   * reads a Token. blocks until a Token is available.
   * @return null if the EOF is reached.
   */
  public Token read() throws IOException, InterruptedException
  {
    return _out.read();
  }

  private void _run()
  {
    try
      {
        _run2();
      }
    catch (Exception e)
      {
        System.out.println("Exception parsing line:"+_lineNumber);
        e.printStackTrace();
      }
  }

  private void _run2() throws InterruptedException
  {
    try
    {
      for(;_fillBuffer();)
      {
        //ystem.out.println("begin process");
        _processBuffer();
        //ystem.out.println("end process");
      }
      _out.write(new Token(Token.EOF, _lineNumber));
    }
    catch (IOException e)
    {
      System.out.println("Exception parsing line:"+_lineNumber);
      _out.write(e);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      _out.write(new IOException("Exception parsing line:"+_lineNumber));
    }
    finally
    {
      _out.close();
    }
  }

  private void _processBuffer() throws InterruptedException
  {
    _offset = 0;

    for(;_offset<_len;)
      {
        //ystem.out.println("line:"+_lineNumber+" offset:"+_offset);
        char ch = _buffer.charAt(_offset++);
        switch(_status)
          {
          case ROOT_MODE :
          case DIVISION_MODE :
            _next = _rootMode(ch, _status);
            break;
          case READ_WORD_MODE :
            _next = _readWordMode(ch, _status, _str);
            break;
          case QUOTE1_MODE :
          case QUOTE2_MODE :
            _next = _quoteMode(ch, _status, _prev, _str);
            break;
          case ESCAPED_CHAR_MODE :
            _str.append('\\');
            _str.append(ch);
            _next = _prev;
            break;
          case POSSIBLE_COMMENT_MODE :
            if (ch=='/') _next = COMMENT1_MODE;
            else if (ch=='*') _next = COMMENT2_MODE;
            else
              {
                _offset--; //Roll Back one
                if (_prev == DIVISION_MODE)
                  {
                    _writeControl('/');
                    _next = ROOT_MODE;
                  }
                else
                  {
                    _next = REGULAR_EXP_MODE;
                  }
              }
            break;
          case COMMENT1_MODE :
          case COMMENT2_MODE :
          case END_COMMENT_MODE :
            _next = _commentMode(ch, _status, _str);
            break;
          case REGULAR_EXP_MODE :
            _next = _regularExpMode(ch, _str);
            break;
          case END_REGULAR_EXP_MODE :
            if ((ch=='g') || (ch=='i'))
              {
                _out.write(new Token(Token.REGULAR_EXP_MODIFIER,
                                     _lineNumber,
                                     ch));
                _next = END_REGULAR_EXP_MODE;
              }
            else
              {
                _offset--; //Rollback
                _next = ROOT_MODE;
              }
            break;
          }
        _prev = _status;
        _status = _next;
      }
  }

  private int _rootMode(char ch, int status) throws InterruptedException
  {
    switch(ch)
      {
      case '\'' :
        return QUOTE1_MODE;
      case '\"' :
        return QUOTE2_MODE;
      case ' '  :
      case '\t' :
        _out.write(WHITESPACE);
        return status;
      case '\n' :
        _out.write(NEWLINE);
        return ROOT_MODE;
      case '.' :
        _out.write(PERIOD);
        return status;
      case ';' :
        _out.write(SEMICOLON);
        return ROOT_MODE;
      case '(' :
      case '{' :
      case '[' :
        _out.write(new Token(Token.LEFT_BRACE, _lineNumber, ch));
        return ROOT_MODE;
      case ')' :
        _out.write(new Token(Token.RIGHT_BRACE, _lineNumber, ch));
        return DIVISION_MODE;
      case '}' :
      case ']' :
        _out.write(new Token(Token.RIGHT_BRACE, _lineNumber, ch));
        return ROOT_MODE;
      case '/'  : return POSSIBLE_COMMENT_MODE;
      default :
        if (_isAlphaNumeric(ch))
          {
            _offset--; //Rollback
            return READ_WORD_MODE;
          }
        else
          {
            _writeControl(ch);
            return ROOT_MODE;
          }
      }
  }

  private int _regularExpMode(char ch, StringBuffer regExp)
    throws InterruptedException
  {
    switch(ch)
      {
      case '\\' :
        return ESCAPED_CHAR_MODE;
      case '/' :
        _out.write(new Token(Token.REGULAR_EXP,
                             _lineNumber,
                             regExp.toString()));
        regExp.setLength(0);
        return END_REGULAR_EXP_MODE;
      default :
        regExp.append(ch);
        return REGULAR_EXP_MODE;
      }
  }

  private int _quoteMode(char ch, int status, int prev,
                         StringBuffer quoteString) throws InterruptedException
  {
    if (((ch=='\'') && (status==QUOTE1_MODE)) ||
        ((ch=='\"') && (status==QUOTE2_MODE)))
      {
        _out.write(new Token(Token.QUOTED,
                             _lineNumber, ch, quoteString.toString()));
        quoteString.setLength(0);
        return ROOT_MODE;
      }
    else if (ch=='\\') return ESCAPED_CHAR_MODE;
    else
      {
        quoteString.append(ch);
        return status;
      }
  }

  private int _commentMode(char ch, int status,
                           StringBuffer commentString)
    throws InterruptedException
  {
    if (status==END_COMMENT_MODE)
      {
        if (ch=='/')
          {
            _writeComment(commentString);
            return ROOT_MODE;
          }
        else
          {
            commentString.append('*');
            _offset--; //Roll back
            return COMMENT2_MODE;
          }
      }
    else if ((status==COMMENT2_MODE) && (ch=='*'))
      {
        return END_COMMENT_MODE;
      }
    else if ((status==COMMENT1_MODE) && (ch=='\n'))
      {
        _writeComment(commentString);
        _out.write(NEWLINE);
        return ROOT_MODE;
      }
    else
      {
        commentString.append(ch);
        return status;
      }
  }

  private int _readWordMode(char ch, int status, StringBuffer wordBuffer)
    throws InterruptedException
  {
    if (_isAlphaNumeric(ch))
      {
        wordBuffer.append(ch);
        return status;
      }
    else
      {
        _writeAlphaNumeric(wordBuffer.toString());
        wordBuffer.setLength(0);
        _offset--; //Rollback
        return DIVISION_MODE;
      }
  }

  private void _writeComment(StringBuffer s) throws InterruptedException
  {
    _out.write(new Token(Token.COMMENT, _lineNumber, s.toString()));
    s.setLength(0);
  }

  private void _writeControl(char ch) throws InterruptedException
  {
    _out.write(new Token(Token.CONTROL, _lineNumber, ch));
  }

  private void _writeAlphaNumeric(String s) throws InterruptedException
  {
    if (Character.isDigit(s.charAt(0)))
      {
        _out.write(new Token(Token.NUMBER, _lineNumber, s));
      }
    else if (_isReservedKeyword(s))
      {
        _out.write(new Token(Token.RESERVED, _lineNumber, s));
      }
    else
      {
        _out.write(new Token(Token.NAME, _lineNumber, s));
      }
  }

  private boolean _isReservedKeyword(String s)
  {
    for(int i=0; i<reservedWords.length; i++)
      {
        if (s.equals(reservedWords[i])) return true;
      }
    return false;
  }

  private boolean _isAlphaNumeric(char ch)
  {
    return Character.isLetterOrDigit(ch) || (ch=='_') ;
  }

  /**
   * @return false if EOF
   */
  private boolean _fillBuffer() throws IOException
  {
    _buffer.setLength(0);
    String s = _in.readLine();
    if (s==null)
      {
        _len=0;
        return false;
      }
    else
      {
        _buffer.append(s).append('\n');
        _len = _buffer.length();
        _lineNumber++;
        return true;
      }
  }

  private void _reset()
  {
    _status = _prev = _next = ROOT_MODE;
    _buffer.setLength(0);
    _len = _lineNumber = _offset = 0;
    _wordBuffer.setLength(0);
    _str.setLength(0);
  }

  private int _status = ROOT_MODE;
  private int _prev = ROOT_MODE;
  private int _next = ROOT_MODE;

  private int _len = 0;
  private int _lineNumber = 0;
  private int _offset = 0;

  private final StringBuffer _buffer = new StringBuffer();
  private final StringBuffer _wordBuffer = new StringBuffer();
  private final StringBuffer _str = new StringBuffer();

  private final BufferedReader _in;
  private final TokenBuffer _out = new TokenBuffer();

  /**
   * These are not all the reserved words in JS but are the only ones
   * important to the filters.
   */
  private static final String[] reservedWords =
  {
    "function", "var"
  };

  private final Token NEWLINE = new Token(Token.NEWLINE, 0);
  private final Token WHITESPACE = new Token(Token.WHITESPACE, 0);
  private final Token PERIOD = new Token(Token.PERIOD, 0);
  private final Token SEMICOLON = new Token(Token.SEMICOLON, 0);

  /**
   * These are the FSM states
   */
  private static final int ROOT_MODE = 0;
  private static final int READ_WORD_MODE = 10;
  private static final int DIVISION_MODE = 15;
  private static final int QUOTE1_MODE = 20;
  private static final int QUOTE2_MODE = 30;
  private static final int ESCAPED_CHAR_MODE = 40;
  private static final int POSSIBLE_COMMENT_MODE = 50;
  private static final int COMMENT1_MODE = 60;
  private static final int COMMENT2_MODE = 70;
  private static final int END_COMMENT_MODE = 80;
  private static final int REGULAR_EXP_MODE = 90;
  private static final int END_REGULAR_EXP_MODE = 95;
}