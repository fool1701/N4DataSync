/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.util;

import javax.baja.sys.Clock;
import com.tridium.nrio.BNrio16Module;
import com.tridium.nrio.BNrioDevice;
import com.tridium.nrio.BNrioNetwork;
import com.tridium.nrio.enums.BNrioDeviceTypeEnum;
import com.tridium.nrio.messages.NrioInputStream;
import com.tridium.nrio.messages.NrioMessageConst;
import com.tridium.nrio.messages.PingMessage;
import com.tridium.nrio.messages.PingResponse;
import com.tridium.nrio.job.BNrioLearnDevicesJob;

/**
 * Created by E333968 on 11/16/2016.
 */
public class DualModuleUtils
  implements com.tridium.nrio.messages.NrioMessageConst
{

  public static int sendPing(BNrioDevice device)
  {
    // Process the request on the calling thread.
    if (!device.isRunning())
      return SEND_PING_NOT_RUNNING;
    BNrioNetwork network = (BNrioNetwork)device.getNetwork();
    if (network.isDownLoadInProcess())
      return SEND_PING_DOWNLOADING;
    if (device.isFault())
      return SEND_PING_FAULT;
    PingMessage req = new PingMessage(device.getAddress(), device.getUid().copyBytes(), device.getDeviceType());
    PingResponse rsp = (PingResponse)(network.sendNrioMessage(req));
    int status = rsp.getStatus();

    if (status == NrioMessageConst.DOWNLOAD_IN_PROGRESS)
      return SEND_PING_DOWNLOADING;
    if (status != NrioMessageConst.MESSAGE_STATUS_OK)
    {
      if(device.incrementPingFailCount())
      {
        device.pingFail("PingMessage error: " + status);
        return SEND_PING_ERROR;
      }
    }
    return SEND_PING_OK;
  }

  public static boolean processPostPing(BNrioDevice device, boolean needsInitialized)
  {
    if(needsInitialized)
    {
      int status = device.sendWriteConfig();
      if(status == NrioMessageConst.MESSAGE_STATUS_OK)
      {
        device.doEnablePolling();
        device.firstPing = false;
        device.pingOk();
        device.readBuildInfo();
        device.initLastWrite();
        if(device instanceof BNrio16Module)
        {
          ((BNrio16Module)device).postWriteOutputDefaults(); //
          ((BNrio16Module)device).writeIo(); // sets forceWrite flag
        }
//        device.doWriteDoValues();
        return true;
      }
      else
      {
        if(device.incrementPingFailCount())
        {
          device.pingFail("WriteConfig error: " + status);
        }
        return false;
      }

    }
    else // ping was OK and device is not currently down.
    {
      // always emable polling Issue
      device.doEnablePolling();
      String installedVersion = device.getInstalledVersion();
      if(installedVersion.length() == 0 || installedVersion.startsWith("up", 0))
        device.readBuildInfo();
      device.pingOk();
      return true;
    }

  }

  public static boolean addressDualModule(BNrioNetwork nrioNet, BNrioLearnDevicesJob job, int moduleType, byte[] uid, int setAddress)
  {
    int otherAddr = nrioNet.getIo34OtherAddr(moduleType, uid);
    if(otherAddr < 0)
    {
      if(job != null)
        job.getDiscoveryLog().message("waiting for partner module to be addressed");
      return true;  // wait for partner module to be addressed.
    }
    // delay 1 second to give pri and sec modules time to sysn up.
    try{Thread.sleep(1000L);}
    catch(Exception ignore) {}
    String priVersion = "";
    String secVersion = "";
    int priAddr = -1;
    int secAddr = -1;
    if(moduleType == REMOTE_IO_34_PRI)
    {
      priAddr = setAddress;
      secAddr = otherAddr;
    }
    else
    {
      priAddr = otherAddr;
      secAddr = setAddress;
    }
    priVersion = nrioNet.readBuildInfo(priAddr);
    secVersion = nrioNet.readBuildInfo(secAddr);
    String version = priVersion + ";" + secVersion;
    if(priVersion.length() != 0 && secVersion.length() != 0)
      job.addLearnedDevice(priAddr, BNrioDeviceTypeEnum.makeFromRaw(REMOTE_IO_34_PRI), uid, version, "", secAddr);
    else
    {
      nrioNet.clearAddressUsedV2(priAddr);
      nrioNet.clearAddressUsedV2(secAddr);
      nrioNet.sendReset(priAddr);
      nrioNet.sendReset(secAddr);
    }
    return false;

  }

  public static int[] bytesToValues(int index, byte[] bytes)
  {
    int[] values = new int[4];
    for(int i = 0; i < 4; ++ i )
    {
      values[i] = 0;
      if(i%2 == 0)
      {
        values[i] = bytes[index++] << 4;
        values[i] |= (bytes[index] >> 4)  & 0x0f;
      }
      else
      {
        values[i] = (bytes[index++] & 0x0f) << 8;
        values[i] |= bytes[index++] & 0x0ff;
      }
      values[i] = values[i] & 0x0fff;
//      System.out.println("values[" + i + "] = 0x" + Integer.toHexString(values[i]));
    }
    return values;
  }

  public static float[] bytesToValues(NrioInputStream in)
  {
    float[] values = new float[4];
    int dualbyte = 0;
    for(int i = 0; i < 4; ++ i )
    {
      int value = 0;
      if(i%2 == 0)
      {
        value = in.read() << 4;
        dualbyte = in.read();
        value |= (dualbyte >> 4)  & 0x0f;
      }
      else
      {
        value = (dualbyte << 8) & 0x0f00;
        value |= in.read() & 0x0ff;
      }
      value = value & 0x0fff;
      values[i] = 10.0f * (float)value/(float)4096;
//      System.out.println("values[" + i + "] = 0x" + Integer.toHexString(values[i]));
    }
    return values;
  }



  protected static final int SEND_PING_OK = 0;
  protected static final int SEND_PING_NOT_RUNNING = 1;
  protected static final int SEND_PING_DOWNLOADING = 2;
  protected static final int SEND_PING_FAULT = 3;
  protected static final int SEND_PING_ERROR = 4;

}
