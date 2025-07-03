/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBlob;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;

import com.tridium.program.BProgram;

/**
 * BFlexUnsolicitedMessage defines a final message.
 *
 * @author    Andy Saunders
 * @creation  14 Sept 2005
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "byteArray",
  type = "BBlob",
  defaultValue = "BBlob.DEFAULT",
  flags = Flags.TRANSIENT,
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"flexSerial:FlexBlobFE\"))"),
  override = true
)
/*
 time stamp of last received data
 */
@NiagaraProperty(
  name = "timestamp",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL"
)
@NiagaraTopic(
  name = "unsolicitedMessageReceived",
  eventType = "BValue"
)
public class BFlexUnsolicitedMessage
  extends BFlexResponseMessage
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BFlexUnsolicitedMessage(2122845218)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "byteArray"

  /**
   * Slot for the {@code byteArray} property.
   * @see #getByteArray
   * @see #setByteArray
   */
  public static final Property byteArray = newProperty(Flags.TRANSIENT, BBlob.DEFAULT, BFacets.make(BFacets.FIELD_EDITOR, BString.make("flexSerial:FlexBlobFE")));

  //endregion Property "byteArray"

  //region Property "timestamp"

  /**
   * Slot for the {@code timestamp} property.
   * time stamp of last received data
   * @see #getTimestamp
   * @see #setTimestamp
   */
  public static final Property timestamp = newProperty(0, BAbsTime.NULL, null);

  /**
   * Get the {@code timestamp} property.
   * time stamp of last received data
   * @see #timestamp
   */
  public BAbsTime getTimestamp() { return (BAbsTime)get(timestamp); }

  /**
   * Set the {@code timestamp} property.
   * time stamp of last received data
   * @see #timestamp
   */
  public void setTimestamp(BAbsTime v) { set(timestamp, v, null); }

  //endregion Property "timestamp"

  //region Topic "unsolicitedMessageReceived"

  /**
   * Slot for the {@code unsolicitedMessageReceived} topic.
   * @see #fireUnsolicitedMessageReceived
   */
  public static final Topic unsolicitedMessageReceived = newTopic(0, null);

  /**
   * Fire an event for the {@code unsolicitedMessageReceived} topic.
   * @see #unsolicitedMessageReceived
   */
  public void fireUnsolicitedMessageReceived(BValue event) { fire(unsolicitedMessageReceived, event, null); }

  //endregion Topic "unsolicitedMessageReceived"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexUnsolicitedMessage.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// 
////////////////////////////////////////////////////////////////

  public void changed(Property p, Context cx)
  {
    super.changed(p,cx);
    if(!isRunning())
      return;
    if(p.equals(byteArray))
    {
      BOrd errorCheckOrd = getMessageValidate();
      if(!errorCheckOrd.equals(BOrd.NULL))
      {
        BObject obj = errorCheckOrd.get(this);
        if(obj instanceof BProgram)
        {
          try
          {
            BProgram errorCheckPgm = (BProgram)obj;
            errorCheckPgm.set("responseByteArray", getByteArray().newCopy());
            errorCheckPgm.doExecute();
            BValue results = errorCheckPgm.get("results");
            String error = ((BString)results).getString();
            if(error == null || error.length() ==0)
            {
              return;
            }
            if(! error.equalsIgnoreCase("OK"))
              return;
          }
          catch(Exception e)
          {
            return;
          }

        }
      }
      // we have received a valid message
      try
      {
        BFlexMessageBlock instance = (BFlexMessageBlock)get("instance");
        if(instance == null)
        {
          this.doCreateInstance();
        }
        instance = (BFlexMessageBlock)get("instance");
        if(instance == null)
           return;
        instance.readFrom(this, new FlexInputStream(this.getByteArray().copyBytes()));
        setTimestamp(Clock.time());
        fireUnsolicitedMessageReceived(null);
      }
      catch(Exception e) 
      {
        e.printStackTrace();
      }

    }
  }
////////////////////////////////////////////////////////////////
//Presentation
////////////////////////////////////////////////////////////////

 public BIcon getIcon() { return icon; }
 private static final BIcon icon = BIcon.make("module://flexSerial/com/tridium/flexSerial/icons/flexMessageUnsolicited.png");



}
