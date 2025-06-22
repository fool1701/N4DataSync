/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.loadable;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BUploadParameters is the base class for arguments
 * which are used to tailor the upload process.
 *
 * @author    Brian Frank
 * @creation  13 Jan 01
 * @version   $Revision: 8$ $Date: 3/30/04 8:12:54 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Upload transient properties.
 */
@NiagaraProperty(
  name = "uploadTransient",
  type = "boolean",
  defaultValue = "true"
)
/*
 Upload persistent properties.
 */
@NiagaraProperty(
  name = "uploadPersistent",
  type = "boolean",
  defaultValue = "true"
)
public class BUploadParameters
  extends BLoadableActionParameters
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.loadable.BUploadParameters(3454547197)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "uploadTransient"

  /**
   * Slot for the {@code uploadTransient} property.
   * Upload transient properties.
   * @see #getUploadTransient
   * @see #setUploadTransient
   */
  public static final Property uploadTransient = newProperty(0, true, null);

  /**
   * Get the {@code uploadTransient} property.
   * Upload transient properties.
   * @see #uploadTransient
   */
  public boolean getUploadTransient() { return getBoolean(uploadTransient); }

  /**
   * Set the {@code uploadTransient} property.
   * Upload transient properties.
   * @see #uploadTransient
   */
  public void setUploadTransient(boolean v) { setBoolean(uploadTransient, v, null); }

  //endregion Property "uploadTransient"

  //region Property "uploadPersistent"

  /**
   * Slot for the {@code uploadPersistent} property.
   * Upload persistent properties.
   * @see #getUploadPersistent
   * @see #setUploadPersistent
   */
  public static final Property uploadPersistent = newProperty(0, true, null);

  /**
   * Get the {@code uploadPersistent} property.
   * Upload persistent properties.
   * @see #uploadPersistent
   */
  public boolean getUploadPersistent() { return getBoolean(uploadPersistent); }

  /**
   * Set the {@code uploadPersistent} property.
   * Upload persistent properties.
   * @see #uploadPersistent
   */
  public void setUploadPersistent(boolean v) { setBoolean(uploadPersistent, v, null); }

  //endregion Property "uploadPersistent"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUploadParameters.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Default constructor.
   */
  public BUploadParameters() {}

  /**
   * Constructor.
   * @param recursive
   */
  public BUploadParameters(boolean recursive)
  {
    super(recursive);
  }
}
