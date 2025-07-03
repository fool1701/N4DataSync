/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.*;

/**
 * BMonth is an enum for the months of the year.
 *
 * @author    Brian Frank
 * @creation  19 Feb 00
 * @version   $Revision: 26$ $Date: 3/28/05 9:23:11 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("january"),
    @Range("february"),
    @Range("march"),
    @Range("april"),
    @Range("may"),
    @Range("june"),
    @Range("july"),
    @Range("august"),
    @Range("september"),
    @Range("october"),
    @Range("november"),
    @Range("december")
  }
)
public final class BMonth
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BMonth(1906332948)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for january. */
  public static final int JANUARY = 0;
  /** Ordinal value for february. */
  public static final int FEBRUARY = 1;
  /** Ordinal value for march. */
  public static final int MARCH = 2;
  /** Ordinal value for april. */
  public static final int APRIL = 3;
  /** Ordinal value for may. */
  public static final int MAY = 4;
  /** Ordinal value for june. */
  public static final int JUNE = 5;
  /** Ordinal value for july. */
  public static final int JULY = 6;
  /** Ordinal value for august. */
  public static final int AUGUST = 7;
  /** Ordinal value for september. */
  public static final int SEPTEMBER = 8;
  /** Ordinal value for october. */
  public static final int OCTOBER = 9;
  /** Ordinal value for november. */
  public static final int NOVEMBER = 10;
  /** Ordinal value for december. */
  public static final int DECEMBER = 11;

  /** BMonth constant for january. */
  public static final BMonth january = new BMonth(JANUARY);
  /** BMonth constant for february. */
  public static final BMonth february = new BMonth(FEBRUARY);
  /** BMonth constant for march. */
  public static final BMonth march = new BMonth(MARCH);
  /** BMonth constant for april. */
  public static final BMonth april = new BMonth(APRIL);
  /** BMonth constant for may. */
  public static final BMonth may = new BMonth(MAY);
  /** BMonth constant for june. */
  public static final BMonth june = new BMonth(JUNE);
  /** BMonth constant for july. */
  public static final BMonth july = new BMonth(JULY);
  /** BMonth constant for august. */
  public static final BMonth august = new BMonth(AUGUST);
  /** BMonth constant for september. */
  public static final BMonth september = new BMonth(SEPTEMBER);
  /** BMonth constant for october. */
  public static final BMonth october = new BMonth(OCTOBER);
  /** BMonth constant for november. */
  public static final BMonth november = new BMonth(NOVEMBER);
  /** BMonth constant for december. */
  public static final BMonth december = new BMonth(DECEMBER);

  /** Factory method with ordinal. */
  public static BMonth make(int ordinal)
  {
    return (BMonth)january.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BMonth make(String tag)
  {
    return (BMonth)january.getRange().get(tag);
  }

  /** Private constructor. */
  private BMonth(int ordinal)
  {
    super(ordinal);
  }

  public static final BMonth DEFAULT = january;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMonth.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the month as an integer.  (e.g. January = 1)
   */
  public int getMonthOfYear()
  {
    return getOrdinal() + 1;
  }

  /**
   * Get the month's abbreviated localized name.
   */
  public String getShortDisplayTag(Context cx)
  {
    return Lexicon.make(Sys.getBajaModule(), cx).getText(getTag()+".short");
  }

  /**
   * Get the next month.
   */  
  public BMonth next()
  {
    int ord = getOrdinal();
    ord = (ord + 1) % 12;
    return make(ord);
  }

  /**
   * Get the previous month.
   */  
  public BMonth previous()
  {
    int ord = getOrdinal();
    ord = ord - 1;
    if (ord == -1) ord = 11;
    return make(ord);
  }
    
}
