/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.comm;

import java.io.*;

import javax.baja.util.*;

import com.tridium.basicdriver.*;
import com.tridium.basicdriver.comm.*;
import com.tridium.flexSerial.messages.*;

/**
 * FlexSerialCommTransmitter is the basic driver for access (writing)
 * to the output stream.  Only one transaction can be 
 * processed at a time. 
 *
 * @author    Andy Saunders
 * @creation  28 April 2004
 * @version   $Revision: 1$ $Date: 03/26/02 12:47:14 PM$
 * @since     Niagara 3.0 basicdriver 1.0
 */

public class FlexSerialCommTransmitter
  extends CommTransmitter

{
  public void setFrameStart(byte[] start)
  {
    frameStart = start;
    //System.out.println("frameStart = " + ByteArrayUtil.toHexString(frameStart));
  }

  public void setFrameEnd(byte[] end)
  {
    frameEnd = end;
    //System.out.println("frameEnd = " + ByteArrayUtil.toHexString(frameEnd));
  }

  /**
  * Method to be called if special processing
  * is required at the start of sending bytes to the output stream.  
  * Should be overridden by subclasses as needed.  The default 
  * is to do nothing.
  */
  public void writeBytesStart(OutputStream out)
    throws Exception
  {
    if(frameStart.length > 0) out.write(frameStart);
  }

  /**
  * Method to be called if special processing
  * is required at the end of sending bytes to the output stream.  
  * Should be overridden by subclasses as needed.  The default 
  * is to do nothing.
  */
  public void writeBytesEnd(OutputStream out)
    throws Exception
  {
    if(frameEnd.length > 0) out.write(frameEnd);
  }

////////////////////////////////////////////////////////////////
//  Attributes of FlexSerialCommTransmitter
////////////////////////////////////////////////////////////////
  private byte[] frameStart = new byte[0];
  private byte[] frameEnd   = new byte[0];
}

