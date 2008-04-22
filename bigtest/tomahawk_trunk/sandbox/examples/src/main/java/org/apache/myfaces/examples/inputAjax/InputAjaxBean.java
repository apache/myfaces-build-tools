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
package org.apache.myfaces.examples.inputAjax;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.EditableValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 * User: treeder
 * Date: Oct 20, 2005
 * Time: 1:30:51 PM
 */
public class InputAjaxBean
{
    private List checkboxItems;
    private List chosenValues = new ArrayList();
    private String text1;
    private String text2;
    private Date date1 = new Date();

    private String radioValue;
    private List radioItems;

    private boolean toggle1 = false;
    private boolean toggle2 = false;

    private String formText1;
    private String formText2;
    private String waitingText1 = "I'm waaaaaiiiiiting...";
    private String waitingText2 = new String(waitingText1);
    private int counter1 = 0;
    private int counter2 = 0;

    /**
     * Simple validator to show error handling messages in examples, returns an error if value string is
     * less then 3 characters
     *
     * @param context
     * @param toValidate
     * @param value
     */
    public void validateText2(FacesContext context, UIComponent toValidate, Object value)
    {
        if (value != null)
        {
            // lets say it must be more than 2 characters
            String valStr = (String) value;
            if (valStr.length() < 3)
            {
                ((EditableValueHolder) toValidate).setValid(false);
                context.addMessage(toValidate.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Text must be longer than 3 characters", null));
            }
        }
    }

    public void validateText1(FacesContext context, UIComponent toValidate, Object value)
    {
        if (value != null)
        {
            // lets say it must be less than 5 characters
            String valStr = (String) value;
            if (valStr.length() > 5)
            {
                ((EditableValueHolder) toValidate).setValid(false);
                context.addMessage(toValidate.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Length must be smaller than 5 characters", null));
            }
        }
    }

    public void validateFormText1(FacesContext context, UIComponent toValidate, Object value)
    {
        if (value != null)
        {
            // lets say it must be at least 5 characters
            String valStr = (String) value;
            if (valStr.length() < 5)
            {
                ((EditableValueHolder) toValidate).setValid(false);
                context.addMessage(toValidate.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Length must be longer than 5 characters", null));
            }
        }
    }
     public void validateFormText2(FacesContext context, UIComponent toValidate, Object value)
    {
        if (value != null)
        {
            // lets say it cannot contain a space
            String valStr = (String) value;
            if (valStr.indexOf(" ") != -1)
            {
                ((EditableValueHolder) toValidate).setValid(false);
                context.addMessage(toValidate.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot contain a space", null));
            }
        }
    }

    public String submit()
    {
        return null;
    }

    /**
     * This one is what should get called through the ajax form
     *
     * @return view
     */
    public String ajaxFormSubmit()
    {
        System.out.println("AJAX FORM SUBMIT CALLED!");
        return null;
    }


    public List getCheckboxItems()
    {
        if (checkboxItems == null)
        {
            checkboxItems = new ArrayList();
            for (int i = 0; i < 10; i++)
                checkboxItems.add(new SelectItem("" + i));
        }
        return checkboxItems;
    }

    public void setCheckboxItems(List checkboxItems)
    {
        this.checkboxItems = checkboxItems;
    }

    public List getRadioItems()
    {
        if (radioItems == null)
        {
            radioItems = new ArrayList();
            char a = 'a';
            for (int i = 0; i < 5; i++)
            {
                radioItems.add(new SelectItem(new String(new char[]{a})));
                a++;
            }
        }
        return radioItems;
    }

    public void setRadioItems(List radioItems)
    {
        this.radioItems = radioItems;
    }


    public List getChosenValues()
    {
        return chosenValues;
    }

    public void setChosenValues(List chosenValues)
    {
        this.chosenValues = chosenValues;
    }

    public String getText1()
    {
        return text1;
    }

    public void setText1(String text1)
    {
        this.text1 = text1;
    }

    public Date getDate1()
    {
        return date1;
    }

    public void setDate1(Date date1)
    {
        this.date1 = date1;
    }


    public String getText2()
    {
        return text2;
    }

    public void setText2(String text2)
    {
        this.text2 = text2;
    }

    public String getRadioValue()
    {
        return radioValue;
    }

    public void setRadioValue(String radioValue)
    {
        this.radioValue = radioValue;
    }

    public boolean isToggle1()
    {
        return toggle1;
    }

    public void setToggle1(boolean toggle1)
    {
        this.toggle1 = toggle1;
    }

    public boolean isToggle2()
    {
        return toggle2;
    }

    public void setToggle2(boolean toggle2)
    {
        this.toggle2 = toggle2;
    }

    public String getFormText2()
    {
        return formText2;
    }

    public void setFormText2(String formText2)
    {
        System.out.println("setting formtext2");
        this.formText2 = formText2;
    }

    public String getFormText1()
    {
        return formText1;
    }

    public void setFormText1(String formText1)
    {
        System.out.println("setting formtext1");
        this.formText1 = formText1;
    }

    public String getWaitingText1()
    {
        if(counter1 > 0){
            waitingText1 = "I was listening " + counter1 + " times!";
        }
        counter1++;
        return waitingText1;
    }

    public void setWaitingText1(String waitingText)
    {
        this.waitingText1 = waitingText;
    }
    public String getWaitingText2()
    {
        if(counter2 > 0){
            waitingText2 = "I was listening " + counter2 + " times!";
        }
        counter2++;
        return waitingText2;
    }

    public void setWaitingText2(String waitingText)
    {
        this.waitingText2 = waitingText;
    }


}
