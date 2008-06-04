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
package org.apache.myfaces.examples.imageloop;

import org.apache.myfaces.custom.imageloop.GraphicItem;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Example image loop bean.
 * @author Felix R�thenbacher (latest modification by $Author:$)
 * @version $Revision:$ $Date:$
 */
public class ImageLoopBean {
    private Collection _imageCollection;
    private GraphicItem[] _imageArray;
    
    public ImageLoopBean() {
        GraphicItem item2 = new GraphicItem("images/imageloop2.png");
        GraphicItem item3 = new GraphicItem("images/imageloop3.png");

        _imageCollection = new ArrayList();
        _imageCollection.add(item2);
        
        _imageArray = new GraphicItem[1];
        _imageArray[0] = item3;
    }
    
    public Collection getImageCollection() {
        return _imageCollection;
    }
    
    public GraphicItem[] getImageArray() {
        return _imageArray;
    }
}
