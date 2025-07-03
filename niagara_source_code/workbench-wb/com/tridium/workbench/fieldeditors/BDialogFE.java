/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BImage;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BToolBar;
import javax.baja.ui.BWidget;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BDialogFE is a base class to make it easy and consistent 
 * to build field editors that popup a BDialog to edit their 
 * contents.
 *
 * @author    Brian Frank       
 * @creation  13 Dec 01
 * @version   $Revision: 15$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraAction(
  name = "editPressed"
)
public abstract class BDialogFE
  extends BWbFieldEditor
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BDialogFE(401614936)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "editPressed"

  /**
   * Slot for the {@code editPressed} action.
   * @see #editPressed()
   */
  public static final Action editPressed = newAction(0, null);

  /**
   * Invoke the {@code editPressed} action.
   * @see #editPressed
   */
  public void editPressed() { invoke(editPressed, null, null); }

  //endregion Action "editPressed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDialogFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BDialogFE()
  {    
    edit = new BButton(editIcon);
    edit.setFocusTraversable(false);
    button = new BToolBar();
    button.add("edit", edit);
    linkTo("editButtonLink", edit,  BButton.actionPerformed, editPressed);
  }
  
////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////

  public void setEnabled(boolean v)
  {
    super.setEnabled(v);
    edit.setEnabled(v);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  public BWidget getEditButton()
  {
    return button;
  }

  public void doEditPressed()
  {
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  public static final BImage editIcon = BImage.make("module://icons/x16/doubleArrowRight.png");
  private BWidget button;
  private BButton edit;
}
