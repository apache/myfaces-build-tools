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
package org.apache.myfaces.custom.tree;

import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;


/**
 * HTML image link.
 *
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision$ $Date$
 */
public class HtmlTreeImageCommandLink
        extends HtmlCommandLink
{

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlTreeImageCommandLink";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.HtmlTreeImageCommandLink";

    private String image;


    public HtmlTreeImageCommandLink()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }


    public String getFamily()
    {
        return "org.apache.myfaces.HtmlTree";
    }


    public String getImage()
    {
        if (image != null) return image;
        ValueBinding vb = getValueBinding("image");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }


    public void setImage(String image)
    {
        this.image = image;
    }


    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = image;
        return ((Object)(values));
    }


    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        image = (String)values[1];
    }
}
