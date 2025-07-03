/**
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.serial;

import java.util.logging.*;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BSerialHelper - Helper Component that handles all of the serial port 
 * communication properties.
 *
 * @author    Scott Hoye
 * @creation  27 Mar 02
 * @version   $Revision: 6$ $Date: 5/23/05 1:20:35 PM EDT$  
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Status of the serial port.  Fault if there was a problem
 with the serial port.  Down if the serial port isn't
 properly configured for use.  Ok if the serial port is
 in use.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.down",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 The serial comm port
 */
@NiagaraProperty(
  name = "portName",
  type = "String",
  defaultValue = "BSerialHelper.noPort",
  flags = Flags.DEFAULT_ON_CLONE
)
/*
 The baud rate to use for the serial communication
 */
@NiagaraProperty(
  name = "baudRate",
  type = "BBaudRate",
  defaultValue = "BSerialBaudRate.baud9600"
)
/*
 The number of data bits to use for the serial communication
 */
@NiagaraProperty(
  name = "dataBits",
  type = "BSerialDataBits",
  defaultValue = "BSerialDataBits.dataBits8"
)
/*
 The number of stop bits to use for the serial communication
 */
@NiagaraProperty(
  name = "stopBits",
  type = "BSerialStopBits",
  defaultValue = "BSerialStopBits.stopBit1"
)
/*
 The parity to use for the serial communication
 */
@NiagaraProperty(
  name = "parity",
  type = "BSerialParity",
  defaultValue = "BSerialParity.none"
)
/*
 The flow control mode to use for the serial communication
 */
@NiagaraProperty(
  name = "flowControlMode",
  type = "BSerialFlowControlMode",
  defaultValue = "BSerialFlowControlMode.none"
)
public class BSerialHelper
  extends BComponent
{
  public static final String noPort = "none";
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.serial.BSerialHelper(3605208241)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * Status of the serial port.  Fault if there was a problem
   * with the serial port.  Down if the serial port isn't
   * properly configured for use.  Ok if the serial port is
   * in use.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.TRANSIENT | Flags.READONLY, BStatus.down, null);

  /**
   * Get the {@code status} property.
   * Status of the serial port.  Fault if there was a problem
   * with the serial port.  Down if the serial port isn't
   * properly configured for use.  Ok if the serial port is
   * in use.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * Status of the serial port.  Fault if there was a problem
   * with the serial port.  Down if the serial port isn't
   * properly configured for use.  Ok if the serial port is
   * in use.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "portName"

  /**
   * Slot for the {@code portName} property.
   * The serial comm port
   * @see #getPortName
   * @see #setPortName
   */
  public static final Property portName = newProperty(Flags.DEFAULT_ON_CLONE, BSerialHelper.noPort, null);

  /**
   * Get the {@code portName} property.
   * The serial comm port
   * @see #portName
   */
  public String getPortName() { return getString(portName); }

  /**
   * Set the {@code portName} property.
   * The serial comm port
   * @see #portName
   */
  public void setPortName(String v) { setString(portName, v, null); }

  //endregion Property "portName"

  //region Property "baudRate"

  /**
   * Slot for the {@code baudRate} property.
   * The baud rate to use for the serial communication
   * @see #getBaudRate
   * @see #setBaudRate
   */
  public static final Property baudRate = newProperty(0, BSerialBaudRate.baud9600, null);

  /**
   * Get the {@code baudRate} property.
   * The baud rate to use for the serial communication
   * @see #baudRate
   */
  public BBaudRate getBaudRate() { return (BBaudRate)get(baudRate); }

  /**
   * Set the {@code baudRate} property.
   * The baud rate to use for the serial communication
   * @see #baudRate
   */
  public void setBaudRate(BBaudRate v) { set(baudRate, v, null); }

  //endregion Property "baudRate"

  //region Property "dataBits"

  /**
   * Slot for the {@code dataBits} property.
   * The number of data bits to use for the serial communication
   * @see #getDataBits
   * @see #setDataBits
   */
  public static final Property dataBits = newProperty(0, BSerialDataBits.dataBits8, null);

  /**
   * Get the {@code dataBits} property.
   * The number of data bits to use for the serial communication
   * @see #dataBits
   */
  public BSerialDataBits getDataBits() { return (BSerialDataBits)get(dataBits); }

  /**
   * Set the {@code dataBits} property.
   * The number of data bits to use for the serial communication
   * @see #dataBits
   */
  public void setDataBits(BSerialDataBits v) { set(dataBits, v, null); }

  //endregion Property "dataBits"

  //region Property "stopBits"

  /**
   * Slot for the {@code stopBits} property.
   * The number of stop bits to use for the serial communication
   * @see #getStopBits
   * @see #setStopBits
   */
  public static final Property stopBits = newProperty(0, BSerialStopBits.stopBit1, null);

  /**
   * Get the {@code stopBits} property.
   * The number of stop bits to use for the serial communication
   * @see #stopBits
   */
  public BSerialStopBits getStopBits() { return (BSerialStopBits)get(stopBits); }

  /**
   * Set the {@code stopBits} property.
   * The number of stop bits to use for the serial communication
   * @see #stopBits
   */
  public void setStopBits(BSerialStopBits v) { set(stopBits, v, null); }

  //endregion Property "stopBits"

  //region Property "parity"

  /**
   * Slot for the {@code parity} property.
   * The parity to use for the serial communication
   * @see #getParity
   * @see #setParity
   */
  public static final Property parity = newProperty(0, BSerialParity.none, null);

  /**
   * Get the {@code parity} property.
   * The parity to use for the serial communication
   * @see #parity
   */
  public BSerialParity getParity() { return (BSerialParity)get(parity); }

  /**
   * Set the {@code parity} property.
   * The parity to use for the serial communication
   * @see #parity
   */
  public void setParity(BSerialParity v) { set(parity, v, null); }

  //endregion Property "parity"

  //region Property "flowControlMode"

  /**
   * Slot for the {@code flowControlMode} property.
   * The flow control mode to use for the serial communication
   * @see #getFlowControlMode
   * @see #setFlowControlMode
   */
  public static final Property flowControlMode = newProperty(0, BSerialFlowControlMode.none, null);

  /**
   * Get the {@code flowControlMode} property.
   * The flow control mode to use for the serial communication
   * @see #flowControlMode
   */
  public BSerialFlowControlMode getFlowControlMode() { return (BSerialFlowControlMode)get(flowControlMode); }

  /**
   * Set the {@code flowControlMode} property.
   * The flow control mode to use for the serial communication
   * @see #flowControlMode
   */
  public void setFlowControlMode(BSerialFlowControlMode v) { set(flowControlMode, v, null); }

  //endregion Property "flowControlMode"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSerialHelper.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

//////////////////////////////////////////////////////////////
// Constructor
//////////////////////////////////////////////////////////////
  
  /** 
   * Default constructor
   */
  public BSerialHelper() 
  {
  }
  
  /**
   * Sets the parent component that uses this 
   * serial helper to configure its serial port communication properties.
   *
   * @param host - The parent component that uses this
   * serial helper to configure its serial port communication properties.
   */
  public void setSerialHelperParent(BISerialHelperParent host)
  {
    this.host = host;
  }
  
  /**
   * Gets the parent component that uses this 
   * serial helper to configure its serial port communication properties.
   * If one hasn't already been explicitly defined, it will attempt to use
   * getParent() and cast it to a BISerialHelperParent.
   */
  public BISerialHelperParent getSerialHelperParent()
  {
    if (host != null)
      return host;
    else
      return (BISerialHelperParent)getParent();
  }
  
//////////////////////////////////////////////////////////////
// BComponent
//////////////////////////////////////////////////////////////

  /**
  * BSerialHelper must reside under a BISerialHelperParent.
  */
  public boolean isParentLegal(BComponent parent)
  {
    return (parent instanceof BISerialHelperParent);
  }

//////////////////////////////////////////////////////////////
// Serial Helper implementation
//////////////////////////////////////////////////////////////

  /**
   * Opens the serial port and sets the port parameters.
   * Returns the serial port as a BISerialPort.
   * 
   * @param owner - The name of the owner to set for the serial port.
   */
  public BISerialPort open(String owner)
    throws Exception
  {        
    BISerialService platSvc = (BISerialService) Sys.getService(BISerialService.TYPE);
    ((BComponent)platSvc).lease();  // create a subscription to force the platform service to lazy-init

    try
    {
      port = platSvc.openPort(getPortName(), owner);
    }
    catch (PortNotFoundException notPort)
    {
      String errMsg = "'" + getPortName() + "' not a valid comm port."; 
      log.log(Level.SEVERE, errMsg, notPort);
      openPortFailure = true;
      computeStatus();      
      if (port != null) port.close();
      throw notPort;
    }              
    catch (PortDeniedException deniedPort)
    {
      String errMsg = "Denied opening comm port '" + getPortName() + "'"; 
      log.log(Level.SEVERE, errMsg, deniedPort);
      openPortFailure = true;
      computeStatus();      
      if (port != null) port.close();
      throw deniedPort;
    }    
    catch (Exception e)
    {
      String errMsg = "Exception opening comm port '" + getPortName() + "'"; 
      log.log(Level.SEVERE, errMsg, e);
      openPortFailure = true;
      computeStatus();      
      if (port != null) port.close();
      throw e;
    }
    openPortFailure = false;            

    //
    // Set comm parameters
    try
    {
      port.setSerialPortParams(getBaudRate(),
                               getDataBits(),
                               getStopBits(),
                               getParity());
    }
    catch(UnsupportedOperationException unsupported)
    {
      String errMsg = "Unsupported comm parameter for " + getPortName(); //id.getCurrentOwner();      
      log.log(Level.SEVERE, errMsg, unsupported);
      portParamFailure = true;
      computeStatus();
      if (port != null) port.close();
      throw unsupported;
    }
    catch(Exception e)
    {
      String errMsg = "Exception setting comm parameters for " + getPortName(); //id.getCurrentOwner();      
      log.log(Level.SEVERE, errMsg, e);
      portParamFailure = true;
      computeStatus();
      if (port != null) port.close();
      throw e;
    }
    portParamFailure = false;
    
    try
    {
      port.setFlowControlMode(getFlowControlMode());
    }
    catch(UnsupportedOperationException unsupported)
    {
      String errMsg = "Unsupported comm parameter (flow control mode) for " + getPortName(); //id.getCurrentOwner();
      log.log(Level.SEVERE, errMsg, unsupported);
      flowControlFailure = true;
      computeStatus();
      if (port != null) port.close();
      throw unsupported;
    }
    catch(Exception e)
    {
      String errMsg = "Exception setting flow control mode for " + getPortName(); //id.getCurrentOwner();
      log.log(Level.SEVERE, errMsg, e);
      flowControlFailure = true;
      computeStatus();
      if (port != null) port.close();
      throw e;
    }
    flowControlFailure = false;
    
    computeStatus();
    return port;
  }


  /**
  * Check to see if a parameter changed that requires a 
  * reopen of the serial port.  If so, tell the parent to 
  * restart the serial port.
  * Also tells the parent to invoke its changed method.
  */
  public void changed(Property property, Context context) 
  {
    //Always first notify the parent of the change!!
    if (getSerialHelperParent() != null)
      getSerialHelperParent().changed(property, context);
    
    if (!isRunning()) return;  // Don't do anything if not running
    
    if (property.equals(portName))
    {
      if (getPortName().equals(noPort))
      {  // 'none' is selected
        computeStatus();
      }
      // tell the parent it's time to restart the port      
      getSerialHelperParent().reopenPort();
      return;
    }
    else if ( (property.equals(dataBits)) || (property.equals(stopBits)) ||
              (property.equals(parity)) || (property.equals(baudRate)) )
    {
      if(port != null)
        updatePortParms(getBaudRate(),
                      getDataBits(),
                      getStopBits(),
                      getParity());
    }
    else if (property.equals(flowControlMode))
    {
      try
      {
        if(port != null)
          port.setFlowControlMode(getFlowControlMode());
      }
      catch(UnsupportedOperationException unsupported)
      {
        log.log(Level.SEVERE, "Unsupported comm parameter", unsupported);
        flowControlFailure = true;
        computeStatus();
        throw new RuntimeException("Unsupported comm parameter");
      }
      flowControlFailure = false;
      computeStatus();    
    }
    else
      super.changed(property, context);
  }

//////////////////////////////////////////////////////////////
// Convenience
//////////////////////////////////////////////////////////////

  /**
   * Updates the serial communication parameters.
   *
   * @param baud - The new baud rate
   * @param dBits - The new number of data bits
   * @param sBits - The new number of stop bits
   * @param parity - The new parity
   */
  private void updatePortParms(BBaudRate baud, 
                               BSerialDataBits dBits, 
                               BSerialStopBits sBits,
                               BSerialParity parity)
  {
    try
    {
      port.setSerialPortParams(baud,  dBits, sBits, parity);
    }
    catch(UnsupportedOperationException unsupported)
    {
      log.log(Level.SEVERE, "Unsupported comm parameter", unsupported);
      portParamFailure = true;
      computeStatus();
      throw new RuntimeException("Unsupported comm parameter");
    }
    portParamFailure = false;
    computeStatus();
  }
  
  /** 
   * Determine the status of the serial port and set the SerialHelper's
   * status as appropriate.  Fault if there was a problem
   * with the serial port.  Down if the serial port isn't
   * properly configured for use (i.e. 'none').  Ok if the serial port is
   * set up properly.
   */
  private void computeStatus()
  {
    if (getPortName().equals(noPort)) // if no port configured, set down
    {
      setStatus(BStatus.down);
      return;
    }
    
    if (openPortFailure || portParamFailure || flowControlFailure)
      setStatus(BStatus.fault);
    else
      setStatus(BStatus.ok);
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getPortName());
    sb.append(", ");
    sb.append(getBaudRate().getOrdinal());
    sb.append(", ");
    sb.append(getDataBits().getOrdinal());
    sb.append(", ");
    sb.append(getStopBits().getOrdinal());
    sb.append(", ");
    sb.append(getParity());
    return sb.toString();
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("connection.png");

//////////////////////////////////////////////////////////////
// Attributes
//////////////////////////////////////////////////////////////
  public static Logger log = Logger.getLogger("SerialHelper");
  private BISerialHelperParent host;
  private BISerialPort port = null; // FIXX - what access should this have??
  
  private boolean openPortFailure = false;
  private boolean portParamFailure = false;
  private boolean flowControlFailure = false;  
}
