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

package org.apache.myfaces.renderkit.html.util;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.apache.myfaces.shared_tomahawk.renderkit.html.util.JavascriptUtils;

/**
 * This Phase listener is necessary for the autoscroll feature.
 * Its only purpose is to determine the former viewId and store it in request scope
 * so that we can later determine if a navigation has happened during rendering.
 */
public class AutoScrollPhaseListener
        implements PhaseListener
{
	private static final long serialVersionUID = -1087143949215838058L;

	public PhaseId getPhaseId()
    {
        return PhaseId.RESTORE_VIEW;
    }

    public void beforePhase(PhaseEvent event)
    {
    }

    public void afterPhase(PhaseEvent event)
    {
        if(event != null)
        {
            FacesContext facesContext = event.getFacesContext();
            UIViewRoot view = facesContext.getViewRoot();
            if(view != null)
            {
                String viewId = view.getViewId();
                if (viewId != null)
                {
                    JavascriptUtils.setOldViewId(facesContext.getExternalContext(), viewId);
                }   
            }
        }
    }

}
