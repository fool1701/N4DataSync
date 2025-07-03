/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BScrollBarPolicy determines when a BScrollPane displays
 * its horizontal or vertical scroll bars.
 *
 * @author    Brian Frank
 * @creation  26 Nov 00
 * @version   $Revision: 2$ $Date: 3/23/05 11:29:07 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("asNeeded"),
    @Range("always"),
    @Range("never")
  }
)
public final class BScrollBarPolicy
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.enums.BScrollBarPolicy(1683398002)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for asNeeded. */
  public static final int AS_NEEDED = 0;
  /** Ordinal value for always. */
  public static final int ALWAYS = 1;
  /** Ordinal value for never. */
  public static final int NEVER = 2;

  /** BScrollBarPolicy constant for asNeeded. */
  public static final BScrollBarPolicy asNeeded = new BScrollBarPolicy(AS_NEEDED);
  /** BScrollBarPolicy constant for always. */
  public static final BScrollBarPolicy always = new BScrollBarPolicy(ALWAYS);
  /** BScrollBarPolicy constant for never. */
  public static final BScrollBarPolicy never = new BScrollBarPolicy(NEVER);

  /** Factory method with ordinal. */
  public static BScrollBarPolicy make(int ordinal)
  {
    return (BScrollBarPolicy)asNeeded.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BScrollBarPolicy make(String tag)
  {
    return (BScrollBarPolicy)asNeeded.getRange().get(tag);
  }

  /** Private constructor. */
  private BScrollBarPolicy(int ordinal)
  {
    super(ordinal);
  }

  public static final BScrollBarPolicy DEFAULT = asNeeded;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BScrollBarPolicy.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
}
