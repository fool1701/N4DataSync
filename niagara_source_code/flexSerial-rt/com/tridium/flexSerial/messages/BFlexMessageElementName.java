/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

//import javax.baja.log.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
//import javax.baja.naming.*;
//import javax.baja.status.*;

/**
 * BFlexMessageElementName is the struct added to support selecting a message element 
 * by name.
 *
 * @author    Andy Saunders       
 * @creation  20 Sept 2005
 * @version   $Revision: 7$ $Date: 11/1/2004 11:59:26 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "elementSelect",
  type = "String",
  defaultValue = ""
)
public class BFlexMessageElementName
  extends BStruct
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BFlexMessageElementName(4257296420)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "elementSelect"

  /**
   * Slot for the {@code elementSelect} property.
   * @see #getElementSelect
   * @see #setElementSelect
   */
  public static final Property elementSelect = newProperty(0, "", null);

  /**
   * Get the {@code elementSelect} property.
   * @see #elementSelect
   */
  public String getElementSelect() { return getString(elementSelect); }

  /**
   * Set the {@code elementSelect} property.
   * @see #elementSelect
   */
  public void setElementSelect(String v) { setString(elementSelect, v, null); }

  //endregion Property "elementSelect"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexMessageElementName.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a BFlexMessageElementName with the policyName specified.
   */
  public BFlexMessageElementName(String name)
  {
    setElementSelect(name);    
  }

  /**
   * No arg constructor.
   */
  public BFlexMessageElementName() 
  {
  }
    
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////


  
} 
