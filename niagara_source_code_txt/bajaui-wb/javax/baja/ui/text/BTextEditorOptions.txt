/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.options.*;

/**
 * The BTextEditorOptions stores the options used to
 * configure text entry.
 *
 * @author    Brian Frank
 * @creation  6 Jul 01
 * @version   $Revision: 14$ $Date: 4/27/05 9:29:31 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Should spaces be shown while editing text.
 */
@NiagaraProperty(
  name = "showSpaces",
  type = "boolean",
  defaultValue = "false"
)
/*
 Should tabs be shown while editing text.
 */
@NiagaraProperty(
  name = "showTabs",
  type = "boolean",
  defaultValue = "false"
)
/*
 Should newlines be shown while editing text.
 */
@NiagaraProperty(
  name = "showNewlines",
  type = "boolean",
  defaultValue = "false"
)
/*
 This is the number of space characters that a
 tab occupies in the text editor.
 */
@NiagaraProperty(
  name = "tabToSpaceConversion",
  type = "int",
  defaultValue = "2",
  facets = @Facet("BFacets.make(BFacets.MIN, BInteger.make(0))")
)
/*
 Display a vertical line at the specified column index,
 or zero to disable this feature.
 */
@NiagaraProperty(
  name = "showMargin",
  type = "int",
  defaultValue = "0",
  facets = @Facet("BFacets.make(BFacets.MIN, BInteger.make(0))")
)
/*
 Stores the color coding for the segment types.
 */
@NiagaraProperty(
  name = "colorCoding",
  type = "BColorCoding",
  defaultValue = "new BColorCoding()"
)
/*
 Stores the key to command bindings.
 */
@NiagaraProperty(
  name = "keyBindings",
  type = "BKeyBindings",
  defaultValue = "new BKeyBindings()"
)
/*
 If true then cursor movement is added to the
 undo stack, otherwise only true edits are
 added to the undo stack.
 */
@NiagaraProperty(
  name = "undoNavigation",
  type = "boolean",
  defaultValue = "true"
)
/*
 Match parenthesis "(...)" as they are typed.
 */
@NiagaraProperty(
  name = "matchParens",
  type = "boolean",
  defaultValue = "true"
)
/*
 Match braces "{...}" as they are typed.
 */
@NiagaraProperty(
  name = "matchBraces",
  type = "boolean",
  defaultValue = "true"
)
/*
 Match brackets "[...]" as they are typed.
 */
@NiagaraProperty(
  name = "matchBrackets",
  type = "boolean",
  defaultValue = "true"
)
/*
 If true, the word right command moves to the end of the
 next word, otherwise it moves to the beginning of the
 next word.
 */
@NiagaraProperty(
  name = "wordRightToEndOfWord",
  type = "boolean",
  defaultValue = "true"
)
public class BTextEditorOptions
  extends BUserOptions
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.text.BTextEditorOptions(465896829)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "showSpaces"

  /**
   * Slot for the {@code showSpaces} property.
   * Should spaces be shown while editing text.
   * @see #getShowSpaces
   * @see #setShowSpaces
   */
  public static final Property showSpaces = newProperty(0, false, null);

  /**
   * Get the {@code showSpaces} property.
   * Should spaces be shown while editing text.
   * @see #showSpaces
   */
  public boolean getShowSpaces() { return getBoolean(showSpaces); }

  /**
   * Set the {@code showSpaces} property.
   * Should spaces be shown while editing text.
   * @see #showSpaces
   */
  public void setShowSpaces(boolean v) { setBoolean(showSpaces, v, null); }

  //endregion Property "showSpaces"

  //region Property "showTabs"

  /**
   * Slot for the {@code showTabs} property.
   * Should tabs be shown while editing text.
   * @see #getShowTabs
   * @see #setShowTabs
   */
  public static final Property showTabs = newProperty(0, false, null);

  /**
   * Get the {@code showTabs} property.
   * Should tabs be shown while editing text.
   * @see #showTabs
   */
  public boolean getShowTabs() { return getBoolean(showTabs); }

  /**
   * Set the {@code showTabs} property.
   * Should tabs be shown while editing text.
   * @see #showTabs
   */
  public void setShowTabs(boolean v) { setBoolean(showTabs, v, null); }

  //endregion Property "showTabs"

  //region Property "showNewlines"

  /**
   * Slot for the {@code showNewlines} property.
   * Should newlines be shown while editing text.
   * @see #getShowNewlines
   * @see #setShowNewlines
   */
  public static final Property showNewlines = newProperty(0, false, null);

  /**
   * Get the {@code showNewlines} property.
   * Should newlines be shown while editing text.
   * @see #showNewlines
   */
  public boolean getShowNewlines() { return getBoolean(showNewlines); }

  /**
   * Set the {@code showNewlines} property.
   * Should newlines be shown while editing text.
   * @see #showNewlines
   */
  public void setShowNewlines(boolean v) { setBoolean(showNewlines, v, null); }

  //endregion Property "showNewlines"

  //region Property "tabToSpaceConversion"

  /**
   * Slot for the {@code tabToSpaceConversion} property.
   * This is the number of space characters that a
   * tab occupies in the text editor.
   * @see #getTabToSpaceConversion
   * @see #setTabToSpaceConversion
   */
  public static final Property tabToSpaceConversion = newProperty(0, 2, BFacets.make(BFacets.MIN, BInteger.make(0)));

  /**
   * Get the {@code tabToSpaceConversion} property.
   * This is the number of space characters that a
   * tab occupies in the text editor.
   * @see #tabToSpaceConversion
   */
  public int getTabToSpaceConversion() { return getInt(tabToSpaceConversion); }

  /**
   * Set the {@code tabToSpaceConversion} property.
   * This is the number of space characters that a
   * tab occupies in the text editor.
   * @see #tabToSpaceConversion
   */
  public void setTabToSpaceConversion(int v) { setInt(tabToSpaceConversion, v, null); }

  //endregion Property "tabToSpaceConversion"

  //region Property "showMargin"

  /**
   * Slot for the {@code showMargin} property.
   * Display a vertical line at the specified column index,
   * or zero to disable this feature.
   * @see #getShowMargin
   * @see #setShowMargin
   */
  public static final Property showMargin = newProperty(0, 0, BFacets.make(BFacets.MIN, BInteger.make(0)));

  /**
   * Get the {@code showMargin} property.
   * Display a vertical line at the specified column index,
   * or zero to disable this feature.
   * @see #showMargin
   */
  public int getShowMargin() { return getInt(showMargin); }

  /**
   * Set the {@code showMargin} property.
   * Display a vertical line at the specified column index,
   * or zero to disable this feature.
   * @see #showMargin
   */
  public void setShowMargin(int v) { setInt(showMargin, v, null); }

  //endregion Property "showMargin"

  //region Property "colorCoding"

  /**
   * Slot for the {@code colorCoding} property.
   * Stores the color coding for the segment types.
   * @see #getColorCoding
   * @see #setColorCoding
   */
  public static final Property colorCoding = newProperty(0, new BColorCoding(), null);

  /**
   * Get the {@code colorCoding} property.
   * Stores the color coding for the segment types.
   * @see #colorCoding
   */
  public BColorCoding getColorCoding() { return (BColorCoding)get(colorCoding); }

  /**
   * Set the {@code colorCoding} property.
   * Stores the color coding for the segment types.
   * @see #colorCoding
   */
  public void setColorCoding(BColorCoding v) { set(colorCoding, v, null); }

  //endregion Property "colorCoding"

  //region Property "keyBindings"

  /**
   * Slot for the {@code keyBindings} property.
   * Stores the key to command bindings.
   * @see #getKeyBindings
   * @see #setKeyBindings
   */
  public static final Property keyBindings = newProperty(0, new BKeyBindings(), null);

  /**
   * Get the {@code keyBindings} property.
   * Stores the key to command bindings.
   * @see #keyBindings
   */
  public BKeyBindings getKeyBindings() { return (BKeyBindings)get(keyBindings); }

  /**
   * Set the {@code keyBindings} property.
   * Stores the key to command bindings.
   * @see #keyBindings
   */
  public void setKeyBindings(BKeyBindings v) { set(keyBindings, v, null); }

  //endregion Property "keyBindings"

  //region Property "undoNavigation"

  /**
   * Slot for the {@code undoNavigation} property.
   * If true then cursor movement is added to the
   * undo stack, otherwise only true edits are
   * added to the undo stack.
   * @see #getUndoNavigation
   * @see #setUndoNavigation
   */
  public static final Property undoNavigation = newProperty(0, true, null);

  /**
   * Get the {@code undoNavigation} property.
   * If true then cursor movement is added to the
   * undo stack, otherwise only true edits are
   * added to the undo stack.
   * @see #undoNavigation
   */
  public boolean getUndoNavigation() { return getBoolean(undoNavigation); }

  /**
   * Set the {@code undoNavigation} property.
   * If true then cursor movement is added to the
   * undo stack, otherwise only true edits are
   * added to the undo stack.
   * @see #undoNavigation
   */
  public void setUndoNavigation(boolean v) { setBoolean(undoNavigation, v, null); }

  //endregion Property "undoNavigation"

  //region Property "matchParens"

  /**
   * Slot for the {@code matchParens} property.
   * Match parenthesis "(...)" as they are typed.
   * @see #getMatchParens
   * @see #setMatchParens
   */
  public static final Property matchParens = newProperty(0, true, null);

  /**
   * Get the {@code matchParens} property.
   * Match parenthesis "(...)" as they are typed.
   * @see #matchParens
   */
  public boolean getMatchParens() { return getBoolean(matchParens); }

  /**
   * Set the {@code matchParens} property.
   * Match parenthesis "(...)" as they are typed.
   * @see #matchParens
   */
  public void setMatchParens(boolean v) { setBoolean(matchParens, v, null); }

  //endregion Property "matchParens"

  //region Property "matchBraces"

  /**
   * Slot for the {@code matchBraces} property.
   * Match braces "{...}" as they are typed.
   * @see #getMatchBraces
   * @see #setMatchBraces
   */
  public static final Property matchBraces = newProperty(0, true, null);

  /**
   * Get the {@code matchBraces} property.
   * Match braces "{...}" as they are typed.
   * @see #matchBraces
   */
  public boolean getMatchBraces() { return getBoolean(matchBraces); }

  /**
   * Set the {@code matchBraces} property.
   * Match braces "{...}" as they are typed.
   * @see #matchBraces
   */
  public void setMatchBraces(boolean v) { setBoolean(matchBraces, v, null); }

  //endregion Property "matchBraces"

  //region Property "matchBrackets"

  /**
   * Slot for the {@code matchBrackets} property.
   * Match brackets "[...]" as they are typed.
   * @see #getMatchBrackets
   * @see #setMatchBrackets
   */
  public static final Property matchBrackets = newProperty(0, true, null);

  /**
   * Get the {@code matchBrackets} property.
   * Match brackets "[...]" as they are typed.
   * @see #matchBrackets
   */
  public boolean getMatchBrackets() { return getBoolean(matchBrackets); }

  /**
   * Set the {@code matchBrackets} property.
   * Match brackets "[...]" as they are typed.
   * @see #matchBrackets
   */
  public void setMatchBrackets(boolean v) { setBoolean(matchBrackets, v, null); }

  //endregion Property "matchBrackets"

  //region Property "wordRightToEndOfWord"

  /**
   * Slot for the {@code wordRightToEndOfWord} property.
   * If true, the word right command moves to the end of the
   * next word, otherwise it moves to the beginning of the
   * next word.
   * @see #getWordRightToEndOfWord
   * @see #setWordRightToEndOfWord
   */
  public static final Property wordRightToEndOfWord = newProperty(0, true, null);

  /**
   * Get the {@code wordRightToEndOfWord} property.
   * If true, the word right command moves to the end of the
   * next word, otherwise it moves to the beginning of the
   * next word.
   * @see #wordRightToEndOfWord
   */
  public boolean getWordRightToEndOfWord() { return getBoolean(wordRightToEndOfWord); }

  /**
   * Set the {@code wordRightToEndOfWord} property.
   * If true, the word right command moves to the end of the
   * next word, otherwise it moves to the beginning of the
   * next word.
   * @see #wordRightToEndOfWord
   */
  public void setWordRightToEndOfWord(boolean v) { setBoolean(wordRightToEndOfWord, v, null); }

  //endregion Property "wordRightToEndOfWord"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTextEditorOptions.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BOptions
////////////////////////////////////////////////////////////////
  
  /**
   * Get the default instance of the options.
   */
  public static BTextEditorOptions make()
  {
    if (options == null)                        
    {                 
      BWidgetApplication app = BWidget.getApplication();     
      if (app != null)
        options = (BTextEditorOptions)app.getOptionsManager().load(TYPE);
      else
        options = new BTextEditorOptions();
    }
    return options;
  }
  private static BTextEditorOptions options;

  
////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////
  
  public void changed(Property prop, Context cx)
  {
    super.changed(prop, cx);
    if (prop == keyBindings) getKeyBindings().updateTable();
  }

}
