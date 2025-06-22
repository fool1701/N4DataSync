/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import javax.baja.lonworks.datatypes.LonAddress;
import javax.baja.lonworks.enums.BLonRepeatTimer;
import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.sys.BajaRuntimeException;

/**
 *  LonMessage is the superclass for all messages
 *  to be passed from and to <code>LonComm</code>.  <p>
 *
 *  Subclasses should override <code>fromInputStream</code>
 *  and <code>toOutputStream</code> as needed.<p>
 *
 *  If a subclass is a request message which
 *  will be sent using LonComm.sendRequest()
 *  then the <code>toResponse</code> method must also
 *  be overridden.<p>
 *
 *  Example implementation for Query SNVT.  This is a
 *  request/response message.<p>
 *  <pre>
 *  public class QuerySNVTRequest
 *    extends LonMessage
 *  {
 *    public QuerySNVTRequest(LonInputStream in)
 *    {
 *      code = QUERY_SNVT_REQUEST;
 *      fromInputStream(inputStream);
 *    }
 *    public void toOutputStream(LonOutputStream out)
 *    {
 *      out.writeUnsigned8(QUERY_SNVT_REQUEST);
 *      out.writeUnsigned16(offset);
 *      out.writeUnsigned8(count);
 *    }
 *    public void fromInputStream(LonInputStream in)
 *    {
 *      int code = in.readUnsigned8();
 *      if(code!=QUERY_SNVT_REQUEST) throw new InvalidResponseException(code);
 *
 *      offset = in.readUnsigned16();
 *      count = in.readUnsigned8();
 *    }
 *
 *    public LonMessage toResponse(LonInputStream in)
 *      throws LonException
 *    {
 *      int code = in.readUnsigned8();
 *
 *      if (code == QUERY_SNVT_SUCCESS)
 *        in.reset();
 *        return new QuerySNVTResponse(in);
 *      else if (code == QUERY_SNVT_FAILED)
 *        throw new FailedResponseException();
 *      else
 *        throw new InvalidResponseException(code);
 *    }
 *
 *    private int     offset;
 *    private int     count;
 *
 *  }
 *
 *  public class QuerySNVTResponse
 *    extends LonMessage
 *  {
 *    public QuerySNVTResponse(LonInputStream in)
 *    {
 *      code = QUERY_SNVT_SUCCESS;
 *      fromInputStream(in);
 *    }
 *    public void fromInputStream(LonInputStream in)
 *    {
 *      if(inputStream.readUnsigned8()!=QUERY_SNVT_SUCCESS)
 *        invalidMsgCodeException();
 *
 *      data = in.readByteArray();
 *    }
 *    private byte[] data;
 *  }
 *  </pre>
 *
 *
 * @author    Robert Adams
 * @creation  14 Feb 02
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:44 PM$
 * @since     Niagara 3.0
 */
public class LonMessage
{

/////////////////////////////////////////////////////////
//  Constructor
/////////////////////////////////////////////////////////
  /** Empty constructor. */
  public LonMessage() {}

  /**
   * Construct a LonMessage with the specified message
   * code and message data supplied by reading all bytes
   * from the <code>LonInputStream</code>.
   */
  public LonMessage(LonInputStream in)
  {
    try { fromInputStream(in); } catch(LonException e) {}
  }

/////////////////////////////////////////////////////////
//  Access methods
/////////////////////////////////////////////////////////
  /**
  *  Get the message code. This will be the value of the first
  *  byte of a received message. This is the LonTalk message type
  *  code.  See Appendix B of the Neuron Chip Data Book for details.
   */
  public int getMessageCode() { return code; }
  /**
  *  Set the message code.  This is the LonTalk message type
  *  code.  See Appendix B of the Neuron Chip Data Book for details.
  */
  public void setMessageCode( int code) {this.code = code; }

  /**
   *  Make this message a far side message for accessing the
   *  far side of an lonworks router.  This will cause a
   *  FAR_SIDE_ESCAPE_CODE to be place in the first byte of
   *  the message.
   */
  public void setFarSide()  { farSide = true; }
  /** Has this message been marked as a farside message. */
  public boolean isFarSide()  { return farSide; }

  /**
   * For Internal use only.<p> Set flag to indicate if this
   * was received using request/response service type.<p>
   */
  public void setRequest(boolean req) { request = req; }
  /** Was this message received using request/response service type. */
  public boolean isRequest() { return request; }

  /** Set flag to indicate if this is a priority message. */
  public void setPriority(boolean priority) {this.priority = priority; }
  /** Is this a priority message. */
  public boolean isPriority() {return priority; }

  /** Set flag to indicate if this message should be authenticated message. */
  public void setAuthenticate(boolean authenticate) {this.authenticate = authenticate; }
  /** Is this an authenticated message. */
  public boolean isAuthenticate() {return authenticate; }

  /** For Internal use only. */
  public void setSourceAddress(LonAddress a) {adr = a;}
  /** The source address of a received message. */
  public LonAddress getSourceAddress() {return adr;}

  /** For Internal use only. */
  public void setTag(int t) { tag = t; }
  /** For Internal use only. */
  public int getTag() { return tag; }

  /** Set the message data.  This does not include the message code. */
  public void setMessageData( byte[] m) {messageData = m; }
  /** Get the message data.  This does not include the message code. */
  public  byte[] getMessageData() { return messageData; }

  /**
   * Set the number of retries if the message is sent
   * using req/resp, ackd, or unackRpt service types.
   * Valid values are 0-15. If value is set greater
   * than 15 then 15 will be used.
   * This will override the default retryCount.
   */
  public void setRetryCount(int retryCount)
    { this.retryCount = (retryCount>15) ? 15 : retryCount;  }
  /** Return the number of retryCount or -1 if not set. */
  public int getRetryCount() { return retryCount; }

  /**
   * Set the transmit timer to use for this message if
   * different from value configured in LonCommConfig.
   * This is the time interval between retries of
   * an outgoing message using req/resp or acknowledged service.
   */
  public void setTransmitTimer(BLonRepeatTimer t) { transmitTimer = t; }
  /** Return the transmit timer to use for this message.
   *  If not set the return value is null.  */
  public BLonRepeatTimer getTransmitTimer() { return transmitTimer; }

  /**
   * Set the repeat timer to use for this message if
   * different from value configured in LonCommConfig.
   * This is the time interval between repetitions of
   * an outgoing message using repeat service.
   */
  public void setRepeatTimer(BLonRepeatTimer t) { repeatTimer = t; }
  /** Return the repeat timer to use for this message.
   *  If not set the return value is null.  */
  public BLonRepeatTimer getRepeatTimer() { return repeatTimer; }

  public void setDomainIndex(int ndx) { domainIndex = ndx; }
  public int getDomainIndex() { return domainIndex; }

/////////////////////////////////////////////////////////
//  Conversion
/////////////////////////////////////////////////////////

  /**
   * Read this message's data from the given input stream.
   * <p>
   * The default implementation will read the first byte to
   * message code and the remaining data in the inputStream
   * to messageData.
   */
  public void fromInputStream(LonInputStream in)
    throws LonException
  {
    code = in.readUnsigned8();
    // Limit data read to ??? MAX_MSG_DATA.
    messageData = in.readByteArray();
  }

  /**
   *  Write this message in network byte format to the
   *  out stream. This should include the message code.
   *  Subclasses should override this method if they
   *  contain data other than the message code.  The
   *  default implementation is to write the message code
   *  and messageData to the the output stream.
   */
  public void toOutputStream(LonOutputStream out)
  {
    out.writeUnsigned8(code);
    if(messageData!=null)out.writeByteArray(messageData);
  }

  /**
   *  This method is used by LonComm to convert a response
   *  message to the appropriate type response. Subclasses
   *  which are request messages should override this method
   *  to process the data from the supplied LonInputStream.
   *  If invalid or failed message data is supplied a
   *  LonException should be thrown.<p>
   *
   *  The default implementation will throw a
   *  BajaRuntimeException("Not a request message.")<p>
   *
   */
  public LonMessage toResponse(LonInputStream in)
   throws LonException
  {
    // If this method not overridden assume not a request message
    throw new BajaRuntimeException("Not a request message.");
  }

/////////////////////////////////////////////////////////
//  Exception utilities
/////////////////////////////////////////////////////////
  /**
   *  Utility to throw exception to indicate invalid
   *  message code encountered in message stream.
   */
  protected final void invalidMsgCodeException(int code)
    throws LonException
  {
    throw new InvalidResponseException(code);
  }

/////////////////////////////////////////////////////////
//  Attributes
/////////////////////////////////////////////////////////
  protected int code;
  private boolean farSide = false;
  private boolean request = true;
  private boolean priority = false;
  private boolean authenticate = false;
  private int domainIndex = 0;
  private LonAddress adr;
  private int tag;
  private int retryCount = -1;
  private BLonRepeatTimer transmitTimer = null;
  private BLonRepeatTimer repeatTimer = null;
  private byte[] messageData = null;

  /** Maximum number of bytes in the data portion of an explicit message */
  public static final int MAX_NETMSG_DATA = 228;

  /** Maximum number of bytes in explicit message for Niagara architecture */
  public static final int MAX_MSG_DATA = MAX_NETMSG_DATA; //change for explicit message 64; // change for LonMaker tunnel 34;

  /**
   *  Far side escape code - used to indicate network management messages destined
   *  for far side of router
   */
  public static final int FAR_SIDE_ESCAPE_CODE               = 0x7E;

}