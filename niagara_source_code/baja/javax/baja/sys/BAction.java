/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BAction is the object type used to add
 * new actions to a BComponent.
 *
 * @author    Brian Frank
 * @creation  28 Sept 00
 * @version   $Revision: 14$ $Date: 3/28/05 9:23:09 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BAction
  extends BStruct
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BAction(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAction.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the parameter type for the action, or
   * if the action takes no arguments return null.
   */
  public abstract Type getParameterType();
  
  /**
   * Get the default parameter to use for the 
   * action, or null if the action takes no
   * arguments.
   */
  public abstract BValue getParameterDefault();
  
  /**
   * Invoke the action on the specified target with
   * given argument array.
   */
  public abstract BValue invoke(BComponent target, BValue arg)
    throws Exception;

  /**
   * Get the return type for the action, or
   * null if the action doesn't return a value.
   */
  public abstract Type getReturnType();

  /**
   * Get the facets for the action or return BFacets.NULL.
   * Default returns BFacets.NULL.
   */
  public BFacets getFacets()
  {
    return BFacets.NULL;
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("action.png");
  
}
