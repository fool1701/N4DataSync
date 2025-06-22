/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

/**
 * CommandArtifact stores the state of a Command 
 * invokation used to provide undo and redo operations.
 *
 * @author    Brian Frank
 * @creation  14 Jul 01
 * @version   $Revision: 4$ $Date: 3/28/05 10:32:22 AM EST$
 * @since     Baja 1.0
 */
public interface CommandArtifact
{

  /**
   * Undo the command invocation.
   */
  public void undo() throws Exception;

  /**
   * Redo the command invocation.
   */
  public void redo() throws Exception;
  
}
