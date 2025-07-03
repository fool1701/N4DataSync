/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BNullWidget is a place holder widget to use as a null property
 * value.
 *
 * @author    John Sublett
 * @creation  11 Dec 2000
 * @version   $Revision: 14$ $Date: 4/2/04 1:46:59 PM EST$
 * @since     Niagara 1.0
 */
@NiagaraType
public class BNullWidget
  extends BWidget
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BNullWidget(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNullWidget.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * The null widget is null!
   */
  public boolean isNull() { return true; }

  /**
   * The preferred size of a null widget is
   * always 0, 0.
   */
  public void computePreferredSize()
  {
    setPreferredSize(0, 0);
  }

  /**
   * Paint the null widget.  For development
   * purposes it paints an "X".
   */
  public void paint(Graphics g)
  {
    /*
    int w = getWidth(), h = getHeight();
    g.setBrush(Color.black);
    g.strokeLine(0, 0, w-1, h-1);
    g.strokeLine(0, h-1, w-1, 0);
    g.strokeRect(0, 0, w-1, h-1);
    */
  }

///////////////////////////////////////////////////////////
// Overrides
///////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/nullWidget.png");

}
