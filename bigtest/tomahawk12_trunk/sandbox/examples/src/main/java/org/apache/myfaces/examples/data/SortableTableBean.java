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
package org.apache.myfaces.examples.data;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Thomas Spiegl
 */
public class SortableTableBean {

    public static class Car {
        private long _id;
        private String _manufacturer;
        private String _model;

        public Car(long id, String manufacturer, String model) {
            _id = id;
            _manufacturer = manufacturer;
            _model = model;
        }

        public long getId() {
            return _id;
        }

        public void setId(long id) {
            _id = id;
        }

        public String getManufacturer() {
            return _manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            _manufacturer = manufacturer;
        }

        public String getModel() {
            return _model;
        }

        public void setModel(String model) {
            _model = model;
        }
    }

    public List getCars() {
        List cars = new ArrayList();
        long i = 1;
        cars.add(new Car(i++, "Ford","Mondeo"));
        cars.add(new Car(i++, "Skoda","Octavia"));
        cars.add(new Car(i++, "Opel","Vectra"));
        cars.add(new Car(i++, "Hyundai","Elantra"));
        cars.add(new Car(i++, "Peugeot","406"));
        cars.add(new Car(i++, "Peugeot","307"));
        cars.add(new Car(i++, "Alfa","Romeo"));
        cars.add(new Car(i++, "Honda","Stream"));
        cars.add(new Car(i++, "Rover","25"));
        cars.add(new Car(i++, "Renault","Laguna"));
        cars.add(new Car(i++, "Mercedes","C-Klasse"));
        cars.add(new Car(i++, "Audi","A4"));
        cars.add(new Car(i++, "Rover","75"));
        cars.add(new Car(i++, "VW","Passat"));
        cars.add(new Car(i++, "Mitsubishi","Carisma"));
        cars.add(new Car(i++, "Opel","Zafira"));
        cars.add(new Car(i++, "Fiat","Multipla"));
        cars.add(new Car(i++, "Honda","Civic"));
        cars.add(new Car(i++, "Nissan","Tino"));
        cars.add(new Car(i++, "Renault","Scenic"));
        cars.add(new Car(i++, "Citroen","Picasso"));
        cars.add(new Car(i++, "Mazda","Premacy"));
        cars.add(new Car(i++, "Mitsubishi","SpaceStar"));
        cars.add(new Car(i++, "Nissan","Almera"));
        cars.add(new Car(i++, "Opel","Corsa"));
        cars.add(new Car(i++, "Toyota","Yaris"));
        cars.add(new Car(i++, "Renault","Clio"));
        cars.add(new Car(i++, "Audi","A2"));
        cars.add(new Car(i++, "Skoda","Fabia"));
        cars.add(new Car(i++, "VW","Polo"));
        cars.add(new Car(i++, "Peugeot","206"));
        cars.add(new Car(i++, "Daihatsu","Sirion"));
        cars.add(new Car(i++, "Daewoo","Matiz"));
        cars.add(new Car(i++, "Ford","Fiesta"));
        return cars;
    }
}