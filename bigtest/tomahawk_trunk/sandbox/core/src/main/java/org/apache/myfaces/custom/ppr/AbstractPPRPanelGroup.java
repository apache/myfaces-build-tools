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
     * @JSFProperty
     */
    public abstract String getPartialTriggers();

    /**
     * @JSFProperty
     */
    public abstract Integer getPeriodicalUpdate();

    /**
     * @JSFProperty
     */
    public abstract String getComponentUpdateFunction();

    /**
     * @JSFProperty
     */
    public abstract String getPeriodicalTriggers();

    /**
     * @JSFProperty
     */
    public abstract String getPartialTriggerPattern();

    /**
     * @JSFProperty
     */
    public abstract String getExcludeFromStoppingPeriodicalUpdate();

    /**
     * @JSFProperty
     *   defaultValue = "Integer.valueOf(2000)"
     */
    public abstract Integer getWaitBeforePeriodicalUpdate();

    /**
     * @JSFProperty
     */
    public abstract String getInlineLoadingMessage();

    /**
     * @JSFProperty
     *   defaultValue = "false"
     */
    public abstract Boolean getShowDebugMessages();

    /**
     * @JSFProperty
     *   defaultValue = "true"
     */
    public abstract Boolean getStateUpdate();

    /**
     * @JSFProperty
     */
    public abstract String getAppendMessages();

    /**
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
