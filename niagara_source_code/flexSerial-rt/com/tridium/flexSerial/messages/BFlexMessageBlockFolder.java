/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import java.util.Vector;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFolder;

import com.tridium.flexSerial.BFlexSerialNetwork;

/**
 * BFlexMessageBlockFolder defines a folder that contains BFlexMessageBlocks.
 *
 * @author    Andy Saunders
 * @creation  14 Sept 2005
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 This is a special FlexMessageBlock used to define the start message frame.
 It is typically a fixed sequence of one or more specific byte values.
 If defined, a byte array will be generated representing this message block and
 will be used by low-level serial receive thread when receiving messages.
 */
@NiagaraProperty(
  name = "frameStart",
  type = "BFlexMessageBlock",
  defaultValue = "new BFlexMessageBlock()"
)
/*
 This is a special FlexMessageBlock used to define the end message frame.
 It is typically a fixed sequence of one or more specific byte values.
 If defined, a byte array will be generated representing this message block and
 will be used by low-level serial receive thread when receiving messages.
 */
@NiagaraProperty(
  name = "frameEnd",
  type = "BFlexMessageBlock",
  defaultValue = "new BFlexMessageBlock()"
)
public class BFlexMessageBlockFolder
  extends BFolder
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BFlexMessageBlockFolder(2615977905)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "frameStart"

  /**
   * Slot for the {@code frameStart} property.
   * This is a special FlexMessageBlock used to define the start message frame.
   * It is typically a fixed sequence of one or more specific byte values.
   * If defined, a byte array will be generated representing this message block and
   * will be used by low-level serial receive thread when receiving messages.
   * @see #getFrameStart
   * @see #setFrameStart
   */
  public static final Property frameStart = newProperty(0, new BFlexMessageBlock(), null);

  /**
   * Get the {@code frameStart} property.
   * This is a special FlexMessageBlock used to define the start message frame.
   * It is typically a fixed sequence of one or more specific byte values.
   * If defined, a byte array will be generated representing this message block and
   * will be used by low-level serial receive thread when receiving messages.
   * @see #frameStart
   */
  public BFlexMessageBlock getFrameStart() { return (BFlexMessageBlock)get(frameStart); }

  /**
   * Set the {@code frameStart} property.
   * This is a special FlexMessageBlock used to define the start message frame.
   * It is typically a fixed sequence of one or more specific byte values.
   * If defined, a byte array will be generated representing this message block and
   * will be used by low-level serial receive thread when receiving messages.
   * @see #frameStart
   */
  public void setFrameStart(BFlexMessageBlock v) { set(frameStart, v, null); }

  //endregion Property "frameStart"

  //region Property "frameEnd"

  /**
   * Slot for the {@code frameEnd} property.
   * This is a special FlexMessageBlock used to define the end message frame.
   * It is typically a fixed sequence of one or more specific byte values.
   * If defined, a byte array will be generated representing this message block and
   * will be used by low-level serial receive thread when receiving messages.
   * @see #getFrameEnd
   * @see #setFrameEnd
   */
  public static final Property frameEnd = newProperty(0, new BFlexMessageBlock(), null);

  /**
   * Get the {@code frameEnd} property.
   * This is a special FlexMessageBlock used to define the end message frame.
   * It is typically a fixed sequence of one or more specific byte values.
   * If defined, a byte array will be generated representing this message block and
   * will be used by low-level serial receive thread when receiving messages.
   * @see #frameEnd
   */
  public BFlexMessageBlock getFrameEnd() { return (BFlexMessageBlock)get(frameEnd); }

  /**
   * Set the {@code frameEnd} property.
   * This is a special FlexMessageBlock used to define the end message frame.
   * It is typically a fixed sequence of one or more specific byte values.
   * If defined, a byte array will be generated representing this message block and
   * will be used by low-level serial receive thread when receiving messages.
   * @see #frameEnd
   */
  public void setFrameEnd(BFlexMessageBlock v) { set(frameEnd, v, null); }

  //endregion Property "frameEnd"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexMessageBlockFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// 
////////////////////////////////////////////////////////////////

  public boolean isNavChild()
  {                                      
    return true;
  }
  
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if(!isRunning() ) return;
    if( p.equals(frameStart) ||
        p.equals(frameEnd)      )
    {
      ((BFlexSerialNetwork)getParent()).messageDefChanged();
    }
  }

  public void added(Property property, Context context)
  {
    if(property.getType().equals(BFlexMessageBlock.TYPE))
      updateMessageBlockSelects();
    super.added(property, context);
  }

  public void removed(Property property, BValue oldValue, Context context)
  {
    if(property.getType().equals(BFlexMessageBlock.TYPE))
      updateMessageBlockSelects();
    super.removed(property, oldValue, context);
  }
  public void renamed(Property property, String oldName, Context context)
  {
    if(property.getType().equals(BFlexMessageBlock.TYPE))
      updateMessageBlockSelects();
    super.renamed(property, oldName, context);
  }

  public void updateMessageBlockSelects()
  {
    if(!isRunning())
      return;
    BFlexMessageFolder messages = ((BFlexSerialNetwork)getParent()).getMessages();
    messages.updateChildBlockSelects(getMessageBlockNames());
  }

  public String[] getMessageBlockNames()
  {
    Vector<String> v = new Vector<>();
    BFlexMessageBlock[] blocks = getChildren(BFlexMessageBlock.class);
    for(int i = 0; i < blocks.length; i++)
    {
      if(blocks[i].getChildren(BFlexMessageElement.class).length > 0)
      {
        v.addElement(blocks[i].getName());
      }
    }
    String[] names = new String[v.size()];
    v.copyInto(names);
    return names;
  }

////////////////////////////////////////////////////////////////
//Presentation
////////////////////////////////////////////////////////////////

 public BIcon getIcon() { return icon; }
 private static final BIcon icon = BIcon.make("module://flexSerial/com/tridium/flexSerial/icons/flexMessageFolder.png");


}
