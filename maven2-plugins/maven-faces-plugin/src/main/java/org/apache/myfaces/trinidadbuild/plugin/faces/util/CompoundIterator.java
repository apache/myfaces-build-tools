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
package org.apache.myfaces.trinidadbuild.plugin.faces.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * CompoundIterator consumes the initial iterator before
 * observing the next iterator.
 */
public class CompoundIterator implements Iterator
{
  public CompoundIterator(
    Iterator primary,
    Iterator secondary)
  {
    this(new Iterator[] {primary, secondary});
  }

  public CompoundIterator(
    Iterator[] iterators)
  {
    _iterators = iterators;
    _index = 0;
    _advance();
  }

  public boolean hasNext()
  {
    return (_next != null);
  }

  public Object next()
  {
    if (_next == null)
      throw new NoSuchElementException();

    Object obj = _next;
    _advance();
    return obj;
  }

  public void remove()
  {
    throw new UnsupportedOperationException();
  }

  private void _advance()
  {
    Iterator current = _iterators[_index];
    if (current != null && !current.hasNext())
    {
      _index ++;
      current = (_index < _iterators.length) ? _iterators[_index] : null;
    }

    _next = (current != null && current.hasNext()) ? current.next() : null;
  }

  private Object _next;
  private Iterator[] _iterators;
  private int _index;
}
