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

package org.apache.myfaces.examples.scope;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

/**
 * A back catalog for industrial magic items
 *
 * @author Werner Punz werpu@gmx.at
 * @version $Revision: $ $Date: $
 */
public class Catalog implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = -134237444934543497L;
    List items = new LinkedList();

    public Catalog()
    {
        super();
        items.add(new SelectItem("", "None"));
        items.add(new SelectItem("Foozler", "Foozler"));
        items.add(new SelectItem("Acme Coyote Bomb", "Acme Coyote Bomb"));
        items.add(new SelectItem("Frobozz Magick Bloink",
                "Frobozz Magick Bloink"));
        items.add(new SelectItem("Stink Bonkaz", "Stink Bonkaz"));
        items.add(new SelectItem("Frobozz Turbo Wand", "Frobozz Turbo Wand"));
        items.add(new SelectItem("Fungaz Rebod Wand", "Fungaz Rebod Wand"));
        items.add(new SelectItem("Industrial Bruara Charm",
                "Industrial Bruara Charm"));
    }

    public List getItems()
    {
        return items;
    }

    public void setItems(List items)
    {
        //this.items = items;
    }
}
