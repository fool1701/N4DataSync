/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BAbstractBar is the superclass of BMenuBar, BMenu, and BToolBar.
 * It provides convenience methods for manipulating the structure
 * consistently.
 *
 * @author    Brian Frank       
 * @creation  2 Oct 04
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:15 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BAbstractBar
  extends BWidget
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BAbstractBar(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbstractBar.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * This menu recursively removes consecutive separators
   * items.  Consecutive separators are two or more separators
   * that occur without a item between them.
   */
  public void removeConsecutiveSeparators()
  {   
    // walk thru removing all consecutive separators                                    
    Property[] props = getPropertiesArray();
    boolean lastWasSep = true;
    for(int i=0; i<props.length; ++i)
    {              
      if (props[i].isFrozen()) continue;
      BValue value = get(props[i]);
      boolean thisIsSep =  value instanceof BSeparator;
      if (thisIsSep && lastWasSep)
        remove(props[i]);
      else
        lastWasSep = thisIsSep;
      
      // recurse if sub menu
      if (value instanceof BSubMenuItem)
        ((BSubMenuItem)value).getMenu().removeConsecutiveSeparators();
    } 
    
    // at this point we may still have an ending separator
    props = getPropertiesArray();
    if (props.length > 0)
    {
      Property last = props[props.length-1];
      if (get(last) instanceof BSeparator)
        remove(last);
    }
  }              
  
  /**
   * Strip all the child widgets that match the given list of names.
   * This method automatically calls removeConsecutiveSeparators().
   */
  public void strip(String[] names)
  {                                                             
    Property[] props = getPropertiesArray();
    for(int i=0; i<props.length; ++i)
    {                           
      Property prop = props[i];
      if (prop.isFrozen()) continue;
      BValue item = get(prop);
      for(int k=0; k<names.length; ++k)
        if (prop.getName().equals(names[k])) { remove(prop); break; }
    }
    removeConsecutiveSeparators();
  }
  
  /**
   * Keep all the child widgets that match the given list of names,
   * but remove everything else.  This method automatically calls
   * removeConsecutiveSeparators().
   */
  public void keep(String[] names)
  {
    Property[] props = getPropertiesArray();
    for(int i=0; i<props.length; ++i)
    {                           
      Property prop = props[i];
      if (prop.isFrozen()) continue;
      BValue item = get(prop);
      if (item instanceof BSeparator) continue; // leave separators
      boolean keepItem = false;
      for(int k=0; k<names.length; ++k)
        if (prop.getName().equals(names[k])) { keepItem = true; break; }
      if (!keepItem) remove(prop);
    }
    removeConsecutiveSeparators();
  }
  
}
