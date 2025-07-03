/*
 * Copyright 2005, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.spell;

/**
 * SpellingError represents a invalid word.
 * 
 * @author    Andy Frank
 * @creation  5 Oct 2005
 * @version   $Revision: 2$ $Date: 6/11/07 12:41:33 PM EDT$
 * @since     Baja 1.0
 */
public interface SpellingError
{
  /** 
   * Returns the misspelt word.
   */
  public String getInvalidWord();

  /** 
   * Returns the start character index of the mispelt word in 
   * the context of the text body passed into the SpellChecker.
   */
  public int getPosition();
  
  /** 
   * Returns the list of suggested Word objects.
   */
  public String[] getSuggestions();
}


