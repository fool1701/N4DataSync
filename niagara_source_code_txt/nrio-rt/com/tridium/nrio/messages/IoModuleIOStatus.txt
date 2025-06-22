/**
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

/**
 * IoModuleIOStatus class represents the status data for the Nrio IO Module.
 *
 * @author    Andy Saunders (Original R2 code)       
 * @creation  12 Jan 2006
 * @author    Andy Saunders
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 */

public class IoModuleIOStatus
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////
  
 /**
  * Empty default constructor
  */
  public IoModuleIOStatus ()
  {
  }
  
  public IoModuleIOStatus (byte[] data)
  {
    NrioInputStream in = new NrioInputStream(data);
    sdi1          = in.readSdi();
    sdi2          = in.readSdi();
    sdi3          = in.readSdi();
    sdi4          = in.readSdi();
    sdi5          = in.readSdi();
    sdi6          = in.readSdi();
    sdi7          = in.readSdi();
    sdi8          = in.readSdi();
    ciData        = in.readSdi();
    di1           = (ciData & 0x0001) != 0;
    di2           = (ciData & 0x0002) != 0;

  }
  

  public boolean        getDi1       ( ) { return di1       ; }
  public boolean        getDi2       ( ) { return di2       ; }
  public int            getSdi1      ( ) { return sdi1      ; }
  public int            getSdi2      ( ) { return sdi2      ; }
  public int            getSdi3      ( ) { return sdi3      ; }
  public int            getSdi4      ( ) { return sdi4      ; }
  public int            getSdi5      ( ) { return sdi5      ; }
  public int            getSdi6      ( ) { return sdi6      ; }
  public int            getSdi7      ( ) { return sdi7      ; }
  public int            getSdi8      ( ) { return sdi8      ; }


  /**
   * Return a debug string for this message.
   */
  public String toDebugString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("IoModuleIOStatus:");
    sb.append("\n  di1          = " + di1       );
    sb.append("\n  di2          = " + di2       );
    sb.append("\n  sdi1         = " + sdi1       );
    sb.append("\n  sdi2         = " + sdi2       );
    sb.append("\n  sdi3         = " + sdi3       );
    sb.append("\n  sdi4         = " + sdi4       );
    sb.append("\n  sdi5         = " + sdi5       );
    sb.append("\n  sdi6         = " + sdi6       );
    sb.append("\n  sdi7         = " + sdi7       );
    sb.append("\n  sdi8         = " + sdi8       );
    return sb.toString();
  }
  

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  protected int sdi1;
  protected int sdi2;
  protected int sdi3;
  protected int sdi4;
  protected int sdi5;
  protected int sdi6;
  protected int sdi7;
  protected int sdi8;
  protected boolean di1;
  protected boolean di2;
  protected int ciData;


  
}
