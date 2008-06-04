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

package org.apache.myfaces.examples.ppr;

import org.apache.myfaces.custom.ppr.PPRPhaseListener;
import org.apache.myfaces.examples.inputSuggestAjax.Address;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ernst Fastl
 */
public class PPRExampleBean
{
    private String _textField;

    private String _message;

    private Boolean _checkBoxValue =Boolean.FALSE;

    private String _dropDownValue;

    private String _inputTextValue;

    private List _names;

    private List _simpleCarList;

    private UIData _carTable;


    public UIData getCarTable() {
        return _carTable;
    }

    public void setCarTable(UIData carTable) {
        _carTable = carTable;
    }

    public List getTypeList() {
        List li = new ArrayList();

        List simpleCarList = getSimpleCarList();

        for (int i = 0; i < simpleCarList.size(); i++) {
            SimpleCar simpleCar = (SimpleCar) simpleCarList.get(i);
            li.add(new SelectItem(simpleCar.getType(),simpleCar.getType(),null));

        }

        return li;
    }

    public List getSimpleCarList() {

        if(_simpleCarList == null) {
            _simpleCarList = new ArrayList();
            _simpleCarList.add(new SimpleCar(1,"Mazda","blue"));
            _simpleCarList.add(new SimpleCar(2,"Renault","green"));
            _simpleCarList.add(new SimpleCar(3,"Nissan","red"));
            _simpleCarList.add(new SimpleCar(4,"BMW","yellow"));
            _simpleCarList.add(new SimpleCar(5,"Mercedes","cyan"));
            _simpleCarList.add(new SimpleCar(6,"Daimler-Chrysler","pink"));
            _simpleCarList.add(new SimpleCar(7,"Volvo","white"));
        }

        return _simpleCarList;
    }

    public Integer getSumIds() {
        List li = getSimpleCarList();

        int sum = 0;

        for (int i = 0; i < li.size(); i++) {
            SimpleCar simpleCar = (SimpleCar) li.get(i);
            sum+=simpleCar.getId();
        }

        return new Integer(sum);
    }

    public void setSimpleCarList(List simpleCarList) {
        _simpleCarList = simpleCarList;
    }

    public void idChanged(ValueChangeEvent evt) {
        Integer id = (Integer) evt.getNewValue();
        SimpleCar car = (SimpleCar) _carTable.getRowData();
        car.setId(id==null?0:id.intValue());


        UIData parentUIData = searchParentUIData(evt.getComponent());

        UIComponent comp = parentUIData.findComponent("sum");

        FacesContext fc = FacesContext.getCurrentInstance();

        PPRPhaseListener.addTriggeredComponent(fc,comp.getClientId(fc));
    }

    private UIData searchParentUIData(UIComponent comp) {
        if(comp == null)
            return null;
        else if(comp.getParent() instanceof UIData)
            return (UIData) comp.getParent();

        return searchParentUIData(comp.getParent());
    }

    public void typeChanged(ValueChangeEvent evt) {
        String type = (String) evt.getNewValue();
        SimpleCar car = (SimpleCar) _carTable.getRowData();
        car.setType(type);
    }

    public void colorChanged(ValueChangeEvent evt) {
        String color = (String) evt.getNewValue();
        SimpleCar car = (SimpleCar) _carTable.getRowData();
        car.setColor(color);
    }

    public List getNames()
    {
        if(_names == null)
            _names = getListMasterData();
        return _names;
    }

    public void setNames(List names)
    {
        _names = names;
    }

    public String getTextField()
    {
        return _textField;
    }

    public void setTextField(String textField)
    {
        this._textField = textField;
    }

	public String testExceptionAction() {
		throw new FacesException("Test PPR Exception Handling");
	}    

    public String searchNames() {
        _names = getListMasterData();

        if(_textField == null || _textField.equals(""))
            return null;

        for (Iterator iterator = _names.iterator(); iterator.hasNext();)
        {
            Object o = iterator.next();
            String currentName = (String) o;
            if(-1 == currentName.toLowerCase().indexOf(_textField.toLowerCase()))
            {
                iterator.remove();
            }
        }
        return null;
    }

    public String doNothingAction()
    {
        return null;
    }

    public String doTimeConsumingStuff()
    {
        try
        {
            Thread.sleep(4000L);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public List getPeriodicalUpdatedValues()
    {
        List refreshList = new ArrayList();

        for (int i = 0; i < 10; i++)
        {
            Address address = new Address((int) (Math.random()*100), "dummyStreet", "fakeCity",
                                                (int) (Math.random()*10), "noState");

            refreshList.add(address);
        }
        return refreshList;
    }

    private List getListMasterData()
    {
        List refreshList = new ArrayList();

        refreshList.add("robert johnson");
        refreshList.add("alpha romeo");
        refreshList.add("bernd billinger");
        refreshList.add("alfred wine");
        refreshList.add("gino lamberti");
        refreshList.add("michael jackson");
        refreshList.add("michael jordon");
        refreshList.add("arnold schwarzenegger");
        refreshList.add("richard gere");
        refreshList.add("scooby doo");
        refreshList.add("spider man");

        return refreshList;
    }

    public void testValueChangeListener(ValueChangeEvent event){

        _message = "Value Change to: ";
        if(event.getNewValue()!=null)
        {
            _message += event.getNewValue().toString();
        }
    }

    public Boolean getCheckBoxValue() {
        return _checkBoxValue;
    }

    public void setCheckBoxValue(Boolean changeCheckBox) {
        _checkBoxValue = changeCheckBox;
    }

    public String getDropDownValue() {
        return _dropDownValue;
    }

    public void setDropDownValue(String changeDropDown) {
        _dropDownValue = changeDropDown;
    }


    public String getInputTextValue() {
        return _inputTextValue;
    }

    public void setInputTextValue(String inputTextValue) {
        _inputTextValue = inputTextValue;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        this._message = message;
    }

    public void checkBoxChanged(ValueChangeEvent evt) {
        _checkBoxValue = (Boolean) evt.getNewValue();
    }

    public void dropDownChanged(ValueChangeEvent evt) {
        _dropDownValue = (String) evt.getNewValue();
    }

    public void inputTextChanged(ValueChangeEvent evt) {
        _inputTextValue = (String) evt.getNewValue();
    }

}
