/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.space;

import javax.baja.agent.*;
import javax.baja.naming.*;
import javax.baja.nav.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.*;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BSpace defines a group of BObjects which share common
 * strategies for loading, caching, lifecycle, naming, and
 * navigation.  Entries in a space implement the BISpaceNode
 * interface.  Spaces are typically mounted under BIAuthorities 
 * in the naming and nav hierarchies.
 *
 * @author    Brian Frank       
 * @creation  21 Jan 03
 * @version   $Revision: 9$ $Date: 3/28/05 9:23:04 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BSpace
  extends BNavContainer
  implements BIAgent, BISpace
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.space.BSpace(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Constructor.
   */
  protected BSpace(String name, LexiconText lexText)
  {
    super(name, lexText);
  }

  /**
   * Constructor.
   */
  protected BSpace(String name)
  {
    super(name);
  }

////////////////////////////////////////////////////////////////
// Identity
////////////////////////////////////////////////////////////////  

  public String getSpaceId()
  {
    return getType().getTypeInfo().toString();
  }
  /**
   * Return true if this space is mounted under an host.
   * Default implementation is <code>getHost() != null</code>.
   */
  @Override
  public boolean isMounted()
  {
    return getHost() != null;
  }

  /**
   * If this space is mounted, then return its parent 
   * container, otherwise return null.
   */
  @Override
  public BISpaceContainer getSpaceContainer()
  {
    return spaceParent;
  }

  /**
   * Sets the parent space container.
   */
  @Override
  public void setSpaceContainer(BISpaceContainer spaceParent)
  {
    this.spaceParent = spaceParent;
  }

  /**
   * If this space is mounted, then return its parent 
   * host, otherwise return null.
   */
  @Override
  public BHost getHost()
  {    
    Object p = getNavParent();
    if (p instanceof BHost) return (BHost)p;
    if (p instanceof BISession) return ((BISession)p).getHost();
    return null;
  }

  /**
   * If this space is mounted, then return its parent 
   * session, otherwise return null.
   */
  @Override
  public BISession getSession()
  {    
    Object p = getNavParent();
    if (p instanceof BISession) return (BISession)p;
    return null;
  }
  
  /**
   * Get an host absolute ord which identifies this
   * space.  If not mounted return null.
   */
  @Override
  public BOrd getAbsoluteOrd()
  {
    BHost host = getHost();
    if (host == null) return null;
    return BOrd.make(host.getAbsoluteOrd(), getOrdInHost());
  }

  /**
   * Get the ord of this space relative to its host.
   */
  @Override
  public BOrd getOrdInHost()
  {
    BISession session = getSession();
    if (session == null) return null;
    return BOrd.make(session.getOrdInHost(), getOrdInSession());
  }
  
  /**
   * Get the ord of this space relative to its session.
   */
  @Override
  public abstract BOrd getOrdInSession();

  /**
   * Get the ord of this space relative to its space container.
   *   Default implementation is to return getOrdInHost().
   */
  @Override
  public BOrd getOrdInSpaceContainer()
  {
    return getOrdInHost();
  }

////////////////////////////////////////////////////////////////
// INavNode
////////////////////////////////////////////////////////////////  

  /**
   * The default implementation is to return getAbsoluteOrd().
   */
  @Override
  public BOrd getNavOrd()
  {
    return getAbsoluteOrd();
  }
  
////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////  

  /**
   * Dump spy info.
   */
  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    out.startProps();
    out.trTitle("Space", 2);
    out.prop("isMounted", isMounted());
    out.prop("host", getHost());
    out.prop("session", getSession());
    out.prop("absoluteOrd", getAbsoluteOrd());
    out.prop("ordInHost", getOrdInHost());
    out.prop("ordInSession", getOrdInSession());
    out.prop("navOrd", getNavOrd());
    out.endProps();
    super.spy(out);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  BISpaceContainer spaceParent = null;
  
}
