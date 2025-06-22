/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.datatypes;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BStruct;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * This base address class
 *
 * @author Robert A Adams
 * @creation Oct 24, 2011
 */
@NiagaraType
public class BAddress
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.datatypes.BAddress(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:11 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAddress.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Returns address in form suitable to linkLayer. Subclasses must override.
   */
  public Object getAddress()
  {
    throw new BajaRuntimeException("getAddress not implemented");
  }

  /**
   * Subclasses must override.
   *
   * @return true if the specified address is for the same device.
   */
  public boolean sameDevice(BAddress comp)
  {
    throw new BajaRuntimeException("sameDevice not implemented");
  }

  /**
   * Override point for subclasses to indicate a valid address. Default returns
   * false.
   *
   * @return true if this is a valid address.
   */
  public boolean isValid()
  {
    return false;
  }

  /**
   * Override point for subclasses to return String to be used a hash key.
   */
  public String toHashString()
  {
    throw new BajaRuntimeException("toHashString not implemented");
  }
}
