package org.apache.myfaces.buildtools.normalizer;

import java.io.PrintWriter;

public class Attribute implements Comparable
{
    String name;
    String value;

    public Attribute(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    public void print(PrintWriter writer)
    {
        writer.append(name);
        writer.append("=");
        writer.append("\"");
        writer.append(value);
        writer.append("\"");
    }

    public int compareTo(Object other)
    {
        Attribute otherAttr = (Attribute) other;
        return name.compareTo(otherAttr.name);
    }    
}