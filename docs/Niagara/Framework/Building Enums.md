# **Building Enums**

## **Overview**

The BEnum base class is used to define enumerated types. \[cite: 401\] An enum is composed of a fixed set of int/String pairs called its range. \[cite: 402\] The int identifiers are called ordinals and the String identifiers are called tags. \[cite: 403\] Enum ranges are managed by the BEnumRange class. \[cite: 404\]

There are three subclasses of BEnum. \[cite: 405\] BBoolean is a special case which models a boolean primitive. \[cite: 405\] The BDynamicEnum class is used to manage weakly typed enums which may store any ordinal and range. \[cite: 406\] Strongly typed enums may be defined at compile time by subclassing BFrozenEnum. \[cite: 407\] The Niagara Framework builds a BFrozenEnum's range using the following set of introspection rules: \[cite: 408\]

* Meet the common rules applicable to all BObjects. \[cite: 409\]  
* Meet the common rules applicable to all BSimples (although BFrozenEnum is not required to declare a DEFAULT field). \[cite: 409\]  
* Define a set of public static final fields which reference instances of the BFrozenEnum's range. \[cite: 410\] Each of these BFrozenEnum must declare a unique ordinal value. \[cite: 411\] By convention ordinals should start at zero and increment by one. \[cite: 412\] Each of these BFrozenEnum must have a type exactly equal to the declaring class. \[cite: 413\]  
* There can be no way to create other instances of the BFrozenEnum outside of the fields declaring its range. \[cite: 414\] This means no other instances declared in static fields, returned by a static method, or instantiable through non-private constructors. \[cite: 415\]  
* There must be at least one BFrozenEnum declared in the range. \[cite: 416\]  
* The default value of a BFrozenEnum is the first instance, by convention with an ordinal value of zero. \[cite: 417\]  
* By convention a public static final int field is defined for each BFrozenEnum in the range to provide access to the ordinal value. \[cite: 418\]

## **Example**

The following source provides a complete example of the implementation for BOrientation: \[cite: 419\]

/\*  
Copyright 2000 Tridium, Inc. All Rights Reserved.  
\*/  
package javax.baja.ui.enum; \[cite: 420\]  
import javax.baja.sys.\*;

/\*\*  
\* BOrientation defines a widget's orientation as  
\* either horizontal or vertical. \[cite: 421\]  
\*/  
public final class BOrientation  
  extends BFrozenEnum  
{  
  public static final int HORIZONTAL \= 0; \[cite: 422\]  
  public static final int VERTICAL \= 1; \[cite: 422\]

  public static final BOrientation horizontal \= new BOrientation(HORIZONTAL); \[cite: 423\]  
  public static final BOrientation vertical \= new BOrientation(VERTICAL); \[cite: 423\]

  public Type getType() { return TYPE; } \[cite: 424\]  
  public static final Type TYPE \= Sys.loadType(BOrientation.class); \[cite: 424\]

  public static BOrientation make(int ordinal)  
  {  
    return (BOrientation)horizontal.getRange().get(ordinal); \[cite: 425\]  
  }

  public static BOrientation make(String tag)  
  {  
    return (BOrientation)horizontal.getRange().get(tag); \[cite: 425\]  
  }

  private BOrientation(int ordinal) { super(ordinal); }  
}  
