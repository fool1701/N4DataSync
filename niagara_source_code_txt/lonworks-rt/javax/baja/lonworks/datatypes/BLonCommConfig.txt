/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import javax.baja.lonworks.BLonNetwork;
import javax.baja.lonworks.LonComm;
import javax.baja.lonworks.enums.BLonReceiveTimer;
import javax.baja.lonworks.enums.BLonRepeatTimer;
import javax.baja.lonworks.io.LonLinkLayer;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.lonworks.loncomm.NLonLinkLayer;

/**
 *   BLonCommConfig specifies the properties need to configure the
 * communication stack for a single lonworks connection.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  19 Feb 02
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 The name of lonworks system device to be accessed by the parent BLonNetwork.
 */
@NiagaraProperty(
  name = "deviceName",
  type = "String",
  defaultValue = "LON1"
)
/*
 The debug flag to enable/disable debug mode
 simple boolean.
 */
@NiagaraProperty(
  name = "linkDebug",
  type = "boolean",
  defaultValue = "false"
)
/*
 Specifies the time interval between repetitions of
 an outgoing message using repeat service.
 */
@NiagaraProperty(
  name = "repeatTimer",
  type = "BLonRepeatTimer",
  defaultValue = "BLonRepeatTimer.milliSec96"
)
/*
 Specifies the time interval in which messages with
 the same tag will be considered duplicates.
 */
@NiagaraProperty(
  name = "receiveTimer",
  type = "BLonReceiveTimer",
  defaultValue = "BLonReceiveTimer.milliSec384"
)
/*
 Specifies the time interval between retries of
 an outgoing message using req/resp or acknowledged service.
 */
@NiagaraProperty(
  name = "transmitTimer",
  type = "BLonRepeatTimer",
  defaultValue = "BLonRepeatTimer.milliSec96"
)
/*
 Specifies the number of retries for repeat,
 req/resp or acknowledged service.
 */
@NiagaraProperty(
  name = "retryCount",
  type = "int",
  defaultValue = "3"
)
public class BLonCommConfig
  extends BStruct
{  
  /**
  
   */
        

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BLonCommConfig(669966460)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "deviceName"

  /**
   * Slot for the {@code deviceName} property.
   * The name of lonworks system device to be accessed by the parent BLonNetwork.
   * @see #getDeviceName
   * @see #setDeviceName
   */
  public static final Property deviceName = newProperty(0, "LON1", null);

  /**
   * Get the {@code deviceName} property.
   * The name of lonworks system device to be accessed by the parent BLonNetwork.
   * @see #deviceName
   */
  public String getDeviceName() { return getString(deviceName); }

  /**
   * Set the {@code deviceName} property.
   * The name of lonworks system device to be accessed by the parent BLonNetwork.
   * @see #deviceName
   */
  public void setDeviceName(String v) { setString(deviceName, v, null); }

  //endregion Property "deviceName"

  //region Property "linkDebug"

  /**
   * Slot for the {@code linkDebug} property.
   * The debug flag to enable/disable debug mode
   * simple boolean.
   * @see #getLinkDebug
   * @see #setLinkDebug
   */
  public static final Property linkDebug = newProperty(0, false, null);

  /**
   * Get the {@code linkDebug} property.
   * The debug flag to enable/disable debug mode
   * simple boolean.
   * @see #linkDebug
   */
  public boolean getLinkDebug() { return getBoolean(linkDebug); }

  /**
   * Set the {@code linkDebug} property.
   * The debug flag to enable/disable debug mode
   * simple boolean.
   * @see #linkDebug
   */
  public void setLinkDebug(boolean v) { setBoolean(linkDebug, v, null); }

  //endregion Property "linkDebug"

  //region Property "repeatTimer"

  /**
   * Slot for the {@code repeatTimer} property.
   * Specifies the time interval between repetitions of
   * an outgoing message using repeat service.
   * @see #getRepeatTimer
   * @see #setRepeatTimer
   */
  public static final Property repeatTimer = newProperty(0, BLonRepeatTimer.milliSec96, null);

  /**
   * Get the {@code repeatTimer} property.
   * Specifies the time interval between repetitions of
   * an outgoing message using repeat service.
   * @see #repeatTimer
   */
  public BLonRepeatTimer getRepeatTimer() { return (BLonRepeatTimer)get(repeatTimer); }

  /**
   * Set the {@code repeatTimer} property.
   * Specifies the time interval between repetitions of
   * an outgoing message using repeat service.
   * @see #repeatTimer
   */
  public void setRepeatTimer(BLonRepeatTimer v) { set(repeatTimer, v, null); }

  //endregion Property "repeatTimer"

  //region Property "receiveTimer"

  /**
   * Slot for the {@code receiveTimer} property.
   * Specifies the time interval in which messages with
   * the same tag will be considered duplicates.
   * @see #getReceiveTimer
   * @see #setReceiveTimer
   */
  public static final Property receiveTimer = newProperty(0, BLonReceiveTimer.milliSec384, null);

  /**
   * Get the {@code receiveTimer} property.
   * Specifies the time interval in which messages with
   * the same tag will be considered duplicates.
   * @see #receiveTimer
   */
  public BLonReceiveTimer getReceiveTimer() { return (BLonReceiveTimer)get(receiveTimer); }

  /**
   * Set the {@code receiveTimer} property.
   * Specifies the time interval in which messages with
   * the same tag will be considered duplicates.
   * @see #receiveTimer
   */
  public void setReceiveTimer(BLonReceiveTimer v) { set(receiveTimer, v, null); }

  //endregion Property "receiveTimer"

  //region Property "transmitTimer"

  /**
   * Slot for the {@code transmitTimer} property.
   * Specifies the time interval between retries of
   * an outgoing message using req/resp or acknowledged service.
   * @see #getTransmitTimer
   * @see #setTransmitTimer
   */
  public static final Property transmitTimer = newProperty(0, BLonRepeatTimer.milliSec96, null);

  /**
   * Get the {@code transmitTimer} property.
   * Specifies the time interval between retries of
   * an outgoing message using req/resp or acknowledged service.
   * @see #transmitTimer
   */
  public BLonRepeatTimer getTransmitTimer() { return (BLonRepeatTimer)get(transmitTimer); }

  /**
   * Set the {@code transmitTimer} property.
   * Specifies the time interval between retries of
   * an outgoing message using req/resp or acknowledged service.
   * @see #transmitTimer
   */
  public void setTransmitTimer(BLonRepeatTimer v) { set(transmitTimer, v, null); }

  //endregion Property "transmitTimer"

  //region Property "retryCount"

  /**
   * Slot for the {@code retryCount} property.
   * Specifies the number of retries for repeat,
   * req/resp or acknowledged service.
   * @see #getRetryCount
   * @see #setRetryCount
   */
  public static final Property retryCount = newProperty(0, 3, null);

  /**
   * Get the {@code retryCount} property.
   * Specifies the number of retries for repeat,
   * req/resp or acknowledged service.
   * @see #retryCount
   */
  public int getRetryCount() { return getInt(retryCount); }

  /**
   * Set the {@code retryCount} property.
   * Specifies the number of retries for repeat,
   * req/resp or acknowledged service.
   * @see #retryCount
   */
  public void setRetryCount(int v) { setInt(retryCount, v, null); }

  //endregion Property "retryCount"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonCommConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////  

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("commConfig.png");

  /** */
  public LonLinkLayer makeLonLinkLayer(LonComm lc, BLonNetwork lon) 
    { return new NLonLinkLayer(lc, lon);  }

}
