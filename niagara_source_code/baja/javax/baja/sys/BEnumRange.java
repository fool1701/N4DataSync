/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.baja.data.BIDataValue;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.util.IntHashMap;
import javax.baja.util.Lexicon;

import com.tridium.sys.schema.EnumType;

/**
 * BEnumRange stores a range of ordinal/name pairs:
 * <pre>
 *   range     := [frozen "+"] dynamic [options]
 *   frozen    := BTypeSpec::typeSpec
 *   dynamic   := "{" + pairs "}"
 *   pairs     := pair [ "," pairs ]
 *   pair      := SlotPath::name "=" ordinal
 *   ordinal   := int
 *   options   := "?" baja::Facets
 *
 *   examples:
 *     {zero=0,one=1,two=2,three=3}
 *     baja:Weekday
 *     baja:Month+{Never=13}
 *     {zero=0,one=1,two=2,three=3}?lexicon=foo;
 * </pre>
 *
 * @author    Brian Frank
 * @creation  13 Dec 01
 * @version   $Revision: 25$ $Date: 4/23/08 11:54:57 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BEnumRange
  extends BSimple
  implements BIDataValue
{

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Create a BEnumRange for the specified frozen enum.
   */
  public static BEnumRange make(Type frozenType)
  {
    return make(frozenType, null, null, 0);
  }

  /**
   * Create an BEnumRange with no frozen range, and a
   * dynamic range where the ordinals are the tag array indices.
   */
  public static BEnumRange make(String[] tags)
  {
    int[] ordinals = new int[tags.length];
    for(int i=0; i<ordinals.length; ++i) ordinals[i] = i;
    return make(null, ordinals, tags, ordinals.length);
  }

  /**
   * Create a BEnumRange with no frozen range, and
   * the specified dynamic range.
   */
  public static BEnumRange make(int[] ordinals, String[] tags)
  {
    return make(null, ordinals, tags, ordinals.length);
  }

  /**
   * Create a BEnumRange with the specified frozen and dynamic range.
   */
  public static BEnumRange make(Type frozenType, int[] ordinals, String[] tags)
  {
    return make(frozenType, ordinals, tags, ordinals.length);
  }

  /**
   * Convenience for {@code make(frozenType, ordinals, tags, count, null)}.
   */
  public static BEnumRange make(Type frozenType, int[] ordinals, String[] tags, int count)
  {
    return make(frozenType, ordinals, tags, count, null);
  }

  /**
   * Create a BEnumRange with the specified frozen enum
   * range and given dynamic range.  The count parameter
   * specifies how many pairs are in the dynamic range arrays.
   */
  public static BEnumRange make(Type frozenType, int[] ordinals, String[] tags, int count, BFacets options)
  {
    // normalize params
    if (options == null)  options = BFacets.DEFAULT;
    if (ordinals == null) ordinals = noDynamic;
    if (tags == null)     tags = noTags;

    // cast frozen type to an EnumType
    EnumType frozen = null;
    if (frozenType instanceof EnumType) // issue 11846
      frozen = (EnumType)frozenType;
    else if (frozenType != null)
      throw new IllegalArgumentException("frozen type is not baja:FrozenEnum - " + frozenType);

    // optimization
    if (count == 0 && options.isNull())
    {
      if (frozen == null) return DEFAULT;
      if (frozen.getRange(false) != null) return frozen.getRange(false);
      return (BEnumRange)(new BEnumRange(frozen, noDynamic, noByOrdinal, noByTag, options).intern());
    }

    // build dynamic maps
    IntHashMap byOrdinal = new IntHashMap();
    HashMap<String,Integer> byTag = new HashMap<>();
    int dynaCnt=0;
    int[] dynaOrdinals = new int[count];
    for(int i=0; i<count; ++i)
    {
      int o = ordinals[i];
      String t = tags[i];

      // check name
      SlotPath.verifyValidName(t);

      // check against frozen
      if (frozen != null && frozen.isOrdinal(o))
      {
        //throw new IllegalArgumentException("Ordinal already in fixed enum: " + o);
        Logger.getLogger("sys").warning("Ignoring dynamic enumeration " + o + ":" + t + " in fixed enum " + frozenType.getTypeName());
        continue;
      }

      // check duplicate ordinal
      if (byOrdinal.get(o) != null)
        throw new IllegalArgumentException("Duplicate ordinal: " + t + "=" + o);

      // check duplicate tag
      if (byTag.get(t) != null)
        throw new IllegalArgumentException("Duplicate tag: " + t + "=" + o);

      // put into map
      byOrdinal.put(o, t);
      byTag.put(t, o);
      dynaOrdinals[dynaCnt++]=o;
    }

    // create safe dynamic ordinals copy
    int[] ordinalsCopy = new int[dynaCnt];
    System.arraycopy(dynaOrdinals, 0, ordinalsCopy, 0, dynaCnt);

    return (BEnumRange)(new BEnumRange(frozen, ordinalsCopy, byOrdinal, byTag, options).intern());
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BEnumRange(EnumType frozen, int[] dynamic,
                     IntHashMap byOrdinal, HashMap<String,Integer> byTag,
                     BFacets options)
  {
    this.frozen    = frozen;
    this.dynamic   = dynamic;
    this.byOrdinal = byOrdinal;
    this.byTag     = byTag;
    this.options   = options;
    this.lexicon   = options.gets("lexicon", null); // cache the lexicon option
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the ordinal list.
   */
  public int[] getOrdinals()
  {
    int[] frozen = noDynamic;
    if (this.frozen != null) frozen = this.frozen.getOrdinals();
    int[] total = new int[frozen.length + dynamic.length];
    System.arraycopy(frozen, 0, total, 0, frozen.length);
    System.arraycopy(dynamic, 0, total, frozen.length, dynamic.length);
    return total;
  }

  /**
   * Return if the ordinal identifier is in either the
   * frozen enum range or dynamic list of ordinal/tag pairs.
   */
  public boolean isOrdinal(int ordinal)
  {
    if (frozen != null && frozen.isOrdinal(ordinal)) return true;
    return byOrdinal.get(ordinal) != null;
  }

  /**
   * Get the String for the specified ordinal.
   */
  public String getTag(int ordinal)
  {
    String tag = (String)byOrdinal.get(ordinal);
    if (tag != null) return tag;

    if (frozen != null && frozen.isOrdinal(ordinal))
      return frozen.getTag(ordinal);

    return String.valueOf(ordinal);
  }

  /**
   * Get the display string for the specified ordinal.  If the
   * ordinal doesn't map to a specific tag then return the ordinal
   * as a decimal string.  If the ordinal maps to the frozen type,
   * see the behavior of BFrozenEnum.getDisplayTag().  Otherwise if
   * the options contains a "lexicon" entry then map to a lexicon,
   * where module is the string value of the lexicon option, and tag
   * is used as the key.  If no lexicon is provided return the
   * unescaped tag.
   */
  public String getDisplayTag(int ordinal, Context cx)
  {
    // frozen always trumps
    if (frozen != null && frozen.isOrdinal(ordinal))
      return frozen.get(ordinal).getDisplayTag(cx);

    // see if maps to dynamic range
    String tag = (String)byOrdinal.get(ordinal);
    if (tag != null)
    {
      // check lexicon
      if (lexicon != null)
      {
        Lexicon lex = Lexicon.make(lexicon, cx);
        String display = lex.get(tag, null);

        String unescapedTag = SlotPath.unescape(tag);
        if (display == null && !unescapedTag.equals(tag)) {
          display = lex.get(unescapedTag, null);
        }

        if (display != null) return display;
      }

      // return unescaped
      if (tag != null) return SlotPath.unescape(tag);
    }

    // not in frozen or dynamic, return string
    return String.valueOf(ordinal);
  }

  /**
   * Return if the specified tag maps within the range.
   */
  public boolean isTag(String tag)
  {
    if (frozen != null && frozen.isTag(tag))
      return true;
    return byTag.get(tag) != null;
  }

  /**
   * Get the ordinal associated with the specified tag.
   */
  public int tagToOrdinal(String tag)
  {
    if (frozen != null && frozen.isTag(tag))
      return frozen.tagToOrdinal(tag);
    Integer o = byTag.get(tag);
    if (o != null)
      return o;
    throw new InvalidEnumException(tag);
  }

  /**
   * Convenience for {@code get(ordinal, true)}.
   */
  public BEnum get(int ordinal)
  {
    return get(ordinal, true);
  }

  /**
   * Get the enum instance for the specified ordinal.  If
   * the ordinal is not found in the range and dynamic
   * is true then a new BDynamicEnum is created for the
   * specified ordinal using this range.  If dynamic is
   * false then an InvalidEnumException is thrown.
   */
  public BEnum get(int ordinal, boolean dynamic)
  {
    // special case for BBoolean
    if (this == BOOLEAN_RANGE)
      return ordinal == 0 ? BBoolean.FALSE : BBoolean.TRUE;

    if (frozen != null && frozen.isOrdinal(ordinal))
      return frozen.get(ordinal);

    if (dynamic)
      return BDynamicEnum.make(ordinal, this);

    throw new InvalidEnumException(ordinal);
  }

  /**
   * Get the enum instance for the specified tag.
   */
  public BEnum get(String tag)
  {
    // special case for BBoolean
    if (this == BOOLEAN_RANGE)
      return tag.equals("false") ? BBoolean.FALSE : BBoolean.TRUE;

    if (frozen != null && frozen.isTag(tag))
      return frozen.get(tag);

    if (byTag.get(tag) != null)
      return BDynamicEnum.make(byTag.get(tag).intValue(), this);

    throw new InvalidEnumException(tag);
  }

  /**
   * Return true if the give ordinal is a
   * valid ordinal in the frozen range.
   */
  public boolean isFrozenOrdinal(int ordinal)
  {
    return frozen != null && frozen.isOrdinal(ordinal);
  }

  /**
   * Return true if the give ordinal is a
   * valid ordinal in the dynamic range.
   */
  public boolean isDynamicOrdinal(int ordinal)
  {
    return byOrdinal.get(ordinal) != null;
  }

  /**
   * Get Type used for the BFrozenEnum range or null
   * if this range has no frozen ordinal/tag pairs.
   */
  public Type getFrozenType()
  {
    return frozen;
  }

  /**
   * Get the options for this range stored as a BFacets instance.
   */
  public BFacets getOptions()
  {
    return options;
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * A range is null if it contains no ordinal/tag pairs.
   */
  @Override
  public boolean isNull()
  {
    return frozen == null && dynamic.length == 0;
  }

  /**
   * BEnumRange uses its encodeToString() value's hash code.
   *
   * @since Niagara 3.4
   */
  @Override
  public int hashCode()
  {
    try
    {
      if (hashCode == -1)
        hashCode = encodeToString().hashCode();
      return hashCode;
    }
    catch(Exception e)
    {
      return System.identityHashCode(this);
    }
  }

  /**
   * Equality is based on both fixed and dynamic range.
   */
  @Override
  public boolean equals(Object o)
  {
    if (o instanceof BEnumRange)
    {
      BEnumRange x = (BEnumRange)o;

      // check frozen
      Type t1 = (frozen == null) ? null : frozen;
      Type t2 = (x.frozen == null) ? null : x.frozen;
      if (t1 != t2) return false;
      if (!options.equals(x.getOptions())) return false;
      // check dynamic
      return byOrdinal.equals(x.byOrdinal);
    }
    return false;
  }

  /**
   * BEnumRange is serialized using writeUTF()
   * of the string format.
   */
  @Override
  public final void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }

  /**
   * BEnumRange is serialized using readUTF()
   * of the string format.
   */
  @Override
  public final BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString(in.readUTF());
  }

  /**
   * Write the BEnumRange's value and range
   * according to the serialization BNF.
   */
  @Override
  public final String encodeToString()
    throws IOException
  {
    if (string == null)
    {
      StringBuilder s = new StringBuilder();

      if (frozen != null)
      {
        s.append(frozen.getModule().getModuleName())
         .append(':').append(frozen.getTypeName());
      }

      if (dynamic.length > 0 || frozen == null)
      {
        if (s.length() > 0) s.append('+');

        s.append('{');
        for(int i=0; i<dynamic.length; ++i)
        {
          int key = dynamic[i];
          String tag = (String)byOrdinal.get(key);
          if (i>0) s.append(',');
          s.append(tag).append('=').append(key);
        }
        s.append('}');
      }

      if (!options.isNull())
        s.append('?').append(options.encodeToString());

      string = s.toString();
    }
    return string;
  }

  /**
   * Encode according to grammar, except if this range
   * contains a fixed enum type, then the enum type is
   * expanded as part of the dynamic range.  This is useful
   * when sending this range into an environment which may
   * not have access to the Baja type system.
   */
  public String encodeToStringExpanded()
    throws IOException
  {
    if (frozen == null) return encodeToString();

    StringBuilder s = new StringBuilder();
    int[] ords = getOrdinals();

    s.append('{');
    for(int i=0; i<ords.length; ++i)
    {
      int ord = ords[i];
      String tag = getTag(ord);
      if (i>0) s.append(',');
      s.append(tag).append('=').append(ord);
    }
    s.append('}');

    return s.toString();
  }

  /**
   * Read the BEnumRange's value and range
   * according to the serialization BNF.
   */
  @Override
  public final BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      // check default
      if (s.equals("{}")) return DEFAULT;

      // split body from options (there can't be a question mark
      // anywhere until after the frozen type or dynamic {}
      BFacets options = null;
      int question = s.indexOf('?');
      if (question > 0)
      {
        options = BFacets.make(s.substring(question+1));
        s = s.substring(0, question);
      }

      // split dynamic and frozen tokens
      String[] split = splitFrozenDynamic(s);
      String frozenStr = split[0];
      String dynamicStr = split[1];

      // get frozen
      Type frozen = null;
      if (frozenStr != null)
      {
        try
        {
          frozen = Sys.getType(frozenStr);
        } catch (ModuleNotFoundException e) {
          // If it's a frozen type from a module we don't have, try to
          // fail gracefully by just not loading a range.  Wish we could
          // have access to the value doc decoder here, that would
          // make this problem potentially go away.
          return NULL;
        }
      }
      if (dynamicStr == null) return make(frozen, null, null, 0, options);

      // check for required braces on dynamic
      if (dynamicStr.charAt(0) != '{') throw new IOException();
      if (dynamicStr.charAt(dynamicStr.length()-1) != '}') throw new IOException();
      dynamicStr = dynamicStr.substring(1, dynamicStr.length()-1);

      // get dynamic
      int count = dynamicStr.length()/4+1;  // each pair at least 4 chars
      int[] ordinals = new int[count];
      String[] tags = new String[count];
      count = parseDynamic(dynamicStr, ordinals, tags);
      return make(frozen, ordinals, tags, count, options);
    }
    catch(IOException e)
    {
      throw e;
    }
    catch(Exception e)
    {
      e.printStackTrace();
      throw new IOException(s);
    }
  }

  static String[] splitFrozenDynamic(String s)
  {
    String frozen = null;
    String dynamic = null;
    int plus = s.indexOf('+');
    if (plus < 0)
    {
      if (s.startsWith("{"))
        dynamic = s;
      else
        frozen  = s;
    }
    else
    {
      if (s.startsWith("{"))
      {
        dynamic = s.substring(0, plus);
        frozen  = s.substring(plus+1);
      }
      else
      {
        frozen  = s.substring(0, plus);
        dynamic = s.substring(plus+1);
      }
    }
    return new String[] { frozen, dynamic };
  }

  static int parseDynamic(String s, int[] ordinals, String[] tags)
    throws Exception
  {
    int count = 0;
    StringTokenizer st = new StringTokenizer(s, "=,");
    while(st.hasMoreTokens())
    {
      tags[count] = st.nextToken();
      ordinals[count] = Integer.parseInt(st.nextToken());
      count++;
    }
    return count;
  }


  /**
   * Return this instance since it's already a data value.
   */
  @Override
  public BIDataValue toDataValue() { return this; }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  static int[] noDynamic = new int[0];
  static String[] noTags = new String[0];
  static IntHashMap noByOrdinal = new IntHashMap();
  static HashMap<String,Integer> noByTag = new HashMap<>();

  /** The null instance is an empty range. */
  public static final BEnumRange NULL = new BEnumRange(null, noDynamic, noByOrdinal, noByTag, BFacets.DEFAULT);

  /** The default is {@code NULL}. */
  public static final BEnumRange DEFAULT = NULL;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnumRange.class);

  /** Special instance used by BBoolean */
  static final BEnumRange BOOLEAN_RANGE = make(new String[] {"false", "true"});

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final EnumType frozen;      // frozen range or null
  private final int[] dynamic;        // array of dynamic ordinals
  private final IntHashMap byOrdinal; // dynamics keyed by ordinal
  private final HashMap<String,Integer> byTag;        // dynamics keyed by tag
  private String string;        // string encoding
  private final BFacets options;      // options
  private final String lexicon;       // options.lexicon
  BFacets asFacets;             // cache for BFacets.make()
  private int hashCode = -1;

}
