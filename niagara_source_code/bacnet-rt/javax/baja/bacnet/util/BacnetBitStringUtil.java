/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.data.BIDataValue;
import javax.baja.sys.*;

import javax.baja.alarm.BAlarmTransitionBits;
import javax.baja.alarm.ext.BLimitEnable;
import javax.baja.status.BStatus;
import javax.baja.util.BDaysOfWeekBits;
import javax.baja.nre.util.IntHashMap;
import javax.baja.util.Lexicon;

import javax.baja.bacnet.datatypes.BBacnetBitString;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;

/**
 * BacnetBitStringUtil.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 25 Mar 02
 * @since Niagara 3 Bacnet 1.0
 */
public class BacnetBitStringUtil
{

////////////////////////////////////////////////////////////////
// Bacnet to Niagara Bit String conversions
////////////////////////////////////////////////////////////////

  /**
   * BacnetDaysOfWeek to BDaysOfWeekBits
   * This is handled specially because extra stuff needs to be done.
   * Niagara uses an integer to represent the days of the week bits,
   * with Sunday in bit position 0, up to Saturday in position 6 (position
   * 7 is unused).  Bacnet uses Monday in position 7, down to Sunday in
   * position 1, with position 0 unused.
   */
  public static BDaysOfWeekBits getBDaysOfWeekBits(BBacnetBitString bs)
  {
    return getBDaysOfWeekBits(bs.getBits());
  }

  /**
   * BacnetDaysOfWeek to BDaysOfWeekBits
   * This is handled specially because extra stuff needs to be done.
   * Niagara uses an integer to represent the days of the week bits,
   * with Sunday in bit position 0, up to Saturday in position 6 (position
   * 7 is unused).  Bacnet uses Monday in position 7, down to Sunday in
   * position 1, with position 0 unused.
   */
  public static BDaysOfWeekBits getBDaysOfWeekBits(boolean[] bsbits)
  {
    int len = 7;
    if (bsbits.length != len) throw new IllegalArgumentException("Invalid bit string length!");
    int bit = 0x2;
    int dowbits = bsbits[6] ? 1 : 0;    // Handle Sunday separately.
    for (int i = 0; i < bsbits.length - 1; i++)
    {
      if (bsbits[i]) dowbits |= bit;
      bit <<= 1;
    }
    return BDaysOfWeekBits.make(dowbits);
  }

  /**
   * BDaysOfWeekBits to BacnetDaysOfWeek
   * This is handled specially because extra stuff needs to be done.
   * Niagara uses an integer to represent the days of the week bits,
   * with Sunday in bit position 0, up to Saturday in position 6 (position
   * 7 is unused).  Bacnet uses Monday in position 7, down to Sunday in
   * position 1, with position 0 unused.
   */
  public static BBacnetBitString getBacnetDaysOfWeek(BDaysOfWeekBits dow)
  {
    boolean[] bsbits = new boolean[7];
    int dowbits = dow.getBits();
    int bit = 0x02;
    for (int i = 0; i < 6; i++)
    {
      bsbits[i] = (dowbits & bit) != 0;
      bit <<= 1;
    }
    bsbits[6] = (dowbits & 1) != 0;
    return BBacnetBitString.make(bsbits, BACNET_DAYS_OF_WEEK_FACETS);
  }

  /**
   * BacnetEventTransitionBits to BAlarmTransitionBits.
   */
  public static BAlarmTransitionBits getBAlarmTransitionBits(BBacnetBitString bs)
  {
    return getBAlarmTransitionBits(bs.getBits());
  }

  /**
   * BacnetEventTransitionBits to BAlarmTransitionBits.
   */
  public static BAlarmTransitionBits getBAlarmTransitionBits(boolean[] bits)
  {
    return (BAlarmTransitionBits)getNiagaraBitString(bits, BACNET_EVENT_TRANSITION_BITS, BAlarmTransitionBits.class);
  }

  /**
   * BAlarmTransitionBits to BacnetEventTransitionBits.
   */
  public static BBacnetBitString getBacnetEventTransitionBits(BAlarmTransitionBits at)
  {
    return getBacnetBitString(at,
      BACNET_EVENT_TRANSITION_BITS,
      BAlarmTransitionBits.class,
      BACNET_EVENT_TRANSITION_BITS_FACETS);
  }

  /**
   * BacnetLimitEnable to BLimitEnable.
   */
  public static BLimitEnable getBLimitEnable(BBacnetBitString bs)
  {
    return getBLimitEnable(bs.getBits());
  }

  /**
   * BacnetLimitEnable to BLimitEnable.
   */
  public static BLimitEnable getBLimitEnable(boolean[] bits)
  {
//    return (BLimitEnable)getNiagaraBitString(bits, BACNET_LIMIT_ENABLE, BLimitEnable.class);
    BLimitEnable le = new BLimitEnable();
    le.setLowLimitEnable(bits[0]);
    le.setHighLimitEnable(bits[1]);
    return le;
  }

  /**
   * BLimitEnable to BacnetLimitEnable.
   */
  public static BBacnetBitString getBacnetLimitEnable(BLimitEnable le)
  {
    return BBacnetBitString.make(new boolean[] { le.getLowLimitEnable(), le.getHighLimitEnable() },
      BACNET_LIMIT_ENABLE_FACETS);
  }

  /**
   * BacnetStatusFlags to BStatus.
   */
  public static BStatus getBStatus(BBacnetBitString bs)
  {
    return getBStatus(bs.getBits());
  }

  /**
   * BacnetStatusFlags to BStatus.
   */
  public static BStatus getBStatus(boolean[] bits)
  {
    int ibits = 0;
    if (bits[0]) ibits |= BStatus.ALARM;
    if (bits[1]) ibits |= BStatus.FAULT;
    if (bits[2]) ibits |= BStatus.OVERRIDDEN;
    if (bits[3]) ibits |= BStatus.DISABLED;
    return BStatus.make(ibits);
  }

  /**
   * BStatus to BacnetStatusFlags.
   */
  public static BBacnetBitString getBacnetStatusFlags(BStatus status)
  {
    return BBacnetBitString.make(new boolean[]
      { status.isAlarm(), status.isFault(), status.isOverridden(), status.isDisabled() });
  }

  /**
   * String representation of BStatus to BacnetStatusFlags.
   */
  public static BBacnetBitString getBacnetStatusFlags(String s)
  {
    return BBacnetBitString.make(new boolean[]
      {
        (s.indexOf("alarm") >= 0),
        (s.indexOf("fault") >= 0),
        (s.indexOf("overridden") >= 0),
        (s.indexOf("outOfService") >= 0)
      });
  }


////////////////////////////////////////////////////////////////
// Generic conversion methods
////////////////////////////////////////////////////////////////

  /**
   * Bacnet to Niagara.
   * Generic conversion from a Bacnet bit string to a Niagara bit string.
   * It is assumed that the Niagara bit string contains
   * a static factory method with the following signature:<p>
   * make(I)L<classname><p>.
   * The bits are filled in the following order: bit1=0x1, bit2=0x2, bit3=0x4, etc.
   * This bit order may not be correct for some bit strings, which may require
   * special processing.  For example, BStatus does not follow this order.
   */
  private static Object getNiagaraBitString(boolean[] bsbits, String bsName, Class<?> niagaraClass)
  {
    int ibits = 0;
    try
    {
      int len = getBitStringLength(bsName);
      if (bsbits.length != len)
        throw new IllegalArgumentException("Invalid bit string length!" + bsbits.length + " != " + len);
      int bit = 1;
      for (int i = 0; i < len; i++)
      {
        if (bsbits[i]) ibits |= bit;
        bit <<= 1;
      }

      Method maker = niagaraClass.getMethod("make", new Class<?>[] { Integer.TYPE });
      return maker.invoke(null, new Object[] { Integer.valueOf(ibits) });
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, "Error creating Bacnet bit string!", e);
      return null;
    }
  }

  /**
   * Niagara to Bacnet.
   * Generic conversion from a Niagara bit string to a Bacnet bit string.
   * It is assumed that the Niagara bit string is stored in an int, which is
   * accessible (read-only) by the method:<p>
   * getBits()I<p>
   * The bits are filled in the following order: bit1=0x1, bit2=0x2, bit3=0x4, etc.
   * This bit order may not be correct for some bit strings, which may require
   * special processing.  For example, BStatus does not follow this order.
   */
  private static BBacnetBitString getBacnetBitString(BSimple bitstr,
                                                     String bsName,
                                                     Class<?> niagaraClass,
                                                     BFacets tags)
  {
    try
    {
      int len = getBitStringLength(bsName);
      boolean[] bsbits = new boolean[len];
      Method getBits = niagaraClass.getMethod("getBits", (Class<?>[])null);
      int ibits = ((Integer)getBits.invoke(bitstr, (Object[])null)).intValue();
      int bit = 1;
      for (int i = 0; i < len; i++)
      {
        bsbits[i] = (ibits & bit) != 0;
        bit <<= 1;
      }
      return BBacnetBitString.make(bsbits, tags);
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, "Error creating Bacnet bit string!", e);
      return BBacnetBitString.make(new boolean[0]);
    }
  }

  public static BBacnetBitString decode(String s,
                                        String bitStringName)
  {
    StringTokenizer st = new StringTokenizer(s, ";");
    HashMap<String, Integer> m = getBitStringIndexMap(bitStringName);
    if (m == null)
      throw new IllegalArgumentException("Bit String " + bitStringName + " not known to BacnetBitStringUtil!");
    boolean[] bits = new boolean[getBitStringLength(bitStringName)];
    while (st.hasMoreTokens())
    {
      Integer ndx = m.get(st.nextToken());
      if (ndx != null)
        bits[ndx.intValue()] = true;
    }
    return BBacnetBitString.make(bits, BFacets.make(getBitStringMap(bitStringName)));
  }

  public static Context getBitStringTags(int objectType, int propertyId)
  {
    return (Context)propIdToTags.get(propertyId);
  }


////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////

  public static int getBitStringLength(String name)
  {
    Integer i = bitStringSizes.get(name);
    return (i == null) ? 0 : i.intValue();
  }

  public static String[] getTags(Context cx)
  {
    if (cx == null) return new String[0];
    int i = 0;
    BString tag;
    ArrayList<String> a = new ArrayList<>();
    do
    {
      tag = (BString)cx.getFacet("bit" + i);
      if (tag != null) a.add(tag.getString());
      i++;
    } while (tag != null);
    return a.toArray(new String[a.size()]);
  }

  public static int getBitIndex(String mapName, String bitName)
  {
    HashMap<String, Integer> m = getBitStringIndexMap(mapName);
    if (m != null)
    {
      Integer id = m.get(bitName);
      if (id != null)
        return id.intValue();
    }
    return -1;
  }

  public static HashMap<String, BIDataValue> getBitStringMap(String name)
  {
    return bitStringsMap.get(name);
  }

  public static HashMap<String, Integer> getBitStringIndexMap(String name)
  {
    return bitStringsIndexMap.get(name);
  }

////////////////////////////////////////////////////////////////
// Facets support
////////////////////////////////////////////////////////////////

  /**
   * Generate a hash map for the given bit string name,
   * containing the bit names, keyed by the bit indices,
   * which are represented as Strings ("bitX"), in order to
   * be usable in a BFacets object.
   *
   * @param lexicon the module lexicon.
   * @param name    the name of the bit string (use the Bacnet spec name).
   * @return a HashMap containing the names for each bit in the string,
   * suitable for using in slot facets.
   */
  private static HashMap<String, BIDataValue> generateMap1(Lexicon lexicon, String name)
  {
    HashMap<String, BIDataValue> map = new HashMap<>();
    map.put("bsName", BString.make(name));
    try
    {
      int len = Integer.parseInt(lexicon.get(name + ".numBits"));
      bitStringSizes.put(name, Integer.valueOf(len));
      for (int i = 0; i < len; i++)
      {
        String key = "bit" + i;
        BString value = BString.make(lexicon.get(name + "." + key));
        map.put(key, value);
      }
      bitStringsMap.put(name, map);
    }
    catch (NullPointerException e)
    {
      logger.log(Level.SEVERE, "Error creating bit string map: " + name, e);
    }
    return map;
  }

  /**
   * Generate a hash map for the given bit string indices,
   * keyed by the bit names.  This is useful for searching if
   * a bit is set in the bit string, where you only have the name
   * (such as checking for service/object type support).
   *
   * @param lexicon the module lexicon.
   * @param name    the name of the bit string (use the Bacnet spec name).
   * @return a HashMap containing the bit ids as Integers, keyed
   * by the bit names.
   */
  private static HashMap<String, Integer> generateMap2(Lexicon lexicon, String name)
  {
    HashMap<String, Integer> map = new HashMap<>();
    try
    {
      int len = Integer.parseInt(lexicon.get(name + ".numBits"));
      for (int i = 0; i < len; i++)
      {
        String bitNum = "bit" + i;
        String key = lexicon.get(name + "." + bitNum);
        map.put(key, Integer.valueOf(i));
      }
      bitStringsIndexMap.put(name, map);
    }
    catch (NullPointerException e)
    {
      logger.log(Level.SEVERE, "Error creating bit string index map: " + name, e);
    }
    return map;
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final BBacnetBitString DEFAULT_STATUS = getBacnetStatusFlags(BStatus.ok);
  /**
   * Module lexicon - contains bit string bit names.
   */
  private static final Lexicon lexicon = Lexicon.make(Sys.getModuleForClass(BacnetBitStringUtil.class),
    Sys.getLanguage());

  private static final Logger logger = Logger.getLogger("bacnet.util");

  /**
   * Bit string length list.
   */
  private static final HashMap<String, Integer> bitStringSizes = new HashMap<>();

  /**
   * Bit string map list.
   */
  private static final HashMap<String, HashMap<String, BIDataValue>> bitStringsMap = new HashMap<>();

  /**
   * Bit string index map list.
   */
  private static final HashMap<String, HashMap<String, Integer>> bitStringsIndexMap = new HashMap<>();

  /**
   * Map of propertyIds to facets for use in generating toString().
   */
  private static final IntHashMap propIdToTags = new IntHashMap();

  /** Bit string names. */
  public static final String BACNET_GENERIC_BITS            = "BacnetGenericBitString";
  public static final String BACNET_DAYS_OF_WEEK = "BacnetDaysOfWeek";
  public static final String BACNET_EVENT_TRANSITION_BITS = "BacnetEventTransitionBits";
  public static final String BACNET_LIMIT_ENABLE = "BacnetLimitEnable";
  public static final String BACNET_LOG_STATUS = "BacnetLogStatus";
  public static final String BACNET_OBJECT_TYPES_SUPPORTED = "BacnetObjectTypesSupported";
  public static final String BACNET_RESULT_FLAGS = "BacnetResultFlags";
  public static final String BACNET_SERVICES_SUPPORTED = "BacnetServicesSupported";
  public static final String BACNET_STATUS_FLAGS = "BacnetStatusFlags";
  /** Hash maps for known bit strings. */
  public static final HashMap<String, BIDataValue> BACNET_GENERIC_BITS_MAP           = generateMap1(lexicon, BACNET_GENERIC_BITS);
  public static final HashMap<String, BIDataValue> BACNET_DAYS_OF_WEEK_MAP = generateMap1(lexicon, BACNET_DAYS_OF_WEEK);
  public static final HashMap<String, BIDataValue> BACNET_EVENT_TRANSITION_BITS_MAP = generateMap1(lexicon, BACNET_EVENT_TRANSITION_BITS);
  public static final HashMap<String, BIDataValue> BACNET_LIMIT_ENABLE_MAP = generateMap1(lexicon, BACNET_LIMIT_ENABLE);
  public static final HashMap<String, BIDataValue> BACNET_LOG_STATUS_MAP = generateMap1(lexicon, BACNET_LOG_STATUS);
  public static final HashMap<String, BIDataValue> BACNET_OBJECT_TYPES_SUPPORTED_MAP = generateMap1(lexicon, BACNET_OBJECT_TYPES_SUPPORTED);
  public static final HashMap<String, BIDataValue> BACNET_RESULT_FLAGS_MAP = generateMap1(lexicon, BACNET_RESULT_FLAGS);
  public static final HashMap<String, BIDataValue> BACNET_SERVICES_SUPPORTED_MAP = generateMap1(lexicon, BACNET_SERVICES_SUPPORTED);
  public static final HashMap<String, BIDataValue> BACNET_STATUS_FLAGS_MAP = generateMap1(lexicon, BACNET_STATUS_FLAGS);

  /**
   * Hash maps for known bit strings.
   */
  public static final HashMap<String, Integer> BACNET_GENERIC_BITS_INDEX_MAP           = generateMap2(lexicon, BACNET_GENERIC_BITS);
  public static final HashMap<String, Integer> BACNET_DAYS_OF_WEEK_INDEX_MAP = generateMap2(lexicon, BACNET_DAYS_OF_WEEK);
  public static final HashMap<String, Integer> BACNET_EVENT_TRANSITION_BITS_INDEX_MAP = generateMap2(lexicon, BACNET_EVENT_TRANSITION_BITS);
  public static final HashMap<String, Integer> BACNET_LIMIT_ENABLE_INDEX_MAP = generateMap2(lexicon, BACNET_LIMIT_ENABLE);
  public static final HashMap<String, Integer> BACNET_LOG_STATUS_INDEX_MAP = generateMap2(lexicon, BACNET_LOG_STATUS);
  public static final HashMap<String, Integer> BACNET_OBJECT_TYPES_SUPPORTED_INDEX_MAP = generateMap2(lexicon, BACNET_OBJECT_TYPES_SUPPORTED);
  public static final HashMap<String, Integer> BACNET_RESULT_FLAGS_INDEX_MAP = generateMap2(lexicon, BACNET_RESULT_FLAGS);
  public static final HashMap<String, Integer> BACNET_SERVICES_SUPPORTED_INDEX_MAP = generateMap2(lexicon, BACNET_SERVICES_SUPPORTED);
  public static final HashMap<String, Integer> BACNET_STATUS_FLAGS_INDEX_MAP = generateMap2(lexicon, BACNET_STATUS_FLAGS);

  /**
   * Facets for slots.
   */
  public static final BFacets BACNET_DAYS_OF_WEEK_FACETS = BFacets.make(BACNET_DAYS_OF_WEEK_MAP);
  public static final BFacets BACNET_EVENT_TRANSITION_BITS_FACETS = BFacets.make(BACNET_EVENT_TRANSITION_BITS_MAP);
  public static final BFacets BACNET_LIMIT_ENABLE_FACETS = BFacets.make(BACNET_LIMIT_ENABLE_MAP);
  public static final BFacets BACNET_LOG_STATUS_FACETS = BFacets.make(BACNET_LOG_STATUS_MAP);
  public static final BFacets BACNET_OBJECT_TYPES_SUPPORTED_FACETS = BFacets.make(BACNET_OBJECT_TYPES_SUPPORTED_MAP);
  public static final BFacets BACNET_RESULT_FLAGS_FACETS = BFacets.make(BACNET_RESULT_FLAGS_MAP);
  public static final BFacets BACNET_SERVICES_SUPPORTED_FACETS = BFacets.make(BACNET_SERVICES_SUPPORTED_MAP);
  public static final BFacets BACNET_STATUS_FLAGS_FACETS = BFacets.make(BACNET_STATUS_FLAGS_MAP);

  /**
   * Context instances.
   */
  public static final Context BacnetDaysOfWeek = /*new BasicContext(null, */BACNET_DAYS_OF_WEEK_FACETS             /*)*/;
  public static final Context BacnetEventTransitionBits = /*new BasicContext(null, */BACNET_EVENT_TRANSITION_BITS_FACETS    /*)*/;
  public static final Context BacnetLimitEnable = /*new BasicContext(null, */BACNET_LIMIT_ENABLE_FACETS             /*)*/;
  public static final Context BacnetLogStatus = /*new BasicContext(null, */BACNET_LOG_STATUS_FACETS             /*)*/;
  public static final Context BacnetObjectTypesSupported = /*new BasicContext(null, */BACNET_OBJECT_TYPES_SUPPORTED_FACETS   /*)*/;
  public static final Context BacnetResultFlags = /*new BasicContext(null, */BACNET_RESULT_FLAGS_FACETS             /*)*/;
  public static final Context BacnetServicesSupported = /*new BasicContext(null, */BACNET_SERVICES_SUPPORTED_FACETS       /*)*/;
  public static final Context BacnetStatusFlags = /*new BasicContext(null, */BACNET_STATUS_FLAGS_FACETS             /*)*/;

  /** Populate the propIdToTags map. */
  static
  {
    // BACnetDaysOfWeek appears as subproperty of BACnetDestination, in NotificationClass.Recipient_List

    // BACnetEventTransitionBits appears in Event_Enable, Acked_Transitions, and Ack_Required
    propIdToTags.put(BBacnetPropertyIdentifier.EVENT_ENABLE, BacnetEventTransitionBits);
    propIdToTags.put(BBacnetPropertyIdentifier.ACKED_TRANSITIONS, BacnetEventTransitionBits);
    propIdToTags.put(BBacnetPropertyIdentifier.ACK_REQUIRED, BacnetEventTransitionBits);

    // BACnetLimitEnable
    propIdToTags.put(BBacnetPropertyIdentifier.LIMIT_ENABLE, BacnetLimitEnable);

    // BACnetLogStatus appears as part of BACnetLogRecord in TrendLog.Log_Buffer

    // BACnetObjectTypesSupported
    propIdToTags.put(BBacnetPropertyIdentifier.PROTOCOL_OBJECT_TYPES_SUPPORTED, BacnetObjectTypesSupported);

    // BACnetResultFlags appears in ReadRangeAck

    // BACnetServicesSupported
    propIdToTags.put(BBacnetPropertyIdentifier.PROTOCOL_SERVICES_SUPPORTED, BacnetServicesSupported);

    // BACnetStatusFlags
    propIdToTags.put(BBacnetPropertyIdentifier.STATUS_FLAGS, BacnetStatusFlags);
  }
}
