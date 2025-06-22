/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.*;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.schema.*;

/**
 * BValue is a BObject which may be used as the value 
 * of a property slot.
 * 
 * @author    Brian Frank
 * @creation  4 Dec 02
 * @version   $Revision: 13$ $Date: 7/8/11 4:09:54 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BValue
  extends BObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BValue(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BValue.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Package private constructor.
   */
  BValue()
  {
  }

////////////////////////////////////////////////////////////////
// Identity
////////////////////////////////////////////////////////////////

  /**
   * Every BValue may be "cloned" using the newCopy method.  
   * Note that BSimple's are immutable so they don't actually 
   * allocate a new instance.  BComplex's are cloned using 
   * the CopyHints default.
   */
  public abstract BValue newCopy();

  /**
   * Convenience for {@code newCopy(CopyHints)} where
   * {@code CopyHints.defaultOnClone = !exact}.
   */
  public abstract BValue newCopy(boolean exact);

  /**
   * This method creates a clone of the BValue using the 
   * specified CopyHints.  Note that BSimple's are immutable 
   * so they don't actually allocate a new instance.   
   */
  public abstract BValue newCopy(CopyHints hints);

  /**
   * This method is used to perform a batch clone.
   */
  public static BValue[] newCopy(BValue[] values, CopyHints hints)
  {                             
    // check if any are components and get space
    BComponentSpace space = null;
    for(int i=0; i<values.length; ++i)
    {
      if (values[i] instanceof BComponent)
      {                                  
        BComponent c = (BComponent)values[i];
        BComponentSpace s = c.getComponentSpace();
        if (space != null && space != s)
          throw new IllegalArgumentException("Components must be in same space");
        space = s;
      }
    }
    
    // if we are dealing with components here, then try 
    // to batch load via the ComponentSpace.loadCallbacks
    if (space != null)   
    {
      BValue[] copy = space.getLoadCallbacks().newCopy(values, hints);
      if (copy != null) return copy;
    }
    
    // if the space didn't work out, then just do it locally
    int serialNum = copySerialNum();
    BValue[] copy = new BValue[values.length];
    for(int i=0; i<values.length; ++i)
      copy[i] = ComplexSlotMap.newCopy(values[i], hints, serialNum);
    return copy;
  }                           
  
  static int copySerialNum()
  {                    
    synchronized(copySerialNumLock)
    {
      return ++copySerialNum;
    }
  }
  private static Object copySerialNumLock = new Object();
  private static int copySerialNum = 0;

////////////////////////////////////////////////////////////////
// Framework Support
////////////////////////////////////////////////////////////////

  /**
   * Package private method to obtain the slot map.
   */
  ComplexSlotMap getSlotMap()
  {
    return null;
  }
}
