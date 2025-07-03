/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.options;

import javax.baja.gx.BImage;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.IFilter;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BAbstractButton;
import javax.baja.ui.BButton;
import javax.baja.ui.BMenu;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;

/**
 * BMruButton is a dropdown style button that
 * displays a MRU list.
 *
 * @author    Andy Frank       
 * @creation  18 Nov 03
 * @version   $Revision: 9$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BMruButton
  extends BButton 
  implements BIMruWidget
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.options.BMruButton(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMruButton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Create a new BMruButton for the specifed MRU list.
   */
  public BMruButton(String mruName)
  {
    this(mruName, null);
  }

  /**
   * Create a new BMruButton for the specifed MRU list, and
   * use the specified filter.
   */
  public BMruButton(String mruName, IFilter filter)
  {
    super(icon);
    
    this.mruName = mruName;
    setFilter(filter);
    setMruController(new MruController());
    setMenuController(new MenuController());
  }

  /**
   * Public no arg constructor.  Do not use directly.
   */
  public BMruButton()
  {
    this(null);
  }
                       
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the current installed MruController.
   */
  public MruController getMruController() 
  { 
    return controller; 
  }
  
  /**
   * Install the specified MruController.
   */
  public void setMruController(MruController c) 
  { 
    controller = c; 
  }

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
// MruController
////////////////////////////////////////////////////////////////

  public static class MruController
  {        
  
    /**
     * Return the display string for an entry found in the MRU list.
     */
    public String toDisplayString(String value) { return value; }
    
    /**
     * Callback when MRU list entry is selected from menu.
     */
    public void select(String value) {}
  }

////////////////////////////////////////////////////////////////
// MenuController
////////////////////////////////////////////////////////////////

  private class MenuController implements BAbstractButton.MenuController
  {
    public boolean isMenuDistinct() { return false; }
    public BMenu getMenu(BAbstractButton b) 
    { 
      BMenu menu = new BMenu();
      String[] v = getMruOptions().getHistory(filter);
      for (int i=0; i<v.length; i++)
        menu.add(null, new Entry(BMruButton.this, v[i]));
      return menu; 
    }
  }
  
  private class Entry extends Command
  {
    public Entry(BWidget owner, String value)
    {
      super(owner, controller.toDisplayString(value));
      this.value = value;
    }                       
    
    public CommandArtifact doInvoke()
    {
      controller.select(value);
      return null;
    }
    private String value;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static BImage icon = BImage.make("module://icons/x16/recent.png");
  
  private MruController controller;
  private String mruName;
  private IFilter filter = null;
}
