/*
 * Copyright 2003 Tridium, Inc.  All rights reserved.
 *
 */

package com.tridium.flexSerial.ui;

import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.mgr.*;
import javax.baja.workbench.mgr.MgrController.IMgrCommand;
import javax.baja.workbench.mgr.MgrController.MgrCommand;
import javax.baja.workbench.component.table.*;
import javax.baja.ui.event.*;
import javax.baja.util.Lexicon;
import com.tridium.flexSerial.messages.BIFlexMessageBlock;


/**
 * @author Andy Saunders
 * @creation 14 Sept 2005
 * @version $Revision: $ $Date: $
 */
public class FlexMessageManagerController
  extends MgrController
  //implements
{

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public FlexMessageManagerController(BFlexMessageManager mgr) 
  {
    super(mgr);
    this.flexManager = mgr;
    this.calcOffsets = new CalcOffsets(mgr);
    calcOffsets.setFlags(BARS | POPUP);
    calcOffsets.setEnabled(true);

  }

  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  protected IMgrCommand[] makeCommands()
  {
    
    return new IMgrCommand[] 
    { 
      newCommand,
      edit,
      calcOffsets
    };      
  }

  
  
/*
  public void updateCommands()
  { 
    newCommand.setFlags( 0 );
    edit.setFlags( 0 );
  }
*/

////////////////////////////////////////////////////////////////
//Discover
////////////////////////////////////////////////////////////////

 class CalcOffsets extends MgrCommand
 {
   CalcOffsets(BWidget owner) { super(owner, lex.getText("calcOffsets")); }
   
   public CommandArtifact doInvoke() 
   throws Exception 
   {
     if(flexManager == null) System.out.println("flexManager == null");
     if(flexManager.getTarget() == null) System.out.println("flexManager.getTarget() == null");
     flexManager.getFlexMessageBlock().calculateOffsets();
     return null; 
   }
 }

 /**
  * This is the callback when the discover command is invoked.
  * Discovery should always set the manager into learn mode.
  * If discovery options are available, then convention is to
  * prompt the user with a dialog for the options before kicking
  * off the discovery.  Standard practice is to use a Job to run
  * the discovery in the background by submiting the job via an
  * action that returns an BOrd and then calling MgrLearn.setJob().
  */
 public CommandArtifact doDiscover(Context cx)
   throws Exception
 {
   learnMode.setSelected(true);
   return null;
 }

 public MgrCommand calcOffsets;

 
  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

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

 BFlexMessageManager flexManager;
 
 
 static final Lexicon lex = Lexicon.make(FlexMessageManagerController.class);
 

}//FlexMessageManagerController
