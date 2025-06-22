/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import java.io.*;

import javax.baja.io.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.*;

import com.tridium.flexSerial.*;
import com.tridium.flexSerial.enums.*;
import com.tridium.flexSerial.messages.*;
import com.tridium.program.*;

/**
 * BFlexRequestMessage defines a final message.
 *
 * @author    Andy Saunders
 * @creation  14 Sept 2005
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BFlexRequestMessage
  extends BFlexMessageSelect
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BFlexRequestMessage(2979906276)1.0$ @*/
/* Generated Thu Sep 30 12:51:14 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexRequestMessage.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// 
////////////////////////////////////////////////////////////////
/*
  public void doCreateInstance()
  {
    BFlexMessage reqMsg = BMessageSelect.getFlexMessage(getMessage());
    try { this.remove("instance"); } catch(Exception e) {}
    this.add("instance", new BFlexMessageBlock());
    BFlexMessageBlock instance = (BFlexMessageBlock)get("instance");
    reqMsg.addMessageItems(instance);
    instance.doCalculateOffsets();
    
  }
  */
  
////////////////////////////////////////////////////////////////
//Presentation
////////////////////////////////////////////////////////////////

 public BIcon getIcon() { return icon; }
 private static final BIcon icon = BIcon.make("module://flexSerial/com/tridium/flexSerial/icons/flexMessageRequest.png");


  
} 
