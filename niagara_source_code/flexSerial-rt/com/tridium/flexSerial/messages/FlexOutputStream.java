/**
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.messages;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import javax.baja.sys.BFacets;

import com.tridium.flexSerial.enums.BEncodeTypeEnum;

/**
 *  FlexOutputStream is an extension of the standard
 *  ByteArrayOutputStream for use in formatting Host 
 *  messages.  
 *
 * @author    Andy Saunders (Original R2 code)       
 * @creation  10 Apr 00
 * @author    Zhong Wang (Fixed the extra leading 
 *              zero problem in toAsciiHexByteArray())
 * @creation  10 Apr 00
 * @author    Scott Hoye (upgraded for R3)
 * @creation  04 Sep 02
 * @version   $Revision: 1$ $Date: 09/04/02 12:47:14 PM$  
 * @since     Niagara 3.0 modbusCore 1.0     
 */
public class FlexOutputStream 
  extends ByteArrayOutputStream  
{
  /**
  * Construct a FlexOutputStream with no initial 
  * capacity
  */
  public FlexOutputStream()
  {
    super();
  }

  /**
  * Construct a FlexOutputStream with the specified
  * initial capacity.
  *
  * @param size initial capacity in bytes
  */
  public FlexOutputStream(int size)
  {
    super(size);
  }


  /**
  * Writes the a byte to the output stream.    
  */
  public void write(int value, BEncodeTypeEnum encode, BFacets facets)
  {
    
    switch(encode.getOrdinal())
    {
    case BEncodeTypeEnum.ASCII_HEX:
      StringBuilder sb = new StringBuilder();
      sb.append("000");
      sb.append(Integer.toString( (value & 0x0ff), 16 ));
      String temp = sb.toString().toUpperCase();
      write( (byte)temp.charAt(temp.length()-2) );
      write( (byte)temp.charAt(temp.length()-1) );
      break;
    default:
      super.write( value );
      
    }
  }

  /**
  * Writes an int to the output stream.    
  */
  public void writeInt(int i, int size, BEncodeTypeEnum encode, BFacets facets)
  {
    boolean isBigEndian = BFlexMessageElement.isBigEndian(facets);
    if(isBigEndian)
      writeBigEndianInt(i, size, encode, facets); 
    else
      writeLittleEndianInt(i, size, encode, facets); 
  }

  /**
  * Writes an int to the output stream.    
  */
  public void writeBigEndianInt(int i, int size, BEncodeTypeEnum encode, BFacets facets)
  {
    byte[] bytes = new byte[size];
    switch(size)
    {
    case 4:
      bytes[0] = (byte)( (i >> 24) & 0x0ff );
      bytes[1] = (byte)( (i >> 16) & 0x0ff );
      bytes[2] = (byte)( (i >> 8 ) & 0x0ff );
      bytes[3] = (byte)( i & 0x0ff );
      break;
    case 3:
      bytes[0] = (byte)( (i >> 16) & 0x0ff );
      bytes[1] = (byte)( (i >> 8 ) & 0x0ff );
      bytes[2] = (byte)( i & 0x0ff );
      break;
    case 2:
      bytes[0] = (byte)( (i >> 8 ) & 0x0ff );
      bytes[1] = (byte)( i & 0x0ff );
      break;
    default:
      bytes[0] = (byte)( i & 0x0ff );
    }
    switch(encode.getOrdinal())
    {
    case BEncodeTypeEnum.ASCII_HEX:
      byte[] asciiBytes = toAsciiHexByteArray(bytes);
      //System.out.println(" input int = " + i);
      //System.out.println(" input bytes = " + ByteArrayUtil.toHexString(bytes));
      //System.out.println(" ascii bytes = " + ByteArrayUtil.toHexString(asciiBytes));
      write(asciiBytes, 0, asciiBytes.length );
      break;
    default:
      write(bytes, 0, bytes.length );

    }
  }

  public void writeLittleEndianInt(int i, int size, BEncodeTypeEnum encode, BFacets facets)
  {
  
    byte[] bytes = new byte[size];
    switch(size)
    {
    case 4:
      bytes[0] = (byte)( i & 0x0ff );
      bytes[1] = (byte)( (i >> 8) & 0x0ff);
      bytes[2] = (byte)( (i >> 16) & 0x0ff);
      bytes[3] = (byte)( (i >> 24) & 0x0ff);
      break;
    case 3:
      bytes[0] = (byte)( i & 0x0ff );
      bytes[1] = (byte)( (i >> 8) & 0x0ff);
      bytes[2] = (byte)( (i >> 16) & 0x0ff);
      break;
    case 2:
      bytes[0] = (byte)( i & 0x0ff );
      bytes[1] = (byte)( (i >> 8) & 0x0ff);
      break;
    default:
      bytes[0] = (byte)( i & 0x0ff );
    }
    
    switch(encode.getOrdinal())
    {
    case BEncodeTypeEnum.ASCII_HEX:
      byte[] asciiBytes = toAsciiHexByteArray(bytes);
      //System.out.println(" input int = " + i);
      //System.out.println(" input bytes = " + ByteArrayUtil.toHexString(bytes));
      //System.out.println(" ascii bytes = " + ByteArrayUtil.toHexString(asciiBytes));
      write(asciiBytes, 0, asciiBytes.length );
      break;
    default:
      write(bytes, 0, bytes.length );
    }

  }

  /**
  * Writes a string to the output stream & null terminates    
  */
  public void writeString(String s, BEncodeTypeEnum encode, BFacets facets)
  {
    try { write( s.getBytes() ); }
    catch( Exception e) {  }
    if(facets.getb(BFlexMessageElement.NULL_TERMINATE, false))
      write( 0x00 );
  }
  
  public byte[] toAsciiHexByteArray(byte[] buf)
  {
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i<buf.length; i++)
    {
      if((buf[i] >= 0x00) && (buf[i] < 0x10))
        sb.append("0");
      sb.append(Integer.toHexString( ((int) buf[i]) & 0xff));     
    }
    String s = sb.toString().toUpperCase();
    //System.out.println("toAsciiHexByteArray: string = " + s);
    byte[] ba = new byte[s.length()];
    System.arraycopy(s.getBytes(StandardCharsets.US_ASCII), 0, ba, 0, ba.length);
    return ba;

  }


  public void setCksumStart(int offset)
  {
    cksumStart = offset;
  }

  public int getCksumStart()
  {
    return cksumStart;
  }

  int cksumStart = 0;

}