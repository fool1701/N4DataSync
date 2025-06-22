/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import javax.baja.lonworks.enums.BLonCalendarTypeEnum;
import javax.baja.lonworks.enums.BLonDaysOfWeekEnum;
import javax.baja.lonworks.enums.BLonElementType;
import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;


/**
 *   This class file represents SNVT_time_zone.
 * <p>
 *
 * @author    Robert Adams
 * @creation  8 Aug 01
 * @version   $Revision: 3$ $Date: 9/18/01 9:50:10 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "secondTimeOffset",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.s32, -86400F, 86400F, 1, null)")
)
@NiagaraProperty(
  name = "typeOfDescription",
  type = "BLonEnum",
  defaultValue = "BLonEnum.make(BLonCalendarTypeEnum.calNul)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.e8, null)")
)
@NiagaraProperty(
  name = "hourOfStartDST",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 23, 1, 5, null)")
)
@NiagaraProperty(
  name = "minuteOfStartDST",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 59, 1, 6, null)")
)
@NiagaraProperty(
  name = "secondOfStartDST",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 59, 1, 7, null)")
)
@NiagaraProperty(
  name = "GdayOfStartDST",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u16,true,0F, true,365F, 1F,0F, true,8,0, false,0F, 1, null)")
)
@NiagaraProperty(
  name = "JdayOfStartDST",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u16,true,1F, true,365F, 1F,0F, true,8,0, false,0F, 1, null)")
)
@NiagaraProperty(
  name = "MmonthOfStartDST",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub,true,1F, true,12F, 1F,0F, true,8,4, false,0F, 4, null)")
)
@NiagaraProperty(
  name = "MweekOfStartDST",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub,true,1F, true,5F, 1F,0F, true,8,1, false,0F, 3, null)")
)
@NiagaraProperty(
  name = "MdatedayOfStartDST",
  type = "BLonEnum",
  defaultValue = "BLonEnum.make(BLonDaysOfWeekEnum.daySun)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.e8, 9)")
)
@NiagaraProperty(
  name = "hourOfEndDST",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 23, 1, 10, null)")
)
@NiagaraProperty(
  name = "minuteOfEndDST",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 59, 1, 11, null)")
)
@NiagaraProperty(
  name = "secondOfEndDST",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 59, 1, 12, null)")
)
@NiagaraProperty(
  name = "GdayOfEndDST",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u16,true,0F, true,365F, 1F,0F, true,13,0, false,0F, 1, null)")
)
@NiagaraProperty(
  name = "JdayOfEndDST",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u16,true,1F, true,365F, 1F,0F, true,13,0, false,0F, 1, null)")
)
@NiagaraProperty(
  name = "MmonthOfEndDST",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub,true,1F, true,12F, 1F,0F, true,13,4, false,0F, 4, null)")
)
@NiagaraProperty(
  name = "MweekOfEndDST",
  type = "BLonFloat",
  defaultValue = "BLonFloat.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.ub,true,1F, true,5F, 1F,0F, true,13,1, false,0F, 3, null)")
)
@NiagaraProperty(
  name = "MgatedayOfEndDST",
  type = "BLonEnum",
  defaultValue = "BLonEnum.make(BLonDaysOfWeekEnum.daySun)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.e8, 14)")
)
public class BLonTimeZone
  extends BLonData
{
/*
byte offsets in SNVT_time_zone
typedef struct {

0  s32_type    second_time_offset;
4  calendar_type_t type_of_description;
5  unsigned short  hour_of_start_DST;
6  unsigned short  minute_of_start_DST;
7  unsigned short  second_of_start_DST;
  union {
8    unsigned long G_day_of_start_DST;
8    unsigned long J_day_of_start_DST;
    struct {
8      unsigned short  month_of_start_DST  :4;
8      unsigned short  week_of_start_DST   :3;
9      SNVT_date_day dateday_of_start_DST;
    } M_start_DST;
  } start_DST;

10  unsigned short  hour_of_end_DST;
11  unsigned short  minute_of_end_DST;
12  unsigned short  second_of_end_DST;
  union {
13    unsigned long G_day_of_end_DST;
13    unsigned long J_day_of_end_DST;
    struct {
13      unsigned short  month_of_end_DST    :4;
13      unsigned short  week_of_end_DST     :3;
14      SNVT_date_day   dateday_of_end_DST;
    } M_end_DST;
  } end_DST;
} SNVT_time_zone;
*/

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonTimeZone(697516219)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "secondTimeOffset"

  /**
   * Slot for the {@code secondTimeOffset} property.
   * @see #getSecondTimeOffset
   * @see #setSecondTimeOffset
   */
  public static final Property secondTimeOffset = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.s32, -86400F, 86400F, 1, null));

  /**
   * Get the {@code secondTimeOffset} property.
   * @see #secondTimeOffset
   */
  public BLonFloat getSecondTimeOffset() { return (BLonFloat)get(secondTimeOffset); }

  /**
   * Set the {@code secondTimeOffset} property.
   * @see #secondTimeOffset
   */
  public void setSecondTimeOffset(BLonFloat v) { set(secondTimeOffset, v, null); }

  //endregion Property "secondTimeOffset"

  //region Property "typeOfDescription"

  /**
   * Slot for the {@code typeOfDescription} property.
   * @see #getTypeOfDescription
   * @see #setTypeOfDescription
   */
  public static final Property typeOfDescription = newProperty(0, BLonEnum.make(BLonCalendarTypeEnum.calNul), LonFacetsUtil.makeFacets(BLonElementType.e8, null));

  /**
   * Get the {@code typeOfDescription} property.
   * @see #typeOfDescription
   */
  public BLonEnum getTypeOfDescription() { return (BLonEnum)get(typeOfDescription); }

  /**
   * Set the {@code typeOfDescription} property.
   * @see #typeOfDescription
   */
  public void setTypeOfDescription(BLonEnum v) { set(typeOfDescription, v, null); }

  //endregion Property "typeOfDescription"

  //region Property "hourOfStartDST"

  /**
   * Slot for the {@code hourOfStartDST} property.
   * @see #getHourOfStartDST
   * @see #setHourOfStartDST
   */
  public static final Property hourOfStartDST = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 23, 1, 5, null));

  /**
   * Get the {@code hourOfStartDST} property.
   * @see #hourOfStartDST
   */
  public BLonFloat getHourOfStartDST() { return (BLonFloat)get(hourOfStartDST); }

  /**
   * Set the {@code hourOfStartDST} property.
   * @see #hourOfStartDST
   */
  public void setHourOfStartDST(BLonFloat v) { set(hourOfStartDST, v, null); }

  //endregion Property "hourOfStartDST"

  //region Property "minuteOfStartDST"

  /**
   * Slot for the {@code minuteOfStartDST} property.
   * @see #getMinuteOfStartDST
   * @see #setMinuteOfStartDST
   */
  public static final Property minuteOfStartDST = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 59, 1, 6, null));

  /**
   * Get the {@code minuteOfStartDST} property.
   * @see #minuteOfStartDST
   */
  public BLonFloat getMinuteOfStartDST() { return (BLonFloat)get(minuteOfStartDST); }

  /**
   * Set the {@code minuteOfStartDST} property.
   * @see #minuteOfStartDST
   */
  public void setMinuteOfStartDST(BLonFloat v) { set(minuteOfStartDST, v, null); }

  //endregion Property "minuteOfStartDST"

  //region Property "secondOfStartDST"

  /**
   * Slot for the {@code secondOfStartDST} property.
   * @see #getSecondOfStartDST
   * @see #setSecondOfStartDST
   */
  public static final Property secondOfStartDST = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 59, 1, 7, null));

  /**
   * Get the {@code secondOfStartDST} property.
   * @see #secondOfStartDST
   */
  public BLonFloat getSecondOfStartDST() { return (BLonFloat)get(secondOfStartDST); }

  /**
   * Set the {@code secondOfStartDST} property.
   * @see #secondOfStartDST
   */
  public void setSecondOfStartDST(BLonFloat v) { set(secondOfStartDST, v, null); }

  //endregion Property "secondOfStartDST"

  //region Property "GdayOfStartDST"

  /**
   * Slot for the {@code GdayOfStartDST} property.
   * @see #getGdayOfStartDST
   * @see #setGdayOfStartDST
   */
  public static final Property GdayOfStartDST = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u16,true,0F, true,365F, 1F,0F, true,8,0, false,0F, 1, null));

  /**
   * Get the {@code GdayOfStartDST} property.
   * @see #GdayOfStartDST
   */
  public BLonFloat getGdayOfStartDST() { return (BLonFloat)get(GdayOfStartDST); }

  /**
   * Set the {@code GdayOfStartDST} property.
   * @see #GdayOfStartDST
   */
  public void setGdayOfStartDST(BLonFloat v) { set(GdayOfStartDST, v, null); }

  //endregion Property "GdayOfStartDST"

  //region Property "JdayOfStartDST"

  /**
   * Slot for the {@code JdayOfStartDST} property.
   * @see #getJdayOfStartDST
   * @see #setJdayOfStartDST
   */
  public static final Property JdayOfStartDST = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u16,true,1F, true,365F, 1F,0F, true,8,0, false,0F, 1, null));

  /**
   * Get the {@code JdayOfStartDST} property.
   * @see #JdayOfStartDST
   */
  public BLonFloat getJdayOfStartDST() { return (BLonFloat)get(JdayOfStartDST); }

  /**
   * Set the {@code JdayOfStartDST} property.
   * @see #JdayOfStartDST
   */
  public void setJdayOfStartDST(BLonFloat v) { set(JdayOfStartDST, v, null); }

  //endregion Property "JdayOfStartDST"

  //region Property "MmonthOfStartDST"

  /**
   * Slot for the {@code MmonthOfStartDST} property.
   * @see #getMmonthOfStartDST
   * @see #setMmonthOfStartDST
   */
  public static final Property MmonthOfStartDST = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub,true,1F, true,12F, 1F,0F, true,8,4, false,0F, 4, null));

  /**
   * Get the {@code MmonthOfStartDST} property.
   * @see #MmonthOfStartDST
   */
  public BLonFloat getMmonthOfStartDST() { return (BLonFloat)get(MmonthOfStartDST); }

  /**
   * Set the {@code MmonthOfStartDST} property.
   * @see #MmonthOfStartDST
   */
  public void setMmonthOfStartDST(BLonFloat v) { set(MmonthOfStartDST, v, null); }

  //endregion Property "MmonthOfStartDST"

  //region Property "MweekOfStartDST"

  /**
   * Slot for the {@code MweekOfStartDST} property.
   * @see #getMweekOfStartDST
   * @see #setMweekOfStartDST
   */
  public static final Property MweekOfStartDST = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub,true,1F, true,5F, 1F,0F, true,8,1, false,0F, 3, null));

  /**
   * Get the {@code MweekOfStartDST} property.
   * @see #MweekOfStartDST
   */
  public BLonFloat getMweekOfStartDST() { return (BLonFloat)get(MweekOfStartDST); }

  /**
   * Set the {@code MweekOfStartDST} property.
   * @see #MweekOfStartDST
   */
  public void setMweekOfStartDST(BLonFloat v) { set(MweekOfStartDST, v, null); }

  //endregion Property "MweekOfStartDST"

  //region Property "MdatedayOfStartDST"

  /**
   * Slot for the {@code MdatedayOfStartDST} property.
   * @see #getMdatedayOfStartDST
   * @see #setMdatedayOfStartDST
   */
  public static final Property MdatedayOfStartDST = newProperty(0, BLonEnum.make(BLonDaysOfWeekEnum.daySun), LonFacetsUtil.makeFacets(BLonElementType.e8, 9));

  /**
   * Get the {@code MdatedayOfStartDST} property.
   * @see #MdatedayOfStartDST
   */
  public BLonEnum getMdatedayOfStartDST() { return (BLonEnum)get(MdatedayOfStartDST); }

  /**
   * Set the {@code MdatedayOfStartDST} property.
   * @see #MdatedayOfStartDST
   */
  public void setMdatedayOfStartDST(BLonEnum v) { set(MdatedayOfStartDST, v, null); }

  //endregion Property "MdatedayOfStartDST"

  //region Property "hourOfEndDST"

  /**
   * Slot for the {@code hourOfEndDST} property.
   * @see #getHourOfEndDST
   * @see #setHourOfEndDST
   */
  public static final Property hourOfEndDST = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 23, 1, 10, null));

  /**
   * Get the {@code hourOfEndDST} property.
   * @see #hourOfEndDST
   */
  public BLonFloat getHourOfEndDST() { return (BLonFloat)get(hourOfEndDST); }

  /**
   * Set the {@code hourOfEndDST} property.
   * @see #hourOfEndDST
   */
  public void setHourOfEndDST(BLonFloat v) { set(hourOfEndDST, v, null); }

  //endregion Property "hourOfEndDST"

  //region Property "minuteOfEndDST"

  /**
   * Slot for the {@code minuteOfEndDST} property.
   * @see #getMinuteOfEndDST
   * @see #setMinuteOfEndDST
   */
  public static final Property minuteOfEndDST = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 59, 1, 11, null));

  /**
   * Get the {@code minuteOfEndDST} property.
   * @see #minuteOfEndDST
   */
  public BLonFloat getMinuteOfEndDST() { return (BLonFloat)get(minuteOfEndDST); }

  /**
   * Set the {@code minuteOfEndDST} property.
   * @see #minuteOfEndDST
   */
  public void setMinuteOfEndDST(BLonFloat v) { set(minuteOfEndDST, v, null); }

  //endregion Property "minuteOfEndDST"

  //region Property "secondOfEndDST"

  /**
   * Slot for the {@code secondOfEndDST} property.
   * @see #getSecondOfEndDST
   * @see #setSecondOfEndDST
   */
  public static final Property secondOfEndDST = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 59, 1, 12, null));

  /**
   * Get the {@code secondOfEndDST} property.
   * @see #secondOfEndDST
   */
  public BLonFloat getSecondOfEndDST() { return (BLonFloat)get(secondOfEndDST); }

  /**
   * Set the {@code secondOfEndDST} property.
   * @see #secondOfEndDST
   */
  public void setSecondOfEndDST(BLonFloat v) { set(secondOfEndDST, v, null); }

  //endregion Property "secondOfEndDST"

  //region Property "GdayOfEndDST"

  /**
   * Slot for the {@code GdayOfEndDST} property.
   * @see #getGdayOfEndDST
   * @see #setGdayOfEndDST
   */
  public static final Property GdayOfEndDST = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u16,true,0F, true,365F, 1F,0F, true,13,0, false,0F, 1, null));

  /**
   * Get the {@code GdayOfEndDST} property.
   * @see #GdayOfEndDST
   */
  public BLonFloat getGdayOfEndDST() { return (BLonFloat)get(GdayOfEndDST); }

  /**
   * Set the {@code GdayOfEndDST} property.
   * @see #GdayOfEndDST
   */
  public void setGdayOfEndDST(BLonFloat v) { set(GdayOfEndDST, v, null); }

  //endregion Property "GdayOfEndDST"

  //region Property "JdayOfEndDST"

  /**
   * Slot for the {@code JdayOfEndDST} property.
   * @see #getJdayOfEndDST
   * @see #setJdayOfEndDST
   */
  public static final Property JdayOfEndDST = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u16,true,1F, true,365F, 1F,0F, true,13,0, false,0F, 1, null));

  /**
   * Get the {@code JdayOfEndDST} property.
   * @see #JdayOfEndDST
   */
  public BLonFloat getJdayOfEndDST() { return (BLonFloat)get(JdayOfEndDST); }

  /**
   * Set the {@code JdayOfEndDST} property.
   * @see #JdayOfEndDST
   */
  public void setJdayOfEndDST(BLonFloat v) { set(JdayOfEndDST, v, null); }

  //endregion Property "JdayOfEndDST"

  //region Property "MmonthOfEndDST"

  /**
   * Slot for the {@code MmonthOfEndDST} property.
   * @see #getMmonthOfEndDST
   * @see #setMmonthOfEndDST
   */
  public static final Property MmonthOfEndDST = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub,true,1F, true,12F, 1F,0F, true,13,4, false,0F, 4, null));

  /**
   * Get the {@code MmonthOfEndDST} property.
   * @see #MmonthOfEndDST
   */
  public BLonFloat getMmonthOfEndDST() { return (BLonFloat)get(MmonthOfEndDST); }

  /**
   * Set the {@code MmonthOfEndDST} property.
   * @see #MmonthOfEndDST
   */
  public void setMmonthOfEndDST(BLonFloat v) { set(MmonthOfEndDST, v, null); }

  //endregion Property "MmonthOfEndDST"

  //region Property "MweekOfEndDST"

  /**
   * Slot for the {@code MweekOfEndDST} property.
   * @see #getMweekOfEndDST
   * @see #setMweekOfEndDST
   */
  public static final Property MweekOfEndDST = newProperty(0, BLonFloat.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.ub,true,1F, true,5F, 1F,0F, true,13,1, false,0F, 3, null));

  /**
   * Get the {@code MweekOfEndDST} property.
   * @see #MweekOfEndDST
   */
  public BLonFloat getMweekOfEndDST() { return (BLonFloat)get(MweekOfEndDST); }

  /**
   * Set the {@code MweekOfEndDST} property.
   * @see #MweekOfEndDST
   */
  public void setMweekOfEndDST(BLonFloat v) { set(MweekOfEndDST, v, null); }

  //endregion Property "MweekOfEndDST"

  //region Property "MgatedayOfEndDST"

  /**
   * Slot for the {@code MgatedayOfEndDST} property.
   * @see #getMgatedayOfEndDST
   * @see #setMgatedayOfEndDST
   */
  public static final Property MgatedayOfEndDST = newProperty(0, BLonEnum.make(BLonDaysOfWeekEnum.daySun), LonFacetsUtil.makeFacets(BLonElementType.e8, 14));

  /**
   * Get the {@code MgatedayOfEndDST} property.
   * @see #MgatedayOfEndDST
   */
  public BLonEnum getMgatedayOfEndDST() { return (BLonEnum)get(MgatedayOfEndDST); }

  /**
   * Set the {@code MgatedayOfEndDST} property.
   * @see #MgatedayOfEndDST
   */
  public void setMgatedayOfEndDST(BLonEnum v) { set(MgatedayOfEndDST, v, null); }

  //endregion Property "MgatedayOfEndDST"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonTimeZone.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   *  Converts data to network byte format
   **/
  public void toOutputStream(LonOutputStream out)
  {
    primitiveToOutputStream(secondTimeOffset, out);
    primitiveToOutputStream(typeOfDescription, out );
    primitiveToOutputStream(hourOfStartDST, out );
    primitiveToOutputStream(minuteOfStartDST, out );
    primitiveToOutputStream(secondOfStartDST, out );
    
    int type = ((BLonCalendarTypeEnum)getTypeOfDescription().getEnum()).getOrdinal();
    if(type == BLonCalendarTypeEnum.CAL_GREG)
    {
      primitiveToOutputStream(GdayOfStartDST, out );
    }
    else if(type == BLonCalendarTypeEnum.CAL_JUL)
    {
      primitiveToOutputStream(JdayOfStartDST, out );
    }
    else
    {
      primitiveToOutputStream(MmonthOfStartDST, out );
      primitiveToOutputStream(MweekOfStartDST, out );
      primitiveToOutputStream(MdatedayOfStartDST, out );
    }
    primitiveToOutputStream(hourOfEndDST, out );
    primitiveToOutputStream(minuteOfEndDST, out );
    primitiveToOutputStream(secondOfEndDST, out );
    
    if(type == BLonCalendarTypeEnum.CAL_GREG)
    {
      primitiveToOutputStream(GdayOfEndDST, out );
    }
    else if(type == BLonCalendarTypeEnum.CAL_JUL)
    {
      primitiveToOutputStream(JdayOfEndDST, out );
    }
    else
    {
      primitiveToOutputStream(MmonthOfEndDST, out );
      primitiveToOutputStream(MweekOfEndDST, out );
      primitiveToOutputStream(MgatedayOfEndDST, out );
    }
  }
  
  /**
   *  Translates from network bytes. Sets the 
   *  value of the object to the state represented 
   *  by the given bytes.
   **/
  public void fromInputStream(LonInputStream in)
  {
    primitiveFromInputStream(secondTimeOffset , in );   
    primitiveFromInputStream(typeOfDescription, in);  
    primitiveFromInputStream(hourOfStartDST, in);     
    primitiveFromInputStream(minuteOfStartDST, in);  
    primitiveFromInputStream(secondOfStartDST, in);  
    
    int type = ((BLonCalendarTypeEnum)getTypeOfDescription().getEnum()).getOrdinal();
    if(type == BLonCalendarTypeEnum.CAL_GREG)
      primitiveFromInputStream(GdayOfStartDST, in);   
    else if(type == BLonCalendarTypeEnum.CAL_JUL)                                                                         
      primitiveFromInputStream(JdayOfStartDST, in);   
    else
    {  
      primitiveFromInputStream(MmonthOfStartDST, in);                                           
      primitiveFromInputStream(MweekOfStartDST, in);                                           
      primitiveFromInputStream(MdatedayOfStartDST, in);                                         
    }
    primitiveFromInputStream(hourOfEndDST, in);    
    primitiveFromInputStream(minuteOfEndDST, in);  
    primitiveFromInputStream(secondOfEndDST, in);  
    
    if(type == BLonCalendarTypeEnum.CAL_GREG)
      primitiveFromInputStream(GdayOfEndDST, in); 
    else if(type == BLonCalendarTypeEnum.CAL_JUL)                                                                     
      primitiveFromInputStream(JdayOfEndDST, in); 
    else
    {  
      primitiveFromInputStream(MmonthOfEndDST, in);  
      primitiveFromInputStream(MweekOfEndDST, in);  
      primitiveFromInputStream(MgatedayOfEndDST, in);
    }

  }


}
