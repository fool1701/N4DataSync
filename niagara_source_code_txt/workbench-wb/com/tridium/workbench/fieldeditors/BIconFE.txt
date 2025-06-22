/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BImage;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdList;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BSpace;
import javax.baja.sys.BIcon;
import javax.baja.sys.BModuleSpace;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.file.BFileChooser;
import javax.baja.ui.file.ExtFileFilter;
import javax.baja.ui.pane.BGridPane;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BIconFE edits BIcon's.
 *
 * @author    Andy Frank
 * @creation  11 May 04
 * @version   $Revision: 12$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:Icon"
  )
)
public class BIconFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BIconFE(3710796082)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BIconFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BIconFE()
  {
    field  = new BTextField("", 60);
    button = new BButton(new Browse(this));
    button.setButtonStyle(BButtonStyle.toolBar);

    BGridPane grid = new BGridPane(2);
    grid.setStretchColumn(0);
    grid.add(null, field);
    grid.add(null, button);
    setContent(grid);
    
    linkTo("lk0", field, BTextField.textModified, setModified);
    linkTo("lk1", field, BTextField.actionPerformed, actionPerformed);
  }

////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////

  public void setEnabled(boolean v)
  {
    super.setEnabled(v);
    field.setEnabled(v);
    button.setEnabled(v);
  }
  
////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  protected void doSetReadonly(boolean readonly)
  {
    field.setEditable(!readonly);
    button.setEnabled(!readonly);
  }  

  protected void doLoadValue(BObject v, Context cx)
  {
    BIcon icon = (BIcon)v;
    BOrdList list = icon.getOrdList();
    BOrd ord = (list.size() > 0) ? list.get(0) : BOrd.NULL;
    field.setText(ord.toString());
  }
  
  protected BObject doSaveValue(BObject v, Context cx)
  {
    if (field.getText().length() == 0) return BIcon.DEFAULT;
    
    BOrd ord = BOrd.make(field.getText());
    if (!ord.isNull()) ord = ord.relativizeToSession();
    return BIcon.make(ord);
  }

////////////////////////////////////////////////////////////////
// Browse
////////////////////////////////////////////////////////////////

  class Browse extends Command
  {
    public Browse(BWidget owner) { super(owner, ""); }
    public BImage getIcon() { return browseIcon; }
    public CommandArtifact doInvoke()
    {
      if (chooser == null)
      {
        chooser = BFileChooser.makeOpen(getOwner());
        chooser.addFilter(ExtFileFilter.images);        
        chooser.setSpaces(new BSpace[] { BModuleSpace.INSTANCE });
      }
      
      String s = field.getText();
      if (s.length() == 0 || s.equals("null"))
        chooser.setCurrentDirectory(BOrd.make("module://icons/x16"));
      else
      {
        try { chooser.setCurrentDirectory(BOrd.make(field.getText())); }
        catch (Exception e) {} // ignore
      }
      
      BOrd ord = chooser.show();
      if (ord != null) field.setText(ord.relativizeToSession().toString());
      return null;
    }
  }
  static BFileChooser chooser;
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  static BImage browseIcon = BImage.make("module://icons/x16/open.png");  
  BTextField field;
  BButton button;
}
