/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.ndriver.datatypes;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.serial.BBaudRate;
import javax.baja.serial.BSerialBaudRate;
import javax.baja.serial.BSerialDataBits;
import javax.baja.serial.BSerialFlowControlMode;
import javax.baja.serial.BSerialParity;
import javax.baja.serial.BSerialStopBits;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.UnitDatabase;

import com.tridium.ndriver.comm.ICommListener;
import com.tridium.ndriver.comm.ILinkLayer;
import com.tridium.ndriver.comm.NComm;
import com.tridium.ndriver.comm.serial.SerialLinkLayer;

/**
 * Communications configuration parameters for serial comm stack.
 *
 * @author Robert A Adams
 * @creation Feb 16, 2011
 */
@NiagaraType
/*
 The serial comm port
 */
@NiagaraProperty(
  name = "portName",
  type = "String",
  defaultValue = "BSerialCommConfig.noPort",
  flags = Flags.DEFAULT_ON_CLONE
)
/*
 The baud rate to use for the serial port
 */
@NiagaraProperty(
  name = "baudRate",
  type = "BBaudRate",
  defaultValue = "BSerialBaudRate.baud9600"
)
/*
 The number of data bits to use for the serial port
 */
@NiagaraProperty(
  name = "dataBits",
  type = "BSerialDataBits",
  defaultValue = "BSerialDataBits.dataBits8"
)
/*
 The number of stop bits to use for the serial port
 */
@NiagaraProperty(
  name = "stopBits",
  type = "BSerialStopBits",
  defaultValue = "BSerialStopBits.stopBit1"
)
/*
 The parity to use for the serial port
 */
@NiagaraProperty(
  name = "parity",
  type = "BSerialParity",
  defaultValue = "BSerialParity.none"
)
/*
 The flow control mode to use for the serial port
 */
@NiagaraProperty(
  name = "flowControlMode",
  type = "BSerialFlowControlMode",
  defaultValue = "BSerialFlowControlMode.none"
)
/*
 the receive timeout setting for the serial port
 */
@NiagaraProperty(
  name = "receiveTimeout",
  type = "int",
  defaultValue = "0",
  facets = @Facet("BFacets.make(BFacets.MIN,BInteger.make(0),BFacets.MAX,BInteger.make(20000),BFacets.UNITS,UnitDatabase.getUnit(\"millisecond\"))")
)
/*
 Specifies the minimum amount of time to wait after a response message is
 received before sending the next request message.
 */
@NiagaraProperty(
  name = "interMessageDelay",
  type = "BRelTime",
  defaultValue = "BRelTime.make(0)",
  facets = @Facet("BFacets.make(BFacets.SHOW_MILLISECONDS, BBoolean.TRUE, BFacets.MIN, BRelTime.make(0), BFacets.MAX, BRelTime.SECOND)")
)
public class BSerialCommConfig
  extends BCommConfig
{
  public static final String noPort = "none";
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.datatypes.BSerialCommConfig(2264492063)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "portName"

  /**
   * Slot for the {@code portName} property.
   * The serial comm port
   * @see #getPortName
   * @see #setPortName
   */
  public static final Property portName = newProperty(Flags.DEFAULT_ON_CLONE, BSerialCommConfig.noPort, null);

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
   * The baud rate to use for the serial port
   * @see #getBaudRate
   * @see #setBaudRate
   */
  public static final Property baudRate = newProperty(0, BSerialBaudRate.baud9600, null);

  /**
   * Get the {@code baudRate} property.
   * The baud rate to use for the serial port
   * @see #baudRate
   */
  public BBaudRate getBaudRate() { return (BBaudRate)get(baudRate); }

  /**
   * Set the {@code baudRate} property.
   * The baud rate to use for the serial port
   * @see #baudRate
   */
  public void setBaudRate(BBaudRate v) { set(baudRate, v, null); }

  //endregion Property "baudRate"

  //region Property "dataBits"

  /**
   * Slot for the {@code dataBits} property.
   * The number of data bits to use for the serial port
   * @see #getDataBits
   * @see #setDataBits
   */
  public static final Property dataBits = newProperty(0, BSerialDataBits.dataBits8, null);

  /**
   * Get the {@code dataBits} property.
   * The number of data bits to use for the serial port
   * @see #dataBits
   */
  public BSerialDataBits getDataBits() { return (BSerialDataBits)get(dataBits); }

  /**
   * Set the {@code dataBits} property.
   * The number of data bits to use for the serial port
   * @see #dataBits
   */
  public void setDataBits(BSerialDataBits v) { set(dataBits, v, null); }

  //endregion Property "dataBits"

  //region Property "stopBits"

  /**
   * Slot for the {@code stopBits} property.
   * The number of stop bits to use for the serial port
   * @see #getStopBits
   * @see #setStopBits
   */
  public static final Property stopBits = newProperty(0, BSerialStopBits.stopBit1, null);

  /**
   * Get the {@code stopBits} property.
   * The number of stop bits to use for the serial port
   * @see #stopBits
   */
  public BSerialStopBits getStopBits() { return (BSerialStopBits)get(stopBits); }

  /**
   * Set the {@code stopBits} property.
   * The number of stop bits to use for the serial port
   * @see #stopBits
   */
  public void setStopBits(BSerialStopBits v) { set(stopBits, v, null); }

  //endregion Property "stopBits"

  //region Property "parity"

  /**
   * Slot for the {@code parity} property.
   * The parity to use for the serial port
   * @see #getParity
   * @see #setParity
   */
  public static final Property parity = newProperty(0, BSerialParity.none, null);

  /**
   * Get the {@code parity} property.
   * The parity to use for the serial port
   * @see #parity
   */
  public BSerialParity getParity() { return (BSerialParity)get(parity); }

  /**
   * Set the {@code parity} property.
   * The parity to use for the serial port
   * @see #parity
   */
  public void setParity(BSerialParity v) { set(parity, v, null); }

  //endregion Property "parity"

  //region Property "flowControlMode"

  /**
   * Slot for the {@code flowControlMode} property.
   * The flow control mode to use for the serial port
   * @see #getFlowControlMode
   * @see #setFlowControlMode
   */
  public static final Property flowControlMode = newProperty(0, BSerialFlowControlMode.none, null);

  /**
   * Get the {@code flowControlMode} property.
   * The flow control mode to use for the serial port
   * @see #flowControlMode
   */
  public BSerialFlowControlMode getFlowControlMode() { return (BSerialFlowControlMode)get(flowControlMode); }

  /**
   * Set the {@code flowControlMode} property.
   * The flow control mode to use for the serial port
   * @see #flowControlMode
   */
  public void setFlowControlMode(BSerialFlowControlMode v) { set(flowControlMode, v, null); }

  //endregion Property "flowControlMode"

  //region Property "receiveTimeout"

  /**
   * Slot for the {@code receiveTimeout} property.
   * the receive timeout setting for the serial port
   * @see #getReceiveTimeout
   * @see #setReceiveTimeout
   */
  public static final Property receiveTimeout = newProperty(0, 0, BFacets.make(BFacets.MIN,BInteger.make(0),BFacets.MAX,BInteger.make(20000),BFacets.UNITS,UnitDatabase.getUnit("millisecond")));

  /**
   * Get the {@code receiveTimeout} property.
   * the receive timeout setting for the serial port
   * @see #receiveTimeout
   */
  public int getReceiveTimeout() { return getInt(receiveTimeout); }

  /**
   * Set the {@code receiveTimeout} property.
   * the receive timeout setting for the serial port
   * @see #receiveTimeout
   */
  public void setReceiveTimeout(int v) { setInt(receiveTimeout, v, null); }

  //endregion Property "receiveTimeout"

  //region Property "interMessageDelay"

  /**
   * Slot for the {@code interMessageDelay} property.
   * Specifies the minimum amount of time to wait after a response message is
   * received before sending the next request message.
   * @see #getInterMessageDelay
   * @see #setInterMessageDelay
   */
  public static final Property interMessageDelay = newProperty(0, BRelTime.make(0), BFacets.make(BFacets.SHOW_MILLISECONDS, BBoolean.TRUE, BFacets.MIN, BRelTime.make(0), BFacets.MAX, BRelTime.SECOND));

  /**
   * Get the {@code interMessageDelay} property.
   * Specifies the minimum amount of time to wait after a response message is
   * received before sending the next request message.
   * @see #interMessageDelay
   */
  public BRelTime getInterMessageDelay() { return (BRelTime)get(interMessageDelay); }

  /**
   * Set the {@code interMessageDelay} property.
   * Specifies the minimum amount of time to wait after a response message is
   * received before sending the next request message.
   * @see #interMessageDelay
   */
  public void setInterMessageDelay(BRelTime v) { set(interMessageDelay, v, null); }

  //endregion Property "interMessageDelay"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSerialCommConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Empty constructor
   */
  public BSerialCommConfig()
  {
  }

  @Deprecated
  public BSerialCommConfig(ICommListener defaultListener)
  {
    super();
    setDefaultListener(defaultListener);
  }

  /**
   * @return new {@code SerialLinkLayer}
   */
  @Override
  public ILinkLayer makeLinkLayer(NComm comm)
  {
    return new SerialLinkLayer(comm, this);
  }

  /**
   * @return default resourcePrefix with ".Serial" appended
   */
  @Override
  public String getResourcePrefix()
  {
    return super.getResourcePrefix() + ".Serial";
  }
}
