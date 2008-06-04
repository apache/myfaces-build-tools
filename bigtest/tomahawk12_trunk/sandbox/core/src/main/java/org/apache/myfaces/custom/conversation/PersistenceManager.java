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
package org.apache.myfaces.custom.conversation;

/**
 * the interface to help the conversation framework to deal with your persistence manager (EntityManager)
 * 
 * @author imario@apache.org
 */
public interface PersistenceManager
{
	/**
	 * commit the transaction
	 */
	public void commit();

	/**
	 * rollback the transaction
	 */
	public void rollback();

	/**
	 * attach to your underlaying persistence
	 */
	public void attach();

	/**
	 * detach from your underlaying persistence
	 */
	public void detach();

	/**
	 * purge this persistence manager - e.g. throw away your session due to some exceptions
	 */
	void purge();
}
