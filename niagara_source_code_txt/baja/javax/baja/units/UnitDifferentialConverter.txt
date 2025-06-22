/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.units;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Map;
import javax.baja.log.Log;
import javax.baja.xml.XElem;
import javax.baja.xml.XParser;
import com.tridium.sys.Nre;

/**
 * 
 * UnitDifferentialConverter provides a mapping
 * between the differential and absolute versions
 * of a unit.
 * E.g. temperature and temperature differential
 * 
 * Accessible via UnitDifferentialConvert.INSTANCE
 *
 * @author Melanie Coggan
 * @creation May 2, 2013
 *
 */
public final class UnitDifferentialConverter
{
////////////////////////////////////////////////////////////////
// Private Constructor
////////////////////////////////////////////////////////////////
  private UnitDifferentialConverter()
  {
    try
    {
      XElem root = null;

      //NCCB-9976: Open unit conversion in privileged scope
      try
      {
        root = AccessController.doPrivileged((PrivilegedExceptionAction<XElem>)() ->
          XParser.make(Nre.bootEnv.read("/defaults/unitDifferentialConversion.xml")).parse());
      }
      catch(PrivilegedActionException pae)
      {
        throw pae.getException();
      }

      XElem[] converts = root.elems();
      for(int i=0; i<converts.length; ++i)
      {
        XElem x = converts[i];  
        try
        {            
          BUnit differential  = UnitDatabase.getUnit(x.get("differential"));
          BUnit absolute = UnitDatabase.getUnit(x.get("absolute"));    
          byDifferential.put(differential.getUnitName(), absolute);
          byAbsolute.put(absolute.getUnitName(), differential);
        }
        catch(Exception e)
        {
          Log.getLog("sys.unitConversion").warning("Parsing convert [line " + x.line() + "]", e);
        }
      }
    }
    catch(Exception e)
    {                              
      Log.getLog("sys.unitConversion").error("Error parsing unitDifferentialConversion.xml", e);
    }
  }
  
////////////////////////////////////////////////////////////////
// Accessors
////////////////////////////////////////////////////////////////
  /** 
   * Returns the differential unit corresponding
   * to the provided unit. If the unit is already
   * a differential or the differential is the 
   * same, returns the provided unit.
   */
  public BUnit getDifferential(BUnit absolute)
  {
    BUnit differential = byAbsolute.get(absolute.getUnitName());
    
    if (differential != null)
      return differential;
    else
      return absolute;
  }
  
  /** 
   * Returns the absolute unit corresponding
   * to the provided unit. If the unit is already
   * absolute or the absolute is the 
   * same, returns the provided unit.
   */
  public BUnit getAbsolute(BUnit differential)
  {
    BUnit absolute = byDifferential.get(differential.getUnitName());
    
    if (absolute != null)
      return absolute;
    else
      return differential;
  }
  
  public static UnitDifferentialConverter getInstance()
  {
    return INSTANCE;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  static Map<String, BUnit> byDifferential = new HashMap<>();
  static Map<String, BUnit> byAbsolute = new HashMap<>();
  
  private static UnitDifferentialConverter INSTANCE = new UnitDifferentialConverter();
}
