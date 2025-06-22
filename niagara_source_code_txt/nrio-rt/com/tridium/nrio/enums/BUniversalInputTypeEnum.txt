/**
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BUniversalInputTypeEnum class provides enumeration of ANDI
 * file mode values
 *
 * @author    Andy Saunders        
 * @creation  20 June 05
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 * @since     Niagara 3.0 andi 1.0     
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "undefined", ordinal = 0),
    @Range(value = "di_Normal", ordinal = 1),
    @Range(value = "di_HighSpeed", ordinal = 2),
    @Range(value = "ai_Resistive", ordinal = 3),
    @Range(value = "ai_0to10_vdc", ordinal = 5)
  }
)
public final class BUniversalInputTypeEnum
  extends BFrozenEnum  
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.enums.BUniversalInputTypeEnum(854598327)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for undefined. */
  public static final int UNDEFINED = 0;
  /** Ordinal value for di_Normal. */
  public static final int DI_NORMAL = 1;
  /** Ordinal value for di_HighSpeed. */
  public static final int DI_HIGH_SPEED = 2;
  /** Ordinal value for ai_Resistive. */
  public static final int AI_RESISTIVE = 3;
  /** Ordinal value for ai_0to10_vdc. */
  public static final int AI_0TO_10_VDC = 5;

  /** BUniversalInputTypeEnum constant for undefined. */
  public static final BUniversalInputTypeEnum undefined = new BUniversalInputTypeEnum(UNDEFINED);
  /** BUniversalInputTypeEnum constant for di_Normal. */
  public static final BUniversalInputTypeEnum di_Normal = new BUniversalInputTypeEnum(DI_NORMAL);
  /** BUniversalInputTypeEnum constant for di_HighSpeed. */
  public static final BUniversalInputTypeEnum di_HighSpeed = new BUniversalInputTypeEnum(DI_HIGH_SPEED);
  /** BUniversalInputTypeEnum constant for ai_Resistive. */
  public static final BUniversalInputTypeEnum ai_Resistive = new BUniversalInputTypeEnum(AI_RESISTIVE);
  /** BUniversalInputTypeEnum constant for ai_0to10_vdc. */
  public static final BUniversalInputTypeEnum ai_0to10_vdc = new BUniversalInputTypeEnum(AI_0TO_10_VDC);

  /** Factory method with ordinal. */
  public static BUniversalInputTypeEnum make(int ordinal)
  {
    return (BUniversalInputTypeEnum)undefined.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BUniversalInputTypeEnum make(String tag)
  {
    return (BUniversalInputTypeEnum)undefined.getRange().get(tag);
  }

  /** Private constructor. */
  private BUniversalInputTypeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BUniversalInputTypeEnum DEFAULT = undefined;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUniversalInputTypeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
