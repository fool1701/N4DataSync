/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.ui;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.flexSerial.BFlexSerialNetwork;
import com.tridium.flexSerial.messages.BFlexMessage;
import com.tridium.flexSerial.messages.BFlexMessageFolder;
import com.tridium.flexSerial.messages.BFlexMessageName;
import com.tridium.workbench.fieldeditors.BComponentNamePickerFE;

/**
 * Plugin for BString when used as an messageComponent identifier.
 *
 * @author    Andy Saunders
 * @creation  15 Sept 2005
 * @version   $Revision$ $Date: 2/15/2005 12:58:34 PM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BMessageSelectFE
  extends BComponentNamePickerFE
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.ui.BMessageSelectFE(2979906276)1.0$ @*/
/* Generated Thu Sep 30 12:51:30 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMessageSelectFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BPlugin
////////////////////////////////////////////////////////////////
  
  protected void doLoadValue(BObject value, Context cx)
  {
    messageName = (BFlexMessageName)value; 
    //super.doLoadValue(value, cx);
    loadName(messageName.getMessageSelect());
  }
  
  protected BObject doSaveValue(BObject value, Context cx)
  {
    messageName = (BFlexMessageName)value;
    messageName.setMessageSelect(saveName());
    value = messageName;
    return value;
  }
  


  public BComponent[] list()
    throws Exception
  {
    //System.out.println("  BMessageSelectFE.list()");
    BFlexSerialNetwork service = BFlexSerialNetwork.getParentFlexNetwork(messageName);
    //System.out.println("  BMessageSelectFE.list(): service = " + service.getName());
    service.lease();
    BFlexMessageFolder messages = service.getMessages();
    messages.lease();
    return messages.getChildren(BFlexMessage.class);
  }

  BFlexMessageName messageName;

}
