/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.history;

import javax.baja.driver.util.BPollScheduler;
import javax.baja.history.BHistoryConfig;
import javax.baja.history.BHistoryId;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BHistoryNetworkExt manages network level functions for the
 * history transfers.  Its primary purpose is to be the container
 * of the rules that specify how the configuration of a history
 * should be changed when the history is pushed into a station.
 * <p>
 * Configuration rules are applied when a history is created.
 * Changing a rule has no effect on existing histories.
 *
 * @author    John Sublett
 * @creation  17 Apr 2003
 * @version   $Revision: 2$ $Date: 5/15/08 4:15:19 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Manages polling for subscribed history descriptors.
 @since Niagara 3.4
 */
@NiagaraProperty(
  name = "onDemandPollScheduler",
  type = "BPollScheduler",
  defaultValue = "new BHistoryPollScheduler()"
)
/*
 The default rule for configuration of histories
 pushed to the local device.  This rule is applied
 when no other config rules are applicable.
 */
@NiagaraProperty(
  name = "defaultRule",
  type = "BConfigRule",
  defaultValue = "BConfigRule.makeDefault()"
)
public class BHistoryNetworkExt
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.history.BHistoryNetworkExt(4004782615)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "onDemandPollScheduler"

  /**
   * Slot for the {@code onDemandPollScheduler} property.
   * Manages polling for subscribed history descriptors.
   * @since Niagara 3.4
   * @see #getOnDemandPollScheduler
   * @see #setOnDemandPollScheduler
   */
  public static final Property onDemandPollScheduler = newProperty(0, new BHistoryPollScheduler(), null);

  /**
   * Get the {@code onDemandPollScheduler} property.
   * Manages polling for subscribed history descriptors.
   * @since Niagara 3.4
   * @see #onDemandPollScheduler
   */
  public BPollScheduler getOnDemandPollScheduler() { return (BPollScheduler)get(onDemandPollScheduler); }

  /**
   * Set the {@code onDemandPollScheduler} property.
   * Manages polling for subscribed history descriptors.
   * @since Niagara 3.4
   * @see #onDemandPollScheduler
   */
  public void setOnDemandPollScheduler(BPollScheduler v) { set(onDemandPollScheduler, v, null); }

  //endregion Property "onDemandPollScheduler"

  //region Property "defaultRule"

  /**
   * Slot for the {@code defaultRule} property.
   * The default rule for configuration of histories
   * pushed to the local device.  This rule is applied
   * when no other config rules are applicable.
   * @see #getDefaultRule
   * @see #setDefaultRule
   */
  public static final Property defaultRule = newProperty(0, BConfigRule.makeDefault(), null);

  /**
   * Get the {@code defaultRule} property.
   * The default rule for configuration of histories
   * pushed to the local device.  This rule is applied
   * when no other config rules are applicable.
   * @see #defaultRule
   */
  public BConfigRule getDefaultRule() { return (BConfigRule)get(defaultRule); }

  /**
   * Set the {@code defaultRule} property.
   * The default rule for configuration of histories
   * pushed to the local device.  This rule is applied
   * when no other config rules are applicable.
   * @see #defaultRule
   */
  public void setDefaultRule(BConfigRule v) { set(defaultRule, v, null); }

  //endregion Property "defaultRule"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryNetworkExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Create a local configuration based on the specified
   * history config instance.
   */
  public BHistoryConfig makeConfig(BHistoryConfig original)
  {
    BHistoryId id = original.getId();
    BConfigRule rule = null;
    BConfigRule defaultRule = getDefaultRule();
    SlotCursor<Property> rules = loadSlots().getProperties();
    while (rules.next(BConfigRule.class))
    {
      BConfigRule test = (BConfigRule)rules.get();
      if (test == defaultRule) continue;
      if (test.isMatch(id))
        return test.makeConfig(original);
    }
    
    return defaultRule.makeConfig(original);
  }
}
