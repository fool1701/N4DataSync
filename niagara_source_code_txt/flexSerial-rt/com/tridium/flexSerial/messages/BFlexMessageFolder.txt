/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import java.io.*;
import java.util.*;

import javax.baja.collection.*;
import javax.baja.io.*;
import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.*;

import com.tridium.flexSerial.*;
import com.tridium.flexSerial.enums.*;

/**
 * BFlexMessageFolder is a special folder that contains user created BFlexMessages.
 *
 * @author    Andy Saunders
 * @creation  14 Sept 2005
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraAction(
  name = "updateBlockSelects"
)
@NiagaraAction(
  name = "updateMessageInstances"
)
public class BFlexMessageFolder
  extends BFolder
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BFlexMessageFolder(3392606915)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "updateBlockSelects"

  /**
   * Slot for the {@code updateBlockSelects} action.
   * @see #updateBlockSelects()
   */
  public static final Action updateBlockSelects = newAction(0, null);

  /**
   * Invoke the {@code updateBlockSelects} action.
   * @see #updateBlockSelects
   */
  public void updateBlockSelects() { invoke(updateBlockSelects, null, null); }

  //endregion Action "updateBlockSelects"

  //region Action "updateMessageInstances"

  /**
   * Slot for the {@code updateMessageInstances} action.
   * @see #updateMessageInstances()
   */
  public static final Action updateMessageInstances = newAction(0, null);

  /**
   * Invoke the {@code updateMessageInstances} action.
   * @see #updateMessageInstances
   */
  public void updateMessageInstances() { invoke(updateMessageInstances, null, null); }

  //endregion Action "updateMessageInstances"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexMessageFolder.class);

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

  public void doUpdateBlockSelects()
  {
    BFlexMessageBlockFolder messageBlockFolder = ((BFlexSerialNetwork)getParent()).getMessageBlocks();
    messageBlockFolder.updateMessageBlockSelects();
  }

  public void updateChildBlockSelects(String[] messageBlockNames)
  {
    BFlexMessage[] flexMessages = getChildren(BFlexMessage.class);
    for(int i = 0; i < flexMessages.length; i++)
    {
      BFlexMessageBlockSelect[] blockSelects = flexMessages[i].getChildren(BFlexMessageBlockSelect.class);
      for(int j = 0; j < blockSelects.length; j++)
      {
        int newOrdinal = 0;
        String currentTag = blockSelects[j].getMessageBlockType().getTag();
        for(int k = 0; k < messageBlockNames.length; k++)
        {
          if(currentTag.equals(messageBlockNames[k]))
          {
            newOrdinal = k;
            break;
          }
        }
        blockSelects[j].setMessageBlockType(BDynamicEnum.make(newOrdinal, BEnumRange.make(messageBlockNames)));
      }
    }
  }

  public void doUpdateMessageInstances()
  {
    BOrd query = null;
    BFlexMessageSelect messageSelect = null;
    BITable<?> result = null;
    BObject base = null;
    String path = null;
    int i = 0;
    int resultSetSize = 0;

    // get the Ord string of the parent FlexSerialNetwork component we are on
    String tempOrdString = ((BFlexSerialNetwork) getParent()).getNavOrd().toString();

    // get all the control points that have proxy extensions
    query = BOrd.make(tempOrdString + "|bql:select slotPathOrd from flexSerial:FlexMessageSelect");

    // get the base
    base = ((BFlexSerialNetwork) getParent()).getAbsoluteOrd().get();

    // get the resultset
    result = (BITable<?>) query.resolve(base).get();

    try (TableCursor<?> cursor = result.cursor())
    {
      while (cursor.next())
      {
        try
        {
          // get the item returned from the BQL query
          path = cursor.cell(result.getColumns().get(0)).toString();

          // turn the string into a ControlPoint
          messageSelect = (BFlexMessageSelect) BOrd.make(path).resolve(base).get();
          messageSelect.doCreateInstance();
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
  }

////////////////////////////////////////////////////////////////
//Presentation
////////////////////////////////////////////////////////////////

 public BIcon getIcon() { return icon; }
 private static final BIcon icon = BIcon.make("module://flexSerial/com/tridium/flexSerial/icons/flexMessageFolder.png");



}
