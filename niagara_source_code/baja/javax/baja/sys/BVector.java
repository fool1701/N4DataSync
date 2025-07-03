/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BVector is a special BComponent used as a "dynamic BStruct".
 * It provides the ability to use dynamic slots like BComponent, but
 * has BStruct-like semantics.
 * 
 * @author    Brian Frank
 * @creation  10 Jun 02
 * @version   $Revision: 4$ $Date: 1/13/04 8:44:01 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BVector
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BVector(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BVector.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Public constructor.
   */
  public BVector()
  {
  }

////////////////////////////////////////////////////////////////
// Component
////////////////////////////////////////////////////////////////

  /**
   * Return false
   */
  @Override
  public boolean isNavChild()
  {
    return false;
  }
}
