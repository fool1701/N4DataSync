/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BImage;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.IFilter;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BTextDropDown;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BComplexNamePickerFE is used by properties which store
 * the name of BComplex in a container as a BString.
 *
 * @author    Andy Saunders
 * @creation  18 Apr 12
 * @version   $Revision: 4$ $Date: 3/28/05 1:40:34 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BComplexNamePickerFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BComplexNamePickerFE(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BComplexNamePickerFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BComplexNamePickerFE()
  {
    setContent(field);
    linkTo("la", field, BTextDropDown.valueModified,   setModified);
    linkTo("lb", field, BTextDropDown.actionPerformed, actionPerformed);
  }
  
  protected void doSetReadonly(boolean readonly)
  { 
    field.getEditor().setEditable(!readonly);
    field.setDropDownEnabled(!readonly);
  }  

  protected void doLoadValue(BObject value, Context cx)
  {                 
    loadName(value.toString());
  }
  
  public void loadName(String name)
  {   
    // update field and assume no drop down
    field.setText(name);
    field.getList().removeAllItems();
    items = null;
    
    try
    {                    
      // get the list from subclass
      BComplex[] components = list(); 
      
      // filter any hidden components
      try
      {
        Array<BComplex> a = new Array<>(components);
        a = a.filter(new IFilter() {
          public boolean accept(Object obj)
          {
            BComplex c = (BComplex)obj;
            BComplex parent = c.getParent();
            return parent == null || !Flags.isHidden(parent, c.getPropertyInParent());
          }
        });             
        components = a.trim();
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
      
      // populate drop down and check for a name match
      items = new Item[components.length];
      Item match = null;
      for(int i=0; i<items.length; ++i)
      {
        Item item = items[i] = new Item(components[i]);
        if (item.name.equals(name)) match = item;
        field.getList().addItem(item.icon, item);
      }       
      
      // if we found a matching name, then update the text 
      // field with the display name, not the raw name                                   
      if (match != null) field.setText(match.displayName);
    }
    catch(Exception e) 
    {                   
    }    
  }
  
  protected BObject doSaveValue(BObject value, Context cx)
  {  
    return BString.make(saveName());
  }
  
  public String saveName()
  {                
    // get the text (at this point we don't know if
    // it is a new raw name or a display name)
    String name = field.getText();            
    
    // try to find match in 
    if (items != null)
    {           
      for(int i=0; i<items.length; ++i)
        if (items[i].displayName.equals(name))
          { name = items[i].name; break; }
    }
    
    return name;
  }                    
  
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
  
  /**
   * Get the list of components to choose from.  Use
   * one of the loadXXX() utility methods.
   */
  public abstract BComplex[] list()
    throws Exception;             
    
////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  /**
   * Find the specified service for the current session.
   */
  public final BComponent loadService(Type serviceType)
  {
    BWbShell shell = this.getWbShell(); 
    BOrd ord = BOrd.make(shell.getActiveOrd(), "service:" + serviceType.toString());
    return (BComponent)ord.get();
  }

  /**
   * Get the list of components to choose from.  Use
   * one of the loadList() utility methods.
   */
  public final BComponent[] loadFromService(Type serviceType, Class<?> cls)
    throws Exception
  {                                      
    BComponent service = loadService(serviceType);
    service.lease();
    return (BComponent[])service.getChildren(cls);
  }                             
  
////////////////////////////////////////////////////////////////
// Item
////////////////////////////////////////////////////////////////

  static class Item
  {             
    Item(BComplex c) 
    {
      name = c.getName();
      displayName = c.getDisplayName(null);
      icon = BImage.make(c.getIcon());
    }
    
    public String toString() { return displayName; }
    
    String name;
    String displayName;
    BImage icon;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  BTextDropDown field = new BTextDropDown("", 20, true);
  Item[] items;
}
