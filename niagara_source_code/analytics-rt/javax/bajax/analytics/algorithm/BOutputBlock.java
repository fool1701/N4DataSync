/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.algorithm;

import javax.bajax.analytics.AnalyticContext;
import javax.bajax.analytics.data.AnalyticTrend;
import javax.bajax.analytics.data.AnalyticValue;

import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridiumx.analytics.algorithm.AlgorithmBlock;
import com.tridiumx.analytics.util.Utils;

/**
 * An AlgorithmBlock with an output.  Most blocks should subclass this.
 * <p>
 * <p>
 * Base implementation for all logic blocks.
 * Sub classes can add input slots of type {@link BBlockPin}.
 * All blocks to return value {@link AnalyticValue} or trend {@link AnalyticTrend} as requested.
 * Subclass {@link BlockTrend} to create your own AnalyticTrend implementation.
 * </p>
 * <p>
 * <pre>
 *   <code>
 * // This block evaluates a two operand addition operation and outputs
 * // the result. Both trend and value requests are supported, only one input
 * // has to have a trend for this block to make a trend. The status of the
 * // output will be the combination of the two operands. If the operation
 * // results in NaN or Infinity, the null status flag will be set.
 *
 *
 * {@literal @}NiagaraType
 * {@literal @}NiagaraProperty(name = "in1", type = "BBlockPin", defaultValue = "new BBlockPin()", flags = Flags.SUMMARY)
 * {@literal @}NiagaraProperty(name = "in2", type = "BBlockPin", defaultValue = "new BBlockPin()", flags = Flags.SUMMARY)
 * public class BBiMathBlock
 *   extends BOutputBlock
 * {
 *
 *
 *   //
 *   // Returns a trend of with the function applied to each row.
 *   //
 *   {@literal @}Override
 *   public AnalyticTrend getTrend(AnalyticContext cx)
 *   {
 *     return new MyTrend(this,cx);
 *   }
 *
 *   //
 *   // Performs the function directly on the inputs.
 *   //
 *   {@literal @}Override
 *   public AnalyticValue getValue(AnalyticContext cx)
 *   {
 *     return eval(getInputValue(0,cx),getInputValue(1,cx));
 *   }
 *
 *
 *   //
 *   // Performs the function for both getValue and getTrend requests.
 *   //
 *   private AnalyticValue eval(AnalyticValue in1, AnalyticValue in2)
 *   {
 *     AnalyticValue ret = in1;
 *     double result = in1.toNumeric() + in2.toNumeric();
 *     int sts = in1.getStatus() | in2.getStatus();
 *     if (Double.isNaN(result) || Double.isInfinite(result))
 *       sts = sts | STATUS_NULL;
 *     if (ret instanceof AnalyticNumeric)
 *       ((AnalyticNumeric)ret).setValue(result);
 *     else
 *       ret.setValue(BDouble.make(result));
 *     ret.setStatus(sts);
 *     return ret;
 *   }
 *
 *   //
 *   // Calls eval for each interval.
 *   //
 *   private class MyTrend extends BlockTrend
 *   {
 *     public MyTrend(AlgorithmBlock block, AnalyticContext cx)
 *     {
 *       super(block,cx);
 *     }
 *     protected AnalyticValue getNext()
 *     {
 *       if (!advance()) return null;
 *       return eval(getValue(0),getValue(1));
 *     }
 *   }
 *
 * }
 *   </code>
 * </pre>
 *
 * @author Aaron Hansen
 * @see BlockTrend
 * @since NA 2.0
 */

@NiagaraType
@NiagaraProperty(
  name = "out",
  type = "BBlockPin",
  defaultValue = "new BBlockPin()",
  flags = Flags.SUMMARY | Flags.READONLY
)
public abstract class BOutputBlock
  extends BAlgorithmBlock
{


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.bajax.analytics.algorithm.BOutputBlock(3405960784)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.SUMMARY | Flags.READONLY, new BBlockPin(), null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public BBlockPin getOut() { return (BBlockPin)get(out); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(BBlockPin v) { set(out, v, null); }

  //endregion Property "out"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOutputBlock.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  /**
   * Returns the input for the given 0-based index.
   *
   * @return Possibly null if unlinked.
   */
  @Override
  public AlgorithmBlock getInput(int idx)
  {
    return getInputBlocks()[idx];
  }

  /**
   * The number of inputs (linked and unlinked) on the block.
   */
  @Override
  public int getInputCount()
  {
    return getInputBlocks().length;
  }

  /**
   * Subclasses must return a default value for any optional inputs (inputs
   * that do not need to be linked).  This won't ) be called for
   * required inputs (isInputRequired(idx) == true).
   *
   * @throws IllegalArgumentException if the input is required.
   */
  @Override
  public AnalyticValue getInputDefault(int idx, AnalyticContext cx)
  {
    throw new IllegalArgumentException(
      Utils.lex("inputRequired") + ": " + idx);
  }

  /**
   * Calls get value on the desired input, or if not linked, returns
   * the default value.
   */
  public AnalyticValue getInputValue(int idx, AnalyticContext cx)
  {
    AlgorithmBlock block = getInput(idx);
    if (block != null)
      return block.getValue(cx);
    return getInputDefault(idx, cx);
  }

  /**
   * Returns a non empty trend if the block supports trend output.
   * Most blocks can subclass  {@link BlockTrend}
   *
   * @see BlockTrend
   */
  public abstract AnalyticTrend getTrend(AnalyticContext cx);

  /**
   * Returns a non null value if the block supports value output.
   */
  public abstract AnalyticValue getValue(AnalyticContext cx);

  /**
   * Returns true if any of the inputs are trended.  Should be overridden
   * if any inputs should be excluded from this determination (such inputs
   * for results of a conditional block).
   */
  @Override
  public boolean hasTrend(AnalyticContext cx)
  {
    AlgorithmBlock input;
    for (int i = 0, len = getInputCount(); i < len; i++)
    {
      input = getInput(i);
      if ((input != null) && input.hasTrend(cx))
        return true;

    }
    return false;
  }

  /**
   * Returns true if all required inputs are linked and they return
   * true for this same call.
   */
  @Override
  public boolean isAvailable(BINavNode node)
  {
    AlgorithmBlock input;
    for (int i = 0, len = getInputCount(); i < len; i++)
    {
      input = getInput(i);
      if (input == null)
      {
        if (isInputRequired(i))
          return false;
      }
      else
      {
        if (!input.isAvailable(node))
          return false;
      }

    }
    return true;
  }

  /**
   * Whether or not the input must be linked, the default implementation
   * simply returns true.
   */
  @Override
  public boolean isInputRequired(int idx)
  {
    return true;
  }


}
