/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.*;

import javax.baja.io.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.schema.*;

/**
 * BSimple is the required base class for primitive types 
 * in the Baja object model.  Simples are the basic building 
 * blocks of the Baja architecture.  They are the atomic 
 * units in which data is modeled, transfered, and stored.  
 * Simples contain no slots themselves, but do have an
 * implicit data value which may be serialized to and
 * from binary and string formats.  
 * <p>
 * BSimple's must be immutable, under no circustances 
 * should there be any way to modify the contents of a 
 * BSimple.  All BSimple must be declared final
 * and may not support subclasses.
 * <p>
 * BSimple must also declare a static final field
 * called {@code DEFAULT} which contains the default
 * instance of the simple type.
 * 
 * @author    Brian Frank
 * @creation  1 Feb 00
 * @version   $Revision: 46$ $Date: 5/14/08 3:14:00 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BSimple
  extends BValue
  implements BIEncodable
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BSimple(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSimple.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * The default implementation of toString() is to
   * call encodeToString().
   */
  @Override
  public String toString(Context context)
  {
    try
    {
      return encodeToString();
    }
    catch(IOException e)
    {
      e.printStackTrace();
      return e.toString();
    }
  }
  
  /**
   * Simple subclasses should override and implement
   * the hashCode() method (this is a must do if interning is
   * used).  If this method is not overridden and it 
   * gets called, it will fail fast by throwing an 
   * UnsupportedOperationException.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    throw new UnsupportedOperationException(getClass().getName());
  }
  
  /**
   * All simples must support an equality check
   * that is true only if the specified obj is of the
   * exact same class and the simple's contents
   * are equal.
   */
  public abstract boolean equals(Object obj);

  /**
   * Return the result of equals().
   */
  @Override
  public final boolean equivalent(Object obj)
  {
    return equals(obj);
  }

  /**
   * Since BSimple's are guaranteed immutable, 
   * the newCopy method always returns itself.
   */
  @Override
  public final BValue newCopy() { return this; }

  /**
   * Since BSimple's are guaranteed immutable, 
   * the newCopy method always returns itself.
   */
  @Override
  public final BValue newCopy(boolean exact) { return this; }

  /**
   * Since BSimple's are guaranteed immutable, 
   * the newCopy method always returns itself.
   */
  @Override
  public final BValue newCopy(CopyHints hints) { return this; }

  /**
   * Encode the simple type using a binary format
   * that can be translated using decode.
   */   
  @Override
  public abstract void encode(DataOutput encoder)
    throws IOException;

  /**
   * Decode the simple using the same binary format
   * that was written using encode, and return the new 
   * instance.  Under no circumstances should this 
   * instance be modified.
   */
  @Override
  public abstract BObject decode(DataInput decoder)
    throws IOException;

  /**
   * Encode the simple using a String format
   * that can be translated using decodeFromString.
   */
  @Override
  public abstract String encodeToString()
    throws IOException;

  /**
   * Decode the simple using the same String format
   * that was written using encodeToString, and return
   * the new instance.  Under no circumstances should 
   * this instance be modified.
   */
  @Override
  public abstract BObject decodeFromString(String s)
    throws IOException;
  
  /**
   * Returns an interned instance of this BSimple.
   * A pool of BSimples (organized by Type), initially 
   * empty, is maintained privately by the class BSimple.
   * When this intern() method is invoked, if the pool 
   * already contains a BSimple equal to this BSimple object 
   * as determined by the equals(Object) and hashCode() methods, 
   * then the BSimple instance from the pool is returned. Otherwise, 
   * this BSimple object is added to the pool and a reference to 
   * this BSimple object is returned.
   * 
   * It follows that for any two BSimples x and y, 
   * x.intern() == y.intern() is true if and only if 
   * x.equals(y) is true AND x.hashCode() == y.hashCode() is
   * also true.
   * 
   * NOTE: Interning for a given type can be disabled by specifying
   * the type in the System.properties file for the 
   * "niagara.intern.excludeTypes" property.  This optional property 
   * contains a semicolon delimited list of excluded types.
   * When disabled, the intern() method will always return this
   * instance.  To disable interning for ALL BSimple types,
   * use the system.property "niagara.intern.disabled=true".
   * 
   * @since Niagara 3.4
   */
  public BSimple intern()
  {
    SimpleType simpleType = (SimpleType)getType();
    if (simpleType != null) // can happen in special cases during initialization, drives me absolutely crazy!
      return simpleType.intern(this);
    else
      return this;
  }
}
