/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.data.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;

/**
 * The BNumber is the abstract super class of numeric
 * simples:  BInteger and BFloat.
 *
 * @author    Brian Frank
 * @creation  1 Feb 00
 * @version   $Revision: 8$ $Date: 3/31/04 9:02:36 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public abstract class BNumber
  extends BSimple
  implements BIComparable, BINumeric, BIDataValue
{                                                

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Package private constuctor.
   */                           
  BNumber()
  {
  }  
                   
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

                   
  /**
   * Get the number as an integer.
   */
  public abstract int getInt();

  /**
   * Get the number as a long.
   */
  public abstract long getLong();

  /**
   * Get the number as an float.
   */
  public abstract float getFloat();

  /**
   * Get the number as an double.
   */
  public abstract double getDouble();      

////////////////////////////////////////////////////////////////
// BINumeric
////////////////////////////////////////////////////////////////  

  /**
   * @return the value as double.
   */
  @Override
  public final double getNumeric()
  {
    return getDouble();
  }

  /**
   * Return BFacets.NULL.
   */
  @Override
  public final BFacets getNumericFacets()
  {
    return BFacets.NULL;
  }

////////////////////////////////////////////////////////////////
// IDataValue
////////////////////////////////////////////////////////////////

  /**
   * Return this instance since it's already a data value.
   */
  @Override
  public final BIDataValue toDataValue()
  { 
    return this; 
  }
  
////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNumber.class);

  /**
   * Converts this instance of a BNumber subclass to a
   * different BNumber subclass.
   *
   * This is a convenient method to convert between the different core
   * subclasses for BNumber such as BDouble, BInteger, BLong, and BFloat.
   *
   * @param number the instance of BNumber to convert to the given type
   * @param type the type of BNumber to convert to
   * @return BNumber
   *
   * @since Niagara 4.10
   */
  public static BNumber cast(BNumber number, Type type)
  {
    return cast(number.getNumeric(), type);
  }

  /**
   * Converts the provided double to a BNumber subclass.
   *
   * @param number the double to turn into a BNumber
   * @param type the type of BNumber to convert to
   * @return BNumber
   *
   * @since Niagara 4.10
   */
  public static BNumber cast(double number, Type type)
  {
    switch (type.getDataTypeSymbol())
    {
      case 'i': return BInteger.make((int) number);
      case 'l': return BLong.make((long) number);
      case 'f': return BFloat.make((float) number);
      case 'd': return BDouble.make(number);
      default: throw new IllegalArgumentException("Invalid type to cast to BNumber: " + type);
    }
  }
}
