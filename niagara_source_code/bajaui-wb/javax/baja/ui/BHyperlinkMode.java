/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;
import javax.baja.ui.event.*;

/**
 * BHyperlinkMode is used to determine how a hyperlink
 * should be performed in BIHyperlinkShell.
 *
 * @author    Brian Frank
 * @creation  27 Oct 03
 * @version   $Revision: 11$ $Date: 5/16/05 8:56:47 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("replace"),
    @Range("newTab"),
    @Range("newShell")
  }
)
public final class BHyperlinkMode
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BHyperlinkMode(2846230226)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for replace. */
  public static final int REPLACE = 0;
  /** Ordinal value for newTab. */
  public static final int NEW_TAB = 1;
  /** Ordinal value for newShell. */
  public static final int NEW_SHELL = 2;

  /** BHyperlinkMode constant for replace. */
  public static final BHyperlinkMode replace = new BHyperlinkMode(REPLACE);
  /** BHyperlinkMode constant for newTab. */
  public static final BHyperlinkMode newTab = new BHyperlinkMode(NEW_TAB);
  /** BHyperlinkMode constant for newShell. */
  public static final BHyperlinkMode newShell = new BHyperlinkMode(NEW_SHELL);

  /** Factory method with ordinal. */
  public static BHyperlinkMode make(int ordinal)
  {
    return (BHyperlinkMode)replace.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BHyperlinkMode make(String tag)
  {
    return (BHyperlinkMode)replace.getRange().get(tag);
  }

  /** Private constructor. */
  private BHyperlinkMode(int ordinal)
  {
    super(ordinal);
  }

  public static final BHyperlinkMode DEFAULT = replace;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHyperlinkMode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Given a mouse event (usually a double click), then the standard 
   * hyperlink mode.  If Ctrl is down, then we return <code>newTab</code>.  
   * If Ctrl+Shift is down then we return <code>newShell</code>.  
   * Otherwise we just return <code>replace</code>.
   */
  public static BHyperlinkMode make(BInputEvent event)
  {
    if (event != null && event.isControlDown())
    {                  
      if (event.isShiftDown())
        return newShell;
      else
        return newTab;
    }                 
    return replace;
  }

}
