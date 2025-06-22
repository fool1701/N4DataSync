/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.commands;

import javax.baja.naming.BOrd;
import javax.baja.sys.BObject;
import javax.baja.sys.BStation;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.util.UiLexicon;

/**
 * StationSaveCommand is used to invoke the Station.save action
 * to flush configuration and runtime data to persistent storage.
 *
 * @author    Brian Frank
 * @creation  13 Apr 05
 * @version   $Revision: 1$ $Date: 4/13/05 6:53:06 PM EDT$
 * @since     Baja 1.0
 */
public class StationSaveCommand
  extends Command
{ 

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Construct StationSaveCommand using a base object that 
   * lives within the session such that the station ord may
   * be resolved using it as the base. 
   */
  public StationSaveCommand(BWidget owner, BObject base)
  {
    super(owner, UiLexicon.bajaui(), "commands.stationSave");
    this.base = base;
  }

////////////////////////////////////////////////////////////////
// Implementation
////////////////////////////////////////////////////////////////  

  public CommandArtifact doInvoke()
    throws Exception
  {
    BStation station = (BStation)BOrd.make("station:|slot:/").get(base);
    station.save();
    return null;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  BObject base;
    
}

