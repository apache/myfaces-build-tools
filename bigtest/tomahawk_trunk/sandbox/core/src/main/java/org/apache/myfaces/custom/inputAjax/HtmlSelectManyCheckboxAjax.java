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
package org.apache.myfaces.custom.inputAjax;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.ext.HtmlSelectManyCheckbox;
import org.apache.myfaces.custom.ajax.AjaxCallbacks;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;
import org.apache.myfaces.custom.ajax.api.DeprecatedAjaxComponent;

/**
 * Current limitations
 * - Bound value must be a Collection of Strings!
 *
 * @author Travis Reeder (latest modification by $Author: mmarinschek $)
 * @version $Revision: 290397 $ $Date: 2005-09-20 10:35:09 +0200 (Di, 20 Sep 2005) $
 */
public class HtmlSelectManyCheckboxAjax extends HtmlSelectManyCheckbox implements DeprecatedAjaxComponent, AjaxCallbacks
{
    private static final Log log = LogFactory.getLog(HtmlSelectManyCheckboxAjax.class);
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlSelectManyCheckboxAjax";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.CheckboxAjax";

    private String onSuccess;
    private String onFailure;
    private String onStart;

    public HtmlSelectManyCheckboxAjax()
    {
        super();
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    /**
     * Decode ajax request and apply value changes
     *
     * @param context
     */
    public void decodeAjax(FacesContext context)
    {
        log.debug("entering HtmlSelectManyCheckboxAjax.decodeAjax");

        // this requires special handling
        // should maybe put the end collection, "c" into the EditableValueHolder as request params: ((EditableValueHolder) component).setSubmittedValue(reqValues);
        Map requestParams = context.getExternalContext().getRequestParameterMap();

        String elname = (String) requestParams.get("elname");
        String elvalue = (String) requestParams.get("elvalue");
        String checkedStr = (String) requestParams.get("checked");
        //System.out.println("checkedStr: " + checkedStr);
        boolean checked = Boolean.valueOf(checkedStr).booleanValue();
        //System.out.println("checked: " + checked);
        // now apply this to the
        Object valOb = this.getValue();
        //System.out.println("valOb: " + valOb);
        if(valOb instanceof Collection){
            // then all good
            //System.out.println("valob is collection");
            log.debug("valOb is collection");
            Collection c = (Collection) valOb;
            updateChosenValue(c, elname, elvalue, checked);
        } else {
            log.error("Invalid chosen values type in HtmlSelectManyCheckbox");
        }

        // now the rest of the lifecycle
        processValidators(context);
        processUpdates(context);
        //context.getViewRoot().processApplication(context);
    }
    /**
     * Will find the chosen value in the chosenValues list and update set or unset accordingly.
     *
     * @param c
     * @param elname
     * @param elvalue
     * @param checked
     */
    public void updateChosenValue(Collection c, String elname, String elvalue, boolean checked) {
        boolean found = false;
        if (c != null) {
            Iterator iter = c.iterator();
            while (iter.hasNext())
            {
                String value = (String) iter.next();
                if (value.equals(elvalue)) {
                    found = true;
                    if (!checked) {
                        // then remove
                        log.debug("Removing elvalue: " + elvalue);
                        iter.remove();
                    } else {
                        // would this ever happen?
                    }
                    break;
                }
            }
            if (!found && checked) {
                // then add it
                log.debug("Adding elvalue: " + elvalue);
                c.add(elvalue);
            }
        } else {
            log.error("LIST IS NULL!!!");
        }
    }

    public void encodeAjax(FacesContext context) throws IOException
    {
        //log.debug("encodeAjax in HtmlSelectManyCheckbox");
        if (context == null) throw new NullPointerException("context");
        if (!isRendered()) return;
        Renderer renderer = getRenderer(context);

        if (renderer != null && renderer instanceof AjaxRenderer)
        {
            ((AjaxRenderer) renderer).encodeAjax(context, this);

        }
    }

    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[4];
        values[0] = super.saveState(context);
        values[1] = onSuccess;
        values[2] = onFailure;
        values[3] = onStart;
        return ((Object) (values));
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        onSuccess = (String)values[1];
        onFailure = (String)values[2];
        onStart = (String)values[3];
    }

    public String getOnSuccess()
    {
        return onSuccess;
    }

    public void setOnSuccess(String onSuccess)
    {
        this.onSuccess = onSuccess;
    }

    public String getOnFailure()
    {
        return onFailure;
    }

    public void setOnFailure(String onFailure)
    {
        this.onFailure = onFailure;
    }

    public String getOnStart()
    {
        return onStart;
    }

    public void setOnStart(String onStart)
    {
        this.onStart = onStart;
    }



}
