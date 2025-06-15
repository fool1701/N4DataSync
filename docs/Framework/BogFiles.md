============================================================
PAGE 63
============================================================

# Bog Files
## Overview
Niagara provides a standard XML format to store a tree of BValues. This XML format is called "bog" for Baja Object
Graph. The bog format is designed for the following criteria:
Easy to serialize a graph to an output stream using one pass;
Easy to deserialize a graph from an input stream using one pass;
Compact XML, using single letter element and attribute names;
Ability to compress using zip;
Bog files are typically given a ".bog" extention. Although the ".palette" extension can be used to distinguish a bog designed
for use as palette; other than extension bog and palette files are identical.
Bog files can be flat XML files or stored inside zip files. If zipped, then the zip file contains a single entry called "file.xml"
with the XML document. You use workbench to copy any BComponent to a directory on your file system to easily
generate a bog.
## API
In general the best way to read and write bog files is via the standard APIs. The BogEncoder class is used to write
BValues to an output stream using bog format. Note that BogEncoder subclasses XWriter for generating an XML
document. You can use the XWriter.setZipped() method to compress the bog file to to a zip file with one entry
called "file.xml". In general you should use the encodeDocument() method to generate a complete bog document.
However you can also use BogEncoder to stream multiple BValues to an XML document using encode().
The BogDecoder class is used to decode a bog document back into BValue instances. Note that BogDecoder
subclasses XParser for parsing XML. When decoding a bog file, XParser will automatically detect if the file is zipped
or not. General usage is to use decodeDocument() in conjunction with BogEncoder.encodeDocument() for
decoding the entire XML document as a BValue. However BogDecoder can also be used to decode BValues mixed with
other XML data using BogDecoder.decode() and the standard XParser APIs.
BogEncoder.marshal() and BogDecoder.unmarshal() are convenience methods to encode and decode a
BValue to and from a String.
## Syntax
The bog format conforms to a very simple syntax. The root of a bog document must always be "bajaObjectGraph". Under
the root there are only three element types, which map to the three slot types:
Element Description
p Contains information about a property slot
a Contains information about a frozen action slot
t Contains information about a frozen topic slot
All other information is encoded into XML attributes:
Attribute Description
n This required attribute stores the slot name.
m Defines a module symbol using the format "symbol=name". Once defined, the
============================================================
PAGE 64
============================================================

symbol is used in subsequent t attributes.
t
Specifies the type of a property using the format "symbol:typename", where
symbol must map to a module declaration earlier in the document. If
unspecified, then the type of the
property's default value is used.
f Specifies slot flags using the format defined by Flags.encodeToString()
h This attribute specifies the handle of BComponents
x Specifies the slot facets using format defined by BFacets.encodeToString()
v Stores the string encoding of BSimples.
In practice the XML will be a series of nested p elements which map to the structure of the BComplex tree. The leaves of
tree will be the BSimples stored in the v attribute.
## Example
A short example of a kitControl:SineWave linked to a kitControl:Add component. The Add component has a dynamic
slot called description where value is "hello", operator flag is set, and facets are defined with multiLine=true.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<bajaObjectGraph version="1.0">
<p m="b=baja" t="b:UnrestrictedFolder">
 <p n="SineWave" h="1" m="kitControl=kitControl" t="kitControl:SineWave">
  </p>
  <p n="amplitude" v="35"/>
 </p>
 <p n="Add" h="3" t="kitControl:Add">
  </p>
  <p n="Link" t="b:Link">
   <p n="sourceOrd" v="h:1"/>
   <p n="sourceSlotName" v="out"/>
   <p n="targetSlotName" v="inA"/>
  </p>
  <p n="description" f="o" x="multiLine=b:true" t="b:String" v="hello"/>
 </p>
</p>
</bajaObjectGraph>
```