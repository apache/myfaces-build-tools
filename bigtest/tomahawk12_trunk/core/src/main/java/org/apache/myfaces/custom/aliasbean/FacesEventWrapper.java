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
package org.apache.myfaces.custom.aliasbean;

import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;
import javax.faces.component.UIComponent;

/**
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

class FacesEventWrapper extends FacesEvent {
    private static final long serialVersionUID = -6878195444276533114L;
    private FacesEvent _wrappedFacesEvent;

    public FacesEventWrapper(FacesEvent facesEvent, UIComponent redirectComponent) {
        super(redirectComponent);
        _wrappedFacesEvent = facesEvent;
    }

    public PhaseId getPhaseId() {
        return _wrappedFacesEvent.getPhaseId();
    }

    public void setPhaseId(PhaseId phaseId) {
        _wrappedFacesEvent.setPhaseId(phaseId);
    }

    public void queue() {
        _wrappedFacesEvent.queue();
    }

    public String toString() {
        return _wrappedFacesEvent.toString();
    }

    public boolean isAppropriateListener(FacesListener faceslistener) {
        return _wrappedFacesEvent.isAppropriateListener(faceslistener);
    }

    public void processListener(FacesListener faceslistener) {
        _wrappedFacesEvent.processListener(faceslistener);
    }

    public FacesEvent getWrappedFacesEvent() {
        return _wrappedFacesEvent;
    }
}