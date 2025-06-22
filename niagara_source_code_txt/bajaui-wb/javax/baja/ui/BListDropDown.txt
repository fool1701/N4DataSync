/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.event.*;
import javax.baja.ui.list.*;

import com.tridium.ui.*;
import com.tridium.ui.theme.custom.nss.StyleUtils;

/**
 * BListDropDown is a BDropDown for a BList.  The list's cell 
 * renderer is used to paint the contents of the currently
 * selected item.
 *
 * @author    Brian Frank       
 * @creation  29 Dec 00
 * @version   $Revision: 6$ $Date: 5/19/10 9:52:40 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Process an action on the list.
 */
@NiagaraAction(
  name = "listActionPerformed"
)
public class BListDropDown
  extends BDropDown
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BListDropDown(3338815408)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "listActionPerformed"

  /**
   * Slot for the {@code listActionPerformed} action.
   * Process an action on the list.
   * @see #listActionPerformed()
   */
  public static final Action listActionPerformed = newAction(0, null);

  /**
   * Invoke the {@code listActionPerformed} action.
   * Process an action on the list.
   * @see #listActionPerformed
   */
  public void listActionPerformed() { invoke(listActionPerformed, null, null); }

  //endregion Action "listActionPerformed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BListDropDown.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a new text drop down.
   */
  public BListDropDown()
  {
    BListDropDownDisplay display = new BListDropDownDisplay();
    BList list = new BList();
    
    setDisplayWidget(display);
    setDropDownWidget(list);
    
    // init list
    list.setVisible(false);
    list.setMultipleSelection(false);
    
    // install a controller which automatically 
    // selects the row the mouse is over
    list.setController(new ListController()
    {
       public void itemEntered(BMouseEvent event, int index)
       { 
         getSelection().deselectAll(); 
         getSelection().select(index); 
       }    
       public void mouseDragged(BMouseEvent event) 
       {
         mouseMoved(event);
       }     
    });
    
    // initialize links
    linkTo(list, BList.actionPerformed, BListDropDown.listActionPerformed);
    linkTo(list, BList.cancelled, BListDropDown.closeDropDown);
  }
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  
  
  /**
   * Get the list for the drop down.
   */
  public BList getList()
  {
    return (BList)getDropDownWidget();
  }
  
  /**
   * Get the selected index of the list or -1 if no selection.
   */
  public int getSelectedIndex()
  {
    return getList().getSelectedIndex();
  }

  /**
   * Get the selected value of the list or null if no selection.
   */
  public Object getSelectedItem()
  {
    return getList().getSelectedItem();
  }

  /**
   * Set the selected index of the list.
   */
  public void setSelectedIndex(int index)
  {
    int oldIndex = getList().getSelectedIndex();
    if (oldIndex != index)
    {
      getList().setSelectedIndex(index);
      fireValueModified(new BWidgetEvent(BWidgetEvent.MODIFIED, this));
    }
  }

  /**
   * Get the selected value of the list.
   */
  public void setSelectedItem(Object item)
  {
    Object oldItem = getList().getSelectedItem();
    if (oldItem != item)
    {
      getList().setSelectedItem(item);
      fireValueModified(new BWidgetEvent(BWidgetEvent.MODIFIED, this));
    }
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /**
   * Open drop down.
   */
  public void doOpenDropDown()
  {
    // save the current index before we open drop down
    index = getList().getSelectedIndex();
    
    // super
    super.doOpenDropDown();
  }

  /**
   * Close drop down.
   */
  public void doCloseDropDown()
  {
    // super
    super.doCloseDropDown();
    
    // set the list's selection back to the index
    // it was when opened because any close other
    // than a list.actionPerformed is a cancel
    getList().setSelectedIndex(index);
    
    // always request focus after dropdown
    getDisplayWidget().requestFocus();
  }

  /**
   * Process an action performed event from the BList.
   */
  public void doListActionPerformed()
  {
    // always fire a change if user made a selection (this
    // handles cases where we want to force a modified event)
    index = getList().getSelectedIndex();
    fireValueModified(new BWidgetEvent(BWidgetEvent.MODIFIED, this));
    
    // close dropdown and repaint  
    closeDropDown();
    repaint();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  int index = -1;

}
