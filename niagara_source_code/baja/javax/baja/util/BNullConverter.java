/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;


/**
 * The BNullConverter is used to represent no conversion.
 *
 * @author    Brian Frank
 * @creation  12 May 04
 * @version   $Revision: 2$ $Date: 5/19/04 4:24:20 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BNullConverter
  extends BConverter
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BNullConverter(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:39 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNullConverter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
    
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Return true.
   */
  @Override
  public boolean isNull()
  {
    return true;
  }     
  
  /**
   * Return the to object.
   */
  @Override
  public BObject convert(BObject from, BObject to, Context cx)
  {
    return to;
  }
  
}
