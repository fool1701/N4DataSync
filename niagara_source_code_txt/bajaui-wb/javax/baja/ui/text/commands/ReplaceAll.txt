/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import java.util.*;

import javax.baja.ui.*;
import javax.baja.ui.commands.*;
import javax.baja.ui.text.*;

/**
 * ReplaceAll
 *
 * @author    Mike Jarmy
 * @creation  20 Sep 03
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:38 AM EST$
 * @since     Baja 1.0
 */
public class ReplaceAll
  extends TextEditorCommand
{ 

  public ReplaceAll(BTextEditor editor, String replaceWith)
  {
    super(editor, "null");                                
    this.replaceWith = replaceWith;
  }  

  public CommandArtifact doInvoke()
  {
    ArrayList<CommandArtifact> list = new ArrayList<>();
    
    while(findNext()) 
    {                              
      Position pos1 = editor.getSelection().getAnchor();
      list.add(new InsertText(editor, replaceWith).doInvoke());
      Position pos2 = editor.getCaretPosition();
      editor.getSelection().select(pos1, pos2);
    }                                                 
    count = list.size();

    return new CompoundCommand.Artifact(
      list.toArray(new CommandArtifact[list.size()]));
  }

  private boolean findNext()
  {
    FindNext cmd = new FindNext(editor);
    cmd.doInvoke();
    return (cmd.getFoundPosition() != null);
  }
                    
  public int getCount() { return count; }
                    
  private String replaceWith;
  private int count;
}

