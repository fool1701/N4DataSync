/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl;

//import javax.baja.log.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
//import javax.baja.naming.*;
//import javax.baja.status.*;

/**
 * BExtensionName is the struct added to support selecting an extension 
 * by name.
 *
 * @author    Andy Saunders       
 * @creation  24 Nov 2004
 * @version   $Revision: 7$ $Date: 11/1/2004 11:59:26 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "extensionName",
  type = "String",
  defaultValue = ""
)
public class BExtensionName
  extends BStruct
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.BExtensionName(1798694059)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "extensionName"

  /**
   * Slot for the {@code extensionName} property.
   * @see #getExtensionName
   * @see #setExtensionName
   */
  public static final Property extensionName = newProperty(0, "", null);

  /**
   * Get the {@code extensionName} property.
   * @see #extensionName
   */
  public String getExtensionName() { return getString(extensionName); }

  /**
   * Set the {@code extensionName} property.
   * @see #extensionName
   */
  public void setExtensionName(String v) { setString(extensionName, v, null); }

  //endregion Property "extensionName"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BExtensionName.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a BExtensionName with the policyName specified.
   */
  public BExtensionName(String name)
  {
    setExtensionName(name);    
  }

  /**
   * No arg constructor.
   */
  public BExtensionName() 
  {
  }
    
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////


  
} 
