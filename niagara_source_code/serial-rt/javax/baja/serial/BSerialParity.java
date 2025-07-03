/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.serial;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BSerialParity represents the parity bit settings
 * for a comm port.
 * <p>
 *
 * @author    Scott Hoye
 * @creation  22 Mar 02
 * @version   $Revision: 5$ $Date: 3/31/04 11:52:52 AM EST$  
 * @since     Niagara 3.0 serial 1.0
 */

@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "none", ordinal = 0),
    @Range(value = "odd", ordinal = 1),
    @Range(value = "even", ordinal = 2),
    @Range(value = "mark", ordinal = 3),
    @Range(value = "space", ordinal = 4)
  }
)
public final class BSerialParity
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.serial.BSerialParity(1917833996)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for none. */
  public static final int NONE = 0;
  /** Ordinal value for odd. */
  public static final int ODD = 1;
  /** Ordinal value for even. */
  public static final int EVEN = 2;
  /** Ordinal value for mark. */
  public static final int MARK = 3;
  /** Ordinal value for space. */
  public static final int SPACE = 4;

  /** BSerialParity constant for none. */
  public static final BSerialParity none = new BSerialParity(NONE);
  /** BSerialParity constant for odd. */
  public static final BSerialParity odd = new BSerialParity(ODD);
  /** BSerialParity constant for even. */
  public static final BSerialParity even = new BSerialParity(EVEN);
  /** BSerialParity constant for mark. */
  public static final BSerialParity mark = new BSerialParity(MARK);
  /** BSerialParity constant for space. */
  public static final BSerialParity space = new BSerialParity(SPACE);

  /** Factory method with ordinal. */
  public static BSerialParity make(int ordinal)
  {
    return (BSerialParity)none.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BSerialParity make(String tag)
  {
    return (BSerialParity)none.getRange().get(tag);
  }

  /** Private constructor. */
  private BSerialParity(int ordinal)
  {
    super(ordinal);
  }

  public static final BSerialParity DEFAULT = none;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSerialParity.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
