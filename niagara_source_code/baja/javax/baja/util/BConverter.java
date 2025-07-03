/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BConverter is used to convert from one BObject type
 * to another BObject type.  Converters are registered
 * in the module manifest and can be queried via the
 * Registry API.
 *
 * @author    Brian Frank
 * @creation  12 May 04
 * @version   $Revision: 3$ $Date: 5/27/04 12:16:26 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BConverter
  extends BStruct
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BConverter(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:39 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BConverter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
    
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////      
  
  /**
   * This method is used to give the converter a chance
   * to intialize its state based on a from and to type.
   */
  public void init(BObject from, BObject to)
  {
  }  
  
  /**
   * Convenience for <code>convert(from, to, null)</code>.
   */
  public final BObject convert(BObject from, BObject to)
  {
    return convert(from, to, null);
  }
  
  
  /**
   * Convert the first object to the second object and return
   * the result.  If the to-object is a complex then the conversion
   * should be in-place and the to-object should be returned.
   * If the to-object is a simple, then the return a new instance 
   * of the same type.
   */
  public abstract BObject convert(BObject from, BObject to, Context cx);
  
}
