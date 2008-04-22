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
 * --
 * $Id$
 */
package org.apache.myfaces.examples.limitrendered;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Andrew Robinson (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class LimitRenderedBean implements Serializable
{
    private boolean aRendered;
    private boolean cRendered;
    private boolean dRendered;
    private boolean bRendered;
    
    private int[] indexes = { 0, -1 };
    private ArrayList indexCollection = new ArrayList(2); 
    
    /**
     * @return the indexes
     */
    public int[] getIndexes()
    {
        return this.indexes;
    }
    
    /**
     * @return the indexCollection
     */
    public ArrayList getIndexCollection()
    {
        if (indexCollection.isEmpty())
        {
            indexCollection.add(new Integer(1));
            indexCollection.add(new Integer(-2));
        }
        return this.indexCollection;
    }

    /**
     * @return the aRendered
     */
    public boolean isARendered()
    {
        return this.aRendered;
    }

    /**
     * @param rendered the aRendered to set
     */
    public void setARendered(boolean rendered)
    {
        this.aRendered = rendered;
    }

    /**
     * @return the cRendered
     */
    public boolean isCRendered()
    {
        return this.cRendered;
    }

    /**
     * @param rendered the cRendered to set
     */
    public void setCRendered(boolean rendered)
    {
        this.cRendered = rendered;
    }

    /**
     * @return the dRendered
     */
    public boolean isDRendered()
    {
        return this.dRendered;
    }

    /**
     * @param rendered the dRendered to set
     */
    public void setDRendered(boolean rendered)
    {
        this.dRendered = rendered;
    }

    /**
     * @return the bRendered
     */
    public boolean isBRendered()
    {
        return this.bRendered;
    }

    /**
     * @param rendered the bRendered to set
     */
    public void setBRendered(boolean rendered)
    {
        this.bRendered = rendered;
    }
}
