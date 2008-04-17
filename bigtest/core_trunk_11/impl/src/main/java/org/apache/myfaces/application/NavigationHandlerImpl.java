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
package org.apache.myfaces.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.config.RuntimeConfig;
import org.apache.myfaces.config.element.NavigationCase;
import org.apache.myfaces.config.element.NavigationRule;
import org.apache.myfaces.portlet.PortletUtil;
import org.apache.myfaces.shared_impl.util.HashMapUtils;

/**
 * @author Thomas Spiegl (latest modification by $Author$)
 * @author Anton Koinov
 * @version $Revision$ $Date$
 */
public class NavigationHandlerImpl
    extends NavigationHandler
{
    private static final Log log = LogFactory.getLog(NavigationHandlerImpl.class);
    private static final String PARTIAL_STATE_SAVING_METHOD_PARAM_NAME = "javax.faces.PARTIAL_STATE_SAVING_METHOD";
    private static final String PARTIAL_STATE_SAVING_METHOD_ON = "true";
    private static final String PARTIAL_STATE_SAVING_METHOD_OFF = "false";

    private static final String ASTERISK = "*";
    private Boolean _partialStateSaving = null;

    private boolean isPartialStateSavingOn(javax.faces.context.FacesContext context)
    {
        if(context == null) throw new NullPointerException("context");
        if (_partialStateSaving != null) return _partialStateSaving.booleanValue();
        String stateSavingMethod = context.getExternalContext().getInitParameter(PARTIAL_STATE_SAVING_METHOD_PARAM_NAME);
        if (stateSavingMethod == null)
        {
            _partialStateSaving = Boolean.FALSE; //Specs 10.1.3: default server saving
            context.getExternalContext().log("No context init parameter '"+PARTIAL_STATE_SAVING_METHOD_PARAM_NAME+"' found; no partial state saving method defined, assuming default partial state saving method off.");
        }
        else if (stateSavingMethod.equals(PARTIAL_STATE_SAVING_METHOD_ON))
        {
            _partialStateSaving = Boolean.TRUE;
        }
        else if (stateSavingMethod.equals(PARTIAL_STATE_SAVING_METHOD_OFF))
        {
            _partialStateSaving = Boolean.FALSE;
        }
        else
        {
            _partialStateSaving = Boolean.FALSE; //Specs 10.1.3: default server saving
            context.getExternalContext().log("Illegal partial state saving method '" + stateSavingMethod + "', default partial state saving will be used (partial state saving off).");
        }
        return _partialStateSaving.booleanValue();
    }


    private Map _navigationCases = null;
    private List _wildcardKeys = new ArrayList();

    public NavigationHandlerImpl()
    {
        if (log.isTraceEnabled()) log.trace("New NavigationHandler instance created");
    }

    public void handleNavigation(FacesContext facesContext, String fromAction, String outcome)
    {
        if (outcome == null)
        {
            // stay on current ViewRoot
            return;
        }

        NavigationCase navigationCase = getNavigationCase(facesContext, fromAction, outcome);

        if (navigationCase != null)
        {
            if (log.isTraceEnabled())
            {
                log.trace("handleNavigation fromAction=" + fromAction + " outcome=" + outcome +
                          " toViewId =" + navigationCase.getToViewId() +
                          " redirect=" + navigationCase.isRedirect());
            }
            if (navigationCase.isRedirect() &&
                (!PortletUtil.isPortletRequest(facesContext)))
            { // Spec section 7.4.2 says "redirects not possible" in this case for portlets
                ExternalContext externalContext = facesContext.getExternalContext();
                ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
                String redirectPath = viewHandler.getActionURL(facesContext, navigationCase.getToViewId());

                try
                {
                    externalContext.redirect(externalContext.encodeActionURL(redirectPath));
                }
                catch (IOException e)
                {
                    throw new FacesException(e.getMessage(), e);
                }
            }
            else
            {
                ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
                //create new view
                String newViewId = navigationCase.getToViewId();
                UIViewRoot viewRoot = null;
                if (isPartialStateSavingOn(facesContext)) {
                    viewRoot = viewHandler.restoreView(facesContext,newViewId);
                } else {
                    viewRoot = viewHandler.createView(facesContext, newViewId);
                }
                facesContext.setViewRoot(viewRoot);
                facesContext.renderResponse();
            }
        }
        else
        {
            // no navigationcase found, stay on current ViewRoot
            if (log.isTraceEnabled())
            {
                log.trace("handleNavigation fromAction=" + fromAction + " outcome=" + outcome +
                          " no matching navigation-case found, staying on current ViewRoot");
            }
        }
    }

    /**
     * Returns the <code>NavigationCase</code>that applies for the given action and outcome
     */
    public NavigationCase getNavigationCase(FacesContext facesContext, String fromAction, String outcome)
    {
        String viewId = facesContext.getViewRoot().getViewId();
        Map casesMap = getNavigationCases(facesContext);
        NavigationCase navigationCase = null;

        List casesList = (List)casesMap.get(viewId);
        if (casesList != null)
        {
            // Exact match?
            navigationCase = calcMatchingNavigationCase(casesList, fromAction, outcome);
        }

        if (navigationCase == null)
        {
            // Wildcard match?
            List keys = getSortedWildcardKeys();
            for (int i = 0, size = keys.size(); i < size; i++)
            {
                String fromViewId = (String)keys.get(i);
                if (fromViewId.length() > 2)
                {
                    String prefix = fromViewId.substring(0, fromViewId.length() - 1);
                    if (viewId != null && viewId.startsWith(prefix))
                    {
                        casesList = (List)casesMap.get(fromViewId);
                        if (casesList != null)
                        {
                            navigationCase = calcMatchingNavigationCase(casesList, fromAction, outcome);
                            if (navigationCase != null) break;
                        }
                    }
                }
                else
                {
                    casesList = (List)casesMap.get(fromViewId);
                    if (casesList != null)
                    {
                        navigationCase = calcMatchingNavigationCase(casesList, fromAction, outcome);
                        if (navigationCase != null) break;
                    }
                }
            }
        }
        return navigationCase;
    }

    /**
     * Returns the view ID that would be created for the given action and outcome
     */
    public String getViewId(FacesContext context, String fromAction, String outcome)
    {
        return this.getNavigationCase(context, fromAction, outcome).getToViewId();
    }

    /**
     * Invoked by the navigation handler before the new view component is created.
     * @param viewId The view ID to be created
     * @return The view ID that should be used instead. If null, the view ID passed
     * in will be used without modification.
     * 
     * <p><b>returns NULL</b></p>
     * 
     * <p>not implemented/called by Apache MyFaces</p>
     */
    public String beforeNavigation(String viewId)
    {
        return null;
    }

    private NavigationCase calcMatchingNavigationCase(List casesList, String actionRef, String outcome)
    {
        for (int i = 0, size = casesList.size(); i < size; i++)
        {
            NavigationCase caze = (NavigationCase)casesList.get(i);
            String cazeOutcome = caze.getFromOutcome();
            String cazeActionRef = caze.getFromAction();
            if ((cazeOutcome == null || cazeOutcome.equals(outcome)) &&
                (cazeActionRef == null || cazeActionRef.equals(actionRef)))
            {
                return caze;
            }
        }
        return null;
    }

    private List getSortedWildcardKeys()
    {
        return _wildcardKeys;
    }

    private Map getNavigationCases(FacesContext facesContext)
    {
        ExternalContext externalContext = facesContext.getExternalContext();
        RuntimeConfig runtimeConfig = RuntimeConfig.getCurrentInstance(externalContext);

        if (_navigationCases == null || runtimeConfig.isNavigationRulesChanged())
        {
            synchronized(this)
            {
                if (_navigationCases == null || runtimeConfig.isNavigationRulesChanged())
                {
                    Collection rules = runtimeConfig.getNavigationRules();
                    int rulesSize = rules.size();
                    Map cases = new HashMap(HashMapUtils.calcCapacity(rulesSize));
                    List wildcardKeys = new ArrayList();

                    for (Iterator iterator = rules.iterator(); iterator.hasNext();)
                    {
                        NavigationRule rule = (NavigationRule) iterator.next();
                        String fromViewId = rule.getFromViewId();

                        //specification 7.4.2 footnote 4 - missing fromViewId is allowed:
                        if (fromViewId == null)
                        {
                            fromViewId = ASTERISK;
                        }
                        else
                        {
                            fromViewId = fromViewId.trim();
                        }

                        List list = (List) cases.get(fromViewId);
                        if (list == null)
                        {
                            list = new ArrayList(rule.getNavigationCases());
                            cases.put(fromViewId, list);
                            if (fromViewId.endsWith(ASTERISK))
                            {
                                wildcardKeys.add(fromViewId);
                            }
                        } else {
                            list.addAll(rule.getNavigationCases());
                        }

                    }
                    Collections.sort(wildcardKeys, new KeyComparator());

                    synchronized (cases)
                    {
                        // We do not really need this sychronization at all, but this
                        // gives us the peace of mind that some good optimizing compiler
                        // will not rearrange the execution of the assignment to an
                        // earlier time, before all init code completes
                        _navigationCases = cases;
                        _wildcardKeys = wildcardKeys;

                        runtimeConfig.setNavigationRulesChanged(false);
                    }
                }
            }
        }
        return _navigationCases;
    }

    private static final class KeyComparator
        implements Comparator
    {
        public int compare(Object o1, Object o2)
        {
            return -(((String)o1).compareTo((String)o2));
        }
    }
}