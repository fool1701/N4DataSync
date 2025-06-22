/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.px;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * Represents the status of a layer
 * 
 * @author    Mike Jarmy
 * @creation  26 Jul 09
 * @version   $Revision: 1$ $Date: 9/10/09 2:02:31 PM EDT$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("normal"),
    @Range("locked"),
    @Range("invisible")
  }
)
public final class BLayerStatus
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.px.BLayerStatus(1906819179)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for normal. */
  public static final int NORMAL = 0;
  /** Ordinal value for locked. */
  public static final int LOCKED = 1;
  /** Ordinal value for invisible. */
  public static final int INVISIBLE = 2;

  /** BLayerStatus constant for normal. */
  public static final BLayerStatus normal = new BLayerStatus(NORMAL);
  /** BLayerStatus constant for locked. */
  public static final BLayerStatus locked = new BLayerStatus(LOCKED);
  /** BLayerStatus constant for invisible. */
  public static final BLayerStatus invisible = new BLayerStatus(INVISIBLE);

  /** Factory method with ordinal. */
  public static BLayerStatus make(int ordinal)
  {
    return (BLayerStatus)normal.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLayerStatus make(String tag)
  {
    return (BLayerStatus)normal.getRange().get(tag);
  }

  /** Private constructor. */
  private BLayerStatus(int ordinal)
  {
    super(ordinal);
  }

  public static final BLayerStatus DEFAULT = normal;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLayerStatus.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
