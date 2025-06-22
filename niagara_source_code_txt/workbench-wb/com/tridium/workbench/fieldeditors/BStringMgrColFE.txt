/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BImage;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComplex;
import javax.baja.sys.BString;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BToolBar;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.pane.BGridPane;
import javax.baja.workbench.mgr.BMgrEditDialog;
import javax.baja.workbench.mgr.MgrEditRow;

import com.tridium.workbench.util.WbUtil;

/**
 * BStringMgrColFE is a specialized string field editor for use in 
 * MgrEditDialogs because it is smart enough to provide batch search/replace.
 *
 * @author    Brian Frank
 * @creation  12 May 05
 * @version   $Revision: 6$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BStringMgrColFE
  extends BStringFE
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BStringMgrColFE(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStringMgrColFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  // For framework use
  public BStringMgrColFE()
  {
    this(0);
  }
    
  public BStringMgrColFE(int colIndex)
  {                                   
    this.colIndex = colIndex;
  }

////////////////////////////////////////////////////////////////
// StringFE
////////////////////////////////////////////////////////////////

  protected void doSetReadonly(boolean readonly)
  {                      
    super.doSetReadonly(readonly);
    replace.setEnabled(!readonly);
  }

  protected void setEditor(BWidget editor)
  {    
    BToolBar buttons = new BToolBar();
    buttons.add("a", replace);
    
    BGridPane grid = new BGridPane(2);
    grid.add("a", editor);
    grid.add("b", buttons);
    
    setContent(grid);
  }

////////////////////////////////////////////////////////////////
// ReplaceCommand
////////////////////////////////////////////////////////////////
 
  class ReplaceCommand extends Command
  {     
    ReplaceCommand(BWidget owner)
    {
      super(owner, null);
      icon = replaceIcon;
    }       
    
    public CommandArtifact doInvoke()
      throws Exception
    {                         
      BMgrEditDialog dialog = getMgrEditDialog();
      if (dialog == null) return null;
      
      MgrEditRow[] rows = dialog.getSelectedRows();     
      if (rows.length == 0) return null;
      
      String[] oldVals = new String[rows.length];
      for(int i=0; i<oldVals.length; ++i)
        oldVals[i] = String.valueOf(rows[i].getCell(colIndex));
        
      WbUtil.BatchReplace batch = new WbUtil.BatchReplace(getOwner(), oldVals);
      String[] newVals = batch.prompt("Batch Search and Replace"); 
      if (newVals == null) return null;

      for(int i=0; i<rows.length; ++i)
        rows[i].setCell(colIndex, BString.make(newVals[i]));
      dialog.syncInputPane();
      
      return null;
    }    
  }                    
  
  BMgrEditDialog getMgrEditDialog()
  {             
    BComplex p = getParent();
    while(p != null)
    {
      if (p instanceof BMgrEditDialog) return (BMgrEditDialog)p;
      p = p.getParent();
    }                   
    return null;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final BImage replaceIcon = BImage.make("module://icons/x16/replace.png");
  
  Command replace = new ReplaceCommand(this);
  int colIndex;
}
