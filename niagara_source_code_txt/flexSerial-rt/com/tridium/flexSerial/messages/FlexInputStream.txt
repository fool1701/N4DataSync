/**
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import java.io.*;

import javax.baja.sys.*;
import com.tridium.flexSerial.enums.*;
/**
 * FlexInputStream is an extension of the standard
 * ByteArrayInputStream for use in parsing Host 
 * messages.  It contains special methods to handle
 * various host message constructs datatypes.
 *
 * @author    Andy Saunders (Original R2 code)       
 * @creation  10 Apr 00
 * @author    Scott Hoye
 * @creation  04 Sep 02
 * @version   $Revision: 1$ $Date: 09/04/02 12:47:14 PM$  
 * @since     Niagara 3.0 flexSerial 1.0     
 */
public class FlexInputStream 
  extends ByteArrayInputStream  
{

  private static final int MAX_BYTES_IN_INT = 4;

  /**
  * Construct an input stream that reads data from
  * the given byte array.
  */
  public FlexInputStream(byte[] buf)
  {
    super(buf);
  }

  /**
  * Construct an input stream that reads length data 
  * from the given byte array beginning at offset.
  */
  public FlexInputStream(byte[] buf, int offset, int length)
  {
    super(buf,offset,length);
  }


  public int read(BEncodeTypeEnum encode, BFacets facets)
  {
    if(encode.equals(BEncodeTypeEnum.AsciiHex))
      return readHexByte();
    if(encode.equals(BEncodeTypeEnum.Ascii))
    {
      return readStringInt(encode,facets);
    }
    return read();
  }

  public int readInt(int size, BEncodeTypeEnum encode, BFacets facets)
  {
    if(encode.equals(BEncodeTypeEnum.Ascii))
    {
      return readStringInt(encode,facets);
    }
    if(size > 4 || size < 1)
    {
      System.out.println("\n**************************************************");
      System.out.println("         Invalid int size: " + size);     
      System.out.println("**************************************************\n");
      if(size > 4) size = 4;
      if(size < 1) size = 1;
    }
    boolean isBigEndian = BFlexMessageElement.isBigEndian(facets);
    if(isBigEndian)
      return readBigEndianInt(size, encode, facets); 
    return readLittleEndianInt(size, encode, facets); 
  }

  private int readBigEndianInt(int size, BEncodeTypeEnum encode, BFacets facets)
  {
    int rawInt = 0;
    if(size <= MAX_BYTES_IN_INT)
    {
      for (int index = size-1; index >= 0; index--)
      {
        int byteValue = read(encode, facets);
        rawInt = rawInt | (byteValue << (index * 8));
      }
    }
    return rawInt;
  }

  private int readLittleEndianInt(int size, BEncodeTypeEnum encode, BFacets facets)
  {
    int rawInt = 0;
    if(size <= MAX_BYTES_IN_INT)
    {
      for (int index = 0; index < size; index++)
      {
        rawInt = rawInt | (read(encode, facets) << index * 8);
      }
    }
    return rawInt;
  }


  public float readFloat(BEncodeTypeEnum encode, BFacets facets)
  {
    if(encode.equals(BEncodeTypeEnum.Ascii))
    {
      String value = readString(encode, facets);
      try
      {
        return Float.parseFloat(value.trim());
      }
      catch(Exception e)
      {
        return Float.NaN;
      }
    }
    try
    {
      return Float.intBitsToFloat(readInt(4, encode, facets));
    }
    catch(Exception e)
    {
      return Float.NaN;
    }
  }

  public int readWord(BEncodeTypeEnum encode, BFacets facets)
  {
    boolean isBigEndian = BFlexMessageElement.isBigEndian(facets);
    if(encode.equals(BEncodeTypeEnum.AsciiHex))
      return readHexWord(isBigEndian);

    if(encode.equals(BEncodeTypeEnum.Ascii))
    {
      String value = readString(encode, facets);
      try
      {
        return Integer.parseInt(value.trim());
      }
      catch(Exception e)
      {
        return -1;
      }
    }

    int value = (read() & 0xff);
    if(isBigEndian)
      return (value << 8) | (read() & 0xff);
    return ((read() & 0x0ff) << 8) | value;
  }

  /**
  * Extract an ascii hex word from the input stream
  */
  public int readHexWord(boolean isBigEndian)
  {
    if(isBigEndian)
      return (readHexByte() << 8) | readHexByte();
    return readHexByte() | (readHexByte() << 8);
  }

  /**
  * Extract an ascii hex byte from the input stream
  */
  public int readHexByte()
  {
    int rawValue = readDigit();
    rawValue     = ( rawValue << 4 ) | readDigit();
    return rawValue;
  }

  /**
  * Extract a digit from the input stream
  */
  public int readDigit()
  {
    return (Character.digit( (char)(read() & 0xff), 16)) & 0x0f;
  }

  private int readStringInt(BEncodeTypeEnum encode, BFacets facets)
  {
    String value = readString(encode, facets);
    try
    {
      return Integer.parseInt(value);
    }
    catch(Exception e)
    {
      return -1;
    }
  }

  public String readString(BEncodeTypeEnum encode, BFacets facets)
  {
    String delimiter = facets.gets("endDelimiter", "");
    int fieldSize = facets.geti("fieldWidth", -1);
    boolean nullTerminate = facets.getb("nullTerminate", false);
    StringBuilder sb = new StringBuilder();
    boolean done = false;
    if(delimiter.length() > 0)
    {
      while(!done)
      {
        int value = read();
        if(value < 0)
          done = true;
        else if(value == delimiter.charAt(0))
          done = true;
        else
          sb.append((char)value);
      }
    }
    else if(fieldSize > 0)
    {
      for(int i = 0; i < fieldSize; i++)
      {
        int value = read();
        if(value < 0)
          break;
        sb.append((char)value);
      }
    }
    else // read until null
    {
      while(!done)
      {
        int value = read();
        if(value < 0)
          done = true;
        else if(value == 0)
          done = true;
        else
          sb.append((char)value);
      }
    }
    return sb.toString();
  }



  
}
