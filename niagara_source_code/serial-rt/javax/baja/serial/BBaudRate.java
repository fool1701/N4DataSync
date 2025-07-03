/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.serial;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BBaudRate represents the possible baud rate choices
 * for a comm port.
 * <p>
 *
 * @author    Scott Hoye
 * @creation  23 Sep 02
 * @version   $Revision: 1$ $Date: 5/31/05 11:35:50 AM EDT$  
 * @since     Niagara 3.0 serial 1.0
 */

@NiagaraType
public abstract class BBaudRate
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.serial.BBaudRate(2979906276)1.0$ @*/
/* Generated Fri Sep 17 11:17:08 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBaudRate.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /** Private constructor. */
  public BBaudRate(int ordinal)
  {
    super(ordinal);
  }

}
