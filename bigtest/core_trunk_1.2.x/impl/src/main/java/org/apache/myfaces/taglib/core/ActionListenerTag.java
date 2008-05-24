/*
 * Copyright 2006 The Apache Software Foundation.
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

import javax.el.ValueExpression;
import javax.faces.component.ActionSource;
import javax.faces.event.ActionListener;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ActionListenerTag
        extends GenericListenerTag<ActionSource, ActionListener>
{
    private static final long serialVersionUID = -2021978765020549175L;

    public ActionListenerTag()
    {
        super(ActionSource.class);
    }

    protected void addListener(ActionSource actionSource, ActionListener actionListener)
    {
        actionSource.addActionListener(actionListener);
    }

    protected ActionListener createDelegateListener(ValueExpression type,
            ValueExpression binding)
    {
        return new DelegateActionListener(type,binding);
    }
}


