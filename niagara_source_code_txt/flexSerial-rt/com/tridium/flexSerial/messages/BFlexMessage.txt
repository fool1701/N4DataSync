/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BFlexMessage defines a final message.
 *
 * @author    Andy Saunders
 * @creation  14 Sept 2005
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "description",
  type = "String",
  defaultValue = ""
)
@NiagaraAction(
  name = "calculateOffsets"
)
public class BFlexMessage
  extends BComponent
  implements BIFlexMessageBlock
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BFlexMessage(2070654062)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "description"

  /**
   * Slot for the {@code description} property.
   * @see #getDescription
   * @see #setDescription
   */
  public static final Property description = newProperty(0, "", null);

  /**
   * Get the {@code description} property.
   * @see #description
   */
  public String getDescription() { return getString(description); }

  /**
   * Set the {@code description} property.
   * @see #description
   */
  public void setDescription(String v) { setString(description, v, null); }

  //endregion Property "description"

  //region Action "calculateOffsets"

  /**
   * Slot for the {@code calculateOffsets} action.
   * @see #calculateOffsets()
   */
  public static final Action calculateOffsets = newAction(0, null);

  /**
   * Invoke the {@code calculateOffsets} action.
   * @see #calculateOffsets
   */
  public void calculateOffsets() { invoke(calculateOffsets, null, null); }

  //endregion Action "calculateOffsets"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexMessage.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// 
////////////////////////////////////////////////////////////////

  public void doCalculateOffsets()
  {
    calculateItemOffsets();
  }

  public void calculateItemOffsets()
  {
    BIFlexMessageElement[] items = getChildren(BIFlexMessageElement.class);
    int offset = 0;
    for(int i = 0; i < items.length; i++)
    {
      items[i].setOffset(offset);
      offset = offset + items[i].getSize();
    }
  }

  public void added(Property property, Context context)
  {
    calculateItemOffsets();
    super.added(property, context);
  }

  public void removed(Property property, BValue oldValue, Context context)
  {
    calculateItemOffsets();
    super.removed(property, oldValue, context);
  }

  public byte[] getByteArray(BObject baseObj)
  {
    //System.out.println("BFlexMessage.getByteArray()");
    BIFlexMessageElement[] items = getChildren(BIFlexMessageElement.class);
    FlexOutputStream out = new FlexOutputStream();
    for(int i = 0; i < items.length; i++)
    {
      items[i].writeTo(baseObj, out);
    }
    byte[] bytes = out.toByteArray();
    return out.toByteArray();
  }
  
  public void addMessageItems( BFlexMessageBlock parent )
  {
    //System.out.println("BFlexMessage.addMessageItems(): " + this.getName());
    BIFlexMessageElement[] items = getChildren(BIFlexMessageElement.class);
    for(int i = 0; i < items.length; i++)
    {
      if(items[i] instanceof BIFlexMessageBlock)                                                         
        ((BIFlexMessageBlock)items[i]).addMessageItems(parent);
      else
        parent.add(((BComplex)items[i]).getName(), ((BComplex)items[i]).newCopy());
    }
  }



  public int getMarker(String markerName)
  {
    //System.out.println("BFlexMessage.getCksumMarker()");
    BIFlexMessageElement[] items = getChildren(BIFlexMessageElement.class);
    FlexOutputStream out = new FlexOutputStream();
    int marker = -1;
    int offset = 0;
    for(int i = 0; i < items.length; i++)
    {
      //System.out.println("BFlexMessage.processingBIFlexMessageElement: " + ((BComponent)items[i]).getName());
      marker = items[i].getMarker(markerName);
      //System.out.println("     Marker = " + marker);
      if(i >= 0)
        return offset + marker;
      offset = offset + items[i].getSize();
    }
    return -1;
  }

  public void readFrom(BObject baseObj, FlexInputStream bytes)
  {
    //System.out.println("BFlexMessage.readFrom()");
    BIFlexMessageElement[] items = getChildren(BIFlexMessageElement.class);
    for(int i = 0; i < items.length; i++)
    {
      items[i].readFrom(baseObj, bytes);
    }
  }
  
////////////////////////////////////////////////////////////////
//Presentation
////////////////////////////////////////////////////////////////

 public BIcon getIcon() { return icon; }
 private static final BIcon icon = BIcon.make("module://flexSerial/com/tridium/flexSerial/icons/flexMessage.png");


}
