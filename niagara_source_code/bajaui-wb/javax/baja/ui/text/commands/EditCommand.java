/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.sys.Property;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.commands.CompoundCommand;
import javax.baja.ui.text.BTextEditor;
import javax.baja.ui.text.Position;
import javax.baja.ui.text.TextController;
import javax.baja.ui.text.TextModel;
import javax.baja.ui.text.TextSelection;

/**
 * EditCommand is the abstract base class for commands
 * which edit the actual text document either through
 * an insert, remove, or both.  All inserts will
 * automatically removed the currently selected text.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 6$ $Date: 3/28/05 10:32:37 AM EST$
 * @since     Baja 1.0
 */
public abstract class EditCommand
  extends TextEditorCommand
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  EditCommand(BTextEditor editor, Property keyBinding)
  {
    super(editor, keyBinding);
  }

  EditCommand(BTextEditor editor, String keyName)
  {
    super(editor, keyName);
  }

////////////////////////////////////////////////////////////////
// Implementation
////////////////////////////////////////////////////////////////

  CommandArtifact insert(char[] text)
  {
    if (!editor.isEditable()) return null;

    if (log.isLoggable(Level.FINE))
    {
      log.fine("-- insert: " + TextModel.toString(text));
    }

    // if anything selected then remove that
    RemoveArtifact remove = removeSelection();
    if (remove != null) remove.redo();

    // create our insert artifact
    Position pos = editor.getCaretPosition();
    InsertArtifact insert = new InsertArtifact(this, pos, text);
    insert.redo();

    // wrap them both
    return new CompoundCommand.Artifact(new CommandArtifact[] { remove, insert});
  }

  CommandArtifact remove(Position from, Position to)
  {
    if (!editor.isEditable()) return null;

    if (log.isLoggable(Level.FINE))
    {
      log.fine("-- remove");
    }

    // if anything selected then remove that
    RemoveArtifact remove = removeSelection();

    // otherwise remove the specified text
    if (remove == null)
    {
      if (from == null || to == null) return null;
      remove = new RemoveArtifact(this, from, to);
    }

    // "redo" it, and return the artifact
    remove.redo();
    return remove;
  }

  RemoveArtifact removeSelection()
  {
    TextSelection sel = editor.getSelection();
    if (!sel.isEmpty())
    {
      Position start = sel.getStart();
      Position end = sel.getEnd();
      return new RemoveArtifact(this, start, end);
    }
    return null;
  }

  CommandArtifact compose(char[] composedText, String committedText,
                          int composedStart, int composedEnd,
                          int caretCharIndex,
                          boolean commit, boolean deleteComposed)
  {
    if (!editor.isEditable()) return null;

    if (log.isLoggable(Level.FINE))
    {
      log.fine("-- compose: ComposedText=" + getComposedTextString(composedText) +
                ", CommittedText=" + committedText +
                ", composedStart=" + composedStart +
                ", composedEnd=" + composedEnd +
                ", caretCharIndex=" + caretCharIndex +
                ", commit=" + commit +
                ", deleteComposed=" + deleteComposed);
    }

    // If anything is selected, remove it
    RemoveArtifact remove = removeSelection();
    if (remove != null) remove.redo();

    // Create our compose artifact
    ComposeArtifact compose = new ComposeArtifact(this, composedText,
                                                  composedStart, composedEnd,
                                                  committedText,
                                                  caretCharIndex,
                                                  commit, deleteComposed);
    compose.redo();

    // wrap them both
    return new CompoundCommand.Artifact(new CommandArtifact[] { remove, compose});
  }

  private String getComposedTextString(char[] a)
  {
    StringBuilder sb = new StringBuilder();
    for (int i=0; i < a.length; i++)
    {
      char c = a[i];

      // Convert the integer to a hexadecimal code.
      String hexCode = Integer.toHexString(c).toUpperCase();
      // but it must be a four number value.
      String hexCodeWithAllLeadingZeros = "0000" + hexCode;
      String hexCodeWithLeadingZeros = hexCodeWithAllLeadingZeros.substring(hexCodeWithAllLeadingZeros.length()-4);

      sb.append("\\u" + hexCodeWithLeadingZeros);
    }

    return sb.toString();
  }

////////////////////////////////////////////////////////////////
// AbstractEditArtifact
////////////////////////////////////////////////////////////////

  static abstract class AbstractEditArtifact
    implements CommandArtifact
  {
    AbstractEditArtifact(EditCommand c)
    {
      this.editor = c.editor;
    }

    protected void doRemove(Position fromPos, Position toPos)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("  doRemove: " + fromPos + " -> " + toPos);
      }

      // remove the characters
      editor.getModel().remove(fromPos, toPos);

      // position the caret at start of remove
      editor.moveCaretPosition(fromPos);
      editor.updateAnchorX();

      // clear the selection
      editor.getSelection().deselect();
      
      // Empty any cached composed position
      TextController controller = editor.getController();
      controller.setComposedPosition(null);
      controller.setTextBeginIndex(0);
      controller.setTextEndIndex(0);     
    }

    protected Position doInsert(Position pos, char[] text)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("  doInsert: " + pos + " -> " + TextModel.toString(text));
      }

      // insert the text
      pos = editor.getModel().insert(pos, text, 0, text.length);

      // move the caret
      editor.moveCaretPosition(pos);
      editor.updateAnchorX();
      // return new position
      return pos;
    }

    protected BTextEditor editor;
  }

////////////////////////////////////////////////////////////////
// RemoveArtifact
////////////////////////////////////////////////////////////////

  class RemoveArtifact
    extends AbstractEditArtifact
  {
    RemoveArtifact(EditCommand c, Position fromPos, Position toPos)
    {
      super(c);

      // we need to get the character previous to toPos
      // because the call to remove is inclusive
      if (!fromPos.equals(toPos))
        toPos = c.editor.getModel().getPrevPosition(toPos);

      this.fromPos = fromPos;
      this.toPos = toPos;
      this.text = c.editor.getModel().getCharArray(fromPos, toPos);
      if (log.isLoggable(Level.FINE))
      {
        log.fine("    RemoveArtifact<init>: " + fromPos + " -> " + toPos + " -> " + TextModel.toString(text));
      }
    }

    @Override
    public void undo()
    {
      doInsert(fromPos, text);
    }

    @Override
    public void redo()
    {
      doRemove(fromPos, toPos);
    }

    private final Position fromPos;
    private final Position toPos;
    private final char[] text;
  }

////////////////////////////////////////////////////////////////
// InsertArtifact
////////////////////////////////////////////////////////////////

  class InsertArtifact
    extends AbstractEditArtifact
  {
    InsertArtifact(EditCommand c, Position pos, char[] text)
    {
      super(c);
      this.origPos = pos;
      this.text = text;
      if (log.isLoggable(Level.FINE))
      {
        log.fine("  InsertArtifact<init>: " + origPos + " -> " + TextModel.toString(text));
      }
    }

    @Override
    public void undo()
    {
      // These methods deal with changes to InputMethodEvent handling in JRE1.7
      TextController controller = this.editor.getController();
      controller.setComposedPosition(origPos);
      
      doRemove(origPos, newPos);
    }

    @Override
    public void redo()
    {
      // These methods deal with changes to InputMethodEvent handling in JRE1.7
      TextController controller = this.editor.getController();
      controller.setComposedPosition(origPos);
      controller.setComposedPosition(null);
      controller.setTextBeginIndex(0);
      controller.setTextEndIndex(0);     

      
      newPos = doInsert(origPos, text);
      newPos = this.editor.getModel().getPrevPosition(newPos);
    }

    private final Position origPos;
    private Position newPos;
    private final char[] text;
  }

////////////////////////////////////////////////////////////////
//ComposeArtifact
////////////////////////////////////////////////////////////////

  class ComposeArtifact
    extends AbstractEditArtifact
  {
    ComposeArtifact(EditCommand c, char[] text,
                    int composedStart, int composedEnd, String committedText,
                    int caretCharIndex,
                    boolean commit, boolean deleteComposed)
    {
      super(c);
      this.text = text;
      this.commit = commit;
      this.committedText = committedText;
      this.deleteComposed = deleteComposed;
      this.composedStart = composedStart;
      this.composedEnd = composedEnd;
      this.caretCharacterIndex = caretCharIndex;
      if (log.isLoggable(Level.FINE))
      {
        log.fine("  ComposeArtifact<init>: " + TextModel.toString(text));
      }

      TextController controller = editor.getController();

      // Retrieve the cached Position of the current text composition
      origPos = controller.getComposedPosition();
      if (origPos == null)
      {
        origPos = editor.getCaretPosition();
        if (text.length > 0)
        {
          // Cache the current position of the start of the composed text
          if (log.isLoggable(Level.FINE))
          {
            log.fine("  Set the cached composedOriginPos = " + origPos.toString());
          }
          controller.setComposedPosition(origPos);
        }
      }

      // Cache the caret position and composition start/end values for use in undo
      origCaretPos = editor.getCaretPosition();
      origComposedStart = controller.getTextBeginIndex();
      origComposedEnd = controller.getTextEndIndex();
}


    public void redo()
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("  doCompose: " + composedStart + " to " + composedEnd +
                 " at " + origPos + " for " + TextModel.toString(text) +
                 " deleteComposed: " + deleteComposed +
                 " commit: " + commit);
      }
      
      TextController controller = editor.getController();
      TextModel model = editor.getModel();

      if (deleteComposed)
      {
        // Delete the existing composed text. It is possible that the
        // composed text is more than one character.
        Position startPos = new Position(origPos.line, origPos.column + origComposedStart);
        Position endPos = new Position(origPos.line, origPos.column + origComposedEnd);
        // Get the character previous to startPos because the call to remove is inclusive
        if (!startPos.equals(endPos))
          endPos = model.getPrevPosition(endPos);

        origText = model.getCharArray(startPos, endPos);
        if (log.isLoggable(Level.FINE))
        {
          log.fine("  deleteComposed " + TextModel.toString(origText) + " from " + startPos + " to " + endPos);
        }

        model.remove(startPos, endPos);
      }

      if (commit && committedText != null && committedText.length() > 0)
      {
        if (log.isLoggable(Level.FINE))
        {
          log.fine("  insertCommitted " + committedText + " at " + origPos);
        }
        model.insert(origPos, committedText.toCharArray(), 0, committedText.length());

        Position nextPos = new Position(origPos.line, origPos.column + committedText.length());
        if (log.isLoggable(Level.FINE))
        {
          log.fine("  move caret to " + nextPos);
        }
        editor.moveCaretPosition(nextPos);
      }
      else if (text.length > 0)
      {
        // Do not set the origPos from the insert results, as we need to account for the
        // composed text aggregation
        if (log.isLoggable(Level.FINE))
        {
          log.fine("  insertComposed " + TextModel.toString(text) + " at " + origPos);
        }
        model.insert(origPos, text, 0, text.length);

        // Move caret if necessary
        if ((caretCharacterIndex) > 0)
        {
          Position nextPos = new Position(origPos.line, origPos.column + caretCharacterIndex);
          if (log.isLoggable(Level.FINE))
          {
            log.fine("  move caret to " + nextPos);
          }
          editor.moveCaretPosition(nextPos);
        }
      }

      if (commit)
      {
        // Empty the cached composed position if this is a commit
        if (log.isLoggable(Level.FINE))
        {
          log.fine("  Reset the cached composedOriginPos = null");
        }
        controller.setComposedPosition(null);
        controller.setTextBeginIndex(0);
        controller.setTextEndIndex(0);
      }
      else
      {
        // Otherwise set the cache for the begin and end indexes
        controller.setTextBeginIndex(composedStart);
        controller.setTextEndIndex(composedEnd);
      }

      editor.updateAnchorX();
    }

    @Override
    public void undo()
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("  undoCompose: " + composedStart + " to " + composedEnd +
                 " at " + origPos + " for " + TextModel.toString(text) +
                 " deleteComposed: " + deleteComposed +
                 " commit: " + commit);
      }

      if (origPos == null) return;

      TextController controller = editor.getController();
      TextModel model = editor.getModel();

      if (commit && committedText != null && committedText.length() > 0)
      {
        if (log.isLoggable(Level.FINE))
        {
          log.fine("  removeCommitted " + committedText + " at " + origPos);
        }
        Position nextPos = new Position(origPos.line, origPos.column + committedText.length());
        if (!origPos.equals(nextPos))
          nextPos = model.getPrevPosition(nextPos);
        model.remove(origPos, nextPos);
      }
      else if (text.length > 0)
      {
        // Do not set the origPos from the insert results, as we need to account for the
        // composed text aggregation
        if (log.isLoggable(Level.FINE))
        {
          log.fine("  removeComposed " + TextModel.toString(text) + " at " + origPos);
        }
        Position nextPos = new Position(origPos.line, origPos.column + text.length);
        if (!origPos.equals(nextPos))
          nextPos = model.getPrevPosition(nextPos);
        model.remove(origPos, nextPos);
      }

      if (origText != null)
      {
        Position startPos = new Position(origPos.line, origPos.column + composedStart);
        model.insert(startPos, origText, 0, origText.length);
      }

      if (origCaretPos != null)
      {
        editor.moveCaretPosition(origCaretPos);
      }

      editor.updateAnchorX();

      // Clear the selection
      editor.getSelection().deselect();

      // Reset the controller cache information
      controller.setTextBeginIndex(origComposedStart);
      controller.setTextEndIndex(origComposedEnd);
      controller.setComposedPosition(origPos);
    }

    private Position origPos;
    private Position origCaretPos; // Needed for undo() operation
    private final char[] text;
    private String committedText = null;
    private final int composedStart;
    private final int composedEnd;
    private int origComposedStart; // Needed for undo() operation
    private int origComposedEnd; // Needed for undo() operation
    private final int caretCharacterIndex;
    private final boolean commit;
    private final boolean deleteComposed;
    private char[] origText = null;
  }

  private static Logger log = Logger.getLogger("ui.command");
}

