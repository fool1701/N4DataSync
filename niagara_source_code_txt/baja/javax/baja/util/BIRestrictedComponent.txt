/**
 * Copyright 2015 Tridium, Inc. - All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BInterface;
import javax.baja.sys.BStation;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.IllegalChildException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.util.ComponentTreeCursor;
import com.tridium.util.ObjectUtil;

/**
 * Defines an interface for station components (especially services)
 * that should be restricted to a certain location in a station and
 * also restricted based on the presence of other restricted components (ie. to
 * prevent duplicate instances).  The default behavior for a BIRestrictedComponent
 * is that only one instance can live under the Services frozen slot of the root
 * BStation object, or under a descendant ServiceContainer of that frozen Services
 * slot.  By default, when enforcing that only one instance of its type is
 * allowed, the duplicate check includes a type being a super/sub type of
 * an existing instance.
 *
 * An implementation class should strongly consider being made final, or if not,
 * make the methods final in order to prevent a subclass from overriding the
 * behavior to remove the restrictions.
 *
 * @author Scott Hoye
 * @creation Jan 13, 2015
 * @since Niagara 4.0
 */
@NiagaraType
public interface BIRestrictedComponent
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BIRestrictedComponent(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:39 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIRestrictedComponent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// RestrictedComponent
////////////////////////////////////////////////////////////////

  /**
   * This method is called just before this BIRestrictedComponent instance is
   * mounted in the parent argument and gives an opportunity to cancel the
   * mount by throwing a <code>LocalizableRuntimeException</code> if the parent
   * is an invalid location for this BIRestrictedComponent instance to be mounted.
   * By default, restricted components are only allowed to live under the
   * station's BServiceContainer (or a descendant BServiceContainer of that
   * root BServiceContainer), and no duplicates are allowed.  Be aware
   * that this method can get called for both an online or offline station,
   * so the implementation should work in both cases.
   */
  default void checkParentForRestrictedComponent(BComponent parent, Context cx)
  {
    checkParentForRestrictedComponent(parent, this);
  }

////////////////////////////////////////////////////////////////
// Default implementation
////////////////////////////////////////////////////////////////

  /**
   * Default implementation that checks to see if the parent argument is
   * a valid location for the BIRestrictedComponent argument to be mounted
   * and throws an <code>IllegalChildException</code> if it is not.
   * This default implementation enforces that the restricted component child
   * is only allowed to live under the station's BServiceContainer (or a
   * descendant BServiceContainer of that root BServiceContainer), and no
   * duplicates are allowed.
   */
  static void checkParentForRestrictedComponent(BComponent parent, BIRestrictedComponent child)
  {
    checkParentIsServiceContainer(parent, child);
    checkForDuplicates(parent, child, /*allowNonExactTypes*/false);
  }

  /**
   * Checks to see if the parent argument is a valid <code>BServiceContainer</code>
   * instance that is suitable for the BIRestrictedComponent child to live
   * and throws an <code>IllegalChildException</code> if it is not.
   */
  static void checkParentIsServiceContainer(BComponent parent, BIRestrictedComponent child)
  {
    if (!parent.getType().is(BServiceContainer.TYPE))
      throw new IllegalChildException("baja", "RestrictedToServiceContainer",
        new Object[] { parent.getType(), child.getType() });
  }

  /**
   * Checks to make sure that there are no duplicate instances of the
   * BIRestrictedComponent child that already exist under the station's BServiceContainer (or a
   * descendant BServiceContainer of that root BServiceContainer).
   * If a duplicate is found, an <code>IllegalChildException</code> is thrown.
   *
   * @param parent The proposed parent where there is a pending mount attempt for the given child.
   * @param child The child that is pending a mount
   * @param allowNonExactTypes if true, the check for duplicates will look for only exact type
   *                           matches (allowing multiple instances of a super/sub type). If false,
   *                           the check for duplicates will include looking for types of a
   *                           matching super/sub type.
   */
  static void checkForDuplicates(BComponent parent, BIRestrictedComponent child, boolean allowNonExactTypes)
  {
    BStation station = ObjectUtil.getStation(parent.getComponentSpace());
    if (station == null) return;
    BServiceContainer services = station.getServices();
    ComponentTreeCursor c = new ComponentTreeCursor(services, null);
    while(c.next())
    {
      BValue value = c.get();
      if ((value != child) &&
        value.getType().is(BIRestrictedComponent.TYPE))
      {
        if (allowNonExactTypes)
        {
          if (child.getType().equals(value.getType()))
            throw new IllegalChildException("baja", "DuplicateRestrictedComponent",
              new Object[] { parent.getType(), child.getType() });
        }
        else if (value.getType().is(child.getType()) || child.getType().is(value.getType()))
          throw new IllegalChildException("baja", "DuplicateRestrictedComponent",
            new Object[] { parent.getType(), child.getType() });
      }
    }
  }

  /**
   * Checks to see if the context identifies a user invoked the pending
   * add operation, and checks to ensure that the user is a Super User.
   * If a non-null user is found, and the user is not a Super User, then
   * an <code>IllegalChildException</code> is thrown.
   */
  static void checkContextForSuperUser(BIRestrictedComponent component, Context cx)
  {
    if (cx != null && cx.getUser() != null && !cx.getUser().getPermissions().isSuperUser())
      throw new IllegalChildException("baja", "RestrictedServiceException",
                                      new Object[] { component.getType() });
  }

}
