/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history.ext;

import java.io.IOException;

import javax.baja.control.BNumericPoint;
import javax.baja.history.BHistoryConfig;
import javax.baja.history.BNumericTrendRecord;
import javax.baja.history.BRolloverValue;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.history.BNumeric64BitTrendRecord;

/**
 * This is an extension for collecting numeric values on a fixed interval.
 *
 * @author    John Sublett
 * @creation  23 Nov 2004
 * @version   $Revision: 5$ $Date: 6/2/05 4:17:10 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Specifies the precision of the persistently stored numeric values.
 */
@NiagaraProperty(
  name = "precision",
  type = "int",
  defaultValue = "32",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"history:PrecisionFE\"), BFacets.UX_FIELD_EDITOR, BString.make(\"history:PrecisionEditor\"))")
)
/*
 Specifies the minimum value for this logged point if applicable.
 Used as a reference when reporting delta log values.
 */
@NiagaraProperty(
  name = "minRolloverValue",
  type = "BRolloverValue",
  defaultValue = "new BRolloverValue()"
)
/*
 Specifies the maximum value for this logged point if applicable.
 Used as a reference when reporting delta log values.
 */
@NiagaraProperty(
  name = "maxRolloverValue",
  type = "BRolloverValue",
  defaultValue = "new BRolloverValue()"
)
public class BNumericIntervalHistoryExt
  extends BIntervalHistoryExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.ext.BNumericIntervalHistoryExt(969909977)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "precision"

  /**
   * Slot for the {@code precision} property.
   * Specifies the precision of the persistently stored numeric values.
   * @see #getPrecision
   * @see #setPrecision
   */
  public static final Property precision = newProperty(0, 32, BFacets.make(BFacets.FIELD_EDITOR, BString.make("history:PrecisionFE"), BFacets.UX_FIELD_EDITOR, BString.make("history:PrecisionEditor")));

  /**
   * Get the {@code precision} property.
   * Specifies the precision of the persistently stored numeric values.
   * @see #precision
   */
  public int getPrecision() { return getInt(precision); }

  /**
   * Set the {@code precision} property.
   * Specifies the precision of the persistently stored numeric values.
   * @see #precision
   */
  public void setPrecision(int v) { setInt(precision, v, null); }

  //endregion Property "precision"

  //region Property "minRolloverValue"

  /**
   * Slot for the {@code minRolloverValue} property.
   * Specifies the minimum value for this logged point if applicable.
   * Used as a reference when reporting delta log values.
   * @see #getMinRolloverValue
   * @see #setMinRolloverValue
   */
  public static final Property minRolloverValue = newProperty(0, new BRolloverValue(), null);

  /**
   * Get the {@code minRolloverValue} property.
   * Specifies the minimum value for this logged point if applicable.
   * Used as a reference when reporting delta log values.
   * @see #minRolloverValue
   */
  public BRolloverValue getMinRolloverValue() { return (BRolloverValue)get(minRolloverValue); }

  /**
   * Set the {@code minRolloverValue} property.
   * Specifies the minimum value for this logged point if applicable.
   * Used as a reference when reporting delta log values.
   * @see #minRolloverValue
   */
  public void setMinRolloverValue(BRolloverValue v) { set(minRolloverValue, v, null); }

  //endregion Property "minRolloverValue"

  //region Property "maxRolloverValue"

  /**
   * Slot for the {@code maxRolloverValue} property.
   * Specifies the maximum value for this logged point if applicable.
   * Used as a reference when reporting delta log values.
   * @see #getMaxRolloverValue
   * @see #setMaxRolloverValue
   */
  public static final Property maxRolloverValue = newProperty(0, new BRolloverValue(), null);

  /**
   * Get the {@code maxRolloverValue} property.
   * Specifies the maximum value for this logged point if applicable.
   * Used as a reference when reporting delta log values.
   * @see #maxRolloverValue
   */
  public BRolloverValue getMaxRolloverValue() { return (BRolloverValue)get(maxRolloverValue); }

  /**
   * Set the {@code maxRolloverValue} property.
   * Specifies the maximum value for this logged point if applicable.
   * Used as a reference when reporting delta log values.
   * @see #maxRolloverValue
   */
  public void setMaxRolloverValue(BRolloverValue v) { set(maxRolloverValue, v, null); }

  //endregion Property "maxRolloverValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNumericIntervalHistoryExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Extensions may only be placed in BControlPoints.
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BNumericPoint;
  }

  /**
   * This extension writes numeric trend records. (history:NumericTrendRecord)
   */
  @Override
  public Type getRecordType()
  {
    if (getPrecision() == 64)
      return BNumeric64BitTrendRecord.TYPE;
    else
      return BNumericTrendRecord.TYPE;
  }

  /**
   * Init on start.
   */
  @Override
  public void started()
    throws Exception
  {
    super.started();
    if (getPrecision() == 64)
      rec = new BNumeric64BitTrendRecord();
    else
      rec = new BNumericTrendRecord();
    syncProperties();
  }

  /**
   * Handle a property change.
   */
  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (cx == Context.decoding) return;
    if (!isRunning()) return;

    if (p.equals(minRolloverValue))
    {
      BHistoryConfig config = getHistoryConfig();
      Property prop = config.loadSlots().getProperty(minRolloverValue.getName());
      if (prop == null)
        config.add(minRolloverValue.getName(), getMinRolloverValue().newCopy(), Flags.READONLY);
      else
        config.set(prop, getMinRolloverValue().newCopy());
    }
    else if (p.equals(maxRolloverValue))
    {
      BHistoryConfig config = getHistoryConfig();
      Property prop = config.loadSlots().getProperty(maxRolloverValue.getName());
      if (prop == null)
        config.add(maxRolloverValue.getName(), getMaxRolloverValue().newCopy(), Flags.READONLY);
      else
        config.set(prop, getMaxRolloverValue().newCopy());
    }
    else if (p.equals(precision))
    {
      BHistoryConfig config = getHistoryConfig();
      Property prop = config.loadSlots().getProperty(precision.getName());
      if (prop == null)
        config.add(
          precision.getName(),
          BInteger.make(getPrecision()),
          Flags.READONLY,
          BFacets.make(
            BFacets.FIELD_EDITOR, BString.make("history:PrecisionFE"),
            BFacets.UX_FIELD_EDITOR, BString.make("history:PrecisionEditor")
          ),
          null);
      else
        config.set(prop, BInteger.make(getPrecision()));

      if (getPrecision() == 64)
        rec = new BNumeric64BitTrendRecord();
      else
        rec = new BNumericTrendRecord();

      if (!config.getRecordType().equals(getRecordType().getTypeSpec()))
        config.setRecordType(getRecordType().getTypeSpec());
    }
  }

  /**
   * Write a record for the specified timestamp and value.
   */
  @Override
  protected void writeRecord(BAbsTime timestamp, BStatusValue out)
    throws IOException
  {
    append(rec.set(timestamp,
                   ((BStatusNumeric)out).getValue(),
                   out.getStatus()));
  }

  /**
   * Synchronize the extension's history config with the special extension
   * properties (Min/Max Rollover Values).
   */
  private void syncProperties()
  {
    BHistoryConfig config = getHistoryConfig();

    Property prop = config.loadSlots().getProperty(minRolloverValue.getName());
    if (prop == null)
      config.add(minRolloverValue.getName(), getMinRolloverValue().newCopy(), Flags.READONLY);
    else
    {
      BRolloverValue configMin = (BRolloverValue)config.get(prop);
      if (!configMin.equivalent(getMinRolloverValue()))
        config.set(prop, getMinRolloverValue().newCopy());
    }

    prop = config.loadSlots().getProperty(maxRolloverValue.getName());
    if (prop == null)
      config.add(maxRolloverValue.getName(), getMaxRolloverValue().newCopy(), Flags.READONLY);
    else
    {
      BRolloverValue configMax = (BRolloverValue)config.get(prop);
      if (!configMax.equivalent(getMaxRolloverValue()))
        config.set(prop, getMaxRolloverValue().newCopy());
    }

    prop = config.loadSlots().getProperty(precision.getName());
    if (prop == null)
      config.add(
        precision.getName(),
        BInteger.make(getPrecision()),
        Flags.READONLY,
        BFacets.make(
          BFacets.FIELD_EDITOR, BString.make("history:PrecisionFE"),
          BFacets.UX_FIELD_EDITOR, BString.make("history:PrecisionEditor")
        ),
        null);
    else
    {
      BInteger configPrecision = (BInteger)config.get(prop);
      if (configPrecision.getInt() != getPrecision())
        config.set(prop, BInteger.make(getPrecision()));
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BNumericTrendRecord rec;



}
