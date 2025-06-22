/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.role;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIPropertyContainer;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A BIRoleListener will receive notifications when 
 * a peer BIRoleSource service removes or adds a role. 
 * 
 * @author    Joseph Chandler
 * @creation  30 Aug 2014
 * @version   4.0
 * @since     Baja 4.0
 */

@NiagaraType
public interface BIRoleListener
  extends BIPropertyContainer
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.role.BIRoleListener(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIRoleListener.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

	/**
	 * Receive notification that a role source has removed
	 * a role and that any principals currently using the 
	 * role should have that role removed. 
	 * 
	 * @param source the BIRoleService
	 * @param roleIdentifier that was removed
	 */
  public void removeRole(BIRoleService source, String roleIdentifier);
	
  /**
   * Receives notifications when a role's identifier has been changed
   * 
   * @param source the BIRoleService
   * @param oldIdentifier The previous role identifier 
   * @param newIdentifier The new role identifier
   */
  public void renameRole(BIRoleService source, String oldIdentifier, String newIdentifier);

  /**
   * Receives notifications when a role's identifier has been changed
   *
   * @param source the identifier of the BIRoleService
   * @param identifier The previous role identifier
   */
  public void changedRole(BIRoleService source, String identifier);
  
}
