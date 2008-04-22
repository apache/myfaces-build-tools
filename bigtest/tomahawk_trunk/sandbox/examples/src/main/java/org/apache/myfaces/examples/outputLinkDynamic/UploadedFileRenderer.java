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
package org.apache.myfaces.examples.outputLinkDynamic;

import java.io.IOException;
import java.io.InputStream;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.custom.dynamicResources.ResourceContext;
import org.apache.myfaces.custom.dynamicResources.ResourceRenderer;

/**
 * @author Mathias Broekelmann, Sylvain Vieujot
 *
 */
public class UploadedFileRenderer implements ResourceRenderer
{
    private OutputLinkDynamicBean _outputLinkDynamicBean;

    public void setContext(FacesContext facesContext, ResourceContext resourceContext)
    {
        ValueBinding vb = facesContext.getApplication().createValueBinding(
                "#{outputLinkDynamicBean}");
        OutputLinkDynamicBean value = (OutputLinkDynamicBean) vb.getValue(facesContext);
        if (value == null)
        {
            throw new IllegalStateException("managed bean outputLinkDynamicBean not found");
        }
        _outputLinkDynamicBean = value;
    }
    
    /**
     * @see org.apache.myfaces.custom.graphicimagedynamic.ImageRenderer#getContentLength()
     */
    public int getContentLength() {
		return _outputLinkDynamicBean.isUploaded() ? (int)_outputLinkDynamicBean.getUpFile().getSize() : -1;
	}

    /**
     * @see org.apache.myfaces.custom.graphicimagedynamic.ImageRenderer#getContentType()
     */
    public String getContentType()
    {
        return _outputLinkDynamicBean.isUploaded() ? _outputLinkDynamicBean.getUpFile()
                .getContentType() : null;
    }

    /**
     * @see org.apache.myfaces.custom.graphicimagedynamic.ImageRenderer#renderImage(javax.faces.context.FacesContext, org.apache.myfaces.custom.graphicimagedynamic.ImageContext, java.io.OutputStream)
     */
    public void renderResource(ResponseStream out)
            throws IOException
    {
        if (_outputLinkDynamicBean.isUploaded())
        {
            InputStream is = _outputLinkDynamicBean.getUpFile().getInputStream();
            try
            {
                byte[] buffer = new byte[1024];
                int len = is.read(buffer);
                while (len != -1)
                {
                    out.write(buffer, 0, len);
                    len = is.read(buffer);
                }
            }
            finally
            {
                is.close();
            }
        }
    }
}
