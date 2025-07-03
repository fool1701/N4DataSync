/**
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import javax.baja.nre.util.ByteArrayUtil;

/**
 * UnconfiguredModuleReply class represents the status data for the 2 Reader Module.
 *
 * @author    Andy Saunders (Original R2 code)
 * @creation  2 Nov 2005
 * @author    Andy Saunders
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$
 */

public class UnconfiguredModuleReply
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////

 /**
  * Empty default constructor
  */
  public UnconfiguredModuleReply ()
  {
  }

  public UnconfiguredModuleReply (byte[] statusData)
  {
    readData( new NrioInputStream(statusData) );
  }

  public UnconfiguredModuleReply (NrioInputStream in)
  {
    readData( in );
  }

  public void readData(NrioInputStream in)
  {
    uid        = in.readBytes(6);
    moduleType = in.read() & 0x0ff;
  }


  public byte[] getUid     ( ) { return uid       ; }
  public int  getModuleType( ) { return moduleType; }

  public void setUid       ( byte[] uid      ) { this.uid        = uid       ; }
  public void setModuleType( int  moduleType ) { this.moduleType = moduleType; }

  public boolean isReader() { return moduleType != 0x08; }

  /**
   * Return a debug string for this message.
   */
  public String toDebugString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("UnconfiguredModuleData:");
    sb.append("\n  uid        = " + ByteArrayUtil.toHexString(uid));
    sb.append("\n  moduleType = " + Integer.toHexString(moduleType));
    return sb.toString();
  }


///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////


  protected byte[] uid = new byte[6];
  protected int moduleType;

}
