#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
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
package ${package};

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Inject;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.WindowScoped;

/**
 * A typical simple backing bean, that is backed to <code>helloWorld.xhtml</code>
 */
@Named("helloWorld")
@WindowScoped
public class HelloWorldController implements Serializable
{

    //properties
    private String name;
    @Inject private GreetingService greetingService;

    /**
     * default empty constructor
     */
    public HelloWorldController()
    {
    }

    /**
     * Method that is backed to a submit button of a form.
     */
    public String send()
    {
        //do real logic, return a string which will be used for the navigation system of JSF
        return "page2.xhtml";
    }

    public String getGreeting()
    {
        return greetingService.createGreeting(name);
    }

    //-------------------getter & setter

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
