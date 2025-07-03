/** Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BAckState represents the states of acknowledgement for an alarm.
 * <br>
 * <ul>
 *  <li>unacked: a 'new' unacknowledged alarm</li>
 *  <li>ackPending: a user has requested that the alrm be acked</li>
 *  <li>acked: the source has recieved the ack request and acknowledged the alarm</li>
 * </ul>
 *
 * @author    Blake M Puhak
 * @creation  14 Sep 04
 * @version   $Revision: 3$ $Date: 3/30/05 11:35:59 AM EST$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("acked"),
    @Range("unacked"),
    @Range("ackPending")
  }
)
public final class BAckState
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BAckState(2981387850)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for acked. */
  public static final int ACKED = 0;
  /** Ordinal value for unacked. */
  public static final int UNACKED = 1;
  /** Ordinal value for ackPending. */
  public static final int ACK_PENDING = 2;

  /** BAckState constant for acked. */
  public static final BAckState acked = new BAckState(ACKED);
  /** BAckState constant for unacked. */
  public static final BAckState unacked = new BAckState(UNACKED);
  /** BAckState constant for ackPending. */
  public static final BAckState ackPending = new BAckState(ACK_PENDING);

  /** Factory method with ordinal. */
  public static BAckState make(int ordinal)
  {
    return (BAckState)acked.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BAckState make(String tag)
  {
    return (BAckState)acked.getRange().get(tag);
  }

  /** Private constructor. */
  private BAckState(int ordinal)
  {
    super(ordinal);
  }

  public static final BAckState DEFAULT = acked;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAckState.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
