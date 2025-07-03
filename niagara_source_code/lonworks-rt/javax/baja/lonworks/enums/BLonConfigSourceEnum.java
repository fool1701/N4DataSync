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
 * The BLonConfigSourceEnum class provides enumeration for SNVT_config_src
 *
 * @author    Sean Morton
 * @creation  18 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:24 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "cfgLocal", ordinal = 0),
    @Range(value = "cfgExternal", ordinal = 1),
    @Range(value = "cfgNul", ordinal = -1)
  }
)
public final class BLonConfigSourceEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonConfigSourceEnum(1349773986)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for cfgLocal. */
  public static final int CFG_LOCAL = 0;
  /** Ordinal value for cfgExternal. */
  public static final int CFG_EXTERNAL = 1;
  /** Ordinal value for cfgNul. */
  public static final int CFG_NUL = -1;

  /** BLonConfigSourceEnum constant for cfgLocal. */
  public static final BLonConfigSourceEnum cfgLocal = new BLonConfigSourceEnum(CFG_LOCAL);
  /** BLonConfigSourceEnum constant for cfgExternal. */
  public static final BLonConfigSourceEnum cfgExternal = new BLonConfigSourceEnum(CFG_EXTERNAL);
  /** BLonConfigSourceEnum constant for cfgNul. */
  public static final BLonConfigSourceEnum cfgNul = new BLonConfigSourceEnum(CFG_NUL);

  /** Factory method with ordinal. */
  public static BLonConfigSourceEnum make(int ordinal)
  {
    return (BLonConfigSourceEnum)cfgLocal.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonConfigSourceEnum make(String tag)
  {
    return (BLonConfigSourceEnum)cfgLocal.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonConfigSourceEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonConfigSourceEnum DEFAULT = cfgLocal;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonConfigSourceEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
