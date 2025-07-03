/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.options;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.IFilter;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BTextDropDown;
import javax.baja.ui.list.BList;

/**
 * BMruTextDropDown is a subclass of BTextDropDown that provides
 * a persistent MRU (most recently used) list of entries.
 *
 * @author    Brian Frank       
 * @creation  20 Dec 03
 * @version   $Revision: 4$ $Date: 3/28/05 10:32:27 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BMruTextDropDown
  extends BTextDropDown
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.options.BMruTextDropDown(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMruTextDropDown.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Create a text drop down using the specified mru options key.
   * The text is initialized by setTextFromMruOptions. 
   */
  public BMruTextDropDown(String mruName)
  {
    this.mruName = mruName;                          
    setTextFromMruOptions();
  }

  /**
   * Create a text drop down using the specified mru options key.
   * The text is initialized by setTextFromMruOptions. 
   */
  public BMruTextDropDown(String mruName, int visibleColumns)
  {                               
    getEditor().setVisibleColumns(visibleColumns);
    this.mruName = mruName;                          
    setTextFromMruOptions();
  }

  /**
   * Create a text drop down using the specified mru options key.
   */
  public BMruTextDropDown(String mruName, String text, int visibleColumns, boolean editable)
  {
    super(text, visibleColumns, editable);
    this.mruName = mruName;
  }

  /**
   * Public no arg constructor.  Do not use directly.
   */
  public BMruTextDropDown()
  {
    this(null);
  }
                       
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the MRU options name.
   */
  public String getMruOptionsName()
  {                       
    return mruName;
  }

  /**
   * Set the MRU options name.
   */
  public void setMruOptionsName(String mruName)
  {                       
    this.mruName = mruName;
  }
  
  /**
   * Get the options by name.
   */
  public BMruOptions getMruOptions()
  {                   
    return BMruOptions.make(getMruOptionsName());
  }                 
  
  /**
   * Get the current value of the text field and save 
   * it in the MruOptions list.  If the text if the empty
   * string then it is not saved. 
   */
  public String getTextAndSave()
  {                              
    String text = getText();
    if (text.length() > 0) getMruOptions().save(text);          
    return text;
  }  
  
  /**
   * Set the text field to the most recently used value
   * found in the MruOptions.  If the history is empty
   * then set the text field to "".
   */
  public void setTextFromMruOptions()
  {                 
    if (mruName == null) return;
    String[] history = getMruOptions().getHistory(filter);
    if (history.length == 0)
      setText("");
    else
      setText(history[0]);
  }  
  
  /**
   * Set the BList of the drop down from the MRU options list.
   */
  public void setListFromMruOptions()
  {             
    BList list = getList();
    list.removeAllItems();
    String[] mru = getMruOptions().getHistory(filter);
    for(int i=0; i<mru.length; ++i)
      list.addItem(mru[i]);
  }
 
  /**
   * Get the filter.
   */
  public IFilter getFilter() 
  { 
    return filter; 
  }

  /**
   * Set the filter.
   */
  public void setFilter(IFilter filter)
  {
    this.filter = filter;
  }

////////////////////////////////////////////////////////////////
// BTextDropDown
////////////////////////////////////////////////////////////////
  
  /**
   * Call <code>setListFromMruOptions()</code> then super.
   */
  public void doOpenDropDown()
  {
    setListFromMruOptions(); 
    super.doOpenDropDown();
  }                                                       
    
                       
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private String mruName;
  private IFilter filter = null;
} 
