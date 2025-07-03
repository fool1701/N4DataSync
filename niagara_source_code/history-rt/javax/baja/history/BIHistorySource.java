/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIHistorySource is an interface implemented by objects that 
 * create a history.  It is used to receive notification
 * that a BHistoryConfig property has changed.  When
 * a BHistoryConfig property changes, the config instance
 * checks to see if its parent is a BIHistorySource.  If so,
 * it notifies the parent that the config has changed.
 *
 * @author    John Sublett
 * @creation  17 Apr 2003
 * @version   $Revision: 1$ $Date: 4/17/03 11:50:53 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIHistorySource
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BIHistorySource(2979906276)1.0$ @*/
/* Generated Thu Jan 27 19:02:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIHistorySource.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Receive notification that a history configuration has
   * changed.
   */
  public void historyConfigChanged(BHistoryConfig config, Property p);
}
