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

import java.util.Collections;
import java.util.List;

import org.apache.myfaces.component.html.ext.HtmlPanelGroup;

/**
 * AJAX component which supports updating its children via AJAX calls. These
 * updates can occur regularly or based on triggering input components.
 *
 * @JSFComponent
 *   name = "s:pprPanelGroup"
 *   class = "org.apache.myfaces.custom.ppr.PPRPanelGroup"
 *   superClass = "org.apache.myfaces.custom.ppr.AbstractPPRPanelGroup"
 *   tagClass = "org.apache.myfaces.custom.ppr.PPRPanelGroupTag"
 *   
 * @author Ernst Fastl
 */
public abstract class AbstractPPRPanelGroup extends HtmlPanelGroup
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.PPRPanelGroup";

    public static final String COMPONENT_FAMILY = "org.apache.myfaces.PPRPanelGroup";

    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.PPRPanelGroup";

    /**
     * Comma or Space seperated List of ids from ui_command-items which trigger a partial update of this PanelGroup
     * 
     * @JSFProperty
     */
    public abstract String getPartialTriggers();

    /**
     * Does a periodical refresh of the partial page inside the ppr group. In milliseconds.
     * No partialTriggers are needed.
     * 
     * @JSFProperty
     */
    public abstract Integer getPeriodicalUpdate();

    /**
     * client javascript function which will do the actual dom update. function signature:
     * function(formNodeElement, updateTargetElement, pprResponseElement)
     * 
     * @JSFProperty
     */
    public abstract String getComponentUpdateFunction();

    /**
     * If a periodicalUpdate is set this trigger starts the periodical requests.
     * No partialTriggers are needed.
     * 
     * @JSFProperty
     */
    public abstract String getPeriodicalTriggers();

    /**
     * Regular Expression If the client Id of a submitting Component matches this Pattern the corresponding pprPanelGroup is updated via AJAX
     * 
     * @JSFProperty
     */
    public abstract String getPartialTriggerPattern();
    
    /**
     * Normally when a link is clicked during periodical update, the update is stopped in order to prevent the server from getting
     * unexpected requests. For any POST-request this is the wanted behaviour because the screen is completely refreshed and periodical
     * updating starts again after the response.
     * However, this behaviour may be unwanted e.g. in case of opening a new window with a link where the main screen should stay refreshed.
     * This attribute takes a regular expression of link-client-ids for which the periodical update should not be stopped.
     * 
     * If this value is given, there will be a default timeout of 2000 milliseconds before any periodical
     * update will start again. This is done as mentioned before in order to prevent the server from getting unexpected requests.   
     * This timeout can be influenced via the waitBeforePeriodicalUpdate attribute.
     * 
     * @JSFProperty
     */
    public abstract String getExcludeFromStoppingPeriodicalUpdate();

    /**
     * This attribute only works in combination with the excludeFromStoppingPeriodicalUpdate attribute. The value
     * in milliseconds tells the periodical update mechanism to stop for the given amount of time after clicking a link, specified by
     * the excludeFromStoppingPeriodicalUpdate attribute.
     * 
     * The default value is 2000 milliseconds.
     * 
     * @JSFProperty
     *   defaultValue = "Integer.valueOf(2000)"
     */
    public abstract Integer getWaitBeforePeriodicalUpdate();

    /**
     * If this attribute is set the content of the PPRPanelGroup will be replaced by the provided
     * Loading-Message during partial update
     * 
     * @JSFProperty
     */
    public abstract String getInlineLoadingMessage();

    /**
     * If false, alert messages which can be fired after a ppr response are not displayed in the browser.
     * May switched to true in test environments. Default: false
     * 
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract Boolean getShowDebugMessages();

    /**
     * If set to false, there will be no stateUpdate on server side due to the partialPage refresh.
     * Default: true
     * 
     * @JSFProperty
     *   defaultValue = "true"
     */
    public abstract Boolean getStateUpdate();

    /**
     * comma separated List of client Ids that specify the messages components
     * in the page to which messages are appended by this PPRPanelGroup
     * 
     * @JSFProperty
     */
    public abstract String getAppendMessages();

    /**
     * comma separated List of client Ids that specify the messages components
     * in the page which messages are replaced by this PPRPanelGroup
     * 
     * @JSFProperty
     */
    public abstract String getReplaceMessages();


    public boolean getInitializationSent(){
        return isInitializationSent();
    }
    
    /**
     * flag to store the information if the initialization code has been sent to the client
     * already. This gets state-saved to have it available between requests, e.g. to make it work
     * within ppr responses too.
     * 
     * @JSFProperty
     *   literalOnly = "true"
     *   tagExcluded = "true"
     */    public abstract boolean isInitializationSent();

    public abstract void setInitializationSent(boolean initializationSent);

    /**
     * @return {@link PartialTriggerParser.PartialTrigger}
     */
    public List parsePartialTriggers()
    {
        List list;
        String partialTriggers = getPartialTriggers();
        //handle partial triggers
        if (partialTriggers != null && partialTriggers.trim().length() > 0) {
            list = (new PartialTriggerParser()).parse(partialTriggers);
        }
        else {
            list = Collections.emptyList();
        }
        return list;
    }

    /**
     * @return {@link PartialTriggerParser.PartialTrigger}
     */
    public List parsePeriodicalTriggers()
    {
        List list;
        String periodicalTriggers = getPeriodicalTriggers();
        if (periodicalTriggers != null && periodicalTriggers.trim().length() <= 0) {
            list = (new PartialTriggerParser()).parse(periodicalTriggers);
        }
        else {
            list = Collections.emptyList();
        }
        return list;
    }

}
