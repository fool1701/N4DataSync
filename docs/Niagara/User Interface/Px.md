# Px

## Overview
 Px is a technology used to package a UI presentation as an XML file. A px file defines a tree of bajaui widgets and their
 data bindings. Any BWidget and BBinding may be used, including those custom developed by you. Typically px files
 are created using a WYSIWYG tool called the PxEditor, although they can also be handcoded or auto-generated.
 Px files are always given a ".px" extension, and modeled with the file:PxFile type.

## Px Views
 A px file may be used in a UI via a variety of mechanisms:
Navigating directly to a px file will display its presentation
The px file may be attached to a component as a dynamic view
Many px files can be automatically translated into HTML using hx
 The WbPxView is the standard presentation engine for px files. WbPxView is the default view of file:PxFile, so you
 can use px files just like an HTML file - by navigating to one.
 The BPxView class may be used to define dynamic views on components. Dynamic views are like dynamic slots, in that
 they are registered on an instance versus a type. A dynamic view is automatically available for every BPxView found in a
 component. Each BPxView provides an ord to the px file to use for that view. PxViews may be added through the
 workbench or programmatically. Since the bindings within a px file are always resolved relative to the current ord, you
 can reuse the same px file across multiple components by specifying bindings with relative ords.
 If all the widgets used in a px file have a translation to hx, then the entire px file can be automatically translated into
 HTML for hx users. See hx for more details.

## PxMedia
 As a general rule any BWidget is automatically supported when viewing a px file. However, viewing a px file in hx only
 supports a subset of widgets (those that have a hx agent). This means that you must target the lowest common
 denominator when creating px presentations. The target media for a px presentation is captured via the BPxMedia class.
 Both the px file and the PxView can store a PxMedia type. Currently there are only two available media types:
 workbench:WbPxMedia and hx:HxPxMedia. The PxEditor will warn you if you attempt to use widgets and bindings
 not supported by the target media.

## API
 The bajaui module provides a standard API for serializing and deserializing a widget tree to and from it's XML
 representation. The PxEncoder class writes a widget tree to an XML document. PxDecoder is used to read an XML
 document into memory as a widget tree.

## Syntax
 The bog XML format is optimized to be very concise with equal weight given to both read and write speed. The px XML
 format is designed to be more human usable. All px files have a root px element with a required version and optional
 media attribute. Within the root px element is an import element and a content element.
 The import section contains a list of module elements. Each module specifies a Niagara module name using the name
 attribute. This module list is used to resolve type names declared in the content section.
 The content section contains a single element which is the root of the px file's widget tree. Each component in the tree
 uses a type name as the element name. These type names are resolved to fully specified type specs via the import section.
Niagara Developer Guide
8/26/2015
100

============================================================
PAGE 103
============================================================

 Frozen simple properties of each component are declared as attributes in the component's start tag. Complex and
 dynamic slots are specified as children elements. The name of the slot inside the parent component may be specified
 using the name attribute. Dynamic simple properties specify their string encoding using the value attribute.

## Example
 The following example shows a BoundLabel placed at 20,20 on a CanvasPane, which is itself nested in a ScrollPane. Note
 since the CanvasPane is the value of ScrollPane's frozen property content, it uses the name attribute. Note how frozen
 simple properties like viewSize, layout, and ord are defined as attributes.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<px version="1.0" media="workbench:WbPxMedia">
<import>
  <module name="baja"/>
  <module name="bajaui"/>
  <module name="converters"/>
  <module name="gx"/>
  <module name="kitPx"/>
</import>
<content>
<ScrollPane>
  <CanvasPane name="content" viewSize="500.0,400.0">
    <BoundLabel layout="20,20,100,20">
      <BoundLabelBinding ord="station:|slot:/Playground/SineWave"
statusEffect="none">
        <ObjectToString name="text"/>
      </BoundLabelBinding>
    </BoundLabel>
  </CanvasPane>
</ScrollPane>
</content>
</px>
```
Niagara Developer Guide
8/26/2015
101
