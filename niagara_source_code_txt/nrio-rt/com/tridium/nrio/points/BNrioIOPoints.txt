/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.points;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.BNrioInputOutputModule;
import com.tridium.nrio.messages.IoModuleIOStatus;
import com.tridium.nrio.messages.NrioMessageConst;

/**
 * BNrioIOPoints is the implementation of BPointDeviceExt
 * which provides a container for points for the Nrio IO Module.
 *
 * @author    Andy Saunders       
 * @creation  12 Jan 06
 * @version   $Revision$ $Date: 8/29/2005 10:21:12 AM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BNrioIOPoints
  extends BNrioPointDeviceExt
  implements NrioMessageConst
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.points.BNrioIOPoints(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:13 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioIOPoints.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
  /**
   * Returns the BGpOutPutProxyExt type.
   */
  public Type getProxyExtType()
  {
    return BNrioProxyExt.TYPE;
  }
  
  /**
   * Returns the BNrioPointFolder type.
   */
  public Type getPointFolderType()
  {
    return BNrioIOPointFolder.TYPE;
  }

  /**
   * BNrioIOPoints can only be contained in a BNrio2ReaderModule.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BNrioInputOutputModule;
  }

  public void started()
  throws Exception
  {
    super.started();
    
    String diActiveText = getLexicon().getText("di.text.active");
    String diInactiveText = getLexicon().getText("di.text.inactive");
    String roActiveText = getLexicon().getText("ro.text.active");
    String roInactiveText = getLexicon().getText("ro.text.inactive");
    
  } 

  public void setIoStatus(IoModuleIOStatus ioStatus)
  {
    setDynamicPoints();
  }


}
