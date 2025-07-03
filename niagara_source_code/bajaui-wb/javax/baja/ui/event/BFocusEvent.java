/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.event;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;

/**
 * BFocusEvent indicates focus lost or gained.
 *
 * @author    Brian Frank on 21 Nov 00
 * @version   $Revision: 6$ $Date: 3/3/09 10:19:16 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BFocusEvent
  extends BWidgetEvent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.event.BFocusEvent(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFocusEvent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Ids
////////////////////////////////////////////////////////////////

  public static final int FOCUS_GAINED = 1004; //FocusEvent.FOCUS_GAINED
  public static final int FOCUS_LOST   = 1005; //FocusEvent.FOCUS_LOST

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a new focus event.
   */
  public BFocusEvent(int id, BWidget source, boolean temporary)
  {
    super(id, source);
    this.temporary = temporary;
  }

  /**
   * No arg constructor
   */
  public BFocusEvent()
  {
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Returns whether this focus change is temporary
   * or permanent.  A temporary focus change is caused
   * by the parent window losing focus, but when it
   * regains focus then the widget will regain the focus.
   * A permanent change is caused when another widget in
   * the same window gains focus.
   */
  public boolean isTemporary()
  {
    return temporary;
  }
  
  /**
   * Get a string representation.
   */
  public String toString(Context cx)
  {
    String id = "?";
    switch(getId())
    {
      case FOCUS_GAINED: id = "FocusGained";  break;
      case FOCUS_LOST:   id = "FocusLost";    break;
    }       
    
    return id + 
           " src=" + (getWidget() != null ? getWidget().getType() : "?");
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private boolean temporary;
  
}
