/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 *   BExtDeviceData supports extended address tables as defined in CEA 709.1-B
 * <p>
 *
 * @author    Robert Adams
 * @creation  8 Nov 06
 * @version   $Revision: 2$ $Date: 9/18/01 9:49:36 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 flag indicating if the device uses extended command set
 */
@NiagaraProperty(
  name = "extended",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "addressTable",
  type = "BAddressTable",
  defaultValue = "new BAddressTable()",
  flags = Flags.HIDDEN,
  override = true
)
@NiagaraProperty(
  name = "extAddressTable",
  type = "BExtAddressTable",
  defaultValue = "new BExtAddressTable()",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY,
  override = true
)
public class BExtDeviceData
  extends BDeviceData
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BExtDeviceData(991841037)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "extended"

  /**
   * Slot for the {@code extended} property.
   * flag indicating if the device uses extended command set
   * @see #getExtended
   * @see #setExtended
   */
  public static final Property extended = newProperty(0, true, null);

  /**
   * Get the {@code extended} property.
   * flag indicating if the device uses extended command set
   * @see #extended
   */
  public boolean getExtended() { return getBoolean(extended); }

  /**
   * Set the {@code extended} property.
   * flag indicating if the device uses extended command set
   * @see #extended
   */
  public void setExtended(boolean v) { setBoolean(extended, v, null); }

  //endregion Property "extended"

  //region Property "addressTable"

  /**
   * Slot for the {@code addressTable} property.
   * @see #getAddressTable
   * @see #setAddressTable
   */
  public static final Property addressTable = newProperty(Flags.HIDDEN, new BAddressTable(), null);

  //endregion Property "addressTable"

  //region Property "extAddressTable"

  /**
   * Slot for the {@code extAddressTable} property.
   * @see #getExtAddressTable
   * @see #setExtAddressTable
   */
  public static final Property extAddressTable = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY, new BExtAddressTable(), null);

  //endregion Property "extAddressTable"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BExtDeviceData.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  public BExtDeviceData() {}

  public static BExtDeviceData make(BDeviceData dd)
  {
    BExtDeviceData edd = new BExtDeviceData ();
    Property[] pa = dd.getPropertiesArray();
    for(int i=0 ; i<pa.length ; i++)
    {
      if(pa[i].getDefaultValue().isComplex())
        edd.get(pa[i]).asComplex().copyFrom(dd.get(pa[i]).asComplex());
      else
        edd.set(pa[i],dd.get(pa[i]),null);
    }  
    return edd;
  }

  /**
   * Get the <code>addressTable</code> property.
   * @see javax.baja.lonworks.datatypes.BDeviceData#addressTable
   */
  public BAddressTable getMyAddressTable() { return (BAddressTable)get(addressTable); }
  
  
  /** 
   * Does this device implement extended command messages.
   */
  public boolean isExtended() { return getExtended(); }

  public void clearAddressTable() { getExtAddressTable().clearTable(); }
  public BIAddressEntry getAddressEntry(int index)                   { return getExtAddressTable().getAddressEntry(index); }
  public void setAddressEntry(int index, BIAddressEntry e)           { getExtAddressTable().setAddressEntry(index,e); }
  public void setAddressEntry(int index, BIAddressEntry e, Context c){ getExtAddressTable().setAddressEntry(index,e,c); }       
  

}
