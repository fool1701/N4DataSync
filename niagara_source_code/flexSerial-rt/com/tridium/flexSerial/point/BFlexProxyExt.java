/*
 * Copyright 2005 Tridium, All Rights Reserved.
 */
package com.tridium.flexSerial.point;

import javax.baja.driver.*;
import javax.baja.driver.point.*;
import javax.baja.driver.util.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

import com.tridium.basicdriver.point.*;
import com.tridium.basicdriver.util.*;
import com.tridium.flexSerial.*;
import com.tridium.flexSerial.messages.*;

/**
 * BFlexProxyExt
 *
 * @author    Andy Saunders
 * @creation  19-Apr-05
 * @version   $Revision$ $Date: 4/18/2005 2:51:31 PM$
 */
@NiagaraType
/*
 Poll frequency bucket
 */
@NiagaraProperty(
  name = "pollFrequency",
  type = "BPollFrequency",
  defaultValue = "BPollFrequency.normal"
)
/*
 Address information about this point
 It will typically used in FlexMessageElements
 */
@NiagaraProperty(
  name = "address",
  type = "String",
  defaultValue = ""
)
/*
 Additional Address information about this point
 It will typically used in FlexMessageElements
 */
@NiagaraProperty(
  name = "address1",
  type = "String",
  defaultValue = ""
)
/*
 Additional Address information about this point
 It will typically used in FlexMessageElements
 */
@NiagaraProperty(
  name = "address2",
  type = "String",
  defaultValue = ""
)
/*
 This is a FlexRequestResponse message pair used to poll for point data.
 */
@NiagaraProperty(
  name = "pollMessage",
  type = "BFlexRequestResponse",
  defaultValue = "new BFlexRequestResponse()"
)
/*
 Thhis is a FlexRequestResponse message pair used to write message data.
 */
@NiagaraProperty(
  name = "writeMessage",
  type = "BFlexRequestResponse",
  defaultValue = "new BFlexRequestResponse()"
)
public class BFlexProxyExt
  extends BBasicProxyExt
 implements BIBasicPollable
{            

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.point.BFlexProxyExt(2680560867)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "pollFrequency"

  /**
   * Slot for the {@code pollFrequency} property.
   * Poll frequency bucket
   * @see #getPollFrequency
   * @see #setPollFrequency
   */
  public static final Property pollFrequency = newProperty(0, BPollFrequency.normal, null);

  /**
   * Get the {@code pollFrequency} property.
   * Poll frequency bucket
   * @see #pollFrequency
   */
  public BPollFrequency getPollFrequency() { return (BPollFrequency)get(pollFrequency); }

  /**
   * Set the {@code pollFrequency} property.
   * Poll frequency bucket
   * @see #pollFrequency
   */
  public void setPollFrequency(BPollFrequency v) { set(pollFrequency, v, null); }

  //endregion Property "pollFrequency"

  //region Property "address"

  /**
   * Slot for the {@code address} property.
   * Address information about this point
   * It will typically used in FlexMessageElements
   * @see #getAddress
   * @see #setAddress
   */
  public static final Property address = newProperty(0, "", null);

  /**
   * Get the {@code address} property.
   * Address information about this point
   * It will typically used in FlexMessageElements
   * @see #address
   */
  public String getAddress() { return getString(address); }

  /**
   * Set the {@code address} property.
   * Address information about this point
   * It will typically used in FlexMessageElements
   * @see #address
   */
  public void setAddress(String v) { setString(address, v, null); }

  //endregion Property "address"

  //region Property "address1"

  /**
   * Slot for the {@code address1} property.
   * Additional Address information about this point
   * It will typically used in FlexMessageElements
   * @see #getAddress1
   * @see #setAddress1
   */
  public static final Property address1 = newProperty(0, "", null);

  /**
   * Get the {@code address1} property.
   * Additional Address information about this point
   * It will typically used in FlexMessageElements
   * @see #address1
   */
  public String getAddress1() { return getString(address1); }

  /**
   * Set the {@code address1} property.
   * Additional Address information about this point
   * It will typically used in FlexMessageElements
   * @see #address1
   */
  public void setAddress1(String v) { setString(address1, v, null); }

  //endregion Property "address1"

  //region Property "address2"

  /**
   * Slot for the {@code address2} property.
   * Additional Address information about this point
   * It will typically used in FlexMessageElements
   * @see #getAddress2
   * @see #setAddress2
   */
  public static final Property address2 = newProperty(0, "", null);

  /**
   * Get the {@code address2} property.
   * Additional Address information about this point
   * It will typically used in FlexMessageElements
   * @see #address2
   */
  public String getAddress2() { return getString(address2); }

  /**
   * Set the {@code address2} property.
   * Additional Address information about this point
   * It will typically used in FlexMessageElements
   * @see #address2
   */
  public void setAddress2(String v) { setString(address2, v, null); }

  //endregion Property "address2"

  //region Property "pollMessage"

  /**
   * Slot for the {@code pollMessage} property.
   * This is a FlexRequestResponse message pair used to poll for point data.
   * @see #getPollMessage
   * @see #setPollMessage
   */
  public static final Property pollMessage = newProperty(0, new BFlexRequestResponse(), null);

  /**
   * Get the {@code pollMessage} property.
   * This is a FlexRequestResponse message pair used to poll for point data.
   * @see #pollMessage
   */
  public BFlexRequestResponse getPollMessage() { return (BFlexRequestResponse)get(pollMessage); }

  /**
   * Set the {@code pollMessage} property.
   * This is a FlexRequestResponse message pair used to poll for point data.
   * @see #pollMessage
   */
  public void setPollMessage(BFlexRequestResponse v) { set(pollMessage, v, null); }

  //endregion Property "pollMessage"

  //region Property "writeMessage"

  /**
   * Slot for the {@code writeMessage} property.
   * Thhis is a FlexRequestResponse message pair used to write message data.
   * @see #getWriteMessage
   * @see #setWriteMessage
   */
  public static final Property writeMessage = newProperty(0, new BFlexRequestResponse(), null);

  /**
   * Get the {@code writeMessage} property.
   * Thhis is a FlexRequestResponse message pair used to write message data.
   * @see #writeMessage
   */
  public BFlexRequestResponse getWriteMessage() { return (BFlexRequestResponse)get(writeMessage); }

  /**
   * Set the {@code writeMessage} property.
   * Thhis is a FlexRequestResponse message pair used to write message data.
   * @see #writeMessage
   */
  public void setWriteMessage(BFlexRequestResponse v) { set(writeMessage, v, null); }

  //endregion Property "writeMessage"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the network cast to a BFlexSerialNetwork.
   */
  public final BFlexSerialNetwork getFlexNetwork()
  {
    return (BFlexSerialNetwork)getNetwork();
  }

  /**
   * Get the device cast to a BFlexSerialDevice.
   */
  public final BFlexSerialDevice getFlexDevice()
  {
    return (BFlexSerialDevice)getDevice();
  }

  /**
   * Get the device address property value.
   */
  public final String getFlexDeviceAddress()
  {
    return ((BFlexSerialDevice)getDevice()).getAddress();
  }

  /**
   * Get the point device ext cast to a BFlexPointDeviceExt.
   */
  public final BFlexPointDeviceExt getFlexPointDeviceExt()
  {
    return (BFlexPointDeviceExt)getDeviceExt();
  }

////////////////////////////////////////////////////////////////
// ProxyExt
////////////////////////////////////////////////////////////////
  
  /**
   * Return the device type. 
   */
  public Type getDeviceExtType()
  {
    return BFlexPointDeviceExt.TYPE;
  }                     
  
  /**
   * Return if this proxy point is readonly, readWrite or writeonly.
   */
  public BReadWriteMode getMode()
  {
    if(getParentPoint().isWritablePoint())
      return getPollMessage().isRequestDefined() ? BReadWriteMode.readWrite : BReadWriteMode.writeonly;
    return BReadWriteMode.readonly; 
  }

  public BFacets getSlotFacets(Slot slot)
  {
    if(slot.equals(readValue) || slot.equals(writeValue) )
       return getPointFacets();
    return super.getSlotFacets(slot);
  }


 /**
  * Override implementation of <code>BIBasicPollable</code>.
  * Causes communication to read the value of this point.
  */

 public void poll()
 {
  // If the parent point is out of service, or the parent
  // device is down, skip this poll.
  if ( isUnoperational() )
  {
   return;
  }
    
  BFlexSerialNetwork network = (BFlexSerialNetwork)getNetwork();
  if (network.getLog().isTraceOn()) network.getLog().trace(this + ".poll()");
      
  pollForData();
    network = null;
 }


 /**
  * This method will poll for data.
  */
 private void pollForData()
 {
   // do not poll if writeonly.
   if(getMode().equals(BReadWriteMode.writeonly))
   {
     readOk(getWriteValue());
     return;
   }
   BFlexRequestResponse pMessage = getPollMessage();
   String results = pMessage.sendMessage(null);
   //System.out.println("???????????????????? sendMessage results = " + results);
   if(results.equalsIgnoreCase("OK"))
   {
     try
     {
       if( ! getPollMessage().isResponseDefined() )
       {
         readFail("poll response message not defined!");
         return;
       }

       BStatusValue newStatusValue = decodeResponseStatusValue(pMessage.getResponse());
       if( newStatusValue != null )
       {
         readOk(newStatusValue);
         getDevice().pingOk();
       }
       else
       {
         readFail("unable to convert response");
       }
     }
     catch(Exception e)
     {
       e.printStackTrace();
       readFail(e.toString());
     }
   }
   else
   {
    if(results.equals("response timeout"))
      getDevice().pingFail(results);
    else
      readFail(results);
   }  
   
 }

  /**
   * This callback from asynchronous thread to send a write to the device.
   * Can be implemented by subclasses!
   *
   * @param BStatusValue out is the value to be sent to the foreign hardware
   */
  public void doWrite(BStatusValue value)
  {
    //System.out.println(" ~~~~~~~~~ doWrite callback with: " + value + " on point: " + getParentPoint().getName());
    //Thread.dumpStack();
    // If the parent point is out of service, or the parent
    // device is down, skip this write.
    if ( isUnoperational() )
    {
     return;
    }
    try
    {
      BFlexRequestResponse pMessage = getWriteMessage();
      String results = pMessage.sendMessage(null);
      //System.out.println("???????????????????? sendMessage results = " + results);
      if(results.equalsIgnoreCase("OK"))
      {
        if( ! getWriteMessage().isResponseDefined() )
        {
          writeOk(value);
          return;
        }
        try
        {
          BStatusValue newStatusValue = decodeResponseStatusValue(pMessage.getResponse());
          if( newStatusValue != null )
            writeOk(newStatusValue);
          else
            writeOk(value);
        }
        catch(Exception e)
        {
          e.printStackTrace();
          writeFail(e.toString());
        }
    
      }
      else
      {
        //System.out.println(" **** write fail with error: " + results);
        writeFail(results);
      }  
    }
    catch(Exception e)
    {
      e.printStackTrace();
      writeFail(e.toString());
    }
  }

  private BStatusValue decodeResponseStatusValue(BFlexResponseMessage rMessage)
  {
    BStatusValue newStatusValue = null;
    try
    {
      BFlexMessageBlock msgBlock = (BFlexMessageBlock)rMessage.get("instance");
      if(msgBlock == null)
      {
        rMessage.doCreateInstance();
        msgBlock = (BFlexMessageBlock)rMessage.get("instance");
      }
      
      BFlexMessageElement msgElement = msgBlock.getMessageElement(rMessage.getElementSelect().getElementSelect());
      if(msgElement == null)
        return null;
      //System.out.println("element value = " + msgElement.getValue());
      if(isNumeric())
      {
        newStatusValue = new BStatusNumeric( msgElement.getDoubleValue() );
      }
      else if(isBoolean())
      {
        newStatusValue = new BStatusBoolean( msgElement.getBooleanValue() );
      }
      else if(isString())
      {
        newStatusValue = new BStatusString( msgElement.getStringValue() );
      }
      else if(isEnum())
      {
        newStatusValue = new BStatusEnum( msgElement.getEnumValue() );
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return newStatusValue;
  }


  protected boolean isBoolean()
  {
  return getParentPoint().getOutStatusValue() instanceof BStatusBoolean;
  }
  
  protected boolean isNumeric()
  {
  return getParentPoint().getOutStatusValue() instanceof BStatusNumeric;
  }
  
  protected boolean isString()
  {
  return getParentPoint().getOutStatusValue() instanceof BStatusString;
  }
  
  protected boolean isEnum()
  {
  return getParentPoint().getOutStatusValue() instanceof BStatusEnum;
  }
  


}
