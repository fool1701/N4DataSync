/*
 * Copyright 2008, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BBufferSizeEnum provides enumeration the application and network
 * buffer sizes. See Neuron Chip Data Book A.1.
 *
 * @author    Robert Adams
 * @creation  16 Sept 08
 * @version   $Revision: 1$ $Date: 12/11/00 8:13:10 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "bufferSize255", ordinal = 0),
    @Range(value = "bufferSize20", ordinal = 2),
    @Range(value = "bufferSize21", ordinal = 3),
    @Range(value = "bufferSize22", ordinal = 4),
    @Range(value = "bufferSize24", ordinal = 5),
    @Range(value = "bufferSize26", ordinal = 6),
    @Range(value = "bufferSize30", ordinal = 7),
    @Range(value = "bufferSize34", ordinal = 8),
    @Range(value = "bufferSize42", ordinal = 9),
    @Range(value = "bufferSize50", ordinal = 10),
    @Range(value = "bufferSize66", ordinal = 11),
    @Range(value = "bufferSize82", ordinal = 12),
    @Range(value = "bufferSize114", ordinal = 13),
    @Range(value = "bufferSize146", ordinal = 14),
    @Range(value = "bufferSize210", ordinal = 15)
  },
  defaultValue = "bufferSize255"
)
public final class BBufferSizeEnum
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BBufferSizeEnum(3096611371)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for bufferSize255. */
  public static final int BUFFER_SIZE_255 = 0;
  /** Ordinal value for bufferSize20. */
  public static final int BUFFER_SIZE_20 = 2;
  /** Ordinal value for bufferSize21. */
  public static final int BUFFER_SIZE_21 = 3;
  /** Ordinal value for bufferSize22. */
  public static final int BUFFER_SIZE_22 = 4;
  /** Ordinal value for bufferSize24. */
  public static final int BUFFER_SIZE_24 = 5;
  /** Ordinal value for bufferSize26. */
  public static final int BUFFER_SIZE_26 = 6;
  /** Ordinal value for bufferSize30. */
  public static final int BUFFER_SIZE_30 = 7;
  /** Ordinal value for bufferSize34. */
  public static final int BUFFER_SIZE_34 = 8;
  /** Ordinal value for bufferSize42. */
  public static final int BUFFER_SIZE_42 = 9;
  /** Ordinal value for bufferSize50. */
  public static final int BUFFER_SIZE_50 = 10;
  /** Ordinal value for bufferSize66. */
  public static final int BUFFER_SIZE_66 = 11;
  /** Ordinal value for bufferSize82. */
  public static final int BUFFER_SIZE_82 = 12;
  /** Ordinal value for bufferSize114. */
  public static final int BUFFER_SIZE_114 = 13;
  /** Ordinal value for bufferSize146. */
  public static final int BUFFER_SIZE_146 = 14;
  /** Ordinal value for bufferSize210. */
  public static final int BUFFER_SIZE_210 = 15;

  /** BBufferSizeEnum constant for bufferSize255. */
  public static final BBufferSizeEnum bufferSize255 = new BBufferSizeEnum(BUFFER_SIZE_255);
  /** BBufferSizeEnum constant for bufferSize20. */
  public static final BBufferSizeEnum bufferSize20 = new BBufferSizeEnum(BUFFER_SIZE_20);
  /** BBufferSizeEnum constant for bufferSize21. */
  public static final BBufferSizeEnum bufferSize21 = new BBufferSizeEnum(BUFFER_SIZE_21);
  /** BBufferSizeEnum constant for bufferSize22. */
  public static final BBufferSizeEnum bufferSize22 = new BBufferSizeEnum(BUFFER_SIZE_22);
  /** BBufferSizeEnum constant for bufferSize24. */
  public static final BBufferSizeEnum bufferSize24 = new BBufferSizeEnum(BUFFER_SIZE_24);
  /** BBufferSizeEnum constant for bufferSize26. */
  public static final BBufferSizeEnum bufferSize26 = new BBufferSizeEnum(BUFFER_SIZE_26);
  /** BBufferSizeEnum constant for bufferSize30. */
  public static final BBufferSizeEnum bufferSize30 = new BBufferSizeEnum(BUFFER_SIZE_30);
  /** BBufferSizeEnum constant for bufferSize34. */
  public static final BBufferSizeEnum bufferSize34 = new BBufferSizeEnum(BUFFER_SIZE_34);
  /** BBufferSizeEnum constant for bufferSize42. */
  public static final BBufferSizeEnum bufferSize42 = new BBufferSizeEnum(BUFFER_SIZE_42);
  /** BBufferSizeEnum constant for bufferSize50. */
  public static final BBufferSizeEnum bufferSize50 = new BBufferSizeEnum(BUFFER_SIZE_50);
  /** BBufferSizeEnum constant for bufferSize66. */
  public static final BBufferSizeEnum bufferSize66 = new BBufferSizeEnum(BUFFER_SIZE_66);
  /** BBufferSizeEnum constant for bufferSize82. */
  public static final BBufferSizeEnum bufferSize82 = new BBufferSizeEnum(BUFFER_SIZE_82);
  /** BBufferSizeEnum constant for bufferSize114. */
  public static final BBufferSizeEnum bufferSize114 = new BBufferSizeEnum(BUFFER_SIZE_114);
  /** BBufferSizeEnum constant for bufferSize146. */
  public static final BBufferSizeEnum bufferSize146 = new BBufferSizeEnum(BUFFER_SIZE_146);
  /** BBufferSizeEnum constant for bufferSize210. */
  public static final BBufferSizeEnum bufferSize210 = new BBufferSizeEnum(BUFFER_SIZE_210);

  /** Factory method with ordinal. */
  public static BBufferSizeEnum make(int ordinal)
  {
    return (BBufferSizeEnum)bufferSize255.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBufferSizeEnum make(String tag)
  {
    return (BBufferSizeEnum)bufferSize255.getRange().get(tag);
  }

  /** Private constructor. */
  private BBufferSizeEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BBufferSizeEnum DEFAULT = bufferSize255;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBufferSizeEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private static final int[] SIZES = new int[]{255, -1, 20, 21, 22, 24, 26, 30, 34, 42, 50, 66, 82, 114, 146, 210};
  
  public int getSize() {return SIZES[getOrdinal()];}
 
}
