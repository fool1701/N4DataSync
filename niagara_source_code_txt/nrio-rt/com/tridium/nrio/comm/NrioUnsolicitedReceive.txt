/**
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.comm;

import javax.baja.log.Log;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.Clock;
import javax.baja.sys.NotRunningException;

import com.tridium.basicdriver.UnsolicitedMessageListener;
import com.tridium.basicdriver.message.ReceivedMessage;
import com.tridium.nrio.BNrio16Module;
import com.tridium.nrio.BNrio34Module;
import com.tridium.nrio.BNrio34PriModule;
import com.tridium.nrio.BNrio34SecModule;
import com.tridium.nrio.BNrioDevice;
import com.tridium.nrio.BNrioInputOutputModule;
import com.tridium.nrio.BNrioNetwork;
import com.tridium.nrio.components.BIoStatus;
import com.tridium.nrio.components.BNrio16Status;
import com.tridium.nrio.components.BNrio34PriStatus;
import com.tridium.nrio.components.BNrio34SecStatus;
import com.tridium.nrio.messages.NrioMessage;
import com.tridium.nrio.messages.NrioMessageConst;
import com.tridium.nrio.messages.NrioReceivedMessage;
import com.tridium.platNrio.NrioConst;

/**
 * The receive thread for processing unsolicited received messages
 * from the driver.
 *
 * @author    Andy Saunders (Original R2 code)
 * @creation  29 Feb 00
 * @author    Andy Saunders (Upgraded for R3)
 * @creation  13 Nov 02
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:11 AM$
 * @since     Niagara 3.0 geM6 1.0
 */
public class NrioUnsolicitedReceive
  implements Runnable, UnsolicitedMessageListener, NrioMessageConst
{

////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////
  public NrioUnsolicitedReceive(BNrioNetwork host)
  {
    this.host = host;
  }


  /**********************************************
  * Initialize
  **********************************************/
  public final void init()
  {
//    devices = new BNrioDevice[20];
  }

  /**********************************************
  * Cleanup the service.
  **********************************************/
  public final void cleanup()
  {
//    devices = null;
  }

  /**********************************************
  * Start the engine thread.
  **********************************************/
  public final void start()
  {
    if(host != null && host.isFatalFault())
      return;
    timeToDie = false;
    myThread = new Thread(this, "NrioUnsolicitedReceive"+host.getTrunk());
    myThread.setPriority(host.getReadThreadPriority());
    myThread.start();
    startTicks = Clock.ticks();
  }

  /**********************************************
  * Stop the engine thread.
  **********************************************/
  public final void stop()
  {
    if (myThread == null)
    {
      return;
    }
  
    getUnsolicitedLog().trace("Requesting termination of NrioUnsolicitedReceive thread");
    
    //Signal the thread's termination
    timeToDie = true;
    myThread.interrupt();
    
    if (!myThreadWaitingForStatusChange)
    {
      //HAREMB-1287: Wait patiently for the threads termination if it is not presently blocked on status change, it should
      //be actively checking the timeToDie signal throughout its lifecycle at various important transitions
      //sensitive to interupt above.
      try
      {
        myThread.join(UNSOLICITED_THREAD_STOP_TIMEOUT);
      }
      catch (InterruptedException ignored) {}
  
      //If we did wait patiently, print a message if it did not exit in a timely fashion
      if (myThread.isAlive())
      {
        getUnsolicitedLog().warning(myThread.getName() + " did not terminate in a timely manner, abandoning thread");
      }
    }
    else
    {
      //Else thread is presently blocked on status change function, it will only unblock with comm is stopped.
      //In that case, simply signal that the thread should stop and make preparations in the status change catch
      //block to ignore any errors - they aren't relevant because the comm layer was forcibly closed.
      getUnsolicitedLog().trace(myThread.getName() + " blocked on status change, not waiting for thread termination");
    }
    
    myThread = null;
  }

  public boolean isDying()
  {
    return timeToDie;
  }

  public void setThreadPriority(int priority)
  {
    if (myThread == null)
    {
      return;
    }
    
    if(priority != myThread.getPriority())
    {
      getUnsolicitedLog().message(myThread.getName() + " thread priority changed from " + myThread.getPriority() + " to " + priority);
      myThread.setPriority(priority);
    }
  }

  ////////////////////////////////////////////////////
  // Nrio method
  ////////////////////////////////////////////////////
  /**********************************************
  *  Nrio point called by the driver
  *  receive API to place unsolicited messages
  *  on the UnsolicitedMessages queue
  *
  *  @param  message incoming message
  **********************************************/
  public void receiveMessage( ReceivedMessage message)
  {
    MessageElement msgElement = new MessageElement((NrioReceivedMessage)message);
    //
    //  Make sure the receive thread has defined
    //  the manager
    //
    if ( unsolicitedMessageManager != null)
    {
      unsolicitedMessageManager.addToTail( msgElement);

    }

  }
  private static Integer DEFAULT_TAG = Integer.valueOf(-1);
  public Object getUnsolicitedListenerCode()
  {
    return DEFAULT_TAG;
  }


////////////////////////////////////////////////////////////////
//  Required run method
////////////////////////////////////////////////////////////////
  public void run()
  {
    try
    {
      getUnsolicitedLog().message("NrioUnsolicitedReceive thread started.");
      NrioMessage statusMessage = new NrioMessage();
      NrioComm comm = (NrioComm)host.getComm();

      byte[] ioStatusBytes = new byte[NrioConst.MAX_MESSAGE_SIZE];
      byte[] lastIoStatusBytes = new byte[NrioConst.MAX_MESSAGE_SIZE];
      long lastTicks = Clock.ticks();
      rateTicks = lastTicks;
      rateCount = msgCount;
      processedRateCount = host.getProcessedUnsolicitedMsgCount();
      while (!timeToDie)
      {
//      nrioService = host.getNrioService();
        final boolean isPushToPoints = host.getPushToPoints();
        comm = (NrioComm)host.getComm();
        try
        {
          if (host.isDownLoadInProcess())
          {
            try
            {
              Thread.sleep(500l);
            }
            catch (Exception e)
            {
              //Thread interrupted, exit if requested
              if (timeToDie)
              {
                break;
              }
            }
          }
          else
          {
            long nowTicks = Clock.ticks();
            long processTime = nowTicks - lastTicks;
            if ((nowTicks - rateTicks) > 5000l)
            {
              host.setUnsolicitedMessageRate((msgCount - rateCount) * 1000f / (float)(nowTicks - rateTicks));
              rateCount = msgCount;
              final long processedUnsolicitedMsgCount = host.getUnsolicitedProcessedCount();
              host.setPushedUnsolicitedMessageRate((processedUnsolicitedMsgCount - processedRateCount) * 1000f / (float)(nowTicks - rateTicks));
              processedRateCount = processedUnsolicitedMsgCount;
              rateTicks = nowTicks;

            }
            host.setUnsolicitedProcessTime((int)processTime);
            totalProcessTime += processTime;
            getUnsolicitedLog().trace("NrioUnsolicitedReceive processing time = " + processTime);
            ByteArrayUtil.copy(ioStatusBytes, lastIoStatusBytes);

            try
            {
              myThreadWaitingForStatusChange = true;
              comm.waitForStatusChange(comm.getHandle(), ioStatusBytes);
            }
            finally
            {
              myThreadWaitingForStatusChange = false;
            }
            
            host.setUnsolicitedMsgCount(++msgCount);
            lastTicks = Clock.ticks();
            if (host.isDownLoadInProcess())
            {
              getUnsolicitedLog().trace("NrioUnsolicitedReceive: wakeup with downLoadInProcess");
              continue;
            }
            int length = (ioStatusBytes[1] & 0x0ff) + 2;
            statusMessage.decodeFromBytes(ioStatusBytes, length);
            getUnsolicitedLog().trace("ioStatusMessage: " + ByteArrayUtil.toHexString(ioStatusBytes, 0, length));
            if ( length < 4 || statusMessage.getType() != NrioMessageConst.MSG_IO_STATUS)
            {
              // debug sleep here to give other things time to run**********
              try
              {
                Thread.sleep(100l);
              }
              catch (Exception e)
              {
                //Thread interrupted, exit if requested
                if (timeToDie)
                {
                  break;
                }
              }
              ++invalidMessageCount;
              getUnsolicitedLog().trace("received invalid message: " + ByteArrayUtil.toHexString(ioStatusBytes, 0, (length)));
              getUnsolicitedLog().trace("            last message: " + ByteArrayUtil.toHexString(lastIoStatusBytes));
            }
            else
            {
              BNrioDevice device = host.getDevice(statusMessage.getAddress());
              if (device != null)
              {
                if (statusMessage.getStatus() != NrioMessageConst.MESSAGE_STATUS_OK)
                {
                  device.pingFail("nrioService reports device is down");
                }
                else
                {
//                  getUnsolicitedLog().message("ioStatusMessage for device instance: " + device.getType().toString());
                  if (!device.getFirstPing())
                  {
                    device.pingOk();
                  }
                  if (device instanceof BNrio34SecModule)
                  {
                    BNrio34SecModule io34Sec = (BNrio34SecModule)device;
                    getUnsolicitedLog().trace("IO34.2 module ioStatusMessage: " + ByteArrayUtil.toHexString(ioStatusBytes, 0, length));
                    if (!((BNrio34SecStatus)io34Sec.getIoStatus()).readIoStatus(ioStatusBytes, 0, statusMessage.getLength() + 2))
                    {
                      droppedByteCount++;
                    }
                    if (isPushToPoints)
                    {
                      final BNrio34Module io34Pri = io34Sec.getParentModule();
                      if (io34Pri != null)
                      {
                        io34Sec.getPoints().doPushToPoints();
//                        io34Pri.getPoints().setDynamicPoints();
                      }

                      else
                      {
                        getUnsolicitedLog().error("IO34.2 module ioStatusMessage: IO34.1 module not found!");
                      }
                    }
                  }
                  else if ( device instanceof BNrio34Module)
                  {
                    BNrio34PriModule io34Pri = (BNrio34PriModule)device;
                    getUnsolicitedLog().trace("IO34.1 module ioStatusMessage: " + ByteArrayUtil.toHexString(ioStatusBytes, 0, length));
                    if (!((BNrio34PriStatus)io34Pri.getIoStatus()).readIoStatus(ioStatusBytes, 0, statusMessage.getLength() + 2))
                    {
                      droppedByteCount++;
                    }
                    if (isPushToPoints)
                    {
                      io34Pri.getPoints().doPushToPoints();
//                      io34Pri.getPoints().setDynamicPoints();
                    }
                  }
                  else if (device instanceof BNrio16Module)
                  {
                    BNrio16Module io16Device = (BNrio16Module)device;
                    getUnsolicitedLog().trace("Io16 module ioStatusMessage: " + ByteArrayUtil.toHexString(ioStatusBytes, 0, length));
                    if (!((BNrio16Status)io16Device.getIoStatus()).readIoStatus(ioStatusBytes, 0, statusMessage.getLength() + 2))
                    {
                      droppedByteCount++;
                    }
                    if (isPushToPoints)
                    {
                      io16Device.getPoints().doPushToPoints();
//                      io16Device.getPoints().setDynamicPoints();
                    }
                  }
                  else if (device instanceof BNrioInputOutputModule)
                  {
                    getUnsolicitedLog().trace("GpIo module ioStatusMessage: " + ByteArrayUtil.toHexString(ioStatusBytes, 0, length));
                    //NCCB-63250: Create and use readIoStatus() to update BIoStatus slots directly on
                    //            the existing BNrioDevice ioStatus slot instead of new reference in parent,
                    //            avoids DRS changed record on each poll.
                    if (!((BIoStatus)device.getIoStatus()).readIoStatus(ioStatusBytes, 0, statusMessage.getLength() + 2))
                    {
                      droppedByteCount++;
                    }
                    device.getPoints().setDynamicPoints();
                  }
                  else
                  {
                    getUnsolicitedLog().trace("Other module ioStatusMessage: " + ByteArrayUtil.toHexString(ioStatusBytes, 0, length));
                    //NCCB-63250: Create and use readIoStatus() to update BIoStatus slots directly on
                    //            the existing BNrioDevice ioStatus slot instead of new reference in parent,
                    //            avoids DRS changed record on each poll.
                    if (!((BIoStatus)device.getIoStatus()).readIoStatus(ioStatusBytes, 0, statusMessage.getLength() + 2))
                    {
                      droppedByteCount++;
                    }
                    device.getPoints().setDynamicPoints();
                    device.processIoStatusMessage(statusMessage);
                  }
                  //getUnsolicitedLog().trace("NrioUnsolicetedReceive:  invalid length or device is not a BNrio2ReaderModule in station.");
                }
              }
              else
              {
                getUnsolicitedLog().message("nrioService reports unknown device is down: " + statusMessage.getAddress());
              }
            }
          }
        }
        catch (Exception e)
        {
          if (host == null || host.getName() == null)
          {
            timeToDie = true;
          }
          
          if (timeToDie)
          {
            break;
          }
          
          if (host.isDownLoadInProcess())
          {
            getUnsolicitedLog().trace("NrioUnsolicitedReceive: wakeup with downLoadInProcess");
          }
          else if (e instanceof NotRunningException)
          {
            getUnsolicitedLog().message("NotRunningException " + host.getName() + ": point just added or removed. ");
          }
          else
          {
            getUnsolicitedLog().error("Exception with " + host.getName() + "-NrioUnsolicitedReceive : " + e);
            String eMessage = e.getMessage();
            if (eMessage != null && eMessage.equals("wait for status change failed"))
            {
              host.configFatal("FatalFault! actrld is not running at " + BAbsTime.now());
              timeToDie = true;
            }
            e.printStackTrace();
          }
          
          // debug sleep here to give other things time to run**********
          try
          {
            Thread.sleep(100l);
          }
          catch (Exception e1)
          {
            //Thread interrupted, exit if requested
            if (timeToDie)
            {
              break;
            }
          }
        }
      }
    }
    finally
    {
      getUnsolicitedLog().message("NrioUnsolicitedReceive thread stopped.");
    }
  } // end of run

  public BNrioDevice getDevice(NrioReceivedMessage message)
  {
    int length = message.getLength();
    if(length <= 0)
      return null;
    int address = message.getBytes()[0] & 0x0ff;
    return host.getDevice(address);
  }

  public Log getUnsolicitedLog()
  {
	  return Log.getLog(host.getName() + ".unsolicited");
  }

//   public void toggleMessageProcessing()
//   {
//     if(++processMessages > 2)
//       processMessages = 0;
//     switch(processMessages)
//     {
//       case 0:
//         System.out.println("processMessages set to NORMAL OPERATION");
//         break;
//       case 1:
//         System.out.println("processMessages set to PROCESS NONE");
//         break;
//       case 2:
//         System.out.println("processMessages set to ONLY IOSTATUS IN DEVICE");
//         break;
//
//     }
//   }

  public long getInvalidMessageCount()
  {
    return invalidMessageCount;
  }
  public long getDroppedByteCount()
  {
    return droppedByteCount;
  }
  public long getTotalProcessTime() { return totalProcessTime; }
  public long getRunTime() { return Clock.ticks() - startTicks; }

  /////////////////////////////////////////////////////////////
  //  Attributes of NrioUnsolicitedReceive class
  /////////////////////////////////////////////////////////////
  private TLinkedListManager  unsolicitedMessageManager = null;
  private BNrioNetwork  host;
  private long msgCount = 0l;
  private long rateTicks = 0l;
  private long  rateCount = 0;
  private long  processedRateCount = 0;
  private long droppedByteCount = 0;
  private long totalProcessTime = 0;
  private long startTicks = 0;
  private long invalidMessageCount = 0;
  
  //Thread lifecycle
  private boolean timeToDie = true;
  private Thread myThread;
  private volatile boolean myThreadWaitingForStatusChange = false;
  private static final int UNSOLICITED_THREAD_STOP_TIMEOUT = 10000;

//  private int processMessages = 0;

} // end of class NrioUnsolicitedReceive
