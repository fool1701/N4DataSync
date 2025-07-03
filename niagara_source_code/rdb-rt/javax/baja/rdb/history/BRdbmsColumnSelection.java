/**
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.history;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BRdbmsColumnSelection is used to specify a relational database column.
 * This is useful when mapping rdb table columns to history columns during
 * the import process.  
 *
 * @author    Scott Hoye
 * @creation  12 Apr 06
 * @version   $Revision: 4$ $Date: 3/19/07 1:17:42 PM EDT$  
 * @since     Baja 3.1     
 */
@NiagaraType
/*
 The rdb table column (list of Strings)
 */
@NiagaraProperty(
  name = "column",
  type = "BDynamicEnum",
  defaultValue = "BDynamicEnum.DEFAULT"
)
public class BRdbmsColumnSelection
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.history.BRdbmsColumnSelection(4123224411)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "column"

  /**
   * Slot for the {@code column} property.
   * The rdb table column (list of Strings)
   * @see #getColumn
   * @see #setColumn
   */
  public static final Property column = newProperty(0, BDynamicEnum.DEFAULT, null);

  /**
   * Get the {@code column} property.
   * The rdb table column (list of Strings)
   * @see #column
   */
  public BDynamicEnum getColumn() { return (BDynamicEnum)get(column); }

  /**
   * Set the {@code column} property.
   * The rdb table column (list of Strings)
   * @see #column
   */
  public void setColumn(BDynamicEnum v) { set(column, v, null); }

  //endregion Property "column"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRdbmsColumnSelection.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public String toString(Context cx)
  {
    return SlotPath.unescape(getColumn().getTag());
  }
  
  public boolean equivalent(Object obj)
  {
    if (obj instanceof BRdbmsColumnSelection)
    {
      return getColumn().getRange().equals(((BRdbmsColumnSelection)obj).getColumn().getRange());
    }
    return false;
  }
}
