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
package org.apache.myfaces.custom.fisheye;

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Provide a FishEye toolbar component from the DOJO toolkit
 * 
 * A Mac OSX-style toolbar, using the DOJO toolkit.
 * 
 * @see <a href="http://dojotoolkit.org/">http://dojotoolkit.org/</a>
 * 
 * @JSFComponent
 *   name = "s:fishEyeNavigationMenu"
 *   class = "org.apache.myfaces.custom.fisheye.HtmlFishEyeNavigationMenu"
 *   superClass = "org.apache.myfaces.custom.fisheye.AbstractHtmlFishEyeNavigationMenu"
 *   tagClass = "org.apache.myfaces.custom.fisheye.HtmlFishEyeNavigationMenuTag"
 *   
 * @author Jurgen Lust (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlFishEyeNavigationMenu extends UIData
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.FishEyeList";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.FishEyeList";

    public static final String EDGE_BOTTOM = "bottom";
    public static final String EDGE_CENTER = "center";
    public static final String EDGE_LEFT = "left";
    public static final String EDGE_RIGHT = "right";
    public static final String EDGE_TOP = "top";
    public static final String HORIZONTAL_ORIENTATION = "horizontal";
    public static final String VERTICAL_ORIENTATION = "vertical";
    private static final String NODE_STAMP_FACET_NAME = "nodeStamp";

    public void processDecodes(FacesContext context) {
        super.processDecodes(context);
        int first = getFirst();
        int rows = getRows();
        int last;
        if (rows == 0)
        {
            last = getRowCount();
        }
        else
        {
            last = first + rows;
        }
        for (int rowIndex = first; last==-1 || rowIndex < last; rowIndex++)
        {
            setRowIndex(rowIndex);

            //scrolled past the last row
            if (!isRowAvailable())
                break;

            for (Iterator it = getChildren().iterator(); it.hasNext();)
            {
                UIComponent child = (UIComponent) it.next();
                if (child instanceof FishEyeCommandLink)
                {
                    if (!child.isRendered())
                    {
                        //Column is not visible
                        continue;
                    }
                    child.processDecodes(context);
                }
            }
        }
    }

    public void setValueBinding(String string, ValueBinding valueBinding) {
        super.setValueBinding(string, valueBinding);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * @JSFProperty
     */
    public abstract Integer getVisibleWindow();

    /**
     * @JSFFacet
     */
    public UIComponent getNodeStamp()
    {
        return (UIComponent) getFacets().get(NODE_STAMP_FACET_NAME);
    }

    /**
     * @JSFProperty
     */
    public abstract String getAttachEdge();

    /**
     * @JSFProperty
     */
    public abstract Boolean getConservativeTrigger();

    /**
     * @JSFProperty
     */
    public abstract Integer getEffectUnits();

    /**
     * @JSFProperty
     */
    public abstract Integer getItemHeight();

    /**
     * @JSFProperty
     */
    public abstract Integer getItemMaxHeight();

    /**
     * @JSFProperty
     */
    public abstract Integer getItemMaxWidth();

    /**
     * @JSFProperty
     */
    public abstract Integer getItemPadding();

    /**
     * @JSFProperty
     */
    public abstract Integer getItemWidth();

    /**
     * @JSFProperty
     */
    public abstract String getLabelEdge();

    /**
     * @JSFProperty
     */
    public abstract String getOrientation();

    public boolean getRendersChildren()
    {
        return true;
    }

}
