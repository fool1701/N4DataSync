/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BImportParameters list options to constrain the 
 * importXml action in BDynamicDevice
 *
 * @author    Robert Adams
 * @creation  18 Oct 01
 * @version   $Revision: 1$ $Date: 10/18/01 2:56:30 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 The name of xml file used int importXml action.
 */
@NiagaraProperty(
  name = "xmlFile",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  facets = @Facet("BFacets.make(\"allowLocalAccess\", BBoolean.TRUE)")
)
/*
 The name of xif file used to describe the device.
 */
@NiagaraProperty(
  name = "xifFile",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  flags = Flags.HIDDEN,
  facets = @Facet("BFacets.make(\"allowLocalAccess\", BBoolean.TRUE)")
)
/*
 The name of resource type file used to describe the device.
 */
@NiagaraProperty(
  name = "resFile",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  flags = Flags.HIDDEN,
  facets = @Facet("BFacets.make(\"allowLocalAccess\", BBoolean.TRUE)")
)
/*
 Should operation include upload of nvConfig data from device.
 */
@NiagaraProperty(
  name = "syncNvConfig",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.HIDDEN
)
/*
 Should lonComponents be partioned in specified objects.
 */
@NiagaraProperty(
  name = "useLonObjects",
  type = "boolean",
  defaultValue = "false"
)
public class BImportParameters
  extends BStruct
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BImportParameters(3763278477)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "xmlFile"

  /**
   * Slot for the {@code xmlFile} property.
   * The name of xml file used int importXml action.
   * @see #getXmlFile
   * @see #setXmlFile
   */
  public static final Property xmlFile = newProperty(0, BOrd.NULL, BFacets.make("allowLocalAccess", BBoolean.TRUE));

  /**
   * Get the {@code xmlFile} property.
   * The name of xml file used int importXml action.
   * @see #xmlFile
   */
  public BOrd getXmlFile() { return (BOrd)get(xmlFile); }

  /**
   * Set the {@code xmlFile} property.
   * The name of xml file used int importXml action.
   * @see #xmlFile
   */
  public void setXmlFile(BOrd v) { set(xmlFile, v, null); }

  //endregion Property "xmlFile"

  //region Property "xifFile"

  /**
   * Slot for the {@code xifFile} property.
   * The name of xif file used to describe the device.
   * @see #getXifFile
   * @see #setXifFile
   */
  public static final Property xifFile = newProperty(Flags.HIDDEN, BOrd.NULL, BFacets.make("allowLocalAccess", BBoolean.TRUE));

  /**
   * Get the {@code xifFile} property.
   * The name of xif file used to describe the device.
   * @see #xifFile
   */
  public BOrd getXifFile() { return (BOrd)get(xifFile); }

  /**
   * Set the {@code xifFile} property.
   * The name of xif file used to describe the device.
   * @see #xifFile
   */
  public void setXifFile(BOrd v) { set(xifFile, v, null); }

  //endregion Property "xifFile"

  //region Property "resFile"

  /**
   * Slot for the {@code resFile} property.
   * The name of resource type file used to describe the device.
   * @see #getResFile
   * @see #setResFile
   */
  public static final Property resFile = newProperty(Flags.HIDDEN, BOrd.NULL, BFacets.make("allowLocalAccess", BBoolean.TRUE));

  /**
   * Get the {@code resFile} property.
   * The name of resource type file used to describe the device.
   * @see #resFile
   */
  public BOrd getResFile() { return (BOrd)get(resFile); }

  /**
   * Set the {@code resFile} property.
   * The name of resource type file used to describe the device.
   * @see #resFile
   */
  public void setResFile(BOrd v) { set(resFile, v, null); }

  //endregion Property "resFile"

  //region Property "syncNvConfig"

  /**
   * Slot for the {@code syncNvConfig} property.
   * Should operation include upload of nvConfig data from device.
   * @see #getSyncNvConfig
   * @see #setSyncNvConfig
   */
  public static final Property syncNvConfig = newProperty(Flags.HIDDEN, false, null);

  /**
   * Get the {@code syncNvConfig} property.
   * Should operation include upload of nvConfig data from device.
   * @see #syncNvConfig
   */
  public boolean getSyncNvConfig() { return getBoolean(syncNvConfig); }

  /**
   * Set the {@code syncNvConfig} property.
   * Should operation include upload of nvConfig data from device.
   * @see #syncNvConfig
   */
  public void setSyncNvConfig(boolean v) { setBoolean(syncNvConfig, v, null); }

  //endregion Property "syncNvConfig"

  //region Property "useLonObjects"

  /**
   * Slot for the {@code useLonObjects} property.
   * Should lonComponents be partioned in specified objects.
   * @see #getUseLonObjects
   * @see #setUseLonObjects
   */
  public static final Property useLonObjects = newProperty(0, false, null);

  /**
   * Get the {@code useLonObjects} property.
   * Should lonComponents be partioned in specified objects.
   * @see #useLonObjects
   */
  public boolean getUseLonObjects() { return getBoolean(useLonObjects); }

  /**
   * Set the {@code useLonObjects} property.
   * Should lonComponents be partioned in specified objects.
   * @see #useLonObjects
   */
  public void setUseLonObjects(boolean v) { setBoolean(useLonObjects, v, null); }

  //endregion Property "useLonObjects"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BImportParameters.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /** Empty constructor.*/
  public BImportParameters() {}
  
//  public BImportParameters(BOrd ord) { setXmlFile(ord); }
  
  /** Create BImportParameters with syncNvConfig set to the specified value.*/
  public BImportParameters(boolean sync)
  {
    setSyncNvConfig(sync);
  }
  
  /** Create BImportParameters with syncNvConfig and useLonObjects set to the specified value.*/
  public BImportParameters(boolean sync, boolean use)
  {
    setSyncNvConfig(sync);
    setUseLonObjects(use);
  }
}
