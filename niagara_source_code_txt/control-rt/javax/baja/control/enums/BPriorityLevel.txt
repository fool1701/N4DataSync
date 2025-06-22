/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

/**
 * BPriorityLevel represents the active level of a writable point.
 *
 * @author    Dan Giorgis
 * @creation  1 May 00
 * @version   $Revision: 10$ $Date: 12/21/10 10:48:10 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("none"),
    @Range("level_1"),
    @Range("level_2"),
    @Range("level_3"),
    @Range("level_4"),
    @Range("level_5"),
    @Range("level_6"),
    @Range("level_7"),
    @Range("level_8"),
    @Range("level_9"),
    @Range("level_10"),
    @Range("level_11"),
    @Range("level_12"),
    @Range("level_13"),
    @Range("level_14"),
    @Range("level_15"),
    @Range("level_16"),
    @Range("fallback")
  }
)
public final class BPriorityLevel
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.enums.BPriorityLevel(72161725)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for none. */
  public static final int NONE = 0;
  /** Ordinal value for level_1. */
  public static final int LEVEL_1 = 1;
  /** Ordinal value for level_2. */
  public static final int LEVEL_2 = 2;
  /** Ordinal value for level_3. */
  public static final int LEVEL_3 = 3;
  /** Ordinal value for level_4. */
  public static final int LEVEL_4 = 4;
  /** Ordinal value for level_5. */
  public static final int LEVEL_5 = 5;
  /** Ordinal value for level_6. */
  public static final int LEVEL_6 = 6;
  /** Ordinal value for level_7. */
  public static final int LEVEL_7 = 7;
  /** Ordinal value for level_8. */
  public static final int LEVEL_8 = 8;
  /** Ordinal value for level_9. */
  public static final int LEVEL_9 = 9;
  /** Ordinal value for level_10. */
  public static final int LEVEL_10 = 10;
  /** Ordinal value for level_11. */
  public static final int LEVEL_11 = 11;
  /** Ordinal value for level_12. */
  public static final int LEVEL_12 = 12;
  /** Ordinal value for level_13. */
  public static final int LEVEL_13 = 13;
  /** Ordinal value for level_14. */
  public static final int LEVEL_14 = 14;
  /** Ordinal value for level_15. */
  public static final int LEVEL_15 = 15;
  /** Ordinal value for level_16. */
  public static final int LEVEL_16 = 16;
  /** Ordinal value for fallback. */
  public static final int FALLBACK = 17;

  /** BPriorityLevel constant for none. */
  public static final BPriorityLevel none = new BPriorityLevel(NONE);
  /** BPriorityLevel constant for level_1. */
  public static final BPriorityLevel level_1 = new BPriorityLevel(LEVEL_1);
  /** BPriorityLevel constant for level_2. */
  public static final BPriorityLevel level_2 = new BPriorityLevel(LEVEL_2);
  /** BPriorityLevel constant for level_3. */
  public static final BPriorityLevel level_3 = new BPriorityLevel(LEVEL_3);
  /** BPriorityLevel constant for level_4. */
  public static final BPriorityLevel level_4 = new BPriorityLevel(LEVEL_4);
  /** BPriorityLevel constant for level_5. */
  public static final BPriorityLevel level_5 = new BPriorityLevel(LEVEL_5);
  /** BPriorityLevel constant for level_6. */
  public static final BPriorityLevel level_6 = new BPriorityLevel(LEVEL_6);
  /** BPriorityLevel constant for level_7. */
  public static final BPriorityLevel level_7 = new BPriorityLevel(LEVEL_7);
  /** BPriorityLevel constant for level_8. */
  public static final BPriorityLevel level_8 = new BPriorityLevel(LEVEL_8);
  /** BPriorityLevel constant for level_9. */
  public static final BPriorityLevel level_9 = new BPriorityLevel(LEVEL_9);
  /** BPriorityLevel constant for level_10. */
  public static final BPriorityLevel level_10 = new BPriorityLevel(LEVEL_10);
  /** BPriorityLevel constant for level_11. */
  public static final BPriorityLevel level_11 = new BPriorityLevel(LEVEL_11);
  /** BPriorityLevel constant for level_12. */
  public static final BPriorityLevel level_12 = new BPriorityLevel(LEVEL_12);
  /** BPriorityLevel constant for level_13. */
  public static final BPriorityLevel level_13 = new BPriorityLevel(LEVEL_13);
  /** BPriorityLevel constant for level_14. */
  public static final BPriorityLevel level_14 = new BPriorityLevel(LEVEL_14);
  /** BPriorityLevel constant for level_15. */
  public static final BPriorityLevel level_15 = new BPriorityLevel(LEVEL_15);
  /** BPriorityLevel constant for level_16. */
  public static final BPriorityLevel level_16 = new BPriorityLevel(LEVEL_16);
  /** BPriorityLevel constant for fallback. */
  public static final BPriorityLevel fallback = new BPriorityLevel(FALLBACK);

  /** Factory method with ordinal. */
  public static BPriorityLevel make(int ordinal)
  {
    return (BPriorityLevel)none.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BPriorityLevel make(String tag)
  {
    return (BPriorityLevel)none.getRange().get(tag);
  }

  /** Private constructor. */
  private BPriorityLevel(int ordinal)
  {
    super(ordinal);
  }

  public static final BPriorityLevel DEFAULT = none;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPriorityLevel.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public String getDisplayTag(Context cx)
  {
    if(getOrdinal()==FALLBACK)
    {
      // See issue 14126 for why we did this 
      return Lexicon.make("control", cx).get("def");
    }
    else
      return super.getDisplayTag(cx);
  }
}
