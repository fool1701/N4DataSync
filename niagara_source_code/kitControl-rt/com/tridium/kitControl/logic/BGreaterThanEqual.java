/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl.logic;

import java.io.*;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BGreaterThanEqual performs the operation A &gt;= B.
 *
 * @author    Dan Giorgis
 * @creation  29 Aug 2001
 * @version   $Revision: 8$ $Date: 3/30/2004 3:40:50 PM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BGreaterThanEqual
  extends BComparison
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.logic.BGreaterThanEqual(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:30 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BGreaterThanEqual.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  protected final boolean calculate(double a, double b)
  {
    return (a >= b);
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/logic/greaterThanEqual.png");

}
