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

package org.apache.myfaces.trinidadbuild.plugin.javascript.obfuscator.javascript15parser;

//~--- JDK imports ------------------------------------------------------------

import java.util.Stack;

//~--- classes ----------------------------------------------------------------

public class ProgramContextStack extends Stack {
    public ProgramContextStack() {}

    //~--- methods ------------------------------------------------------------

    public ProgramContext popContext() {
        return ((ProgramContext) pop());
    }

    public void pushContext(ProgramContext context) {
        push(context);
    }

    //~--- get methods --------------------------------------------------------

    public AnnotatedToken getToken(String name) {
        AnnotatedToken token = null;

        for (int i = size() - 1; i >= 0; i--) {
            ProgramContext context = (ProgramContext) get(i);

            if (null != (token = context.getToken(name))) {
                break;
            }
        }

        return token;
    }

    /**
     *
     * @param name
     * @param depth
     * @return
     */
    public AnnotatedToken getToken(String name, int depth) {
        int end;

        end = size() - depth;
        end = Math.max(end, 0);

        AnnotatedToken token = null;

        for (int i = size() - 1; i >= end; i--) {
            ProgramContext context = (ProgramContext) get(i);

            if (null != (token = context.getToken(name))) {
                break;
            }
        }

        return token;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com