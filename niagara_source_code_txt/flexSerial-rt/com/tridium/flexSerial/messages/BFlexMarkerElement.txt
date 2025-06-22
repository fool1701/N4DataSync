/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.flexSerial.enums.BDataTypeEnum;

/**
 * BFlexMarkerElement defines a primitive message element.
 * <P>The purpose of the FlexMessageElement is to completely define a primitive element
 * of a message.  It contain enough information to serialize this element value to and
 * from a native byte array.  The FlexSerial driver provides a special view and editor
 * that can be used to create FlexMessageElements.</P>
 *
 * @author    Andy Saunders
 * @creation  14 Sept 2005
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "size",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY,
  override = true
)
@NiagaraProperty(
  name = "dataType",
  type = "BDataTypeEnum",
  defaultValue = "BDataTypeEnum.Marker",
  flags = Flags.READONLY,
  override = true
)
/*
 This is the value of this message element and is dependent on the dataType
 specified.  This value can be a constant or can be a "indirect" value from
 the object defined in the source property.
 */
@NiagaraProperty(
  name = "value",
  type = "BValue",
  defaultValue = "BString.make(\"\")",
  override = true
)
public class BFlexMarkerElement
  extends BFlexMessageElement
  implements BIFlexMessageElement
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BFlexMarkerElement(3549410834)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "size"

  /**
   * Slot for the {@code size} property.
   * @see #getSize
   * @see #setSize
   */
  public static final Property size = newProperty(Flags.READONLY, 0, null);

  //endregion Property "size"

  //region Property "dataType"

  /**
   * Slot for the {@code dataType} property.
   * @see #getDataType
   * @see #setDataType
   */
  public static final Property dataType = newProperty(Flags.READONLY, BDataTypeEnum.Marker, null);

  //endregion Property "dataType"

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * This is the value of this message element and is dependent on the dataType
   * specified.  This value can be a constant or can be a "indirect" value from
   * the object defined in the source property.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(0, BString.make(""), null);

  //endregion Property "value"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexMarkerElement.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////

}
