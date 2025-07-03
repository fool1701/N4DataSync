/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.ui;

import javax.baja.alarm.*;
import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.*;
import javax.baja.workbench.*;

import com.tridium.flexSerial.*;
import com.tridium.flexSerial.messages.*;
import com.tridium.workbench.fieldeditors.*;

/**
 * Plugin for BString when used as an messageElement identifier.
 *
 * @author    Andy Saunders
 * @creation  15 Sept 2005
 * @version   $Revision$ $Date: 2/15/2005 12:58:34 PM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BFlexMessageElementSelectFE
  extends BComponentNamePickerFE
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.ui.BFlexMessageElementSelectFE(2979906276)1.0$ @*/
/* Generated Thu Sep 30 12:51:30 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexMessageElementSelectFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BPlugin
////////////////////////////////////////////////////////////////

  protected void doLoadValue(BObject value, Context cx)
  {
    elementName = (BFlexMessageElementName)value; 
    responseMessage = (BFlexResponseMessage) elementName.getParent();
    loadName(elementName.getElementSelect());
  }
  
  protected BObject doSaveValue(BObject value, Context cx)
  {
    elementName.setElementSelect(saveName());
    return elementName;
  }
  


  

  
  public BComponent[] list()
    throws Exception
  {

    BFlexMessageBlock msg = (BFlexMessageBlock)responseMessage.get("instance");
    msg.lease();
    return (BComponent[])msg.getChildren(BFlexMessageElement.class);
  }
  
  BFlexMessageElementName elementName;
  BFlexResponseMessage responseMessage;

}
