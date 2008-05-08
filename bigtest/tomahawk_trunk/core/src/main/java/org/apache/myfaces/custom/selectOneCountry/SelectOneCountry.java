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
package org.apache.myfaces.custom.selectOneCountry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;

import javax.faces.component.UISelectItems;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.model.SelectItem;

import org.apache.myfaces.component.html.ext.HtmlSelectOneMenu;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

/**
 * @JSFComponent
 *   name = "t:selectOneCountry"
 *   tagClass = "org.apache.myfaces.custom.selectOneCountry.SelectOneCountryTag"
 * 
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SelectOneCountry extends HtmlSelectOneMenu {
    public static final String COMPONENT_TYPE = "org.apache.myfaces.SelectOneCountry";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.SelectOneCountryRenderer";

    private Integer _maxLength = null;
    
    private String _emptySelection = null;

    public SelectOneCountry() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public Integer getMaxLength() {
        if (_maxLength != null) return _maxLength;
        ValueBinding vb = getValueBinding("maxLength");
        return vb != null ? (Integer)vb.getValue(getFacesContext()) : null;
    }
    public void setMaxLength(Integer maxLength) {
        _maxLength = maxLength;
    }
    
    public String getEmptySelection() {
    	if (_emptySelection != null) return _emptySelection;
        ValueBinding vb = getValueBinding("emptySelection");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }
    public void setEmptySelection(String emptySelection) {
    	_emptySelection = emptySelection;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[3];
        values[0] = super.saveState(context);
        values[1] = _maxLength;
        values[2] = _emptySelection;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _maxLength = (Integer)values[1];
        _emptySelection = (String) values[2];
    }

    private Set getFilterSet(){
        List selectItems = RendererUtils.getSelectItemList( this );
        Set set = new HashSet( selectItems.size() );

        for (Iterator i = selectItems.iterator(); i.hasNext(); )
            set.add( ((SelectItem)i.next()).getValue().toString().toUpperCase() );

        return set;
    }

    protected List getCountriesChoicesAsSelectItemList(){
        //return RendererUtils.getSelectItemList(component);

        Set filterSet = getFilterSet();

        String[] availableCountries = Locale.getISOCountries();

        Locale currentLocale;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = facesContext.getViewRoot();
        if( viewRoot != null )
            currentLocale = viewRoot.getLocale();
        else
            currentLocale = facesContext.getApplication().getDefaultLocale();


        TreeMap map = new TreeMap();
        // TreeMap is sorted according to the keys' natural order

        for(int i=0; i<availableCountries.length; i++){
            String countryCode = availableCountries[i];
            if( ! filterSet.isEmpty() && ! filterSet.contains(countryCode))
                continue;
            Locale tmp = new Locale(countryCode, countryCode);
            map.put(tmp.getDisplayCountry(currentLocale), countryCode);
        }

        List countriesSelectItems = new ArrayList( map.size() );
        if(getEmptySelection() != null)
        	countriesSelectItems.add(new SelectItem("", getEmptySelection()));

        Integer maxLength = getMaxLength();
        int maxDescriptionLength = maxLength==null ? Integer.MAX_VALUE : maxLength.intValue();
        if( maxDescriptionLength < 5 )
            maxDescriptionLength = 5;

        for(Iterator i = map.keySet().iterator(); i.hasNext(); ){
            String countryName = (String) i.next();
            String countryCode = (String) map.get( countryName );
            String label;
            if( countryName.length() <= maxDescriptionLength )
                label = countryName;
            else
                label = countryName.substring(0, maxDescriptionLength-3)+"...";

            countriesSelectItems.add( new SelectItem(countryCode, label) );
        }

        return countriesSelectItems;
    }

    protected void validateValue(FacesContext context, Object value) {
    	UISelectItems selectItems = new UISelectItems();
    	selectItems.setTransient(true);
    	selectItems.setValue(getCountriesChoicesAsSelectItemList());
    	getChildren().add(selectItems);
    	
    	super.validateValue(context,value);
    }
}
