package org.apache.myfaces.buildtools.normalizer;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represent an XML element which has either body text or a list of child elements.
 * <p>
 * Mixed-content elements are not supported. Namespaces are ignored.
 */
public class Element implements Comparable
{
    String name;
    Attribute[] attributes;
    String content;
    List children = new LinkedList();
    String sortKey = null;

    // The attributes array is expected to be sorted before passing in here.
    public Element(String name, Attribute[] attributes)
    {
        this.name = name;
        this.attributes = attributes;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
    
    public void addChild(Element child)
    {
        children.add(child);
    }

    public void sortChildren()
    {
        if (children.isEmpty())
        {
            // Nothing to sort here.
            if (content == null)
                sortKey = ""; // should never happen
            else
                sortKey = content;
            return;
        }

        // first ensure each child is internally sorted
        for(Iterator i = children.iterator(); i.hasNext(); )
        {
            ((Element) i.next()).sortChildren();
        }

        // now sort the list of children relative to each other
        Collections.sort(children);
        
        // and compute the sortKey for this element
        StringBuffer buf = new StringBuffer();
        for(Iterator i = children.iterator(); i.hasNext(); )
        {
            Element child = (Element) i.next();
            if (buf.length() == 0)
            {
                buf.append("&");
            }
            buf.append(child.sortKey);
        }
        sortKey = buf.toString();
    }

    public void print(PrintWriter writer, int indentAmount)
    {
        Utils.indent(writer, indentAmount);
        writer.append("<");
        writer.append(name);
        if (attributes != null)
        {
            for(int i=0; i<attributes.length; ++i)
            {
                writer.append(" ");
                attributes[i].print(writer);
            }
        }

        if (content != null)
        {
            writer.append(">");
            writer.append(content);
            writer.append("</");
            writer.append(name);
            writer.append(">\n");
        }
        else if (children.size() == 0)
        {
            writer.append("/>\n");
        }
        else
        {
            writer.append(">\n");
            for(Iterator i = children.iterator(); i.hasNext();)
            {
                Element e = (Element)i.next();
                e.print(writer, indentAmount+1);
            }
            Utils.indent(writer, indentAmount);
            writer.append("</");
            writer.append(name);
            writer.append(">\n");
        }
    }

    /**
     * Return a value that indicates how this element should be sorted relative to
     * other elements.
     * <p>
     * This method is not safe to call until after sortChildren() has been invoked.
     */
    public int compareTo(Object other)
    {
        Element otherElement = (Element) other;

        // Sort tags with just text content before ones with child elements
        if (this.children.isEmpty() && !otherElement.children.isEmpty())
            return -1;
        else if (!this.children.isEmpty() && otherElement.children.isEmpty())
            return +1;
        
        // Sort tags with different names alphabetically
        int compare = name.compareTo(otherElement.name);
        if (compare != 0)
        {
            return compare;
        }

        // Ok, we have two tags with the same name and both have child elements.
        // Sort by the sortkey we computed during the sortChildren method
        return sortKey.compareTo(otherElement.sortKey);
    }
}
