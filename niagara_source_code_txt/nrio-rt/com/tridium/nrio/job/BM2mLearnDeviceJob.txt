/*
 * @copyright 2005 Tridium Inc.
 */
package com.tridium.nrio.job;

import java.util.Vector;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.sys.BBlob;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.BM2mIoNetwork;
import com.tridium.nrio.BNrio16Module;
import com.tridium.nrio.components.BNrioLearnDeviceEntry;
import com.tridium.nrio.enums.BNrioDeviceTypeEnum;
import com.tridium.nrio.messages.NrioInputStream;
import com.tridium.nrio.messages.NrioMessage;
import com.tridium.nrio.messages.NrioMessageConst;
import com.tridium.nrio.messages.NrioReceivedMessage;
import com.tridium.nrio.messages.UnconfiguredModuleReply;

/**
 * BM2mLearnDeviceJob - This job is used by the access device manager to discover connected
 * access devices.
 *
 * @author    Andy Saunders
 * @creation  Nov 17, 2005
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
@NiagaraType
public class BM2mLearnDeviceJob
  extends BNrioLearnDevicesJob
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.job.BM2mLearnDeviceJob(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:13 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BM2mLearnDeviceJob.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



  /**
   * Constructor - BM2mLearnDeviceJob
   *
   *
   */
  public BM2mLearnDeviceJob()
  {
    super();
  }
  /**
   * Constructor - BM2mLearnDeviceJob
   */
  public BM2mLearnDeviceJob(BM2mIoNetwork net )
  {
    super(net);
  }

//  void addLearnedDevice(int address, BNrioDeviceTypeEnum type, byte[] uid, String version, String usedBy)
//  {
//    //String learnName=getLearnName(unitNumberAddress);
//    String learnName = "device_" + ByteArrayUtil.toHexString(uid);
//    if (getLearnedDevices().get(learnName)==null)          // If a learn entry does not yet exist for this point
//    {
//      getLearnedDevices().add(learnName,new BNrioLearnDeviceEntry(address, type, uid, version, usedBy)); // Then this adds one
//      logMessage("found device " + learnName + " usedBy: " + usedBy);
//      learnCount++;
//    }
//    else
//    {
//    	getDiscoveryLog().trace(learnName + " already learned ???");
//    } //<- else, point already learned!
//    progress(learnCount * 100 / 16);
//  }
//
//  void removeLearnedDevice(byte[] uid)
//  {
//    String learnName = "device" + ByteArrayUtil.toHexString(uid);
//    if (getLearnedDevices().get(learnName)!=null) // If a learn entry exists for this point
//    {
//      getLearnedDevices().remove(learnName);      // Then this removes it.
//      learnCount--;
//    }
//    // else {} <- else, point not learned anyway so don't worry about it
//  }

  /* (non-Javadoc)
   * @see javax.baja.job.BSimpleJob#run(javax.baja.sys.Context)
   */
  public void run(Context cx) throws Exception
  {
    logMessage("starting");
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
          exceptionCount = 0;
          try{Thread.sleep(20l);}catch(Exception e){}
          getDiscoveryLog().trace("*** unconfigDevices.size() = " + unconfigDevices.size());
	        for(int i = 0; i < unconfigDevices.size(); i++)
	        {
	          byte[] data = unconfigDevices.elementAt(i);
	          getDiscoveryLog().trace("NrioDiscovery byte[] = " + ByteArrayUtil.toHexString(data));
	          NrioReceivedMessage receivedMessage = new NrioReceivedMessage(data, data.length, null);
	          inStream = message.readResponse(receivedMessage);
	          ucmr.readData(inStream);
	          BNrio16Module localIo = ((BM2mIoNetwork)nrioNet).getLocalIoModule();
	          int setAddress = 1;
	          if( localIo == null )
	          {
	            if( setAddressAndPing(setAddress, ucmr.getUid(), ucmr.getModuleType()) != NrioMessageConst.MESSAGE_STATUS_OK)
	            {
		        	// reset device in  case the device actually received setAddress
	              nrioNet.clearAddressUsed(setAddress);
	//	        	nrioNet.sendReset(setAddress);
	              continue;
	            }
	            else
	            {
	              String version = nrioNet.readBuildInfo(setAddress);
	              addLearnedDevice(setAddress, BNrioDeviceTypeEnum.makeFromRaw(ucmr.getModuleType()), ucmr.getUid(), version, "", -1);
	            }
	          }
	          else // already in station
	          {
	        	  getDiscoveryLog().trace(" ******  discovered device allready in station ******");
	        	  localIo.setAddress(1);
	        	  localIo.setUid(BBlob.make(ucmr.getUid()));
	        	  if(ucmr == null)
	        	    getDiscoveryLog().message("ucmr is NULL");
	        	  else if(ucmr.getUid() == null)
	        	    getDiscoveryLog().message("ucmr.getUid() is NULL");
	        	  getDiscoveryLog().message("ucmr.getModuleType() = " + ucmr.getModuleType());
	            if( setAddressAndPing(setAddress, ucmr.getUid(), ucmr.getModuleType()) == NrioMessageConst.MESSAGE_STATUS_OK)
	            {
	              if( localIo.sendWriteConfig() == 0)
	                nrioNet.enablePolling(setAddress);
	              else
	            	  getDiscoveryLog().trace("write config was not Ok");
	              localIo.readBuildInfo();
	            }
	            else
	            {
	            	getDiscoveryLog().trace("Set logical address and Ping was not Ok");
	              addLearnedDevice(setAddress, BNrioDeviceTypeEnum.makeFromRaw(ucmr.getModuleType()), ucmr.getUid(), localIo.getInstalledVersion(), localIo.getDisplayName(null), -1);
	            }
	          }
	        }
        }
        catch(Exception e)
        {
        	exceptionCount++;
        	getDiscoveryLog().trace("*** caught Exception: " + e + " count = " + exceptionCount);
        	e.printStackTrace();
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
          nrioNet.sendReset(learnEntries[i].getAddress());
      }
    progress(100);

 }

//  private void addExistingDevices()
//  {
//    BDevice[] devices = nrioNet.getDevices();
//    for(int i = 0; i < devices.length; i++)
//    {
//      if( !(devices[i] instanceof BNrioDevice) )
//        continue;
//      if(devices[i].isDown())
//        continue;
//      BNrioDevice device = (BNrioDevice)devices[i];
//      addLearnedDevice(device.getAddress(), device.getDeviceType(), device.getUid().copyBytes(), device.getInstalledVersion(), device.getDisplayName(null));
//    }
//  }
//
//  private int setAddressAndPing(int logicalAddress, byte[] uid, int type)
//  {
//    SetLogicalAddressMessage setAddrMsg = new SetLogicalAddressMessage( logicalAddress, uid );
//    NrioMessage rsp = (NrioMessage)(nrioNet.sendNrioMessage(setAddrMsg));
//    if(rsp.getStatus() != NrioMessageConst.MESSAGE_STATUS_OK)
//      return rsp.getStatus();
//    try{Thread.sleep(20l);}catch(Exception e){}
//    PingMessage pReq = new PingMessage(logicalAddress, uid, type);
//    PingResponse pRsp = (PingResponse)(nrioNet.sendNrioMessage(pReq));
//    return pRsp.getStatus();
//  }
//
//
//  private static final String LEARN_NAME_PREFIX = "Device";
//  final BM2mIoNetwork nrioNet;
//  private int learnCount = 0;
//
//  public Log getDiscoveryLog()
//  {
//	return Log.getLog(nrioNet.getName() + ".discovery");
//  }
//
//  private void logMessage(String message)
//  {
//    log().message(message);
//
//    if(nrioNet != null)
//    {
//      Log discLog = getDiscoveryLog();
//      int severity = discLog.getSeverity();
//      discLog.setSeverity(Log.MESSAGE);
//      discLog.message("Learn Nrio Devices Job:" + message);
//      discLog.setSeverity(severity);
//    }
//  }

}
