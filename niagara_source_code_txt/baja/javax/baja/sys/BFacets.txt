/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.WeakHashMap;
import javax.baja.data.BIDataValue;
import javax.baja.data.DataUtil;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.IntHashMap;
import javax.baja.units.BUnit;
import javax.baja.user.BUser;
import com.tridium.sys.schema.Fw;

/**
 * BFacets is a map of name/value pairs used to annotate a
 * BComplex's Slot or to just provide additional metadata
 * about something.  The values of facets may only be
 * BIDataValues which are a predefined subset of simples.
 * The encoding BNF:
 *
 * <pre>{@code
 *   facets    := pairs
 *   pairs     := pair { "|" pairs }
 *   pair      := SlotPath::name "=" typeValue
 *   dataValue := DataUtil.marshal & unmarshal
 * }</pre>
 *
 * @author    Brian Frank
 * @creation  23 Jan 01
 * @version   $Revision: 92$ $Date: 7/15/11 11:42:15 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BFacets
  extends BSimple
  implements Context
{

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  /**
   * Used with certain types (such as numerics, enums, Strings, RelTimes, etc) to specify
   * inclusive min [BNumber]. This particular facet is enforced on both the client
   * and server (station) sides during value changes.
   */
  public static final String MIN = "min";

  /**
   * Used with certain types (such as numerics, enums, Strings, RelTimes, etc) to specify
   * inclusive max [BNumber]. This particular facet is enforced on both the client
   * and server (station) sides during value changes.
   */
  public static final String MAX = "max";

  /** Used with booleans to define text to display when true.
      The value is a String evaluated as a baja:Format. [BString] */
  public static final String TRUE_TEXT = "trueText";

  /** Used with booleans to define text to display when false.
      The value is a String evaluated as a baja:Format [BString] */
  public static final String FALSE_TEXT = "falseText";

  /** Used with floats to define number of digits after decimal [BInteger] */
  public static final String PRECISION = "precision";

  /** Used with integers and floats to units for quantity [BUnit] */
  public static final String UNITS = "units";

  /** Used with integers to qualify base radix [BInteger] */
  public static final String RADIX = "radix";

  /** Used with enums to define enum range [BEnumRange] */
  public static final String RANGE = "range";

  /** Used with abs time to show date [BBoolean] */
  public static final String SHOW_DATE = "showDate";

  /** Used with abs time to show time [BBoolean] */
  public static final String SHOW_TIME = "showTime";

  /** Used with abs time to show seconds [BBoolean] */
  public static final String SHOW_SECONDS = "showSeconds";

  /** Used with abs and rel time to show milliseconds [BBoolean] */
  public static final String SHOW_MILLISECONDS = "showMilliseconds";

  /** Used with abs time to show timezone [BBoolean] */
  public static final String SHOW_TIME_ZONE = "showTimeZone";

  /** Used with abs time to specify display timezone [BTimeZone] */
  public static final String TIME_ZONE = "TimeZone";

  /** Used with strings to support a multiline editor [BBoolean] */
  public static final String MULTI_LINE = "multiLine";

  /** Used with strings to specify the number of columns in a text field [BInteger] */
  public static final String FIELD_WIDTH = "fieldWidth";

  /** Used simples to allow/disallow null [BBoolean] */
  public static final String ALLOW_NULL = "allowNull";

  /** Used with ords to indicate if ord should be relativized to
      session during save, default is true [BBoolean] */
  public static final String ORD_RELATIVIZE= "ordRelativize";

  /** This key is used to provide the type spec of
      the field editor to use for editing a property
      value.  It overrides the default field editor
      registered for the property's value.  [BString] */
  public static final String FIELD_EDITOR = "fieldEditor";

  /**
   * This key allows a slot to specify which JS field editor should be used
   * to edit this slot (in applications that support dynamic selection of
   * field editors, such as Multisheet). This should provide the type spec of a
   * {@code web:IJavaScript} implementation. Alternatively, it can provide
   * a RequireJS module ID directly. [BString]
   */
  public static final String UX_FIELD_EDITOR = "uxFieldEditor";

  /** This key is used to provide the type spec of
      the inplace editor to use for editing a property
      value.  It overrides the default inplace editor
      registered for the property's value.  [BString] */
  public static final String INPLACE_EDITOR = "inplaceEditor";

  /** This key is used to provide the type spec of
      the cell editor to use for editing a property
      value.  It overrides the default cell editor
      registered for the property's value.  [BString] */
  public static final String CELL_EDITOR = "cellEditor";

  /** Specifies a target type. [BString formatted as a TypeSpec] */
  public static final String TARGET_TYPE = "targetType";

  /** Specifies a the desired unit system to use for user display.
      If none then no conversion should take place, otherwise the
      system attempts to convert to the most suitable unit.
      [DynamicEnum for UnitConversion] */
  public static final String UNIT_CONVERSION = "unitConversion";

  /** A semicolon separated list of realm names.  See BStation
      for more details on realms. */
  public static final String REALMS = "realms";

  /** Used with numerics to enable/disable displaying units [BBoolean] */
  public static final String SHOW_UNITS = "showUnits";

  /** Used with numerics to enable/disable displaying of separators
      between every 3 digits (i.e. 10,000 vs 10000) [BBoolean] */
  public static final String SHOW_SEPARATORS = "showSeparators";

  /**
   * Used with numerics to indicate if the sign of the number (+/-) should
   * always be displayed or not. When false or unspecified, only the '-'
   * sign will be displayed for negative numbers. This facet only applies
   * to numbers with a base radix of 10. [BBoolean]
   * <p>
   * @since Niagara 3.5
   */
  public static final String FORCE_SIGN = "forceSign";

  /**
   * Used on BComplex object when child slots are to be persisted to/from a
   * location different from that specified when calling ValueDocEncoder.
   * The value of this facet is an ORD string.
   * <p>
   * Any ORD that resolves to a BIDataFile can be used.
   * <p>
   * @since Niagara 4.0
   */
  public static final String ENCODER_FILE = "encoderFile";

  /**
   * Used on control point facets or the station's sysInfo facets,
   * this optional BRelTime facet specifies the maximum duration allowed
   * for an override action on the applicable control point(s) [BRelTime]
   *
   * @since Niagara 3.8
   */
  public static final String MAX_OVERRIDE_DURATION = "maxOverrideDuration";

  /**
   * Used in entity queries to set a default namespace for tags and relations
   * that would otherwise be global.
   */
  public static final String NAMESPACE = "namespace";

  /**
   * Indicates that a slot is security related and changes should be
   * logged to the security history [BBoolean]
   *
   * @since Niagara 4.9
   */
  public static final String SECURITY = "security";

  // NOTE: Whenever you add a constant here, make sure you also
  //       add it to rel/workbench/facetKeys.properties.

////////////////////////////////////////////////////////////////
// Factory: Boolean
////////////////////////////////////////////////////////////////

  private static final String[] BOOLEAN_KEYS = { TRUE_TEXT, FALSE_TEXT };
  private static final BString TRUE_STRING = BString.make("true");
  private static final BString FALSE_STRING = BString.make("false");

  /**
   * Make a facets for a boolean: TRUE_TEXT, FALSE_TEXT.
   */
  public static BFacets makeBoolean(BString trueText, BString falseText)
  {
    return factory(BOOLEAN_KEYS,
      new BIDataValue[] { trueText, falseText });
  }

  /**
   * Make a facets for a boolean: TRUE_TEXT, FALSE_TEXT.
   */
  public static BFacets makeBoolean(String trueText, String falseText)
  {
    return makeBoolean(BString.make(trueText), BString.make(falseText));
  }

  /**
   * Make a facets for a boolean.
   * Default true text to "true" and false test to "false".
   */
  public static BFacets makeBoolean()
  {
    return makeBoolean(TRUE_STRING, FALSE_STRING);
  }

////////////////////////////////////////////////////////////////
// Factory: Numeric
////////////////////////////////////////////////////////////////

  private static final String[] NUMERIC_KEYS = { UNITS, PRECISION, MIN, MAX };

  /**
   * Make a facets for a numeric: UNITS, PRECISION, MIN, and MAX.
   */
  public static BFacets makeNumeric(BUnit unit, BInteger precision, BNumber min, BNumber max)
  {
    if (unit == null) unit = BUnit.NULL;
    return factory(NUMERIC_KEYS,
      new BIDataValue[] { unit, precision, min, max });
  }

  /**
   * Make a facets for a float.
   * Default units to null, precision to 1, min to -inf and
   * max to +inf.
   */
  public static BFacets makeNumeric()
  {
    return makeNumeric(null, BInteger.make(1),
      BDouble.NEGATIVE_INFINITY, BDouble.POSITIVE_INFINITY);
  }

  /**
   * Make a facets for a float: PRECISION.
   * Default min to -inf and max to +inf, and units to null.
   */
  public static BFacets makeNumeric(int precision)
  {
    return makeNumeric(null, BInteger.make(precision),
      BDouble.NEGATIVE_INFINITY, BDouble.POSITIVE_INFINITY);
  }

  /**
   * Make a facets for a float: UNITS, PRECISION.
   * Default min to -inf and max to +inf.
   */
  public static BFacets makeNumeric(BUnit unit, int precision)
  {
    return makeNumeric(unit, BInteger.make(precision),
      BDouble.NEGATIVE_INFINITY, BDouble.POSITIVE_INFINITY);
  }

  /**
   * Make a facets for a float: UNITS, MIN, and MAX.
   * Default precision to 1.
   */
  public static BFacets makeNumeric(BUnit unit, BNumber min, BNumber max)
  {
    return makeNumeric(unit, BInteger.make(1), min, max);
  }

  /**
   * Make a facets for a float: UNITS, PRECISION, MIN, and MAX.
   */
  public static BFacets makeNumeric(BUnit unit, int precision, double min, double max)
  {
    return makeNumeric(unit, BInteger.make(precision),
      BDouble.make(min), BDouble.make(max));
  }

////////////////////////////////////////////////////////////////
// Factory: Integer
////////////////////////////////////////////////////////////////

  private static final String[] INT_KEYS = { UNITS, MIN, MAX, RADIX };

  /**
   * Make a facets for an int: UNITS, MIN, MAX, and RADIX.
   */
  public static BFacets makeInt(BUnit unit, int min, int max, int radix)
  {
    if (unit == null) unit = BUnit.NULL;

    return factory(INT_KEYS,
      new BIDataValue[] {
        unit,
        BInteger.make(min),
        BInteger.make(max),
        BInteger.make(radix)});
  }

  /**
   * Make a facets for an int: UNITS, MIN and MAX.
   * Use default radix of base 10.
   */
  public static BFacets makeInt(BUnit unit, int min, int max)
  {
    return makeInt(unit, min, max, 10);
  }

  /**
   * Make a facets for an int: UNITS, MIN and MAX.
   * Default min to MIN_VALUE and max to MAX_VALUE.
   * Use default radix of base 10.
   */
  public static BFacets makeInt(BUnit units)
  {
    return makeInt(units, Integer.MIN_VALUE, Integer.MAX_VALUE, 10);
  }

  /**
   * Make a facets for an int: UNITS, MIN and MAX.
   * Default units to null.  Use default radix of base 10.
   */
  public static BFacets makeInt(int min, int max)
  {
    return makeInt(null, min, max, 10);
  }

  /**
   * Make a facets for an int: UNITS, MIN and MAX.  Default
   * units to null, min to MIN_VALUE and max to MAX_VALUE.
   * Use default radix of base 10.
   */
  public static BFacets makeInt()
  {
    return makeInt(null, Integer.MIN_VALUE, Integer.MAX_VALUE, 10);
  }

////////////////////////////////////////////////////////////////
// Factory: Enum
////////////////////////////////////////////////////////////////

  private static final String[] ENUM_KEYS = { RANGE };

  /**
   * Make a facets for an enum: RANGE.
   */
  public static BFacets makeEnum(BEnumRange range)
  {
    if (range == null) range = BEnumRange.NULL;

    // we only create one instance of facets per range and cache it on the range itself
    if (range.asFacets == null)
      range.asFacets = factory(ENUM_KEYS, new BIDataValue[] { range });
    return range.asFacets;
  }

  /**
   * Make a facets for an enum: RANGE.
   * Default range to null.
   */
  public static BFacets makeEnum()
  {
    return makeEnum(null);
  }

////////////////////////////////////////////////////////////////
// Factory: Generic
////////////////////////////////////////////////////////////////

  /**
   * Create a BFacets instance with one key/value pair.
   */
  public static BFacets make(String key, boolean value)
  {
    return make(key, BBoolean.make(value));
  }

  /**
   * Create a BFacets instance with one key/value pair.
   */
  public static BFacets make(String key, int value)
  {
    return make(key, BInteger.make(value));
  }

  /**
   * Create a BFacets instance with one key/value pair.
   */
  public static BFacets make(String key, long value)
  {
    return make(key, BLong.make(value));
  }

  /**
   * Create a BFacets instance with one key/value pair.
   */
  public static BFacets make(String key, String value)
  {
    return make(key, BString.make(value));
  }

  /**
   * Create a BFacets instance with one key/value pair.
   */
  public static BFacets make(String key, BIDataValue value)
  {
    // optimize for range facets
    if (key.equals(RANGE) && value instanceof BEnumRange)
      return makeEnum((BEnumRange)value);

    SlotPath.verifyValidName(key); verifyValue(value);
    return factory(
      new String[] { key },
      new BIDataValue[] { value });
  }

  /**
   * Create a BFacets instance with two key/value pairs.
   * Caution: if key1.equals(key2), there will be duplicate keys in the resulting BFacets
   */
  public static BFacets make(
    String key1, BIDataValue value1,
    String key2, BIDataValue value2)
  {
    SlotPath.verifyValidName(key1); verifyValue(value1);
    SlotPath.verifyValidName(key2); verifyValue(value2);
    return factory(
      new String[] { key1, key2 },
      new BIDataValue[] { value1, value2 });
  }

  /**
   * Create a BFacets instance with three key/value pairs.
   * Caution: if any of the keys are equal, there will be duplicate keys in the resulting BFacets
   */
  public static BFacets make(
    String key1, BIDataValue value1,
    String key2, BIDataValue value2,
    String key3, BIDataValue value3)
  {
    SlotPath.verifyValidName(key1); verifyValue(value1);
    SlotPath.verifyValidName(key2); verifyValue(value2);
    SlotPath.verifyValidName(key3); verifyValue(value3);
    return factory(
      new String[] { key1, key2, key3 },
      new BIDataValue[] { value1, value2, value3 });
  }

  /**
   * Create a BFacets instance from its string encoding.
   * Caution: if encoding contains duplicate keys, the resulting BFacets object will contain
   * duplicate keys.
   */
  public static BFacets make(String encoding)
    throws IOException
  {
    return (BFacets)DEFAULT.decodeFromString(encoding);
  }

  /**
   * Trys to create a BFacets instance from its string encoding using
   * {@link #make(String) BFacets.make(String)}. However, if that method throws an IOException,
   * it will be caught and {@code BFacets.NULL} will be returned instead.
   * Caution: if encoding contains duplicate keys, the resulting BFacets object will contain
   * duplicate keys.
   *
   * @since Niagara 3.5
   */
  public static BFacets tryMake(String encoding)
  {
    try { return make(encoding); }
    catch (IOException e) { return BFacets.NULL; }
  }

  /**
   * Create a BFacets instance with the key/value pairs stored in the specified Map.  The keys
   * must be valid String names and each value must be a non-null BIDataValue.
   */
  public static BFacets make(Map<String,? extends BIDataValue> map)
  {
    if (map.isEmpty()) return NULL;

    String[] keys = new String[map.size()];
    BIDataValue[] values = new BIDataValue[map.size()];
    Iterator<String> it = map.keySet().iterator();
    for(int i=0; it.hasNext(); ++i)
    {
      // check name
      String name = it.next();
      SlotPath.verifyValidName(name);

      // check value
      BIDataValue value = map.get(name);
      verifyValue(value);

      keys[i] = name;
      values[i] = value;
    }
    return factory(keys, values);
  }

  /**
   * Create a BFacets instance with the key/value pairs stored in the two arrays.  The keys must
   * be valid String names.
   * Caution: duplicate values in keys will result in duplicate keys in the resulting BFacets
   * object.
   */
  public static BFacets make(String[] keys, BIDataValue[] values)
  {
    if (keys.length != values.length)
      throw new IllegalArgumentException();

    for(int i=0; i<values.length; ++i)
    {
      SlotPath.verifyValidName(keys[i]);
      verifyValue(values[i]);
    }

    return factory(keys.clone(), values.clone());
  }

  /**
   * Make a facets which is the same as the original, except ensure that the specified name/value
   * pair is contained. If the original already contains the specified name/value pair then the
   * original is returned.  Otherwise a new instance is created.
   */
  public static BFacets make(BFacets orig, String key, BIDataValue value)
  {
    SlotPath.verifyValidName(key);
    verifyValue(value);

    int index = orig.indexOf(key);
    if (index >= 0)
    {
      if (orig.values[index].equals(value))
      {
        return orig;
      }
      else
      {
        BIDataValue[] newValues = orig.values.clone();
        newValues[index] = value;
        return factory(orig.keys, newValues, orig.pickle == SKIP_INTERN_PICKLE);
      }
    }
    else
    {
      int origLen = orig.keys.length;
      String[] newKeys = new String[origLen+1];
      BIDataValue[] newValues = new BIDataValue[origLen+1];
      System.arraycopy(orig.keys, 0, newKeys, 0, origLen);
      System.arraycopy(orig.values, 0, newValues, 0, origLen);
      newKeys[origLen] = key;
      newValues[origLen] = value;
      return factory(newKeys, newValues, orig.pickle == SKIP_INTERN_PICKLE);
    }
  }

  /**
   * Make a facets but insure the specified key is removed. If the orig facets doesn't contain
   * the specified key then return the original instance.
   */
  public static BFacets makeRemove(BFacets orig, String key)
  {
    int index = orig.indexOf(key);
    if (index < 0) return orig;
    if (orig.keys.length == 1) return NULL;

    int origLen = orig.keys.length;

    String[] newKeys = new String[origLen-1];
    System.arraycopy(orig.keys, 0, newKeys, 0, index);
    System.arraycopy(orig.keys, index+1, newKeys, index, origLen-index-1);

    BIDataValue[] newValues = new BIDataValue[origLen-1];
    System.arraycopy(orig.values, 0, newValues, 0, index);
    System.arraycopy(orig.values, index+1, newValues, index, origLen-index-1);

    return factory(newKeys, newValues, orig.pickle == SKIP_INTERN_PICKLE);
  }

  /**
   * Make a facets but insure the specified keys are removed.
   * If the orig facets doesn't contain any of the specified keys
   * then return the original instance.
   *
   * @since Niagara 3.7
   */
  public static BFacets makeRemove(BFacets orig, String[] keys)
  {
    if (orig.keys.length == 0) return NULL;

    IntHashMap toRemove = new IntHashMap();
    for (String key : keys)
    {
      int index = orig.indexOf(key);
      if (index >= 0) toRemove.put(index, key);
    }

    if (toRemove.isEmpty())
      return orig;

    int origLen = orig.keys.length;

    String[] newKeys = new String[origLen-toRemove.size()];
    BIDataValue[] newValues = new BIDataValue[origLen-toRemove.size()];
    int newIdx = 0;
    for (int i=0; i<origLen; i++)
    {
      if (toRemove.get(i) == null)
      {
        newKeys[newIdx] = orig.keys[i];
        newValues[newIdx] = orig.values[i];
        newIdx++;
      }
    }

    return factory(newKeys, newValues, orig.pickle == SKIP_INTERN_PICKLE);
  }

  /**
   * Make a facets that is the result of merging the keys from the toAdd instance into the
   * original facets. If toAdd contains any keys that already exist in orig, then the orig values
   * are overwritten.
   */
  public static BFacets make(BFacets orig, BFacets toAdd)
  {
    // make sure a merge is actually necessary
    if ((orig == null) || orig.isNull())
      return (toAdd == null) ? BFacets.NULL : toAdd;

    if ((toAdd == null) || toAdd.isNull())
      return (orig == null) ? BFacets.NULL : orig;

    int maxNewLen = orig.keys.length + toAdd.keys.length;
    String[] newKeys = new String[maxNewLen];
    BIDataValue[] newVals = new BIDataValue[maxNewLen];
    System.arraycopy(orig.keys, 0, newKeys, 0, orig.keys.length);
    System.arraycopy(orig.values, 0, newVals, 0, orig.values.length);

    int addIndex = orig.keys.length;
    for (int i = 0; i < toAdd.keys.length; i++)
    {
      int origIndex = orig.indexOf(toAdd.keys[i]);
      if (origIndex == -1)
      {
        newKeys[addIndex] = toAdd.keys[i];
        newVals[addIndex++] = toAdd.values[i];
      }
      else
        newVals[origIndex] = toAdd.values[i];
    }

    boolean skipIntern = (orig.pickle == SKIP_INTERN_PICKLE) || (toAdd.pickle == SKIP_INTERN_PICKLE);
    if (addIndex != maxNewLen)
    {
      String[] keys = new String[addIndex];
      BIDataValue[] values = new BIDataValue[addIndex];

      System.arraycopy(newKeys, 0, keys, 0, addIndex);
      System.arraycopy(newVals, 0, values, 0, addIndex);
      return factory(keys, values, skipIntern);
    }
    else
      return factory(newKeys, newVals, skipIntern);
  }

  /**
   * If the facets is null return BFacets.NULL, otherwise return the facets instance.
   */
  public static BFacets make(BFacets facets)
  {
    if (facets == null) return BFacets.NULL;
    else return facets;
  }

  /**
   * Starting in Niagara 3.4, interning for BFacets was
   * introduced to take advantage of commonly used BFacet
   * instances.  As a result, since BFacets is not completely
   * immutable (due to pickles), it may be necessary
   * to retrieve a new (non-interned) copy of a BFacets instance
   * when using pickles.  So this method was
   * added to retrieve such a non-interned copy with the
   * given pickle object applied to the BFacets instance returned.
   *
   * This method should be used in place of the deprecated
   * method {@code setPickle(Object pickle)}
   *
   * @since Niagara 3.4
   */
  public static BFacets makePickle(BFacets facets, Object pickle)
  {
    BFacets result = new BFacets(facets.keys, facets.values, facets.encodeStr);
    result.pickle = pickle;
    return result;
  }


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.  At this point we should
   * have safe copies of the keys and values array.
   */
  private BFacets(String[] keys, BIDataValue[] values, String encodeStr)
  {
    if (keys != null)
    {
      for (int i = 0; i < keys.length; i++)
        keys[i] = internedKey(keys[i]);
    }
    this.keys = keys;
    this.values = values;
    this.encodeStr = encodeStr;
  }

  /**
   * This is the main factory method. It returns an interned
   * BFacets instance.
   */
  private static BFacets factory(String[] keys, BIDataValue[] values)
  {
    // return an interned BFacets instance
    return (BFacets)(new BFacets(keys, values, null).intern());
  }

  /**
   * This is the main factory method. It returns an interned
   * BFacets instance.
   */
  private static BFacets factory(String[] keys, BIDataValue[] values, boolean skipIntern)
  {
    BFacets facets = new BFacets(keys, values, null);
    if (skipIntern)
    {
      //facets.setPickle(SKIP_INTERN_PICKLE);
      facets.pickle = SKIP_INTERN_PICKLE;
      return facets;
    }
    else
    {
      // return an interned BFacets instance
      return (BFacets)facets.intern();
    }
  }

  /**
   * create the encoding string
   */
  private static String encode(String[] keys, BIDataValue[] values)
  {
    StringBuilder s = new StringBuilder();
    for(int i=0; i<keys.length; ++i)
    {
      String key = keys[i];
      BObject value = (BObject)values[i];

      if (i > 0) s.append('|');

      try { s.append(key).append('=').append(DataUtil.marshal(value)); }
      catch (Exception e) { throw new BajaRuntimeException(e); }
    }
    return s.toString();
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the number of facets in this instance.
   *
   * @return Returns the number of facet keys in this instance.
   */
  public int size()
  {
    return keys.length;
  }

  /**
   * Test whether this instance has 0 facets defined.
   *
   * @return Returns true if the instance is empty, false otherwise.
   */
  public boolean isEmpty()
  {
    return keys.length == 0;
  }

  /**
   * Get the list of key names.
   */
  public String[] list()
  {
    return keys.clone();
  }

  /**
   * Get the index in the key or return -1.
   */
  private int indexOf(String name)
  {
    for(int i=0; i<keys.length; ++i)
      if (keys[i].equals(name)) return i;
    return -1;
  }

  /**
   * Get a facet value by name.
   */
  public BObject get(String name)
  {
    for(int i=0; i<keys.length; ++i)
      if (keys[i].equals(name)) return (BObject)values[i];
    return null;
  }

  /**
   * Get a facet value by name.
   */
  public BObject get(String name, BObject def)
  {
    for(int i=0; i<keys.length; ++i)
      if (keys[i].equals(name)) return (BObject)values[i];
    return def;
  }

  /**
   * If the facet specified by name is a BBoolean then return
   * its value as a boolean.  Otherwise return def.
   */
  public boolean getb(String name, boolean def)
  {
    BObject val = get(name);
    if (val instanceof BBoolean)
      return ((BBoolean)val).getBoolean();
    return def;
  }

  /**
   * If the facet specified by name is a BNumber then return it as
   * an int.  If the facet is an BEnum then return it's ordinal.
   * Otherwise return def.
   */
  public int geti(String name, int def)
  {
    BObject val = get(name);
    if (val instanceof BNumber) return ((BNumber)val).getInt();
    if (val instanceof BEnum)   return ((BEnum)val).getOrdinal();
    return def;
  }

  /**
   * If the facet specified by name is a BNumber then return it as
   * a long.  If the facet is an BEnum then return it's ordinal.
   * Otherwise return def.
   */
  public long getl(String name, long def)
  {
    BObject val = get(name);
    if (val instanceof BNumber) return ((BNumber)val).getLong();
    if (val instanceof BEnum)   return ((BEnum)val).getOrdinal();
    return def;
  }

  /**
   * If the facet specified by name is a BNumber then return
   * its value as a float.  Otherwise return def.
   */
  public float getf(String name, float def)
  {
    BObject val = get(name);
    if (val instanceof BNumber)
      return ((BNumber)val).getFloat();
    return def;
  }

  /**
   * If the facet specified by name is a BNumber then return
   * its value as a double.  Otherwise return def.
   */
  public double getd(String name, double def)
  {
    BObject val = get(name);
    if (val instanceof BNumber)
      return ((BNumber)val).getDouble();
    return def;
  }

  /**
   * If the facet specified by name is found then return it as a string.
   * If not found return def.
   */
  public String gets(String name, String def)
  {
    BObject val = get(name);
    if (val == null) return def;
    return val.toString();
  }

////////////////////////////////////////////////////////////////
// Context
////////////////////////////////////////////////////////////////

  /**
   * Always return null.
   */
  @Override
  public Context getBase()
  {
    return null;
  }

  /**
   * Always return null.
   */
  @Override
  public BUser getUser()
  {
    return null;
  }

  /**
   * Return {@code get(name)}.
   */
  @Override
  public BObject getFacet(String name)
  {
    return get(name);
  }

  /**
   * Always return Sys.getLanguage().
   */
  @Override
  public String getLanguage()
  {
    return Sys.getLanguage();
  }

  /**
   * Return this.
   */
  @Override
  public BFacets getFacets()
  {
    return this;
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * @return if this is the NULL instance.
   */
  @Override
  public boolean isNull()
  {
    return keys.length == 0;
  }

  /**
   * BFacets are equal if their name/value pairs are equal.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof BFacets))
      return false;

    BFacets a = this;
    BFacets b = (BFacets)obj;

    // confirm each BFacets object has the same number of keys
    if (a.keys.length != b.keys.length) return false;

    // get our key from src facet and perform get on facet we are
    // comparing to
    for( int i = 0; i < a.keys.length; i++ )
    {
      String ak = a.keys[i];
      Object av = a.get(ak);
      Object bv = b.get(ak);
      if ( null == bv || !av.equals(bv)) return false;
    }

    return true;
  }

  /**
   * Return string.
   */
  @Override
  public String toString(Context context)
  {
    StringBuilder s = new StringBuilder();
    for(int i=0; i<keys.length; ++i)
    {
      if (i > 0) s.append(',');
      // don't want to format values using passed context
      // because that is usually myself and doesn't make sense
      s.append(SlotPath.unescape(keys[i])).append('=').append(values[i].toString(context));
    }
    return s.toString();
  }

  /**
   * BFacets uses its encodeToString() value's hash code.
   */
  public int hashCode()
  {
    try
    {
      return encodeToString().hashCode();
    }
    catch(Exception e)
    {
      return System.identityHashCode(this);
    }
  }

  /**
   * BFacets is encoded using writeUTF().
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }

  /**
   * BFacets is decoded using readUTF().
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString(in.readUTF());
  }

  /**
   * Convert this instance to a data value.  This
   * returns encodeToString() as a BString.
   */
  @Override
  public BIDataValue toDataValue()
  {
    try
    {
      return BString.make(encodeToString());
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return BString.make(toString());
    }
  }

  /**
   * Encode according to grammar.
   */
  @Override
  public String encodeToString()
    throws IOException
  {
    // encodeStr will be null only if we are *NOT* interning
    if (encodeStr == null)
      encodeStr = encode(keys, values);

    return encodeStr;
  }

  /**
   * Decode according to grammar.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    if(s==null || s.isEmpty())
      return BFacets.NULL;

    try
    {
      ArrayList<String> keys   = new ArrayList<>();
      ArrayList<BIDataValue> values = new ArrayList<>();
      StringTokenizer st = new StringTokenizer(s, "|");
      while(st.hasMoreTokens())
      {
        String tok = st.nextToken();

        // parse name
        int eq = tok.indexOf('=');
        String name = tok.substring(0, eq);
        SlotPath.verifyValidName(name);

        // parse value
        BIDataValue value = (BIDataValue) DataUtil.unmarshal(tok.substring(eq+1));

        keys.add(name);
        values.add(value);
      }

      if (keys.isEmpty()) return NULL;

      // removed since result.string should always be recomputed at least once in case of encoding
      // changes
      // result.string = s;

      return factory(
        keys.toArray(new String[keys.size()]),
        values.toArray(new BIDataValue[values.size()]));
    }
    catch(IOException e)
    {
      throw e;
    }
    catch(Exception e)
    {
      e.printStackTrace();
      throw new IOException(e.toString());
    }
  }

  /**
   * Verify that value is not null.
   */
  private static void verifyValue(BIDataValue value)
  {
    if (value == null)
      throw new NullPointerException();
  }

////////////////////////////////////////////////////////////////
// Pickle
////////////////////////////////////////////////////////////////

  /**
   * The pickle is an arbitrary Java object which has been
   * cached on this instance of BFacets.  It is never serialized,
   * but rather has a lifetime only within the current VM.
   * When using this API be mindful that as a BSimple this
   * instance will be returned from {@code newCopy()} and
   * could get propagated in unexpected ways.
   */
  public final Object getPickle()
  {
    return pickle;
  }

  /**
   * As of Niagara 3.4, this method is deprecated.  Use
   * {@code makePickle(BFacets facets, Object pickle)} instead.
   *
   * Sets the pickle instance.  See the {@link #getPickle()}
   * method for details about how to use the pickle.
   *
   * @deprecated As of Niagara 3.4, use {@link #makePickle(BFacets facets, Object pickle)} instead.
   *
   */
  @Deprecated
  public final void setPickle(Object pickle)
  {
    this.pickle = pickle;
  }

////////////////////////////////////////////////////////////////
// Framework Support
////////////////////////////////////////////////////////////////

  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    if (x == Fw.SKIP_INTERN)
    {
      if (pickle == SKIP_INTERN_PICKLE)
        return Boolean.TRUE;

      // If this BFacet contains any keys that indicate to skip
      // interning, we check for that now and return Boolean.TRUE
      // if any are found
      for (int i = 0; i < noInternFacetKeys.size(); i++)
      {
        if (get(noInternFacetKeys.get(i)) != null)
          return Boolean.TRUE;
      }

      return null;
    }

    return super.fw(x, a, b, c, d);
  }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  /** The null instance. */
  public static final BFacets NULL = factory(new String[0], new BIDataValue[0]);

  /** Get default constant is {@code NULL} */
  public static final BFacets DEFAULT = NULL;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFacets.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  // If this pickle is detected on the BFacets instance, it will not attempt
  // to intern it
  public static final Object SKIP_INTERN_PICKLE = new Object();

  private String[] keys;
  private BIDataValue[] values;
  private String encodeStr;
  private Object pickle;

  private final static boolean internDisabled = AccessController.doPrivileged((PrivilegedAction<Boolean>)
    () -> Boolean.getBoolean("niagara.intern.disabled"));

  //Default list of facet key values, that if detected, would cause
  // interning to be skipped for the BFacets instance
  private final static String defaultExcludedFacetKeys = "sourceName;historyId;lastTimestamp;firstTimestamp;startTime;endTime;foxSessionId;historyCursorPreRec;historyCursorPostRec";

  // Stores a list of facet key values that shouldn't ever be interned
  private final static Array<String> noInternFacetKeys = new Array<>(String.class);
  static
  {
    if (!internDisabled)
    {
      String types = AccessController.doPrivileged((PrivilegedAction<String>) () ->
        System.getProperty("niagara.intern.excludedFacetKeys", defaultExcludedFacetKeys));
      if (types != null)
      {
        StringTokenizer tokenizer = new StringTokenizer(types, ";");
        while(tokenizer.hasMoreTokens())
        {
          try { noInternFacetKeys.add(tokenizer.nextToken()); }
          catch (Exception e) { e.printStackTrace(); }
        }
      }
    }
  }

  private static String internedKey(String key)
  {
    WeakReference<String> iKey = keyMap.get(key);
    if (iKey == null)
    {
      iKey = new WeakReference<>(key);
      keyMap.put(key, iKey);
    }

    return iKey.get();
  }

  private static Map<String, WeakReference<String>> keyMap = Collections.synchronizedMap(new WeakHashMap<>());
}
