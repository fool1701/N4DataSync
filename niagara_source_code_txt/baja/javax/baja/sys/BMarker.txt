/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import javax.baja.data.BIDataValue;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;

/**
 * A marker value is used primarily with the tagging API in order to represent the
 * presence of a named tag that has no meaningful value. That is, the tag itself is
 * sufficient to convey meaning.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
@NiagaraType
@NoSlotomatic
public final class BMarker
  extends BSimple
  implements BIDataValue, BIBoolean
{
  /** Required for a BSimple. A BMarker has no other value than this. */
  public static final BMarker DEFAULT = new BMarker();

  /** More friendly name for a Marker. Equivalent to {@link #DEFAULT}. */
  public static final BMarker MARKER = DEFAULT;
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $javax.baja.sys.BMarker(2979906276)1.0$ @*/
/* Generated Fri Apr 10 10:49:46 EDT 2015 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMarker.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private static final String ENCODING_STR = "M";

  private BMarker()
  {
  }

  @Override
  public boolean equals(Object obj)
  {
    return DEFAULT == obj;
  }

  @Override
  public int hashCode()
  {
    return ENCODING_STR.hashCode();
  }

  @Override
  public void encode(DataOutput encoder) throws IOException
  {
    encoder.writeUTF(encodeToString());
  }

  @Override
  public BObject decode(DataInput decoder) throws IOException
  {
    return decodeFromString(decoder.readUTF());
  }

  @Override
  public String encodeToString()
  {
    return ENCODING_STR;
  }

  @Override
  public BObject decodeFromString(String s) throws IOException
  {
    if (ENCODING_STR.equals(s))
    {
      return DEFAULT;
    }
    throw new IOException(String.format("Invalid encoding for BMarker: %s", s));
  }

  @Override
  public boolean getBoolean()
  {
    return true;
  }

  @Override
  public BFacets getBooleanFacets()
  {
    return BBoolean.TRUE.getBooleanFacets();
  }

  @Override
  public BEnum getEnum()
  {
    return BBoolean.TRUE.getEnum();
  }

  @Override
  public BFacets getEnumFacets()
  {
    return BBoolean.TRUE.getEnumFacets();
  }

  @Override
  public BIDataValue toDataValue()
  {
    return this;
  }

  @Override
  public String toString(Context context)
  {
    return getTypeDisplayName(context);
  }
}
