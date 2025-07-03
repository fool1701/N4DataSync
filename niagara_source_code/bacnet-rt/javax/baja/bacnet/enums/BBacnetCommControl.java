/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import javax.baja.bacnet.BacnetConst;
import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BBacnetCommControl represents the communication control enumeration
 * defined in the DeviceCommunicationControl-Request definition.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 05 Nov 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("enable"),
    @Range("disable"),
    @Range("disableInitiation")
  }
)
public final class BBacnetCommControl
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetCommControl(3258114258)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for enable. */
  public static final int ENABLE = 0;
  /** Ordinal value for disable. */
  public static final int DISABLE = 1;
  /** Ordinal value for disableInitiation. */
  public static final int DISABLE_INITIATION = 2;

  /** BBacnetCommControl constant for enable. */
  public static final BBacnetCommControl enable = new BBacnetCommControl(ENABLE);
  /** BBacnetCommControl constant for disable. */
  public static final BBacnetCommControl disable = new BBacnetCommControl(DISABLE);
  /** BBacnetCommControl constant for disableInitiation. */
  public static final BBacnetCommControl disableInitiation = new BBacnetCommControl(DISABLE_INITIATION);

  /** Factory method with ordinal. */
  public static BBacnetCommControl make(int ordinal)
  {
    return (BBacnetCommControl)enable.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetCommControl make(String tag)
  {
    return (BBacnetCommControl)enable.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetCommControl(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetCommControl DEFAULT = enable;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetCommControl.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Static methods
////////////////////////////////////////////////////////////////

  /**
   * Create a string tag for the given ordinal.
   *
   * @return the tag for the ordinal, if it is known,
   * or construct one using standard prefixes.
   */
  public static String tag(int id)
  {
    return DEFAULT.getRange().getTag(id);
  }

  /**
   * @param value the boolean value to be represented.
   * @return a BBacnetCommControl object with the given code.
  public static final BBacnetCommControl make(boolean value)
  {
  return (value ? enable : disable);
  }
   */

  /**
   * @return String representation of this BEnum.
   */
  public String toString(Context context)
  {
    if ((context != null) && context.equals(BacnetConst.facetsContext))
      return getTag();
    return getDisplayTag(context);
  }
}
