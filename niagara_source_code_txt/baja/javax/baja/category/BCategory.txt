/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.category;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BCategory models a category.  ICategorizable objects are mapped into 
 * categories using BCategoryMask.  Then the BCategoryService maps those
 * bits into first class BCategory components.
 *
 * @author    Brian Frank
 * @creation  12 Feb 05
 * @version   $Revision: 5$ $Date: 3/12/08 5:40:46 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Provides status for the category. If the disabled flag is set
 the category is not yet assigned an index. The fault flag
 is set if the category has an invalid index.
 */
@NiagaraProperty(
  name = "index",
  type = "int",
  defaultValue = "0"
)
/*
 The category mode is used to determine a user's permissions
 for objects in the category.  The default is "union".
 */
@NiagaraProperty(
  name = "mode",
  type = "BCategoryMode",
  defaultValue = "BCategoryMode.union"
)
public class BCategory
  extends BAbstractCategory
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.category.BCategory(829947661)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "index"

  /**
   * Slot for the {@code index} property.
   * Provides status for the category. If the disabled flag is set
   * the category is not yet assigned an index. The fault flag
   * is set if the category has an invalid index.
   * @see #getIndex
   * @see #setIndex
   */
  public static final Property index = newProperty(0, 0, null);

  /**
   * Get the {@code index} property.
   * Provides status for the category. If the disabled flag is set
   * the category is not yet assigned an index. The fault flag
   * is set if the category has an invalid index.
   * @see #index
   */
  public int getIndex() { return getInt(index); }

  /**
   * Set the {@code index} property.
   * Provides status for the category. If the disabled flag is set
   * the category is not yet assigned an index. The fault flag
   * is set if the category has an invalid index.
   * @see #index
   */
  public void setIndex(int v) { setInt(index, v, null); }

  //endregion Property "index"

  //region Property "mode"

  /**
   * Slot for the {@code mode} property.
   * The category mode is used to determine a user's permissions
   * for objects in the category.  The default is "union".
   * @see #getMode
   * @see #setMode
   */
  public static final Property mode = newProperty(0, BCategoryMode.union, null);

  /**
   * Get the {@code mode} property.
   * The category mode is used to determine a user's permissions
   * for objects in the category.  The default is "union".
   * @see #mode
   */
  public BCategoryMode getMode() { return (BCategoryMode)get(mode); }

  /**
   * Set the {@code mode} property.
   * The category mode is used to determine a user's permissions
   * for objects in the category.  The default is "union".
   * @see #mode
   */
  public void setMode(BCategoryMode v) { set(mode, v, null); }

  //endregion Property "mode"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCategory.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  @Override
  public void changed(Property prop, Context cx)
  {
    super.changed(prop, cx);
    
    if (!isRunning())
    {
      return;
    }
    
    if (prop == index && getParent() instanceof BCategoryService)
    {
      ((BCategoryService)getParent()).rebuildLookup();
    }
  }
  
////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////
  
  /**
   * To string.
   */
  @Override
  public String toString(Context cx)
  {                                
    return getDisplayName(cx) + "[" + getIndex() + "]";
  }

}
