/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import javax.baja.lonworks.datatypes.BAddressEntry;
import javax.baja.lonworks.enums.BLonElementType;
import javax.baja.lonworks.enums.BLonFileRequestEnum;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;


/**
 *   This class file represents SNVT_file_req.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  4 Sept 01
 * @version   $Revision: 3$ $Date: 9/28/01 10:20:43 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "request",
  type = "BLonEnum",
  defaultValue = "BLonEnum.make(BLonFileRequestEnum.DEFAULT)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.e8, null)")
)
@NiagaraProperty(
  name = "index",
  type = "BLonInteger",
  defaultValue = "BLonInteger.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementQualifiers.UNSIGNED_LONG1, null)")
)
@NiagaraProperty(
  name = "recvTimeout",
  type = "BLonInteger",
  defaultValue = "BLonInteger.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementQualifiers.UNSIGNED_LONG1, null)")
)
@NiagaraProperty(
  name = "address",
  type = "BLonSimple",
  defaultValue = "BLonSimple.make(BAddressEntry.DEFAULT)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.na, 4, null)")
)
@NiagaraProperty(
  name = "authenticate",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.FALSE",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementQualifiers.BOOLEAN, null)")
)
@NiagaraProperty(
  name = "priority",
  type = "BLonBoolean",
  defaultValue = "BLonBoolean.FALSE",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementQualifiers.BOOLEAN, null)")
)
public class BLonFileReq
  extends BLonData
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonFileReq(730221339)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "request"

  /**
   * Slot for the {@code request} property.
   * @see #getRequest
   * @see #setRequest
   */
  public static final Property request = newProperty(0, BLonEnum.make(BLonFileRequestEnum.DEFAULT), LonFacetsUtil.makeFacets(BLonElementType.e8, null));

  /**
   * Get the {@code request} property.
   * @see #request
   */
  public BLonEnum getRequest() { return (BLonEnum)get(request); }

  /**
   * Set the {@code request} property.
   * @see #request
   */
  public void setRequest(BLonEnum v) { set(request, v, null); }

  //endregion Property "request"

  //region Property "index"

  /**
   * Slot for the {@code index} property.
   * @see #getIndex
   * @see #setIndex
   */
  public static final Property index = newProperty(0, BLonInteger.DEFAULT, LonFacetsUtil.makeFacets(BLonElementQualifiers.UNSIGNED_LONG1, null));

  /**
   * Get the {@code index} property.
   * @see #index
   */
  public BLonInteger getIndex() { return (BLonInteger)get(index); }

  /**
   * Set the {@code index} property.
   * @see #index
   */
  public void setIndex(BLonInteger v) { set(index, v, null); }

  //endregion Property "index"

  //region Property "recvTimeout"

  /**
   * Slot for the {@code recvTimeout} property.
   * @see #getRecvTimeout
   * @see #setRecvTimeout
   */
  public static final Property recvTimeout = newProperty(0, BLonInteger.DEFAULT, LonFacetsUtil.makeFacets(BLonElementQualifiers.UNSIGNED_LONG1, null));

  /**
   * Get the {@code recvTimeout} property.
   * @see #recvTimeout
   */
  public BLonInteger getRecvTimeout() { return (BLonInteger)get(recvTimeout); }

  /**
   * Set the {@code recvTimeout} property.
   * @see #recvTimeout
   */
  public void setRecvTimeout(BLonInteger v) { set(recvTimeout, v, null); }

  //endregion Property "recvTimeout"

  //region Property "address"

  /**
   * Slot for the {@code address} property.
   * @see #getAddress
   * @see #setAddress
   */
  public static final Property address = newProperty(0, BLonSimple.make(BAddressEntry.DEFAULT), LonFacetsUtil.makeFacets(BLonElementType.na, 4, null));

  /**
   * Get the {@code address} property.
   * @see #address
   */
  public BLonSimple getAddress() { return (BLonSimple)get(address); }

  /**
   * Set the {@code address} property.
   * @see #address
   */
  public void setAddress(BLonSimple v) { set(address, v, null); }

  //endregion Property "address"

  //region Property "authenticate"

  /**
   * Slot for the {@code authenticate} property.
   * @see #getAuthenticate
   * @see #setAuthenticate
   */
  public static final Property authenticate = newProperty(0, BLonBoolean.FALSE, LonFacetsUtil.makeFacets(BLonElementQualifiers.BOOLEAN, null));

  /**
   * Get the {@code authenticate} property.
   * @see #authenticate
   */
  public BLonBoolean getAuthenticate() { return (BLonBoolean)get(authenticate); }

  /**
   * Set the {@code authenticate} property.
   * @see #authenticate
   */
  public void setAuthenticate(BLonBoolean v) { set(authenticate, v, null); }

  //endregion Property "authenticate"

  //region Property "priority"

  /**
   * Slot for the {@code priority} property.
   * @see #getPriority
   * @see #setPriority
   */
  public static final Property priority = newProperty(0, BLonBoolean.FALSE, LonFacetsUtil.makeFacets(BLonElementQualifiers.BOOLEAN, null));

  /**
   * Get the {@code priority} property.
   * @see #priority
   */
  public BLonBoolean getPriority() { return (BLonBoolean)get(priority); }

  /**
   * Set the {@code priority} property.
   * @see #priority
   */
  public void setPriority(BLonBoolean v) { set(priority, v, null); }

  //endregion Property "priority"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonFileReq.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
