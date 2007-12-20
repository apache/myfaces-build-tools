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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

/**
 * SortedIterator presents the elements of another iterator in a sorted order.
 */
public class SortedIterator implements Iterator
{
  public SortedIterator(
    Iterator unsorted)
  {
     // this implementation just pulls the entire contents into a list --
     // it will be awkward if the unsorted iterator is infinite
     ArrayList sortedList = new ArrayList();
     while(unsorted.hasNext())
     {
       sortedList.add(unsorted.next());
     }
     Collections.sort(sortedList);
     _sorted = sortedList.iterator();
  }

  public boolean hasNext()
  {
    return _sorted.hasNext();
  }

  public Object next()
  {
    return _sorted.next();
  }

  public void remove()
  {
    throw new UnsupportedOperationException();
  }

  private final Iterator _sorted;

}
