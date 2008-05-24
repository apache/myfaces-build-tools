/*
 * Copyright 2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.myfaces.taglib.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared_impl.util.ClassUtils;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.Serializable;

/**
 * @author martin.haimberger
 * @version $Revision: 462929 $ $Date: 2006-10-11 21:26:36 +0100 (mi�, 11 oct 2006) $
 */
public class PhaseListenerTag extends TagSupport {

    private final Log log = LogFactory.getLog(PhaseListenerTag.class);

    /**
     * The fully qualified class name of the PhaseListener which should be created.
     */
    private ValueExpression type = null;

    /**
     * A value binding expression used to create a PhaseListener instance.
     */
    private ValueExpression binding = null;

    /*--- Setter ---*/
    public void setType(ValueExpression type) {
        this.type = type;
    }

    public void setBinding(ValueExpression binding) {
        this.binding = binding;
    }


    public int doStartTag() throws JspException {

        // JSF-Spec 1.2 9.4.9
        //    Locate the one and only UIViewRoot custom action instance by walking up the tag tree
        //    until you find a UIComponentELTag instance that has no parent. If the
        //    getCreated() method of this instance returns true, check the binding attribute.

        Tag parent = this;
        UIComponentELTag parentTag = null;
        while ((parent = parent.getParent()) != null) {
            if (parent instanceof UIComponentELTag) {
                parentTag = (UIComponentELTag) parent;
            }
        }

        if (parentTag == null) {
            throw new JspException("Not nested in a UIViewRoot Error for tag with handler class: " + this.getClass().getName());
        }

        if (!parentTag.getCreated()) {
            return SKIP_BODY;
        }

        UIViewRoot root = (UIViewRoot) parentTag.getComponentInstance();

        // JSF-Spec 1.2 9.4.9
        // If binding is set, call binding.getValue() to obtain a reference to the
        // PhaseListener instance. If there is no exception thrown, and binding.getValue()
        // returned a non-null object that implements javax.faces.event.PhaseListener, register
        // it by calling addPhaseListener(). If there was an exception thrown, rethrow the
        // exception as a JspException.

        // If the listener instance could not be created, check the type attribute. If the type
        // attribute is set, instantiate an instance of the specified class, and register it by calling
        // addPhaseListener(). If the binding attribute was also set, store the listener instance
        // by calling binding.setValue(). If there was an exception thrown, rethrow the
        // exception as a JspException.

        PhaseListener listener = null;
        try {
            listener = new BindingPhaseListener(binding, type);
        } catch(Exception ex) {
            throw new JspException(ex.getMessage(),ex);
        }

        root.addPhaseListener(listener);

        return SKIP_BODY;

    }

    private static class BindingPhaseListener
            implements PhaseListener, Serializable {

        private transient PhaseListener phaseListenerCache = null;
        private ValueExpression type;
        private ValueExpression binding;
        private final Log log = LogFactory.getLog(PhaseListenerTag.class);

        BindingPhaseListener(ValueExpression binding, ValueExpression type) {
            this.binding = binding;
            this.type = type;
        }

        public void afterPhase(PhaseEvent event) {
            PhaseListener listener = getPhaseListener();
            if (listener != null) {
                listener.afterPhase(event);
            }
        }

        public void beforePhase(PhaseEvent event) {
            PhaseListener listener = getPhaseListener();
            if (listener != null) {
                listener.beforePhase(event);
            }
        }

        public PhaseId getPhaseId() {
            PhaseListener listener = getPhaseListener();
            if (listener != null) {
                return listener.getPhaseId();
            }

            return null;

        }

        private PhaseListener getPhaseListener() {

            if (phaseListenerCache != null) {
                return phaseListenerCache;
            } else {
                // create PhaseListenerInstance
                phaseListenerCache = getPhaseListnerInstance(binding, type);
            }
            if (phaseListenerCache == null) {
                log.warn("PhaseListener will not be installed. Both binding and type are null.");

            }

            return phaseListenerCache;

        }

        private PhaseListener getPhaseListnerInstance(ValueExpression binding,
                                                      ValueExpression type) {
            FacesContext currentFacesContext = FacesContext.getCurrentInstance();
            Object phasesInstance = null;
            if (binding != null) {
                phasesInstance = binding.getValue(currentFacesContext.getELContext());
            } else if (type != null) {
                try {
                    phasesInstance = ClassUtils.newInstance((String)type.getValue(currentFacesContext.getELContext()));
                } catch (FacesException ex) {
                    throw new AbortProcessingException(ex.getMessage(), ex);
                }
            }
            return (PhaseListener) phasesInstance;

        }

    }
}
