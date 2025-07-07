// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Test: Exact copy of BDataSourceFolder structure
 * This tests if we can replicate the working component exactly
 */
@NiagaraType
@NiagaraProperty(
  name = "displayName",
  type = "baja:String",
  defaultValue = "BString.make(\"Test Data Sources\")"
)
public class BTestDeeper5_ExactDataSourceFolderCopy extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BTestDeeper5_ExactDataSourceFolderCopy(2018014743)1.0$ @*/
/* Generated Mon Jul 07 07:30:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "displayName"

  /**
   * Slot for the {@code displayName} property.
   * @see #getDisplayName
   * @see #setDisplayName
   */
  public static final Property displayName = newProperty(0, BString.make("Test Data Sources"), null);

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
  public static final Type TYPE = Sys.loadType(BTestDeeper5_ExactDataSourceFolderCopy.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTestDeeper5_ExactDataSourceFolderCopy() {
    super();
    System.out.println("🧪 TestDeeper5 - Exact copy of working BDataSourceFolder");
  }
}
