/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.role;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissionsMap;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BAdminRole is the super user role.
 *
 * @author    Joseph Chandler
 * @creation  30 Aug 2014
 * @version   4
 * @since     Baja 4.0
 */
@NiagaraType
public class BAdminRole
  extends BAbstractRole
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.role.BAdminRole(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAdminRole.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BAdminRole() { }
  
////////////////////////////////////////////////////////////////
// BIRole
////////////////////////////////////////////////////////////////

  @Override
  public String getRoleIdentifier()
  {
    return getName();
  }
  
  @Override
  public boolean getEnabled()
  {
	  return true;
  }
  
  @Override
  public BPermissionsMap getPermissions()
  {
	//The admin role always grants super user access.
    return BPermissionsMap.SUPER_USER;
  }
}
