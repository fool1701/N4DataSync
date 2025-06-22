/*
 * Copyright 2007 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

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
  name = "fileName",
  type = "String",
  defaultValue = ""
)
public class BLocalExtractXifParameter
  extends BStruct
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BLocalExtractXifParameter(1041878234)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "fileName"

  /**
   * Slot for the {@code fileName} property.
   * @see #getFileName
   * @see #setFileName
   */
  public static final Property fileName = newProperty(0, "", null);

  /**
   * Get the {@code fileName} property.
   * @see #fileName
   */
  public String getFileName() { return getString(fileName); }

  /**
   * Set the {@code fileName} property.
   * @see #fileName
   */
  public void setFileName(String v) { setString(fileName, v, null); }

  //endregion Property "fileName"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLocalExtractXifParameter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  
}
