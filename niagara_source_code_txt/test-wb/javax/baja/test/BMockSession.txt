/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.test;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.baja.naming.BHost;
import javax.baja.naming.BISession;
import javax.baja.naming.BOrd;
import javax.baja.naming.BServiceScheme;
import javax.baja.nav.BINavContainer;
import javax.baja.nav.BNavContainer;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BComponentSpace;
import javax.baja.space.BISpace;
import javax.baja.space.BISpaceContainer;
import javax.baja.sys.BComponent;
import javax.baja.sys.BStation;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.test.BMockSessionScheme.MockSessionQuery;


@NiagaraType
/**
 * BMockSession is a {@link javax.baja.naming.BISession} implementation that can be
 * used instead of actual network sessions or {@link javax.baja.naming.BLocalHost}
 * for mocking certain system behaviors.
 *
 * The session name is an arbitrary string used to identify the session during
 * a test execution, making it possible to have several BMockSessions under the
 * single {@link BMockHost#INSTANCE}.
 *
 * To create a BMockSession, callers can use {@link BMockHost#makeSession(String)}
 * or resolve an ord having the format "mockhost:|mocksession:sessionName".
 *
 * Mock spaces of various types can be mounted under mock sessions.
 *
 * @author Matt Boon
 * @since Niagara 4.0
 */
public class BMockSession
  extends BNavContainer
  implements BISession, BISpaceContainer, BINavContainer, BServiceScheme.ServiceSession
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.test.BMockSession(2979906276)1.0$ @*/
/* Generated Wed Jan 05 17:05:31 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMockSession.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Package constructor, callers should use {@link javax.baja.test.BMockHost#makeSession(String)}
   */
  BMockSession(String name)
  {
    super(name);
  }

////////////////////////////////////////////////////////////////
// BISession
////////////////////////////////////////////////////////////////

  /**
   * Does the session have an active logical connection.
   */
  @Override
  public boolean isConnected()
  {
    return true;
  }

  /**
   * Do nothing
   */
  @Override
  public void connect()
    throws Exception
  {
  }

  /**
   * Do nothing
   */
  @Override
  public void disconnect()
  {
  }

  /**
   * Remove the session from the list of {@link javax.baja.test.BMockHost} children
   */
  @Override
  public void close()
  {
    BMockHost.INSTANCE.removeSession(getNavName());
  }

  /**
   * Get the parent host or null if unmounted.
   */
  @Override
  public BHost getHost()
  {
    return BMockHost.INSTANCE;
  }

  /**
   * Get the host absolute ord for this object.
   */
  @Override
  public BOrd getAbsoluteOrd()
  {
    return BOrd.make(BMockHost.INSTANCE.getAbsoluteOrd(), new MockSessionQuery(getNavName()));
  }

  /**
   * Get the ord of this session within its parent host.
   */
  @Override
  public BOrd getOrdInHost()
  {
    return BOrd.make(new MockSessionQuery(getNavName()));
  }

  /**
   * Get the Context to use for this session.
   */
  @Override
  public Context getSessionContext()
  {
    return null;
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  /**
   * Get the primary ord used to navigate to a view on
   * this object.  This should be an normalized absolute
   * ord.
   */
  @Override
  public BOrd getNavOrd()
  {
    return getAbsoluteOrd();
  }

////////////////////////////////////////////////////////////////
// BISpaceContainer
////////////////////////////////////////////////////////////////

  /**
   * Add the specified BISpace to this container.
   */
  @Override
  public BISpace mountSpace(BISpace space)
  {
    synchronized (spaces)
    {
      if (spaces.contains(space))
      {
        throw new IllegalArgumentException("Space already mounted " + space);
      }
      spaces.add(space);
      space.setSpaceContainer(this);
      return space;
    }
  }

  /**
   * Remove the specified BISpace from this container.
   */
  @Override
  public void unmountSpace(BISpace space)
  {
    synchronized (spaces)
    {
      if (!spaces.contains(space))
      {
        throw new IllegalArgumentException("Space not mounted " + space);
      }
      spaces.remove(space);
      space.setSpaceContainer(null);
    }
  }

  /**
   * Get a Iterator to all spaces in this space container.
   *
   * @return an Iterator to all spaces in this space container.
   */
  @Override
  public Iterator<BISpace> getSpaces()
  {
    return spaces.iterator();
  }

////////////////////////////////////////////////////////////////
// BServiceScheme.ServiceSession
////////////////////////////////////////////////////////////////

  @Override
  public BComponent getService(Type type)
  {
    Iterator<BISpace> cursor = getSpaces();
    while (cursor.hasNext())
    {
      BISpace space = cursor.next();
      if (space instanceof BComponentSpace)
      {
        BComponentSpace componentSpace = (BComponentSpace)space;
        if (componentSpace.getRootComponent() instanceof BStation)
        {
          BStation station = (BStation)componentSpace.getRootComponent();
          for (BComponent service :
            station.getServices().getChildComponents())
          {
            if (service.getType().is(type))
            {
              return service;
            }
          }
        }
      }
    }
    return null;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final Set<BISpace> spaces = ConcurrentHashMap.newKeySet();
}
