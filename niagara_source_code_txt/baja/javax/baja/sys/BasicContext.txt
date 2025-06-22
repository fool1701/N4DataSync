/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.user.*;

/**
 * BasicContext is a simple implementation of the 
 * Context interface.
 *
 * @author    Brian Frank on 14 Feb 01
 * @version   $Revision: 14$ $Date: 3/28/05 9:23:09 AM EST$
 * @since     Baja 1.0
 */
public class BasicContext
  implements Context
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Create a basic context with a the specified user and facets.
   */
  public BasicContext(BUser user, BFacets facets)
  {
    this.user   = user;
    if (user == null)
    {
      if (facets == null)
      {
        this.facets = BFacets.NULL;
      }
      else
      {
        this.facets = facets;
      }
    }
    else if (facets == null)
    {
      this.facets = user.getFacets();
    }
    else
    {
      this.facets = BFacets.make(user.getFacets(), facets);
    }
    invariant();
  }

  /**
   * Create a basic context with specified user and BFacets.NULL. 
   */
  public BasicContext(BUser user)
  {      
    this.user   = user; 
    this.facets = (user != null) ? user.getFacets() : BFacets.NULL;
    invariant();
  }

  /**
   * Create a basic context with specified user lang, and BFacets.NULL. 
   */
  public BasicContext(BUser user, String lang)
  {      
    this.user   = user;
    this.facets = (user != null) ? user.getFacets() : BFacets.NULL;
    this.lang   = lang;
    invariant();
  }

  /**
   * Create a basic context using information from another context.
   */
  public BasicContext(Context base)
  {
    if (base != null)
    {                             
      this.base   = base;
      this.user   = base.getUser();
      this.facets = base.getFacets();
      this.lang   = base.getLanguage();
    }
    invariant();
  }

  /**
   * Create a basic context using information from another context.
   */
  public BasicContext(Context base, BFacets additionalFacets)
  {
    if (base != null)
    {      
      this.base   = base;
      this.user   = base.getUser();
      this.facets = base.getFacets();
      this.lang   = base.getLanguage();
    }   
    facets = BFacets.make(facets, additionalFacets);
    invariant();
  }

  /**
   * Create a basic context with a null user and BFacets.NULL. 
   */
  public BasicContext()
  {    
    invariant();
  }
  
  /**
   * Check the invariants and make sure this 
   * BasicContext is ok to use.
   */
  private void invariant()
  {
    // insure facets
    if (facets == null) 
      facets = BFacets.NULL;
      
    // insure language  
    if (lang == null) 
    {
      if (user != null && !user.getLanguage().equals(""))
        this.lang = user.getLanguage();
      else
        this.lang = Sys.getLanguage();
    }
  }

////////////////////////////////////////////////////////////////
// Context
////////////////////////////////////////////////////////////////

  /**
   * Get the user for the context of this operation, 
   * or null if no user information is available.
   */
  @Override
  public BUser getUser()
  {
    return user;
  }

  /**
   * Get the facets. This should never be null.
   */
  @Override
  public BFacets getFacets()
  {
    return facets;
  }
  
  /**
   * Get a facet value by name.  The default 
   * implementation routes to the facets instance.
   */
  @Override
  public BObject getFacet(String name)
  {
    return facets.getFacet(name);
  }

  /**
   * Default implementation is to return language 
   * property from user or Sys.language() if there 
   * is no user.
   */
  @Override
  public String getLanguage()
  {
    return lang;
  }              

  /**
   * If this Context wraps another Context, then return 
   * the base Context.  Otherwise return null.
   */
  @Override
  public Context getBase()
  {
    return base;
  }
  
  /**
   * To string.
   */
  public String toString()
  {
    return "BasicContext[facets=" + facets + " lang=" + lang + " user=" + user + "]";
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  protected Context base;
  protected BUser user;
  protected BFacets facets;
  protected String lang;
  
}
