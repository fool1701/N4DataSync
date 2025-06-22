/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.ui;

import java.util.Stack;
import javax.baja.control.BControlPoint;
import javax.baja.driver.point.BProxyExt;
import javax.baja.nre.util.Array;
import javax.baja.sys.BComplex;
import javax.baja.sys.BSimple;
import javax.baja.sys.BStruct;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.util.Lexicon;
import javax.baja.workbench.BWbEditor;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrEditRow;
import com.tridium.ndriver.discover.BINDiscoveryGroup;
import com.tridium.ndriver.util.SfUtil;
import com.tridium.workbench.propsheet.BPropertySheetFE;

/**
 * This class contains utility methods to help the NDeviceManager and the
 * NPointManager automatically generate the MgrColumn arrays that are needed for
 * the database and discovery tables.
 * <p>
 * The method 'getColumnsFor' reviews a prototype database or discovery object
 * and returns an array of MgrColumns objects for use in the database or
 * discovery table's table-model.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
public final class NMgrColumnUtil
{
  private NMgrColumnUtil() {}

////////////////////////////////////////////////////////////////
// NMgrColumnUtil - Public API
////////////////////////////////////////////////////////////////

  /**
   * Populate the MgrColumn[] array for the given sample instance of a database
   * or discovery object.
   */
  public static void getColumnsFor(BComplex c, Array<MgrColumn> mgrColumns)
  {
    Stack<Property> cols = new Stack<>();

    // For proxExt type add its property in the controlPoint that is the database object 
    if (c instanceof BProxyExt)
    {
      cols.add(BControlPoint.proxyExt);
    }

    recurse(c, mgrColumns, cols);
  }

  private static void recurse(BComplex c, Array<MgrColumn> mgrColumns, Stack<Property> cols)
  {
    Property[] pa = c.getPropertiesArray();
    for (int i = 0; i < pa.length; ++i)
    {
      Property prop = pa[i];
      if (!SfUtil.isMgrInclude(prop))
      {
        continue;
      }

      BValue typ = c.get(prop);
      cols.push(prop);
      if (typ instanceof BSimple || (typ instanceof BStruct && hasCustomFE(prop)))
      {
        int fl = SfUtil.getMgrColumnFlags(prop);
        Property[] path = cols.toArray(new Property[0]);
        mgrColumns.add(new NMgrPropPath(path, fl));
      }
      else if (typ instanceof BComplex)
      {
        recurse((BComplex)typ, mgrColumns, cols);
      }
      cols.pop();
    }
  }

  // Check if specified properties default value use a custom field editor
  private static boolean hasCustomFE(Property p)
  {
    BWbFieldEditor fe = BWbFieldEditor.makeFor(p.getDefaultValue());
    return !fe.getType().is(BPropertySheetFE.TYPE);
  }

  /**
   * Converts the given array of properties into a string containing the names
   * of each property separated by period characters.
   * <p>
   * The getMgrColumnName method calls this method to construct a lexicon key.
   * It will look into the driver's lexicon to see if the driver developer
   * customizes the property name.
   */
  static String toDotNotation(Property[] propPath)
  {
    String dotNotation = "";

    for (int i = 0; i < propPath.length; i++)
    {
      if (dotNotation.length() > 0)
      {
        dotNotation += ".";
      }

      dotNotation += propPath[i].getName();
    }

    // Removes "proxyExt." -- helps for the point manager where most NMgrColumn's are prop paths under
    // A control point staring with the control point's proxyExt property
    if (dotNotation.startsWith("proxyExt."))
    {
      dotNotation = dotNotation.substring(9);
    }

    return dotNotation;
  }

  /**
   * Gets the name of the mgr column for the given prop path.
   */
  static String getMgrColumnName(Property[] props)
  {
    String mgrColumnName = props[props.length - 1].getDefaultDisplayName(null);
    String propPathInDotNotation = toDotNotation(props);

    // Issue 12158 - Fix point and device manager lexicon for column names
    for (int i = 0; i < props.length; i++)
    {
      // This uses the lexicon value for the dot notation for the property
      // closest to the end of the given array of props.
      Lexicon propLex = props[i].getDeclaringType().getModule().getLexicon();

      if (propLex != null)
      {
        mgrColumnName = propLex.get(propPathInDotNotation, mgrColumnName);
      }

    }

    return mgrColumnName;
  }


////////////////////////////////////////////////////////////////
// NMgrProp
////////////////////////////////////////////////////////////////

  /**
   * Extend MgrColumn.PropPath to provide special handling of BINDiscoveryGroup
   */
  public static class NMgrPropPath
    extends MgrColumn.PropPath
  {

    public NMgrPropPath(Property props[], int flags)
    {
      super(getMgrColumnName(props), props, flags);
    }

    @Override
    public BWbEditor toEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor)
    {
      return super.toEditor(rows, colIndex, null);
    }

    @Override
    public Object get(Object row)
    {
      // Issue 11725 - 'Discovered' list fails to paint if outer-most level
      // consists of exactly one discovery group.
      if (row instanceof BINDiscoveryGroup)
      {
        // NOTE: For groups, the fw only calls this method to paint 1st col
        return row.toString();
      }
      else
      {
        return super.get(row);
      }
    }

    @Override
    public String toDisplayString(Object row, Object value, Context cx)
    {
      // Issue 11725 - 'Discovered' list paints incorrectly if exactly one
      // discovery item and the item is a discovery group. 
      if (row instanceof BINDiscoveryGroup)
      {
        // NOTE: For groups, the fw only calls this method to paint 1st col
        return row.toString();
      }
      else
      {
        return super.toDisplayString(row, value, cx);
      }
    }
  }
}
