/**
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import java.io.ByteArrayInputStream;

/**
 * NrioInputStream is an extension of the standard
 * ByteArrayInputStream for use in parsing Host 
 * messages.  It contains special methods to handle
 * various host message constructs datatypes.
 *
 * @author    Andy Saunders (Original R2 code)       
 * @creation  10 Apr 00
 * @author    Andy Saunders
 * @creation  04 Sep 02
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 * @since     Niagara 3.0 modbusAscii 1.0     
 */
public class NrioInputStream 
  extends ByteArrayInputStream  
{
  /**
  * Construct an input stream that reads data from
  * the given byte array.
  */
  public NrioInputStream(byte[] buf)
  {
    super(buf);
  }

  /**
  * Construct an input stream that reads length data 
  * from the given byte array beginning at offset.
  */
  public NrioInputStream(byte[] buf, int offset, int length)
  {
    super(buf,offset,length);
  }

  public int readInt()
  {
	    int value = read() & 0x0ff;
	    value = value | ((read() & 0x0ff)<<8);
	    return value;
//    int value = read() & 0x0ff;
//    value = value << 8;
//    value = value | (read() & 0x0ff);
//    return value;
  }
  
  public int readCount()
  {
	  int value = read() & 0x0ff;
	  value = value << 8;
	  value = value | (read() & 0x0ff);
	  return value;
  }

  public int readSdi()
  {
    int value = read() & 0x0ff;
    value = value | ((read() & 0x0ff)<<8);
    return value;
  }

  public int  readInt(int numBytes)
  {
    int temp = read() & 0x0ff;
    for(int i = 1; i < numBytes; i++)
    {
      temp = temp | ((read() & 0x0ff) << 8);
    }
    return temp;
  }

  public long  readLong()
  {
    long temp = (long)readWord() & 0x0ffffL;
    return temp | ((readWord()& 0x0ffffL) << 16);
  }
  
  public int readWord()
  {
    int temp = read() & 0x0ff;
    return temp | ((read() & 0x0ff)) << 8;
  }

  public byte[] readBytes(int numBytes)
  {
    byte[] bytes = new byte[numBytes];
    for(int i = 0; i < numBytes; i++)
    {
      bytes[i] = (byte)(read() & 0x0ff);
    }
    return bytes;
  }


  
}