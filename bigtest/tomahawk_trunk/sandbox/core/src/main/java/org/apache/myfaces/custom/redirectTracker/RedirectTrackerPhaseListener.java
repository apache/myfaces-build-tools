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
package org.apache.myfaces.custom.redirectTracker;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * reinitialize the system after a tracked redirect
 */
public class RedirectTrackerPhaseListener implements PhaseListener
{
	public void afterPhase(PhaseEvent event)
	{
		if (event.getPhaseId().equals(PhaseId.RESTORE_VIEW))
		{
			RedirectTrackerManager manager = RedirectTrackerManager.getInstance(event.getFacesContext());
			if (manager != null)
			{
				manager.processTrackedRequest(event.getFacesContext());
			}
		}
	}

	public void beforePhase(PhaseEvent event)
	{
		if (event.getPhaseId().equals(PhaseId.RESTORE_VIEW))
		{
			RedirectTrackerManager manager = RedirectTrackerManager.getInstance(event.getFacesContext());
			if (manager != null)
			{
				manager.clearSaveStateBean();
			}
		}
	}

	public PhaseId getPhaseId()
	{
		return PhaseId.ANY_PHASE;
	}
}
