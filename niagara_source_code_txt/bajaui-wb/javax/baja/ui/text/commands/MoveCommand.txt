/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * MoveCommand is the abstract base class for command
 * which move the cursor.  If the shift key is held
 * during a move command then the text between the anchor 
 * position and new position is also selected. Move 
 * commands are optionally undoable if the undoNavigation 
 * property is true in the BTextEditorOptions.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 4$ $Date: 9/4/07 10:57:29 AM EDT$
 * @since     Baja 1.0
 */
public abstract class MoveCommand
  extends TextEditorCommand
{ 

  MoveCommand(BTextEditor editor, Property keyBinding)
  {
    super(editor, keyBinding);
  }  

  MoveCommand(BTextEditor editor, String keyName)
  {
    super(editor, keyName);
  }  
  
  public boolean isShiftDown()
  { 
    return shiftDown;
  }
  
  public void setShiftDown(boolean shiftDown)
  {
    this.shiftDown = shiftDown;
  }

  public CommandArtifact move(Position oldPos, Position newPos) 
  {
    // clip to a valid position
    newPos = clip(newPos);
    
    // if no change, then don't do anything
    if (oldPos.equals(newPos)) return null;
    
    // use the MoveCaretArtifact to move the caret
    MoveCaretArtifact artifact = new MoveCaretArtifact(editor, oldPos, newPos);
    artifact.redo();
    
    // if the shift key is down, then this move 
    // includes a selection change
    if (shiftDown)
    {
      Position anchor = editor.getSelection().getAnchor();
      if (anchor == null) anchor = oldPos;
      editor.getSelection().select(anchor, newPos);
    }
    else
    {
      editor.getSelection().deselect();
    }
    
    // optionally return an artifact for undo
    if (editor.getOptions().getUndoNavigation())
      return artifact;
    else
      return null;
  }

  Position clip(Position pos)
  {
    int lineCount = editor.getModel().getLineCount();
    int q = pos.line;
    int c = pos.column;
    
    if (q > lineCount-1) q = lineCount-1;
    if (q < 0) q = 0;
        
    if (q == lineCount-1)
    { 
      int colCount = editor.getModel().getLine(q).getColumnCount();
      if (c > colCount) c = colCount;
    }
    if (c < 0) c = 0;
    
    return new Position(q, c);
  }

  static class MoveCaretArtifact implements CommandArtifact
  {
    MoveCaretArtifact(BTextEditor editor, Position oldPos, Position newPos) 
    { 
      this.editor = editor;
      this.oldPos = oldPos;
      this.newPos = newPos;
    }
    
    public void redo() { editor.moveCaretPosition(newPos); }
    
    public void undo() { editor.moveCaretPosition(oldPos); }
    
    private BTextEditor editor;
    private Position oldPos;
    private Position newPos;    
  }
  
  private boolean shiftDown;
}

