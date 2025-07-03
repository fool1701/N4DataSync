/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.algorithm;

import java.util.logging.Level;
import javax.bajax.analytics.AnalyticContext;
import javax.bajax.analytics.data.AnalyticTrend;
import javax.bajax.analytics.data.AnalyticValue;
import javax.bajax.analytics.time.Interval;
import com.tridiumx.analytics.AnalyticContextWrapper;
import com.tridiumx.analytics.algorithm.AlgorithmBlock;
import com.tridiumx.analytics.trend.AbstractTrend;
import com.tridiumx.analytics.trend.IntervalTrend;
import com.tridiumx.analytics.util.Utils;

/**
 * A trend composed of multiple datasources, only one of which has to have
 * a trend.
 * <p>
 * Subclasses must implement the getNext method. The subclass is responsible
 * for calling advance and it can call it multiple times before returning
 * a value.
 * <p>
 * <pre>
 *   <code>
 *   // Calls eval for each interval.
 *   //
 *   class MyTrend extends BlockTrend
 *   {
 *     public MyTrend(AlgorithmBlock block, AnalyticContext cx)
 *     {
 *       super(block,cx);
 *     }
 *     protected AnalyticValue getNext()
 *     {
 *       if (!advance()){
 *        return null;
 *       }
 *       return eval(getValue(0),getValue(1));
 *     }
 *
 *     private AnalyticValue eval(AnalyticValue in1, AnalyticValue in2)
 *     {
 *       AnalyticValue ret = in1;
 *       double result = in1.toNumeric() + in2.toNumeric();
 *       int sts = in1.getStatus() | in2.getStatus();
 *       if (Double.isNaN(result) || Double.isInfinite(result)){
 *         sts = sts | STATUS_NULL;
 *       }
 *       if (ret instanceof AnalyticNumeric){
 *         ((AnalyticNumeric)ret).setValue(result);
 *       }
 *       else{
 *         ret.setValue(BDouble.make(result));
 *       }
 *       ret.setStatus(sts);
 *       return ret;
 *    }
 *   }
 *   </code>
 * </pre>
 *
 * @author Aaron Hansen
 * @since NA 2.0
 */
public abstract class BlockTrend extends AbstractTrend
{


  private BlockTrend()
  {
  }

  /**
   * @param block The algorithm block
   * @param cx    Analytic Context
   */
  public BlockTrend(AlgorithmBlock block, AnalyticContext cx)
  {
    super(cx);
    int len = block.getInputCount();
    this.sources = new Source[len];
    AlgorithmBlock input;
    AlgorithmBlock[] inputs = new AlgorithmBlock[len];
    for (int i = 0; i < len; i++)
    {
      input = block.getInput(i);
      inputs[i] = input; //for detecting duplicate inputs
      if (input != null)
      {
        for (int j = 0; j < i; j++)
        {
          if (input == inputs[j])
          {
            sources[i] = new ProxySource(sources[j]);
            break;
          }
        }
      }
      if (sources[i] == null)
      {
        if (input == null)
          sources[i] = new ValueSource(block.getInputDefault(i, cx));
        else if (input.hasTrend(cx))
          sources[i] = new TrendSource(input.getTrend(cx));
        else
          sources[i] = new ValueSource(input.getValue(cx));
      }
    }
    init();
  }


  /**
   * This advances all trends until all timestamps align.  Values of some
   * trends will be skipped when any of the other trends don't have a value
   * with the same timestamp.
   *
   * @return true if the timestamps of the trends align else false
   */
  public boolean advance()
  {
    long last = timestamp;
    long latest = NULL_TS;
    long cur;
    boolean done = false;
    AnalyticValue val;
    while (!done)
    {
      done = true; //repeat until all trends align
      for (int i = 0, len = sources.length; i < len; i++)
      {
        if (sources[i].isTrend())
        {
          val = sources[i].getValue();
          if (val == null)
            return false;
          cur = val.getTimestamp();
          if (latest == NULL_TS)
            latest = cur;
          while ((cur == last) || (cur < latest))
          {
            if (!sources[i].next())
              return false;
            val = sources[i].getValue();
            cur = val.getTimestamp();
          }
          if (cur > latest)
          {
            latest = cur;
            if (i > 0) done = false; //must reloop
          }
        }
        else if (!sources[i].isProxy())
        {
          sources[i].getValue().setTimestamp(latest);
        }
      }//for
    }
    return (timestamp = latest) != NULL_TS;
  }

  /**
   * The timestamp for the current set of values.
   *
   * @return timestamp of the current trend record/value
   */
  public long getTimestamp()
  {
    return timestamp;
  }

  /**
   * The current value for the input at the given index.
   *
   * @param idx the index of the block
   * @return the current value of the algorithm block connected to the index.
   */
  public AnalyticValue getValue(int idx)
  {
    return sources[idx].getValue();
  }


  /**
   * Finds the interval and prepares all of the inputs for iteration.
   */
  private void init()
  {
    int length = sources.length;
    Interval ivl = null;
    //Determine the larget interval
    for (int i = 0; i < length; i++)
    {
      if (sources[i].isTrend())
      {
        Interval tivl = sources[i].getTrend().getContext().getInterval(true);
        if (ivl == null)
        {
          ivl = tivl;
        }
        else if (tivl != null)
        {
          if (tivl.getOrdinal() > ivl.getOrdinal())
            ivl = tivl;
        }
      }
    }
    if (ivl == null)
      return;
    if (!ivl.equals(getContext().getInterval(true)))
    {
      //I'm changing my interval
      AnalyticContextWrapper cx = new AnalyticContextWrapper(getContext());
      cx.setInterval(ivl);
      setContext(cx);
    }
    for (int i = 0; i < length; i++)
    {
      if (sources[i].isTrend())
      {
        if (!ivl.equals(sources[i].getTrend().getContext().getInterval(true)))
        {
          //Changing the interval of the trend
          AnalyticContextWrapper cx = new AnalyticContextWrapper(
            sources[i].getTrend().getContext());
          cx.setInterval(ivl);
          sources[i].setTrend(new IntervalTrend(sources[i].getTrend(), cx));
        }
        sources[i].next();
      }
    }
  }

  @Override
  public void close()
  {
    try
    {
      if (sources != null)
      {
        for (int i = 0; i < sources.length; i++)
        {
          if (sources[i] != null && sources[i].getTrend() != null)
            sources[i].getTrend().close();
        }
      }
    }
    catch (Exception e)
    {
      Utils.log().log(Level.SEVERE, "Some objects could not be closed. There may be potential impact on memory. Try to restart the station if facing memory leak issues. Error: " + e.getMessage());
    }
  }


  /**
   * Common abstraction for trends and non-trends.
   */
  private interface Source
  {
    AnalyticTrend getTrend();

    AnalyticValue getValue();

    boolean isProxy();

    boolean isTrend();

    boolean next();

    void setTrend(AnalyticTrend trend);
  }

  /**
   * Used for duplicate datasources, which will be common.  This eliminates
   * having to call getTrend on the same data source multiple times.
   */
  private static class ProxySource implements Source
  {
    ProxySource(Source source)
    {
      this.source = source;
    }

    public AnalyticTrend getTrend()
    {
      throw new IllegalStateException(Utils.lex("notTrend"));
    }

    public AnalyticValue getValue()
    {
      return source.getValue();
    }

    public boolean isProxy()
    {
      return true;
    }

    public boolean isTrend()
    {
      return false;
    }

    public boolean next()
    {
      throw new IllegalStateException(Utils.lex("notTrend"));
    }

    public void setTrend(AnalyticTrend trend)
    {
      throw new IllegalStateException(Utils.lex("notTrend"));
    }

    private Source source;
  }

  /**
   * For a trended data source.
   */
  private static class TrendSource implements Source
  {
    TrendSource(AnalyticTrend trend)
    {
      this.trend = trend;
    }

    public AnalyticTrend getTrend()
    {
      return trend;
    }

    public AnalyticValue getValue()
    {
      return value;
    }

    public boolean isProxy()
    {
      return false;
    }

    public boolean isTrend()
    {
      return true;
    }

    public boolean next()
    {
      if (trend.hasNext())
      {
        value = trend.next();
        return true;
      }
      value = null;
      return false;
    }

    public void setTrend(AnalyticTrend trend)
    {
      this.trend = trend;
    }

    private AnalyticTrend trend;
    private AnalyticValue value;
  }

  /**
   * For an untrended data source.
   */
  private static class ValueSource implements Source
  {
    ValueSource(AnalyticValue value)
    {
      this.value = value;
    }

    public AnalyticTrend getTrend()
    {
      throw new IllegalStateException(Utils.lex("notTrend"));
    }

    public AnalyticValue getValue()
    {
      return value;
    }

    public boolean isProxy()
    {
      return false;
    }

    public boolean isTrend()
    {
      return false;
    }

    public boolean next()
    {
      throw new IllegalStateException(Utils.lex("notTrend"));
    }

    public void setTrend(AnalyticTrend trend)
    {
      throw new IllegalStateException(Utils.lex("notTrend"));
    }

    private AnalyticValue value;
  }


  private static final int NULL_TS = -1;


  private long timestamp = NULL_TS;
  private Source[] sources;


}
