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
package org.apache.myfaces.custom.dynaForm.component.dynaForm;

import org.apache.myfaces.custom.dynaForm.metadata.Extractor;
import org.apache.myfaces.custom.dynaForm.uri.FacesUriResolver;
import org.apache.myfaces.custom.dynaForm.uri.UriResolver.Configuration;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.persistence.Transient;

/**
 * the dynaForm component<br />
 * handles whats needed to dynamically create JSF component trees
 */
public class DynaForm extends UIComponentBase
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.dynaForm.DynaForm";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.dynaForm.DynaForm";
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.dynaForm.DynaForm";

    public static final String DYNA_FORM_CREATED = "org.apache.myfaces.dynaForm.CREATED";

    private String uri;
    private String bundle;
    private String var;
    private String valueBindingPrefix;
    private Boolean displayOnly;
    private Boolean exclusiveFields;

    @Transient
    private transient Configuration configuration = null;
    private transient DynaConfigs formConfigs = null;

    @Override
    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    /**
     * @see #setUri
     */
    public String getUri()
    {
        if (uri != null)
        {
            return uri;
        }
        ValueBinding vb = getValueBinding("uri");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    /**
     * how to reach the model<br />
     * e.g. ejb:fqn.to.model.Entity means use EJB3 to work with the given entity<br />
     * beside the model itself the uri scheme also configures what to do with this entity
     *
     * @see org.apache.myfaces.custom.dynaForm.uri.UriResolver
     */
    public void setUri(String uri)
    {
        this.uri = uri;
    }

    /**
     * @see #setValueBindingPrefix
     */
    public String getValueBindingPrefix()
    {
        if (valueBindingPrefix != null)
        {
            return valueBindingPrefix;
        }
        ValueBinding vb = getValueBinding("valueBindingPrefix");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    /**
     * The value binding prefix which will be used to create the real value binding.
     * If this is missing and the layout component has a "var" attribute its
     * value will be used.
     */
    public void setValueBindingPrefix(String valueBindingPrefix)
    {
        this.valueBindingPrefix = valueBindingPrefix;
    }

    /**
     * @see #setBundle
     */
    public String getBundle()
    {
        if (bundle != null)
        {
            return bundle;
        }
        ValueBinding vb = getValueBinding("bundle");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    /**
     * The bundle to use to convert the labels to readable strings
     */
    public void setBundle(String bundle)
    {
        this.bundle = bundle;
    }

    /**
     * @see #setVar
     */
    public String getVar()
    {
        if (var != null)
        {
            return var;
        }
        ValueBinding vb = getValueBinding("var");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    /**
     * Display the whole form in read only mode
     */
    public void setDisplayOnly(boolean displayOnly)
    {
        this.displayOnly = displayOnly;
    }

    /**
     * @see #setVar
     */
    public boolean isDisplayOnly()
    {
        if (displayOnly != null)
        {
            return displayOnly.booleanValue();
        }
        ValueBinding vb = getValueBinding("displayOnly");
        if (vb != null)
        {
            Boolean ret = (Boolean) vb.getValue(getFacesContext());
            if (ret != null)
            {
                return ret.booleanValue();
            }
        }

        return false;
    }

    /**
     * Process only fields listed by their facets
     */
    public void setExclusiveFields(boolean exclusiveFields)
    {
        this.exclusiveFields = exclusiveFields;
    }

    /**
     * @see #setExclusiveFields(boolean)
     */
    public boolean isExclusiveFields()
    {
        if (exclusiveFields != null)
        {
            return exclusiveFields.booleanValue();
        }
        ValueBinding vb = getValueBinding("exclusiveFields");
        if (vb != null)
        {
            Boolean ret = (Boolean) vb.getValue(getFacesContext());
            if (ret != null)
            {
                return ret.booleanValue();
            }
        }

        return false;
    }

    /**
     * the var name used to allow access to the form controller
     */
    public void setVar(String var)
    {
        this.var = var;
    }

    @Override
    public void restoreState(FacesContext context, Object stateArray)
    {
        Object[] states = (Object[]) stateArray;
        super.restoreState(context, states[0]);
        uri = (String) states[1];
        var = (String) states[2];
        displayOnly = (Boolean) states[3];
        bundle = (String) states[4];
        valueBindingPrefix = (String) states[5];
    }

    @Override
    public Object saveState(FacesContext context)
    {
        return new Object[]
            {
                super.saveState(context),
                uri,
                var,
                displayOnly,
                bundle,
                valueBindingPrefix
            };
    }

    /**
     * get the overall configuration based on the given uri
     *
     * @see org.apache.myfaces.custom.dynaForm.uri.UriResolver
     */
    protected Configuration getConfiguration()
    {
        if (configuration == null)
        {
            configuration = new FacesUriResolver().resolveUri(getUri());
        }
        return configuration;
    }

    public Extractor getExtractor()
    {
        return getConfiguration().getExtractor();
    }

    /**
     * try to find a parent dyna form+
     */
    protected DynaForm findParentDynaForm(DynaForm start)
    {
        UIComponent component = start.getParent();
        while (component != null)
        {
            if (component instanceof DynaForm)
            {
                return (DynaForm) component;
            }
            component = component.getParent();
        }

        return null;
    }

    /**
     * Find the dynaForm component
     */
    public static DynaForm getDynaForm(UIComponent component)
    {
        if (component == null)
        {
            throw new IllegalArgumentException("component==null not allowed");
        }

        UIComponent dynaFormComponent = component;
        while (dynaFormComponent != null && (!(dynaFormComponent instanceof DynaForm)))
        {
            dynaFormComponent = dynaFormComponent.getParent();
        }
        if (dynaFormComponent == null)
        {
            throw new IllegalArgumentException("component with id '" + component.getId() + "' not contained in an dynaForm");
        }

        return (DynaForm) dynaFormComponent;
    }

    /**
     * get access to the special form configurations
     */
    public DynaConfigs getFormConfigs()
    {
        if (formConfigs != null)
        {
            return formConfigs;
        }

        for (Object obj : getChildren())
        {
            if (obj instanceof DynaConfigs)
            {
                formConfigs = (DynaConfigs) obj;
                return formConfigs;
			}
		}

		return null;
	}
}