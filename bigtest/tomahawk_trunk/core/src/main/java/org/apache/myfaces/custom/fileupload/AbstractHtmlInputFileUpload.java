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
package org.apache.myfaces.custom.fileupload;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;

import org.apache.myfaces.component.AlignProperty;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.shared_tomahawk.util.MessageUtils;

/**
 * @JSFComponent
 *   name = "t:inputFileUpload"
 *   class = "org.apache.myfaces.custom.fileupload.HtmlInputFileUpload"
 *   superClass = "org.apache.myfaces.custom.fileupload.AbstractHtmlInputFileUpload"
 *   tagClass = "org.apache.myfaces.custom.fileupload.HtmlInputFileUploadTag"
 * 
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlInputFileUpload
        extends HtmlInputText
        implements UserRoleAware, AlignProperty
{
    private static final String SIZE_LIMIT_EXCEEDED = "sizeLimitExceeded";
    private static final String FILEUPLOAD_MAX_SIZE = "org.apache.myfaces.custom.fileupload.maxSize";
    private static final String FILEUPLOAD_EXCEPTION = "org.apache.myfaces.custom.fileupload.exception";
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlInputFileUpload";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.FileUpload";
    public static final String SIZE_LIMIT_MESSAGE_ID = "org.apache.myfaces.FileUpload.SIZE_LIMIT";

    public AbstractHtmlInputFileUpload()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setUploadedFile(UploadedFile upFile)
    {
        setValue(upFile);
    }

    public UploadedFile getUploadedFile()
    {
        return (UploadedFile)getValue();
    }
    
    /**
     * @JSFProperty
     */
	public abstract String getStorage();
    /**
     * @JSFProperty
     */
    public abstract String getAccept();

    public boolean isRendered()
    {
        if (!UserRoleUtils.isVisibleOnUserRole(this)) return false;
        return super.isRendered();
    }
    
    protected void validateValue(FacesContext context, Object convertedValue)
    {
        super.validateValue(context, convertedValue);
        
        if (isValid())
        {
              String exception =
                (String) context.getExternalContext().getRequestMap().get(FILEUPLOAD_EXCEPTION);
              
              if(exception != null ) {
                if(exception.equals(SIZE_LIMIT_EXCEEDED)) {
                  Integer maxSize =
                    (Integer) context.getExternalContext().getRequestMap().get(FILEUPLOAD_MAX_SIZE);
                  MessageUtils.addMessage(FacesMessage.SEVERITY_ERROR,
                              SIZE_LIMIT_MESSAGE_ID, new Object[] { getId(),
                                      maxSize},
                              getClientId(context), context);
                  setValid(false);
                }else {
                  throw new IllegalStateException("other exceptions not handled yet, exception : "+exception);
                }
             }
         }
     }
    
}
