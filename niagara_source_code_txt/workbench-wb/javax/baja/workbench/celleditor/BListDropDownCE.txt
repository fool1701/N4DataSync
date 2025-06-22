/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.celleditor;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BDropDown;
import javax.baja.ui.BListDropDown;

import com.tridium.workbench.cellmini.BMiniListDropDown;

/**
 * BListDropDownCE 
 *
 * @author    Mike Jarmy
 * @creation  13 Aug 02
 * @version   $Revision: 1$ $Date: 8/15/07 3:38:59 PM EDT$
 * @since     Baja 1.0
 */
 
@NiagaraType
public abstract class BListDropDownCE
  extends BDropDownCE
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.celleditor.BListDropDownCE(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BListDropDownCE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BListDropDownCE()
  {
    add("listDrop", listDrop);
    linkTo("linkMod", listDrop, BMiniListDropDown.valueModified, dropDownModified);
  }

////////////////////////////////////////////////////////////////
// BDropDownCE
////////////////////////////////////////////////////////////////

  public BDropDown getDropDown() { return listDrop; } 

  public BListDropDown getListDropDown() { return listDrop; }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BMiniListDropDown listDrop = new BMiniListDropDown();
}
