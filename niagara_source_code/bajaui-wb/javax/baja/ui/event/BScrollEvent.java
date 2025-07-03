/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.event;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;

/**
 * BScrollEvent indicates a change in scroll bar positioning.
 *
 * @author    Brian Frank
 * @creation  27 Nov 00
 * @version   $Revision: 7$ $Date: 3/3/09 10:19:17 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BScrollEvent
  extends BWidgetEvent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.event.BScrollEvent(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BScrollEvent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int POSITION_CHANGED = 601; //AdjustmentEvent.ADJUSTMENT_VALUE_CHANGED;
  
  public static final int BLOCK_DECREMENT = 3;   //AdjustmentEvent.BLOCK_DECREMENT;
  public static final int BLOCK_INCREMENT = 4;   //AdjustmentEvent.BLOCK_INCREMENT;
  public static final int UNIT_INCREMENT  = 1;   //AdjustmentEvent.UNIT_INCREMENT;
  public static final int UNIT_DECREMENT  = 2;   //AdjustmentEvent.UNIT_DECREMENT;
  public static final int TRACK           = 5;   //AdjustmentEvent.TRACK;
  public static final int OTHER           = 602; //AdjustmentEvent.ADJUSTMENT_LAST + 1;

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a new focus event.
   */
  public BScrollEvent(int id, BWidget source, int position, int adjustmentType)
  {
    super(id, source);
    this.position = position;
    this.adjustmentType = adjustmentType;
  }

  /**
   * No arg constructor
   */
  public BScrollEvent()
  {
  }
  
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Get the new scrollbar position.
   */
  public int getPosition()
  {
    return position;
  }

  /**
   * Get the adjustment type constant which defines
   * how the scrollbar's position was changed.
   */
  public int getAdjustmentType()
  {
    return adjustmentType;
  }
  
  public String toString(Context context)
  {
    return "BScrollEvent[POSITION_CHANGED pos" + position + 
           " type=" + adjustmentType + "]";
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private int position;
  private int adjustmentType;
  
}
