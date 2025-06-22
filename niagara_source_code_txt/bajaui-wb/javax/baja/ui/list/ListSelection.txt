/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.list;

import java.util.*;
import javax.baja.ui.event.*;

/**
 * ListSelection is responsible for storing the currently
 * selected items.  The default implementation supports both
 * single or muliple item index selection.  
 *
 * @author    Brian Frank
 * @creation  9 Jul 02
 * @version   $Revision: 9$ $Date: 6/11/07 12:41:33 PM EDT$
 * @since     Baja 1.0
 */
public class ListSelection
  extends BList.ListSupport
{

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Is the specified item index selected.
   */
  public boolean isSelected(int index)
  {
    return bits.get(index);
  }             
  
  /**
   * Select all the items.
   */                     
  public void selectAll()
  {
    int count = getModel().getItemCount();
    if (count > 0) select(0, count-1);
  }  

  /**
   * Convenience for <code>select(index, false)</code>.
   */
  public void select(int index)
  {
    select(index, false);
  }

  /**
   * Add the specified index to the current selection.  
   * Both the lead and anchor become the specified index.
   * If clear flag is true then deselect all first.
   */
  public void select(int index, boolean clear)
  {
    if (clear || index < 0) bits = new BitSet(bits.size());
 
    if (index >= 0)
    {
      bits.set(index);
      anchor = lead = index;
    }
    
    updateList();
  }
  
  /**
   * Convenience for <code>select(anchor, lead, false)</code>.
   */
  public void select(int anchor, int lead)
  {
    select(anchor, lead, false);
  }

  /**
   * Add the items between start and end inclusively
   * to the current selection.  The first index
   * becomes the new anchor, and the second new
   * lead.  If the clear flag is true then deselect all
   * first.
   */
  public void select(int anchor, int lead, boolean clear)
  {
    this.anchor = anchor;
    this.lead = lead;
    
    int start = anchor;
    int end = lead;
    if (end < start) 
      { int temp = start; start = end; end = temp; }
      
    for(int r=start; r<=end; ++r) bits.set(r);
    updateList();
  }
  
  /**
   * Select the specified indices.  The first one is 
   * used as the selection anchor.
   */
  public void select(int[] indices)
  {
    bits = new BitSet(bits.size());
    if (indices.length == 0) return;
    anchor = indices[0];
    lead = indices[indices.length-1];
    for(int i=0; i<indices.length; ++i)
      bits.set(indices[i]);
    updateList();
  }
  
  /**
   * Deselect the specified index, and set both the
   * anchor and lead to the new index.
   */
  public void deselect(int index)
  {
    bits.clear(index);
    anchor = lead = index;
    updateList();
  }

  /**
   * Deselect all the rows.
   */
  public void deselectAll()
  {
    bits = new BitSet(bits.size());
    updateList();
  }

  /**
   * Return true if item count is zero.
   */
  public boolean isEmpty()
  {                     
    return getItemCount() == 0;  // NOTE: performance could be better
  }

  /**
   * Get the number of items that are currently selected.
   */
  public int getItemCount()
  {
    int count = bits.size();
    int n = 0;
    for(int i=0; i<count; ++i)
      if (bits.get(i)) n++;
    return n;
  }

  /**
   * Get the index of the first selected item, or 
   * return -1 if no items are currently selected.
   */
  public int getItem()
  {
    for(int r=0; r<bits.size(); ++r)
      if (bits.get(r)) return r;
    return -1;
  }

  /**
   * Get indices of the selected items, or return an
   * empty array if no items currently selected.
   */
  public int[] getItems()
  {
    int itemCount = getList().getModel().getItemCount();
    int[] temp = new int[itemCount];
    int n = 0;
    
    for(int r=0; r<itemCount; ++r)
      if (bits.get(r)) temp[n++] = r;
    
    int[] result = new int[n];
    System.arraycopy(temp, 0, result, 0, n);
    return result;
  }                  

  /**
   * Convenience for <code>getSubject(lead)</code>
   */
  public final ListSubject getSubject()
  {
    return getSubject(lead);                         
  }                
  
  /**
   * Return the selected items as a Subject.
   */
  public ListSubject getSubject(int activeIndex)
  {                 
    return new ListSubject(getList(), getItems(), activeIndex);
  }  
  
  /**
   * Get the selection anchor index, or -1 if no
   * anchor is active.  The anchor selection is 
   * the usually the first item index selected or 
   * unselected.
   */
  public int getAnchor()
  {
    return anchor;
  }
  
  /**
   * Get the selection lead row index, or -1 if no 
   * lead is active.  The lead selection is 
   * the last item index selected or unselected.
   */
  public int getLead()
  {
    return lead;
  }
    
  /**
   * Call this method to update the table whenever the 
   * selection is modified.  It automatically fires
   * the selectionModified event.
   */
  public void updateList()
  {
    BList list = getList();
    if (list != null) 
    {
      list.repaint();
      getList().fireSelectionModified(new BWidgetEvent(BWidgetEvent.MODIFIED, getList()));
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BitSet bits = new BitSet();
  private int lead = -1;
  private int anchor = -1;

}
