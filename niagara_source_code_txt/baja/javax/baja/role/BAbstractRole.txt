/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.role;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BAbstractRole is role base class.
 *
 * @author    Joseph Chandler
 * @creation  30 Aug 2014
 * @version   4
 * @since     Baja 4.0
 */
@NiagaraType
public abstract class BAbstractRole
  extends BComponent
  implements BIRole, Comparable<BIRole>
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.role.BAbstractRole(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbstractRole.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
////////////////////////////////////////////////////////////////
//Comparable
////////////////////////////////////////////////////////////////

  @Override
  public int compareTo(BIRole other)
  {
    if(other!=null)
    {
      String roleId = getRoleIdentifier();
      if(roleId != null)
      {
        String otherRoleId = other.getRoleIdentifier();
        if(otherRoleId != null)
          return roleId.compareTo(otherRoleId);
      }
    }
    return -1;
  }
}
