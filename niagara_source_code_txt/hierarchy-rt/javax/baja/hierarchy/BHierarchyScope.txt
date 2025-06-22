/*
  Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hierarchy;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BHierarchyScope defines a space that a hierarchy is built against.
 *
 * @author Blake Puhak
 * @creation 31 March 2014
 * @since Niagara 4.0
 */
@NiagaraType
@NiagaraProperty(
  name = "scopeOrd",
  type = "BOrd",
  defaultValue = "BOrd.NULL"
)
public class BHierarchyScope
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hierarchy.BHierarchyScope(1831606731)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "scopeOrd"

  /**
   * Slot for the {@code scopeOrd} property.
   * @see #getScopeOrd
   * @see #setScopeOrd
   */
  public static final Property scopeOrd = newProperty(0, BOrd.NULL, null);

  /**
   * Get the {@code scopeOrd} property.
   * @see #scopeOrd
   */
  public BOrd getScopeOrd() { return (BOrd)get(scopeOrd); }

  /**
   * Set the {@code scopeOrd} property.
   * @see #scopeOrd
   */
  public void setScopeOrd(BOrd v) { set(scopeOrd, v, null); }

  //endregion Property "scopeOrd"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHierarchyScope.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  public BHierarchyScope() { }

  public BHierarchyScope(BOrd scopeOrd)
  {
    setScopeOrd(scopeOrd);
  }

  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return (parent instanceof BHierarchyScopeContainer);
  }

  /**
   * A Hierarchy can have only have BLevelDef children.
   */
  @Override
  public boolean isChildLegal(BComponent child)
  {
    return false;
  }
}
