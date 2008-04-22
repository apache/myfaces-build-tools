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
package org.apache.myfaces.custom.loadbundle;

import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import java.io.IOException;
import java.util.*;

/**
 * A load-bundle alternative which allows to use load-bundle
 * even on AJAX-enabled pages.
 * <p/>
 * A component that allows to load bundles not only on rendering, but whenever the
 * page author needs it. By default, it loads it on every lifecycle phase
 * except restore-state and save-state.* 
 * <p/>
 * The core-load-bundle only loads its message-bundle
 * on rendering - this load-bundle does it on every life-cycle,
 * and optionally whenever the method loadBundle is called.
 *
 * @author Martin Marinschek
 */
public class LoadBundle extends UIComponentBase implements StateHolder {

    private static Log log = LogFactory.getLog(LoadBundle.class);

    public static final String COMPONENT_TYPE = "org.apache.myfaces.LoadBundle";
    public static final String COMPONENT_FAMILY = "org.apache.myfaces.LoadBundle";

    private String basename;
    private String var;

    private boolean alreadyLoaded = false;

    public LoadBundle()
    {
        // Default constructor
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public String getBasename()
    {
        if (basename != null)
            return basename;
        ValueBinding vb = getValueBinding("basename");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setBasename(String basename)
    {
        this.basename = basename;
    }

    public String getVar()
    {
        if (var != null)
            return var;
        ValueBinding vb = getValueBinding("var");
        return vb != null ? _ComponentUtils.getStringValue(getFacesContext(), vb) : null;
    }

    public void setVar(String var)
    {
        this.var = var;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        this.basename = (String) values[0];
        this.var = (String) values[1];
    }

    public Object saveState(FacesContext context)
    {
        Object[] values = new Object[2];
        values[0] = this.basename;
        values[1] = this.var;
        return values;
    }

    public Object processSaveState(FacesContext context)
    {
        //intentionally don't do anything special in this phase
        return super.processSaveState(context);
    }

    public void processRestoreState(FacesContext context, Object state)
    {
        //intentionally don't do anything special in this phase
        super.processRestoreState(context, state);
    }

    public void processValidators(FacesContext context)
    {
        loadBundle();
        super.processValidators(context);
    }

    public void processDecodes(FacesContext context)
    {
        loadBundle();
        super.processDecodes(context);
    }

    public void processUpdates(FacesContext context)
    {
        loadBundle();
        super.processUpdates(context);
    }


  public void encodeBegin(FacesContext context) throws IOException {
        loadBundle();
  }


  public void encodeEnd(FacesContext context) throws IOException {
  }

  public void queueEvent(FacesEvent event)
    {
        super.queueEvent(new FacesEventWrapper(event, this));
    }

    public void broadcast(FacesEvent event) throws AbortProcessingException {
        loadBundle();

        if (event instanceof FacesEventWrapper)
        {
            FacesEvent originalEvent = ((FacesEventWrapper) event).getWrappedFacesEvent();
            originalEvent.getComponent().broadcast(originalEvent);
        }
        else
        {
            super.broadcast(event);
        }
    }

    public void loadBundle() {
        if(alreadyLoaded)
            return;

        resolveBundle(getBasename());

        alreadyLoaded = true;
    }

    /**
     * This method is copied over from LoadBundle in core.
     * If you change anything here, think about changing it there as well.
     *
     * @param resolvedBasename
     */
    private void resolveBundle(String resolvedBasename) {
        //ATTENTION: read comment above before changing this!
        FacesContext facesContext = FacesContext.getCurrentInstance();

        UIViewRoot viewRoot = facesContext.getViewRoot();
        if (viewRoot == null)
        {
            throw new IllegalStateException("No view root! LoadBundle must be nested inside <f:view> action.");
        }

        Locale locale = viewRoot.getLocale();
        if (locale == null)
        {
            locale = facesContext.getApplication().getDefaultLocale();
        }

        final ResourceBundle bundle;
        try
        {
            bundle = ResourceBundle.getBundle(resolvedBasename,
                                              locale,
                                              Thread.currentThread().getContextClassLoader());

            facesContext.getExternalContext().getRequestMap().put(getVar(),
                                                                  new BundleMap(bundle));

        }
        catch (MissingResourceException e)
        {
            log.error("Resource bundle '" + resolvedBasename + "' could not be found.");
        }
        //ATTENTION: read comment above before changing this!
    }


    /**
     * This class is copied over from LoadBundle in myfaces-api.
     * If you change anything here, think about changing it there as well.
     *
     */
    private static class BundleMap implements Map
    {
        //ATTENTION: read javadoc
        private ResourceBundle _bundle;
        private List _values;

        public BundleMap(ResourceBundle bundle)
        {
            _bundle = bundle;
        }

        //Optimized methods

        public Object get(Object key)
        {
            try {
                return _bundle.getObject(key.toString());
            } catch (Exception e) {
                return "MISSING: " + key + " :MISSING";
            }
        }

        public boolean isEmpty()
        {
            return !_bundle.getKeys().hasMoreElements();
        }

        public boolean containsKey(Object key)
        {
        	try {
                return _bundle.getObject(key.toString()) != null;
        	} catch (MissingResourceException e) {
        		return false;
        	}
        }


        //Unoptimized methods

        public Collection values()
        {
            if (_values == null)
            {
                _values = new ArrayList();
                for (Enumeration enumer = _bundle.getKeys(); enumer.hasMoreElements(); )
                {
                    String v = _bundle.getString((String)enumer.nextElement());
                    _values.add(v);
                }
            }
            return _values;
        }

        public int size()
        {
            return values().size();
        }

        public boolean containsValue(Object value)
        {
            return values().contains(value);
        }

        public Set entrySet()
        {
            Set set = new HashSet();
            for (Enumeration enumer = _bundle.getKeys(); enumer.hasMoreElements(); )
            {
                final String k = (String)enumer.nextElement();
                set.add(new Map.Entry() {
                    public Object getKey()
                    {
                        return k;
                    }

                    public Object getValue()
                    {
                        return _bundle.getObject(k);
                    }

                    public Object setValue(Object value)
                    {
                        throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
                    }
                });
            }
            return set;
        }

        public Set keySet()
        {
            Set set = new HashSet();
            for (Enumeration enumer = _bundle.getKeys(); enumer.hasMoreElements(); )
            {
                set.add(enumer.nextElement());
            }
            return set;
        }
        //ATTENTION: read javadoc


        //Unsupported methods

        public Object remove(Object key)
        {
            throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
        }

        public void putAll(Map t)
        {
            throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
        }

        public Object put(Object key, Object value)
        {
            throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
        }

        public void clear()
        {
            throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
        }
        //ATTENTION: read javadoc
    }
}
