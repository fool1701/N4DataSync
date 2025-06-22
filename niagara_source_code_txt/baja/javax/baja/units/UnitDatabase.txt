/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.units;

import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.xml.XElem;
import javax.baja.xml.XParser;

import com.tridium.sys.Nre;

/**
 * UnitDatabase encapsulates the database of built-in units
 * provided framework.  It is organized into groups of Quanities
 * to make building user interfaces easier.
 *
 * @author    Brian Frank
 * @creation  18 Dec 01
 * @version   $Revision: 10$ $Date: 6/14/07 1:00:46 PM EDT$
 * @since     Baja 1.0
 */
public class UnitDatabase
{

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get a unit from the default database.  If unit does not
   * exist or has not previously been defined using BUnit.make()
   * then throw a UnitException.
   */
  public static BUnit getUnit(String name)
  {
    getDefault();
    return BUnit.getUnit(name);
  }

  /**
   * Load and get the default unit database.  This
   * populates the BUnit table of units from the file
   * "{home}/lib/unit.xml".  Once the database has
   * been loaded the standard units may be accessed by
   * BUnit.getUnit(), or by category by the getQuantities()
   * method.
   */
  public static UnitDatabase getDefault()
  {
    if (db == null)
    {
      db = new UnitDatabase();
      try
      {
        db.load();
      }
      catch(Exception e)
      {
        Logger.getLogger("sys.unitdb").log(Level.SEVERE, "Cannot load database", e);
      }
    }

    return db;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get a copy of the Quantity groups specified
   * in the unit database.
   */
  public Quantity[] getQuantities()
  {
    Quantity[] copy = new Quantity[quantities.length];
    System.arraycopy(quantities, 0, copy, 0, copy.length);
    return copy;
  }

  /**
   * Get the Quantity which contains the specified
   * unit or return null if the unit is not contained
   * by the database.
   */
  public Quantity getQuantity(BUnit unit)
  {
    return unit.quantity;
  }

////////////////////////////////////////////////////////////////
// Dump
////////////////////////////////////////////////////////////////

  /**
   * Dump the database to standard output for debugging.
   */
  public void dump()
  {
    Quantity[] q = getQuantities();
    for(int i=0; i<q.length; ++i)
    {
      System.out.println(q[i].getName());
      BUnit[] u = q[i].getUnits();
      for(int j=0; j<u.length; ++j)
        System.out.println("   " + u[j].encodeToString());
    }
  }

////////////////////////////////////////////////////////////////
// Load
////////////////////////////////////////////////////////////////

  private void load()
    throws Exception
  {
    AccessController.doPrivileged(new LoadUnitsPrivilegedAction());
  }

  private class LoadUnitsPrivilegedAction
    implements PrivilegedExceptionAction<Object>
  {
    @Override
    public Object run() throws Exception
    {
      // NOTE - the platform:com.tridium.install.BUnitDatabaseMerge class,
      // which merges 2 versions of a units.xml file, may be affected if
      // changes to the file schema are made here.

      XElem unitdb = XParser.make(Nre.bootEnv.read("/defaults/units.xml")).parse();

      XElem[] q = unitdb.elems("quantity");
      quantities = new Quantity[q.length];
      for(int i=0; i<q.length; ++i)
      {
        String qname = q[i].get("n");
        BDimension dim = null;
        try
        {
          dim = (BDimension)BDimension.DEFAULT.decodeFromString(q[i].get("dim"));
        }
        catch(Exception e)
        {
          throw new Exception("Error parsing quanitity \"" + qname + "\" [line " + q[i].line() + "] " + e);
        }

        XElem[] u = q[i].elems("unit");
        BUnit[] units = new BUnit[u.length];
        for(int j=0; j<u.length; ++j) units[j] = parseUnit(dim, u[j]);

        quantities[i] = new Quantity(qname, dim, units);
        for(int j=0; j<units.length; ++j)
          if (units[j] != null) units[j].quantity = quantities[i];
      }

      return  null;
    }
  }

  private BUnit parseUnit(BDimension dim, XElem u)
    throws Exception
  {
    String name = u.get("n");
    try
    {
      String sym = u.get("s", null);

      double scale = 1;
      String scaleStr = u.get("scale", null);
      if (scaleStr != null) scale = Double.parseDouble(scaleStr);

      double offset = 0;
      String offsetStr = u.get("offset", null);
      if (offsetStr != null) offset = Double.parseDouble(offsetStr);

      boolean prefix = false;
      String prefixStr = u.get("prefix", null);
      if (prefixStr != null) prefix = prefixStr.equalsIgnoreCase("true");

      return BUnit.make(name, sym, dim, scale, offset, prefix);
    }
    catch(Exception e)
    {
      Logger.getLogger("sys.unitdb").log(Level.WARNING, "Error parsing unit \"" + name + "\" [line " + u.line() + "] ", e);
      return null;
    }
  }

////////////////////////////////////////////////////////////////
// Quanity
////////////////////////////////////////////////////////////////

  /**
   * The Quantity class is used to group a set of
   * BUnits which measure a like quantity.
   */
  public static class Quantity
  {
    Quantity(String name, BDimension dim, BUnit[] units)
    {
      this.name = name;
      this.dim = dim;
      this.units = units;
    }

    /**
     * Get the name of the quantity.
     */
    public String getName()
    {
      return name;
    }

    /**
     * Get the dimension of the quantity.
     * @since Niagara 4.13
     */
    public BDimension getDimension()
    {
      return dim;
    }

    /**
     * Get a list of the units grouped by this quantity.
     */
    public BUnit[] getUnits()
    {
      BUnit[] copy = new BUnit[units.length];
      System.arraycopy(units, 0, copy, 0, copy.length);
      return copy;
    }

    /**
     * To string.
     */
    @Override
    public String toString()
    {
      return name + " (" + dim + ")";
    }

    final String name;
    final BDimension dim;
    final BUnit[] units;
  }

////////////////////////////////////////////////////////////////
// Obix Convertor
////////////////////////////////////////////////////////////////

  /*
  public static void main(String[] args)
    throws Exception
  {
    UnitDatabase db = getDefault();

    XWriter out = new XWriter(new java.io.FileOutputStream("units.obix"));
    out.prolog();
    out.w("\n<!-- Standard oBIX Unit Database -->\n\n");
    out.w("<obj>\n");

    for(int i=0; i<db.quantities.length; ++i)
    {
      Quantity q = db.quantities[i];
      String qn = q.name.replace(' ', '_');
      BDimension dim = q.dim;
      String is = "obix:Unit";
      out.w("\n<!-- ").safe(q.name).w(" -->\n");
      if (!dim.equals(BDimension.DEFAULT))
      {
        is = "obix:units/"+qn + " " + is;
        out.w("\n<obj href=\"obix:units/").safe(qn).w("\" is=\"obix:Unit\" display=\"").w(q.name).w("\">\n");
        out.w("  <obj is=\"obix:Dimension\">\n");
        if (dim.getKilogram() != 0) out.w("    <int name=\"kg\"  val=\"" + dim.getKilogram() + "\"/>\n");
        if (dim.getMeter() != 0)    out.w("    <int name=\"m\"   val=\"" + dim.getMeter() + "\"/>\n");
        if (dim.getSecond() != 0)   out.w("    <int name=\"sec\" val=\"" + dim.getSecond() + "\"/>\n");
        if (dim.getKelvin() != 0)   out.w("    <int name=\"K\"   val=\"" + dim.getKelvin() + "\"/>\n");
        if (dim.getAmpere() != 0)   out.w("    <int name=\"A\"   val=\"" + dim.getAmpere() + "\"/>\n");
        if (dim.getMole() != 0)     out.w("    <int name=\"mol\" val=\"" + dim.getMole() + "\"/>\n");
        if (dim.getCandela() != 0)  out.w("    <int name=\"cd\"  val=\"" + dim.getCandela() + "\"/>\n");
        out.w("  </obj>\n");
        out.w("</obj>\n");
      }

      for(int j=0; j<q.units.length; ++j)
      {
        BUnit u = q.units[j];
        String name = u.getUnitName();
        String xname = name.replace(' ', '_');

        out.w("\n<obj href=\"obix:units/").safe(xname).w("\" is=\"").w(is).w("\" display=\"").safe(name).w("\">\n");
        if (!u.getSymbol().equals(name)) out.w("  <str  name=\"symbol\" val=\"").safe(u.getSymbol()).w("\"/>\n");
        if (u.getScale() != 1)  out.w("  <real name=\"scale\"  val=\"").w(u.getScale()).w("\"/>\n");
        if (u.getOffset() != 0) out.w("  <real name=\"offset\" val=\"").w(u.getOffset()).w("\"/>\n");
        out.w("</obj>\n");
      }
    }

    out.w("</obj>\n");
    out.close();
  }
  */

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static UnitDatabase db;

  private Quantity[] quantities;

}
