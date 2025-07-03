/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.sql;

import javax.baja.naming.*;

/**
 * SqlQuery
 *
 * @author    Mike Jarmy
 * @creation  28 Jul 2003
 * @version   $Revision: 1$ $Date: 7/29/03 8:49:19 AM EDT$
 * @since     Baja 1.0
 */
public class SqlQuery implements OrdQuery
{
  public SqlQuery(String body) 
  { 
    this.sql = body;
  } 
  
  public boolean isHost() { return false; }

  public boolean isSession() { return false; }

  public void normalize(OrdQueryList list, int index) {}

  public String getScheme() { return "sql"; }
  
  public String getBody() { return sql; }
  
  public String toString() { return "sql:" + getBody(); }
  
  String sql;
}

