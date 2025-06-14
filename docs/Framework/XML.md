============================================================
PAGE 60
============================================================

# XML
## Overview
The javax.baja.xml package defines the core XML API used in the Niagara architecture. The two cornerstones of this APIs
 are:
1. XElem: Provides a standard representation of an XML element tree to be used in memory. It is similar to the W3's
 DOM, but much lighter weight.
2. XParser: XParser is a light weight XML parser. It may be used in two modes: to read an entire XML document into
 memory or as a pull-parser.
The Baja XML APIs are designed to be small, fast, and easy to use. To achieve this simplicity many advanced features of
 XML are not supported by the javax.baja.xml APIs:
Only UTF-8 and UTF-16 encodings are supported. Unicode characters in attributes and text sections are escaped
 using the standard entity syntax '&#dd;' or '&#xhh;'.
All element, attribute, and character data productions are supported.
CDATA sections are supported.
Namespaces are supported at both the element and attribute level.
Doctype declarations, DTDs, entity declarations are all ignored by the XML parser. XML used in Niagara is always
 validated at the application level for completeness and efficiency.
Processing instructions are ignored by the XML parser.
No access to comments is provided by the XML parser.
Character data consisting only of whitespace is always ignored.
## Example XML
For the code examples provided we will use this file "test.xml":

```xml
<root xmlns="ns-stuff" xmlns:u="ns-user">
  <u:user name="biff" age="29">
    <u:description>Biff rocks</u:description>
    <u:skills sing="true" dance="false"/>
  </u:user>
  <user name="elvis" alive="maybe" xmlns="">
    <skills sing="true" dance="true"/>
  </user>
  <attr1="1" u:attr2="2"/>
</root>
```

## Working with XElem
The XElem class is used to model an XML element tree. An element is defined by:
Namespace: Elements which are in a namespace will return a non-null value for ns(). You may also use the
 prefix() and uri() methods to access the namespace prefix and URI. The "xmlns" attribute defines the default
 namespace which will apply to all child elements without an explicit prefix. The "xmlns:{prefix}" attribute defines
 an namespace used by child elements with the specified prefix.
Name: The name() method returns the local name of the element without the prefix. You may also use qname()
 to get the qualified name with the prefix.
Attributes: Every element has zero or more attributes declared within the element start tag. There are an
 abundance of convenience methods used to access these attributes. Attributes without an explicit prefix are
 assumed to be in no namespace, not the default namespace.
============================================================
PAGE 61
============================================================

Content: Every element has zero of more content children. Each content child is either an XText or XElem
 instance. Character data (including CDATA) is represented using XText.
The following code illustrates many of the commonly used methods on XElem:

```java
// parse the test file
XElem root = XParser.make(new File("test.xml")).parse();

// dump xml tree to standard out
root.dump();

// dump root identity
System.out.println("root.name   = " + root.name());
System.out.println("root.ns     = " + root.ns());

// get elements
System.out.println("elems()     = " + root.elems().length);
System.out.println("elems(user) = " + root.elems("user").length);

// biff
XElem biff = root.elem(0);
System.out.println("biff.name   = " + biff.name());
System.out.println("biff.ns     = " + biff.ns());
System.out.println("biff.age    = " + biff.get("age"));
// elvin
XElem elvis = root.elem(1);
XElem skills = elvis.elem("skills");
System.out.println("elvis.name  = " + elvis.name());
System.out.println("elvis.ns    = " + elvis.ns());
System.out.println("skills.sing = " + skills.getb("sing"));
```

Output from code above:
```
root.name   = root
root.ns     = ns-stuff
elems()     = 3
elems(user) = 2
biff.name   = user
biff.ns     = ns-user
biff.age    = 29
elvis.name  = user
elvis.ns    = null
skills.sing = true
```
## Working with XParser
The XParser class is used to parse XML input streams into XElems. The easiest way to do this is to parse the entire
 document into memory using the parse() method:

```java
// parse and close input stream
XElem root = XParser.make(in).parse();
```

The above code follows the W3 DOM model of parsing a document entirely into memory. In most cases this is usually
 acceptable. However it can create efficiency problems when parsing large documents, especially when mapping the
 XElems into other data structures.
To support more efficient parsing of XML streams, XParser may also be used to read
 elements off the input stream one at a time. This is similar to the SAX API, except you pull events instead of having them
 pushed to you. A pull model is much easier to work with.
To work with the pull XParser APIs you will use the next() method to iterate through the content instances. This
============================================================
PAGE 62
============================================================

 effectively tokenizes the stream into XElem and XText chunks. Each call to next() advances to the next token and
 returns an int constant: ELEM_START, ELEM_END, TEXT, or EOF. You may also check the type of the current token
 using type(). You may access the current token using elem() or text().
XParser maintains a stack of XElems for you from the root element down to the current element. You may check the
 depth of the stack using the depth() method. You can also get the current element at any position in the stack using
 elem(int depth).
It is very important to understand the XElem at given depth is only valid until the parser returns ELEM_END for that
 depth. After that the element will be reused. The XText instance is only valid until the next call to next(). You can
 make a safe copy of the current token using copy().
The following code illustrates using XParser in pull mode:

```java
XParser p = XParser.make(new File("test.xml"));
p.next(); // /root start
System.out.println("root.start: " + p.elem().name() + " " + p.depth());
p.next(); // root/user(biff) start
System.out.println("biff.start: " + p.elem().name() + " " + p.depth());
p.next(); // root/user/description start
System.out.println("desc.start: " + p.elem().name() + " " + p.depth());
p.next(); // root/user/description text
System.out.println("desc.text:  " + p.text() + " " + p.depth());
p.next(); // root/user/description end
System.out.println("desc.end:   " + p.elem().name() + " " + p.depth());
p.skip(); // skip root/user/skills
p.next(); // root/user(biff) end
System.out.println("biff.end: " + p.elem().name() + " " + p.depth());
p.next(); // root/user(elvis) start
System.out.println("elvis.start: " + p.elem().name() + " " + p.depth());
```

Output from code above:
```
root.start:  root        1
biff.start:  user        2
desc.start:  description 3
desc.text:   Biff rocks  3
desc.end:    description 3
biff.end:    user        2
elvis.start: user        2
```

