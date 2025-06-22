/**
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import java.io.IOException;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
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
import javax.baja.units.BDimension;
import javax.baja.units.BUnit;
import javax.baja.util.BTypeSpec;

/**
 * BAlarmDbConfig is the configuration for the alarm database.
 *
 * @author Blake Puhak
 * @creation 09 June 2014
 * @since Niagara 4.0
 */
@NiagaraType
@NiagaraProperty(
  name = "recordType",
  type = "BTypeSpec",
  defaultValue = "BTypeSpec.make(\"alarm\", \"AlarmRecord\")",
  flags = Flags.READONLY | Flags.HIDDEN
)
@NiagaraProperty(
  name = "schema",
  type = "BAlarmSchema",
  defaultValue = "BAlarmSchema.DEFAULT",
  flags = Flags.HIDDEN | Flags.READONLY
)
public class BAlarmDbConfig
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BAlarmDbConfig(646142715)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "recordType"

  /**
   * Slot for the {@code recordType} property.
   * @see #getRecordType
   * @see #setRecordType
   */
  public static final Property recordType = newProperty(Flags.READONLY | Flags.HIDDEN, BTypeSpec.make("alarm", "AlarmRecord"), null);

  /**
   * Get the {@code recordType} property.
   * @see #recordType
   */
  public BTypeSpec getRecordType() { return (BTypeSpec)get(recordType); }

  /**
   * Set the {@code recordType} property.
   * @see #recordType
   */
  public void setRecordType(BTypeSpec v) { set(recordType, v, null); }

  //endregion Property "recordType"

  //region Property "schema"

  /**
   * Slot for the {@code schema} property.
   * @see #getSchema
   * @see #setSchema
   */
  public static final Property schema = newProperty(Flags.HIDDEN | Flags.READONLY, BAlarmSchema.DEFAULT, null);

  /**
   * Get the {@code schema} property.
   * @see #schema
   */
  public BAlarmSchema getSchema() { return (BAlarmSchema)get(schema); }

  /**
   * Set the {@code schema} property.
   * @see #schema
   */
  public void setSchema(BAlarmSchema v) { set(schema, v, null); }

  //endregion Property "schema"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmDbConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BAlarmDbConfig()
  {
  }

  /**
   * Don't display in NavTree
   */
  @Override
  public boolean isNavChild()
  {
    return false;
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
      BAlarmRecord rec = makeRecord();
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
  public BAlarmRecord makeRecord()
    throws AlarmException
  {
    BTypeSpec recordType = getRecordType();
    return makeRecord(recordType);
  }

  /**
   * Create an instance of the specified type.
   */
  private static BAlarmRecord makeRecord(BTypeSpec typeSpec)
  {
    Type recordType = typeSpec.getResolvedType();
    return (BAlarmRecord)recordType.getInstance();
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
    throws IOException, AlarmException
  {
    if (recordSize == -1)
    {
      try
      {
        BAlarmRecord rec = makeRecord();
        if (!rec.isFixedSize())
          throw new UnsupportedOperationException
            ("A fixed record size cannot be determined for" +
             " an alarm database with variable length records.");
        
        recordSize = rec.getRecordSize();
      }
      catch(ModuleException e)
      {
        throw new AlarmException(e.getMessage(), e);
      }
      catch(TypeException e)
      {
        throw new AlarmException(e.getMessage(), e);
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
      if (getSchema().equals(BAlarmSchema.DEFAULT))
        setSchema(makeRecord().getSchema());
    }
    
    if (!isRunning()) return;

    BComplex parent = getParent();
    if (parent instanceof BAlarmService)
      ((BAlarmService)parent).alarmDbConfigChanged(p);
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
    if (parent instanceof BAlarmService)
      ((BAlarmService)parent).alarmDbConfigChanged(p);
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
    if (parent instanceof BAlarmService)
      ((BAlarmService)parent).alarmDbConfigChanged(p);
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
    if (parent instanceof BAlarmService)
      ((BAlarmService)parent).alarmDbConfigChanged(p);
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
    if (parent instanceof BAlarmService)
      ((BAlarmService)parent).alarmDbConfigChanged(slot.asProperty());
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
    if (parent instanceof BAlarmService)
      ((BAlarmService)parent).alarmDbConfigChanged(slot.asProperty());
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private int recordSize = -1;
  private BAlarmRecord prototype;
}
