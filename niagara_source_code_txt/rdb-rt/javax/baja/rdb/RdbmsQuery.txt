/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb;

import javax.baja.naming.*;

/**
 * RdbmsQuery
 *
 * @author    Mike Jarmy
 * @creation  24 Jul 03
 * @version   $Revision: 1$ $Date: 7/29/03 8:49:18 AM EDT$
 * @since     Baja 1.0
 */
public abstract class RdbmsQuery implements OrdQuery
{
  public RdbmsQuery(String scheme) 
  { 
    this.scheme = scheme;
  } 
  
  public boolean isHost() { return false; }
  public boolean isSession() { return true; }
  public void normalize(OrdQueryList list, int index) { list.shiftToHost(index); }
  public String getScheme() { return scheme; }  
  public String toString() { return scheme + ":" + getBody(); }
  
  public abstract RdbmsQuery duplicate();
  public abstract String getNavName();

  protected String scheme;
}

