/*
 * Copyright 2008, Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitLon;

import javax.baja.lonworks.BLocalLonDevice;
import javax.baja.lonworks.BLonDevice;
import javax.baja.lonworks.datatypes.BLocal;
import javax.baja.lonworks.enums.BBufferCountEnum;
import javax.baja.lonworks.enums.BBufferSizeEnum;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;
import javax.baja.util.IFuture;
import javax.baja.util.Invocation;

import com.tridium.driver.util.DrByteArrayUtil;
import com.tridium.lonworks.netmessages.NetMessages;
import com.tridium.lonworks.netmessages.WriteMemRequest;
import com.tridium.lonworks.util.Neuron;
import com.tridium.lonworks.util.RunnableCommand;
import com.tridium.lonworks.util.selfdoc.ReadOnlyStruct;

/**
 * BBufferParams is a utility object used to modify the Network and Application
 * buffer sizes and counts in the Neuron of the local lonworks interface. See
 * Neuron Chip Data Book Appendex A.1.  It can only be added as a child of the 
 * localLonDevice.<p>
 * When this object is first added the size and count current used is read from
 * Neuron and stored. The total memory used for these original buffer settings is
 * stored in <code>originalSize</code>.<p>
 * The sizes and counts can be changed to new settings. As they are changed the 
 * <code>currentSize</code> is updated to indicate the total memory need to implement
 * the desired setting.  This can not exceed the <code>originalSize</code> or it 
 * might make the Neuron inoperable. When the counts and sizes are as desired the 
 * <code>updateBuffers</code> action can be invoked to write these new settings to
 * the Neuron. If <code>currentSize</code> is greater than <code>originalSize</code>
 * an exception is thrown and the Neuron is not modified.
 *
 *
 * @author    Robert Adams
 * @creation  16 Sept 08
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "rcvTransCnt",
  type = "int",
  defaultValue = "1",
  facets = @Facet("BFacets.makeNumeric(BUnit.NULL, 0, 1, 16)")
)
@NiagaraProperty(
  name = "appOutSize",
  type = "BBufferSizeEnum",
  defaultValue = "BBufferSizeEnum.DEFAULT"
)
@NiagaraProperty(
  name = "appInSize",
  type = "BBufferSizeEnum",
  defaultValue = "BBufferSizeEnum.DEFAULT"
)
@NiagaraProperty(
  name = "netOutSize",
  type = "BBufferSizeEnum",
  defaultValue = "BBufferSizeEnum.DEFAULT"
)
@NiagaraProperty(
  name = "netInSize",
  type = "BBufferSizeEnum",
  defaultValue = "BBufferSizeEnum.DEFAULT"
)
@NiagaraProperty(
  name = "appOutCnt",
  type = "BBufferCountEnum",
  defaultValue = "BBufferCountEnum.DEFAULT"
)
@NiagaraProperty(
  name = "appInCnt",
  type = "BBufferCountEnum",
  defaultValue = "BBufferCountEnum.DEFAULT"
)
@NiagaraProperty(
  name = "netOutCnt",
  type = "BBufferCountEnum",
  defaultValue = "BBufferCountEnum.DEFAULT"
)
@NiagaraProperty(
  name = "netInCnt",
  type = "BBufferCountEnum",
  defaultValue = "BBufferCountEnum.DEFAULT"
)
@NiagaraProperty(
  name = "priAppOutCnt",
  type = "BBufferCountEnum",
  defaultValue = "BBufferCountEnum.DEFAULT"
)
@NiagaraProperty(
  name = "priNetOutCnt",
  type = "BBufferCountEnum",
  defaultValue = "BBufferCountEnum.DEFAULT"
)
@NiagaraProperty(
  name = "originalSize",
  type = "int",
  defaultValue = "-1",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "currentSize",
  type = "int",
  defaultValue = "-1"
)
@NiagaraAction(
  name = "updateBuffers"
)
public class BBufferParams
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitLon.BBufferParams(3413051496)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "rcvTransCnt"

  /**
   * Slot for the {@code rcvTransCnt} property.
   * @see #getRcvTransCnt
   * @see #setRcvTransCnt
   */
  public static final Property rcvTransCnt = newProperty(0, 1, BFacets.makeNumeric(BUnit.NULL, 0, 1, 16));

  /**
   * Get the {@code rcvTransCnt} property.
   * @see #rcvTransCnt
   */
  public int getRcvTransCnt() { return getInt(rcvTransCnt); }

  /**
   * Set the {@code rcvTransCnt} property.
   * @see #rcvTransCnt
   */
  public void setRcvTransCnt(int v) { setInt(rcvTransCnt, v, null); }

  //endregion Property "rcvTransCnt"

  //region Property "appOutSize"

  /**
   * Slot for the {@code appOutSize} property.
   * @see #getAppOutSize
   * @see #setAppOutSize
   */
  public static final Property appOutSize = newProperty(0, BBufferSizeEnum.DEFAULT, null);

  /**
   * Get the {@code appOutSize} property.
   * @see #appOutSize
   */
  public BBufferSizeEnum getAppOutSize() { return (BBufferSizeEnum)get(appOutSize); }

  /**
   * Set the {@code appOutSize} property.
   * @see #appOutSize
   */
  public void setAppOutSize(BBufferSizeEnum v) { set(appOutSize, v, null); }

  //endregion Property "appOutSize"

  //region Property "appInSize"

  /**
   * Slot for the {@code appInSize} property.
   * @see #getAppInSize
   * @see #setAppInSize
   */
  public static final Property appInSize = newProperty(0, BBufferSizeEnum.DEFAULT, null);

  /**
   * Get the {@code appInSize} property.
   * @see #appInSize
   */
  public BBufferSizeEnum getAppInSize() { return (BBufferSizeEnum)get(appInSize); }

  /**
   * Set the {@code appInSize} property.
   * @see #appInSize
   */
  public void setAppInSize(BBufferSizeEnum v) { set(appInSize, v, null); }

  //endregion Property "appInSize"

  //region Property "netOutSize"

  /**
   * Slot for the {@code netOutSize} property.
   * @see #getNetOutSize
   * @see #setNetOutSize
   */
  public static final Property netOutSize = newProperty(0, BBufferSizeEnum.DEFAULT, null);

  /**
   * Get the {@code netOutSize} property.
   * @see #netOutSize
   */
  public BBufferSizeEnum getNetOutSize() { return (BBufferSizeEnum)get(netOutSize); }

  /**
   * Set the {@code netOutSize} property.
   * @see #netOutSize
   */
  public void setNetOutSize(BBufferSizeEnum v) { set(netOutSize, v, null); }

  //endregion Property "netOutSize"

  //region Property "netInSize"

  /**
   * Slot for the {@code netInSize} property.
   * @see #getNetInSize
   * @see #setNetInSize
   */
  public static final Property netInSize = newProperty(0, BBufferSizeEnum.DEFAULT, null);

  /**
   * Get the {@code netInSize} property.
   * @see #netInSize
   */
  public BBufferSizeEnum getNetInSize() { return (BBufferSizeEnum)get(netInSize); }

  /**
   * Set the {@code netInSize} property.
   * @see #netInSize
   */
  public void setNetInSize(BBufferSizeEnum v) { set(netInSize, v, null); }

  //endregion Property "netInSize"

  //region Property "appOutCnt"

  /**
   * Slot for the {@code appOutCnt} property.
   * @see #getAppOutCnt
   * @see #setAppOutCnt
   */
  public static final Property appOutCnt = newProperty(0, BBufferCountEnum.DEFAULT, null);

  /**
   * Get the {@code appOutCnt} property.
   * @see #appOutCnt
   */
  public BBufferCountEnum getAppOutCnt() { return (BBufferCountEnum)get(appOutCnt); }

  /**
   * Set the {@code appOutCnt} property.
   * @see #appOutCnt
   */
  public void setAppOutCnt(BBufferCountEnum v) { set(appOutCnt, v, null); }

  //endregion Property "appOutCnt"

  //region Property "appInCnt"

  /**
   * Slot for the {@code appInCnt} property.
   * @see #getAppInCnt
   * @see #setAppInCnt
   */
  public static final Property appInCnt = newProperty(0, BBufferCountEnum.DEFAULT, null);

  /**
   * Get the {@code appInCnt} property.
   * @see #appInCnt
   */
  public BBufferCountEnum getAppInCnt() { return (BBufferCountEnum)get(appInCnt); }

  /**
   * Set the {@code appInCnt} property.
   * @see #appInCnt
   */
  public void setAppInCnt(BBufferCountEnum v) { set(appInCnt, v, null); }

  //endregion Property "appInCnt"

  //region Property "netOutCnt"

  /**
   * Slot for the {@code netOutCnt} property.
   * @see #getNetOutCnt
   * @see #setNetOutCnt
   */
  public static final Property netOutCnt = newProperty(0, BBufferCountEnum.DEFAULT, null);

  /**
   * Get the {@code netOutCnt} property.
   * @see #netOutCnt
   */
  public BBufferCountEnum getNetOutCnt() { return (BBufferCountEnum)get(netOutCnt); }

  /**
   * Set the {@code netOutCnt} property.
   * @see #netOutCnt
   */
  public void setNetOutCnt(BBufferCountEnum v) { set(netOutCnt, v, null); }

  //endregion Property "netOutCnt"

  //region Property "netInCnt"

  /**
   * Slot for the {@code netInCnt} property.
   * @see #getNetInCnt
   * @see #setNetInCnt
   */
  public static final Property netInCnt = newProperty(0, BBufferCountEnum.DEFAULT, null);

  /**
   * Get the {@code netInCnt} property.
   * @see #netInCnt
   */
  public BBufferCountEnum getNetInCnt() { return (BBufferCountEnum)get(netInCnt); }

  /**
   * Set the {@code netInCnt} property.
   * @see #netInCnt
   */
  public void setNetInCnt(BBufferCountEnum v) { set(netInCnt, v, null); }

  //endregion Property "netInCnt"

  //region Property "priAppOutCnt"

  /**
   * Slot for the {@code priAppOutCnt} property.
   * @see #getPriAppOutCnt
   * @see #setPriAppOutCnt
   */
  public static final Property priAppOutCnt = newProperty(0, BBufferCountEnum.DEFAULT, null);

  /**
   * Get the {@code priAppOutCnt} property.
   * @see #priAppOutCnt
   */
  public BBufferCountEnum getPriAppOutCnt() { return (BBufferCountEnum)get(priAppOutCnt); }

  /**
   * Set the {@code priAppOutCnt} property.
   * @see #priAppOutCnt
   */
  public void setPriAppOutCnt(BBufferCountEnum v) { set(priAppOutCnt, v, null); }

  //endregion Property "priAppOutCnt"

  //region Property "priNetOutCnt"

  /**
   * Slot for the {@code priNetOutCnt} property.
   * @see #getPriNetOutCnt
   * @see #setPriNetOutCnt
   */
  public static final Property priNetOutCnt = newProperty(0, BBufferCountEnum.DEFAULT, null);

  /**
   * Get the {@code priNetOutCnt} property.
   * @see #priNetOutCnt
   */
  public BBufferCountEnum getPriNetOutCnt() { return (BBufferCountEnum)get(priNetOutCnt); }

  /**
   * Set the {@code priNetOutCnt} property.
   * @see #priNetOutCnt
   */
  public void setPriNetOutCnt(BBufferCountEnum v) { set(priNetOutCnt, v, null); }

  //endregion Property "priNetOutCnt"

  //region Property "originalSize"

  /**
   * Slot for the {@code originalSize} property.
   * @see #getOriginalSize
   * @see #setOriginalSize
   */
  public static final Property originalSize = newProperty(Flags.READONLY, -1, null);

  /**
   * Get the {@code originalSize} property.
   * @see #originalSize
   */
  public int getOriginalSize() { return getInt(originalSize); }

  /**
   * Set the {@code originalSize} property.
   * @see #originalSize
   */
  public void setOriginalSize(int v) { setInt(originalSize, v, null); }

  //endregion Property "originalSize"

  //region Property "currentSize"

  /**
   * Slot for the {@code currentSize} property.
   * @see #getCurrentSize
   * @see #setCurrentSize
   */
  public static final Property currentSize = newProperty(0, -1, null);

  /**
   * Get the {@code currentSize} property.
   * @see #currentSize
   */
  public int getCurrentSize() { return getInt(currentSize); }

  /**
   * Set the {@code currentSize} property.
   * @see #currentSize
   */
  public void setCurrentSize(int v) { setInt(currentSize, v, null); }

  //endregion Property "currentSize"

  //region Action "updateBuffers"

  /**
   * Slot for the {@code updateBuffers} action.
   * @see #updateBuffers()
   */
  public static final Action updateBuffers = newAction(0, null);

  /**
   * Invoke the {@code updateBuffers} action.
   * @see #updateBuffers
   */
  public void updateBuffers() { invoke(updateBuffers, null, null); }

  //endregion Action "updateBuffers"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBufferParams.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  public BBufferParams() {}
  
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BLocalLonDevice; 
  } 
  
  public void started()
    throws Exception
  {
    super.started();
    if(getOriginalSize()<0) initParams();
  }  
  
  public void initParams()
  {
    BLonDevice dev = (BLonDevice)getParent();
    
    RunnableCommand r = new RunnableCommand(dev)
      {
        public void run() { doInitParams((BLonDevice)arg1); }
      };
      
    dev.postAsync(r);
  }

  /**
   * Route async actions
   */
  public IFuture post(Action action, BValue arg, Context cx)
  {
    if (action.equals(updateBuffers)) return postUpdateBuffers(cx);
    return super.post(action, arg, cx);
  }

  private IFuture postUpdateBuffers(Context cx)
  {
    BLonDevice dev = (BLonDevice)getParent();
    return dev.postAsync(new Invocation(this, updateBuffers, null, cx));
  }


  public void doInitParams(BLonDevice dev)
  {
    if(getOriginalSize()!=-1) return;
    
    try
    {
      ReadOnlyStruct ro = ReadOnlyStruct.make(BLocal.local, dev.lonComm(), dev.authenticate(), false);
      
      setRcvTransCnt (ro.getRcvTransCount()+1);
      setAppOutSize  (BBufferSizeEnum .make(ro.getAppBufOutSize()   ));
      setAppInSize   (BBufferSizeEnum .make(ro.getAppBufInSize()    ));
      setNetOutSize  (BBufferSizeEnum .make(ro.getNetBufOutSize()   ));
      setNetInSize   (BBufferSizeEnum .make(ro.getNetBufInSize()    ));
      setAppOutCnt   (BBufferCountEnum.make(ro.getAppBufOutCount()  ));
      setAppInCnt    (BBufferCountEnum.make(ro.getAppBufInCount()   ));
      setNetOutCnt   (BBufferCountEnum.make(ro.getNetBufOutCount()  ));
      setNetInCnt    (BBufferCountEnum.make(ro.getNetBufInCount()   ));
      setPriAppOutCnt(BBufferCountEnum.make(ro.getAppBufOutPriorityCount()  ));   
      setPriNetOutCnt(BBufferCountEnum.make(ro.getNetBufOutPriorityCount()  )); 
      
      setOriginalSize(calculateSize());
    }
    catch(Throwable e)
    {
      e.printStackTrace();
    }
  }

  public void changed(Property prop, Context context)
  {
    super.changed(prop,context);
    
    setCurrentSize(calculateSize());
  }

  public void doUpdateBuffers()
  {
    if(getCurrentSize()>getOriginalSize())
      throw new LocalizableRuntimeException("kitLon","buffercheck");
    
    byte[] data = new byte[6];

    data[0] = (byte)(getRcvTransCnt()-1 & 0x0f);
    data[1] = (byte)(((getAppOutSize  ().getOrdinal() & 0x0f)<<4) | (getAppInSize   ().getOrdinal() & 0x0f));
    data[2] = (byte)(((getNetOutSize  ().getOrdinal() & 0x0f)<<4) | (getNetInSize   ().getOrdinal() & 0x0f));
    data[3] = (byte)(((getPriNetOutCnt().getOrdinal() & 0x0f)<<4) | (getPriAppOutCnt().getOrdinal() & 0x0f));
    data[4] = (byte)(((getAppOutCnt   ().getOrdinal() & 0x0f)<<4) | (getAppInCnt    ().getOrdinal() & 0x0f));
    data[5] = (byte)(((getNetOutCnt   ().getOrdinal() & 0x0f)<<4) | (getNetInCnt    ().getOrdinal() & 0x0f));
   
    try
    {
      BLonDevice dev = (BLonDevice)getParent();

      byte[] ra = Neuron.readMemory(NetMessages.READ_ONLY_RELATIVE,dev, 0x17, 1);
      data[0] = (byte)((ra[0] & 0xf0) | (data[0] & 0x0f));

      // Attempt to write new buffer params to neuron
      WriteMemRequest writeReq = new WriteMemRequest(NetMessages.READ_ONLY_RELATIVE,
                                         0x17,
                                         data.length,
                                         NetMessages.BOTH_CS_RECALC | NetMessages.ONLY_RESET,
                                         data);
      ((BLonDevice)getParent()).lonComm().sendUnacknowledged(BLocal.local, writeReq);
    }
    catch(Throwable e)
    {
      e.printStackTrace();
    }
    
  }
  
  
  private int calculateSize()
  {
    return (getRcvTransCnt() * 13) + 
           (getAppOutSize().getSize() * getAppOutCnt().getCount()   ) + 
           (getAppInSize().getSize()  * getAppInCnt().getCount()    ) +
           (getNetOutSize().getSize() * getNetOutCnt().getCount()   ) +
           (getNetInSize().getSize()  * getNetInCnt().getCount()    ) +
           (getAppOutSize().getSize() * getPriAppOutCnt().getCount()) +
           (getNetOutSize().getSize() * getPriNetOutCnt().getCount()) ;
  }
  
  
}      
