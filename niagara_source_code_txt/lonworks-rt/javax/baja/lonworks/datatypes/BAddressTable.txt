/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import javax.baja.nre.annotations.NiagaraProperty;
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
@NiagaraProperty(
  name = "entry0",
  type = "BAddressEntry",
  defaultValue = "BAddressEntry.DEFAULT"
)
@NiagaraProperty(
  name = "entry1",
  type = "BAddressEntry",
  defaultValue = "BAddressEntry.DEFAULT"
)
@NiagaraProperty(
  name = "entry2",
  type = "BAddressEntry",
  defaultValue = "BAddressEntry.DEFAULT"
)
@NiagaraProperty(
  name = "entry3",
  type = "BAddressEntry",
  defaultValue = "BAddressEntry.DEFAULT"
)
@NiagaraProperty(
  name = "entry4",
  type = "BAddressEntry",
  defaultValue = "BAddressEntry.DEFAULT"
)
@NiagaraProperty(
  name = "entry5",
  type = "BAddressEntry",
  defaultValue = "BAddressEntry.DEFAULT"
)
@NiagaraProperty(
  name = "entry6",
  type = "BAddressEntry",
  defaultValue = "BAddressEntry.DEFAULT"
)
@NiagaraProperty(
  name = "entry7",
  type = "BAddressEntry",
  defaultValue = "BAddressEntry.DEFAULT"
)
@NiagaraProperty(
  name = "entry8",
  type = "BAddressEntry",
  defaultValue = "BAddressEntry.DEFAULT"
)
@NiagaraProperty(
  name = "entry9",
  type = "BAddressEntry",
  defaultValue = "BAddressEntry.DEFAULT"
)
@NiagaraProperty(
  name = "entry10",
  type = "BAddressEntry",
  defaultValue = "BAddressEntry.DEFAULT"
)
@NiagaraProperty(
  name = "entry11",
  type = "BAddressEntry",
  defaultValue = "BAddressEntry.DEFAULT"
)
@NiagaraProperty(
  name = "entry12",
  type = "BAddressEntry",
  defaultValue = "BAddressEntry.DEFAULT"
)
@NiagaraProperty(
  name = "entry13",
  type = "BAddressEntry",
  defaultValue = "BAddressEntry.DEFAULT"
)
@NiagaraProperty(
  name = "entry14",
  type = "BAddressEntry",
  defaultValue = "BAddressEntry.DEFAULT"
)
public class BAddressTable
  extends BStruct
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BAddressTable(4214794735)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "entry0"

  /**
   * Slot for the {@code entry0} property.
   * @see #getEntry0
   * @see #setEntry0
   */
  public static final Property entry0 = newProperty(0, BAddressEntry.DEFAULT, null);

  /**
   * Get the {@code entry0} property.
   * @see #entry0
   */
  public BAddressEntry getEntry0() { return (BAddressEntry)get(entry0); }

  /**
   * Set the {@code entry0} property.
   * @see #entry0
   */
  public void setEntry0(BAddressEntry v) { set(entry0, v, null); }

  //endregion Property "entry0"

  //region Property "entry1"

  /**
   * Slot for the {@code entry1} property.
   * @see #getEntry1
   * @see #setEntry1
   */
  public static final Property entry1 = newProperty(0, BAddressEntry.DEFAULT, null);

  /**
   * Get the {@code entry1} property.
   * @see #entry1
   */
  public BAddressEntry getEntry1() { return (BAddressEntry)get(entry1); }

  /**
   * Set the {@code entry1} property.
   * @see #entry1
   */
  public void setEntry1(BAddressEntry v) { set(entry1, v, null); }

  //endregion Property "entry1"

  //region Property "entry2"

  /**
   * Slot for the {@code entry2} property.
   * @see #getEntry2
   * @see #setEntry2
   */
  public static final Property entry2 = newProperty(0, BAddressEntry.DEFAULT, null);

  /**
   * Get the {@code entry2} property.
   * @see #entry2
   */
  public BAddressEntry getEntry2() { return (BAddressEntry)get(entry2); }

  /**
   * Set the {@code entry2} property.
   * @see #entry2
   */
  public void setEntry2(BAddressEntry v) { set(entry2, v, null); }

  //endregion Property "entry2"

  //region Property "entry3"

  /**
   * Slot for the {@code entry3} property.
   * @see #getEntry3
   * @see #setEntry3
   */
  public static final Property entry3 = newProperty(0, BAddressEntry.DEFAULT, null);

  /**
   * Get the {@code entry3} property.
   * @see #entry3
   */
  public BAddressEntry getEntry3() { return (BAddressEntry)get(entry3); }

  /**
   * Set the {@code entry3} property.
   * @see #entry3
   */
  public void setEntry3(BAddressEntry v) { set(entry3, v, null); }

  //endregion Property "entry3"

  //region Property "entry4"

  /**
   * Slot for the {@code entry4} property.
   * @see #getEntry4
   * @see #setEntry4
   */
  public static final Property entry4 = newProperty(0, BAddressEntry.DEFAULT, null);

  /**
   * Get the {@code entry4} property.
   * @see #entry4
   */
  public BAddressEntry getEntry4() { return (BAddressEntry)get(entry4); }

  /**
   * Set the {@code entry4} property.
   * @see #entry4
   */
  public void setEntry4(BAddressEntry v) { set(entry4, v, null); }

  //endregion Property "entry4"

  //region Property "entry5"

  /**
   * Slot for the {@code entry5} property.
   * @see #getEntry5
   * @see #setEntry5
   */
  public static final Property entry5 = newProperty(0, BAddressEntry.DEFAULT, null);

  /**
   * Get the {@code entry5} property.
   * @see #entry5
   */
  public BAddressEntry getEntry5() { return (BAddressEntry)get(entry5); }

  /**
   * Set the {@code entry5} property.
   * @see #entry5
   */
  public void setEntry5(BAddressEntry v) { set(entry5, v, null); }

  //endregion Property "entry5"

  //region Property "entry6"

  /**
   * Slot for the {@code entry6} property.
   * @see #getEntry6
   * @see #setEntry6
   */
  public static final Property entry6 = newProperty(0, BAddressEntry.DEFAULT, null);

  /**
   * Get the {@code entry6} property.
   * @see #entry6
   */
  public BAddressEntry getEntry6() { return (BAddressEntry)get(entry6); }

  /**
   * Set the {@code entry6} property.
   * @see #entry6
   */
  public void setEntry6(BAddressEntry v) { set(entry6, v, null); }

  //endregion Property "entry6"

  //region Property "entry7"

  /**
   * Slot for the {@code entry7} property.
   * @see #getEntry7
   * @see #setEntry7
   */
  public static final Property entry7 = newProperty(0, BAddressEntry.DEFAULT, null);

  /**
   * Get the {@code entry7} property.
   * @see #entry7
   */
  public BAddressEntry getEntry7() { return (BAddressEntry)get(entry7); }

  /**
   * Set the {@code entry7} property.
   * @see #entry7
   */
  public void setEntry7(BAddressEntry v) { set(entry7, v, null); }

  //endregion Property "entry7"

  //region Property "entry8"

  /**
   * Slot for the {@code entry8} property.
   * @see #getEntry8
   * @see #setEntry8
   */
  public static final Property entry8 = newProperty(0, BAddressEntry.DEFAULT, null);

  /**
   * Get the {@code entry8} property.
   * @see #entry8
   */
  public BAddressEntry getEntry8() { return (BAddressEntry)get(entry8); }

  /**
   * Set the {@code entry8} property.
   * @see #entry8
   */
  public void setEntry8(BAddressEntry v) { set(entry8, v, null); }

  //endregion Property "entry8"

  //region Property "entry9"

  /**
   * Slot for the {@code entry9} property.
   * @see #getEntry9
   * @see #setEntry9
   */
  public static final Property entry9 = newProperty(0, BAddressEntry.DEFAULT, null);

  /**
   * Get the {@code entry9} property.
   * @see #entry9
   */
  public BAddressEntry getEntry9() { return (BAddressEntry)get(entry9); }

  /**
   * Set the {@code entry9} property.
   * @see #entry9
   */
  public void setEntry9(BAddressEntry v) { set(entry9, v, null); }

  //endregion Property "entry9"

  //region Property "entry10"

  /**
   * Slot for the {@code entry10} property.
   * @see #getEntry10
   * @see #setEntry10
   */
  public static final Property entry10 = newProperty(0, BAddressEntry.DEFAULT, null);

  /**
   * Get the {@code entry10} property.
   * @see #entry10
   */
  public BAddressEntry getEntry10() { return (BAddressEntry)get(entry10); }

  /**
   * Set the {@code entry10} property.
   * @see #entry10
   */
  public void setEntry10(BAddressEntry v) { set(entry10, v, null); }

  //endregion Property "entry10"

  //region Property "entry11"

  /**
   * Slot for the {@code entry11} property.
   * @see #getEntry11
   * @see #setEntry11
   */
  public static final Property entry11 = newProperty(0, BAddressEntry.DEFAULT, null);

  /**
   * Get the {@code entry11} property.
   * @see #entry11
   */
  public BAddressEntry getEntry11() { return (BAddressEntry)get(entry11); }

  /**
   * Set the {@code entry11} property.
   * @see #entry11
   */
  public void setEntry11(BAddressEntry v) { set(entry11, v, null); }

  //endregion Property "entry11"

  //region Property "entry12"

  /**
   * Slot for the {@code entry12} property.
   * @see #getEntry12
   * @see #setEntry12
   */
  public static final Property entry12 = newProperty(0, BAddressEntry.DEFAULT, null);

  /**
   * Get the {@code entry12} property.
   * @see #entry12
   */
  public BAddressEntry getEntry12() { return (BAddressEntry)get(entry12); }

  /**
   * Set the {@code entry12} property.
   * @see #entry12
   */
  public void setEntry12(BAddressEntry v) { set(entry12, v, null); }

  //endregion Property "entry12"

  //region Property "entry13"

  /**
   * Slot for the {@code entry13} property.
   * @see #getEntry13
   * @see #setEntry13
   */
  public static final Property entry13 = newProperty(0, BAddressEntry.DEFAULT, null);

  /**
   * Get the {@code entry13} property.
   * @see #entry13
   */
  public BAddressEntry getEntry13() { return (BAddressEntry)get(entry13); }

  /**
   * Set the {@code entry13} property.
   * @see #entry13
   */
  public void setEntry13(BAddressEntry v) { set(entry13, v, null); }

  //endregion Property "entry13"

  //region Property "entry14"

  /**
   * Slot for the {@code entry14} property.
   * @see #getEntry14
   * @see #setEntry14
   */
  public static final Property entry14 = newProperty(0, BAddressEntry.DEFAULT, null);

  /**
   * Get the {@code entry14} property.
   * @see #entry14
   */
  public BAddressEntry getEntry14() { return (BAddressEntry)get(entry14); }

  /**
   * Set the {@code entry14} property.
   * @see #entry14
   */
  public void setEntry14(BAddressEntry v) { set(entry14, v, null); }

  //endregion Property "entry14"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAddressTable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public void clearTable()
  {
    setEntry0 (BAddressEntry.DEFAULT);
    setEntry1 (BAddressEntry.DEFAULT);
    setEntry2 (BAddressEntry.DEFAULT);
    setEntry3 (BAddressEntry.DEFAULT);
    setEntry4 (BAddressEntry.DEFAULT);
    setEntry5 (BAddressEntry.DEFAULT);
    setEntry6 (BAddressEntry.DEFAULT);
    setEntry7 (BAddressEntry.DEFAULT);
    setEntry8 (BAddressEntry.DEFAULT);
    setEntry9 (BAddressEntry.DEFAULT);
    setEntry10(BAddressEntry.DEFAULT);
    setEntry11(BAddressEntry.DEFAULT);
    setEntry12(BAddressEntry.DEFAULT);
    setEntry13(BAddressEntry.DEFAULT);
    setEntry14(BAddressEntry.DEFAULT);
  }
  
  public BAddressEntry getAddressEntry(int index)
  {
    switch(index)
    {
      case 0  : return getEntry0  ();
      case 1  : return getEntry1  ();
      case 2  : return getEntry2  ();
      case 3  : return getEntry3  ();
      case 4  : return getEntry4  ();
      case 5  : return getEntry5  ();
      case 6  : return getEntry6  ();
      case 7  : return getEntry7  ();
      case 8  : return getEntry8  ();
      case 9  : return getEntry9  ();
      case 10 : return getEntry10 ();
      case 11 : return getEntry11 ();
      case 12 : return getEntry12 ();
      case 13 : return getEntry13 ();
      case 14 : return getEntry14 ();
    }
    return BAddressEntry.DEFAULT;
  } 
     
  public void setAddressEntry(int index, BIAddressEntry ie)
    { setAddressEntry(index, ie, null); }
    
  public void setAddressEntry(int index, BIAddressEntry ie, Context c)
  {
    BAddressEntry e = BAddressEntry.make(ie);
    //if(getEntry(index).equals(e)) return;
    switch(index)
    {
      case 0  : set(entry0 , e, c); break;
      case 1  : set(entry1 , e, c); break;
      case 2  : set(entry2 , e, c); break;
      case 3  : set(entry3 , e, c); break;
      case 4  : set(entry4 , e, c); break;
      case 5  : set(entry5 , e, c); break;
      case 6  : set(entry6 , e, c); break;
      case 7  : set(entry7 , e, c); break;
      case 8  : set(entry8 , e, c); break;
      case 9  : set(entry9 , e, c); break;
      case 10 : set(entry10, e, c); break;
      case 11 : set(entry11, e, c); break;
      case 12 : set(entry12, e, c); break;
      case 13 : set(entry13, e, c); break;
      case 14 : set(entry14, e, c); break;
    }
  }    
  
 
  public BAddressEntry[] getAddresses()
  {
    BAddressEntry[] a = new BAddressEntry[15];
    a[0]  =  getEntry0  ();
    a[1]  =  getEntry1  ();
    a[2]  =  getEntry2  ();
    a[3]  =  getEntry3  ();
    a[4]  =  getEntry4  ();
    a[5]  =  getEntry5  ();
    a[6]  =  getEntry6  ();
    a[7]  =  getEntry7  ();
    a[8]  =  getEntry8  ();
    a[9]  =  getEntry9  ();
    a[10] =  getEntry10 ();
    a[11] =  getEntry11 ();
    a[12] =  getEntry12 ();
    a[13] =  getEntry13 ();
    a[14] =  getEntry14 ();
    return a;
  }
  
}
