/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BStruct is the base class for a BObject which has
 * one or more properties.  BStructs must only declare
 * properties which are typed as boolean, int, long,
 * float, double, String, BSimple, or other BStructs.
 * This means that a BStruct may never have a BComponent
 * property.  BStructs only support Property slots, never
 * Actions or Topics.
 * 
 * @author Brian Frank on 27 Nov 00
 * @since Baja 1.0
 */
@NiagaraType
public class BStruct
  extends BComplex
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BStruct(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStruct.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Public no arg constructor.
   */
  public BStruct()
  {
  }
}
