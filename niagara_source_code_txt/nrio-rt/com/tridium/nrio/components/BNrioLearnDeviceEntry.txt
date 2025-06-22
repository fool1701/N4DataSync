/*
 * @copyright 2005 Tridium Inc.
 */
package com.tridium.nrio.components;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BBlob;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BInteger;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

import com.tridium.nrio.BNrioDevice;
import com.tridium.nrio.BNrioNetwork;
import com.tridium.nrio.enums.BNrioDeviceTypeEnum;

/**
 * BNrioLearnDeviceEntry - The learn devices job places instances of this component
 * under the learned devices folder on the network.
 *
 * @author    Andy Saunders
 * @creation  Nov 17, 2005
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 This is the unit number of the discovered access device
 */
@NiagaraProperty(
  name = "address",
  type = "int",
  defaultValue = "0"
)
/*
 This is the unit number of the discovered access device
 */
@NiagaraProperty(
  name = "uid",
  type = "BBlob",
  defaultValue = "BBlob.DEFAULT"
)
/*
 This is the type of this device
 */
@NiagaraProperty(
  name = "deviceType",
  type = "BNrioDeviceTypeEnum",
  defaultValue = "BNrioDeviceTypeEnum.none"
)
@NiagaraProperty(
  name = "version",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "usedBy",
  type = "String",
  defaultValue = ""
)
/*
 This is the address of the secondary device if a IO34 module
 */
@NiagaraProperty(
  name = "secAddr",
  type = "String",
  defaultValue = ""
)
public class BNrioLearnDeviceEntry
  extends BComponent
  implements Runnable
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.components.BNrioLearnDeviceEntry(4133632428)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "address"

  /**
   * Slot for the {@code address} property.
   * This is the unit number of the discovered access device
   * @see #getAddress
   * @see #setAddress
   */
  public static final Property address = newProperty(0, 0, null);

  /**
   * Get the {@code address} property.
   * This is the unit number of the discovered access device
   * @see #address
   */
  public int getAddress() { return getInt(address); }

  /**
   * Set the {@code address} property.
   * This is the unit number of the discovered access device
   * @see #address
   */
  public void setAddress(int v) { setInt(address, v, null); }

  //endregion Property "address"

  //region Property "uid"

  /**
   * Slot for the {@code uid} property.
   * This is the unit number of the discovered access device
   * @see #getUid
   * @see #setUid
   */
  public static final Property uid = newProperty(0, BBlob.DEFAULT, null);

  /**
   * Get the {@code uid} property.
   * This is the unit number of the discovered access device
   * @see #uid
   */
  public BBlob getUid() { return (BBlob)get(uid); }

  /**
   * Set the {@code uid} property.
   * This is the unit number of the discovered access device
   * @see #uid
   */
  public void setUid(BBlob v) { set(uid, v, null); }

  //endregion Property "uid"

  //region Property "deviceType"

  /**
   * Slot for the {@code deviceType} property.
   * This is the type of this device
   * @see #getDeviceType
   * @see #setDeviceType
   */
  public static final Property deviceType = newProperty(0, BNrioDeviceTypeEnum.none, null);

  /**
   * Get the {@code deviceType} property.
   * This is the type of this device
   * @see #deviceType
   */
  public BNrioDeviceTypeEnum getDeviceType() { return (BNrioDeviceTypeEnum)get(deviceType); }

  /**
   * Set the {@code deviceType} property.
   * This is the type of this device
   * @see #deviceType
   */
  public void setDeviceType(BNrioDeviceTypeEnum v) { set(deviceType, v, null); }

  //endregion Property "deviceType"

  //region Property "version"

  /**
   * Slot for the {@code version} property.
   * @see #getVersion
   * @see #setVersion
   */
  public static final Property version = newProperty(0, "", null);

  /**
   * Get the {@code version} property.
   * @see #version
   */
  public String getVersion() { return getString(version); }

  /**
   * Set the {@code version} property.
   * @see #version
   */
  public void setVersion(String v) { setString(version, v, null); }

  //endregion Property "version"

  //region Property "usedBy"

  /**
   * Slot for the {@code usedBy} property.
   * @see #getUsedBy
   * @see #setUsedBy
   */
  public static final Property usedBy = newProperty(0, "", null);

  /**
   * Get the {@code usedBy} property.
   * @see #usedBy
   */
  public String getUsedBy() { return getString(usedBy); }

  /**
   * Set the {@code usedBy} property.
   * @see #usedBy
   */
  public void setUsedBy(String v) { setString(usedBy, v, null); }

  //endregion Property "usedBy"

  //region Property "secAddr"

  /**
   * Slot for the {@code secAddr} property.
   * This is the address of the secondary device if a IO34 module
   * @see #getSecAddr
   * @see #setSecAddr
   */
  public static final Property secAddr = newProperty(0, "", null);

  /**
   * Get the {@code secAddr} property.
   * This is the address of the secondary device if a IO34 module
   * @see #secAddr
   */
  public String getSecAddr() { return getString(secAddr); }

  /**
   * Set the {@code secAddr} property.
   * This is the address of the secondary device if a IO34 module
   * @see #secAddr
   */
  public void setSecAddr(String v) { setString(secAddr, v, null); }

  //endregion Property "secAddr"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioLearnDeviceEntry.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BNrioLearnDeviceEntry(int address, BNrioDeviceTypeEnum type, byte[] uid, String version, String usedBy, int secAddr)
  {
    setAddress(address);
    setDeviceType(type);
    setUid(BBlob.make(uid));
    setVersion(version);
    setUsedBy(usedBy);
    if(secAddr > 0)
      setSecAddr(Integer.toString(secAddr));
  }

  public BNrioLearnDeviceEntry(int address, BNrioDeviceTypeEnum type, byte[] uid, String version, String usedBy)
  {
    setAddress(address);
    setDeviceType(type);
    setUid(BBlob.make(uid));
    setVersion(version);
    setUsedBy(usedBy);
  }

  public BNrioLearnDeviceEntry(int address, BNrioDeviceTypeEnum type, byte[] uid)
  {
    setAddress(address);
    setDeviceType(type);
    setUid(BBlob.make(uid));
  }
  public BNrioLearnDeviceEntry(){}

  public String getDefaultAddAddress()
  {
    switch (getDeviceType().getOrdinal())
    {
      case BNrioDeviceTypeEnum.IO_16:
        return getDeviceType().getTag() + "_" + getAddress();
      case BNrioDeviceTypeEnum.IO_34:
        return getDeviceType().getTag() + "_" + getAddress() + "_" + getSecAddr();
      default:
        return getDeviceType().getTag() + "_" + getAddress();
    }
  }

  public boolean isMatchable(BComponent device)
  {
    if(!(device instanceof BNrioDevice) )
      return false;
    BNrioDevice dbDevice = (BNrioDevice)device;
    if( !(dbDevice.getDeviceType().isMatchable(getDeviceType())) )
      return false;
    //System.out.println("********** Learn device entry.isMatchable(): " + device.getName() + " isDown() = " + dbDevice.isDown());
    return dbDevice.isDown() ||
           dbDevice.getAddress() == 0;
  }

  public int getSecAddrInt()
  {
    String secAddr = getSecAddr();
    int addr = -1;
    try{addr = Integer.parseInt(secAddr);} catch(Exception ignore) {}
    return addr;
  }

  public boolean isWinkActive()
  {
    return winkActive;
  }
  
//  public String getWinkCmd()
//  {
//    if(winkActive)
//      return "winkCancel";
//    return "wink";
//  }
//  
  public void doWinkDevice(BNrioNetwork network, BBoolean value)
  {
    //System.out.println("doWinkDevice on device: " + getAddress());
    this.network = network;
    if(!winkActive)
    {
      winkThread = new Thread(this);
      winkValue = 1;
      winkActive = true;
      network.enableWinking(this);
      winkThread.start();
      this.setUsedBy(lex.get("winking"));
    }
    else
    {
      winkActive = false;
      network.disableWinking(this);
      this.setUsedBy("");

    }

  }
  
  public void stopWink()
  {
    if(winkActive)
    {
      network.disableWinking(this);
    }
    winkActive = false;
    if(getUsedBy().equals(lex.get("winking")))
      setUsedBy("");
  }
  
  public void run()
  {
    int count = 0;
    while(winkActive)
    {
      if(winkValue == 0)
        winkValue = 1;
      else
        winkValue = 0;
      //System.out.println("winking on device: " + getAddress() + " with value: " + winkValue);
      network.winkDevice(BInteger.make( (getAddress() << 8) | winkValue ));
      try{ Thread.sleep(WINK_CYCLE); }
      catch(Exception ignore) {}
      winkActive = (++count <= WINK_COUNT);
    }
    setUsedBy("");
    network.disableWinking(this);
  }

  public  static Lexicon lex = Lexicon.make(BNrioLearnDeviceEntry.class);

  private static final int WINK_COUNT = 20;   // number of wink state changes (10 sec)
  private static final long WINK_CYCLE = 500; // wink state change time in ms

  private BNrioNetwork network;
  private Thread winkThread;
  private static boolean winkActive = false;
  private int winkValue = 0;
 

}
