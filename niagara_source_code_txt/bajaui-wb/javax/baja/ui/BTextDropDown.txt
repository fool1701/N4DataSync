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

/**
 * BTextDropDown uses a BTextField for the display and a 
 * BList for the drop down.
 *
 * @author    Brian Frank       
 * @creation  29 Dec 00
 * @version   $Revision: 7$ $Date: 5/8/07 4:31:38 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Process an action on the list.
 */
@NiagaraAction(
  name = "listActionPerformed"
)
public class BTextDropDown
  extends BDropDown
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BTextDropDown(3338815408)1.0$ @*/
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
  public static final Type TYPE = Sys.loadType(BTextDropDown.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a new text drop down.
   */
  public BTextDropDown(String text, int visibleColumns, boolean editable)
  {
    this();
    getEditor().setText(text);
    getEditor().setVisibleColumns(visibleColumns);
    getEditor().setEditable(editable);
  }

  /**
   * Construct a new text drop down.
   */
  public BTextDropDown(int visibleColumns, boolean editable)
  {
    this();
    getEditor().setVisibleColumns(visibleColumns);
    getEditor().setEditable(editable);
  }

  /**
   * Construct a new text drop down.
   */
  public BTextDropDown(boolean editable)
  {
    this();
    getEditor().setEditable(editable);
  }

  /**
   * Construct a new text drop down. Populate the list
   * with the string representation of the specified list of objects
   * and set the text to the string representation of the first list item.
   *
   * This method will not work if the list is null or if it contains an item
   * that is null.
   *
   * @param list The list of objects whose string representations you'd
   *             like to make a text drop down of.
   * @param visibleColumns The number of columns which should be visible when
   *                       computing the text editor's preferred layout.
   * @param editable If editable is true then the text may be modified.
   *                 If false the text is readonly.
   */
  public BTextDropDown(java.util.List<?> list, int visibleColumns, boolean editable)
  {
    this(visibleColumns, editable);
    for(int i=0; i<list.size(); ++i)
      getList().addItem(list.get(i).toString());
      
    if (list.size() > 0)
    {
      setText(list.get(0).toString());
      getList().setSelectedIndex(0);
    }
  }

  /**
   * Construct a new text drop down.
   */
  public BTextDropDown()
  {
    this(new BTextField(), new BList());
  }

  /**
   * Construct a new text drop down with the given
   * editor and list.
   */
  public BTextDropDown(BTextField editor, BList list)
  {
    setDisplayWidget(editor);
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
      });
    
    // initialize links
    linkTo(editor, BTextField.actionPerformed, BTextDropDown.actionPerformed);
    linkTo(editor, BTextField.textModified, BTextDropDown.valueModified);
    linkTo(list, BList.actionPerformed, BTextDropDown.listActionPerformed);
    linkTo(list, BList.cancelled, BTextDropDown.closeDropDown);
  }
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Get the text editor.
   */
  public BTextField getEditor() 
  {
    return (BTextField)getDisplayWidget();
  }
  
  /**
   * Get the list for the drop down.
   */
  public BList getList()
  {
    return (BList)getDropDownWidget();
  }

  /**
   * Get the current text value from the editor.
   */
  public String getText()
  {
    return getEditor().getText();
  }

  /**
   * Set the text value of the editor.
   */
  public void setText(String text)
  {
    getEditor().setText(text);
  }

  public void addItem(String text)
  {
    BList itemList = getList();
    itemList.addItem(text);
    itemList.setSelectedIndex(0);
    syncTextFromList();
  }

  public void addItem(BObject entry)
  {
    BList itemList = getList();
    itemList.addItem(entry);
    itemList.setSelectedIndex(0);
    syncTextFromList();
  }

  public BObject getSelectedItem()
  {
    BList itemList = getList();
    return (BObject)itemList.getItem(itemList.getSelectedIndex());
  }

////////////////////////////////////////////////////////////////
// BDropDown
////////////////////////////////////////////////////////////////

  /**
   * Open the drop down.
   */
  public void doOpenDropDown()
  {
    super.doOpenDropDown();
    
    // try and find the current text in the list
    BList list = getList();
    String text = getEditor().getText();
    int row = -1;
    for(int r=0; r<list.getModel().getItemCount(); ++r)
      if (text.equals(list.getModel().getItem(r)))
        { row = r; break; }
    
    // initialize the list to the current 
    // selection or first selection
    if (row < 0) row = 0;
    list.setSelectedIndex(row);
    list.ensureItemIsVisible(row);
  }  

  /**
   * Close drop down.
   */
  public void doCloseDropDown()
  {
    // super
    super.doCloseDropDown();
        
    // always request focus after dropdown
    getDisplayWidget().requestFocus();
  }

  /**
   * Process an action performed event from the BList.
   */
  public void doListActionPerformed()
  {
    if (isDropDownOpen())
    {
      syncTextFromList();
      UiEnv.get().closePopup(null);
    }
  }

  /**
   * Set the value of the text editor with the 
   * selected item from the list.
   */
  protected void syncTextFromList() 
  {
    BList list = getList();
    Object value = list.getSelectedItem();
    if (value != null)
      getEditor().setText( value.toString() );
  }
}
