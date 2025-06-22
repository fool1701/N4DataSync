/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.math;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BTangent performs the operation out = tan(inA)
 *
 * @author    Dan Giorgis
 * @creation  29 Aug 2001
 * @version   $Revision: 7$ $Date: 3/30/2004 3:42:48 PM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BTangent
  extends BUnaryMath
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.math.BTangent(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTangent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected double calculate(double a)
  {
    return Math.tan(a);
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/util/sine.png");

}
