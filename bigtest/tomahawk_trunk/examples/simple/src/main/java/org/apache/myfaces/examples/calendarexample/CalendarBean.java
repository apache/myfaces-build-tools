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
package org.apache.myfaces.examples.calendarexample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Martin Marinschek
 * @version $Revision: $ $Date: $
 *          <p/>
 *          $Log: $
 */
public class CalendarBean implements Serializable
{
    /**
     * serial id for serialisation
     */
    private static final long serialVersionUID = 1L;

    private static Log log = LogFactory.getLog(CalendarBean.class);

    private List _dates;

    private String _text;
    private Date _firstDate;
    private Date _secondDate;

    public List getDates()
    {
        if(_dates == null)
        {
            _dates = new ArrayList();

            for(int i=0; i<3; i++)
                _dates.add(new DateHolder());
        }

        return _dates;
    }

    public void setDates(List dates)
    {
        _dates = dates;
    }

    public String getText()
    {
        return _text;
    }

    public void setText(String text)
    {
        _text = text;
    }

    public Date getFirstDate()
    {
        return _firstDate;
    }

    public void setFirstDate(Date firstDate)
    {
        _firstDate = firstDate;
    }

    public Date getSecondDate()
    {
        return _secondDate;
    }

    public void setSecondDate(Date secondDate)
    {
        _secondDate = secondDate;
    }

    public String submitMethod()
    {
        log.info("submit method called");

        return "submit";
    }


}
