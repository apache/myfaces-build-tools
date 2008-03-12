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
package javax.faces.component;

import javax.faces.context.FacesContext;

/**
 * Component for choosing one option out of a set of possibilities.
 * <p>
 * This component is expected to have children of type UISelectItem or
 * UISelectItems; these define the set of possible options that the
 * user can choose from.
 * <p>
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UISelectOne extends UIInput
{
    public static final String INVALID_MESSAGE_ID = "javax.faces.component.UISelectOne.INVALID";

    /**
     * Verify that the result of converting the newly submitted value is
     * <i>equal</i> to the value property of one of the child SelectItem
     * objects. If this is not true, a validation error is reported.
     * 
     * @see javax.faces.component.UIInput#validateValue(javax.faces.context.FacesContext, java.lang.Object)
     */
    protected void validateValue(FacesContext context, Object value)
    {
        super.validateValue(context, value);

        if (!isValid() || value == null)
        {
            return;
        }

        // selected value must match to one of the available options
        if (!_SelectItemsUtil.matchValue(value, new _SelectItemsIterator(this)))
        {
            _MessageUtils.addErrorMessage(context, this, INVALID_MESSAGE_ID,
                            new Object[] {getId()});
            setValid(false);
        }
    }

    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "javax.faces.SelectOne";
    public static final String COMPONENT_FAMILY = "javax.faces.SelectOne";
    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Menu";

    public UISelectOne()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    //------------------ GENERATED CODE END ---------------------------------------
}
