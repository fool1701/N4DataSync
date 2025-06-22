/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.ui.CommandArtifact;
import javax.baja.ui.text.BKeyBindings;
import javax.baja.ui.text.BTextEditor;

public class ComposeText
  extends EditCommand
{
  public ComposeText(BTextEditor widget, String composedText, String committedText,
                     int composedStart, int composedEnd,
                     int caretCharIndex,
                     boolean commit, boolean deleteComposed)
  {
    this(widget, composedText.toCharArray(), committedText, composedStart,
         composedEnd, caretCharIndex, commit, deleteComposed);
  }

  public ComposeText(BTextEditor widget, char[] composedText, String committedText,
                     int composedStart, int composedEnd,
                     int caretCharIndex,
                     boolean commit, boolean deleteComposed)
  {
    super(widget, BKeyBindings.paste);
    this.composedText = composedText;
    this.composedStart = composedStart;
    this.composedEnd = composedEnd;
    this.caretCharacterIndex = caretCharIndex;
    this.committedText = committedText;
    this.commit = commit;
    this.deleteComposed = deleteComposed;

    if (log.isLoggable(Level.FINE))
    {
      log.fine("ComposeText - composed:" + new String(composedText) +
               ", committed:" + committedText +
               ", caretCharIndex:" + caretCharIndex +
               ", commit:" + commit + ", delete:" + deleteComposed);
    }
  }

  @Override
  public CommandArtifact doInvoke()
  {
    return compose(composedText, committedText, composedStart, composedEnd,
                   caretCharacterIndex, commit, deleteComposed);
  }

  private final char[] composedText;
  private final int composedStart, composedEnd;
  private final boolean commit, deleteComposed;
  private final String committedText;
  private final int caretCharacterIndex;

  private static Logger log = Logger.getLogger("ui.command");
}
