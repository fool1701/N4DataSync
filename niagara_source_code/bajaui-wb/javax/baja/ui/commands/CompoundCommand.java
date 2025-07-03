/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.commands;

import javax.baja.ui.*;

/**
 * CompoundCommand invokes an array of Commands as one group.
 *
 * @author    Mike Jarmy
 * @creation  17 Sep 03
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:23 AM EST$
 * @since     Baja 1.0
 */
public class CompoundCommand extends Command
{
////////////////////////////////////////////////////////////////
// constructor
////////////////////////////////////////////////////////////////

  /**
   * Create a new command that will invoke a group of commands
   */
  public CompoundCommand(BWidget owner, String label, Command[] commands)
  {
    super(owner, label);
    this.commands = commands;
  }

////////////////////////////////////////////////////////////////
// Command
////////////////////////////////////////////////////////////////

  /**
   * Invoke the action.
   */
  public CommandArtifact doInvoke()
    throws Exception
  {                         
    CommandArtifact[] artifacts = new CommandArtifact[commands.length];
    for (int i = 0; i < commands.length; i++)
    {
      artifacts[i] = commands[i].doInvoke();      
    }
    return new Artifact(artifacts);
        
//    Artifact a = new Artifact(artifacts);
//    a.redo();
//    return a;
  }

////////////////////////////////////////////////////////////////
// Artifact
////////////////////////////////////////////////////////////////

  public static class Artifact implements CommandArtifact
  {
    public Artifact(CommandArtifact[] artifacts)    
    {
      this.artifacts = artifacts;
    } 
    
    public void undo() throws Exception
    {
      for (int i = artifacts.length-1; i >= 0; i--)
      {
        if (artifacts[i] != null) artifacts[i].undo();
      }      
    }
    
    public void redo() throws Exception
    {
      for (int i = 0; i < artifacts.length; i++)
      {
        if (artifacts[i] != null) artifacts[i].redo();
      }            
    }    
    
    private CommandArtifact[] artifacts;  
  }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

  private Command[] commands;
}
