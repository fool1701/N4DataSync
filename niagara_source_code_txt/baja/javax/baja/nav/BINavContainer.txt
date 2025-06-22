/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nav;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BINavContainer is a interface that allows mounting,
 * unmounting, and reordering of other arbitrary BINavNodes.
 *
 * @author    Andrew Saunders       
 * @creation  25 Aug 2013
 * @since     Baja 4.0
 */
@NiagaraType
public interface BINavContainer
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.nav.BINavContainer(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BINavContainer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Child Management
////////////////////////////////////////////////////////////////  
  
  /**
   * Add the specified BINavNode to this container.
   */
  public void addNavChild(BINavNode child);

  /**
   * Remove the specified BINavNode from this container.
   */
  public void removeNavChild(BINavNode child);
  
  /**
   * Reorder the nav children.  The list of children passed must
   * be instances returned from <code>getNavChildren</code>.
   */
  public void reorderNavChildren(BINavNode[] children);
  
  
}
