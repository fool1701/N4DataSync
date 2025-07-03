/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.role;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissionsMap;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIRole represents a group of permissions in the Baja framework.
 *
 * @author    Joseph Chandler
 * @creation  30 Aug 2014
 * @version   4.0
 * @since     Baja 4.0
 */
@NiagaraType
public interface BIRole
  extends BInterface, Comparable<BIRole>
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.role.BIRole(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIRole.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  public String getRoleIdentifier();
  
  public BPermissionsMap getPermissions();

  public boolean getEnabled();

}
