/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.test;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import javax.net.SocketFactory;

import javax.baja.naming.BHost;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BMockHost is a singleton host under which mock sessions and spaces can be mounted
 *
 * @author Matt Boon
 * @since Niagara 4.0
 */

@NiagaraType
@NiagaraSingleton
public class BMockHost
  extends BHost
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.test.BMockHost(2747097003)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BMockHost INSTANCE = new BMockHost();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMockHost.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor, use {@link #INSTANCE}
   */
  private BMockHost()
  {
    super("mockhost");
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public void clearSessions()
  {
    synchronized(sessionMap)
    {
      sessionMap.clear();
    }
  }

  public BMockSession makeSession(String sessionName)
  {
    synchronized(sessionMap)
    {
      if (!sessionMap.containsKey(sessionName))
      {
        sessionMap.put(sessionName, new BMockSession(sessionName));
      }
      return sessionMap.get(sessionName);
    }
  }

  public void removeSession(String sessionName)
  {
    synchronized(sessionMap)
    {
      sessionMap.remove(sessionName);
    }
  }

////////////////////////////////////////////////////////////////
// BHost
////////////////////////////////////////////////////////////////

  /**
   * No IP Support
   * @throws java.lang.UnsupportedOperationException
   */
  @Override
  public Socket openSocket(int port)
    throws IOException
  {
    throw new UnsupportedOperationException();
  }

  /**
   * No IP Support
   * @throws java.lang.UnsupportedOperationException
   */
  @Override
  public Socket openSocket(int port, SocketFactory socketFactory)
    throws IOException
  {
    throw new UnsupportedOperationException();
  }

  /**
   * No IP Support
   * @throws java.lang.UnsupportedOperationException
   */
  @Override
  public Socket openSocket(int port, SocketFactory socketFactory, int timeout)
    throws IOException
  {
    throw new UnsupportedOperationException();
  }

  /**
   * No IP Support
   * @throws java.lang.UnsupportedOperationException
   */
  @Override
  public DatagramSocket openDatagramSocket(int port)
    throws IOException
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Get host's ord.
   */
  @Override
  public BOrd getAbsoluteOrd()
  {
    return BMockHostScheme.ORD;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final Map<String,BMockSession> sessionMap = new HashMap<>();
}
