/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.serial;

import java.io.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * The BISerialPort is the public API used to access serial ports
 *
 *
 * @author    Dan Giorgis
 * @creation  03 Feb 04
 * @version   $Revision: 4$ $Date: 5/23/05 1:20:35 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BISerialPort
  extends BInterface
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.serial.BISerialPort(2979906276)1.0$ @*/
/* Generated Fri Sep 17 11:17:08 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BISerialPort.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   *  Once close is called, the BISerialPort reference becomes invalid.
   *  All further method invocations will result in a PortClosedException
   */
  public void close();

  public String getOwner();
  
  public String getOsPortName();
     
  public void sendBreak(int millis);
  
  public void setFlowControlMode(BSerialFlowControlMode flowcontrol) throws UnsupportedOperationException;
  public BSerialFlowControlMode getFlowControlMode();

  public BBaudRate getBaudRate();
  public BSerialDataBits getDataBits();
  public BSerialStopBits getStopBits();
  public BSerialParity   getParity();

  public void setSerialPortParams(BBaudRate baudrate,
                             BSerialDataBits dataBits,
                             BSerialStopBits stopBits,
                             BSerialParity parity) throws UnsupportedOperationException;

  public void setDTR(boolean dtr);
  public boolean isDTR();
  public void setRTS(boolean rts);
  public boolean isRTS();
  public boolean isCTS();
  public boolean isDSR();
  public boolean isRI();
  public boolean isCD();

  public void enableReceiveThreshold(int threshold) throws UnsupportedOperationException;
  public void disableReceiveThreshold();
  public boolean isReceiveThresholdEnabled();
  public int getReceiveThreshold() throws UnsupportedOperationException;

  public void enableReceiveTimeout(int timeout) throws UnsupportedOperationException;
  public void disableReceiveTimeout();
  public boolean isReceiveTimeoutEnabled();
  public int getReceiveTimeout() throws UnsupportedOperationException;

  public InputStream getInputStream() throws IOException;
  public OutputStream getOutputStream() throws IOException;

  /*
  Send a reset command to a port driver that supports reset.
  NOTE: If your class supports reset, you should implement a "resetPort" action, such that the
  doResetPort() callback is required.
   */
  public void doResetPort() throws IOException;
}
