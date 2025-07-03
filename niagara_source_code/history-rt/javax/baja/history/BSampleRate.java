/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BSimple;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BTypeSpec;
import javax.baja.util.Lexicon;

/**
 * BSampleRate defines the sample rate of a live history chart.
 *
 * @author    John Huffman
 * @creation  03 Sep 2007
 * @version   $Revision: 4$ $Date: 7/31/08 9:06:59 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BSampleRate
  extends BSimple
{
  /**
   * Private constructor.
   */
  private BSampleRate()
  {
  }

  /**
   * Make an auto instance.
   */
  public static BSampleRate makeAuto()
  {
    return( make( true, false, DEFAULT_VALUE ) );
  }

  /**
   * Make a cov instance.
   */
  public static BSampleRate makeCov()
  {
    return( make( false, true, DEFAULT_VALUE ) );
  }

  /**
   * Make an instance with a specified value.
   */
  public static BSampleRate make(BRelTime value)
  {
    return( make( false, false, value ) );
  }

  /**
   * Make an instance with a specified value.
   */
  public static BSampleRate make( boolean auto, boolean cov, BRelTime value )
  {
    if (value == null)
      value = DEFAULT_VALUE;
    BSampleRate result = new BSampleRate();
    result.auto = auto;
    result.cov = cov;
    result.value = value;
    return result;
  }

  /**
   * Is this value computed automatically?
   */
  public boolean isAuto()
  {
    return auto;
  }

  /**
   * Is this value computed on COV?
   */
  public boolean isCov()
  {
    return cov;
  }

  /**
   * Get the value.  If auto or cov, null is returned.
   */
  public BRelTime getValue()
  {
    if ( ( auto ) || ( cov ) )
    {
      return DEFAULT_VALUE;
    }
    else
    {
      if ( value == null )
        return DEFAULT_VALUE;
      return value;
    }
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * BSampleRate uses its encodeToString() value's hash code.
   *
   * @since Niagara 3.4
   */
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
   * Compare this instance to the specified instance for equality.
   */
  public boolean equals(Object o)
  {
    if (o instanceof BSampleRate) // issue 11846
    {
      BSampleRate other = (BSampleRate) o;
      if ( auto )
        return other.auto;
      if ( cov )
        return other.cov;
      else
        return value.equals( other.value );
    }

    return false;
  }

  /**
   * Encode this instance to the specified output.
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeBoolean(auto);
    if ( ( !auto ) && ( !cov ) )
    {
      value.getType().getTypeSpec().encode(out);
      value.encode(out);
    }
  }

  /**
   * Decode an instance from the specified input.
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    boolean auto = in.readBoolean();
    if (auto)
      return makeAuto();
    else
    {
      BTypeSpec type = (BTypeSpec)BTypeSpec.DEFAULT.decode(in);
      BRelTime value = (BRelTime)((BRelTime)type.getInstance()).decode(in);
      return make(value);
    }
  }

  /**
   * Encode this instance to a string.
   */
  @Override
  public String encodeToString()
    throws IOException
  {
    if (auto)
      return "auto";
    else if (cov)
      return "cov";
    else
      return "fixed," + value.getType().getTypeSpec().encodeToString() + "," + value.encodeToString();
  }

  /**
   * Decode an instance from the specified string.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    boolean auto = s.equals("auto");
    boolean cov = s.equals("cov");
    if (auto)
      return makeAuto();
    else if (cov)
      return makeCov();
    else
    {
      int comma = s.indexOf(',');
      s = s.substring( comma + 1 );
      comma = s.indexOf(',');
      BTypeSpec type = BTypeSpec.make(s.substring(0, comma));
      BRelTime value = (BRelTime)((BRelTime)type.getInstance()).decodeFromString(s.substring(comma+1));
      return make(value);
    }
  }

  /**
   * Get a string representation of this bound.
   */
  @Override
  public String toString(Context cx)
  {
    if (auto)
      return lex.getText("auto");
    if (cov)
      return lex.getText("cov");
    else
      return value.toString(cx);
  }

  /**
   * Get a string representation of this bound.
   */
  @Override
  public String toDebugString()
  {
    StringBuilder sb = new StringBuilder();

    sb.append( "auto = "  + auto + ", " );
    sb.append( "cov = "  + cov + ", " );
    sb.append( "value = " + value );

    return sb.toString();
  }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  public static final BSampleRate DEFAULT = BSampleRate.makeAuto();
  public static final BRelTime DEFAULT_VALUE = BRelTime.make( 5000L );

  public static final Type TYPE = Sys.loadType(BSampleRate.class);
  @Override
  public Type getType() { return TYPE; }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static Lexicon lex = Lexicon.make("history");

  private boolean auto = true;
  private boolean cov = false;
  private BRelTime value = DEFAULT_VALUE;
  private int hashCode = -1;
}
