/** Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.flexSerial.BFlexSerialNetwork;

/**
 * BFlexMessageBlockSelect is a data structure used to select a defined message component
 *
 * @author    Andy Saunders
 * @creation  15 Sept 05
 * @version   $Revision:$ $Date: 4/6/2005 5:43:17 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "messageBlockType",
  type = "BDynamicEnum",
  defaultValue = "BDynamicEnum.DEFAULT"
)
@NiagaraProperty(
  name = "offset",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "size",
  type = "int",
  defaultValue = "1",
  flags = Flags.READONLY
)
public class BFlexMessageBlockSelect
  extends BComponent
  implements BIFlexMessageElement, BIFlexMessageBlock
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BFlexMessageBlockSelect(3662557931)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "messageBlockType"

  /**
   * Slot for the {@code messageBlockType} property.
   * @see #getMessageBlockType
   * @see #setMessageBlockType
   */
  public static final Property messageBlockType = newProperty(0, BDynamicEnum.DEFAULT, null);

  /**
   * Get the {@code messageBlockType} property.
   * @see #messageBlockType
   */
  public BDynamicEnum getMessageBlockType() { return (BDynamicEnum)get(messageBlockType); }

  /**
   * Set the {@code messageBlockType} property.
   * @see #messageBlockType
   */
  public void setMessageBlockType(BDynamicEnum v) { set(messageBlockType, v, null); }

  //endregion Property "messageBlockType"

  //region Property "offset"

  /**
   * Slot for the {@code offset} property.
   * @see #getOffset
   * @see #setOffset
   */
  public static final Property offset = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code offset} property.
   * @see #offset
   */
  public int getOffset() { return getInt(offset); }

  /**
   * Set the {@code offset} property.
   * @see #offset
   */
  public void setOffset(int v) { setInt(offset, v, null); }

  //endregion Property "offset"

  //region Property "size"

  /**
   * Slot for the {@code size} property.
   * @see #getSize
   * @see #setSize
   */
  public static final Property size = newProperty(Flags.READONLY, 1, null);

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

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexMessageBlockSelect.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BFlexMessageBlockSelect()
  {
  }

  public BFlexMessageBlockSelect(BDynamicEnum messageBlocks)
  {
    setMessageBlockType(messageBlocks);
  }


  public void changed(Property p, Context c)
  {
    if( !Sys.atSteadyState() )
        return;

    if ( ( c == Context.decoding ) || ( !isRunning() ) )
        return;

    System.out.println("*** " + this.getName() + ".changed with: " + p);
    if(p.equals(messageBlockType))
    {
      BFlexMessageBlock msgComp = getMessageComponent();
      if(msgComp == null)
        return;
      setSize(msgComp.getSize());
    }
    else
      super.changed(p, c);
  }

  public BFlexMessageBlock getMessageComponent()
  {
    BFlexSerialNetwork service = BFlexSerialNetwork.getParentFlexNetwork(this);
    BFlexMessageBlockFolder dataTypesFolder = service.getMessageBlocks();
    BFlexMessageBlock[] msgBlockTypes = dataTypesFolder.getChildren(BFlexMessageBlock.class);
    for(int i = 0; i < msgBlockTypes.length; i++)
    {
      if(msgBlockTypes[i].getName().equals(getMessageBlockType().getTag()) )
        return msgBlockTypes[i];
    }
    return null;
  }

  public void writeTo(BObject baseObj, FlexOutputStream out)
  {
    System.out.println("BFlexMessageBlockSelect.writeTo(): " + this.getName());
    getMessageComponent().writeTo(baseObj, out);
  }
  
  public void readFrom(BObject baseObj, FlexInputStream out)
  {
    System.out.println("BFlexMessageBlockSelect.readFrom(): " + this.getName());
    getMessageComponent().readFrom(baseObj, out);
  }
  
  public void addMessageItems(BFlexMessageBlock parent)
  {
    //System.out.println("BFlexMessageBlock.addMessageItems(): " + this.getName());
    getMessageComponent().addMessageItems(parent);
  }

  public void calculateItemOffsets()
  {
    //System.out.println("BFlexMessageBlock.calculateItemOffsets(): " + this.getName());
    getMessageComponent().calculateItemOffsets();
  }

  public void calculateOffsets()
  {
    
  }

  
  
  public int getMarker(String markerName)
  {
    System.out.println("BFlexMessageBlockSelect.getMarker(): " + this.getName());
    return getMessageComponent().getMarker(markerName);
  }
////////////////////////////////////////////////////////////////
//Presentation
////////////////////////////////////////////////////////////////

 public BIcon getIcon() { return icon; }
 private static final BIcon icon = BIcon.make("module://flexSerial/com/tridium/flexSerial/icons/flexMessageBlock.png");


}
