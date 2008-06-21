package org.apache.myfaces.buildtools.normalizer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Read input file and write it out with everything normalized.
 */
public class Normalizer extends DefaultHandler
{
    Stack elements = new Stack();
    Element rootElement = null;
    StringBuffer currContent = new StringBuffer();

    public static void main( String[] args ) throws Exception
    {
        if (args.length < 1)
        {
            System.out.println("Usage: App inputFile");
            System.exit(-1);
        }
        
        new Normalizer().process(args[0]);
    }
    
    private void process(String filename) throws Exception
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        File f = new File(filename);

        parser.parse(f, this);
        rootElement.sortChildren();
        PrintWriter pw = new PrintWriter(System.out);
        rootElement.print(pw, 0);
        pw.flush();
    }

    private static final Attribute[] EMPTY_ATTRS = new Attribute[0];

    // Ignore any external entities. In particular, this skips the reading of dtds.
    public InputSource resolveEntity(String publicId, String systemId)
     throws IOException, SAXException
    {
        InputStream is = new ByteArrayInputStream(new byte[0]);
        return new InputSource(is);
    }
            
    public void startElement(String uri, String localName, String qname, Attributes attributes)
    {
        int nAttrs = attributes.getLength();
        List attrList = new ArrayList(nAttrs);
        for(int i=0; i<nAttrs; ++i) {
            String name = attributes.getQName(i);
            String value = attributes.getValue(i);
            Attribute a = new Attribute(name, value);
            attrList.add(a);
        }
        Collections.sort(attrList);

        Attribute[] attrs = (Attribute[]) attrList.toArray(EMPTY_ATTRS); 
        Element e = new Element(qname, attrs);

        Element parent;
        if (elements.isEmpty())
        {
            rootElement = e;
        }
        else if (!qname.equals("description"))
        {
            parent = (Element) elements.peek();
            parent.addChild(e);
        }
        elements.push(e);

        // check whether currContent is already non-whitespace.
        // if it is, then throw an exception about "mixed content not supported"
        currContent.setLength(0);
    }

    public void endElement(String uri, String localName, String qname)
    {
        String s = currContent.toString().trim();
        if (!s.isEmpty())
        {
            getCurrElement().setContent(s);
        }
        currContent.setLength(0);

        elements.pop();
    }

    public void characters(char[] ch, int start, int length)
    {
        currContent.append(ch, start, length);
    }
    
    private Element getCurrElement()
    {
        return (Element) elements.peek();
    }
}
