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
package org.apache.myfaces.custom.dynaForm.lib;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class DefaultValueMap<K, V> implements Map<K, V>, Serializable
{
	private static final long serialVersionUID = -1311019539599397096L;
	
	private final Map<K, V> original;
	private final DefaultValueFactory<V> defaultValueFactory;

	public static interface DefaultValueFactory<V> extends Serializable
	{
		public V create();
	}
	
	public DefaultValueMap(Map<K, V> original, DefaultValueFactory<V> defaultValueFactory)
	{
		this.original = original;
		this.defaultValueFactory = defaultValueFactory;
	}

	public void clear()
	{
		original.clear();
	}

	public boolean containsKey(Object key)
	{
		return original.containsKey(key);
	}

	public boolean containsValue(Object value)
	{
		return original.containsValue(value);
	}

	public Set<Entry<K, V>> entrySet()
	{
		return original.entrySet();
	}

	public boolean equals(Object o)
	{
		return original.equals(o);
	}

	@SuppressWarnings("unchecked")
	public V get(Object key)
	{
		V ret = original.get(key);
		if (ret == null)
		{
			ret = defaultValueFactory.create();
			original.put((K) key, ret);
		}
		
		return ret;
	}

	public int hashCode()
	{
		return original.hashCode();
	}

	public boolean isEmpty()
	{
		return original.isEmpty();
	}

	public Set<K> keySet()
	{
		return original.keySet();
	}

	public V put(K key, V value)
	{
		return original.put(key, value);
	}

	public void putAll(Map<? extends K, ? extends V> t)
	{
		original.putAll(t);
	}

	public V remove(Object key)
	{
		return original.remove(key);
	}

	public int size()
	{
		return original.size();
	}

	public Collection<V> values()
	{
		return original.values();
	}
}
