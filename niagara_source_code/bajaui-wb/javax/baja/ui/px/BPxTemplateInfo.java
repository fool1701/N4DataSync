/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.px;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BPxTemplateInfo is a container class for info needed to link deployed px files to source template.
 *
 * @author Robert Adams
 * @creation 8/26/2014
 * @since Baja 1.0
 */
@NiagaraType
/*
 Name of template containing associated px file
 */
@NiagaraProperty(
  name = "templateName",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY
)
/*
 Template vendor
 */
@NiagaraProperty(
  name = "vendor",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY
)
/*
 Template version
 */
@NiagaraProperty(
  name = "version",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY
)
public class BPxTemplateInfo
   extends BStruct
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.px.BPxTemplateInfo(1929924797)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "templateName"

  /**
   * Slot for the {@code templateName} property.
   * Name of template containing associated px file
   * @see #getTemplateName
   * @see #setTemplateName
   */
  public static final Property templateName = newProperty(Flags.READONLY, "", null);

  /**
   * Get the {@code templateName} property.
   * Name of template containing associated px file
   * @see #templateName
   */
  public String getTemplateName() { return getString(templateName); }

  /**
   * Set the {@code templateName} property.
   * Name of template containing associated px file
   * @see #templateName
   */
  public void setTemplateName(String v) { setString(templateName, v, null); }

  //endregion Property "templateName"

  //region Property "vendor"

  /**
   * Slot for the {@code vendor} property.
   * Template vendor
   * @see #getVendor
   * @see #setVendor
   */
  public static final Property vendor = newProperty(Flags.READONLY, "", null);

  /**
   * Get the {@code vendor} property.
   * Template vendor
   * @see #vendor
   */
  public String getVendor() { return getString(vendor); }

  /**
   * Set the {@code vendor} property.
   * Template vendor
   * @see #vendor
   */
  public void setVendor(String v) { setString(vendor, v, null); }

  //endregion Property "vendor"

  //region Property "version"

  /**
   * Slot for the {@code version} property.
   * Template version
   * @see #getVersion
   * @see #setVersion
   */
  public static final Property version = newProperty(Flags.READONLY, "", null);

  /**
   * Get the {@code version} property.
   * Template version
   * @see #version
   */
  public String getVersion() { return getString(version); }

  /**
   * Set the {@code version} property.
   * Template version
   * @see #version
   */
  public void setVersion(String v) { setString(version, v, null); }

  //endregion Property "version"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPxTemplateInfo.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /** Empty constructor */
  public BPxTemplateInfo() {}

  /** Constructor with property values. */
  public BPxTemplateInfo(String name, String vendor, String version)
  {
    setTemplateName(name);
    setVendor(vendor);
    setVersion(version);
  }
}
