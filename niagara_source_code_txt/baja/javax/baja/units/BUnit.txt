/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.units;

import java.io.*;
import java.util.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;
import javax.baja.data.*;

/**
 * BUnit encapsulates a unit of measurement.  A BUnit is
 * composed of a unitName which must be unique within the
 * VM.  It may optionally have a symbol which may be an
 * abbreviation or shorthand notation for the unit.  Every
 * BUnit is mapped to an instance of BDimension which
 * specifies the units ratio of the seven base SI units.
 * Lastly each BUnit contains a scale and offset which is
 * used to normalize the BUnit to the unit represented
 * by the BDimension.  Non-linear unit conversion are currently 
 * not supported.
 * <p>
 * Normalization equations:
 * <pre>
 *   unit = dimension * scale + offset
 *   toNormal = scalar * scale + offset
 *   fromNormal = (scalar - offset) / scale
 *   toUnit = fromNormal( toUnit.toNormal(scalar) )
 *   toUnit = ((scalar*thisScale + thisOffset) - toOffset) / toScale
 * </pre>
 * <p>
 * Serialization format:
 * <pre>
 *   unit          = unitName symbol dimension scaleOffset
 *   unitName      = string ";"
 *   symbol        = ("" | string) ";"
 *   dimension     = dimension.encodeToString ";"
 *   scaleOffset   = ["*" scale] ["+" offset] ";"
 *   scale         = double
 *   offset        = double
 *
 * Examples:
 *   kilometer;km;(m);*1000;
 *   meters/second;;(m)(s-1);;
 * </pre>
 *
 * @author    Brian Frank
 * @creation  17 Dec 01
 * @version   $Revision: 17$ $Date: 11/13/09 4:47:24 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BUnit
  extends BSimple
  implements BIDataValue
{ 

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Get an existing unit by name.  This requires that
   * the unit already created with through a call to
   * make() or by loading the UnitDatabase.  This method
   * throws UnitException if the specified unit does
   * not exist.
   */
  public static BUnit getUnit(String unitName)
  {
    BUnit unit = cache.get(unitName);
    
    if (unit == null)
    {
      // This is tricky because the UnitDatabase calls BUnit.getUnit() in
      // UnitDatabase.getUnit().  If we're not careful we can easily
      // get into infinite recursion when the unitName is truly undefined.
    
      // force the database to load
      UnitDatabase.getDefault();
      
      // recheck the cache after the load
      unit = cache.get(unitName);
      
      // now just bail if it's still null
      if (unit == null) throw new UnitException("Unknown unit: " + unitName);
    }
    
    return unit;
  }

  /**
   * Make a unit: symbol defaults to unitName, 
   * scale defaults to 1, offset defaults to 0.
   */
  public static BUnit make(String unitName, BDimension dimension)
  {
    return make(unitName, null, dimension, 1d, 0d);
  }

  /**
   * Make a unit: scale defaults to 1, offset defaults to 0.
   */
  public static BUnit make(String unitName, String symbol, BDimension dimension)
  {
    return make(unitName, symbol, dimension, 1d, 0d);
  }

  /**
   * Make a unit: offset defaults to 0.
   */
  public static BUnit make(String unitName, String symbol, BDimension dimension, double scale)
  {
    return make(unitName, symbol, dimension, scale, 0d);
  }

  /**
   * Make a unit: symbol defaults to unitName, offset defaults to 0.
   */
  public static BUnit make(String unitName, BDimension dimension, double scale)
  {
    return make(unitName, null, dimension, scale, 0d);
  }

  /**
   * Factory which accepts all components of a BUnit.
   *
   * @param unitName Unique name which identifies this unit.
   *   The name may contain any character but ';' or '"'.
   * @param symbol Abbreviated notation for unit.  The symbol may 
   *   contain any character but ';'.
   * @param dimension Mathematical ratio of the seven base
   *   SI units (plus cost).  The dimension serves as the
   *   normalization unit and to identify like quantities.
   * @param scale This is the multiplier to convert from
   *   normal to this unit.  For instance km would have a 
   *   scale of 1000 since you must multiply meter (the 
   *   dimension)by 1000 to get 1 km.  Likewise the scale for 
   *   mm is .001.  A value of 1 indicates no scale.
   * @param offset Rarely used, but specifies a scalar to
   *   add to the dimension to get this unit.  Celsius has
   *   an offset of 273.15 from its dimension Kelvin.  A value 
   *   of 0 indicates no offset.
   *
   * @throws UnitException if the specified unitName already
   *   maps to another BUnit with a different symbol, dimension, 
   *   scale or offset.
   */
  public static BUnit make(String unitName, String symbol, BDimension dimension, 
                           double scale, double offset)
  {
    return make(unitName, symbol, dimension, scale, offset, false);
  }

  /**
   * Factory which accepts all components of a BUnit.
   *
   * @param unitName Unique name which identifies this unit.
   *   The name may contain any character but ';' or '"'.
   * @param symbol Abbreviated notation for unit.  The symbol may 
   *   contain any character but ';'.
   * @param dimension Mathematical ratio of the seven base
   *   SI units (plus cost).  The dimension serves as the
   *   normalization unit and to identify like quantities.
   * @param scale This is the multiplier to convert from
   *   normal to this unit.  For instance km would have a 
   *   scale of 1000 since you must multiply meter (the 
   *   dimension)by 1000 to get 1 km.  Likewise the scale for 
   *   mm is .001.  A value of 1 indicates no scale.
   * @param offset Rarely used, but specifies a scalar to
   *   add to the dimension to get this unit.  Celsius has
   *   an offset of 273.15 from its dimension Kelvin.  A value 
   *   of 0 indicates no offset.
   * @param prefix Is this unit a prefix to the value, otherwise
   *   is assumed follow the value when output as a text string. 
   *
   * @throws UnitException if the specified unitName already
   *   maps to another BUnit with a different dimension.
   *   
   * @since Niagara 3.7
   */  
  public static BUnit make(String unitName, String symbol, BDimension dimension, 
                           double scale, double offset, boolean prefix)
  {
    if (unitName == null || dimension == null) 
      throw new NullPointerException();
      
    if (unitName.indexOf(';') >= 0)
      throw new UnitException("Invalid unitName: " + unitName);
    
    if (symbol == null) 
      symbol = unitName;
    else if (symbol.indexOf(';') >= 0)
      throw new UnitException("Invalid symbol: " + symbol);
      
    synchronized(cache)
    {
      BUnit unit = cache.get(unitName);
      if (unit != null)
      {     
        if (/*!unit.symbol.equals(symbol) ||*/
            !unit.dimension.equals(dimension))// ||
//            unit.scale != scale ||
//            unit.offset != offset)
          throw new UnitException("Duplicate unit is incompatible: " + unitName);
          
        return unit;  
      }
    
      unit = new BUnit();
      unit.unitName = unitName;
      unit.symbol = symbol;
      unit.dimension = dimension;
      unit.scale = scale;
      unit.offset = offset;
      unit.prefix = prefix;
      
      cache.put(unitName, unit);
      
      return unit;
    }
  }
  
  private BUnit() {}

  private static final Map<String, BUnit> cache = new HashMap<>();
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the unit's dimension.
   */
  public final BDimension getDimension()
  {
    return dimension;
  }
  
  /**
   * Get the unit name which uniquely identifies 
   * this BUnit instance in the VM.
   */
  public final String getUnitName()
  {
    return unitName;
  }

  /**
   * Get the unit name using the specified Context.
   */
  public final String getUnitName(Context context)
  {
    return unitName;
  }

  /**
   * Get the symbol to use as an abbreviation for the
   * unit.  If no symbol was specified, this defaults
   * to use the unit name.
   */
  public final String getSymbol()
  {
    return symbol;
  }

  /**
   * Get the symbol using the specified Context.
   */
  public final String getSymbol(Context context)
  {
    return symbol;
  }
  
  /**
   * Get the scale which is used multiply the 
   * dimension to get this unit:
   * <pre>
   *   unit = dimension * scale + offset
   * </pre>
   */
  public final double getScale()
  {
    return scale;
  }

  /**
   * Get the offset which is used to add to the 
   * dimension to get this unit:
   * <pre>
   *   unit = dimension * scale + offset
   * </pre>
   */
  public final double getOffset()
  {
    return offset;
  }

  /**
   * Get whether this unit should be displayed prior
   * to the numeric value.  Otherwise it is displayed after. 
   * @since Niagara 3.7
   */
  public final boolean getIsPrefix()
  {
    return prefix;
  }

////////////////////////////////////////////////////////////////
// Conversion
////////////////////////////////////////////////////////////////  

  /**
   * Is this unit convertible to the specified unit.
   * Convertible units must have identical dimensions.
   */
  public final boolean isConvertible(BUnit unit)
  {
    return dimension.equals(unit.dimension);
  }
  
  /**
   * Given a scalar measurement of this unit, convert
   * it to its normal value which is a conversion from
   * this unit to its dimension (scale=1, offset=0):
   * <pre>
   *   toNormal = scalar*scale + offset
   * </pre>
   */
  public final double toNormal(double scalar)
  {
    return scalar*scale + offset;
  }
  
  /**
   * Given a scalar measurement normalized to the dimension
   * unit (scale=1, offset=0), convert it to a measurement
   * in this unit:
   * <pre>
   *   fromNormal = (scalar - offset)/scale
   * </pre>
   */
  public final double fromNormal(double scalar)
  {
    return (scalar - offset)/scale;
  }
  
  /**
   * Convert a scalar in this unit to a scalar in the
   * specified unit:
   * <pre>
   *   toUnit = fromNormal( toUnit.toNormal(scalar) )
   *   toUnit = ((scalar*thisScale + thisOffset) - toOffset) / toScale
   * </pre>
   *
   * @throws UnitException is the this unit is not convertible
   *   to the specified unit (they must have same dimension).
   */
  public final double convertTo(BUnit toUnit, double scalar)
  {
    if (!dimension.equals(toUnit.dimension))
      throw new UnitException("Not convertible: " + this + " -> " + toUnit);
    return ((scalar*scale + offset) - toUnit.offset)/toUnit.scale;
  }
  
  /**
   * Converts an absolute unit to a differential unit.
   * This is useful for units like "celsius" where
   * a differential is not the same as an absolute value
   * due to a non-zero offset.
   * 
   * If the unit is already a differential, or the
   * differential is the same, returns the unit.
   */
  public BUnit getDifferentialUnit()
  {
    return UnitDifferentialConverter.getInstance().getDifferential(this);
  }
  
  /**
   * Converts a differential unit to an absolute unit.
   * This is useful for units like "celsius" where
   * a differential is not the same as an absolute value
   * due to a non-zero offset.
   * 
   * If the unit is already an absolute, or the
   * absolute is the same, returns the unit.
   */
  public BUnit getAbsoluteUnit()
  {
    return UnitDifferentialConverter.getInstance().getAbsolute(this);
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * Is this instance the NULL unit.
   */
  @Override
  public final boolean isNull()
  {
    return this == NULL;
  }
  
  /**
   * To string returns the unit's symbol.
   */
  @Override
  public String toString(Context context)
  {
    return getSymbol(context);
  }
  
  /**
   * BUnit hashcode.
   */
  public final int hashCode()
  {
    return unitName.hashCode();
  }
  
  /**
   * BUnit equality.
   */
  public final boolean equals(Object obj)
  {
    // since the factory method guarantees
    // normalized instances we can use "=="
    return this == obj;
  }

  /**
   * BUnit is serialized using writeUTF(encodeToString()).
   */
  @Override
  public final void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }
  
  /**
   * BUnit is unserialized using decodeFromString(readUTF()).
   */
  @Override
  public final BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString(in.readUTF());
  }
  
  /**
   * Encode the unit in its string format.
   */               
  @Override
  public final String encodeToString()
  {
    if (string == null)
    {
      StringBuilder s = new StringBuilder();
      s.append(unitName).append(';');
      if (symbol != unitName) s.append(symbol); s.append(';');
      s.append(dimension.encodeToString()).append(';');
      if (scale != 1) s.append('*').append(scale);
      if (offset != 0) s.append('+').append(offset);
      s.append(';');
      string = s.toString();
    }
    return string;
  }

  /**
   * Decode the unit in its string format.
   */
  @Override
  public final BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      int x = 0, y = s.indexOf(';');
      String unitName = s.substring(x, y);
      
      x = y; y = s.indexOf(';', y+1);
      String symbol = (x+1 == y) ? null : s.substring(x+1, y);
      
      x = y; y = s.indexOf(';', y+1);
      BDimension dimension = (BDimension)this.dimension.decodeFromString(s.substring(x+1, y));
      
      x = y; y = s.indexOf(';', y+1);
      String eq = s.substring(x+1, y);
      double scale = 1;
      double offset = 0;
      int eqLength = eq.length();
      if (eqLength > 0)
      {
        int z = 0;
        if (eq.charAt(0) == '*')
        {
          z = eq.indexOf('+'); if (z < 0) z = eqLength;
          scale = Double.parseDouble(eq.substring(1, z));
        }
        if (z < eqLength && eq.charAt(z) == '+')
        {
          offset = Double.parseDouble(eq.substring(z+1, eqLength));
        }
      }
     
      return make(unitName, symbol, dimension, scale, offset);
    }
    catch(Throwable e)
    {
      e.printStackTrace();
      throw new IOException(s);      
    }
  }

  /**
   * Return this instance since it's already a data value.
   */
  @Override
  public BIDataValue toDataValue() { return this; }
  
////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  /**
   * The null unit.
   */
  public static final BUnit NULL = make("null", BDimension.DEFAULT);
  
  /**
   * The default unit is the NULL.
   */
  public static final BUnit DEFAULT = NULL;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUnit.class);
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private BDimension dimension;
  private String unitName;
  private String symbol;
  private double scale;
  private double offset;
  private String string;
  private boolean prefix;
  
  UnitDatabase.Quantity quantity;
  
}
