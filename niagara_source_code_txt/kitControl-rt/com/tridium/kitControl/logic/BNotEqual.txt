/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.logic;

import java.io.*;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BNotEqual performs the operation A != B.
 *
 * @author    Dan Giorgis
 * @creation  29 Aug 2001
 * @version   $Revision: 7$ $Date: 3/30/2004 3:41:03 PM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BNotEqual
  extends BComparison
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.logic.BNotEqual(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNotEqual.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  protected final boolean calculate(double a, double b)
  {
    return (a != b);
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/logic/notEqual.png");

}
