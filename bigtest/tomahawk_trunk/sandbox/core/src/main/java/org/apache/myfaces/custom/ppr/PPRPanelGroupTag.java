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

import org.apache.myfaces.taglib.html.ext.HtmlPanelGroupTag;

import javax.faces.component.UIComponent;

/**
 * @author Ernst Fastl
 */
public class PPRPanelGroupTag extends HtmlPanelGroupTag
{
    private String _partialTriggers;

    private String _partialTriggerPattern;

    private String _inlineLoadingMessage;

    private String _periodicalUpdate;

    private String _periodicalTriggers;

    private String _excludeFromStoppingPeriodicalUpdate;

    private String _showDebugMessages;

    private String _stateUpdate;

    private String _waitBeforePeriodicalUpdate;

    private String _appendMessages;

    private String _replaceMessages;

    private String _componentUpdateFunction;

    public String getComponentType()
    {
        return PPRPanelGroup.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return PPRPanelGroup.DEFAULT_RENDERER_TYPE;
    }

    public void release()
    {
        super.release();
        _partialTriggers = null;
        _periodicalUpdate = null;
        _periodicalTriggers = null;
        _showDebugMessages = null;
        _stateUpdate = null;
        _excludeFromStoppingPeriodicalUpdate = null;
        _waitBeforePeriodicalUpdate = null;
        _appendMessages = null;
        _replaceMessages = null;
        _componentUpdateFunction = null;
    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setStringProperty(component, "partialTriggers", _partialTriggers);
        setStringProperty(component, "partialTriggerPattern", _partialTriggerPattern);
        setStringProperty(component, "inlineLoadingMessage", _inlineLoadingMessage);
        setIntegerProperty(component, "periodicalUpdate", _periodicalUpdate);
        setStringProperty(component, "periodicalTriggers", _periodicalTriggers);
        setStringProperty(component, "excludeFromStoppingPeriodicalUpdate", _excludeFromStoppingPeriodicalUpdate);
        setIntegerProperty(component, "waitBeforePeriodicalUpdate", _waitBeforePeriodicalUpdate);
        setBooleanProperty(component, "showDebugMessages", _showDebugMessages);
        setBooleanProperty(component, "stateUpdate", _stateUpdate);
        setStringProperty(component, "appendMessages", _appendMessages);
        setStringProperty(component, "replaceMessages", _replaceMessages);
        setStringProperty(component, "componentUpdateFunction", _componentUpdateFunction);
    }

    public String getPartialTriggers()
    {
        return _partialTriggers;
    }

    public void setPartialTriggers(String partialTriggers)
    {
        this._partialTriggers = partialTriggers;
    }

    public String getPartialTriggerPattern()
    {
        return _partialTriggerPattern;
    }

    public void setPartialTriggerPattern(String triggerPattern)
    {
        _partialTriggerPattern = triggerPattern;
    }

    public String getInlineLoadingMessage()
    {
        return _inlineLoadingMessage;
    }

    public void setInlineLoadingMessage(String loadingMessage)
    {
        _inlineLoadingMessage = loadingMessage;
    }

    public void setPeriodicalUpdate(String periodicalUpdate)
    {
        _periodicalUpdate = periodicalUpdate;
    }

    public String getPeriodicalTriggers()
    {
        return _periodicalTriggers;
    }

    public void setPeriodicalTriggers(String periodicalTriggers)
    {
        _periodicalTriggers = periodicalTriggers;
    }

    public void setShowDebugMessages(String showDebugMessages)
    {
        _showDebugMessages = showDebugMessages;
    }

    public void setStateUpdate(String stateUpdate)
    {
        _stateUpdate = stateUpdate;
    }

    public void setExcludeFromStoppingPeriodicalUpdate(String excludeFromStoppingPeriodicalUpdate)
    {
        _excludeFromStoppingPeriodicalUpdate = excludeFromStoppingPeriodicalUpdate;
    }

    public void setWaitBeforePeriodicalUpdate(String waitBeforePeriodicalUpdate)
    {
        _waitBeforePeriodicalUpdate = waitBeforePeriodicalUpdate;
    }

    public String getAppendMessages()
    {
        return _appendMessages;
    }

    public void setAppendMessages(String _appendMessages)
    {
        this._appendMessages = _appendMessages;
    }

    public String getReplaceMessages()
    {
        return _replaceMessages;
    }

    public void setReplaceMessages(String _replaceMessages)
    {
        this._replaceMessages = _replaceMessages;
    }

    public String getComponentUpdateFunction()
    {
        return _componentUpdateFunction;
    }

    public void setComponentUpdateFunction(String componentUpdateFunction)
    {
        this._componentUpdateFunction = componentUpdateFunction;
    }
}
