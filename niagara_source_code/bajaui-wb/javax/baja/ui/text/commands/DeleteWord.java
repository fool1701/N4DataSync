/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * DeleteWord deletes all the characters from the caret
 * position to the end of the segment.
 *
 * @author    Brian Frank
 * @creation  12 Aug 01
 * @version   $Revision: 4$ $Date: 3/28/05 10:32:36 AM EST$
 * @since     Baja 1.0
 */
public class DeleteWord
  extends EditCommand
{ 

  public DeleteWord(BTextEditor editor)
  {
    super(editor, BKeyBindings.deleteWord);
  }  

  public CommandArtifact doInvoke()
  {
    Position from = editor.getCaretPosition();

    Position to = null;
    Line line = editor.getModel().getLine(from.line);
    Segment seg = line.getSegmentAt(from.column);
    if (seg != null)
    {
      to = new Position(from.line, seg.offset+seg.length);
    }
    else if (from.line+1 < editor.getModel().getLineCount())
    {
      // we may want to insert chars here so that we 
      // don't shift the mouse cursor back, but it seems
      // like a rare case and is a lot of work
      from = new Position(from.line, line.getColumnCountWithoutNewline());
      to = new Position(from.line, from.column+1);      
    }
    
    if (to != null) return remove(from, to);
    else return null;
  }
    
}

