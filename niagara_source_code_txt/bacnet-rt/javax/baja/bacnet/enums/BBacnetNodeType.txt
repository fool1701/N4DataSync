/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * BBacnetNodeType represents the BACnetNodeType enumeration.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 20 Jan 2009
 * @since Niagara 3.5
 */

@NiagaraType
@NiagaraEnum(
  range = {
    @Range("unknown"),
    @Range("system"),
    @Range("network"),
    @Range("device"),
    @Range("organizational"),
    @Range("area"),
    @Range("equipment"),
    @Range("point"),
    @Range("collection"),
    @Range("property"),
    @Range("functional"),
    @Range("other")
  }
)
public final class BBacnetNodeType
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetNodeType(128625200)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for unknown. */
  public static final int UNKNOWN = 0;
  /** Ordinal value for system. */
  public static final int SYSTEM = 1;
  /** Ordinal value for network. */
  public static final int NETWORK = 2;
  /** Ordinal value for device. */
  public static final int DEVICE = 3;
  /** Ordinal value for organizational. */
  public static final int ORGANIZATIONAL = 4;
  /** Ordinal value for area. */
  public static final int AREA = 5;
  /** Ordinal value for equipment. */
  public static final int EQUIPMENT = 6;
  /** Ordinal value for point. */
  public static final int POINT = 7;
  /** Ordinal value for collection. */
  public static final int COLLECTION = 8;
  /** Ordinal value for property. */
  public static final int PROPERTY = 9;
  /** Ordinal value for functional. */
  public static final int FUNCTIONAL = 10;
  /** Ordinal value for other. */
  public static final int OTHER = 11;

  /** BBacnetNodeType constant for unknown. */
  public static final BBacnetNodeType unknown = new BBacnetNodeType(UNKNOWN);
  /** BBacnetNodeType constant for system. */
  public static final BBacnetNodeType system = new BBacnetNodeType(SYSTEM);
  /** BBacnetNodeType constant for network. */
  public static final BBacnetNodeType network = new BBacnetNodeType(NETWORK);
  /** BBacnetNodeType constant for device. */
  public static final BBacnetNodeType device = new BBacnetNodeType(DEVICE);
  /** BBacnetNodeType constant for organizational. */
  public static final BBacnetNodeType organizational = new BBacnetNodeType(ORGANIZATIONAL);
  /** BBacnetNodeType constant for area. */
  public static final BBacnetNodeType area = new BBacnetNodeType(AREA);
  /** BBacnetNodeType constant for equipment. */
  public static final BBacnetNodeType equipment = new BBacnetNodeType(EQUIPMENT);
  /** BBacnetNodeType constant for point. */
  public static final BBacnetNodeType point = new BBacnetNodeType(POINT);
  /** BBacnetNodeType constant for collection. */
  public static final BBacnetNodeType collection = new BBacnetNodeType(COLLECTION);
  /** BBacnetNodeType constant for property. */
  public static final BBacnetNodeType property = new BBacnetNodeType(PROPERTY);
  /** BBacnetNodeType constant for functional. */
  public static final BBacnetNodeType functional = new BBacnetNodeType(FUNCTIONAL);
  /** BBacnetNodeType constant for other. */
  public static final BBacnetNodeType other = new BBacnetNodeType(OTHER);

  /** Factory method with ordinal. */
  public static BBacnetNodeType make(int ordinal)
  {
    return (BBacnetNodeType)unknown.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetNodeType make(String tag)
  {
    return (BBacnetNodeType)unknown.getRange().get(tag);
  }

  /** Private constructor. */
  private BBacnetNodeType(int ordinal)
  {
    super(ordinal);
  }

  public static final BBacnetNodeType DEFAULT = unknown;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetNodeType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
