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
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Statistic {
    private static Log log = LogFactory.getLog(Statistic.class);
        
    private int summary;
    private DayRecord records[];

    // inserts random statistic data
    public Statistic(){
        int rows = 3;
        records = new DayRecord[rows];
        
        Calendar cal = Calendar.getInstance();
        Random rnd = new Random();        
        
        for (int i = 0; i < rows; i++){
            cal.set(2004, 5, i);
            records[i] = new DayRecord(cal.getTime(), rnd.nextInt(5000));
        }
        
    }
    
    public DayRecord[] getRecords() {
        return records;
    }

    public void setRecords(DayRecord[] records) {
        this.records = records;
    }

    public int getSummary() {
        summary = 0;
        
        for (int i = 0; i < records.length; i++)
            summary += records[i].getVisitors();
        
        return(summary);
    }
    
}