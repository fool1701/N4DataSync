/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.role;

import java.util.List;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIPropertyContainer;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A BIRoleSource can resolve BIRoles based on UUIDs.
 * 
 * @author    Joseph Chandler
 * @creation  30 Aug 2014
 * @version   4.0
 * @since     Baja 4.0
 */

@NiagaraType
public interface BIRoleService
  extends BIPropertyContainer
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.role.BIRoleService(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIRoleService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the namespace of this RoleSource
   * 
   * @return the namespace of roleIdentifiers from this source 
   */
  public String getSourceIdentifier();
  
	/**
	 * Locate a BIRole
	 * @param roleIdentifier the identifier of the role being located
	 * @return a BIRole that matches the identifier or 
	 *         null if no Role can be located from this source
	 */
  public BIRole getRole(String roleIdentifier);
	
  /**
   * Returns the list of Role identifiers that this
   * role source can resolve. 
   * 
   * @return List of available roles
   */
  public List<String> getRoleIds(); 
  
  /**
   * Returns the list of enabled Role identifiers that this
   * role source can resolve. 
   * 
   * @return List of available roles
   */
  public List<String> getEnabledRoleIds(); 
  
  
  /**
   * Notify the BIRoleService when the permissions of
   * child roles are altered so that caches can be
   * properly invalidated when needed. 
   * 
   */
  public void clearPermissionsCache();
  
}
