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
 * The BLonGroupRestrictionEnum represents the group_restriction enum from
 * CEA-709.1-B (p.79).  It is used in Expanded Address Table entries to expanded
 * the allowed behaviour of address entries. 
 *
 * @author    Robert Adams
 * @creation  13 Dec 06
 * @version   $Revision: 1$ $Date: 8/9/01 2:22:40 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "grpNormal", ordinal = 0),
    @Range(value = "grpOutputOnly", ordinal = 1),
    @Range(value = "grpInputNoAck", ordinal = 2)
  }
)
public final class BLonGroupRestrictionEnum
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonGroupRestrictionEnum(2352325768)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for grpNormal. */
  public static final int GRP_NORMAL = 0;
  /** Ordinal value for grpOutputOnly. */
  public static final int GRP_OUTPUT_ONLY = 1;
  /** Ordinal value for grpInputNoAck. */
  public static final int GRP_INPUT_NO_ACK = 2;

  /** BLonGroupRestrictionEnum constant for grpNormal. */
  public static final BLonGroupRestrictionEnum grpNormal = new BLonGroupRestrictionEnum(GRP_NORMAL);
  /** BLonGroupRestrictionEnum constant for grpOutputOnly. */
  public static final BLonGroupRestrictionEnum grpOutputOnly = new BLonGroupRestrictionEnum(GRP_OUTPUT_ONLY);
  /** BLonGroupRestrictionEnum constant for grpInputNoAck. */
  public static final BLonGroupRestrictionEnum grpInputNoAck = new BLonGroupRestrictionEnum(GRP_INPUT_NO_ACK);

  /** Factory method with ordinal. */
  public static BLonGroupRestrictionEnum make(int ordinal)
  {
    return (BLonGroupRestrictionEnum)grpNormal.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonGroupRestrictionEnum make(String tag)
  {
    return (BLonGroupRestrictionEnum)grpNormal.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonGroupRestrictionEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonGroupRestrictionEnum DEFAULT = grpNormal;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonGroupRestrictionEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
