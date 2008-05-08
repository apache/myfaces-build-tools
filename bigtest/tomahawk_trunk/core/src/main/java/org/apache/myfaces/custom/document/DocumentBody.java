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
package org.apache.myfaces.custom.document;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Document to enclose the document body. If not otherwise possible you can use
 * state="start|end" to demarkate the document boundaries
 * 
 * @JSFComponent
 *   name = "t:documentBody"
 *   tagClass = "org.apache.myfaces.custom.document.DocumentBodyTag"
 *   
 * @author Mario Ivankovits (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DocumentBody extends AbstractDocument
{
	public static final String COMPONENT_TYPE = "org.apache.myfaces.DocumentBody";
	private static final String DEFAULT_RENDERER_TYPE = DocumentBodyRenderer.RENDERER_TYPE;
    private String _onload;
    private String _onunload;
    private String _onresize;
    private String _onkeypress;

	public DocumentBody()
	{
		super(DEFAULT_RENDERER_TYPE);
	}

    /**
     * @param localValue
     * @param valueBindingName
     * @return the value
     */
    private Object getLocalOrValueBindingValue(Object localValue,
                    String valueBindingName)
    {
        if (localValue != null)
            return localValue;
        ValueBinding vb = getValueBinding(valueBindingName);
        return vb != null ? vb.getValue(getFacesContext()) : null;
    }

    public void setOnload(String onload)
    {
        _onload = onload;
    }

    public String getOnload()
    {
        return (String) getLocalOrValueBindingValue(_onload, "onload");
    }

    public void setOnunload(String onunload)
    {
        _onunload = onunload;
    }

    public String getOnunload()
    {
        return (String) getLocalOrValueBindingValue(_onunload, "onunload");
    }

    public void setOnresize(String onresize)
    {
        _onresize = onresize;
    }

    public String getOnresize()
    {
        return (String) getLocalOrValueBindingValue(_onresize, "onresize");
    }

    public void setOnkeypress(String onkeypress)
    {
        _onkeypress = onkeypress;
    }

    public String getOnkeypress()
    {
        return (String) getLocalOrValueBindingValue(_onkeypress, "onkeypress");
    }

    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[5];
        values[0] = super.saveState(context);
        values[1] = _onload;
        values[2] = _onunload;
        values[3] = _onresize;
        values[4] = _onkeypress;
        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        _onload = (String) values[1];
        _onunload = (String) values[2];
        _onresize = (String) values[3];
        _onkeypress = (String) values[4];
    }
}