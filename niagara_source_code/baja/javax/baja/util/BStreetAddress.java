/*
 * Copyright 2001, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.util;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BStreetAddress contains the information for a site location
 * by mailing address.
 *
 * @author    John Sublett
 * @creation  18 Jan 2001
 * @version   $Revision: 3$ $Date: 1/22/01 4:31:22 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Line 1 in the street address.
 */
@NiagaraProperty(
  name = "address1",
  type = "String",
  defaultValue = ""
)
/*
 Line 2 in the street address, in case one line isn't enough.
 */
@NiagaraProperty(
  name = "address2",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "city",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "state",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "zip",
  type = "String",
  defaultValue = ""
)
public class BStreetAddress
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BStreetAddress(149643845)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "address1"

  /**
   * Slot for the {@code address1} property.
   * Line 1 in the street address.
   * @see #getAddress1
   * @see #setAddress1
   */
  public static final Property address1 = newProperty(0, "", null);

  /**
   * Get the {@code address1} property.
   * Line 1 in the street address.
   * @see #address1
   */
  public String getAddress1() { return getString(address1); }

  /**
   * Set the {@code address1} property.
   * Line 1 in the street address.
   * @see #address1
   */
  public void setAddress1(String v) { setString(address1, v, null); }

  //endregion Property "address1"

  //region Property "address2"

  /**
   * Slot for the {@code address2} property.
   * Line 2 in the street address, in case one line isn't enough.
   * @see #getAddress2
   * @see #setAddress2
   */
  public static final Property address2 = newProperty(0, "", null);

  /**
   * Get the {@code address2} property.
   * Line 2 in the street address, in case one line isn't enough.
   * @see #address2
   */
  public String getAddress2() { return getString(address2); }

  /**
   * Set the {@code address2} property.
   * Line 2 in the street address, in case one line isn't enough.
   * @see #address2
   */
  public void setAddress2(String v) { setString(address2, v, null); }

  //endregion Property "address2"

  //region Property "city"

  /**
   * Slot for the {@code city} property.
   * @see #getCity
   * @see #setCity
   */
  public static final Property city = newProperty(0, "", null);

  /**
   * Get the {@code city} property.
   * @see #city
   */
  public String getCity() { return getString(city); }

  /**
   * Set the {@code city} property.
   * @see #city
   */
  public void setCity(String v) { setString(city, v, null); }

  //endregion Property "city"

  //region Property "state"

  /**
   * Slot for the {@code state} property.
   * @see #getState
   * @see #setState
   */
  public static final Property state = newProperty(0, "", null);

  /**
   * Get the {@code state} property.
   * @see #state
   */
  public String getState() { return getString(state); }

  /**
   * Set the {@code state} property.
   * @see #state
   */
  public void setState(String v) { setString(state, v, null); }

  //endregion Property "state"

  //region Property "zip"

  /**
   * Slot for the {@code zip} property.
   * @see #getZip
   * @see #setZip
   */
  public static final Property zip = newProperty(0, "", null);

  /**
   * Get the {@code zip} property.
   * @see #zip
   */
  public String getZip() { return getString(zip); }

  /**
   * Set the {@code zip} property.
   * @see #zip
   */
  public void setZip(String v) { setString(zip, v, null); }

  //endregion Property "zip"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStreetAddress.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
