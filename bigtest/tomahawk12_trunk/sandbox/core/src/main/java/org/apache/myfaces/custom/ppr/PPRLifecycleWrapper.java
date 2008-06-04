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
package org.apache.myfaces.custom.ppr;

import javax.faces.FacesException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;

/**
 * A lifecycle wrapper which just decorates the lifecycle passed in to the constructor.
 * On any ppr request this wrapper will wrap the UIViewRoot of the current view to
 * make it possible to process just those components in question (affected components).
 */
public class PPRLifecycleWrapper extends Lifecycle
{
    private final Lifecycle delegate;

    public PPRLifecycleWrapper(Lifecycle delegate)
    {
        this.delegate = delegate;
    }

    public void addPhaseListener(PhaseListener listener)
    {
        this.delegate.addPhaseListener(listener);
    }

    public void execute(final FacesContext context) throws FacesException
    {
        if (PPRSupport.isPartialRequest(context))
        {
            FacesContext wrappedContext = new PPRFacesContextWrapper(context)
            {
                public UIViewRoot getViewRoot()
                {
                    UIViewRoot viewRoot = context.getViewRoot();
                    if (viewRoot == null)
                    {
                        return null;
                    }

                    return new PPRViewRootWrapper(viewRoot);
                }
            };

            this.delegate.execute(wrappedContext);
        }
        else
        {
            this.delegate.execute(context);
        }
    }

    public PhaseListener[] getPhaseListeners()
    {
        return this.delegate.getPhaseListeners();
    }

    public void removePhaseListener(PhaseListener listener)
    {
        this.delegate.removePhaseListener(listener);
    }

    public void render(FacesContext context) throws FacesException
    {
        this.delegate.render(context);
    }
}
