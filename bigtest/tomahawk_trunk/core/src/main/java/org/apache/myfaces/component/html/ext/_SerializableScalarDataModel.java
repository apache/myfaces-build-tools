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
package org.apache.myfaces.component.html.ext;

import java.util.Collections;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
class _SerializableScalarDataModel
        extends _SerializableDataModel
{
    private static final long serialVersionUID = 8344668178297539400L;
    //private static final Log log = LogFactory.getLog(_SerializableDataModel.class);

    public _SerializableScalarDataModel(int first, int rows, Object scalar)
    {
        _first = first;
        _rows = rows;
        _rowCount = 1;
        if (_rows <= 0)
        {
            _rows = _rowCount - first;
        }
        _list = Collections.singletonList(scalar);
    }
}
