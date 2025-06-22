/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BTotalizationInterval is a BEnum specifying totalization
 * intervals.
 *
 * @author    Dan Giorgis
 * @creation  9 Nov 00
 * @version   $Revision: 11$ $Date: 3/23/05 11:37:10 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("minutely"),
    @Range("hourly")
  }
)
public final class BTotalizationInterval
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.enums.BTotalizationInterval(1328899602)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for minutely. */
  public static final int MINUTELY = 0;
  /** Ordinal value for hourly. */
  public static final int HOURLY = 1;

  /** BTotalizationInterval constant for minutely. */
  public static final BTotalizationInterval minutely = new BTotalizationInterval(MINUTELY);
  /** BTotalizationInterval constant for hourly. */
  public static final BTotalizationInterval hourly = new BTotalizationInterval(HOURLY);

  /** Factory method with ordinal. */
  public static BTotalizationInterval make(int ordinal)
  {
    return (BTotalizationInterval)minutely.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BTotalizationInterval make(String tag)
  {
    return (BTotalizationInterval)minutely.getRange().get(tag);
  }

  /** Private constructor. */
  private BTotalizationInterval(int ordinal)
  {
    super(ordinal);
  }

  public static final BTotalizationInterval DEFAULT = minutely;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTotalizationInterval.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
