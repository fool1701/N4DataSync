/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BDiscretePoint is the superclass of BBooleanPoint and BEnumPoint.
 *
 * @author    Brian Frank
 * @creation  20 Jan 04
 * @version   $Revision: 3$ $Date: 3/28/05 11:40:31 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BDiscretePoint
  extends BControlPoint
  implements BIEnum
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.BDiscretePoint(2979906276)1.0$ @*/
/* Generated Wed Jan 26 11:36:16 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDiscretePoint.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Is the discrete point active.
   */
  public abstract boolean isActive();
 
}
