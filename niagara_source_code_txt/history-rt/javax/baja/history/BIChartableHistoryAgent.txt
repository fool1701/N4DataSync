/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIChartableHistoryAgent is a marker interface implemented by agents
 * (ie. views) on BIHistory instances that have a record type of
 * BTrendRecord and which also support charting (ie. numeric, boolean,
 * or enumerated trend record types typically support charting).  It is
 * used to help filter and sort the registered agents (views) on a
 * BIHistory instance.  It should be implemented by agents (views) that
 * only support histories that contain chartable trend records.
 *
 * @see javax.baja.history.BITrendHistoryAgent
 * @see javax.baja.history.BTrendRecord
 *
 * @author Scott Hoye
 * @creation  1/27/2014
 * @since Niagara 4.0
 *
 */
@NiagaraType
public interface BIChartableHistoryAgent
  extends BITrendHistoryAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BIChartableHistoryAgent(2979906276)1.0$ @*/
/* Generated Thu Jan 27 19:02:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIChartableHistoryAgent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
