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

package org.apache.myfaces.examples.misc;

import java.io.Serializable;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TabbedPaneBean implements Serializable
{
    //private static final Log log = LogFactory.getLog(TabbedPaneBean.class);

    /**
     * serial id for serialisation versioning
     */
    private static final long serialVersionUID = 1L;
    
    private boolean           _tab1Visible     = true;
    private boolean           _tab2Visible     = true;
    private boolean           _tab3Visible     = true;

    public boolean isTab1Visible()
    {
        return _tab1Visible;
    }

    public void setTab1Visible(boolean tab1Visible)
    {
        _tab1Visible = tab1Visible;
    }

    public boolean isTab2Visible()
    {
        return _tab2Visible;
    }

    public void setTab2Visible(boolean tab2Visible)
    {
        _tab2Visible = tab2Visible;
    }

    public boolean isTab3Visible()
    {
        return _tab3Visible;
    }

    public void setTab3Visible(boolean tab3Visible)
    {
        _tab3Visible = tab3Visible;
    }
}
