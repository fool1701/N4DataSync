/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.util;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIPollable is the interface implemented by components
 * used with the BPollManager to build a poll scheme.
 *
 * @author    Brian Frank
 * @creation  21 Jan 02
 * @version   $Revision: 2$ $Date: 12/20/02 2:32:33 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIPollable
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.util.BIPollable(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIPollable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the component's configured poll frequency.
   */
  BPollFrequency getPollFrequency();
}
