/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 *  Contains the data in lonworks device address table. A lonworks
 *  address table can have up to 15 entries. See Neuron Chip Data Book A.3.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  8 Nov 00
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
@NiagaraType
public class BExtAddressTable
  extends BComponent
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BExtAddressTable(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:20 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BExtAddressTable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BExtAddressEntry getAddressEntry(int index)
  {
    String name = propName(index);
    Property p = getProperty(name);
    if(p==null) return BExtAddressEntry.DEFAULT;
    return (BExtAddressEntry)get(p);
  } 
  
  public boolean isEntryInUse(int index)
  {
    String name = propName(index);
    Property p = getProperty(name);
    return (p!=null);
  }
  
  public void setAddressEntry(int index, BIAddressEntry ie)
  {
    setAddressEntry(index, ie, null); 
  }
    
  public void setAddressEntry(int index, BIAddressEntry ie, Context c)
  {
    BExtAddressEntry e = BExtAddressEntry.make(ie);
    String name = propName(index);
    Property p = getProperty(name);
    if(p==null)
      add(name,e,c);
    else
      set(p,e,c);
  }    
  
  public void clearTable()
  {
    removeAll();
  }
  private String propName(int ndx) { return "adrEntry" + ndx; }

  
}
