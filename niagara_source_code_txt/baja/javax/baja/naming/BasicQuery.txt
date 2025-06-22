/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.nre.util.TextUtil;

/**
 * BasicQuery is a simple implementation of OrdQuery 
 * that stores the scheme and body as fields.
 *
 * @author    Brian Frank
 * @creation  3 Jan 03
 * @version   $Revision: 8$ $Date: 3/28/05 9:22:59 AM EST$
 * @since     Baja 1.0
 */
public class BasicQuery
  implements OrdQuery
{

  /**
   * Construct an BasicQuery with the specified scheme and body.
   */
  public BasicQuery(String scheme, String body)
  {
    this.scheme = TextUtil.toLowerCase(scheme).trim();
    this.body = body.trim();
  }
  
  /**
   * Return the scheme field.
   */
  @Override
  public String getScheme()
  {
    return scheme;
  }

  /**
   * Return the body field.
   */
  @Override
  public String getBody()
  {
    return body;
  }

  /**
   * Return false.
   */
  @Override
  public boolean isHost()
  { 
    return false; 
  }

  /**
   * Return false.
   */
  @Override
  public boolean isSession()
  { 
    return false; 
  }

  /**
   * Do nothing.
   */
  @Override
  public void normalize(OrdQueryList list, int index)
  {
  }
  
  /**
   * Return {@code scheme + ":" + body}.
   */  
  public String toString()
  {
    return scheme + ':' + body;
  }
  
  /** This is the scheme id of the query */  
  protected String scheme;
  
  /** This is the body of the query */  
  protected String body;
    
}

