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
 * BFlexMessageBlockName is the struct added to support selecting a message block 
 * by name.
 *
 * @author    Andy Saunders       
 * @creation  20 Sept 2005
 * @version   $Revision: 7$ $Date: 11/1/2004 11:59:26 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "blockSelect",
  type = "String",
  defaultValue = ""
)
public class BFlexMessageBlockName
  extends BStruct
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BFlexMessageBlockName(195569472)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "blockSelect"

  /**
   * Slot for the {@code blockSelect} property.
   * @see #getBlockSelect
   * @see #setBlockSelect
   */
  public static final Property blockSelect = newProperty(0, "", null);

  /**
   * Get the {@code blockSelect} property.
   * @see #blockSelect
   */
  public String getBlockSelect() { return getString(blockSelect); }

  /**
   * Set the {@code blockSelect} property.
   * @see #blockSelect
   */
  public void setBlockSelect(String v) { setString(blockSelect, v, null); }

  //endregion Property "blockSelect"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexMessageBlockName.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a BFlexMessageBlockName with the policyName specified.
   */
  public BFlexMessageBlockName(String name)
  {
    setBlockSelect(name);    
  }

  /**
   * No arg constructor.
   */
  public BFlexMessageBlockName() 
  {
  }
    
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public String toString(Context cx)
  {
    return getBlockSelect();
  }
  
} 
