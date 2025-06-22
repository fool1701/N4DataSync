/**
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * The BDataTypeEnum class provides enumeration of data type selection for messageElement data.
 * 
 *
 * @author    Andy Saunders        
 * @creation  07 July 2004
 * @version   $Revision: 1$ $Date: 04/05/02 12:47:14 PM$  
 * @since     Niagara 3.0 andi 1.0     
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("Byte"),
    @Range("Word"),
    @Range("Integer"),
    @Range("Float"),
    @Range("String"),
    @Range("Marker")
  }
)
public final class BDataTypeEnum
  extends BFrozenEnum  
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.enums.BDataTypeEnum(3717560061)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for Byte. */
  public static final int BYTE = 0;
  /** Ordinal value for Word. */
  public static final int WORD = 1;
  /** Ordinal value for Integer. */
  public static final int INTEGER = 2;
  /** Ordinal value for Float. */
  public static final int FLOAT = 3;
  /** Ordinal value for String. */
  public static final int STRING = 4;
  /** Ordinal value for Marker. */
  public static final int MARKER = 5;

  /** BDataTypeEnum constant for Byte. */
  public static final BDataTypeEnum Byte = new BDataTypeEnum(BYTE);
  /** BDataTypeEnum constant for Word. */
  public static final BDataTypeEnum Word = new BDataTypeEnum(WORD);
  /** BDataTypeEnum constant for Integer. */
  public static final BDataTypeEnum Integer = new BDataTypeEnum(INTEGER);
  /** BDataTypeEnum constant for Float. */
  public static final BDataTypeEnum Float = new BDataTypeEnum(FLOAT);
  /** BDataTypeEnum constant for String. */
  public static final BDataTypeEnum String = new BDataTypeEnum(STRING);
  /** BDataTypeEnum constant for Marker. */
  public static final BDataTypeEnum Marker = new BDataTypeEnum(MARKER);

  /** Factory method with ordinal. */
  public static BDataTypeEnum make(int ordinal)
  {
    return (BDataTypeEnum)Byte.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BDataTypeEnum make(String tag)
  {
    return (BDataTypeEnum)Byte.getRange().get(tag);
  }

  /** Private constructor. */
  private BDataTypeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BDataTypeEnum DEFAULT = Byte;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDataTypeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



}
