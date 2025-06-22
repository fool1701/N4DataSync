/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.pane;

import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;
import javax.baja.ui.UndoManager;

import com.tridium.ui.theme.Theme;

/**
 * BPane is the base class for containers of BWidgets.
 *
 * @author    Brian Frank
 * @creation  19 Nov 00
 * @version   $Revision: 3$ $Date: 6/9/11 3:05:48 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BPane
  extends BWidget
  implements UndoManager.Scope  
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BPane(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:35 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  public String getStyleSelector() {
    return "pane";
  }
  
  public void paint(Graphics g) {
    Theme.pane().paintBackground(g, this);
    super.paint(g);
  }


  
////////////////////////////////////////////////////////////////
// Undo
////////////////////////////////////////////////////////////////
  
  /**
   * Get the UndoManager for this shell.
   */
  public UndoManager getInstalledUndoManager()
  {
    return undoManager;
  }
  
  /**
   * Set the UndoManager for this shell.
   */
  public void setInstalledUndoManager(UndoManager undoManager)
  {
    this.undoManager = undoManager;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private UndoManager undoManager = null;
}
