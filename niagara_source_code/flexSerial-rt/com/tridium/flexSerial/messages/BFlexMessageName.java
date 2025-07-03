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
 * BFlexMessageName is the struct added to support selecting a message 
 * by name.
 *
 * @author    Andy Saunders       
 * @creation  20 Sept 2005
 * @version   $Revision: 7$ $Date: 11/1/2004 11:59:26 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "messageSelect",
  type = "String",
  defaultValue = ""
)
public class BFlexMessageName
  extends BStruct
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BFlexMessageName(23655511)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "messageSelect"

  /**
   * Slot for the {@code messageSelect} property.
   * @see #getMessageSelect
   * @see #setMessageSelect
   */
  public static final Property messageSelect = newProperty(0, "", null);

  /**
   * Get the {@code messageSelect} property.
   * @see #messageSelect
   */
  public String getMessageSelect() { return getString(messageSelect); }

  /**
   * Set the {@code messageSelect} property.
   * @see #messageSelect
   */
  public void setMessageSelect(String v) { setString(messageSelect, v, null); }

  //endregion Property "messageSelect"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexMessageName.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a BFlexMessageName with the specified.
   */
  public BFlexMessageName(String name)
  {
    setMessageSelect(name);    
  }

  /**
   * No arg constructor.
   */
  public BFlexMessageName() 
  {
  }
    
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public String toString(Context cx)
  {
    return getMessageSelect();
  }

////////////////////////////////////////////////////////////////
//Presentation
////////////////////////////////////////////////////////////////

 public BIcon getIcon() { return icon; }
 private static final BIcon icon = BIcon.make("module://flexSerial/com/tridium/flexSerial/icons/flexMessage.png");


} 
