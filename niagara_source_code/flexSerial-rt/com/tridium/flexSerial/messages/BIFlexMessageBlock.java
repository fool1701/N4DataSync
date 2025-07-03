/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import java.util.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * This interface is implemented message component objects
 *  
 * @author    Andy Saunders
 * @creation  15 Sept 2005
 * @version   $Revision: 1$ $Date: 04/02/02 2:56:44 PM$
 * @since     Niagara 3.0 basicdriver 1.0
 */
@NiagaraType
public interface BIFlexMessageBlock
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.messages.BIFlexMessageBlock(2979906276)1.0$ @*/
/* Generated Thu Sep 30 12:51:14 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIFlexMessageBlock.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  public void calculateOffsets();
  public void calculateItemOffsets();
  public void addMessageItems(BFlexMessageBlock v);


}
