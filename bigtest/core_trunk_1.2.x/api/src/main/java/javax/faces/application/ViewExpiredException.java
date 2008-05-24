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

package javax.faces.application;

import javax.faces.FacesException;

/**
 * See Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Stan Silvert
 */
public class ViewExpiredException extends FacesException {
    
    private String viewId;
    
    public ViewExpiredException() {
    }
    
    public ViewExpiredException(String viewId) {
        this.viewId = viewId;
    }
    
    public ViewExpiredException(String message, String viewId) {
        super(message);
        this.viewId = viewId;
    }
    
    public ViewExpiredException(String message, Throwable cause, String viewId) {
        super(message, cause);
        this.viewId = viewId;
    }
    
    public ViewExpiredException(Throwable cause, String viewId) {
        super(cause);
        this.viewId = viewId;
    }

    public String getViewId() {
        return viewId;
    }
    
    public String getMessage() {
        if (viewId != null) {
            return viewId + super.getMessage();
        }
        
        return super.getMessage();
    }
}
