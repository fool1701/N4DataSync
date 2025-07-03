/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.options;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BUserOptions options that can be changed by the user.  UserOptions
 * must be keyed by <code>getType().toString()</code>.
 *
 * @author    Andy Frank
 * @creation  03 Feb 03
 * @version   $Revision: 4$ $Date: 3/28/05 10:32:27 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BUserOptions
  extends BOptions
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.options.BUserOptions(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUserOptions.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
