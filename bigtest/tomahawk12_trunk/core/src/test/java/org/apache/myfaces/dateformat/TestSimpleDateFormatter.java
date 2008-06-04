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
package org.apache.myfaces.dateformat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import junit.framework.TestCase;

import org.joda.time.DateTime;

public class TestSimpleDateFormatter extends TestCase
{
    // test just the very basics of date formatting
    public void testFormatSimple()
    {
        SimpleDateFormatter sdf = new SimpleDateFormatter("yyyy-MM-dd'T'hh:mm:ss", null);
        Date d = new Date();
        d.setYear(1987 - 1900);
        d.setMonth(2);
        d.setDate(12);
        d.setHours(13);
        d.setMinutes(23);
        d.setSeconds(59);
        
        String s = sdf.format(d);
        assertEquals("1987-03-12T01:23:59", s);
    }

    // test just the very basics of date parsing
    public void testParseSimple()
    {
        SimpleDateFormatter sdf = new SimpleDateFormatter("yyyy-MM-dd'T'HH:mm:ss", null);
        Date d = sdf.parse("1987-03-12T04:23:59");

        assertNotNull(d);
        assertEquals(1987, d.getYear() + 1900);
        assertEquals(2, d.getMonth());
        assertEquals(12, d.getDate());
        assertEquals(4, d.getHours());
        assertEquals(23, d.getMinutes());
        assertEquals(59, d.getSeconds());
    }

    /**
     * Check the parsing of dates with months that use names.
     */
    public void testParseNamedMonth()
    {
    	Locale locale = Locale.ENGLISH;
        org.apache.myfaces.dateformat.DateFormatSymbols symbols = 
        	new org.apache.myfaces.dateformat.DateFormatSymbols(locale);
        int firstDayOfWeek = 0;
        SimpleDateFormatter sdf = new SimpleDateFormatter(
        		"dd-MMM-yyyy", symbols, firstDayOfWeek);
        
        Date d = sdf.parse("12-Mar-2008");
        assertNotNull(d);
        assertEquals(2008, d.getYear() + 1900);
        assertEquals(2, d.getMonth());
        assertEquals(12, d.getDate());
        assertEquals(0, d.getHours());
        assertEquals(0, d.getMinutes());
        assertEquals(0, d.getSeconds());
    }

    // test every possible formatter in date formatting
    public void testFormatAll()
    {
        Date d = new Date();
        d.setYear(1987 - 1900);
        d.setMonth(2);
        d.setDate(12);
        d.setHours(4);
        d.setMinutes(23);
        d.setSeconds(59);
        
        String[] data =
        {
            "yyyy", "1987",
            "yyy", "87",
            "yy", "87",
            "y", "87",
            "MMMM", "March",
            "MMM", "Mar",
            "MM", "03",
            "M", "3",
            "dd", "12",
            "EEEE", "Thursday",
            "EE", "Thu",
            "HH", "04",
            "H", "4",
            "hh", "04",
            "h", "4",
            "mm", "23",
            "m", "23",
            "ss", "59",
            "s", "59",
            "a", "AM"
        };

        Locale locale = Locale.ENGLISH;
        for(int i=0; i<data.length; i+=2)
        {
            String pattern = data[i];
            String expected = data[i+1];

            SimpleDateFormatter sdf = new SimpleDateFormatter(pattern, null);
            String s = sdf.format(d);
            assertEquals("custom:" + pattern, expected, s);
            
            SimpleDateFormat sf = new SimpleDateFormat(pattern, locale);
            String s2 = sf.format(d);
            assertEquals("std:" + pattern, expected, s2);
        }
    }
    
    // test every possible formatter in date parsing
    public void testParseAll()
    {
        Date d = new Date();
        d.setYear(1987 - 1900);
        d.setMonth(2);
        d.setDate(12);
        d.setHours(4);
        d.setMinutes(23);
        d.setSeconds(59);
        
        String[] data =
        {
            "yyyy", "1987",
            "yyy", "87",
            "yy", "87",
            "y", "87",
            "MMMM", "March",
            "MMM", "Mar",
            "MM", "03",
            "M", "3",
            "dd", "12",

            // These are difficult to test in this way; disable them
            //"EEEE", "Monday",
            //"EE", "Mon",

            "HH", "04",
            "H", "4",

            "hh", "04",
            "h", "4",
            "mm", "23",
            "m", "23",
            "ss", "59",
            "s", "59",
            "a", "AM"
        };

        Locale locale = Locale.ENGLISH;
        for(int i=0; i<data.length; i+=2)
        {
            String pattern = data[i];
            String expected = data[i+1];

            // parse it with our code, then format it with the std code and
            // see if we get the same value back.
            SimpleDateFormatter sdf = new SimpleDateFormatter(pattern, null);
        
            Date d2 = sdf.parse(expected);
            SimpleDateFormat sf = new SimpleDateFormat(pattern, locale);
            String s2 = sf.format(d2);
            assertEquals(pattern, expected, s2);
        }
    }

    // try to parse various combinations, and see what we get
    public void testParseAssorted() throws Exception {
        Object[] data =
        {
            // test standard, with literals
            "yyyy-MM-dd", "1987-01-08", new Date(1987-1900, 0, 8),
            
            // test standard, with multichar literal sequences: any non-alpha
            // char must exactly match the input.
            "yyyy--MM-:()dd", "1987--01-:()08", new Date(1987-1900, 0, 8),
            
            // test standard, with quoted chars.
            "yyyy'T'MM'T'dd", "1987T01T08", new Date(1987-1900, 0, 8),
            
            // test standard, with non-pattern chars.
            // An alpha non-pattern char --> error
            "yyyyRMMRdd", "1987-01-08", null,
            
            // test quoted text
            "yyyy'year'MM'month'dd", "2003year04month06", new Date(2003-1900, 03, 06),
            
            // test mismatched quoted text
            "yyyy'year'MM'month'dd", "2003yexr04month06", null,
            
            // test short year format with no century wraparound
            "yy-MM-dd", "99-04-06", new Date(1999-1900, 03, 06),
            
            // test short year format with century wraparound
            "yy-MM-dd", "03-04-06", new Date(2003-1900, 03, 06),
            
            // test short year format with no century wraparound
            "yy-MM-dd", "33-04-06", new Date(1933-1900, 03, 06),
        };

        Locale locale = Locale.ENGLISH;
        for(int i=0; i<data.length; i+=3)
        {
            String pattern = (String) data[i];
            String input = (String) data[i+1];
            Date expected = (Date) data[i+2];

            // parse it with our code, and see if we get the expected result
            SimpleDateFormatter sdf = new SimpleDateFormatter(pattern, null);
            Date d = sdf.parse(input);
            assertEquals("custom:" + pattern, expected, d);
            
            // try with the standard parser too
            try
            {
                SimpleDateFormat sf = new SimpleDateFormat(pattern, locale);
                Date d2 = sf.parse(input);
                assertEquals("std:" + pattern, expected, d2);
            }
            catch(java.text.ParseException e)
            {
                // thrown when the input does not match the pattern
                assertEquals("std:" + pattern, null, expected);
            }
            catch(IllegalArgumentException e)
            {
                // thrown when the pattern is not value
                assertEquals("std:" + pattern, null, expected);
            }
        }
    }

    // try to format with various combinations, and see what we get
    public void testFormatAssorted() throws Exception
    {
        Date d = new Date();
        d.setYear(1987 - 1900);
        d.setMonth(2);
        d.setDate(12);
        d.setHours(4);
        d.setMinutes(23);
        d.setSeconds(59);
        
        String[] data =
        {
            // test standard, with literals
            "yyyy-MM-dd", "1987-03-12",
            
            // test standard, with multichar literal sequences: any non-alpha
            "yyyy--MM-:()dd", "1987--03-:()12",
            
            // test standard, with non-pattern chars.--> error
            "yyyyTMMTdd", null,
            
            // test standard, with non-pattern chars.
            "yyyy'T'MM'T'dd", "1987T03T12",
            
            // test quoted text
            "yyyy'year'MM'month'dd", "1987year03month12",
        };

        Locale locale = Locale.ENGLISH;
        for(int i=0; i<data.length; i+=2)
        {
            String pattern = data[i];
            String expected = data[i+1];

            // format it with our code, and check against expected.
            SimpleDateFormatter sdf = new SimpleDateFormatter(pattern, null);
            String s = sdf.format(d);
            assertEquals("custom:" + pattern, expected, s);

            // try with the standard parser too
            try
            {
                SimpleDateFormat sf = new SimpleDateFormat(pattern, locale);
                String s2 = sf.format(d);
                assertEquals("std:" + pattern, expected, s2);
            }
            catch(IllegalArgumentException e)
            {
                // thrown when the pattern is not value
                assertEquals("std:" + pattern, null, expected);
            }
        }
    }

    // test just the very basics of date parsing
    public void testWeekParseSimple()
    {
        SimpleDateFormatter sdf = new SimpleDateFormatter("xxxx-ww", null);
        Date d = sdf.parse("2009-06");

        assertNotNull(d);
        assertEquals(2009, d.getYear() + 1900);
        assertEquals(2, d.getMonth() + 1);
        assertEquals(2, d.getDate());
        assertEquals(0, d.getHours());
        assertEquals(0, d.getMinutes());
        assertEquals(0, d.getSeconds());
    }
    
    public void testWeekFormatAgainstJoda() throws Exception
    {
        // for every year from 2000-2010, test:
        //   1-8 jan jan
        //   29,30 may,
        //   1-8 june
        //   23-31 dec
         for(int year = 2000; year < 2020; ++year)
         {
        	 checkWeekFormatAgainstJoda(year, 0, 1);
        	 checkWeekFormatAgainstJoda(year, 0, 2);
        	 checkWeekFormatAgainstJoda(year, 0, 3);
        	 checkWeekFormatAgainstJoda(year, 0, 4);
        	 checkWeekFormatAgainstJoda(year, 0, 5);
        	 checkWeekFormatAgainstJoda(year, 0, 6);
        	 checkWeekFormatAgainstJoda(year, 0, 7);
        	 checkWeekFormatAgainstJoda(year, 0, 8);

        	 checkWeekFormatAgainstJoda(year, 4, 29);
        	 checkWeekFormatAgainstJoda(year, 4, 30);
        	 checkWeekFormatAgainstJoda(year, 5, 1);
        	 checkWeekFormatAgainstJoda(year, 5, 2);
        	 checkWeekFormatAgainstJoda(year, 5, 3);
        	 checkWeekFormatAgainstJoda(year, 5, 4);
        	 checkWeekFormatAgainstJoda(year, 5, 5);
        	 checkWeekFormatAgainstJoda(year, 5, 6);
        	 checkWeekFormatAgainstJoda(year, 5, 7);
        	 checkWeekFormatAgainstJoda(year, 5, 8);

        	 checkWeekFormatAgainstJoda(year, 11, 23);
        	 checkWeekFormatAgainstJoda(year, 11, 24);
        	 checkWeekFormatAgainstJoda(year, 11, 25);
        	 checkWeekFormatAgainstJoda(year, 11, 26);
        	 checkWeekFormatAgainstJoda(year, 11, 27);
        	 checkWeekFormatAgainstJoda(year, 11, 28);
        	 checkWeekFormatAgainstJoda(year, 11, 29);
        	 checkWeekFormatAgainstJoda(year, 11, 30);
        	 checkWeekFormatAgainstJoda(year, 11, 31);
         }
    }

    private void checkWeekFormatAgainstJoda(int year, int month, int day)
    {
        Date date = new Date(year-1900, month, day);
        DateTime jdt = new DateTime(date.getTime());
        int jodaWeekOfWeekyear = jdt.getWeekOfWeekyear();
        int jodaWeekyear = jdt.getWeekyear();

        WeekDate iwd = SimpleDateFormatter.getIsoWeekDate(date);

        // the java.util.Date convention is that 1 = monday
        int firstDayOfWeek = 1;
        WeekDate jwd = SimpleDateFormatter.getWeekDate(date, firstDayOfWeek);

        /*
        String ds = new SimpleDateFormat("yyyy-MM-dd").format(date);
        System.out.println(
            ds + ":"
            + "(" + jodaWeekyear + "-" + jodaWeekOfWeekyear + ")"
            + ",(" + iwd.year + "-" + iwd.week + ")"
            + ",(" + jwd.year + "-" + jwd.week + ")"
            );
            */
        assertEquals(jodaWeekyear, iwd.getYear());
        assertEquals(jodaWeekOfWeekyear, iwd.getWeek());
        assertEquals(jodaWeekyear, jwd.getYear());
        assertEquals(jodaWeekOfWeekyear, jwd.getWeek());
    }
}
