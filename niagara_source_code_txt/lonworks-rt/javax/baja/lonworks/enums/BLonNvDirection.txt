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
 * BLonNvDirection provides enumeration of the direction of
 * a network variable (input or output).
 * <p>
 * @author    Robert Adams
 * @creation  09 Nov 00
 * @version   $Revision: 2$ $Date: 9/18/01 9:49:48 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("input"),
    @Range("output")
  }
)
public final class BLonNvDirection
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonNvDirection(2771513657)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for input. */
  public static final int INPUT = 0;
  /** Ordinal value for output. */
  public static final int OUTPUT = 1;

  /** BLonNvDirection constant for input. */
  public static final BLonNvDirection input = new BLonNvDirection(INPUT);
  /** BLonNvDirection constant for output. */
  public static final BLonNvDirection output = new BLonNvDirection(OUTPUT);

  /** Factory method with ordinal. */
  public static BLonNvDirection make(int ordinal)
  {
    return (BLonNvDirection)input.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonNvDirection make(String tag)
  {
    return (BLonNvDirection)input.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonNvDirection(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonNvDirection DEFAULT = input;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonNvDirection.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BLonNvDirection reverse()
  {
    return (this==input) ? output : input; 
  }
  

}
