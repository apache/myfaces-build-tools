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
package org.apache.myfaces.custom.buffer;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * A component that renders its child components into an in-memory buffer rather than
 * render them directly to the response stream.
 * <p>
 * Property "into" is an EL expression that specifies where to store a String holding
 * the results of rendering all the children of this component; this is assigned to
 * after rendering of this component (and its children) is complete.
 * <p>
 * Typically, an h:output tag is then used later in the same page to output the buffer
 * contents.
 * <p>
 * This can be useful with JSF1.1/JSP2.0 to work around the well-known problem where
 * on first render of a page, a component "A" cannot reference a component "B" which is
 * defined later in the page because it has not yet been created. A solution is to define
 * "B" before "A", but wrapped in a Buffer component. Component A can then be rendered
 * and successfully reference "B" because it now exists. And later in the page, the buffer
 * contents can then be output, preserving the original layout.
 * <p>
 * This can also be useful when rendering the same data block multiple times within a page.
 * For example, a datatable can be rendered with a datascroller both before and after it;
 * first render the table into a buffer B1, then render the datascroller into a buffer B2,
 * then output buffers B2,B1,B2.
 * 
 * @JSFComponent
 *   name = "t:buffer"
 *   class = "org.apache.myfaces.custom.buffer.Buffer"
 *   superClass = "org.apache.myfaces.custom.buffer.AbstractBuffer"
 *   tagClass = "org.apache.myfaces.custom.buffer.BufferTag"
 *   
 * @JSFJspProperty 
 *   name = "rendered"
 *   returnType = "boolean" 
 *   tagExcluded = "true"
 *   
 * @JSFJspProperty
 *   name = "binding"
 *   returnType = "java.lang.String"
 *   tagExcluded = "true"
 * 
 * @JSFJspProperty
 *   name = "id"
 *   returnType = "java.lang.String"
 *   tagExcluded = "true" 
 * 
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractBuffer extends UIComponentBase{

    public static final String COMPONENT_TYPE = "org.apache.myfaces.Buffer";
    public static final String COMPONENT_FAMILY = "javax.faces.Data";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Buffer";

    protected abstract String getLocalInto();
    
    public abstract void setInto(String into);

    void fill(String content, FacesContext facesContext){
        ValueBinding intoVB;

        if (getLocalInto() == null) {
            intoVB = getValueBinding("into");
            setInto(intoVB.getExpressionString());
        } else {
            intoVB = facesContext.getApplication().createValueBinding( getLocalInto() );
        }

        intoVB.setValue(facesContext, content);
    }
    
    /**
     * An EL expression that specifies where to store a String holding 
     * the results of rendering all the children of this component; 
     * this is assigned to after rendering of this component (and its 
     * children) is complete.
     * 
     * @JSFProperty
     *   required = "true"
     *   localMethod = "true"    
     */
    protected abstract String getInto(); 

}