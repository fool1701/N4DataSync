/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics;

import com.tridiumx.analytics.util.Strings;

import javax.baja.sys.BBoolean;
import javax.baja.sys.Context;
import javax.bajax.analytics.data.BCombination;
import javax.bajax.analytics.data.Combination;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Encapsulates the details about a request for the Niagara Analytics
 * Framework.
 *
 * @author Aaron Hansen
 * @see AnalyticContext
 */
public class MultiAnalyticContext extends AnalyticContext
{

  public MultiAnalyticContext(Context base)
  {
    super(base);
  }

  public MultiAnalyticContext setAggMode(boolean arg)
  {
    this.aggMode = BBoolean.make(arg);
    return this;
  }

  public boolean getAggMode()
  {
    return this.aggMode != null && this.aggMode.getBoolean();
  }

  public MultiAnalyticContext setAgg(Combination arg)
  {
    this.agg = arg;
    return this;
  }

  public Combination getAgg()
  {
    if (agg != null)
    {
      return agg;
    }
    return BCombination.first;
  }

  /**
   * Get the forced value of agg
   * @param force
   * @return
   */
  public Combination getAgg(boolean force)
  {
    if ((agg == null) && force)
      return getDataPolicy().getDefaultAggregation();
    return getAgg();
  }


  public MultiAnalyticContext setMutliParams(List<Map<String, String>> args)
  {
    this.multiParams = args;
    return this;
  }


  public List<Map<String, String>> getMultiParams()
  {
    return this.multiParams;
  }


  public static MultiAnalyticContext make(Map<String, String> args, Context base)
  {
    MultiAnalyticContext cx = (MultiAnalyticContext)AnalyticContext.updateAnalyticContext(args, new MultiAnalyticContext(base));
    String str = args.get("multiOrd");
    if (str != null)
    {
      cx.setMutliParams(Arrays.asList(str.split(","))
        .stream()
        .filter(Objects::nonNull)
        .map(s -> Strings.decodeQueryString(s))
        .collect(Collectors.toList()));
    }
    str = args.get("aggMode");
    if (str != null)
    {
      cx.aggMode = BBoolean.make(str);
    }
    str = args.get("agg");
    if (str != null)
    {
      cx.setAgg(BCombination.make(str));
    }
    return cx;
  }

// Commented the below method to use parent's facets. MultiScheme facets are unique, as the meta can belong to
//  every group/ord in the schemes.
//  public BFacets getFacets()
//  {
//   return null;
//  }

  @Override
  public Combination getRollup(boolean force)
  {
    return rollup;
  }

  @Override
  public Combination getAggregation(boolean force)
  {
    return aggregation;
  }

  protected List<Map<String, String>> multiParams;
  protected BBoolean aggMode;
  protected Combination agg;
  protected String toString;
  private AnalyticContext base;


}
