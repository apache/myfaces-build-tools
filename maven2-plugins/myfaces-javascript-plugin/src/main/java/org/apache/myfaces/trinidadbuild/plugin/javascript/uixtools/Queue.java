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

import java.util.List;

/**
 * Implements a first-in-first-out (FIFO) queue. Typically, one thread will add
 * elements to this queue, and another will remove elements from this queue.
 * This class is thread safe.
 * @version $Name:  $ ($Revision$) $Date$
 */
public class Queue
{
  /**
   * @param size the maximum size of this queue
   */
  public Queue(int size)
  {
    if (size<=0)
      throw new IllegalArgumentException("size is nonpositive:"+size);
    _buf = new Object[size];
  }

  /**
   * @return the number of elements in this queue. This will never be larger
   * than the {@link #Queue(int) maximum size} of this queue.
   */
  public final synchronized int size()
  {
    return _size;
  }

  /**
   * @return true if the queue has been closed.
   * @see #close()
   */
  public final synchronized boolean isClosed()
  {
    return _closed;
  }

  /**
   * closes this queue. Any consequent {@link #add(Object)} method calls will
   * fail.  All {@link #get()} operations will succeed until the queue is
   * empty. This method may be called multiple times.
   * @see #isClosed()
   */
  public synchronized void close()
  {
    _closed = true;
    notifyAll();
  }

  /**
   * @return true if the queue is full and a call to {@link #add(Object)}
   * would block.
   */
  public final boolean isFull()
  {
    return (size() == _buf.length);
  }

  /**
   * @return true if the queue is empty and a call to {@link #get()}
   * would block.
   */
  public final boolean isEmpty()
  {
    return (size() == 0);
  }

  /**
   * This method blocks until space is available in this queue.
   * @param obj the Object to add to the end of this queue. null is permitted.
   * @exception InterruptedException if the current thread is interrupted.
   * @exception IllegalStateException if queue is closed.
   * @see #close()
   * @see #remove()
   */
  public synchronized void add(Object obj)
    throws InterruptedException, IllegalStateException
  {
    for(;isFull() && (!isClosed());)
    {
      //ystem.out.println("waiting to add. size");
      wait();
    }
    //ystem.out.println("adding. size:"+size());

    _checkIsClosed();

    _buf[_head] = obj;
    _head = _incIndex(_head);
    _size++;
    // yes, we are waking up all threads, including those that are
    // waiting to do an add. This may be inefficient.
    // note that we must do notifyAll() and not notify()
    notifyAll();
  }

  /**
   * This method blocks until some element is added to this queue, or the queue
   * is closed.
   * @return removes and returns the Object at the front of this queue.
   * null may be returned if null was added using {@link #add(Object)}
   * @exception InterruptedException if the current thread is interrupted.
   * @exception IllegalStateException if queue is closed.
   * @see #close()
   * @see #add(Object)
   * @see #remove(LIst,int)
   */
  public synchronized Object remove()
    throws InterruptedException, IllegalStateException
  {
    for(;isEmpty();)
    {
      _checkIsClosed();
      wait();
    }

    Object res = _buf[_tail];
    _buf[_tail] = null; // allow garbage collect
    _tail = _incIndex(_tail);
    _size--;

    // yes, we are waking up all threads, including those that are
    // waiting to do a remove. This may be inefficient.
    // note that we must do notifyAll() and not notify()
    notifyAll();
    return res;
  }

  /**
   * Removes multiple elements. This method will block until there is something
   * to remove.
   * @param collector all the elements removed from this queue are added to
   * the end of this List.
   * @param count the maximum number of elements to remove. If this is zero,
   * then it defaults to the maximum size of this queue.
   * @return the number of elements actually removed.
   * @see #remove()
   */
  public synchronized int remove(List collector, int count)
    throws InterruptedException, IllegalStateException
  {
    collector.add(remove());

    int sz = size()+1;
    if ((count == 0) || (count > sz))
      count = sz;
    else if (count < 0)
      throw new IllegalArgumentException("count is negative");

    int read = 1;
    try
    {
      for(;read < count; read++)
      {
        collector.add(remove());
      }
    }
    catch(IllegalStateException e)
    {
      // this should not happen unless the user has subclassed remove() and
      // done something weird
    }
    catch(InterruptedException e)
    {
      // this should not happen unless the user has subclassed remove() and
      // done something weird

      // mark this thread as interrupted, so that it doesn't block again.
      Thread.currentThread().interrupt();
    }
    return read;
  }

  private int _incIndex(int index)
  {
    index++;
    return (index < _buf.length) ? index : 0;
  }

  private void _checkIsClosed()
  {
    if (isClosed())
      throw new IllegalStateException("Queue has been closed");
  }

  private final Object[] _buf;
  private boolean _closed = false;
  private int _size = 0, _head = 0, _tail = 0;
}