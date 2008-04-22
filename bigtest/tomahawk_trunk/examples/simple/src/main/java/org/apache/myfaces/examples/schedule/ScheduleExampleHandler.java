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
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.myfaces.custom.schedule.model.DefaultScheduleEntry;
import org.apache.myfaces.custom.schedule.model.ScheduleModel;
import org.apache.myfaces.custom.schedule.model.SimpleScheduleModel;

/**
 * Handler class for the schedule example 
 * 
 * @author Jurgen Lust (latest modification by $Author$)
 * @version $Revision$
 */
public class ScheduleExampleHandler implements Serializable
{
    private static final long serialVersionUID = -8815771399735333108L;

    private ScheduleModel model;

    public ScheduleModel getModel()
    {
        return model;
    }

    public void setModel(ScheduleModel model)
    {
        this.model = model;
    }

    public void deleteSelectedEntry(ActionEvent event)
    {
        if (model == null)
            return;
        model.removeSelectedEntry();
    }

    public void addSampleHoliday(ActionEvent event)
    {
        if (model instanceof SimpleScheduleModel)
        {
            SimpleScheduleModel ssm = (SimpleScheduleModel) model;
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(ssm.getSelectedDate());
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            ssm.setHoliday(calendar.getTime(), "Poeperkesdag");
            ssm.refresh();
        }
    }

    public void addSampleEntries(ActionEvent event)
    {
        if (model == null)
            return;
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(model.getSelectedDate());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        DefaultScheduleEntry entry1 = new DefaultScheduleEntry();
        // every entry in a schedule must have a unique id
        entry1.setId(RandomStringUtils.randomNumeric(32));
        entry1.setStartTime(calendar.getTime());
        calendar.add(Calendar.MINUTE, 45);
        entry1.setEndTime(calendar.getTime());
        entry1.setTitle("Test MyFaces schedule component");
        entry1.setSubtitle("my office");
        entry1
                .setDescription("We need to get this thing out of the sandbox ASAP");
        model.addEntry(entry1);
        DefaultScheduleEntry entry2 = new DefaultScheduleEntry();
        entry2.setId(RandomStringUtils.randomNumeric(32));
        // entry2 overlaps entry1
        calendar.add(Calendar.MINUTE, -20);
        entry2.setStartTime(calendar.getTime());
        calendar.add(Calendar.HOUR, 2);
        entry2.setEndTime(calendar.getTime());
        entry2.setTitle("Show schedule component to boss");
        entry2.setSubtitle("my office");
        entry2.setDescription("Convince him to get time to thoroughly test it");
        model.addEntry(entry2);
        DefaultScheduleEntry entry3 = new DefaultScheduleEntry();
        entry3.setId(RandomStringUtils.randomNumeric(32));
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        entry3.setStartTime(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        entry3.setEndTime(calendar.getTime());
        entry3.setTitle("Thoroughly test schedule component");
        model.addEntry(entry3);
        DefaultScheduleEntry entry4 = new DefaultScheduleEntry();
        entry4.setId(RandomStringUtils.randomNumeric(32));
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        entry4.setStartTime(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        entry4.setEndTime(calendar.getTime());
        entry4.setTitle("Long lunch");
        model.addEntry(entry4);
        DefaultScheduleEntry entry5 = new DefaultScheduleEntry();
        entry5.setId(RandomStringUtils.randomNumeric(32));
        calendar.add(Calendar.MONTH, 2);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        entry5.setStartTime(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        entry5.setEndTime(calendar.getTime());
        entry5.setTitle("Fishing trip");
        model.addEntry(entry5);
        //Let's add a zero length entry...
        DefaultScheduleEntry entry6 = new DefaultScheduleEntry();
        calendar.setTime(model.getSelectedDate());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        entry6.setId(RandomStringUtils.randomNumeric(32));
        entry6.setStartTime(calendar.getTime());
        entry6.setEndTime(calendar.getTime());
        entry6.setTitle("Zero length entry");
        entry6.setDescription("Is only rendered when the 'renderZeroLengthEntries' attribute is 'true'");
        model.addEntry(entry6);
        //And also an allday event
        DefaultScheduleEntry entry7 = new DefaultScheduleEntry();
        entry7.setId(RandomStringUtils.randomNumeric(32));
        entry7.setTitle("All day event");
        entry7.setSubtitle("This event renders as an all-day event");
        entry7.setAllDay(true);
        model.addEntry(entry7);
        model.refresh();
    }
}
