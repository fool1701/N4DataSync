/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.space;

import javax.baja.naming.*;
import javax.baja.nav.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BISpaceNode
 *
 * @author Brian Frank
 * @version $Revision: 9$ $Date: 5/19/03 11:15:29 AM EDT$
 * @creation 21 Jan 03
 * @since Baja 1.0
 */
@NiagaraType
public interface BISpaceNode
  extends BINavNode
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.space.BISpaceNode(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BISpaceNode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Identity
////////////////////////////////////////////////////////////////

  /**
   * Get the host containing this node, or null if unmounted.
   */
  default public BHost getHost()
  {
    BSpace space = getSpace();
    if (space != null)
    {
      return space.getHost();
    }
    else
    {
      return null;
    }
  }

  /**
   * Get the session containing this node, or null if unmounted.
   */
  default public BISession getSession()
  {
    BSpace space = getSpace();
    if (space != null)
    {
      return space.getSession();
    }
    else
    {
      return null;
    }
  }

  /**
   * Get the space containing this node, or null if unmounted.
   */
  public BSpace getSpace();

  /**
   * A space node is mounted if it is parented by a mounted
   * space.  Mounted nodes have a host absolute ord and return
   * non-null for getSpace(), getSession(), and getHost().
   */
  default public boolean isMounted()
  {
    BSpace space = getSpace();
    if (space != null)
    {
      return space.isMounted();
    }
    else
    {
      return false;
    }
  }

  /**
   * Get an host absolute ord.  Return null if not
   * mounted.  This should be space.absoluteOrd + ordInSpace.
   */
  default public BOrd getAbsoluteOrd()
  {
    if (isMounted())
    {
      return BOrd.make(getSpace().getAbsoluteOrd(), getOrdInSpace());
    }
    else
    {
      return null;
    }
  }

  /**
   * Get an ord relative to the host.  Return null if not
   * mounted.  This should be space.ordInHost + ordInSpace.
   */
  default public BOrd getOrdInHost()
  {
    if (isMounted())
    {
      return BOrd.make(getSpace().getOrdInHost(), getOrdInSpace());
    }
    else
    {
      return null;
    }
  }

  /**
   * Get an ord relative to the session.  Return null if not
   * mounted.  This should be space.ordInSession + ordInSpace.
   */
  public BOrd getOrdInSession();

  /**
   * Get the ord which identifies this entry in its space.
   * This ord should be relative with the space as the base.
   * If not mounted then return null.
   */
  public BOrd getOrdInSpace();

////////////////////////////////////////////////////////////////
// Operations
////////////////////////////////////////////////////////////////  

  /**
   * If this object is currently contained by the current Mark
   * and the mark is set to pending move, then this method returns
   * true.  In user interfaces this flag should be used to render
   * the object grayed out to illustrate that a cut operation has
   * been performed on the object, but that a paste operation
   * is needed to complete the move.
   */
  public boolean isPendingMove();

  /**
   * Set the pending move flag.
   */
  public void setPendingMove(boolean pendingMove);
}
