/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BIProtected;
import javax.baja.space.BISpaceNode;
import javax.baja.sys.BIPropertyContainer;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BTypeSpec;

/**
 * BIHistory is the interface for accessing the historical data of a
 * Baja history.
 *
 * @author John Sublett
 */
@NiagaraType
public interface BIHistory
  extends BISpaceNode, BIProtected, BIPropertyContainer
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BIHistory(2979906276)1.0$ @*/
/* Generated Thu Jan 27 19:02:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIHistory.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the unique identifier for this history.
   */
  BHistoryId getId();

  /**
   * Get the record type for this history.  The record type
   * is also available as part of the history config, but if
   * the record type is all you need, it is often more efficient
   * to get it with this method than through the config.
   */
  BTypeSpec getRecordType();

  /**
   * Get the configuration for this history.
   */
  BHistoryConfig getConfig();

  /**
   * Close this history and release any associated resources.
   */
  void close();
  
}
