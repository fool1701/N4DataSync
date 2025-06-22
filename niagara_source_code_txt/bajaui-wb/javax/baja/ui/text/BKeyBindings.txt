/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;

import java.io.*;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.event.*;
import javax.baja.ui.text.commands.*;
import javax.baja.xml.*;

/**
 * The BKeyBindings stores a mapping between TextCommands 
 * and BAccelerator keys.
 *
 * @author    Brian Frank
 * @creation  7 Jul 01
 * @version   $Revision: 17$ $Date: 3/28/05 10:32:33 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "moveUp",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Up\")"
)
@NiagaraProperty(
  name = "moveDown",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Down\")"
)
@NiagaraProperty(
  name = "moveLeft",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Left\")"
)
@NiagaraProperty(
  name = "moveRight",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Right\")"
)
@NiagaraProperty(
  name = "pageUp",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"PageUp\")"
)
@NiagaraProperty(
  name = "pageDown",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"PageDown\")"
)
@NiagaraProperty(
  name = "lineStart",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Home\")"
)
@NiagaraProperty(
  name = "lineEnd",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"End\")"
)
@NiagaraProperty(
  name = "documentStart",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+Home\")"
)
@NiagaraProperty(
  name = "documentEnd",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+End\")"
)
@NiagaraProperty(
  name = "wordLeft",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+Left\")"
)
@NiagaraProperty(
  name = "wordRight",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+Right\")"
)
@NiagaraProperty(
  name = "cut",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+X\")"
)
@NiagaraProperty(
  name = "copy",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+C\")"
)
@NiagaraProperty(
  name = "paste",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+V\")"
)
@NiagaraProperty(
  name = "cut2",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Shift+Delete\")"
)
@NiagaraProperty(
  name = "copy2",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+Insert\")"
)
@NiagaraProperty(
  name = "paste2",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Shift+Insert\")"
)
@NiagaraProperty(
  name = "undo",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+Z\")"
)
@NiagaraProperty(
  name = "redo",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+Alt+Z\")"
)
@NiagaraProperty(
  name = "delete",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Delete\")"
)
@NiagaraProperty(
  name = "backspace",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Backspace\")"
)
@NiagaraProperty(
  name = "cutLine",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+Y\")"
)
@NiagaraProperty(
  name = "deleteWord",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+Delete\")"
)
@NiagaraProperty(
  name = "tabForward",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Tab\")"
)
@NiagaraProperty(
  name = "tabBack",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Shift+Tab\")"
)
@NiagaraProperty(
  name = "toggleSlashSlash",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+/\")"
)
@NiagaraProperty(
  name = "wordWrap",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+W\")"
)
@NiagaraProperty(
  name = "goTo",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+G\")"
)
@NiagaraProperty(
  name = "find",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"F5\")"
)
@NiagaraProperty(
  name = "findNext",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+F\")"
)
@NiagaraProperty(
  name = "findPrev",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+Shift+F\")"
)
@NiagaraProperty(
  name = "replace",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"F6\")"
)
@NiagaraProperty(
  name = "reloadMacros",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+M\")"
)
@NiagaraProperty(
  name = "selectAll",
  type = "BAccelerator",
  defaultValue = "BAccelerator.make(\"Ctrl+A\")"
)
public class BKeyBindings
  extends BComponent
{        
  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.text.BKeyBindings(2288992649)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "moveUp"

  /**
   * Slot for the {@code moveUp} property.
   * @see #getMoveUp
   * @see #setMoveUp
   */
  public static final Property moveUp = newProperty(0, BAccelerator.make("Up"), null);

  /**
   * Get the {@code moveUp} property.
   * @see #moveUp
   */
  public BAccelerator getMoveUp() { return (BAccelerator)get(moveUp); }

  /**
   * Set the {@code moveUp} property.
   * @see #moveUp
   */
  public void setMoveUp(BAccelerator v) { set(moveUp, v, null); }

  //endregion Property "moveUp"

  //region Property "moveDown"

  /**
   * Slot for the {@code moveDown} property.
   * @see #getMoveDown
   * @see #setMoveDown
   */
  public static final Property moveDown = newProperty(0, BAccelerator.make("Down"), null);

  /**
   * Get the {@code moveDown} property.
   * @see #moveDown
   */
  public BAccelerator getMoveDown() { return (BAccelerator)get(moveDown); }

  /**
   * Set the {@code moveDown} property.
   * @see #moveDown
   */
  public void setMoveDown(BAccelerator v) { set(moveDown, v, null); }

  //endregion Property "moveDown"

  //region Property "moveLeft"

  /**
   * Slot for the {@code moveLeft} property.
   * @see #getMoveLeft
   * @see #setMoveLeft
   */
  public static final Property moveLeft = newProperty(0, BAccelerator.make("Left"), null);

  /**
   * Get the {@code moveLeft} property.
   * @see #moveLeft
   */
  public BAccelerator getMoveLeft() { return (BAccelerator)get(moveLeft); }

  /**
   * Set the {@code moveLeft} property.
   * @see #moveLeft
   */
  public void setMoveLeft(BAccelerator v) { set(moveLeft, v, null); }

  //endregion Property "moveLeft"

  //region Property "moveRight"

  /**
   * Slot for the {@code moveRight} property.
   * @see #getMoveRight
   * @see #setMoveRight
   */
  public static final Property moveRight = newProperty(0, BAccelerator.make("Right"), null);

  /**
   * Get the {@code moveRight} property.
   * @see #moveRight
   */
  public BAccelerator getMoveRight() { return (BAccelerator)get(moveRight); }

  /**
   * Set the {@code moveRight} property.
   * @see #moveRight
   */
  public void setMoveRight(BAccelerator v) { set(moveRight, v, null); }

  //endregion Property "moveRight"

  //region Property "pageUp"

  /**
   * Slot for the {@code pageUp} property.
   * @see #getPageUp
   * @see #setPageUp
   */
  public static final Property pageUp = newProperty(0, BAccelerator.make("PageUp"), null);

  /**
   * Get the {@code pageUp} property.
   * @see #pageUp
   */
  public BAccelerator getPageUp() { return (BAccelerator)get(pageUp); }

  /**
   * Set the {@code pageUp} property.
   * @see #pageUp
   */
  public void setPageUp(BAccelerator v) { set(pageUp, v, null); }

  //endregion Property "pageUp"

  //region Property "pageDown"

  /**
   * Slot for the {@code pageDown} property.
   * @see #getPageDown
   * @see #setPageDown
   */
  public static final Property pageDown = newProperty(0, BAccelerator.make("PageDown"), null);

  /**
   * Get the {@code pageDown} property.
   * @see #pageDown
   */
  public BAccelerator getPageDown() { return (BAccelerator)get(pageDown); }

  /**
   * Set the {@code pageDown} property.
   * @see #pageDown
   */
  public void setPageDown(BAccelerator v) { set(pageDown, v, null); }

  //endregion Property "pageDown"

  //region Property "lineStart"

  /**
   * Slot for the {@code lineStart} property.
   * @see #getLineStart
   * @see #setLineStart
   */
  public static final Property lineStart = newProperty(0, BAccelerator.make("Home"), null);

  /**
   * Get the {@code lineStart} property.
   * @see #lineStart
   */
  public BAccelerator getLineStart() { return (BAccelerator)get(lineStart); }

  /**
   * Set the {@code lineStart} property.
   * @see #lineStart
   */
  public void setLineStart(BAccelerator v) { set(lineStart, v, null); }

  //endregion Property "lineStart"

  //region Property "lineEnd"

  /**
   * Slot for the {@code lineEnd} property.
   * @see #getLineEnd
   * @see #setLineEnd
   */
  public static final Property lineEnd = newProperty(0, BAccelerator.make("End"), null);

  /**
   * Get the {@code lineEnd} property.
   * @see #lineEnd
   */
  public BAccelerator getLineEnd() { return (BAccelerator)get(lineEnd); }

  /**
   * Set the {@code lineEnd} property.
   * @see #lineEnd
   */
  public void setLineEnd(BAccelerator v) { set(lineEnd, v, null); }

  //endregion Property "lineEnd"

  //region Property "documentStart"

  /**
   * Slot for the {@code documentStart} property.
   * @see #getDocumentStart
   * @see #setDocumentStart
   */
  public static final Property documentStart = newProperty(0, BAccelerator.make("Ctrl+Home"), null);

  /**
   * Get the {@code documentStart} property.
   * @see #documentStart
   */
  public BAccelerator getDocumentStart() { return (BAccelerator)get(documentStart); }

  /**
   * Set the {@code documentStart} property.
   * @see #documentStart
   */
  public void setDocumentStart(BAccelerator v) { set(documentStart, v, null); }

  //endregion Property "documentStart"

  //region Property "documentEnd"

  /**
   * Slot for the {@code documentEnd} property.
   * @see #getDocumentEnd
   * @see #setDocumentEnd
   */
  public static final Property documentEnd = newProperty(0, BAccelerator.make("Ctrl+End"), null);

  /**
   * Get the {@code documentEnd} property.
   * @see #documentEnd
   */
  public BAccelerator getDocumentEnd() { return (BAccelerator)get(documentEnd); }

  /**
   * Set the {@code documentEnd} property.
   * @see #documentEnd
   */
  public void setDocumentEnd(BAccelerator v) { set(documentEnd, v, null); }

  //endregion Property "documentEnd"

  //region Property "wordLeft"

  /**
   * Slot for the {@code wordLeft} property.
   * @see #getWordLeft
   * @see #setWordLeft
   */
  public static final Property wordLeft = newProperty(0, BAccelerator.make("Ctrl+Left"), null);

  /**
   * Get the {@code wordLeft} property.
   * @see #wordLeft
   */
  public BAccelerator getWordLeft() { return (BAccelerator)get(wordLeft); }

  /**
   * Set the {@code wordLeft} property.
   * @see #wordLeft
   */
  public void setWordLeft(BAccelerator v) { set(wordLeft, v, null); }

  //endregion Property "wordLeft"

  //region Property "wordRight"

  /**
   * Slot for the {@code wordRight} property.
   * @see #getWordRight
   * @see #setWordRight
   */
  public static final Property wordRight = newProperty(0, BAccelerator.make("Ctrl+Right"), null);

  /**
   * Get the {@code wordRight} property.
   * @see #wordRight
   */
  public BAccelerator getWordRight() { return (BAccelerator)get(wordRight); }

  /**
   * Set the {@code wordRight} property.
   * @see #wordRight
   */
  public void setWordRight(BAccelerator v) { set(wordRight, v, null); }

  //endregion Property "wordRight"

  //region Property "cut"

  /**
   * Slot for the {@code cut} property.
   * @see #getCut
   * @see #setCut
   */
  public static final Property cut = newProperty(0, BAccelerator.make("Ctrl+X"), null);

  /**
   * Get the {@code cut} property.
   * @see #cut
   */
  public BAccelerator getCut() { return (BAccelerator)get(cut); }

  /**
   * Set the {@code cut} property.
   * @see #cut
   */
  public void setCut(BAccelerator v) { set(cut, v, null); }

  //endregion Property "cut"

  //region Property "copy"

  /**
   * Slot for the {@code copy} property.
   * @see #getCopy
   * @see #setCopy
   */
  public static final Property copy = newProperty(0, BAccelerator.make("Ctrl+C"), null);

  /**
   * Get the {@code copy} property.
   * @see #copy
   */
  public BAccelerator getCopy() { return (BAccelerator)get(copy); }

  /**
   * Set the {@code copy} property.
   * @see #copy
   */
  public void setCopy(BAccelerator v) { set(copy, v, null); }

  //endregion Property "copy"

  //region Property "paste"

  /**
   * Slot for the {@code paste} property.
   * @see #getPaste
   * @see #setPaste
   */
  public static final Property paste = newProperty(0, BAccelerator.make("Ctrl+V"), null);

  /**
   * Get the {@code paste} property.
   * @see #paste
   */
  public BAccelerator getPaste() { return (BAccelerator)get(paste); }

  /**
   * Set the {@code paste} property.
   * @see #paste
   */
  public void setPaste(BAccelerator v) { set(paste, v, null); }

  //endregion Property "paste"

  //region Property "cut2"

  /**
   * Slot for the {@code cut2} property.
   * @see #getCut2
   * @see #setCut2
   */
  public static final Property cut2 = newProperty(0, BAccelerator.make("Shift+Delete"), null);

  /**
   * Get the {@code cut2} property.
   * @see #cut2
   */
  public BAccelerator getCut2() { return (BAccelerator)get(cut2); }

  /**
   * Set the {@code cut2} property.
   * @see #cut2
   */
  public void setCut2(BAccelerator v) { set(cut2, v, null); }

  //endregion Property "cut2"

  //region Property "copy2"

  /**
   * Slot for the {@code copy2} property.
   * @see #getCopy2
   * @see #setCopy2
   */
  public static final Property copy2 = newProperty(0, BAccelerator.make("Ctrl+Insert"), null);

  /**
   * Get the {@code copy2} property.
   * @see #copy2
   */
  public BAccelerator getCopy2() { return (BAccelerator)get(copy2); }

  /**
   * Set the {@code copy2} property.
   * @see #copy2
   */
  public void setCopy2(BAccelerator v) { set(copy2, v, null); }

  //endregion Property "copy2"

  //region Property "paste2"

  /**
   * Slot for the {@code paste2} property.
   * @see #getPaste2
   * @see #setPaste2
   */
  public static final Property paste2 = newProperty(0, BAccelerator.make("Shift+Insert"), null);

  /**
   * Get the {@code paste2} property.
   * @see #paste2
   */
  public BAccelerator getPaste2() { return (BAccelerator)get(paste2); }

  /**
   * Set the {@code paste2} property.
   * @see #paste2
   */
  public void setPaste2(BAccelerator v) { set(paste2, v, null); }

  //endregion Property "paste2"

  //region Property "undo"

  /**
   * Slot for the {@code undo} property.
   * @see #getUndo
   * @see #setUndo
   */
  public static final Property undo = newProperty(0, BAccelerator.make("Ctrl+Z"), null);

  /**
   * Get the {@code undo} property.
   * @see #undo
   */
  public BAccelerator getUndo() { return (BAccelerator)get(undo); }

  /**
   * Set the {@code undo} property.
   * @see #undo
   */
  public void setUndo(BAccelerator v) { set(undo, v, null); }

  //endregion Property "undo"

  //region Property "redo"

  /**
   * Slot for the {@code redo} property.
   * @see #getRedo
   * @see #setRedo
   */
  public static final Property redo = newProperty(0, BAccelerator.make("Ctrl+Alt+Z"), null);

  /**
   * Get the {@code redo} property.
   * @see #redo
   */
  public BAccelerator getRedo() { return (BAccelerator)get(redo); }

  /**
   * Set the {@code redo} property.
   * @see #redo
   */
  public void setRedo(BAccelerator v) { set(redo, v, null); }

  //endregion Property "redo"

  //region Property "delete"

  /**
   * Slot for the {@code delete} property.
   * @see #getDelete
   * @see #setDelete
   */
  public static final Property delete = newProperty(0, BAccelerator.make("Delete"), null);

  /**
   * Get the {@code delete} property.
   * @see #delete
   */
  public BAccelerator getDelete() { return (BAccelerator)get(delete); }

  /**
   * Set the {@code delete} property.
   * @see #delete
   */
  public void setDelete(BAccelerator v) { set(delete, v, null); }

  //endregion Property "delete"

  //region Property "backspace"

  /**
   * Slot for the {@code backspace} property.
   * @see #getBackspace
   * @see #setBackspace
   */
  public static final Property backspace = newProperty(0, BAccelerator.make("Backspace"), null);

  /**
   * Get the {@code backspace} property.
   * @see #backspace
   */
  public BAccelerator getBackspace() { return (BAccelerator)get(backspace); }

  /**
   * Set the {@code backspace} property.
   * @see #backspace
   */
  public void setBackspace(BAccelerator v) { set(backspace, v, null); }

  //endregion Property "backspace"

  //region Property "cutLine"

  /**
   * Slot for the {@code cutLine} property.
   * @see #getCutLine
   * @see #setCutLine
   */
  public static final Property cutLine = newProperty(0, BAccelerator.make("Ctrl+Y"), null);

  /**
   * Get the {@code cutLine} property.
   * @see #cutLine
   */
  public BAccelerator getCutLine() { return (BAccelerator)get(cutLine); }

  /**
   * Set the {@code cutLine} property.
   * @see #cutLine
   */
  public void setCutLine(BAccelerator v) { set(cutLine, v, null); }

  //endregion Property "cutLine"

  //region Property "deleteWord"

  /**
   * Slot for the {@code deleteWord} property.
   * @see #getDeleteWord
   * @see #setDeleteWord
   */
  public static final Property deleteWord = newProperty(0, BAccelerator.make("Ctrl+Delete"), null);

  /**
   * Get the {@code deleteWord} property.
   * @see #deleteWord
   */
  public BAccelerator getDeleteWord() { return (BAccelerator)get(deleteWord); }

  /**
   * Set the {@code deleteWord} property.
   * @see #deleteWord
   */
  public void setDeleteWord(BAccelerator v) { set(deleteWord, v, null); }

  //endregion Property "deleteWord"

  //region Property "tabForward"

  /**
   * Slot for the {@code tabForward} property.
   * @see #getTabForward
   * @see #setTabForward
   */
  public static final Property tabForward = newProperty(0, BAccelerator.make("Tab"), null);

  /**
   * Get the {@code tabForward} property.
   * @see #tabForward
   */
  public BAccelerator getTabForward() { return (BAccelerator)get(tabForward); }

  /**
   * Set the {@code tabForward} property.
   * @see #tabForward
   */
  public void setTabForward(BAccelerator v) { set(tabForward, v, null); }

  //endregion Property "tabForward"

  //region Property "tabBack"

  /**
   * Slot for the {@code tabBack} property.
   * @see #getTabBack
   * @see #setTabBack
   */
  public static final Property tabBack = newProperty(0, BAccelerator.make("Shift+Tab"), null);

  /**
   * Get the {@code tabBack} property.
   * @see #tabBack
   */
  public BAccelerator getTabBack() { return (BAccelerator)get(tabBack); }

  /**
   * Set the {@code tabBack} property.
   * @see #tabBack
   */
  public void setTabBack(BAccelerator v) { set(tabBack, v, null); }

  //endregion Property "tabBack"

  //region Property "toggleSlashSlash"

  /**
   * Slot for the {@code toggleSlashSlash} property.
   * @see #getToggleSlashSlash
   * @see #setToggleSlashSlash
   */
  public static final Property toggleSlashSlash = newProperty(0, BAccelerator.make("Ctrl+/"), null);

  /**
   * Get the {@code toggleSlashSlash} property.
   * @see #toggleSlashSlash
   */
  public BAccelerator getToggleSlashSlash() { return (BAccelerator)get(toggleSlashSlash); }

  /**
   * Set the {@code toggleSlashSlash} property.
   * @see #toggleSlashSlash
   */
  public void setToggleSlashSlash(BAccelerator v) { set(toggleSlashSlash, v, null); }

  //endregion Property "toggleSlashSlash"

  //region Property "wordWrap"

  /**
   * Slot for the {@code wordWrap} property.
   * @see #getWordWrap
   * @see #setWordWrap
   */
  public static final Property wordWrap = newProperty(0, BAccelerator.make("Ctrl+W"), null);

  /**
   * Get the {@code wordWrap} property.
   * @see #wordWrap
   */
  public BAccelerator getWordWrap() { return (BAccelerator)get(wordWrap); }

  /**
   * Set the {@code wordWrap} property.
   * @see #wordWrap
   */
  public void setWordWrap(BAccelerator v) { set(wordWrap, v, null); }

  //endregion Property "wordWrap"

  //region Property "goTo"

  /**
   * Slot for the {@code goTo} property.
   * @see #getGoTo
   * @see #setGoTo
   */
  public static final Property goTo = newProperty(0, BAccelerator.make("Ctrl+G"), null);

  /**
   * Get the {@code goTo} property.
   * @see #goTo
   */
  public BAccelerator getGoTo() { return (BAccelerator)get(goTo); }

  /**
   * Set the {@code goTo} property.
   * @see #goTo
   */
  public void setGoTo(BAccelerator v) { set(goTo, v, null); }

  //endregion Property "goTo"

  //region Property "find"

  /**
   * Slot for the {@code find} property.
   * @see #getFind
   * @see #setFind
   */
  public static final Property find = newProperty(0, BAccelerator.make("F5"), null);

  /**
   * Get the {@code find} property.
   * @see #find
   */
  public BAccelerator getFind() { return (BAccelerator)get(find); }

  /**
   * Set the {@code find} property.
   * @see #find
   */
  public void setFind(BAccelerator v) { set(find, v, null); }

  //endregion Property "find"

  //region Property "findNext"

  /**
   * Slot for the {@code findNext} property.
   * @see #getFindNext
   * @see #setFindNext
   */
  public static final Property findNext = newProperty(0, BAccelerator.make("Ctrl+F"), null);

  /**
   * Get the {@code findNext} property.
   * @see #findNext
   */
  public BAccelerator getFindNext() { return (BAccelerator)get(findNext); }

  /**
   * Set the {@code findNext} property.
   * @see #findNext
   */
  public void setFindNext(BAccelerator v) { set(findNext, v, null); }

  //endregion Property "findNext"

  //region Property "findPrev"

  /**
   * Slot for the {@code findPrev} property.
   * @see #getFindPrev
   * @see #setFindPrev
   */
  public static final Property findPrev = newProperty(0, BAccelerator.make("Ctrl+Shift+F"), null);

  /**
   * Get the {@code findPrev} property.
   * @see #findPrev
   */
  public BAccelerator getFindPrev() { return (BAccelerator)get(findPrev); }

  /**
   * Set the {@code findPrev} property.
   * @see #findPrev
   */
  public void setFindPrev(BAccelerator v) { set(findPrev, v, null); }

  //endregion Property "findPrev"

  //region Property "replace"

  /**
   * Slot for the {@code replace} property.
   * @see #getReplace
   * @see #setReplace
   */
  public static final Property replace = newProperty(0, BAccelerator.make("F6"), null);

  /**
   * Get the {@code replace} property.
   * @see #replace
   */
  public BAccelerator getReplace() { return (BAccelerator)get(replace); }

  /**
   * Set the {@code replace} property.
   * @see #replace
   */
  public void setReplace(BAccelerator v) { set(replace, v, null); }

  //endregion Property "replace"

  //region Property "reloadMacros"

  /**
   * Slot for the {@code reloadMacros} property.
   * @see #getReloadMacros
   * @see #setReloadMacros
   */
  public static final Property reloadMacros = newProperty(0, BAccelerator.make("Ctrl+M"), null);

  /**
   * Get the {@code reloadMacros} property.
   * @see #reloadMacros
   */
  public BAccelerator getReloadMacros() { return (BAccelerator)get(reloadMacros); }

  /**
   * Set the {@code reloadMacros} property.
   * @see #reloadMacros
   */
  public void setReloadMacros(BAccelerator v) { set(reloadMacros, v, null); }

  //endregion Property "reloadMacros"

  //region Property "selectAll"

  /**
   * Slot for the {@code selectAll} property.
   * @see #getSelectAll
   * @see #setSelectAll
   */
  public static final Property selectAll = newProperty(0, BAccelerator.make("Ctrl+A"), null);

  /**
   * Get the {@code selectAll} property.
   * @see #selectAll
   */
  public BAccelerator getSelectAll() { return (BAccelerator)get(selectAll); }

  /**
   * Set the {@code selectAll} property.
   * @see #selectAll
   */
  public void setSelectAll(BAccelerator v) { set(selectAll, v, null); }

  //endregion Property "selectAll"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BKeyBindings.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Lookup
////////////////////////////////////////////////////////////////

  /**
   * Map an event to a Command, or return null if no key
   * binding for the specified BKeyEvent.
   */
  public Command eventToCommand(BTextEditor editor, BKeyEvent event)
  {
    if (table == null) updateTable();
    int keyCode = event.getKeyCode();
    int modifiers = event.getModifiersEx();
    
    // lookup the accelerator
    BAccelerator acc = BAccelerator.make(keyCode, modifiers);
    Property prop = table.get(acc);
    
    // map to a command if found
    if (prop != null)
      return editor.getCommandFactory().make(prop.getName());
    
    // try a macro
    Macro macro = macros.get(acc);
    if (macro != null)
      return macro.makeCommand(editor);
      
    // now we try to see if the event maps to a 
    // MoveCommand without the shift key
    if (event.isShiftDown())
    {
      acc = BAccelerator.make(keyCode, modifiers & ~BInputEvent.SHIFT_DOWN_MASK);
      prop = table.get(acc);
      if (prop != null)
      {
        Command command = editor.getCommandFactory().make(prop.getName());
        if (command instanceof MoveCommand)
        {
          MoveCommand moveCommand = (MoveCommand)command;
          moveCommand.setShiftDown(true);
          return moveCommand;
        }
      }
    }
    
    return null;
  }           
  
  /**
   * Populate cached lookup table.
   */
  void updateTable()
  {               
    table = new Hashtable<>();
    SlotCursor<Property> c = getProperties();
    while(c.next())
      table.put(c.get(), c.property());
  }                              

  public void changed(Property prop, Context cx)
  {
    super.changed(prop, cx);
    updateTable();
  }

////////////////////////////////////////////////////////////////
// macros
////////////////////////////////////////////////////////////////

  private static Hashtable<BAccelerator, Macro> macros = new Hashtable<>();
  static { loadMacros(); }

  public static void loadMacros()
  {
    AccessController.doPrivileged(new LoadMacrosPrivilegedAction());
  }

  private static class LoadMacrosPrivilegedAction
    implements PrivilegedAction<Void>
  {
    @Override
    public Void run()
    {
      try
      {
        macros.clear();
        File file = new File(Sys.getNiagaraUserHome(), "etc/macros.xml");
        if (file.exists())
        {
          XElem elem = XParser.make(file).parse();
          XElem[] mcrs = elem.elems();
          for(int i=0; i<mcrs.length; ++i)
          {
            Macro m = new Macro(mcrs[i]);
            macros.put(m.acc, m);
          }
        }
      }
      catch(Exception e)
      {
        System.out.print("ERROR: Cannot initialize macros");
        e.printStackTrace();
      }

      return null;
    }
  }

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("keyboardKey.png");
  

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

  Hashtable<BValue, Property> table;

} 
