/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.event;

import java.awt.event.InputMethodEvent;
import java.awt.font.TextHitInfo;
import java.text.AttributedCharacterIterator;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;

/**
 * BInputMethodEvent contain information about text that is being
 * composed using an input method. 
 *
 * @author    Tim Urenda
 * @creation  10 Jul 12
 * @version   $Revision: $ $Date: $
 * @since     Baja 3.8
 */
@NiagaraType
public class BInputMethodEvent
  extends BInputEvent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.event.BInputMethodEvent(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BInputMethodEvent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//Ids
////////////////////////////////////////////////////////////////

  public static final int CARET_POSITION_CHANGED    = InputMethodEvent.CARET_POSITION_CHANGED;
  public static final int INPUT_METHOD_TEXT_CHANGED = InputMethodEvent.INPUT_METHOD_TEXT_CHANGED;





////////////////////////////////////////////////////////////////
//Constructors
////////////////////////////////////////////////////////////////

  public BInputMethodEvent(int id, BWidget source,
                           AttributedCharacterIterator text,
                           TextHitInfo caret, TextHitInfo visiblePosition,
                           int committedCharacterCount)
  {
    super(id, source, 0);
    this.caret = caret;
    this.committedCharacterCount = committedCharacterCount;
    this.text = text;
    this.visiblePosition = visiblePosition;
  }
  
  /**
   * No arg constructor
   */
  public BInputMethodEvent()
  {
  }

////////////////////////////////////////////////////////////////
//Access
////////////////////////////////////////////////////////////////

  /**
   * This method returns the input method text. This can be <code>null</code>,
   * and will always be null for <code>CARET_POSITION_CHANGED</code> events.
   * Characters from 0 to <code>getCommittedCharacterCount()-1</code> have
   * been committed, the remaining characters are composed text.
   *
   * @return the input method text, or null
   */
  public AttributedCharacterIterator getText()
  {
    return text;
  }
  
  /**
   * Returns the number of committed characters in the input method text.
   *
   * @return the number of committed characters in the input method text
   */
  public int getCommittedCharacterCount()
  {
    return committedCharacterCount;
  }

  /**
   * Returns the caret position. The caret offset is relative to the composed
   * text of the most recent <code>INPUT_METHOD_TEXT_CHANGED</code> event.
   *
   * @return the caret position, or null
   */
  public TextHitInfo getCaret()
  {
    return caret;
  }

  /**
   * Returns the position that is most important to be visible, or null if
   * such a hint is not necessary. The caret offset is relative to the composed
   * text of the most recent <code>INPUT_METHOD_TEXT_CHANGED</code> event.
   *
   * @return the position that is most important to be visible
   */
  public TextHitInfo getVisiblePosition()
  {
    return visiblePosition;
  }

  /** The input method text. */
  private AttributedCharacterIterator text;
  
  /** The number of committed characters in the text. */
  private int committedCharacterCount;
  
  /** The caret. */
  private TextHitInfo caret;
  
  /** The most important position to be visible. */
  private TextHitInfo visiblePosition;

}
