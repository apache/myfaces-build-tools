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
package javax.faces.application;

import javax.faces.context.FacesContext;
import java.io.IOException;


/**
 * Responsible for storing sufficient information about a component tree so that an identical tree
 * can later be recreated.
 * <p>
 * It is up to the concrete implementation to decide whether to use information from the "view template"
 * that was used to first create the view, or whether to store sufficient information to enable the
 * view to be restored without any reference to the original template. However as JSF components have
 * mutable fields that can be set by code, and affected by user input, at least some state does need
 * to be kept in order to recreate a previously-existing component tree.
 * <p>
 * There are two different options defined by the specification: "client" and "server" state.
 * <p>
 * When "client" state is configured, all state information required to create the tree is embedded within
 * the data rendered to the client. Note that because data received from a remote client must always be
 * treated as "tainted", care must be taken when using such data. Some StateManager implementations may
 * use encryption to ensure that clients cannot modify the data, and that the data received on postback
 * is therefore trustworthy.
 * <p>
 * When "server" state is configured, the data is saved somewhere "on the back end", and (at most) a
 * token is embedded in the data rendered to the user.
 * <p>
 * This class is usually invoked by a concrete implementation of ViewHandler.
 * <p>
 * Note that class ViewHandler isolates JSF components from the details of the request format. This class
 * isolates JSF components from the details of the response format. Because request and response are usually
 * tightly coupled, the StateManager and ViewHandler implementations are also usually fairly tightly coupled
 * (ie the ViewHandler/StateManager implementations come as pairs).
 * <p>
 * See also the <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author$)
 * @author Stan Silvert
 * @version $Revision$ $Date$
 */
public abstract class StateManager
{
    public static final String STATE_SAVING_METHOD_PARAM_NAME = "javax.faces.STATE_SAVING_METHOD";
    public static final String STATE_SAVING_METHOD_CLIENT = "client";
    public static final String STATE_SAVING_METHOD_SERVER = "server";
    private Boolean _savingStateInClient = null;
    
    /**
     * Invokes getTreeStructureToSave and getComponentStateToSave, then return an object that wraps the two
     * resulting objects. This object can then be passed to method writeState.
     * <p>
     * Deprecated; use saveView instead.
     * 
     * @deprecated
     */
    public StateManager.SerializedView saveSerializedView(javax.faces.context.FacesContext context) {
        return null;
    }
    
    /**
     * Returns an object that is sufficient to recreate the component tree that is the viewroot of
     * the specified context.
     * <p>
     * The return value is suitable for passing to method writeState.
     * 
     * @since 1.2
     */
    public Object saveView(FacesContext context) {
        StateManager.SerializedView serializedView = saveSerializedView(context);
        if (serializedView == null) return null;
        
        Object[] structureAndState = new Object[2];
        structureAndState[0] = serializedView.getStructure();
        structureAndState[1] = serializedView.getState();
        
        return structureAndState;
    }
    
    /**
     * Return data that is sufficient to recreate the component tree that is the viewroot of the specified
     * context, but without restoring the state in the components.
     * <p>
     * Using this data, a tree of components which has the same "shape" as the original component
     * tree can be recreated. However the component instances themselves will have only their default
     * values, ie their member fields will not have been set to the original values.
     * <p>
     * Deprecated; use saveView instead.
     * 
     * @deprecated
     */
    protected Object getTreeStructureToSave(javax.faces.context.FacesContext context) {
        return null;
    }

    /**
     * Return data that can be applied to a component tree created using the "getTreeStructureToSave"
     * method.
     * <p>
     * Deprecated; use saveView instead.
     * 
     * @deprecated
     */
    protected Object getComponentStateToSave(javax.faces.context.FacesContext context) {
        return null;
    }

    /**
     * Associate the provided state object with the current response being generated.
     * <p>
     * When client-side state is enabled, it is expected that method writes the data contained in the
     * state parameter to the response somehow.
     * <p>
     * When server-side state is enabled, at most a "token" is expected to be written.
     * <p>
     * Deprecated; use writeState(FacesContext, Object) instead. This method was abstract
     * in JSF1.1, but is now an empty non-abstract method so that old classes that implement
     * this method continue to work, while new classes can just override the new writeState
     * method rather than this one.
     * 
     * @deprecated
     */
    public void writeState(javax.faces.context.FacesContext context,
                                    StateManager.SerializedView state)
            throws java.io.IOException {
        // default impl does nothing as per JSF 1.2 javadoc
    }
    
    /**
     * Associate the provided state object with the current response being generated.
     * <p>
     * When client-side state is enabled, it is expected that method writes the data contained in the
     * state parameter to the response somehow.
     * <p>
     * When server-side state is enabled, at most a "token" is expected to be written.
     * <p>
     * This method should be overridden by subclasses. It is not abstract because a default
     * implementation is provided that forwards to the old writeState method; this allows
     * subclasses of StateManager written using the JSF1.1 API to continue to work.
     * <p>
     * @since 1.2
     */
    public void writeState(FacesContext context, Object state) throws IOException {
        if (!(state instanceof Object[])) return;
        Object[] structureAndState = (Object[])state;
        if (structureAndState.length < 2) return;
        
        writeState(context, new StateManager.SerializedView(structureAndState[0], structureAndState[1]));
    }

    public abstract javax.faces.component.UIViewRoot restoreView(javax.faces.context.FacesContext context,
                                                                 String viewId,
                                                                 String renderKitId);

    /**
     * @deprecated
     */
    protected javax.faces.component.UIViewRoot restoreTreeStructure(javax.faces.context.FacesContext context,
                                                                    String viewId,
                                                                    String renderKitId) {
        return null;
    }

    /**
     * @deprecated
     */
    protected void restoreComponentState(javax.faces.context.FacesContext context,
                                         javax.faces.component.UIViewRoot viewRoot,
                                         String renderKitId) {
        // default impl does nothing as per JSF 1.2 javadoc
    }

    public boolean isSavingStateInClient(javax.faces.context.FacesContext context)
    {
        if(context == null) throw new NullPointerException("context");
        if (_savingStateInClient != null) return _savingStateInClient.booleanValue();
        String stateSavingMethod = context.getExternalContext().getInitParameter(STATE_SAVING_METHOD_PARAM_NAME);
        if (stateSavingMethod == null)
        {
            _savingStateInClient = Boolean.FALSE; //Specs 10.1.3: default server saving
            context.getExternalContext().log("No state saving method defined, assuming default server state saving");
        }
        else if (stateSavingMethod.equals(STATE_SAVING_METHOD_CLIENT))
        {
            _savingStateInClient = Boolean.TRUE;
        }
        else if (stateSavingMethod.equals(STATE_SAVING_METHOD_SERVER))
        {
            _savingStateInClient = Boolean.FALSE;
        }
        else
        {
            _savingStateInClient = Boolean.FALSE; //Specs 10.1.3: default server saving
            context.getExternalContext().log("Illegal state saving method '" + stateSavingMethod + "', default server state saving will be used");
        }
        return _savingStateInClient.booleanValue();
    }


    /**
     * @deprecated
     */
    public class SerializedView 
    {
        private Object _structure;
        private Object _state;

        /**
         * @deprecated
         */
        public SerializedView(Object structure, Object state)
        {
            _structure = structure;
            _state = state;
        }

        /**
         * @deprecated
         */
        public Object getStructure()
        {
            return _structure;
        }

        /**
         * @deprecated
         */
        public Object getState()
        {
            return _state;
        }
    }
}
