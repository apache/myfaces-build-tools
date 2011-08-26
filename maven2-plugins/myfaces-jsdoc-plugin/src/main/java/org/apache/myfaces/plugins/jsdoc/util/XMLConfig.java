/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.myfaces.plugins.jsdoc.util;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import static javax.xml.stream.XMLStreamConstants.*;

/**
 * @author Werner Punz (latest modification by $Author$)
 * @version $Revision$ $Date$
 *          <p/>
 *          A helper which reads the xml config appopriately
 */

public class XMLConfig
{

    String _fileName = null;

    List _fileNames = null;
    Map _fileNameIdx = new HashMap();

    public XMLConfig(String fileName) throws XMLStreamException, FileNotFoundException
    {
        _fileName = fileName;
        _fileNames = new LinkedList();
        postCreate();
    }

    protected void postCreate() throws XMLStreamException, FileNotFoundException
    {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        // einen neuen eventReader einrichten
        InputStream in = new FileInputStream(_fileName);
        XMLEventReader parser = inputFactory.createXMLEventReader(in);
        StringBuilder elementText = null;

        int cnt = 0;
        while (parser.hasNext())
        {
            XMLEvent event = parser.nextEvent();
            switch (event.getEventType())
            {
                case XMLStreamConstants.END_DOCUMENT:
                    parser.close();
                    break;
                case XMLStreamConstants.START_ELEMENT:
                    if (!event.asStartElement().getName().toString().equals("include"))
                    {
                        break;
                    }
                    elementText = new StringBuilder(100);
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (elementText == null)
                    {
                        break;
                    }
                    elementText.append(event.asCharacters().getData());
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (!event.asEndElement().getName().toString().equals("include") || elementText == null)
                    {
                        break;
                    }

                    _fileNames.add(elementText.toString());
                    _fileNameIdx.put(elementText.toString(), new Integer(cnt));
                    cnt++;
                    elementText = null;
                    break;
                default:
                    break;
            }
        }
    }

    public String getFileName()
    {
        return _fileName;
    }

    public void setFileName(String fileName)
    {
        _fileName = fileName;
    }

    public List getFileNames()
    {
        return _fileNames;
    }

    public void setFileNames(List fileNames)
    {
        _fileNames = fileNames;
    }

    public Map getFileNameIdx()
    {
        return _fileNameIdx;
    }

    public void setFileNameIdx(Map fileNameIdx)
    {
        _fileNameIdx = fileNameIdx;
    }
}
