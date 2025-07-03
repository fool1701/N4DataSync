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
 * The BUiAiTypeEnum class provides enumeration of Universal Analog Input types
 *
 * @author    Andy Saunders        
 * @creation  20 June 05
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 * @since     Niagara 3.0 andi 1.0     
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("ai_Thermister"),
    @Range("ai_Platimum"),
    @Range("ai_0to10_vdc"),
    @Range("ai_4to20_ma")
  }
)
public final class BUiAiTypeEnum
  extends BFrozenEnum  
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.enums.BUiAiTypeEnum(1393453279)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for ai_Thermister. */
  public static final int AI_THERMISTER = 0;
  /** Ordinal value for ai_Platimum. */
  public static final int AI_PLATIMUM = 1;
  /** Ordinal value for ai_0to10_vdc. */
  public static final int AI_0TO_10_VDC = 2;
  /** Ordinal value for ai_4to20_ma. */
  public static final int AI_4TO_20_MA = 3;

  /** BUiAiTypeEnum constant for ai_Thermister. */
  public static final BUiAiTypeEnum ai_Thermister = new BUiAiTypeEnum(AI_THERMISTER);
  /** BUiAiTypeEnum constant for ai_Platimum. */
  public static final BUiAiTypeEnum ai_Platimum = new BUiAiTypeEnum(AI_PLATIMUM);
  /** BUiAiTypeEnum constant for ai_0to10_vdc. */
  public static final BUiAiTypeEnum ai_0to10_vdc = new BUiAiTypeEnum(AI_0TO_10_VDC);
  /** BUiAiTypeEnum constant for ai_4to20_ma. */
  public static final BUiAiTypeEnum ai_4to20_ma = new BUiAiTypeEnum(AI_4TO_20_MA);

  /** Factory method with ordinal. */
  public static BUiAiTypeEnum make(int ordinal)
  {
    return (BUiAiTypeEnum)ai_Thermister.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BUiAiTypeEnum make(String tag)
  {
    return (BUiAiTypeEnum)ai_Thermister.getRange().get(tag);
  }

  /** Private constructor. */
  private BUiAiTypeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BUiAiTypeEnum DEFAULT = ai_Thermister;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUiAiTypeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
