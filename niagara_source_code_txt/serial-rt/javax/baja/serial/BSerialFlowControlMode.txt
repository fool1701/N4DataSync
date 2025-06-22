/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.serial;

import java.io.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;

/**
 * BSerialFlowControlMode represents the flow control settings
 * for a comm port.
 * <p>
 *
 * @author    Scott Hoye
 * @creation  30 Sep 02
 * @version   $Revision: 3$ $Date: 9/30/08 5:09:02 PM EDT$
 * @since     Niagara 3.0 serial 1.0
 */

@NiagaraType
@NoSlotomatic
public final class BSerialFlowControlMode
  extends BBitString
{
  public static final BSerialFlowControlMode none = new BSerialFlowControlMode(0);
  public static final BSerialFlowControlMode DEFAULT = none;
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.serial.BSerialFlowControlMode(2979906276)1.0$ @*/
/* Generated Fri Sep 17 11:17:08 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSerialFlowControlMode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////
 
  /**
   * Factory method for creating flow control 
   * status from bit mask.
   */
  public static BSerialFlowControlMode make(int bits)
  {
    if (bits == 0) return none;
    return new BSerialFlowControlMode(bits);
  }
  
  /**
   * Returns flow control status instance equal to the 
   * given status with the given bit set or cleared
   */
  public static BSerialFlowControlMode make(BSerialFlowControlMode orig, int bit, boolean bitState)
  {
    int newBits = bitState ? (orig.bits | bit) : (orig.bits & ~bit);

    if (newBits == 0) return none;
    if (orig.bits == newBits) return orig;
    return new BSerialFlowControlMode(newBits);
  }

  /** Convenience for <code>make(orig, RTS_CTS_ON_INPUT, state)</code>. */
  public static BSerialFlowControlMode makeRtsCtsOnInput(BSerialFlowControlMode orig, boolean state)
  {
    return make(orig, RTS_CTS_ON_INPUT, state);
  }

  /** Convenience for <code>make(orig, RTS_CTS_ON_OUTPUT, state)</code>. */
  public static BSerialFlowControlMode makeRtsCtsOnOutput(BSerialFlowControlMode orig, boolean state)
  { 
    return make(orig, RTS_CTS_ON_OUTPUT, state); 
  }

  /** Convenience for <code>make(orig, XON_XOFF_ON_INPUT, state)</code>. */
  public static BSerialFlowControlMode makeXonXoffOnInput(BSerialFlowControlMode orig, boolean state)
  { 
    return make(orig, XON_XOFF_ON_INPUT, state);
  }

  /** Convenience for <code>make(orig, XON_XOFF_ON_OUTPUT, state)</code>. */
  public static BSerialFlowControlMode makeXonXoffOnOutput(BSerialFlowControlMode orig, boolean state)
  {
    return make(orig, XON_XOFF_ON_OUTPUT, state);
  }

  /**
   * Private constructor.
   */
  private BSerialFlowControlMode(int bits)
  {
    this.bits = bits;
  }

////////////////////////////////////////////////////////////////
// Bit Getters
////////////////////////////////////////////////////////////////

  /**
   * Get the flow control status bit mask.
   */
  public int getBits() { return bits; }

  
  /**
   * Return true if the bits are equal to 0.
   */
  public boolean isNone() { return bits == 0; }

  /**
   * Returning true indicates that the RtsCtsOnInput
   * flow control mode is enabled.
   */
  public boolean isRtsCtsOnInput() { return (bits & RTS_CTS_ON_INPUT) != 0; }
  
  /**
   * Returning true indicates that the RtsCtsOnOutput
   * flow control mode is enabled.
   */
  public boolean isRtsCtsOnOutput() { return (bits & RTS_CTS_ON_OUTPUT) != 0; }
  
  /**
   * Returning true indicates that the XonXoffOnInput
   * flow control mode is enabled.
   */
  public boolean isXonXoffOnInput() { return (bits & XON_XOFF_ON_INPUT) != 0; }
  
  /**
   * Returning true indicates that the XonXoffOnOutput
   * flow control mode is enabled.
   */
  public boolean isXonXoffOnOutput() { return (bits & XON_XOFF_ON_OUTPUT) != 0; }
  
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
   * The empty tag is "none".
   */
  public String getEmptyTag()
  {
    return "none";
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * BSerialFlowControlMode hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    return bits;
  }
  
  /**
   * Equality is based on bitmask equality.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BSerialFlowControlMode)
      return ((BSerialFlowControlMode)obj).bits == bits;
    return false;
  }
      
  /**
   * To string.
   */
  public String toString(Context context)
  {
    if (bits == 0) return "{none}";
    StringBuilder s = new StringBuilder();
    s.append('{');
    if (isRtsCtsOnInput()) s.append("RtsCtsOnInput,");
    if (isRtsCtsOnOutput()) s.append("RtsCtsOnOutput,");
    if (isXonXoffOnInput()) s.append("XonXoffOnInput,");
    if (isXonXoffOnOutput()) s.append("XonXoffOnOutput,");
    
    s.setCharAt(s.length()-1, '}');
    return s.toString();
  }
  
  /**
   * Binary encoding is writeInt(bits).
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeInt(bits);
  }
  
  /**
   * Binary decoding is make(readInt()).
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return make( in.readInt() );
  }

  /**
   * Text format is the bit mask in hex.
   */
  public String encodeToString()
    throws IOException
  {
    return Integer.toHexString(bits);
  }

  /**
   * Read the bit mask as hex.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      return make( Integer.parseInt(s, 16) );
    }
    catch(Exception e)
    {
      throw new IOException("Invalid bits: " + s);
    }
  }

  /**
   * Get the BTypeSpec for this type's BPlugin.
   */
//  public BTypeSpec getPluginTypeSpec()
//  {
//    return pluginType;
//  }
//  static final BTypeSpec pluginType = BTypeSpec.make("serialdriver", "FlowControlPlugin");
  
////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////  
 

  public static final int RTS_CTS_ON_INPUT   = 1;
  public static final int RTS_CTS_ON_OUTPUT  = 2;
  public static final int XON_XOFF_ON_INPUT  = 4;
  public static final int XON_XOFF_ON_OUTPUT = 8;

  public static final BSerialFlowControlMode rtsCtsOnInput = new BSerialFlowControlMode(RTS_CTS_ON_INPUT);  
  public static final BSerialFlowControlMode rtsCtsOnOutput = new BSerialFlowControlMode(RTS_CTS_ON_OUTPUT);  
  public static final BSerialFlowControlMode xonXoffOnInput    = new BSerialFlowControlMode(XON_XOFF_ON_INPUT);  
  public static final BSerialFlowControlMode xonXoffOnOutput  = new BSerialFlowControlMode(XON_XOFF_ON_OUTPUT);

  private static Support support = new Support(DEFAULT);
  static
  {
    support.add(RTS_CTS_ON_INPUT, "RtsCtsOnInput");
    support.add(RTS_CTS_ON_OUTPUT, "RtsCtsOnOutput");
    support.add(XON_XOFF_ON_INPUT, "XonXoffOnInput");
    support.add(XON_XOFF_ON_OUTPUT, "XonXoffOnOutput");
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private int bits;
  
}
