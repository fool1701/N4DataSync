/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics;

import javax.baja.status.*;

/**
 * Commonly used constants in the Niagara Analytics Framework.
 *
 * @author Aaron Hansen
 */
public interface AnalyticConstants
{

  public static final int COMBINATION_NONE = -1;
  public static final int COMBINATION_AND = 0;
  public static final int COMBINATION_AVG = 1;
  public static final int COMBINATION_COUNT = 2;
  public static final int COMBINATION_FIRST = 3;
  public static final int COMBINATION_LAST = 4;
  public static final int COMBINATION_MAX = 5;
  public static final int COMBINATION_MEDIAN = 6;
  public static final int COMBINATION_MIN = 7;
  public static final int COMBINATION_MODE = 8;
  public static final int COMBINATION_OR = 9;
  public static final int COMBINATION_RANGE = 10;
  public static final int COMBINATION_SUM = 11;
  public static final int COMBINATION_TREND = 12;
  public static final int COMBINATION_LOADFACTOR = 13;
  public static final int COMBINATION_STD_DEVIATION = 14;
  public static final int COMBINATION_HEATING_DEGREE_DAY = 15;
  public static final int COMBINATION_COOLING_DEGREE_DAY = 16;

  public static final int INTERVAL_NONE = 0;
  public static final int INTERVAL_SECOND = 1;
  public static final int INTERVAL_FIVE_SECONDS = 2;
  public static final int INTERVAL_TEN_SECONDS = 3;
  public static final int INTERVAL_FIFTEEN_SECONDS = 4;
  public static final int INTERVAL_THIRTY_SECONDS = 5;
  public static final int INTERVAL_MINUTE = 6;
  public static final int INTERVAL_FIVE_MINUTES = 7;
  public static final int INTERVAL_TEN_MINUTES = 8;
  public static final int INTERVAL_FIFTEEN_MINUTES = 9;
  public static final int INTERVAL_TWENTY_MINUTES = 10;
  public static final int INTERVAL_THIRTY_MINUTES = 11;
  public static final int INTERVAL_HOUR = 12;
  public static final int INTERVAL_TWO_HOURS = 13;
  public static final int INTERVAL_THREE_HOURS = 14;
  public static final int INTERVAL_FOUR_HOURS = 15;
  public static final int INTERVAL_SIX_HOURS = 16;
  public static final int INTERVAL_TWELVE_HOURS = 17;
  public static final int INTERVAL_DAY = 18;
  public static final int INTERVAL_WEEK = 19;
  public static final int INTERVAL_MONTH = 20;
  public static final int INTERVAL_QUARTER = 21;
  public static final int INTERVAL_YEAR = 22;

  public static final long MILLIS_SECOND = 1000l;
  public static final long MILLIS_FIVE_SECONDS = 5000l;
  public static final long MILLIS_TEN_SECONDS = 10000l;
  public static final long MILLIS_FIFTEEN_SECONDS = 15000l;
  public static final long MILLIS_THIRTY_SECONDS = 30000l;
  public static final long MILLIS_MINUTE = 60000l;
  public static final long MILLIS_FIVE_MINUTES = 300000l;
  public static final long MILLIS_TEN_MINUTES = 600000l;
  public static final long MILLIS_FIFTEEN_MINUTES = 900000l;
  public static final long MILLIS_TWENTY_MINUTES = 1200000l;
  public static final long MILLIS_THIRTY_MINUTES = 1800000l;
  public static final long MILLIS_HOUR = 3600000l;
  public static final long MILLIS_TWO_HOURS = 7200000l;
  public static final long MILLIS_THREE_HOURS = 10800000l;
  public static final long MILLIS_FOUR_HOURS = 14400000l;
  public static final long MILLIS_SIX_HOURS = 21600000l;
  public static final long MILLIS_TWELVE_HOURS = 43200000l;
  public static final long MILLIS_DAY = 86400000l;
  public static final long MILLIS_WEEK = 604800000l;
  public static final long MILLIS_MONTH = 2592000000l;
  public static final long MILLIS_QUARTER = MILLIS_MONTH * 3;
  public static final long MILLIS_YEAR = MILLIS_MONTH * 12;
  public static final long MILLIS_DECADE = MILLIS_YEAR * 10;
  public static final long MILLIS_CENTURY = MILLIS_YEAR * 100;

  /**
   * The algorithm namespace.
   */
  public static final String NS_ALGO = "alg";

  /*Constants Releated to Facets in AnalyticTrend*/
  public static final int DEFAULT_PRECESION = 2;
  public static final String FIRST_TIMESTAMP = "firstTimestamp";
  public static final String LAST_TIMESTAMP = "lastTimestamp";
  public static final String START_TIME = "startTime";
  public static final String END_TIME = "endTime";
  public static final String TIME_ZONE = "timeZone";
  public static final String SERIES_NAME = "seriesName";
  public static final String MULTI_TREND_FACET = "mt";
  public static final String MULTI_TREND_COUNT = "tc";


  /**
   * 0x0001, values with this status are considered invalid.
   */
  public static final int STATUS_DISABLED = BStatus.DISABLED; //0x0001;
  /**
   * 0x0002, values with this status are considered invalid.
   */
  public static final int STATUS_FAULT = BStatus.FAULT; //0x0002;
  /**
   * 0x0004, values with this status are considered invalid.
   */
  public static final int STATUS_DOWN = BStatus.DOWN; //0x0004;
  /**
   * 0x0008, the value is valid.
   */
  public static final int STATUS_ALARM = BStatus.ALARM; //0x0008;
  /**
   * 0x0010, values with this status are considered invalid.
   */
  public static final int STATUS_STALE = BStatus.STALE; //0x0010;
  /**
   * 0x0020, the value is valid.
   */
  public static final int STATUS_OVERRIDDEN = BStatus.OVERRIDDEN; //0x0020;
  /**
   * 0x0040, values with this status are considered invalid.
   */
  public static final int STATUS_NULL = BStatus.NULL; //0x0040;
  /**
   * 0x0080, the value is valid.
   */
  public static final int STATUS_UNACKED_ALARM = BStatus.UNACKED_ALARM; //0x0080;

  public static final int VALUE_BOOLEAN = 0;
  public static final int VALUE_ENUM = 1;
  public static final int VALUE_NUMERIC = 2;
  public static final int VALUE_STRING = 3;

  /////////////////////////////////////////////////////////////////
  // Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////
   /*
   MDC vicinity calculation function
   Percentage tolerance applicable for the time stamp equivalance comparison
    */
  public static short findVicinityTolerance(long interval)
  {
    short vicinityTolerance;
    if (interval <= 5000)
    {
      vicinityTolerance = 50;
    }
    else if (interval > 5000 && interval <= 3600000) // 5 seconds to 1hr
    {
      vicinityTolerance = 40;
    }
    else if (interval > 3600000 && interval < 21600000) //1hr to 6 hr
    {
      vicinityTolerance = 10;
    }
    else
    {
      vicinityTolerance = 5;
    }
    return vicinityTolerance;
  }
}
