// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Final Test: Exact copy of BDataSourceFolder property structure
 * This tests if we can replicate the exact working property pattern
 */
@NiagaraType
@NiagaraProperty(
  name = "displayName",
  type = "baja:String",
  defaultValue = "BString.make(\"Test Final\")"
)
public class BTestFinal_PropertyIssue extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BTestFinal_PropertyIssue(2018014743)1.0$ @*/
/* Generated Mon Jul 07 07:30:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "displayName"

  /**
   * Slot for the {@code displayName} property.
   * @see #getDisplayName
   * @see #setDisplayName
   */
  public static final Property displayName = newProperty(0, BString.make("Test Final"), null);

  /**
   * Get the {@code displayName} property.
   * @see #displayName
   */
  public BString getDisplayName() { return (BString)get(displayName); }

  /**
   * Set the {@code displayName} property.
   * @see #displayName
   */
  public void setDisplayName(BString v) { set(displayName, v, null); }

  //endregion Property "displayName"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTestFinal_PropertyIssue.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTestFinal_PropertyIssue() {
    super();
    System.out.println("🧪 TestFinal - Exact copy of BDataSourceFolder property pattern");
  }
}
