/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics;

import java.util.Map;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.units.BUnit;
import javax.bajax.analytics.data.AnalyticValue;
import javax.bajax.analytics.data.BCombination;
import javax.bajax.analytics.data.Combination;
import javax.bajax.analytics.data.Combiner;
import javax.bajax.analytics.time.BInterval;
import com.tridiumx.analytics.AnalyticContextWrapper;
import com.tridiumx.analytics.combine.AbstractCombiner;
import com.tridiumx.analytics.data.NumericValue;

public class DegreeDayAnalyticContext extends AnalyticContext
{

  private DegreeDayAnalyticContext(Context base)
  {
    super(base);
  }


  /**
   * In default AnalyticContext the values will be converted as per data policy before any calculation.
   * For degree day calculation, the temperature should be converted to same unit as base temperature.
   *
   * @param force - true will cause the context to fetch unit using additional logic.
   * @return BUnit
   */

  @Override
  public BUnit getUnit(boolean force)
  {
    if (unit != null) return unit;
    if (!force) return null;
    BUnit ret = getBaseTempUnit();
    if (ret != null) return ret;
    return BUnit.NULL;
  }


  @Override
  public Combination getRollup(boolean force)
  {
    return rollup;
  }

  @Override
  public Combination getAggregation(boolean force)
  {
    return BCombination.avg;
  }

  public BDouble getBaseTemp()
  {
    return baseTemp;
  }

  public DegreeDayAnalyticContext setBaseTemp(BDouble baseTemp)
  {
    this.baseTemp = baseTemp;
    return this;
  }


  public BUnit getBaseTempUnit()
  {
    if (baseTempUnit != null && !baseTempUnit.isNull())
    {
      return baseTempUnit;
    }
    if (getDataPolicy().getFacets() != null)
    {
      return (BUnit)getDataPolicy().getFacets().get(BFacets.UNITS);
    }

    return null;
  }

  public DegreeDayAnalyticContext setBaseTempUnit(BUnit baseTempUnit)
  {
    this.baseTempUnit = baseTempUnit;
    return this;
  }

  @Override
  public BUnit getInnermostUnit()
  {
    return null;
  }

  public AnalyticContext newCopy()
  {
    return new DegreeDayAnalyticContext(new BasicContext(getUser()))
      .setBaseTemp(getBaseTemp())
      .setBaseTempUnit(getBaseTempUnit())
      .setAggregation(getAggregation(true))
      .setData(getData())
      .setDataFilter(getDataFilter())
      .setLeadingDeltas(getLeadingDeltas())
      .setInterval(getInterval(true))
      .setNode(getNode())
      .setRollup(getRollup(true))
      .setTimeRange(getTimeRange(true))
      .setTimeZone(getTimeZone())
      .setNormalizeTime(getNormalizeTime())
      .setUnit(getUnit(false))
      .setUseCache(getUseCache())
      .setDowInclusion(getDowInclusion().encodeToString())
      .setSeriesName(this.seriesName)
      .setHistory(getHistory().getBoolean())
      .setHisTotEnabled(getHisTotEnabled());
  }


  private BDouble baseTemp;
  private BUnit baseTempUnit;


  private enum DegreeDay
  {

    HEATING(COMBINATION_HEATING_DEGREE_DAY, "heating"),
    COOLING(COMBINATION_COOLING_DEGREE_DAY, "cooling");

    private int ordinal;
    private String tag;

    public static final DegreeDay DEFAULT = HEATING;

    DegreeDay(int ordinal, String tag)
    {
      this.ordinal = ordinal;
      this.tag = tag;
    }

    public DegreeDay getByTag(String tag)
    {
      switch (tag)
      {
        case "heating":
          return HEATING;
        case "cooling":
          return COOLING;
        default:
          return DEFAULT;
      }
    }

    public int getOrdinal()
    {
      return ordinal;
    }

    public String getTag()
    {
      return tag;
    }

  }

  /**
   * Not added to BCombination so that it doesn't appear in field editors.
   */
  private static final class DegreeDayCombination implements Combination
  {

    public DegreeDayCombination(double baseTemp, DegreeDay degreeDay)
    {
      this.degreeDayCombiner = new DegreeDayCombiner(baseTemp, degreeDay);
    }

    @Override
    public int getOrdinal()
    {
      return this.degreeDayCombiner.getDegreeDay().getOrdinal();
    }

    @Override
    public String getTag()
    {
      return this.degreeDayCombiner.getDegreeDay().getTag();
    }

    @Override
    public Combiner makeCombiner()
    {
      return degreeDayCombiner;
    }

    private DegreeDayCombiner degreeDayCombiner;
  }


  /**
   * Degree Day combiner for calculating degree day for a particular day from series of
   * OAT data for the day.
   * Heating Degree Day is applicable if the OAT is less than the base temperature else taken as 0.
   * Cooling Degree Day is applicable if the OAT is greater than the base temperature else taken as 0.
   * <p>
   * The output will represent the degree day for a day, this value can then be added up to calculate
   * degree day for higher intervals.
   */
  private static final class DegreeDayCombiner extends AbstractCombiner
  {

    protected DegreeDayCombiner(double baseTemp, DegreeDay degreeDay)
    {
      super(degreeDay.ordinal);
      this.baseTemp = baseTemp;
      this.degreeDay = degreeDay;
    }

    public DegreeDay getDegreeDay()
    {
      return degreeDay;
    }

    @Override
    protected void doReset()
    {
      value = 0;
    }


    @Override
    protected boolean doUpdate(AnalyticValue arg)
    {
      double tempValue = arg.toNumeric();
      switch (degreeDay)
      {
        case HEATING:
          if (tempValue < baseTemp)
          {
            tempValue = Math.abs(arg.toNumeric() - baseTemp);
          }
          else
          {
            // Returning false to skip count increment
            return false;
          }
          break;
        case COOLING:
          if (tempValue > baseTemp)
          {
            tempValue = Math.abs(arg.toNumeric() - baseTemp);
          }
          else
          {
            // Returning false to skip count increment
            return false;
          }
          break;
      }
      if (getCount() == 0)
      {
        value = tempValue;
        analytic.copyTimestamp(arg);
        analytic.copyStatus(arg);
      }
      else
      {
        value += tempValue;
        analytic.copyTimestamp(arg);
        analytic.orStatus(arg);
      }
      return true;
    }

    @Override
    public AnalyticValue getValue()
    {
      double avg = 0;
      int count = getCount();
      if (count != 0) avg = value / ((double)count);
      analytic.setValue(avg);
      return analytic;
    }

    private NumericValue analytic = new NumericValue();
    private double value = 0;
    private double baseTemp;
    private DegreeDay degreeDay;
  }


  /**
   * Degree day calculation requires two level rollup.
   * First step - calculate the degree day for each interval and average at the day level.
   * Second step - if the interval is higher than day, sum up the degree day for days.
   * <p>
   * Wrapper context will do the first step.
   * Inner context will be used for the second step.
   */
  public static class DegreeDayAnalyticContextWrapper
    extends AnalyticContextWrapper
  {


    private DegreeDayAnalyticContextWrapper(DegreeDayAnalyticContext arg)
    {
      super(arg);
    }

    public static DegreeDayAnalyticContextWrapper make(Map<String, String> args, Context base)
    {

      DegreeDayAnalyticContext inner = (DegreeDayAnalyticContext)AnalyticContext.updateAnalyticContext(args, new DegreeDayAnalyticContext(base));

      String str = args.get("baseTemp");
      if (str != null)
      {
        inner.setBaseTemp(BDouble.make(str));
      }
      else{
        inner.setBaseTemp(BDouble.DEFAULT);
      }

      str = args.get("baseTempUnit");
      if (str != null)
      {
        inner.setBaseTempUnit(BUnit.getUnit(str));
      }

      str = args.get("degreeDay");
      DegreeDay degreeDay = DegreeDay.DEFAULT;
      if (str != null)
      {
        degreeDay = DegreeDay.DEFAULT.getByTag(str);
      }

      DegreeDayAnalyticContextWrapper wrapper = new DegreeDayAnalyticContextWrapper(inner);
      wrapper.setInterval(BInterval.day);
      wrapper.setRollup(new DegreeDayCombination(inner.getBaseTemp().getDouble(), degreeDay));
      wrapper.setUnit(null);

      inner.setRollup(BCombination.sum);
      if (inner.getInterval(true).getOrdinal() < wrapper.getInterval(true).getOrdinal())
      {
        inner.setInterval(BInterval.day);
      }

      return wrapper;
    }

    public BDouble getBaseTemp()
    {
      return ((DegreeDayAnalyticContext)getInner()).getBaseTemp();
    }


    public BUnit getBaseTempUnit()
    {
      BUnit baseTempUnit = ((DegreeDayAnalyticContext)getInner()).getBaseTempUnit();

      if (baseTempUnit != null && !baseTempUnit.isNull())
      {
        return baseTempUnit;
      }
      if (getDataPolicy().getFacets() != null)
      {
        return (BUnit)getDataPolicy().getFacets().get(BFacets.UNITS);
      }

      return null;
    }

    @Override
    public Combination getRollup(boolean force)
    {
      return rollup;
    }

    @Override
    public BUnit getUnit(boolean force)
    {
      if (unit != null) return unit;
      if (!force) return null;
      BUnit ret = getBaseTempUnit();
      if (ret != null) return ret;
      return BUnit.NULL;
    }

    @Override
    public Combination getAggregation(boolean force)
    {
      return BCombination.avg;
    }

    @Override
    public AnalyticContext newCopy()
    {
      return new DegreeDayAnalyticContext(new BasicContext(getUser()))
        .setBaseTemp(getBaseTemp())
        .setBaseTempUnit(getBaseTempUnit())
        .setAggregation(getAggregation(true))
        .setData(getData())
        .setDataFilter(getDataFilter())
        .setLeadingDeltas(getLeadingDeltas())
        .setInterval(getInterval(true))
        .setNode(getNode())
        .setRollup(getRollup(true))
        .setTimeRange(getTimeRange(true))
        .setTimeZone(getTimeZone())
        .setNormalizeTime(getNormalizeTime())
        .setUnit(getUnit(false))
        .setUseCache(getUseCache())
        .setDowInclusion(getDowInclusion().encodeToString())
        .setSeriesName(this.seriesName)
        .setHistory(getHistory().getBoolean())
        .setHisTotEnabled(getHisTotEnabled());
    }
  }
}
