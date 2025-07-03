/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.data;

import java.io.*;
import javax.baja.sys.*;
import javax.baja.naming.*;

/**
 * DataUtil provides utility methods for the Data API.
 *
 * @author    Brian Frank
 * @creation  21 Feb 03
 * @version   $Revision: 11$ $Date: 8/16/04 8:35:03 PM EDT$
 * @since     Baja 1.0
 */
public class DataUtil
{ 
  
  /**
   * This a standard utility to encode a data value into
   * into its "symbol:value" format as specified by 
   * the value's BNF production.  The exception to the
   * rule is that if the value is a BString we escape
   * it using SlotPath.escape.  If value is null then
   * return "null".
   */
  public static String marshal(BIDataValue value)
    throws IOException
  {
    return marshal((BObject)value);
  }

  /**
   * This a standard utility to encode a data value into
   * into its "symbol:value" format as specified by 
   * the value's BNF production.  The exceptions to the
   * rule is that if the value is a BString or BOrd we escape
   * it using SlotPath.escape.  If value is null then
   * return "null".
   */
  public static String marshal(BObject value)
    throws IOException
  {
    if (value == null) return "null";
    
    StringBuilder s = new StringBuilder();
    s.append(value.getType().getDataTypeSymbol());
    s.append(':');
    if (value instanceof BString)
      s.append(SlotPath.escape(value.toString()));
    else if (value instanceof BOrd)
      s.append(SlotPath.escape(((BSimple)value).encodeToString()));
    else
      s.append(((BSimple)value).encodeToString());
    return s.toString();
  }
  
  /**
   * This a standard utility to decode a data value
   * from its "symbol:value" format.  The exception
   * to the rule is that 's' / BString and BOrd are unescaped
   * using SlotPath.unescape.  If the string is "null"
   * then return null.
   */
  public static BObject unmarshal(String s)
    throws IOException
  {
    if (s.equals("null")) return null;
    
    // parse symbol
    char symbol = s.charAt(0);
    if (s.charAt(1) != ':')
      throw new IOException("Expecting colon " + s);
    String v = s.substring(2);
    
    // lookup type
    Type type = DataTypes.getBySymbol(symbol);
    if (type == null)
      throw new IOException("Unknown symbol " + symbol);

    // parse value
    if (type == BString.TYPE)
    {
      return BString.make(SlotPath.unescape(v));
    }
    else if (type == BOrd.TYPE)
    {
      BSimple simple = (BSimple)type.getInstance();
      return simple.decodeFromString(SlotPath.unescape(v));
    }
    else
    {
      BSimple simple = (BSimple)type.getInstance();
      return simple.decodeFromString(v);
    }
  }
}
