/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * ToggleSlashSlash toggles the C++/Java "//" comment
 * on and off from the beginning of selected lines.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:39 AM EST$
 * @since     Baja 1.0
 */
public class ToggleSlashSlash
  extends TextEditorCommand
{ 

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public ToggleSlashSlash(BTextEditor editor)
  {
    super(editor, BKeyBindings.toggleSlashSlash);    
  }  

////////////////////////////////////////////////////////////////
// Invoke
////////////////////////////////////////////////////////////////  

  public CommandArtifact doInvoke()
  {
    if (editor.getSelection().isEmpty()) return null;
    Artifact art = new Artifact(editor);
    return art.invoke();
  }

////////////////////////////////////////////////////////////////
// Artifact
////////////////////////////////////////////////////////////////  

  static class Artifact implements CommandArtifact
  { 
  
    Artifact(BTextEditor editor)
    {
      Position start = editor.getSelection().getStart();
      Position end = editor.getSelection().getEnd();
      this.editor = editor;
      this.line0 = start.line;
      this.line1 = end.column > 0 ? end.line : end.line-1;
    }

    Artifact invoke()
    {
      // create insert buffer
      String text = toggle(getLines());

      // remove old lines
      Position rs = new Position(line0, 0);
      Position re = new Position(line1, editor.getModel().getLine(line1).getColumnCount());
      editor.getModel().remove(rs, re);
      
      // insert new tet
      editor.getModel().insert(rs, text);
      return this;
    }
    
    private String toggle(String[] lines)
    {
      boolean comment = !lines[0].startsWith("//");
      StringBuilder s = new StringBuilder();
      for(int i=0; i<lines.length; ++i)
      {
        String line = lines[i];
        
        int j = 0;
        if (comment)
        {
          if (!line.startsWith("//"))  s.append('/').append('/');
        }
        else
        {
          if (line.startsWith("//")) j = 2;
        }
        for(; j<line.length(); ++j) s.append(line.charAt(j));  
      }
      return s.toString();
    }

    private String[] getLines()
    {
      String[] lines = new String[line1-line0+1];
      for(int i=0; i<lines.length; ++i)
        lines[i] = new String(editor.getModel().getLine(i+line0).buffer);
      return lines;
    }

    public void undo() { invoke(); }
    public void redo() { invoke(); }
    
    BTextEditor editor;
    int line0, line1;
  }  

}

