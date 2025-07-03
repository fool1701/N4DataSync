/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitLon;

import javax.baja.lonworks.datatypes.BNeuronId;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BStruct;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 *   This class file specifies parameters needed by BLonReplace.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  24 Aug 07
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Flag indicating use of service pin to identify
 physical device.
 */
@NiagaraProperty(
  name = "servicePin",
  type = "boolean",
  defaultValue = "false"
)
/*
 NeuronId if different than current setting. Otherwise leave
 at BNeuronId.DEFAULT.
 */
@NiagaraProperty(
  name = "neuronId",
  type = "BNeuronId",
  defaultValue = "BNeuronId.DEFAULT"
)
public class BReplaceParameter
  extends BStruct
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitLon.BReplaceParameter(1646228914)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "servicePin"

  /**
   * Slot for the {@code servicePin} property.
   * Flag indicating use of service pin to identify
   * physical device.
   * @see #getServicePin
   * @see #setServicePin
   */
  public static final Property servicePin = newProperty(0, false, null);

  /**
   * Get the {@code servicePin} property.
   * Flag indicating use of service pin to identify
   * physical device.
   * @see #servicePin
   */
  public boolean getServicePin() { return getBoolean(servicePin); }

  /**
   * Set the {@code servicePin} property.
   * Flag indicating use of service pin to identify
   * physical device.
   * @see #servicePin
   */
  public void setServicePin(boolean v) { setBoolean(servicePin, v, null); }

  //endregion Property "servicePin"

  //region Property "neuronId"

  /**
   * Slot for the {@code neuronId} property.
   * NeuronId if different than current setting. Otherwise leave
   * at BNeuronId.DEFAULT.
   * @see #getNeuronId
   * @see #setNeuronId
   */
  public static final Property neuronId = newProperty(0, BNeuronId.DEFAULT, null);

  /**
   * Get the {@code neuronId} property.
   * NeuronId if different than current setting. Otherwise leave
   * at BNeuronId.DEFAULT.
   * @see #neuronId
   */
  public BNeuronId getNeuronId() { return (BNeuronId)get(neuronId); }

  /**
   * Set the {@code neuronId} property.
   * NeuronId if different than current setting. Otherwise leave
   * at BNeuronId.DEFAULT.
   * @see #neuronId
   */
  public void setNeuronId(BNeuronId v) { set(neuronId, v, null); }

  //endregion Property "neuronId"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BReplaceParameter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * No arg constructor
  public BReplaceParameter()
  {
  }
   */

  
}
