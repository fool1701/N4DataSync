/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BStorageType specifies the storage mechanism for the records in
 * a BHistory.
 *
 * @author    John Sublett
 * @creation  2 July 2002
 * @version   $Revision: 4$ $Date: 3/31/04 11:50:48 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("file")
  }
)
public final class BStorageType
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BStorageType(2321218931)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for file. */
  public static final int FILE = 0;

  /** BStorageType constant for file. */
  public static final BStorageType file = new BStorageType(FILE);

  /** Factory method with ordinal. */
  public static BStorageType make(int ordinal)
  {
    return (BStorageType)file.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BStorageType make(String tag)
  {
    return (BStorageType)file.getRange().get(tag);
  }

  /** Private constructor. */
  private BStorageType(int ordinal)
  {
    super(ordinal);
  }

  public static final BStorageType DEFAULT = file;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStorageType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
