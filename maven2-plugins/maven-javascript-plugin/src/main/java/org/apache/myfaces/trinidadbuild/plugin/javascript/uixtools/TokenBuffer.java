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

/**
 * A buffer to hold Token objects. Tokens can be read from and written to this
 * buffer as if it were a queue. it is thread safe. Best if a single thread is
 * reading and a single thread is writing.
 * @version $Name:  $ ($Revision$) $Date$
 */
public class TokenBuffer extends Queue implements TokenReader
{

  /**
   * @param bufferSize the maximum size of this buffer.
   */
  public TokenBuffer(int bufferSize)
  {
    super(bufferSize);
  }

  public TokenBuffer()
  {
    this(100);
  }

  /**
   * reads a Token from this buffer. This method blocks until data is available
   * @return null if there is no more data and this buffer has been closed.
   * @see TokenReader
   */
  public synchronized Token read() throws IOException, InterruptedException
  {
    Token tok;
    try
    {
      tok = (Token) super.remove();
    }
    catch (IllegalStateException e)
    {
      tok = null;
    }

    if (tok==_EXCEPTION_TOKEN)
    {
      throw _getException();
    }
    return tok;
  }

  /**
   * This method blocks if the buffer is full.
   * @param tok the token to write to this buffer
   */
  public synchronized void write(Token tok) throws InterruptedException
  {
    super.add(tok);
  }

  public synchronized void write(IOException e) throws InterruptedException
  {
    _setException(e);
    write(_EXCEPTION_TOKEN);
    close();
  }

  private synchronized void _setException(IOException e)
  {
    _exception = e;
  }

  private synchronized IOException _getException()
  {
    return _exception;
  }

  private IOException _exception = null;

  private static final Token _EXCEPTION_TOKEN = new Token(-1, 0);
}