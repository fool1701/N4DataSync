/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.status;

import java.io.*;
import java.util.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;
import javax.baja.data.*;
import javax.baja.util.*;

/**
 * BStatus provides a bit mask for various standardized 
 * status flags in the Baja control architecture.  Plus
 * it provides for arbitrary extensions using BFacets.
 *
 * @author    Brian Frank
 * @creation  11 Apr 00
 * @version   $Revision: 41$ $Date: 1/25/08 4:04:37 PM EST$
 * @since     Baja 1.0
 */

@NiagaraType
@NoSlotomatic
public final class BStatus
  extends BBitString
  implements BIStatus
{ 

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////
 
  /**
   * Factory method for creating status from bit mask 
   * with null facets.
   */
  public static BStatus make(int bits)
  {
    return make(bits, BFacets.NULL);
  }

  /**
   * Factory method for creating status from an existing
   * status using the specified bit mask.
   */
  public static BStatus make(BStatus orig, int bits)
  {
    if (orig.bits == bits) return orig;
    return make(bits, orig.facets);
  }

  /**
   * Factory method for creating status from bit mask 
   * with null facets.
   */
  public static BStatus make(int bits, BFacets facets)
  {
    // try to intern to a common instance
    if (((facets == null) || facets.isNull()) && (bits < 256))
    { // First check lazy loaded null facets status table
      if (nullLookupTable[bits] == null)
        nullLookupTable[bits] = new BStatus(bits, BFacets.NULL);
      return nullLookupTable[bits];
    }
    
    // Next, return an interned instance
    return (BStatus)(new BStatus(bits, facets).intern());
  }
  
  /**
   * Returns status instance equal to the given status 
   * with the given bit set or cleared
   */
  public static BStatus make(BStatus orig, int bit, boolean bitState)
  {
    int newBits = bitState ? (orig.bits | bit) : (orig.bits & ~bit);

    if (newBits == 0 && orig.facets.isNull()) return ok;
    if (orig.bits == newBits) return orig;
    return make(newBits, orig.facets);
  }

  /** Convenience for <code>make(orig, DISABLED, state)</code>. */
  public static BStatus makeDisabled(BStatus orig, boolean state)
  { 
    return make(orig, DISABLED, state);
  }

  /** Convenience for <code>make(orig, FAULT, state)</code>. */
  public static BStatus makeFault(BStatus orig, boolean state)
  {  
    return make(orig, FAULT, state);
  }

  /** Convenience for <code>make(orig, DOWN, state)</code>. */
  public static BStatus makeDown(BStatus orig, boolean state)
  {
    return make(orig, DOWN, state);
  }

  /** Convenience for <code>make(orig, ALARM, state)</code>. */
  public static BStatus makeAlarm(BStatus orig, boolean state)
  {
    return make(orig, ALARM, state);
  }

  /** Convenience for <code>make(orig, STALE, state)</code>. */
  public static BStatus makeStale(BStatus orig, boolean state)
  {
    return make(orig, STALE, state);
  }

  /** Convenience for <code>make(orig, OVERRIDDEN, state)</code>. */
  public static BStatus makeOverridden(BStatus orig, boolean state)
  { 
    return make(orig, OVERRIDDEN, state);
  }

  /** Convenience for <code>make(orig, NULL, state)</code>. */
  public static BStatus makeNull(BStatus orig, boolean state)
  {
    return make(orig, NULL, state);
  }

  /** Convenience for <code>make(orig, UNACKED_ALARM, state)</code>. */
  public static BStatus makeUnackedAlarm(BStatus orig, boolean state)
  {
    return make(orig, UNACKED_ALARM, state);
  }
  
  /**
   * Make a status from the original value, but with 
   * the specified facet name/value pair.
   */
  public static BStatus make(BStatus orig, String name, BIDataValue value)
  {
    BFacets facets = BFacets.make(orig.facets, name, value);
    if (facets == orig.facets) return orig;
    return make(orig.bits, facets);
  }

  /** Convenience for <code>make(orig, name, BBoolean.make(value))</code>. */
  public static BStatus make(BStatus orig, String name, boolean value)
  {
    return make(orig, name, BBoolean.make(value));
  }

  /** Convenience for <code>make(orig, name, BInteger.make(value))</code>. */
  public static BStatus make(BStatus orig, String name, int value)
  {
    return make(orig, name, BInteger.make(value));
  }

  /** Convenience for <code>make(orig, name, BFloat.make(value))</code>. */
  public static BStatus make(BStatus orig, String name, float value)
  {
    return make(orig, name, BFloat.make(value));
  }

  /** Convenience for <code>make(orig, name, BString.make(value))</code>. */
  public static BStatus make(BStatus orig, String name, String value)
  {
    return make(orig, name, BString.make(value));
  }
  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BStatus(int bits, BFacets facets)
  {
    this.bits = bits;
    this.facets = facets != null ? facets : BFacets.NULL;
  }

////////////////////////////////////////////////////////////////
// BIStatus
////////////////////////////////////////////////////////////////  

  /**
   * Return this.
   */
  public BStatus getStatus()
  {
    return this;
  }

////////////////////////////////////////////////////////////////
// Facets
////////////////////////////////////////////////////////////////  

  /**
   * Get additional name-value pairs associated with this
   * status.  Or return BFacets.NULL if none are configured.
   */
  public BFacets getFacets()
  {
    return facets;
  }
  
  /**
   * Return <code>getFacets().get(name)</code>.
   */
  public BObject get(String name)
  {
    return facets.get(name);
  }

  /**
   * Return <code>getFacets().getb(name, def)</code>.
   */
  public boolean getb(String name, boolean def)
  {
    return facets.getb(name, def);
  }

  /**
   * Return <code>getFacets().geti(name, def)</code>.
   */
  public int geti(String name, int def)
  {              
    return facets.geti(name, def);
  }

  /**
   * Return <code>getFacets().getl(name, def)</code>.
   */
  public long getl(String name, long def)
  {              
    return facets.getl(name, def);
  }

  /**
   * Return <code>getFacets().getf(name, def)</code>.
   */
  public float getf(String name, float def)
  {
    return facets.getf(name, def);
  }

  /**
   * Return <code>getFacets().getd(name, def)</code>.
   */
  public double getd(String name, double def)
  {
    return facets.getd(name, def);
  }

  /**
   * Return <code>getFacets().gets(name, def)</code>.
   */
  public String gets(String name, String def)
  {
    return facets.gets(name, def);
  }

////////////////////////////////////////////////////////////////
// Bit Getters
////////////////////////////////////////////////////////////////

  /**
   * Get the control status bit mask.
   */
  public int getBits() { return bits; }
  
  /**
   * Returns true if the associated value is not 
   * disabled, fault, down, stale or null.
   */
  public boolean isValid() 
  { 
    return ( ((bits & DISABLED) == 0) &&
             ((bits & FAULT) == 0) && 
             ((bits & DOWN)  == 0) && 
             ((bits & STALE) == 0) && 
             ((bits & NULL)  == 0) );  
  }
  
  /**
   * Return true if the bits are equal to 0.
   */
  public boolean isOk() { return bits == 0; }

  /**
   * Disabled indicates that the user has 
   * manually set the component out of service.
   */
  public boolean isDisabled() { return (bits & DISABLED) != 0; }
  
  /**
   * Fault indicates a hardware, software or 
   * configuration problem. 
   */
  public boolean isFault() { return (bits & FAULT) != 0; }

  /**
   * Down indicates a communications problem.
   */
  public boolean isDown() { return (bits & DOWN) != 0; }

  /**
   * In alarm indicates that point is currently in
   * its configured alarm state.
   */
  public boolean isAlarm() { return (bits & ALARM) != 0; }

  /**
   * Stale indicates that a period of time has elapsed which
   * renders the current value untrustworthy.
   */
  public boolean isStale() { return (bits & STALE) != 0; }

  /**
   * Overridden indicates that the user has manually overridden 
   * the component.
   */
  public boolean isOverridden()   { return (bits & OVERRIDDEN) != 0; }
  
  /**
   * The null flag indicates that the associated 
   * value should not be used.  It represents a 
   * don't care condition.  
   * <p>
   * It is utilized in combination with priority 
   * arrays so that points may take or release 
   * control of their level in a priority array 
   * based on their current state.
   * <p>
   * It is also utilized in math, logic and other
   * application blocks that take a variable number
   * of inputs.  Any input with the null flag set
   * is ignored.  
   */
  public boolean isNull() { return (bits & NULL) != 0; }

  /**
   * The unackedAlarm flag indicates that the 
   * associated point has an unacked alarm.  This
   * is different than then inAlarm bit.
   */
  public boolean isUnackedAlarm() { return (bits & UNACKED_ALARM) != 0; }

////////////////////////////////////////////////////////////////
// BBitString
////////////////////////////////////////////////////////////////

  /**
   * Return if the bit specified by the given ordinal is set.
   */
  public boolean getBit(int ordinal)
  {
    return (bits & ordinal) != 0;
  }

  /**
   * Return if the bit specified by the given tag is set.
   */
  public boolean getBit(String tag)
  {
    return getBit(tagToOrdinal(tag));
  }

  /**
   * Get an array enumerating the list of all known
   * ordinal values of this bitstring instance.
   */
  public int[] getOrdinals()
  {
    return support.getOrdinals();
  }
  
  /**
   * Is the specified ordinal value included in this
   * bitstring's range of valid ordinals.
   */
  public boolean isOrdinal(int ordinal)
  {
    return support.isOrdinal(ordinal);
  }
  
  /**
   * Get the tag identifier for an ordinal value.
   */
  public String getTag(int ordinal)
  {
    return support.getTag(ordinal);
  }

  /**
   * Get the user readable tag for an ordinal value.
   */
  public String getDisplayTag(int ordinal, Context cx)
  {
    return support.getDisplayTag(ordinal, cx);
  }
  
  /**
   * Get the BBitString instance which maps to the 
   * specified set of ordinal values.
   */
  public BBitString getInstance(int[] ordinals)
  {
    int mask = 0;
    for(int i=0; i<ordinals.length; ++i) mask |= ordinals[i];
    return make(mask);
  }
  
  /**
   * Return true if the specified tag is contained by the range.
   */
  public boolean isTag(String tag)
  {
    return support.isTag(tag);
  }
  
  /**
   * Get the ordinal associated with the specified tag.
   */
  public int tagToOrdinal(String tag)
  {
    return support.tagToOrdinal(tag);
  }

  /**
   * Empty is the same as ok (bits are 0).
   */
  public boolean isEmpty()
  {
    return bits == 0;
  }

  /**
   * The empty tag is "ok".
   */
  public String getEmptyTag()
  {
    return "ok";
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * Generate a hash code based on the bits/facets combination.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    if (hashCode == -1) 
      hashCode = (bits << 13) ^ facets.hashCode();
    return hashCode;
  }
  
  /**
   * Equality is based on bitmask and facets equality.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BStatus)
    {
      BStatus x = (BStatus)obj;
      return x.bits == bits && x.facets.equals(facets);
    }
    return false;
  }

  /**
   * Get a string of just the flags which are set or 
   * return ok if not set.
   */
  public String flagsToString(Context cx)
  {
    if (bits == 0) return Lexicon.make(Sys.getBajaModule(), cx).getText("Status.ok");
    
    StringBuilder s = new StringBuilder();
    if (isDisabled())     s.append(getDisplayTag(DISABLED, cx)).append(',');
    if (isFault())        s.append(getDisplayTag(FAULT, cx)).append(',');
    if (isDown())         s.append(getDisplayTag(DOWN, cx)).append(',');
    if (isAlarm())        s.append(getDisplayTag(ALARM, cx)).append(',');
    if (isStale())        s.append(getDisplayTag(STALE, cx)).append(',');
    if (isOverridden())   s.append(getDisplayTag(OVERRIDDEN, cx)).append(',');
    if (isNull())         s.append(getDisplayTag(NULL, cx)).append(',');
    if (isUnackedAlarm()) s.append(getDisplayTag(UNACKED_ALARM, cx)).append(',');
    s.setLength(s.length()-1);
        
    return s.toString();
  }
      
  /**
   * To string.
   */
  public String toString(Context context)
  {                 
    StringBuilder s = new StringBuilder();
    
    s.append('{').append(flagsToString(context)).append('}');

    if (!facets.isNull())
    {   
      String[] keys = facets.list();
      for(int i=0; i<keys.length; ++i)
      {
        String key = keys[i];
        BObject val = facets.get(key);
        if (key.equals("activeLevel"))
          s.append(" @ ").append(val);
        else if (val == BBoolean.TRUE)
          s.append(' ').append(key);
        else
          s.append(' ').append(key).append('=').append(val);
      }
    }
    
    return s.toString();
  }
  
  /**
   * Binary encoding is writeInt(bits) writeUTF(facets).
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeInt(bits);
    out.writeUTF(facets.encodeToString());
  }
  
  /**
   * Binary decoding bits = readInt(); facets = readUTF()
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return make(in.readInt(), (BFacets)BFacets.NULL.decodeFromString(in.readUTF()));
  }

  /**
   * Text format is {@code "<bits in hex>[;<facets>]"}
   */
  public String encodeToString()
    throws IOException
  {
    String s = Integer.toHexString(bits);
    if (facets.isNull()) return s;
    else return s + ';' + facets.encodeToString();
  }

  /**
   * Read from text format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      int semi = s.indexOf(';');
      if (semi < 0)
      {
        return make( Integer.parseInt(s, 16) );
      }
      else
      {
        int bits = Integer.parseInt(s.substring(0, semi), 16);
        BFacets facets = (BFacets)BFacets.NULL.decodeFromString(s.substring(semi+1));
        return make(bits, facets);
      }
    }
    catch(Exception e)
    {
      throw new IOException("Invalid bits: " + s);
    }
  }

////////////////////////////////////////////////////////////////
// Colors
////////////////////////////////////////////////////////////////

  /**
   * If object implements BIStatus then return the appriopate
   * foreground color as a gx:Color, otherwise return the default.
   */
  public BSimple getForegroundColor(BSimple defaultColor)
  {
    if (isDisabled()) return disabledFg;
    if (isFault()) return faultFg;
    if (isDown()) return downFg;
    if (isAlarm()) return alarmFg;
    if (isStale()) return staleFg;
    if (isOverridden()) return overriddenFg;
    return defaultColor;
  }

  /**
   * If object implements BIStatus then return the appriopate
   * background color as a gx:Color, otherwise return the default.
   */
  public BSimple getBackgroundColor(BSimple defaultColor)
  {
    if (isDisabled()) return disabledBg;
    if (isFault()) return faultBg;
    if (isDown()) return downBg;
    if (isAlarm()) return alarmBg;
    if (isStale()) return staleBg;
    if (isOverridden()) return overriddenBg;
    return defaultColor;
  }
    
  /** Foreground gx:BColor for disabled state */
  public static BSimple disabledFg;
  /** Background gx:BColor for disabled state */
  public static BSimple disabledBg;

  /** Foreground gx:BColor for fault state */
  public static BSimple faultFg;
  /** Background gx:BColor for fault state */
  public static BSimple faultBg;
  
  /** Foreground gx:BColor for down state */
  public static BSimple downFg;
  /** Background gx:BColor for down state */
  public static BSimple downBg;

  /** Foreground gx:BColor for alarm state */
  public static BSimple alarmFg;
  /** Background gx:BColor for alarm state */
  public static BSimple alarmBg;

  /** Foreground gx:BColor for stale state */
  public static BSimple staleFg;
  /** Background gx:BColor for stale state */
  public static BSimple staleBg;
  
  /** Foreground gx:BColor for overridden state */
  public static BSimple overriddenFg;
  /** Background gx:BColor for overridden state */
  public static BSimple overriddenBg;
  
  static
  {
    try
    {
      Lexicon lex = Lexicon.make("baja");
      BSimple proto = (BSimple)Sys.getType("gx:Color").getInstance();
      
      disabledFg     = color(lex, proto, "Status.disabled.fg");
      disabledBg     = color(lex, proto, "Status.disabled.bg");
      faultFg        = color(lex, proto, "Status.fault.fg");
      faultBg        = color(lex, proto, "Status.fault.bg");
      downFg         = color(lex, proto, "Status.down.fg");
      downBg         = color(lex, proto, "Status.down.bg");
      alarmFg        = color(lex, proto, "Status.alarm.fg");
      alarmBg        = color(lex, proto, "Status.alarm.bg");
      staleFg        = color(lex, proto, "Status.stale.fg");
      staleBg        = color(lex, proto, "Status.stale.bg");
      overriddenFg   = color(lex, proto, "Status.overridden.fg");
      overriddenBg   = color(lex, proto, "Status.overridden.bg");
    }
    catch(TypeNotFoundException e)
    {        
      // ignore
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  static BSimple color(Lexicon lex, BSimple proto, String key)
    throws IOException
  {
    String val = lex.get(key, null);
    if (val == null) return null;
    return (BSimple)proto.decodeFromString(val);
  }
  
////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////
  
  // Lookup map used for interning BStatus
  private static BStatus[] nullLookupTable = new BStatus[256]; // lazy loaded

  /** Bit for disabled 0x0001 */
  public static final int DISABLED       = 0x0001;
  /** Bit for fault 0x0002 */
  public static final int FAULT          = 0x0002;
  /** Bit for down 0x0004 */
  public static final int DOWN           = 0x0004;
  /** Bit for alarm 0x0008 */
  public static final int ALARM          = 0x0008;
  /** Bit for stale 0x0010 */
  public static final int STALE          = 0x0010;
  /** Bit for overridden 0x0020 */
  public static final int OVERRIDDEN     = 0x0020;
  /** Bit for null 0x0040 */
  public static final int NULL           = 0x0040;
  /** Bit for unackedAlarm 0x0080 */
  public static final int UNACKED_ALARM  = 0x0080;
  
  public static final BStatus ok            = make(0, BFacets.NULL);
  public static final BStatus disabled      = make(DISABLED, BFacets.NULL);
  public static final BStatus fault         = make(FAULT, BFacets.NULL);
  public static final BStatus down          = make(DOWN, BFacets.NULL);
  public static final BStatus alarm         = make(ALARM, BFacets.NULL);
  public static final BStatus stale         = make(STALE, BFacets.NULL);
  public static final BStatus overridden    = make(OVERRIDDEN, BFacets.NULL);
  public static final BStatus nullStatus    = make(NULL, BFacets.NULL);
  public static final BStatus unackedAlarm  = make(UNACKED_ALARM, BFacets.NULL);
  
  public static final BStatus DEFAULT = ok;

  private static Support support = new Support(DEFAULT);
  static
  {
    support.add(DISABLED,       "disabled");
    support.add(FAULT,          "fault");
    support.add(DOWN,           "down");
    support.add(ALARM,          "alarm");
    support.add(STALE,          "stale");
    support.add(OVERRIDDEN,     "overridden");
    support.add(NULL,           "null");
    support.add(UNACKED_ALARM,  "unackedAlarm");
  }
  
  /** Used to indicate the active priority of a 16 level priority array [BEnum of BPriorityLevel] */
  public static final String ACTIVE_LEVEL = "activeLevel";

////////////////////////////////////////////////////////////////
// Type 
////////////////////////////////////////////////////////////////  

  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatus.class);
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private int bits;
  private BFacets facets;
  private int hashCode = -1;
  
}
