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
package org.apache.myfaces.examples.inputsuggest;

/**
 * This class is basically designed to simulate an application-specific bean that might exist in some
 * application.  The point here is to demonstrate how an application-specific bean such as this can
 * be integrated into inputSuggest.  The JSP page and the inputSuggest component know nothing about
 * StateInfo or {@link User}.  The {@link UserHandler} backing bean and the value binding expression in
 * the JSP help bridge this gap.
 *
 * @author Sean Schofield
 * @version $Revision: $ $Date: $
 */
public class StateInfo {
    private String key;
    private String text;

    public StateInfo(String key, String text) {
        this.key = key;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
