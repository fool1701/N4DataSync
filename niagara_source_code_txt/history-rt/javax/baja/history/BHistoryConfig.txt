/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import java.io.IOException;

import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdList;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.ModuleException;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.sys.TypeException;
import javax.baja.sys.TypeNotFoundException;
import javax.baja.timezone.BTimeZone;
import javax.baja.util.BNameList;
import javax.baja.util.BTypeSpec;

/**
 * BHistoryConfig is the configuration for a history in the
 * history database.
 *
 * @author    John Sublett
 * @creation  02 Apr 2003
 * @version   $Revision: 24$ $Date: 5/22/08 2:41:23 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The unique identifier for this history within the
 entire system.
 */
@NiagaraProperty(
  name = "id",
  type = "BHistoryId",
  defaultValue = "BHistoryId.NULL",
  flags = Flags.DEFAULT_ON_CLONE | Flags.SUMMARY | Flags.OPERATOR | Flags.READONLY
)
/*
 Temporary for the transition from the old historyName
 scheme.  Will be removed before the final release.
 */
@NiagaraProperty(
  name = "historyName",
  type = "String",
  defaultValue = "",
  flags = Flags.HIDDEN | Flags.TRANSIENT
)
/*
 The list of ords that identifies the original source
 of the history.  For a history, the ord list indicates
 the path that the history has moved through the system
 via the archive mechanism.  The history in the source
 device will have an ord list of length 1.
 */
@NiagaraProperty(
  name = "source",
  type = "BOrdList",
  defaultValue = "BOrdList.NULL",
  flags = Flags.SUMMARY | Flags.OPERATOR | Flags.READONLY
)
/*
 The handle for the source of this history in the station
 that originally created it.
 */
@NiagaraProperty(
  name = "sourceHandle",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  flags = Flags.HIDDEN
)
/*
 The timezone where this history was originally collected.
 */
@NiagaraProperty(
  name = "timeZone",
  type = "BTimeZone",
  defaultValue = "BTimeZone.NULL",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY
)
/*
 The type spec for the records in the history.
 */
@NiagaraProperty(
  name = "recordType",
  type = "BTypeSpec",
  defaultValue = "BTypeSpec.NULL",
  flags = Flags.READONLY
)
/*
 The schema for the record.  This allows the history
 to be read even if the original record type class
 has changed or is not available.
 */
@NiagaraProperty(
  name = "schema",
  type = "BHistorySchema",
  defaultValue = "BHistorySchema.DEFAULT",
  flags = Flags.READONLY | Flags.HIDDEN
)
/*
 The amount of data that can be stored in the history.  The capacity
 can be defined either by record count or by storage size.
 */
@NiagaraProperty(
  name = "capacity",
  type = "BCapacity",
  defaultValue = "BCapacity.makeByRecordCount(500)"
)
/*
 This defines the behavior when an attempt is made to write records
 to a history with limited capacity that is full.
 */
@NiagaraProperty(
  name = "fullPolicy",
  type = "BFullPolicy",
  defaultValue = "BFullPolicy.roll"
)
/*
 This defines the mechanism for storage of the history records.
 */
@NiagaraProperty(
  name = "storageType",
  type = "BStorageType",
  defaultValue = "BStorageType.file",
  flags = Flags.HIDDEN
)
/*
 The amount of time between records in the history.
 */
@NiagaraProperty(
  name = "interval",
  type = "BCollectionInterval",
  defaultValue = "BCollectionInterval.IRREGULAR",
  flags = Flags.OPERATOR | Flags.READONLY
)
/*
 Contains a list of system tag names (each tag
 is separated by a semicolon) that can be used to identify
 the history as part of a system (or part of multiple systems).
 @since Niagara 3.4
 */
@NiagaraProperty(
  name = "systemTags",
  type = "BNameList",
  defaultValue = "BNameList.NULL"
)
public class BHistoryConfig
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BHistoryConfig(3004268112)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "id"

  /**
   * Slot for the {@code id} property.
   * The unique identifier for this history within the
   * entire system.
   * @see #getId
   * @see #setId
   */
  public static final Property id = newProperty(Flags.DEFAULT_ON_CLONE | Flags.SUMMARY | Flags.OPERATOR | Flags.READONLY, BHistoryId.NULL, null);

  /**
   * Get the {@code id} property.
   * The unique identifier for this history within the
   * entire system.
   * @see #id
   */
  public BHistoryId getId() { return (BHistoryId)get(id); }

  /**
   * Set the {@code id} property.
   * The unique identifier for this history within the
   * entire system.
   * @see #id
   */
  public void setId(BHistoryId v) { set(id, v, null); }

  //endregion Property "id"

  //region Property "historyName"

  /**
   * Slot for the {@code historyName} property.
   * Temporary for the transition from the old historyName
   * scheme.  Will be removed before the final release.
   * @see #getHistoryName
   * @see #setHistoryName
   */
  public static final Property historyName = newProperty(Flags.HIDDEN | Flags.TRANSIENT, "", null);

  /**
   * Get the {@code historyName} property.
   * Temporary for the transition from the old historyName
   * scheme.  Will be removed before the final release.
   * @see #historyName
   */
  public String getHistoryName() { return getString(historyName); }

  /**
   * Set the {@code historyName} property.
   * Temporary for the transition from the old historyName
   * scheme.  Will be removed before the final release.
   * @see #historyName
   */
  public void setHistoryName(String v) { setString(historyName, v, null); }

  //endregion Property "historyName"

  //region Property "source"

  /**
   * Slot for the {@code source} property.
   * The list of ords that identifies the original source
   * of the history.  For a history, the ord list indicates
   * the path that the history has moved through the system
   * via the archive mechanism.  The history in the source
   * device will have an ord list of length 1.
   * @see #getSource
   * @see #setSource
   */
  public static final Property source = newProperty(Flags.SUMMARY | Flags.OPERATOR | Flags.READONLY, BOrdList.NULL, null);

  /**
   * Get the {@code source} property.
   * The list of ords that identifies the original source
   * of the history.  For a history, the ord list indicates
   * the path that the history has moved through the system
   * via the archive mechanism.  The history in the source
   * device will have an ord list of length 1.
   * @see #source
   */
  public BOrdList getSource() { return (BOrdList)get(source); }

  /**
   * Set the {@code source} property.
   * The list of ords that identifies the original source
   * of the history.  For a history, the ord list indicates
   * the path that the history has moved through the system
   * via the archive mechanism.  The history in the source
   * device will have an ord list of length 1.
   * @see #source
   */
  public void setSource(BOrdList v) { set(source, v, null); }

  //endregion Property "source"

  //region Property "sourceHandle"

  /**
   * Slot for the {@code sourceHandle} property.
   * The handle for the source of this history in the station
   * that originally created it.
   * @see #getSourceHandle
   * @see #setSourceHandle
   */
  public static final Property sourceHandle = newProperty(Flags.HIDDEN, BOrd.NULL, null);

  /**
   * Get the {@code sourceHandle} property.
   * The handle for the source of this history in the station
   * that originally created it.
   * @see #sourceHandle
   */
  public BOrd getSourceHandle() { return (BOrd)get(sourceHandle); }

  /**
   * Set the {@code sourceHandle} property.
   * The handle for the source of this history in the station
   * that originally created it.
   * @see #sourceHandle
   */
  public void setSourceHandle(BOrd v) { set(sourceHandle, v, null); }

  //endregion Property "sourceHandle"

  //region Property "timeZone"

  /**
   * Slot for the {@code timeZone} property.
   * The timezone where this history was originally collected.
   * @see #getTimeZone
   * @see #setTimeZone
   */
  public static final Property timeZone = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY, BTimeZone.NULL, null);

  /**
   * Get the {@code timeZone} property.
   * The timezone where this history was originally collected.
   * @see #timeZone
   */
  public BTimeZone getTimeZone() { return (BTimeZone)get(timeZone); }

  /**
   * Set the {@code timeZone} property.
   * The timezone where this history was originally collected.
   * @see #timeZone
   */
  public void setTimeZone(BTimeZone v) { set(timeZone, v, null); }

  //endregion Property "timeZone"

  //region Property "recordType"

  /**
   * Slot for the {@code recordType} property.
   * The type spec for the records in the history.
   * @see #getRecordType
   * @see #setRecordType
   */
  public static final Property recordType = newProperty(Flags.READONLY, BTypeSpec.NULL, null);

  /**
   * Get the {@code recordType} property.
   * The type spec for the records in the history.
   * @see #recordType
   */
  public BTypeSpec getRecordType() { return (BTypeSpec)get(recordType); }

  /**
   * Set the {@code recordType} property.
   * The type spec for the records in the history.
   * @see #recordType
   */
  public void setRecordType(BTypeSpec v) { set(recordType, v, null); }

  //endregion Property "recordType"

  //region Property "schema"

  /**
   * Slot for the {@code schema} property.
   * The schema for the record.  This allows the history
   * to be read even if the original record type class
   * has changed or is not available.
   * @see #getSchema
   * @see #setSchema
   */
  public static final Property schema = newProperty(Flags.READONLY | Flags.HIDDEN, BHistorySchema.DEFAULT, null);

  /**
   * Get the {@code schema} property.
   * The schema for the record.  This allows the history
   * to be read even if the original record type class
   * has changed or is not available.
   * @see #schema
   */
  public BHistorySchema getSchema() { return (BHistorySchema)get(schema); }

  /**
   * Set the {@code schema} property.
   * The schema for the record.  This allows the history
   * to be read even if the original record type class
   * has changed or is not available.
   * @see #schema
   */
  public void setSchema(BHistorySchema v) { set(schema, v, null); }

  //endregion Property "schema"

  //region Property "capacity"

  /**
   * Slot for the {@code capacity} property.
   * The amount of data that can be stored in the history.  The capacity
   * can be defined either by record count or by storage size.
   * @see #getCapacity
   * @see #setCapacity
   */
  public static final Property capacity = newProperty(0, BCapacity.makeByRecordCount(500), null);

  /**
   * Get the {@code capacity} property.
   * The amount of data that can be stored in the history.  The capacity
   * can be defined either by record count or by storage size.
   * @see #capacity
   */
  public BCapacity getCapacity() { return (BCapacity)get(capacity); }

  /**
   * Set the {@code capacity} property.
   * The amount of data that can be stored in the history.  The capacity
   * can be defined either by record count or by storage size.
   * @see #capacity
   */
  public void setCapacity(BCapacity v) { set(capacity, v, null); }

  //endregion Property "capacity"

  //region Property "fullPolicy"

  /**
   * Slot for the {@code fullPolicy} property.
   * This defines the behavior when an attempt is made to write records
   * to a history with limited capacity that is full.
   * @see #getFullPolicy
   * @see #setFullPolicy
   */
  public static final Property fullPolicy = newProperty(0, BFullPolicy.roll, null);

  /**
   * Get the {@code fullPolicy} property.
   * This defines the behavior when an attempt is made to write records
   * to a history with limited capacity that is full.
   * @see #fullPolicy
   */
  public BFullPolicy getFullPolicy() { return (BFullPolicy)get(fullPolicy); }

  /**
   * Set the {@code fullPolicy} property.
   * This defines the behavior when an attempt is made to write records
   * to a history with limited capacity that is full.
   * @see #fullPolicy
   */
  public void setFullPolicy(BFullPolicy v) { set(fullPolicy, v, null); }

  //endregion Property "fullPolicy"

  //region Property "storageType"

  /**
   * Slot for the {@code storageType} property.
   * This defines the mechanism for storage of the history records.
   * @see #getStorageType
   * @see #setStorageType
   */
  public static final Property storageType = newProperty(Flags.HIDDEN, BStorageType.file, null);

  /**
   * Get the {@code storageType} property.
   * This defines the mechanism for storage of the history records.
   * @see #storageType
   */
  public BStorageType getStorageType() { return (BStorageType)get(storageType); }

  /**
   * Set the {@code storageType} property.
   * This defines the mechanism for storage of the history records.
   * @see #storageType
   */
  public void setStorageType(BStorageType v) { set(storageType, v, null); }

  //endregion Property "storageType"

  //region Property "interval"

  /**
   * Slot for the {@code interval} property.
   * The amount of time between records in the history.
   * @see #getInterval
   * @see #setInterval
   */
  public static final Property interval = newProperty(Flags.OPERATOR | Flags.READONLY, BCollectionInterval.IRREGULAR, null);

  /**
   * Get the {@code interval} property.
   * The amount of time between records in the history.
   * @see #interval
   */
  public BCollectionInterval getInterval() { return (BCollectionInterval)get(interval); }

  /**
   * Set the {@code interval} property.
   * The amount of time between records in the history.
   * @see #interval
   */
  public void setInterval(BCollectionInterval v) { set(interval, v, null); }

  //endregion Property "interval"

  //region Property "systemTags"

  /**
   * Slot for the {@code systemTags} property.
   * Contains a list of system tag names (each tag
   * is separated by a semicolon) that can be used to identify
   * the history as part of a system (or part of multiple systems).
   * @since Niagara 3.4
   * @see #getSystemTags
   * @see #setSystemTags
   */
  public static final Property systemTags = newProperty(0, BNameList.NULL, null);

  /**
   * Get the {@code systemTags} property.
   * Contains a list of system tag names (each tag
   * is separated by a semicolon) that can be used to identify
   * the history as part of a system (or part of multiple systems).
   * @since Niagara 3.4
   * @see #systemTags
   */
  public BNameList getSystemTags() { return (BNameList)get(systemTags); }

  /**
   * Set the {@code systemTags} property.
   * Contains a list of system tag names (each tag
   * is separated by a semicolon) that can be used to identify
   * the history as part of a system (or part of multiple systems).
   * @since Niagara 3.4
   * @see #systemTags
   */
  public void setSystemTags(BNameList v) { set(systemTags, v, null); }

  //endregion Property "systemTags"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BHistoryConfig()
  {
  }

  public BHistoryConfig(BHistoryId id, BTypeSpec recType)
  {
    setId(id);
    setRecordType(recType);
    setSchema(makeRecord(recType, BHistoryRecord.VERSION_2).getSchema());
  }

  public BHistoryConfig(BCapacity capacity, BFullPolicy fullPolicy)
  {
    setCapacity(capacity);
    setFullPolicy(fullPolicy);
  }

  public BHistoryConfig(BHistoryId id,
                        BTypeSpec  recordType,
                        BCapacity  capacity)
  {
    setId(id);
    setRecordType(recordType);
    setSchema(makeRecord(recordType, BHistoryRecord.VERSION_2).getSchema());
    setCapacity(capacity);
  }


  /**
   * Get the type for the column with the specified name.
   *
   * @param name The name of the column.
   * @return Returns the type of the target column or null if the column does
   *   not exist.
   */
  public Type getColumnType(String name)
  {
    try
    {
      BHistoryRecord rec = makeRecord();
      Property prop = rec.getProperty(name);
      if (prop == null)
        return null;
      else
        return prop.getDefaultValue().getType();
    }
    catch(Exception e)
    {
      return null;
    }
  }

  /**
   * Make a template record.  This creates a new instance with default values.
   *
   * @return The template record.
   */
  public BHistoryRecord makeRecord()
    throws HistoryException
  {
    return makeRecord(BHistoryRecord.VERSION_2);
  }

  public BHistoryRecord makeRecord(int version)
    throws HistoryException
  {
    BTypeSpec recordType = getRecordType();
    try
    {
      return makeRecord(recordType, version);
    }
    catch (TypeNotFoundException ex)
    {
      // TODO - Temporary for Float -> Numeric transition
      if (recordType.getTypeName().equals("FloatTrendRecord"))
      {
        setRecordType(BNumericTrendRecord.TYPE.getTypeSpec());
        return new BNumericTrendRecord();
      }
      else
        throw ex;
    }
  }

  /**
   * Create an instance of the specified type.
   */
  private static BHistoryRecord makeRecord(BTypeSpec typeSpec, int version)
  {
    Type recordType = typeSpec.getResolvedType();
    BHistoryRecord record = (BHistoryRecord)recordType.getInstance();
    record.setHistoryVersion(version);
    return record;
  }

  /**
   * Make a prototype record for the relation.
   */
  public BObject makePrototype()
  {
    try
    {
      return makeRecord();
    }
    catch(Exception e)
    {
      throw new BajaRuntimeException(e);
    }
  }

  /**
   * Get the size of the records in this history.  If the record size is not
   * fixed an UnsupportedOperationException is thrown.
   *
   * @exception UnsupportedOperationException Thrown if the record size is not fixed.
   */
  public int getRecordSize()
    throws IOException, HistoryException
  {
    if (recordSize == -1)
    {
      try
      {
        BHistoryRecord rec = makeRecord();
        if (!rec.isFixedSize())
          throw new UnsupportedOperationException
            ("A fixed record size cannot be determined for" +
             " a history with variable length records.");

        recordSize = rec.getRecordSize();
      }
      catch(ModuleException e)
      {
        throw new HistoryException(e.getMessage(), e);
      }
      catch(TypeException e)
      {
        throw new HistoryException(e.getMessage(), e);
      }
    }

    return recordSize;
  }

  /**
   * Handle a property change.
   */
  @Override
  public void changed(Property p, Context c)
  {
    if (c == Context.decoding) return;

    // Init the schema if necessary
    if (p == recordType)
    {
      if (getSchema().equals(BHistorySchema.DEFAULT))
        setSchema(makeRecord().getSchema());
    }

    if (!isRunning()) return;

    BComplex parent = getParent();
    if (parent instanceof BIHistorySource)
      ((BIHistorySource)parent).historyConfigChanged(this, p);
  }

  /**
   * Handle a property addition.
   */
  @Override
  public void added(Property p, Context c)
  {
    if (c == Context.decoding) return;
    if (!isRunning()) return;

    BComplex parent = getParent();
    if (parent instanceof BIHistorySource)
      ((BIHistorySource)parent).historyConfigChanged(this, p);
  }

  /**
   * Handle a property removal.
   */
  @Override
  public void removed(Property p, BValue value, Context c)
  {
    if (c == Context.decoding) return;
    if (!isRunning()) return;

    BComplex parent = getParent();
    if (parent instanceof BIHistorySource)
      ((BIHistorySource)parent).historyConfigChanged(this, p);
  }

  /**
   * Called when an existing property is renamed via one
   * of the <code>rename</code> methods.
   */
  @Override
  public void renamed(Property p, String oldName, Context c)
  {
    if (c == Context.decoding) return;
    if (!isRunning()) return;

    BComplex parent = getParent();
    if (parent instanceof BIHistorySource)
      ((BIHistorySource)parent).historyConfigChanged(this, p);
  }

  /**
   * Called when a slot's flags are modified via one of
   * the <code>setFlags</code> methods.
   */
  @Override
  public void flagsChanged(Slot slot, Context c)
  {
    if (c == Context.decoding) return;
    if (!isRunning()) return;
    if (!slot.isProperty()) return;

    BComplex parent = getParent();
    if (parent instanceof BIHistorySource)
      ((BIHistorySource)parent).historyConfigChanged(this, slot.asProperty());
  }

  /**
   * Called when a slot's facets are modified via one of
   * the <code>setFacets</code> methods.
   */
  @Override
  public void facetsChanged(Slot slot, Context c)
  {
    if (c == Context.decoding) return;
    if (!isRunning()) return;
    if (!slot.isProperty()) return;

    BComplex parent = getParent();
    if (parent instanceof BIHistorySource)
      ((BIHistorySource)parent).historyConfigChanged(this, slot.asProperty());
  }

  @Override
  public String toString(Context cx)
  {
    StringBuilder out = new StringBuilder();
    out.append(BHistoryConfig.interval.getDefaultDisplayName(cx)+": "+getInterval()).append(", ");
    out.append(BHistoryConfig.recordType.getDefaultDisplayName(cx)+": ");
    out.append(getRecordType().isNull() ? "null" : getRecordType().getResolvedType().getDisplayName(cx).toLowerCase()).append(", ");
    out.append(BHistoryConfig.capacity.getDefaultDisplayName(cx)+": "+getCapacity()).append(", ");
    out.append(BHistoryConfig.fullPolicy.getDefaultDisplayName(cx)+": "+getFullPolicy());
    return out.toString();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private int recordSize = -1;
  private BHistoryRecord prototype;
}
