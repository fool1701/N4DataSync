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
import javax.baja.util.*;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.mgr.*;
import javax.baja.workbench.mgr.folder.*;

import com.tridium.flexSerial.*;
import com.tridium.flexSerial.messages.*;
import com.tridium.flexSerial.point.*;

/**
 * BMessageBlockManager
 *
 * @author    Andy Saunders
 * @creation  19-Apr-05
 * @version   $Revision$ $Date: 2/9/2005 4:52:47 PM$
 */
@NiagaraType(
  agent = @AgentOn(
    types = "flexSerial:FlexMessageBlockFolder",
    requiredPermissions = "r"
  )
)
public class BMessageBlockManager
  extends BAbstractManager
{                
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.ui.BMessageBlockManager(172611655)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMessageBlockManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BMessageBlockManager()
  {
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
    Model(BMessageBlockManager manager) { super(manager); }
    
    protected MgrColumn[] makeColumns()
    {        
      return cols;   
    }


    public MgrTypeInfo[] getNewTypes()
    {
      return new MgrTypeInfo[] 
      { 
        MgrTypeInfo.make(BFlexMessageBlock.TYPE),
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
        BFlexMessageBlock.TYPE ,
      };
    }

    public boolean accept(BComponent comp)
    {                                 
      return ( comp instanceof BFlexMessageBlock );
    }


  }                                 
                               
////////////////////////////////////////////////////////////////
// Controller
////////////////////////////////////////////////////////////////

  class Controller extends MgrController
  {             
    Controller(BMessageBlockManager mgr) { super(mgr); }

    public void cellDoubleClicked(BMgrTable table, BMouseEvent event, int row, int col)
    {
      BComponent comp = getTable().getComponentAt(row);
      BWbShell shell = getManager().getWbShell();
      shell.hyperlink(new HyperlinkInfo(comp.getNavOrd(), event));
      return;
    }
    
    
  }                 
                         
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static Lexicon lex = Lexicon.make(BMessageBlockManager.class); 
  
  // base class columns
  MgrColumn[] cols = 
  { 
    new MgrColumn.Path(MgrColumn.UNSEEN),
    new MgrColumn.Name(),
    new MgrColumn.Prop(BFlexMessageBlock.description, MgrColumn.EDITABLE),
  };
  
  
}
