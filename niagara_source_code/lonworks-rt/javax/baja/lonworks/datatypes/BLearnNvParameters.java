/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BStruct;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BLearnNvParameters list options to constrain the 
 * importXml action in BDynamicDevice
 *
 * @author    Robert Adams
 * @creation  18 Oct 01
 * @version   $Revision: 1$ $Date: 10/18/01 2:56:30 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Should lonComponents be partitioned in specified objects.
 */
@NiagaraProperty(
  name = "useLonObjects",
  type = "boolean",
  defaultValue = "false"
)
public class BLearnNvParameters
  extends BStruct
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BLearnNvParameters(897325362)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "useLonObjects"

  /**
   * Slot for the {@code useLonObjects} property.
   * Should lonComponents be partitioned in specified objects.
   * @see #getUseLonObjects
   * @see #setUseLonObjects
   */
  public static final Property useLonObjects = newProperty(0, false, null);

  /**
   * Get the {@code useLonObjects} property.
   * Should lonComponents be partitioned in specified objects.
   * @see #useLonObjects
   */
  public boolean getUseLonObjects() { return getBoolean(useLonObjects); }

  /**
   * Set the {@code useLonObjects} property.
   * Should lonComponents be partitioned in specified objects.
   * @see #useLonObjects
   */
  public void setUseLonObjects(boolean v) { setBoolean(useLonObjects, v, null); }

  //endregion Property "useLonObjects"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLearnNvParameters.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /** Empty constructor.*/
  public BLearnNvParameters() {}
  
  /** Create BLearnNvParameters with useLonObjects flag set to the specified value.*/
  public BLearnNvParameters(boolean useLo)
  {
    setUseLonObjects(useLo);
  }
}
