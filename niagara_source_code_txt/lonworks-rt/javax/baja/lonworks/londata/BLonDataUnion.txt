/*
 * Copyright 2010 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import javax.baja.lonworks.datatypes.BUnionQualifiers;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * The BLonDataUnion contains support for BLonData which
 * has a union in its type definition. 
 * <p>
 *
 * @author    Robert Adams
 * @creation  5 May 01
 * @version   $Revision: 8$ $Date: 9/28/01 10:20:42 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "unionQuals",
  type = "BUnionQualifiers",
  defaultValue = "new BUnionQualifiers()",
  flags = Flags.HIDDEN
)
public class BLonDataUnion
    extends BLonData
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonDataUnion(1100424292)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "unionQuals"

  /**
   * Slot for the {@code unionQuals} property.
   * @see #getUnionQuals
   * @see #setUnionQuals
   */
  public static final Property unionQuals = newProperty(Flags.HIDDEN, new BUnionQualifiers(), null);

  /**
   * Get the {@code unionQuals} property.
   * @see #unionQuals
   */
  public BUnionQualifiers getUnionQuals() { return (BUnionQualifiers)get(unionQuals); }

  /**
   * Set the {@code unionQuals} property.
   * @see #unionQuals
   */
  public void setUnionQuals(BUnionQualifiers v) { set(unionQuals, v, null); }

  //endregion Property "unionQuals"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonDataUnion.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////
 
  /**
   *  Verify that the specified lonData has the same set of primitive
   *  data elements by type, qualifiers, and name. Will recurse  into
   *  nested datatypes.
   */
  public boolean hasEquivalentElements(BLonData ld)
  {
    if(!super.hasEquivalentElements(ld)) return false;
    if( !((BLonDataUnion)ld).getUnionQuals().equivalent(getUnionQuals()) ) return false;
    return true;
  }
  public boolean isUnion() { return true; }

////////////////////////////////////////////////////////////////
// Implementation
////////////////////////////////////////////////////////////////
  public Property[] getActiveProperties()
  {
    return getActiveProperties(get(getProperty(getUnionQuals().getConditionProp())));
  }

  public Property[] getActiveProperties(BObject condVal)
  {
    return getUnionQuals().getActiveProperties(condVal);
  }

  Property[] getActiveProps() { return getActiveProperties(); } 
}
