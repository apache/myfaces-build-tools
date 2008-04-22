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
package org.apache.myfaces.custom.valueChangeNotifier;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * PhaseListener to trigger the fireing of collected valueChange events.
 *
 * @author Mario Ivankovits <imario - at - apache.org>  (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ValueChangePhaseListener implements PhaseListener
{
	public ValueChangePhaseListener()
	{
	}

	public void afterPhase(PhaseEvent event)
	{
		if (ValueChangeManager.hasManager(event.getFacesContext()))
		{
			ValueChangeManager manager = ValueChangeManager.getManager(event.getFacesContext());
			manager.fireEvents(event.getFacesContext());
		}
	}

	public void beforePhase(PhaseEvent event)
	{
	}

	public PhaseId getPhaseId()
	{
		return PhaseId.UPDATE_MODEL_VALUES;
	}
}