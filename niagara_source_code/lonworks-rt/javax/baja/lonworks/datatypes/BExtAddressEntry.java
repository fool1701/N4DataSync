/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.lonworks.enums.BAddressType;
import javax.baja.lonworks.enums.BLonReceiveTimer;
import javax.baja.lonworks.enums.BLonRepeatTimer;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.lonworks.datatypes.BLinkDescriptor;
import com.tridium.lonworks.util.LonByteArrayUtil;
import com.tridium.lonworks.util.NmUtil;

/**
 * BExtAddressEntry contains the data in a single address entry in a lonworks
 * device address table. See CEA-709.1-B A.29 (p.387)
 * <p>
 * @author    Robert Adams
 * @creation  12 Dec 06
 * @version   $Revision: 3$ $Date: 9/28/01 10:20:36 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BExtAddressEntry
  extends BSimple
  implements BIAddressEntry
{
  /** Default BExtAddressEntry. */
  public static final BExtAddressEntry DEFAULT = new BExtAddressEntry();

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BExtAddressEntry(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:20 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BExtAddressEntry.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Factory method for creating address entry 
   * from a byte array.
   */
  public static BExtAddressEntry make(byte[] b)
  {
    return new BExtAddressEntry(b,0);
  }
  
  @Deprecated
  public static BExtAddressEntry make( BAddressType addressType  ,
                         int  size         ,
                         int  groupOrSubnet,
                         int  memberOrNode,
                         int  descriptor  )
  {
    return BExtAddressEntry.make(addressType,size,groupOrSubnet,memberOrNode,descriptor,0);
  }

  @Deprecated
  public static BExtAddressEntry make( BAddressType addressType  ,
                         int  size         ,
                         int  groupOrSubnet,
                         int  memberOrNode,
                         int  descriptor  ,
                         int  domain)
  {
    return BExtAddressEntry.make(addressType,size,groupOrSubnet,memberOrNode,descriptor,domain,
                                 new BLinkDescriptor());
  }
  
  /**
   * Create a BAddressEntry with the specified elements.
   */
  public static BExtAddressEntry make( BAddressType addressType  ,
                         int  size         ,
                         int  groupOrSubnet,
                         int  memberOrNode,
                         int  descriptor ,
                         int  domain,
                         BLinkDescriptor desc)
  {
    return new BExtAddressEntry(addressType,size,groupOrSubnet,memberOrNode,descriptor,domain,
                             desc.getRepeatTimer(), desc.getRetries(), desc.getReceiveTimer(), desc.getTransmitTimer());
  }
  
  public static BExtAddressEntry make( BAddressType addressType  ,
                         int  size         ,
                         int  groupOrSubnet,
                         int  memberOrNode,
                         int  descriptor ,
                         int  domain,
                         BLonRepeatTimer rptTmr,
                         int retries,
                         BLonReceiveTimer rcvTmr,
                         BLonRepeatTimer txTmr)
  {
    return new BExtAddressEntry(addressType,size,groupOrSubnet,memberOrNode,descriptor,domain,
                             rptTmr, retries, rcvTmr, txTmr);
  }

  @Deprecated
  public static BExtAddressEntry makeSubnetNodeEntry(BSubnetNode sn, int  descriptor)
  {
    return make(BAddressType.subnetNode, 0, sn.getSubnetId(), sn.getNodeId(), descriptor);
  }
  
  public static BExtAddressEntry makeSubnetNodeEntry(BSubnetNode sn, int  descriptor, int domainNdx, BLinkDescriptor desc)
  {
    return make(BAddressType.subnetNode, 0, sn.getSubnetId(), sn.getNodeId(), descriptor, domainNdx, desc);
  }
 
  public static BExtAddressEntry make(BIAddressEntry ie)
  {
    if(ie.isExtended()) return (BExtAddressEntry)ie;
    
    BAddressEntry ee = (BAddressEntry)ie;
    return new BExtAddressEntry(ee.getAddressType  (),
                             ee.getSize         (),
                             ee.getGroupOrSubnet(),
                             ee.getMemberOrNode (),
                             ee.getDescriptor   (),
                             ee.getDomain       (),
                             ee.getRepeatTimer  (), 
                             ee.getRetries      (),
                             ee.getReceiveTimer (),
                             ee.getTransmitTimer());
  }
  
  /**
   * Private constructor.
   */
  private BExtAddressEntry(byte[] a,  int  descriptor) 
  { 
    int len = a.length;
    if(len!=ADDRESS_ENTRY_LENGTH)
      throw new BajaRuntimeException("Invalid array length " + len + " in BExtAddressEntry make.");

    addressEntry = new byte[len];
    System.arraycopy(a,0,addressEntry,0,len);
    this.descriptor = descriptor;
  }
  
  private BExtAddressEntry() {} 
 
  /**
   * Create a BExtAddressEntry with the specified elements.
   */
  private BExtAddressEntry ( BAddressType addressType  ,
                         int  size         ,
                         int  groupOrSubnet,
                         int  memberOrNode,
                         int  descriptor  ,
                         int  domain,
                         BLonRepeatTimer rptTmr,
                         int retries,
                         BLonReceiveTimer rcvTmr,
                         BLonRepeatTimer txTmr )
  {
    setAddressType   (addressType);
    this.descriptor = descriptor;
    
    // If this is type turnaround or unused leave other fields zero.
    if(addressEntry[0]==0) return;
    
    if(addressType.equals(BAddressType.group)) setSize(size);
    setGroupOrSubnet (groupOrSubnet);
    setMemberOrNode  (memberOrNode);
    setDomain        (domain);
    
    setRepeatTimer(rptTmr);
    setRetries(retries);
    setReceiveTimer(rcvTmr);
    setTransmitTimer(txTmr);
  }
  
///////////////////////////////////////////////////////////
//  BIAddressEntry
///////////////////////////////////////////////////////////
  
  public BAddressType getAddressType()
  {
     if(addressEntry[0] == 0) 
       return (addressEntry[1]==0) ? BAddressType.none : BAddressType.turnaround;
     if(addressEntry[0] == 1)
       return BAddressType.subnetNode;
     if(addressEntry[0] == 3)
       return BAddressType.broadcast;
     return BAddressType.group;  
  }   
  public boolean isGroupAddress()      { return (addressEntry[0]& 0x080)!=0; }
  public boolean isSubnetNodeAddress() { return addressEntry[0] == 1; }
  public boolean isTurnAroundAddress() { return (addressEntry[0] == 0) &&  (addressEntry[1]!=0); }
  
  private void setAddressType(BAddressType addressType  )
  {
    switch (addressType.getOrdinal())
    {
      case BAddressType.GROUP:    
        addressEntry[0] |= 0x080; 
        break;       
      case BAddressType.SUBNET_NODE:       
        addressEntry[0] = 1;
        break;
      case BAddressType.BROADCAST:       
        addressEntry[0] = 3;
        break;
      case BAddressType.TURNAROUND:       
        addressEntry[0] = 0;
        addressEntry[1] = 1;
        break;
      default: // Not in use
        addressEntry[0] = 0;
        addressEntry[1] = 0;
        break;
     }
  }

  public int getSize() 
    { return addressEntry[0] & 0x07f; }   

  private void setSize(int size ) 
  {
    // Size can be from 2-64. 0 of unlimited size. Unlimited requires use of unackd/unackdRpt or
    if(size>64) size=0;
    addressEntry[0] = (byte)((size & 0x07f ) | (addressEntry[0] & 0x080)); 
  }   
  
  public int getGroupOrSubnet() 
    { return addressEntry[4] & 0x0ff ; }  
  private void setGroupOrSubnet (int groupOrSubnet) 
    { addressEntry[4] = (byte)groupOrSubnet; }  
    
  public int getMemberOrNode() 
    { return addressEntry[1] & 0x07f ; }  
  private void setMemberOrNode(int memberOrNode) 
    { addressEntry[1] = (byte)((memberOrNode & 0x07f ) | (addressEntry[1] & 0x080)); }  
    
  public int getDomain()
    { return (addressEntry[5]<<8) & addressEntry[6] ; }  
  private void setDomain(int dom)
  {  
    addressEntry[5] = (byte)((dom>>8) & 0x0ff);  
    addressEntry[6] = (byte)(dom & 0x0ff); 
  } 

  public BLonRepeatTimer getRepeatTimer(){ return BLonRepeatTimer.make((addressEntry[2] & 0x0f0)>>4); }
  private void setRepeatTimer(BLonRepeatTimer rptTmr) 
  { addressEntry[2] = (byte)((rptTmr.getOrdinal()<<4 & 0x0f0 ) | (addressEntry[2] & 0x0f)); }  

  public int getRetries(){ return addressEntry[2] & 0x0f; }
  private void setRetries(int retries) 
  { addressEntry[2] = (byte)((retries & 0x0f ) | (addressEntry[2] & 0x0f0)); }  

  public BLonReceiveTimer getReceiveTimer(){ return BLonReceiveTimer.make((addressEntry[3] & 0x0f0)>>4); }
  private void setReceiveTimer(BLonReceiveTimer rcvTmr) 
  { addressEntry[3] = (byte)((rcvTmr.getOrdinal()<<4 & 0x0f0 ) | (addressEntry[3] & 0x0f)); }  

  public BLonRepeatTimer getTransmitTimer(){ return BLonRepeatTimer.make(addressEntry[3] & 0x0f); }
  private void setTransmitTimer(BLonRepeatTimer txTmr) 
  { addressEntry[3] = (byte)((txTmr.getOrdinal() & 0x0f ) | (addressEntry[3] & 0x0f0)); }  
 
  public int getDescriptor() { return descriptor; }
   
  public boolean isExtended() { return true; }
  
  public BSubnetNode getSubnetNodeAddress()
  {
    return BSubnetNode.make(getGroupOrSubnet(), getMemberOrNode()); 
  }
  
  public byte[] getRawAddress() {return addressEntry; }
  
  /**
   * Test if the obj is equal in value to this BExtAddressEntry.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof BExtAddressEntry))
      return false;
    
    BExtAddressEntry comp = (BExtAddressEntry)obj;
    if(descriptor != comp.descriptor) return false;
    if(addressEntry.length != comp.addressEntry.length) return false;
    
    for (int i = 0; i < ADDRESS_ENTRY_LENGTH; i++)
    {
      if (addressEntry[i] != comp.addressEntry[i])  return false;
    }

    return true;
  }
  
  public boolean isSameAddress(BIAddressEntry iae)
  {
    return (getAddressType()      == iae.getAddressType()     ) &&
           (isGroupAddress()      == iae.isGroupAddress()     ) && 
           (isSubnetNodeAddress() == iae.isSubnetNodeAddress()) && 
           (isTurnAroundAddress() == iae.isTurnAroundAddress()) && 
           (getSize()             == iae.getSize()            ) && 
           (getGroupOrSubnet()    == iae.getGroupOrSubnet()   ) && 
           (getMemberOrNode()     == iae.getMemberOrNode()    ) && 
           (getDescriptor()       == iae.getDescriptor()      ) && 
           (getDomain()           == iae.getDomain()          ); 
  }
  
///////////////////////////////////////////////////////////
//  BSimple implementation
///////////////////////////////////////////////////////////
  /**
   *
   */
  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();
    if(addressEntry[0] == 0) 
    {
      if (addressEntry[1]==0) return "Not In Use";
      sb.append("Turnaround");
    }  
    else if(addressEntry[0] == 1)
    {
      sb.append("Subnet ").append(getGroupOrSubnet());
      sb.append(" Node ").append(getMemberOrNode());
    }
    else if(addressEntry[0] == 3)
    {
      sb.append("Broadcast on subnet ").append(getGroupOrSubnet());
    }  
    else
    {
      sb.append("Group# ").append(getGroupOrSubnet());
      sb.append(" Member# ").append(getMemberOrNode());
      sb.append(" size ").append(getSize());
    }
    sb.append(" Domain ").append(getDomain());
    
    sb.append(" ").append(NmUtil.getLinkType(descriptor));
    
    // for debug, remove
    //sb.append(" ").append(LonByteArrayUtil.toString(addressEntry));
    
    return sb.toString();
  }
  
  /** */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeInt(descriptor);
    out.writeInt(ADDRESS_ENTRY_LENGTH);
    out.write(addressEntry);
  }
  
  /** */
  public BObject decode(DataInput in)
    throws IOException
  {
    int descriptor = in.readInt();
    int aLen = in.readInt();
    byte[] a = new byte[aLen];
    in.readFully(a, 0, aLen);
    return new BExtAddressEntry(a,descriptor);
  }

  /**
   * Write the primitive in String format.
   */
  public String encodeToString()
    throws IOException
  {
    return descriptor + "/" + addressEntry.length + "/" + LonByteArrayUtil.toString(addressEntry);
  }

  /**
   * Read the primitive from String format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  { 
    try{
      int pos = s.indexOf("/");
      int descr = Integer.parseInt(s.substring(0, pos));
      int pos2 = s.indexOf("/",pos+1);
      int len = Integer.parseInt(s.substring(pos+1, pos2));
      byte[] a = LonByteArrayUtil.getBytes(s.substring(pos2 + 1),len);
      return new BExtAddressEntry(a,descr);
    }
    catch(Throwable e)
    { 
      System.out.println("FORMATE ERROR {" + s + "}\n" + e);
      return  DEFAULT; 
    }
  }
  

  /**
   *  Write BExtAddressEntry byte representation to the specified
   *  output stream.
   */
  public void toOutputStream(LonOutputStream out)
  {
    out.writeByteArray(addressEntry);
  }
  
//  /**
//   *  Create a BExtAddressEntry from the byte representation in the specified
//   *  input stream.
//   */
//  public BILonNetworkSimple fromInputStream(LonInputStream in)
//  { return new BExtAddressEntry(in); }
//
//  /**
//   *  Create a BExtAddressEntry from the data in the specified
//   *  input stream.
//   */
//  private BExtAddressEntry(LonInputStream in)
//  {
//    for(int i=0 ; i<ADDRESS_ENTRY_LENGTH ; i++)
//      addressEntry[i] = (byte)in.read();
//  }
//
//
//  /** Get the network byte length.  */
//  public int getNetLength() { return ADDRESS_ENTRY_LENGTH; }

  private static final int ADDRESS_ENTRY_LENGTH = 7;

///////////////////////////////////////////////////////////
//  Attributes
///////////////////////////////////////////////////////////

  private byte[]  addressEntry = {0,0,3,6,0,0,0};    
  private int     descriptor = 0;
}
