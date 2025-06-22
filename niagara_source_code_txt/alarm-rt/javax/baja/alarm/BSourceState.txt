/** Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import java.io.*;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BSourceState represents the state of an alarm source.
 *
 * <ul>
 *  <li>offnormal: the source is out of the defined normal range</li>
 *  <li>fault: the source is is reporting an invalid value/condition</li>
 *  <li>normal: the source is in its normal range</li>
 *  <li>alert: an alarm that does not have a normal state</li>
 * </ul>
 *
 * @author    Blake M Puhak
 * @creation  14 Sep 04
 * @version   $Revision: 5$ $Date: 3/30/05 11:35:59 AM EST$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("normal"),
    @Range("offnormal"),
    @Range("fault"),
    @Range("alert")
  }
)
public final class BSourceState
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BSourceState(659528854)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for normal. */
  public static final int NORMAL = 0;
  /** Ordinal value for offnormal. */
  public static final int OFFNORMAL = 1;
  /** Ordinal value for fault. */
  public static final int FAULT = 2;
  /** Ordinal value for alert. */
  public static final int ALERT = 3;

  /** BSourceState constant for normal. */
  public static final BSourceState normal = new BSourceState(NORMAL);
  /** BSourceState constant for offnormal. */
  public static final BSourceState offnormal = new BSourceState(OFFNORMAL);
  /** BSourceState constant for fault. */
  public static final BSourceState fault = new BSourceState(FAULT);
  /** BSourceState constant for alert. */
  public static final BSourceState alert = new BSourceState(ALERT);

  /** Factory method with ordinal. */
  public static BSourceState make(int ordinal)
  {
    return (BSourceState)normal.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BSourceState make(String tag)
  {
    return (BSourceState)normal.getRange().get(tag);
  }

  /** Private constructor. */
  private BSourceState(int ordinal)
  {
    super(ordinal);
  }

  public static final BSourceState DEFAULT = normal;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSourceState.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BAlarmTransitionBits getAlarmTransitionBits()
  {
    switch(getOrdinal())
    {
      case NORMAL:    return BAlarmTransitionBits.toNormal;
      case OFFNORMAL: return BAlarmTransitionBits.toOffnormal;
      case FAULT:     return BAlarmTransitionBits.toFault;
      case ALERT:     return BAlarmTransitionBits.toAlert;
    }
    return BAlarmTransitionBits.DEFAULT;
  }

////////////////////////////////////////////////////////////////
// Test
////////////////////////////////////////////////////////////////

//  public static void main(String[] args)
//    throws Exception
//  {
//    BSourceState orig = BSourceState.normal;
//    ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
//    DataOutputStream out = new DataOutputStream(outBytes);
//    orig.encode(out);
//
//    ByteArrayInputStream inBytes = new ByteArrayInputStream(outBytes.toByteArray());
//    DataInputStream in = new DataInputStream(inBytes);
//    BSourceState decoded = (BSourceState)BSourceState.normal.decode(in);
//    System.out.println(decoded);
//  }
}
