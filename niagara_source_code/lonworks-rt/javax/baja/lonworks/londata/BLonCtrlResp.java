/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import javax.baja.lonworks.enums.BLonControlRespEnum;
import javax.baja.lonworks.enums.BLonElementType;
import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;


/**
 *   This class file represents SNVT_ctrl_resp.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  4 Sept 01
 * @version   $Revision: 3$ $Date: 9/28/01 10:20:43 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "status",
  type = "BLonEnum",
  defaultValue = "BLonEnum.make(BLonControlRespEnum.DEFAULT)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.e8, null)")
)
@NiagaraProperty(
  name = "senderId",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u16, 1, 65534, 1, 65535, 1, 0, null)")
)
@NiagaraProperty(
  name = "senderRangeLower",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u16, 1, 65534, 1, 65535, 1, 0, null)")
)
@NiagaraProperty(
  name = "senderRangeUpper",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u16, 1, 65534, 1, 65535, 3, 0, null)")
)
@NiagaraProperty(
  name = "controllerId",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u16, 1, 65534, 1, 65535, 5, 0, null)")
)
public class BLonCtrlResp
  extends BLonData
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonCtrlResp(2100734966)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(0, BLonEnum.make(BLonControlRespEnum.DEFAULT), LonFacetsUtil.makeFacets(BLonElementType.e8, null));

  /**
   * Get the {@code status} property.
   * @see #status
   */
  public BLonEnum getStatus() { return (BLonEnum)get(status); }

  /**
   * Set the {@code status} property.
   * @see #status
   */
  public void setStatus(BLonEnum v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "senderId"

  /**
   * Slot for the {@code senderId} property.
   * @see #getSenderId
   * @see #setSenderId
   */
  public static final Property senderId = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u16, 1, 65534, 1, 65535, 1, 0, null));

  /**
   * Get the {@code senderId} property.
   * @see #senderId
   */
  public BLonFloat getSenderId() { return (BLonFloat)get(senderId); }

  /**
   * Set the {@code senderId} property.
   * @see #senderId
   */
  public void setSenderId(BLonFloat v) { set(senderId, v, null); }

  //endregion Property "senderId"

  //region Property "senderRangeLower"

  /**
   * Slot for the {@code senderRangeLower} property.
   * @see #getSenderRangeLower
   * @see #setSenderRangeLower
   */
  public static final Property senderRangeLower = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u16, 1, 65534, 1, 65535, 1, 0, null));

  /**
   * Get the {@code senderRangeLower} property.
   * @see #senderRangeLower
   */
  public BLonFloat getSenderRangeLower() { return (BLonFloat)get(senderRangeLower); }

  /**
   * Set the {@code senderRangeLower} property.
   * @see #senderRangeLower
   */
  public void setSenderRangeLower(BLonFloat v) { set(senderRangeLower, v, null); }

  //endregion Property "senderRangeLower"

  //region Property "senderRangeUpper"

  /**
   * Slot for the {@code senderRangeUpper} property.
   * @see #getSenderRangeUpper
   * @see #setSenderRangeUpper
   */
  public static final Property senderRangeUpper = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u16, 1, 65534, 1, 65535, 3, 0, null));

  /**
   * Get the {@code senderRangeUpper} property.
   * @see #senderRangeUpper
   */
  public BLonFloat getSenderRangeUpper() { return (BLonFloat)get(senderRangeUpper); }

  /**
   * Set the {@code senderRangeUpper} property.
   * @see #senderRangeUpper
   */
  public void setSenderRangeUpper(BLonFloat v) { set(senderRangeUpper, v, null); }

  //endregion Property "senderRangeUpper"

  //region Property "controllerId"

  /**
   * Slot for the {@code controllerId} property.
   * @see #getControllerId
   * @see #setControllerId
   */
  public static final Property controllerId = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u16, 1, 65534, 1, 65535, 5, 0, null));

  /**
   * Get the {@code controllerId} property.
   * @see #controllerId
   */
  public BLonFloat getControllerId() { return (BLonFloat)get(controllerId); }

  /**
   * Set the {@code controllerId} property.
   * @see #controllerId
   */
  public void setControllerId(BLonFloat v) { set(controllerId, v, null); }

  //endregion Property "controllerId"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonCtrlResp.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /*
   *  Statement on union from SNVT Master List - May 2002
   *
   *  A union holds the logical id of the controllable device. For camera telemetry
   *  receivers this value is a fixed value configured prior to use.
   *
   *  For matrixes, this value holds the currently selected monitor, by the specified
   *  controller in the contoller_id field. The matrix is logically assigned by monitor
   *  range during configuration; thus, these values must be transmitted when the
   *  status is CTRLR_RES.
   *
   *  e.g., A matrix having the monitor range 1 to 16 must on reset send:
   *  sender.range.lower = 1
   *  sender.range.upper = 16
   */
   
  public void toOutputStream(LonOutputStream out)
  {
    primitiveToOutputStream(status          , out);
    
    int st = getStatus().getEnum().getOrdinal();
    if(st == BLonControlRespEnum.CTRLR_RES)
    {
      primitiveToOutputStream(senderRangeLower, out);
      primitiveToOutputStream(senderRangeUpper, out);
    }  
    else
    {
      primitiveToOutputStream(senderId        , out);
      out.writeUnsigned16(0);
    }  
    primitiveToOutputStream(controllerId    , out);
  }
  
  public void fromInputStream(LonInputStream in)
  {
    primitiveFromInputStream(status          , in);

    int st = getStatus().getEnum().getOrdinal();
    if(st == BLonControlRespEnum.CTRLR_RES)
    {
      primitiveFromInputStream(senderRangeLower, in);
      primitiveFromInputStream(senderRangeUpper, in);
    }
    else
    {
      primitiveFromInputStream(senderId        , in);
      in.readUnsigned16();
    }  
    primitiveFromInputStream(controllerId    , in);
  }  

}
