/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.components;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BBlob;
import javax.baja.sys.BFacets;
import javax.baja.sys.BString;
import javax.baja.sys.BStruct;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.messages.NrioInputStream;

/**
 * BNrio16Status - This is a structure to represent the base IO status from an NrioDevice.
 *
 * @author Andy Saunders on Nov 17, 2005
 * @since Niagara 3.0
 */
@NiagaraType
/*
 last io status message received
 */
@NiagaraProperty(
  name = "ioStatus",
  type = "BBlob",
  defaultValue = "BBlob.DEFAULT",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"nrio:FlexBlobFE\"))")
)
/*
 This is the raw value from the 1st sdi.
 */
@NiagaraProperty(
  name = "sdi1",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 This is the raw value from the 1st sdi.
 */
@NiagaraProperty(
  name = "sdi2",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 This is the raw value from the 1st sdi.
 */
@NiagaraProperty(
  name = "sdi3",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 This is the raw value from the 1st sdi.
 */
@NiagaraProperty(
  name = "sdi4",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 This is the raw value from the 1st sdi.
 */
@NiagaraProperty(
  name = "sdi5",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 This is the raw value from the 1st sdi.
 */
@NiagaraProperty(
  name = "sdi6",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 This is the raw value from the 1st sdi.
 */
@NiagaraProperty(
  name = "sdi7",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 This is the raw value from the 1st sdi.
 */
@NiagaraProperty(
  name = "sdi8",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 This is the first digital input
 */
@NiagaraProperty(
  name = "di1",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 This is the first digital input
 */
@NiagaraProperty(
  name = "di2",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 This is the first digital input
 */
@NiagaraProperty(
  name = "di3",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.TRANSIENT | Flags.READONLY
)
public class BIoStatus
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.components.BIoStatus(3476614430)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "ioStatus"

  /**
   * Slot for the {@code ioStatus} property.
   * last io status message received
   * @see #getIoStatus
   * @see #setIoStatus
   */
  public static final Property ioStatus = newProperty(Flags.TRANSIENT | Flags.READONLY, BBlob.DEFAULT, BFacets.make(BFacets.FIELD_EDITOR, BString.make("nrio:FlexBlobFE")));

  /**
   * Get the {@code ioStatus} property.
   * last io status message received
   * @see #ioStatus
   */
  public BBlob getIoStatus() { return (BBlob)get(ioStatus); }

  /**
   * Set the {@code ioStatus} property.
   * last io status message received
   * @see #ioStatus
   */
  public void setIoStatus(BBlob v) { set(ioStatus, v, null); }

  //endregion Property "ioStatus"

  //region Property "sdi1"

  /**
   * Slot for the {@code sdi1} property.
   * This is the raw value from the 1st sdi.
   * @see #getSdi1
   * @see #setSdi1
   */
  public static final Property sdi1 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, null);

  /**
   * Get the {@code sdi1} property.
   * This is the raw value from the 1st sdi.
   * @see #sdi1
   */
  public int getSdi1() { return getInt(sdi1); }

  /**
   * Set the {@code sdi1} property.
   * This is the raw value from the 1st sdi.
   * @see #sdi1
   */
  public void setSdi1(int v) { setInt(sdi1, v, null); }

  //endregion Property "sdi1"

  //region Property "sdi2"

  /**
   * Slot for the {@code sdi2} property.
   * This is the raw value from the 1st sdi.
   * @see #getSdi2
   * @see #setSdi2
   */
  public static final Property sdi2 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, null);

  /**
   * Get the {@code sdi2} property.
   * This is the raw value from the 1st sdi.
   * @see #sdi2
   */
  public int getSdi2() { return getInt(sdi2); }

  /**
   * Set the {@code sdi2} property.
   * This is the raw value from the 1st sdi.
   * @see #sdi2
   */
  public void setSdi2(int v) { setInt(sdi2, v, null); }

  //endregion Property "sdi2"

  //region Property "sdi3"

  /**
   * Slot for the {@code sdi3} property.
   * This is the raw value from the 1st sdi.
   * @see #getSdi3
   * @see #setSdi3
   */
  public static final Property sdi3 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, null);

  /**
   * Get the {@code sdi3} property.
   * This is the raw value from the 1st sdi.
   * @see #sdi3
   */
  public int getSdi3() { return getInt(sdi3); }

  /**
   * Set the {@code sdi3} property.
   * This is the raw value from the 1st sdi.
   * @see #sdi3
   */
  public void setSdi3(int v) { setInt(sdi3, v, null); }

  //endregion Property "sdi3"

  //region Property "sdi4"

  /**
   * Slot for the {@code sdi4} property.
   * This is the raw value from the 1st sdi.
   * @see #getSdi4
   * @see #setSdi4
   */
  public static final Property sdi4 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, null);

  /**
   * Get the {@code sdi4} property.
   * This is the raw value from the 1st sdi.
   * @see #sdi4
   */
  public int getSdi4() { return getInt(sdi4); }

  /**
   * Set the {@code sdi4} property.
   * This is the raw value from the 1st sdi.
   * @see #sdi4
   */
  public void setSdi4(int v) { setInt(sdi4, v, null); }

  //endregion Property "sdi4"

  //region Property "sdi5"

  /**
   * Slot for the {@code sdi5} property.
   * This is the raw value from the 1st sdi.
   * @see #getSdi5
   * @see #setSdi5
   */
  public static final Property sdi5 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, null);

  /**
   * Get the {@code sdi5} property.
   * This is the raw value from the 1st sdi.
   * @see #sdi5
   */
  public int getSdi5() { return getInt(sdi5); }

  /**
   * Set the {@code sdi5} property.
   * This is the raw value from the 1st sdi.
   * @see #sdi5
   */
  public void setSdi5(int v) { setInt(sdi5, v, null); }

  //endregion Property "sdi5"

  //region Property "sdi6"

  /**
   * Slot for the {@code sdi6} property.
   * This is the raw value from the 1st sdi.
   * @see #getSdi6
   * @see #setSdi6
   */
  public static final Property sdi6 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, null);

  /**
   * Get the {@code sdi6} property.
   * This is the raw value from the 1st sdi.
   * @see #sdi6
   */
  public int getSdi6() { return getInt(sdi6); }

  /**
   * Set the {@code sdi6} property.
   * This is the raw value from the 1st sdi.
   * @see #sdi6
   */
  public void setSdi6(int v) { setInt(sdi6, v, null); }

  //endregion Property "sdi6"

  //region Property "sdi7"

  /**
   * Slot for the {@code sdi7} property.
   * This is the raw value from the 1st sdi.
   * @see #getSdi7
   * @see #setSdi7
   */
  public static final Property sdi7 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, null);

  /**
   * Get the {@code sdi7} property.
   * This is the raw value from the 1st sdi.
   * @see #sdi7
   */
  public int getSdi7() { return getInt(sdi7); }

  /**
   * Set the {@code sdi7} property.
   * This is the raw value from the 1st sdi.
   * @see #sdi7
   */
  public void setSdi7(int v) { setInt(sdi7, v, null); }

  //endregion Property "sdi7"

  //region Property "sdi8"

  /**
   * Slot for the {@code sdi8} property.
   * This is the raw value from the 1st sdi.
   * @see #getSdi8
   * @see #setSdi8
   */
  public static final Property sdi8 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, null);

  /**
   * Get the {@code sdi8} property.
   * This is the raw value from the 1st sdi.
   * @see #sdi8
   */
  public int getSdi8() { return getInt(sdi8); }

  /**
   * Set the {@code sdi8} property.
   * This is the raw value from the 1st sdi.
   * @see #sdi8
   */
  public void setSdi8(int v) { setInt(sdi8, v, null); }

  //endregion Property "sdi8"

  //region Property "di1"

  /**
   * Slot for the {@code di1} property.
   * This is the first digital input
   * @see #getDi1
   * @see #setDi1
   */
  public static final Property di1 = newProperty(Flags.TRANSIENT | Flags.READONLY, false, null);

  /**
   * Get the {@code di1} property.
   * This is the first digital input
   * @see #di1
   */
  public boolean getDi1() { return getBoolean(di1); }

  /**
   * Set the {@code di1} property.
   * This is the first digital input
   * @see #di1
   */
  public void setDi1(boolean v) { setBoolean(di1, v, null); }

  //endregion Property "di1"

  //region Property "di2"

  /**
   * Slot for the {@code di2} property.
   * This is the first digital input
   * @see #getDi2
   * @see #setDi2
   */
  public static final Property di2 = newProperty(Flags.TRANSIENT | Flags.READONLY, false, null);

  /**
   * Get the {@code di2} property.
   * This is the first digital input
   * @see #di2
   */
  public boolean getDi2() { return getBoolean(di2); }

  /**
   * Set the {@code di2} property.
   * This is the first digital input
   * @see #di2
   */
  public void setDi2(boolean v) { setBoolean(di2, v, null); }

  //endregion Property "di2"

  //region Property "di3"

  /**
   * Slot for the {@code di3} property.
   * This is the first digital input
   * @see #getDi3
   * @see #setDi3
   */
  public static final Property di3 = newProperty(Flags.TRANSIENT | Flags.READONLY, false, null);

  /**
   * Get the {@code di3} property.
   * This is the first digital input
   * @see #di3
   */
  public boolean getDi3() { return getBoolean(di3); }

  /**
   * Set the {@code di3} property.
   * This is the first digital input
   * @see #di3
   */
  public void setDi3(boolean v) { setBoolean(di3, v, null); }

  //endregion Property "di3"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BIoStatus.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BIoStatus()
  {
  }

  public BIoStatus(byte[] data, int start, int length)
  {
    readIoStatus(data, start, length);
  }

  public byte[] copyBytes()
  {
    return getIoStatus().copyBytes();
  }

  public boolean readIoStatus(byte[] data, int start, int length)
  {
    setIoStatus(BBlob.make(data, start, length));

    NrioInputStream in = new NrioInputStream(data, start, length);
    int address = in.read() & 0x0ff;
    int msgLength = in.read() & 0x0ff;
    int type = in.read() & 0x0ff;
    int status = in.read() & 0x0ff;
    setSdi1(in.readSdi());
    setSdi2(in.readSdi());
    setSdi3(in.readSdi());
    setSdi4(in.readSdi());
    setSdi5(in.readSdi());
    setSdi6(in.readSdi());
    setSdi7(in.readSdi());
    setSdi8(in.readSdi());
    int ciData = in.readSdi();
    setDi1((ciData & 0x0001) == 0);
    setDi2((ciData & 0x0002) == 0);
    setDi3((ciData & 0x0004) == 0);
    return true;
  }
}
