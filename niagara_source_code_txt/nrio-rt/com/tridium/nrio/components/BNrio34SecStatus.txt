/*
 * @copyright 2016 Tridium Inc.
 */
package com.tridium.nrio.components;

import javax.baja.log.Log;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BBlob;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BString;
import javax.baja.sys.BStruct;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.messages.NrioInputStream;

/**
 * BNrio34SecStatus - This is a structure to represent the base IO status from the GpIoModule.
 *
 * @author    Andy Saunders
 * @creation  Oct 9, 2016
 */

@NiagaraType
@NiagaraProperty(
  name = "ioStatus",
  type = "BBlob",
  defaultValue = "BBlob.DEFAULT",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.FIELD_EDITOR", value = "BString.make(\"nrio:FlexBlobFE\")")
)
@NiagaraProperty(
  name = "activeAiMap",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "valueAI1",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "valueAI2",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "valueAI3",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "valueAI4",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "valueAI5",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "valueAI6",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "valueAI7",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "valueAI8",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "activeDiMap",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "valueHighSpeedDIs",
  type = "long",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "countHighSpeedDI1",
  type = "long",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "countHighSpeedDI2",
  type = "long",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "countHighSpeedDI3",
  type = "long",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "countHighSpeedDI4",
  type = "long",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "countHighSpeedDI5",
  type = "long",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "countHighSpeedDI6",
  type = "long",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "countHighSpeedDI7",
  type = "long",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "countHighSpeedDI8",
  type = "long",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "valueOverrides",
  type = "int",
  defaultValue = "0",
  flags = Flags.TRANSIENT | Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "totalCount1",
  type = "long",
  defaultValue = "0",
  flags = Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "totalCount2",
  type = "long",
  defaultValue = "0",
  flags = Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "totalCount3",
  type = "long",
  defaultValue = "0",
  flags = Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "totalCount4",
  type = "long",
  defaultValue = "0",
  flags = Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "totalCount5",
  type = "long",
  defaultValue = "0",
  flags = Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "totalCount6",
  type = "long",
  defaultValue = "0",
  flags = Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "totalCount7",
  type = "long",
  defaultValue = "0",
  flags = Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)
@NiagaraProperty(
  name = "totalCount8",
  type = "long",
  defaultValue = "0",
  flags = Flags.READONLY,
  facets = @Facet(name = "BFacets.RADIX", value = "16")
)





public class BNrio34SecStatus
  extends BStruct
  implements BINrioIoStatus
{
  private static BFacets blobFeFacets = BFacets.make(BFacets.FIELD_EDITOR, BString.make("nrio:FlexBlobFE"));
  private static BFacets plusReverse = BFacets.make(blobFeFacets, "reverse", BBoolean.make(true));
  private static BFacets radex16 = BFacets.make(BFacets.RADIX, BInteger.make(16));
  private static int transientReadonly = Flags.TRANSIENT | Flags.READONLY;



//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.components.BNrio34SecStatus(1749282132)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "ioStatus"

  /**
   * Slot for the {@code ioStatus} property.
   * @see #getIoStatus
   * @see #setIoStatus
   */
  public static final Property ioStatus = newProperty(Flags.TRANSIENT | Flags.READONLY, BBlob.DEFAULT, BFacets.make(BFacets.FIELD_EDITOR, BString.make("nrio:FlexBlobFE")));

  /**
   * Get the {@code ioStatus} property.
   * @see #ioStatus
   */
  public BBlob getIoStatus() { return (BBlob)get(ioStatus); }

  /**
   * Set the {@code ioStatus} property.
   * @see #ioStatus
   */
  public void setIoStatus(BBlob v) { set(ioStatus, v, null); }

  //endregion Property "ioStatus"

  //region Property "activeAiMap"

  /**
   * Slot for the {@code activeAiMap} property.
   * @see #getActiveAiMap
   * @see #setActiveAiMap
   */
  public static final Property activeAiMap = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code activeAiMap} property.
   * @see #activeAiMap
   */
  public int getActiveAiMap() { return getInt(activeAiMap); }

  /**
   * Set the {@code activeAiMap} property.
   * @see #activeAiMap
   */
  public void setActiveAiMap(int v) { setInt(activeAiMap, v, null); }

  //endregion Property "activeAiMap"

  //region Property "valueAI1"

  /**
   * Slot for the {@code valueAI1} property.
   * @see #getValueAI1
   * @see #setValueAI1
   */
  public static final Property valueAI1 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code valueAI1} property.
   * @see #valueAI1
   */
  public int getValueAI1() { return getInt(valueAI1); }

  /**
   * Set the {@code valueAI1} property.
   * @see #valueAI1
   */
  public void setValueAI1(int v) { setInt(valueAI1, v, null); }

  //endregion Property "valueAI1"

  //region Property "valueAI2"

  /**
   * Slot for the {@code valueAI2} property.
   * @see #getValueAI2
   * @see #setValueAI2
   */
  public static final Property valueAI2 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code valueAI2} property.
   * @see #valueAI2
   */
  public int getValueAI2() { return getInt(valueAI2); }

  /**
   * Set the {@code valueAI2} property.
   * @see #valueAI2
   */
  public void setValueAI2(int v) { setInt(valueAI2, v, null); }

  //endregion Property "valueAI2"

  //region Property "valueAI3"

  /**
   * Slot for the {@code valueAI3} property.
   * @see #getValueAI3
   * @see #setValueAI3
   */
  public static final Property valueAI3 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code valueAI3} property.
   * @see #valueAI3
   */
  public int getValueAI3() { return getInt(valueAI3); }

  /**
   * Set the {@code valueAI3} property.
   * @see #valueAI3
   */
  public void setValueAI3(int v) { setInt(valueAI3, v, null); }

  //endregion Property "valueAI3"

  //region Property "valueAI4"

  /**
   * Slot for the {@code valueAI4} property.
   * @see #getValueAI4
   * @see #setValueAI4
   */
  public static final Property valueAI4 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code valueAI4} property.
   * @see #valueAI4
   */
  public int getValueAI4() { return getInt(valueAI4); }

  /**
   * Set the {@code valueAI4} property.
   * @see #valueAI4
   */
  public void setValueAI4(int v) { setInt(valueAI4, v, null); }

  //endregion Property "valueAI4"

  //region Property "valueAI5"

  /**
   * Slot for the {@code valueAI5} property.
   * @see #getValueAI5
   * @see #setValueAI5
   */
  public static final Property valueAI5 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code valueAI5} property.
   * @see #valueAI5
   */
  public int getValueAI5() { return getInt(valueAI5); }

  /**
   * Set the {@code valueAI5} property.
   * @see #valueAI5
   */
  public void setValueAI5(int v) { setInt(valueAI5, v, null); }

  //endregion Property "valueAI5"

  //region Property "valueAI6"

  /**
   * Slot for the {@code valueAI6} property.
   * @see #getValueAI6
   * @see #setValueAI6
   */
  public static final Property valueAI6 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code valueAI6} property.
   * @see #valueAI6
   */
  public int getValueAI6() { return getInt(valueAI6); }

  /**
   * Set the {@code valueAI6} property.
   * @see #valueAI6
   */
  public void setValueAI6(int v) { setInt(valueAI6, v, null); }

  //endregion Property "valueAI6"

  //region Property "valueAI7"

  /**
   * Slot for the {@code valueAI7} property.
   * @see #getValueAI7
   * @see #setValueAI7
   */
  public static final Property valueAI7 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code valueAI7} property.
   * @see #valueAI7
   */
  public int getValueAI7() { return getInt(valueAI7); }

  /**
   * Set the {@code valueAI7} property.
   * @see #valueAI7
   */
  public void setValueAI7(int v) { setInt(valueAI7, v, null); }

  //endregion Property "valueAI7"

  //region Property "valueAI8"

  /**
   * Slot for the {@code valueAI8} property.
   * @see #getValueAI8
   * @see #setValueAI8
   */
  public static final Property valueAI8 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code valueAI8} property.
   * @see #valueAI8
   */
  public int getValueAI8() { return getInt(valueAI8); }

  /**
   * Set the {@code valueAI8} property.
   * @see #valueAI8
   */
  public void setValueAI8(int v) { setInt(valueAI8, v, null); }

  //endregion Property "valueAI8"

  //region Property "activeDiMap"

  /**
   * Slot for the {@code activeDiMap} property.
   * @see #getActiveDiMap
   * @see #setActiveDiMap
   */
  public static final Property activeDiMap = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code activeDiMap} property.
   * @see #activeDiMap
   */
  public int getActiveDiMap() { return getInt(activeDiMap); }

  /**
   * Set the {@code activeDiMap} property.
   * @see #activeDiMap
   */
  public void setActiveDiMap(int v) { setInt(activeDiMap, v, null); }

  //endregion Property "activeDiMap"

  //region Property "valueHighSpeedDIs"

  /**
   * Slot for the {@code valueHighSpeedDIs} property.
   * @see #getValueHighSpeedDIs
   * @see #setValueHighSpeedDIs
   */
  public static final Property valueHighSpeedDIs = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code valueHighSpeedDIs} property.
   * @see #valueHighSpeedDIs
   */
  public long getValueHighSpeedDIs() { return getLong(valueHighSpeedDIs); }

  /**
   * Set the {@code valueHighSpeedDIs} property.
   * @see #valueHighSpeedDIs
   */
  public void setValueHighSpeedDIs(long v) { setLong(valueHighSpeedDIs, v, null); }

  //endregion Property "valueHighSpeedDIs"

  //region Property "countHighSpeedDI1"

  /**
   * Slot for the {@code countHighSpeedDI1} property.
   * @see #getCountHighSpeedDI1
   * @see #setCountHighSpeedDI1
   */
  public static final Property countHighSpeedDI1 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code countHighSpeedDI1} property.
   * @see #countHighSpeedDI1
   */
  public long getCountHighSpeedDI1() { return getLong(countHighSpeedDI1); }

  /**
   * Set the {@code countHighSpeedDI1} property.
   * @see #countHighSpeedDI1
   */
  public void setCountHighSpeedDI1(long v) { setLong(countHighSpeedDI1, v, null); }

  //endregion Property "countHighSpeedDI1"

  //region Property "countHighSpeedDI2"

  /**
   * Slot for the {@code countHighSpeedDI2} property.
   * @see #getCountHighSpeedDI2
   * @see #setCountHighSpeedDI2
   */
  public static final Property countHighSpeedDI2 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code countHighSpeedDI2} property.
   * @see #countHighSpeedDI2
   */
  public long getCountHighSpeedDI2() { return getLong(countHighSpeedDI2); }

  /**
   * Set the {@code countHighSpeedDI2} property.
   * @see #countHighSpeedDI2
   */
  public void setCountHighSpeedDI2(long v) { setLong(countHighSpeedDI2, v, null); }

  //endregion Property "countHighSpeedDI2"

  //region Property "countHighSpeedDI3"

  /**
   * Slot for the {@code countHighSpeedDI3} property.
   * @see #getCountHighSpeedDI3
   * @see #setCountHighSpeedDI3
   */
  public static final Property countHighSpeedDI3 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code countHighSpeedDI3} property.
   * @see #countHighSpeedDI3
   */
  public long getCountHighSpeedDI3() { return getLong(countHighSpeedDI3); }

  /**
   * Set the {@code countHighSpeedDI3} property.
   * @see #countHighSpeedDI3
   */
  public void setCountHighSpeedDI3(long v) { setLong(countHighSpeedDI3, v, null); }

  //endregion Property "countHighSpeedDI3"

  //region Property "countHighSpeedDI4"

  /**
   * Slot for the {@code countHighSpeedDI4} property.
   * @see #getCountHighSpeedDI4
   * @see #setCountHighSpeedDI4
   */
  public static final Property countHighSpeedDI4 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code countHighSpeedDI4} property.
   * @see #countHighSpeedDI4
   */
  public long getCountHighSpeedDI4() { return getLong(countHighSpeedDI4); }

  /**
   * Set the {@code countHighSpeedDI4} property.
   * @see #countHighSpeedDI4
   */
  public void setCountHighSpeedDI4(long v) { setLong(countHighSpeedDI4, v, null); }

  //endregion Property "countHighSpeedDI4"

  //region Property "countHighSpeedDI5"

  /**
   * Slot for the {@code countHighSpeedDI5} property.
   * @see #getCountHighSpeedDI5
   * @see #setCountHighSpeedDI5
   */
  public static final Property countHighSpeedDI5 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code countHighSpeedDI5} property.
   * @see #countHighSpeedDI5
   */
  public long getCountHighSpeedDI5() { return getLong(countHighSpeedDI5); }

  /**
   * Set the {@code countHighSpeedDI5} property.
   * @see #countHighSpeedDI5
   */
  public void setCountHighSpeedDI5(long v) { setLong(countHighSpeedDI5, v, null); }

  //endregion Property "countHighSpeedDI5"

  //region Property "countHighSpeedDI6"

  /**
   * Slot for the {@code countHighSpeedDI6} property.
   * @see #getCountHighSpeedDI6
   * @see #setCountHighSpeedDI6
   */
  public static final Property countHighSpeedDI6 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code countHighSpeedDI6} property.
   * @see #countHighSpeedDI6
   */
  public long getCountHighSpeedDI6() { return getLong(countHighSpeedDI6); }

  /**
   * Set the {@code countHighSpeedDI6} property.
   * @see #countHighSpeedDI6
   */
  public void setCountHighSpeedDI6(long v) { setLong(countHighSpeedDI6, v, null); }

  //endregion Property "countHighSpeedDI6"

  //region Property "countHighSpeedDI7"

  /**
   * Slot for the {@code countHighSpeedDI7} property.
   * @see #getCountHighSpeedDI7
   * @see #setCountHighSpeedDI7
   */
  public static final Property countHighSpeedDI7 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code countHighSpeedDI7} property.
   * @see #countHighSpeedDI7
   */
  public long getCountHighSpeedDI7() { return getLong(countHighSpeedDI7); }

  /**
   * Set the {@code countHighSpeedDI7} property.
   * @see #countHighSpeedDI7
   */
  public void setCountHighSpeedDI7(long v) { setLong(countHighSpeedDI7, v, null); }

  //endregion Property "countHighSpeedDI7"

  //region Property "countHighSpeedDI8"

  /**
   * Slot for the {@code countHighSpeedDI8} property.
   * @see #getCountHighSpeedDI8
   * @see #setCountHighSpeedDI8
   */
  public static final Property countHighSpeedDI8 = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code countHighSpeedDI8} property.
   * @see #countHighSpeedDI8
   */
  public long getCountHighSpeedDI8() { return getLong(countHighSpeedDI8); }

  /**
   * Set the {@code countHighSpeedDI8} property.
   * @see #countHighSpeedDI8
   */
  public void setCountHighSpeedDI8(long v) { setLong(countHighSpeedDI8, v, null); }

  //endregion Property "countHighSpeedDI8"

  //region Property "valueOverrides"

  /**
   * Slot for the {@code valueOverrides} property.
   * @see #getValueOverrides
   * @see #setValueOverrides
   */
  public static final Property valueOverrides = newProperty(Flags.TRANSIENT | Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code valueOverrides} property.
   * @see #valueOverrides
   */
  public int getValueOverrides() { return getInt(valueOverrides); }

  /**
   * Set the {@code valueOverrides} property.
   * @see #valueOverrides
   */
  public void setValueOverrides(int v) { setInt(valueOverrides, v, null); }

  //endregion Property "valueOverrides"

  //region Property "totalCount1"

  /**
   * Slot for the {@code totalCount1} property.
   * @see #getTotalCount1
   * @see #setTotalCount1
   */
  public static final Property totalCount1 = newProperty(Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code totalCount1} property.
   * @see #totalCount1
   */
  public long getTotalCount1() { return getLong(totalCount1); }

  /**
   * Set the {@code totalCount1} property.
   * @see #totalCount1
   */
  public void setTotalCount1(long v) { setLong(totalCount1, v, null); }

  //endregion Property "totalCount1"

  //region Property "totalCount2"

  /**
   * Slot for the {@code totalCount2} property.
   * @see #getTotalCount2
   * @see #setTotalCount2
   */
  public static final Property totalCount2 = newProperty(Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code totalCount2} property.
   * @see #totalCount2
   */
  public long getTotalCount2() { return getLong(totalCount2); }

  /**
   * Set the {@code totalCount2} property.
   * @see #totalCount2
   */
  public void setTotalCount2(long v) { setLong(totalCount2, v, null); }

  //endregion Property "totalCount2"

  //region Property "totalCount3"

  /**
   * Slot for the {@code totalCount3} property.
   * @see #getTotalCount3
   * @see #setTotalCount3
   */
  public static final Property totalCount3 = newProperty(Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code totalCount3} property.
   * @see #totalCount3
   */
  public long getTotalCount3() { return getLong(totalCount3); }

  /**
   * Set the {@code totalCount3} property.
   * @see #totalCount3
   */
  public void setTotalCount3(long v) { setLong(totalCount3, v, null); }

  //endregion Property "totalCount3"

  //region Property "totalCount4"

  /**
   * Slot for the {@code totalCount4} property.
   * @see #getTotalCount4
   * @see #setTotalCount4
   */
  public static final Property totalCount4 = newProperty(Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code totalCount4} property.
   * @see #totalCount4
   */
  public long getTotalCount4() { return getLong(totalCount4); }

  /**
   * Set the {@code totalCount4} property.
   * @see #totalCount4
   */
  public void setTotalCount4(long v) { setLong(totalCount4, v, null); }

  //endregion Property "totalCount4"

  //region Property "totalCount5"

  /**
   * Slot for the {@code totalCount5} property.
   * @see #getTotalCount5
   * @see #setTotalCount5
   */
  public static final Property totalCount5 = newProperty(Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code totalCount5} property.
   * @see #totalCount5
   */
  public long getTotalCount5() { return getLong(totalCount5); }

  /**
   * Set the {@code totalCount5} property.
   * @see #totalCount5
   */
  public void setTotalCount5(long v) { setLong(totalCount5, v, null); }

  //endregion Property "totalCount5"

  //region Property "totalCount6"

  /**
   * Slot for the {@code totalCount6} property.
   * @see #getTotalCount6
   * @see #setTotalCount6
   */
  public static final Property totalCount6 = newProperty(Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code totalCount6} property.
   * @see #totalCount6
   */
  public long getTotalCount6() { return getLong(totalCount6); }

  /**
   * Set the {@code totalCount6} property.
   * @see #totalCount6
   */
  public void setTotalCount6(long v) { setLong(totalCount6, v, null); }

  //endregion Property "totalCount6"

  //region Property "totalCount7"

  /**
   * Slot for the {@code totalCount7} property.
   * @see #getTotalCount7
   * @see #setTotalCount7
   */
  public static final Property totalCount7 = newProperty(Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code totalCount7} property.
   * @see #totalCount7
   */
  public long getTotalCount7() { return getLong(totalCount7); }

  /**
   * Set the {@code totalCount7} property.
   * @see #totalCount7
   */
  public void setTotalCount7(long v) { setLong(totalCount7, v, null); }

  //endregion Property "totalCount7"

  //region Property "totalCount8"

  /**
   * Slot for the {@code totalCount8} property.
   * @see #getTotalCount8
   * @see #setTotalCount8
   */
  public static final Property totalCount8 = newProperty(Flags.READONLY, 0, BFacets.make(BFacets.RADIX, 16));

  /**
   * Get the {@code totalCount8} property.
   * @see #totalCount8
   */
  public long getTotalCount8() { return getLong(totalCount8); }

  /**
   * Set the {@code totalCount8} property.
   * @see #totalCount8
   */
  public void setTotalCount8(long v) { setLong(totalCount8, v, null); }

  //endregion Property "totalCount8"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrio34SecStatus.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BNrio34SecStatus()
  {
  }

  public void doClearTotals()
  {
    setTotalCount1(0l);
    setTotalCount2(0l);
    setTotalCount3(0l);
    setTotalCount4(0l);
    setTotalCount5(0l);
    setTotalCount6(0l);
    setTotalCount7(0l);
    setTotalCount8(0l);

  }


  // returns true if processing is OK.
  public boolean readIoStatus(byte[] data, int start, int length)
  {
    //check for dropped byte. bytes a offset 0x38 & 0x39 should be 0xffff.
    if( (data[start+0x38] & 0x0ff) == 0x0ff &&
      (data[start+0x39] & 0x0ff) == 0x0ff    )
      setIoStatus(BBlob.make(data, start, length));
    else
    {
      counterLog.message("dropped byte detected, throwing away message");
      return false;
    }

//                                1                               2                               3                   3
//  1 2 3 4 5 6 7 8 9 a b c d e f 0 1 2 3 4 5 6 7 8 9 a b c d e f 0 1 2 3 4 5 6 7 8 9 a b c d e f 0 1 2 3 4 5 6 7 8 9 a b c d e
//023a0800ff000000000000000000000000000000000000ff0000000000000000000000000000000000000000000000000000000000000000ffff00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
    NrioInputStream in = new NrioInputStream(data, start, length);
    int address = in.read() & 0x0ff;			//0
    int msgLength  = in.read() & 0x0ff;			//
    int type    = in.read() & 0x0ff;			//00
    int status  = in.read() & 0x0ff;			//01
    this.setActiveAiMap(in.readInt());			//
    setValueAI1(in.readInt());
    setValueAI2(in.readInt());
    setValueAI3(in.readInt());
    setValueAI4(in.readInt());
    setValueAI5(in.readInt());
    setValueAI6(in.readInt());
    setValueAI7(in.readInt());
    setValueAI8(in.readInt());
    setActiveDiMap(in.read() & 0x0ff);
    setValueHighSpeedDIs(in.read() & 0x0ff);

    for(int i = 1; i < 9; i++)
    {
      updateCounts(i, in.readLong()) ;
    }
    firstUpdate = true;   // nccb-33633 e333968 3/26/2018
    return true;
  }

  public void updateCounts(int index, long newCount)
  {
    long currentCount = getDiCounts(index);
    long deltaCount;
    if(newCount < currentCount)  // rollover test
    {
      deltaCount = newCount;
      if(counterLog.isTraceOn())
      {
        counterLog.trace(index + ": Rollover; newCount=" + newCount + ", currentCount= " + currentCount + ", countDelta = " + deltaCount );
      }
    }
    else
      deltaCount = newCount - currentCount;
    if(deltaCount > 0 && counterLog.isTraceOn() && deltaCount > 25)
    {
      counterLog.trace("Input: " + index + ", new Count = " + newCount + ", countDelta = " + deltaCount );
    }
    // nccb-33633 e333968 3/26/2018
    if(hasFirstUpdate())
    {
      addTotalCount(index, deltaCount);
    }
    else if(counterLog.isTraceOn())
    {
      counterLog.trace("Frist count update, initialize counts.");
    }
    // end nccb-33633 e333968 3/26/2018
    setCountHighSpeedDi(index, newCount);
  }

  public void addTotalCount(int index, long deltaCount)
  {
    long newCount = 0;

    switch(index)
    {
      case 1: newCount = deltaCount + getTotalCount1(); break;
      case 2: newCount = deltaCount + getTotalCount2(); break;
      case 3: newCount = deltaCount + getTotalCount3(); break;
      case 4: newCount = deltaCount + getTotalCount4(); break;
      case 5: newCount = deltaCount + getTotalCount5(); break;
      case 6: newCount = deltaCount + getTotalCount6(); break;
      case 7: newCount = deltaCount + getTotalCount7(); break;
      case 8: newCount = deltaCount + getTotalCount8(); break;
    }

    switch(index)
    {
      case 1: setTotalCount1(newCount); break;
      case 2: setTotalCount2(newCount); break;
      case 3: setTotalCount3(newCount); break;
      case 4: setTotalCount4(newCount); break;
      case 5: setTotalCount5(newCount); break;
      case 6: setTotalCount6(newCount); break;
      case 7: setTotalCount7(newCount); break;
      case 8: setTotalCount8(newCount); break;
    }
  }

  public void setCountHighSpeedDi(int index, long newCount)
  {
    switch(index)
    {
      case 1: setCountHighSpeedDI1(newCount); break;
      case 2: setCountHighSpeedDI2(newCount); break;
      case 3: setCountHighSpeedDI3(newCount); break;
      case 4: setCountHighSpeedDI4(newCount); break;
      case 5: setCountHighSpeedDI5(newCount); break;
      case 6: setCountHighSpeedDI6(newCount); break;
      case 7: setCountHighSpeedDI7(newCount); break;
      case 8: setCountHighSpeedDI8(newCount); break;
    }
  }


  public boolean getDi(int instance)
  {
    int index = instance-1;
    if(index < 0) index = 0;
    if(index > 7) index = 7;
    int mask = 0x01 << index;
    return (getValueHighSpeedDIs() & mask)== 0;
  }

  public int getAi(int instance)
  {
    switch(instance)
    {
      case 1: return getValueAI1();
      case 2: return getValueAI2();
      case 3: return getValueAI3();
      case 4: return getValueAI4();
      case 5: return getValueAI5();
      case 6: return getValueAI6();
      case 7: return getValueAI7();
      case 8: return getValueAI8();
    }
    return -1;
  }

  public void setTotalCounts(int instance, long value)
  {
    switch(instance)
    {
      case 1: setTotalCount1(value); break;
      case 2: setTotalCount2(value); break;
      case 3: setTotalCount3(value); break;
      case 4: setTotalCount4(value); break;
      case 5: setTotalCount5(value); break;
      case 6: setTotalCount6(value); break;
      case 7: setTotalCount7(value); break;
      case 8: setTotalCount8(value); break;
    }

  }

  public long getTotalCounts(int instance)
  {
    switch(instance)
    {
      case 1: return getTotalCount1();
      case 2: return getTotalCount2();
      case 3: return getTotalCount3();
      case 4: return getTotalCount4();
      case 5: return getTotalCount5();
      case 6: return getTotalCount6();
      case 7: return getTotalCount7();
      case 8: return getTotalCount8();
    }
    return -1;
  }
  public long getDiCounts(int instance)
  {
    switch(instance)
    {
      case 1: return getCountHighSpeedDI1();
      case 2: return getCountHighSpeedDI2();
      case 3: return getCountHighSpeedDI3();
      case 4: return getCountHighSpeedDI4();
      case 5: return getCountHighSpeedDI5();
      case 6: return getCountHighSpeedDI6();
      case 7: return getCountHighSpeedDI7();
      case 8: return getCountHighSpeedDI8();
    }
    return -1;
  }
  public byte[] copyBytes()
  {
    return getIoStatus().copyBytes();
  }
  // nccb-33633 e333968 3/26/2018
  public boolean hasFirstUpdate() { return firstUpdate; }
  private boolean firstUpdate = false;

  public static Log counterLog = Log.getLog("nrio.counter");

}
