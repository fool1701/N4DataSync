/**
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

/**
 * NrioDeviceIoStatus class represents the base IO status message from access modules.
 *
 * @author    Andy Saunders (Original R2 code)       
 * @creation  2 Nov 2005
 * @author    Andy Saunders
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 */

public class NrioDeviceIoStatus
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////
  
 /**
  * Empty default constructor
  */
  public NrioDeviceIoStatus ()
  {
  }
  
  public NrioDeviceIoStatus (byte[] data)
  {
    decodeFromBytes(data);
  }
  
  public void decodeFromBytes(byte[] data)
  {
    NrioInputStream in = new NrioInputStream(data);
    for (int i = 0; i < 8; i++) 
    {
      sdi[i] = in.readSdi();
    }
    ciData        = in.readSdi();
  }

  public int getSdi( int index ) 
  {
    if(index < 1 || index > 8)
      return -1;
    return sdi[index-1]; 
  }

  public boolean getDi ( int index )
  {
    if(index < 1 || index > 16)
      return false;
    return (ciData & (0x0001 << (index-1))) != 0 ;
  }


  /**
   * Return a debug string for this message.
  public String toDebugString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("NrioDeviceIoStatus:");
    sb.append("\n  tamper        = " + tamper       );
    sb.append("\n  batteryStatus = " + batteryStatus);
    sb.append("\n  powerSci      = " + powerSci     );
    sb.append("\n  reader1: ");
    sb.append( reader1.toDebugString() )      ;
    sb.append("\n  reader2: ");
    sb.append( reader2.toDebugString() )      ;
    return sb.toString();
  }
  
  */

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  protected int[] sdi = new int[8];
  protected int ciData;

}
