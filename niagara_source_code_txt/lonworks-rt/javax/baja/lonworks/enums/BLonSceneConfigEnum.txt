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
 * The BLonSceneConfigEnum class provides enumeration for SNVT_scene_cfg
 *
 * @author    Sean Morton
 * @creation  19 Jul 01
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:37 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "scfSave", ordinal = 0),
    @Range(value = "scfClear", ordinal = 1),
    @Range(value = "scfReport", ordinal = 2),
    @Range(value = "scfSize", ordinal = 3),
    @Range(value = "scfFree", ordinal = 4),
    @Range(value = "scfNul", ordinal = -1)
  }
)
public final class BLonSceneConfigEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonSceneConfigEnum(1995012213)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for scfSave. */
  public static final int SCF_SAVE = 0;
  /** Ordinal value for scfClear. */
  public static final int SCF_CLEAR = 1;
  /** Ordinal value for scfReport. */
  public static final int SCF_REPORT = 2;
  /** Ordinal value for scfSize. */
  public static final int SCF_SIZE = 3;
  /** Ordinal value for scfFree. */
  public static final int SCF_FREE = 4;
  /** Ordinal value for scfNul. */
  public static final int SCF_NUL = -1;

  /** BLonSceneConfigEnum constant for scfSave. */
  public static final BLonSceneConfigEnum scfSave = new BLonSceneConfigEnum(SCF_SAVE);
  /** BLonSceneConfigEnum constant for scfClear. */
  public static final BLonSceneConfigEnum scfClear = new BLonSceneConfigEnum(SCF_CLEAR);
  /** BLonSceneConfigEnum constant for scfReport. */
  public static final BLonSceneConfigEnum scfReport = new BLonSceneConfigEnum(SCF_REPORT);
  /** BLonSceneConfigEnum constant for scfSize. */
  public static final BLonSceneConfigEnum scfSize = new BLonSceneConfigEnum(SCF_SIZE);
  /** BLonSceneConfigEnum constant for scfFree. */
  public static final BLonSceneConfigEnum scfFree = new BLonSceneConfigEnum(SCF_FREE);
  /** BLonSceneConfigEnum constant for scfNul. */
  public static final BLonSceneConfigEnum scfNul = new BLonSceneConfigEnum(SCF_NUL);

  /** Factory method with ordinal. */
  public static BLonSceneConfigEnum make(int ordinal)
  {
    return (BLonSceneConfigEnum)scfSave.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonSceneConfigEnum make(String tag)
  {
    return (BLonSceneConfigEnum)scfSave.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonSceneConfigEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonSceneConfigEnum DEFAULT = scfSave;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonSceneConfigEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
