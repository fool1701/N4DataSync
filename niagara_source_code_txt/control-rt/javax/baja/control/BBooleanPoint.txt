/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIBoolean;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BBooleanPoint defines a read only boolean control point.
 *
 * @author    Dan Giorgis
 * @creation  15 Nov 00
 * @version   $Revision: 35$ $Date: 3/28/05 11:40:31 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.makeBoolean()",
  override = true
)
/*
 The out slot of a BBooleanPoint is a
 BStatusBoolean
 */
@NiagaraProperty(
  name = "out",
  type = "BStatusBoolean",
  defaultValue = "new BStatusBoolean()",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY
)
public class BBooleanPoint
  extends BDiscretePoint
  implements BIBoolean
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.BBooleanPoint(352312234)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.makeBoolean(), null);

  //endregion Property "facets"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * The out slot of a BBooleanPoint is a
   * BStatusBoolean
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, new BStatusBoolean(), null);

  /**
   * Get the {@code out} property.
   * The out slot of a BBooleanPoint is a
   * BStatusBoolean
   * @see #out
   */
  public BStatusBoolean getOut() { return (BStatusBoolean)get(out); }

  /**
   * Set the {@code out} property.
   * The out slot of a BBooleanPoint is a
   * BStatusBoolean
   * @see #out
   */
  public void setOut(BStatusBoolean v) { set(out, v, null); }

  //endregion Property "out"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBooleanPoint.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Interfaces
////////////////////////////////////////////////////////////////

  /**
   * Return the vaule as a boolean.
   */
  public final boolean getBoolean() { return getOut().getValue(); }

  /**
   * Return getFacets().
   */
  public final BFacets getBooleanFacets() { return getFacets(); }

  /**
   * Return the vaule as a enum.
   */
  public final BEnum getEnum() { return getOut().getEnum(); }

  /**
   * Return getFacets().
   */
  public final BFacets getEnumFacets() { return getFacets(); }

  /**
   * Get the control output element.
   */
  public final BStatusValue getOutStatusValue() { return getOut(); }
  
  /**
   * Return the boolean value of out.
   */
  public final boolean isActive() { return getBoolean(); }

////////////////////////////////////////////////////////////////
// ControlPoint
////////////////////////////////////////////////////////////////  

  /**
   * Default implementation is to do nothing.
   */
  public void onExecute(BStatusValue out, Context cx)
  {
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  public BIcon getIcon() 
  { 
    BValue dynamic = get("icon");
    if (dynamic instanceof BIcon)
      return (BIcon)dynamic;
    return icon; 
  }
  private static final BIcon icon = BIcon.std("control/booleanPoint.png");

}
