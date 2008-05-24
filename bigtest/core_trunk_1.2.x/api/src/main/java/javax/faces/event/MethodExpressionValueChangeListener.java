/*
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.faces.event;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

/**
 * See Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Stan Silvert
 */
public class MethodExpressionValueChangeListener implements ValueChangeListener, StateHolder {
    
    private MethodExpression methodExpression;
    
    private boolean isTransient = false;
    
    /** Creates a new instance of MethodExpressionValueChangeListener */
    public MethodExpressionValueChangeListener() {
    }
    
    public MethodExpressionValueChangeListener(MethodExpression methodExpression) {
        this.methodExpression = methodExpression;
    }
    
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
        try {
            Object[] params = new Object[]{event};
            methodExpression.invoke(elContext(), params);
        } catch (Exception e) {
            throw new AbortProcessingException(e);
        }
    }
    
    private ELContext elContext() {
        return FacesContext.getCurrentInstance().getELContext();
    }
    
    public void restoreState(FacesContext context, Object state) {
        methodExpression = (MethodExpression)state;
    }

    public Object saveState(FacesContext context) {
        return methodExpression;
    }

    public void setTransient(boolean newTransientValue) {
        isTransient = newTransientValue;
    }

    public boolean isTransient() {
        return isTransient;
    }

}
