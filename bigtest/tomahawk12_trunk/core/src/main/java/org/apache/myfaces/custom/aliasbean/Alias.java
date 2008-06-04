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
package org.apache.myfaces.custom.aliasbean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A helper bean used by both AliasBean and AliasBeansScope components.
 * <p>
 * An Alias instance represents a single mapping from a "temporary" bean name
 * to the real bean that temporary name should reference. When this alias
 * is "activated" the temporary name is registered and when the alias is
 * "deactivated" the temporary name is removed.
 *
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

class Alias {
    static final Log log = LogFactory.getLog(Alias.class);
	
	private transient UIComponent _aliasComponent;
	private String _aliasBeanExpression;
    private String _valueExpression;
	private transient boolean _active = false;
	
	private transient Object evaluatedExpression = null;

	Alias(AliasBean aliasComponent){
		this._aliasComponent = aliasComponent;
	}

	/**
	 * Define the temporary/transient name that will exist while this alias
	 * is "active" (in scope). This is usually a constant string.
	 */
	void setAliasBeanExpression(String aliasBeanExpression){
		this._aliasBeanExpression = aliasBeanExpression;
	}
	
	/**
	 * Define the object that will be referenced by the temporary/transient
	 * name while it exists.
	 * <p>
	 * This can be a constant, but is more usually an EL expression. The value is
	 * recalculated each time this alias is "activated".
	 */
	void setValueExpression(String valueExpression){
		this._valueExpression = valueExpression;
	}
	
	String getValueExpression(){
		return _valueExpression;
	}
	
	boolean isActive(){
		return _active;
	}
	
	String[] saveState(){
		return new String[]{_aliasBeanExpression, _valueExpression};
	}
	
	void restoreState(Object state){
		String[] values = (String[]) state;
		_aliasBeanExpression = values[0];
		_valueExpression = values[1];
	}
	
	private void computeEvaluatedExpression(FacesContext facesContext){
		if( evaluatedExpression != null )
			return;
		
		ValueBinding valueVB = null;
        if (_valueExpression == null) {
            valueVB = _aliasComponent.getValueBinding("value");
            _valueExpression = valueVB.getExpressionString();
        }

        if( valueVB == null ){
            if( _valueExpression.startsWith("#{") ){
                valueVB = facesContext.getApplication().createValueBinding(_valueExpression);
				evaluatedExpression = valueVB.getValue(facesContext);
            }else{
				evaluatedExpression = _valueExpression;
            }
        }else{
			evaluatedExpression = valueVB.getValue(facesContext);
        }
	}

	/**
	 * Activate this alias (ie create the temporary name).
	 */
	void make(FacesContext facesContext){
		if( _active )
			return;

        ValueBinding aliasVB;
        if (_aliasBeanExpression == null) {
            aliasVB = _aliasComponent.getValueBinding("alias");
			if( aliasVB == null )
				return;
            _aliasBeanExpression = aliasVB.getExpressionString();
			if( _aliasBeanExpression == null )
				return;
        } else {
            aliasVB = facesContext.getApplication().createValueBinding(_aliasBeanExpression);
        }

		computeEvaluatedExpression( facesContext );
		
        aliasVB.setValue(facesContext, evaluatedExpression);
		_active = true;

        log.debug("makeAlias: " + _valueExpression + " = " + _aliasBeanExpression);
	}
	
	/**
	 * Deactivate this alias (ie remove the temporary name).
	 */
	void remove(FacesContext facesContext){
        _active = false;
		if( evaluatedExpression == null )
			return;
        
        evaluatedExpression = null;

        log.debug("removeAlias: " + _valueExpression + " != " + _aliasBeanExpression);
        ValueBinding aliasVB = _aliasComponent.getValueBinding("alias");
        if( aliasVB != null )
			aliasVB.setValue(facesContext, null);
	}
}
