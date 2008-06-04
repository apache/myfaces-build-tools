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
package org.apache.myfaces.custom.schedule;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.ActionSource;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import org.apache.myfaces.component.MethodBindingToMethodExpression;
import org.apache.myfaces.component.MethodExpressionToMethodBinding;
import org.apache.myfaces.custom.schedule.model.ScheduleDay;
import org.apache.myfaces.custom.schedule.model.ScheduleEntry;

/**
 * This class contains all 'interactive' stuff for the Schedule component, meaning
 * actions and actionListeners.
 * 
 * @JSFComponent
 *   parent = "org.apache.myfaces.custom.schedule.UIScheduleBase"
 * 
 * @author Jurgen Lust
 * @version $Revision$
 */
public class UISchedule extends UIScheduleBase implements
        Serializable, ActionSource
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.UISchedule";
    
    private class ScheduleActionListener implements ActionListener
    {
        //~ Methods ------------------------------------------------------------

        /**
         * @see javax.faces.event.ActionListener#processAction(javax.faces.event.ActionEvent)
         */
        public void processAction(ActionEvent event)
                throws AbortProcessingException
        {
            UISchedule schedule = (UISchedule) event.getComponent();
            ScheduleEntry entry = schedule.getSubmittedEntry();
            schedule.getModel().setSelectedEntry(entry);
            schedule.setSubmittedEntry(null);

            if (schedule.getAction() != null)
            {
                getFacesContext().getApplication().getActionListener()
                        .processAction(event);
            }
        }
    }
    
    private static final long serialVersionUID = -8333458172939036755L;
    //private MethodBinding _action;
    private MethodBinding _actionListener;
    private ScheduleActionListener _scheduleListener;
    private ScheduleEntry _submittedEntry;
    private Date _lastClickedDateAndTime = null;
    



    public UISchedule()
    {
        super();
        _scheduleListener = new ScheduleActionListener();
        addActionListener(_scheduleListener); 
    }

    public void addActionListener(ActionListener listener)
    {
        addFacesListener(listener);
    }

    
    /**
     * @see javax.faces.component.UIComponent#broadcast(javax.faces.event.FacesEvent)
     */
    public void broadcast(FacesEvent event) throws AbortProcessingException
    {
        FacesContext context = getFacesContext();
        //invoke the mouselistener first
        if (event instanceof ScheduleMouseEvent)
        {
            ScheduleMouseEvent mouseEvent = (ScheduleMouseEvent) event;
            MethodBinding mouseListener = getMouseListener();

            if (mouseListener != null)
            {
                mouseListener.invoke(context,
                        new Object[] { mouseEvent });
            }
        }

        //then invode any other listeners
        super.broadcast(event);

        MethodBinding actionListener = getActionListener();

        if (actionListener != null)
        {
            actionListener.invoke(context, new Object[] { event });
        }
    }

    /**
     * Find the entry with the given id
     *
     * @param id the id
     *
     * @return the entry
     */
    protected ScheduleEntry findEntry(String id)
    {
        if (id == null)
        {
            return null;
        }

        for (Iterator dayIterator = getModel().iterator(); dayIterator
                .hasNext();)
        {
            ScheduleDay day = (ScheduleDay) dayIterator.next();

            for (Iterator iter = day.iterator(); iter.hasNext();)
            {
                ScheduleEntry entry = (ScheduleEntry) iter.next();

                if (id.equals(entry.getId()))
                {
                    return entry;
                }
            }
        }

        return null;
    }
    
    /**
     * @deprecated Use setActionExpression instead.
     */
    public void setAction(MethodBinding action)
    {
        if(action != null)
        {
            setActionExpression(new MethodBindingToMethodExpression(action));
        } 
        else
        {
            setActionExpression(null);
        }
    }

    /**
     * @deprecated Use getActionExpression() instead.
     */
    public MethodBinding getAction()
    {
        MethodExpression actionExpression = getActionExpression();
        if (actionExpression instanceof MethodBindingToMethodExpression) {
            return ((MethodBindingToMethodExpression)actionExpression).getMethodBinding();
        }
        if(actionExpression != null)
        {
            return new MethodExpressionToMethodBinding(actionExpression);
        }
        return null;
    }
    
    // Property: actionExpression
    private MethodExpression _actionExpression;

    /**
     * Gets Specifies the action to take when this command is invoked.
     *         If the value is an expression, it is expected to be a method
     *         binding EL expression that identifies an action method. An action method
     *         accepts no parameters and has a String return value, called the action
     *         outcome, that identifies the next view displayed. The phase that this
     *         event is fired in can be controlled via the immediate attribute.
     * 
     *         If the value is a string literal, it is treated as a navigation outcome
     *         for the current view.  This is functionally equivalent to a reference to
     *         an action method that returns the string literal.
     *
     * @JSFProperty
     *   returnSignature="java.lang.String"
     *   jspName="action"
     * @return  the new actionExpression value
     */
    public MethodExpression getActionExpression()
    {
      if (_actionExpression != null)
      {
        return _actionExpression;
      }
      ValueExpression expression = getValueExpression("actionExpression");
      if (expression != null)
      {
        return (MethodExpression)expression.getValue(getFacesContext().getELContext());
      }
      return null;
    }

    /**
     * Sets Specifies the action to take when this command is invoked.
     *         If the value is an expression, it is expected to be a method
     *         binding EL expression that identifies an action method. An action method
     *         accepts no parameters and has a String return value, called the action
     *         outcome, that identifies the next view displayed. The phase that this
     *         event is fired in can be controlled via the immediate attribute.
     * 
     *         If the value is a string literal, it is treated as a navigation outcome
     *         for the current view.  This is functionally equivalent to a reference to
     *         an action method that returns the string literal.
     * 
     * @param actionExpression  the new actionExpression value
     */
    public void setActionExpression(MethodExpression actionExpression)
    {
      this._actionExpression = actionExpression;
    }
    
    
    // Property: mouseListenerExpression
    private MethodExpression _mouseListenerExpression;

    /**
     * @JSFProperty
     *   returnSignature="void"
     *   methodSignature="org.apache.myfaces.custom.schedule.ScheduleMouseEvent"
     *   stateHolder="true"
     *   jspName="mouseListener"
     *   
     * @return
     */
    public MethodExpression getMouseListenerExpression()
    {
      if (_mouseListenerExpression != null)
      {
        return _mouseListenerExpression;
      }
      ValueExpression expression = getValueExpression("mouseListenerExpression");
      if (expression != null)
      {
        return (MethodExpression)expression.getValue(getFacesContext().getELContext());
      }
      return null;
    }

    public void setMouseListenerExpression(MethodExpression mouseListenerExpression)
    {
      this._mouseListenerExpression = mouseListenerExpression;
    }    
    
    /*
    public MethodBinding getAction()
    {
        return _action;
    }*/

    /**
     * @JSFProperty
     *   returnSignature="void"
     *   methodSignature="javax.faces.event.ActionEvent"
     */
    public MethodBinding getActionListener()
    {
        return _actionListener;
    }

    public ActionListener[] getActionListeners()
    {
        return (ActionListener[]) getFacesListeners(ActionListener.class);
    }

    /**
     * The last date and time of day that was clicked. This is set when
     * submitOnClick is true, and the schedule is clicked by the user.
     * 
     * @JSFProperty
     *   tagExcluded = "true"
     * @return the last clicked date and time
     */
    public Date getLastClickedDateAndTime()
    {
        return _lastClickedDateAndTime;
    }
    
    /**
     * @deprecated Use setMouseListenerExpression instead.
     */
    public void setMouseListener(MethodBinding mouseListener)
    {
        if(mouseListener != null)
        {
            setMouseListenerExpression(new MethodBindingToMethodExpression(mouseListener));
        } 
        else
        {
            setMouseListenerExpression(null);
        }
    }

    /**
     *   
     * @return the method binding to the mouse listener method
     */
    public MethodBinding getMouseListener()
    {
        MethodExpression mouseListenerExpression = getMouseListenerExpression();
        if (mouseListenerExpression instanceof MethodBindingToMethodExpression) {
            return ((MethodBindingToMethodExpression)mouseListenerExpression).getMethodBinding();
        }
        if(mouseListenerExpression != null)
        {
            return new MethodExpressionToMethodBinding(mouseListenerExpression);
        }
        return null;
    }

    /**
     * @return the submittedEntry
     */
    protected ScheduleEntry getSubmittedEntry()
    {
        return _submittedEntry;
    }

    /**
     * @see javax.faces.component.UIComponent#queueEvent(javax.faces.event.FacesEvent)
     */
    public void queueEvent(FacesEvent event)
    {
        if (event instanceof ActionEvent || event instanceof ScheduleMouseEvent)
        {
            if (isImmediate())
            {
                event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
            }
            else
            {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            }
        }

        super.queueEvent(event);
    }

    public void removeActionListener(ActionListener listener)
    {
        removeFacesListener(listener);
    }

    /**
     * This method is invoked at the beginning of the restore view phase,
     * resetting all mouse event variables that were left from the previous
     * request
     */
    protected void resetMouseEvents()
    {
        this._lastClickedDateAndTime = null;
    }

    /**
     * @see org.apache.myfaces.custom.schedule.UIScheduleBase#restoreState(javax.faces.context.FacesContext, java.lang.Object)
     */
    public void restoreState(FacesContext context, Object state)
    {
        removeActionListener(_scheduleListener);

        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        _lastClickedDateAndTime = (Date) values[1];
        _actionListener = (MethodBinding) restoreAttachedState(context,
                values[2]);
        _actionExpression = (MethodExpression) restoreAttachedState(context, values[3]);
        _mouseListenerExpression = (MethodExpression) restoreAttachedState(context, values[4]);
        addActionListener(_scheduleListener);
    }
    
    /**
     * @see org.apache.myfaces.custom.schedule.UIScheduleBase#saveState(javax.faces.context.FacesContext)
     */
    public Object saveState(FacesContext context)
    {
        removeActionListener(_scheduleListener);

        Object[] values = new Object[5];
        values[0] = super.saveState(context);
        values[1] = _lastClickedDateAndTime;
        values[2] = saveAttachedState(context, _actionListener);
        values[3] = saveAttachedState(context, _actionExpression);
        values[4] = saveAttachedState(context, _mouseListenerExpression);
        
        return values;
    }

    

    public void setActionListener(MethodBinding actionListener)
    {
        this._actionListener = actionListener;
    }

    
    
    /**
     * The last date and time of day that was clicked. This is set when
     * submitOnClick is true, and the schedule is clicked by the user.
     * 
     * @return the last clicked date and time
     */
    protected void setLastClickedDateAndTime(Date lastClickedDateAndTime)
    {
        this._lastClickedDateAndTime = lastClickedDateAndTime;
    }


    /**
     * @param submittedEntry the submittedEntry to set
     */
    protected void setSubmittedEntry(ScheduleEntry submittedEntry)
    {
        this._submittedEntry = submittedEntry;
    }
    
}
