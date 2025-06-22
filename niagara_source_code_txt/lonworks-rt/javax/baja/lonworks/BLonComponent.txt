/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import javax.baja.lonworks.londata.BLonData;
import javax.baja.lonworks.londata.BLonPrimitive;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;

import com.tridium.lonworks.Lon;

/**
 *   BLonComponent is the base component for lonworks device variable
 *   components with qualified data (BNetworkVariable, BNetworkConfig,
 *   ConfigParameter).  It provides support for converting
 *   LonDataElements from/to their byte representation.
 * <p>
 *
 * @author    Robert Adams
 * @creation  8 Nov 00
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:42 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Read data from physical device.
 */
@NiagaraAction(
  name = "forceRead",
  flags = Flags.ASYNC
)
/*
 Write data to physical device.
 */
@NiagaraAction(
  name = "forceWrite",
  flags = Flags.ASYNC
)
/*
 This action will update the data elements with BLonNetwork.lonNoWrite context.
 It is used to push implementation to station where lonNoWrite context can be used.
 */
@NiagaraAction(
  name = "update",
  parameterType = "BLonData",
  defaultValue = "new BLonData()",
  flags = Flags.HIDDEN
)
public abstract class BLonComponent
  extends BLonData
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.BLonComponent(4235407541)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "forceRead"

  /**
   * Slot for the {@code forceRead} action.
   * Read data from physical device.
   * @see #forceRead()
   */
  public static final Action forceRead = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code forceRead} action.
   * Read data from physical device.
   * @see #forceRead
   */
  public void forceRead() { invoke(forceRead, null, null); }

  //endregion Action "forceRead"

  //region Action "forceWrite"

  /**
   * Slot for the {@code forceWrite} action.
   * Write data to physical device.
   * @see #forceWrite()
   */
  public static final Action forceWrite = newAction(Flags.ASYNC, null);

  /**
   * Invoke the {@code forceWrite} action.
   * Write data to physical device.
   * @see #forceWrite
   */
  public void forceWrite() { invoke(forceWrite, null, null); }

  //endregion Action "forceWrite"

  //region Action "update"

  /**
   * Slot for the {@code update} action.
   * This action will update the data elements with BLonNetwork.lonNoWrite context.
   * It is used to push implementation to station where lonNoWrite context can be used.
   * @see #update(BLonData parameter)
   */
  public static final Action update = newAction(Flags.HIDDEN, new BLonData(), null);

  /**
   * Invoke the {@code update} action.
   * This action will update the data elements with BLonNetwork.lonNoWrite context.
   * It is used to push implementation to station where lonNoWrite context can be used.
   * @see #update
   */
  public void update(BLonData parameter) { invoke(update, parameter, null); }

  //endregion Action "update"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonComponent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * No arg constructor
   */
  public BLonComponent() {}

  /** Return true if parent is a BLoadableDevice or BLoadableObject. */  
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BLonDevice ||
           parent instanceof BLonObject;
  }
////////////////////////////////////////////////////////////
//  Parent Utilities
////////////////////////////////////////////////////////////
  public BLonDevice getDevice()
  {
    if(dev == null)
    {
      for(BComplex p = this; (dev == null) && (p != null); p = p.getParent())
        if (p.getType().is(BLonDevice.TYPE)) dev = (BLonDevice)p;
    }
    return dev;
  }
  private BLonDevice dev = null;

  private BLonNetwork getNetwork()
  {
    if(network!=null) return network;

    BComplex p = getParent();
    while(!(p instanceof BLonNetwork)) p = p.getParent();
    network = (BLonNetwork)p;

    return network;
  }
  private BLonNetwork network = null;

////////////////////////////////////////////////////////////
//  Overrides
////////////////////////////////////////////////////////////
  public final void started()
    throws Exception
  {
    super.started();

    // Force reevaluation of dev and network
    dev = null;
    network = null;
    
    // Hide the write action on non-writeables
    if(!isWriteable()) setFlags(forceWrite, getFlags(forceWrite) | Flags.HIDDEN);
    
    // Call readOk on ncis and cps to clear stale flag on any associated proxy points
    if(isForeignPersistent()) readOk();
    
    // Check for illegal length
    if((isNetworkVariable() || isNetworkConfig() ) && getByteLength()>Lon.maxNvLength())
    {
      getDevice().log().severe(getDisplayName(null) + " disabled because length " + getByteLength() + " > " + Lon.maxNvLength() + " bytes");
      illegalLength = true;
    }
 
    lonComponentStarted();
  }

  public final void stopped()
    throws Exception
  {
    super.stopped();
    lonComponentStopped();
    dev = null;
    network = null;
  }

  public void lonComponentStarted() {}
  public void lonComponentStopped() {}
  
  public boolean isNavChild() { return Lon.lcInNavTree();}



////////////////////////////////////////////////////////////
//  TEMP
////////////////////////////////////////////////////////////
  /**
   * The LonComponent will have a "data" property if data
   * required subclassing BLonData. If not data elements 
   * are direct children and this object will be returned 
   * cast as a BLonData.
   */
  public BLonData getData() 
  {
    BObject dat = get("data");
    if(dat!=null && dat instanceof BLonData)
    {
      return (BLonData)dat;
    }
   
    return this;
  }

  /**
   * If the  argument is a subclass of BLonData then elements
   * will be copied to this object.  If an instance of BLonData
   * is passed then it will be set as "data" property on this
   * object. Existing data elements will be removed.
   */
  public void setData(BLonData v) 
  {
    // If subclass of BLonData add directly as property "data"
    String typName = v.getType().getTypeName();
    if(!typName.equals(BLonData.TYPE.getTypeName()))
    {
      if(get("data")!=null)
       set("data",v);
      else 
       add("data",v);
      
      return;
    }
  
    // Remove existing data elements
    Property[] a = getPropertiesArray();
    for(int i=0 ; i<a.length ; i++)
    {
      if(isDataProp(a[i])) remove(a[i]);
    }  
    
    // Add data elements from v
    a = v.getPropertiesArray();
    for(int i=0 ; i<a.length ; i++)
    {
      Property prop = a[i];
      if(isDataProp(prop)) add(prop.getName(), v.get(prop), v.getFlags(prop), prop.getFacets(),null);
    }  
  }
  
  /**
   *  This is intended to return a copy of data which can then be
   *  modified without forcing any writes to the the device.  Once changes
   *  are complete user should call <code>updateData()</code> to apply changes 
   *  before writing data.
   *  @return Returns a copy of the BLonData component of this BLonComponent.
   */
  public final BLonData copyData()
  {
    return (BLonData)getData().newCopy(true);
  }
  
  /** 
   *  Copy the supplied data to this BLonComponent using the BLonNetwork.lonNoWrite context.
   *   This will allow all data elements to be set without causing multiple updates to the device.
   *   This is intended for use with the data returned from a call to <code>copyData()</code>.
   *   <p>  This method may be called from the station or client side. <p>
   *   @param data data to write to this BLonComponent - must be of the same type and have the same elements
   *   @param write if true then force a write to device after all data is set
   */
  public final void updateData(BLonData data, boolean write)
  {
    if(isRunning()) 
    {
      doUpdate(data);
      if(write) doForceWrite();
    }  
    else
    {
      update(data);
      if(write) forceWrite();
    }  
  }  
  
  /** For Internal use. Implementation of hidden action */
  public final void doUpdate(BLonData dat)
  {
    BLonData myData = (BLonData)get("data");
    if(myData==null) myData=this;

  //  origData.copyFrom(dat,BLonNetwork.lonNoWrite); - this only works with frozen slots
    copyData(dat,myData); 
    
    // Force propagation of linked nvs.
    dataChanged(BLonNetwork.lonNoWrite);
  }
  
  private void copyData(BLonData src,BLonData dest)
  {
    Property[] psrc = src.getPropertiesArray();
    Property[] pdest = dest.getPropertiesArray();

    if(psrc.length!=pdest.length) throw new BajaRuntimeException("Unmatched Type in doUpdate " + getDisplayName(null));
    for(int i=0; i<psrc.length ; i++)
    {
      if(psrc[i].getType()!= pdest[i].getType()) throw new BajaRuntimeException("Unmatched Type in doUpdate " + getDisplayName(null));
      // Copy primitives
      if(psrc[i].getType().is(BLonPrimitive.TYPE))
        dest.set(pdest[i],src.get(psrc[i]),BLonNetwork.lonNoPropagateNoWrite);
      // Recurse into BLonData  
      else if(psrc[i].getType().is(BLonData.TYPE))
        copyData((BLonData)src.get(psrc[i]),(BLonData)dest.get(pdest[i]));
      // Ignore the rest  
    }
  }
  
////////////////////////////////////////////////////////////
//  Read/Write action support
////////////////////////////////////////////////////////////

 /**
   * Route async actions
   */
  public IFuture post(Action action, BValue arg, Context cx)
  {
    if (action.equals(forceRead)) return postForceRead(cx);
    if (action.equals(forceWrite)) return postForceWrite(cx);
    return super.post(action, arg, cx);
  }

  protected IFuture postForceRead(Context cx)
  {
   //System.out.println("postRead  " + getDisplayName(null)) ;
    return getNetwork().postAsync(new Invocation(this, forceRead, null, cx));
  }

  protected IFuture postForceWrite(Context cx)
  {
    if(!isWriteable())// throw new BajaRuntimeException(getDevice().getDisplayName(null) + ":" + getDisplayName(null) + " is not writeable.");
      throw new LocalizableRuntimeException("lonworks","lonComponent.notWritable");
      
   //System.out.println("postWrite  " + getDisplayName(null)) ;
    return getNetwork().postWrite(new Invocation(this, forceWrite, null, cx));
  }


////////////////////////////////////////////////////////////
//  BILoadable Support
////////////////////////////////////////////////////////////
 
  /**
   *  Override point for BLonComponents to write their data to the device.<p>
   *  The write is performed on the calling thread.
   */
  public abstract void doForceWrite();

  /**
   *  Override point for BLonComponents to read their data from the device.<p>
   *  The read is performed on the calling thread.
   */
  public abstract void doForceRead();


  /**
   * Does this component represent data stored persistently in the device.
   * This will normally be configuration data.
   * <p>
   * @return Returns false - subclasses should override to return true if needed.
   */
  public boolean isForeignPersistent() { return false; }

  /**
   * Does this component represent a writable value in device. Default returns true.
   */
  public boolean isWriteable() { return true; }


////////////////////////////////////////////////////////////
//  LonComponent api
////////////////////////////////////////////////////////////
  /**
   * Return the BLonDevice which contains this Component.
   * <p>
   * @return Parent device.
   */
  public BLonDevice lonDevice() { return getDevice(); }

  /**
   * Return the BLonNetwork which contains this Component.
   * <p>
   * @return Parent network.
   */
  public BLonNetwork lonNetwork() { return getNetwork(); }

  /** Is this LonComponent a BNetworkVariable. */
  public boolean isNetworkVariable() { return false; }
  /** Is this LonComponent a BNetworkConfig. */
  public boolean isNetworkConfig()   { return false; }
  /** Is this LonComponent a BConfigParameter. */
  public boolean isConfigParameter() { return false; }
  /** Is this LonComponent a BLocalNetworkVariable */
  public boolean isLocalNv() { return false; }
  /** Is this LonComponent a BCLocalNetworkConfig */
  public boolean isLocalNci() { return false; }


  public void subscribed()
  {
   // if(!isForeignPersistent())
    {
      readSubscribed();
     //System.out.println("subscribed " + getParent().getDisplayName(null) + ":" + getDisplayName(null) + " " + subscribeCount);
    } 
  }
  public void unsubscribed()
  {
   // if(!isForeignPersistent())
    {
      readUnsubscribed();
     //System.out.println("unsubscribed " + getParent().getDisplayName(null) + ":" + getDisplayName(null) + " " + subscribeCount);
    }   
  }

  /** Receive point subscriptions. Increment subscribeCount. */
  public void readSubscribed()
  {
    subscribeCount++;
    if(subscribeCount==1)
    {
      // When first subscribed read the property.
      lonComponentSubscribed();
    }
  }

  public String debugName()
   { return getParent().getDisplayName(null) + ":" + getDisplayName(null) + " "; }


  /** Receive point subscriptions. Decrement subscribeCount */
  public void readUnsubscribed()
  {
    subscribeCount--;
    if(subscribeCount==0) lonComponentUnsubscribed();
  }
  
  /** Callback to indicate LonComponent transition from 0 to 1 subscriber */
  protected void lonComponentSubscribed() {}
  /**  Callback to indicate LonComponent transition from 1 to 0 subscriber */
  protected void lonComponentUnsubscribed() {}

////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////
  protected int subscribeCount = 0;
  public boolean illegalLength = false;
}
