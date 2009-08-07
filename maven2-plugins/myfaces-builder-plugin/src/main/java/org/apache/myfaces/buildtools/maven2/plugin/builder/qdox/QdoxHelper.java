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
package org.apache.myfaces.buildtools.maven2.plugin.builder.qdox;

/**
 * Helper class used by QdoxModelBuilder and provide utility methods. 
 * 
 * @since 1.0.4
 * @author Leonardo Uribe (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class QdoxHelper
{

    /**
     * Convert a method name to a property name.
     */
    public static String methodToPropName(String methodName)
    {
        StringBuffer name = new StringBuffer();
        if (methodName.startsWith("get") || methodName.startsWith("set"))
        {
            name.append(methodName.substring(3));
        }
        else if (methodName.startsWith("is"))
        {
            name.append(methodName.substring(2));
        }
        else
        {
            throw new IllegalArgumentException("Invalid annotated method name "
                    + methodName);
        }
    
        // Handle following styles of property name
        // getfooBar --> fooBar
        // getFooBar --> fooBar
        // getURL --> url
        // getURLLocation --> urlLocation
        for (int i = 0; i < name.length(); ++i)
        {
            char c = name.charAt(i);
            if (Character.isUpperCase(c))
            {
                name.setCharAt(i, Character.toLowerCase(c));
            }
            else
            {
                if (i > 1)
                {
                    // reset the previous char to uppercase
                    c = name.charAt(i - 1);
                    name.setCharAt(i - 1, Character.toUpperCase(c));
                }
                break;
            }
        }
        return name.toString();
    }

    /**
     * Given the full javadoc for a component, extract just the "first
     * sentence".
     * <p>
     * Initially, just find the first dot, and strip out any linefeeds. Later,
     * try to handle "e.g." and similar (see javadoc algorithm for sentence
     * detection).
     */
    public static String getFirstSentence(String doc)
    {
        if (doc == null)
        {
            return null;
        }
    
        int index = doc.indexOf('.');
        if (index == -1)
        {
            return doc;
        }
        // abc.
        return doc.substring(0, index);
    }

}
