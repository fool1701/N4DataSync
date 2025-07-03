/*
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.report.grid;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Implemented by a grid that models a grid of objects in a station.
 * <p>
 * Please note, any implementation must also extend BComponent.
 * </p>
 *
 * @see BGrid
 *
 * @author gjohnson on 13/03/2018
 * @since Niagara 4.6
 */
@NiagaraType
public interface BIGrid extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.report.grid.BIGrid(2979906276)1.0$ @*/
/* Generated Fri Nov 19 16:00:09 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIGrid.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Resolve this grid to produce a GridModel.
   *
   * @param base The base object used to resolve the model.
   * @param cx The calling Context used to resolve the model.
   * @return The grid model.
   */
  GridModel resolve(BObject base, Context cx);
}
