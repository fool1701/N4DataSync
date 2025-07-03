/*
 * @copyright 2005 Tridium Inc.
 */
package com.tridium.nrio.components;

import javax.baja.control.BBooleanPoint;
import javax.baja.control.BBooleanWritable;
import javax.baja.control.BControlPoint;
import javax.baja.control.BEnumPoint;
import javax.baja.control.ext.BAbstractProxyExt;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.enums.BNrioIoTypeEnum;
import com.tridium.nrio.points.BNrioProxyExt;

/**
 * BNrioIOPointEntry - The learn IO Module discovery job places instances of this component.
 * 
 *
 * @author    Andy Saunders
 * @creation  13 Jan 2005
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 This is the unit number of the discovered access device
 */
@NiagaraProperty(
  name = "ioType",
  type = "BNrioIoTypeEnum",
  defaultValue = "BNrioIoTypeEnum.digitalInput"
)
/*
 This instance number for this IO
 */
@NiagaraProperty(
  name = "instance",
  type = "int",
  defaultValue = "0"
)
@NiagaraProperty(
  name = "usedByPoint",
  type = "String",
  defaultValue = ""
)
public class BNrioIOPointEntry
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.components.BNrioIOPointEntry(8973712)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "ioType"

  /**
   * Slot for the {@code ioType} property.
   * This is the unit number of the discovered access device
   * @see #getIoType
   * @see #setIoType
   */
  public static final Property ioType = newProperty(0, BNrioIoTypeEnum.digitalInput, null);

  /**
   * Get the {@code ioType} property.
   * This is the unit number of the discovered access device
   * @see #ioType
   */
  public BNrioIoTypeEnum getIoType() { return (BNrioIoTypeEnum)get(ioType); }

  /**
   * Set the {@code ioType} property.
   * This is the unit number of the discovered access device
   * @see #ioType
   */
  public void setIoType(BNrioIoTypeEnum v) { set(ioType, v, null); }

  //endregion Property "ioType"

  //region Property "instance"

  /**
   * Slot for the {@code instance} property.
   * This instance number for this IO
   * @see #getInstance
   * @see #setInstance
   */
  public static final Property instance = newProperty(0, 0, null);

  /**
   * Get the {@code instance} property.
   * This instance number for this IO
   * @see #instance
   */
  public int getInstance() { return getInt(instance); }

  /**
   * Set the {@code instance} property.
   * This instance number for this IO
   * @see #instance
   */
  public void setInstance(int v) { setInt(instance, v, null); }

  //endregion Property "instance"

  //region Property "usedByPoint"

  /**
   * Slot for the {@code usedByPoint} property.
   * @see #getUsedByPoint
   * @see #setUsedByPoint
   */
  public static final Property usedByPoint = newProperty(0, "", null);

  /**
   * Get the {@code usedByPoint} property.
   * @see #usedByPoint
   */
  public String getUsedByPoint() { return getString(usedByPoint); }

  /**
   * Set the {@code usedByPoint} property.
   * @see #usedByPoint
   */
  public void setUsedByPoint(String v) { setString(usedByPoint, v, null); }

  //endregion Property "usedByPoint"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioIOPointEntry.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BNrioIOPointEntry(BNrioIoTypeEnum type, int instance)
  {
    setIoType(type);
    setInstance(instance);
  }
  public BNrioIOPointEntry(){}

  public boolean matches(BComponent point)
  {
    if(!(point instanceof BControlPoint) )
      return false;
    BControlPoint cp = (BControlPoint)point;
    BAbstractProxyExt proxy = cp.getProxyExt();
    if( !(proxy instanceof BNrioProxyExt) )
      return false;
    if( ((BNrioProxyExt)proxy).isDisabled() )
      return false;
    int proxInstance = ((BNrioProxyExt)proxy).getInstance();
    if( getInstance() != proxInstance)
      return false;

    if( cp instanceof BBooleanWritable )
      return getIoType().equals(BNrioIoTypeEnum.relayOutput) ||
             getIoType().equals(BNrioIoTypeEnum.cardReaderOutput)    ;

    if( cp instanceof BEnumPoint )
      return getIoType().equals(BNrioIoTypeEnum.supervisedDigitalInput);

    if( cp instanceof BBooleanPoint &&  ((BNrioProxyExt)proxy).getIsSdi())
      return getIoType().equals(BNrioIoTypeEnum.supervisedDigitalInput);

    return ( (getIoType().equals(BNrioIoTypeEnum.digitalInput)) && (cp instanceof BBooleanPoint) );  
  }

  public boolean isMatchable(BComponent point)
  {
    if(!(point instanceof BControlPoint) )
      return false;
    BControlPoint cp = (BControlPoint)point;
    BAbstractProxyExt proxy = cp.getProxyExt();
    if( !(proxy instanceof BNrioProxyExt) )
      return false;
    int proxInstance = ((BNrioProxyExt)proxy).getInstance();
    if( proxInstance != 0)
      return false;

    if( cp instanceof BBooleanWritable )
      return getIoType().equals(BNrioIoTypeEnum.relayOutput);

    if( cp instanceof BEnumPoint )
      return getIoType().equals(BNrioIoTypeEnum.supervisedDigitalInput);

    if( cp instanceof BBooleanPoint &&  ((BNrioProxyExt)proxy).getIsSdi())
      return getIoType().equals(BNrioIoTypeEnum.supervisedDigitalInput);

    return ( (getIoType().equals(BNrioIoTypeEnum.digitalInput)) && (cp instanceof BBooleanPoint) );  
  }
}
