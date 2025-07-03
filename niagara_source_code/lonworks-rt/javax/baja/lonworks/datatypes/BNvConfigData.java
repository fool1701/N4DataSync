/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import javax.baja.lonworks.enums.BLonNvDirection;
import javax.baja.lonworks.enums.BLonServiceType;
import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BStruct;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 *  This class file represents the network variable data 
 *  described in Appendix A.4.1: Neuron Chip Data Structures 
 *  in the Neuron Chip Data Book.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  8 Jan 01
 * @version   $Revision: 2$ $Date: 9/18/01 9:50:15 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Flag to indicate if nv uses priority messaging.
 */
@NiagaraProperty(
  name = "priority",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY
)
/*
 Flag to indicate if nv is input or output.
 */
@NiagaraProperty(
  name = "direction",
  type = "BLonNvDirection",
  defaultValue = "BLonNvDirection.input",
  flags = Flags.READONLY
)
/*
 Selector used to bind nvs.
 */
@NiagaraProperty(
  name = "selector",
  type = "int",
  defaultValue = "-1",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY,
  facets = @Facet("BFacets.make(BFacets.RADIX,BInteger.make(16))")
)
/*
 Flag to indicate if this is a turnaround nv.
 */
@NiagaraProperty(
  name = "turnAround",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY
)
/*
 Service type used for updates if nv is bound.
 */
@NiagaraProperty(
  name = "serviceType",
  type = "BLonServiceType",
  defaultValue = "BLonServiceType.unacked",
  flags = Flags.READONLY
)
/*
 Flag to indicate if this nv will use authenticated
 updates if it is bound.
 */
@NiagaraProperty(
  name = "authenticated",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY
)
/*
 Index in address table for implicit addressing of
 nv updates. Valid values of 0-14 if configured
 or -1 if not configured. In devices supporting extended
 address table valid values are 0 - 65534
 */
@NiagaraProperty(
  name = "addrIndex",
  type = "int",
  defaultValue = "DEFAULT_ADDR_INDEX",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY
)
public class BNvConfigData
  extends BStruct
{  
  public static final int DEFAULT_ADDR_INDEX  = -1;
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BNvConfigData(1082977652)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "priority"

  /**
   * Slot for the {@code priority} property.
   * Flag to indicate if nv uses priority messaging.
   * @see #getPriority
   * @see #setPriority
   */
  public static final Property priority = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY, false, null);

  /**
   * Get the {@code priority} property.
   * Flag to indicate if nv uses priority messaging.
   * @see #priority
   */
  public boolean getPriority() { return getBoolean(priority); }

  /**
   * Set the {@code priority} property.
   * Flag to indicate if nv uses priority messaging.
   * @see #priority
   */
  public void setPriority(boolean v) { setBoolean(priority, v, null); }

  //endregion Property "priority"

  //region Property "direction"

  /**
   * Slot for the {@code direction} property.
   * Flag to indicate if nv is input or output.
   * @see #getDirection
   * @see #setDirection
   */
  public static final Property direction = newProperty(Flags.READONLY, BLonNvDirection.input, null);

  /**
   * Get the {@code direction} property.
   * Flag to indicate if nv is input or output.
   * @see #direction
   */
  public BLonNvDirection getDirection() { return (BLonNvDirection)get(direction); }

  /**
   * Set the {@code direction} property.
   * Flag to indicate if nv is input or output.
   * @see #direction
   */
  public void setDirection(BLonNvDirection v) { set(direction, v, null); }

  //endregion Property "direction"

  //region Property "selector"

  /**
   * Slot for the {@code selector} property.
   * Selector used to bind nvs.
   * @see #getSelector
   * @see #setSelector
   */
  public static final Property selector = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY, -1, BFacets.make(BFacets.RADIX,BInteger.make(16)));

  /**
   * Get the {@code selector} property.
   * Selector used to bind nvs.
   * @see #selector
   */
  public int getSelector() { return getInt(selector); }

  /**
   * Set the {@code selector} property.
   * Selector used to bind nvs.
   * @see #selector
   */
  public void setSelector(int v) { setInt(selector, v, null); }

  //endregion Property "selector"

  //region Property "turnAround"

  /**
   * Slot for the {@code turnAround} property.
   * Flag to indicate if this is a turnaround nv.
   * @see #getTurnAround
   * @see #setTurnAround
   */
  public static final Property turnAround = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY, false, null);

  /**
   * Get the {@code turnAround} property.
   * Flag to indicate if this is a turnaround nv.
   * @see #turnAround
   */
  public boolean getTurnAround() { return getBoolean(turnAround); }

  /**
   * Set the {@code turnAround} property.
   * Flag to indicate if this is a turnaround nv.
   * @see #turnAround
   */
  public void setTurnAround(boolean v) { setBoolean(turnAround, v, null); }

  //endregion Property "turnAround"

  //region Property "serviceType"

  /**
   * Slot for the {@code serviceType} property.
   * Service type used for updates if nv is bound.
   * @see #getServiceType
   * @see #setServiceType
   */
  public static final Property serviceType = newProperty(Flags.READONLY, BLonServiceType.unacked, null);

  /**
   * Get the {@code serviceType} property.
   * Service type used for updates if nv is bound.
   * @see #serviceType
   */
  public BLonServiceType getServiceType() { return (BLonServiceType)get(serviceType); }

  /**
   * Set the {@code serviceType} property.
   * Service type used for updates if nv is bound.
   * @see #serviceType
   */
  public void setServiceType(BLonServiceType v) { set(serviceType, v, null); }

  //endregion Property "serviceType"

  //region Property "authenticated"

  /**
   * Slot for the {@code authenticated} property.
   * Flag to indicate if this nv will use authenticated
   * updates if it is bound.
   * @see #getAuthenticated
   * @see #setAuthenticated
   */
  public static final Property authenticated = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY, false, null);

  /**
   * Get the {@code authenticated} property.
   * Flag to indicate if this nv will use authenticated
   * updates if it is bound.
   * @see #authenticated
   */
  public boolean getAuthenticated() { return getBoolean(authenticated); }

  /**
   * Set the {@code authenticated} property.
   * Flag to indicate if this nv will use authenticated
   * updates if it is bound.
   * @see #authenticated
   */
  public void setAuthenticated(boolean v) { setBoolean(authenticated, v, null); }

  //endregion Property "authenticated"

  //region Property "addrIndex"

  /**
   * Slot for the {@code addrIndex} property.
   * Index in address table for implicit addressing of
   * nv updates. Valid values of 0-14 if configured
   * or -1 if not configured. In devices supporting extended
   * address table valid values are 0 - 65534
   * @see #getAddrIndex
   * @see #setAddrIndex
   */
  public static final Property addrIndex = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY, DEFAULT_ADDR_INDEX, null);

  /**
   * Get the {@code addrIndex} property.
   * Index in address table for implicit addressing of
   * nv updates. Valid values of 0-14 if configured
   * or -1 if not configured. In devices supporting extended
   * address table valid values are 0 - 65534
   * @see #addrIndex
   */
  public int getAddrIndex() { return getInt(addrIndex); }

  /**
   * Set the {@code addrIndex} property.
   * Index in address table for implicit addressing of
   * nv updates. Valid values of 0-14 if configured
   * or -1 if not configured. In devices supporting extended
   * address table valid values are 0 - 65534
   * @see #addrIndex
   */
  public void setAddrIndex(int v) { setInt(addrIndex, v, null); }

  //endregion Property "addrIndex"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNvConfigData.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public boolean isAlias() { return false; }

  /**
   *  Write a network byte image of this NvConfig to a
   *  LonOutputStream.
   *  
   *  @param  out  data stream of message
   */
  public void writeNetworkBytes(LonOutputStream out)
  {
    int mrk = out.setBitFieldMark();
    
    out.writeBooleanBit(getPriority(),0,7,1);
    out.writeBooleanBit(isOutput(),0,6,1);
    
    int sel = getSelector();
    out.writeBit(sel>>8,0,0,6);
    out.writeUnsigned8(sel);

    out.writeBooleanBit(getTurnAround(),2,7,1);
    out.writeBit(getServiceType().getOrdinal(),2,5,2);
    out.writeBooleanBit(getAuthenticated(),2,4,1);
    
    int ndx = (getAddrIndex()==DEFAULT_ADDR_INDEX) ? UNUSED_ADDR_INDEX : getAddrIndex();
    out.writeBit(ndx,2,0,4);
    
    out.resetBitFieldMark(mrk);
  }

  /**
   *  Extract the Nv Config Response data from
   *  the LonInputStream 
   *  
   *  @param  in  data stream of message
   */
  public void fromInputStream(LonInputStream in)
  {
    int mrk = in.setBitFieldMark();

    setPriority       (in.readBooleanBit(0,7,1));
    
    BLonNvDirection dir = (in.readBooleanBit(0,6,1)) ? BLonNvDirection.output : BLonNvDirection.input;
    setDirection      (dir);
    
    int sel = (in.readBit(0,0,6)<<8) | in.readUnsigned8();
    setSelector       (sel);
    setTurnAround     (in.readBooleanBit(2,7,1));
    setServiceType    (BLonServiceType.make(in.readBit(2,5,2)));
    setAuthenticated  (in.readBooleanBit(2,4,1));
    
    int ndx = in.readBit(2,0,4);
    setAddrIndex      (ndx==UNUSED_ADDR_INDEX ? DEFAULT_ADDR_INDEX : ndx);    
  
    in.resetBitFieldMark(mrk);
    
  }


////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////  
                                                                         
//  private static final int PRIORITY_NET_IMAGE        = 0x80;
//  private static final int DIRECTION_NET_IMAGE       = 0x40;
//  private static final int TURNAROUND_NET_IMAGE      = 0x80;
//  private static final int SERVICE_TYPE_NET_IMAGE    = 0x60;
//  private static final int AUTH_NET_IMAGE            = 0x10;
//  private static final int ADDR_INDEX_NET_IMAGE      = 0x0F;
//
//  private static final int UPPER_SEL_MASK            = 0x3F;
//  private static final int LOWER_SEL_MASK            = 0xFF;
 
  /** Value of base unbound selector.  Unbound selector
   *  for specific nv is UNBOUND_NV_BASE_SELECTOR - nvIndex */
  public static final int UNBOUND_NV_BASE_SELECTOR = 0x3fff;

  public static final int MAX_BOUND_SELECTOR  = 0x02FFF;
 
  /** Default value for address index. */
  public static final int UNUSED_ADDR_INDEX     = 15;
  public static final int UNUSED_EXT_ADDR_INDEX = 65535;

////////////////////////////////////////////////////////////////
//  Overrides
////////////////////////////////////////////////////////////////  
  public boolean isInput()   { return getDirection() == BLonNvDirection.input; }
  public boolean isOutput()  { return getDirection() == BLonNvDirection.output; }
  public boolean isBoundNv() { return (getSelector() >= 0) && (getSelector() < MAX_BOUND_SELECTOR); }
  
  /**
   * Set nv to unbound state 
   */
  public void setUnbound(int nvIndex)
  {
    setSelector(UNBOUND_NV_BASE_SELECTOR - nvIndex);
    setTurnAround(false);
    setAddrIndex(DEFAULT_ADDR_INDEX); 
  }

  public String toString(Context c)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("sel:0x").append(Integer.toString(getSelector(),16));  
    sb.append(",").append(getServiceType());  
    sb.append(",adr:").append(getAddrIndex());  
    if(getDirection()==BLonNvDirection.input) sb.append(",in"); else sb.append(",out");  
    if(getTurnAround()) sb.append(",turn");  
    if(getPriority())sb.append(",pri");  
    if(getAuthenticated()) sb.append(",auth");  
    return sb.toString();
  }

  /**
   * Get serviceType suitable for nv writes. This will filter serviceType
   * to change BLonServiceType.request to BLonServiceType.acked.
   * @return one of BLonServiceType.acked,unacked,unackedRpt
   */
  public BLonServiceType getWriteServiceType()
  {
    return getServiceType().getWriteServiceType();
  }
  
}
