/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.event;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;

/**
 * BWidgetEvent is the base class for all widget events.
 *
 * @author    Brian Frank
 * @creation  21 Nov 00
 * @version   $Revision: 5$ $Date: 1/13/03 11:49:21 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BWidgetEvent
  extends BStruct
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.event.BWidgetEvent(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWidgetEvent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MODIFIED         = 1;
  public static final int ACTION_PERFORMED = 2;

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a new widget event for the
   * specified source widget.
   */
  public BWidgetEvent(int id, BWidget source)
  {
    this.id = id;
    this.source = source;
  }

  /**
   * No arg constructor
   */
  public BWidgetEvent()
  {
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Get the event id.
   */
  public int getId()
  {
    return id;
  }

  /**
   * Get the source widget of this event.
   */
  public BWidget getWidget()
  {
    return source;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  private int id;
  private BWidget source;
  
}
