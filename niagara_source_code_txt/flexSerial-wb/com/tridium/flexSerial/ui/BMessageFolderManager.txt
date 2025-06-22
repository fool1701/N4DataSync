/*
 * Copyright 2005 Tridium, All Rights Reserved.
 */
package com.tridium.flexSerial.ui;

import javax.baja.control.*;
import javax.baja.driver.*;
import javax.baja.driver.point.*;
import javax.baja.driver.ui.point.*;
import javax.baja.gx.*;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.util.UiLexicon;
import javax.baja.util.*;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.mgr.*;
import javax.baja.workbench.mgr.MgrController.IMgrCommand;
import javax.baja.workbench.mgr.MgrController.MgrCommand;
import javax.baja.workbench.mgr.folder.*;

import com.tridium.flexSerial.*;
import com.tridium.flexSerial.messages.*;
import com.tridium.flexSerial.point.*;
import com.tridium.flexSerial.ui.FlexMessageManagerController.CalcOffsets;

/**
 * BMessageFolderManager
 *
 * @author    Andy Saunders
 * @creation  19-Apr-05
 * @version   $Revision$ $Date: 2/9/2005 4:52:47 PM$
 */
@NiagaraType(
  agent = @AgentOn(
    types = "flexSerial:FlexMessageFolder",
    requiredPermissions = "r"
  )
)
public class BMessageFolderManager
  extends BAbstractManager
{                
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.ui.BMessageFolderManager(496799563)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMessageFolderManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BMessageFolderManager()
  {
  }
  
  public void doLoadValue(BObject value, Context cx)
  {
    super.doLoadValue(value, cx);
    relayout();
    flexTarget = (BFlexMessageFolder)value;
  }
  
  
////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

  protected MgrModel makeModel() { return new Model(this); }
  protected MgrController makeController() { return new Controller(this); }
                                        
////////////////////////////////////////////////////////////////
// Model
////////////////////////////////////////////////////////////////
  
  class Model extends MgrModel
  {
    Model(BMessageFolderManager manager) { super(manager); }
    
    protected MgrColumn[] makeColumns()
    {        
      return cols;   
    }


    public MgrTypeInfo[] getNewTypes()
    {
      return new MgrTypeInfo[] 
      { 
        MgrTypeInfo.make(BFlexMessage.TYPE),
      };
    } 

    public int getSubscribeDepth()
    {           
      return 1;
    }  

    public Type[] getIncludeTypes()
    {
      return new Type[] 
      { 
        BFlexMessage.TYPE ,
      };
    }

    public boolean accept(BComponent comp)
    {                                 
      return ( comp instanceof BFlexMessage );
    }


  }                                 
                               
////////////////////////////////////////////////////////////////
// Controller
////////////////////////////////////////////////////////////////

  class Controller extends MgrController
  {             
    Controller(BMessageFolderManager mgr) 
    { 
      super(mgr); 
      this.flexManager = mgr;
      this.updateInstances = new UpdateInstances(mgr);
      updateInstances.setFlags(BARS | POPUP);
      updateInstances.setEnabled(true);

    }

    MgrCommand updateInstances;
    BMessageFolderManager flexManager;
    

    /////////////////////////////////////////////////////////////////
    // Methods - Public and in alphabetical order by method name.
    /////////////////////////////////////////////////////////////////

    protected IMgrCommand[] makeCommands()
    {
      
      return new IMgrCommand[] 
      { 
        newCommand,
        edit,
        updateInstances
      };      
    }

    
    
    public void cellDoubleClicked(BMgrTable table, BMouseEvent event, int row, int col)
    {
      BComponent comp = getTable().getComponentAt(row);
      BWbShell shell = getManager().getWbShell();
      shell.hyperlink(new HyperlinkInfo(comp.getNavOrd(), event));
      return;
    }
    
    class UpdateInstances extends MgrCommand
    {
      UpdateInstances(BWidget owner) { super(owner, lexicon, "updateInstances"); }
      
      public CommandArtifact doInvoke() 
      throws Exception 
      {
        flexTarget.updateMessageInstances(); 
        return null; 
      }
    }
    

    
  }                 
                         
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  BFlexMessageFolder flexTarget;


  static Lexicon lex = Lexicon.make(BMessageFolderManager.class); 
  static final UiLexicon lexicon = UiLexicon.makeUiLexicon(BMessageFolderManager.class);
 
  // base class columns
  MgrColumn[] cols = 
  { 
    new MgrColumn.Path(MgrColumn.UNSEEN),
    new MgrColumn.Name(),
    new MgrColumn.Prop(BFlexMessage.description, MgrColumn.EDITABLE),
  };
  
}
