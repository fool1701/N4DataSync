/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.data;

import javax.bajax.analytics.AnalyticConstants;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridiumx.analytics.combine.*;
import com.tridiumx.analytics.util.Utils;


/**
 * Niagara implementation of the Combination interface.
 *
 * @author Aaron Hansen
 * @see Combination
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range(value = "and", ordinal = 0),
    @Range(value = "avg", ordinal = 1),
    @Range(value = "count", ordinal = 2),
    @Range(value = "first", ordinal = 3),
    @Range(value = "last", ordinal = 4),
    @Range(value = "max", ordinal = 5),
    @Range(value = "median", ordinal = 6),
    @Range(value = "min", ordinal = 7),
    @Range(value = "mode", ordinal = 8),
    @Range(value = "or", ordinal = 9),
    @Range(value = "range", ordinal = 10),
    @Range(value = "sum", ordinal = 11),
    @Range(value = "loadFactor", ordinal = 13),
    @Range(value = "stdDev", ordinal = 14)
  }
)
public final class BCombination extends BFrozenEnum
  implements Combination
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.bajax.analytics.data.BCombination(3041921236)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for and. */
  public static final int AND = 0;
  /** Ordinal value for avg. */
  public static final int AVG = 1;
  /** Ordinal value for count. */
  public static final int COUNT = 2;
  /** Ordinal value for first. */
  public static final int FIRST = 3;
  /** Ordinal value for last. */
  public static final int LAST = 4;
  /** Ordinal value for max. */
  public static final int MAX = 5;
  /** Ordinal value for median. */
  public static final int MEDIAN = 6;
  /** Ordinal value for min. */
  public static final int MIN = 7;
  /** Ordinal value for mode. */
  public static final int MODE = 8;
  /** Ordinal value for or. */
  public static final int OR = 9;
  /** Ordinal value for range. */
  public static final int RANGE = 10;
  /** Ordinal value for sum. */
  public static final int SUM = 11;
  /** Ordinal value for loadFactor. */
  public static final int LOAD_FACTOR = 13;
  /** Ordinal value for stdDev. */
  public static final int STD_DEV = 14;

  /** BCombination constant for and. */
  public static final BCombination and = new BCombination(AND);
  /** BCombination constant for avg. */
  public static final BCombination avg = new BCombination(AVG);
  /** BCombination constant for count. */
  public static final BCombination count = new BCombination(COUNT);
  /** BCombination constant for first. */
  public static final BCombination first = new BCombination(FIRST);
  /** BCombination constant for last. */
  public static final BCombination last = new BCombination(LAST);
  /** BCombination constant for max. */
  public static final BCombination max = new BCombination(MAX);
  /** BCombination constant for median. */
  public static final BCombination median = new BCombination(MEDIAN);
  /** BCombination constant for min. */
  public static final BCombination min = new BCombination(MIN);
  /** BCombination constant for mode. */
  public static final BCombination mode = new BCombination(MODE);
  /** BCombination constant for or. */
  public static final BCombination or = new BCombination(OR);
  /** BCombination constant for range. */
  public static final BCombination range = new BCombination(RANGE);
  /** BCombination constant for sum. */
  public static final BCombination sum = new BCombination(SUM);
  /** BCombination constant for loadFactor. */
  public static final BCombination loadFactor = new BCombination(LOAD_FACTOR);
  /** BCombination constant for stdDev. */
  public static final BCombination stdDev = new BCombination(STD_DEV);

  /** Factory method with ordinal. */
  public static BCombination make(int ordinal)
  {
    return (BCombination)and.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BCombination make(String tag)
  {
    return (BCombination)and.getRange().get(tag);
  }

  /** Private constructor. */
  private BCombination(int ordinal)
  {
    super(ordinal);
  }

  public static final BCombination DEFAULT = and;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCombination.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Makes a combiner for the specific combination.
   */
  public Combiner makeCombiner()
  {
    switch (getOrdinal())
    {
      case COMBINATION_AND :
        return new AndCombiner();
      case COMBINATION_AVG :
        return new AvgCombiner();
      case COMBINATION_COUNT :
        return new CountCombiner();
      case COMBINATION_FIRST :
        return new FirstCombiner();
      case COMBINATION_LAST :
        return new LastCombiner();
      case COMBINATION_MAX :
        return new MaxCombiner();
      case COMBINATION_MEDIAN :
        return new MedianCombiner();
      case COMBINATION_MIN :
        return new MinCombiner();
      case COMBINATION_MODE :
        return new ModeCombiner();
      case COMBINATION_OR :
        return new OrCombiner();
      case COMBINATION_RANGE :
        return new RangeCombiner();
      case COMBINATION_SUM :
        return new SumCombiner();
      case COMBINATION_LOADFACTOR :
        return new LoadFactorCombiner();
      case COMBINATION_STD_DEVIATION :
        return new StandardDeviationCombiner();
    }
    throw new IllegalStateException(
        Utils.lex("unknownCombination") + ": " + getOrdinal());
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Default and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Private and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Inner Classes - in alphabetical order by class name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Constants - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//BCombination
