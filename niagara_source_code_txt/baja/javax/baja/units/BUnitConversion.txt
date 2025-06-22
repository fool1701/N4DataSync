/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.units;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.xml.XElem;
import javax.baja.xml.XParser;

import com.tridium.sys.Nre;

/**
 * BUnitConversion defines a desired unit system for display.
 *
 * @author    Brian Frank
 * @creation  14 Sep 04
 * @version   $Revision: 2$ $Date: 3/28/05 9:23:15 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("none"),
    @Range("metric"),
    @Range("english")
  }
)
public final class BUnitConversion
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.units.BUnitConversion(2578340442)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for none. */
  public static final int NONE = 0;
  /** Ordinal value for metric. */
  public static final int METRIC = 1;
  /** Ordinal value for english. */
  public static final int ENGLISH = 2;

  /** BUnitConversion constant for none. */
  public static final BUnitConversion none = new BUnitConversion(NONE);
  /** BUnitConversion constant for metric. */
  public static final BUnitConversion metric = new BUnitConversion(METRIC);
  /** BUnitConversion constant for english. */
  public static final BUnitConversion english = new BUnitConversion(ENGLISH);

  /** Factory method with ordinal. */
  public static BUnitConversion make(int ordinal)
  {
    return (BUnitConversion)none.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BUnitConversion make(String tag)
  {
    return (BUnitConversion)none.getRange().get(tag);
  }

  /** Private constructor. */
  private BUnitConversion(int ordinal)
  {
    super(ordinal);
  }

  public static final BUnitConversion DEFAULT = none;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUnitConversion.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * What is the desired unit to use for the specified unit.
   * If conversion is none then always return the specified unit.
   * If conversion is metric, return suitable metric unit.
   * If conversion is english, return suitable english unit.
   */
  public BUnit getDesiredUnit(BUnit unit)
  {
    if (unit == null) return null;
    if (this == none) return unit;

    if (!loaded) load();

    if (this == metric)
    {
      BUnit desired = byEnglish.get(unit.getUnitName());
      if (desired != null) return desired;
    }

    else if (this == english)
    {
      BUnit desired = byMetric.get(unit.getUnitName());
      if (desired != null) return desired;
    }

    return unit;
  }

////////////////////////////////////////////////////////////////
// Load
////////////////////////////////////////////////////////////////

  static void load()
  {
    if (loaded) return;
    loaded = true;

    List<Entry> entries;

    try
    {
      XElem root;

      //NCCB-10370: Open unit conversion in privileged scope
      try
      {
        root = AccessController.doPrivileged((PrivilegedExceptionAction<XElem>)() ->
          XParser.make(Nre.bootEnv.read("/defaults/unitConversion.xml")).parse());
      }
      catch(PrivilegedActionException pae)
      {
        throw pae.getException();
      }

      XElem[] converts = root.elems();
      entries = new ArrayList<>(converts.length);
      for (XElem x : converts)
      {
        try
        {
          BUnit metric = UnitDatabase.getUnit(x.get("metric"));
          BUnit english = UnitDatabase.getUnit(x.get("english"));
          byMetric.put(metric.getUnitName(), english);
          byEnglish.put(english.getUnitName(), metric);
          entries.add(new Entry(metric, english));
        }
        catch (Exception e)
        {
          Logger.getLogger("sys.unitConversion").log(Level.WARNING, "Parsing convert [line " + x.line() + "]", e);
        }
      }
    }
    catch(Exception e)
    {
      entries = new ArrayList<>();
      Logger.getLogger("sys.unitConversion").log(Level.SEVERE, "Error parsing unitConversion.xml", e);
    }

    BUnitConversion.entries = Collections.unmodifiableList(entries);
  }

  /**
   * Get a list of all entries contained in the unit conversion database.
   *
   * @return a read-only list of unit conversion entries.
   */
  public static List<Entry> getEntries()
  {
    load();
    return entries;
  }

  /**
   * A single entry in the unit conversion database, defining a relationship
   * between a metric unit and an English unit.
   */
  public static final class Entry
  {
    private BUnit metric;
    private BUnit english;
    Entry(BUnit metric, BUnit english) { this.metric = metric; this.english = english; }

    /** @return the metric unit */
    public BUnit getMetric() { return metric; }

    /** @return the English unit */
    public BUnit getEnglish() { return english; }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static Map<String,BUnit> byMetric = new HashMap<>();
  private static Map<String,BUnit> byEnglish = new HashMap<>();
  private static List<Entry> entries;
  private static volatile boolean loaded = false;
}
