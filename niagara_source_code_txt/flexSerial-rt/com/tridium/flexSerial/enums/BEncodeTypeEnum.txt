/**
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * The BEncodeTypeEnum class provides enumeration of encode type selection form message data
 * 
 *
 * @author    Andy Saunders        
 * @creation  20 Sept 2005
 * @version   $Revision: 1$ $Date: 04/05/02 12:47:14 PM$  
 * @since     Niagara 3.0 andi 1.0     
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("None"),
    @Range("Ascii"),
    @Range("AsciiHex")
  }
)
public final class BEncodeTypeEnum
  extends BFrozenEnum  
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.enums.BEncodeTypeEnum(946792557)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for None. */
  public static final int NONE = 0;
  /** Ordinal value for Ascii. */
  public static final int ASCII = 1;
  /** Ordinal value for AsciiHex. */
  public static final int ASCII_HEX = 2;

  /** BEncodeTypeEnum constant for None. */
  public static final BEncodeTypeEnum None = new BEncodeTypeEnum(NONE);
  /** BEncodeTypeEnum constant for Ascii. */
  public static final BEncodeTypeEnum Ascii = new BEncodeTypeEnum(ASCII);
  /** BEncodeTypeEnum constant for AsciiHex. */
  public static final BEncodeTypeEnum AsciiHex = new BEncodeTypeEnum(ASCII_HEX);

  /** Factory method with ordinal. */
  public static BEncodeTypeEnum make(int ordinal)
  {
    return (BEncodeTypeEnum)None.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BEncodeTypeEnum make(String tag)
  {
    return (BEncodeTypeEnum)None.getRange().get(tag);
  }

  /** Private constructor. */
  private BEncodeTypeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BEncodeTypeEnum DEFAULT = None;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEncodeTypeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



}
