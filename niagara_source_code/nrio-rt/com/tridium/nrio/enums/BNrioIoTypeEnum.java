/**
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BNrioIoTypeEnum class provides enumeration of ANDI
 * file mode values
 *
 * @author    Andy Saunders        
 * @creation  20 June 05
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 * @since     Niagara 3.0 andi 1.0     
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("digitalInput"),
    @Range("supervisedDigitalInput"),
    @Range("relayOutput"),
    @Range("cardReaderOutput"),
    @Range("universalInput"),
    @Range("analogOutput")
  }
)
public final class BNrioIoTypeEnum
  extends BFrozenEnum  
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.enums.BNrioIoTypeEnum(1702996017)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for digitalInput. */
  public static final int DIGITAL_INPUT = 0;
  /** Ordinal value for supervisedDigitalInput. */
  public static final int SUPERVISED_DIGITAL_INPUT = 1;
  /** Ordinal value for relayOutput. */
  public static final int RELAY_OUTPUT = 2;
  /** Ordinal value for cardReaderOutput. */
  public static final int CARD_READER_OUTPUT = 3;
  /** Ordinal value for universalInput. */
  public static final int UNIVERSAL_INPUT = 4;
  /** Ordinal value for analogOutput. */
  public static final int ANALOG_OUTPUT = 5;

  /** BNrioIoTypeEnum constant for digitalInput. */
  public static final BNrioIoTypeEnum digitalInput = new BNrioIoTypeEnum(DIGITAL_INPUT);
  /** BNrioIoTypeEnum constant for supervisedDigitalInput. */
  public static final BNrioIoTypeEnum supervisedDigitalInput = new BNrioIoTypeEnum(SUPERVISED_DIGITAL_INPUT);
  /** BNrioIoTypeEnum constant for relayOutput. */
  public static final BNrioIoTypeEnum relayOutput = new BNrioIoTypeEnum(RELAY_OUTPUT);
  /** BNrioIoTypeEnum constant for cardReaderOutput. */
  public static final BNrioIoTypeEnum cardReaderOutput = new BNrioIoTypeEnum(CARD_READER_OUTPUT);
  /** BNrioIoTypeEnum constant for universalInput. */
  public static final BNrioIoTypeEnum universalInput = new BNrioIoTypeEnum(UNIVERSAL_INPUT);
  /** BNrioIoTypeEnum constant for analogOutput. */
  public static final BNrioIoTypeEnum analogOutput = new BNrioIoTypeEnum(ANALOG_OUTPUT);

  /** Factory method with ordinal. */
  public static BNrioIoTypeEnum make(int ordinal)
  {
    return (BNrioIoTypeEnum)digitalInput.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BNrioIoTypeEnum make(String tag)
  {
    return (BNrioIoTypeEnum)digitalInput.getRange().get(tag);
  }

  /** Private constructor. */
  private BNrioIoTypeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BNrioIoTypeEnum DEFAULT = digitalInput;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioIoTypeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


}
