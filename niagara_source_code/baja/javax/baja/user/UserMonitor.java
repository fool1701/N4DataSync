/*
 * Copyright 2013, Tridium Inc, All Rights Rervered.
 */
package javax.baja.user;

import javax.baja.security.BPasswordAuthenticator;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BValue;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Subscriber;

import com.tridium.sys.schema.Fw;

/**
 * Detects and fires User Events on User related Components.
 * <p>
 * Primarily this is used by the UserService and UserPrototypes
 * Components. Since both components require events to be fired, it
 * makes sense for them to share the same code.
 * <p>
 * In order to use this class, a Component with a Topic named
 * <code>userEvent</code> which fires {@link BUserEvent} must be passed in.
 * The specified Component must also forward all events to
 * the {@link #fw(int, Object, Object, Object, Object)} method.
 *
 * @see BUserService
 * @see BUserPrototypes
 * @see BUserEvent
 *
 * @author   Gareth Johnson
 * @creation 30 Jul 2013
 * @since    Niagara 3.8
 */
public final class UserMonitor
{
  UserMonitor(BComponent comp)
  {
    if (comp.getTopic("userEvent") == null)
      throw new BajaRuntimeException("Component must implement userEvent Topic!");

    this.comp = comp;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  void fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.DESCENDANTS_STARTED:
        fwDecendantsStarted();
        break;
      case Fw.DESCENDANTS_STOPPED:
        fwDecendantsStopped();
        break;
      case Fw.ADDED:
        fwAdded((Property)a, (Context)b);
        break;
      case Fw.REMOVED:
        fwRemoved((Property)a, (BValue)b, (Context)c);
        break;
      case Fw.RENAMED:
        fwRenamed((Property)a, (String)b, (Context)c);
        break;
      case Fw.CHANGED:
        fwChanged((Property)a, (Context)b);
    };
  }

  void setModified(BUser user)
  {
    user.updateVersion();
    fireUserEvent(new BUserEvent(BUserEvent.MODIFIED, user));
  }

////////////////////////////////////////////////////////////////
// Fw
////////////////////////////////////////////////////////////////

  private void fwDecendantsStarted()
  {
    subscribeToAllUsers();
  }

  private void fwDecendantsStopped()
  {
    subscriber.unsubscribeAll();
  }

  private void fwAdded(Property prop, Context cx)
  {
    if (prop.getType().is(BUser.TYPE))
    {
      BUser user = (BUser)comp.get(prop);
      if (user.getParent() instanceof BUserService &&
          user.getAuthenticator() instanceof BPasswordAuthenticator)
      {
        ((BPasswordAuthenticator)user.getAuthenticator()).convertToPbkdf2Password();
      }

      if (!comp.isRunning()) return;
      subscriber.subscribe(user, 10);
      cleanupSubscriptions();
      fireUserEvent(BUserEvent.makeAdded(user));
    }
  }

  private void fwRemoved(Property prop, BValue oldValue, Context cx)
  {
    if (!comp.isRunning()) return;

    if (prop.getType().is(BUser.TYPE))
    {
      BUser user = (BUser)oldValue;
      subscriber.unsubscribe(user);
      cleanupSubscriptions();
      fireUserEvent(BUserEvent.makeRemoved(user, prop.getName()));
    }
  }

  private void fwRenamed(Property prop, String oldName, Context cx)
  {
    if (!comp.isRunning()) return;
    if (prop.getType().is(BUser.TYPE))
    {
      BUser user = (BUser)comp.get(prop);
      cleanupSubscriptions();
      fireUserEvent(BUserEvent.makeRenamed(user, oldName));
    }
  }

  private void fwChanged(Property prop, Context cx)
  {
    if (!comp.isRunning()) return;
    if (prop.getType().is(BUser.TYPE))
    {
      BUser user = (BUser)comp.get(prop);

      if (user.getParent() instanceof BUserService &&
          user.getAuthenticator() instanceof BPasswordAuthenticator)
      {
        ((BPasswordAuthenticator)user.getAuthenticator()).convertToPbkdf2Password();
      }

      subscriber.subscribe(user, 10);
      cleanupSubscriptions();
      fireUserEvent(BUserEvent.makeModified(user));
    }
  }

////////////////////////////////////////////////////////////////
// Subscription
////////////////////////////////////////////////////////////////

  private void subscribeToAllUsers()
  {
    subscriber.unsubscribeAll();

    SlotCursor<Property> c = comp.getProperties();
    while (c.next(BUser.class))
    {
      BUser user = (BUser)c.get();
      subscriber.subscribe(user, 10);
    }
  }

  private void cleanupSubscriptions()
  {
    BComponent[] comps = subscriber.getSubscriptions();

    for (int i = 0; i < comps.length; ++i)
    {
      // Remove any non-mounted Components from the Subscriber
      if (!comps[i].isMounted())
        subscriber.unsubscribe(comps[i]);
    }
  }

  private class UserSubscriber extends Subscriber
  {
    @SuppressWarnings("fallthrough")
    public void event(BComponentEvent event)
    {
      int eid = event.getId();
      BUser user = getUser(event);
      if (user == null) return;

      // This switch contains a fall-through if the slot is not the user version
      switch (eid)
      {
        case BComponentEvent.PROPERTY_ADDED:
        case BComponentEvent.PROPERTY_CHANGED:
          if (event.getSlot() == BUser.version)
            break;
        case BComponentEvent.FLAGS_CHANGED:
        case BComponentEvent.PROPERTY_REMOVED:
        case BComponentEvent.PROPERTY_RENAMED:
          BUserEvent evt = new BUserEvent(BUserEvent.MODIFIED, user);
          user.updateVersion();
          fireUserEvent(evt);
          break;
      }
    }

    private BUser getUser(BComponentEvent event)
    {
      BComponent src = event.getSourceComponent();
      if (src instanceof BUser)
        return (BUser)src;
      else
      {
        BComplex user = src.getParent();
        while (user != null && !(user instanceof BUser))
          user = user.getParent();

        return (BUser)user;
      }
    }
  }

////////////////////////////////////////////////////////////////
// Util
////////////////////////////////////////////////////////////////

  private void fireUserEvent(BUserEvent event)
  {
    comp.fire(comp.getTopic("userEvent"), event);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BComponent comp;
  private UserSubscriber subscriber = new UserSubscriber();
}
