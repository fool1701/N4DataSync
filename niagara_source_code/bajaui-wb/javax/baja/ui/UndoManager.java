/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.util.*;
import javax.baja.ui.util.*;

/**
 * UndoManager is responsible for managing the queue
 * of undoable and redoable Commands which invoked in
 * the context of a BWidget.
 *
 * @author    Brian Frank
 * @creation  14 Jul 01
 * @version   $Revision: 9$ $Date: 11/22/06 4:41:17 PM EST$
 * @since     Baja 1.0
 */
public class UndoManager
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct an UndoManager for the specified BWidget.
   */
  public UndoManager(BWidget owner)
  {
    if (owner == null) throw new NullPointerException("Null owner");
    this.owner = owner;
    this.undoCommand = new UndoCommand(owner);
    this.redoCommand = new RedoCommand(owner);
  }

////////////////////////////////////////////////////////////////
// Artifacts
////////////////////////////////////////////////////////////////

  /**
   * Get the limit of artifacts to remember.
   */
  public int getMaxArtifacts()
  {
    return maxArtifacts;
  }

  /**
   * Set the limit of artifacts to remember.
   */
  public synchronized void setMaxArtifacts(int maxArtifacts)
  {
    if (undos.size() > maxArtifacts)
    {
      while(undos.size() > maxArtifacts) undos.remove(0);
      redos.clear();
    }
    this.maxArtifacts = maxArtifacts;
  }

  /**
   * Get the undo command for this owner.
   */
  public Command getUndoCommand()
  {
    return undoCommand;
  }

  /**
   * Get the redo command for this owner.
   */
  public Command getRedoCommand()
  {
    return redoCommand;
  }
  
  /**
   * Perform an undo operation.
   */
  public synchronized void undo()
  {
    if (undos.size() == 0)
      return;
    
    CommandArtifact artifact = undos.pop();
    redos.push(artifact);
    
    try
    {
      artifact.undo();
    }
    catch(Throwable e)
    {
      e.printStackTrace();
      String title = UiLexicon.bajaui().getText("dialog.error");
      String msg = UiLexicon.bajaui().getText("undo.error");
      BDialog.error(owner, title, msg, e);
    }
        
    checkCommandEnables();
  }

  /**
   * Perform an undo operation.
   */
  public synchronized void redo()
  {
    if (redos.size() == 0)  
      return;

    CommandArtifact artifact = redos.pop();
    undos.push(artifact);

    try
    {
      artifact.redo();
    }
    catch(Throwable e)
    {
      e.printStackTrace();
      String title = UiLexicon.bajaui().getText("dialog.error");
      String msg = UiLexicon.bajaui().getText("redo.error");
      BDialog.error(owner, title, msg, e);
    }
    
    checkCommandEnables();
  }
  
  /**
   * Add an artifact into the 
   */
  public synchronized void addArtifact(CommandArtifact artifact)
  {
    if (undos.size() > maxArtifacts) undos.remove(0);
    undos.add(artifact);
    redos.clear();
    checkCommandEnables();
  }
  
  /**
   * Discard all the queued artifacts.
   */
  public synchronized void discardAllArtifacts()
  {
    undos.clear();
    redos.clear();
    checkCommandEnables();    
  }
    
  /**
   * Insure the command are enabled correctly.
   */
  private void checkCommandEnables()
  {
    undoCommand.setEnabled(undos.size() > 0);
    redoCommand.setEnabled(redos.size() > 0);
  }

////////////////////////////////////////////////////////////////
// UndoCommand
////////////////////////////////////////////////////////////////  

  class UndoCommand extends Command
  {
    UndoCommand(BWidget owner) 
    { 
      super(owner, UiLexicon.bajaui().module, "commands.undo"); 
      setEnabled(false);
    }
    
    public CommandArtifact doInvoke()
      { undo(); return null; }   
  }

////////////////////////////////////////////////////////////////
// RedoCommand
////////////////////////////////////////////////////////////////  

  class RedoCommand extends Command
  {
    RedoCommand(BWidget owner) 
    { 
      super(owner, UiLexicon.bajaui().module, "commands.redo"); 
      setEnabled(false);
    }
    
    public CommandArtifact doInvoke()
      { redo(); return null; }
  }
  
////////////////////////////////////////////////////////////////
// Scope
////////////////////////////////////////////////////////////////  

  public interface Scope
  {
    public UndoManager getInstalledUndoManager();
    public void setInstalledUndoManager(UndoManager undoManager);
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  protected final BWidget owner;
  private UndoCommand undoCommand;
  private RedoCommand redoCommand;
  private Stack<CommandArtifact> undos = new Stack<>();
  private Stack<CommandArtifact> redos = new Stack<>();
  private int maxArtifacts = 10;
  
}
