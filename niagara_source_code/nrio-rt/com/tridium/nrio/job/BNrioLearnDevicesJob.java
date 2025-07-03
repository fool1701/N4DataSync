/*
 * @copyright 2005 Tridium Inc.
 */
package com.tridium.nrio.job;

import java.util.Vector;

import javax.baja.driver.BDevice;
import javax.baja.job.BSimpleJob;
import javax.baja.log.Log;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFolder;

import com.tridium.nrio.BNrio34Module;
import com.tridium.nrio.BNrio34SecModule;
import com.tridium.nrio.BNrioDevice;
import com.tridium.nrio.BNrioNetwork;
import com.tridium.nrio.components.BNrioLearnDeviceEntry;
import com.tridium.nrio.enums.BNrioDeviceTypeEnum;
import com.tridium.nrio.messages.NrioInputStream;
import com.tridium.nrio.messages.NrioMessage;
import com.tridium.nrio.messages.NrioMessageConst;
import com.tridium.nrio.messages.NrioReceivedMessage;
import com.tridium.nrio.messages.PingMessage;
import com.tridium.nrio.messages.PingResponse;
import com.tridium.nrio.messages.SetLogicalAddressMessage;
import com.tridium.nrio.messages.UnconfiguredModuleReply;
import com.tridium.nrio.util.DualModuleUtils;

/**
 * BNrioLearnDevicesJob - This job is used by the nrio device manager to discover connected
 * nrio devices.
 *
 * @author    Andy Saunders
 * @creation  Nov 17, 2005
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Contains dynamic BNrioLearnDeviceEntry slots, each slot corresponds to
 The discovery information about a learned device.
 */
@NiagaraProperty(
  name = "learnedDevices",
  type = "BFolder",
  defaultValue = "new BFolder()",
  flags = Flags.HIDDEN | Flags.READONLY | Flags.TRANSIENT
)
public class BNrioLearnDevicesJob
  extends BSimpleJob
  implements NrioMessageConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.job.BNrioLearnDevicesJob(3006384311)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "learnedDevices"

  /**
   * Slot for the {@code learnedDevices} property.
   * Contains dynamic BNrioLearnDeviceEntry slots, each slot corresponds to
   * The discovery information about a learned device.
   * @see #getLearnedDevices
   * @see #setLearnedDevices
   */
  public static final Property learnedDevices = newProperty(Flags.HIDDEN | Flags.READONLY | Flags.TRANSIENT, new BFolder(), null);

  /**
   * Get the {@code learnedDevices} property.
   * Contains dynamic BNrioLearnDeviceEntry slots, each slot corresponds to
   * The discovery information about a learned device.
   * @see #learnedDevices
   */
  public BFolder getLearnedDevices() { return (BFolder)get(learnedDevices); }

  /**
   * Set the {@code learnedDevices} property.
   * Contains dynamic BNrioLearnDeviceEntry slots, each slot corresponds to
   * The discovery information about a learned device.
   * @see #learnedDevices
   */
  public void setLearnedDevices(BFolder v) { set(learnedDevices, v, null); }

  //endregion Property "learnedDevices"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioLearnDevicesJob.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



  /**
   * Constructor - BNrioLearnDevicesJob
   *
   *
   */
  public BNrioLearnDevicesJob()
  {
    super();
    this.nrioNet=null;
  }
  /**
   * Constructor - BNrioLearnDevicesJob
   */
  public BNrioLearnDevicesJob(BNrioNetwork net )
  {
    super();
    this.nrioNet=net;
  }

  protected void addLearnedDevice(BNrioDevice existingDevice)
  {
    BNrioLearnDeviceEntry learnDeviceEntry = existingDevice.makeLearnDeviceEntry();
    addLearnedDevice(learnDeviceEntry);
  }

  // this method is used by the Enterprise Security's AccessDriver.
  public void addLearnedDevice(int address, BNrioDeviceTypeEnum type, byte[] uid, String version, String usedBy)
  {
    //String learnName=getLearnName(unitNumberAddress);
    BNrioLearnDeviceEntry deviceEntry = new BNrioLearnDeviceEntry(address, type, uid, version, usedBy);
    addLearnedDevice(deviceEntry);
  }

  public void addLearnedDevice(int address, BNrioDeviceTypeEnum type, byte[] uid, String version, String usedBy, int secAddr)
  {
    //String learnName=getLearnName(unitNumberAddress);
    BNrioLearnDeviceEntry deviceEntry = new BNrioLearnDeviceEntry(address, type, uid, version, usedBy, secAddr);
    addLearnedDevice(deviceEntry);
  }

  protected void addLearnedDevice(BNrioLearnDeviceEntry deviceEntry)
  {
    String learnName = "device_" + ByteArrayUtil.toHexString(deviceEntry.getUid().copyBytes());
    if (getLearnedDevices().get(learnName)==null)          // If a learn entry does not yet exist for this point
    {
      getLearnedDevices().add(learnName,  deviceEntry); // Then this adds one
      logMessage("found device " + learnName + " usedBy: " + deviceEntry.getUsedBy());
      learnCount++;

    }
    else
    {
      getDiscoveryLog().trace(learnName + " already learned ???");
    } //<- else, point already learned!
    progress(learnCount * 100 / 16);
  }

  protected void removeLearnedDevice(byte[] uid)
  {
    String learnName = "device" + ByteArrayUtil.toHexString(uid);
    if (getLearnedDevices().get(learnName)!=null) // If a learn entry exists for this point
    {
      getLearnedDevices().remove(learnName);      // Then this removes it.
      learnCount--;
    }
    // else {} <- else, point not learned anyway so don't worry about it
  }

  /* (non-Javadoc)
   * @see javax.baja.job.BSimpleJob#run(javax.baja.sys.Context)
   */
  public void run(Context cx) throws Exception
  {
    logMessage("starting");
    if(nrioNet.isDisabled() || nrioNet.isFault())
    {
      logMessage("Network is disabled or configured incorrectly");
      throw new RuntimeException("Network is disabled or configured incorrectly");

    }
    try
    {
      NrioMessage message = new NrioMessage();
      NrioInputStream inStream;
      UnconfiguredModuleReply ucmr = new UnconfiguredModuleReply();
      nrioNet.initLogicalAddressMap();
      Vector<byte[]> unconfigDevices = new Vector<>();
      boolean done = false;
      boolean firstRun = true;
      int exceptionCount = 0;
      while(firstRun ||
      (unconfigDevices.size() > 0 && !done && isAlive()) )
      {
        firstRun = false;

        try
        {
          getDiscoveryLog().trace("*** nrioNet.discover() called *****");
          unconfigDevices = nrioNet.discover();
          try{Thread.sleep(20l);}catch(Exception e){}
          getDiscoveryLog().trace("*** unconfigDevices.size() = " + unconfigDevices.size());
          for(int i = 0; i < unconfigDevices.size(); i++)
          {
            byte[] data = unconfigDevices.elementAt(i);
            getDiscoveryLog().trace("NrioDiscovery byte[] = " + ByteArrayUtil.toHexString(data));
            NrioReceivedMessage receivedMessage = new NrioReceivedMessage(data, data.length, null);
            inStream = message.readResponse(receivedMessage);
            ucmr.readData(inStream);
            byte[] uid = ucmr.getUid();
            BNrioDevice stationDevice = nrioNet.getDevice(uid);
            int setAddress = -1;
            int moduleType = ucmr.getModuleType();
            if( stationDevice == null )
            {
              setAddress = nrioNet.getFreeAddressV2(moduleType, uid);
              if(setAddress < 0)
              {
               getDiscoveryLog().trace("************** no free addresses availiable ****************");
                // all address slots must be filled
                // so lets add the learned device and exit
                logMessage("no free address is availiable for this module!");
                addLearnedDevice(setAddress, BNrioDeviceTypeEnum.makeFromRaw(moduleType), uid, "", "", -1);
                done = true;
                continue;
              }
              if( setAddressAndPing(setAddress, uid, moduleType) != NrioMessageConst.MESSAGE_STATUS_OK)
              {
                getDiscoveryLog().message("setAddressAndPing returned error");
                nrioNet.clearAddressUsedV2(setAddress);
                continue;
              }
              else
              {
                if(moduleType == REMOTE_IO_34_PRI || moduleType == REMOTE_IO_34_SEC)
                {
                  if(DualModuleUtils.addressDualModule(nrioNet, this, moduleType, uid, setAddress))
                    continue;
                }
                else // not a IO34 (dual module)
                {

                  String version = nrioNet.readBuildInfo(setAddress);
                  addLearnedDevice(setAddress, BNrioDeviceTypeEnum.makeFromRaw(moduleType), uid, version, "", -1);
                }
              }
            }
            else // already in station
            {
              if(moduleType == REMOTE_IO_34_SEC)
              {
                if(stationDevice instanceof BNrio34SecModule)
                  stationDevice = ((BNrio34SecModule)stationDevice).getParentModule();
              }
              getDiscoveryLog().trace(stationDevice.getSlotPath() + ":  discovered device allready in station ******");
              stationDevice.doSetAddressAndPing();
              stationDevice.doEnablePolling();
              stationDevice.readBuildInfo();
              setAddress = stationDevice.getAddress();
            }
          }
        }
        catch(Exception e)
        {
          exceptionCount++;
          getDiscoveryLog().trace("*** caught Exception: " + e + " count = " + exceptionCount);
          if(getDiscoveryLog().isTraceOn())
          {
            e.printStackTrace();
          }
          if(exceptionCount > 10)
            done = true;
          else
            firstRun=true;
          try{Thread.sleep(20l);}catch(Exception e1){}
        }

      }
      addExistingDevices();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    // reset any devices that are not currently in the station.
    BNrioLearnDeviceEntry[] learnEntries = getLearnedDevices().getChildren(BNrioLearnDeviceEntry.class);
    for(int i = 0; i < learnEntries.length; i++)
    {
      if( learnEntries[i].getUsedBy().equals("") )
      {
        int address = learnEntries[i].getAddress();
        nrioNet.sendReset(address);
        address = learnEntries[i].getSecAddrInt();
        if(address > 0)
        {
          nrioNet.sendReset(learnEntries[i].getSecAddrInt());
        }
      }
    }
    nrioNet.initLogicalAddressMap();
    progress(100);

 }

  protected void addExistingDevices()
  {
    BDevice[] devices = nrioNet.getDevices();
    for(int i = 0; i < devices.length; i++)
    {
      if( !(devices[i] instanceof BNrioDevice) )
        continue;
      if(devices[i].isFault())
        continue;
      BNrioDevice device = (BNrioDevice)devices[i];
      addLearnedDevice(device);
    }
  }

  protected int setAddressAndPing(int logicalAddress, byte[] uid, int type)
  {
    SetLogicalAddressMessage setAddrMsg = null;
    if(type == REMOTE_IO_34_PRI || type == REMOTE_IO_34_SEC)
      setAddrMsg = new SetLogicalAddressMessage( logicalAddress, uid, type );
    else
      setAddrMsg = new SetLogicalAddressMessage( logicalAddress, uid );
    NrioMessage rsp = nrioNet.sendNrioMessage(setAddrMsg);
    if(rsp.getStatus() != NrioMessageConst.MESSAGE_STATUS_OK)
    {
      this.getDiscoveryLog().trace("setLogicalAddress, " + logicalAddress + " returned error, " + rsp.getStatus());
//      return rsp.getStatus();
    }
    try{Thread.sleep(500l);}catch(Exception e){}
    PingMessage pReq = new PingMessage(logicalAddress, uid, type);
    PingResponse pRsp = (PingResponse)(nrioNet.sendNrioMessage(pReq));
    if(pRsp.getStatus() != NrioMessageConst.MESSAGE_STATUS_OK)
    {
      this.getDiscoveryLog().trace("ping after setLogicalAddress, " + logicalAddress + " returned error, " + pRsp.getStatus());
    }
    return pRsp.getStatus();
  }

//  protected int setAddressAndPing(int logicalAddress, byte[] uid, int type)
//  {
//    SetLogicalAddressMessage setAddrMsg = null;
//    if(type == REMOTE_IO_34_PRI || type == REMOTE_IO_34_SEC)
//      setAddrMsg = new SetLogicalAddressMessage( logicalAddress, uid, type );
//    else
//      setAddrMsg = new SetLogicalAddressMessage( logicalAddress, uid );
//    getDiscoveryLog().trace("setLogicalAddress: " + logicalAddress);
//    NrioMessage rsp = nrioNet.sendNrioMessage(setAddrMsg);
//    if(rsp.getStatus() != NrioMessageConst.MESSAGE_STATUS_OK)
//    {
//      this.getDiscoveryLog().trace("setLogicalAddress, " + logicalAddress + " returned error, " + rsp.getStatus());
////      return rsp.getStatus();
//    }
//    try{Thread.sleep(500l);}catch(Exception e){}
//    getDiscoveryLog().trace("sendPingMessage: " + logicalAddress + " " +  ByteArrayUtil.toHexString(uid) + " type " + type);
//    PingMessage pReq = new PingMessage(logicalAddress, uid, type);
//    PingResponse pRsp = (PingResponse)(nrioNet.sendNrioMessage(pReq));
//    if(pRsp.getStatus() != NrioMessageConst.MESSAGE_STATUS_OK)
//    {
//      this.getDiscoveryLog().trace("ping after setLogicalAddress, " + logicalAddress + " returned error, " + pRsp.getStatus());
//    }
//    return pRsp.getStatus();
//  }


  protected static final String LEARN_NAME_PREFIX = "Device";
  final BNrioNetwork nrioNet;
  protected int learnCount = 0;

  public Log getDiscoveryLog()
  {
 return Log.getLog(nrioNet.getName() + ".discovery");
  }

  protected void logMessage(String message)
  {
    log().message(message);

    if(nrioNet != null)
    {
      Log discLog = getDiscoveryLog();
      int severity = discLog.getSeverity();
      discLog.setSeverity(Log.MESSAGE);
      discLog.message("Learn Niagara Remote IO Devices Job:" + message);
      discLog.setSeverity(severity);
    }
  }

}
