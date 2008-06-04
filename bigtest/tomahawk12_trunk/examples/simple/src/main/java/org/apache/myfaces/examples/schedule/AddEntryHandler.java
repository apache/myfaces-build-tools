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
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.myfaces.custom.schedule.model.DefaultScheduleEntry;
import org.apache.myfaces.custom.schedule.model.ScheduleModel;

/**
 * Handler class for manually adding an entry to the example schedule model.
 * 
 * @author Jurgen Lust (latest modification by $Author$)
 * @version $Revision$
 */
public class AddEntryHandler implements Serializable
{
    private static final long serialVersionUID = -4253400845605088699L;

    private Date from;

    private Date until;

    private String title;

    private String location;

    private String comments;

    private ScheduleModel model;

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public Date getFrom()
    {
        return from;
    }

    public void setFrom(Date from)
    {
        this.from = from;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public ScheduleModel getModel()
    {
        return model;
    }

    public void setModel(ScheduleModel model)
    {
        this.model = model;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Date getUntil()
    {
        return until;
    }

    public void setUntil(Date until)
    {
        this.until = until;
    }

    public String add()
    {
        if (!from.before(until))
        {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "start time must be before end time", null));
            return "failure";
        }
        DefaultScheduleEntry entry = new DefaultScheduleEntry();
        entry.setId(RandomStringUtils.randomNumeric(32));
        entry.setStartTime(from);
        entry.setEndTime(until);
        entry.setTitle(title);
        entry.setSubtitle(location);
        entry.setDescription(comments);
        model.addEntry(entry);
        model.refresh();
        return "success";
    }

}
