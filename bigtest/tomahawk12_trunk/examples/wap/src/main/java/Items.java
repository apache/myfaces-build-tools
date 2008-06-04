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
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import java.util.*;
/**
 *
 * @author  Jijik
 */
public class Items {
    private List equipments = null;
    
    /** Creates a new instance of LanguageItems */
    public Items() {
        equipments = new ArrayList();

        SelectItemGroup first = new SelectItemGroup("braking");
        SelectItemGroup second = new SelectItemGroup("equipment");
        
        SelectItem[] array1 = {new SelectItem("abs"), new SelectItem("esp")}; 
        SelectItem[] array2 = {new SelectItem("radio"), new SelectItem("gps"), new SelectItem("mobil set")};
        
        first.setSelectItems(array1);
        second.setSelectItems(array2);
        
        equipments.add(first);
        equipments.add(second);
    }

    public List getEquipments() {
        return equipments;
    }

    public void setEquipments(List equipments) {
        this.equipments = equipments;
    }
    
}
