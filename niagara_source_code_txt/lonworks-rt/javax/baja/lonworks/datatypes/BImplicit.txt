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

/**
 *   BImplicit encapsulates a LonWorks implicit address.
 * <p>
 * @author    Robert Adams
 * @creation  29 Oct 2003
 * @version   $Revision: 1$ $Date: 9/12/01 2:04:39 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BImplicit
  extends BSimple
  implements LonAddress
{
  public static final BImplicit DEFAULT = new BImplicit(0);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BImplicit(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:20 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BImplicit.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Factory method for creating BImplicit tag.
   */
  public static BImplicit make(int tag)
  {
    return new BImplicit(tag);
  }

  public BImplicit makeFrom(int tag)
  {
    if(this.tag == tag) return this;
    
    return new BImplicit(tag);
  }
  
  private BImplicit(int t) 
  {
    this.tag = t;
  }

  /** Get the address table index of this Implicit address. */
  public int getTag() { return tag ; }
    
  /**
   * BImplicit hash code {@code IMPLICIT<<24 | tag}
   */
  public int hashCode()
  {
    return IMPLICIT<<24 | tag;
  }

  /**
   * Test if the obj is equal in value to this BImplicit.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof BImplicit))
      return false;
        
    return ((BImplicit)obj).tag == tag;
 }
  
  /**
   * @return String for BImplicit value.
   */
  public String toString(Context context)
  {
    return "Implicit " + tag;
  }
  
  /**
   * Serialized BImplicit.
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeInt ( tag );
  }
  
  /**
   *  BImplicit is unserialized.
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return makeFrom(in.readInt());
  }

  /**
   * Write the primitive in String format.
   */
  public String encodeToString()
    throws IOException
  {
    return Integer.toString(tag);
  }

  /**
   * Read the primitive from String format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    int t = Integer.parseInt(s);
    if(t == tag) return this;
    return make(t);
  }

  private int tag;

/////////////////////////////////////////////////  
// LonAddress  api
/////////////////////////////////////////////////

  /** Return address type LOCAL */
  public int getAddressType() { return IMPLICIT; }
}
