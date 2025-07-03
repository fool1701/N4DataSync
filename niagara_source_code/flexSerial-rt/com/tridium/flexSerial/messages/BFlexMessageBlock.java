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
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BFlexMessageBlock defines a collection of message items.
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
@NiagaraProperty(
  name = "size",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY
)
@NiagaraAction(
  name = "calculateOffsets"
)
public class BFlexMessageBlock
  extends BComponent
  implements BIFlexMessageBlock
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BFlexMessageBlock(36911883)1.0$ @*/
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

  //region Property "size"

  /**
   * Slot for the {@code size} property.
   * @see #getSize
   * @see #setSize
   */
  public static final Property size = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code size} property.
   * @see #size
   */
  public int getSize() { return getInt(size); }

  /**
   * Set the {@code size} property.
   * @see #size
   */
  public void setSize(int v) { setInt(size, v, null); }

  //endregion Property "size"

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
  public static final Type TYPE = Sys.loadType(BFlexMessageBlock.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// 
////////////////////////////////////////////////////////////////

  public void changed(Property p, Context cx)
  {
    if(!isRunning())
      return;
    Property pip = getPropertyInParent();
    // force a change on parent BFlexMessageBlockFolder if this is frameEnd
    // or frameStart.
    if(pip != null && ( pip.equals(BFlexMessageBlockFolder.frameEnd) ||
                        pip.equals(BFlexMessageBlockFolder.frameStart)   ) )
    {
      ((BComponent)getParent()).changed(BFlexMessageBlockFolder.frameStart, cx);
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

  
  public void doCalculateOffsets()
  {
    calculateItemOffsets();
  }

  public void calculateItemOffsets()
  {
    BFlexMessageElement[] items = getChildren(BFlexMessageElement.class);
    int offset = 0;
    int size = 0;
    for(int i = 0; i < items.length; i++)
    {
      items[i].setOffset(offset);
      offset = offset + items[i].getSize();
      size = size + items[i].getSize();
    }
    setSize(size);
  }

  public void exposeInParent()
  {
    BFlexMessageElement[] items = getChildren(BFlexMessageElement.class);
    for(int i = 0; i < items.length; i++)
    {
      items[i].exposeInParent();
    }
  }

  public void writeTo(BObject baseObj, FlexOutputStream out)
  {
    //System.out.println("BFlexMessageBlock.writeTo(): " + this.getName());
    BIFlexMessageElement[] items = getChildren(BIFlexMessageElement.class);
    for(int i = 0; i < items.length; i++)
    {
      items[i].writeTo(baseObj, out);
    }
  }

  public void readFrom(BObject baseObj, FlexInputStream out)
  {
    //System.out.println("BFlexMessageBlock.readFrom(): " + this.getName());
    BIFlexMessageElement[] items = getChildren(BIFlexMessageElement.class);
    for(int i = 0; i < items.length; i++)
    {
      items[i].readFrom(baseObj, out);
    }
  }

  public byte[] getByteArray()
  {
    FlexOutputStream out = new FlexOutputStream();
    writeTo(this, out);
    return out.toByteArray();
  }

  public int getMarker(String markerName)
  {
    System.out.println("BFlexMessageBlock.getMarker(): " + this.getName());
    BIFlexMessageElement[] items = getChildren(BIFlexMessageElement.class);
    for(int i = 0; i < items.length; i++)
    {
      if( items[i].getMarker(markerName) >= 0 )
        return items[i].getOffset();
    }
    return -1;
  }

  public void addMessageItems(BFlexMessageBlock parent)
  {
    //System.out.println("BFlexMessageBlock.addMessageItems(): " + this.getName());
    BIFlexMessageElement[] items = getChildren(BIFlexMessageElement.class);
    for(int i = 0; i < items.length; i++)
    {
      if(items[i] instanceof BIFlexMessageBlock)                                                         
        ((BIFlexMessageBlock)items[i]).addMessageItems(parent);
      else
        parent.add(((BComplex)items[i]).getName() + "?", ((BComplex)items[i]).newCopy());
    }
  }
  
  public BFlexMessageElement getMessageElement(String name)
  {
    //System.out.println("BFlexMessageBlock.getMessageElement(): " + this.getName());
    BFlexMessageElement[] items = getChildren(BFlexMessageElement.class);
    for(int i = 0; i < items.length; i++)
    {
      if( items[i].getName().equals(name) )
        return items[i];
    }
    return null;
  }


  public int getOffset()
  {
    return 0;
  }
  
////////////////////////////////////////////////////////////////
//Presentation
////////////////////////////////////////////////////////////////

 public BIcon getIcon() { return icon; }
 private static final BIcon icon = BIcon.make("module://flexSerial/com/tridium/flexSerial/icons/flexMessageBlock.png");

  
}
