This project is a simple command-line tool that "normalises" a JSP TLD file or a JSF faces-config file.
After two files have been "normalized", they can then be compared with a simple "diff" tool to see what
real differences (if any) there are between the two original files.

All "non-critical" data, like comments and "description" fields are discarded. 
All xml elements and attributes are then sorted into a consistent order:
 * elements with just text content are sorted before others.
 * elements are sorted alphabetically by element name
 * elements with same name are sorted according to the text content of their child nodes
   (means that <tag><name>foo</name></tag> is sorted on the content of the name child).
 
This tool is partly a generic XML "normaliser", but takes a few shortcuts that work specifically
on TLD and faces-config files. It could possibly be expanded to support comparison of other xml
files, but wasn't specifically designed for that purpose. And it is definitely a "hack"; a tool
that is just complex enough for its purpose but not elegant. For example:
* "mixed content" elements are just not supported
* xml namespaces are not handled properly
* external dtds and entity references are ignored