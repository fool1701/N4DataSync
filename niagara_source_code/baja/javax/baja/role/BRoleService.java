/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.baja.agent.AgentList;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIService;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.IllegalNameException;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.user.BUser;
import javax.baja.util.BIRestrictedComponent;

/**
 * BRoleService is the service used to manage 
 * Security Roles.
 *
 * @author    Joseph Chandler
 * @creation  30 Aug 14
 * @version   4.0
 * @since     Baja 4.0
 */
@NiagaraType
/*
 Predefined admin super user role.
 */
@NiagaraProperty(
  name = "admin",
  type = "BAbstractRole",
  defaultValue = "new BAdminRole()"
)
public class BRoleService
  extends BComponent
  implements BIService, BIRoleService, BIRestrictedComponent
{            
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.role.BRoleService(1913120311)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "admin"

  /**
   * Slot for the {@code admin} property.
   * Predefined admin super user role.
   * @see #getAdmin
   * @see #setAdmin
   */
  public static final Property admin = newProperty(0, new BAdminRole(), null);

  /**
   * Get the {@code admin} property.
   * Predefined admin super user role.
   * @see #admin
   */
  public BAbstractRole getAdmin() { return (BAbstractRole)get(admin); }

  /**
   * Set the {@code admin} property.
   * Predefined admin super user role.
   * @see #admin
   */
  public void setAdmin(BAbstractRole v) { set(admin, v, null); }

  //endregion Property "admin"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRoleService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BRoleService()
  {

  }
////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Get the RoleService or throw ServiceNotFoundException.
   */
  public static BRoleService getService()
  {
    return (BRoleService) Sys.getService(TYPE);
  }
                                                                              
////////////////////////////////////////////////////////////////
// Service
////////////////////////////////////////////////////////////////
  
  /**
   * Register this component under "baja:RoleService".
   */
  @Override
  public Type[] getServiceTypes()
  {
    return serviceTypes;
  }
  
  private static Type[] serviceTypes = new Type[] { TYPE, BIRoleService.TYPE};

////////////////////////////////////////////////////////////////
// Service Lifecycle overrides
////////////////////////////////////////////////////////////////
  /**
   * Service start.
   */
  @Override
  public void serviceStarted()
    throws Exception
  {
  }

  /**
   * Service stop.
   */
  @Override
  public void serviceStopped()
    throws Exception
  {
  }

////////////////////////////////////////////////////////////////
// BIRestrictedComponent
////////////////////////////////////////////////////////////////

  /**
   * Only one allowed to live under the station's BServiceContainer.
   * Only Super Users are allowed to add an instance of this type to the station.
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context cx)
  {
    BIRestrictedComponent.checkContextForSuperUser(this, cx);
    BIRestrictedComponent.checkParentForRestrictedComponent(parent, this);
  }

////////////////////////////////////////////////////////////////
// Basic Safety Precautions!
////////////////////////////////////////////////////////////////

  @Override
  public void checkRemove(Property property, Context context)
  {
    String roleId = property.getName();
    BUser user = context != null ? context.getUser() : null;

    Map<String, BIRole> su = getSuperuserRoles();
    BIRole suRoleToRemove = su.get(roleId);
    if(suRoleToRemove != null)
      checkRemoveSuperuserRole(su, user, suRoleToRemove);
    
    if(user != null)
    {
      BValue value = get(property);
      if(value instanceof BIRole && user != null)
        checkRemoveRoleManagement(user, (BIRole) value);  
    }
    
  }
  
  /*
   * Prevent the removal of the system's last super user role.
   * Prevent the removal of the current user's only super user role. 
   */
  private void checkRemoveSuperuserRole(Map<String, BIRole> superUsers, BUser user, BIRole suRole)
  {
    if(superUsers.size() <= 1)
    {
      throw new LocalizableRuntimeException("baja", 
          "role.removal.cannotRemoveOnlySuperUserRole", 
          new String[] {suRole.getRoleIdentifier()});
      
    }else if(user != null)
    {     
      Set<BIRole> userRoles = user.getRoleSet();
      if(userRoles.contains(suRole)) 
      {
        userRoles.remove(suRole);
        boolean hasOtherSuperRole = false;
        for(BIRole role : userRoles)
        {
          if(role.getPermissions().isSuperUser())
          {
            hasOtherSuperRole = true;
            break;
          }
        }

        if(!hasOtherSuperRole)
          throw new LocalizableRuntimeException("baja", 
              "role.removal.cannotRemoveYourOnlySuperUserRole",
              
              new String[] {suRole.getRoleIdentifier()});
      }
    }
  }
  
  /*
  * Prevent the removal of a role if the user removing the role
  * would lose the ability to manage roles.
  */
  private void checkRemoveRoleManagement(BUser user, BIRole role)
  {
    BUser userWithoutRole = (BUser) user.newCopy();
    userWithoutRole.removeRole(this, role.getRoleIdentifier(), Context.skipValidate);

    BPermissions permissions = getPermissions(userWithoutRole);
    if(!permissions.has(BPermissions.ADMIN_WRITE))
      throw new LocalizableRuntimeException("baja", 
          "role.removal.cannotRemoveYourOnlyRoleManagementRole", new String[] {role.getRoleIdentifier()});
  }
  
  /*
   * If a new role is added to a BRoleService the permission cache must be cleared.
   */
  @Override
  public void added(Property property, Context context)
  {
    if(get(property) instanceof BIRole)
      clearPermissionsCache();
  }
  

  /**
   * Remove deleted roles from all users
   * 
   * @param property
   * @param oldValue
   * @param context
   */
  @Override
  public void removed(Property property, BValue oldValue, Context context)
  {
    if(!(oldValue instanceof BIRole))
      return;
    
    clearPermissionsCache();
    for(BIRoleListener rl : getRoleListeners())
    {
      try
      {
        rl.removeRole(this, property.getName());
      }catch(Exception e)
      {
        //Ignore misbehaving listeners and continue notifying all listeners.
        notificationError(rl, e);
      }
    }
  }
  
  @Override
  public void checkRename(Property property, String newName, Context context)
  {
    if(newName.equalsIgnoreCase(ADMIN_ROLE))
      throw new IllegalNameException("baja","role.renameToAdmin", null);
  }
  
  @Override
  public void checkAdd(String name, BValue value, int flags, BFacets facets, Context context)
  {
    super.checkAdd(name, value, flags, facets, context);
    if(name.equalsIgnoreCase(ADMIN_ROLE))
      throw new IllegalNameException("baja","role.renameToAdmin", null);
  }
  
  /**
   * Rename the role in all users
   * 
   * @param property
   * @param oldName
   * @param context
   */
  @Override
  public void renamed(Property property, String oldName, Context context)
  {
    if(!(get(property) instanceof BIRole))
      return;
    
    clearPermissionsCache();
    for(BIRoleListener rl : getRoleListeners())
    {
      try
      {
        rl.renameRole(this, oldName, property.getName());
      }catch(Exception e)
      {
        //Ignore misbehaving listeners and continue notifying all listeners.
        notificationError(rl, e);
      }
    }
  }

  @Override
  public void changed(Property property, Context context)
  {
    if (get(property) instanceof BIRole)
    {
      changedRole((BIRole) get(property), context);
    }
  }

  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList list = super.getAgents(cx);

    int wbRoleManager = list.indexOf("wbutil:RoleManager");
    int html5RoleManager = list.indexOf("webEditors:RoleManager");

    if (html5RoleManager >= 0 && html5RoleManager < wbRoleManager) {
      list.swap(wbRoleManager, html5RoleManager);
    }
    return list;
  }

  public void changedRole(BIRole role, Context context)
  {
    clearPermissionsCache();
    for(BIRoleListener rl : getRoleListeners())
    {
      try
      {
        rl.changedRole(this, role.getRoleIdentifier());
      }catch(Exception e)
      {
        //Ignore misbehaving listeners and continue notifying all listeners.
        notificationError(rl, e);
      }
    }
  }
  
  private static void notificationError(BIRoleListener rl, Exception e)
  {
    log.warning("Error notifying role listener: " + rl + e.getMessage());
  }
  
////////////////////////////////////////////////////////////////
// Utilities
////////////////////////////////////////////////////////////////

  private BIRoleListener[] getRoleListeners()
  {
    BComplex parent = getParent();
    if(parent != null)
    {
      BComponent parentComponent = parent.getParentComponent();
      if(parentComponent != null)
        return parentComponent.getChildren(BIRoleListener.class);
    }
    return new BIRoleListener[0]; 
  }
  
  @Override
  public void clearPermissionsCache()
  {
    synchronized(SUPERUSER_LOCK)
    {
      this.superUsers = null;
    }
  }
  /*
   * This superuser cache will only work if the
   * child roles notify there BIRoleService parent
   * when a superuser flag is added or removed.
   */
  private Map<String, BIRole> getSuperuserRoles()
  {
    synchronized(SUPERUSER_LOCK)
    {
      if(superUsers == null)
      {
        superUsers = new HashMap<>();
        for(BIRole role : getChildren(BIRole.class))
        {
          if(role.getPermissions().isSuperUser())
            superUsers.put(role.getRoleIdentifier(), role);
        }
      }
      return superUsers;
    }
  }
  
////////////////////////////////////////////////////////////////
// BIRoleSource -- in the future, allow other RoleSources to 
//                 be checked (ldap?)
////////////////////////////////////////////////////////////////

  /**
   * {@inheritDoc}
   * @param roleIdentifier {@inheritDoc}. The supplied roleIdentifier MUST be an <b>escaped</b> slot name.
   */
  @Override
  public BIRole getRole(String roleIdentifier)
  {
  	BObject child = get(roleIdentifier);
  	if(child != null && child instanceof BIRole)
  	  return (BIRole) child;
  	
  	return null;
  }
  
  @Override
  public String getSourceIdentifier()
  {
    return DEFAULT_ROLE_SERVICE_NAMESPACE;
  }
  
////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////
  /**
   * {@inheritDoc}
   * @return {@inheritDoc} The returned List will contain <b>escaped</b> slot names.
   */
  @Override
  public List<String> getRoleIds() 
  {
    return getRoles(true);
  }

  /**
   * {@inheritDoc}
   * @return {@inheritDoc}. The returned List will contain <b>escaped</b> slot names.
   */
  @Override
  public List<String> getEnabledRoleIds()
  {
    return getRoles(false);
  }

  /**
   * @param all if true, all roles will be included. Otherwise only enabled roles will be included.
   * @return A List of all (or enabled) roles. The names are <b>escaped</b> slot names.
   */
  private List<String> getRoles(boolean all)
  { 
    BIRole[] children = getChildren(BIRole.class);
    List<String> roles = new ArrayList<>(children.length);
    for(BIRole role : children)
    {
      if(all || role.getEnabled())
        roles.add(role.getRoleIdentifier());
    }
    return roles;
  }
  
 /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("navOnly/roleService.png");
  

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////
  private Map<String, BIRole> superUsers = null;
  
  private final Object SUPERUSER_LOCK = new Object();
  
  
  public static final String ADMIN_ROLE = "admin";
  
  private static final String DEFAULT_ROLE_SERVICE_NAMESPACE="";
  static final Logger log = Logger.getLogger("sys.service");
}
