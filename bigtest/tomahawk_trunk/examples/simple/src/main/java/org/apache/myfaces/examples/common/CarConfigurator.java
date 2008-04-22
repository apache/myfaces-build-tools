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
package org.apache.myfaces.examples.common;

import org.apache.myfaces.examples.util.LocalizedSelectItem;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CarConfigurator
        implements Serializable
{

    /**
     * serial id for serialisation
     */
    private static final long serialVersionUID = 1L;
    private static List _cars;
    private static List _colors;
    private static List _extrasList;
    private static HashMap _priceList = new HashMap();
    private static HashMap _priceFactorColors = new HashMap();
    private static HashMap _priceListExtras = new HashMap();
    static
    {
        _cars = new ArrayList();
        _colors = new ArrayList();
        _extrasList = new ArrayList();

        _cars.add(new SelectItem("c1", "Audee X6", null));
        _cars.add(new SelectItem("c2", "PMW 321u", null));
        _cars.add(new SelectItem("c3", "Masta ZX7", null));
        _cars.add(new SelectItem("c4", "Renolt ESP", null));
        _cars.add(new SelectItem("c5", "WV Lumpo", null));
        _cars.add(new SelectItem("c6", "James Blond Car", null));
        _cars.add(new SelectItem("c7", "Neko Bus", null));

        _colors.add(new LocalizedSelectItem(new Color("color_black"),"color_black"));
        _colors.add(new LocalizedSelectItem(new Color("color_blue"),"color_blue"));
        _colors.add(new LocalizedSelectItem(new Color("color_marine"),"color_marine"));
        _colors.add(new LocalizedSelectItem(new Color("color_red"),"color_red"));

        _extrasList.add(new LocalizedSelectItem("extra_aircond"));
        _extrasList.add(new LocalizedSelectItem("extra_sideab"));
        _extrasList.add(new LocalizedSelectItem("extra_mirrowheat"));
        _extrasList.add(new LocalizedSelectItem("extra_leaderseat"));

        _priceList.put("c1", new BigDecimal(30000));
        _priceList.put("c2", new BigDecimal(32000));
        _priceList.put("c3", new BigDecimal(20000));
        _priceList.put("c4", new BigDecimal(25000));
        _priceList.put("c5", new BigDecimal(10000));
        _priceList.put("c6", new BigDecimal(100000000));
        _priceList.put("c7", new BigDecimal(1000000000));

        _priceFactorColors.put("color_black", new BigDecimal(1.15));
        _priceFactorColors.put("color_blue", new BigDecimal(1.10));
        _priceFactorColors.put("color_marine", new BigDecimal(1.05));
        _priceFactorColors.put("color_red", new BigDecimal(1.0));

        _priceListExtras.put("extra_aircond", new BigDecimal(510));
        _priceListExtras.put("extra_sideab", new BigDecimal(1220));
        _priceListExtras.put("extra_mirrowheat", new BigDecimal(1230));
        _priceListExtras.put("extra_leaderseat", new BigDecimal(840));
    }


    private BigDecimal _price = new BigDecimal(0);
    private String[] _extras;
    private String _discount = "0";
    private String _discount2 = "0";
    private String _bandName;
    private String _car;
    private Color _color = new Color("color_blue");
    private List _interiorColors = null;
    private boolean _salesTax = false;
    private Long _doors = new Long(4);

    public CarConfigurator()
    {
    }


    public Long getDoors()
    {
        return _doors;
    }


    public void setDoors(Long doors)
    {
        this._doors = doors;
    }


    public List getCars()
    {
        return _cars;
    }

    public List getColors()
    {
        return _colors;
    }

    public List getExtrasList()
    {
        return _extrasList;
    }

    public String getCar()
    {
        return _car;
    }

    public void setCar(String car)
    {
        _car = car;
    }

    public Color getColor()
    {
        return _color;
    }

    public void setColor(Color color)
    {
        _color = color;
    }

    public List getInteriorColors()
    {
        return _interiorColors;
    }

    public void setInteriorColors(List interiorColors)
    {
        _interiorColors = interiorColors;
    }

    public BigDecimal getPrice()
    {
        return _price;
    }

    public void setPrice(BigDecimal price)
    {
        _price = price;
    }

    public String[] getExtras()
    {
        return _extras;
    }

    public void setExtras(String[] extras)
    {
        _extras = extras;
    }

    public String getDiscount()
    {
        return _discount;
    }

    public void setDiscount(String discount)
    {
        _discount = discount;
    }

    public String getDiscount2()
    {
        return _discount2;
    }

    public void setDiscount2(String discount2)
    {
        _discount2 = discount2;
    }

    public String getBandName()
    {
        return _bandName;
    }

    public void setBandName(String bandName)
    {
        _bandName = bandName;
    }

    public boolean isSalesTax()
    {
        return _salesTax;
    }

    public void setSalesTax(boolean salesTax)
    {
        _salesTax = salesTax;
    }

    public String calcPrice()
    {
        String car = getCar();
        Color color = getColor();
        if (car == null ||
            color == null)
        {
            _price = new BigDecimal(0);
            return "ok";
        }

        BigDecimal carprice = (BigDecimal)_priceList.get(car);
        BigDecimal colorfactor = (BigDecimal)_priceFactorColors.get(color.getColor());
        if (carprice == null ||
            colorfactor == null)
        {
            _price = new BigDecimal(0);
            return "ok";
        }
        _price = carprice.multiply(colorfactor);

        String[] extras = getExtras();
        if (extras != null)
        {
            for (int i = 0; i < extras.length; i++)
            {
                String extra = extras[i];
                _price = _price.add((BigDecimal)_priceListExtras.get(extra));
            }
        }

        if (_discount != null)
        {
            try
            {
                int i = Integer.parseInt(_discount);
                switch (i)
                {
                    case 0: break;
                    case 1: _price = _price.multiply(new BigDecimal(0.95)); break;
                    case 2: _price = _price.multiply(new BigDecimal(0.91)); break;
                }
            }
            catch (NumberFormatException e)
            {
            }
        }

        if (_discount2 != null)
        {
            try
            {
                int i = Integer.parseInt(_discount2);
                switch (i)
                {
                    case 1: _price = _price.multiply(new BigDecimal(0.85)); break;
                    case 2: _price = _price.multiply(new BigDecimal(0.80)); break;
                    case 3: _price = _price.multiply(new BigDecimal(0.6)); break;
                }
            }
            catch (NumberFormatException e)
            {
            }
        }

        if (_salesTax)
        {
            _price = _price.multiply(new BigDecimal(1.2));
        }

        return "ok";
    }


    public void validateCar(FacesContext context, UIComponent component, Object value) throws ValidatorException
    {
        if (value instanceof String && value.equals("c6"))
        {
            throw new ValidatorException(new FacesMessage("Are you kidding?", "You cannot buy a James Blond car!"));
        }
    }

    public Converter getColorConverter()
    {
        return new ColorConverter();
    }

    public static class ColorConverter implements Converter
    {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String string) throws ConverterException
        {
            if(string==null)
                return null;

            return new Color(string);
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) throws ConverterException
        {
            if(object instanceof Color)
            {
                return ((Color) object).getColor();
            }

            return null;
        }
    }

    public static class Color implements Serializable
    {
        private String color;

        public Color(String color)
        {
            this.color = color;
        }

        public String getColor()
        {
            return color;
        }

        public void setColor(String color)
        {
            this.color = color;
        }

        public boolean equals(Object cmp)
        {
            if(!(cmp instanceof Color))
                return false;

            String cmpColor = ((Color) cmp).getColor();

            if(this.color == null && cmpColor!=null)
                return false;

            return this.color.equals(cmpColor);
        }
    }

}
