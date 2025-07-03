/*
 *
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 *
 */

package javax.bajax.analytics.algorithm;

import java.util.ArrayList;
import javax.bajax.analytics.AnalyticConstants;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BLink;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Knob;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridiumx.analytics.algorithm.AlgorithmBlock;

import com.tridium.sys.schema.Fw;


/**
 * Base implementation for logic blocks, most blocks
 * should subclass {@link BOutputBlock}.
 *
 * @author Aaron Hansen
 * @see BOutputBlock
 * @since NA 2.0
 */
@NiagaraType
public abstract class BAlgorithmBlock
  extends BComponent
  implements AlgorithmBlock, AnalyticConstants
{



//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.bajax.analytics.algorithm.BAlgorithmBlock(2979906276)1.0$ @*/
/* Generated Sat Nov 13 10:09:19 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlgorithmBlock.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch (x)
    {
      case Fw.ADDED:
      case Fw.AT_STEADY_STATE:
      case Fw.RENAMED:
      case Fw.REORDERED:
      case Fw.REMOVED:
        updateConfig();
        break;
    }

    return super.fw(x, a, b, c, d);
  }


  public BIcon getIcon()
  {
    return icon;
  }

  /**
   * Updates config.
   */
  @Override
  public void knobAdded(Knob k, Context cx)
  {
    updateConfig();
    super.knobAdded(k, cx);
  }

  /**
   * Updates config.
   */
  @Override
  public void knobRemoved(Knob k, Context cx)
  {
    updateConfig();
    super.knobRemoved(k, cx);
  }


  /**
   * Gets the link source for the slot and casts it to BAlgorithmBlock.
   * This is less efficient than the public method which gets the input
   * by it's index.
   *
   * @param prop the property that needs to be retrieved.
   * @return the requested property casted to BAlgorithmBlock
   */
  protected BAlgorithmBlock getInput(Property prop)
  {
    BLink[] links = getLinks(prop);
    if ((links == null) || (links.length == 0))
      return null;
    return (BAlgorithmBlock)links[0].getSourceComponent();
  }

  /**
   * Gets the link source for the named slot and casts it to BAlgorithmBlock.
   * This is less efficient than the public method which gets the input
   * by it's index.
   *
   * @param slotName the name of the slot to be retrieved
   * @return BAlgorithmBlock
   */
  protected BAlgorithmBlock getInput(String slotName)
  {
    return getInput(getProperty(slotName));
  }

  /**
   * Returns an array of inputs, some of which may be null if unlinked.
   *
   * @return An array of input blocks, some slots may be null if unlinked.
   */
  protected AlgorithmBlock[] getInputBlocks()
  {
    if (inputBlocks == null)
    {
      Property[] props = getInputProperties();
      BAlgorithmBlock[] blocks = new BAlgorithmBlock[props.length];
      for (int i = props.length; --i >= 0; )
      {
        blocks[i] = getInput(props[i]);
        if ((blocks[i] == null) && isInputRequired(i))
          getPin(props[i]).configFail();
        else
          getPin(props[i]).configOk();
      }
      inputBlocks = blocks;
    }
    return inputBlocks;
  }

  /**
   * Returns all BBlockPin properties that are not readonly.
   *
   * @return The subset of properties which are of type {@code BBlockPin} and are not read only
   */
  protected Property[] getInputProperties()
  {
    Property p;
    ArrayList<Property> list = new ArrayList<>();
    for (SlotCursor<Property> cur = getProperties(); cur.next(); )
    {
      p = cur.property();
      if (get(p) instanceof BBlockPin)
      {
        if (!Flags.isReadonly(this, p))
          list.add(p);
      }
    }
    return list.toArray(new Property[list.size()]);
  }

  /**
   * A convenience which simply casts the value for the given property.
   *
   * @param p the property
   * @return the property casted to BBlockPin
   */
  protected BBlockPin getPin(Property p)
  {
    return (BBlockPin)get(p);
  }

  /**
   * Overrides must call super.updateConfig()
   */
  protected void updateConfig()
  {
    if (!isRunning()) return;
    inputBlocks = null;
    getInputBlocks();
  }


  private static BIcon icon = BIcon.std("gear.png");
  private AlgorithmBlock[] inputBlocks;

}
