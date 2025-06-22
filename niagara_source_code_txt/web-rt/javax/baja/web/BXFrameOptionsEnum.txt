/**
 * Copyright 2011 - Tridium Inc, All Rights Reserved.
 */

package javax.baja.web;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BXFrameOptionsEnum is a list of the supported options for the xframe options header
 * 
 * @author    $Bill Smith
 * @creation  Nov 14, 2014
 * @version   $Revision$ $Date$
 * @since     Niagara 3.7
 */

@NiagaraType
@NiagaraEnum(
  range = {
    @Range("deny"),
    @Range("sameorigin"),
    @Range("any")
  },
  defaultValue = "sameorigin"
)
public final class BXFrameOptionsEnum
    extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BXFrameOptionsEnum(3303438484)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for deny. */
  public static final int DENY = 0;
  /** Ordinal value for sameorigin. */
  public static final int SAMEORIGIN = 1;
  /** Ordinal value for any. */
  public static final int ANY = 2;

  /** BXFrameOptionsEnum constant for deny. */
  public static final BXFrameOptionsEnum deny = new BXFrameOptionsEnum(DENY);
  /** BXFrameOptionsEnum constant for sameorigin. */
  public static final BXFrameOptionsEnum sameorigin = new BXFrameOptionsEnum(SAMEORIGIN);
  /** BXFrameOptionsEnum constant for any. */
  public static final BXFrameOptionsEnum any = new BXFrameOptionsEnum(ANY);

  /** Factory method with ordinal. */
  public static BXFrameOptionsEnum make(int ordinal)
  {
    return (BXFrameOptionsEnum)deny.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BXFrameOptionsEnum make(String tag)
  {
    return (BXFrameOptionsEnum)deny.getRange().get(tag);
  }

  /** Private constructor. */
  private BXFrameOptionsEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BXFrameOptionsEnum DEFAULT = sameorigin;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BXFrameOptionsEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
