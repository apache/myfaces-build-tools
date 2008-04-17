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

package javax.faces.event;

import java.util.EventObject;

import javax.faces.component.UIComponent;

/**
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/api/index.html">JSF Specification</a>
 *
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class FacesEvent extends EventObject
{
	// FIELDS
    private PhaseId _phaseId;

	// CONSTRUCTORS
	public FacesEvent(UIComponent uiComponent)
	{
        super(uiComponent);
        if (uiComponent == null) throw new IllegalArgumentException("uiComponent");
        _phaseId = PhaseId.ANY_PHASE;
	}

	// METHODS
    public abstract boolean isAppropriateListener(FacesListener faceslistener);

    public abstract void processListener(FacesListener faceslistener);

	public UIComponent getComponent()
	{
        return (UIComponent)getSource();
	}

	public void queue()
	{
        ((UIComponent)getSource()).queueEvent(this);
	}

    public PhaseId getPhaseId()
    {
        return _phaseId;
    }

    public void setPhaseId(PhaseId phaseId)
    {
        _phaseId = phaseId;
    }
}
