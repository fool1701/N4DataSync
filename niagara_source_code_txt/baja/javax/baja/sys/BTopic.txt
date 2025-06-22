/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BTopic is the object type used to add
 * new topics to a BComponent.
 *
 * @author    Brian Frank
 * @creation  28 Sept 00
 * @version   $Revision: 9$ $Date: 3/28/05 9:23:12 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BTopic
  extends BStruct
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BTopic(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTopic.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the event type of the topic.
   */
  public abstract Type getEventType();

  /**
   * Get the facets for the action or return BFacets.NULL.
   * Default returns BFacets.NULL.
   */
  public BFacets getFacets()
  {
    return BFacets.NULL;
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("shapes/circleGold.png");
    
}
