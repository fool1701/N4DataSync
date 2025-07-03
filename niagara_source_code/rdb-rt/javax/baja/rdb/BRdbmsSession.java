/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb;

import java.sql.*;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.*;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BRdbmsSession is a session for a BRdbms
 *
 * @author    Mike Jarmy
 * @creation  24 Jul 03
 * @version   $Revision: 6$ $Date: 5/16/05 3:34:34 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public final class BRdbmsSession
  extends BSession
  implements AuthenticationRealm
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.BRdbmsSession(2979906276)1.0$ @*/
/* Generated Sat Jan 29 17:54:41 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRdbmsSession.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////  

  public static BRdbmsSession make(BHost host, RdbmsQuery query, BRdbmsScheme scheme)
  {
    String navName = query.getNavName();
        
    // if not cached in host, then we need to create
    BRdbmsSession session = (BRdbmsSession)host.getNavChild(navName);
    if (session == null)
    {
      session = new BRdbmsSession(host, query, scheme);      
      host.addNavChild(session);
    }    
    
    // return
    return session;
  }
  
  private BRdbmsSession(BHost host, RdbmsQuery query, BRdbmsScheme scheme)
  {
    super(query.getNavName());
    
    this.host = host;
    this.query = query;
    this.scheme = scheme;
    
    this.ordInHost = BOrd.make(query.duplicate());
    this.absOrd = BOrd.make(host.getAbsoluteOrd(), ordInHost);

    this.database = scheme.newDatabase(host, query);
    this.connection = null;
    this.credentials = null;
  }

////////////////////////////////////////////////////////////////
// BISession Connection State
////////////////////////////////////////////////////////////////  

  /**
   * Return if space is currently connected.
   */
  public boolean isConnected()
  {
    return (connection != null);
  }
  
  /**
   * Connect to remote station using current credentials.
   */
  public void connect()
    throws Exception
  {
    if (isConnected()) return;

    try
    {
      connection = database.getConnection(
        (credentials == null) ? "" : credentials.getUsername(),
        (credentials == null) ? BPassword.DEFAULT : credentials.getPassword());
      database.setStatus(BStatus.makeDown(database.getStatus(), false));
    }
    catch(SQLException e)
    {
      database.setStatus(BStatus.makeDown(database.getStatus(), true));
      throw new AuthenticationException(this, e);
    }
  }

  /**
   * Disconnect from remote system.
   */
  public void disconnect()
  {
    if (connection == null) return;
    try
    {
      if (!connection.isClosed()) connection.close();      
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    connection = null;
  }
    
  /**
   * Disconnect and remove the BRdbmsSession from the navigation tree.
   */
  public void close()
  {
    disconnect();
    host.removeNavChild(this);
  }

////////////////////////////////////////////////////////////////
// BISession Naming
////////////////////////////////////////////////////////////////  

  /**
   * Get the host for this session.
   */
  public BHost getHost()
  {
    return host;
  }

  public BOrd getAbsoluteOrd()
  {
    return absOrd;
  }

  public BOrd getOrdInHost()
  {
    return ordInHost;
  }

////////////////////////////////////////////////////////////////
// AuthenticationRealm Implementation
////////////////////////////////////////////////////////////////

  /**
   * Get a description of the space itself.  The value
   * of this method should be a universal identifier for
   * the space which makes sense to a user and may be
   * used as key for caching credentials.
   */
  public String getAuthenticationRealmName()
  {
    return absOrd.toString();
  }

  /**
   * 
   */
  public String getAuthenticationScheme()
  {
    return scheme.getAuthName();
  }

  /**
   * Make a default instance of the proper BICredentials
   * implementation to use for prompting the user.
   */
  public BICredentials makeCredentials()
  {
    return new BUsernameAndPassword();
  }

  /**
   * Get the current credentials being used to log 
   * into the authentication space.
   */
  public BICredentials getCredentials()
  {
    return credentials;
  }
 
  /**
   * Set the current credentials to use for the next
   * authentication attempt.
   */
  public void setCredentials(BICredentials credentials)
  {
    this.credentials = (BUsernameAndPassword)credentials;
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////  

  /**
   * Display name is the oracle address.
   */
  public String getNavDisplayName(Context cx)
  {
    String string = scheme.getAuthName() + ":" + getHost().toString() + ":" + query.getBody();
      
    if (isConnected()) return string + " [Connected]";
    else return string + " [Disconnected]";
  }

  /**
   * Return absolute ord.
   */
  public BOrd getNavOrd()
  {
    return absOrd;
  }

////////////////////////////////////////////////////////////////
// getters
////////////////////////////////////////////////////////////////

  public String getHostName() { return host.toString(); }
  public RdbmsQuery getQuery() { return query; }
  public BRdbmsScheme getScheme() { return scheme; }

  public BRdbms getDatabase() { return database; }
  public Connection getConnection() { return connection; }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  public BIcon getIcon() { return isConnected() ? iconConnected : iconDisconnected; }
  private static final BIcon iconConnected = BIcon.std("database.png");
  private static final BIcon iconDisconnected = BIcon.std("databaseDisconnected.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private BHost host;
  private RdbmsQuery query;
  private BRdbmsScheme scheme;

  private BOrd ordInHost;
  private BOrd absOrd;

  private BRdbms database;
  private Connection connection;
  private BUsernameAndPassword credentials;
}
