/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * Determines how a file copy should proceed when a file copy is requested
 * and a file with the given name already exists
 * 
 * @author    Matt Boon       
 * @creation  04 Feb 05
 * @version   $Revision: 1$ $Date: 2/4/05 2:25:58 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("all"),
    @Range("different"),
    @Range("none")
  }
)
public final class BOverwritePolicy
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.platform.BOverwritePolicy(2414735031)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for all. */
  public static final int ALL = 0;
  /** Ordinal value for different. */
  public static final int DIFFERENT = 1;
  /** Ordinal value for none. */
  public static final int NONE = 2;

  /** BOverwritePolicy constant for all. */
  public static final BOverwritePolicy all = new BOverwritePolicy(ALL);
  /** BOverwritePolicy constant for different. */
  public static final BOverwritePolicy different = new BOverwritePolicy(DIFFERENT);
  /** BOverwritePolicy constant for none. */
  public static final BOverwritePolicy none = new BOverwritePolicy(NONE);

  /** Factory method with ordinal. */
  public static BOverwritePolicy make(int ordinal)
  {
    return (BOverwritePolicy)all.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BOverwritePolicy make(String tag)
  {
    return (BOverwritePolicy)all.getRange().get(tag);
  }

  /** Private constructor. */
  private BOverwritePolicy(int ordinal)
  {
    super(ordinal);
  }

  public static final BOverwritePolicy DEFAULT = all;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOverwritePolicy.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
