/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BLonNodeState provides enumeration for the state of
 * a lonworks device as defined in Neuron Chip Data Book 
 * Appendix B.1.6.
 * <p>
 * @author    Robert Adams
 * @creation  14 Dec 00
 * @version   $Revision: 2$ $Date: 9/18/01 9:49:48 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("unknown"),
    @Range("unconfigured"),
    @Range("configOnline"),
    @Range("configOffline"),
    @Range("applicationless"),
    @Range("hardOffline")
  }
)
public final class BLonNodeState
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonNodeState(1397620614)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for unknown. */
  public static final int UNKNOWN = 0;
  /** Ordinal value for unconfigured. */
  public static final int UNCONFIGURED = 1;
  /** Ordinal value for configOnline. */
  public static final int CONFIG_ONLINE = 2;
  /** Ordinal value for configOffline. */
  public static final int CONFIG_OFFLINE = 3;
  /** Ordinal value for applicationless. */
  public static final int APPLICATIONLESS = 4;
  /** Ordinal value for hardOffline. */
  public static final int HARD_OFFLINE = 5;

  /** BLonNodeState constant for unknown. */
  public static final BLonNodeState unknown = new BLonNodeState(UNKNOWN);
  /** BLonNodeState constant for unconfigured. */
  public static final BLonNodeState unconfigured = new BLonNodeState(UNCONFIGURED);
  /** BLonNodeState constant for configOnline. */
  public static final BLonNodeState configOnline = new BLonNodeState(CONFIG_ONLINE);
  /** BLonNodeState constant for configOffline. */
  public static final BLonNodeState configOffline = new BLonNodeState(CONFIG_OFFLINE);
  /** BLonNodeState constant for applicationless. */
  public static final BLonNodeState applicationless = new BLonNodeState(APPLICATIONLESS);
  /** BLonNodeState constant for hardOffline. */
  public static final BLonNodeState hardOffline = new BLonNodeState(HARD_OFFLINE);

  /** Factory method with ordinal. */
  public static BLonNodeState make(int ordinal)
  {
    return (BLonNodeState)unknown.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonNodeState make(String tag)
  {
    return (BLonNodeState)unknown.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonNodeState(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonNodeState DEFAULT = unknown;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonNodeState.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /** Is this a configured state - configOnline or configOffline */
  public boolean isConfigured() 
    { return getOrdinal()==CONFIG_ONLINE || getOrdinal()==CONFIG_OFFLINE; }

  
}
