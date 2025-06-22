/*
 * Copyright 2006, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.report.grid;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BGrid models a grid of objects in a station.
 *
 * @see BIGrid
 *
 * @author    Andy Frank
 * @creation  1 Nov 06
 * @version   $Revision: 1$ $Date: 11/2/06 2:42:59 PM EST$
 * @since     Niagara 3.2
 */
@NiagaraType
public abstract class BGrid
  extends BComponent
  implements BIGrid
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.report.grid.BGrid(2979906276)1.0$ @*/
/* Generated Fri Nov 19 16:00:09 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BGrid.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  
}
