/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BIHyperlinkShell is implemented by BWidgetShell's 
 * which support the notion of hyperlinking. 
 *
 * @author    Brian Frank
 * @creation  19 May 04
 * @version   $Revision: 1$ $Date: 5/19/04 4:20:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIHyperlinkShell
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BIHyperlinkShell(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIHyperlinkShell.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Convenience for <code>hyperlink(new HyperlinkInfo(ord))</code>
   * which does a replace hyperlink to the specified ord.
   */
  public void hyperlink(BOrd ord);

  /**
   * Perform a hyperlink on this shell using the information
   * contained by the specified HyperlinkInfo including its
   * ord and mode.
   */
  public void hyperlink(HyperlinkInfo info);

  
}
