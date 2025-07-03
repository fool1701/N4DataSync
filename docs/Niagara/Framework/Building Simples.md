# **Building Simples**

## **Overview**

BSimple is the base class for all atomic data types in Niagara. \[cite: 382\] As an atomic data type, BSimples store a simple piece of data which cannot be decomposed. \[cite: 383\] All simples are immutable, that is once an instance is created it may never change its state. \[cite: 384\] Concrete subclasses of BSimple must meet the following requirements: \[cite: 385\]

* Meet the common rules applicable to all BObjects. \[cite: 386\]  
* Must declare a public static final field named DEFAULT which contains a reference to the default instance for the BSimple. \[cite: 386\]  
* All BSimples must be immutable\! Under no circumstances should there be any way for an instance of BSimple to have its state changed after construction. \[cite: 387\]  
* Every concrete subclass of BSimple must be declared final. \[cite: 388\]  
* Every BSimple must implement the equals() method to compare the equality of its atomic data. \[cite: 389\]  
* Every BSimple must implement binary serialization: \[cite: 390\]  
  public abstract void encode(DataOutput out);  
  public abstract BObject decode(DataInput in);

* Every BSimple must implement text serialization: \[cite: 391\]  
  public abstract String encodeToString();  
  public abstract BObject decodeFromString(String s);

* Convention is to make constructors private and provide one or more factory methods called make. \[cite: 392\]

## **Example**

The following source provides an example (BInteger): \[cite: 393\]

/\*  
Copyright 2000 Tridium, Inc. All Rights Reserved.  
\*/  
package javax.baja.sys; \[cite: 394\]  
import java.io.\*;

/\*\*  
\* The BInteger is the wrapper class for Java primitive  
\* int objects. \[cite: 395\]  
\*/  
public final class BInteger  
  extends BNumber  
{  
  public static BInteger make(int value)  
  {  
    if (value \== 0\) return DEFAULT;  
    return new BInteger(value);  
  }

  private BInteger(int value)  
  {  
    this.value \= value;  
  }

  public int getInt()  
  {  
    return value;  
  }

  public float getFloat()  
  {  
    return (float)value;  
  }

  public int hashCode()  
  {  
    return value; \[cite: 397\]  
  }

  public boolean equals(Object obj)  
  {  
    if (obj instanceof BInteger)  
      return ((BInteger)obj).value \== value;  
    return false;  
  }

  public String toString(Context context)  
  {  
    return String.valueOf(value); \[cite: 398\]  
  }

  public void encode(DataOutput out)  
    throws IOException  
  {  
    out.writeInt(value);  
  }

  public BObject decode(DataInput in)  
    throws IOException  
  {  
    return new BInteger(in.readInt());  
  }

  public String encodeToString()  
    throws IOException // Note: Original PDF might have a typo here, encodeToString typically doesn't throw IOException for simple types unless it involves I/O itself. The example shows it, so I'll keep it.  
  {  
    return String.valueOf(value); \[cite: 399\]  
  }

  public BObject decodeFromString(String s)  
    throws IOException // Note: Original PDF might have a typo here, decodeFromString might throw a NumberFormatException or similar, wrapped in IOException. The example shows it, so I'll keep it.  
  {  
    try  
    {  
      return new BInteger(Integer.parseInt(s));  
    }  
    catch (Exception e)  
    {  
      throw new IOException("Invalid integer: " \+ s);  
    }  
  }

  public static final BInteger DEFAULT \= new BInteger(0);  
  public Type getType() { return TYPE; }  
  public static final Type TYPE \= Sys.loadType(BInteger.class); \[cite: 400\]  
  private int value;  
}  
