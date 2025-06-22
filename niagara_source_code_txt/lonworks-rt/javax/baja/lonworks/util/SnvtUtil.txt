/*
 * Copyright 2000 Tridium Inc. All Rights Reserved.
 */
package javax.baja.lonworks.util;

import com.tridium.lonworks.xml.*;

import javax.baja.lonworks.enums.BLonElementType;
import javax.baja.lonworks.londata.*;
import javax.baja.sys.BFacets;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;
import javax.baja.units.UnitDatabase;
import java.util.Vector;

/**
 *   This class contains static methods used to manage Snvt
 *  datatypes.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  25 June 01
 * @version   $Revision: 2$ $Date: 9/28/01 11:22:58 AM$
 * @since     Niagara 3.0
 */
public class SnvtUtil
{  

  /**
   * Construct the LonData component for the specified snvt type
   * from the data in the standard.lnml file. If the snvtType is 
   * not defined return a BLonByteArray of the specified size.
   */
  public static BLonData getLonData(int snvtType, int size)
  { 
    BLonData data = getLonData(snvtType);
    if(data!=null) return data;
    return new BLonData(BLonByteArray.make(size),BLonElementQualifiers.make(BLonElementType.na,size),null);
  }
  
  /**
   * Construct the LonData component for the specified snvt type
   * from the data in the standard.lnml file.
   */
  public static BLonData getLonData(int snvtType)
   { return getLonData(snvtType,false); }
   
  /**
   * Construct the LonData component for the specified snvt type
   * from the data in the standard.lnml file. 
   * <p>
   * Diff flag indicates that any temperature elements are temp diff.
   * Engineering units will be modified accordingly.
   */
  public static BLonData getLonData(int snvtType, boolean diff)
  {
    XLonInterfaceFile standard = XUtil.getStandard();
    Vector<XTypeDef> types = standard.types;
    String typeScope = "0," + Integer.toString(snvtType);
    for(int i=0 ; i<types.size() ; i++)
    {
      XTypeDef t = types.elementAt(i);
      if(!t.isCpType() && t.getTypeScope().equals(typeScope))
      {
        BLonData ldata = t.getLonData(standard);
        if(diff) makeDiff(ldata);
        return ldata;
      }
    }  
    return null;
  }
  
  /** DEPRECATE - use XUtil.getStandard() */
  public static Object getStandard()
  {
    return XUtil.getStandard();
  }

  /** DEPRECATE - use XUtil.getLonDataNv()  */
  public static BLonData getLonData(Object xnv, Object root)
  {
    return XUtil.getLonDataNv((XNetVariable)xnv, (XLonInterfaceFile)root);
  }
  
  /** Create a BLonEnum for specified standard enumType */
  public static BLonEnum getStandardEnum(String enumType)
  {
    XLonInterfaceFile sxfile = XUtil.getStandard();
   
    XEnumDef ed = sxfile.resolveEnumDef(enumType);
    if(ed == null)
      throw new RuntimeException("No enumType " + enumType);

    return ed.getEnum();
  } 


  // Walk elements and change temp units to temp diff units
  private static void makeDiff(BLonData ld)
  {
    SlotCursor<Property> c = ld.getProperties();
    while(c.nextObject())
    {
      Property p = c.property();
      Type  typ = p.getType();
      
      if(typ.is(BLonPrimitive.TYPE))
      {
        BFacets f = p.getFacets();
        BUnit u = (BUnit)f.get(BFacets.UNITS);
        if(isTemp(u)) ld.setFacets(p,makeNewUnits(f, makeTempDiff(u)));
      }

      // Allow recursion into child BLonData
      else if(typ.is(BLonData.TYPE))
      {
        makeDiff((BLonData)c.get());
      }
    }  
  }
  
  private static final BUnit makeTempDiff(BUnit u)     
  { 
    return  UnitDatabase.getUnit(u.getUnitName() + " degrees");
  } 
  
  private static final boolean isTemp(BUnit u)     
  { 
    UnitDatabase.Quantity q = UnitDatabase.getDefault().getQuantity(u);
    return q.getName().equals("temperature"); 
  } 
  private static final boolean isTempDiff(BUnit u) 
  { 
    UnitDatabase.Quantity q = UnitDatabase.getDefault().getQuantity(u);
    return q.getName().equals("temperature differential"); 
  } 
  private static BFacets makeNewUnits(BFacets f, BUnit u)
  {
    BFacets newFacets = BFacets.makeRemove(f,BFacets.UNITS);
    return BFacets.make(newFacets,BFacets.UNITS,u);
  }
  
  // Create a list of nvs for test.lnml to test creation of nv types
  // to run >>nre lonworks:javax.baja.lonworks.util.SnvtUtil
/*
  public static void main(String[] args)
  {
      Vector types = XUtil.getStandard().types;
      for(int i=0 ; i<types.size() ; i++)
      {
        XTypeDef t = ((XTypeDef)types.elementAt(i));
        if(!t.isCpType())
        { 
/*
  <nvoStatus type="XNetworkVariable">
   <index v="1"/>
   <direction v="output"/>
   <snvtType v="objStatus"/>
  </nvoStatus>
* /
          String nvName = t.getName();
          System.out.println("  <nvo" + nvName + " type=\"XNetworkVariable\">");
          System.out.println("   <index v=\"" + i + "\"/>");
          System.out.println("   <direction v=\"output\"/>");
          System.out.println("   <snvtType v=\"" + NameUtil.toJavaName(nvName,false) + "\"/>");
          System.out.println("  </nvo" + nvName + ">");
        }
      }

  }
*/  

}
