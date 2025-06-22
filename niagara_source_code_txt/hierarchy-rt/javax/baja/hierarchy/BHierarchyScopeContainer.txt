/*
  Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hierarchy;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A HierarchyScopeContainer groups the HierarchyScopes for a given Hierarchy.
 *
 * @author Blake Puhak
 * @since Niagara 4.0
 */
@NiagaraType
public class BHierarchyScopeContainer
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hierarchy.BHierarchyScopeContainer(2979906276)1.0$ @*/
/* Generated Tue Jan 18 11:31:27 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHierarchyScopeContainer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Allow only BHierarchy parents.
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BHierarchy;
  }

  /**
   * Returns all of the licensed hierarchy scopes
   * @return array of licensed hierarchy scopes available.
   */
  public BHierarchyScope[] getHierarchyScopes()
  {
    BHierarchyScope[] scopes = getChildren(BHierarchyScope.class);
    BHierarchyService service = (BHierarchyService)Sys.getService(BHierarchyService.TYPE);
    if (service != null)
    {
      scopes = BHierarchyService.getLicensedScopes(scopes);
    }
    return scopes;
  }
}
