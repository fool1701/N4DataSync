/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.sql;

import java.io.StringReader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.SQLException;

import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdScheme;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.SyntaxException;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.rdb.BRdbms;
import javax.baja.rdb.BRdbmsSession;
import javax.baja.rdb.RdbmsContext;
import javax.baja.security.BPermissions;
import javax.baja.security.PermissionException;
import javax.baja.sys.BObject;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.fox.sys.BFoxChannelRegistry;
import com.tridium.fox.sys.BFoxSession;
import com.tridium.rdb.BResultSetTable;
import com.tridium.rdb.fox.BSqlSchemeChannel;
import com.tridium.rdb.sql.SqlParser;
import com.tridium.rdb.util.PasswordUtils;

/**
 * BSqlScheme
 *
 * @author Mike Jarmy
 * @version $Revision: 8$ $Date: 4/1/09 9:55:53 AM EDT$
 * @creation 28 Jul 2003
 * @since Baja 1.0
 */
@NiagaraType(
  ordScheme = "sql"
)
@NiagaraSingleton
public class BSqlScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.sql.BSqlScheme(2864185796)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BSqlScheme INSTANCE = new BSqlScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSqlScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BSqlScheme()
  {
    super("sql");
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  /**
   * @return an instance of SqlQuery.
   */
  public OrdQuery parse(String queryBody)
  {
    return new SqlQuery(queryBody);
  }

  /**
   * @return an OrdTarget that will resolve to a BITable.
   */
  public OrdTarget resolve(OrdTarget base, OrdQuery ordQuery)
    throws SyntaxException, UnresolvedException
  {
    Connection connection = null;

    try
    {
      if (!(base.getComponent().getSession() instanceof BFoxSession))
      {
        ConnectionAndContext cc = obtainConnectionAndContext(base);
        connection = cc.connection;

        RdbmsContext cx = cc.context;
        String sql = ordQuery.getBody();

        if (NIAGARA_SQL_PARSER_DISABLED)
        {
          return new OrdTarget(base, new BResultSetTable<>(connection, sql, cx));
        }

        SqlParser parser = new SqlParser(new StringReader(sql), /*debug*/ true);

        if (parser.parse() != 0)
          throw new SQLException(makeErrorMessage(parser));

        String parametricQuery = parser.getQuery();
        Object[] params = parser.getParameters();

        return new OrdTarget(base, new BResultSetTable<>(connection, parametricQuery, params, cx));
      }
      else
      {
        BFoxChannelRegistry channels = ((BFoxSession)base.getComponent().getSession()).getConnection().getChannels();
        BSqlSchemeChannel channel = (BSqlSchemeChannel)channels.get(BSqlSchemeChannel.CHANNEL_NAME, BSqlSchemeChannel.TYPE);
        return channel.resolve(base);
      }
    }
    catch (Exception e)
    {
      //NCCB-14359
      String msg = !PasswordUtils.isPasswordChangeAllowed(BOrd.toSession(base.get())) ?
        "Access denied: SQL scheme usage is restricted to sessions connected via FOXS" :
        "Could not resolve " + base + ", " + ordQuery;
      Throwable cause = e.getCause();
      if(cause != null)
      {
        // Throw a more detailed message if possible
        String msg2 = cause.getLocalizedMessage();
        throw new UnresolvedException(msg + ". " + msg2, e);
      }
      else if (!(base.getComponent().getSession() instanceof BFoxSession))
      {
        throw new UnresolvedException(msg, e);
      }
      else
      {
        // Throw the error using the message from Fox
        throw new UnresolvedException(e.getLocalizedMessage(), e);
      }
    }
    finally
    {
      if (connection != null)
      {
        try
        {
          connection.close();
        }
        catch (Exception e)
        {
          throw new BajaRuntimeException(e);
        }
      }
    }
  }

  /**
   * Resolve directly and consume the result set one row at a time via a consumer object.
   *
   * @since Niagara 4.4
   *
   * @param consumer applied for each row in the result set.
   * @param base database ord
   * @param ordQuery query ord
   */
  public void resolve(SqlSchemeConsumer consumer, OrdTarget base, OrdQuery ordQuery)
  {
    Connection connection = null;
    try
    {
      ConnectionAndContext cc = obtainConnectionAndContext(base);
      connection = cc.connection;
      RdbmsContext cx = cc.context;

      String sql = (ordQuery).getBody();

      if (NIAGARA_SQL_PARSER_DISABLED)
      {
        BResultSetTable.load(consumer, connection, sql, null, cx);
      }
      else
      {
        SqlParser parser = new SqlParser(new StringReader(sql), /*debug*/ true);

        if (parser.parse() != 0)
          throw new SQLException(makeErrorMessage(parser));

        String parametricQuery = parser.getQuery();
        Object[] params = parser.getParameters();

        BResultSetTable.load(consumer, connection, parametricQuery, params, cx);
      }
    }
    catch (Exception e)
    {
      Throwable cause = e.getCause();
      String msg = "Could not resolve " + base + ", " + ordQuery + ".  " + e.getLocalizedMessage();
      if (cause != null)
      {
        msg = msg + e.getCause().getLocalizedMessage();
      }
      throw new UnresolvedException(msg, e);
    }
    finally
    {
      if(connection != null)
      {
        try
        {
          connection.close();
        }
        catch(Exception e)
        {
          throw new BajaRuntimeException(e);
        }
      }
    }
  }

  private static String makeErrorMessage(SqlParser parser)
  {
    return "Syntax error near: \"" + parser.getQuery().trim() + "\"\nUnexpected token \" " + parser.text().trim() + "\"" +
      " at line: " + parser.line() + ", column: " + parser.column();
  }

  /**
   * obtainConnection
   */
  private ConnectionAndContext obtainConnectionAndContext(OrdTarget base)
    throws Exception
  {
    BObject baseObject = base.get();
    BRdbms db;
    
    if(baseObject instanceof BRdbms)
    {
      db = (BRdbms)baseObject;
    }
    else
    {
      BRdbmsSession session = (BRdbmsSession)BOrd.toSession(baseObject);
      session.connect();
      db = session.getDatabase();
    }

    db.lease();
    
    // sql scheme must be enabled
    if (!db.getSqlSchemeEnabled())
      throw new BajaRuntimeException(
        "The 'sql' scheme is not enabled.");

    // must have adminWrite and adminInvoke
    BPermissions permissions = db.getPermissions(base);
    if (!permissions.hasAdminWrite() || !permissions.hasAdminInvoke())
      throw new PermissionException();

    return new ConnectionAndContext(db.getConnection(db.getUserName(), db.getPassword()), db.getRdbmsContext());
  }

  /**
   * return both the Connection and a context
   */
  private static class ConnectionAndContext
  {
    ConnectionAndContext(Connection connection, RdbmsContext context)
    {
      this.connection = connection;
      this.context = context;
    }

    Connection connection;
    RdbmsContext context;
  }

  private static final boolean NIAGARA_SQL_PARSER_DISABLED = AccessController.doPrivileged(
    (PrivilegedAction<Boolean>)() -> Boolean.parseBoolean(System.getProperty("niagara.sql.parser.disable", "false")));
}
