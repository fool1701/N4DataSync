/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package com.examples.envctrldriver.points;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BStruct;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The Point Learn entry is a simple struct to contain the information
 * we obtain during initial discovery of a Point on the remote device.
 * This information is later used to create a new Point under the
 * device extension on the Station with the correct Proxy information
 * to represent and connect with the remote point.
 *
 * @author J. Spangler
 */
@NiagaraType
@NiagaraProperty(
  name = "pointName",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "pointId",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "pointType",
  type = "String",
  defaultValue = "number"
)
public class BPointLearnEntry
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.examples.envctrldriver.points.BPointLearnEntry(1460004967)1.0$ @*/
/* Generated Thu Dec 01 20:57:46 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "pointName"

  /**
   * Slot for the {@code pointName} property.
   * @see #getPointName
   * @see #setPointName
   */
  public static final Property pointName = newProperty(0, "", null);

  /**
   * Get the {@code pointName} property.
   * @see #pointName
   */
  public String getPointName() { return getString(pointName); }

  /**
   * Set the {@code pointName} property.
   * @see #pointName
   */
  public void setPointName(String v) { setString(pointName, v, null); }

  //endregion Property "pointName"

  //region Property "pointId"

  /**
   * Slot for the {@code pointId} property.
   * @see #getPointId
   * @see #setPointId
   */
  public static final Property pointId = newProperty(0, "", null);

  /**
   * Get the {@code pointId} property.
   * @see #pointId
   */
  public String getPointId() { return getString(pointId); }

  /**
   * Set the {@code pointId} property.
   * @see #pointId
   */
  public void setPointId(String v) { setString(pointId, v, null); }

  //endregion Property "pointId"

  //region Property "pointType"

  /**
   * Slot for the {@code pointType} property.
   * @see #getPointType
   * @see #setPointType
   */
  public static final Property pointType = newProperty(0, "number", null);

  /**
   * Get the {@code pointType} property.
   * @see #pointType
   */
  public String getPointType() { return getString(pointType); }

  /**
   * Set the {@code pointType} property.
   * @see #pointType
   */
  public void setPointType(String v) { setString(pointType, v, null); }

  //endregion Property "pointType"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPointLearnEntry.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
