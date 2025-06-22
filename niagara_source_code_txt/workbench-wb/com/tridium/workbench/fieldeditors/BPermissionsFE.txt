/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BDialog;
import javax.baja.ui.BLabel;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BFlowPane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.util.Lexicon;

/**
 * BPermissionsFE edits BPermissions.
 *
 * @author    Andy Frank       
 * @creation  20 Jul 04
 * @version   $Revision: 3$ $Date: 3/28/05 1:40:36 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:Permissions"
  )
)
public class BPermissionsFE
  extends BDialogFE
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BPermissionsFE(3617160297)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPermissionsFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  public BPermissionsFE()
  {
    BFlowPane pane = new BFlowPane();
    pane.add(null, label = new BLabel());
    pane.add(null, getEditButton());
    setContent(pane);
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////
  
  protected void doSetReadonly(boolean readonly)
  {
    getEditButton().setVisible(!readonly);
  }
  
  protected void doLoadValue(BObject value, Context cx)
  {
    permissions = (BPermissions)value;
    String s = permissions.toString(cx);
    label.setText(s.length() == 0 ? none : s);
  }
  
  protected BObject doSaveValue(BObject facetsValue, Context cx)
    throws Exception
  {
    return permissions;
  }

////////////////////////////////////////////////////////////////
// Events
////////////////////////////////////////////////////////////////

  public void doEditPressed()
  {
    try
    { 
      Context cx = getCurrentContext();
      BPermissionsEditor editor = new BPermissionsEditor();
      editor.loadValue(permissions, cx);
      BGridPane grid = new BGridPane(1);
      grid.add(null, editor);
      
      if (BDialog.OK == BDialog.open(this, lex.getText("permissionsFE.editPermissions"), 
        new BBorderPane(grid), BDialog.OK_CANCEL))
      {
        loadValue(editor.saveValue(cx), cx);
        setModified();
      }
    }
    catch (Exception e) { BDialog.error(this, "Error", "Error", e); }
  }
       
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  static Lexicon lex = Lexicon.make("workbench");
  static String none = lex.getText("permissionsFE.none");
  
  BLabel label;
  BPermissions permissions;
}
