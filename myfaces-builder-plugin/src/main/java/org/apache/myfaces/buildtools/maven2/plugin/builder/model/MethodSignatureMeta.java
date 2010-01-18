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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.myfaces.buildtools.maven2.plugin.builder.io.XmlWriter;

/**
 * MethodSignatureMeta is a Java representation of the faces-config component
 * property-extension method-signature XML element.
 */
public class MethodSignatureMeta extends Object
{
    /**
     * Creates a new MethodSignatureBean.
     */
    public MethodSignatureMeta()
    {
        _parameterTypes = new LinkedList();
    }
    
    public static void addXmlRules(Digester digester, String prefix)
    {
        String newPrefix = prefix + "/methodBindingSignature";
        digester.addObjectCreate(newPrefix , MethodSignatureMeta.class);
        digester.addBeanPropertySetter(newPrefix +"/returnType", "returnType");
        digester.addCallMethod(newPrefix +"/parameterType","addParameterType", 1);
        digester.addCallParam(newPrefix +"/parameterType", 0);
        digester.addSetNext(newPrefix ,"setMethodBindingSignature", MethodSignatureMeta.class.getName());
        
    }
    
    public static void writeXml(XmlWriter out, MethodSignatureMeta pm)
    {
        out.beginElement("methodBindingSignature");
        out.writeElement("returnType", pm._returnType);
        
        for (Iterator i = pm._parameterTypes.iterator(); i.hasNext();)
        {
            String param = (String) i.next();
            out.writeElement("parameterType", param);
        }
        
        out.endElement("methodBindingSignature");
    }

    /**
     * Adds a new parameter type to this method signature.
     *
     * @param parameterType  the parameter type
     */
    public void addParameterType(String parameterType)
    {
        _parameterTypes.add(parameterType);
    }

    /**
     * Returns the list of parameter types as an array.
     *
     * @return  the parameter type list
     */
    public String[] getParameterTypes()
    {
        return (String[]) _parameterTypes.toArray(new String[0]);
    }
    
    public String getParameterTypesAsString()
    {
        String[] params = this.getParameterTypes();
        StringBuffer resp = new StringBuffer(); 
        for (int i = 0; i < params.length; i++)
        {
          if (i > 0)
          {
            resp.append(", ");
          }
          resp.append(params[i]);
        }
        return resp.toString();
    }

    /**
     * Sets the return type of this method signature.
     *
     * @param returnType  the method signature return type
     */
    public void setReturnType(String returnType)
    {
        _returnType = returnType;
    }

    /**
     * Returns the return type of this method signature.
     *
     * @return  the method signature return type
     */
    public String getReturnType()
    {
        return _returnType;
    }

    private String _returnType;
    private List _parameterTypes;
}
