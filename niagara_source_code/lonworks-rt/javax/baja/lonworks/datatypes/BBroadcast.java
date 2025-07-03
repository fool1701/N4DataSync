/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
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
 * BBroadcast encapsulates a broadcast address.
 * <p>
 * @author    Robert Adams
 * @creation  4 March 02
 * @version   $Revision: 1$ $Date: 9/12/01 2:04:39 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BBroadcast
  extends BSimple
  implements LonAddress
{
  public static final BBroadcast domain0 = new BBroadcast(0);
  public static final BBroadcast domain1 = new BBroadcast(1);
  public static final BBroadcast DEFAULT = domain0;

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BBroadcast(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:20 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBroadcast.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Factory method for creating BBroadcast
   */
  public static BBroadcast make(int d)
  {
   if(d==0)return domain0;
   return domain1;
  }
  
  /**
   * Private constructor.
   */
  private BBroadcast (int d)
  {
    domainNdx = d;
  }
 
  /** Return the index of the domain for broadcast. */
  public int getDomainIndex() { return domainNdx; }

  /**
   * Test if the obj is equal in value to this BBroadcast.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof BBroadcast))
      return false;
        
    return domainNdx == ((BBroadcast)obj).domainNdx;
 }
  
  /**
   *
   */
  public String toString(Context context)
  {
    return("broadcast domain" + domainNdx);
  }
  
  /**
   * BBroadcast hash code {@code BROADCAST<<24 | domainNdx}
   */
  public int hashCode()
  {
    return BROADCAST<<24 | domainNdx;
  }

  
  /**
   * 
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeInt  ( domainNdx );
  }
  
  /**
   *  
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return BBroadcast.make(in.readInt());
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
    return BBroadcast.make( Integer.decode(s.substring(s.length()-1)).intValue() );
  }

  private int domainNdx;

/////////////////////////////////////////////////  
// LonAddress  api
/////////////////////////////////////////////////

  /** Return address type BROADCAST */
  public int getAddressType() { return BROADCAST; }
}
