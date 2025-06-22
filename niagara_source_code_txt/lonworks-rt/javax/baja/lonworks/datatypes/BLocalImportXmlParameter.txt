/*
 * Copyright 2007 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BStruct;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;


/**
 *   This class file specifies parameters needed for 
 * importing xml data to local device.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  14 Aug 07
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "file",
  type = "BOrd",
  defaultValue = "BOrd.NULL"
)
public class BLocalImportXmlParameter
  extends BStruct
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BLocalImportXmlParameter(2246395599)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "file"

  /**
   * Slot for the {@code file} property.
   * @see #getFile
   * @see #setFile
   */
  public static final Property file = newProperty(0, BOrd.NULL, null);

  /**
   * Get the {@code file} property.
   * @see #file
   */
  public BOrd getFile() { return (BOrd)get(file); }

  /**
   * Set the {@code file} property.
   * @see #file
   */
  public void setFile(BOrd v) { set(file, v, null); }

  //endregion Property "file"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLocalImportXmlParameter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  
}
