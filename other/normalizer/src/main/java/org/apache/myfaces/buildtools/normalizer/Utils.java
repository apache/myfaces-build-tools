package org.apache.myfaces.buildtools.normalizer;

import java.io.PrintWriter;


/**
 * Read input file and write it out with everything normalized.
 */
public class Utils 
{
    public static void indent(PrintWriter writer, int amount)
    {
        for(int i=0; i<amount; ++i)
            writer.write("  ");
    }
}
