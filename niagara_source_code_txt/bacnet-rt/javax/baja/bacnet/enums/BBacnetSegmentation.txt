/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BBacnetSegmentation represents the Bacnet Segmentation
 * enumeration.
 *
 * @author Craig Gemmill
 * @version $Revision: 8$ $Date: 12/19/01 4:36:01 PM$
 * @creation 10 Aug 00
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("segmentedBoth"),
    @Range("segmentedTransmit"),
    @Range("segmentedReceive"),
    @Range("noSegmentation")
  },
  defaultValue = "noSegmentation"
)
public final class BBacnetSegmentation
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetSegmentation(766942515)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for segmentedBoth. */
  public static final int SEGMENTED_BOTH = 0;
  /** Ordinal value for segmentedTransmit. */
  public static final int SEGMENTED_TRANSMIT = 1;
  /** Ordinal value for segmentedReceive. */
  public static final int SEGMENTED_RECEIVE = 2;
  /** Ordinal value for noSegmentation. */
  public static final int NO_SEGMENTATION = 3;

  /** BBacnetSegmentation constant for segmentedBoth. */
  public static final BBacnetSegmentation segmentedBoth = new BBacnetSegmentation(SEGMENTED_BOTH);
  /** BBacnetSegmentation constant for segmentedTransmit. */
  public static final BBacnetSegmentation segmentedTransmit = new BBacnetSegmentation(SEGMENTED_TRANSMIT);
  /** BBacnetSegmentation constant for segmentedReceive. */
  public static final BBacnetSegmentation segmentedReceive = new BBacnetSegmentation(SEGMENTED_RECEIVE);
  /** BBacnetSegmentation constant for noSegmentation. */
  public static final BBacnetSegmentation noSegmentation = new BBacnetSegmentation(NO_SEGMENTATION);

  /** Factory method with ordinal. */
  public static BBacnetSegmentation make(int ordinal)
  {
    return (BBacnetSegmentation)segmentedBoth.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetSegmentation make(String tag)
  {
    return (BBacnetSegmentation)segmentedBoth.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetSegmentation(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetSegmentation DEFAULT = noSegmentation;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetSegmentation.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////

  /**
   * Is segmented transmit supported?
   *
   * @return true for segmentedBoth and segmentedTransmit.
   */
  public boolean isSegmentedTransmit()
  {
    return getOrdinal() < SEGMENTED_RECEIVE;
  }

  /**
   * Is segmented receive supported?
   *
   * @return true for segmentedBoth and segmentedReceive.
   */
  public boolean isSegmentedReceive()
  {
    return (getOrdinal() & SEGMENTED_TRANSMIT) == 0;
  }


////////////////////////////////////////////////////////////////
// Static methods
////////////////////////////////////////////////////////////////

  /**
   * Create a string tag for the given ordinal.
   *
   * @return the tag for the ordinal, if it is known,
   * or construct one using standard prefixes.
   */
  public static String tag(int id)
  {
    return DEFAULT.getRange().getTag(id);
  }
}
