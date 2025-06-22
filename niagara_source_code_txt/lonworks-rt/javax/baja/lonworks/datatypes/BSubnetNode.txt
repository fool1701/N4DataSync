/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.util.IntHashMap;
import javax.baja.sys.*;

/**
 *   BSubnetNode encapsulates the subnet node address of
 * a LonWorks device.
 *
 * @author    Robert Adams
 * @creation  14 Dec 00
 * @version   $Revision: 1$ $Date: 9/12/01 2:04:39 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BSubnetNode
  extends BSimple
  implements LonAddress
{
  public static final BSubnetNode DEFAULT = new BSubnetNode(0,0);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BSubnetNode(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSubnetNode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Factory method for creating BSubnetNode
   * from subnet and node ids.
   */
  public static BSubnetNode make(int subnetId, int nodeId)
  {
    BSubnetNode sn;
    synchronized(cache)
    {
      int hash = (subnetId<<8) | nodeId;
      sn = (BSubnetNode)cache.get(hash);
      if(sn==null)
      {
        sn = new BSubnetNode(subnetId,nodeId);
        cache.put(hash,sn);
      }
    }
    return sn;
  }
  static IntHashMap cache = new IntHashMap();

  public BSubnetNode makeFrom(int subnetId, int nodeId)
  {
    return make(subnetId,nodeId);
  }

  /**
   * Private constructor.
   */
  private BSubnetNode(int subnetId, int nodeId)
  {
    if(subnetId<0 || subnetId>255 || nodeId<0 || nodeId>127)
      throw new BajaRuntimeException("Invalid subnet/node " + subnetId + "/" + nodeId + ". Valid values are subnet 1-255 node 1-127.");
    this.subnetId = subnetId;
    this.nodeId = nodeId;
  }

  public int getSubnetId () { return subnetId ; }
  public int getNodeId   () { return nodeId   ; }

  /**
   * BSubnetNode hash code {@code SUBNET_NODE<<24 | (subnetId<<8) | nodeId}
   */
  public int hashCode()
  {
    return SUBNET_NODE<<24 | (subnetId<<8) | nodeId;
  }

  /**
   * Test if the obj is equal in value to this BSubnetNode.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof BSubnetNode))
      return false;

    BSubnetNode comp = (BSubnetNode)obj;

    return compare(comp.subnetId, comp.nodeId);
  }

  private boolean compare(int subnet, int node)
  {
    return ( (subnetId == subnet) &&
             (nodeId == node) );
  }

  /**
   *
   */
  public String toString(Context context)
  {
    return(subnetId + "/" + nodeId);
  }

  /**
   *
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeInt    ( subnetId );
    out.writeInt    ( nodeId   );
  }

  /**
   *
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return makeFrom(in.readInt(),in.readInt());
  }

  /**
   * Write the primitive in String format.
   */
  public String encodeToString()
    throws IOException
  {
    return subnetId + "/" + nodeId;
  }

  /**
   * Read the primitive from String format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    int pos = s.indexOf("/");
    int subnet = Integer.parseInt(s.substring(0, pos));
    int node = Integer.parseInt(s.substring(pos + 1));

    return makeFrom(subnet, node );
  }

  private int subnetId;
  private int nodeId;

/////////////////////////////////////////////////
// LonAddress  api
/////////////////////////////////////////////////

  /** Return address type SUBNET_NODE */
  public int getAddressType() { return SUBNET_NODE; }

}
