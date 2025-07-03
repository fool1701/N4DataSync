/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.ui;

import javax.baja.alarm.*;
import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.*;

import com.tridium.flexSerial.*;
import com.tridium.flexSerial.messages.*;
import com.tridium.workbench.fieldeditors.*;

/**
 * Plugin for BString when used as an messageComponent identifier.
 *
 * @author    Andy Saunders
 * @creation  15 Sept 2005
 * @version   $Revision$ $Date: 2/15/2005 12:58:34 PM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BFlexMessageBlockSelectFE
  extends BComponentNamePickerFE
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.ui.BFlexMessageBlockSelectFE(2979906276)1.0$ @*/
/* Generated Thu Sep 30 12:51:30 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexMessageBlockSelectFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BPlugin
////////////////////////////////////////////////////////////////
  
  protected void doLoadValue(BObject value, Context cx)
  {
    blockName = (BFlexMessageBlockName)value; 
    //super.doLoadValue(value, cx);
    loadName(blockName.getBlockSelect());
  }

  protected BObject doSaveValue(BObject value, Context cx)
  {
    blockName = (BFlexMessageBlockName)value;
    blockName.setBlockSelect(saveName());
    value = blockName;
    return value;
  }
  
  
  public BComponent[] list()
    throws Exception
  {
    System.out.println(" BFlexMessageBlockSelectFE called with: " + blockName.getName());
    BFlexSerialNetwork service = BFlexSerialNetwork.getParentFlexNetwork(blockName);
    //BFlexSerialNetwork service = (BFlexSerialNetwork)loadService(BFlexSerialNetwork.TYPE);
    System.out.println("service = " + service.getName());
    service.lease();
    BComponent msgStructs = (BComponent)(service.getMessageBlocks());
    msgStructs.lease();
    return (BComponent[])msgStructs.getChildren(BFlexMessageBlock.class);
  }

  BFlexMessageBlockName blockName;

}
