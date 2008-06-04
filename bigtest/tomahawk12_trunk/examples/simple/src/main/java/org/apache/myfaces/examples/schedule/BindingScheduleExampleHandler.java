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

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.schedule.HtmlSchedule;
import org.apache.myfaces.custom.schedule.ScheduleMouseEvent;

/**
 * Handler class for demonstrating the schedule mouse events.
 * 
 * @author Jurgen Lust (latest modification by $Author$)
 * @version $Revision$
 */
public class BindingScheduleExampleHandler extends ScheduleExampleHandler
        implements Serializable
{
    private static final long serialVersionUID = 763734566918182549L;
    private static final Log log = LogFactory
            .getLog(BindingScheduleExampleHandler.class);
    private HtmlSchedule schedule;
    private String mouseActionText;

    public String getMouseActionText()
    {
        return mouseActionText;
    }

    public HtmlSchedule getSchedule()
    {
        return schedule;
    }

    public void setSchedule(HtmlSchedule schedule)
    {
        this.schedule = schedule;
    }

    public String getLastClickedDate()
    {
        if (getSchedule() == null
                || getSchedule().getLastClickedDateAndTime() == null)
            return "no date/time clicked";
        return getSchedule().getLastClickedDateAndTime().toString();
    }

    public String scheduleAction()
    {
        log.info("The schedule was clicked");
        log.info("selected entry: " + schedule.getModel().getSelectedEntry());
        return "success";
    }

    public void scheduleClicked(ScheduleMouseEvent event)
    {
        log.info("scheduleClicked!!!" + event.getEventType());
        StringBuffer buffer = new StringBuffer();
        switch (event.getEventType())
        {
        case ScheduleMouseEvent.SCHEDULE_BODY_CLICKED:
            buffer.append("schedule body was clicked: ");
            buffer.append(event.getClickedTime());
            break;
        case ScheduleMouseEvent.SCHEDULE_HEADER_CLICKED:
            buffer.append("schedule header was clicked: ");
            buffer.append(event.getClickedDate());
            break;
        case ScheduleMouseEvent.SCHEDULE_ENTRY_CLICKED:
            buffer.append("schedule entry was clicked.");
            
            break;
        default:
            buffer.append("no schedule mouse events registered");
        }
        mouseActionText = buffer.toString();
    }
}
