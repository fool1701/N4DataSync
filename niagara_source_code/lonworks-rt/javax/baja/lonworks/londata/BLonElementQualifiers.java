/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.lonworks.LonException;
import javax.baja.lonworks.enums.BLonElementType;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;

/**
 * The BLonElementQualifiers defines qualifiers for a data
 * element within an nv, nci, of config property. 
 * It specifies resolution and offset, min,max and invalid values,
 * and byte and bit position of the element.  Element qualifiers  
 * are needed to convert controller byte data from/to 
 * linkable baja data elements. <p>
 *
 * @author    Robert Adams
 * @creation  5 Jan 01
 * @version   $Revision: 5$ $Date: 9/18/01 9:49:38 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BLonElementQualifiers
  extends BSimple
{
  /**
   * The default.
   */
  public static final BLonElementQualifiers DEFAULT = new BLonElementQualifiers();

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonElementQualifiers(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonElementQualifiers.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * Create a BLonElementQualifiers with the given elemType and length.
   */
  public static BLonElementQualifiers make(BLonElementType elemtype,
                                           int  length  ) 
   { return new BLonElementQualifiers(elemtype, length);  } 

  /**
   * Create a BLonElementQualifiers with the given qualifiers.
   */
  public static  BLonElementQualifiers make( BLonElementType elemtype,
                                             boolean         hasMinimum  ,
                                             float           minimum     ,
                                             boolean         hasMaximum  ,
                                             float           maximum     ,
                                             float           resolution  ,
                                             float           offset      ,
                                             boolean         hasOffset    ,
                                             int             byteOffset  ,
                                             int             bitOffset   ,
                                             boolean         hasInvalid  ,
                                             float           invalidValue,
                                             int             length ) 
  {
    return new BLonElementQualifiers(elemtype,  hasMinimum  ,
                                     Float.valueOf(minimum), hasMaximum  ,
                                     Float.valueOf(maximum), resolution  ,
                                     offset      , hasOffset   ,
                                     byteOffset  , bitOffset   ,
                                     hasInvalid  ,  Float.valueOf(invalidValue),
                                     length ); 
  }
  /**
   * Create a BLonElementQualifiers with the given qualifiers.
   *  @since Niagara 3.8.26 
   */
  public static  BLonElementQualifiers make( BLonElementType elemtype,
                                             boolean         hasMinimum  ,
                                             Number          minimum     ,
                                             boolean         hasMaximum  ,
                                             Number          maximum     ,
                                             float           resolution  ,
                                             float           offset      ,
                                             boolean         hasOffset    ,
                                             int             byteOffset  ,
                                             int             bitOffset   ,
                                             boolean         hasInvalid  ,
                                             Number          invalidValue,
                                             int             length ) 
  {
    return new BLonElementQualifiers(elemtype,  hasMinimum  ,
                                     minimum ,  hasMaximum  ,
                                     maximum ,  resolution  ,
                                     offset      , hasOffset   ,
                                     byteOffset  , bitOffset   ,
                                     hasInvalid  ,  invalidValue,
                                     length ); 
  }

  public static  BLonElementQualifiers make( BLonElementType elemtype,
                          float   minimum     ,
                          float   maximum      ,
                          float   resolution    ,
                          float   invalidValue ) 
  {
    return new BLonElementQualifiers(elemtype, true, Float.valueOf(minimum), true, Float.valueOf(maximum) ,
                          resolution, DEFAULT_OFFSET, false, DEFAULT_B_OFFSET, DEFAULT_B_OFFSET, true,  Float.valueOf(invalidValue), DEFAULT_LENGTH );
  }

  /** Empty constructor.*/
  private BLonElementQualifiers() { } 

  private BLonElementQualifiers( BLonElementType elemtype ) 
    { this.elemtype = elemtype; } 

  private BLonElementQualifiers( BLonElementType elemtype,
                                 int     length ) 
  {
    this.elemtype     = elemtype    ;
    this.length       = length      ;
  } 
  
  private BLonElementQualifiers( BLonElementType elemtype,
                          boolean hasMinimum  ,
                          Number   minimum     ,
                          boolean hasMaximum  ,
                          Number   maximum     ,
                          float   resolution  ,
                          float   offset      ,
                          boolean hasOffset    ,
                          int     byteOffset  ,
                          int     bitOffset   ,
                          boolean hasInvalid  ,
                          Number   invalidValue,
                          int     length ) 
  {
    this.elemtype     = elemtype    ;
    this.hasMinimum   = hasMinimum  ;
    this.minimum      = minimum ;
    this.hasMaximum   = hasMaximum  ;
    this.maximum      = maximum ;
    this.resolution   = resolution  ;
    this.offset       = offset      ;
    this.hasOffset    = hasOffset   ;
    this.byteOffset   = byteOffset  ;
    this.bitOffset    = bitOffset   ;
    this.hasInvalid   = hasInvalid  ;
    this.invalidValue = invalidValue;
    this.length       = length      ;
  } 
  private BLonElementQualifiers( BLonElementType elemtype,
                          float   minimum     ,
                          float   maximum     ,
                          float   resolution      ) 
  {
    this.elemtype     = elemtype    ;
    this.hasMinimum   = true  ;
    this.minimum      = Float.valueOf(minimum) ;
    this.hasMaximum   = true  ;
    this.maximum      = Float.valueOf(maximum) ;
    this.resolution   = resolution  ;
  } 
  
  private BLonElementQualifiers( BLonElementType elemtype,
                          float   minimum     ,
                          float   maximum      ,
                          float   resolution    ,
                          float   invalidValue ) 
  {
    this.elemtype     = elemtype    ;
    this.hasMinimum   = true  ;
    this.minimum      = Float.valueOf(minimum) ;
    this.hasMaximum   = true  ;
    this.maximum      = Float.valueOf(maximum);
    this.resolution   = resolution  ;
    this.hasInvalid   = true  ;
    this.invalidValue = Float.valueOf(invalidValue);
  } 

  /**
   * Test if the obj is equal in value to this BLonElementQualifiers.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof BLonElementQualifiers))
      return false;
    
    BLonElementQualifiers comp = (BLonElementQualifiers)obj;
    if ( comp.elemtype.equals(elemtype) &&  
         BFloat.equals(comp.resolution, resolution) &&  
         BFloat.equals(comp.offset, offset) &&  
         (comp.hasMinimum == hasMinimum) && 
         numberEquals(comp.minimum, minimum) &&  
         (comp.hasMaximum == hasMaximum) && 
         numberEquals(comp.maximum, maximum) &&  
         (comp.hasOffset == hasOffset) && 
         (comp.byteOffset == byteOffset) && 
         (comp.bitOffset == bitOffset) && 
         (comp.length == length) &&
         (comp.hasInvalid == hasInvalid) && 
         numberEquals(comp.invalidValue, invalidValue) )
    {
      return true;
    }
    return false;
  }
  
  private boolean numberEquals(Number comp, Number n)
  {
    if(n instanceof Long) return comp.longValue()==n.longValue();
    return Double.valueOf(comp.doubleValue()).equals(Double.valueOf(n.doubleValue()));
  }
  
  /**
   * Test if the obj is equal in value to this BLonElementQualifiers.
   */
  public boolean canCopyFrom(Object obj)
  {
    if (!(obj instanceof BLonElementQualifiers))
      return false;
    
    BLonElementQualifiers comp = (BLonElementQualifiers)obj;
    if ( comp.elemtype.equals(elemtype) &&  
         BFloat.equals(comp.resolution, resolution) &&  
         BFloat.equals(comp.offset, offset) &&  
   //      (comp.hasMinimum == hasMinimum) && 
   //      BFloat.equals(comp.minimum, minimum) &&  
   //      (comp.hasMaximum == hasMaximum) && 
   //      BFloat.equals(comp.maximum, maximum) &&  
         (comp.hasOffset == hasOffset) && 
         (!hasOffset || (comp.byteOffset == byteOffset)) &&
         (!hasOffset || (comp.bitOffset == bitOffset)) &&
         (comp.length == length) &&
         (comp.hasInvalid == hasInvalid) && 
         numberEquals(comp.invalidValue, invalidValue) )
    {
      return true;
    }

    return false;
  }
  
  /** @return the length of qualified data element in bytes. If bit field return 0. */
  public int getDataByteLength()
    throws LonException
  {     
    switch(elemtype.getOrdinal())
    {
      case BLonElementType.C8:    
      case BLonElementType.B8:    
      case BLonElementType.E8:   
      case BLonElementType.S8:    
      case BLonElementType.U8:    
        return 1; 
      case BLonElementType.S16:    
      case BLonElementType.U16:    
        return 2; 
      case BLonElementType.F32:    
      case BLonElementType.S32:    
      case BLonElementType.U32:    
        return 4; 
      case BLonElementType.F64:    
      case BLonElementType.S64:    
      case BLonElementType.U64:    
        return 8; 
      case BLonElementType.EB:    
      case BLonElementType.ESB:    
      case BLonElementType.BB:    
      case BLonElementType.UB:    
      case BLonElementType.SB:    
        return 0; 
      case BLonElementType.ST:    
      case BLonElementType.NA:    
        return length;
    }
        
    throw new LonException("Unsupported element type in BLonElementQualifier.getDataByteLenth() " + elemtype);
  }
  
  /**
   *
   */
  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(elemtype.getTag()).append(" ");
    sb.append("res=").append(resolution).append(" ");
    sb.append("off=").append(offset).append(" ");
    sb.append("min=").append(hasMinimum?"t":"f").append(minimum).append(" ");
    sb.append("max=").append(hasMaximum?"t":"f").append(maximum).append(" ");
    sb.append("byt=").append(hasOffset?"t":"f").append(byteOffset ).append(" ");
    sb.append("bit=").append(bitOffset).append(" ");
    sb.append("len=").append(length).append(" ");
    sb.append("inv=").append(hasInvalid?"t":"f").append(invalidValue).append(" ");
        
    return sb.toString();
  }
  
  /**
   * Serialized.
   */
  public void encode(DataOutput out)
    throws IOException
  {
    throw new IOException("BLonElementQualifiers not serialized");
//    elemtype.encode(out);
//    out.writeBoolean(hasMinimum   );
//    out.writeFloat  (minimum      );
//    out.writeBoolean(hasMaximum   );
//    out.writeFloat  (maximum      );
//    out.writeFloat  (resolution   );
//    out.writeFloat  (offset       );
//    out.writeBoolean(hasOffset     );
//    out.writeInt    (byteOffset   );
//    out.writeInt    (bitOffset    );
//    out.writeBoolean(hasInvalid   );
//    out.writeFloat  (invalidValue );
//    out.writeInt    (length       );
  }
  
  /**
   *  Unserialized.
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    throw new IOException("BLonElementQualifiers not serialized");
//    return new BLonElementQualifiers( (BLonElementType)elemtype.decode(in),
//                                in.readBoolean(),
//                                in.readFloat  (),
//                                in.readBoolean(),
//                                in.readFloat  (),
//                                in.readFloat  (),
//                                in.readFloat  (),
//                                in.readBoolean(),
//                                in.readInt    (),
//                                in.readInt    (),
//                                in.readBoolean(),
//                                in.readFloat  (),
//                                in.readInt    ()  );
  }

  /**
   * Write the primitive in String format.
   */
  public String encodeToString()
    throws IOException
  {     
    throw new IOException("BLonElementQualifiers cannot encodeTostring");
//    StringBuilder sb = new StringBuilder();
//    sb.append(elemtype.getTag()).append(" ");
//    
//    if(hasOffset)sb.append("byt=").append(byteOffset).append(" ");
// 
//    switch(elemtype.getOrdinal())
//    {
//      case BLonElementType.C8:    
//      case BLonElementType.B8:    
//      case BLonElementType.E8:   
//        // no qualifiers
//        break; 
//      case BLonElementType.S8:    
//      case BLonElementType.U8:    
//      case BLonElementType.S16:    
//      case BLonElementType.U16:    
//      case BLonElementType.F32:    
//      case BLonElementType.S32:    
//      case BLonElementType.U32:    
//      case BLonElementType.F64:    
//      case BLonElementType.U64:    
//      case BLonElementType.S64:    
//        sb.append("res=").append(resolution).append(" ");
//        sb.append("off=").append(offset).append(" ");
//        addMinMax(sb);        
//        break; 
//      case BLonElementType.EB:    
//      case BLonElementType.BB:    
//      case BLonElementType.UB:    
//      case BLonElementType.SB:    
//      case BLonElementType.ESB:    
//        sb.append("bit=").append(bitOffset).append(" ");
//        sb.append("len=").append(length).append(" ");
//        addMinMax(sb);        
//        break;
//      case BLonElementType.ST:    
//      case BLonElementType.NA:    
//        sb.append("len=").append(length).append(" ");
//        break;
//    }
//        
//    return sb.toString();
  }
  
//  private void addMinMax(StringBuilder sb)
//  {
//     if(hasMinimum)sb.append("min=").append(minimum).append(" ");
//     if(hasMaximum)sb.append("max=").append(maximum).append(" ");
//     if(hasInvalid)sb.append("invld=").append(invalidValue).append(" ");
//  }
 

  /**
   * Read the primitive from String format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    throw new IOException("BLonElementQualifiers cannot decodeFromString");
//     StringTokenizer st = new  StringTokenizer(s, " ");
//      
//     BLonElementType elemtype = (BLonElementType)this.elemtype.decodeFromString(st.nextToken());
//     float   resolution   = 1.0f;   
//     float   offset       = 0.0f;
//     boolean hasMinimum   = false;
//     float   minimum      = 0.0f;
//     boolean hasMaximum   = false;
//     float   maximum      = 0.0f;
//     boolean hasOffset     = false;
//     int     bitOffset    = 0;
//     int     byteOffset   = 0;
//     boolean hasInvalid   = false;
//     float   invalidValue = Float.NaN;
//     int     length       = 0;
//     
//     while(st.hasMoreTokens())
//     {
//       String tok = st.nextToken();
//       int equalPos = tok.indexOf("=");
//       if(equalPos < 0) continue;
//       String qual = tok.substring(0,equalPos);
//       String val  = tok.substring(equalPos+1);
//       if(qual.equals("res"))        { resolution = Float.parseFloat(val); }
//       else if(qual.equals("off"))   { offset = Float.parseFloat(val); }
//       else if(qual.equals("min"))   { minimum = Float.parseFloat(val); hasMinimum = true; }
//       else if(qual.equals("max"))   { maximum = Float.parseFloat(val); hasMaximum = true; }
//       else if(qual.equals("byt"))   { byteOffset = Integer.parseInt(val); hasOffset = true;}
//       else if(qual.equals("bit"))   { bitOffset = Integer.parseInt(val);hasOffset = true; }
//       else if(qual.equals("len"))   { length = Integer.parseInt(val); }
//       else if(qual.equals("invld")) { invalidValue = Float.parseFloat(val); hasInvalid = true; }
//    
//     }
//      
//     return new BLonElementQualifiers( elemtype    ,
//                                 hasMinimum  , minimum     ,
//                                 hasMaximum  , maximum     ,
//                                 resolution  ,
//                                 offset      ,
//                                 hasOffset    , byteOffset  , bitOffset ,
//                                 hasInvalid  , invalidValue,
//                                 length );
  }

  public  BLonElementType  getElemtype() { return  elemtype ; }  
  public  float   getResolution()   { return  resolution  ; }  
  public  float   getOffset()       { return  offset      ; }
  public  int     getByteOffset()   { return  byteOffset  ; }
  public  int     getBitOffset()    { return  bitOffset   ; }
  public  int     getSize()         { return  length      ; }
  public  int     getLength()       { return  length      ; }
  public  boolean hasOffset()       { return  hasOffset   ; }
  public  boolean hasMinimum()      { return  hasMinimum  ; }
  public  float   getMinimum()      { return  minimum.floatValue()  ; }
  /**  @since Niagara 3.8.26 */     
  public  double  getMinimumD()     { return  minimum.doubleValue() ; }
  /**  @since Niagara 3.8.26 */     
  public  Number  getMinimumN()     { return  minimum     ; }
  public  boolean hasMaximum()      { return  hasMaximum  ; }
  public  float   getMaximum()      { return  maximum.floatValue()  ; }
  /**  @since Niagara 3.8.26 */
  public  double  getMaximumD()     { return  maximum.doubleValue(); }
  /**  @since Niagara 3.8.26 */     
  public  Number  getMaximumN()     { return  maximum     ; }
  public  boolean hasInvalidValue() { return  hasInvalid  ; }
  public  float   getInvalidValue() { return  invalidValue.floatValue() ; }
  /**  @since Niagara 3.8.26 */
  public  long   getInvalidValueL() { return  invalidValue.longValue() ; }
  /**  @since Niagara 3.8.26 */
  public  Number   getInvalidValueN() { return  invalidValue ; }

  /**  @since Niagara 3.8.26 */
  public boolean isInvalid(long lval) { return hasInvalidValue() && (lval == invalidValue.longValue()); }
  /**  @since Niagara 3.8.26 */
  public boolean isInvalid(float fval) { return hasInvalidValue() && (fval == invalidValue.floatValue()); }
  /**  @since Niagara 3.8.26 */
  public boolean isInvalid(double dval) { return hasInvalidValue() && (dval == invalidValue.doubleValue()); }
  
  private  BLonElementType elemtype = BLonElementType.na;
  private  float  resolution = 1.0f;   
  private  float  offset = DEFAULT_OFFSET;
  
  private  boolean hasMinimum = false;
  private  Number minimum = Float.valueOf(0.0f);
  private  boolean hasMaximum = false;
  private  Number maximum = Float.valueOf(0.0f);
  
  private  boolean hasOffset = false;
  private  int  byteOffset = DEFAULT_B_OFFSET;
  private  int  bitOffset = DEFAULT_B_OFFSET;

  private  boolean hasInvalid = false;
  private  Number   invalidValue = Float.valueOf(0.0f); // This should always be long from resource files but legacy needs float
  
  
  private static final float DEFAULT_OFFSET = 0.0f;
  private static final int DEFAULT_B_OFFSET = 0;
  private static final int DEFAULT_LENGTH = 0;

  /**
   * if type ==  bit then this is the bit count
   * if type ==  typeless or union then this is the byte count 
   */
  private  int  length = 0;
  
  //
  //  Qualifiers used in snvt data elements.
  //
  public static final BLonElementQualifiers NONE                = new BLonElementQualifiers();
  public static final BLonElementQualifiers ENUM                = new BLonElementQualifiers(BLonElementType.e8);
  public static final BLonElementQualifiers BOOLEAN             = BLonElementQualifiers.make(BLonElementType.b8, false,0F, false,0F, 1F,0F, false,0,0, false,0F, 1);
//  public static final BLonElementQualifiers UNSIGNED_LONG1i     = new BLonElementQualifiers(BLonElementType.u16, 0,        65534,    1,       65535);
  public static final BLonElementQualifiers SIGNED_LONG1        = new BLonElementQualifiers(BLonElementType.s16, -32768F,  32767F,   1F);
  public static final BLonElementQualifiers UNSIGNED_LONG1      = new BLonElementQualifiers(BLonElementType.u16, 0, 65535,  1);
  public static final BLonElementQualifiers S32                 = new BLonElementQualifiers(BLonElementType.s32);
  public static final BLonElementQualifiers U8                  = new BLonElementQualifiers(BLonElementType.u8, 0, 255, 1);
  public static final BLonElementQualifiers U16                 = new BLonElementQualifiers(BLonElementType.u16, 0, 65535,  1);
  public static final BLonElementQualifiers HOUR                = new BLonElementQualifiers(BLonElementType.u8, 0, 23, 1);
  public static final BLonElementQualifiers MINUTE              = new BLonElementQualifiers(BLonElementType.u8, 0, 59, 1);
}
