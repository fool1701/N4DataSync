/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;

import javax.baja.ui.event.*;

/**
 * TextSelection is used to store a BTextWidget's selection.
 *
 * @author    Brian Frank       
 * @creation  5 Aug 01
 * @version   $Revision: 6$ $Date: 3/28/05 10:32:36 AM EST$
 * @since     Baja 1.0
 */
public class TextSelection
  extends BTextEditor.TextSupport
{ 

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Return true if there is no current selection.
   */
  public boolean isEmpty()
  {
    return anchor == null || lead == null || anchor.equals(lead);
  }

  /**
   * Get the anchor position or null if no selection is 
   * active.  The anchor position is the beginning of the 
   * selection and "anchors" the selection as the cursor 
   * is moved before and after the anchor position.  The 
   * anchor position may be before or after the lead 
   * position.
   */
  public Position getAnchor()
  {
    return anchor;
  }
  
  /**
   * Get the lead selection position or null if no
   * selection is active.  The lead is usually the last
   * position selection.  The lead may be before or
   * after the anchor.
   */
  public Position getLead()
  {
    return lead;
  }
  
  /**
   * Get the selection start position, or null if no
   * selection is active.  The start position is 
   * guaranteed to before the end position.
   */
  public Position getStart()
  {
    return start;
  }

  /**
   * Get the selection end position, or null if no
   * selection is active.  The end position is 
   * guaranteed to be after the start position.
   */
  public Position getEnd()
  {
    return end;
  }
  
  /**
   * Select the range of text between the two positions 
   * inclusively.  The first position specified becomes 
   * the new anchor and the second the new lead.  The
   * caret is automatically positioned at the lead.
   */
  public void select(Position anchor, Position lead)
  {
    this.anchor = anchor;
    this.lead = lead;
    
    if (anchor.compareTo(lead) < 0)
      { start = anchor; end = lead; }
    else
      { start = lead; end = anchor; }
      
    getEditor().moveCaretPosition(lead);
    updateEditor();
  }

  /**
   * Select all the text and position the caret at
   * the document start.
   */
  public void selectAll()
  {
    Position docStart = getModel().getStartPosition();
    Position docEnd = getModel().getEndPosition();
    
    if (docStart.equals(docEnd)) return;
    
    anchor = start = docStart;
    lead = end = docEnd;
    getEditor().moveCaretPosition(start);
    updateEditor();
  }
  
  /**
   * Clear the current selection.
   */
  public void deselect()
  {
    if (anchor != null)
    {
      anchor = lead = start = end = null;
      updateEditor();
    }
  }
  
  /**
   * Update the editor with the new selection, and
   * fire the selectionModified event.
   */
  public void updateEditor()
  {
    BTextEditor editor = getEditor();
    editor.updateEnableStates();
    editor.repaint();
    editor.fireSelectionModified(new BWidgetEvent(BWidgetEvent.MODIFIED, getEditor()));
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private Position anchor;
  private Position lead;
  private Position start;
  private Position end;
  
}
