/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BSingleton is a BObject which allocates exactly one 
 * instance per VM.  It provides a useful pattern for
 * typed libraries and factories.  BSingletons must 
 * provide a static final called INSTANCE which declares
 * the singleton's instance.  Subclasses should also
 * declare protected or private constructors to prevent
 * unwanted instances from being allocated.
 * 
 * @author    Brian Frank
 * @creation  11 Dec 02
 * @version   $Revision: 2$ $Date: 1/28/03 8:03:47 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BSingleton
  extends BObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BSingleton(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSingleton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Protected constructor.
   */
  protected BSingleton()
  {
  }
}
