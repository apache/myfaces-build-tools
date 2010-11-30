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
package org.apache.myfaces.buildtools.maven2.plugin.builder.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;

/**
 * 
 * @since 1.0.4
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 796607 $ $Date: 2009-07-21 22:00:30 -0500 (mar, 21 jul 2009) $
 */
public class WebConfigMeta implements WebConfigParamHolder
{
    private String _xmlElementName;
    private String _modelId;
    
    protected Map _webConfigParameters = new LinkedHashMap();
    
    /**
     * Add digester rules to repopulate an instance of this type from an xml
     * file.
     */
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/webConfig";
        digester.addObjectCreate(newPrefix, WebConfigMeta.class);
        digester.addSetNext(newPrefix, "addWebConfig");        
        digester.addBeanPropertySetter(newPrefix + "/modelId");
        WebConfigParamMeta.addXmlRules(digester, newPrefix);
    }

    /**
     * Constructor.
     * 
     * Param xmlElementName is the name of the xml element that is created
     * when method writeXml is invoked.
     */
    public WebConfigMeta()
    {
        _xmlElementName = "webConfig";
    }

    /**
     * Write the properties of this instance out as xml.
     * <p>
     * The name of the xml element that is created to hold the properties
     * was specified when the constructor was called.
     * <p>
     * Subclasses that want to output their own properties should not
     * override this method. Instead, they should override writeXmlSimple
     * (and in rare cases writeXmlComplex).
     * <p>
     * Having two write methods (writeXmlSimple/writeXmlComplex) gives some basic
     * control over the order in which data is written to xml, in order to make
     * the generated xml look nice. Any properties written in writeXmlSimple will
     * appear in the output file before properties written by writeXmlComplex. 
     * Therefore, properties which are "easily read" should be written out in
     * a writeXmlSimple method. Data which has large CDATA blocks, or complicated
     * nested structure should be written out in a writeXmlComplex method so that
     * the "simple" stuff can be easily read and is not buried in the middle of
     * the harder-to-read output.
     */
    protected void writeXml(XmlWriter out)
    {
        out.beginElement(_xmlElementName);
        writeXmlSimple(out);
        writeXmlComplex(out);
        out.endElement(_xmlElementName);
    }

    /**
     * Write this model out as xml.
     * <p>
     * Subclasses that wish to write out properties as xml should override
     * this method, call the super implementation, then call methods on the
     * XmlWriter object to output their data.
     */
    protected void writeXmlSimple(XmlWriter out)
    {
        out.writeElement("modelId", _modelId);
    }

    /**
     * See documentation for writeXml and writeXmlSimple methods.
     */
    protected void writeXmlComplex(XmlWriter out)
    {
        for (Iterator i = _webConfigParameters.values().iterator(); i.hasNext();)
        {
            WebConfigParamMeta prop = (WebConfigParamMeta) i.next();
            WebConfigParamMeta.writeXml(out, prop);
        }
    }

    /**
     * Merge any inheritable data from the specified "other" instance into
     * the metadata held by this instance.
     */
    protected void merge(ClassMeta other)
    {
        // There is nothing to merge between two ClassMeta objects;
        // none of the properties on this class are inheritable.
    }

    /**
     * Indicates which "group" of metadata this class belongs to.
     * <p>
     * Projects can inherit metadata from other projects, in which case
     * all the ClassMeta objects end up in one big collection. But for
     * some purposes it is necessary to iterate over the objects belonging
     * to only one project (eg when generating components). This return
     * value can be tested to check which "group" (project) a particular
     * instance belongs to.
     */
    public String getModelId()
    {
        return _modelId;
    }

    public void setModelId(String modelId)
    {
        this._modelId = modelId;
    }

    public void addWebConfigParam(WebConfigParamMeta wcp)
    {
        _webConfigParameters.put(wcp.getName(), wcp);
    }
    
    public int webConfigParametersSize()
    {
        return _webConfigParameters.size();
    }

    public WebConfigParamMeta getWebConfigParam(String name)
    {
        return (WebConfigParamMeta)_webConfigParameters.get(name);
    }

    public Iterator webConfigParameters()
    {
        return _webConfigParameters.values().iterator();
    }
    
    //THIS METHODS ARE USED FOR VELOCITY TO GET DATA AND GENERATE CLASSES
    
    public Collection getWebConfigParametersList()
    {
        return _webConfigParameters.values();
    }
}
