/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio;

import javax.baja.driver.loadable.BDownloadParameters;
import javax.baja.driver.loadable.BUploadParameters;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BBlob;
import javax.baja.sys.BFacets;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.components.BIoStatus;
import com.tridium.nrio.enums.BNrioDeviceTypeEnum;
import com.tridium.nrio.points.BNrioPointDeviceExt;


/**
 * BNrioDualDevice represents a device
 *
 * @author    Andy Saunders       
 * @creation  21 Jan 02
 * @version   $Revision$ $Date: 8/29/2005 10:21:10 AM$
 * @since     Baja 1.0
 */

@NiagaraType
@NiagaraProperty(
  name = "address",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE,
  facets = {
    @Facet(name = "BFacets.MIN", value = "0"),
    @Facet(name = "BFacets.MAX", value = "15")
  },
  override = true
)
@NiagaraProperty(
  name = "deviceType",
  type = "BNrioDeviceTypeEnum",
  defaultValue = "BNrioDeviceTypeEnum.none",
  flags = Flags.READONLY,
  override = true
)
@NiagaraProperty(
  name = "uid",
  type = "BBlob",
  defaultValue = "BBlob.make( defaultUid )",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE,
  facets = @Facet(name = "BFacets.FIELD_EDITOR", value = "BString.make(\"nrio:FlexBlobFE\")"),
  override = true
)
@NiagaraProperty(
  name = "installedVersion",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE,
  override = true
)
@NiagaraProperty(
  name = "availableVersion",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY,
  override = true
)
@NiagaraProperty(
  name = "winkOutput",
  type = "int",
  defaultValue = "1",
  facets = {
    @Facet(name = "BFacets.MIN", value = "1"),
    @Facet(name = "BFacets.MAX", value = "8")
  },
  override = true
)
@NiagaraProperty(
  name = "winkDuration",
  type = "BRelTime",
  defaultValue = "BRelTime.makeSeconds(10)",
  facets = {
    @Facet(name = "BFacets.MAX", value = "BRelTime.makeSeconds(60)"),
    @Facet(name = "BFacets.MIN", value = "BRelTime.makeSeconds(5)")
  },
  override = true
)
@NiagaraProperty(
  name = "points",
  type = "BNrioPointDeviceExt",
  defaultValue = "new BNrioPointDeviceExt()",
  override = true
)
@NiagaraProperty(
  name = "ioStatus",
  type = "BStruct",
  defaultValue = "new BIoStatus()",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE,
  override = true
)
@NiagaraProperty(
  name = "io34Sec",
  type = "BNrioDevice",
  defaultValue = "new BNrioDevice()"
)
@NiagaraAction(
  name = "upload",
  parameterType = "BUploadParameters",
  defaultValue = "new BUploadParameters()",
  flags = Flags.ASYNC | Flags.HIDDEN,
  override = true
)
@NiagaraAction(
  name = "download",
  parameterType = "BDownloadParameters",
  defaultValue = "new BDownloadParameters()",
  flags = Flags.ASYNC | Flags.HIDDEN,
  override = true
)
@NiagaraAction(
  name = "winkDevice",
  flags = Flags.HIDDEN,
  override = true
)
@NiagaraAction(
  name = "winkTimeout",
  flags = Flags.HIDDEN,
  override = true
)
@NiagaraAction(
  name = "winkCancel",
  flags = Flags.HIDDEN,
  override = true
)
@NiagaraAction(
  name = "enablePolling",
  flags = Flags.HIDDEN,
  override = true
)
@NiagaraAction(
  name = "disablePolling",
  flags = Flags.HIDDEN,
  override = true
)
@NiagaraAction(
  name = "setPingOk",
  flags = Flags.HIDDEN,
  override = true
)
@NiagaraAction(
  name = "setAddressAndPing",
  flags = Flags.HIDDEN,
  override = true
)
@NiagaraAction(
  name = "dumpIoMap",
  flags = Flags.HIDDEN,
  override = true
)
@NiagaraAction(
  name = "readInfoMemory",
  flags = Flags.HIDDEN,
  override = true
)
@NiagaraAction(
  name = "clearInfoMemory",
  flags = Flags.HIDDEN,
  override = true
)
public class BNrioDualDevice
  extends BNrioDevice
{
  static byte[] defaultUid = { (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, };

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.BNrioDualDevice(683007198)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "address"

  /**
   * Slot for the {@code address} property.
   * @see #getAddress
   * @see #setAddress
   */
  public static final Property address = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, 0, BFacets.make(BFacets.make(BFacets.MIN, 0), BFacets.make(BFacets.MAX, 15)));

  //endregion Property "address"

  //region Property "deviceType"

  /**
   * Slot for the {@code deviceType} property.
   * @see #getDeviceType
   * @see #setDeviceType
   */
  public static final Property deviceType = newProperty(Flags.READONLY, BNrioDeviceTypeEnum.none, null);

  //endregion Property "deviceType"

  //region Property "uid"

  /**
   * Slot for the {@code uid} property.
   * @see #getUid
   * @see #setUid
   */
  public static final Property uid = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, BBlob.make( defaultUid ), BFacets.make(BFacets.FIELD_EDITOR, BString.make("nrio:FlexBlobFE")));

  //endregion Property "uid"

  //region Property "installedVersion"

  /**
   * Slot for the {@code installedVersion} property.
   * @see #getInstalledVersion
   * @see #setInstalledVersion
   */
  public static final Property installedVersion = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, "", null);

  //endregion Property "installedVersion"

  //region Property "availableVersion"

  /**
   * Slot for the {@code availableVersion} property.
   * @see #getAvailableVersion
   * @see #setAvailableVersion
   */
  public static final Property availableVersion = newProperty(Flags.READONLY, "", null);

  //endregion Property "availableVersion"

  //region Property "winkOutput"

  /**
   * Slot for the {@code winkOutput} property.
   * @see #getWinkOutput
   * @see #setWinkOutput
   */
  public static final Property winkOutput = newProperty(0, 1, BFacets.make(BFacets.make(BFacets.MIN, 1), BFacets.make(BFacets.MAX, 8)));

  //endregion Property "winkOutput"

  //region Property "winkDuration"

  /**
   * Slot for the {@code winkDuration} property.
   * @see #getWinkDuration
   * @see #setWinkDuration
   */
  public static final Property winkDuration = newProperty(0, BRelTime.makeSeconds(10), BFacets.make(BFacets.make(BFacets.MAX, BRelTime.makeSeconds(60)), BFacets.make(BFacets.MIN, BRelTime.makeSeconds(5))));

  //endregion Property "winkDuration"

  //region Property "points"

  /**
   * Slot for the {@code points} property.
   * @see #getPoints
   * @see #setPoints
   */
  public static final Property points = newProperty(0, new BNrioPointDeviceExt(), null);

  //endregion Property "points"

  //region Property "ioStatus"

  /**
   * Slot for the {@code ioStatus} property.
   * @see #getIoStatus
   * @see #setIoStatus
   */
  public static final Property ioStatus = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, new BIoStatus(), null);

  //endregion Property "ioStatus"

  //region Property "io34Sec"

  /**
   * Slot for the {@code io34Sec} property.
   * @see #getIo34Sec
   * @see #setIo34Sec
   */
  public static final Property io34Sec = newProperty(0, new BNrioDevice(), null);

  /**
   * Get the {@code io34Sec} property.
   * @see #io34Sec
   */
  public BNrioDevice getIo34Sec() { return (BNrioDevice)get(io34Sec); }

  /**
   * Set the {@code io34Sec} property.
   * @see #io34Sec
   */
  public void setIo34Sec(BNrioDevice v) { set(io34Sec, v, null); }

  //endregion Property "io34Sec"

  //region Action "upload"

  /**
   * Slot for the {@code upload} action.
   * @see #upload(BUploadParameters parameter)
   */
  public static final Action upload = newAction(Flags.ASYNC | Flags.HIDDEN, new BUploadParameters(), null);

  //endregion Action "upload"

  //region Action "download"

  /**
   * Slot for the {@code download} action.
   * @see #download(BDownloadParameters parameter)
   */
  public static final Action download = newAction(Flags.ASYNC | Flags.HIDDEN, new BDownloadParameters(), null);

  //endregion Action "download"

  //region Action "winkDevice"

  /**
   * Slot for the {@code winkDevice} action.
   * @see #winkDevice()
   */
  public static final Action winkDevice = newAction(Flags.HIDDEN, null);

  //endregion Action "winkDevice"

  //region Action "winkTimeout"

  /**
   * Slot for the {@code winkTimeout} action.
   * @see #winkTimeout()
   */
  public static final Action winkTimeout = newAction(Flags.HIDDEN, null);

  //endregion Action "winkTimeout"

  //region Action "winkCancel"

  /**
   * Slot for the {@code winkCancel} action.
   * @see #winkCancel()
   */
  public static final Action winkCancel = newAction(Flags.HIDDEN, null);

  //endregion Action "winkCancel"

  //region Action "enablePolling"

  /**
   * Slot for the {@code enablePolling} action.
   * @see #enablePolling()
   */
  public static final Action enablePolling = newAction(Flags.HIDDEN, null);

  //endregion Action "enablePolling"

  //region Action "disablePolling"

  /**
   * Slot for the {@code disablePolling} action.
   * @see #disablePolling()
   */
  public static final Action disablePolling = newAction(Flags.HIDDEN, null);

  //endregion Action "disablePolling"

  //region Action "setPingOk"

  /**
   * Slot for the {@code setPingOk} action.
   * @see #setPingOk()
   */
  public static final Action setPingOk = newAction(Flags.HIDDEN, null);

  //endregion Action "setPingOk"

  //region Action "setAddressAndPing"

  /**
   * Slot for the {@code setAddressAndPing} action.
   * @see #setAddressAndPing()
   */
  public static final Action setAddressAndPing = newAction(Flags.HIDDEN, null);

  //endregion Action "setAddressAndPing"

  //region Action "dumpIoMap"

  /**
   * Slot for the {@code dumpIoMap} action.
   * @see #dumpIoMap()
   */
  public static final Action dumpIoMap = newAction(Flags.HIDDEN, null);

  //endregion Action "dumpIoMap"

  //region Action "readInfoMemory"

  /**
   * Slot for the {@code readInfoMemory} action.
   * @see #readInfoMemory()
   */
  public static final Action readInfoMemory = newAction(Flags.HIDDEN, null);

  //endregion Action "readInfoMemory"

  //region Action "clearInfoMemory"

  /**
   * Slot for the {@code clearInfoMemory} action.
   * @see #clearInfoMemory()
   */
  public static final Action clearInfoMemory = newAction(Flags.HIDDEN, null);

  //endregion Action "clearInfoMemory"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioDualDevice.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////


}
