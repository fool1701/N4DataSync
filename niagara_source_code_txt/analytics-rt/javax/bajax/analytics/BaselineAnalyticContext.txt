/*
 *
 * Copyright 2022 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics;

import java.util.Map;
import javax.baja.sys.BBoolean;
import javax.baja.sys.Context;
import javax.bajax.analytics.time.BAnalyticTimeRange;
import javax.bajax.analytics.time.TimeRange;

/**
 * Encapsulates the details about a request for the Niagara Analytics
 * Framework.
 *
 * @author h129264
 * @see BaselineAnalyticContext
 */
public class BaselineAnalyticContext extends AnalyticContext
{

  public BaselineAnalyticContext(Context base)
  {
    super(base);
  }

  public TimeRange getBaselineTimeRange()
  {
    return baselineTimeRange;
  }

  public void setBaselineTimeRange(TimeRange baselineTimeRange)
  {
    this.baselineTimeRange = baselineTimeRange;
  }

  public BBoolean isAlignDow()
  {
    return alignDow;
  }

  public void setAlignDow(BBoolean alignDow)
  {
    this.alignDow = alignDow;
  }

  public static BaselineAnalyticContext make(Map<String, String> args, Context base)
  {
    BaselineAnalyticContext cx = (BaselineAnalyticContext) AnalyticContext.updateAnalyticContext(args, new BaselineAnalyticContext(base));

    String value;

    if ((value = args.get("baselineTimeRange")) != null)
    {
      cx.setBaselineTimeRange(BAnalyticTimeRange.make(value));
    }

    if ((value = args.get("alignDow")) != null)
    {
      cx.setAlignDow(BBoolean.make(value));
    }

    return cx;
  }

  protected TimeRange baselineTimeRange;
  protected BBoolean alignDow;
  protected BBoolean baseline = BBoolean.DEFAULT;

}
