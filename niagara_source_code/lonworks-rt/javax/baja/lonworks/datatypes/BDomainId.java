/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;

import com.tridium.lonworks.util.LonByteArrayUtil;

/**
 *  Domain Id as defined in Neuron Chip Data Book A.2.1.
 *
 * @author    Robert Adams
 * @creation  11 Feb 02
 * @version   $Revision: 1$ $Date: 9/12/01 2:04:40 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BDomainId
  extends BSimple
{
  public static final BDomainId DEFAULT = new BDomainId();

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BDomainId(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:20 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDomainId.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * Create a BDomainId with the given byte array of the specified length.
   */
  public static BDomainId make(int len, byte[] b) 
  {
    return new BDomainId(len, b);
  }
  
  public BDomainId makeFrom(int len, byte[] b) 
  {
    if(compare(len, b)) return this;
    return new BDomainId(len, b);
  }
  
  private BDomainId(int len, byte[] b) 
  {
    if(!isValidLength(len)) throw new RuntimeException("Invalid DomainId length.");
    this.domainLength = len;
    System.arraycopy(b,0,domainId,0,len);
  }

  private BDomainId() {}
  

////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////

  /** Get domainId byte array. */
  public byte[] getDomainId()
  {
    byte[] a = new byte[domainLength];
    System.arraycopy(domainId,0,a,0,domainLength);
    return a;
  }

  /** Get domainId length. */
  public int getLength()
    { return domainLength; }

  /** Is this the zero length domain? */
  public boolean isZeroLength()
   { return domainLength == 0; }
   
  /**
   * Get 6 byte domainId.
  public byte[] getByteArray() 
    { return domainId; }
   */

  /**
   * 
  public int hashCode()
  {
    return Float.floatToIntBits(value);
  }
   */  
   
  /**
   * Test if the obj is equal in value to this BDomainId.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof BDomainId))
      return false;
    
    BDomainId comp = (BDomainId)obj;
    return compare(comp.domainLength, comp.domainId);
  }
  
  private boolean compare(int len, byte[] b)
  {
    if(domainLength != len) return false;
    for (int i = 0; i < len; i++)
    {
      if (domainId[i] != b[i]) return false;
    }
    return true;
  }
  
  /**
   *
   */
  public String toString(Context context)
  {
    return "len=" + domainLength + ":" + LonByteArrayUtil.toString(domainId,'.',domainLength);
  }
  
  /**
   * Serialized).
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeInt(domainLength);
    for(int i=0 ; i<domainLength ; i++) out.writeInt(domainId[i]);
  }
  
  /**
   *  Deserialized .
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    int len = in.readInt();
    byte[] id = new byte[MAX_DOMAIN_LENGTH];
    for (int i = 0; i < len; i++)
    {
      id[i] = (byte)in.readInt();
    }
    return new BDomainId(len, id);
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
    if(s.length()==0)  return DEFAULT;
    
    String slen = s.substring(s.indexOf('=')+1, s.indexOf(':')).trim();
    int len = Integer.parseInt(slen);
    if(len==0)  return DEFAULT;
    if(!isValidLength(len)) throw new RuntimeException("Invalid DomainId length.");
    
    String id = s.substring(s.indexOf(':')+1);
    byte[] domId = LonByteArrayUtil.getBytes(id,len);
    return new BDomainId(len,domId);
  }
  
  private boolean isValidLength(int len)
  {
    switch(len) 
    {
      case 0: case 1: case 3: case 6: 
        return true;
    }
    return false;
  }
  
  public static final int MAX_DOMAIN_LENGTH = 6;

  private byte[] domainId = {0,0,0,0,0,0};    
  private int domainLength = 0;
}
