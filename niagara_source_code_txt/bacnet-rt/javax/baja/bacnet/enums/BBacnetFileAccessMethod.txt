/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BBacnetFileAccessMethod represents the BacnetFileAccessMethod
 * enumeration.<p>
 * NOTE: These enumeration values are the opposite of the ASN tag
 * values.  The enumeration value for record access is 0, while the
 * AtomicReadFile ASN.1 tag defining record access is 1.  The enumeration
 * value is only used in the File object's File_Access_Method property.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 06 Mar 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("recordAccess"),
    @Range("streamAccess")
  }
)
public final class BBacnetFileAccessMethod
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetFileAccessMethod(2619004564)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for recordAccess. */
  public static final int RECORD_ACCESS = 0;
  /** Ordinal value for streamAccess. */
  public static final int STREAM_ACCESS = 1;

  /** BBacnetFileAccessMethod constant for recordAccess. */
  public static final BBacnetFileAccessMethod recordAccess = new BBacnetFileAccessMethod(RECORD_ACCESS);
  /** BBacnetFileAccessMethod constant for streamAccess. */
  public static final BBacnetFileAccessMethod streamAccess = new BBacnetFileAccessMethod(STREAM_ACCESS);

  /** Factory method with ordinal. */
  public static BBacnetFileAccessMethod make(int ordinal)
  {
    return (BBacnetFileAccessMethod)recordAccess.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetFileAccessMethod make(String tag)
  {
    return (BBacnetFileAccessMethod)recordAccess.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetFileAccessMethod(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetFileAccessMethod DEFAULT = recordAccess;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetFileAccessMethod.class);

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
}
