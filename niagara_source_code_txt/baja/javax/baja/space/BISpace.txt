/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.space;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.LexiconText;

/**
 * Interface for Spaces.  A Space defines a group of BObjects which share common
 * strategies for loading, caching, lifecycle, and naming.  
 * Entries in a space implement the BISpaceNode
 * interface.  Spaces are typically mounted under BIAuthorities 
 * in the naming and nav hierarchies.
 *
 * @author    Andrew Saunders       
 * @creation  25 Aug 2013
 * @since     Baja 4.0
 */
@NiagaraType
public interface BISpace
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.space.BISpace(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BISpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// API
////////////////////////////////////////////////////////////////  

  /**
   * Get a LexiconText that provides the localized display name of this space.
   *
   * @return Return a LexiconText for the space display name.
   */
  public LexiconText getLexiconText();

  /**
   * Get the display name for this space.  The default implementation
   * resolves the LexiconText using the context.
   *
   * @param cx The context for localizing the display name.
   * @return Returns a localized display name for this space.
   */
  default public String getDisplayName(Context cx)
  {
    return getLexiconText().getText(cx);
  }

  /**
   * Return true if this space is mounted under an host.
   * Default implementation is <code>getHost() != null</code>.
   */
  default public boolean isMounted()
  {
    return getHost() != null;
  }

  /**
   * If this space is mounted, then return its parent 
   * host, otherwise return null.
   */
  public BHost getHost();

  /**
   * If this space is mounted, then return its parent 
   * BISpaceContainer, otherwise return null.
   */
  public BISpaceContainer getSpaceContainer();

  /**
   * Sets the parent space container.  
   * Note: This method should only be invoked when a space is mounted and unmounted its parent BISpaceContainer.  
   */
  public void setSpaceContainer(BISpaceContainer spaceParent);

  /**
   * If this space is mounted, then return its parent 
   * session, otherwise return null.
   */
  public BISession getSession();
  
  /**
   * Get an host absolute ord which identifies this
   * space.  If not mounted return null.
   */
  public BOrd getAbsoluteOrd();

  /**
   * Get the ord of this space relative to its host.
   */
  public BOrd getOrdInHost();
  
  /**
   * Get the ord of this space relative to its session.
   */
  public BOrd getOrdInSession();

  /**
   * Get the ord of this space relative to its space container.
   */
  public BOrd getOrdInSpaceContainer();


}
