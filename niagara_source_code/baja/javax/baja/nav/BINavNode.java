/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nav;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BINavNode is the interface implemented by BObjects which are 
 * mounted into the navigation tree under BNavRoot.INSTANCE.
 * Each nav node in the tree is uniquely identified under its
 * parent via a nav name, and uniquely idenfified in the entire
 * tree via the NavPath.
 *
 * @author    Brian Frank       
 * @creation  22 Jan 03
 * @version   $Revision: 6$ $Date: 3/1/05 10:24:39 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BINavNode
  extends BInterface
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.nav.BINavNode(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BINavNode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the name which uniquely identifies this node in its 
   * parent.  This name must be a valid slot name.
   */
  String getNavName();
  
  /**
   * Get the display text of the navigation node.
   */
  String getNavDisplayName(Context cx);

  /**
   * Get a short description of the nav node.  Return 
   * null if no description is available.
   */
  String getNavDescription(Context cx);

  /**
   * Get the parent navigation node.
   */
  BINavNode getNavParent();
    
  /**
   * If this node knows that it has no children, then
   * return false.  If the node has children or will
   * find out during a call to getNavChildren(), then 
   * return true.
   */
  boolean hasNavChildren();
  
  /**
   * Get the child by the specified name, or 
   * return null if not found.
   */
  BINavNode getNavChild(String navName);

  /**
   * Get the child by the specified name, or throw
   * UnresolvedException if not found.
   */
  BINavNode resolveNavChild(String navName);
  
  /**
   * Get the children nodes for this navigation node.  Return 
   * an array of length zero if there are no children.  Note 
   * that this method does not take a Context, so it is possible 
   * that the resulting list doesn't take security permissions 
   * into account.  Use <code>BNavContainer.filter()</code> to
   * filter out nodes based on permissions; this is typically
   * done when using this method to do server side processing
   * such as a web servlet.
   */
  BINavNode[] getNavChildren();
  
  /**
   * Get the primary ord used to navigate to a view on 
   * this object.  This should be an normalized absolute 
   * ord.
   */
  BOrd getNavOrd();

  /**
   * Get the icon for the navigation node.
   */
  BIcon getNavIcon();

  /**
   * @since Niagara 4.0
   */
  default Iterator<BINavNode> iterateNavDescendants()
  {
    if (hasNavChildren())
    {
      return new NavDescendantsIterator(this);
    }
    else
    {
      return Collections.emptyIterator();
    }
  }
  
  public static class NavDescendantsIterator
    implements Iterator<BINavNode>
  {
    public NavDescendantsIterator(BINavNode root)
    {
      if (root.hasNavChildren())
      {
        navChildren = root.getNavChildren();
      }
      else
      {
        navChildren = null;
      }
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext()
    {
      if (navChildren == null)
      {
        return false;
      }
      else
      {
        if (childIndex < navChildren.length)
        {
          return true;
        }
        while ((childIterator != null) && !childIterator.hasNext())
        {
          if (iteratorIndex < navChildren.length)
          {
            childIterator = navChildren[iteratorIndex].iterateNavDescendants();
            iteratorIndex++;
          }
          else
          {
            childIterator = null;
          }
        }
        return childIterator != null;
      }
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public BINavNode next()
      throws NoSuchElementException
    {
      if (navChildren == null)
      {
        throw new NoSuchElementException();
      }
      else if (childIndex < navChildren.length)
      {
        childIndex++;
        return navChildren[childIndex - 1];
      }
      else if (childIterator == null)
      {
        throw new NoSuchElementException();
      }
      else
      {
        return childIterator.next();
      }
    }

    private final BINavNode[] navChildren;
    private int childIndex = 0;
    private int iteratorIndex = 0;
    private Iterator<BINavNode> childIterator = Collections.emptyIterator();
  }
}
