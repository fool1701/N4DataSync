/**
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import java.io.ByteArrayOutputStream;

/**
 *  NrioOutputStream is an extension of the standard
 *  ByteArrayOutputStream for use in formatting Host 
 *  messages.  
 *
 * @author    Andy Saunders (Original R2 code)       
 * @creation  10 Apr 00
 * @author    Zhong Wang (Fixed the extra leading 
 *              zero problem in toAsciiHexByteArray())
 * @creation  10 Apr 00
 * @author    Andy Saunders (upgraded for R3)
 * @creation  04 Sep 02
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 * @since     Niagara 3.0 modbusCore 1.0     
 */
public class NrioOutputStream 
  extends ByteArrayOutputStream  
{
  /**
  * Construct a NrioOutputStream with no initial 
  * capacity
  */
  public NrioOutputStream()
  {
    super();
  }

  /**
  * Construct a NrioOutputStream with the specified
  * initial capacity.
  *
  * @param size initial capacity in bytes
  */
  public NrioOutputStream(int size)
  {
    super(size);
  }


  /**
  * Writes the a byte to the output stream.    
  */
  public void write(byte i)
  {
    super.write( (int)i );
  }

  /**
  * Writes an int to the output stream.    
  */
  public void writeInt(int i)
  {
    write( (i>>8) & 0x00ff);
    write( i & 0x00ff);
  }

  /**
  * Writes an int to the output stream.    
  */
  public void writeIntRev(int i)
  {
    write( i & 0x00ff);
    write( (i>>8) & 0x00ff);
  }

  /**
  * Writes an long to the output stream.    
  */
  public void writeLong(long value, int size)
  {
    for(int shiftBytes = size-1; shiftBytes >= 0; shiftBytes--)
      write( (int)(value >> (8 * shiftBytes)  & 0x00ff ) );
  }

}