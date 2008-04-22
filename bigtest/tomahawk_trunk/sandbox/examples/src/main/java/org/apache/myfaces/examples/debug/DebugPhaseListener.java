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

package org.apache.myfaces.examples.debug;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * shows logger messages for each phase of the FacesLifecycle.
 * @author matzew
 *
 */
public class DebugPhaseListener implements PhaseListener
{

	private static Log log = LogFactory.getLog(DebugPhaseListener.class);
	
	public void afterPhase(PhaseEvent event)
	{
		if(log.isInfoEnabled())
		{
			log.info("AFTER PHASE " + event.getPhaseId());
		}
	}

	public void beforePhase(PhaseEvent event)
	{
		if(log.isInfoEnabled())
		{
			log.info("BEFORE PHASE " + event.getPhaseId());
		}
	
	}

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
}