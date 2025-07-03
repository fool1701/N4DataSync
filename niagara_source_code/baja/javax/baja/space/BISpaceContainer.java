/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.space;

import java.util.Iterator;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BISpaceContainer is the interface implemented by BObjects which can 
 * contain spaces.
 *
 * @author    Andrew Saunders       
 * @creation  23 Aug 2013
 * @since     Baja 4.0
 */
@NiagaraType
public interface BISpaceContainer
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.space.BISpaceContainer(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BISpaceContainer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// space Management
////////////////////////////////////////////////////////////////  
  
  /**
   * Add the specified BISpace to this container.
   *   
   */
  public BISpace mountSpace(BISpace space);
  
  /**
   * Remove the specified BISpace from this container.
   *   
   */
  public void unmountSpace(BISpace space);
  
  /**
   * Get a Iterator to all spaces in this space container.
   *
   * @return an Iterator to all spaces in this space container.
   */
  public Iterator<BISpace> getSpaces();
  
  
}
