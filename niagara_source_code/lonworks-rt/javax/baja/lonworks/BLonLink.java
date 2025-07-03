/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import javax.baja.lonworks.datatypes.BNvConfigData;
import javax.baja.lonworks.enums.BLonLinkType;
import javax.baja.lonworks.londata.BLonData;
import javax.baja.lonworks.londata.BLonPrimitive;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.BNullConverter;

/**
 * BLonLink represents links between NetworkVariables and MessageTags
 * on LonDevices.
 * <p>
 * NOTE: Changed to be subclass of BConversionLink in lonworks 3.6.32. 
 * Prior to that build was subclass of BLink.  This change allows link
 * validation to behave the same when linking subclasses of BNetworkVariable.
 *
 * @author    Robert Adams
 * @creation  06 June 02
 * @version   $Revision: 3$ $Date: 10/18/01 2:56:40 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Type of service used to update link.
 */
@NiagaraProperty(
  name = "linkType",
  type = "BLonLinkType",
  defaultValue = "BLonLinkType.standard"
)
/*
 Use priority slot for link.
 */
@NiagaraProperty(
  name = "priority",
  type = "boolean",
  defaultValue = "false"
)
/*
 Is this link between message tags.  If not then links nvs.
 */
@NiagaraProperty(
  name = "messageTag",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "remoteLink",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "pseudoLink",
  type = "boolean",
  defaultValue = "false"
)
public class BLonLink
  extends BConversionLink
{

  /**
   * Construct a direct BLink.
   */
  public BLonLink(BComponent source, Slot sourceSlot, Slot targetSlot)
  {
    super(source, sourceSlot, targetSlot, new BNullConverter());
  }

  /**
   * Construct an indirect BLink.
   */
  public BLonLink(BOrd sourceOrd, String sourceSlot, String targetSlot, boolean enabled)
  {
    super(sourceOrd, sourceSlot, targetSlot, enabled, new BNullConverter());
  }

  /**  Default no argument  */
  public BLonLink() {/* setEnabled(false);*/ }


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.BLonLink(1040246171)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "linkType"

  /**
   * Slot for the {@code linkType} property.
   * Type of service used to update link.
   * @see #getLinkType
   * @see #setLinkType
   */
  public static final Property linkType = newProperty(0, BLonLinkType.standard, null);

  /**
   * Get the {@code linkType} property.
   * Type of service used to update link.
   * @see #linkType
   */
  public BLonLinkType getLinkType() { return (BLonLinkType)get(linkType); }

  /**
   * Set the {@code linkType} property.
   * Type of service used to update link.
   * @see #linkType
   */
  public void setLinkType(BLonLinkType v) { set(linkType, v, null); }

  //endregion Property "linkType"

  //region Property "priority"

  /**
   * Slot for the {@code priority} property.
   * Use priority slot for link.
   * @see #getPriority
   * @see #setPriority
   */
  public static final Property priority = newProperty(0, false, null);

  /**
   * Get the {@code priority} property.
   * Use priority slot for link.
   * @see #priority
   */
  public boolean getPriority() { return getBoolean(priority); }

  /**
   * Set the {@code priority} property.
   * Use priority slot for link.
   * @see #priority
   */
  public void setPriority(boolean v) { setBoolean(priority, v, null); }

  //endregion Property "priority"

  //region Property "messageTag"

  /**
   * Slot for the {@code messageTag} property.
   * Is this link between message tags.  If not then links nvs.
   * @see #getMessageTag
   * @see #setMessageTag
   */
  public static final Property messageTag = newProperty(0, false, null);

  /**
   * Get the {@code messageTag} property.
   * Is this link between message tags.  If not then links nvs.
   * @see #messageTag
   */
  public boolean getMessageTag() { return getBoolean(messageTag); }

  /**
   * Set the {@code messageTag} property.
   * Is this link between message tags.  If not then links nvs.
   * @see #messageTag
   */
  public void setMessageTag(boolean v) { setBoolean(messageTag, v, null); }

  //endregion Property "messageTag"

  //region Property "remoteLink"

  /**
   * Slot for the {@code remoteLink} property.
   * @see #getRemoteLink
   * @see #setRemoteLink
   */
  public static final Property remoteLink = newProperty(0, false, null);

  /**
   * Get the {@code remoteLink} property.
   * @see #remoteLink
   */
  public boolean getRemoteLink() { return getBoolean(remoteLink); }

  /**
   * Set the {@code remoteLink} property.
   * @see #remoteLink
   */
  public void setRemoteLink(boolean v) { setBoolean(remoteLink, v, null); }

  //endregion Property "remoteLink"

  //region Property "pseudoLink"

  /**
   * Slot for the {@code pseudoLink} property.
   * @see #getPseudoLink
   * @see #setPseudoLink
   */
  public static final Property pseudoLink = newProperty(0, false, null);

  /**
   * Get the {@code pseudoLink} property.
   * @see #pseudoLink
   */
  public boolean getPseudoLink() { return getBoolean(pseudoLink); }

  /**
   * Set the {@code pseudoLink} property.
   * @see #pseudoLink
   */
  public void setPseudoLink(boolean v) { setBoolean(pseudoLink, v, null); }

  //endregion Property "pseudoLink"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonLink.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  // Called when knob added to insure that destination nv has been accessed.
  // This is needed because if lonlink is deleted before getDestinationNv() 
  // has been accessed an exception will be thrown.
  /** FOR INTERNAL USE */
  public void lonActivate() 
  {
    if(getMessageTag()) return;     
    getDestinationNv(); 
  }
  
  /** Copy data from source nv to target nv. */
  void propagateNv(BNetworkVariable srcNv)
  {
    if(getMessageTag()) return;     
    
    BNetworkVariable destNv = getDestinationNv();

    // Don't propagate if nvs are bound to each other -
    // this link already updated by nvUpdate between devices.
    // Remote & Pseudo link must always update
    if(!getRemoteLink() && !getPseudoLink() && isBound(srcNv,destNv))
    {
     //*update debug*/System.out.println("don't propagate lonLink - isBound=" + isBound(srcNv,destNv));
       return;
    }
    
     //*update debug*/System.out.println(NmUtil.timeStamp() + "  " + srcNv.debugName() + " propagate to " + destNv.debugName());

    // If the types are equivalent then copy. 
    BLonData srcData =  srcNv.getData();
    BLonData destData = destNv.getData();
    if( !propagateObjects(srcData,destData) )
    {
       // If not equivalent use from/to netbytes to propagate.
       destData.fromNetBytes(srcData.toNetBytes());
    }
    // After all data sets, notify destNv of change.
    destNv.dataChanged(null);
  }

  /** 
   * Determine if this link is bound. Both src and dest nvs must
   * be bound with the same selector. 
   */
  public boolean isBound(BNetworkVariable srcNv)
  {
    if(getMessageTag()) return false;     
    return isBound(srcNv, getDestinationNv());
  }

  private boolean isBound(BNetworkVariable srcNv, BNetworkVariable destNv)
  {
    BNvConfigData srcNvCfg = srcNv.getNvConfigData();
    BNvConfigData destNvCfg = destNv.getNvConfigData();

    // Check that src and dest are bound to each other.
    if(srcNvCfg.isBoundNv() && destNvCfg.isBoundNv())
    {
      if(srcNvCfg.getSelector() == destNvCfg.getSelector()) return true;
      // Selectors don't match check for an alias on the src
      if(srcNv.lonDevice().getAlias(srcNv,0)!=null) return true;
    }
    return false;
  }

  // Used by BLonDevice
  public BNetworkVariable getDestinationNv()
  {
    // don't cache this as nv can change during runtime operations
    BComponent t = getTargetComponent();
    Property tProp = getTargetSlot().asProperty();
    return (BNetworkVariable)t.get(tProp);
  }

  private BNetworkVariable getSourceNv()
  {
    // don't cache this as nv can change during runtime operations
    BComponent s = getSourceComponent();
    Property sProp = getSourceSlot().asProperty();
    return (BNetworkVariable)s.get(sProp);
  }

  // Copy src data to dest data.  If there is a type mismatch return false.
  private boolean propagateObjects(BLonData src, BLonData dest)
  {
    Property[] sa = src.getPropertiesArray();
    Property[] da = dest.getPropertiesArray();

    if(sa.length != da.length) return false;
    //  throw new BajaRuntimeException("Error in propagateObjects.");

    try
    {
      for(int i=0 ; i<sa.length ; i++)
      {
        // Propagate BLonPrimitive or BLonData - ignore others
        // NOTE: this assumes any dynamic properties will follow data elements
        Property srcProp = sa[i];
        Property destProp = da[i];
        
        if( srcProp.getType() != destProp.getType() ) return false;
        
        if(srcProp.getType().is(BLonPrimitive.TYPE))
        {
            dest.set(destProp,src.get(srcProp),BLonNetwork.lonNoWrite);
        }
        else if(srcProp.getType().is(BLonData.TYPE))
        {
          if( !propagateObjects((BLonData)src.get(srcProp), (BLonData)dest.get(destProp)) )
            return false;
        }  
      }
    }
    catch(Throwable e)
    {
      System.out.println("Error in propagateObjects." + toString(null) + "\n" + e);
      return false;
    }
    return true;
  }

  /** Short circuit propagate.  */
  public final void propagate(BValue arg)
  {
    // NOTE: This is called when the link is activated. Must allow
    // activation to establish knob on source side.
    
    if(getMessageTag()) return;

    // PropagateNv's activation will occur after steady state when user is adding
    // a link, this will push values when link first added.
    BNetworkVariable srcNv = getSourceNv();
    if(!srcNv.isRunning()) return;
    propagateNv(srcNv);

  }


  public String toString(Context c)
  {
    return (getMessageTag() ? "msgTag " : "") + super.toString(c) + "| linkType = " + getLinkType() + " priority = " + getPriority();
  }

////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////

}
