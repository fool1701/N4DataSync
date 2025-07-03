/*
 * @copyright 2005 Tridium Inc.
 */
package com.tridium.nrio.job;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Vector;
import java.util.logging.Logger;

import javax.baja.driver.BDevice;
import javax.baja.file.BIFile;
import javax.baja.job.BSimpleJob;
import javax.baja.log.Log;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.BNrio34Module;
import com.tridium.nrio.BNrioDevice;
import com.tridium.nrio.BNrioNetwork;
import com.tridium.nrio.messages.NrioMessage;
import com.tridium.nrio.messages.NrioMessageConst;
import com.tridium.nrio.messages.WriteDownLoadData;
import com.tridium.nrio.messages.WriteDownLoadStart;
import com.tridium.nrio.messages.WriteDownLoadStop;
import com.tridium.nrio.util.FirmwareUpgradeUtils;

/**
 * BUpgradeFirmwareJob - This job is used by the access network to upgrade the firmware in the
 * connected access devices.
 *
 * @author    Andy Saunders
 * @creation  08 Feb 2006
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
@NiagaraType
public class BUpgradeFirmwareJob
  extends BSimpleJob
  implements NrioMessageConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.job.BUpgradeFirmwareJob(2979906276)1.0$ @*/
/* Generated Fri Jul 30 15:18:13 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUpgradeFirmwareJob.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



  /**
   * Constructor - BUpgradeFirmwareJob
   *
   *
   */
  public BUpgradeFirmwareJob()
  {
    super();
    this.accessNet=null;
  }
  /**
   * Constructor - BUpgradeFirmwareJob
   */
  public BUpgradeFirmwareJob(BNrioNetwork net )
  {
    super();
    this.accessNet=net;
  }

  private static boolean isRunning = false;

  /* (non-Javadoc)
   * @see javax.baja.job.BSimpleJob#run(javax.baja.sys.Context)
   */
  public void run(Context cx) throws Exception
  {
    if(isRunning)
    {
      throw new IllegalStateException("FirmwareUpgrade already running could not start!!!");
    }
    isRunning = true;
    logMessage("starting...");
    boolean downLoadError = false;
    BDevice[] devices = accessNet.getDevices();
    try
    {
      accessNet.setDownLoadInProcess(true);
      logMessage("Wait 15 seconds to allow all devices to go 0ffline");
      for(int i = 1; i <= 15; i++)
      {
       progress(i);
       try{Thread.sleep(1000l);} catch(Exception e){}
      }

      for(int i = 0; i < devices.length; i++)
      {
        if( !(devices[i] instanceof BNrioDevice) )
          continue;
        if(devices[i].isDown())
          continue;
        BNrioDevice device = (BNrioDevice)devices[i];
        if( !upgradeFirmware(device) )
          downLoadError = true;
        if(device instanceof BNrio34Module)
        {
          if( !upgradeFirmware(((BNrio34Module)device).getIo34Sec()) )
            downLoadError = true;
        }
        progress( 15 + ((i+1)*85)/devices.length );
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    try{Thread.sleep(1000l);} catch(Exception e){}
    accessNet.setDownLoadInProcess(false);
    for(int i = 0; i < devices.length; i++)
    {
      ((BNrioDevice)devices[i]).doPing();
      ((BNrioDevice)devices[i]).enablePolling();

    }
    isRunning = false;
    if(downLoadError)
    {
      throw new IllegalStateException("complete with errors");
      //logMessage(" complete with errors");
      //failed(null);
    }
  }

  private boolean upgradeFirmware(BNrioDevice device)
  {
    // this forceUpgrade is just a test thing
    boolean forceUpgrade = false;
    if( !forceUpgrade && device.getInstalledVersion().equals(device.getAvailableVersion()) )
    {
      logMessage(" device " + device.getDisplayName(null) + " is up to date");
      return true;
    }
    // get download file.
    BIFile downLoadFile = accessNet.getFirmwareFile(device.getDeviceType());
    if(downLoadFile == null)
    {
      logMessage("downloadFile is null!!");
      return false;
    }

    return FirmwareUpgradeUtils.upgradeFirmware(accessNet, device, downLoadFile, this);

  }


  final BNrioNetwork accessNet;

  private void logMessage(String message)
  {
    log().message(message);

    if(accessNet != null)
    {
      int severity = accessNet.getLog().getSeverity();
      accessNet.getLog().setSeverity(Log.MESSAGE);
      accessNet.getLog().message("Upgrade Firmware Job:" + message);
      accessNet.getLog().setSeverity(severity);
    }
  }

  public static Logger logger = Logger.getLogger("nrio.upgradeFirmware");

}
