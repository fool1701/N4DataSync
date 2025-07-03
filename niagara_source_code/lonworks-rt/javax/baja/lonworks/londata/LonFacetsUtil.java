/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import javax.baja.data.BIDataValue;
import javax.baja.lonworks.LonException;
import javax.baja.lonworks.enums.BLonElementType;
import javax.baja.sys.*;
import javax.baja.units.BUnit;
import java.util.HashMap;


/**
 *   This class file contains utilities for managing BLonElementQualifiers
 * and BFacets on BLonData data elements.  BLonData consists of one or more
 * BLonPrimitives.  BLonData may be nested.  The qualifiers associated
 * with each data element are stored as facets on the elements property.  
 * The qualifiers are needed when data is read from or written to the device.
 * When the qualifiers are first requested the facets are converted to qualifiers
 * which are stored as the facets pickle.  Successives request for the qualifiers
 * merely retrieve the pickle from the facets.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  8 Nov 01
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
public class LonFacetsUtil
{  
  // Used by LonUtilRequest 
  /**
   * Get the BLonElementQualifiers for a primitive data element in data of a BLonComponent.
   * This will return BLonElementQualifiers with the byte offset correctly set.
   * <p>
   * @param londata the top level BLonData containing the BLonPrimitive element of 
   *                interest - this can be accessed by getData() on any BLonComponent.
   * @param prop  the property for the BLonPrimitive element of 
   *                interest    
   * @return the BLonElementQualifiers for the primitive data element.
   */
  public static BLonElementQualifiers getElementQualifiers(BLonData londata, Property prop)
    throws LonException
  {
/*    // First check for an existing pickle which includes the byte offset.
    // This is not always present - if not recalculate the qualifiers for
    // londata under the assumption that others elements will be accessed also.
    BFacets f = prop.getFacets();
    BLonElementQualifiers e = (BLonElementQualifiers)f.getPickle();
    if(e!=null && e.hasOffset()) return e;
    
    updateElementQualifiers(londata, 0);
    
    return (BLonElementQualifiers)prop.getFacets().getPickle();
*/
    BLonElementQualifiers e = getQualifiers(prop.getFacets()); 
    if(e.hasOffset()) return e;
    
    return  BLonElementQualifiers.make(e.getElemtype    (),     
                                       e.hasMinimum     (), 
                                       e.getMinimum     (), 
                                       e.hasMaximum     (), 
                                       e.getMaximum     (), 
                                       e.getResolution  (), 
                                       e.getOffset      (), 
                                       true,                        //e.getHasOffset   (),
                                       getByteOffset(londata,prop), //e.getByteOffset  (), 
                                       0,                           //e.getBitOffset   (), 
                                       e.hasInvalidValue(), 
                                       e.getInvalidValue(), 
                                       e.getLength      ());      
  }
  
  private static int getByteOffset(BLonData londata, Property prop)
  {
    EqSearch eqs = new EqSearch();
    eqs.sLd = londata;
    eqs.prop = prop;
    
    BLonData ld = londata;
    // Make sure this is the container BLonData
    while(ld.getParent() instanceof BLonData) ld = (BLonData)ld.getParent();
    
    getByteOffset(eqs,ld);
    return eqs.offset;
  }
  
  private static void getByteOffset(EqSearch eqs, BLonData ld)
  {
    SlotCursor<Property> c = ld.getProperties();
    while(c.nextObject())
    {
      BObject obj = c.get();
    
      // Allow recursion into child BLonData
      if(BLonData.class.isInstance(obj))
      {
        getByteOffset(eqs,(BLonData)obj);
        if(eqs.found) return;
        continue;
      }  
      
      if(!BLonPrimitive.class.isInstance(obj)) continue;
      
      Property prop = c.property();
      // If this is search ld and prop then end search
      if(prop == eqs.prop && ld == eqs.sLd)
      {
        eqs.found = true; 
        return; 
      }
      
      BLonElementQualifiers e = getQualifiers(prop.getFacets()); 
      // Check for qualifier with byte offset 
      if(e.hasOffset())
      {
        // If qualifier has byteOffset specified this is a bit fields or union.  
        // Use the specified offset
        eqs.offset = e.getByteOffset();
      }
      // Accumulate offsets.
      try { eqs.offset += e.getDataByteLength(); } catch(Exception ex) {System.out.println(ex);}
    }
  }
  
  private static class EqSearch
  {
    BLonData sLd;
    Property prop;
    int offset = 0;
    boolean found = false;
  }
 
  // Used in BLonData from/to stream
  /** Get a BLonElementQualifiers for the specified facets. */
  public static BLonElementQualifiers getQualifiers(BFacets f)  
  {
    Object pkl = f.getPickle();
    if(pkl!=null && (pkl instanceof BLonElementQualifiers))
    {
      return (BLonElementQualifiers)pkl;
    }

    BLonElementQualifiers elemQual = makeLonQualifiers(f,-1);
    BFacets.makePickle(f, elemQual);
    return elemQual;
  }
  
  // Used in BLonData constructors
  /** Create a BFacets for the specifed BLonElementQualifiers and BUnit */
  public static BFacets makeFacets(BLonElementQualifiers elemQual, BUnit unit)
  {
    HashMap<String,BIDataValue> map = new HashMap<>();
    if(unit!=null)map.put(UNITS, unit);

    BLonElementType elemtype = elemQual.getElemtype();
    map.put(TYPE, BString.make(elemtype.getTag()) );
    if(elemQual.getByteOffset()>0)   map.put(BYT, BInteger.make(elemQual.getByteOffset()) );
    switch(elemtype.getOrdinal())
    {
      case BLonElementType.C8:    
      case BLonElementType.B8:    
      case BLonElementType.E8:   
        // no qualifiers
        break; 
      case BLonElementType.S8:    
      case BLonElementType.U8:    
      case BLonElementType.S16:    
      case BLonElementType.U16:    
      case BLonElementType.F32:    
      case BLonElementType.S32:
      case BLonElementType.F64:    
      case BLonElementType.U32:
      case BLonElementType.S64:
      case BLonElementType.U64:
        float resolution = elemQual.getResolution();
        if(resolution!=0F && resolution!=1F) map.put(RES, BFloat.make(elemQual.getResolution()));
        if(elemQual.getOffset()!=0F) map.put(OFF, BFloat.make(elemQual.getOffset()) );
        addMinMax(map,elemQual);        
        addPrec(map,elemQual);        
        break; 
      case BLonElementType.EB:    
      case BLonElementType.ESB:    
      case BLonElementType.BB:    
        map.put(BIT, BInteger.make(elemQual.getBitOffset()) );
        map.put(LEN, BInteger.make(elemQual.getLength()) );
        break;
      case BLonElementType.UB:    
      case BLonElementType.SB:    
        map.put(BIT, BInteger.make(elemQual.getBitOffset()) );
        map.put(LEN, BInteger.make(elemQual.getLength()) );
        addMinMax(map,elemQual);        
        map.put(BFacets.PRECISION, BInteger.make(0));
        break;
      case BLonElementType.ST:    
      case BLonElementType.NA:    
        map.put(LEN, BInteger.make(elemQual.getLength()) );
        break;
      default:
        System.out.println("LonFacetsUtil.makeFacets did not handle elemtype=" + elemtype);
        break;          
    }
        
    return BFacets.make(map);
  }
  
  private static void addMinMax(HashMap<String,BIDataValue> map,BLonElementQualifiers elemQual)
  {
     map.put(MIN, elemQual.hasMinimum() ? toBNumber(elemQual.getMinimumN())
                                        : getMinimumMin(elemQual) );
     map.put(MAX, elemQual.hasMaximum() ? toBNumber(elemQual.getMaximumN())           
                                        : getMaximumMax(elemQual) );
     if(elemQual.hasInvalidValue())map.put(INVLD,toBNumber(elemQual.getInvalidValueN()) );
  }
  
  /** Get the maximum value possible for the specified qualifiers. */
  private static BNumber getMaximumMax(BLonElementQualifiers elemQual)
  {
    double  max = 0.0;
    switch( elemQual.getElemtype().getOrdinal())
    {
      case BLonElementType.S8: 
        max = 127.0;
        break;     
      case BLonElementType.U8:    
        max = 255.0;
        break;     
      case BLonElementType.S16:    
        max = 32767.0;
        break;     
      case BLonElementType.U16:    
        max = 65535.0;
        break;     
      case BLonElementType.S32:    
        max = 2147483647.0;
        break;     
      case BLonElementType.U32:    
        max = 4294967295.0;
        break;     
      case BLonElementType.S64:    
        max = Math.pow(2,63)-1;
        break;     
      case BLonElementType.U64:    
        // Theoretical limit is 18446744073709551615 specified value is limited by need to use double
        // which can only hold a mantisa with less than 8 digits, can not use long as it is signed 64,  
        // can not use BigInteger for facets because there is no BNumber support of BigInteger.        
        max = 18446744073709550000.0; //Math.pow(2,64)-1;
        break;     
      case BLonElementType.F32:    
        max = Float.POSITIVE_INFINITY;
        break;     
      case BLonElementType.F64:    
        max = Double.POSITIVE_INFINITY;
        break;     
      case BLonElementType.UB:    
        max = (1<<elemQual.getLength()) - 1;
        break;     
      case BLonElementType.SB:    
        max = ( 1<<(elemQual.getLength()-1) ) - 1;
        break;     
      default:  
        max = Float.POSITIVE_INFINITY;
     }
     if(max==elemQual.getInvalidValue()) max = max - 1;

     max = (max * elemQual.getResolution()) - elemQual.getOffset();
     
     if(max<Float.MAX_VALUE)
       return BFloat.make((float) max);
     return BDouble.make(max); 
  }
  
  /** Get the minimum value possible for the specified qualifiers. */
  private static BNumber getMinimumMin(BLonElementQualifiers elemQual)
  {
    double  min = 0.0;
    switch( elemQual.getElemtype().getOrdinal())
    {
      case BLonElementType.S8: 
        min = -128.0;
        break;     
      case BLonElementType.S16:    
        min = -32768.0;
        break;     
      case BLonElementType.S32:    
        min = -2147483648.0;
        break;     
      case BLonElementType.S64:    
        min = -9223372036854775808.0;
        break;     
      case BLonElementType.U64:    
      case BLonElementType.U32:    
      case BLonElementType.U16:    
      case BLonElementType.U8:    
      case BLonElementType.UB:    
        min = 0;
        break;     
      case BLonElementType.SB:    
        min = -(1<<(elemQual.getLength()-1));
        break;     
     case BLonElementType.F64:
        min = Double.NEGATIVE_INFINITY;
        break;     
     default:  
        min = Float.NEGATIVE_INFINITY;
     }
     min = (min * elemQual.getResolution()) - elemQual.getOffset();
     
     if(min>Float.MIN_VALUE)
       return BFloat.make((float) min);
     return BDouble.make(min); 
  }
  
  /** Set the precision based on resolution. Res=0.1 - precision=1 */ 
  private static void addPrec(HashMap<String,BIDataValue> map,BLonElementQualifiers elemQual)
  {
    float res = elemQual.getResolution();

    int pre = elemQual.getElemtype().equals(BLonElementType.f32) ? 2 : 0;
    if(res < 1.0F) pre = (int)Math.ceil(-(Math.log(res)/Math.log(10)));

    map.put(BFacets.PRECISION, BInteger.make(pre));
  }

  private static BLonElementQualifiers makeLonQualifiers(BFacets f, int bOffset)
  {
     float   resolution   = 1.0f;   
     float   offset       = 0.0f;
     boolean hasMinimum   = false;
     Number   minimum     = Float.valueOf(0.0f);
     boolean hasMaximum   = false;
     Number   maximum     = Float.valueOf(0.0f);
     boolean hasOffset    = false;
     int     bitOffset    = 0;
     int     byteOffset   = 0;
     boolean hasInvalid   = false;
     Number  invalidValue = Float.valueOf(Float.NaN);
     int     length       = 0;
     
     BLonElementType elemtype = (BLonElementType)BLonElementType.na.getRange().get(((BString)f.getFacet(TYPE)).getString());
     if(elemtype == null) return BLonElementQualifiers.NONE;
     BObject s;
     if( (s = f.getFacet(RES)) != null)   { resolution = ((BFloat)s).getFloat(); }
     if( (s = f.getFacet(OFF)) != null)   { offset = ((BFloat)s).getFloat(); }
     if( (s = f.getFacet(MIN)) != null)   { minimum = parseNumber(s); hasMinimum = true; }
     if( (s = f.getFacet(MAX)) != null)   { maximum = parseNumber(s); hasMaximum = true; }
     if( (s = f.getFacet(BYT)) != null)   { byteOffset = ((BInteger)s).getInt(); if(byteOffset>0) hasOffset = true;}
     if( (s = f.getFacet(BIT)) != null)   { bitOffset = ((BInteger)s).getInt(); hasOffset = true; }
     if( (s = f.getFacet(LEN)) != null)   { length = ((BInteger)s).getInt(); }
     if( (s = f.getFacet(INVLD)) != null) { invalidValue = parseNumber(s); hasInvalid = true; }
            
     if(!hasOffset && bOffset>=0) { byteOffset = bOffset; hasOffset=true; }
     
     return BLonElementQualifiers.make( elemtype    ,
                                 hasMinimum  , minimum     ,
                                 hasMaximum  , maximum     ,
                                 resolution  ,
                                 offset      ,
                                 hasOffset    , byteOffset  , bitOffset ,
                                 hasInvalid  , invalidValue,
                                 length );
  }
 
  private static Number parseNumber(BObject s)
  {
    if(s instanceof BLong)
    {
      return Long.valueOf( ((BLong)s).getLong() );
    }
    if(s instanceof BDouble)
    {
      return Double.valueOf( ((BDouble)s).getDouble() );
    }
    return Float.valueOf( ((BFloat)s).getFloat() );
  }

  private static BNumber toBNumber(Number n)
  {
    if(n instanceof Long)
    {
      return BLong.make( ((Long)n).longValue() );
    }
    if(n instanceof Double)
    {
      return BDouble.make( ((Double)n).doubleValue() );
    }
    return BFloat.make( ((Float)n).floatValue() );
  }
  
  /**
   * Create a BFacets with the specified elements to qualify a BLonPrimitive.
   */
  public static BFacets makeFacets( BLonElementType elemtype,
                                BUnit   unit  ) 
  {
    return makeFacets(BLonElementQualifiers.make(elemtype,0), unit);
  } 

  /**
   * Create a BFacets with the specified elements to qualify a BLonPrimitive.
   */
  public static BFacets makeFacets( BLonElementType elemtype,
                                int     len,
                                BUnit   unit ) 
  {
    return makeFacets(BLonElementQualifiers.make(elemtype,len), unit);
  } 
  
  /**
   * Create a BFacets with the specified elements to qualify a BLonPrimitive.
   */
  @SuppressWarnings("fallthrough")
  public static BFacets makeFacets( BLonElementType elemtype,
                          boolean hasMinimum  ,
                          float   minimum     ,
                          boolean hasMaximum  ,
                          float   maximum     ,
                          float   resolution  ,
                          float   offset      ,
                          boolean hasOffsets  ,
                          int     byteOffset  ,
                          int     bitOffset   ,
                          boolean hasInvalid  ,
                          float   invalidValue,
                          int     length,
                          BUnit   unit ) 
  {
    HashMap<String,BIDataValue> map = new HashMap<>();
    map.put(TYPE, BString.make(elemtype.getTag()) );
    if(unit!=null) map.put(UNITS, unit);
    if(hasOffsets)   map.put(BYT, BInteger.make(byteOffset) );
    switch(elemtype.getOrdinal())
    {
      case BLonElementType.S8:    
      case BLonElementType.U8:    
      case BLonElementType.S16:    
      case BLonElementType.U16:    
      case BLonElementType.F32:    
      case BLonElementType.S32:    
      case BLonElementType.F64:    
      case BLonElementType.U32:    
        if(resolution!=0F && resolution!=1F) map.put(RES, BFloat.make(resolution) );
        if(offset!=0F) map.put(OFF, BFloat.make(offset) );
        if(hasMinimum)  map.put(MIN,  BFloat.make(minimum) );
        if(hasMaximum)  map.put(MAX,  BFloat.make(maximum) );
        if(hasInvalid)  map.put(INVLD,BFloat.make(invalidValue) );
        break; 
      case BLonElementType.UB:    
        if(!hasMinimum)  map.put(MIN,  BFloat.make(0) ); // unsigned without specified min is min=0
        // fall through
      case BLonElementType.EB:    
      case BLonElementType.ESB:    
      case BLonElementType.BB:    
      case BLonElementType.SB:    
        map.put(BIT, BInteger.make(bitOffset) );
        map.put(LEN, BInteger.make(length) );
        if(hasMinimum)  map.put(MIN,  BFloat.make(minimum) );
        if(hasMaximum)  map.put(MAX,  BFloat.make(maximum) );
        if(hasInvalid)  map.put(INVLD,BFloat.make(invalidValue) );
        break;
      case BLonElementType.ST:    
      case BLonElementType.NA:    
        map.put(LEN, BInteger.make(length) );
        break;
    }
    return BFacets.make(map);
  } 


  /**
   * Create a BFacets. 
   */
  public static BFacets makeFacets( BLonElementType elemtype,
                                float   minimum     ,
                                float   maximum     ,
                                float   resolution ,
                                BUnit   unit      ) 
  {
    BLonElementQualifiers eq = BLonElementQualifiers.make( elemtype, true, minimum, true, maximum, resolution, 0F, false,
                                             0, 0, false, 0F, 0); 
    return makeFacets(eq, unit);
  } 
  
  /**
   * Create a BFacets. 
   * 
   */
  public static BFacets makeFacets( BLonElementType elemtype,
                                float   minimum     ,
                                float   maximum     ,
                                float   resolution ,
                                int     byteOffset ,
                                BUnit   unit      ) 
  {
    BLonElementQualifiers eq = BLonElementQualifiers.make( elemtype, true, minimum, true, maximum, resolution, 0F, false,
                                                           byteOffset, 0, false, 0F, 0); 
    return makeFacets(eq, unit);
  } 
  
  /**
   * Create a BFacets.
   * @since   Niagara 3.5.24
   */
  public static BFacets makeFacets( BLonElementType elemtype,
                                    float   minimum     ,
                                    BUnit   unit      ) 
  {
    BLonElementQualifiers eq = BLonElementQualifiers.make( elemtype, true, minimum,false, 0F, 1F, 0F, false,
                                             0, 0, false, 0F, 0); 
    return makeFacets(eq, unit);
  } 
  
  /**
   * Create a BFacets.
   * @since   Niagara 3.5.24
   */
  public static BFacets makeFacets( BLonElementType elemtype   ,
                                    int             byteOffset  ) 
  {
    BLonElementQualifiers eq = BLonElementQualifiers.make( elemtype, false, 0F,false, 0F, 1F, 0F, false,
                                             byteOffset, 0, false, 0F, 0); 
    return makeFacets(eq, null);
  } 

  /**
   * Create a BFacets.
   */
  public static BFacets makeFacets( BLonElementType elemtype   ,
                                    int             byteOffset ,
                                    int             bitOffset  ,
                                    int             len     ,
                                    BUnit           unit       ) 
  {
    BLonElementQualifiers eq = BLonElementQualifiers.make( elemtype, false, 0F,false, 0F, 1F, 0F, false,
                                             byteOffset, bitOffset, false, 0F,len); 
    return makeFacets(eq, unit);
  } 

  /**
   * Create a BFacets with the specified elements to qualify a BLonPrimitive.
   */
  public static BFacets makeFacets( BLonElementType elemtype,
                                float   minimum     ,
                                float   maximum      ,
                                float   resolution    ,
                                float   invalidValue,
                                BUnit   unit  ) 
  {
    BLonElementQualifiers eq = BLonElementQualifiers.make( elemtype, true, minimum, true, maximum,
                                             resolution, 0F, false,
                                             0, 0, true, invalidValue, 0); 
    return makeFacets(eq, unit);
  } 
  
  /**
   * Create a BFacets with the specified elements to qualify a BLonPrimitive.
   * @since   Niagara 3.5.24
   */
  public static BFacets makeFacets( BLonElementType elemtype,
                                float   minimum     ,
                                float   maximum      ,
                                float   resolution    ,
                                float   invalidValue,
                                int     byteOffset  ,
                                int     bitOffset   ,
                                BUnit   unit  ) 
  {
    BLonElementQualifiers eq = BLonElementQualifiers.make( elemtype, true, minimum, true, maximum,
                                             resolution, 0F, false,
                                             byteOffset, bitOffset, true, invalidValue, 0); 
    return makeFacets(eq, unit);
  } 


  public static final String TYPE   = "type";
  public static final String MIN    = BFacets.MIN;
  public static final String MAX    = BFacets.MAX;
  public static final String RES    = "res";
  public static final String OFF    = "off";
  public static final String BYT    = "byt";
  public static final String BIT    = "bit";
  public static final String LEN    = "len";
  public static final String INVLD  = "invld";
  public static final String UNITS  = BFacets.UNITS;
  
  
}

