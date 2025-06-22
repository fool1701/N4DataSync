/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.role;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissionsMap;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BRole is the component which represents 
 * a group of permissions in the Baja framework.
 *
 * @author    Joseph Chandler
 * @creation  30 Aug 2014
 * @version   4
 * @since     Baja 4.0
 */

@NiagaraType
/*
 If set to false, then the role will not be used when searching for roles.
 */
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true",
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
/*
 Permissions granted this user.
 */
@NiagaraProperty(
  name = "permissions",
  type = "BPermissionsMap",
  defaultValue = "BPermissionsMap.DEFAULT",
  facets = @Facet("BFacets.make(BFacets.SECURITY, BBoolean.TRUE)")
)
public class BRole
  extends BAbstractRole
{ 

  


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.role.BRole(4255444763)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * If set to false, then the role will not be used when searching for roles.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code enabled} property.
   * If set to false, then the role will not be used when searching for roles.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * If set to false, then the role will not be used when searching for roles.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "permissions"

  /**
   * Slot for the {@code permissions} property.
   * Permissions granted this user.
   * @see #getPermissions
   * @see #setPermissions
   */
  public static final Property permissions = newProperty(0, BPermissionsMap.DEFAULT, BFacets.make(BFacets.SECURITY, BBoolean.TRUE));

  /**
   * Get the {@code permissions} property.
   * Permissions granted this user.
   * @see #permissions
   */
  public BPermissionsMap getPermissions() { return (BPermissionsMap)get(permissions); }

  /**
   * Set the {@code permissions} property.
   * Permissions granted this user.
   * @see #permissions
   */
  public void setPermissions(BPermissionsMap v) { set(permissions, v, null); }

  //endregion Property "permissions"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRole.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BRole()
  {
  }
  
////////////////////////////////////////////////////////////////
// BIRole
////////////////////////////////////////////////////////////////

  @Override
  public String getRoleIdentifier()
  {
    return getName();
  }
 

  @Override
  public void changed(Property p, Context cx)
  {
    if(isRunning()){
      if(getParent() instanceof BRoleService){
        ((BRoleService) getParent()).changedRole(this, cx);
      }
    }
  }
  
}
