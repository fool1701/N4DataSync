/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.components.BIoStatus;
import com.tridium.nrio.enums.BNrioDeviceTypeEnum;
import com.tridium.nrio.messages.IoModuleIOStatus;
import com.tridium.nrio.messages.NrioMessage;
import com.tridium.nrio.messages.WriteConfigMessage;
import com.tridium.nrio.messages.WriteDOMessage;
import com.tridium.nrio.points.BNrioIOPoints;

/**
 * BNrioInputOutputModule represents a Nrio IO Module
 *
 * @author Andy Saunders on 12 Jan 06
 * @since Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "deviceType",
  type = "BNrioDeviceTypeEnum",
  defaultValue = "BNrioDeviceTypeEnum.remoteInputOutput",
  flags = Flags.READONLY,
  override = true
)
@NiagaraProperty(
  name = "points",
  type = "BNrioPointDeviceExt",
  defaultValue = "new BNrioIOPoints()",
  override = true
)
@NiagaraAction(
  name = "writeConfig",
  flags = Flags.HIDDEN
)
public class BNrioInputOutputModule
  extends BNrioDevice
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.BNrioInputOutputModule(1149282181)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "deviceType"

  /**
   * Slot for the {@code deviceType} property.
   * @see #getDeviceType
   * @see #setDeviceType
   */
  public static final Property deviceType = newProperty(Flags.READONLY, BNrioDeviceTypeEnum.remoteInputOutput, null);

  //endregion Property "deviceType"

  //region Property "points"

  /**
   * Slot for the {@code points} property.
   * @see #getPoints
   * @see #setPoints
   */
  public static final Property points = newProperty(0, new BNrioIOPoints(), null);

  //endregion Property "points"

  //region Action "writeConfig"

  /**
   * Slot for the {@code writeConfig} action.
   * @see #writeConfig()
   */
  public static final Action writeConfig = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code writeConfig} action.
   * @see #writeConfig
   */
  public void writeConfig() { invoke(writeConfig, null, null); }

  //endregion Action "writeConfig"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioInputOutputModule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning())
    {
      return;
    }
    if (p.equals(status))
    {
      if (isDown() || isDisabled())
      {
        // clear lastWriteData
        lastWriteValue = 0;
      }
    }
  }

  public void doWriteConfig()
  {
    sendWriteConfig();
  }

  public int sendWriteConfig()
  {
    BNrioNetwork network = (BNrioNetwork) getNetwork();
    WriteConfigMessage cReq = new WriteConfigMessage(getAddress(), 0x0000);
    NrioMessage cRsp = network.sendNrioMessage(cReq);
    return cRsp.getStatus();
  }

  public int setDoValue(boolean value, int instance)
  {
    int mask = 0x0080 << instance;
    setWrDoValue(value, mask);
    return 0;
  }

  public synchronized void setWrDoValue(boolean value, int mask)
  {
    if (value)
    {
      wrDoValue = wrDoValue | mask;
    }
    else
    {
      wrDoValue = wrDoValue & ~mask;
    }
    ((BNrioNetwork) getNetwork()).interruptWriteThread();
  }

  public int doWriteDoValues()
  {
    BNrioNetwork network = (BNrioNetwork)getNetwork();
    int thisWrite = wrDoValue;
    if (thisWrite != lastWriteValue)
    {
      WriteDOMessage req = new WriteDOMessage(getAddress(), thisWrite);
      NrioMessage rsp = network.sendNrioMessage(req);
      if (rsp != null && rsp.getStatus() == 0)
      {
        if (!getFirstPing())
        {
          pingOk();
        }
        lastWriteValue = thisWrite;
      }
      return rsp.getStatus();
    }
    return 0;
  }

  public void doDumpIoMap()
  {
    BOrd[] map = getUsedDiArray();
    {
      System.out.println("Digital input use map:");
      for (int i = 0; i < map.length; i++)
      {
        System.out.println("   di" + (i + 1) + " used = " + map[i]);
      }
    }
    map = getUsedRelayArray();
    {
      System.out.println("Relay output use map:");
      for (int i = 0; i < map.length; i++)
      {
        System.out.println("   ro" + (i + 1) + " used = " + map[i]);
      }
    }
    map = getUsedSdiArray();
    {
      System.out.println("Sdi use map:");
      for (int i = 0; i < map.length; i++)
      {
        System.out.println("   sdi" + (i + 1) + " used = " + map[i]);
      }
    }
  }

  /**
   * Return the boolean array that indicates how many relays are supported and which are
   * currently assigned.  A true value will indicate that a relay is assigned.
   */
  public BOrd[] getUsedRelayArray()
  {
    return getUsedRelayArray(8);
  }

  /**
   * Return the boolean array that indicates how many digital inputs are supported and which are
   * currently assigned.  A true value will indicate that a digital input is assigned.
   */
  public BOrd[] getUsedDiArray()
  {
    return getUsedDiArray(2);
  }

  /**
   * Return the boolean array that indicates how many supervised digital inputs are supported
   * and which are currently assigned.
   * A true value will indicate that a digital input is assigned.
   */
  public BOrd[] getUsedSdiArray()
  {
    return getUsedSdiArray(8);
  }

  // must be overridden by subclasses
  public void updateProxyValues()
  {
    byte[] statusBytes = ((BIoStatus)getIoStatus()).copyBytes();
    if (statusBytes.length < 12)
    {
      return;
    }
    NrioMessage statusMsg = new NrioMessage();
    statusMsg.decodeFromBytes(statusBytes, statusBytes.length);

    ((BNrioIOPoints) getPoints()).setIoStatus(new IoModuleIOStatus(statusMsg.getData()));
  }

  private int wrDoValue = 0;
  private int lastWriteValue = 0;
  private final static byte[] NO_DATA = new byte[0];
}
