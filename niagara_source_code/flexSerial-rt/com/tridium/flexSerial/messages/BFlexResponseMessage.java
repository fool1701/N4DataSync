/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import java.io.*;

import javax.baja.io.*;
import javax.baja.naming.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.*;

import com.tridium.flexSerial.*;
import com.tridium.flexSerial.enums.*;
import com.tridium.flexSerial.messages.*;
import com.tridium.program.*;

/**
 * BFlexResponseMessage defines a final message.
 *
 * @author    Andy Saunders
 * @creation  14 Sept 2005
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "elementSelect",
  type = "BFlexMessageElementName",
  defaultValue = "new BFlexMessageElementName()",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"flexSerial:FlexMessageElementSelectFE\"))")
)
@NiagaraProperty(
  name = "messageValidate",
  type = "BOrd",
  defaultValue = "BOrd.NULL"
)
public class BFlexResponseMessage
  extends BFlexMessageSelect
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BFlexResponseMessage(3080701371)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "elementSelect"

  /**
   * Slot for the {@code elementSelect} property.
   * @see #getElementSelect
   * @see #setElementSelect
   */
  public static final Property elementSelect = newProperty(0, new BFlexMessageElementName(), BFacets.make(BFacets.FIELD_EDITOR, BString.make("flexSerial:FlexMessageElementSelectFE")));

  /**
   * Get the {@code elementSelect} property.
   * @see #elementSelect
   */
  public BFlexMessageElementName getElementSelect() { return (BFlexMessageElementName)get(elementSelect); }

  /**
   * Set the {@code elementSelect} property.
   * @see #elementSelect
   */
  public void setElementSelect(BFlexMessageElementName v) { set(elementSelect, v, null); }

  //endregion Property "elementSelect"

  //region Property "messageValidate"

  /**
   * Slot for the {@code messageValidate} property.
   * @see #getMessageValidate
   * @see #setMessageValidate
   */
  public static final Property messageValidate = newProperty(0, BOrd.NULL, null);

  /**
   * Get the {@code messageValidate} property.
   * @see #messageValidate
   */
  public BOrd getMessageValidate() { return (BOrd)get(messageValidate); }

  /**
   * Set the {@code messageValidate} property.
   * @see #messageValidate
   */
  public void setMessageValidate(BOrd v) { set(messageValidate, v, null); }

  //endregion Property "messageValidate"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexResponseMessage.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// 
////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////
//Presentation
////////////////////////////////////////////////////////////////

 public BIcon getIcon() { return icon; }
 private static final BIcon icon = BIcon.make("module://flexSerial/com/tridium/flexSerial/icons/flexMessageResponse.png");



}
