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
 * The BSdiEnum class provides enumeration of ANDI
 * file mode values
 *
 * @author    Andy Saunders        
 * @creation  20 June 05
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 * @since     Niagara 3.0 andi 1.0     
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("closed"),
    @Range("shorted"),
    @Range("open"),
    @Range("cut")
  }
)
public final class BSdiEnum
  extends BFrozenEnum  
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.enums.BSdiEnum(1501809860)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for closed. */
  public static final int CLOSED = 0;
  /** Ordinal value for shorted. */
  public static final int SHORTED = 1;
  /** Ordinal value for open. */
  public static final int OPEN = 2;
  /** Ordinal value for cut. */
  public static final int CUT = 3;

  /** BSdiEnum constant for closed. */
  public static final BSdiEnum closed = new BSdiEnum(CLOSED);
  /** BSdiEnum constant for shorted. */
  public static final BSdiEnum shorted = new BSdiEnum(SHORTED);
  /** BSdiEnum constant for open. */
  public static final BSdiEnum open = new BSdiEnum(OPEN);
  /** BSdiEnum constant for cut. */
  public static final BSdiEnum cut = new BSdiEnum(CUT);

  /** Factory method with ordinal. */
  public static BSdiEnum make(int ordinal)
  {
    return (BSdiEnum)closed.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BSdiEnum make(String tag)
  {
    return (BSdiEnum)closed.getRange().get(tag);
  }

  /** Private constructor. */
  private BSdiEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BSdiEnum DEFAULT = closed;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSdiEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public boolean isActive()
  {
    return getOrdinal() == CLOSED;
  }



}
