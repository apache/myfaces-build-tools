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
package org.apache.myfaces.custom.autoupdatedatatable;

import org.apache.myfaces.component.html.ext.HtmlDataTable;
import org.apache.myfaces.custom.ajax.api.AjaxComponent;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;

import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.el.ValueBinding;
import java.io.IOException;

/**
 * @JSFComponent
 *   name = "s:autoUpdateDataTable"
 *   tagClass = "org.apache.myfaces.custom.autoupdatedatatable.AutoUpdateDataTableTag"
 *   
 * @author J&ouml;rg Artaker
 * @author Thomas Huber
 * @version $Revision: $ $Date: $
 *          <p/>
 *          $Log: $
 * @deprecated: Use periodicalUpdate mechanism of partial page rendering instead 
 */
public class AutoUpdateDataTable extends HtmlDataTable implements AjaxComponent{

    public static final String COMPONENT_TYPE = "org.apache.myfaces.AutoUpdateDataTable";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.AutoUpdateDataTable";

    private String _frequency;
    private String _onSuccess;

    public AutoUpdateDataTable()
    {
        super();
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    /**
     * @param context FacesContext
     * @param state Object
     */
    public void processRestoreState(FacesContext context, Object state)
	{
		super.processRestoreState(context, state);
	}

    /**
     * @param context FacesContext
     * @return Object
     */
    public Object processSaveState(FacesContext context)
	{
		return super.processSaveState(context);
	}   

    /**
     * @param context FacesContext
     * @return the values Object[]
     */
    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[3];
        values[0] = super.saveState(context);
        values[1] = _frequency;
        values[2] = _onSuccess;

        return values;
    }

    /**
     * @param context FacesContext
     * @param state Object
     */
    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _frequency = (String) values[1];
        _onSuccess = (String) values[2];
    }

    /**
     * @return the frequency String
     */
    public String getFrequency() {
        if (_frequency!= null)
            return _frequency;
        ValueBinding vb = getValueBinding("frequency");
        if( vb == null )
        	return null;
        Object eval = vb.getValue(getFacesContext());
        return eval == null ? null : eval.toString();
    }

    /**
     * @param frequency String
     */
    public void setFrequency(String frequency) {
        _frequency = frequency;
    }

    public String getOnSuccess() {
         if (_onSuccess!= null)
            return _onSuccess;
        ValueBinding vb = getValueBinding("onSuccess");
        if( vb == null )
        	return null;
        Object eval = vb.getValue(getFacesContext());
        return eval == null ? null : eval.toString();
    }

    public void setOnSuccess(String _onSuccess) {
        this._onSuccess = _onSuccess;
    }

    /**
     * @param context FacesContext
     * @throws java.io.IOException
     */
    public void encodeAjax(FacesContext context) throws IOException {
        if (context == null) throw new NullPointerException("context");
        if (!isRendered()) return;
        Renderer renderer = getRenderer(context);

         if (isValidChildren())
        {
            setPreservedDataModel(null);
        }
        
        if (renderer != null && renderer instanceof AjaxRenderer)
        {

            ((AjaxRenderer) renderer).encodeAjax(context, this);
            
        }
    }

    public void decodeAjax(FacesContext context)
    {

    }
}
