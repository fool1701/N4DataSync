/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BValign defines an enumeration for vertical 
 * justification: top, center, bottom, or fill
 *
 * @author    Brian Frank
 * @creation  1 Dec 00
 * @version   $Revision: 4$ $Date: 3/23/05 11:29:07 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("top"),
    @Range("center"),
    @Range("bottom"),
    @Range("fill")
  }
)
public final class BValign
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.enums.BValign(3128683453)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for top. */
  public static final int TOP = 0;
  /** Ordinal value for center. */
  public static final int CENTER = 1;
  /** Ordinal value for bottom. */
  public static final int BOTTOM = 2;
  /** Ordinal value for fill. */
  public static final int FILL = 3;

  /** BValign constant for top. */
  public static final BValign top = new BValign(TOP);
  /** BValign constant for center. */
  public static final BValign center = new BValign(CENTER);
  /** BValign constant for bottom. */
  public static final BValign bottom = new BValign(BOTTOM);
  /** BValign constant for fill. */
  public static final BValign fill = new BValign(FILL);

  /** Factory method with ordinal. */
  public static BValign make(int ordinal)
  {
    return (BValign)top.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BValign make(String tag)
  {
    return (BValign)top.getRange().get(tag);
  }

  /** Private constructor. */
  private BValign(int ordinal)
  {
    super(ordinal);
  }

  public static final BValign DEFAULT = top;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BValign.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
}
