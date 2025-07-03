/*
 * @copyright 2005 Tridium Inc.
 */
package com.tridium.nrio.components;

import javax.baja.control.BBooleanPoint;
import javax.baja.control.BBooleanWritable;
import javax.baja.control.BControlPoint;
import javax.baja.control.BNumericPoint;
import javax.baja.control.BNumericWritable;
import javax.baja.control.ext.BAbstractProxyExt;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.enums.BNrioIoTypeEnum;
import com.tridium.nrio.enums.BUniversalInputTypeEnum;
import com.tridium.nrio.points.BNrio16ProxyExt;
import com.tridium.nrio.points.BUiProxyExt;

/**
 * BUIPointEntry - The learn IO Module discovery job places instances of this component.
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
 This selects specific input type if universal input
 */
@NiagaraProperty(
  name = "uiType",
  type = "BUniversalInputTypeEnum",
  defaultValue = "BUniversalInputTypeEnum.undefined"
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
public class BUIPointEntry
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.components.BUIPointEntry(3279418366)1.0$ @*/
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

  //region Property "uiType"

  /**
   * Slot for the {@code uiType} property.
   * This selects specific input type if universal input
   * @see #getUiType
   * @see #setUiType
   */
  public static final Property uiType = newProperty(0, BUniversalInputTypeEnum.undefined, null);

  /**
   * Get the {@code uiType} property.
   * This selects specific input type if universal input
   * @see #uiType
   */
  public BUniversalInputTypeEnum getUiType() { return (BUniversalInputTypeEnum)get(uiType); }

  /**
   * Set the {@code uiType} property.
   * This selects specific input type if universal input
   * @see #uiType
   */
  public void setUiType(BUniversalInputTypeEnum v) { set(uiType, v, null); }

  //endregion Property "uiType"

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
  public static final Type TYPE = Sys.loadType(BUIPointEntry.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BUIPointEntry(BNrioIoTypeEnum type, int instance)
  {
    setIoType(type);
    setInstance(instance);
  }
  public BUIPointEntry(){}

  public boolean matches(BComponent point)
  {
    if(!(point instanceof BControlPoint) )
      return false;
    BControlPoint cp = (BControlPoint)point;
    BAbstractProxyExt proxy = cp.getProxyExt();
    if( !(proxy instanceof BNrio16ProxyExt) )
      return false;
    if( !((BNrio16ProxyExt)proxy).getEnabled() )
      return false;
    int proxInstance = ((BNrio16ProxyExt)proxy).getInstance();
    if( getInstance() != proxInstance)
      return false;
    if( cp instanceof BBooleanWritable )
      return getIoType().equals(BNrioIoTypeEnum.relayOutput);
    if( cp instanceof BBooleanPoint )
      return getIoType().equals(BNrioIoTypeEnum.universalInput);
    if( cp instanceof BNumericWritable )
      return getIoType().equals(BNrioIoTypeEnum.analogOutput);
    if( cp instanceof BNumericPoint )
      return getIoType().equals(BNrioIoTypeEnum.universalInput);
    return false;
  }

  public boolean isMatchable(BComponent point)
  {
    if(!(point instanceof BControlPoint) )
      return false;
    BControlPoint cp = (BControlPoint)point;
    BAbstractProxyExt proxy = cp.getProxyExt();
    if( !(proxy instanceof BUiProxyExt) )
      return false;
    int proxInstance = ((BUiProxyExt)proxy).getInstance();
    if( proxInstance != 0)
      return false;

    if( cp instanceof BBooleanWritable )
      return getIoType().equals(BNrioIoTypeEnum.relayOutput);


    return ( (getIoType().equals(BNrioIoTypeEnum.digitalInput)) && (cp instanceof BBooleanPoint) );  
  }
}
