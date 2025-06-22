/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.history;

import javax.baja.driver.BDeviceNetwork;
import javax.baja.driver.util.BDescriptor;
import javax.baja.history.BHistoryId;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.sys.BComplex;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BArchiveDescriptor describes a history archive.  It includes
 * information about which histories will be archived and
 * when the archive will occur.
 *
 * @author    John Sublett
 * @creation  31 Mar 2003
 * @version   $Revision: 13$ $Date: 9/10/08 11:19:30 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The id of the history to be transferred by this descriptor.
 */
@NiagaraProperty(
  name = "historyId",
  type = "BHistoryId",
  defaultValue = "BHistoryId.NULL"
)
public abstract class BArchiveDescriptor
  extends BDescriptor
  implements BIStatus
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.history.BArchiveDescriptor(2903739385)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "historyId"

  /**
   * Slot for the {@code historyId} property.
   * The id of the history to be transferred by this descriptor.
   * @see #getHistoryId
   * @see #setHistoryId
   */
  public static final Property historyId = newProperty(0, BHistoryId.NULL, null);

  /**
   * Get the {@code historyId} property.
   * The id of the history to be transferred by this descriptor.
   * @see #historyId
   */
  public BHistoryId getHistoryId() { return (BHistoryId)get(historyId); }

  /**
   * Set the {@code historyId} property.
   * The id of the history to be transferred by this descriptor.
   * @see #historyId
   */
  public void setHistoryId(BHistoryId v) { set(historyId, v, null); }

  //endregion Property "historyId"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BArchiveDescriptor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Is this descriptor enabled for archiving?
   */
  public boolean isEnabled()
  {
    return !getStatus().isDisabled();
  }
  
  /**
   * Get the parent history network extension.
   * The default implementation is to check the parent
   * device network for a child history network ext.
   * If not found, returns null.
   * 
   * @since Niagara 3.4
   */
  public BHistoryNetworkExt getHistoryNetworkExt()
  {
    BDeviceNetwork net = null;
    try
    {
      net = getNetwork();
    }
    catch(NotRunningException e)
    { // suppress NotRunningExceptions
    }
    
    if (net == null)
    {
      BComplex parent = getParent();
      while (parent != null)
      {
        if (parent instanceof BDeviceNetwork)
        {
          net = (BDeviceNetwork)parent;
          if (!isRunning()) net.lease();
          break;
        }
        parent = parent.getParent();
      }
    }
    
    if (net != null)
    {
      Object[] exts = net.getChildren(BHistoryNetworkExt.class);
      if ((exts != null) && (exts.length > 0))
        return (BHistoryNetworkExt)exts[0];
    }
    return null;
  }
}
