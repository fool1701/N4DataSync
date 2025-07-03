/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.point;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BReadWriteMode indicates readonly, read-write, or writeonly capability.
 *
 * @author    Brian Frank       
 * @creation  22 Jun 04
 * @version   $Revision: 1$ $Date: 6/24/04 2:24:45 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("readonly"),
    @Range("readWrite"),
    @Range("writeonly")
  }
)
public final class BReadWriteMode
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.point.BReadWriteMode(3915819417)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for readonly. */
  public static final int READONLY = 0;
  /** Ordinal value for readWrite. */
  public static final int READ_WRITE = 1;
  /** Ordinal value for writeonly. */
  public static final int WRITEONLY = 2;

  /** BReadWriteMode constant for readonly. */
  public static final BReadWriteMode readonly = new BReadWriteMode(READONLY);
  /** BReadWriteMode constant for readWrite. */
  public static final BReadWriteMode readWrite = new BReadWriteMode(READ_WRITE);
  /** BReadWriteMode constant for writeonly. */
  public static final BReadWriteMode writeonly = new BReadWriteMode(WRITEONLY);

  /** Factory method with ordinal. */
  public static BReadWriteMode make(int ordinal)
  {
    return (BReadWriteMode)readonly.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BReadWriteMode make(String tag)
  {
    return (BReadWriteMode)readonly.getRange().get(tag);
  }

  /** Private constructor. */
  private BReadWriteMode(int ordinal)
  {
    super(ordinal);
  }

  public static final BReadWriteMode DEFAULT = readonly;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BReadWriteMode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Return if readonly.
   */
  public boolean isReadonly()
  {
    return this == readonly;
  }
  
  /**
   * Return if readonly or readWrite.
   */
  public boolean isRead()
  {
    return this == readonly || this == readWrite;
  }

  /**
   * Return if readWrite or writeonly.
   */
  public boolean isWrite()
  {
    return this == readWrite || this == writeonly;
  }

  /**
   * Return if writeonly.
   */
  public boolean isWriteonly()
  {
    return this == writeonly;
  }

  public String getDisplayTag(Context cx)
  {           
    switch(getOrdinal())
    {
      case READONLY:   return "RO";
      case READ_WRITE: return "RW";
      case WRITEONLY:  return "WO";
    }                              
    throw new IllegalStateException();
  }
}
