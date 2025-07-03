/**
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BStruct;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconText;

/**
 * BRolloverValue is used to specify a rollover value (i.e. min or max) for
 * a numeric history to support delta logging.  The value can be null (unspecified),
 * which means that no rollover value is defined.
 *
 * @author    Scott Hoye
 * @creation  12 May 05
 * @version   $Revision: 2$ $Date: 5/23/05 1:34:16 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 True if unspecified, false if the value field is valid.
 */
@NiagaraProperty(
  name = "unspecified",
  type = "boolean",
  defaultValue = "true"
)
/*
 The rollover value.
 */
@NiagaraProperty(
  name = "value",
  type = "double",
  defaultValue = "0.0"
)
public final class BRolloverValue
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BRolloverValue(1511464406)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "unspecified"

  /**
   * Slot for the {@code unspecified} property.
   * True if unspecified, false if the value field is valid.
   * @see #getUnspecified
   * @see #setUnspecified
   */
  public static final Property unspecified = newProperty(0, true, null);

  /**
   * Get the {@code unspecified} property.
   * True if unspecified, false if the value field is valid.
   * @see #unspecified
   */
  public boolean getUnspecified() { return getBoolean(unspecified); }

  /**
   * Set the {@code unspecified} property.
   * True if unspecified, false if the value field is valid.
   * @see #unspecified
   */
  public void setUnspecified(boolean v) { setBoolean(unspecified, v, null); }

  //endregion Property "unspecified"

  //region Property "value"

  /**
   * Slot for the {@code value} property.
   * The rollover value.
   * @see #getValue
   * @see #setValue
   */
  public static final Property value = newProperty(0, 0.0, null);

  /**
   * Get the {@code value} property.
   * The rollover value.
   * @see #value
   */
  public double getValue() { return getDouble(value); }

  /**
   * Set the {@code value} property.
   * The rollover value.
   * @see #value
   */
  public void setValue(double v) { setDouble(value, v, null); }

  //endregion Property "value"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRolloverValue.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Override
  public String toString(Context cx)
  {
    if (getUnspecified())
      return unspecifiedTxt.getText(cx);
    else
      return Double.toString(getValue());
  }

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  private static final LexiconText unspecifiedTxt = LexiconText.make("history", "rollover.unspecified");
}
