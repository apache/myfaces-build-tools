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
package org.apache.myfaces.validator;

import java.util.*;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.shared_tomahawk.util.MessageUtils;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

/**
 * Base validator implementation for Tomahawk validators.
 *
 */
public abstract class ValidatorBase implements StateHolder, Validator {

    private String _summaryMessage = null;
    private String _detailMessage = null;
    private boolean _transient = false;

    /**
     *
     * @return  The summary message to be displayed
     */
    public String getSummaryMessage()
    {
        if (_summaryMessage != null) return _summaryMessage;
        ValueBinding vb = getValueBinding("summaryMessage");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    /**
     *
     * @param message   The summary message to be displayed.
     */
    public void setSummaryMessage(String message) {
        _summaryMessage = message;
    }

    /**
     *
     * @return  The message.
     * @deprecated Use getDetailMessage()
     */
    public String getMessage() {
        return getDetailMessage();
    }

    /**
     *
     * @param message  The detail message to be displayed.
     * @deprecated Use setDetailMessage()
     */
    public void setMessage(String message) {
        setDetailMessage(message);
    }


    /**
     *
     * @return  The detail message.
     */
    public String getDetailMessage() {
        if (_detailMessage != null) return _detailMessage;
        ValueBinding vb = getValueBinding("detailMessage");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    /**
     *
     * @param message  The detail message to be displayed.
     */
    public void setDetailMessage(String message) {
        _detailMessage = message;
    }


    /**
     * @param context
     */
    public Object saveState(FacesContext context) {
        Object[] state = new Object[3];
        state[0] = _summaryMessage;
        state[1] = _detailMessage;
        state[2] = saveValueBindingMap(context);
        return state;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        _summaryMessage = (String) values[0];
        _detailMessage = (String) values[1];
        restoreValueBindingMap(context, values[2]);
    }

    public boolean isTransient() {
        return _transient;
    }

    public void setTransient(boolean newTransientValue) {
        _transient = newTransientValue;
    }

    // Utility methods

    /**
     * @param defaultMessage The default message we would expect.
     * @param args Arguments for parsing this message.
     * @return FacesMessage
     */
    protected FacesMessage getFacesMessage(String defaultMessage, Object[] args) {
        FacesMessage msg;

        if (getSummaryMessage() == null && getDetailMessage() == null)
        {
            msg = MessageUtils.getMessage(FacesMessage.SEVERITY_ERROR, defaultMessage, args);
        } else {
            Locale locale = MessageUtils.getCurrentLocale();
            String summaryText = MessageUtils.substituteParams(locale, getSummaryMessage(), args);
            String detailText = MessageUtils.substituteParams(locale, getDetailMessage(), args);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summaryText, detailText);
        }
        return msg;
    }

    // --------------------- borrowed from UIComponentBase ------------

    private Map _valueBindingMap = null;

    public ValueBinding getValueBinding(String name)
    {
        if (name == null) throw new NullPointerException("name");
        if (_valueBindingMap == null)
        {
            return null;
        }
        else
        {
            return (ValueBinding)_valueBindingMap.get(name);
        }
    }

    public void setValueBinding(String name,
                                ValueBinding binding)
    {
        if (name == null) throw new NullPointerException("name");
        if (_valueBindingMap == null)
        {
            _valueBindingMap = new HashMap();
        }
        _valueBindingMap.put(name, binding);
    }

    private Object saveValueBindingMap(FacesContext context)
    {
        if (_valueBindingMap != null)
        {
            int initCapacity = (_valueBindingMap.size() * 4 + 3) / 3;
            HashMap stateMap = new HashMap(initCapacity);
            for (Iterator it = _valueBindingMap.entrySet().iterator(); it.hasNext(); )
            {
                Map.Entry entry = (Map.Entry)it.next();
                stateMap.put(entry.getKey(),
                             saveAttachedState(context, entry.getValue()));
            }
            return stateMap;
        }
        else
        {
            return null;
        }
    }

    private void restoreValueBindingMap(FacesContext context, Object stateObj)
    {
        if (stateObj != null)
        {
            Map stateMap = (Map)stateObj;
            int initCapacity = (stateMap.size() * 4 + 3) / 3;
            _valueBindingMap = new HashMap(initCapacity);
            for (Iterator it = stateMap.entrySet().iterator(); it.hasNext(); )
            {
                Map.Entry entry = (Map.Entry)it.next();
                _valueBindingMap.put(entry.getKey(),
                                     restoreAttachedState(context, entry.getValue()));
            }
        }
        else
        {
            _valueBindingMap = null;
        }
    }

    /**
     * Serializes objects which are "attached" to this component but which are
     * not UIComponent children of it. Examples are validator and listener
     * objects. To be precise, it returns an object which implements
     * java.io.Serializable, and which when serialized will persist the
     * state of the provided object.
     * <p>
     * If the attachedObject is a List then every object in the list is saved
     * via a call to this method, and the returned wrapper object contains
     * a List object.
     * <p>
     * If the object implements StateHolder then the object's saveState is
     * called immediately, and a wrapper is returned which contains both
     * this saved state and the original class name. However in the case
     * where the StateHolder.isTransient method returns true, null is
     * returned instead.
     * <p>
     * If the object implements java.io.Serializable then the object is simply
     * returned immediately; standard java serialization will later be used
     * to store this object.
     * <p>
     * In all other cases, a wrapper is returned which simply stores the type
     * of the provided object. When deserialized, a default instance of that
     * type will be recreated.
     */
    public static Object saveAttachedState(FacesContext context,
                                           Object attachedObject)
    {
        if (attachedObject == null) return null;
        if (attachedObject instanceof List)
        {
            List lst = new ArrayList(((List)attachedObject).size());
            for (Iterator it = ((List)attachedObject).iterator(); it.hasNext(); )
            {
                lst.add(saveAttachedState(context, it.next()));
            }
            return new AttachedListStateWrapper(lst);
        }
        else if (attachedObject instanceof StateHolder)
        {
            if (((StateHolder)attachedObject).isTransient())
            {
                return null;
            }
            else
            {
                return new AttachedStateWrapper(attachedObject.getClass(),
                                                 ((StateHolder)attachedObject).saveState(context));
            }
        }
        else if (attachedObject instanceof Serializable)
        {
            return attachedObject;
        }
        else
        {
            return new AttachedStateWrapper(attachedObject.getClass(), null);
        }
    }

    public static Object restoreAttachedState(FacesContext context,
                                              Object stateObj)
            throws IllegalStateException
    {
        if (context == null) throw new NullPointerException("context");
        if (stateObj == null) return null;
        if (stateObj instanceof AttachedListStateWrapper)
        {
            List lst = ((AttachedListStateWrapper)stateObj).getWrappedStateList();
            List restoredList = new ArrayList(lst.size());
            for (Iterator it = lst.iterator(); it.hasNext(); )
            {
                restoredList.add(restoreAttachedState(context, it.next()));
            }
            return restoredList;
        }
        else if (stateObj instanceof AttachedStateWrapper)
        {
            Class clazz = ((AttachedStateWrapper)stateObj).getClazz();
            Object restoredObject;
            try
            {
                restoredObject = clazz.newInstance();
            }
            catch (InstantiationException e)
            {
                throw new RuntimeException("Could not restore StateHolder of type " + clazz.getName() + " (missing no-args constructor?)", e);
            }
            catch (IllegalAccessException e)
            {
                throw new RuntimeException(e);
            }
            if (restoredObject instanceof StateHolder)
            {
                Object wrappedState = ((AttachedStateWrapper)stateObj).getWrappedStateObject();
                ((StateHolder)restoredObject).restoreState(context, wrappedState);
            }
            return restoredObject;
        }
        else
        {
            return stateObj;
        }
    }


    protected FacesContext getFacesContext()
    {
        return FacesContext.getCurrentInstance();
    }
}
