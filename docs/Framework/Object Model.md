# Object Model

## Niagara Types

The heart of Niagara is its type system layered above Java type system. Niagara Types are monikers to a Java class in a specific module. The interface `javax.baja.sys.Type` is used to represent Types in the Niagara Framework. Every Type is globally identified by its module name and its type name. As previously discussed, a module name globally identifies a Niagara software module. The type name is a simple String name which is mapped to a Java class name by the "module.xml" manifest file. Type's are commonly identified using a format of:

`{module name}:{type name}`

Examples:

* `baja:AbsTime`
* `bajaui:TextField`

**Note:** to avoid confusion with the various uses of the word type, we will use capitalization when talking about a Niagara Type.

## BObject

All Java classes which implement a Niagara Type are subclassed from `BObject`. It is useful to compare `Type` and `BObject` to their low level Java counter parts:

| Java                      | Niagara                       |
| :------------------------ | :---------------------------- |
| `java.lang.Object`        | `javax.baja.sys.BObject`      |
| `java.lang.Class`         | `javax.baja.sys.Type`         |
| `java.lang.reflect.Member`| `javax.baja.sys.Slot` (discussed later) |

`Type` and `Slot` capture the concepts of meta-data, while `BObject` provides the base class of Niagara object instances themselves.

## BInterface

Java interfaces may be mapped into the Niagara type system by extending `BInterface`. You can query whether a `Type` maps to a class or an interface using the method `isInterface()`.

Classes which implement `BInterface` must also extend `BObject`. All `BInterface` class names should be prefixed with "BI".

## BObject Semantics

Subclassing from `BObject` provides some common semantics that all instances of Niagara Types share:

* They all support a `getType()` method.
* Types installed on a system can be extensively queried using the registry.
* All `BObject`s have an icon accessed via `getIcon()`.
* All `BObject`s have a set of agents accessed via `getAgents()`. Most agents are user agents which provide some visualization or configuration mechanism for the `BObject`.

## Building BObject

By subclassing `BObject` you make an ordinary Java class into a Niagara Type. You must obey the following rules when creating a Type:

* Types must declare a mapping between their type name and their qualified Java class name in "module.xml". The Java class name must always be prefixed with 'B', but the type name doesn't include this leading 'B'. For example:
  `<type name="FooBar" class="javax.baja.control.BFooBar" />`

* All Types must override the `getType()` method to return a statically cached `Type` instance created by the `Sys.loadType()` method:

    ```java
    public Type getType() { return TYPE; }
    public static final Type TYPE = Sys.loadType(BFooBar.class);
    ```
