/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import java.io.*;

import javax.baja.io.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.*;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.*;

import com.tridium.flexSerial.*;
import com.tridium.flexSerial.enums.*;
import com.tridium.flexSerial.messages.*;
import com.tridium.program.*;

/**
 * BFlexMessageSelect defines a final message.
 *
 * @author    Andy Saunders
 * @creation  14 Sept 2005
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "message",
  type = "BFlexMessageName",
  defaultValue = "new BFlexMessageName()",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"flexSerial:MessageSelectFE\"))")
)
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.NULL"
)
@NiagaraProperty(
  name = "byteArray",
  type = "BBlob",
  defaultValue = "BBlob.DEFAULT",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"flexSerial:FlexBlobFE\"))")
)
@NiagaraAction(
  name = "showInstance",
  flags = Flags.HIDDEN
)
@NiagaraAction(
  name = "hideInstance",
  flags = Flags.HIDDEN
)
public class BFlexMessageSelect
  extends BComponent
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BFlexMessageSelect(388505934)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "message"

  /**
   * Slot for the {@code message} property.
   * @see #getMessage
   * @see #setMessage
   */
  public static final Property message = newProperty(0, new BFlexMessageName(), BFacets.make(BFacets.FIELD_EDITOR, BString.make("flexSerial:MessageSelectFE")));

  /**
   * Get the {@code message} property.
   * @see #message
   */
  public BFlexMessageName getMessage() { return (BFlexMessageName)get(message); }

  /**
   * Set the {@code message} property.
   * @see #message
   */
  public void setMessage(BFlexMessageName v) { set(message, v, null); }

  //endregion Property "message"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.NULL, null);

  /**
   * Get the {@code facets} property.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "byteArray"

  /**
   * Slot for the {@code byteArray} property.
   * @see #getByteArray
   * @see #setByteArray
   */
  public static final Property byteArray = newProperty(Flags.TRANSIENT | Flags.READONLY, BBlob.DEFAULT, BFacets.make(BFacets.FIELD_EDITOR, BString.make("flexSerial:FlexBlobFE")));

  /**
   * Get the {@code byteArray} property.
   * @see #byteArray
   */
  public BBlob getByteArray() { return (BBlob)get(byteArray); }

  /**
   * Set the {@code byteArray} property.
   * @see #byteArray
   */
  public void setByteArray(BBlob v) { set(byteArray, v, null); }

  //endregion Property "byteArray"

  //region Action "showInstance"

  /**
   * Slot for the {@code showInstance} action.
   * @see #showInstance()
   */
  public static final Action showInstance = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code showInstance} action.
   * @see #showInstance
   */
  public void showInstance() { invoke(showInstance, null, null); }

  //endregion Action "showInstance"

  //region Action "hideInstance"

  /**
   * Slot for the {@code hideInstance} action.
   * @see #hideInstance()
   */
  public static final Action hideInstance = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code hideInstance} action.
   * @see #hideInstance
   */
  public void hideInstance() { invoke(hideInstance, null, null); }

  //endregion Action "hideInstance"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexMessageSelect.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////

  public void doShowInstance()
  {
    try
    {
      Slot instanceSlot = this.getSlot("instance");
      if(instanceSlot == null)
      {
        this.setFlags(showInstance, getFlags(showInstance) | Flags.HIDDEN);
        this.setFlags(hideInstance, getFlags(hideInstance) | Flags.HIDDEN);
        return;
      }
      this.setFlags(instanceSlot, this.getFlags(instanceSlot) & ~Flags.HIDDEN);
      this.setFlags(showInstance, getFlags(showInstance) |  Flags.HIDDEN);
      this.setFlags(hideInstance, getFlags(hideInstance) & ~Flags.HIDDEN);

    }
    catch(Exception e){ e.printStackTrace(); }
  }

  public void doHideInstance()
  {
    try
    {
      Slot instanceSlot = this.getSlot("instance");
      if(instanceSlot == null)
      {
        this.setFlags(showInstance, getFlags(showInstance) | Flags.HIDDEN);
        this.setFlags(hideInstance, getFlags(hideInstance) | Flags.HIDDEN);
        return;
      }
      this.setFlags(instanceSlot, this.getFlags(instanceSlot) | Flags.HIDDEN);
      this.setFlags(showInstance, getFlags(showInstance) & ~Flags.HIDDEN);
      this.setFlags(hideInstance, getFlags(hideInstance) |  Flags.HIDDEN);
    }
    catch(Exception e){ e.printStackTrace(); }
  }

  public void doCreateInstance()
  {
    BFlexMessage reqMsg = BMessageSelect.getFlexMessage(this, getMessage().getMessageSelect());
    if(reqMsg == null)
      return;
    try { this.remove("instance"); } catch(Exception e) {}
    this.add("instance", new BFlexMessageBlock(), Flags.HIDDEN | Flags.TRANSIENT);
    BFlexMessageBlock instance = (BFlexMessageBlock)get("instance");
    reqMsg.addMessageItems(instance);
    instance.doCalculateOffsets();
    instance.exposeInParent();
    this.setFlags(showInstance, getFlags(showInstance) & ~Flags.HIDDEN);
    this.setFlags(hideInstance, getFlags(hideInstance) |  Flags.HIDDEN);

  }

  public void doMakeByteArray()
  {
    BFlexMessage reqMsg = BMessageSelect.getFlexMessage(this, getMessage().getMessageSelect());
    BFlexMessageBlock instance = (BFlexMessageBlock)get("instance");
    FlexOutputStream out = new FlexOutputStream();
    instance.writeTo(this, out);
    //System.out.println(ByteArrayUtil.toHexString(out.toByteArray()));

  }

  public void changed(Property p, Context cx)
  {
    if(!isRunning() )
      return;
    if(cx != null && cx.equals(Context.decoding) )
      return;
    if(p.equals(message))
    {
      if(getMessage().getMessageSelect().length() == 0)
        try { this.remove("instance"); } catch(Exception e) {}
      else
        doCreateInstance();
    }
  }

  public BFacets getSlotFacets(Slot slot)
  {
    BFacets facets = super.getSlotFacets(slot);
    if(slot.equals(byteArray))
    {
      return BFacets.make(facets, getFacets());
    }
    return facets;
  }

  public String toString(Context cx)
  {

    if( ! getFacets().getb(BFlexMessageElement.SHOW_ASCII, false))
      return ByteArrayUtil.toHexString( getByteArray().copyBytes());
    else
      return new String(getByteArray().copyBytes());
  }

////////////////////////////////////////////////////////////////
//Presentation
////////////////////////////////////////////////////////////////

 public BIcon getIcon() { return icon; }
 private static final BIcon icon = BIcon.make("module://flexSerial/com/tridium/flexSerial/icons/flexMessage.png");


}
