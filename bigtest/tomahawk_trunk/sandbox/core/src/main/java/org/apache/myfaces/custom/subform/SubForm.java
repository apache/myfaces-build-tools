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
package org.apache.myfaces.custom.subform;

import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import java.util.Iterator;
import java.util.List;

/**A SubForm which will allow for partial validation
 * and model update.
 *
 * Components will be validated and updated only if
 * either a child-component of this form caused
 * the submit of the form, or an extended commandLink
 * or commandButton with the actionFor attribute set
 * to the client-id of this component was used.
 *
 * You can have several comma-separated entries in
 * the actionFor-attribute - with this it's possible to
 * validate and update more than one subForm at once.
 *
 *
 *
 * @JSFComponent
 *   name = "s:subForm"
 *   tagClass = "org.apache.myfaces.custom.subform.SubFormTag"
 *   
 * @author Gerald Muellan
 * @author Martin Marinschek
 *         Date: 19.01.2006
 *         Time: 13:58:18
 */
public class SubForm extends UIComponentBase
                     implements NamingContainer
{

    public static final String COMPONENT_TYPE = "org.apache.myfaces.SubForm";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.SubForm";
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.SubForm";

    private static final String PARTIAL_ENABLED = "org.apache.myfaces.IsPartialPhaseExecutionEnabled";
    private boolean _submitted;

	private Boolean preserveSubmittedValues;

	public SubForm()
    {
        super.setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public boolean isSubmitted()
    {
        return _submitted;
    }

    public void setSubmitted(boolean submitted)
    {
        _submitted = submitted;
    }

	public Boolean getPreserveSubmittedValues()
	{
		if (preserveSubmittedValues != null)
		{
			return preserveSubmittedValues;
		}

		ValueBinding vb = getValueBinding("preserveSubmittedValues");

		return (Boolean) ((vb != null) ? vb.getValue(getFacesContext()) : null);
	}

	public void setPreserveSubmittedValues(Boolean preserveSubmittedValues)
	{
		this.preserveSubmittedValues = preserveSubmittedValues;
	}

	public Object saveState(FacesContext context)
	{
		return new Object[]
		{
			super.saveState(context),
			preserveSubmittedValues
		};
	}

	public void restoreState(FacesContext context, Object state)
	{
		Object[] states = (Object[]) state;

		super.restoreState(context, states[0]);
		preserveSubmittedValues=(Boolean)states[1];
	}


	public void processDecodes(FacesContext context)
	{
		super.processDecodes(context);
		if (!isRendered()) return;

		if (!_submitted && Boolean.FALSE.equals(getPreserveSubmittedValues()))
		{
			// didn't find any better way as we do not know if we are submitted before the
			// decode phase, but then all the other components have the submitted value
			// set already.
			// so lets reset them again ... boring hack.
			resetSubmittedValues(this, context);
		}
	}

	public void processValidators(FacesContext context)
    {
		if (context == null) throw new NullPointerException("context");
		if (!isRendered()) return;

		boolean partialEnabled = isPartialEnabled(context, PhaseId.PROCESS_VALIDATIONS);

		if(partialEnabled || (_submitted && isEmptyList(context)))
		{
			for (Iterator it = getFacetsAndChildren(); it.hasNext(); )
			{
				UIComponent childOrFacet = (UIComponent)it.next();
				childOrFacet.processValidators(context);
			}
		}
		else
		{
			processSubFormValidators(this,context);
		}
    }

    public void processUpdates(FacesContext context)
    {
        if (context == null) throw new NullPointerException("context");
        if (!isRendered()) return;

        boolean partialEnabled = isPartialEnabled(context,PhaseId.UPDATE_MODEL_VALUES);

        if(partialEnabled || _submitted)
        {
            for (Iterator it = getFacetsAndChildren(); it.hasNext(); )
            {
                UIComponent childOrFacet = (UIComponent)it.next();
                childOrFacet.processUpdates(context);
            }
        }
        else
        {
            processSubFormUpdates(this,context);
        }
    }

	private static void resetSubmittedValues(UIComponent comp, FacesContext context)
	{
		for (Iterator it = comp.getFacetsAndChildren(); it.hasNext(); )
		{
			UIComponent childOrFacet = (UIComponent)it.next();
			if (childOrFacet instanceof SubForm)
			{
				// we are not responsible for this subForm, are we?
				continue;
			}

			if (childOrFacet instanceof EditableValueHolder)
			{
				((EditableValueHolder) childOrFacet).setSubmittedValue(null);
			}

			resetSubmittedValues(childOrFacet, context);
		}
	}

	private static void processSubFormUpdates(UIComponent comp, FacesContext context)
    {
        for (Iterator it = comp.getFacetsAndChildren(); it.hasNext(); )
        {
            UIComponent childOrFacet = (UIComponent)it.next();

            if(childOrFacet instanceof SubForm)
            {
                childOrFacet.processUpdates(context);
            }
            else
            {
                processSubFormUpdates(childOrFacet, context);
            }
        }
    }

    private static void processSubFormValidators(UIComponent comp, FacesContext context)
    {
        for (Iterator it = comp.getFacetsAndChildren(); it.hasNext(); )
        {
            UIComponent childOrFacet = (UIComponent)it.next();

            if(childOrFacet instanceof SubForm)
            {
                childOrFacet.processValidators(context);
            }
            else
            {
                processSubFormValidators(childOrFacet, context);
            }
        }
    }

    public void queueEvent(FacesEvent event)
    {
        if(event instanceof ActionEvent)
        {
            _submitted = true;
        }

        // This idea is taken from ADF faces - my approach of checking for instanceof ActionEvent
        // didn't go as far as necessary for dataTables.
        // In the dataTable case, the ActionEvent is wrapped in an EventWrapper
        //
        // I still believe the second part of the if condition is a hack:
        // If the event is being queued for anything *after* APPLY_REQUEST_VALUES,
        // then this subform is active - IMHO there might be other events not relating
        // to the action system which are queued after this phase.
        if (PhaseId.APPLY_REQUEST_VALUES.compareTo(event.getPhaseId()) < 0)
        {
            setSubmitted(true);
        }

        super.queueEvent(event);
    }

    protected boolean isEmptyList(FacesContext context)
    {
        //get the list of (parent) client-ids for which a validation/model update should be performed
        List li = (List) context.getExternalContext().getRequestMap().get(
                RendererUtils.ACTION_FOR_LIST);

        return li==null || li.size()==0;
    }

    /**Sets up information if this component is included in
     * the group of components which are associated with the current action.
     *
     * @param context
     * @return true if there has been a change by this setup which has to be undone after the phase finishes.
     */
    protected boolean isPartialEnabled(FacesContext context, PhaseId phaseId)
    {
        //we want to execute validation (and model update) only
        //if certain conditions are met
        //especially, we want to switch validation/update on/off depending on
        //the attribute "actionFor" of a MyFaces extended button or link
        //if you use commandButtons which don't set these
        //request parameters, this won't cause any adverse effects

        boolean partialEnabled = false;

        //get the list of (parent) client-ids for which a validation/model update should be performed
        List li = (List) context.getExternalContext().getRequestMap().get(
                RendererUtils.ACTION_FOR_LIST);

        //if there is a list, check if the current client id
        //matches an entry of the list
        if(li != null && li.contains(getClientId(context)))
        {
            if(!context.getExternalContext().getRequestMap().containsKey(PARTIAL_ENABLED))
            {
                partialEnabled=true;
            }
        }

        if(partialEnabled)
        {
            //get the list of phases which should be executed
            List phaseList = (List) context.getExternalContext().getRequestMap().get(
                RendererUtils.ACTION_FOR_PHASE_LIST);

            if(phaseList != null && !phaseList.isEmpty() && !phaseList.contains(phaseId))
            {
                partialEnabled=false;
            }
        }

        return partialEnabled;
    }


}
