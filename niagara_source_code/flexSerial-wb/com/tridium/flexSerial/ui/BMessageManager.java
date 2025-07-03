/* 
 * Copyright 2005 Tridium, Inc.  All rights reserved.
 * 
 */

package com.tridium.flexSerial.ui;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.workbench.mgr.*;

import com.tridium.flexSerial.messages.*;

/**
 * @author Andy Saunders
 * @creation 14 Sept 2005
 * @version $Revision: 4$ $Date: 5/26/2004 7:22:11 AM$
 */
@NiagaraType(
  agent = @AgentOn(
    types = "flexSerial:FlexMessageBlock",
    requiredPermissions = "r"
  )
)
public class BMessageManager 
  extends BAbstractManager
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.ui.BMessageManager(2189505257)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMessageManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BMessageManager() {}


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  
  public void doLoadValue(BObject value, Context cx)
  {
    super.doLoadValue(value, cx);
    relayout();
  }
  


  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  protected MgrModel makeModel() 
  { 
    return new MessageModel(this); 
  }

  protected MgrController makeController() 
  { 
    return new MessageManagerController(this); 
  }

  /////////////////////////////////////////////////////////////////
  // Methods - Default and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Private and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Inner Classes - in alphabetical order by class name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Constants - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////
  
  
}//BMessageManager
