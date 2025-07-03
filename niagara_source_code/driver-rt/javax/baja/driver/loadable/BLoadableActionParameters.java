/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.loadable;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BStruct;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BLoadableActionParameters is the base class for
 * parameters specifying a lifecycle operation on
 * a BILoadable.
 *
 * @author    Robert Adams
 * @creation  4 Feb 02
 * @version   $Revision: 8$ $Date: 3/30/04 8:12:50 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Should this operation recurse through all the
 BILoadable children in the descendent tree.
 */
@NiagaraProperty(
  name = "recursive",
  type = "boolean",
  defaultValue = "true"
)
public abstract class BLoadableActionParameters
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.loadable.BLoadableActionParameters(1480563034)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "recursive"

  /**
   * Slot for the {@code recursive} property.
   * Should this operation recurse through all the
   * BILoadable children in the descendent tree.
   * @see #getRecursive
   * @see #setRecursive
   */
  public static final Property recursive = newProperty(0, true, null);

  /**
   * Get the {@code recursive} property.
   * Should this operation recurse through all the
   * BILoadable children in the descendent tree.
   * @see #recursive
   */
  public boolean getRecursive() { return getBoolean(recursive); }

  /**
   * Set the {@code recursive} property.
   * Should this operation recurse through all the
   * BILoadable children in the descendent tree.
   * @see #recursive
   */
  public void setRecursive(boolean v) { setBoolean(recursive, v, null); }

  //endregion Property "recursive"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLoadableActionParameters.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Constructor.
   */
  public BLoadableActionParameters() {}

  /**
   * Full constructor.
   * @param recursive
   */
  public BLoadableActionParameters(boolean recursive)
  {
    setRecursive(recursive);
  }
}
