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
package org.apache.myfaces.config.element;

import java.util.Collection;

/**
 * Entry of the Collection returned by
 * {@link org.apache.myfaces.config.RuntimeConfig#getNavigationRules()}.
 *
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface NavigationRule
{
    // <!ELEMENT navigation-rule (description*, display-name*, icon*, from-view-id?, navigation-case*)>

    public String getFromViewId();

    /**
     * @return a Collection of {@link org.apache.myfaces.config.element.NavigationCase}s
     */
    public Collection getNavigationCases();
}
