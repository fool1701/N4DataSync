/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 *  Contains the data in lonworks device alias table.
 * <p>
 *
 * @author    Robert Adams
 * @creation  8 Nov 00
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 the number of network variable aliases supported on the device
 */
@NiagaraProperty(
  name = "aliasCount",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY
)
/*
 the offset to add to the alias index when accessing alias config data
 */
@NiagaraProperty(
  name = "aliasOffset",
  type = "int",
  defaultValue = "0",
  flags = Flags.HIDDEN
)
public class BAliasTable
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BAliasTable(2579730935)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "aliasCount"

  /**
   * Slot for the {@code aliasCount} property.
   * the number of network variable aliases supported on the device
   * @see #getAliasCount
   * @see #setAliasCount
   */
  public static final Property aliasCount = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code aliasCount} property.
   * the number of network variable aliases supported on the device
   * @see #aliasCount
   */
  public int getAliasCount() { return getInt(aliasCount); }

  /**
   * Set the {@code aliasCount} property.
   * the number of network variable aliases supported on the device
   * @see #aliasCount
   */
  public void setAliasCount(int v) { setInt(aliasCount, v, null); }

  //endregion Property "aliasCount"

  //region Property "aliasOffset"

  /**
   * Slot for the {@code aliasOffset} property.
   * the offset to add to the alias index when accessing alias config data
   * @see #getAliasOffset
   * @see #setAliasOffset
   */
  public static final Property aliasOffset = newProperty(Flags.HIDDEN, 0, null);

  /**
   * Get the {@code aliasOffset} property.
   * the offset to add to the alias index when accessing alias config data
   * @see #aliasOffset
   */
  public int getAliasOffset() { return getInt(aliasOffset); }

  /**
   * Set the {@code aliasOffset} property.
   * the offset to add to the alias index when accessing alias config data
   * @see #aliasOffset
   */
  public void setAliasOffset(int v) { setInt(aliasOffset, v, null); }

  //endregion Property "aliasOffset"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAliasTable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
/* */
  public BAliasTable(){}

  public BAliasTable(int count)
  {
    setAliasCount(count);
    //createCount(count);
    verifyAliasCount();
  }
  
  public void started()
    throws Exception
  {
    super.started();
    
    if(getAliasCount()>0) verifyAliasCount();
  }
  
  public void changed(Property prop, Context context)
  {
    super.changed(prop, context);
    if(!isRunning()) return;

    if(prop == aliasCount){verifyAliasCount();}
  }


  public void verifyAliasCount()
  {
    int count = getAliasCount();
    int i=0;
    String entryName;
    Property prop;
    for(; i<count ; i++)
    {
      entryName = propName(i);
      // If the property already exists set to default else add.
      prop = getProperty(entryName);
      if( prop == null)
        add(entryName,new BAliasConfigData(),null);
    }

    // remove any additional entries
    entryName = propName(i++);
    while( (prop = getProperty(entryName)) != null)
    {
      remove(prop);
      entryName = propName(i++);
    }
  }


  public void clearTable()
  {
    SlotCursor<Property> c = getProperties();
    while(c.next(BAliasConfigData.class))
    {
      ((BAliasConfigData)c.get()).clearData();
    }
  }

  public BAliasConfigData getAliasEntry(int index)
  {
    return (BAliasConfigData)get(propName(index));
  }

  public void setAliasEntry(int index, BAliasConfigData ad)
  {
    set(propName(index),ad);
  }

  public BAliasConfigData[] getAliasArray()
  {
    BAliasConfigData[] a = new BAliasConfigData[getAliasCount()];

    for(int i=0 ; i<a.length ; i++) a[i] = getAliasEntry(i);

    return a;
  }

  private String propName(int ndx) { return "entry" + ndx; }



}
