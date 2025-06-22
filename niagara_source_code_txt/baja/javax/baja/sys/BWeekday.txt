/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

/**
 * BWeekday is an enum for the weekdays of the week.
 *
 * @author    Brian Frank
 * @creation  1 Feb 00
 * @version   $Revision: 26$ $Date: 3/28/05 9:23:13 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("sunday"),
    @Range("monday"),
    @Range("tuesday"),
    @Range("wednesday"),
    @Range("thursday"),
    @Range("friday"),
    @Range("saturday")
  }
)
public final class BWeekday
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BWeekday(2942494763)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for sunday. */
  public static final int SUNDAY = 0;
  /** Ordinal value for monday. */
  public static final int MONDAY = 1;
  /** Ordinal value for tuesday. */
  public static final int TUESDAY = 2;
  /** Ordinal value for wednesday. */
  public static final int WEDNESDAY = 3;
  /** Ordinal value for thursday. */
  public static final int THURSDAY = 4;
  /** Ordinal value for friday. */
  public static final int FRIDAY = 5;
  /** Ordinal value for saturday. */
  public static final int SATURDAY = 6;

  /** BWeekday constant for sunday. */
  public static final BWeekday sunday = new BWeekday(SUNDAY);
  /** BWeekday constant for monday. */
  public static final BWeekday monday = new BWeekday(MONDAY);
  /** BWeekday constant for tuesday. */
  public static final BWeekday tuesday = new BWeekday(TUESDAY);
  /** BWeekday constant for wednesday. */
  public static final BWeekday wednesday = new BWeekday(WEDNESDAY);
  /** BWeekday constant for thursday. */
  public static final BWeekday thursday = new BWeekday(THURSDAY);
  /** BWeekday constant for friday. */
  public static final BWeekday friday = new BWeekday(FRIDAY);
  /** BWeekday constant for saturday. */
  public static final BWeekday saturday = new BWeekday(SATURDAY);

  /** Factory method with ordinal. */
  public static BWeekday make(int ordinal)
  {
    return (BWeekday)sunday.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BWeekday make(String tag)
  {
    return (BWeekday)sunday.getRange().get(tag);
  }

  /** Private constructor. */
  private BWeekday(int ordinal)
  {
    super(ordinal);
  }

  public static final BWeekday DEFAULT = sunday;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWeekday.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Checks the lexicon for weekday.firstDayOfWeekName
   * The value should coorespond to one of the .name day values.
   */
  public static BWeekday getFirstDayOfWeek(Context cx)
  {
    Lexicon lex = Lexicon.make(TYPE.getModule(),cx);
    String tag = lex.get("weekday.firstDayOfWeek",null);
    if (tag == null) 
      return sunday;
    return BWeekday.make(tag);
  }

  /**
   * Get the weekdays's abbreviated localized name.
   */
  public String getShortDisplayTag(Context cx)
  {
    return Lexicon.make(Sys.getBajaModule(), cx).getText(getTag()+".short");
  }

  /**
   * Get the next weekday.
   */  
  public BWeekday next()
  {
    int ord = getOrdinal();
    ord = (ord + 1) % 7;
    return make(ord);
  }

  /**
   * Get the previous weekday.
   */  
  public BWeekday previous()
  {
    int ord = getOrdinal();
    ord = ord - 1;
    if (ord == -1) ord = 6;
    return make(ord);
  }
        
}
