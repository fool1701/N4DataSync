/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.ui.history;

import javax.baja.driver.history.BArchiveDescriptor;
import javax.baja.nre.util.Array;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.ui.CommandArtifact;
import javax.baja.util.Lexicon;
import javax.baja.workbench.mgr.BAbstractManager;
import javax.baja.workbench.mgr.BMgrTable;
import javax.baja.workbench.mgr.MgrEdit;
import javax.baja.workbench.mgr.MgrEditRow;
import javax.baja.workbench.mgr.MgrLearn;
import javax.baja.workbench.mgr.MgrTypeInfo;
import javax.baja.workbench.mgr.folder.FolderController;

/**
 * ArchiveManagerController is the MgrController for the archive manager.
 *
 * @author    John Sublett
 * @creation  14 Jan 2004
 * @version   $Revision: 8$ $Date: 5/19/09 2:54:57 PM EDT$
 * @since     Baja 1.0
 */
public class ArchiveManagerController
  extends FolderController
{                 
 
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
 
  public ArchiveManagerController(BArchiveManager manager) 
  { 
    super(manager); 
    this.archive = new ArchiveCommand(manager);
    cancel.setFlags(0);   
    archive.setFlags(BARS | POPUP);
  }

////////////////////////////////////////////////////////////////
// MgrController
////////////////////////////////////////////////////////////////

  /**
   * For exports, the discovery can always be handled in a common way.
   * For imports, it entirely specific to the driver.
   */
  public CommandArtifact doDiscover(Context cx)    
    throws Exception
  {                                           
    super.doDiscover(cx);
    
    // if the learn is a HistoryLearn, try some extra stuff
    MgrLearn mgrLearn = getManager().getLearn();
    if (mgrLearn instanceof HistoryLearn)
    {
      HistoryLearn learn = (HistoryLearn)mgrLearn;
      if (learn.getJobBar() == null)
      {
        learn.updateRoots(learn.getDiscovery((BComponent)getManager().getCurrentValue()));
      }
    }
    
    return null;
  }                       
  
  /**
   * Make the commands.
   */
  protected IMgrCommand[] makeCommands()
  {               
    IMgrCommand[] cmds = super.makeCommands();
    
    if (!((BArchiveManager)getManager()).supportsArchiveFolders())
    { // If Folders aren't supported by the driver yet, don't include
      // the 'New Folder' command.
      Array<IMgrCommand> stripped = new Array<>(IMgrCommand.class, cmds.length);
      for (int i = 0; i < cmds.length; i++)
      {
        if ((cmds[i] != newFolder) && (cmds[i] != allDescendants))
          stripped.add(cmds[i]);
      }
      cmds = stripped.trim();
    }
    
    return append(cmds, new IMgrCommand[] { archive });
  }              

  /**
   * Update the command states.
   */
  public void updateCommands()
  {        
    super.updateCommands();     
           
    BMgrTable table = getManager().getModel().getTable();      
    archive.setEnabled(table != null && !table.getSelection().isEmpty());
  }

////////////////////////////////////////////////////////////////
// MgrEdit
////////////////////////////////////////////////////////////////

  /**
   * Make an instance of MgrEdit to use.
   */
  public MgrEdit makeEdit(String label)
  {                                     
    return new ArchiveEdit(getManager(), label);
  }

  private class ArchiveEdit
    extends MgrEdit
  {
    public ArchiveEdit(BAbstractManager manager, String label)
    {
      super(manager, label);
    }
    
    /**
     * This is a callback for making an instance of a 
     * MgrEditRow for the specified target.
     */
    protected MgrEditRow makeRow(BComponent target, Object discovery, MgrTypeInfo[] types)
      throws Exception
    {
      target.lease(3);
      return new MgrEditRow(target, discovery, types);
    }
  }

////////////////////////////////////////////////////////////////
// ArchiveCommand
////////////////////////////////////////////////////////////////

  class ArchiveCommand
    extends MgrCommand
  {
    ArchiveCommand(BArchiveManager owner)
    {
      super(owner, lex, "archive.command.archive");
    }
    
    public CommandArtifact doInvoke()
    {
      BComponent[] sel = getManager().getModel().getTable().getSelectedComponents();
      
      for (int i = 0; i < sel.length; i++)
      {
        if (sel[i] instanceof BArchiveDescriptor)
          ((BArchiveDescriptor)sel[i]).execute();
      }
      
      return null;
    }
  }      
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static Lexicon lex = Lexicon.make("driver");

  public final MgrCommand archive;
  
}