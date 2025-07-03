/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import java.util.*;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BCharacterSetEncoding represents the enumeration of character sets
 * defined by Bacnet for string encoding.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 22 Mar 02
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "iso10646_UTF8", ordinal = 0),
    @Range(value = "ibmMicrosoftDBCS", ordinal = 1),
    @Range(value = "jisX0208", ordinal = 2),
    @Range(value = "iso10646_UCS4", ordinal = 3),
    @Range(value = "iso10646_UCS2", ordinal = 4),
    @Range(value = "iso8859_1", ordinal = 5),
    @Range(value = "unknown", ordinal = 255)
  }
)
public final class BCharacterSetEncoding
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BCharacterSetEncoding(462053815)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for iso10646_UTF8. */
  public static final int ISO_10646_UTF8 = 0;
  /** Ordinal value for ibmMicrosoftDBCS. */
  public static final int IBM_MICROSOFT_DBCS = 1;
  /** Ordinal value for jisX0208. */
  public static final int JIS_X0208 = 2;
  /** Ordinal value for iso10646_UCS4. */
  public static final int ISO_10646_UCS4 = 3;
  /** Ordinal value for iso10646_UCS2. */
  public static final int ISO_10646_UCS2 = 4;
  /** Ordinal value for iso8859_1. */
  public static final int ISO_8859_1 = 5;
  /** Ordinal value for unknown. */
  public static final int UNKNOWN = 255;

  /** BCharacterSetEncoding constant for iso10646_UTF8. */
  public static final BCharacterSetEncoding iso10646_UTF8 = new BCharacterSetEncoding(ISO_10646_UTF8);
  /** BCharacterSetEncoding constant for ibmMicrosoftDBCS. */
  public static final BCharacterSetEncoding ibmMicrosoftDBCS = new BCharacterSetEncoding(IBM_MICROSOFT_DBCS);
  /** BCharacterSetEncoding constant for jisX0208. */
  public static final BCharacterSetEncoding jisX0208 = new BCharacterSetEncoding(JIS_X0208);
  /** BCharacterSetEncoding constant for iso10646_UCS4. */
  public static final BCharacterSetEncoding iso10646_UCS4 = new BCharacterSetEncoding(ISO_10646_UCS4);
  /** BCharacterSetEncoding constant for iso10646_UCS2. */
  public static final BCharacterSetEncoding iso10646_UCS2 = new BCharacterSetEncoding(ISO_10646_UCS2);
  /** BCharacterSetEncoding constant for iso8859_1. */
  public static final BCharacterSetEncoding iso8859_1 = new BCharacterSetEncoding(ISO_8859_1);
  /** BCharacterSetEncoding constant for unknown. */
  public static final BCharacterSetEncoding unknown = new BCharacterSetEncoding(UNKNOWN);

  /** Factory method with ordinal. */
  public static BCharacterSetEncoding make(int ordinal)
  {
    return (BCharacterSetEncoding)iso10646_UTF8.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BCharacterSetEncoding make(String tag)
  {
    return (BCharacterSetEncoding)iso10646_UTF8.getRange().get(tag);
  }

  /** Private constructor. */
  private BCharacterSetEncoding(int ordinal)
  {
    super(ordinal);
  }

  public static final BCharacterSetEncoding DEFAULT = iso10646_UTF8;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCharacterSetEncoding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  //Java
  public String getEncodingName()
  {
    int ord = getOrdinal();
    if (ord >= 0 && ord < javaNames.length - 1)
      return javaNames[ord];

    return javaNames[javaNames.length - 1];
  }

  //BACnet
  public String getCharSetName()
  {
    int ord = getOrdinal();
    if (ord >= 0 && ord < bacnetNames.length - 1)
      return bacnetNames[ord];

    return bacnetNames[bacnetNames.length - 1];
  }

  public static BCharacterSetEncoding fromCharSetName(String charsetName)
  {
    return BCharacterSetEncoding.make(map.get(charsetName));
  }

  private static final String[] javaNames =
    {
      "UTF-8",
      "DBCS",
      "EUC-JP",
      "UTF-32BE",
      "UTF-16BE",
      "ISO-8859-1",
      null
    };

  private static final String[] bacnetNames =
    {
      "ISO 10646 (UTF-8)",
      "IBM/Microsoft DBCS",
      "JIS X 0208",
      "ISO 10646 (UCS-4)",
      "ISO 10646 (UCS-2)",
      "ISO 8859-1",
      "Unknown"
    };

  private static final HashMap<String, String> map = new HashMap<>();

  static
  {
    for (int i = 0; i < javaNames.length; i++)
    {
      String javaName = javaNames[i];
      if (javaName != null)
        map.put(bacnetNames[i], javaName);
    }
  }
}
