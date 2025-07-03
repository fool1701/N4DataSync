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
 * The BLonNilEnum class is a dummy enum to service as a 
 * place holder in slots where the enum type is not known.
 *
 * @author    Robert Adams
 * @creation  6 April 04
 * @version   $Revision: 4$ $Date: 9/18/01 9:49:32 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("nil")
  }
)
public final class BLonNilEnum
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonNilEnum(133624514)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for nil. */
  public static final int NIL = 0;

  /** BLonNilEnum constant for nil. */
  public static final BLonNilEnum nil = new BLonNilEnum(NIL);

  /** Factory method with ordinal. */
  public static BLonNilEnum make(int ordinal)
  {
    return (BLonNilEnum)nil.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonNilEnum make(String tag)
  {
    return (BLonNilEnum)nil.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonNilEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonNilEnum DEFAULT = nil;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonNilEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
