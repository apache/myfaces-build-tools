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

import org.apache.commons.fileupload.DefaultFileItem;
import org.apache.commons.fileupload.FileItem;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * @author Sylvain Vieujot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UploadedFileDefaultFileImpl extends UploadedFileDefaultImplBase
{
  private static final long serialVersionUID = -6401426361519246443L;
  private transient DefaultFileItem fileItem = null;
  private StorageStrategy storageStrategy;

    public UploadedFileDefaultFileImpl(final FileItem fileItem) throws IOException
    {
        super(fileItem.getName(), fileItem.getContentType());
    	this.fileItem = (DefaultFileItem) fileItem;
      storageStrategy = new DiskStorageStrategy() {

        public File getTempFile() {
          return UploadedFileDefaultFileImpl.this.fileItem.getStoreLocation();
        }

        public void deleteFileContents() {
          UploadedFileDefaultFileImpl.this.fileItem.delete();
        }
        
      };
    }


    /**
     * Answer the uploaded file contents.
     *
     * @return file contents
     */
    public byte[] getBytes() throws IOException
    {
    	byte[] bytes = new byte[(int)getSize()];
        if (fileItem != null) fileItem.getInputStream().read(bytes);
        return bytes;
    }


    /**
     * Answer the uploaded file contents input stream
     *
     * @return InputStream
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException
    {
    	return fileItem != null
               ? fileItem.getInputStream()
               : new ByteArrayInputStream(new byte[0]);
    }


    /**
     * Answer the size of this file.
     * @return long
     */
    public long getSize()
    {
    	return fileItem != null ? fileItem.getSize() : 0;
    }


    public StorageStrategy getStorageStrategy() {
      return storageStrategy;
    }
}
