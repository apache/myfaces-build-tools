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

package org.apache.myfaces.examples.schedule;

import java.util.HashMap;
import java.util.Random;

import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.schedule.DefaultScheduleEntryRenderer;
import org.apache.myfaces.custom.schedule.HtmlSchedule;
import org.apache.myfaces.custom.schedule.model.ScheduleEntry;

/**
 * An example ScheduleEntryRenderer that assigns a random color to each
 * entry.
 * 
 * @author Jurgen Lust (latest modification by $Author$)
 * @version $Revision$
 */
public class RandomColorScheduleEntryRenderer extends
        DefaultScheduleEntryRenderer
{
    private static final long serialVersionUID = -4594648204963119057L;
    private HashMap colors = new HashMap();

    public String getColor(FacesContext context, HtmlSchedule schedule,
            ScheduleEntry entry, boolean selected)
    {
        if (colors.containsKey(entry.getId()))
            return (String) colors.get(entry.getId());
        StringBuffer color = new StringBuffer();
        Random random = new Random();
        color.append("rgb(");
        color.append(random.nextInt(255));
        color.append(",");
        color.append(random.nextInt(255));
        color.append(",");
        color.append(random.nextInt(255));
        color.append(")");
        String colorString = color.toString();
        colors.put(entry.getId(), colorString);
        return colorString;
    }

}
