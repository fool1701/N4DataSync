/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.BacnetConst;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

/**
 * BBacnetOctetString represents an octet string (byte array).
 *
 * @author Craig Gemmill
 * @version $Revision: 2$ $Date: 11/20/01 9:19:59 AM$
 * @creation 26 Oct 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BBacnetOctetString
  extends BSimple
{
  /**
   * Private constructor.
   *
   * @param arr byte array to use.
   */
  private BBacnetOctetString(byte[] arr)
  {
    if (arr == null)
    {
      this.arr = null;
    }
    else
    {
      this.arr = new byte[arr.length];
      System.arraycopy(arr, 0, this.arr, 0, arr.length);
    }
    getHashCode();
  }

  /**
   * Factory method.
   * This also handles maintenance of a pool of BBacnetOctetStrings.
   * The convention is that the first element in the pool is
   * the null entry, for faster reference of this common instance.
   *
   * @param arr byte array to use.
   */
  public static synchronized BBacnetOctetString make(byte[] arr)
  {
    return new BBacnetOctetString(arr);
  }

////////////////////////////////////////////////////////////////
//  BObject
////////////////////////////////////////////////////////////////

  /**
   * Some types of BObjects are used to indicate
   * a null value.  This method allows those types to
   * declare their null status by overriding this common
   * method.  The default is to return false.
   *
   * @return true if this octet string contains a null array.
   */
  public boolean isNull()
  {
    return arr == null;
  }

////////////////////////////////////////////////////////////////
//  BSimple
////////////////////////////////////////////////////////////////

  /**
   * Returns true if the byte arrays are exactly equal.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BBacnetOctetString)
    {
      BBacnetOctetString comp = (BBacnetOctetString)obj;
      return Arrays.equals(comp.arr, arr);
    }
    return false;
  }

  /**
   * Encode the simple type using a binary format
   * that can be translated using decode.
   */
  public void encode(DataOutput out)
    throws IOException
  {
    if (arr == null)
      out.writeInt(0);
    else
    {
      out.writeInt(arr.length);
      out.write(arr, 0, arr.length);
    }
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
    byte[] a = null;
    int len = -1;
    try
    {
      len = in.readInt();
      if (len != 0)
      {
        a = new byte[len];
        in.readFully(a, 0, len);
      }
      return make(a);
    }
    catch (OutOfMemoryError e)
    {
      throw new IOException("Invalid octet string length: " + len);
    }
  }

  /**
   * Encode the simple using a String format
   * that can be translated using decodeFromString.
   */
  public String encodeToString()
    throws IOException
  {
    return encodeToString(arr);
  }

  /**
   * Encode a byte array using the String format
   * for the BACnet octet string simple.
   * @since Niagara 4.11
   */
  public static String encodeToString(byte[] arr)
  {
    if ((arr == null) || (arr.length == 0))
      return "null";
    else
    {
      StringBuilder sb = new StringBuilder();
      sb.append(hexByte(arr[0]));
      for (int i = 1; i < arr.length; i++)
      {
        sb.append(" ");
        sb.append(hexByte(arr[i]));
      }
      return sb.toString();
    }
  }

  private static String hexByte(byte b)
  {
    String s = TextUtil.byteToHexString(b);
    return (s.length() == 1) ? "0" + s : s;
  }

  /**
   * Decode the simple using the same String format
   * that was written using encodeToString, and return
   * the new instance.  Under no circumstances should
   * this instance be modified.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    byte[] a;
    if (s.equals("null"))
      a = null;
    else
    {
      StringTokenizer st = new StringTokenizer(s, " ");
      a = new byte[st.countTokens()];
      try
      {
        for (int i = 0; i < a.length; i++)
          a[i] = (byte)Integer.parseInt(st.nextToken(), 16);
      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, "Exception occurred in decodeFromString", e);
        a = null;
      }
    }
    return make(a);
  }

////////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////////

  /**
   * Get the length of the byte array.
   *
   * @return the length as an int.
   */
  public int length()
  {
    return arr.length;
  }

  /**
   * Get the byte at the specified index.
   *
   * @return the specified byte as a byte.
   */
  public byte byteAt(int index)
  {
    return arr[index];
  }

  /**
   * Get (a copy of) the byte array.
   *
   * @return a byte array copied from this octet string's array.
   */
  public byte[] getBytes()
  {
    if (arr == null) return null;
    byte[] b = new byte[arr.length];
    System.arraycopy(arr, 0, b, 0, b.length);
    return b;
  }

  /**
   * Get the byte array.
   * Do not alter the contents of this array and complain
   * when things go wrong.
   *
   * @return a this octet string's array.
   */
  public byte[] getAddr()
  {
    if (arr == null) return null;
    return arr;
  }

  /**
   * To String.
   */
  public String toString(Context cx)
  {
    if (cx != null)
    {
      if (cx.equals(BacnetConst.nameContext))
        return toNameString();
      else if (cx.equals(BacnetConst.deviceRegistryContext))
      {
        return TextUtil.bytesToHexString(arr);
      }
      else
      {
        BString osType = (BString)cx.getFacet(BACNET_OCTET_STRING);
        if (osType != null)
        {
          StringBuilder sb = new StringBuilder();
          String base = osType.getString() + ".b";
          for (int i = 0; i < arr.length; i++)
          {
            String key = base + String.valueOf(i) + "." + String.valueOf(arr[i]);
            String s = lex.get(key);
            if (s == null) s = hexByte(arr[i]);
            sb.append(s).append(':');
          }
          return sb.substring(0, sb.length() - 1);
        }
      }
    }

    try
    {
      return encodeToString();
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, "Exception occurred in toString", e);
      return e.toString();
    }
  }

  /**
   * Hash code.
   * The hash code for a BBacnetOctetString is its unique id.
   *
   * @return the id assigned when the string is created.
   */
  public int hashCode()
  {
    return hashCode;
  }

  /**
   * Return a string suitable for inclusion in a Niagara name.
   */
  private String toNameString()
  {
    if ((arr == null) || (arr.length == 0))
      return "null";
    StringBuilder sb = new StringBuilder();
    sb.append("_").append(TextUtil.byteToHexString(arr[0]));
    for (int i = 1; i < arr.length; i++)
      sb.append("_").append(TextUtil.byteToHexString(arr[i]));
    return sb.toString();
  }

////////////////////////////////////////////////////////////////
//  Utilities
////////////////////////////////////////////////////////////////

  /**
   * Encode the simple using a String format
   * that can be translated using decodeFromString.
   */
  public static String bytesToString(byte[] b)
  {
    if ((b == null) || (b.length == 0))
      return "null";
    else
    {
      StringBuilder sb = new StringBuilder();
      String s = TextUtil.byteToHexString(b[0]);
      if (s.length() == 1) sb.append("0");
      sb.append(s);
      for (int i = 1; i < b.length; i++)
      {
        sb.append(" ");
        s = TextUtil.byteToHexString(b[i]);
        if (s.length() == 1) sb.append("0");
        sb.append(s);
      }
      return sb.toString();
    }
  }

  /**
   * Decode the simple using the same String format
   * that was written using encodeToString, and return
   * the new instance.  Under no circumstances should
   * this instance be modified.
   */
  public static byte[] stringToBytes(String s)
  {
    byte[] a;
    if (s.equals("null"))
      a = null;
    else
    {
      StringTokenizer st = new StringTokenizer(s, " ");
      a = new byte[st.countTokens()];
      try
      {
        for (int i = 0; i < a.length; i++)
          a[i] = (byte)Integer.parseInt(st.nextToken(), 16);
      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, "Exception occurred in stringToBytes", e);
        a = null;
      }
    }
    return a;
  }

  private void getHashCode()
  {
    int result = 0;

    if (arr != null)
    {
      result = 1;
      for (int i = 0; i < arr.length; i++)
        result = 31 * result + arr[i];
    }

    hashCode = result;
  }

////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  private static Lexicon lex = Lexicon.make(BBacnetOctetString.class);

  public static final String BACNET_OCTET_STRING = "bacOctetStr";

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  /**
   * Default BBacnetOctetString is a null byte array.
   */
  public static final BBacnetOctetString DEFAULT = make(null);
  public static final BBacnetOctetString BACNET_WEEK_N_DAY = make(new byte[] { (byte)0xFF, (byte)0xFF, (byte)0xFF });

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetOctetString.class);

////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  private byte[] arr = null;
  private int hashCode;

  private static final Logger logger = Logger.getLogger("bacnet.datatypes");
}
