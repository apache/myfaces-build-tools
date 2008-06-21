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
    static String[] SORT_PRIORITY_HIGH = new String[]
    {
        "name",
    };
    
    static String[] SORT_PRIORITY_LOW = new String[]
    {
        "class"
    };

    String name;
    Attribute[] attributes;
    String content;
    List children = new LinkedList();
    
    // Value used to choose ordering for two elements with different names.
    // Elements whose name matches a "high priority" pattern are output before
    // elements with "normal" names, which are output before elements whose
    // names match a "low priority" pattern.
    int sortPriority = 0;

    // Value used to choose ordering for two elements that have the same
    // name and no text content. This is built from the text content of
    // the (ordered) set of child elements, so that two nodes with similar
    // child elements get sorted in the same order.
    String sortKey = null;

    // The attributes array is expected to be sorted before passing in here.
    public Element(String name, Attribute[] attributes)
    {
        this.name = name;
        this.attributes = attributes;
        sortPriority = getSortPriority(name);
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

        // Compute the sortKey for this element, used when
        // two elements have identical names and therefore need
        // to be ordered according to the content of their
        // child elements.
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

        if (name.equals(otherElement.name))
        {
            // Ok, we have two tags with the same name and both have child elements.
            // Sort by the sortkey we computed during the sortChildren method
            return sortKey.compareTo(otherElement.sortKey);
        }

        // Names are different: check whether either name has special priority.
        if (sortPriority != otherElement.sortPriority)
        {
            return otherElement.sortPriority - sortPriority;
        }

        // Ok, different names and the priority of the name is equal.
        // So just sort alphabetically.
        return name.compareTo(otherElement.name);
    }

    /**
     * Return the "priority" of this name.
     * <p>
     * Names with higher priority are sorted earlier in the list of elements.
     * And as elements with equal names are sorted according to the contents
     * of their children, this also affects the sorting order of the parent
     * element of an element that has a "priority" value.
     * <p>
     * Normal element names have zero priority.
     */
    private int getSortPriority(String ename)
    {
        for(int i=0; i<SORT_PRIORITY_HIGH.length; ++i)
        {
            if (ename.contains(SORT_PRIORITY_HIGH[i]))
            {
                // always positive
                return SORT_PRIORITY_HIGH.length - i;
            }
        }

        for(int i=0; i<SORT_PRIORITY_LOW.length; ++i)
        {
            if (ename.contains(SORT_PRIORITY_LOW[i]))
            {
                // always negative
                return i - SORT_PRIORITY_LOW.length;
            }
        }
        
        return 0;
    }
}
