/*
 * Copyright 2010 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BUnionQualifier contains info for one branch in a union.
 * <p>
 * @author    Robert Adams
 * @creation  20 Jan 2010
 * @version   $Revision: 3$ $Date: 9/28/01 10:20:36 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "branch",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "branchProps",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY
)
@NiagaraProperty(
  name = "conditions",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY
)
public final class BUnionQualifier
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BUnionQualifier(2527628776)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "branch"

  /**
   * Slot for the {@code branch} property.
   * @see #getBranch
   * @see #setBranch
   */
  public static final Property branch = newProperty(Flags.READONLY, "", null);

  /**
   * Get the {@code branch} property.
   * @see #branch
   */
  public String getBranch() { return getString(branch); }

  /**
   * Set the {@code branch} property.
   * @see #branch
   */
  public void setBranch(String v) { setString(branch, v, null); }

  //endregion Property "branch"

  //region Property "branchProps"

  /**
   * Slot for the {@code branchProps} property.
   * @see #getBranchProps
   * @see #setBranchProps
   */
  public static final Property branchProps = newProperty(Flags.READONLY, "", null);

  /**
   * Get the {@code branchProps} property.
   * @see #branchProps
   */
  public String getBranchProps() { return getString(branchProps); }

  /**
   * Set the {@code branchProps} property.
   * @see #branchProps
   */
  public void setBranchProps(String v) { setString(branchProps, v, null); }

  //endregion Property "branchProps"

  //region Property "conditions"

  /**
   * Slot for the {@code conditions} property.
   * @see #getConditions
   * @see #setConditions
   */
  public static final Property conditions = newProperty(Flags.READONLY, "", null);

  /**
   * Get the {@code conditions} property.
   * @see #conditions
   */
  public String getConditions() { return getString(conditions); }

  /**
   * Set the {@code conditions} property.
   * @see #conditions
   */
  public void setConditions(String v) { setString(conditions, v, null); }

  //endregion Property "conditions"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUnionQualifier.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public String toString(Context c) { return getConditions(); }

}
