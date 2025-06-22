/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;

import javax.baja.ui.*;
import javax.baja.ui.commands.*;
import javax.baja.ui.text.commands.*;

/**
 * TextCommandFactory is responsible for accessing the
 * predefined TextCommands for a BTextWidget and for 
 * mapping key events to TextCommands.
 *
 * @author    Brian Frank       
 * @creation  9 Dec 00
 * @version   $Revision: 12$ $Date: 11/14/07 11:33:10 AM EST$
 * @since     Baja 1.0
 */
public class TextCommandFactory
  extends BTextEditor.TextSupport
{ 

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////  

  public static final String MOVE_UP        = "moveUp";
  public static final String MOVE_DOWN      = "moveDown";
  public static final String MOVE_LEFT      = "moveLeft";
  public static final String MOVE_RIGHT     = "moveRight";
  public static final String PAGE_UP        = "pageUp";
  public static final String PAGE_DOWN      = "pageDown";
  public static final String LINE_START     = "lineStart";
  public static final String LINE_END       = "lineEnd";
  public static final String DOCUMENT_START = "documentStart";
  public static final String DOCUMENT_END   = "documentEnd";
  public static final String WORD_LEFT      = "wordLeft";
  public static final String WORD_RIGHT     = "wordRight";
  public static final String END            = "redo";
  public static final String CUT            = "cut";
  public static final String COPY           = "copy";
  public static final String PASTE          = "paste";
  public static final String CUT2           = "cut2";
  public static final String COPY2          = "copy2";
  public static final String PASTE2         = "paste2";
  public static final String UNDO           = "undo";
  public static final String REDO           = "redo";
  public static final String DELETE         = "delete";
  public static final String BACKSPACE      = "backspace";
  public static final String DELETE_WORD    = "deleteWord";
  public static final String CUT_LINE       = "cutLine";
  public static final String TAB_FORWARD    = "tabForward";
  public static final String TAB_BACK       = "tabBack";
  public static final String TOGGLE_SLASH_SLASH = "toggleSlashSlash";
  public static final String FIND           = "find";
  public static final String FIND_NEXT      = "findNext";
  public static final String FIND_PREV      = "findPrev";
  public static final String REPLACE        = "replace"; 
  public static final String GOTO           = "goTo"; 
  public static final String WORD_WRAP      = "wordWrap";
  public static final String RELOAD_MACROS  = "reloadMacros";
  public static final String SELECT_ALL     = "selectAll";
  
////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Make a command using one of the predefined id constants.
   * If the predefinedId passed does not map to a valid
   * command then return null.
   */
  public Command make(String predefinedId)
  {
    BTextEditor editor = getEditor();
    if (editor.getShell() == null) return null;
    
    String id = predefinedId.intern();
    switch(id.charAt(0))
    {
      case 'b':
        if (id == BACKSPACE) return new Backspace(editor);
        return null;
      case 'c':
        if (id == CUT) return new CutCommand(editor);
        if (id == CUT2) return new CutCommand(editor);
        if (id == COPY) return new CopyCommand(editor);
        if (id == COPY2) return new CopyCommand(editor);
        if (id == CUT_LINE) return new CutLine(editor);
        return null;
      case 'd':
        if (id == DOCUMENT_START) return new DocumentStart(editor);
        if (id == DOCUMENT_END) return new DocumentEnd(editor);
        if (id == DELETE) return new Delete(editor);
        if (id == DELETE_WORD) return new DeleteWord(editor);
        return null;
      case 'f':
        if (id == FIND) return new Find(editor);
        if (id == FIND_NEXT) return new FindNext(editor);
        if (id == FIND_PREV) return new FindPrev(editor);
        return null;
      case 'g':
        if (id == GOTO) return new Goto(editor);
        return null;
      case 'l':
        if (id == LINE_START) return new LineStart(editor);
        if (id == LINE_END) return new LineEnd(editor);
        return null;
      case 'm':
        if (id == MOVE_UP) return new MoveUp(editor);
        if (id == MOVE_DOWN) return new MoveDown(editor);
        if (id == MOVE_LEFT) return new MoveLeft(editor);
        if (id == MOVE_RIGHT) return new MoveRight(editor);
        return null;
      case 'p':
        if (id == PASTE) return new PasteCommand(editor);
        if (id == PASTE2) return new PasteCommand(editor);
        if (id == PAGE_DOWN) return new PageDown(editor);
        if (id == PAGE_UP) return new PageUp(editor);
        return null;
      case 'r':
        if (id == REDO) return editor.getUndoManager().getRedoCommand();
        if (id == REPLACE) return new Replace(editor);
        if (id == RELOAD_MACROS) return new ReloadMacros(editor);
        return null;
      case 's':
        if (id == SELECT_ALL) return new SelectAll(editor);
        return null;
      case 't':
        if (id == TAB_FORWARD) return new TabForward(editor);
        if (id == TAB_BACK) return new TabBack(editor);
        if (id == TOGGLE_SLASH_SLASH) return new ToggleSlashSlash(editor);
        return null;
      case 'u':
        if (id == UNDO) return editor.getUndoManager().getUndoCommand();
        return null;
      case 'w':
        if (id == WORD_LEFT) return new WordLeft(editor);
        if (id == WORD_RIGHT) return new WordRight(editor);
        if (id == WORD_WRAP) return new WordWrap(editor);
        return null;
    }
    
    return null;
  }

}
