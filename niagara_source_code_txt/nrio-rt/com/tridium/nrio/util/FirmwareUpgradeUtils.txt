/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Vector;
import javax.baja.file.BIFile;
import javax.baja.log.Log;
import javax.baja.sys.Clock;
import javax.baja.util.*;
import javax.baja.job.*;
import com.tridium.nrio.BNrioDevice;
import com.tridium.nrio.BNrioNetwork;
import com.tridium.nrio.messages.NrioInputStream;
import com.tridium.nrio.messages.NrioMessage;
import com.tridium.nrio.messages.NrioMessageConst;
import com.tridium.nrio.messages.PingMessage;
import com.tridium.nrio.messages.PingResponse;
import com.tridium.nrio.messages.WriteDownLoadData;
import com.tridium.nrio.messages.WriteDownLoadStart;
import com.tridium.nrio.messages.WriteDownLoadStop;

/**
 * Created by E333968 on 11/16/2016.
 */
public class FirmwareUpgradeUtils
  implements NrioMessageConst

{

  public static boolean upgradeFirmware(BNrioNetwork accessNet, BNrioDevice device, BIFile downLoadFile, BJob job )
  {
    if(downLoadFile == null)
    {
      logMessage(accessNet, job, "downloadFile is null!!");
      return false;
    }

    logMessage(accessNet, job, " upgrading device: " + device.getDisplayName(null) + " with file " + downLoadFile.getFileName());
    IntelHexFile hexFile = IntelHexFile.make(downLoadFile);
    if(hexFile == null)
    {
      logMessage(accessNet, job, "hexFile is null!!!!!!!!!!!!!!!!!");
      return false;
    }
    Vector<IntelHexFile.MemoryBlock> v = hexFile.readMemoryBlocks();
    if(v == null)
    {
      logMessage(accessNet, job, "vector is null!!!!!!!!!!!!!!!!!");
      return false;
    }
    long startTicks = Clock.ticks();
    device.setInstalledVersion("upgrading");
    try{Thread.sleep(100l);} catch(Exception e){}
    WriteDownLoadStart message = new WriteDownLoadStart(device.getAddress());
    NrioMessage response = accessNet.sendDownLoadMessage(message, 0);
    try{Thread.sleep(100l);} catch(Exception e){}
    if(response == null  || response.getStatus() != 0)
    {
      logMessage(accessNet, job, "Aborting - start down load returned error!");
      device.setInstalledVersion("upgrade failed");
      return false;
    }
    // first download the c000 block of data
    boolean doesC000Exist = false;
    for(int i = 0; i < v.size(); i++)
    {
      IntelHexFile.MemoryBlock memBlock = v.elementAt(i);
      if(memBlock.baseAddress >= 0xc000 && memBlock.baseAddress < 0xd000)
      {
        doesC000Exist = true;
        if(!writeMemoryBlock(accessNet, device.getAddress(), memBlock))
        {
          logMessage(accessNet, job, " WriteDownLoad message returned error!!!");
          device.setInstalledVersion("upgrade failed");
          return false;
        }
        try{Thread.sleep(100l);} catch(Exception e){}
      }
    }
    WriteDownLoadStop stopMsg = new WriteDownLoadStop(device.getAddress());
    response = accessNet.sendDownLoadMessage(stopMsg, 0);
    if(!doesC000Exist)
    {
      logMessage(accessNet, job, " Oxc000 block does not exist!!!");
      device.setInstalledVersion("upgrade failed");
      return false;
    }
    // now down load other blocks
    for(int i = 0; i < v.size(); i++)
    {
      IntelHexFile.MemoryBlock memBlock = v.elementAt(i);
      if(memBlock.baseAddress < 0xc000 || memBlock.baseAddress >= 0xd000)
      {
        try{Thread.sleep(100l);} catch(Exception e){}
        writeMemoryBlock(accessNet, device.getAddress(), memBlock);
        try{Thread.sleep(100l);} catch(Exception e){}
      }
    }

    stopMsg = new WriteDownLoadStop(device.getAddress());
    response = accessNet.sendDownLoadMessage(stopMsg, 0);
    long loadTimeMillis = Clock.ticks() - startTicks;
    logMessage(accessNet, job, device.getDisplayName(null) + " - Download completed in " +loadTimeMillis + " milliseconds" );
    device.setInstalledVersion("upgraded");
    try{Thread.sleep(1000l);} catch(Exception e){}
    //device.enablePolling();
    //try{Thread.sleep(2000l);} catch(Exception e){}
    //device.disablePolling();
    //try{Thread.sleep(1000l);} catch(Exception e){}
    return true;
  }

  private static boolean writeMemoryBlock(BNrioNetwork accessNet, int address, IntelHexFile.MemoryBlock memBlock)
  {
    byte[] bytes = memBlock.out.toByteArray();
    int offset = 0;
    WriteDownLoadData message = new WriteDownLoadData(address, bytes, memBlock.baseAddress, offset);
    while(offset < bytes.length)
    {
      message.setOffset(offset);
      try{Thread.sleep(10l);} catch(Exception e){}
      NrioMessage response = accessNet.sendDownLoadMessage(message, 2);
      if( response == null || response.getStatus() != 0)
      {
        return false;
      }
      offset = offset + MAX_MEMORY_DOWN_LOAD_SIZE;
    }
    return true;
  }

  private static void logMessage(BNrioNetwork accessNet, BJob job, String message)
  {
    if(job != null)
      job.log().message(message);

    if(accessNet != null)
    {
      int severity = accessNet.getLog().getSeverity();
      accessNet.getLog().setSeverity(Log.MESSAGE);
      accessNet.getLog().message("Upgrade Firmware Job:" + message);
      accessNet.getLog().setSeverity(severity);
    }
  }


}
