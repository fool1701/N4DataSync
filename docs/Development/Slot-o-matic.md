# Slot-o-matic

## Overview
The Slot-o-matic is a java source code preprocessor which generates java source code for Baja slots based on a predefined comment header block. The generated code is placed in the same source file, and all other code in the original source is not modified in any way.

## Usage

### Invocation
Slot-o-matic is invoked by the executable `slot.exe`. To get help invoke:
```
D:\>slot -?
usage: slot [-f] [-?] <dir | file ...>
-f force recompile of specified targets
-? provides additional help.
slot compiles Baja object files.
For a file to be compiled, it must have a name
  of the form B[A-Z]*.java.
slot will happily recurse any and all directory arguments.
```

Slot-o-matic will compile any file that meets the following conditions:
1. The file name is of format `B[A-Z]*.java`, e.g.  `BObject.java` or `BSystem.java`, but not `Ball.java`.
2. The source code has a comment block delimited  by `/*- -*/`.

When Slot-o-matic compiles a file, it reads the comment block and generates new java source code based on the contents of that block. The new source is placed in the file being compiled immediately after the Baja comment block. If any errors are found, the contents of the file are not altered in any way. The source file may (and indeed probably must) have any other source code required to implement the class in the source file, as with normal java source. The only difference between a normal java source file and one usable by Slot-o-matic is the `/*- -*/` comment block.

Compiling a file is simple:
```
D:\>slot D:\niagara\r3dev\fw\history\javax\baja\history\BHistoryService.java
  Compile BHistoryService.java
Compiled 1 files
D:\>
```

As is a directory:
```
D:\>slot D:\niagara\r3dev\fw\history
  Compile BHistoryService.java
Compiled 1 files
D:\>
```

Slot-o-matic works like `make` in that it will only compile files whose `/*- -*/` comment block's content has changed since the last compile. To force recompile, use the `-f` flag on a file or directory:

```
D:\>slot -f D:\niagara\r3dev\fw\history\src\javax\baja\history\
  Compile BBooleanHistory.java
  Compile BFloatHistory.java
  Compile BHistory.java
  Compile BHistoryDevicelet.java
  Compile BHistoryJoin.java
  Compile BHistoryPeriod.java
  Compile BHistoryService.java
  Compile BHistorySync.java
  Compile BStorageType.java
Compiled 9 files
D:\>
```

### Slot file format
As stated above, Slot-o-matic will compile only files that meet certain conditions. In addition to having an appropriate file (and by extension class) name, the source code in the file must contain a comment block that describes the slots on the object to be compiled.

#### Examples

##### Class Example

This example class would resolve in a file named `BImaginaryObject.java`.

```java
  /*-
  class BImaginaryObject
  {
    properties
    {
      imaginaryName: String
        -- The imaginary name for the imaginary object.
        default {[ "imaginaryName" ]}
      size: int
        -- The size of the imaginary object.
        flags { readonly, transient }
        default {[ 0 ]}
    }
    actions
    {
      imagine(arg: BComponent)
        -- Imagine something
        default {[ new BComponent() ]}
      create(): BSystem
        -- Create a new imaginary system.
    }
    topics
    {
      imaginationLost: BImaginaryEvent
        -- Fire an event when the object loses its imagination.
    }
  }
  -*/
```

There are blocks for each of the major slot types: properties, actions, and topics. None of the blocks needs to be present.

##### Properties Block
Each property has a name and a data type. Comments are specified via the "--" tag per line of comment. All comments are transferred to the javadoc headers of the generated source code but are of course optional. A default value for all properties must be specified. The default block is delineated by `{[ ]}` and may have any sequence of java code inside it. Flags on the property may also optionally be specified. For more information on the available flags, see the Flags bajadoc. Slot-o-matic will generate all get and set methods for the property.

##### Actions Block
Each action may have 0 or 1 input (formal) arguments, and may optionally return a value. Actions are commented like properties. The input argument, if present, must have a default value as with a property. Slot-o-matic will generate the action invocation code; the implementor of the class must provide a `do<actionName>` method that provides the action implementation.

##### Topics Block
Each topic specifies a name and an event type that it sends when fired. Slot-o-matic generates code to fire the event.

##### Enum Example
This example class would resolve in a file named `BImaginaryEnum.java`.

```java
  /*-
  enum BImaginaryEnum
  {
    range
    {
      good,
      bad,
      ugly
    }
    default {[ bad ]}
  }
  -*/
```

Each member of the enumeration is specified.

### BNF
The formal BNF of the format is as follows:

```
Unit ::= ( Annotation )? ( Class | Enum )
Class ::= "class" Identifier "{" ( Singleton )? ( PropertyBlock | ActionBlock |
 TopicBlock )* "}"
Singleton ::= "singleton"
PropertyBlock ::= ( Annotation )* "properties" "{" ( Property )* "}"
Property ::= ( Annotation )* Identifier ":" BajaType ( Default | Flags | SlotFacets
 )*
ActionBlock ::= ( Annotation )* "actions" "{" ( Action )* "}"
Action ::= ( Annotation )* Identifier "(" ( FormalParameter )? ")" ( ":" BajaType
 )? ( Default | Flags | SlotFacets )*
FormalParameter ::= Identifier ( ":" BajaType )?
TopicBlock ::= ( Annotation )* "topics" "{" ( Topic )* "}"
Topic ::= ( Annotation )* Identifier ( ":" BajaType )? ( Flags | SlotFacets )*
Enum ::= "enum" Identifier "{" RangeBlock EnumDefault"}"
RangeBlock ::= "range" "{" ( Range ( "," Range )* )? ( "," )? "}"
Range ::= Identifier ( "=" JavaExpression )?
EnumDefault ::= "default" "{" "[" JavaExpression "]" "}"
Flags ::= "flags" "{" ( Identifier ( "," Identifier )* )? ( "," )? "}"
Default ::= "default" "{" "[" JavaExpression "]" "}"
SlotFacets ::= "slotfacets" "{" "[" ( Facets ( "," Facets )* )? ( "," )? "]" "}"
Facets ::= ( BajaName "=" )? JavaExpression
Identifier ::= <IDENTIFIER>
BajaType ::= ( BajaPrimitive | BajaName )
BajaPrimitive ::= ( "boolean" | "int" | "long" | "float" | "double" )
BajaName ::= Identifier ( "." Identifier )*
Annotation ::= "@" Identifier ( NormalAnnotation | SingleAnnotation )?
SingleAnnotation ::= "(" ( JavaExpression | JavaExpressionList ) ")"
NormalAnnotation ::= "(" KeyValue ( "," KeyValue )* ")"
KeyValue ::= Identifier "=" ( JavaExpression | JavaExpressionList )
JavaExpressionList ::= "{" JavaExpression ( "," JavaExpression )* ( "," )? "}"
JavaExpression ::= <JAVA_EXPRESSION>
