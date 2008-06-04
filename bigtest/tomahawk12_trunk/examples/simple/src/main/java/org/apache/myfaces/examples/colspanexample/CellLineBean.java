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

import java.util.ArrayList;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

public class CellLineBean {
    public DataModel getCells() {
        ArrayList a = new ArrayList();
        a.add(new Cell("1"));
        a.add(new Cell("2"));
        a.add(new Cell("3"));
        a.add(new Cell("4"));
        a.add(new Cell("5"));
        DataModel testModel = new ListDataModel();
        testModel.setWrappedData(a);
        return testModel;
    }
}
