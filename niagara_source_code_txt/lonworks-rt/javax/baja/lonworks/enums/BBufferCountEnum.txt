////////////////////////////////////////////////////////////////
//
// File: BBufferCountEnum.java
//                                                              
// Revision History:                                            
//    16 Sept 08	  Robert A Adams                      
//                                                              
// Copyright 2008 Tridium, Inc                            
////////////////////////////////////////////////////////////////
package javax.baja.lonworks.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BEnum;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BBufferCountEnum provides enumeration for application and network
 * buffer counts. See Neuron Chip Data Book A.1.
 *
 * @author    Robert Adams
 * @creation  16 Sept 08
 * @version   $Revision: 1$ $Date: 12/11/00 8:13:10 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "bufferCnt0", ordinal = 0),
    @Range(value = "bufferCnt1", ordinal = 2),
    @Range(value = "bufferCnt2", ordinal = 3),
    @Range(value = "bufferCnt3", ordinal = 4),
    @Range(value = "bufferCnt5", ordinal = 5),
    @Range(value = "bufferCnt7", ordinal = 6),
    @Range(value = "bufferCnt11", ordinal = 7),
    @Range(value = "bufferCnt15", ordinal = 8),
    @Range(value = "bufferCnt23", ordinal = 9),
    @Range(value = "bufferCnt31", ordinal = 10),
    @Range(value = "bufferCnt47", ordinal = 11),
    @Range(value = "bufferCnt63", ordinal = 12),
    @Range(value = "bufferCnt95", ordinal = 13),
    @Range(value = "bufferCnt127", ordinal = 14),
    @Range(value = "bufferCnt191", ordinal = 15)
  },
  defaultValue = "bufferCnt0"
)
public final class BBufferCountEnum
  extends BFrozenEnum
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.enums.BBufferCountEnum(3899543923)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for bufferCnt0. */
  public static final int BUFFER_CNT_0 = 0;
  /** Ordinal value for bufferCnt1. */
  public static final int BUFFER_CNT_1 = 2;
  /** Ordinal value for bufferCnt2. */
  public static final int BUFFER_CNT_2 = 3;
  /** Ordinal value for bufferCnt3. */
  public static final int BUFFER_CNT_3 = 4;
  /** Ordinal value for bufferCnt5. */
  public static final int BUFFER_CNT_5 = 5;
  /** Ordinal value for bufferCnt7. */
  public static final int BUFFER_CNT_7 = 6;
  /** Ordinal value for bufferCnt11. */
  public static final int BUFFER_CNT_11 = 7;
  /** Ordinal value for bufferCnt15. */
  public static final int BUFFER_CNT_15 = 8;
  /** Ordinal value for bufferCnt23. */
  public static final int BUFFER_CNT_23 = 9;
  /** Ordinal value for bufferCnt31. */
  public static final int BUFFER_CNT_31 = 10;
  /** Ordinal value for bufferCnt47. */
  public static final int BUFFER_CNT_47 = 11;
  /** Ordinal value for bufferCnt63. */
  public static final int BUFFER_CNT_63 = 12;
  /** Ordinal value for bufferCnt95. */
  public static final int BUFFER_CNT_95 = 13;
  /** Ordinal value for bufferCnt127. */
  public static final int BUFFER_CNT_127 = 14;
  /** Ordinal value for bufferCnt191. */
  public static final int BUFFER_CNT_191 = 15;

  /** BBufferCountEnum constant for bufferCnt0. */
  public static final BBufferCountEnum bufferCnt0 = new BBufferCountEnum(BUFFER_CNT_0);
  /** BBufferCountEnum constant for bufferCnt1. */
  public static final BBufferCountEnum bufferCnt1 = new BBufferCountEnum(BUFFER_CNT_1);
  /** BBufferCountEnum constant for bufferCnt2. */
  public static final BBufferCountEnum bufferCnt2 = new BBufferCountEnum(BUFFER_CNT_2);
  /** BBufferCountEnum constant for bufferCnt3. */
  public static final BBufferCountEnum bufferCnt3 = new BBufferCountEnum(BUFFER_CNT_3);
  /** BBufferCountEnum constant for bufferCnt5. */
  public static final BBufferCountEnum bufferCnt5 = new BBufferCountEnum(BUFFER_CNT_5);
  /** BBufferCountEnum constant for bufferCnt7. */
  public static final BBufferCountEnum bufferCnt7 = new BBufferCountEnum(BUFFER_CNT_7);
  /** BBufferCountEnum constant for bufferCnt11. */
  public static final BBufferCountEnum bufferCnt11 = new BBufferCountEnum(BUFFER_CNT_11);
  /** BBufferCountEnum constant for bufferCnt15. */
  public static final BBufferCountEnum bufferCnt15 = new BBufferCountEnum(BUFFER_CNT_15);
  /** BBufferCountEnum constant for bufferCnt23. */
  public static final BBufferCountEnum bufferCnt23 = new BBufferCountEnum(BUFFER_CNT_23);
  /** BBufferCountEnum constant for bufferCnt31. */
  public static final BBufferCountEnum bufferCnt31 = new BBufferCountEnum(BUFFER_CNT_31);
  /** BBufferCountEnum constant for bufferCnt47. */
  public static final BBufferCountEnum bufferCnt47 = new BBufferCountEnum(BUFFER_CNT_47);
  /** BBufferCountEnum constant for bufferCnt63. */
  public static final BBufferCountEnum bufferCnt63 = new BBufferCountEnum(BUFFER_CNT_63);
  /** BBufferCountEnum constant for bufferCnt95. */
  public static final BBufferCountEnum bufferCnt95 = new BBufferCountEnum(BUFFER_CNT_95);
  /** BBufferCountEnum constant for bufferCnt127. */
  public static final BBufferCountEnum bufferCnt127 = new BBufferCountEnum(BUFFER_CNT_127);
  /** BBufferCountEnum constant for bufferCnt191. */
  public static final BBufferCountEnum bufferCnt191 = new BBufferCountEnum(BUFFER_CNT_191);

  /** Factory method with ordinal. */
  public static BBufferCountEnum make(int ordinal)
  {
    return (BBufferCountEnum)bufferCnt0.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBufferCountEnum make(String tag)
  {
    return (BBufferCountEnum)bufferCnt0.getRange().get(tag);
  }

  /** Private constructor. */
  private BBufferCountEnum(int ordinal)
  {
    super(ordinal);
  }

  public static final BBufferCountEnum DEFAULT = bufferCnt0;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBufferCountEnum.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private static final int[] COUNTS = new int[]{0, -1, 1, 2, 3, 5, 7, 11, 15, 23, 31, 47, 63, 95, 127, 191 };
  
  public int getCount() {return COUNTS[getOrdinal()];}

//  // to run >>nre attAlarmModule:com.tridium.att.datatypes.BBufferCountEnum
//  public static void main(String[] args)
//  {
//    System.out.println(bufferCnt0  );
//    System.out.println(bufferCnt1  );
//    System.out.println(bufferCnt2  );
//    System.out.println(bufferCnt3  );
//    System.out.println(bufferCnt5  );
//    System.out.println(bufferCnt7  );
//    System.out.println(bufferCnt11 );
//    System.out.println(bufferCnt15 );
//    System.out.println(bufferCnt23 );
//    System.out.println(bufferCnt31 );
//    System.out.println(bufferCnt47 );
//    System.out.println(bufferCnt63 );
//    System.out.println(bufferCnt95 );
//    System.out.println(bufferCnt127);
//    System.out.println(bufferCnt191);
//  }
}
