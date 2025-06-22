/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;

/**
 * BBacnetNull represents a null value in a Bacnet property.
 *
 * @author Craig Gemmill
 * @version $Revision: 8$ $Date: 12/19/01 4:35:52 PM$
 * @creation 09 Aug 01
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NoSlotomatic
public final class BBacnetNull
  extends BSimple
{
  /**
   * Constructor.
   */
  private BBacnetNull()
  {
  }


////////////////////////////////////////////////////////////////
//  BSimple
////////////////////////////////////////////////////////////////

  /**
   * Some types of BObjects are used to indicate
   * a null value.  This method allows those types to
   * declare their null status by overriding this common
   * method.  The default is to return false.
   *
   * @return true because this represents a null value.
   */
  public boolean isNull()
  {
    return true;
  }

  /**
   */
  public boolean equals(Object obj)
  {
//    return this == obj; // this doesn't seem to work all the time?
    return obj instanceof BBacnetNull;
  }

  /**
   * Encode the simple type using a binary format
   * that can be translated using decode.
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeByte(0);
  }

  /**
   * Decode the simple using the same binary format
   * that was written using encode, and return the new
   * instance.  Under no circumstances should this
   * instance be modified.
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    in.readByte();
    return DEFAULT;
  }

  /**
   * Encode the simple using a String format
   * that can be translated using decodeFromString.
   */
  public String encodeToString()
    throws IOException
  {
    return NULL_STR;
  }

  /**
   * Decode the simple using the same String format
   * that was written using encodeToString, and return
   * the new instance.  Under no circumstances should
   * this instance me modified.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    if (!s.equals(NULL_STR))
      throw new IOException(s);
    return DEFAULT;
  }


////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * To String.
   */
  public String toString(Context context)
  {
    return NULL_STR;
  }

  /**
   * Hash code.
   * The hash code for a BBacnetNull is its unique id.
   */
  public int hashCode()
  {
    return 0;
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final BBacnetNull DEFAULT = new BBacnetNull();
  public static final String NULL_STR = "NULL";

  public Type getType()
  {
    return TYPE;
  }

  public static final Type TYPE = Sys.loadType(BBacnetNull.class);

}