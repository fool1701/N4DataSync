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
 * The BLonConfigScope class specifies the scope of a ConfigParameter.
 *
 * @author    Robert Adams
 * @creation  12 Jan 01
 * @version   $Revision: 4$ $Date: 9/18/01 9:49:32 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("node"),
    @Range("object"),
    @Range("nv")
  }
)
public final class BLonConfigScope
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BLonConfigScope(1302308965)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for node. */
  public static final int NODE = 0;
  /** Ordinal value for object. */
  public static final int OBJECT = 1;
  /** Ordinal value for nv. */
  public static final int NV = 2;

  /** BLonConfigScope constant for node. */
  public static final BLonConfigScope node = new BLonConfigScope(NODE);
  /** BLonConfigScope constant for object. */
  public static final BLonConfigScope object = new BLonConfigScope(OBJECT);
  /** BLonConfigScope constant for nv. */
  public static final BLonConfigScope nv = new BLonConfigScope(NV);

  /** Factory method with ordinal. */
  public static BLonConfigScope make(int ordinal)
  {
    return (BLonConfigScope)node.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BLonConfigScope make(String tag)
  {
    return (BLonConfigScope)node.getRange().get(tag);
  }

  /** Private constructor. */
  private BLonConfigScope(int ordinal)
  {
    super(ordinal);
  }

  public static final BLonConfigScope DEFAULT = node;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonConfigScope.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
