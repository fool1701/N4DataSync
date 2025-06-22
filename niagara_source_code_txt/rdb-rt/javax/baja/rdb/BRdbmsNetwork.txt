/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.driver.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.sys.*;

import com.tridium.fox.sys.BFoxChannelRegistry;
import com.tridium.rdb.fox.BSqlSchemeChannel;

/**
 * BRdbmsNetwork models a network of BRdbms objects.
 *
 * @author    Mike Jarmy
 * @creation  24 Jul 03
 * @version   $Revision: 6$ $Date: 4/1/05 12:52:51 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BRdbmsNetwork
  extends BDeviceNetwork
  implements BIService
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.BRdbmsNetwork(2979906276)1.0$ @*/
/* Generated Sat Jan 29 17:54:41 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRdbmsNetwork.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BDeviceNetwork
////////////////////////////////////////////////////////////////

  public Type getDeviceType()
  {
    return BRdbms.TYPE;
  }

  public Type getDeviceFolderType()
  {
    return BRdbmsFolder.TYPE;
  }

//////////////////////////////////////////////////////////////////
//// BIService
//////////////////////////////////////////////////////////////////

  /**
   * Register this component under "rdb:DatabaseNetwork".
   */
  public Type[] getServiceTypes()
  {
    return serviceTypes;
  }
  private static Type[] serviceTypes = new Type[] { TYPE };

  @Override
  public void serviceStarted()
  {
    if(isOperational())
    {
      // Add a rdbms fox channel
      try
      {
        BFoxChannelRegistry registry = BFoxChannelRegistry.getPrototype();
        if (registry.get(BSqlSchemeChannel.CHANNEL_NAME) == null)
        {
          registry.add(BSqlSchemeChannel.CHANNEL_NAME, new BSqlSchemeChannel());
        }
      }
      catch (Exception e)
      {
        log.log(Level.SEVERE, "Unable to add BSqlSchemeChannel. " + e.getLocalizedMessage(), e);
      }
    }
  }

  @Override
  public void serviceStopped() throws Exception
  {
    // remove the rdbms channel
    try
    {
      BFoxChannelRegistry registry = BFoxChannelRegistry.getPrototype();
      if (registry.get(BSqlSchemeChannel.CHANNEL_NAME) != null)
        registry.remove(BSqlSchemeChannel.CHANNEL_NAME);
    }
    catch (Exception e)
    {
      log.log(Level.SEVERE,"Unable to remove BSqlSchemeChannel. " + e.getLocalizedMessage(), e);
    }
  }

  /**
   * Return if the service is neither disabled, nor in fault.
   */
  public final boolean isOperational()
  {
    BStatus status = getStatus();
    return !isFatalFault() && !status.isDisabled() && !status.isFault();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final Logger log = Logger.getLogger("rdb");

}
