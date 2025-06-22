/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nre.util;

import java.io.*;

/**
 * Encode and decode binary data in Base 64.
 *
 * @author    John Sublett
 * @creation  19 Aug 98
 * @version   $Revision: 4$ $Date: 4/26/05 3:46:30 PM EDT$
 * @since     Niagara 1.0
 * @deprecated Use {@link java.util.Base64}.
 */
@Deprecated
public class Base64
{

///////////////////////////////////////////////////////////
// Encoding table
///////////////////////////////////////////////////////////

  private final static char[] encodeTable =
  {
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
    'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
    'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
    'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
    'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
    '0', '1', '2', '3', '4', '5', '6', '7', '8',
    '9', '+', '/'
  };
  
///////////////////////////////////////////////////////////
// Constants
///////////////////////////////////////////////////////////
  
  private static final char PAD = '=';

  public static final int HASHLEN = 16;
  public static final int HASHHEXLEN = 32;

///////////////////////////////////////////////////////////
// Encoding
///////////////////////////////////////////////////////////

  /**
   * Encode the specified string.
   *
   * Replacement:
   * <code>
   *   java.util.Base64.getEncoder().encodeToString(s.getBytes())
   * </code>
   */
  public static String encode(String s)
  {
    return encode(s.getBytes());
  }

  /**
   * Encode the specified byte array.
   *
   * Replacement:
   * <code>
   *   java.util.Base64.getEncoder().encodeToString(buf)
   * </code>
   */
  public static String encode(byte[] buf)
  {
    return encode(buf, -1);
  }

  /**
   * Encode the specified byte array.
   *
   * Replacement:
   * <code>
   *   java.util.Base64.getMimeEncoder(linelen, "\n").encodeToString(buf)
   * </code>
   *
   * @param linelen the number of characters per
   *   line in the output, -1 indicates no line breaks
   */
  public static String encode(byte[] buf, int linelen)
  {
    int pos = 0;

    StringBuilder sbuf = new StringBuilder((int)((float)buf.length * 1.33));

    int bytesRemaining = buf.length;
    int index = 0;

    while(bytesRemaining >= 3)
    {
      int i0 = (0xFF & buf[index]) >> 2;
      int i1 = ((0xFF & buf[index] & 0x3) << 4) +
               ((0xFF & buf[index + 1]) >> 4);
      int i2 = ((0xF & buf[index + 1]) << 2) +
               ((0xFF & buf[index + 2]) >> 6);
      int i3 = 0xFF & buf[index + 2] & 0x3f;
      
      pos = append(sbuf, encodeTable[i0], linelen, pos);
      pos = append(sbuf, encodeTable[i1], linelen, pos);
      pos = append(sbuf, encodeTable[i2], linelen, pos);
      pos = append(sbuf, encodeTable[i3], linelen, pos);

      bytesRemaining -= 3;
      index += 3;
    }
    
    if (bytesRemaining > 0)
    {
      byte[] remainder = new byte[3];
      for (int i = 0; i < bytesRemaining; i++)
        remainder[i] = buf[index + i];
      for (int i = bytesRemaining; i < 3; i++)
        remainder[i] = 0;
      
      int lastOut = bytesRemaining == 1 ? 2 : 3;
      
      int i0 = (0xFF & remainder[0]) >> 2;
      int i1 = ((0xFF & remainder[0] & 0x3) << 4) +
               ((0xFF & remainder[1]) >> 4);
      int i2 = ((0xF & remainder[1]) << 2) +
               ((0xFF & remainder[2]) >> 6);
      
      pos = append(sbuf, encodeTable[i0], linelen, pos);
      if (lastOut > 1)
        pos = append(sbuf, encodeTable[i1], linelen, pos);
      if (lastOut > 2)
        pos = append(sbuf, encodeTable[i2], linelen, pos);
        
      int padCount = 4 - lastOut;
      for (int i = 0; i < padCount; i++)
        pos = append(sbuf, PAD, linelen, pos);
    }

    return sbuf.toString();
  }
  
  /**
   * Append a character to the specified buffer inserting
   * line breaks if necessary to conform to the linelen.
   */
  private static final int append(StringBuilder sbuf, char ch,
                                  int linelen, int pos)
  {
    if (linelen != -1)
    {
      if (pos == linelen)
      {
        sbuf.append('\n');
        pos = 0;
      }
 
      pos++;
    }

    sbuf.append(ch);
    return pos;    
  }
                                             

///////////////////////////////////////////////////////////
// Decoding
///////////////////////////////////////////////////////////

  /**
   * Decode the specified source string.
   *
   * Replacement:
   * <code>
   *   java.util.Base64.getDecoder().decode(src)
   * </code>
   */
  public static byte[] decode(String src)
  {
    int bits = src.length() * 6;
    ByteArrayOutputStream res = new ByteArrayOutputStream(bits / 8);
   
    int index = 0;
    int bytesRemaining = src.length();

    while (bytesRemaining >= 4)
    {
      int val0 = getVal(src.charAt(index));
      while ((val0 == -2) && (bytesRemaining > 0))
      {
        index++;
        bytesRemaining--;
        if (bytesRemaining > 0)
          val0 = getVal(src.charAt(index)); 
      }
      
      if (bytesRemaining == 0)
        throw new IllegalStateException("Unexpected end of input.");

      int val1 = getVal(src.charAt(index + 1));
      while ((val1 == -2) && (bytesRemaining > 0))
      {
        index++;
        bytesRemaining--;
        if (bytesRemaining > 0)
          val1 = getVal(src.charAt(index + 1)); 
      }
      
      if (bytesRemaining == 0)
        throw new IllegalStateException("Unexpected end of input.");

      int val2 = getVal(src.charAt(index + 2));
      while ((val2 == -2) && (bytesRemaining > 0))
      {
        index++;
        bytesRemaining--;
        if (bytesRemaining > 0)
          val2 = getVal(src.charAt(index + 2)); 
      }
      
      if (bytesRemaining == 0)
        throw new IllegalStateException("Unexpected end of input.");

      int val3 = getVal(src.charAt(index + 3));
      while ((val3 == -2) && (bytesRemaining > 0))
      {
        index++;
        bytesRemaining--;
        if (bytesRemaining > 0)
          val3 = getVal(src.charAt(index + 3)); 
      }
      
      if (bytesRemaining == 0)
        throw new IllegalStateException("Unexpected end of input.");

      int group = 0;
      int padCount = 0;
      if (val0 != -1)
        group |= val0 << 18;
      else
        padCount++;

      if (val1 != -1)
        group |= val1 << 12;
      else
        padCount++;

      if (val2 != -1)
        group |= val2 << 6;
      else
        padCount++;

      if (val3 != -1)
        group |= val3;
      else
        padCount++;

      res.write((group & 0xFF0000) >> 16);
      if (val2 != -1)
      {
        res.write((group & 0xFF00) >> 8);
        if (val3 != -1)
          res.write(group & 0xFF);
      }

      if (padCount > 0)
        bytesRemaining = 0;
      else
        bytesRemaining -= 4;
      index += 4;
    }

    return res.toByteArray();
  }

  /**
   * Decode the specified source string.
   *
   * Replacement:
   * <code>
   *   new String(java.util.Base64.getDecoder().decode(s))
   * </code>
   */
  public static String decodeToString(String s)
  {
    return new String(decode(s));
  }

  /**
   * Get the value for the specified character. If
   * the character is the pad character, -1 is returned.
   */
  private static int getVal(char ch)
  {
    if (ch == PAD)
      return -1;

    int val = (int)ch;
    if ((val >= 65) && (val <= 90))
      return val - 65;
    else if ((val >= 97) && (val <= 122))
      return val - 71;
    else if ((val >= 48) && (val <= 57))
      return val + 4;
    else if (val == 43)
      return 62;
    else if (val == 47)
      return 63;
    else
      return -2;
  }
}
