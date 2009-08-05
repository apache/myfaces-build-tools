package org.myorganization.converter;

public class PhoneNumber
{
    private String areaCode;
    private String number;
    
    public PhoneNumber(String number) 
    {
        this.number = number;
    }
    
    public PhoneNumber(String areaCode, String number) 
    {
        this(number);
        this.areaCode = areaCode;
    }
    
    public String getAreaCode()
    {
        return areaCode;
    }
    public void setAreaCode(String areaCode)
    {
        this.areaCode = areaCode;
    }
    public String getNumber()
    {
        return number;
    }
    public void setNumber(String number)
    {
        this.number = number;
    }
    
}
