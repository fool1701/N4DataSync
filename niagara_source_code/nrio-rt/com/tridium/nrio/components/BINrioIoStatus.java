/*
 * @copyright 2016 Tridium Inc.
 */
package com.tridium.nrio.components;

import javax.baja.log.Log;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BBlob;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BInterface;
import javax.baja.sys.BString;
import javax.baja.sys.BStruct;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.messages.NrioInputStream;

/**
 * BINrioIoStatus - This interface defines the api for the Input Output status for the IO16 and the IO34 modules.
 *
 * @author    Andy Saunders
 * @creation  Oct 13, 2016
 */
@NiagaraType

public interface BINrioIoStatus
  extends BInterface

{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.components.BINrioIoStatus(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:13 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BINrioIoStatus.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BBlob getIoStatus();
  public void setIoStatus(BBlob v);
  public void doClearTotals();
  public boolean readIoStatus(byte[] data, int start, int length);
  public void updateCounts(int index, long newCount);
  public void addTotalCount(int index, long deltaCount);
  public void setCountHighSpeedDi(int index, long newCount);
  public boolean getDi(int instance);
  public int getAi(int instance);
  public void setTotalCounts(int instance, long value);
  public long getTotalCounts(int instance);
  public long getDiCounts(int instance);
  public default byte[] copyBytes()
  {
    return getIoStatus().copyBytes();
  }
  public default int getMaxUiInstance() { return 8; };
  // nccb-33633 e333968 3/26/2018
  public boolean hasFirstUpdate();

}
