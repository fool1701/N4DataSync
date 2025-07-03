/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BTextField;

/**
 * BUuidFE allows viewing and editing of a BUuid
 * using a text field.
 *
 * @author    John Sublett
 * @creation  17 Oct 20021
 * @version   $Revision: 2$ $Date: 3/28/05 1:40:37 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:Uuid"
  )
)
public class BUuidFE
  extends BDefaultSimpleFE
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BUuidFE(2980611027)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUuidFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BUuidFE()
  {
    ((BTextField)getContent()).setVisibleColumns(37);
  }
}
