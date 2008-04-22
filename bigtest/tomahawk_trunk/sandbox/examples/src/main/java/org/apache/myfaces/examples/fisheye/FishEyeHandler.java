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
package org.apache.myfaces.examples.fisheye;

import java.io.Serializable;
import java.util.*;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.component.UIComponent;

import org.apache.myfaces.custom.navmenu.UINavigationMenuItem;
import org.apache.myfaces.custom.fisheye.FishEyeItem;
import org.apache.myfaces.custom.fisheye.FishEyeCommandLink;

/**
 * Handler for the FishEye example
 * 
 * @author Jurgen Lust (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FishEyeHandler implements Serializable
{
    private String _actionName;
    private static BundleMap _labels;
    static {
        _labels = new BundleMap(ResourceBundle.getBundle("org.apache.myfaces.examples.fisheye.labels",
                                          Locale.ENGLISH,
                                          Thread.currentThread().getContextClassLoader()));
    }

    public FishEyeHandler()
    {
        this._actionName = "please click on a menu item";
    }

    public String getActionName()
    {
        return _actionName;
    }

    public void processAction(ActionEvent event) throws AbortProcessingException
    {
        UIComponent comp =  event.getComponent();
        String caption;
        if (comp instanceof FishEyeCommandLink) {
            caption = ((FishEyeCommandLink) comp).getCaption();
        } else {
            // deprecated
            caption = ((UINavigationMenuItem) comp).getItemLabel();
        }
        _actionName = caption + " item was clicked";
    }

    public List getItems() {
        List list = new ArrayList();
        list.add(new FooFishEyeItem("browser","images/icon_browser.png"));
        list.add(new FooFishEyeItem("cal", "images/icon_calendar.png"));
        list.add(new FooFishEyeItem("email", "images/icon_email.png"));
        list.add(new FooFishEyeItem("texteditor", "images/icon_texteditor.png"));
        list.add(new FooFishEyeItem("swupdate", "images/icon_update.png"));
        list.add(new FooFishEyeItem("users", "images/icon_users.png"));
        return list;
    }

    public class FooFishEyeItem extends FishEyeItem {
        public FooFishEyeItem(String caption, String iconSrc) {
            super(caption, iconSrc);
        }

        public void action(ActionEvent event) {
            _actionName = getCaption() + " item was clicked";
        }

    }

    public Map getLabels() {
        return _labels;
    }

    private static class BundleMap implements Map
    {
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
    }
}
