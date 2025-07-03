/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.points;

import javax.baja.control.BControlPoint;
import javax.baja.control.ext.BAbstractProxyExt;
import javax.baja.log.Log;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.BNrio16Module;
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
public class BNrio16Points
  extends BNrioPointDeviceExt
  implements NrioMessageConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.points.BNrio16Points(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:13 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrio16Points.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * BNrioIOPoints can only be contained in a BNrio2ReaderModule.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BNrio16Module;
  }

  /**
   * Returns the BGpOutPutProxyExt type.
   */
  public Type getProxyExtType()
  {
    return BNrio16ProxyExt.TYPE;
  }
  
  /**
   * Returns the BNrioPointFolder type.
   */
  public Type getPointFolderType()
  {
    return BNrio16PointFolder.TYPE;
  }



  public void started()
  throws Exception
  {
    super.started();
  }

  public void setDynamicPoints()
  {
    BControlPoint[] cps = this.getPoints();
    for(int i = 0; i < cps.length; i++)
    {
      if( cps[i].getPropertyInParent().isFrozen() )
        continue;
      BAbstractProxyExt proxy = cps[i].getProxyExt();
      if(proxy instanceof BUiProxyExt)
      {
        BUiProxyExt proxyExt = (BUiProxyExt)proxy;
        if(!proxyExt.getEnabled() || cps[i].isWritablePoint())
          continue;
        proxyExt.ioValueChanged();
      }
    }
  }
  

  private static final Log log = Log.getLog("nrio.config");  

}
