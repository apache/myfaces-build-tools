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
package org.apache.myfaces.buildtools.maven2.plugin.builder.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogChute;

public class ConsoleLogSystem implements LogChute
{

    final Logger log = Logger.getLogger(ConsoleLogSystem.class.getName());
    
    public Logger getLogger()
    {
        return log;
    }
    
    public void init(RuntimeServices runtimeservices) throws Exception
    {
        return;
    }

    public boolean isLevelEnabled(int i)
    {
        return true;
    }

    public void log(int level, String message)
    {
        switch ( level )
        {
            case LogChute.WARN_ID:
                getLogger().log(Level.WARNING, message );
                break;
            case LogChute.INFO_ID:
                // velocity info messages are too verbose, just consider them as debug messages...
                getLogger().log(Level.INFO, message );
                break;
            case LogChute.DEBUG_ID:
                getLogger().log(Level.FINE, message );
                break;
            case LogChute.ERROR_ID:
                getLogger().log(Level.SEVERE, message );
                break;
            default:
                getLogger().log(Level.ALL, message );
                break;
        }        
    }

    public void log(int level, String message, Throwable throwable)
    {
        log(level,message);
        throw new RuntimeException(throwable);
    }

}
