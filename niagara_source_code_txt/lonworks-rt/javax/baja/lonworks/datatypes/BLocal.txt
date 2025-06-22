/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 *   BLocal encapsulates the address for the local
 * interface to the lonworks network.
 * <p>
 * @author    Robert Adams
 * @creation  14 Dec 00
 * @version   $Revision: 1$ $Date: 9/12/01 2:04:39 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BLocal
  extends BSimple
  implements LonAddress
{
  public static final BLocal local = new BLocal();
  public static final BLocal DEFAULT = local;

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BLocal(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:20 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLocal.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Factory method for creating BLocal
   * from subnet and node ids.
   */
  public static BLocal make()
  {
    return local;
  }


  /**
   * Test if the obj is equal in value to this BLocal.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof BLocal))
      return false;
        
    return true;
  }

  /**
   * BLocal hash code {@code LOCAL<<24}
   */
  public int hashCode()
  {
    return LOCAL<<24;
  }

  
  /**
   *
   */
  public String toString(Context context)
  {
    return("local device");
  }
  
  /**
   * 
   */
  public void encode(DataOutput out)
    throws IOException
  {
  }
  
  /**
   *  
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return BLocal.local;
  }

  /**
   * Write the primitive in String format.
   */
  public String encodeToString()
    throws IOException
  {
    return toString(null);
  }

  /**
   * Read the primitive from String format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    return local;
  }

/////////////////////////////////////////////////
// LonAddress  api
/////////////////////////////////////////////////

  /** Return address type LOCAL */
  public int getAddressType() { return LOCAL; }
}
