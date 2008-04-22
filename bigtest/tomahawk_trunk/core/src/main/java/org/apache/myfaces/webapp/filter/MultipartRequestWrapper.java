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
package org.apache.myfaces.webapp.filter;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.DefaultFileItemFactory;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MultipartRequestWrapper
        extends HttpServletRequestWrapper
{
    private static Log log = LogFactory.getLog(MultipartRequestWrapper.class);
    public static final String UPLOADED_FILES_ATTRIBUTE = "org.apache.myfaces.uploadedFiles";
    public static final String WWW_FORM_URLENCODED_TYPE = "application/x-www-form-urlencoded";

    HttpServletRequest request = null;
    HashMap parametersMap = null;
    DiskFileUpload fileUpload = null;
    HashMap fileItems = null;
    int maxSize;
    int thresholdSize;
    String repositoryPath;

    public MultipartRequestWrapper(HttpServletRequest request,
                                   int maxSize, int thresholdSize,
                                   String repositoryPath){
        super( request );
        this.request = request;
        this.maxSize = maxSize;
        this.thresholdSize = thresholdSize;
        this.repositoryPath = repositoryPath;
    }

    private void parseRequest() {
        fileUpload = new DiskFileUpload();
        fileUpload.setFileItemFactory(new DefaultFileItemFactory());
        fileUpload.setSizeMax(maxSize);

        fileUpload.setSizeThreshold(thresholdSize);

        if(repositoryPath != null && repositoryPath.trim().length()>0)
            fileUpload.setRepositoryPath(repositoryPath);

        String charset = request.getCharacterEncoding();
        fileUpload.setHeaderEncoding(charset);


        List requestParameters = null;
        try{
            requestParameters = fileUpload.parseRequest(request);
        } catch (FileUploadBase.SizeLimitExceededException e) {

            request.setAttribute(
                "org.apache.myfaces.custom.fileupload.exception","sizeLimitExceeded");
            request.setAttribute("org.apache.myfaces.custom.fileupload.maxSize",
                new Integer(maxSize));
            
            requestParameters = Collections.EMPTY_LIST;

        }catch(FileUploadException fue){
            log.error("Exception while uploading file.", fue);
            requestParameters = Collections.EMPTY_LIST;
        }

        parametersMap = new HashMap( requestParameters.size() );
        fileItems = new HashMap();

        for (Iterator iter = requestParameters.iterator(); iter.hasNext(); ){
            FileItem fileItem = (FileItem) iter.next();

            if (fileItem.isFormField()) {
                String name = fileItem.getFieldName();

                // The following code avoids commons-fileupload charset problem.
                // After fixing commons-fileupload, this code should be
                //
                // 	String value = fileItem.getString();
                //
                String value = null;
                if ( charset == null) {
                    value = fileItem.getString();
                } else {
                    try {
                        value = new String(fileItem.get(), charset);
                    } catch (UnsupportedEncodingException e){
                        value = fileItem.getString();
                    }
                }

                addTextParameter(name, value);
            } else { // fileItem is a File
                if (fileItem.getName() != null) {
                    fileItems.put(fileItem.getFieldName(), fileItem);
                }
            }
        }

        //Add the query string paramters
        for (Iterator it = request.getParameterMap().entrySet().iterator(); it.hasNext(); )
        {
            Map.Entry entry = (Map.Entry)it.next();

            Object value = entry.getValue();

            if(value instanceof String[])
            {
                String[] valuesArray = (String[])entry.getValue();
                for (int i = 0; i < valuesArray.length; i++)
                {
                    addTextParameter((String)entry.getKey(), valuesArray[i]);
                }
            }
            else if(value instanceof String)
            {
                String strValue = (String)entry.getValue();
                addTextParameter((String)entry.getKey(), strValue);
            }
            else if(value != null)
                throw new IllegalStateException("value of type : "+value.getClass()+" for key : "+
                        entry.getKey()+" cannot be handled.");

        }
    }

    private void addTextParameter(String name, String value){
        if( ! parametersMap.containsKey( name ) ){
            String[] valuesArray = {value};
            parametersMap.put(name, valuesArray);
        }else{
            String[] storedValues = (String[])parametersMap.get( name );
            int lengthSrc = storedValues.length;
            String[] valuesArray = new String[lengthSrc+1];
            System.arraycopy(storedValues, 0, valuesArray, 0, lengthSrc);
            valuesArray[lengthSrc] = value;
            parametersMap.put(name, valuesArray);
        }
    }

    public Enumeration getParameterNames() {
        if( parametersMap == null ) parseRequest();

        return Collections.enumeration( parametersMap.keySet() );
    }

    public String getParameter(String name) {
        if( parametersMap == null ) parseRequest();

        String[] values = (String[])parametersMap.get( name );
        if( values == null )
            return null;
        return values[0];
    }

    public String[] getParameterValues(String name) {
        if( parametersMap == null ) parseRequest();

        return (String[])parametersMap.get( name );
    }

    public Map getParameterMap() {
        if( parametersMap == null ) parseRequest();

        return parametersMap;
    }

    // Hook for the x:inputFileUpload tag.
    public FileItem getFileItem(String fieldName) {
        if( fileItems == null ) parseRequest();

        return (FileItem) fileItems.get( fieldName );
    }

    /**
     * Not used internally by MyFaces, but provides a way to handle the uploaded files
     * out of MyFaces.
     */
    public Map getFileItems(){
        if( fileItems == null ) parseRequest();
        return fileItems;
    }


    public Object getAttribute(String string) {
        if (string.equals(UPLOADED_FILES_ATTRIBUTE)) {
            return getFileItems();
        }
        return super.getAttribute(string);
    }
    
    public String getContentType()
    {
      return WWW_FORM_URLENCODED_TYPE;
    }     
}