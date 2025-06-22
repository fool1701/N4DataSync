/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIEnum;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BEnumPoint defines a read only enum control point.
 *
 * @author    Dan Giorgis
 * @creation  02 May 01
 * @version   $Revision: 22$ $Date: 3/28/05 11:40:32 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.makeEnum()",
  override = true
)
/*
 The out slot of a BBooleanPoint is a
 BStatusEnum
 */
@NiagaraProperty(
  name = "out",
  type = "BStatusEnum",
  defaultValue = "new BStatusEnum()",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY
)
public class BEnumPoint
  extends BDiscretePoint
  implements BIEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.BEnumPoint(915362447)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.makeEnum(), null);

  //endregion Property "facets"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * The out slot of a BBooleanPoint is a
   * BStatusEnum
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, new BStatusEnum(), null);

  /**
   * Get the {@code out} property.
   * The out slot of a BBooleanPoint is a
   * BStatusEnum
   * @see #out
   */
  public BStatusEnum getOut() { return (BStatusEnum)get(out); }

  /**
   * Set the {@code out} property.
   * The out slot of a BBooleanPoint is a
   * BStatusEnum
   * @see #out
   */
  public void setOut(BStatusEnum v) { set(out, v, null); }

  //endregion Property "out"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnumPoint.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Interfaces
////////////////////////////////////////////////////////////////

  /**
   * Return the vaule as a enum.
   */
  public final BEnum getEnum() { return getOut().getValue(); }

  /**
   * Return getFacets().
   */
  public final BFacets getEnumFacets() { return getFacets(); }

  /**
   * Get the control output value.
   */
  public final BStatusValue getOutStatusValue() { return getOut(); }

  /**
   * Return the result of <code>out.value.isActive</code>.
   */
  public final boolean isActive() { return getEnum().isActive(); }

////////////////////////////////////////////////////////////////
// ControlPoint
////////////////////////////////////////////////////////////////  

  /**
   * Default implementation is to do nothing.
   */
  public void onExecute(BStatusValue out, Context cx)
  {
    // Need to check to see if the point facets enum range changed,
    // and if so, update the working value
    BDynamicEnum val = (BDynamicEnum)out.getValueValue();
    BEnumRange range = val.getRange();
    
    BFacets facets = getFacets();
    BEnumRange facetsRange = (BEnumRange)(facets.get(BFacets.RANGE));
    if (facetsRange == null)
      facetsRange = BEnumRange.NULL;
    if (facetsRange.equals(range))
      return; // range check is fine, nothing to do
    
    // enum range has changed, so update the working variable
    // so the output will be reflected
    val = BDynamicEnum.make(val.getOrdinal(), facetsRange);
    out.setValueValue(val);
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
  private static final BIcon icon = BIcon.std("control/enumPoint.png");

}
