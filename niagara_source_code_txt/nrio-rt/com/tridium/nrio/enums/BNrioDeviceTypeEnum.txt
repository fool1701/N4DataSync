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
 * The BNrioDeviceTypeEnum class provides enumeration of ANDI
 * file mode values
 *
 * @author    Andy Saunders        
 * @creation  27 June 03
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:11 AM$  
 * @since     Niagara 3.0 andi 1.0     
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("none"),
    @Range("baseBoardReader"),
    @Range("remoteReader"),
    @Range("remoteInputOutput"),
    @Range("io16"),
    @Range("io16V1"),
    @Range("io34"),
    @Range("io34sec")
  }
)
public final class BNrioDeviceTypeEnum
  extends BFrozenEnum  
{





//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.enums.BNrioDeviceTypeEnum(1041974224)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for none. */
  public static final int NONE = 0;
  /** Ordinal value for baseBoardReader. */
  public static final int BASE_BOARD_READER = 1;
  /** Ordinal value for remoteReader. */
  public static final int REMOTE_READER = 2;
  /** Ordinal value for remoteInputOutput. */
  public static final int REMOTE_INPUT_OUTPUT = 3;
  /** Ordinal value for io16. */
  public static final int IO_16 = 4;
  /** Ordinal value for io16V1. */
  public static final int IO_16V1 = 5;
  /** Ordinal value for io34. */
  public static final int IO_34 = 6;
  /** Ordinal value for io34sec. */
  public static final int IO_34SEC = 7;

  /** BNrioDeviceTypeEnum constant for none. */
  public static final BNrioDeviceTypeEnum none = new BNrioDeviceTypeEnum(NONE);
  /** BNrioDeviceTypeEnum constant for baseBoardReader. */
  public static final BNrioDeviceTypeEnum baseBoardReader = new BNrioDeviceTypeEnum(BASE_BOARD_READER);
  /** BNrioDeviceTypeEnum constant for remoteReader. */
  public static final BNrioDeviceTypeEnum remoteReader = new BNrioDeviceTypeEnum(REMOTE_READER);
  /** BNrioDeviceTypeEnum constant for remoteInputOutput. */
  public static final BNrioDeviceTypeEnum remoteInputOutput = new BNrioDeviceTypeEnum(REMOTE_INPUT_OUTPUT);
  /** BNrioDeviceTypeEnum constant for io16. */
  public static final BNrioDeviceTypeEnum io16 = new BNrioDeviceTypeEnum(IO_16);
  /** BNrioDeviceTypeEnum constant for io16V1. */
  public static final BNrioDeviceTypeEnum io16V1 = new BNrioDeviceTypeEnum(IO_16V1);
  /** BNrioDeviceTypeEnum constant for io34. */
  public static final BNrioDeviceTypeEnum io34 = new BNrioDeviceTypeEnum(IO_34);
  /** BNrioDeviceTypeEnum constant for io34sec. */
  public static final BNrioDeviceTypeEnum io34sec = new BNrioDeviceTypeEnum(IO_34SEC);

  /** Factory method with ordinal. */
  public static BNrioDeviceTypeEnum make(int ordinal)
  {
    return (BNrioDeviceTypeEnum)none.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BNrioDeviceTypeEnum make(String tag)
  {
    return (BNrioDeviceTypeEnum)none.getRange().get(tag);
  }

  /** Private constructor. */
  private BNrioDeviceTypeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BNrioDeviceTypeEnum DEFAULT = none;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioDeviceTypeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public static BNrioDeviceTypeEnum makeFromRaw(int raw)
  {                     
    switch(raw)
    {
      case 0x07: return remoteReader;
      case 0x08: return remoteInputOutput;
      case 0x06: return baseBoardReader;
      case 0x09: return io16;
      case 0x0A: return io16V1;
      case 0x0B: return io34;
      case 0x0C: return io34sec;
      default: return none;
    }                      
  }

  public int getRawInt()
  {
    switch(getOrdinal())
    {
      case REMOTE_READER:       return 0x07;
      case REMOTE_INPUT_OUTPUT: return 0x08;
      case BASE_BOARD_READER:   return 0x06;
      case IO_16:               return 0x09;
      case IO_16V1:             return 0x0A;
      case IO_34:               return 0x0B;
      case IO_34SEC:            return 0x0C;
    }
    return 0;
  }

  public boolean isMatchable(BNrioDeviceTypeEnum other)
  {
    final int ordinal = getOrdinal();
    final int otherOrdinal = other.getOrdinal();
    if(ordinal == otherOrdinal)
      return true;
    return (ordinal == IO_16 && otherOrdinal == IO_16V1) ||
           (ordinal == IO_16V1 && otherOrdinal == IO_16);
  }


}
