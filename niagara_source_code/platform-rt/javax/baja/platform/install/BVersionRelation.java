/*
 * Copyright 2007 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform.install;

import javax.baja.data.*;
import javax.baja.io.BIEncodable;
import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * Specifies how a PlatformDependency's version should be checked against
 * a PlatformPart's version
 * 
 * @author    Matt Boon       
 * @creation  3 Apr 07
 * @version   $Revision: 5$ $Date: 9/30/09 12:45:15 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("minimum"),
    @Range("exact"),
    @Range("maximum")
  }
)
public final class BVersionRelation
  extends BFrozenEnum
  implements BIEncodable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.platform.install.BVersionRelation(3067006771)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for minimum. */
  public static final int MINIMUM = 0;
  /** Ordinal value for exact. */
  public static final int EXACT = 1;
  /** Ordinal value for maximum. */
  public static final int MAXIMUM = 2;

  /** BVersionRelation constant for minimum. */
  public static final BVersionRelation minimum = new BVersionRelation(MINIMUM);
  /** BVersionRelation constant for exact. */
  public static final BVersionRelation exact = new BVersionRelation(EXACT);
  /** BVersionRelation constant for maximum. */
  public static final BVersionRelation maximum = new BVersionRelation(MAXIMUM);

  /** Factory method with ordinal. */
  public static BVersionRelation make(int ordinal)
  {
    return (BVersionRelation)minimum.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BVersionRelation make(String tag)
  {
    return (BVersionRelation)minimum.getRange().get(tag);
  }

  /** Private constructor. */
  private BVersionRelation(int ordinal)
  {
    super(ordinal);
  }

  public static final BVersionRelation DEFAULT = minimum;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BVersionRelation.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
