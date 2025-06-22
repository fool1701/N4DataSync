/*
 * Copyright 2023 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.util.Set;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * An interface that can be optionally implemented by {@link BComponent} subclasses
 * that wish to declare certain {@link Slot} children that can never be the source
 * and/or target of a link in Niagara.  Enforcement of un-linkable child Slots occurs in
 * {@link BComponent#checkLink(BComponent, Slot, Slot, Context)}
 * and
 * {@link BLink#activate()}.
 *
 * @author   Scott Hoye on 11/17/2023
 * @since    Niagara 4.10u7
 */
@NiagaraType
public interface BIUnlinkableSlotsContainer
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BIUnlinkableSlotsContainer(2979906276)1.0$ @*/
/* Generated Fri Nov 17 12:48:43 EST 2023 by Slot-o-Matic (c) Tridium, Inc. 2012-2023 */

  //region Type

  Type TYPE = Sys.loadType(BIUnlinkableSlotsContainer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Returns a Set of {@link Slot} instances with any direct child Slots of
   * the component implementing this interface that are not allowed to
   * be the source of links. The default implementation of this method
   * returns null, indicating that no child Slots are invalid to be
   * the source of a {@link BLink}. Subclasses overriding this method should
   * always return an unmodifiable Set (see {@link java.util.Collections#unmodifiableSet(Set)}
   * or {@link java.util.Collections#singleton(Object)}) and consider making
   * the method final. The Context parameter may be useful to give some insight
   * to the condition when this method is called. The Context will be null for
   * link activations ({@link BLink#activate()} calling this method, but it may
   * be non-null for link validity checks ({@link LinkCheck}).
   */
  default Set<Slot> getUnlinkableSourceSlots(Context context)
  {
    return null;
  }

  /**
   * Returns a Set of {@link Slot} instances with any direct child Slots of
   * the component implementing this interface that are not allowed to
   * be the target of links. The default implementation of this method
   * returns null, indicating that no child Slots are invalid to be
   * the target of a {@link BLink}. Subclasses overriding this method should
   * always return an unmodifiable Set (see {@link java.util.Collections#unmodifiableSet(Set)}
   * or {@link java.util.Collections#singleton(Object)}) and consider making
   * the method final. The Context parameter may be useful to give some insight
   * to the condition when this method is called. The Context will be null for
   * link activations ({@link BLink#activate()} calling this method, but it may
   * be non-null for link validity checks ({@link LinkCheck}).
   */
  default Set<Slot> getUnlinkableTargetSlots(Context context)
  {
    return null;
  }
}
