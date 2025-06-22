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

import com.tridium.lonworks.util.LonByteArrayUtil;

/**
 *  The authentication key.  See Neuron Chip Data Book A.2.1. 
 *
 * @author    Robert Adams
 * @creation  14 Feb 02
 * @version   $Revision: 1$ $Date: 9/12/01 2:04:40 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BAuthenticationKey
  extends BSimple
{
  private static final byte[] defaultKey = {-1,-1,-1,-1,-1,-1};

  public static final BAuthenticationKey DEFAULT = new BAuthenticationKey(defaultKey);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BAuthenticationKey(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:20 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAuthenticationKey.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Create a BAuthenticationKey with the given byte array.
   */
  public static BAuthenticationKey make (byte[] b) 
  {
    return new BAuthenticationKey(b);
  }
  
  public BAuthenticationKey makeFrom (byte[] b) 
  {
    if(compare(b)) return this;
    return new BAuthenticationKey(b);
  }
  
  private BAuthenticationKey(byte[] b) 
  {
    if(b.length != 6) throw new RuntimeException("Attempt to create BAuthenticationKey with invalid length");
    this.authKey = b;
  }

  /**
   * Get 6 byte authKey.
   */
  public byte[] getByteArray() 
 { 
    byte[] a = new byte[AUTH_KEY_LENGTH];
    System.arraycopy(authKey, 0, a, 0, AUTH_KEY_LENGTH);
    return a; 
  }

  
  /**
   * Is this BAuthenticationKey all zeros.
   */
  public boolean isZero()
  {
    for (int i = 0; i < authKey.length; i++)
    {
      if (authKey[i] != 0) return false;
    }
    return true;
  }
  
   
  /**
   * Test if the obj is equal in value to this BAuthenticationKey.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof BAuthenticationKey))
      return false;
    
    BAuthenticationKey comp = (BAuthenticationKey)obj;
    return compare(comp.authKey);
  } 
  
  private boolean compare(byte[] key)
  {
    if(key.length != AUTH_KEY_LENGTH) return false;
    
    for (int i = 0; i < AUTH_KEY_LENGTH; i++)
    {
      if (authKey[i] != key[i]) return false;
    }
    return true;
  }
  
  /**
   *
   */
  public String toString(Context context)
  {
    return LonByteArrayUtil.toString(authKey);
  }
  
  /**
   * 
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.write(authKey);
  }
  
  /**
   *  
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    byte[] aKey = new byte[AUTH_KEY_LENGTH];
    in.readFully(aKey, 0, AUTH_KEY_LENGTH);
    return new BAuthenticationKey(aKey);
  }

  /**
   * Write the primitive in String format.
   */
  public String encodeToString()
    throws IOException
  {
    return toString();
  }

  /**
   * Read the primitive from String format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    return new BAuthenticationKey(LonByteArrayUtil.getBytes(s,AUTH_KEY_LENGTH));
  }
  
  public static final int AUTH_KEY_LENGTH = 6;
  private byte[] authKey;    
}
