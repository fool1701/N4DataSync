/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.options;

import java.util.ArrayList;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.IFilter;
import javax.baja.sys.BComponent;
import javax.baja.sys.BString;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BMruOptions maintains a list of most recently used objects.
 * By convention instances of BMruOptions are stored under an 
 * options container keyed as "mru".
 *
 * @author    Andy Frank       
 * @creation  18 Nov 03
 * @version   $Revision: 5$ $Date: 3/28/05 10:32:26 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BMruOptions
  extends BOptions
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.options.BMruOptions(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMruOptions.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  
  
////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Get the specified MRU options by name.  If <code>name</code> 
   * does not exist, it is created.  By convention instances of 
   * BMruOptions are stored under an options container keyed as "mru".
   */
  public static BMruOptions make(String name)
  {                               
    BOptions parent = load("mru", BOptions.TYPE);
    
    BMruOptions options = (BMruOptions)parent.get(name);
    if (options == null)
      parent.add(name, options = new BMruOptions());
      
    return options;
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Get the container options.
   */
  public BOptions getParentOptions()
  {
    return (BOptions)getParent();
  }  

  /**
   * Save the options.
   */
  public void save()
  {                  
    getParentOptions().save();
  }
  
  /**
   * Save the specified value as the most recently used
   * object in this options list.  Truncate list to last
   * 15 entries if necessary.
   */
  public void save(String value)
  {
    BString bstr = BString.make(value);

    // Remove duplicates
    Property[] props = getPropertiesArray();
    for (int i=0; i<props.length; i++)
      if (bstr.equals(get(props[i])))
        remove(props[i]);
    
    add(null, bstr);
    
    // Maintain only last 15 queries
    props = getPropertiesArray();
    int index = 0;
    while (props.length-index > 15) 
      remove(props[index++]);     
      
    save();
  }

  /**
   * Convenience for <code>getHistory(null)</code>.
   */
  public String[] getHistory() 
  { 
    return getHistory(null); 
  }

  /**
   * Get the MRU historical list. The list is ordered 
   * with the most recent object at index 0.  If no entries 
   * have been saved yet return an array of length 0.
   */
  public String[] getHistory(IFilter filter)
  {
    BString[] bstr = getChildren(BString.class);
    ArrayList<String> list = new ArrayList<>();
    for (int i=0; i<bstr.length; i++) 
    {
      String temp = bstr[i].getString();
      if (filter == null || filter.accept(temp)) 
        list.add(0, temp);
    }
    return list.toArray(new String[list.size()]);
  }
  
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BOptions;
  }
}
