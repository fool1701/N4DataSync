/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import javax.baja.lonworks.enums.BLonElementType;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;


/**
 *   This class file represents SNVT_file_pos.
 * <p>
 *
 * @author    Sean Morton
 * @creation  19 July 01
 * @version   $Revision: 2$ $Date: 9/18/01 9:49:40 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "pointer",
  type = "BLonInteger",
  defaultValue = "BLonInteger.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementQualifiers.S32, null)")
)
@NiagaraProperty(
  name = "length",
  type = "BLonInteger",
  defaultValue = "BLonInteger.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementQualifiers.make(BLonElementType.u16,0,65534,1,65535), null)")
)
public class BLonFilePos
  extends BLonData
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonFilePos(3346623213)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "pointer"

  /**
   * Slot for the {@code pointer} property.
   * @see #getPointer
   * @see #setPointer
   */
  public static final Property pointer = newProperty(0, BLonInteger.DEFAULT, LonFacetsUtil.makeFacets(BLonElementQualifiers.S32, null));

  /**
   * Get the {@code pointer} property.
   * @see #pointer
   */
  public BLonInteger getPointer() { return (BLonInteger)get(pointer); }

  /**
   * Set the {@code pointer} property.
   * @see #pointer
   */
  public void setPointer(BLonInteger v) { set(pointer, v, null); }

  //endregion Property "pointer"

  //region Property "length"

  /**
   * Slot for the {@code length} property.
   * @see #getLength
   * @see #setLength
   */
  public static final Property length = newProperty(0, BLonInteger.DEFAULT, LonFacetsUtil.makeFacets(BLonElementQualifiers.make(BLonElementType.u16,0,65534,1,65535), null));

  /**
   * Get the {@code length} property.
   * @see #length
   */
  public BLonInteger getLength() { return (BLonInteger)get(length); }

  /**
   * Set the {@code length} property.
   * @see #length
   */
  public void setLength(BLonInteger v) { set(length, v, null); }

  //endregion Property "length"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonFilePos.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}
