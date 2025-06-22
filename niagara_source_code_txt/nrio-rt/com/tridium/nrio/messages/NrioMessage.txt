/**
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import java.io.OutputStream;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.nre.util.TextUtil;
import com.tridium.basicdriver.message.Message;
import com.tridium.basicdriver.message.ReceivedMessage;

/**
 * NrioMessage is the super class for all nrio messages.
 *
 * @author    Andy Saunders (Original R2 code)
 * @creation  07 Nov 99
 * @author    Andy Saunders
 * @creation  04 Sep 02
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$
 * @since     Niagara 3.0 nrio 1.0
 */
public class NrioMessage
  extends Message
  implements NrioMessageConst
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////

 /**
  * Empty default constructor
  */
  public NrioMessage ()
  {
  }

 /**
  * Constructor with Modbus device address and function code
  * initialization parameters.
  */
  public NrioMessage ( int address, int type, byte[] data)
  {
    this.address = address;
    this.type    = type;
    this.status  = 0;
    this.data    = data;
  }


  public int    getAddress( ) { return address ; }
  public int    getLength ( ) { return length  ; }
  public int    getType   ( ) { return type    ; }
  public int    getStatus ( ) { return status  ; }
  public byte[] getData   ( ) { return data    ; }
  public boolean isOk() { return status == 0; }

  public void setAddress( int    address) { this.address = address; }
  public void setLength ( int    length ) { this.length  = length ; }
  public void setType   ( int    type   ) { this.type    = type   ; }
  public void setStatus ( int    status ) { this.status  = status ; }
  public void setData   ( byte[] data   ) { this.data    = data   ; }

  /**
  * Write this message to the given output stream.
  */
  public void write(OutputStream out)
  {
    try
    {
      byte[] ba = getByteArray();
//            System.out.println(" sending packet: ");
//            ByteArrayUtil.hexDump(ba);
      out.write(ba);
    }
    catch (Exception e) {}
  }

  public byte[] getByteArray()
  {
    NrioOutputStream packet = new NrioOutputStream();
    packet.write(address);
    packet.write(HEADER_SIZE + data.length);
    packet.write(type);
    packet.write(status);
    for(int i = 0; i < data.length; i++)
      packet.write(data[i]);
    return packet.toByteArray();
  }

  /**
  * Convert a GeM6 received message to
  * a response Message.
  */
  public Message toResponse(ReceivedMessage resp)
  {
    NrioReceivedMessage accessResp = (NrioReceivedMessage)resp;
    NrioResponse respMessage = new NrioResponse();
    respMessage.readResponse(accessResp.getBytes(), accessResp.getLength());
    return respMessage;
  }

  /**********************************************
  *  This method will read a response to a request message
  *  into the base message structure
  **********************************************/
  public NrioInputStream readResponse(NrioReceivedMessage message)
  {
    return decodeFromBytes(message.getBytes(), message.getLength());
  }

  public NrioInputStream decodeFromBytes(byte[] dataBytes, int nbytes)
  {
    NrioInputStream inStream = new NrioInputStream(dataBytes, 0, nbytes);
    this.address = inStream.read() & 0x0ff;
    this.length  = inStream.read() & 0x0ff;
    this.type    = inStream.read() & 0x0ff;
    this.status  = inStream.read() & 0x0ff;
    this.data = new byte[inStream.available()];
    for(int i = 0; i < data.length; i++)
      this.data[i] = (byte)(inStream.read() & 0xff);
    inStream = new NrioInputStream(data);
    return inStream;
  }



  /**
   * Return a debug string for this message.
   */
  public String toDebugString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("NrioMessage       = " + TextUtil.getClassName(getClass()));
    sb.append("\n  address = " + Integer.toHexString(address));
    sb.append("\n  length  = " + Integer.toHexString(length ));
    sb.append("\n  type    = " + Integer.toHexString(type   ));
    sb.append("\n  status  = " + Integer.toHexString(status ));
    sb.append("\n  data    = " + ByteArrayUtil.toHexString(data) );
    return sb.toString();
  }


///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////


  protected int    address ;
  protected int    length  ;
  protected int    type    ;
  protected int    status  ;
  protected byte[] data    = new byte[0];


}
