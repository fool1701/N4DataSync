/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.enums.*;

import com.tridium.ui.theme.*;

/**
 * BSeparator is used in BToolBars and BMenu's to 
 * provide a visible separator between groups of
 * buttons.
 *
 * @author    Brian Frank       
 * @creation  4 Jan 01
 * @version   $Revision: 18$ $Date: 7/1/11 5:32:16 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Orientation defines whether the separator is
 horizontal or vertical.
 */
@NiagaraProperty(
  name = "orientation",
  type = "BOrientation",
  defaultValue = "BOrientation.horizontal"
)
public class BSeparator
  extends BWidget
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BSeparator(381090303)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "orientation"

  /**
   * Slot for the {@code orientation} property.
   * Orientation defines whether the separator is
   * horizontal or vertical.
   * @see #getOrientation
   * @see #setOrientation
   */
  public static final Property orientation = newProperty(0, BOrientation.horizontal, null);

  /**
   * Get the {@code orientation} property.
   * Orientation defines whether the separator is
   * horizontal or vertical.
   * @see #orientation
   */
  public BOrientation getOrientation() { return (BOrientation)get(orientation); }

  /**
   * Set the {@code orientation} property.
   * Orientation defines whether the separator is
   * horizontal or vertical.
   * @see #orientation
   */
  public void setOrientation(BOrientation v) { set(orientation, v, null); }

  //endregion Property "orientation"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSeparator.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Separator constructor with orientation.
   */
  public BSeparator(BOrientation orientation)
  {
    setOrientation(orientation);
  }

  /**
   * No arg constructor.
   */
  public BSeparator()
  {
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Separators do not receive input events.  
   */
  public boolean receiveInputEvents()
  {
    return false;
  }

  /**
   * Compute the menu bar's preferred size.
   */
  public void computePreferredSize()
  {
    double w = Theme.separator().getFixedWidth(this);
    setPreferredSize(w, w);
  }
  
  /**
   * Paint the menu bar.
   */ 
  public void paint(Graphics g)
  {
    Theme.separator().paintSeparator(g, this);
  }
  
  public String getStyleSelector() { return "separator"; }
  
///////////////////////////////////////////////////////////
// Overrides
/////////////////////////////////////////////////////////// 

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/separator.png");
  
}
