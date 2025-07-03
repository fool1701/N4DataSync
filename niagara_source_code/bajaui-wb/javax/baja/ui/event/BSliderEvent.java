/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.event;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;

/**
 * BSliderEvent indicates a change in slider positioning.
 *
 * @author    Andy Frank
 * @creation  16 May 01
 * @version   $Revision: 5$ $Date: 3/3/09 10:19:17 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BSliderEvent
  extends BWidgetEvent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.event.BSliderEvent(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSliderEvent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int VALUE_CHANGED = 602; //AdjustmentEvent.ADJUSTMENT_VALUE_CHANGED+1;

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a new focus event.
   */
  public BSliderEvent(int id, BWidget source, double value)
  {
    super(id, source);
    this.value = value;
  }

  /**
   * No arg constructor
   */
  public BSliderEvent()
  {
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the new slider value.
   */
  public double getValue()
  {
    return value;
  }

  public String toString(Context context)
  {
    return "BSliderEvent[VALUE_CHANGED val" + value + "]";
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private double value;
}
