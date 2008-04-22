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
package org.apache.myfaces.examples.colspanexample;

public class Line {
    private String col1;
    private String col2;
    private String col3;
    private String col4;
    private String col5;
    public Line(String col1, String col2, String col3, String col4, String col5) {
        super();
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
        this.col5 = col5;
    }
    public String getCol1() {
        return col1;
    }
    public void setCol1(String col1) {
        this.col1 = col1;
    }
    public String getCol2() {
        return col2;
    }
    public void setCol2(String col2) {
        this.col2 = col2;
    }
    public String getCol3() {
        return col3;
    }
    public void setCol3(String col3) {
        this.col3 = col3;
    }
    public String getCol4() {
        return col4;
    }
    public void setCol4(String col4) {
        this.col4 = col4;
    }
    public String getCol5() {
        return col5;
    }
    public void setCol5(String col5) {
        this.col5 = col5;
    }
}
