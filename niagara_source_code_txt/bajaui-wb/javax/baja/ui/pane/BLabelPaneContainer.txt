/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.pane;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;

/**
 * BLabelPaneContainer is the base class for special layouts 
 * using BLabelPane.  Unlike other panes, a BLabelPaneContainer 
 * is responsible for laying out its grandchildren, not its 
 * children.
 *
 * @author    Brian Frank
 * @creation  5 Dec 00
 * @version   $Revision: 14$ $Date: 3/28/05 10:32:28 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BLabelPaneContainer
  extends BPane
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BLabelPaneContainer(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:35 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLabelPaneContainer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  /**
   * Add the specified label pane.
   */
  public void addPane(BLabelPane pane)
  {
    add(null, pane, null);
  }

  /**
   * Add the specified widget with its label.
   */
  public void addPane(BLabel label, BWidget content)
  {
    addPane(new BLabelPane(label, content));
  }

  /**
   * Add the specified widget with its label text.
   */
  public void addPane(String label, BWidget content)
  {
    addPane(new BLabelPane(label, content));
  }

  /**
   * Add the specified widget with its label text and icon.
   */
  public void addPane(String label, BImage icon, BWidget content)
  {
    addPane(new BLabelPane(label, icon, content));
  }

  /**
   * Removes the label pane with the specified content.
   */
  public void removePane(BWidget content)
  {  
    remove(content.getParent().getPropertyInParent());
  }

  /**
   * Only BLabelPanes are valid children.
   */
  @Override
  public boolean isChildLegal(BComponent child)
  {
    if (!super.isChildLegal(child))
    {
      return false;
    }
    return child instanceof BLabelPane || child instanceof BBinding;
  }
  
  @Override
  public void doLayout(BWidget[] kids) {}
}
