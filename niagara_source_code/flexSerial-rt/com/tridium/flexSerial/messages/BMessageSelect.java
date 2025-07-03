/** Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BString;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.flexSerial.BFlexSerialNetwork;

/**
 * BMessageSelect is a used to select a FlexMessages defined.
 *
 * @author    Andy Saunders
 * @creation  15 Sept 05
 * @version   $Revision:$ $Date: 4/6/2005 5:43:17 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "message",
  type = "String",
  defaultValue = "",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"flexSerial:MessageSelectFE\"))")
)
public class BMessageSelect
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BMessageSelect(1314307385)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "message"

  /**
   * Slot for the {@code message} property.
   * @see #getMessage
   * @see #setMessage
   */
  public static final Property message = newProperty(0, "", BFacets.make(BFacets.FIELD_EDITOR, BString.make("flexSerial:MessageSelectFE")));

  /**
   * Get the {@code message} property.
   * @see #message
   */
  public String getMessage() { return getString(message); }

  /**
   * Set the {@code message} property.
   * @see #message
   */
  public void setMessage(String v) { setString(message, v, null); }

  //endregion Property "message"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMessageSelect.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public static BFlexMessage getFlexMessage(BComplex base, String message)
  {
    BFlexSerialNetwork service = BFlexSerialNetwork.getParentFlexNetwork(base);
    BFlexMessageFolder messageFolder = service.getMessages();
    BFlexMessage[] messages = messageFolder.getChildren(BFlexMessage.class);
    for(int i = 0; i < messages.length; i++)
    {
      if(messages[i].getName().equals(message) )
        return messages[i];
    }
    return null;
  }

}
