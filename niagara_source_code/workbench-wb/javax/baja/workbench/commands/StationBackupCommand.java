/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.util.UiLexicon;

/**
 * StationBackupCommand is used to invoke a backup using the BackupService.
 *
 * @author    Brian Frank
 * @creation  13 Apr 05
 * @version   $Revision: 3$ $Date: 6/11/07 12:41:49 PM EDT$
 * @since     Baja 1.0
 */
public class StationBackupCommand
  extends Command
{ 

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Construct StationBackupCommand using a base object that 
   * lives within the session such that the BackupService ord 
   * may be resolved using it as the base. 
   */
  public StationBackupCommand(BWidget owner, BObject base)
  {
    super(owner, UiLexicon.bajaui(), "commands.stationBackup");
    this.base = base;
  }

////////////////////////////////////////////////////////////////
// Implementation
////////////////////////////////////////////////////////////////  

  public CommandArtifact doInvoke()
    throws Exception
  {                           
    // use reflection so that we don't have a dependency
    Class<?> cls = Sys.loadClass("backup", "com.tridium.backup.ui.BBackupManager");
    Method method = cls.getMethod("stationBackupCommand", new Class<?>[] { BWidget.class, BObject.class });
    try
    {
      method.invoke(null, new Object[] { getOwner(), base });
    }
    catch (InvocationTargetException ite)
    {
      if (ite.getTargetException() instanceof Exception)
      {
        throw (Exception)ite.getTargetException();
      }
      else
      {
        throw ite;
      }
    }
    return null;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  BObject base;
    
}

